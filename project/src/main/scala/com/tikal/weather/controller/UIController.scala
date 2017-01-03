package com.tikal.weather.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation._
import org.springframework.ui.Model
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import com.tikal.weather.service.RealTimeTemperature


@Controller
@RequestMapping(value = Array("/ui"))
class UIController {
  private val logger: Logger = LoggerFactory.getLogger(classOf[UIController])

  @Autowired
  val rtTemperatureService : RealTimeTemperature = null ;

  @RequestMapping(value = Array("fullMonth"), method = Array(RequestMethod.GET))
  def ui(model : Model):  String = {
    model.addAttribute("month", "June")
    model.addAttribute("year", "2016")
    model.addAttribute("data", rtTemperatureService.minMaxTemperature("7151", "06", "2016"));
    "FullMonthTemperatureGraph"
  }


  @RequestMapping(value = Array("oneDay"), method = Array(RequestMethod.GET))
  def oneDay(model : Model):  String = {
    model.addAttribute("day", "01")
    model.addAttribute("month", "06")
    model.addAttribute("year", "2016")
    val data = rtTemperatureService.temperatureForOneDay("7151", "01", "06", "2016") ;
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
