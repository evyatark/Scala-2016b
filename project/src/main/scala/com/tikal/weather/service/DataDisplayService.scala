package com.tikal.weather.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.tikal.weather.dao.RealTimeDataDao
import scala.collection.JavaConversions.asScalaBuffer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.tikal.weather.dao.HistoricalDataDao

@Service
class DataDisplayService {
  
  private val logger: Logger = LoggerFactory.getLogger(classOf[DataDisplayService])

    
  @Autowired
  val realTimeDao : RealTimeDataDao = null ;
  
  @Autowired
  val historicDao : HistoricalDataDao = null ;
  
  def displayAllRtData() : String = {
    logger.info("displayAllRtData")
    val s = asScalaBuffer(realTimeDao.findByStationId("7151")).toList.mkString("\n")
    logger.info(s)
    s
  }
  
  def displayDayRtData(date : String) : String = {
    logger.info(s"displayRtData for ${date}")
    val s = time { asScalaBuffer(realTimeDao.findByDate(date)).toList.mkString("\n") }
    //logger.info(s)
    s
  }

  def displayHistoricDayData(date : String) : String = {
    val s = time { asScalaBuffer(historicDao.findByDate(date)).toList.mkString("\n") }
    s
  }

  def displayHistoricDayData(station : String, date : String) : String = {
    val s = time { asScalaBuffer(historicDao.findByDateAndStationId(date, station)).toList.mkString("\n") }
    logger.info(s"station $station, date $date: $s")
    s
  }
  def displayHistoricDayData(stationId : String, month : String, year : String) : String = {
    val s = time { asScalaBuffer(historicDao.findByStationIdAndMonthAndYear(stationId, month.toInt, year.toInt)).toList.mkString("\n") }
    //logger.info(s"station $station, date $date: $s")
    s
  }
  
  def time[R](block: => R): R = {  
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    logger.info("Elapsed time: " + (t1 - t0)/1000000 + "ms")
    result
  }
}