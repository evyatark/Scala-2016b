package com.tikal.weather.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation._
import org.springframework.beans.factory.annotation.Autowired
//import com.tikal.weather.dao.RealTimeDataDao
//import com.tikal.weather.model.RealTimeData
import javax.annotation.PostConstruct
import org.springframework.stereotype.Controller
import java.lang.Iterable
import scala.collection.JavaConversions.asScalaBuffer
import com.tikal.weather.service.RealTimeTemperature
import com.tikal.weather.service.RealTimeTemperature
import com.tikal.weather.service.DataDisplayService
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData

/**
  * Created by Evyatar on 1/7/2016.
  */
@RestController
class WeatherController {
  private val logger: Logger = LoggerFactory.getLogger(classOf[WeatherController])

  
  @Autowired
  val dao : RealTimeDataDao = null;

  @Autowired
  val rtTemperatureService : RealTimeTemperature = null ;
  
  
  @RequestMapping(value = Array("/dataMonth"), method = Array(RequestMethod.GET))
  def dataMonth(
      @RequestParam(value="month", defaultValue="06") month : String,
      @RequestParam(value="year", defaultValue="2016") year : String
      ):  String = {
      logger.warn(s"data, month=$month, year=$year")
      rtTemperatureService.minMaxTemperature("7151", month, year);
  }

  
  @PostConstruct
  def init() = {
//    logger.info("postConstruct");
//    val x : RealTimeData = new RealTimeData();
//    //x.id = 111 // no use to set id, it is a generated value!!!
//    x.stationId = "111" ;
//    x.date = "01-01-1930"
//    x.maxTemperature = "27.1" ;
//    x.minTemperature = "12.2";
//    dao.save(x)
  }
  
  @RequestMapping(value = Array("/test"), method = Array(RequestMethod.GET))
  def test():  String = {
      logger.warn(s"test")
      val y  = dao.findAll() ;
      val all : List[RealTimeData] = asScalaBuffer(y.asInstanceOf[java.util.ArrayList[RealTimeData]]).toList
      logger.info("size="+all.size);
      val x : RealTimeData = dao.findOne(all(0).id)
      s"${x.stationId} ${x.date} ${x.time} ${x.maxTemperature}"
  }
  

}
