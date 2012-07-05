/*
 * Copyright 2012 Eric Olander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.scala.sound.midi.message

/**
 * Factory object for javax.sound.midi.ShortMessage
 */
object ShortMessage {

  /**
   *
   * @param command MIDI command
   * @param channel MIDI channel
   * @param data1 first data byte
   * @return javax.sound.midi.ShortMessage instance
   */
  def apply(command: Int, channel: Int, data1: Int) = {
    val msg = new javax.sound.midi.ShortMessage
    msg.setMessage(command, channel, data1)
    msg
  }

  /**
   *
   * @param command MIDI command
   * @param channel MIDI channel
   * @param data1
   * @param data2
   * @return javax.sound.midi.ShortMessage instance
   */
  def apply(command: Int, channel: Int, data1: Int, data2: Int) = {
    val msg = new javax.sound.midi.ShortMessage
    msg.setMessage(command, channel, data1, data2)
    msg
  }
}