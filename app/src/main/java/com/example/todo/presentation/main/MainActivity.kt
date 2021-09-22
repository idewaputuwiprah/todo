package com.example.todo.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.todo.R
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAllPermission()
        createOsmAndroidTileLocation()
    }


    private fun createOsmAndroidTileLocation() {
        Configuration.getInstance().load(
            applicationContext, PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )
        )
    }

    private fun checkAllPermission(): Boolean {
        var result: Int
        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 300)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

}