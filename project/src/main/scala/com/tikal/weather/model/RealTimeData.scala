package com.tikal.weather.model


import scala.beans.BeanProperty
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class RealTimeData {
  @Id
  @BeanProperty var id : java.lang.String = _ ;
  
  @BeanProperty var stationId : String = _
  @BeanProperty  var date : String = _
  @BeanProperty  var time : String = _
  @BeanProperty  var dateTime : java.lang.Long = _
  @BeanProperty  var temperature : String = _
  @BeanProperty  var maxTemperature : String = _
  @BeanProperty  var minTemperature : String = _

  @BeanProperty  var nearGroundTemperature : String = _
  @BeanProperty  var humidity : String = _
  @BeanProperty  var rain : String = _

  @BeanProperty  var month : Int = _
  @BeanProperty  var year : Int = _
  
  override def toString() = {
    s"$stationId $date $time $temperature $maxTemperature $minTemperature $nearGroundTemperature $humidity $rain"
  }
}