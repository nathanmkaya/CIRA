package dita.dev.cira.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class Utils {
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()


}