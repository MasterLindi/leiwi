package dal.repository

import akka.actor.Scope
import org.specs2.execute.AsResult
import org.specs2.mutable.Around
import play.api.test.{FakeApplication, Helpers, Port, TestServer}

/**
 * Created by Christoph on 13.04.2015.
 */
class WithTestServer(val app: FakeApplication = FakeApplication(),
                     val port: Int = Helpers.testServerPort) extends Around with Scope {
  implicit def implicitApp = app

  implicit def implicitPort: Port = port

  synchronized {
    if (!WithTestServer.isRunning) {
      WithTestServer.start(app, port)
    }
  }

  // Implements around an example
  override def around[T: AsResult](t: => T): org.specs2.execute.Result = {
    println("Running test with test server===================")
    AsResult(t)
  }

  override def withFallback(other: Scope): Scope = ???
}

object WithTestServer {

  var singletonTestServer: TestServer = null

  var isRunning = false

  def start(app: FakeApplication = FakeApplication(), port: Int = Helpers.testServerPort) = {
    implicit def implicitApp = app
    implicit def implicitPort: Port = port
    singletonTestServer = TestServer(port, app)
    singletonTestServer.start()
    isRunning = true
  }
}
