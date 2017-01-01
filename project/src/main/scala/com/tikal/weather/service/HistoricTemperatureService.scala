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
    val yearFrom = yearFromStr.toInt
    val yearTo = yearToStr.toInt
    
    // calc average of month for each year
    val averagesList : Seq[(Int,Double)] =
    for (year <- (yearFrom to yearTo) ) yield {
//      val x = dao.findByStationIdAndMonthAndYear(stationId, month, year)
//      logger.info ( s"retrieved ${x.size()} items for $month $year");
      val allMonth : List[HistoricalData] = findDataForStation(stationId, month, year) ;
      val list : List[Double] = allMonth
              .filter(w => w.month == month)
              .filter(w => w.year == year)
          .flatMap(w => toDouble(w.maxTemperature))
      (year, list.sum / list.size)        
    }
    // return graph of the averages over the years
    
    
        // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
    "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature']," +
    averagesList.map ( x => s"['${x._1}', ${x._2}]" ).mkString(",") +
//    "['1930', 31.0]," +
//    "['1931', 31.4]," +
//    "['1932', 32.0]," +
//    "['1933', 32.5]," +
//    "['1934', 33.0]," +
//    "['1935', 34.0]" +
    // ...
    "]"


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

  
  def toDouble(x : String) : Option[Double] = {
    try {
      Some(x.toDouble)
    }
    catch {
      case _ => None 
    }
  }

  
  def monthByName(month : String) : String = {
    Map("07"->"July", "08"->"August", "09"->"September").getOrElse(month, month);
  }
  
  def stationByName(stationId : String) : String = {
    Map("7150"->"בית ג'מל", "1540"->"עין החורש",
        "4640" -> "צפת",
        //"" -> "",
        "5360"->"תבור").getOrElse(stationId, stationId);
  }
}