package com.example.locationcontrol.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationcontrol.R
import com.example.locationcontrol.data.*
import com.example.locationcontrol.data.model.Location
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , LocationsAdapter.BBListener{

    val locationViewModel = LocationsViewModel(locationsRepository = LocationsRepository(dataSource = LocationsDataSource()))

    val adapter = LocationsAdapter(ArrayList<Location>(),this)

    var listChanged : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locations_recycler.adapter = adapter

        val layoutManger = LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false)
        locations_recycler.layoutManager = layoutManger

        locationViewModel.locations.observe(this, Observer {
            adapter.list.clear()
            adapter.list.addAll(it)
            adapter.notifyDataSetChanged()
        })

        locationViewModel.sendingResult.observe(this, Observer {
            Toast.makeText(this, "Wysyłano", Toast.LENGTH_LONG).show()
        })

        locationViewModel.compatibility.observe(this, Observer{
            listChanged = true
            adapter.notifyDataSetChanged()
        })


        locationViewModel.errorMessage.observe(this, Observer {
            adapter.notifyDataSetChanged()
            Toast.makeText(this, it,Toast.LENGTH_LONG).show()
        })

        locationViewModel.sendingState.observe(this, Observer {
            if(it){
                sending_pb.visibility = View.VISIBLE
                radioGroup.visibility = View.GONE
                locations_recycler.visibility = View.GONE
                send_button.visibility = View.GONE
            }
            else{
                sending_pb.visibility = View.GONE
                radioGroup.visibility = View.VISIBLE
                locations_recycler.visibility = View.VISIBLE
                send_button.visibility = View.VISIBLE
            }
        })

        locations_recycler.addItemDecoration(RecyclerMarginDecorator())

    }

    fun onRadioClick(view: View){
        if(listChanged){
            val yesNoDialog = YesNoDialog(){
                locationViewModel.loadLocations(view.id)
            }
            yesNoDialog.show(supportFragmentManager,"")
        }
        else {
            locationViewModel.loadLocations(view.id)
        }
    }

    private fun onLogoutClick(){
        val loginRepository= LoginRepository(dataSource = LoginDataSource(), context = this)
        loginRepository.logout()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onSendClick(view : View){
        locationViewModel.send(adapter.list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onLogoutClick()
        return true
    }

    override fun onBBChanged(location: Location) {
        locationViewModel.checkCompatibility(location)
    }


}