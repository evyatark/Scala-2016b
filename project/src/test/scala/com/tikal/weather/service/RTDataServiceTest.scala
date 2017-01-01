package com.tikal.weather.service


import java.io.File
import java.nio.file.{Files, StandardCopyOption}
import javax.annotation.PostConstruct

import org.junit.{Ignore, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.test.{IntegrationTest, SpringApplicationConfiguration, TestRestTemplate, WebIntegrationTest}
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.Assert
import com.tikal.weather.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.tikal.weather.dao.RealTimeDataDao
import com.tikal.weather.model.RealTimeData
import org.junit.Assert._


@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(Array(classOf[Application]))
@Ignore
class RTDataServiceTest {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RTDataServiceTest])

  @Autowired
  val rtDataService : RTDataService = null ;
  
  
  @Test
  def weatherTest(): Unit = {
    val x = rtDataService.findDataByMonth("7151", "06", "2016") ;
    logger.info(s"there are ${x.size} data items for 06-2016");
  }

  @Test
  def test1(): Unit = {
    
    val x = rtDataService.findAllStationIds() ;
    logger.info(s"there are ${x.size} station Ids:");
    logger.info(x.mkString);
  }

  @Test
  def test2(): Unit = {
    val x = rtDataService.findByDateRange("01-06-2016", "03-06-2016") ;
    logger.info(s"there are ${x.size} items for 01-06-2016 - 03-06-2016");
    x.sortWith(lt).foreach { item => logger.info(item.toString()) }
  }

  @Test
  def test3(): Unit = {
    val x = rtDataService.findByStationAndDateRange("7151", "01-06-2016", "03-06-2016") ;
    assertEquals(288, x.size)
    logger.info(s"there are ${x.size} items for 01-06-2016 - 03-06-2016");
    x.sortWith(lt).foreach { item => logger.info(item.toString()) }
    
    assertEquals(0, rtDataService.findByStationAndDateRange("7150", "01-06-2016", "03-06-2016").size);
  }

  def lt (x : RealTimeData, y : RealTimeData) : Boolean = {
    x.dateTime < y.dateTime
  }
}
  