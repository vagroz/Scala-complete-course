package lectures.functions

import org.scalacheck.Gen
import org.scalacheck.Prop.BooleanOperators
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import AuthenticationData._


/**
  * Авторизация - это очень важно, поэтому нам необходимо покрыть тестами ответсвенный за нее код
  * (lectures.functions.Authentication)
  *
  * Для этого
  * * * * уберите extends App у Authentication
  * * * * замените AuthenticationData.testUsers соответствующими генераторами
  * * * * напишите
  * * * * * 2 теста на authByCard
  * * * * * 2 теста на authByLP
  * * * * * 1 тест на их композицию
  *
  */
class AuthenticationTest extends FlatSpec
  with Matchers with PropertyChecks {

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSize = 10, minSuccessful = 20)

  val testCardUserGenerator: Gen[CardUser] = Gen.frequency(
    (5, Gen.oneOf(registeredCards.toSeq)),
    (5, Gen.choose(1, 1000).map(CardCredentials(_)))
  ).flatMap { cred =>
    Gen.posNum[Int].map(CardUser(_, cred))
  }

  val LPGen = Gen.alphaStr.map(_.take(10))

  val testLPUserGenerator = Gen.frequency(
    (5, Gen.oneOf(registeredLoginAndPassword.toSeq)),
    (5, for (login <- LPGen; password <- LPGen) yield LPCredentials(login, password))
  ).flatMap { cred =>
    Gen.posNum[Int].map(LPUser(_, cred))
  }


  "Authentication by card" should "authorize successfully" in {
    forAll(testCardUserGenerator){ user =>
      registeredCards.contains(user.credentials) ==>
        (Authentication.authByCard(user) == user)
    }
  }

  it should "reject bad users" in {
    val badUserGenerator: Gen[User] = Gen.frequency(
      (6, testCardUserGenerator.suchThat(user => !registeredCards.contains(user.credentials))),
      (2, Gen.const(AnonymousUser())),
      (2, testLPUserGenerator)
    )
    forAll(badUserGenerator){ badUser =>
      Authentication.authByCard.lift(badUser) shouldBe 'empty
    }
  }

  "Authentication by login" should "authorize successfully" in {
    forAll(testLPUserGenerator.suchThat(user => registeredLoginAndPassword.contains(user.credentials))) { user =>
      Authentication.authByLP(user) shouldBe user
    }
  }

  it should "reject bad users" in {
    val badUserGenerator: Gen[User] = Gen.frequency(
      (8, testLPUserGenerator.suchThat(user => !registeredLoginAndPassword.contains(user.credentials))),
      (2, Gen.const(AnonymousUser())),
      (2, testCardUserGenerator)
    )
    forAll(badUserGenerator){ badUser =>
      Authentication.authByLP.lift(badUser) shouldBe 'empty
    }
  }

  "Authentication" should "authorize correctly" in {
    val currentGen = Gen.frequency(
      (4, testCardUserGenerator.map(user => (user, registeredCards.contains(user.credentials)))),
      (4, testLPUserGenerator.map(user => (user, registeredLoginAndPassword.contains(user.credentials)))),
      (2, Gen.const((AnonymousUser(), false)))
    )

    forAll(currentGen){
      case (user, isRegistered) =>
        val res = Authentication.auth(user)
        if (isRegistered) res should contain(user)
        else res shouldBe 'empty
    }
  }

}
