import java.io.{FileOutputStream, File, BufferedWriter, OutputStreamWriter}
import java.util.zip.GZIPOutputStream

import ModelDistributor.{FinalizeOutput, InsertTtl}
import akka.actor.Actor

/**
 * Created by Chile on 3/18/2015.
 */
class GzOutputCollector(filename: String) extends Actor {
  val zip = new GZIPOutputStream(new FileOutputStream(new File(filename)))
  val writer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"))

  override def receive: Receive =
  {
    case InsertTtl(model) =>
    {
      writer.append(model)
    }
    case FinalizeOutput() =>
    {
      writer.close()
    }
    case _ =>
  }
}
