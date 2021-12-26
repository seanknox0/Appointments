package ie.wit.appointments.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentModel(var id: Long = 0,
                            var patient: String = "",
                            var date: String = "") : Parcelable