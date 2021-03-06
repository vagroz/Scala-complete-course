package lectures.di
import java.sql.{Connection, DriverManager}

import scala.util.Try

/**
  * ConnectionManagerImpl - это реализация менеджера соединений, которая берет на себя
  * ответсвенность за создание и закрытие соединения с БД
  *
  * Метод connection - должен создавать новое соединение или брать его из пула соединений (в зависимости от реализации)
  * Метод close - должен закрывать соединение (или возвращать в ПУЛ)
  *
  * УРЛ БД должен храниться в `configuraiton` в параметре `connectionUri` ("jdbc:sqlite:memory")
  * Если, для хранения соединений будет использован какой-либо пул, размер пула должен находться в параметрее `connectionPoolSize`
  *
  */
class ConnectionManagerImpl(configuration: Configuration) extends ConnectionManager{
  override def connection: Option[Connection] = Try {
    val uri = configuration.attribute("connectionUri").getOrElse("defaultUri")
    Class.forName("org.sqlite.JDBC")
    val connection = DriverManager.getConnection(uri)
    connection.setAutoCommit(false)
    connection
  }.toOption

  override def close(connection: Connection): Unit = {
    connection.commit()
    connection.close()
  }
}
