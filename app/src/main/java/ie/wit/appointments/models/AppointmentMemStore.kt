package ie.wit.appointments.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class AppointmentMemStore : AppointmentStore {

    val appointments = ArrayList<AppointmentModel>()

    override fun findAll(): List<AppointmentModel> {
        return appointments
    }

    override fun create(appointment: AppointmentModel) {
        appointment.id = getId()
        appointments.add(appointment)
        logAll()
    }

    override fun update(appointment: AppointmentModel) {
        val foundAppointment: AppointmentModel? = appointments.find { p -> p.id == appointment.id }
        if (foundAppointment != null) {
            foundAppointment.patient = appointment.patient
            foundAppointment.date = appointment.date
            foundAppointment.image = appointment.image
            foundAppointment.lat = appointment.lat
            foundAppointment.lng = appointment.lng
            foundAppointment.zoom = appointment.zoom
            logAll()
        }
    }

    fun logAll() {
        appointments.forEach{ i("${it}") }
    }
}