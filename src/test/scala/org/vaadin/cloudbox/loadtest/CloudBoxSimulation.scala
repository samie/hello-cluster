package org.vaadin.cloudbox.loadtest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CloudBoxSimulation extends Simulation {

  val httpProtocol = http
  .baseURL("http://192.168.0.254")
  .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""))
  .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")
  .acceptEncodingHeader("""gzip, deflate""")
  .acceptLanguageHeader("""en-US,en;q=0.5""")
  .connection("""keep-alive""")
  .contentTypeHeader("""application/json; charset=UTF-8""")
  .userAgentHeader("""Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:32.0) Gecko/20100101 Firefox/32.0""")

  val headers_0 = Map(
    """Accept""" -> """text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8""",
    """Cache-Control""" -> """max-age=0""")
  
  val headers_6 = Map(
    """Cache-Control""" -> """no-cache""",
    """Content-Type""" -> """application/x-www-form-urlencoded; charset=UTF-8""",
    """Pragma""" -> """no-cache""")

  val headers_8 = Map(
    """Cache-Control""" -> """no-cache""",
    """Pragma""" -> """no-cache""")

                
  val uri1 = """http://192.168.0.254/hellocluster"""

  // static resoures not fetched, can/should be moved to CDN
  val scn = scenario("CloudBox Cluster Simulation")
  .group("Init session") {
    exec(http("request_0")
         .get("""/hellocluster""")
         .headers(headers_0))
  }
  .group("Initial UI request") {
    exec(http("request_6")
         .post(uri1 + """/?v-1411570305799""")
         .headers(headers_6)
         .formParam("""v-browserDetails""", """1""")
         .formParam("""theme""", """valo""")
         .formParam("""v-appId""", """hellocluster-1193424664""")
         .formParam("""v-sh""", """900""")
         .formParam("""v-sw""", """1440""")
         .formParam("""v-cw""", """1184""")
         .formParam("""v-ch""", """623""")
         .formParam("""v-curdate""", """1411570305799""")
         .formParam("""v-tzo""", """-180""")
         .formParam("""v-dstd""", """60""")
         .formParam("""v-rtzo""", """-120""")
         .formParam("""v-dston""", """true""")
         .formParam("""v-vw""", """1184""")
         .formParam("""v-vh""", """0""")
         .formParam("""v-loc""", """http://192.168.0.254/hellocluster/""")
         .formParam("""v-wn""", """hellocluster-1193424664-0.6654904903306132"""))
    .pause(3)
  }
  .group("Application use") {
    exec(http("Button click 1")
         .post("""/hellocluster/UIDL/?v-wsver=7.3.1&v-uiId=0""")
         .headers(headers_8)
         .body(RawFileBody("RecordedSimulation_request_0008.txt"))
         .check(regex("Greetings").find(0).exists))
    .pause(5)
    .exec(http("Button click 2")
          .post("""/hellocluster/UIDL/?v-wsver=7.3.1&v-uiId=0""")
          .headers(headers_8)
          .body(RawFileBody("RecordedSimulation_request_0008.txt"))
          .check(regex("Greetings").find(0).exists))
    .pause(3)
    .exec(http("Button click 3")
          .post("""/hellocluster/UIDL/?v-wsver=7.3.1&v-uiId=0""")
          .headers(headers_8)
          .body(RawFileBody("RecordedSimulation_request_0008.txt"))
          .check(regex("Greetings").find(0).exists))
    .exec(http("Button click 4")
          .post("""/hellocluster/UIDL/?v-wsver=7.3.1&v-uiId=0""")
          .headers(headers_8)
          .body(RawFileBody("RecordedSimulation_request_0008.txt"))
          .check(regex("Greetings").find(0).exists))
        
  };
//  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

  // This uses more load, simulates 1000 users who arrive with-in 10 seconds
  setUp(scn.inject(rampUsers(1000) over (40 seconds))).protocols(httpProtocol)

}