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

import java.io.File
import javax.sound.midi._
import javax.sound.sampled.{AudioInputStream, LineEvent, LineListener}

package object midi {
  implicit def synthAsReceiver(synth:Synthesizer) = synth.getReceiver

  implicit def midiDeviceToRichMidiDevice(m: MidiDevice) = new RichMidiDevice(m)

  implicit def receiverToRichReceiver(r: Receiver) = new RichReceiver(r)

  implicit def fileToRichMidiFile(f: File) = new RichMidiFile(f)
  
  implicit def sbToRichSoundbank(sb: Soundbank) = new RichSoundbank(sb)

  implicit def instToRichInstrument(inst: Instrument) = new RichInstrument(inst)

  implicit def controllerEventListenerImplicit(func: (ShortMessage) => Unit) = {
    new ControllerEventListener {
      def controlChange(msg: ShortMessage) {func(msg)}
    }
  }

  implicit def metaEventListenerImplicit(func: (MetaMessage) => Unit) = {
    new MetaEventListener {
      def meta(msg: MetaMessage) {func(msg)}
    }
  }

  // standard using block definition
  def using[X <: {def close()}, A](resource: X)(f: X => A) = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }
}

package object sampled {
  implicit def lineListenerImplicit(func: (LineEvent) => Unit) = {
    new LineListener {
      def update(msg: LineEvent) {func(msg)}
    }
  }
  
  implicit def audioInStreamToAudioFmt(s: AudioInputStream) = s.getFormat
  
  implicit def audioInputStreamToRichAudioInputStream(s: AudioInputStream) = new RichAudioInputStream(s)

  // standard using block definition
  def using[X <: {def close()}, A](resource: X)(f: X => A) = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }
}