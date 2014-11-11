package code
package snippet

import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import java.util.Locale
import code.lib.DependencyFactory
import net.liftweb.common.Box
import java.util.Date
import net.liftweb.http.S
import net.liftweb.util.Helpers._
import net.liftweb.util.Props
import java.util.ResourceBundle
import net.liftweb.util.NamedPF

trait Localized {
  def getLocalizedString(key: String, locale: Locale) = {
    val bundles = LiftRules.resourceForCurrentLoc.vend() ::: LiftRules.resourceNames.flatMap(name =>
      tryo {
        if (Props.devMode) {
          tryo {
            val clz = this.getClass.getClassLoader.loadClass("java.util.ResourceBundle")
            val meth = clz.getDeclaredMethods.
              filter { m => m.getName == "clearCache" && m.getParameterTypes.length == 0 }.toList.head
            meth.invoke(null)
          }
        }
        List(ResourceBundle.getBundle(name, locale))
      }.openOr(NamedPF.applyBox((name, locale), LiftRules.resourceBundleFactories.toList).map(List(_)) openOr Nil))
    bundles.find(bundle => bundle.containsKey(key)).map(_.getString(key)).getOrElse(key)
  }

}

class HelloWorld extends Localized with Loggable {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  def howdy = {

    logger.info("::::German:::::" + getLocalizedString("invalid.email.address", new Locale("de")))
    logger.info("::::English:::::" + getLocalizedString("invalid.email.address", new Locale("en")))

    logger.info("::::English:::::" + getLocalizedString("invalid.email.address", new Locale("en")))
    logger.info("::::German:::::" + getLocalizedString("invalid.email.address", new Locale("de")))

    /**
     * logger.info("::::English:::::" + S.?("invalid.email.address", new Locale("en")))
     * logger.info("::::German:::::" + S.?("invalid.email.address", new Locale("de")))
     * logger.info(":::Default:::::" + S.?("invalid.email.address"))
     *
     * it would display
     * ::::English:::::Invalid email address
     * ::::German:::::Invalid email address
     * :::Default:::::Invalid email address
     *
     * when i passed German locale first then it would translate all string into german text. for example
     * logger.info("::::German:::::" + S.?("invalid.email.address", new Locale("de")))
     * logger.info("::::English:::::" + S.?("invalid.email.address", new Locale("en")))
     * logger.info(":::Default:::::" + S.?("invalid.email.address"))
     *
     * it would display
     * ::::German:::::Die E-Mail-Adresse ist ungültig
     * ::::English:::::Die E-Mail-Adresse ist ungültig
     * :::Default:::::Die E-Mail-Adresse ist ungültig
     */
    "#time *" #> date.map(_.toString)
  }

  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */
}

