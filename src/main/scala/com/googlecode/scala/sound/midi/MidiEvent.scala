package com.googlecode.scala.sound.midi

import javax.sound.midi.MidiMessage

object MidiEvent {
  def apply(msg: MidiMessage, tick: Long) = {
    new javax.sound.midi.MidiEvent(msg, tick)
  }
}