package com.tikal.workshop

import scala.collection.mutable.ListBuffer

object Qwerty {
  
  def main(args : Array[String]) : Unit = {
    
    val listOfNumbers = List(10, 12, 4, 5, 8, -4);
    
    var list2 : ListBuffer[Int] = new ListBuffer[Int]();
    
    
    val listOfEvenNumbers = theScalaWayToFilter1(listOfNumbers);
    
    println("filtering odd numbers: " + listOfEvenNumbers);
    
    println("Java: " + theJavaWayToFilter(listOfNumbers));
  }
  
  def theScalaWayToFilter1(list : List[Int]) : List[Int] = {
    //list.filter ( _%2==0  )
    //list.filter( (i : Int) => i%2==0 )
    //list.filter( (i ) => i%2==0 )
    //list.filter( j  => j%2==0 )
    
    
    list.filter(_ % 2 == 0)
  }
  
//  def isEven1(i : Int) : Boolean = {
//    (i % 2 == 0) ;
//  }
  
  
  
  
  
  // ----------------------------------------
  
  def isEven(x : Int) : Boolean = {
    x % 2 == 0
  }
  
  def theScalaWayToFilter(list : List[Int]) : Seq[Int] = {
    list.filter(isEven)
    
    // you can also sort and reverse
    //list.filter(isEven).sorted
    //list.filter(isEven).sorted.reverse
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  def theJavaWayToFilter(list : List[Int]) : Seq[Int] = {
    var outputList : ListBuffer[Int] = new ListBuffer();
    for (x <- list) {
      if (isEven(x)) {
        outputList.append(x);
      }
    }
    outputList
  }
}