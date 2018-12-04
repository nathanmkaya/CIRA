package dita.dev.cira.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dita.dev.cira.model.Issue
import java.io.File

object IssueRepository {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    var img = HashMap<String, Any>()

    fun addIssue(issue: Issue?, callback: Callback) {
        /*db.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()*/

        if (FirebaseAuth.getInstance().currentUser?.isAnonymous == true) {
            println(FirebaseAuth.getInstance().currentUser)
        } else {
            println("Not signed in")
            FirebaseAuth.getInstance().signInAnonymously().addOnFailureListener {
                println("Sign in failed")
            }.addOnCompleteListener {
                println("Sign successful")
            }
        }
        val snapshot = storage.reference.child("issues/${issue?.title}")

        snapshot.putFile(Uri.fromFile(File(issue?.img)))
            .addOnFailureListener {
                callback.onError()
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->

                    issue?.let { issue1 ->
                        db.collection("issues").add(issue1)

                            .addOnCompleteListener { task ->
                                task.result?.update("download_url", uri.toString())
                                callback.onComplete()
                            }.addOnFailureListener {
                                callback.onError()
                            }
                    }

                }
            }
    }

    fun getIssue(): Issue? {
        return null
    }

    interface Callback {
        fun onError()
        fun onComplete()
    }
}