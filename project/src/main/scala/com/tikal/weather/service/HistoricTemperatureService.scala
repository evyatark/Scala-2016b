package com.tikal.weather.service

import org.springframework.stereotype.Service
import com.tikal.weather.dao.HistoricalDataDao
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions.asScalaBuffer
import com.tikal.weather.model.HistoricalData
import java.util.ArrayList

@Service
class HistoricTemperatureService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[HistoricTemperatureService])

  @Autowired
  val hisDao : HistoricalDataDao = null;
  
  def monthlyAverage(stationId : String,
      monthStr : String,
      yearFromStr : String,
      yearToStr : String) : String = {
    
		  val format = new java.text.SimpleDateFormat("MM-dd-yyyy")
		  val fromDate = format.parse("01-01-" + yearFromStr);
		  val toDate = format.parse("01-01-" + yearToStr);
    val fromLong = new java.util.Date(yearFromStr.toInt,1,1).getTime;
    val ttoLong = new java.util.Date(yearToStr.toInt,1,1).getTime
    logger.debug(s"Data Params : $yearFromStr To $yearToStr In Str : $fromLong,$ttoLong" )
    
    val allTest : java.util.ArrayList[HistoricalData] = hisDao.findByStationIdAndMonthAndYear(stationId,7,1930)
    logger.debug("Data allTest Length : " + allTest.size())
      val all: java.util.ArrayList[HistoricalData] = hisDao.findByStationIdAndDateTimeBetween(stationId,fromDate.getTime,toDate.getTime)
      logger.debug("Data Length : " + all.size())
    // return graph of the averages over the years
    val dataTuples = all.groupBy { singleDay => singleDay.year }
                        .map(averagePerYear).toList.sortWith((lt1,lt2) => lt1._1<lt2._1)
                        
                   
    logger.debug(dataTuples.map(historicalDataToString).mkString(","));
    "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature','Min']," +    
    dataTuples.map(historicalDataToString).mkString(",") +
    "]"
//        // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
//    "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature']," +    
//    "['1930', 31.0]," +
//    "['1931', 31.4]," +
//    "['1932', 32.0]," +
//    "['1933', 32.5]," +
//    "['1934', 33.0]," +
//    "['1935', 34.0]" +
//    // ...
//    "]"


  }
  def averagePerYear(data : (Int,Seq[HistoricalData])) : (Int,Double,Double) = {
    
    val temperturesPerYear = data._2.map { singleDay => (numToDouble(singleDay.maxTemperature),numToDouble(singleDay.minTemperature)) }
    val avgTem = (temperturesPerYear.flatMap(max => max._1).sum/temperturesPerYear.size,temperturesPerYear.flatMap(max => max._2).sum/temperturesPerYear.size)
    (data._1,avgTem._1,avgTem._2)
  }

  def numToDouble(num : String) : Option[Double] = {
    try
    {
      Some(num.toDouble)
    } catch {
      case _ => None
    }
  }

  
  def historicalDataToString(data : (Int,Double,Double)) : String = {
    "['"+data._1+"', "+data._2+", "+data._3+"]"
  }
  
    
  def monthByName(month : String) : String = {
    Map("07"->"July", "08"->"August", "09"->"September").getOrElse(month, month);
  }

  
  
  
  
  
  def tupleContains(station : String) : List[String] = {
    val alt = List(List("7150","7151"),List("4640","4642"),List("5360","5358"),List("1540","1545","1546"));
    var s : List[String] = List.empty[String] ;
    for (list <- alt) {
      if (list.contains(station)) {
        s = list ;
      }
    }
    if (s.isEmpty) List(station) else s
  }
  
  def findDataForStation(stationId : String, month : Int, year: Int) : List[HistoricalData] = {
    val stations : List[String] = tupleContains(stationId) ;
    stations.
      map{stationId => hisDao.findByStationIdAndMonthAndYear(stationId, month, year)}.
      flatMap{arrayList => asScalaBuffer(arrayList).toList}
  }

  
  
  def stationByName(stationId : String) : String = {
    Map("7150"->"בית ג'מל", "1540"->"עין החורש",
        "4640" -> "צפת",
        //"" -> "",
        "5360"->"תבור").getOrElse(stationId, stationId);
  }
}