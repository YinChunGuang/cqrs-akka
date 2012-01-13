package net.debasishg.domain.trade
package event

import akka.dispatch._
import akka.util.duration._

trait Event
trait State

case class EventLogEntry(entryId: Long, objectId: String, inState: State, withData: Option[Any], event: Event)

trait EventLog extends Iterable[EventLogEntry] {
  def iterator: Iterator[EventLogEntry]
  def iterator(fromEntryId: Long): Iterator[EventLogEntry]
  def appendAsync(id: String, state: State, data: Option[Any], event: Event): Future[EventLogEntry]
  def append(id: String, state: State, data: Option[Any], event: Event): EventLogEntry = 
    Await.result(appendAsync(id, state, data, event), 1 second)
}
