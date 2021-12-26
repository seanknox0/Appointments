package ie.wit.appointments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.appointments.databinding.CardPlacemarkBinding
import ie.wit.appointments.models.AppointmentModel

class AppointmentAdapter constructor(private var appointments: List<AppointmentModel>) :
    RecyclerView.Adapter<AppointmentAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val appointment = appointments[holder.adapterPosition]
        holder.bind(appointment)
    }

    override fun getItemCount(): Int = appointments.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: AppointmentModel) {
            binding.appointmentPatient.text = appointment.patient
            binding.aptdate.text = appointment.date
        }
    }
}