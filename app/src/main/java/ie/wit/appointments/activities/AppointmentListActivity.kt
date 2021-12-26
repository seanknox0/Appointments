package ie.wit.appointments.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>


    override fun onAppointmentClick(appointment: AppointmentModel) {
        val launcherIntent = Intent(this, AppointmentActivity::class.java)
        launcherIntent.putExtra("appointment_edit", appointment)
        refreshIntentLauncher.launch(launcherIntent)
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

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AppointmentActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}