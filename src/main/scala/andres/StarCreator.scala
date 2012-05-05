package andres

import org.neo4j.cypher.ExecutionEngine
import org.neo4j.graphdb.Node

class StarCreator(engine: ExecutionEngine) {
  def createStart(k: Int): Node = {
    val edgeNodes = (1 to k).map(x => Map[String, Any]()).toSeq

    engine.execute("""
create center
with center
create edge = {edges}
with center, edge
create center-[:X]->edge
return distinct center
    """).columnAs[Node]().toList.head


  }
}