import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import ru.ifmo.jb.{MyInterceptor, MyUtils}

import language.postfixOps
import scala.concurrent.duration._

case object Ping
case object Pong

class Pinger extends Actor {
  var countDown = 100

  def receive: Receive = {
    context.system.log.info(MyUtils.logMsg(this.receive))
    case Pong =>
      println(s"${self.path} received pong, count down $countDown")

      if (countDown > 0) {
        countDown -= 1
        sender() ! Ping
      } else {
        sender() ! PoisonPill
        self ! PoisonPill
      }
  }
}

class Ponger(pinger: ActorRef) extends Actor {
  def receive: Receive = {
    case Ping =>
      println(s"${self.path} received ping")
      pinger ! Pong
  }
}

val system = ActorSystem("pingpong")

val pinger = system.actorOf(Props[Pinger](), "pinger")

val ponger = system.actorOf(Props(classOf[Ponger], pinger), "ponger")

import system.dispatcher
system.scheduler.scheduleOnce(500 millis) {
  ponger ! Ping
}