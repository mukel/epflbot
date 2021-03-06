package ch.epfl.telegram

import ch.epfl.telegram.commands._
import ch.epfl.telegram.utils.{AsyncUpdates, LogUpdates}
import info.mukel.telegrambot4s.api._

import scala.io.Source
import scala.util.Properties
import scala.concurrent.duration._

object EpflBot
    extends App
    // Bot skeleton
    with TelegramBot
    with Polling
    with WebRoutes
    with Commands
    with ChatActions

    // EPFLBot extensions
    with TL
    with Bus
    with Survey
    with InlineEpflDirectory
    with Events
    with Menus
    with Room
    with About
    with Beers
    with Microsite
    // with AddYourCoolFeatureHere ...

    /* The access-control trait must be last */
    with TequilaAuthentication
    /* All updates are processed asynchronously */
    with AsyncUpdates
    with LogUpdates {

  lazy val token = Properties
    .envOrNone("EPFLBOT_TOKEN")
    .getOrElse(Source.fromFile("token").getLines().mkString)

  // Give enough time to spawn docker, create indices..
  system.scheduler.schedule(initialDelay = 1.minute, interval = 1.day) {
    LDAP.refreshDirectory()
  }

  run()
}
