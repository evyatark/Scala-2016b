package com.tikal.weather.service

import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import com.tikal.weather.Application
import org.springframework.beans.factory.annotation.Autowired
import org.junit.{Ignore, Test}
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.tikal.weather.reader.RealTimeReader

@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(Array(classOf[Application]))
class RealTimeReaderTest {
  private val logger: Logger = LoggerFactory.getLogger(classOf[RealTimeReaderTest])

  @Autowired
  val rtReader : RealTimeReader = null;
  
  @Test
  def readBeitJamalJuneTest(): Unit = {
    rtReader.readDataFromFile("data/jamal_june_2016_all.csv")
  }

}