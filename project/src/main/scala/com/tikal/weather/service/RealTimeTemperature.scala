package com.tikal.weather.service

import org.springframework.stereotype.Service
//import com.tikal.weather.model.RealTimeData
//import com.tikal.weather.dao.RealTimeDataDao
import org.springframework.beans.factory.annotation.Autowired
import scala.collection.JavaConversions.asScalaBuffer
import org.slf4j.{Logger, LoggerFactory}
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData

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
    
    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
    "[ ['Time', 'Temperature']," +
    "['00:10', 21.0]," +
    "['00:20', 21.4]," +
    "['00:30', 22.0]," +
    "['00:40', 22.5]," +
    "['00:50', 23.0]," +
    "['01:00', 24.0]" +
    // ...
    "]"
  }

  def minMaxTemperature_old(stationId : String): String = {

    val all: List[RealTimeData] = asScalaBuffer(dao.findByStationId(stationId)).toList
    val allData = all.map { x => (x.date.split("-")(0), x.minTemperature, x.maxTemperature) }
    val maxMinPerDay =
      for (i <- (1 to 30)) yield {
        (i, allData.filter(t => t._1.toInt == i).map(t => t._3.toDouble).max, allData.filter(t => t._1.toInt == i).map(t => t._2.toDouble).min)
      }
    //val xx = all.map { x => s"[${x.date.split("-")(0)},${x.minTemperature},${x.maxTemperature}]" }
    val xx = maxMinPerDay.map { x => s"[${x._1},${x._3},${x._2}]" }
    logger.info(xx.mkString(","))
    "[ ['Date', 'Min Temperature', 'Max Temperature']," + xx.mkString(",") + "]"
    //"[ [\"Date\", \"Min Temperature\", \"Max Temperature\"]," + xx.mkString(",") + "]"

  }
  
  
  def minMaxTemperature(stationId : String, month : String, year : String): String = {
    logger.info("started");
    val all: List[RealTimeData] = rtService.findByStationAndMonthYear(stationId, month.toInt, year.toInt)
    logger.info("retrieved " + all.size + " items");
    val allData = all.map { x => (x.date.split("-")(0), x.minTemperature, x.maxTemperature) }
    val maxMinPerDay =
      for (i <- (1 to 30)) yield {
        logger.info("working on day " + i);
        val mmax = allData.filter(t => t._1.toInt == i).map(t => toDouble(t._3)).max
        val mmin = allData.filter(t => t._1.toInt == i).map(t => toDouble(t._2)).min
        (i, mmax, mmin)
      }
    //val xx = all.map { x => s"[${x.date.split("-")(0)},${x.minTemperature},${x.maxTemperature}]" }
    val xx = maxMinPerDay.map { x => s"[${x._1},${x._3.getOrElse(0.0)},${x._2.getOrElse(0.0)}]" }
    logger.info(xx.mkString(","))
    "[ ['Date', 'Min Temperature', 'Max Temperature']," + xx.mkString(",") + "]"
    //"[ [\"Date\", \"Min Temperature\", \"Max Temperature\"]," + xx.mkString(",") + "]"

  }
  

  def toDouble(x : String) : Option[Double] = {
    try {
      Some(x.toDouble)
    }
    catch {
      case _ => None 
    }
  }

}