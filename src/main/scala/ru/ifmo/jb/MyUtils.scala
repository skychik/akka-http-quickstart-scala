package ru.ifmo.jb

import akka.actor.AbstractActor.Receive
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors


object MyUtils {
  def intercept[T <: WithId](behavior: Behavior[T]): Behavior[T] = {
    Behaviors.intercept[T, T](() => new MyInterceptor[T]())(behavior)
  }

  def logMsg(msg: WithId): String = {
    s"received: name=${msg.getClass.getName}, id=${msg.__sender_state_id}"
  }

  def logReceive(receive: Receive): Receive = {

  }
}
