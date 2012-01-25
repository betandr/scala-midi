//class GenNotes {

//  val A\u266f:Int = 4

val names = List("A", "As|Bb", "B", "C", "Cs|Db", "D", "Ds|Eb", "E", "F", "Fs|Gb", "G", "Gs|Ab")

val noteStream = Stream.continually(names.toStream).flatten

def intStream(i: Int): Stream[Int] = Stream.cons(i, intStream(i + 1))


intStream(21)

//  val no = for {
//    n <- 21 to 108
//    val l = noteStream.zip(intStream(0))
//  } yield(l)
//  println(no)

def printNoteDef(note: String, v: Int) {
  println("val %s = %d" format(note, v))
}

val noteList: List[(String, (Int, Int))] = noteStream
                                           .take((108 - 21) + 1)
                                           .toList
                                           .zip(intStream(21).map(x => ((x / 12) - 1, x)))

noteList.foreach(n =>
                   if (n._1.contains("|")) {
                     val i = n._1.indexOf("|")
                     printNoteDef(n._1.substring(0, i) + n._2._1, n._2._2)
                     printNoteDef(n._1.substring(i + 1) + n._2._1, n._2._2)
                   } else {
                     printNoteDef(n._1 + n._2._1, n._2._2)
                   }
)
//}