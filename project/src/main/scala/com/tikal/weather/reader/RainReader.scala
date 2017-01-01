package com.tikal.weather.reader

import org.springframework.stereotype.Component
import com.tikal.weather.dao.HistoricalDataDao
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.tikal.weather.model.HistoricalData
import scala.io.Codec
import scala.io.Source
import com.tikal.weather.model.Stations._
import javax.persistence.Entity

@Component
class RainReader {
   private val logger: Logger = LoggerFactory.getLogger(classOf[RainReader])

  @Autowired
  val dao : HistoricalDataDao = null;
 
   
  def readDataFromFile(fileName: String) = {
    val x = getClass().getClassLoader().getResource(fileName)
    val codec: Codec = Codec.string2codec("Windows-1255");
    // required because of Hebrew in the data file
    val linesAllFiles = Source.fromURL(x)(codec).getLines().toList.drop(1)
    loadLinesToDB(linesAllFiles)
  }
   
  
  
  
case class WeatherData(
                          val stationName: String,
                          val date: String,
                          val time: String,
                          val temp: String,
                          val maxTemp: String,
                          val minTemp: String,
                          val nearGroundTemp: String,
                          val humidity: String,
                          val rain: String
                        );
  
  def toTime(date: String, time: String): Long = {
    // date is 01-06-2016
    // time is 00:20

    val format = new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm")
    format.parse(date + " " + time).getTime
  }
  
  def loadLinesToDB(linesToLoad: List[String]): Unit = {
    val lines: List[WeatherData] = linesToLoad.map(line => {
      //logger.info(line);
      val p = line.split(",");
      //logger.info("" + p(1) + ", " + p(2) + ", " + p(3) + ", " + p(4));
      val data = new WeatherData(p(0), p(2), "00:00", "-", "-", "-", "-", "-", p(3));
      data
    })
    
    val rtAllData: List[HistoricalData] = for (line <- lines) yield {
      val rtData: HistoricalData = new HistoricalData();
      //logger.info(line.stationName);
      rtData.stationId = stationNameToId(line.stationName)
      rtData.date = line.date;
      rtData.id = rtData.stationId + "-" + rtData.date;
      rtData.rain = line.rain;
      rtData.dateTime = toTime(rtData.date, "00:00");
      
      val dayMonthYear = rtData.date.split("-") ;
      rtData.month = dayMonthYear(1).toInt;
      rtData.year = dayMonthYear(2).toInt;
      logger.info(rtData.toString());
      rtData

    }
    logger.info(s"saving all data");
    val dataByDay = rtAllData.groupBy { item => item.year } .toSeq.sortBy(_._1) ;
    for ((k,v) <- dataByDay) {
      logger.info(s"saving all ${v.size} data items for year $k");
      //dao.save(asJavaIterable(v));
    }
  }  
  
  def stationNameToId(name : String) : String = {
    val ret = mapHistoricStationNames.getOrElse(name, "0")
    logger.info("name=" + name + ", id=" + ret);
    ret
  }
}