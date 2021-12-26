package ie.wit.appointments.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.appointments.databinding.ActivityAppointmentBinding
import ie.wit.appointments.models.AppointmentModel
import timber.log.Timber
import timber.log.Timber.i
import java.util.ArrayList

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    var appointment = AppointmentModel()
    val appointments = ArrayList<AppointmentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Appointment Activity started...")

        binding.btnAdd.setOnClickListener() {
            appointment.patient = binding.appointmentPatient.text.toString()
            appointment.date = binding.aptdate.text.toString()

            if (appointment.patient.isNotEmpty()) {
                appointments.add(appointment.copy())
                i("add Button Pressed: ${appointment}")
                for (i in appointments.indices)
                { i("Appointment[$i]:${this.appointments[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a patient name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}