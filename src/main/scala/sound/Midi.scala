package sound

import javax.sound.midi.{Synthesizer, MidiUnavailableException, MidiSystem, Receiver}
import com.googlecode.scala.sound.midi.Notes._
import com.googlecode.scala.sound.midi.RichMidiDevice._
import com.googlecode.scala.sound.midi.message.{NoteOff, NoteOn}

object Midi {

  val MajorScale = List(0, 2, 2, 1, 2, 2, 2, 1)
  val MinorScale = List(0, 2, 1, 2, 2, 2, 1, 2)

//  var synth    : Synthesizer = null
  var rcvr     : Receiver    = null
  var timeStamp: Long        = 0

  def playNote {
    // Start playing the note Middle C (60),
    // moderately loud (velocity = 93).
    val timeStamp: Long = -1
    //  val rcvr = MidiSystem.getReceiver()
    rcvr.send(NoteOn(0, C4, 93), timeStamp)
  }

  def playScale(scale: List[Int]) {
    val timeStamp: Long = -1
    var note = C4
    scale.foreach {
                    interval =>
                      note += interval
                      rcvr.send(NoteOn(0, note, 93), timeStamp)
                      Thread.sleep(200)
                      rcvr.send(NoteOff(0, note, 93), timeStamp)
                  }
  }

  def playScale {
    for (note <- C4 until C5) {
      rcvr.send(NoteOn(0, note, 93), timeStamp)
      timeStamp += 200000
      rcvr.send(NoteOff(0, note, 93), timeStamp)
    }
  }

  def playChord {
    rcvr.send(NoteOn(0, C4, 93), timeStamp)
    rcvr.send(NoteOn(0, Eb4, 93), timeStamp)
    rcvr.send(NoteOn(0, G4, 93), timeStamp)
    rcvr.send(NoteOn(0, Bb4, 93), timeStamp)
    Thread.sleep(400)
    timeStamp += 400000
    rcvr.send(NoteOff(0, C4, 93), timeStamp)
    rcvr.send(NoteOff(0, Eb4, 93), timeStamp)
    rcvr.send(NoteOff(0, G4, 93), timeStamp)
    rcvr.send(NoteOff(0, Bb4, 93), timeStamp)
    Thread.sleep(400)
  }

  // standard using block definition
  def using[X <: {def close()}, A](resource: X)(f: X => A) = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }

  def main(args: Array[String]) {

    val devInfo = MidiSystem.getMidiDeviceInfo
    devInfo.foreach(println _)

    val seq = MidiSystem.getSequencer

    using(MidiSystem.getSynthesizer) {
      synth => {
        if (!(synth.isOpen)) {
          synth.open()
        }
        timeStamp = synth.getMicrosecondPosition
        val soundbank = synth.getDefaultSoundbank
        println(soundbank.getDescription)
        val instList = synth.getAvailableInstruments
        instList.foreach(println _)
        synth.loadInstrument(instList(137))
        val channels = synth.getChannels
        channels(0).programChange(0, 50)
        rcvr = synth.getReceiver
        //   rcvr.send(ProgramChange(0, 8), -1)
        playScale(MinorScale)
        playChord
        //   synth.
        //   device.

      }
    }
  }

}