package dita.dev.cira.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import com.nguyenhoanglam.imagepicker.model.Image
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Issue(
    var title: String,
    var description: String,
    var img: String? = null,
    var witnesses: Int = 0,
    var location: String = "",
    @ServerTimestamp val timeCreated: Timestamp? = null,
    val download_url: String? = null,
    val fixed: Boolean = false,
    val witness_accepted: Int = 0
): Parcelable {
    constructor(): this("", "", null, 0, "")
}