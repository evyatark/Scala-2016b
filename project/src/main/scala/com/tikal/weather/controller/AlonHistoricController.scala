package com.tikal.weather.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation._
import org.springframework.ui.Model
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import com.tikal.weather.service.RealTimeTemperature
import com.tikal.weather.service.HistoricTemperatureService
import com.tikal.weather.service.AlonHistoricTemperatureService


@Controller
@RequestMapping(value = Array("/ui/alon/hist"))
class AlonHistoricController {
  private val logger: Logger = LoggerFactory.getLogger(classOf[AlonHistoricController])

  @Autowired
  val historicTemperatureService : AlonHistoricTemperatureService = null ;

  @RequestMapping(value = Array("monthlyAverage"), method = Array(RequestMethod.GET))
  def monthlyAverage(model : Model,
      @RequestParam(value="month", defaultValue="07") month : String,
      @RequestParam(value="stationId", defaultValue="7150") stationId : String,
      @RequestParam(value="yearFrom", defaultValue="1930") yearFrom : String,
      @RequestParam(value="yearTo", defaultValue="2016") yearTo : String
  ):  String = {
    model.addAttribute("month", month)
    model.addAttribute("yearFrom", yearFrom)
    model.addAttribute("yearTo", yearTo)
    model.addAttribute("data", historicTemperatureService.monthlyAverage(stationId, month, yearFrom, yearTo));
    model.addAttribute("monthName", historicTemperatureService.monthByName(month) );
    model.addAttribute("stationName", historicTemperatureService.stationByName(stationId) );
    "AlonMonthlyAverageTemperatureGraph"
  }



}
