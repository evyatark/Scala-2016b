package com.tikal.weather.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation._
import org.springframework.ui.Model
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import com.tikal.weather.service.RealTimeTemperature
import com.tikal.weather.service.HistoricTemperatureService


@Controller
@RequestMapping(value = Array("/ui/hist"))
class HistoricController {
  private val logger: Logger = LoggerFactory.getLogger(classOf[HistoricController])

  @Autowired
  val historicTemperatureService : HistoricTemperatureService = null ;

  @RequestMapping(value = Array("monthlyAverage"), method = Array(RequestMethod.GET))
  def monthlyAverage(model : Model,
      @RequestParam(value="month", defaultValue="07") month : String,
      @RequestParam(value="stationId", defaultValue="7151") stationId : String,
      @RequestParam(value="yearFrom", defaultValue="1930") yearFrom : String
  ):  String = {
    val yearTo = "2016" ;
    model.addAttribute("month", month)
    model.addAttribute("yearFrom", yearFrom)
    model.addAttribute("yearTo", yearTo)
    model.addAttribute("data", historicTemperatureService.monthlyAverage(stationId, month, yearFrom, yearTo));
    model.addAttribute("monthName", historicTemperatureService.monthByName(month) );
    model.addAttribute("stationName", historicTemperatureService.stationByName(stationId) );
    "MonthlyAverageTemperatureGraph"
  }



}
