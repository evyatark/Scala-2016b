package com.tikal.weather.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tikal.weather.dao.RealTimeDataDao

import javax.persistence.Entity

@Service
class RealTimeTemperature {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RealTimeTemperature])

  @Autowired
  val dao : RealTimeDataDao = null;

  @Autowired
  val rtService : RTDataService = null ;

  
  /**
   * Implement this service correctly!
   */
  def temperatureForOneDay(stationId : String, day : String, month : String, year : String): String = {
    // one option is to retrieve all data for that month:
    //val all: List[RealTimeData] = rtService.findByStationAndMonthYear(stationId, month.toInt, year.toInt)
    // and work from there

    val d = rtService.findByStationAndMonthYear(stationId, month.toInt, year.toInt).
      filter(rtd => {rtd.date.split("-")(0).toInt==day.toInt}).
      map(rtd => {"['"+rtd.time+"', " + rtd.temperature + "],"}).
      reduceLeft((a:String,b:String)=>a+b)
//    println(d)
    "[ ['Time', 'Temperature']," + d + "]"
    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
//    "[ ['Time', 'Temperature']," +
//    "['00:10', 21.0]," +
//    "['00:20', 21.4]," +
//    "['00:30', 22.0]," +
//    "['00:40', 22.5]," +
//    "['00:50', -23.0]," +
//    "['01:00', 24.0]" +
//    // ...
//    "]"
  }


  
  def minMaxTemperature(stationId : String, month : String, year : String): String = {

    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:

    val xx = List(
        "['01-06-2016',35.0,24.0]",
        "['02-06-2016',34.0,23.0]",
        "['03-06-2016',35.0,24.0]",
        "['04-06-2016',34.0,23.0]"
        )
    logger.info(xx.mkString(","))
    "[ ['Date', 'Min Temperature', 'Max Temperature']," + xx.mkString(",") + "]"
    //"[ [\"Date\", \"Min Temperature\", \"Max Temperature\"]," + xx.mkString(",") + "]"

  }
  

  

}