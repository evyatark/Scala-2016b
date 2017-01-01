package com.tikal.workshop

import scala.collection.mutable.ListBuffer

object SortWith{
  
  def main(args : Array[String]) : Unit = {
    
    val listOfNumbers = List(10, 12, 4, 5, 8, -4);
    println("sorted: " + listOfNumbers.sorted);
    
    // now, sort this list in descending order (implement f ...)
    val sortedList = listOfNumbers.sortWith(f) ;
    println("sorted with descending order: " + sortedList) ;
    
    
    
    
    
    
    
    // -------------------------------------------
    
    //  def f ( x : Int, y : Int) : Boolean = {

    
    
    
    
    
    
    
    
    
    
    // same, using a function literal
    val sorted1 = listOfNumbers.sortWith( (x : Int, y : Int) => x > y )
    val sorted2 = listOfNumbers.sortWith( (x, y) => x > y )
    val sorted3 = listOfNumbers.sortWith( _ > _ )
    println("sorted with: " + sorted3) ;
  }
  
  def f ( x : Int, y : Int) : Boolean = {
    x > y  // reversed order!
  }
  
}