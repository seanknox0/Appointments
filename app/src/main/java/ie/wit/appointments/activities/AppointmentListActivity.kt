package ie.wit.appointments.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.appointments.R
import ie.wit.appointments.adapters.AppointmentAdapter
import ie.wit.appointments.adapters.AppointmentListener
import ie.wit.appointments.databinding.ActivityAppointmentListBinding
import ie.wit.appointments.main.MainApp
import ie.wit.appointments.models.AppointmentModel


class AppointmentListActivity : AppCompatActivity(), AppointmentListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityAppointmentListBinding


    override fun onAppointmentClick(appointment: AppointmentModel) {
        val launcherIntent = Intent(this, AppointmentActivity::class.java)
        launcherIntent.putExtra("appointment_edit", appointment)
        startActivityForResult(launcherIntent,0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = AppointmentAdapter(app.appointments.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AppointmentActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}