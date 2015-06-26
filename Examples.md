# Introduction #

Comparing code examples from http://docs.oracle.com/javase/tutorial/sound/MIDI-messages.html

# Details #

## Sending a message ##
Java API:
```
ShortMessage myMsg = new ShortMessage();
// Start playing the note Middle C (60), 
// moderately loud (velocity = 93).
myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
long timeStamp = -1;
Receiver rcvr = MidiSystem.getReceiver();
rcvr.send(myMsg, timeStamp);
```

Scala API:

```
// Start playing the note Middle C (60),
// moderately loud (velocity = 93).
val timeStamp: Long = -1
val rcvr = MidiSystem.getReceiver
rcvr.send(NoteOn(0, C4, 93), timeStamp)
```

or

```
rcvr ! (NoteOn(0, C4, 93), timeStamp)
```
## Connecting devices ##

Java API:
```
    Sequencer        seq;
    Transmitter      seqTrans;
    Synthesizer      synth;
    Receiver         synthRcvr;
    try {
          seq     = MidiSystem.getSequencer();
          seqTrans = seq.getTransmitter();
          synth   = MidiSystem.getSynthesizer();
          synthRcvr = synth.getReceiver(); 
          seqTrans.setReceiver(synthRcvr);      
    } catch (MidiUnavailableException e) {
          // handle or throw exception
    }
```

Scala API:
```
    val seq: Sequencer
    val synth: Synthesizer
    try {
          seq   = MidiSystem.getSequencer
          synth = MidiSystem.getSynthesizer
          seq -> synth
    } catch {
        case e:MidiUnavailableException =>
          // handle or throw exception
    }
```
## Playing a MIDI file ##
```
import com.googlecode.scala.sound.midi._
import java.io.File
import javax.sound.midi.MidiSystem

object SequencePlayer {

  def main(args: Array[String]) {
    val file = new File("some midi file")
    using(MidiSystem.getSequencer) {
      seq => {
        seq.open
        (file -> seq).start
        // hack - there should be a better way to do this.  Without
        // this the code exits before the sequence is played 
        while (seq.isRunning) {
          Thread.sleep(2000)
        }
      }
    }
  }
}
```