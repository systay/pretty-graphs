package andres

import java.io.File

trait DeleteDir {
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