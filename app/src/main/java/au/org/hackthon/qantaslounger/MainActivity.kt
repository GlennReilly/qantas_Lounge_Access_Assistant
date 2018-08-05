package au.org.hackthon.qantaslounger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import data.FlightDetailDataProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FlightDetailDataProvider.initialise(applicationContext)
    }
}
