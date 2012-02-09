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

import javax.sound.midi.MetaMessage
import java.nio.ByteBuffer

object SetTempo {
  def apply(tempoInBPM: Int) = {
    require(tempoInBPM > 0)
    val microSecondsPerBeat = 60000000 / tempoInBPM
    val bytes = ByteBuffer.allocate(4).putInt(microSecondsPerBeat).array().dropWhile(x => x == 0)
    val msg = new MetaMessage()
    msg.setMessage(0x51, bytes, bytes.length)
    msg
  }
}
