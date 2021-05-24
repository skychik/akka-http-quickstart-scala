package ru.ifmo.jb

import akka.actor.typed.{MailboxSelector, SpawnProtocol}
import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.dispatch.{Envelope, MailboxType, MessageQueue, ProducesMessageQueue}

object MyMailbox {
  // This is the MessageQueue implementation
  class MyMessageQueue(queue: MessageQueue) extends MessageQueue {
    def enqueue(receiver: ActorRef, handle: Envelope): Unit = {
      handle.message match {
        case messageWithId: WithId =>
          messageWithId.__receiver = Option(receiver)
          queue.enqueue(receiver, handle)
        case _ => queue.enqueue(receiver, handle)
      }
      Actor
    }
    def dequeue(): Envelope = {
      val envelope = queue.dequeue()
      log(envelope)
      envelope
    }
    def numberOfMessages: Int = queue.numberOfMessages
    def hasMessages: Boolean = queue.hasMessages
    def cleanUp(owner: ActorRef, deadLetters: MessageQueue): Unit = queue.cleanUp(owner, deadLetters)

    private def log(envelope: Envelope): Unit = {

    }
  }
}

class MyMailbox(mailbox: MailboxType) extends MailboxType with ProducesMessageQueue[MyMailbox.MyMessageQueue] {
  import MyMailbox._

  // The create method is called to create the MessageQueue
  final override def create(owner: Option[ActorRef], system: Option[ActorSystem]): MessageQueue =
    new MyMessageQueue(mailbox.create(owner, system))
}
