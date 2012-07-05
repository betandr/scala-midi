package com.googlecode.scala.sound.midi.message

/**
 * Factory object for Stop messages
 */
object Stop {
  def apply() = {
    ShortMessage(javax.sound.midi.ShortMessage.STOP, 0x00, 0x00)
  }
}
