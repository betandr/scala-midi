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
import java.net.URL
import scala.language.{implicitConversions, reflectiveCalls}

/**
 *
 */
package object midi {

  class RichInstrument(inst: Instrument) {
    /**
     * Loads this instrument into the specified synth
     * @param synth
     * @return
     */
    def ->(synth: Synthesizer) = {
      synth.loadInstrument(inst)
      synth
    }
  }

  class RichMidiDevice(val m: MidiDevice) {
    /**
     * Connects this midi devices's transmitter to the other's receiver
     * @param other
     * @return
     */
    def ->(other: MidiDevice) = {
      require(other != null)
      val t = m.getTransmitter
      val r = other.getReceiver
      t.setReceiver(r)
      other
    }
  }

  class RichMidiEvent(e: javax.sound.midi.MidiEvent) {
    /**
     * Adds this midi event to the specified track
     * @param track
     * @return
     */
    def ->(track: Track) = {
      track.add(e)
      track
    }
  }

  class RichMidiFile(val file: File) {
    /**
     * Loads the sequencer's sequence from this file
     * @param seq
     * @return
     */
    def ->(seq: Sequencer) = {
      seq.setSequence(MidiSystem.getSequence(file))
      seq
    }

    /**
     * Loads the synth's instruments from this soundbank file
     * @param synth
     * @return
     */
    def ->(synth: Synthesizer) = {
      synth.loadAllInstruments(MidiSystem.getSoundbank(file))
      synth
    }
  }

  class RichReceiver(val rcvr: Receiver) {
    /**
     * Sends a midiMessage to this receiver
     * @param midiMessage
     * @param l
     * @return
     */
    def !(midiMessage: javax.sound.midi.MidiMessage, l: Long) = {
      rcvr.send(midiMessage, l)
      rcvr
    }

    /**
     * Sends a midiEvent to this receiver
     * @param midiEvent
     * @return
     */
    def !(midiEvent: javax.sound.midi.MidiEvent) = {
      rcvr.send(midiEvent.getMessage, midiEvent.getTick)
      rcvr
    }
  }

  class RichSequence(seq: javax.sound.midi.Sequence) {
    /**
     * Writes this sequence to the specified file
     * @param file
     */
    def >> (file: File) {
      val midiType = MidiSystem.getMidiFileTypes(seq).min
      MidiSystem.write(seq, midiType, file)
    }
  }

  class RichSoundbank(sb: Soundbank) {

    /**
     * Loads the synth's instruments from this soundbank
     * @param synth
     * @return
     */
    def ->(synth: Synthesizer) = {
      synth.loadAllInstruments(sb)
      synth
    }
  }

  class RichURL(url: URL) {

    /**
     * Loads the sequencer's sequence from this URL
     * @param seq
     * @return
     */
    def ->(seq: Sequencer) = {
      seq.setSequence(MidiSystem.getSequence(url))
      seq
    }

    /**
     * Loads the synth's instruments from the soundbank specified by this URL
     * @param synth
     * @return
     */
    def ->(synth: Synthesizer) = {
      synth.loadAllInstruments(MidiSystem.getSoundbank(url))
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

  implicit def urlToRichURL(url: URL) = new RichURL(url)

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