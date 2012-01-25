package com.googlecode.scala.sound.midi.message

object NoteOn {
  def apply(channel: Int, key: Int, velocity: Int) = {
    ShortMessage(javax.sound.midi.ShortMessage.NOTE_ON, channel, key, velocity)
  }
}