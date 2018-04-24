package lectures.di.scaldi
import lectures.di._
import _root_.scaldi.{Injectable, Injector, Module}
import lectures.functions.{AnonymousUser, User}

class DependenciesModule extends Module {
  binding to ConfigurationImpl(inject[Map[String,String]])
  bind[ConnectionManager] to new ConnectionManagerImpl(inject[Configuration])
  bind[UserService] to new UserServiceImpl(inject[ConnectionManager])
}

class BindMapconfigModule extends Module {
  binding to Map("connectionUri" -> "jdbc:sqlite:memory",
    "user" -> "Frosya", "password" -> "qwerty3")
}

class ScalDiProgram(implicit inj: Injector) extends Injectable {
  val userService = inject[UserService]
  val configuration = inject[Configuration]

  def run: Option[User] = {
    for {u <- configuration.attribute("user")
         pwd <- configuration.attribute("password")
         user <- userService.userByCredentials(u, pwd)
    } yield user
  }

  def runWithDefault: User = {
    run match {
      case Some(user) => user
      case None => AnonymousUser()
    }
  }
}
