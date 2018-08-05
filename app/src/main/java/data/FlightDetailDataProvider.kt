package data

import android.content.Context
import model.FlightDetailsCollection
import java.io.InputStream
import java.io.InputStreamReader
import utils.SerializerUtil


object FlightDetailDataProvider {
    const val sourceDataFileName = "FlightDetails.json"
    var flightDetailsCollection: FlightDetailsCollection? = null

    fun initialise(applicationContext: Context) {
        val gsonBuilder = SerializerUtil.setupGsonDateTimeParsing()
        val inputStream: InputStream = applicationContext?.assets.open(sourceDataFileName)
        val inputStreamReader = InputStreamReader(inputStream)
        val gson = gsonBuilder.create()
        flightDetailsCollection = gson.fromJson(inputStreamReader, FlightDetailsCollection::class.java)
    }
}


