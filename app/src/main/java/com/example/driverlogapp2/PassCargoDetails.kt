package com.example.driverlogapp2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.driverlogapp2.databinding.ActivityPassCargoDetailsBinding
import com.example.driverlogapp2.databinding.ActivityRequestDetailsBinding

class PassCargoDetails : AppCompatActivity() {

    lateinit var binding: ActivityPassCargoDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassCargoDetailsBinding.inflate(getLayoutInflater())
        setContentView(binding.getRoot())

        if (intent == null) {
            return
        }
        binding.routeInstanceDest.setText(intent.getStringExtra("address"))

        var text = ""
        // NEW PASS
        var newPassengers = intent.getSerializableExtra("newPassengers") as ArrayList<String>?;
        text = setPassCargo(newPassengers)
        binding.AllNewPassengers.text = text

        // EXIT PASS
        var exitPassengers = intent.getSerializableExtra("exitPassengers") as ArrayList<String>?;
        text = setPassCargo(exitPassengers)
        binding.AllDelPassengers.text = text

        // NEW CARGO
        var newCargo = intent.getSerializableExtra("newCargo") as ArrayList<String>?;
        text = setPassCargo(newCargo)
        binding.AllNewCargos.text = text

        // EXIT CARGO
        var exitCargo = intent.getSerializableExtra("exitCargo") as ArrayList<String>?;
        text = setPassCargo(exitCargo)
        binding.AllDelCargos.text = text


    }

    fun setPassCargo(items: ArrayList<String>?): String {
        var ListText = ""
        if (items != null && items.size != 0) {
            for (item in items) {
                ListText += item + "\n"
            }
        } else {
            ListText = "Не требуется\n"
        }
        return ListText;
    }

}