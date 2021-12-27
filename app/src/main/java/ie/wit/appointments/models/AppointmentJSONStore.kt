package ie.wit.appointments.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.appointments.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "appointments.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<AppointmentModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class AppointmentJSONStore(private val context: Context) : AppointmentStore {

    var appointments = mutableListOf<AppointmentModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<AppointmentModel> {
        logAll()
        return appointments
    }

    override fun create(appointment: AppointmentModel) {
        appointment.id = generateRandomId()
        appointments.add(appointment)
        serialize()
    }


    override fun update(appointment: AppointmentModel) {
        val appointmentsList = findAll() as ArrayList<AppointmentModel>
        var foundAppointment: AppointmentModel? = appointmentsList.find { p -> p.id == appointment.id }
        if (foundAppointment != null) {
            foundAppointment.patient = appointment.patient
            foundAppointment.date = appointment.date
            foundAppointment.time = appointment.time
            foundAppointment.service = appointment.service
            foundAppointment.image = appointment.image
            foundAppointment.lat = appointment.lat
            foundAppointment.lng = appointment.lng
            foundAppointment.zoom = appointment.zoom
        }
        serialize()
    }

    override fun delete(appointment: AppointmentModel) {
        appointments.remove(appointment)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(appointments, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        appointments = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        appointments.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}