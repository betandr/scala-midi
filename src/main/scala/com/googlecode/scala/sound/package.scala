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
package com.googlecode.scala.sound

import javax.sound.midi.{Receiver, Synthesizer}
import java.io.File


package object midi {
  implicit def synthAsReceiver(synth:Synthesizer) = synth.getReceiver

  implicit def receiverToRichReceiver(r: Receiver) = new RichReceiver(r)

  implicit def fileToRichMidiFile(f: File) = new RichMidiFile(f)

  // standard using block definition
  def using[X <: {def close()}, A](resource: X)(f: X => A) = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }
}