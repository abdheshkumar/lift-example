package code
package snippet

import scala.xml.{ NodeSeq, Text }
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._
import net.liftweb.http.S
import java.util.Locale

class HelloWorld extends Loggable {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  // replace the contents of the element with id "time" with the date
  def howdy = {

	logger.info("::::German:::::" + S.?("invalid.email.address", new Locale("de")))
    logger.info("::::English:::::" + S.?("invalid.email.address", new Locale("en")))
    logger.info(":::Default:::::" + S.?("invalid.email.address"))
    
    /**
       logger.info("::::English:::::" + S.?("invalid.email.address", new Locale("en")))
       logger.info("::::German:::::" + S.?("invalid.email.address", new Locale("de")))
       logger.info(":::Default:::::" + S.?("invalid.email.address"))

		it would display 
       ::::English:::::Invalid email address
       ::::German:::::Invalid email address
       :::Default:::::Invalid email address

		when i passed German locale first then it would translate all string into german text. for example
		logger.info("::::German:::::" + S.?("invalid.email.address", new Locale("de")))
		logger.info("::::English:::::" + S.?("invalid.email.address", new Locale("en")))
		logger.info(":::Default:::::" + S.?("invalid.email.address"))

		it would display 
		::::German:::::Die E-Mail-Adresse ist ungültig
		::::English:::::Die E-Mail-Adresse ist ungültig
		:::Default:::::Die E-Mail-Adresse ist ungültig
     */
    "#time *" #> date.map(_.toString)
  }

  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */
}

