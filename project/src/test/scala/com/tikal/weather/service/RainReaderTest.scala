package com.tikal.weather.service

import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import com.tikal.weather.Application
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.junit.Ignore
import com.tikal.weather.reader.RainReader

@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(Array(classOf[Application]))
class RainReaderTest {
  private val logger: Logger = LoggerFactory.getLogger(classOf[RainReaderTest])

  @Autowired
  val rainReader : RainReader = null;
  
  @Test
  def readBeitJamal1930Test(): Unit = {
    rainReader.readDataFromFile("data/jamal historic rain 1930 1960.csv")
  }

}