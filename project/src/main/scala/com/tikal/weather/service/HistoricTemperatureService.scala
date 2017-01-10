package com.tikal.weather.service

import org.springframework.stereotype.Service
import com.tikal.weather.dao.HistoricalDataDao
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions.asScalaBuffer
import com.tikal.weather.model.HistoricalData
import java.util.ArrayList
import java.time.LocalDate
import com.tikal.weather.utils.DateUtils

@Service
class HistoricTemperatureService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[HistoricTemperatureService])

  @Autowired
  val dao : HistoricalDataDao = null;
  
  val dateUtils = DateUtils
  def monthlyAverage(stationId : String,
      monthStr : String,
      yearFromStr : String,
      yearToStr : String) : String = {
    

    // return graph of the averages over the years
    
    val from = dateUtils.getFirstDayOfYear(yearFromStr).getOrElse(LocalDate.of(1970, 1, 1))
    val to = dateUtils.getLastDayOfYear(yearToStr).getOrElse(LocalDate.now)
    
    val all =(for (year <- yearFromStr.toInt to yearToStr.toInt) yield( dao.findByStationIdAndMonthAndYear(stationId, monthStr.toInt, year))).flatten
      
    
    //val all = dao.findByStationIdAndDateTimeBetween(stationId, from.toEpochDay(), to.toEpochDay())//.filter(data => data.month == monthStr.toInt)
       
    val result = all.groupBy(data => data.year).map(e=>(e._1, e._2.asInstanceOf[Vector[HistoricalData]].map(x => (x.minTemperature.toDouble + x.maxTemperature.toDouble)/2).asInstanceOf[Vector[Double]])) 
    val formattedResult = result.map(e => (e._1,BigDecimal(e._2.sum / e._2.size).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)).toSeq.sortWith((l,r)=> l._1< r._1).map(e => s"['${e._1}', ${e._2}]")

    val prefix =  "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature'],"
    
   val returned = formattedResult.mkString(prefix, ",", "]")
   
   returned
   // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
  /*   "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature']," +    
    "['2010', 31.0]," +
   // "['1931', 31.4]," +
    "['2014', 32.0]" +
   // "['1933', 32.5]," +
 //   "['1934', 33.0]," +
  //  "['1935', 34.0]" +
    // ...
    "]"*/
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
      map{stationId => dao.findByStationIdAndMonthAndYear(stationId, month, year)}.
      flatMap{arrayList => asScalaBuffer(arrayList).toList}
  }

  
  
  def stationByName(stationId : String) : String = {
    Map("7150"->"בית ג'מל", "1540"->"עין החורש",
        "4640" -> "צפת",
        //"" -> "",
        "5360"->"תבור").getOrElse(stationId, stationId);
  }
}