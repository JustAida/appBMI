import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val gender: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val lifeStyle: String
): Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

}
