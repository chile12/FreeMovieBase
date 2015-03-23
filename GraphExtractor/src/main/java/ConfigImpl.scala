import scala.util.parsing.json.JSON

/**
 * Created by Chile on 3/18/2015.
 */
class ConfigImpl(path: String) {

  private val source = scala.io.Source.fromFile(path)
  private val jsonString = source.mkString
  source.close()

  val zz = JSON.parseFull(jsonString)
  private val zw : Map[String, Any] = JSON.parseFull(jsonString).get.asInstanceOf[Map[String, Any]].get("map").get.asInstanceOf[Map[String, Any]]

  val vHost: String = zw.get("virtuosoHost").get.toString
  val vPort: Integer = zw.get("virtuosoPort").get.asInstanceOf[Double].toInt
  val vUser: String = zw.get("virtuosoUser").get.toString
  val vPass: String = zw.get("virtuosoPass").get.toString
  val inputFile: String = zw.get("inputFile").get.toString
  val outputDirectory: String = zw.get("baseDirectory").get.toString
  val graphName: String = zw.get("graphName").get.toString
  val filter: List[List[String]] = zw.get("filter").get.asInstanceOf[List[List[String]]]
  val filterInserts = zw.get("filterInserts").get.asInstanceOf[Map[String, String]]

}
