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

/**
 *
 */
package object midi {

  class RichInstrument(inst: Instrument) {
    def ->(synth: Synthesizer) = {
      synth.loadInstrument(inst)
      synth
    }
  }

  class RichMidiDevice(val m: MidiDevice) {
    def ->(other: MidiDevice) = {
      require(other != null)
      val t = m.getTransmitter
      val r = other.getReceiver
      t.setReceiver(r)
      other
    }
  }

  class RichMidiEvent(e: javax.sound.midi.MidiEvent) {
    def ->(track: Track) {
      track.add(e)
    }
  }

  class RichMidiFile(val file: File) {
    def ->(seq: Sequencer) = {
      seq.setSequence(MidiSystem.getSequence(file))
      seq
    }

    def ->(synth: Synthesizer) = {
      synth.loadAllInstruments(MidiSystem.getSoundbank(file))
      synth
    }
  }

  class RichReceiver(val rcvr: Receiver) {
    def !(midiMessage: javax.sound.midi.MidiMessage, l: Long) {
      rcvr.send(midiMessage, l)
    }
  }

  class RichSequence(seq: javax.sound.midi.Sequence) {
    def >> (file: File) {
      val midiType = MidiSystem.getMidiFileTypes(seq).min
      MidiSystem.write(seq, midiType, file)
    }
  }

  class RichSoundbank(sb: Soundbank) {
    def ->(synth: Synthesizer) = {
      synth.loadAllInstruments(sb)
      synth
    }
  }

  implicit def synthAsReceiver(synth:Synthesizer) = synth.getReceiver

  implicit def midiDeviceToRichMidiDevice(m: MidiDevice) = new RichMidiDevice(m)

  implicit def midiEventToRichMidiEvent(e: MidiEvent) = new RichMidiEvent(e)

  implicit def receiverToRichReceiver(r: Receiver) = new RichReceiver(r)

  implicit def fileToRichMidiFile(f: File) = new RichMidiFile(f)
  
  implicit def sbToRichSoundbank(sb: Soundbank) = new RichSoundbank(sb)

  implicit def seqToRichSequence(seq: Sequence) = new RichSequence(seq)

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

/**
 *
 */
package object sampled {

  class RichAudioInputStream(stream: AudioInputStream) {
    def >>(line: javax.sound.sampled.SourceDataLine) {
      if (!line.isOpen) {
        line.open(stream.getFormat)
      }
      line.start()
      val numBytesToRead = line.getBufferSize
      val myData = new Array[Byte](numBytesToRead)

      var numBytesRead = stream.read(myData, 0, numBytesToRead)
      while (numBytesRead > 0) {
        line.write(myData, 0, numBytesRead);
        numBytesRead = stream.read(myData, 0, numBytesToRead)
      }
      line.drain()
      line.stop()
    }
  }

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