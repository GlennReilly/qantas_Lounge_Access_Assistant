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
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*


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

    private fun doImageStuff(bitmap: Bitmap) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE,
                FirebaseVisionBarcode.FORMAT_CODE_128).build()

        val image = FirebaseVisionImage.fromBitmap(bitmap)
        FirebaseVision.getInstance()
            .getVisionBarcodeDetector(options)
            //.visionBarcodeDetector
            .detectInImage(image)
            .addOnSuccessListener {
                if (it.isNotEmpty()) {
                    message.text = "Success: ${it.first().rawValue}"
                } else {
                    message.text = "Failed to detect any barcode."
                }
            }
            .addOnFailureListener {
                message.text = it.message
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
            doImageStuff(photo)
        }

    }
}
