package model

import org.joda.time.DateTime

data class FlightDetailsCollection(val flightDetailsCollection: List<FlightDetails>)

data class FlightDetails(val flightNumber:String, val departureDate: DateTime, val passengerManifest: List<PassengerManifest>)

data class PassengerManifest(val firstName: String?, val lastName: String, val ffNum:String?, val tier:String?)
