package lectures.di.reader

import java.sql.DriverManager

import lectures.di.{ConfigurationImpl, UserServiceImpl}
import lectures.functions.User
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class ReaderDIUserServiceTest extends WordSpec with Matchers with BeforeAndAfterAll {


  "Reader program" should {
    "should not find any user" in {
      val configuration = ConfigurationImpl(Map("connectionUri" -> "jdbc:sqlite:memory"))
      val programConfig = UserServiceProgramDepsImpl(configuration)
      val r = UserServiceReaderDIProgramImpl()
      val res: Option[User] = r.run(programConfig)
      res shouldBe None
    }

    "find correct user" in {
      val configuration = ConfigurationImpl(Map("connectionUri" -> "jdbc:sqlite:memory"))
      configuration.setAttribute("user", "Frosya")
      configuration.setAttribute("password", "qwerty3")
      // Именно в этом месте происходит иньекция зависимостей в наше небольшое приложение
      val programConfig = UserServiceProgramDepsImpl(configuration)
      val r = UserServiceReaderDIProgramImpl()
      val Some(res) = r.run(programConfig)
      res.id shouldBe 22
    }
  }
}
