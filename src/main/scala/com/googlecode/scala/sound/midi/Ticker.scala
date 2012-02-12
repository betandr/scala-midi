package com.googlecode.scala.sound.midi

import util.Random

class Ticker(val tempo: Int, val ticks: Int) {
  def ticksPerSecond = ticks * (tempo / 60.0)

  def tickSize = 1.0 / ticksPerSecond

  var currentTick = 0

  var triplet = false

  var human = false
  var hfactor = 0
  var bins = 0
  
  val rnd = new Random()

  def humanize(n: Int) {
    human = true
    hfactor = n
    bins = 2*n + 1
  }
  
  def offset(n: Int) = {
    if (human) {
      n + (rnd.nextInt(bins) - hfactor)
    } else {
      n
    }
  }

  def currentBeat = {
    (currentTick / ticks) % 4
  }

  def onBeat = {
    currentTick == (currentTick / ticks) * ticks
  }

  def nextQuarter = {
    currentTick += ticks
    offset(currentTick)
  }

  def nextEighth = {
    if (triplet) {
      currentTick += (ticks / 3)
    } else {
      currentTick += (ticks / 2)
    }
    offset(currentTick)
  }

  def nextSixteenth = {
    if (triplet) {
      currentTick += (ticks / 6)
    } else {
      currentTick += (ticks / 4)
    }
    offset(currentTick)
  }

  def nextWhole = {
    currentTick += ticks * 4
    offset(currentTick)
  }

  def nextHalf = {
    currentTick += ticks * 2
    offset(currentTick)
  }

}

object Ticker {
  def apply(tempo: Int, ticks: Int) = new Ticker(tempo, ticks)
  
  def apply(seq: javax.sound.midi.Sequence) = new Ticker(116, seq.getResolution)
  
}