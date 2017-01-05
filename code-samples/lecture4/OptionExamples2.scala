package com.tikal.workshop

object OptionExamples2 {
  def main(args : Array[String]) : Unit = {
    //f1();
    //f2();
    //f3();
    f4();
  }
  
  def f1() : Unit = {
    def parseDouble(x : String) : Double = {
      x.toDouble
    }
    println(parseDouble("4"));
    println(parseDouble("4.0"));
    //println(parseDouble("-"));
  }
  
  
  
  
  
  
  
  
  def f2() : Unit = {
    
    def parseDouble(x : String) : Option[Double] = {
      if (canBeParsedToDouble(x)) {
        Some(x.toDouble)
      }
      else {
        None
      }
    }
    
    println(parseDouble("4"));
    println(parseDouble("4.0"));
    println(parseDouble("???"));
  }
  
  
  
  
  
  
  
  
  def f3() : Unit = {
    
    def parseDouble(x : String) : Option[Double] = {
      try {
        Some(x.toDouble)
      }
      catch {
        case _ => None
      }
    }
    
    println(parseDouble("4"));
    println(parseDouble("4.0"));
    println(parseDouble("???"));
    
    
    
    
    
    
    //val z : Double = parseDouble("4") // compilation error

    
    val x : Double = parseDouble("-").get;  // will compile, but considered bad habit
   
    val y : Double = parseDouble("4").getOrElse(0)   // supply a default value, to be used if None. (and note the lazy evaluation of the default)
    
    
    val a : Option[Double] = parseDouble("4");
    a match {
      case None => println("nothing");
      case Some(theValue) => println("the value inside is: " + theValue);
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  def f4() : Unit = {
    val listOfNumbers  = List("4", "0", "21", "13", "-", "23", "0", "18", "3", "5");
//    def parseDouble1(x : String) : Double = {
//      x.toDouble
//    }
//    val result : List[Double] = listOfNumbers.map((z : String) => parseDouble1(z)) ;  // will throw exception at runtime
//    println(result);
//    println("average is " + result.sum / result.size)
//
//  }
    def parseDouble(x : String) : Option[Double] = {
      try {
        Some(x.toDouble)
      }
      catch {
        case _ => None
      }
    }
    
//    val results : List[Option[Double]]  = listOfNumbers.map(y => parseDouble(y)) ;
//    println(results);
// 
//    
//    // I want the average of the numbers in the list:
//    //val averageImpossible = results.sum / results.size  // compilation error
//    
//    println(results.flatten);
//    
//  
//   
//    
//    val average = results.flatten.sum / results.flatten.size
//    println(average)
//    
//    
//    
    val results2 : List[Double] = listOfNumbers.flatMap ((y : String) => parseDouble(y)) ;
    println(results2);
    val averagePossible = results2.sum / results2.size
    println(averagePossible);
  }
  
  
        /*  

  */

  
  
  def canBeParsedToDouble(x : String) : Boolean = {
    try {
      x.toDouble
      true
    }
    catch {
      case _ => false
    }
  }
}