package ru.ifmo.jb

import akka.actor.Actor
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{Behavior, BehaviorInterceptor, TypedActorContext}

class MyInterceptor[T <: WithId] extends BehaviorInterceptor[T, T] {
  override def aroundReceive(ctx: TypedActorContext[T], msg: T, target: BehaviorInterceptor.ReceiveTarget[T]): Behavior[T] = {
    ctx.asScala.log.info(MyUtils.logMsg(msg))
    target(ctx, msg)
  }
}