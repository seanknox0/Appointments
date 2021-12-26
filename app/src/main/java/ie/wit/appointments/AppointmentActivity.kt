package ie.wit.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.appointments.R
import timber.log.Timber
import timber.log.Timber.i

class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        Timber.plant(Timber.DebugTree())

        i("Appointment Activity started...")
    }
}