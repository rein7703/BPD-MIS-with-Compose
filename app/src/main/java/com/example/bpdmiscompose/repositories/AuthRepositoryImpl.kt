package com.example.bpdmiscompose.repositories
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject



class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth

) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    private val usersRef: CollectionReference = Firebase.firestore.collection(
        USERS_REF
    )
    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }


    override suspend fun addUser(email: String, password: String, emailAdmin:String, passwordAdmin : String, onComplete: (Boolean) -> Unit) {
        try {
            val credential = EmailAuthProvider.getCredential(emailAdmin, passwordAdmin)
            currentUser?.reauthenticate(credential)?.addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User re-authenticated.")
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            onComplete(true)
                            Log.d(TAG, "createUserWithEmail:success")
                            logout()
                            firebaseAuth.signInWithEmailAndPassword(emailAdmin, passwordAdmin).addOnCompleteListener { task ->
                                Log.i(TAG, "User re-login: ${emailAdmin} $passwordAdmin")
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Berhasil login lagi.")
                                } else {
                                    Log.d(TAG, "Gagal login lagi")
                                }
                            }
                        } else {
                            onComplete(false)
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        }
                    }
                    onComplete(true)
                } else {
                    Log.d(TAG, "User re-authenticated failed. ${task.exception}")
                    onComplete(false)
                }
            }


        } catch (e: Exception) {
            onComplete(false)
            e.printStackTrace()
        }
    }
    override suspend fun updatePassword(email: String, oldPassword : String, newPassword : String, onComplete:(Boolean) -> Unit) {
        try{
            val credential = EmailAuthProvider.getCredential(email, oldPassword)
            currentUser?.reauthenticate(credential)?.addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User re-authenticated.")
                    currentUser?.updatePassword(newPassword)!!.addOnCompleteListener{ it ->
                        if (it.isSuccessful){
                            Log.d(TAG, "User password updated.")
                        } else {
                            Log.d(TAG, "User password update failed.")
                        }
                    }
                    onComplete(true)
                } else {
                    Log.d(TAG, "User re-authenticated failed. ${task.exception}")
                    onComplete(false)
                }
            }
        } catch (e: Exception) {
            onComplete(false)
            e.printStackTrace()

        }
    }


}