There is a good example of writing a MIDI file at http://www.automatic-pilot.com/midifile.html

Here is that code ported to use this project:

```
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