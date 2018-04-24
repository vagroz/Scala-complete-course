package lectures.di.scaldi

import lectures.functions.{LPUser, User}
import org.scalatest.{FlatSpec, Matchers}
import scaldi.Module

class ScalDiProgramTest extends FlatSpec
  with Matchers{

  "ScalDi program" should
    "not find any user" in {

      implicit val inj = new DependenciesModule :: new Module {
        binding to Map.empty[String, String]
      }

      val r = new ScalDiProgram()

      val res: Option[User] = r.run
      res shouldBe None
    }

    it should "find correct user" in {

      implicit val inj = new DependenciesModule :: new BindMapconfigModule

      val r = new ScalDiProgram()
      val Some(user: LPUser) = r.run
      user.credentials.login shouldBe "Frosya"
      user.id shouldBe 22
    }
}
