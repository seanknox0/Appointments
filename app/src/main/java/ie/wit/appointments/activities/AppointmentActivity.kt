package ie.wit.appointments.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.appointments.R
import ie.wit.appointments.databinding.ActivityAppointmentBinding
import ie.wit.appointments.helpers.showImagePicker
import ie.wit.appointments.main.MainApp
import ie.wit.appointments.models.AppointmentModel
import ie.wit.appointments.models.Location
import timber.log.Timber.i

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    var appointment = AppointmentModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
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
            binding.apttime.setText(appointment.time)
            binding.service.setText(appointment.service)
            binding.btnAdd.setText(R.string.save_appointment)
            Picasso.get()
                .load(appointment.image)
                .into(binding.appointmentImage)
            if (appointment.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_appointment_image)
            }
        }

        binding.appointmentLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (appointment.zoom != 0f) {
                location.lat =  appointment.lat
                location.lng = appointment.lng
                location.zoom = appointment.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.btnAdd.setOnClickListener() {
            appointment.patient = binding.appointmentPatient.text.toString()
            appointment.date = binding.aptdate.text.toString()
            binding.apttime.setText(appointment.time)
            binding.service.setText(appointment.service)

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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_appointment, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.appointments.delete(appointment)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            appointment.lat = location.lat
                            appointment.lng = location.lng
                            appointment.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            appointment.image = result.data!!.data!!
                            Picasso.get()
                                   .load(appointment.image)
                                   .into(binding.appointmentImage)
                            binding.chooseImage.setText(R.string.change_appointment_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}