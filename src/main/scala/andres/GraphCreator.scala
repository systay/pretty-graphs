/**
 * Copyright (c) 2002-2012 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package andres

import org.neo4j.cypher.ExecutionEngine
import org.neo4j.graphdb.Node

class GraphCreator(engine: ExecutionEngine) {

  /**
   * Creates a graph star http://en.wikipedia.org/wiki/Star_(graph_theory)
   * @param k The number of leaves. Must be at least 3
   * @return The center node of the star
   */
  def createStar(k: Int): Node = {
    assume(k > 2)

    val edgeNodes = (1 to k).map(x => Map[String, Any]()).toSeq

    createStarFromSeq(edgeNodes)
  }


  private def createStarFromSeq(edgeNodes: Seq[Map[String, Any]]): Node = engine.execute("""
create center
with center
create edge = {edges}
with center, edge
create center-[:X]->edge
return distinct center
  """, Map("edges" -> edgeNodes)).columnAs[Node]("center").toList.head

  /**
   * Creates a graph wheel http://en.wikipedia.org/wiki/Wheel_graph
   * @param n The number of leaves. Must be at least 3
   * @return The center node of the wheel
   */
  def createWheel(n: Int): Node = {
    assume(n > 2)

    val center = createNumberedStar(n)

    engine.execute("""
start center = node({center})
match center-->edgeN, center-->edgeNplus1
where edgeN.count = edgeNplus1.count + 1
create edgeN-[:X]->edgeNplus1
with center, min(edgeNplus1.count) as min, max(edgeN.count) as max
match center-->edgeMax, center-->edgeMin
where edgeMax.count = max and edgeMin.count = min
create edgeMax-[:X]->edgeMin""", Map("center" -> center))

    center
  }

  private def createNumberedStar(n: Int): Node = {
    val edgeNodes = (1 to n).map(x => Map[String, Any]("count" -> x)).toSeq
    val center = createStarFromSeq(edgeNodes)
    center
  }

  /**
   * Create a friendship graph http://en.wikipedia.org/wiki/Friendship_graph
   * @param n The number of cycles in the friendship graph
   * @return The center node of the friendship graph
   */
  def createFriendShipGraph(n: Int): Node = {
    val center = createNumberedStar(n)

    engine.execute("""
start center = node({center})
match center-->leaf
create other, other-[:X]->leaf, center-[:X]->other""", Map("center" -> center))

    center
  }
}