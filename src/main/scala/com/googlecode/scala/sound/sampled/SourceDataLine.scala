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
package com.googlecode.scala.sound.sampled

import java.net.URL
import java.io.File
import javax.sound.sampled.{AudioSystem, AudioFormat}

/**
 * Factory object for SourceDataLine
 */
object SourceDataLine {

  def apply(format: AudioFormat): javax.sound.sampled.SourceDataLine = {
    AudioSystem.getSourceDataLine(format)
  }

  /**
   * Creates a SourceDataLine for the specified file
   * @param file
   * @return
   */
  def apply(file: File): javax.sound.sampled.SourceDataLine = {
    apply(AudioSystem.getAudioInputStream(file))
  }

  /**
   * Creates a SourceDataLine for the specified URL
   * @param url
   * @return
   */
  def apply(url: URL): javax.sound.sampled.SourceDataLine = {
    apply(AudioSystem.getAudioInputStream(url))
  }
}