package model

import org.joda.time.DateTime

data class FlightDetailsCollection(val flightDetailsCollection: List<FlightDetails>)

data class FlightDetails(val flightNumber:String, val departureDate: DateTime)
