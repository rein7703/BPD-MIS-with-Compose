package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.SkedulSetoranModal
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

val SKEDUL_SETORAN_COLLECTION_REF = "skedulSetoranModal"

class SkedulSetoranRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) : SkedulSetoranRepository {
    override val currentUser
        get() = firebaseAuth.currentUser

    override suspend fun hasUser(): Boolean = firebaseAuth.currentUser != null
    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    private val skedulSetoranRef: CollectionReference = Firebase.firestore.collection(
        SKEDUL_SETORAN_COLLECTION_REF
    )

    override suspend fun getSkedulSetoran(): Flow<Resources<List<SkedulSetoranModal>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = skedulSetoranRef
                .orderBy("tahun", Query.Direction.DESCENDING)
                .orderBy("pemegangSaham", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val skedulSetoranModal = snapshot.toObjects(SkedulSetoranModal::class.java)
                        Resources.Success(data = skedulSetoranModal)
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

    override suspend fun getSkedulSetoranByPemegangSaham(
        pemegangSaham: String,
    ): Flow<Resources<List<SkedulSetoranModal>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = skedulSetoranRef
                .orderBy("tahun", Query.Direction.DESCENDING)
                .whereEqualTo("pemegangSaham", pemegangSaham)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val skedulSetoranModal = snapshot.toObjects(SkedulSetoranModal::class.java)
                        Resources.Success(data = skedulSetoranModal)
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

    override suspend fun getSkedulSetoranByTahun(tahun: Int): Flow<Resources<List<SkedulSetoranModal>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = skedulSetoranRef
                .orderBy("pemegangSaham", Query.Direction.DESCENDING)
                .whereEqualTo("tahun", tahun)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val skedulSetoranModal = snapshot.toObjects(SkedulSetoranModal::class.java)
                        Resources.Success(data = skedulSetoranModal)
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

    override suspend fun addSkedulSetoran(
        pemegangSaham: String,
        tahun: Int,
        nominal: Long,
        onComplete : (Boolean) -> Unit
    ) {
        val doc = skedulSetoranRef.whereEqualTo("pemegangSaham", pemegangSaham).whereEqualTo("tahun", tahun).get().await()
        if(doc.isEmpty){
            val documentId = skedulSetoranRef.document().id
            val skedulSetoranModal = SkedulSetoranModal(
                pemegangSaham = pemegangSaham,
                tahun = tahun,
                nominal = nominal,
                skedulId = documentId,
                timestamp = Timestamp.now()
            )
            skedulSetoranRef
                .document(documentId)
                .set(skedulSetoranModal)
                .addOnCompleteListener{result->
                    onComplete.invoke(result.isSuccessful)
                }
        }else {
            onComplete.invoke(false)
            throw Exception("Data pemda $pemegangSaham pada tahun $tahun sudah ada. Mohon lakukan perubahan atau hapus data.")
        }
    }

    override suspend fun updateSkedulSetoran(
        pemegangSaham: String,
        tahun: Int,
        nominal : Long,
        skedulId : String,
        onResult: (Boolean) -> Unit
    ) {
        val doc = skedulSetoranRef.whereEqualTo("pemegangSaham", pemegangSaham).whereEqualTo("tahun", tahun).get().await()
        if (doc.isEmpty || doc.documents[0].id == skedulId){
            val updateData = hashMapOf<String, Any>(
                "pemegangSaham" to pemegangSaham,
                "nominal" to nominal,
                "tahun" to tahun
            )

            skedulSetoranRef
                .document(skedulId)
                .update(updateData)
                .addOnCompleteListener{result ->
                    onResult.invoke(result.isSuccessful)
                }
        } else {
            onResult.invoke(false)
            throw Exception("Data pemda $pemegangSaham pada tahun $tahun sudah ada. Mohon lakukan perubahan pada kombinasi pemda dan tahun yang sama.")
        }
    }

    override suspend fun deleteSkedulSetoran(skedulId: String, onComplete : (Boolean) -> Unit ) {
        skedulSetoranRef.document(skedulId)
            .delete()
            .addOnCompleteListener{
                onComplete.invoke(it.isSuccessful)
            }
    }

    override suspend fun getYearList(): Flow<Resources<List<Int>>> = flow{
        try {
            val result = skedulSetoranRef.get().await()
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


    override suspend fun getPemegangSahamList(): Flow<Resources<List<String>>> = flow{
        try{
            val result = skedulSetoranRef.get().await()
            val pemagangSahamList = mutableListOf<String>()
            for (document in result.documents) {
                val pemegangSaham = document.getString("pemegangSaham") ?: continue
                if (!pemagangSahamList.contains(pemegangSaham)) {
                    pemagangSahamList.add(pemegangSaham)
                }
            }
            emit(Resources.Success(pemagangSahamList))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }


}