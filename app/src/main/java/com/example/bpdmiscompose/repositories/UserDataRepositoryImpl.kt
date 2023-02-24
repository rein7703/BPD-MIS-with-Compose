package com.example.bpdmiscompose.repositories

import android.content.ContentValues
import android.util.Log
import com.example.bpdmiscompose.dataClass.UserDataClass
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


val USERS_REF = "users"

class UserDataRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) : UserDataRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    private val usersRef: CollectionReference = Firebase.firestore.collection(
        USERS_REF
    )

    override fun getAllUserData(): Flow<Resources<List<UserDataClass>>> = callbackFlow  {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = usersRef
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val users = snapshot.toObjects(UserDataClass::class.java)
                        Resources.Success(data = users)
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
    override fun getUserData(email : String) : Flow<Resources<UserDataClass>> = flow{
        try {
            val result = usersRef.whereEqualTo("email", email).get().await()
            if(result.documents.isNotEmpty()){
                val resultAsClass = result.documents[0].toObject(UserDataClass::class.java)
                emit(Resources.Success(resultAsClass))
                Log.i(ContentValues.TAG, "${Resources.Success(resultAsClass.toString())}")
                emit(Resources.Success(resultAsClass))
            }else {
                emit(Resources.Success(UserDataClass()))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    override fun addUserData(name: String, idPegawai: String, jabatan: String, nomorHP: String, email: String, status: Boolean, onComplete : (Boolean) -> Unit) {
        try {
            val documentId = usersRef.document().id
            val user = UserDataClass(
                name = name,
                idPegawai = idPegawai,
                jabatan = jabatan,
                nomorHP = nomorHP,
                email = email,
                status = status,
                dataId = documentId
            )
            usersRef.document(documentId).set(user)
                .addOnCompleteListener{result->
                onComplete.invoke(result.isSuccessful)
            }
        } catch (e: Exception) {
            onComplete.invoke(false)
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}