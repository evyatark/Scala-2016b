package com.tikal.weather.utils

import java.util.Calendar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
  def getDay(ms :Long) : Int ={
   val cal =  java.util.Calendar.getInstance()
   cal.setTimeInMillis(ms)
   val day = cal.get(Calendar.DAY_OF_MONTH)
   day
   
 }
  val DATE_FORMAT = "dd-MM-yyyy"
  
  def getLastDayOfYear(year:String) : Option[LocalDate] = {
    val strDate = s"31-12-$year"
    createLocalDate(strDate)
  } 
  
  def getFirstDayOfYear(year:String) : Option[LocalDate] = {
    val strDate = s"01-01-$year"
    createLocalDate(strDate)
  } 
  
  def createLocalDate(strDate :String) :Option[LocalDate] ={
     val date = try{
     Some (LocalDate.parse(strDate, DateTimeFormatter.ofPattern(DATE_FORMAT)))
    } catch {
      case _: Throwable => None
    }
    date
  }
  
}