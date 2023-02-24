package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.RBBIndikatorDataClass
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

val RBB_REF = "RBB"

class RBBRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) : RBBRepository {
    override val currentUser
        get() = firebaseAuth.currentUser

    override suspend fun hasUser(): Boolean = firebaseAuth.currentUser != null
    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    private val rbbRef : CollectionReference = Firebase.firestore.collection(RBB_REF)
    override suspend fun getRBB()
    : Flow<Resources<List<RBBIndikatorDataClass>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = rbbRef
                .orderBy("tahun", Query.Direction.DESCENDING)
                .orderBy("jenisKinerja", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val rbb = snapshot.toObjects(RBBIndikatorDataClass::class.java)
                        Resources.Success(data = rbb)
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
    override suspend fun getRBBByMetrics(tahun : Int, jenisKinerja: String)
    : Flow<Resources<RBBIndikatorDataClass>> = flow {
        try {
            val result = rbbRef
                .whereEqualTo("tahun", tahun)
                .whereEqualTo("jenisKinerja", jenisKinerja)
                .get().await()
            val resultAsClass = result.documents[0].toObject(RBBIndikatorDataClass::class.java)
            emit(Resources.Success(resultAsClass))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }


    override suspend fun addRBB(kantor : String,  jenisKinerja: String, tahun : Int, nominal: Double, onComplete : (Boolean) -> Unit)
    {
        val doc = rbbRef.whereEqualTo("kantor", kantor).whereEqualTo("jenisKinerja", jenisKinerja).whereEqualTo("tahun", tahun).get().await()
        if(doc.isEmpty){
            val documentId = rbbRef.document().id
            val rbb = RBBIndikatorDataClass(
                kantor = kantor,
                jenisKinerja = jenisKinerja,
                tahun = tahun,
                nominal = nominal,
                rbbid = documentId
            )
            rbbRef
                .document(documentId)
                .set(rbb)
                .addOnCompleteListener{result->
                    onComplete.invoke(result.isSuccessful)
                }
        }else {
            onComplete.invoke(false)
            throw Exception("RBB untuk $kantor pada tahun $tahun dan jenis kinerja $jenisKinerja sudah ada. Mohon hapus terlebih dahulu")
        }
    }
    override suspend fun updateRBB(RBBId : String, kantor: String,  jenisKinerja: String, tahun: Int, nominal: Double, onResult : (Boolean)-> Unit)
    {
        val doc = rbbRef.whereEqualTo("tahun", tahun).whereEqualTo("jenisKinerja", jenisKinerja).whereEqualTo("kantor", kantor).get().await()
        if (doc.isEmpty || doc.documents[0].id == RBBId){
            val updateData = hashMapOf<String, Any>(
                "kantor" to kantor,
                "jenisKinerja" to jenisKinerja,
                "tahun" to tahun,
                "nominal" to nominal
            )
            rbbRef
                .document(RBBId)
                .update(updateData)
                .addOnCompleteListener{result ->
                    onResult.invoke(result.isSuccessful)
                }
        } else {
            onResult.invoke(false)
            throw Exception("RBB untuk $kantor pada tahun $tahun dan jenis kinerja $jenisKinerja sudah ada. Pastikan tidak ada duplikasi data")
        }
    }
    override suspend fun deleteRBB(RBBId : String, onComplete: (Boolean)-> Unit)
    {
        rbbRef
            .document(RBBId)
            .delete()
            .addOnCompleteListener{result ->
                onComplete.invoke(result.isSuccessful)
            }
    }


}