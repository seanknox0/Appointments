package ie.wit.appointments.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentModel(var id: Long = 0,
                            var patient: String = "",
                            var date: String = "",
                            var image: Uri = Uri.EMPTY) : Parcelable