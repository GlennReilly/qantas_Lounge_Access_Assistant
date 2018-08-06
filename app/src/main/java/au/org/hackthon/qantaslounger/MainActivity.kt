package au.org.hackthon.qantaslounger

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import data.FlightDetailDataProvider
import android.graphics.Bitmap
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    companion object {
        internal val REQUEST_TAKE_PHOTO = 1
        internal val MY_CAMERA_PERMISSION_CODE = 100
    }

    val scanButton: Button? = null
    val imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FlightDetailDataProvider.initialise(applicationContext)

        val scanButton = findViewById(R.id.scanButton) as Button
        val imageView = findViewById(R.id.imageView) as ImageView

        scanButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }


    private fun dispatchTakePictureIntent() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf( Manifest.permission.CAMERA),
                MY_CAMERA_PERMISSION_CODE)
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            imageView?.setImageBitmap(photo)
        }

    }
}
