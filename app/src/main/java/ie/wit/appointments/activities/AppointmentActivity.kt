package ie.wit.appointments.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.appointments.R
import ie.wit.appointments.databinding.ActivityAppointmentBinding
import ie.wit.appointments.main.MainApp
import ie.wit.appointments.models.AppointmentModel
import timber.log.Timber
import timber.log.Timber.i
import java.util.ArrayList

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    var appointment = AppointmentModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        var edit = false

        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        app = application as MainApp
        i("Appointment Activity started...")

        if (intent.hasExtra("appointment_edit")) {
            edit = true
            appointment = intent.extras?.getParcelable("appointment_edit")!!
            binding.appointmentPatient.setText(appointment.patient)
            binding.aptdate.setText(appointment.date)
            binding.btnAdd.setText(R.string.save_appointment)
        }

        binding.btnAdd.setOnClickListener() {
            appointment.patient = binding.appointmentPatient.text.toString()
            appointment.date = binding.aptdate.text.toString()

            if (appointment.patient.isEmpty()) {
                Snackbar.make(it,R.string.enter_appointment_title, Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                if (edit) {
                    app.appointments.update(appointment.copy())
                } else {
                    app.appointments.create(appointment.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_appointment, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}