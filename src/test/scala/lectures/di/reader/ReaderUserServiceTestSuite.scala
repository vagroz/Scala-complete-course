package lectures.di.reader

import java.sql.Connection

import lectures.di.{Configuration, ConnectionManager, ConnectionManagerImpl, UserServiceTestSuite}

class ReaderUserServiceTestSuite(thunk:  => Unit)(implicit configuration: Configuration) {

  import UserServiceTestSuite._

  private val connectionManager = new ConnectionManagerImpl(configuration)

  private val maybeConnection = connectionManager.connection

  if (maybeConnection.isDefined) {
    val connection = maybeConnection.get

    val dropTaleStmt = connection.prepareStatement(dropTale)
    dropTaleStmt.execute()

    val createStmt = connection.prepareStatement(createTable)
    createStmt.execute()

    val insertStmt = connection.prepareStatement(addUser)
    connection.commit()

    for ((id, k, v) <- users) {
      insertStmt.setInt(1, id)
      insertStmt.setString(2, k)
      insertStmt.setString(3, v)
      insertStmt.execute()
    }

    connectionManager.close(connection)

    thunk
  } else {
    //log.error
    println("Unable to get connection!")
  }
}