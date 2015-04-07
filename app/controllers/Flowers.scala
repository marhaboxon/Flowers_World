package controllers

import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger

/**
 * Created by Marhabo on 04.04.2015.
 */

import scala.slick.lifted.TableQuery

class Flowers extends Controller {

  val flowers = TableQuery[FlowerTable]

  def list = DBAction { implicit rs =>
   Logger.info(s"SHOW_ALL = ${flowers.list}")
    Ok(views.html.list(flowers.list))
  }

  def showAddForm = Action {
    Ok(views.html.add())
  }

  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val name = formParams.get("name")(0)
    val description = formParams.get("description")(0)
    val types = formParams.get("types")(0)

    println("Name: " + name )
    val FlowerId = (flowers returning flowers.map(_.id)) += Flower(None, name, description, types)
    Redirect(routes.Flowers.list())
  }
  def remove(id: Int) = DBAction { implicit request =>
    flowers.filter(_.id === id).delete;
    Redirect(routes.Flowers.list())
  }
}
