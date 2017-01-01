package com.tikal.workshop

object Upper {
  def main(args: Array[String]): Unit = {
    val list = List("Evyatar", "Yaniv", "Alex", "Tal", "Inbar", "Alon", "Arnon", "Daniel")
    //  list = List("EVYATAR", "YANIV", "ALEX", "TAL", "Inbar", "Alon", "Arnon", "Daniel")
    println(list) ;
    
    val up : Seq[String] = changeToUpperCase(list) ;
    println(up);
  }

  def changeToUpperCase(list : List[String]) : Seq[String] = {
    list.map(_.toUpperCase())
  }
  
  
  
  
  
  
  
  
  // ------------------------------------------------------
  
  
  
  
  //   def changeToUpperCase(strings: Seq[String]): Seq[String] = {

  // and use map() on "strings"
  
  
  
  def changeToUpperCase(strings: Seq[String]): Seq[String] = {
    strings.map(f) ;
  }
  
  def f (s : String) : String = {
    s.toUpperCase()
  }

  
//     strings.map((s: String) => s.toUpperCase())
//     strings.map(s => s.toUpperCase());
//     strings.map(_.toUpperCase())

}