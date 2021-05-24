package ru.ifmo.jb

import akka.actor.Actor
import akka.actor.typed.{ActorSystem, Props}

trait MyActorExtension extends Actor {
  def receiveExtension: Receive = PartialFunction.empty
}

abstract class MyActor extends MyActorExtension {
  protected def receiveMsg: Receive

  def receive: Receive = receiveExtension orElse receiveMsg
}

trait ActorLogger extends MyActor {

  abstract override def receiveExtension: Receive = {
    case msg =>
      println(s"********** Logging : $msg")
      super.receiveExtension.applyOrElse(msg, receiveMsg)
  }

}

//object A {
//  val system = ActorSystem[Nothing]("foobar")
//  val myActor = system.actorOf(Props)
//}