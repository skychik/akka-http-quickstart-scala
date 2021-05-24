package ru.ifmo.jb

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors.{Receive, Supervise}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors, StashBuffer, TimerScheduler}

import scala.reflect.ClassTag

/**
 * MyBehaviours – is Behaviours, which also log
 */
object MyBehaviours {
  def setup[T](factory: ActorContext[T] => Behavior[T]): Behavior[T] =
  // TODO: менять контекст
    Behaviors.setup(factory)

  def withStash[T](capacity: Int)(factory: StashBuffer[T] => Behavior[T]): Behavior[T] =
    Behaviors.withStash(capacity)(factory)

  def same[T]: Behavior[T] = Behaviors.same

  def unhandled[T]: Behavior[T] = Behaviors.unhandled

  def stopped[T]: Behavior[T] = Behaviors.stopped

  def stopped[T](postStop: () => Unit): Behavior[T] = Behaviors.stopped(postStop)

  def empty[T]: Behavior[T] = Behaviors.empty

  def ignore[T]: Behavior[T] = Behaviors.ignore

  def receive[T <: WithId](onMessage: (ActorContext[T], T) => Behavior[T]): Receive[T] =
  // TODO: менять контекст
    MyUtils.intercept[T](Behaviors.receive(onMessage)).asInstanceOf[Receive[T]]

  def receiveMessage[T <: WithId](onMessage: T => Behavior[T]): Receive[T] =
    MyUtils.intercept[T](Behaviors.receiveMessage(onMessage)).asInstanceOf[Receive[T]]

  def receivePartial[T <: WithId](onMessage: PartialFunction[(ActorContext[T], T), Behavior[T]]): Receive[T] =
  // TODO: менять контекст
    MyUtils.intercept[T](Behaviors.receivePartial(onMessage)).asInstanceOf[Receive[T]]


  def receiveMessagePartial[T <: WithId](onMessage: PartialFunction[T, Behavior[T]]): Receive[T] =
    MyUtils.intercept[T](Behaviors.receiveMessagePartial(onMessage)).asInstanceOf[Receive[T]]


  def receiveSignal[T <: WithId](handler: PartialFunction[(ActorContext[T], Signal), Behavior[T]]): Behavior[T] =
  // TODO: менять контекст
    Behaviors.receiveSignal(handler)

  def intercept[O, I](behaviorInterceptor: () => BehaviorInterceptor[O, I])(behavior: Behavior[I]): Behavior[O] =
    Behaviors.intercept(behaviorInterceptor)(behavior)

  def monitor[T: ClassTag](monitor: ActorRef[T], behavior: Behavior[T]): Behavior[T] =
    Behaviors.monitor(monitor, behavior)

  def logMessages[T](behavior: Behavior[T]): Behavior[T] = Behaviors.logMessages(behavior)

  def logMessages[T](logOptions: LogOptions, behavior: Behavior[T]): Behavior[T] =
    Behaviors.logMessages(logOptions, behavior)

  def supervise[T](wrapped: Behavior[T]): Supervise[T] = Behaviors.supervise(wrapped)

  def withTimers[T](factory: TimerScheduler[T] => Behavior[T]): Behavior[T] = Behaviors.withTimers(factory)

  def withMdc[T: ClassTag](mdcForMessage: T => Map[String, String])(behavior: Behavior[T]): Behavior[T] =
    Behaviors.withMdc(mdcForMessage)(behavior)

  def withMdc[T: ClassTag](staticMdc: Map[String, String])(behavior: Behavior[T]): Behavior[T] =
    Behaviors.withMdc(staticMdc)(behavior)

  def withMdc[T: ClassTag](staticMdc: Map[String, String], mdcForMessage: T => Map[String, String])(
    behavior: Behavior[T]): Behavior[T] = Behaviors.withMdc(staticMdc, mdcForMessage)(behavior)
}
