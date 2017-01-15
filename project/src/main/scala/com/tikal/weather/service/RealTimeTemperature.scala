package com.tikal.weather.service

import org.springframework.stereotype.Service
//import com.tikal.weather.model.RealTimeData
//import com.tikal.weather.dao.RealTimeDataDao
import org.springframework.beans.factory.annotation.Autowired
import scala.collection.JavaConversions.asScalaBuffer
import org.slf4j.{Logger, LoggerFactory}
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.YearMonth
import scala.util.Try

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
    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
    // "[ ['Time', 'Temperature'], ['00:10', 21.0], ... ]
    val dayStats = statsForOneDay(stationId, day, month, year)
    val dataStr = dayStats.map({ x => s"['${x.time}', ${x.minTemperature}, ${x.maxTemperature}]"}).mkString(", ")

    "[ ['Time', 'Min Temperature', 'Max Temperature']," + dataStr + "]"
  }

  def statsForOneDay(stationId : String, day : String, month : String, year : String): List[RealTimeData] = {
    val fromStr: String = List(month, day, year).mkString("-")
    val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyy");
    val fromLocDate : LocalDate = LocalDate.parse(fromStr, formatter);
    val zoneId : ZoneId = ZoneId.systemDefault();
    val fromEpoch = fromLocDate.atStartOfDay(zoneId).toEpochSecond() * 1000
    val toDate = fromLocDate.plusDays(1)
    val toEpoch = toDate.atStartOfDay(zoneId).toEpochSecond() * 1000
    rtService.findByStasionAndDateRangeEpoch(
                                          stationId,
                                          fromEpoch,
                                          toEpoch
                                       )

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