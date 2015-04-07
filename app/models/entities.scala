package models

/**
 * Created by Marhabo on 4/04/15.
 */

import com.sun.org.glassfish.gmbal.Description
import play.api.db.slick.Config.driver.simple._


case class Flower(id: Option[Int],
                  name: String,
                 description: String,
                 types: String
                   )

class FlowerTable(tag: Tag) extends Table[Flower] (tag, "UZFLO"){

  def id=column[Int] ("ID", O.PrimaryKey,O.AutoInc)

  def name = column[String]("NAME", O.Default(""))

  def description = column[String]("DESCRIPTION", O.Default(""))

  def types = column[String]("TYPE", O.Default(""))

  def * =(id.?, name, description, types ) <> (Flower.tupled, Flower.unapply)
}