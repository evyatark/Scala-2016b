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
import com.tikal.weather.dao.HistoricalDataDao
import com.tikal.weather.model.HistoricalData
import scala.collection.JavaConversions.asScalaBuffer


@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(Array(classOf[Application]))
class HistoricDaoTest {
  private val logger: Logger = LoggerFactory.getLogger(classOf[HistoricDaoTest])

  @Autowired
  val dao : HistoricalDataDao = null;
  
  @Test
  def countReadingsPerYearTest(): Unit = {
    for (year <- (1930 to 2016)) {
      val x = dao.findByDate("01-01-" + year.toString())
      val size = x.size()
      logger.info(s"$year - $size");
    }
  }

  @Test
  def displayReadingsPerYearTest(): Unit = {
    for (year <- (2015 to 2016)) {
      val x = dao.findByDate("01-01-" + year.toString())
      val allStations : List[HistoricalData] = asScalaBuffer(x).toList
      for (aa <- allStations) {
        logger.info (aa.toString());
      }
      val size = x.size()
      logger.info(s"$year - $size");
    }
  }
  
}