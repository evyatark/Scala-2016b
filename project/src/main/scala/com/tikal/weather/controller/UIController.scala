package com.tikal.weather.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation._
import org.springframework.ui.Model
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import com.tikal.weather.service.RealTimeTemperature
import com.tikal.weather.model.Stations


@Controller
@RequestMapping(value = Array("/ui"))
class UIController {
  private val logger: Logger = LoggerFactory.getLogger(classOf[UIController])

  @Autowired
  val rtTemperatureService : RealTimeTemperature = null ;

  @RequestMapping(value = Array("fullMonth"), method = Array(RequestMethod.GET))
  def ui(model : Model,
         @RequestParam(value = "month", required=false, defaultValue="06") month: String,
         @RequestParam(value = "year", required=false, defaultValue="2016") year: String,
         @RequestParam(value = "station_id", required=false, defaultValue="7151") stationId: String
  ):  String = {
    model.addAttribute("month", month)
    model.addAttribute("year", year)
    model.addAttribute("data", rtTemperatureService.minMaxTemperature(stationId, month, year));
    "FullMonthTemperatureGraph"
  }


  @RequestMapping(value = Array("oneDay"), method = Array(RequestMethod.GET))
  def oneDay(model : Model,
            @RequestParam(value = "day", required=false, defaultValue="01") day: String,
            @RequestParam(value = "month", required=false, defaultValue="06") month: String,
            @RequestParam(value = "year", required=false, defaultValue="2016") year: String,
            @RequestParam(value = "station_id", required=false, defaultValue="7151") stationId: String
  ) :  String = {
    model.addAttribute("day", day)
    model.addAttribute("month", month)
    model.addAttribute("year", year)
    val stationName = Stations.idToStation(stationId)
    model.addAttribute("stationName", stationName)
    val data = rtTemperatureService.temperatureForOneDay(stationId, day, month, year) ;
    model.addAttribute("data", data);
    "OneDayTemperatureGraph"
  }

  
  
  @RequestMapping(value = Array("hello"), method = Array(RequestMethod.GET))
  def hello(model : Model):  String = {
    // add value of "name" to Model ("$name" is used in the template)
    model.addAttribute("name", "Evyatar")
    
    // return the name of the view:
    "Hello"
  }
}
