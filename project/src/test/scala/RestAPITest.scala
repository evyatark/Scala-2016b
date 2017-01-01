import java.io.File
import java.nio.file.{StandardCopyOption, Files}
import javax.annotation.PostConstruct

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.test.{IntegrationTest, SpringApplicationConfiguration, TestRestTemplate, WebIntegrationTest}
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.Assert
import com.tikal.weather.Application
import org.junit.Ignore


@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(Array(classOf[Application]))
@WebIntegrationTest
@IntegrationTest
@Ignore
class RestAPITest {

  @Autowired
  var application: Application = _

  @Value("${server.context-path}")
  var serverContextPath: String = _


  @PostConstruct
  def init(): Unit = {
  }

  @Test
  @Ignore
  def weatherTest(): Unit = {
    val rest = new TestRestTemplate();
    val dataUrl = s"http://localhost:8092/$serverContextPath/dataMonth"
    val vars: Map[String, String] = Map()
    val response = rest.getForObject(dataUrl, classOf[String], vars)
    Assert.notNull(response)
    org.junit.Assert.assertTrue(response.trim().startsWith("[ ['Date',"))
  }



}
