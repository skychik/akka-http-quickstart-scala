package ru.ifmo.jb

import akka.actor.typed.{ActorSystem, Extension, ExtensionId}


/**
 * Extension, which adds unique id for each state of an actor
 * and logs it, so you can lately analyse the sequence:
 * who called whom and why some actor broke, while receiving some message
 *
 * @param system
 */
@deprecated
class ActorCounterLogger(system: ActorSystem[_]) extends Extension {

}

@deprecated
object ActorCounterLogger extends ExtensionId[ActorCounterLogger] {
  def createExtension(system: ActorSystem[_]): ActorCounterLogger = new ActorCounterLogger(system)

  def get(system: ActorSystem[_]): ActorCounterLogger = apply(system)
}