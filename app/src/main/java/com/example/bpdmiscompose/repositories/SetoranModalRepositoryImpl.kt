package com.example.bpdmiscompose.repositories

import android.content.ContentValues
import android.util.Log
import com.example.bpdmiscompose.dataClass.SetoranModal
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

val SETORANMODAL_COLLECTION_REF = "setoranModal"

class SetoranModalRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) : SetoranModalRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun hasUser(): Boolean = firebaseAuth.currentUser != null
    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    private val setoranModalRef: CollectionReference = Firebase.firestore.collection(
        SETORANMODAL_COLLECTION_REF
    )

    override suspend fun getAllSetoranModal(): Flow<Resources<List<SetoranModal>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = setoranModalRef
                .orderBy("tahun", Query.Direction.DESCENDING)
                .orderBy("pemdaId", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val setoranModal = snapshot.toObjects(SetoranModal::class.java)
                        Resources.Success(data = setoranModal)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }

        } catch (e: java.lang.Exception) {
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    override suspend fun getSingleSetoran(
        setoranId: String,
    ) : Flow<Resources<SetoranModal>> = flow{
        try {
            val result = setoranModalRef.document(setoranId).get().await()
            val resultAsClass = result.toObject(SetoranModal::class.java)
            emit(Resources.Success(resultAsClass))
            Log.i(ContentValues.TAG, "${Resources.Success(resultAsClass.toString())}")


        } catch (e: Exception) {
            emit(Resources.Error(e))
        }

    }

    override suspend fun getSetoranByTahun(
        tahun: Int,
    ): Flow<Resources<List<SetoranModal>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = setoranModalRef
                .orderBy("pemdaId", Query.Direction.DESCENDING)
                .whereEqualTo("tahun", tahun)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val setoranModal = snapshot.toObjects(SetoranModal::class.java)
                        Resources.Success(data = setoranModal)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: java.lang.Exception) {
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }
    override suspend fun addSetoran(
        userId : String,
        pemdaId : String,
        tahun: Int,
        modalDisetorRUPS:Long ,
        komposisiRUPS: Int ,
        realisasiDanaSetoranModal: Long ,
        totalModalDesember: Long ,
        timestamp: Timestamp,
        onComplete : (Boolean) -> Unit
    ){

        val doc = setoranModalRef.whereEqualTo("pemdaId", pemdaId).whereEqualTo("tahun", tahun).get().await()
        if(doc.isEmpty){
            val documentId = setoranModalRef.document().id
            val setoran = SetoranModal(pemdaId, tahun, modalDisetorRUPS, komposisiRUPS, realisasiDanaSetoranModal, totalModalDesember, timestamp,
                documentId = documentId
            )
            setoranModalRef
                .document(documentId)
                .set(setoran)
                .addOnCompleteListener{result->
                    onComplete.invoke(result.isSuccessful)
                }
        }else {
            onComplete.invoke(false)
            throw Exception("Data pemda $pemdaId pada tahun $tahun sudah ada. Mohon lakukan perubahan atau hapus data.")
        }

    }

    override fun deleteSetoran (setoranId: String, onComplete: (Boolean) -> Unit){
        setoranModalRef.document(setoranId)
            .delete()
            .addOnCompleteListener{
                onComplete.invoke(it.isSuccessful)
            }
    }

    override suspend fun updateSetoran(
        setoranId:String,
        pemdaId : String,
        tahun: Int,
        modalDisetorRUPS: Long ,
        komposisiRUPS: Int ,
        realisasiDanaSetoranModal: Long ,
        totalModalDesember: Long ,
        timestamp: Timestamp,
        onResult : (Boolean) -> Unit
    ){

        val doc = setoranModalRef.whereEqualTo("pemdaId", pemdaId).whereEqualTo("tahun", tahun).get().await()
        if (doc.isEmpty || doc.documents[0].id == setoranId){
            val updateData = hashMapOf<String, Any>(
                "pemdaId" to pemdaId,
                "tahun" to tahun,
                "modalDisetorRUPS" to modalDisetorRUPS,
                "komposisiRUPS" to komposisiRUPS,
                "realisasiDanaSetoranModal" to realisasiDanaSetoranModal,
                "totalModalDesember" to totalModalDesember,
                "timestamp" to timestamp
            )

            setoranModalRef
                .document(setoranId)
                .update(updateData)
                .addOnCompleteListener{result ->
                    onResult.invoke(result.isSuccessful)
                }
        } else {
            onResult.invoke(false)
            throw Exception("Data pemda $pemdaId pada tahun $tahun sudah ada. Mohon lakukan perubahan pada kombinasi pemda dan tahun yang sama.")
        }

    }

    override suspend fun getAllYears() : Flow<Resources<List<Int>>> = flow{
        try {
            val result = setoranModalRef.get().await()
            val years = mutableListOf<Int>()
            for (document in result.documents) {
                val tahun = document.getLong("tahun")?.toInt() ?: continue
                if (!years.contains(tahun)) {
                    years.add(tahun)
                }
            }
            emit(Resources.Success(years))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }



    override suspend fun getPemdaByYear(tahun : Int) : Flow<Resources<List<String>>> = flow{
        try {
            val result = setoranModalRef.whereEqualTo("tahun", tahun).get().await()
            val pemdaList = mutableListOf<String>()
            for (document in result.documents) {
                val pemda = document.getString("pemdaId") ?: continue
                if (!pemdaList.contains(pemda)) {
                    pemdaList.add(pemda)
                }
            }
            emit(Resources.Success(pemdaList))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    override suspend fun getSingleSetoranByYearAndPemda(pemdaId: String, tahun: Int): Flow<Resources<SetoranModal>> = flow {
        try {
            val result = setoranModalRef.whereEqualTo("pemdaId", pemdaId).whereEqualTo("tahun", tahun).get().await()
            val resultAsClass = result.documents[0].toObject(SetoranModal::class.java)
            emit(Resources.Success(resultAsClass))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    override suspend fun getAllYearsByPemda(pemdaId : String) : Flow<Resources<List<Int>>> = flow{
        try {
            val result = setoranModalRef.whereEqualTo("pemdaId", pemdaId).get().await()
            val years = mutableListOf<Int>()
            for (document in result.documents) {
                val tahun = document.getLong("tahun")?.toInt() ?: continue
                if (!years.contains(tahun)) {
                    years.add(tahun)
                }
            }
            emit(Resources.Success(years))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }





}
