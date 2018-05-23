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

  def genWithAnonymous(gen: Gen[User]): Gen[(User, Boolean)] = {
    Gen.frequency(
      (8, gen),
      (2, Gen.const(AnonymousUser()))
    ).map {
      case user: CardUser =>
        (user, registeredCards.contains(user.credentials))
      case user: LPUser =>
        (user, registeredLoginAndPassword.contains(user.credentials))
      case user =>
        (user, false)
    }
  }


  "Authentication by card" should "authorize successfully registered users and reject bad users" in {
    forAll(genWithAnonymous(testCardUserGenerator)) {
      case (user, isRegistered) =>
        val result = Authentication.authByCard.lift(user)
        result shouldBe {
          if (isRegistered) Some(user)
          else None
        }
    }
  }

  "Authentication by login" should "authorize successfully registered users and reject bad users" in {
    forAll(genWithAnonymous(testLPUserGenerator)) {
      case (user, isRegistered) =>
        val result = Authentication.authByLP.lift(user)
        if (isRegistered) result should contain(user)
        else result shouldBe 'empty
    }
  }

  "Authentication" should "authorize and reject correctly" in {

    forAll(
      Gen.frequency(
        (1, genWithAnonymous(testLPUserGenerator)),
        (1, genWithAnonymous(testCardUserGenerator))
      )
    ){
      case (user, isRegistered) =>
        val res = Authentication.auth(user)
        if (isRegistered) res should contain(user)
        else res shouldBe 'empty
    }
  }

}
