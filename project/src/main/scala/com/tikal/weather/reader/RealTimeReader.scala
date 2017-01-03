package com.tikal.weather.reader

import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scala.io.{Codec, Source}
import scala.collection.immutable.HashMap
import javax.annotation.PostConstruct
import javax.persistence.Entity
import scala.collection.JavaConversions
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure

@Component
class RealTimeReader {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RealTimeReader])

  @Autowired
  val d: RealTimeDataDao = null;

  @PostConstruct
    def init() = {
      logger.info("init - read file and save to DB")
      //readDataFromFile("data/jamal_june_2016_all.csv")
      val f = Future {
        readDataFromFile("data/realTime/Jamal_2014.csv")
        readDataFromFile("data/realTime/Jamal_2015.csv")
        readDataFromFile("data/realTime/Jamal_2016.csv")
        readDataFromFile("data/realTime/Avne_2016.csv")
        readDataFromFile("data/realTime/Dagan_2016.csv")
        readDataFromFile("data/realTime/Elat_2016.csv")
        true }
      f.onComplete { 
        case Success(value) => logger.warn(s"finished saving rt to DB (status=$value)")
        case Failure(e) => e.printStackTrace
      }      
    }

  def readDataFromFile(fileName: String) = {
    var data: RealTimeData = new RealTimeData();
    data.dateTime = System.currentTimeMillis();

    val x = getClass().getClassLoader().getResource(fileName)
    val codec: Codec = Codec.string2codec("Windows-1255");
    // required because of Hebrew in the data file
    val linesAllFiles = Source.fromURL(x)(codec).getLines().toList.drop(1)
    loadLinesToDB(linesAllFiles)
  }

  def loadLinesToDB(linesToLoad: List[String]): Unit = {
    val lines: List[WeatherData] = linesToLoad.map(line => {
      //logger.info(line);
      val p = line.split(",");
      logger.info("" + p(1) + ", " + p(2) + ", " + p(3) + ", " + p(4) + ", " + p(5) + ", " + p(6) + ", " + p(7) + ", " + p(8));
      val data = new WeatherData(p(0), p(1), p(2), p(3), p(4), p(5), p(6), p(7), p(8));
      data
    })
    def f(arg : (String, Option[String])) : Boolean = {
      arg match {
      case (_, None) => true ;
      case _ => false ;
      }
    }
    val unknownStations = lines.map ( _.stationName.trim() ).distinct.map { x => (x, mapStationNames.get(x)) }.filter(f).map((arg : (String, Option[String])) => arg._1)
    if (!unknownStations.isEmpty) logger.warn("unidentified station names: " + unknownStations.mkString(","))
    
    val rtAllData: List[RealTimeData] = for (line <- lines) yield {
      val rtData: RealTimeData = new RealTimeData();
      //logger.info(line.stationName);
      rtData.id = rtData.stationId + "-" + rtData.date + "-" + rtData.time
      rtData.stationId = mapStationNames.getOrElse(line.stationName.trim(), "unknown");
      //logger.info("station=" + rtData.stationId);
      rtData.date = line.date;
      rtData.time = line.time;
      rtData.maxTemperature = line.maxTemp;
      rtData.minTemperature = line.minTemp;
      rtData.humidity = line.humidity;
      rtData.rain = line.rain;
      rtData.nearGroundTemperature = line.nearGroundTemp;
      rtData.temperature = line.temp;
      rtData.dateTime = toTime(rtData.date, rtData.time);
      rtData.id = rtData.stationId + "-" + rtData.date + "-" + rtData.time
      
      val dayMonthYear = rtData.date.split("-") ;
      rtData.month = dayMonthYear(1).toInt;
      rtData.year = dayMonthYear(2).toInt;
      rtData

    }
    logger.info(s"saving all data");
    val dataByDay = rtAllData.groupBy { item => item.date } ;
    for ((k,v) <- dataByDay) {
      logger.info(s"saving all data for day $k");
      d.save(JavaConversions.asJavaIterable(v));
    }
    //d.save(asJavaIterable(rtAllData));
  }

  def toTime(date: String, time: String): Long = {
    // date is 01-06-2016
    // time is 00:20

    val format = new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm")
    format.parse(date + " " + time).getTime
  }

  
  val mapStationNames = HashMap (
"בית ג'מל" -> "7151" ,
"אילת" -> "16" ,      
  "אפק" -> "01" ,
"אריאל מכללה" -> "02" ,
"אשדוד נמל" -> "03" ,
"אשקלון נמל" -> "04" ,
"אבני איתן" -> "05" ,
"איילת השחר" -> "06" ,
"באר שבע" -> "07" ,
"בשור חווה" -> "08" ,
"בית דגן" -> "09" ,
"בית הערבה" -> "10" ,
"בית צידה" -> "11" ,
"דפנה" -> "12" ,
"דייר חנא" -> "13" ,
"דורות" -> "14" ,
"חוות עדן" -> "15" ,
"אילון" -> "17" ,
"אשחר" -> "18" ,
"גלעד" -> "19" ,
"גמלא" -> "20" ,
"גת" -> "21" ,
"גלגל" -> "22" ,
"חדרה נמל" -> "23" ,
"חפץ חיים" -> "24" ,
"חיפה בתי זיקוק" -> "25" ,
"חיפה טכניון" -> "26" ,
"חיפה אוניברסיטה" -> "27" ,
"הכפר הירוק" -> "28" ,
"הר חרשה" -> "29" ,
"חרשים" -> "30" ,
"חצבה" -> "31" ,
"איתמר" -> "32" ,
"ירושלים מרכז" -> "33" ,
"ירושלים גבעת רם" -> "34" ,
"כפר בלום" -> "35" ,
"כפר גלעדי" -> "36" ,
"כפר נחום" -> "37" ,
"להב" -> "38" ,
"לב כנרת" -> "39" ,
"יבנאל" -> "40" ,
"יטבתה" -> "41" ,
"זכרון יעקב" -> "42",     

"עפולה ניר העמק" -> "43",
"עמיעד" -> "44",
"ערד" -> "45",
"עבדת" -> "46",
"עין גדי מרחצאות" -> "47",
"עין החורש" -> "48",
"עין השופט" -> "49",
"עין כרמל" -> "50",
"עזוז" -> "51",
"מעלה אדומים" -> "52",
"מעלה גלבוע" -> "53",
"מסדה" -> "54",
"מרחביה" -> "55",
"מרום גולן פיכמן" -> "56",
"מצוקי דרגות" -> "57",
"נחשון" -> "58",
"נגבה" -> "59",
"נאות סמדר" -> "60",
"נתיב הל\"ה" -> "61",
"נבטים" -> "62",
"נווה יער" -> "63",
"ניצן" -> "64",
"פארן" -> "65",
"קרני שומרון" -> "66",
"קבוצת יבנה" -> "67",
"ראש הנקרה" -> "68",
"ראש צורים" -> "69",
"שדה בוקר" -> "70",
"שדה אליהו" -> "71",
"סדום" -> "72",
"שערי תקוה" -> "73",
"שני" -> "74",
"שבי ציון" -> "75",
"תבור כדורי" -> "76",
"תל-אביב חוף" -> "77",
"צפת הר כנען" -> "78",
"צמח" -> "79",
"צומת הנגב" -> "80",
"צובה" -> "81"
  )
  

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
}