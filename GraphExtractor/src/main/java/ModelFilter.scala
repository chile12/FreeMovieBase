
import java.io.{BufferedInputStream, FileInputStream, StringWriter}
import java.util.zip.GZIPInputStream

import ModelDistributor._
import TripleSource.TripleSource
import akka.actor.{Actor, ActorRef, Props}
import org.openrdf.model.impl.{TreeModel, URIImpl}
import org.openrdf.model.{Model, Literal, Resource, Statement}
import org.openrdf.rio.RDFHandler
import org.openrdf.rio.ntriples.{NTriplesParser, NTriplesWriter}

import scala.collection.{JavaConversions, immutable}
import scala.util.control.Breaks._

/**
 * Created by Chile on 3/17/2015.
 */
class ModelFilter(subj: String, pred: String, obj: String, lang: String, outDir :String, virtusosCollector: ActorRef) extends Actor{
  val subjList = if(subj != null && subj.startsWith("file:")) loadResourceList(subj.substring(5), TripleSource.Subject) else null
  val predList = if(pred != null && pred.startsWith("file:")) loadResourceList(pred.substring(5), TripleSource.Predicate) else null
  val objList = if(obj != null && obj.startsWith("file:")) loadResourceList(obj.substring(5), TripleSource.Object) else null

  val subjUri = if(subj != null && subjList == null) new URIImpl(resolveNamespaces(subj)) else null
  val predUri = if(pred != null && predList == null) new URIImpl(resolveNamespaces(pred)) else null
  val objUri = if(obj != null && objList == null) new URIImpl(resolveNamespaces(obj)) else null

  val name = (if(subj != null) "subj_" + subj) + (if(pred != null) "_pred_" + pred).toString + (if(obj != null) "_obj_" + obj) + (if(lang != null) "_language_" + lang).toString
  val fileOutput = context.actorOf(Props(classOf[GzOutputCollector], outDir + name.replace(':', '.') + ".ttl.gz"))
  var firstInsert = true

  context.parent ! FilterReady(self.path.toString)

  override def receive: Receive =
  {
    case ModelOrg(model) => {
      val m = model.filter(subjUri, predUri, objUri)

      if (m.size() > 0)
      {
        val sw = new StringWriter()
        val writer: NTriplesWriter = new NTriplesWriter(sw)
        val iter = m.iterator()
        writer.startRDF()
        if(firstInsert) {
          writer.handleComment("FreeBase extraction for filter: " + name)
          firstInsert = false
        }
        while (iter.hasNext)
        {
          breakable {
            val st: Statement = iter.next()
            if (subjList != null) {
              if (!subjList.contains(st.getSubject))
                break;
            }
            if (predList != null) {
              if (!predList.contains(st.getPredicate))
                break;
            }
            if (objList != null) {
              if (!objList.contains(st.getObject.asInstanceOf[Resource]))
                break;
            }
            if (lang != null && st.getObject != null) {
              st.getObject match {
                case q if q == classOf[Literal] =>
                  if (q.asInstanceOf[Literal].getLanguage != lang)
                    break;
                case _ =>
              }
            }
            writer.handleStatement(st)
          }
        }
        writer.endRDF()
        if(virtusosCollector != null)
          virtusosCollector ! InsertTtl(sw.toString)
        fileOutput ! InsertTtl(sw.toString)
      }
      sender ! JobDone(self.path.toString)
    }
    case FinalizeOutput() =>
    {
      fileOutput ! FinalizeOutput()
    }
    case _ =>  //TODO
  }

  def resolveNamespaces(uri: String) : String =
  {
    var u = uri
    ModelFilter.prefixes.keys.foreach(key =>
    {
      val ind = u.indexOf(':')
      if(ind >= 0 && u.substring(0, ind) == key)
        u = ModelFilter.prefixes(key) + u.substring(ind+1)
    })
    u
  }

  def loadResourceList(path: String,source: TripleSource) : immutable.Set[Resource] =
  {
    val inputStream = new GZIPInputStream(new BufferedInputStream(new FileInputStream(path)))
    val parser = new NTriplesParser()
    val handler = new FilterRdfHandler()
    parser.setRDFHandler(handler)
    parser.setStopAtFirstError(false)
    parser.parse(inputStream, "egal")

    val res : java.util.Set[Resource] = source match  {
        case TripleSource.Subject => handler.getModel().subjects()
        case TripleSource.Predicate => handler.getModel().predicates().asInstanceOf[java.util.Set[Resource]]
        case TripleSource.Object => handler.getModel().objects().asInstanceOf[java.util.Set[Resource]]
      }
    JavaConversions.asScalaSet[Resource](res).toSet
  }

  class FilterRdfHandler extends RDFHandler
  {
    private var model : TreeModel = new TreeModel()

    override def startRDF(): Unit =    {  }

    override def handleComment(s: String): Unit =     {}

    override def handleStatement(statement: Statement): Unit =
    {
      model.add(statement)
    }

    override def endRDF(): Unit =     {  }

    override def handleNamespace(s: String, s1: String): Unit = {}

    def getModel() : Model = model
  }
}

object ModelFilter{
    val prefixes = Map("ns" -> "http://rdf.freebase.com/ns/",
      "key" -> "http://rdf.freebase.com/key/",
      "owl" -> "http://www.w3.org/2002/07/owl#",
      "rdfs" -> "http://www.w3.org/2000/01/rdf-schema#",
      "xsd" -> "http://www.w3.org/2001/XMLSchema#")
}

object TripleSource extends Enumeration {
  type TripleSource = Value
  val Subject, Predicate, Object = Value
}
