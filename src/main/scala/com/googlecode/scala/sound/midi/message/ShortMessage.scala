package com.googlecode.scala.sound.midi.message

object ShortMessage {

  def apply(command:Int, channel:Int, data1:Int) = {
    val msg = new javax.sound.midi.ShortMessage
    msg.setMessage(command, channel, data1)
    msg
  }

  def apply(command:Int, channel:Int, data1:Int, data2:Int) = {
    val msg = new javax.sound.midi.ShortMessage
    msg.setMessage(command, channel, data1, data2)
    msg
  }
}