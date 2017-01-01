package com.tikal.weather.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData
import scala.collection.JavaConversions.asScalaBuffer
import org.springframework.stereotype.Service

@Service
class RTDataService {
  
  private val logger: Logger = LoggerFactory.getLogger(classOf[RTDataService])

  @Autowired
  val dao : RealTimeDataDao = null;
  

  def findDataByMonth(stationId : String, month : String, year : String): List[RealTimeData] = {
    val all : List[RealTimeData] = asScalaBuffer(dao.findByStationId(stationId)).toList ;
    val ofThisMonth = all.filter { x : RealTimeData => x.date.endsWith(s"$month-$year") } ;
    ofThisMonth
  }
  
  def findAllStationIds(): List[String] = {
    List("7150");
//    templateDao.findDistinctStationIds();
  }
  
  def findByDateRange(from : String, to : String) : List[RealTimeData] = {
    val fromLong = dateAsLong(from)
    val toLong = dateAsLong(to)
    val x = dao.findByDateTimeBetween(fromLong-1, toLong)
    asScalaBuffer(x).toList
  }
  
  def findByStationAndMonthYear(stationId : String, month : Int, year : Int) : List[RealTimeData] = {
    val x = dao.findByStationIdAndMonthAndYear(stationId, month, year)
    asScalaBuffer(x).toList
  }
  
  def findByStationAndDateRange(stationId : String, from : String, to : String) : List[RealTimeData] = {
    val fromLong = dateAsLong(from)
    val toLong = dateAsLong(to)
    val x = dao.findByStationIdAndDateTimeBetween(stationId, fromLong-1, toLong)
    asScalaBuffer(x).toList
  }
  
  def dateAsLong(date: String): Long = {
    // date is 01-06-2016

    val format = new java.text.SimpleDateFormat("MM-dd-yyyy")
    format.parse(date).getTime
  }
}