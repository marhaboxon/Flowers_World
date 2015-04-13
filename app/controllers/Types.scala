package controllers

import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger
import play.api.libs.json.Json._
import play.api.libs.json.Json

/**
 * Created by User on 12.04.2015.
 */
class Types extends Controller {

  val types = TableQuery[TypeTable]

  def list = DBAction { implicit rs =>
    Ok(views.html.listFlowerType(types.list))
  }

  def showAdd = DBAction { implicit rs =>
    Ok(views.html.addFlowerType())
  }

  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val name = formParams.get("name")(0)
    val flowerId = (types returning types.map(_.id)) += Type(None, name)
    Logger.info(s"LastId = $flowerId")
    Redirect(routes.Types.list())
  }

  def remove(id: Int) = DBAction { implicit request =>
    types.filter(_.id === id).delete;
    Redirect(routes.Types.list())
  }

  def update(id: Int) = DBAction { implicit rs =>
    val formParams = rs.body.asFormUrlEncoded
    val name = formParams.get("name")(0)

    val f_type = Type(Some(id), name)
    types.filter(_.id === id).update(f_type)
    Redirect(routes.Types.list())
  }

  def showEdit(typeId: Int) = DBAction { implicit request =>
    val byId = types.findBy(_.id)
    val f_type = byId(typeId).list.head

    Ok(views.html.editFlowerType(f_type))
  }
}
