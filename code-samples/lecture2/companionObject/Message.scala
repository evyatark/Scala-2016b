package com.tikal.workshop

import java.util.Locale


class Message(_name : String, _text : String) {
  
  val name : String = _name ;
  val text : String = _text ;
  
  override def toString() : String = {
    s"$text $name!"
  }
}

object Message {
  def apply (_name : String) : Message = {
    new Message(_name, "Goodbye");
  }
}