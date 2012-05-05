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

import org.junit.Test
import org.neo4j.cypher.ExecutionEngine
import org.neo4j.kernel.EmbeddedGraphDatabase
import java.io.File

class GraphCreatorTest {
  val directory = "./target/graph.db"
  delete(directory)

  val db = new EmbeddedGraphDatabase(directory)
  val engine = new ExecutionEngine(db)
  val creator = new GraphCreator(engine)
  val k = 6

  @Test def createGraphs() {
    val star = creator.createStar(k)
    val wheel = creator.createWheel(k)
    val friends = creator.createFriendShipGraph(k)

    val map = Map(
      "star" -> star,
      "wheel" -> wheel,
      "friends" -> friends
    )

    engine.execute("start ref=node(0) create ref-[:wheel]->{wheel}, ref-[:star]->{star}, ref-[:friends]->{friends}", map)
    db.shutdown()
  }

  def delete(fileName: String): Unit = delete(new File(fileName))

  def delete(files: Iterable[File]): Unit = files.foreach(delete)

  def delete(file: File) {
    if (file.isDirectory) {
      delete(listFiles(file))
      file.delete
    }
    else if (file.exists)
      file.delete
  }

  def listFiles(dir: File): Array[File] = wrapNull(dir.listFiles())

  private def wrapNull(a: Array[File]) = {
    if (a == null)
      new Array[File](0)
    else
      a
  }
}