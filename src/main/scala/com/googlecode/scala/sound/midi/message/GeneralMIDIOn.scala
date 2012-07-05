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

import javax.sound.midi.SysexMessage

/**
 * Factory object for GeneralMIDIOn sysex messages
 */
object GeneralMIDIOn {
  def apply() = {
    val b = List[Byte](0xF0.toByte, 0x7E, 0x7F, 0x09, 0x01, 0xF7.toByte).toArray
    val msg = new SysexMessage()
    msg.setMessage(b, b.length)
    msg
  }
}
