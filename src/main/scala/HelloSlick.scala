import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

// The main application
object HelloSlick extends App {
  val db = Database.forConfig("h2mem1")

  val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]
  val coffees: TableQuery[Coffees] = TableQuery[Coffees]

  val setupAction: DBIO[Unit] = DBIO.seq(
    (suppliers.schema ++ coffees.schema).create,

    suppliers += SupplierRow(101, "Acme, Inc. 1", "99 Market Street", "Groundsville", "CA", "95199"),
    suppliers += SupplierRow(49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460"),
    suppliers += SupplierRow(150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")
  )

  val setup: Future[Unit] = db.run(setupAction)

  setup
    .flatMap(_ => db.run(suppliers.result))
    .andThen {
      case Success(r) => println(r)
      case Failure(ex) => println(ex)
    }
    .andThen {
      // exit
      case _ => System.exit(0)
    }


  // keep things running
  Thread.currentThread().join()
}
