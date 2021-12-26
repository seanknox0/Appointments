package ie.wit.appointments.models

interface AppointmentStore {
    fun findAll(): List<AppointmentModel>
    fun create(appointment: AppointmentModel)
    fun update(appointment: AppointmentModel)
}