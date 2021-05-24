package ru.ifmo.jb

import akka.actor.ActorRef

trait WithId {
  var __sender_state_id: Int = null
  var __sender: Option[ActorRef] = None
  var __receiver: Option[ActorRef] = None
}
