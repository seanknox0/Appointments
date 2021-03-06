package ie.wit.appointments.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentModel(var id: Long = 0,
                            var patient: String = "",
                            var date: String = "",
                            var time: String = "",
                            var service: String = "",
                            var image: Uri = Uri.EMPTY,
                            var lat : Double = 0.0,
                            var lng: Double = 0.0,
                            var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable