import ModelDistributor.Start
import akka.actor.Actor


/**
 * Created by Chile on 3/25/2015.
 */
class StreamControlActor(reader: RdfFileReader) extends Actor{
  override def receive: Receive =
  {
    case Start() =>
    {
      reader.startReading()
    }
  }
}
