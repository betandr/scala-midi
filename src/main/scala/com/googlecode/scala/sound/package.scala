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