package com.tikal.workshop

import scala.io.Codec
import scala.io.Source

object aaa {
  val fileName = "C:\\work\\scala\\data\\weather\\BeitJamal\\minmaxtemperature\\ims_data_max_min_7150_1930_1960.csv";
  val codec: Codec = Codec.string2codec("Windows-1255"); // required because of Hebrew in the data file
  val allData: List[String] = Source.fromFile(fileName)(codec).getLines().toList // read lines from the file
}