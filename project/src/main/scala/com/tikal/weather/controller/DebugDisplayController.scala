package com.tikal.weather.controller

import com.tikal.weather.service.{ DataDisplayService }
import com.tikal.weather.reader.RealTimeReader
import org.slf4j.{ Logger, LoggerFactory }
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import org.springframework.web.multipart.MultipartFile
import scala.io.{ Codec, Source }
import com.tikal.weather.reader.HistoricReader

/**
 * Created by Evyatar on 1/7/2016.
 */
@RestController
class DebugDisplayController {
  private val logger: Logger = LoggerFactory.getLogger(classOf[DebugDisplayController])

  @Autowired
  val dataDisplayService: DataDisplayService = null;

  @Autowired
  val rtReader: RealTimeReader = null
  
  @Autowired
  val historicReader: HistoricReader = null

  @RequestMapping(value = Array("/dbDisplay"), method = Array(RequestMethod.GET))
  def dbDisplay(): String = {
    dataDisplayService.displayAllRtData()
  }

  @RequestMapping(value = Array("/dbByDate"), method = Array(RequestMethod.GET))
  def dbDisplayByDate(@RequestParam("date") date: String): String = {
    dataDisplayService.displayDayRtData(date)
  }

  @RequestMapping(value = Array("/dbByDate2/{date}"), method = Array(RequestMethod.GET))
  def dbDisplayByDate2(@PathVariable("date") date: String): String = {
    dataDisplayService.displayDayRtData(date)
  }

  @RequestMapping(value = Array("/historic/{date}"), method = Array(RequestMethod.GET))
  def dbDisplayHistoricDataByDate(@PathVariable("date") date: String): String = {
    dataDisplayService.displayHistoricDayData(date)
  }

  @RequestMapping(value = Array("/historic/{date}/{station}"), method = Array(RequestMethod.GET))
  def dbDisplayHistoricDataByDate(@PathVariable("date") date: String, @PathVariable("station") station: String): String = {
    dataDisplayService.displayHistoricDayData(station, date)
  }

  @RequestMapping(value = Array("/historic/{station}/{month}/{year}"), method = Array(RequestMethod.GET))
  def dbDisplayHistoricDataByDate(
      @PathVariable("month") month: String, 
      @PathVariable("year") year: String, 
      @PathVariable("station") station: String): String = {
    dataDisplayService.displayHistoricDayData(station, month, year)
  }
  
  @RequestMapping(value = Array("/dbUploadFile"), method = Array(RequestMethod.POST))
  def dbUploadFile(@RequestBody() file: MultipartFile): String = {
    if (!file.isEmpty) {
      val lines = Source.fromInputStream(file.getInputStream)(Codec.string2codec("Windows-1255"))
        .getLines().toList.drop(1)
      rtReader.loadLinesToDB(lines)
      return "Successfully loaded file to DB"
    }
    "File could not be read"
  }

  
  @RequestMapping(value = Array("/uploadHistoricTemperatureFile"), method = Array(RequestMethod.POST))
  def uploadHistoricTemperatureFile(@RequestBody() file: MultipartFile): String = {
    if (!file.isEmpty) {
      val lines = Source.fromInputStream(file.getInputStream)(Codec.string2codec("Windows-1255"))
        .getLines().toList.drop(1)
      historicReader.loadLinesToDB(lines)
      return "Successfully loaded file to DB"
    }
    "File could not be read"
  }

}
