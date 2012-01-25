package com.googlecode.scala.sound.midi

import javax.sound.midi.MidiDevice

class RichMidiDevice(val m: MidiDevice) {

  def ->(other: MidiDevice) {
    require(other != null)
    val t = m.getTransmitter
    val r = other.getReceiver
    t.setReceiver(r)
  }
}

object RichMidiDevice {
  implicit def midiDeviceToRichMidiDevice(m: MidiDevice) = new RichMidiDevice(m)
}