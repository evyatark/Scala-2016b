package com.tikal.workshop

class Message(val name : String, val text : String) {

  def display() : String = {
    s"$text ${name * 3}"
  }
  
}

object Message {
  def apply(name : String) : Message = {
    new Message(name, "Goodbye");
  }
}