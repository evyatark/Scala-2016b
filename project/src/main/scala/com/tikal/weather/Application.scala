package com.tikal.weather

import javax.annotation.{PostConstruct, PreDestroy}
import org.slf4j.LoggerFactory
import org.springframework.boot._
import org.springframework.boot.autoconfigure._
import org.springframework.context.annotation._


@Configuration
@ComponentScan
@SpringBootApplication
class Application {

  val log = LoggerFactory.getLogger(this.getClass.getSimpleName)

  @PostConstruct
  def init(): Unit = {
    log.info("server started")
  }

  @PreDestroy
  def shutdown(): Unit = {
    log.info("server shutdown")
  }

}

object Application {
  def main(args: Array[String]) {
//    val configuration: Array[Object] = Array(classOf[Application])
//    val springApplication = new SpringApplication(configuration:_*)
//    springApplication.addListeners(new ApplicationPidFileWriter())
//    springApplication.run(args:_*)
    SpringApplication.run(classOf[Application])
  }
}

