package com.tikal.weather.service

import org.springframework.stereotype.Service
//import com.tikal.weather.model.RealTimeData
//import com.tikal.weather.dao.RealTimeDataDao
import org.springframework.beans.factory.annotation.Autowired
import scala.collection.JavaConversions.asScalaBuffer
import org.slf4j.{ Logger, LoggerFactory }
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData

@Service
class AlonRealTimeTemperature {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RealTimeTemperature])

  @Autowired
  val dao: RealTimeDataDao = null;

  @Autowired
  val rtService: RTDataService = null;

  /**
   * Implement this service correctly!
   */
  def temperatureForOneDay(stationId: String, day: String, month: String, year: String): String = {
    // one option is to retrieve all data for that month:
    val monthInt = month.toInt
    val dayInt = year.toInt
    val all: List[RealTimeData] = rtService.findByStationAndMonthYear(stationId, monthInt, dayInt)

    // and work from there
    val dayTemp = all.filter { rtd => (rtd.getDate().split("-")(0) == day) }.map { rtd => "['" + rtd.getTime() + "', " + rtd.getTemperature() + "]" }
    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
    println(dayTemp)
    "[ ['Time', 'Temperature']," + dayTemp.mkString(",") + "]"
  }

  def minMaxTemperature(stationId: String, month: String, year: String): String = {

    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:

    val xx = List(
      "['01-06-2016',35.0,24.0]",
      "['02-06-2016',34.0,23.0]",
      "['03-06-2016',35.0,24.0]",
      "['04-06-2016',34.0,23.0]")
    logger.info(xx.mkString(","))
    "[ ['Date', 'Min Temperature', 'Max Temperature']," + xx.mkString(",") + "]"
    //"[ [\"Date\", \"Min Temperature\", \"Max Temperature\"]," + xx.mkString(",") + "]"

  }

}