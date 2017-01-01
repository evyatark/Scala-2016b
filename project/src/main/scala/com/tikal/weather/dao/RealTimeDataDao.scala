package com.tikal.weather.dao

import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import com.tikal.weather.model.RealTimeData
import org.springframework.data.repository.CrudRepository


trait RealTimeDataDao extends CrudRepository[RealTimeData, java.lang.String] {
  
  def findByStationId(stationId : String) : java.util.ArrayList[RealTimeData] ;

  def findByDate(date : String) : java.util.ArrayList[RealTimeData] ;

  def findByDateTimeBetween(from : Long, to : Long) : java.util.ArrayList[RealTimeData] ;
  def findByStationIdAndDateTimeBetween(stationId : String, from : Long, to : Long) : java.util.ArrayList[RealTimeData] ;
  
  def findByStationIdAndMonthAndYear(stationId : String, month : Int, year : Int) : java.util.ArrayList[RealTimeData] ;
  def findByStationIdAndDate(stationId : String, date : String) : java.util.ArrayList[RealTimeData] ;
}