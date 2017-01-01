package com.tikal.workshop

object Hello {
  def main(args : Array[String]) : Unit = {
    val names : List[String] = List("Evyatar", "Guy", "Lev", "Ilan") ;
    
    for (name <- names) {
      //val m1 : Message = new Message(name, "Hello") ;
      val m : Message = Message(name) ;
      println(m.display());
    }
  }
}