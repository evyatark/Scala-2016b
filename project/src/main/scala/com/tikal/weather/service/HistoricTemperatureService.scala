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
  val dao : HistoricalDataDao = null;
  
  def monthlyAverage(stationId : String,
      monthStr : String,
      yearFromStr : String,
      yearToStr : String) : String = {
    
    val month = monthStr.toInt
    // return graph of the averages over the years
    val pairs = (yearFromStr.toInt to yearToStr.toInt).map({y=>Tuple2(y, average(y, month, stationId))}).filter(!_._2.isEmpty).map(t=>{"['"+t._1+"',"+t._2.get+"],"})
    println(pairs)
        // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
//    "['1930', 31.0]," +
//    "['1931', 31.4]," +
//    "['1932', 32.0]," +
//    "['1933', 32.5]," +
//    "['1934', 33.0]," +
//    "['1935', 34.0]" +
    // ...

    "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature']," +    
    pairs.reduce(_+_) +
    "]"
  }
  
  val MONTHS = List("January", "February", "March", "April", "May", "June", "July", "August", "September", "November", "December")  
  def monthByName(month : String) : String = {
    try {
      MONTHS(month.toInt - 1)
    } catch {
       case e: Exception => month
    }
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

  def average(year: Int, month: Int, stationId: String): Option[Double] = {
    val values = findDataForStation(stationId, month, year).filter(x=>x.maxTemperature!="-")
    if (values.length > 0) {
      Some(values.map({ x => x.maxTemperature.toDouble}).reduceLeft(_+_) / values.length)
    } else {
      None
    }
  }
}