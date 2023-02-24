package com.example.bpdmiscompose.repositories

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.bpdmiscompose.dataClass.IndikatorDataClass
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
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


val INDIKATOR_KEUANGAN_REF = "indikatorKeuanganUtama"

class IndikatorKeuanganRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) : IndikatorKeuanganRepository {

    private val indikatorKeuanganRef: CollectionReference = Firebase.firestore.collection(
        INDIKATOR_KEUANGAN_REF
    )
    override val currentUser
        get() = firebaseAuth.currentUser

    override suspend fun hasUser(): Boolean = firebaseAuth.currentUser != null
    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override suspend fun getIndikatorKeuangan()
    : Flow<Resources<List<IndikatorDataClass>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = indikatorKeuanganRef
                .orderBy("jenisKinerja", Query.Direction.DESCENDING)
                .orderBy("tahun", Query.Direction.DESCENDING)
                .orderBy("bulan", Query.Direction.DESCENDING)
                .orderBy("jenisKantor", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val indikatorKeuangan = snapshot.toObjects(IndikatorDataClass::class.java)
                        Resources.Success(data = indikatorKeuangan)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }

        } catch (e: Exception) {
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    override suspend fun getIndikatorByParams(
        jenisKinerja: String,
        jenisKantor: String,
        tahun: Int,
        bulan: Int
    ): Flow<Resources<List<IndikatorDataClass>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = indikatorKeuanganRef
                .whereEqualTo("tahun", tahun)
                .whereEqualTo("bulan", bulan)
                .whereEqualTo("jenisKinerja", jenisKinerja)
                .whereEqualTo("jenisKantor", jenisKantor)
                .orderBy("kantor", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val indikatorKeuangan = snapshot.toObjects(IndikatorDataClass::class.java)
                        Resources.Success(data = indikatorKeuangan)
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

    override suspend fun getIndikatorSingular(
        kantor: String,
        jenisKantor: String,
        jenisKinerja: String,
        tahun: Int,
        bulan: Int
    ): Flow<Resources<IndikatorDataClass>> = flow {
        try {
            val result = indikatorKeuanganRef
                .whereEqualTo("kantor", kantor)
                .whereEqualTo("jenisKantor", jenisKantor)
                .whereEqualTo("jenisKinerja", jenisKinerja)
                .whereEqualTo("tahun", tahun)
                .whereEqualTo("bulan", bulan)
                .limit(1)
                .get().await()

            if(result.documents.isNotEmpty()){
                val resultAsClass = result.documents[0].toObject(IndikatorDataClass::class.java)
                emit(Resources.Success(resultAsClass))
                Log.i(ContentValues.TAG, "${Resources.Success(resultAsClass.toString())}")
                emit(Resources.Success(resultAsClass))
            }else {
                emit(Resources.Success(IndikatorDataClass()))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }
    override suspend fun addIndikatorKeuangan(
        kantor: String,
        jenisKantor: String,
        jenisKinerja: String,
        tahun: Int,
        bulan: Int,
        nominal: Double,
        onComplete : (Boolean) -> Unit
    ) {
        val doc = indikatorKeuanganRef.whereEqualTo("tahun", tahun).whereEqualTo("jenisKinerja", jenisKinerja).whereEqualTo("bulan", bulan).whereEqualTo("kantor", kantor).get().await()
        if(doc.isEmpty){
            val documentId = indikatorKeuanganRef.document().id
            val indikatorKeuangan = IndikatorDataClass(
                indikatorID = documentId,
                kantor = kantor,
                jenisKantor = jenisKantor,
                jenisKinerja = jenisKinerja,
                tahun = tahun,
                bulan = bulan,
                nominal = nominal,
                timestamp = Timestamp.now()
            )
            indikatorKeuanganRef
                .document(documentId)
                .set(indikatorKeuangan)
                .addOnCompleteListener{result->
                    onComplete.invoke(result.isSuccessful)
                }
        }else {
            onComplete.invoke(false)
            throw Exception("Data $jenisKinerja untuk kantor $kantor pada bulan $bulan tahun $tahun sudah ada")
        }
    }

    override suspend fun updateIndikatorKeuangan(
        indikatorID: String,
        kantor: String,
        jenisKantor: String,
        jenisKinerja: String,
        tahun: Int,
        bulan: Int,
        nominal: Double,
        onResult: (Boolean) -> Unit
    )  {
        val doc = indikatorKeuanganRef.whereEqualTo("kantor", kantor).whereEqualTo("tahun", tahun).whereEqualTo("bulan", bulan).get().await()
        Log.i(TAG, "updateIndikatorKeuangan: ${doc.documents[0].id}, $indikatorID")
        if (doc.isEmpty || doc.documents[0].id == indikatorID){
            val updateData = hashMapOf<String, Any>(
                "kantor" to kantor,
                "jenisKantor" to jenisKantor,
                "jenisKinerja" to jenisKinerja,
                "tahun" to tahun,
                "bulan" to bulan,
                "nominal" to nominal,
            )

            indikatorKeuanganRef
                .document(indikatorID)
                .update(updateData)
                .addOnCompleteListener{result ->
                    onResult.invoke(result.isSuccessful)
                }
        } else {
            onResult.invoke(false)
            throw Exception("Data $jenisKinerja untuk kantor $kantor pada bulan $bulan tahun $tahun sudah ada. Mohon lakukan perubahaan untuk kombinasi yang sama")
        }
    }

    override suspend fun deleteIndikatorKeuangan(indikatorID: String, onComplete: (Boolean) -> Unit) {
        indikatorKeuanganRef.document(indikatorID)
            .delete()
            .addOnCompleteListener{
                onComplete.invoke(it.isSuccessful)
            }
    }


    override suspend fun getYearList(): Flow<Resources<List<Int>>> = flow{
        try {
            val result = indikatorKeuanganRef.get().await()
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