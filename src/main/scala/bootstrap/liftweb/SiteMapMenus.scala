package bootstrap.liftweb

import net.liftweb.sitemap.SiteMap
import net.liftweb.sitemap.Menu
import code.snippet.ParamInfo
import code.model.User
import net.liftweb.common.Full
import net.liftweb.sitemap.Loc
import net.liftweb.sitemap.Loc.Link

object SiteMapMenus {
  val menu = Menu.param[ParamInfo]("Param", "Param",
    s => {
      println("::::::::::::Menu.param:::::::::" + s)
      Full(ParamInfo(s))
    },
    pi => pi.theParam) / "param"

  val siteMap = SiteMap(
    Menu.i("Home") / "index" >> User.AddUserMenusAfter,
    menu,
    Menu(Loc("Static", Link(List("static"), true, "/static/index"), "Static Content")))
}