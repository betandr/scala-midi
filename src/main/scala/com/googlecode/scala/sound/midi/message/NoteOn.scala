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
 * Factory object for NoteOn messages
 */
object NoteOn {
  def apply(channel: Int, key: Int, velocity: Int) = {
    ShortMessage(javax.sound.midi.ShortMessage.NOTE_ON, channel, key, velocity)
  }

  def unapply(msg: javax.sound.midi.ShortMessage) = {
    if (msg.getCommand == javax.sound.midi.ShortMessage.NOTE_ON) {
      Some(msg.getChannel, msg.getData1, msg.getData2)
    } else None
  }
}
