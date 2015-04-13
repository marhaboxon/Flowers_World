package models

/**
 * Created by Marhabo on 4/04/15.
 */
import com.sun.org.glassfish.gmbal.Description
import play.api.db.slick.Config.driver.simple._
case class Flower(id: Option[Int],
                  imgName: String,
                  typeId: Int,
                  name: String,
                 description: String,
                   cost: String
                 )

case class Type(id: Option[Int],
                  name: String)

case class Flowers_Type(flower: Flower,typeName: String)

class FlowerTable(tag: Tag) extends Table[Flower] (tag, "UZFLO"){
  val types = TableQuery[TypeTable]

  def id=column[Int] ("ID", O.PrimaryKey,O.AutoInc)

  def imgName = column[String]("IMGNAME", O.Default(""))

  def typeId = column[Int]("TYPE_ID", O.NotNull)

  def name = column[String]("NAME", O.Default(""))

  def description = column[String]("DESCRIPTION", O.Default(""))

  def cost = column[String]("COST", O.Default(""))

  def * =(id.?,imgName,typeId,name,description,cost) <> (Flower.tupled, Flower.unapply)

  def f_type = foreignKey("FLOWER_FK_TYPE_ID", typeId, types)(_.id)

}
class TypeTable(tag: Tag) extends Table[Type] (tag, "TYPE"){

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String] ("NAME", O.Default(""))

  def * =(id.?, name) <> (Type.tupled, Type.unapply _)
}