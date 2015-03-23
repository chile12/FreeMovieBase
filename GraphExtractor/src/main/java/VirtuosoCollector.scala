import ModelDistributor.{FinalizeOutput, InsertTtl}
import akka.actor.Actor

/**
 * Created by Chile on 3/17/2015.
 */
class VirtuosoCollector(host:String, port:Integer, user:String, pass: String, graph: String) extends Actor{

  val connector = new VirtuosoConnector(host, port, user, pass)
  connector.initializeGraphLoading(graph)

  override def receive: Receive =
  {
    case InsertTtl(model) =>
    {
      connector.addTTL(model)
    }
    case FinalizeOutput() =>
    {
      connector.finalizeGraphLoading()
    }
    case _ =>
  }
}
