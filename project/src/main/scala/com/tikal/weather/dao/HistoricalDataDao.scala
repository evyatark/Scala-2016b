package com.tikal.weather.dao

import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import com.tikal.weather.model.RealTimeData
import com.tikal.weather.model.HistoricalData
import org.springframework.data.repository.CrudRepository


trait HistoricalDataDao extends CrudRepository[HistoricalData, java.lang.String] {
  
  def findByStationId(stationId : String) : java.util.ArrayList[HistoricalData] ;

  def findByDate(date : String) : java.util.ArrayList[HistoricalData] ;
  def findByDateAndStationId(date : String, stationId : String) : java.util.ArrayList[HistoricalData] ;

  def findByDateTimeBetween(from : Long, to : Long) : java.util.ArrayList[HistoricalData] ;
  def findByStationIdAndDateTimeBetween(stationId : String, from : Long, to : Long) : java.util.ArrayList[HistoricalData] ;
  
  def findByStationIdAndMonthAndYear(stationId : String, month : Int, year : Int) : java.util.ArrayList[HistoricalData] ;
}