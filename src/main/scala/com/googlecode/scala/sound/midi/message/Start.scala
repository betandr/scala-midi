package com.googlecode.scala.sound.midi.message

/**
 * Factory object for Start messages
 */
object Start {
  def apply() = {
    ShortMessage(javax.sound.midi.ShortMessage.START, 0x00, 0x00)
  }
}
