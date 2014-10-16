package code.snippet

import net.liftweb._
import util.Helpers._
import common._
import http._
import sitemap._
import java.util.Date
import bootstrap.liftweb.Boot
import bootstrap.liftweb.SiteMapMenus

// capture the page parameter information
case class ParamInfo(theParam: String)

// a snippet that takes the page parameter information
class ShowParam(pi: ParamInfo) {
  def render = "*" #> pi.theParam
}

object Param {
  // Create a menu for /param/somedata

  lazy val loc = SiteMapMenus.menu.toLoc

  def render = "*" #> loc.currentValue.map(_.theParam)
}

