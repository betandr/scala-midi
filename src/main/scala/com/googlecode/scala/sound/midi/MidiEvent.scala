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
package com.googlecode.scala.sound.midi

import javax.sound.midi.MidiMessage

/**
 * Factory object for javax.sound.midi.MidiEvent
 */
object MidiEvent {

  /**
   *
   * @param msg
   * @param tick
   * @return javax.sound.midi.MidiEvent instance
   */
  def apply(msg: MidiMessage, tick: Long) = {
    new javax.sound.midi.MidiEvent(msg, tick)
  }

  def unapply(event: javax.sound.midi.MidiEvent) = {
    Some(event.getMessage, event.getTick)
  }
}
