package com.googlecode.scala.sound.midi.message

/**
 * Factory object for Continue messages
 */
object Continue {
  def apply() = {
    ShortMessage(javax.sound.midi.ShortMessage.CONTINUE, 0x00, 0x00)
  }
}
