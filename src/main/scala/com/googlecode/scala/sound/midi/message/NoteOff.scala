package com.googlecode.scala.sound.midi.message

object NoteOff {
  def apply(channel: Int, key: Int, velocity: Int) = {
    ShortMessage(javax.sound.midi.ShortMessage.NOTE_OFF, channel, key, velocity)
  }
}