object GenNotes {

  val names = List("A", "As|Bb", "B", "C", "Cs|Db", "D", "Ds|Eb", "E", "F", "Fs|Gb", "G", "Gs|Ab")

  val noteStream = Stream.continually(names.toStream).flatten

  def intStream(i: Int): Stream[Int] = Stream.cons(i, intStream(i + 1))

  def printNoteDef(note: String, v: Int) {
    println("val %s = %d" format(note, v))
  }

  val noteList: List[(String, (Int, Int))] =
    noteStream
    .take((108 - 21) + 1)
    .toList
    .zip(intStream(21).map(x => ((x / 12) - 1, x)))

  def main(args: Array[String]) {
    noteList.foreach {
      n =>
        if (n._1.contains("|")) {
          val i = n._1.indexOf("|")
          printNoteDef(n._1.substring(0, i) + n._2._1, n._2._2)
          printNoteDef(n._1.substring(i + 1) + n._2._1, n._2._2)
        } else {
          printNoteDef(n._1 + n._2._1, n._2._2)
        }
    }
  }
}