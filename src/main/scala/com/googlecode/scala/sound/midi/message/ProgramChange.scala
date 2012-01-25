package com.googlecode.scala.sound.midi.message

object ProgramChange {
  def apply(channel: Int, patch: Int) = {
    ShortMessage(javax.sound.midi.ShortMessage.PROGRAM_CHANGE, channel, patch)
  }
}