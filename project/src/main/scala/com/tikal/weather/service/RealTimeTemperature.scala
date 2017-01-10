package com.tikal.weather.service

import org.springframework.stereotype.Service
//import com.tikal.weather.model.RealTimeData
//import com.tikal.weather.dao.RealTimeDataDao
import org.springframework.beans.factory.annotation.Autowired
import scala.collection.JavaConversions.asScalaBuffer
import org.slf4j.{Logger, LoggerFactory}
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData
import java.util.Calendar
import com.tikal.weather.utils.DateUtils

@Service
class RealTimeTemperature {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RealTimeTemperature])

  @Autowired
  val dao : RealTimeDataDao = null;

  @Autowired
  val rtService : RTDataService = null ;

  
  val dateUtils = DateUtils
  /**
   * Implement this service correctly!
   */
  /**
 * @param stationId
 * @param day
 * @param month
 * @param year
 * @return
 */
  
 
  
def temperatureForOneDay(stationId : String, day : String, month : String, year : String): String = {
    // one option is to retrieve all data for that month:
    val all: List[RealTimeData] = rtService.findByStationAndMonthYear(stationId, month.toInt, year.toInt)
    // and work from there
 
    val returned =  all.filter(t => dateUtils.getDay(t.dateTime) == day.toInt && t.temperature !="-").map(data => s"['${data.time}', ${data.temperature}]").mkString("[ ['Time', 'Temperature'],","," ,"]")
    
    returned
    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
  /*  "[ ['Time', 'Temperature']," +
    "['00:10', 21.0,]," +
    "['00:20', 21.4, 23.0]," +
    "['00:30', 22.0, 24.0]," +
    "['00:40', 22.5, 25.0]," +
    "['00:50', 23.0, 26.0]," +
    "['01:00', 24.0, 27.0]" +
    // ...
    "]"
 */
  }
 


  
  def minMaxTemperature(stationId : String, month : String, year : String): String = {

    // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
val all: List[RealTimeData] = rtService.findByStationAndMonthYear(stationId, month.toInt, year.toInt)
    // and work from there
   val result = all.groupBy(data => data.date).map(e=>(e._1, e._2.asInstanceOf[List[RealTimeData]].minBy(data => data.minTemperature).minTemperature,e._2.asInstanceOf[List[RealTimeData]].maxBy(data => data.maxTemperature).maxTemperature ))   
    //val returned =  all.map(data => s"['${data.date}', ${data.minTemperature}, ${data.maxTemperature}]").mkString("[ ['Date', 'Min Temperature', 'Max Temperature'],","," ,"]")
    val returned =result.filter(data=> data._2 !="-" && data._3 !="-") .map(data => s"['${data._1}', ${data._2}, ${data._3}]")mkString("[ ['Date', 'Min Temperature', 'Max Temperature'],","," ,"]")
    returned
 
   /* val xx = List(
        "['01-06-2016',35.0,24.0]",
        "['02-06-2016',34.0,23.0]",
        "['03-06-2016',35.0,24.0]",
        "['04-06-2016',34.0,23.0]"
        )
    logger.info(xx.mkString(","))
   
     "[ ['Date', 'Min Temperature', 'Max Temperature']," + xx.mkString(",") + "]"
   */
    //"[ [\"Date\", \"Min Temperature\", \"Max Temperature\"]," + xx.mkString(",") + "]"

  }
  

  

}