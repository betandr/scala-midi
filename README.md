# scala-midi
Automatically exported from [code.google.com/p/scala-midi](http://code.google.com/p/scala-midi)

This project provides a Scala API layer over javax.sound.midi and javax.sound.sampled. 

##Play an audio file

```scala
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

##Simpler file player using default buffer sizes and audio formats

```scala
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

##Play audio from a URL

```scala
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

##Writing a MIDI file

There is a good example of writing a MIDI file at [www.automatic-pilot.com/midifile.html](http://www.automatic-pilot.com/midifile.html)

Here is that code ported to use this project:

```scala
package com.googlecode.scala.sound.midi.examples

import javax.sound.midi.Sequence
import com.googlecode.scala.sound.midi._
import com.googlecode.scala.sound.midi.Notes._
import com.googlecode.scala.sound.midi.MidiEvent
import com.googlecode.scala.sound.midi.message._
import java.io.File

// scala port of http://www.automatic-pilot.com/midifile.html
object MidiFile {

  def main(args: Array[String]) {
    val sequence = new Sequence(Sequence.PPQ, 24)
    val track = sequence.createTrack()

    MidiEvent(GeneralMIDIOn(), 0) -> track
    MidiEvent(SetTempo(457), 0) -> track  //457.76 in example code
    MidiEvent(TrackName("midifile track"), 0) -> track
    MidiEvent(OmniOn(), 0) -> track
    MidiEvent(PolyOn(), 0) -> track
    MidiEvent(ProgramChange(0, 0), 0) -> track
    MidiEvent(NoteOn(0, C4, 96), 1) -> track
    MidiEvent(NoteOff(0, C4, 64), 121) -> track
    MidiEvent(EndOfTrack(), 140) -> track

    sequence >> new File("midifile.mid")
  }
}
```

##Examples

Comparing code examples from [docs.oracle.com/javase/tutorial/sound/MIDI-messages.html](http://docs.oracle.com/javase/tutorial/sound/MIDI-messages.html)

#Sending a message
Java API:

```java
ShortMessage myMsg = new ShortMessage();
// Start playing the note Middle C (60), 
// moderately loud (velocity = 93).
myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
long timeStamp = -1;
Receiver rcvr = MidiSystem.getReceiver();
rcvr.send(myMsg, timeStamp);
```

Scala API:

```scala
// Start playing the note Middle C (60),
// moderately loud (velocity = 93).
val timeStamp: Long = -1
val rcvr = MidiSystem.getReceiver
rcvr.send(NoteOn(0, C4, 93), timeStamp)
or

rcvr ! (NoteOn(0, C4, 93), timeStamp)
```

##Connecting devices
###Java API:

```java
Sequencer        seq;
Transmitter      seqTrans;
Synthesizer      synth;
Receiver         synthRcvr;
try {
      seq     = MidiSystem.getSequencer();
      seqTrans = seq.getTransmitter();
      synth   = MidiSystem.getSynthesizer();
      synthRcvr = synth.getReceiver(); 
      seqTrans.setReceiver(synthRcvr);      
} catch (MidiUnavailableException e) {
      // handle or throw exception
}
```

###Scala API:

```scala
val seq: Sequencer
val synth: Synthesizer
try {
      seq   = MidiSystem.getSequencer
      synth = MidiSystem.getSynthesizer
      seq -> synth
} catch {
    case e:MidiUnavailableException =>
      // handle or throw exception
}
```

##Playing a MIDI file

```scala
import com.googlecode.scala.sound.midi._
import java.io.File
import javax.sound.midi.MidiSystem

object SequencePlayer {

  def main(args: Array[String]) {
    val file = new File("some midi file")
    using(MidiSystem.getSequencer) {
      seq => {
        seq.open
        (file -> seq).start
        // hack - there should be a better way to do this.  Without
        // this the code exits before the sequence is played 
        while (seq.isRunning) {
          Thread.sleep(2000)
        }
      }
    }
  }
}
```

##Resources

* [docs.oracle.com/javase/tutorial/sound/TOC.html](http://docs.oracle.com/javase/tutorial/sound/TOC.html)
* [docs.oracle.com/javase/7/docs/api/index.html?javax/sound/midi](http://docs.oracle.com/javase/7/docs/api/index.html?javax/sound/midi)
* [www.midi.org/techspecs/midimessages.php](http://www.midi.org/techspecs/midimessages.php)
* [www.jsresources.org/index.html](http://www.jsresources.org/index.html)
* [www.nch.com.au/acm/formats.html](http://www.nch.com.au/acm/formats.html)
