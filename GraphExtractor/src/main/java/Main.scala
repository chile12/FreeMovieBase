import ModelDistributor.{SetStreamPauser}
import akka.actor.{ActorSystem, Props}

/**
 * Created by Chile on 3/18/2015.
 */
object Main {
  def main(args: Array[String]) {
    assert((args.length > 0))
    val actorSystem = ActorSystem()

    val config: ConfigImpl = new ConfigImpl(args(0))
    val rdfReader = new RdfFileReader()
    val modelDistributor = actorSystem.actorOf(Props(classOf[ModelDistributor], config))
    val pauseStream = rdfReader.prepareReading(config.inputFile, modelDistributor)
    val streamPauser = ActorSystem().actorOf(Props(classOf[StreamPauser], pauseStream))
      modelDistributor ! SetStreamPauser(streamPauser)  //set pauseInputStream

    if(args.length > 1)
      rdfReader.startReading(args(1).toLong)
    else
      rdfReader.startReading()
  }
}
