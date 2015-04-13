package controllers

import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger
import scala.slick.lifted.TableQuery
/**
 * Created by Marhabo on 04.04.2015.
 */
class Flowers extends Controller {

  val flowers = TableQuery[FlowerTable]
  val types = TableQuery[TypeTable]

  def list = DBAction { implicit rs =>
   //Logger.info(s"SHOW_ALL = ${flowers.list}")
    val flowerResult = (for {
      (flower, f_type) <- flowers leftJoin types on (_.typeId === _.id)
    } yield (flower, f_type.name)).list
    .map { case (flower, typeName) => Flowers_Type(flower, typeName)}
    Ok(views.html.list(flowerResult))
  }

  def showAdd = DBAction { implicit rs =>
    Ok(views.html.add(types.list))
  }

  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val imgName = formParams.get("imgName")(0)
    val typeId = formParams.get("type")(0).toInt
    val name = formParams.get("name")(0)
    val description = formParams.get("description")(0)
    val cost = formParams.get("cost")(0)


    val flowerId = (flowers returning flowers.map(_.id)) += Flower(None, imgName, typeId, name, description, cost)
    Logger.info(s"LastId = $flowerId")
    Redirect(routes.Flowers.list())
  }


  def remove(id: Int) = DBAction { implicit request =>
    flowers.filter(_.id === id).delete;
    Redirect(routes.Flowers.list())
  }
  def update(id: Int) = DBAction { implicit rs =>
    val formParams = rs.body.asFormUrlEncoded
    val imgName = formParams.get("img")(0)
    val typeId = formParams.get("country")(0).toInt
    val name = formParams.get("name")(0)
    val description = formParams.get("description") (0)
    val cost = formParams.get("cost")(0)


    val flower = Flower(Some(id), imgName, typeId, name, description, cost)
    flowers.filter(_.id === id).update(flower)

    Redirect(routes.Flowers.list())
  }

  def showEdit(flowerId: Int) = DBAction { implicit request =>
    val byId = flowers.findBy(_.id)
    val flower = byId(flowerId).list.head

    Ok(views.html.edit(flower, types.list))
  }

}
