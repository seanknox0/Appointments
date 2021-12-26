package ie.wit.appointments.main

import android.app.Application
import ie.wit.appointments.models.AppointmentMemStore
// import ie.wit.appointments.models.AppointmentModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val appointments = ArrayList<AppointmentModel>()
    val appointments = AppointmentMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Application started")
        // appointments.add(AppointmentModel("One", "About one..."))
        // appointments.add(AppointmentModel("Two", "About two..."))
        // appointments.add(AppointmentModel("Three", "About three..."))
    }
}