package com.googlecode.scala.sound.midi

import java.io.File
import javax.sound.midi.{MidiSystem, Sequencer}


class RichMidiFile(val file:File) {
  def ->(seq: Sequencer){
    seq.setSequence(MidiSystem.getSequence(file))
  }

}