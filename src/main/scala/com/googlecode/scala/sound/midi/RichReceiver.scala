package com.googlecode.scala.sound.midi

import javax.sound.midi.Receiver

class RichReceiver(val rcvr: Receiver) {
  def !(midiMessage: javax.sound.midi.MidiMessage, l: Long) {
    rcvr.send(midiMessage, l)
  }
}