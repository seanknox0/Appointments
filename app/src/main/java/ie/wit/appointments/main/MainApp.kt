package ie.wit.appointments.main

import android.app.Application
import ie.wit.appointments.models.AppointmentJSONStore
import ie.wit.appointments.models.AppointmentMemStore
import ie.wit.appointments.models.AppointmentStore
// import ie.wit.appointments.models.AppointmentModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var appointments: AppointmentStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //appointments = AppointmentMemStore()
        appointments = AppointmentJSONStore(applicationContext)
        i("Appointment System started")
    }
}