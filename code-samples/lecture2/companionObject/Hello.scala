package com.tikal.workshop


object Hello {
  def main(args : Array[String]) : Unit = {
    var messages : List[Message] = List.empty ;
    for (arg <- args) {
      val m : Message = Message(arg);
      messages = m +: messages ;
    }
    
    println(messages.mkString(","))
  }
}