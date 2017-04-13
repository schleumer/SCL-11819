import slick.jdbc.H2Profile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}
import slick.collection.heterogeneous.HNil

case class SupplierRow(id: Int, name: String, street: String, city: String, state: String, zip: String)
case class CoffeeRow(name: String, supID: Int, price: Double, sales: Int, total: Int)

class Suppliers(tag: Tag)
  extends Table[SupplierRow](tag, "SUPPLIERS") {

  def id: Rep[Int] = column[Int]("SUP_ID", O.PrimaryKey)
  def name: Rep[String] = column[String]("SUP_NAME")
  def street: Rep[String] = column[String]("STREET")
  def city: Rep[String] = column[String]("CITY")
  def state: Rep[String] = column[String]("STATE")
  def zip: Rep[String] = column[String]("ZIP")

  // XXX: this mapTo will give "Cannot resolve symbol mapTo"
  def * : ProvenShape[SupplierRow] = (id :: name :: street :: city :: state :: zip :: HNil).mapTo[SupplierRow]
}

class Coffees(tag: Tag)
  extends Table[CoffeeRow](tag, "COFFEES") {

  def name: Rep[String] = column[String]("COF_NAME", O.PrimaryKey)
  def supID: Rep[Int] = column[Int]("SUP_ID")
  def price: Rep[Double] = column[Double]("PRICE")
  def sales: Rep[Int] = column[Int]("SALES")
  def total: Rep[Int] = column[Int]("TOTAL")

  def * : ProvenShape[CoffeeRow] = (name :: supID :: price :: sales :: total :: HNil).mapTo[CoffeeRow]
}
