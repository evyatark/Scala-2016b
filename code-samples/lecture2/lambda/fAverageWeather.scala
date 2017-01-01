package com.tikal.workshop

import scala.collection.mutable.ListBuffer

object AverageWeather {
  
  def main(args : Array[String]) : Unit = {
    
        
    val listOfWeatherDays = List( new WeatherData("01-06-2016", 34.8),
                                  new WeatherData("02-06-2016", 33.4),
                                  new WeatherData("03-06-2016", 39.1),
                                  new WeatherData("04-06-2016", 41.5),
                                  new WeatherData("05-06-2016", 35.6),
                                  new WeatherData("06-06-2016", 31.8),
                                  new WeatherData("07-06-2016", 35.4),
                                  new WeatherData("08-06-2016", 37.6),
                                  new WeatherData("09-06-2016", 28.6),
                                  new WeatherData("10-06-2016", 28.5),
                                  new WeatherData("11-06-2016", 29.2),
                                  new WeatherData("12-06-2016", 31.5),
                                  new WeatherData("13-06-2016", 37.4),
                                  new WeatherData("14-06-2016", 37.4),
                                  new WeatherData("15-06-2016", 35.9),
                                  new WeatherData("16-06-2016", 30.3),
                                  new WeatherData("17-06-2016", 31.9),
                                  new WeatherData("18-06-2016", 34.0),
                                  new WeatherData("19-06-2016", 35.7),
                                  new WeatherData("01-07-2016", 34.8),
                                  new WeatherData("02-07-2016", 33.4),
                                  new WeatherData("03-07-2016", 39.1),
                                  new WeatherData("04-07-2016", 41.5),
                                  new WeatherData("05-07-2016", 35.6),
                                  new WeatherData("06-07-2016", 31.8),
                                  new WeatherData("20-06-2016", 41.5)  // actually was different
    )
    
    def f( w : WeatherData) : Boolean = {
      val s = w.date.split("-")
      s(0).toInt <= 20 && s(1).toInt == 6
    }
    // 1 - what was the averaged temperature in June (1st - 20th)?
    val x = listOfWeatherDays.filter(f);
    //def fff(d1 : Double, d2: Double) : Double =  d1 + d2
    val average = x.map(_.maxTemperature).reduce(_+_) / x.length
    println(s"Average: ${average}");
    
    // 2 - what was the maximal temperature?
    val theMax = 2.0
    println(s"Max: $theMax");
    
    // 3 - what was the maximal temperature, and at what date it occured?
    // Java way
    var dayOfMax : String = "" ;
    var maxTemerature : Double = -100.0 ;
    for (day <- listOfWeatherDays) {
      // use day.maxTemperature and day.date
      // ...
    }
    println(s"Java: Max: $maxTemerature was at $dayOfMax");
  

    // 4 - what was the maximal temperature, and at what date it occured?
    // Scala way
    val theMax2 = 3.0
    val theDate2 = "xyz"
    println(s"Scala: Max: $theMax2 was at $theDate2");
  }
}

class WeatherData(val date : String, val maxTemperature : Double);
