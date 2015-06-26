# Introduction #

Examples using the javax.sound.sampled API


# Details #
Play an audio file
```
import javax.sound.sampled.AudioSystem
import java.io.File
import com.googlecode.scala.sound.sampled._

object Player {

  def main(args: Array[String]) {
    val file = new File("some audio file")
    val stream = AudioSystem.getAudioInputStream(file)
    using(SourceDataLine(stream)) {
      line => {
        line.open(stream)
        line.start()
        var myData = new Array[Byte](1024)
        val numBytesToRead = 1024
        var numBytesRead = stream.read(myData, 0, numBytesToRead)
        while (numBytesRead > 0) {
          line.write(myData, 0, numBytesRead);
          numBytesRead = stream.read(myData, 0, numBytesToRead)
        }
        line.drain()
        line.stop()
      }
    }
  }
}
```

Simpler file player using default buffer sizes and audio formats
```
import java.io.File
import com.googlecode.scala.sound.sampled._
import javax.sound.sampled.{LineEvent, AudioSystem}

object Player {

  def main(args: Array[String]) {
    val file = new File("some audio file")
    val stream = AudioSystem.getAudioInputStream(file)
    using(SourceDataLine(stream)) {
      line => {
        line.addLineListener((e: LineEvent) => {println(e)})
        stream >> line
      }
    }
  }
}
```

Play audio from a URL
```
import com.googlecode.scala.sound.sampled._
import javax.sound.sampled.{LineEvent, AudioSystem}
import java.net.URL

object Player {

  def main(args: Array[String]) {
    val url = new URL("http://www.mediacollege.com/audio/tone/files/1kHz_44100Hz_16bit_05sec.wav")
    val stream = AudioSystem.getAudioInputStream(url)
    using(SourceDataLine(stream)) {
      line => {
        line.addLineListener((e: LineEvent) => {println(e)})
        stream >> line
      }
    }
  }
}
```