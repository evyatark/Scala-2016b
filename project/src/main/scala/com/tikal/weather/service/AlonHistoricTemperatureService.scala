package com.tikal.weather.service

import org.springframework.stereotype.Service
import com.tikal.weather.dao.HistoricalDataDao
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions.asScalaBuffer
import com.tikal.weather.model.HistoricalData
import java.util.ArrayList
import scala.collection.mutable.ListBuffer

@Service
class AlonHistoricTemperatureService {
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
    val years = yearFrom to yearTo
    val list : ListBuffer[String] = new ListBuffer[String]
    for (year <- years) {
      val yearTemps = dao.findByStationIdAndMonthAndYear(stationId, month, year).toList.map { d => (parseDouble(d.getMaxTemperature()), parseDouble(d.getMinTemperature())) }
      val yearMaxTemps = yearTemps.map(_._1).flatten
      val yearMinTemps = yearTemps.map(_._2).flatten
      if (yearMaxTemps.size > 0)  list.append("['" + year + "', " + calcAvg(yearMaxTemps) + "," + calcAvg(yearMinTemps) + "]") 
    } 
    
    def parseDouble(x : String) : Option[Double] = {
    		try {
    			Some(x.toDouble)
    		}
    		catch {
    		case _ => None
    		}
    }
    
    def calcAvg(list: List[Double]) : Double = {
      if (list.size > 0) list.sum / list.size else 0
    }
    
        // THE RESULT OF THIS SERVICE IS A STRING THAT looks like this:
    "[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature', '" + monthByName(monthStr) + " Average Min Temperature']," +  list.mkString(",") + "]"    
    
    
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