package com.tikal.workshop

object Weather {
  def main(args: Array[String]): Unit = {

    val data = List(
      new WeatherData("01-06-2016", 34.8),
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
      new WeatherData("16-06-2017", 30.3),
      new WeatherData("17-06-2017", 31.9),
      new WeatherData("18-06-2017", 34.0),
      new WeatherData("19-06-2017", 35.7),
      new WeatherData("20-06-2017", 41.5));
    
    val listOfNumbers = data.filter ( x => x.date.endsWith("06-2016") ).map ( x => x.temperature )
    val average = listOfNumbers.sum / listOfNumbers.size ;
    
    println(average);
      
    
  }
}

class WeatherData(val date: String, val temperature: Double) 
