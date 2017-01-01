package com.tikal.workshop

class HelloMessage(_name : String) extends Message {
  
  
  var name : String = _name ;
  
  def display() : Unit = {
    println(s"Hello $name");
  }
}