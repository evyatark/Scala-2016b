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
    

    // return graph of the averages over the years
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
 
        val yearFrom = format.parse(yearFromStr+ "-01-01");
        val yearTo = format.parse(yearToStr+"2013-01-01");


   var allBetweenYears = dao.findByStationIdAndDateTimeBetween(stationId, yearFrom.getTime, yearTo.getTime);
   var filteredData =  allBetweenYears.filter {hd=>filterDataForMonth(hd,monthStr)}
   var groupedData = filteredData.groupBy { x => x.getMonth()+"-"+x.getYear() };
   var groupedMappedData = groupedData.map(x=>(x._1,x._2.map { y => y.getMaxTemperature().toFloat }))
   var finalData = groupedMappedData.map(x=>(x._1.split("-")(1),x._2.sum/x._2.length))
    println("stop");
   
    val builder = StringBuilder.newBuilder
    builder.append("[ ['Year', '" + monthByName(monthStr) + " Average Max Temperature'],");
    var finalDataSorted = finalData.toSeq.sortBy(_._1)
    for(x<-finalDataSorted){
      builder.append("['").append(x._1).append("', ").append(x._2).append("],");
    }
    builder.append("]");
    
        var result = builder.toString();
    result = result.patch(result.lastIndexOf(','),"", 1)

    println(result);
     result;
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
  
    def filterDataForMonth(hd : HistoricalData ,  month : String):Boolean ={
    if(hd.getDate().split("-")(1).equals(month) && !hd.maxTemperature.equals("-"))
      true
    else
      false
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