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

import javax.sound.sampled.{AudioInputStream, SourceDataLine}


class RichAudioInputStream(stream: AudioInputStream) {
   def >>(line: SourceDataLine) {
     if (!line.isOpen) {
       line.open()
     }
     line.start()
     val numBytesToRead = line.getBufferSize
     var myData = new Array[Byte](numBytesToRead)

     var numBytesRead = stream.read(myData, 0, numBytesToRead)
     while (numBytesRead > 0) {
       line.write(myData, 0, numBytesRead);
       numBytesRead = stream.read(myData, 0, numBytesToRead)
     }
     line.drain()
     line.stop()
   }
}