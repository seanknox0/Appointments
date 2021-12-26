package ie.wit.appointments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.appointments.databinding.CardPlacemarkBinding
import ie.wit.appointments.models.AppointmentModel

interface AppointmentListener {
    fun onAppointmentClick(appointment: AppointmentModel)
}

class AppointmentAdapter constructor(private var appointments: List<AppointmentModel>,
                                   private val listener: AppointmentListener) :
    RecyclerView.Adapter<AppointmentAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val appointment = appointments[holder.adapterPosition]
        holder.bind(appointment, listener)
    }

    override fun getItemCount(): Int = appointments.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: AppointmentModel, listener: AppointmentListener) {
            binding.appointmentPatient.text = appointment.patient
            binding.aptdate.text = appointment.date
            Picasso.get().load(appointment.image).resize(200, 200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onAppointmentClick(appointment) }
        }
    }
}