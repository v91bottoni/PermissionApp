package com.v1bottoni.permissionapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.v1bottoni.permissionapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val REQUEST_CAMERA_CODE = 2
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickButton()
    }

    private fun onClickButton() {
        binding.btnCamera.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                showCustomDialog()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    startCamera()
                }
            }
            else -> {
            }
        }
    }

    private fun showCustomDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.attention))
            .setMessage(getString(R.string.should_grant))
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
                dialog.dismiss()})
            .setNegativeButton(getString(R.string.no_thx), { dialog, which -> dialog.dismiss() })
            .show()
    }

    private fun startCamera() {
        Snackbar.make(binding.root, getString(R.string.camera_enabled), Snackbar.LENGTH_LONG).show()
    }
}