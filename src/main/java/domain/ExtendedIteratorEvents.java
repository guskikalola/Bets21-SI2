package domain;

import java.beans.EventSetDescriptor;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExtendedIteratorEvents implements ExtendedIterator<Event> {

	Vector<Event> events;
	int index = 0;
	
	public ExtendedIteratorEvents() {
		this.events = new Vector<Event>();
	}

	public ExtendedIteratorEvents(Vector<Event> events) {
		this.events = events;
	}

	@Override
	public boolean hasNext() {
		return index < events.size() - 1;
	}

	@Override
	public Event next() {
		Event ev = events.get(index);
		index++;
		return ev;
	}

	@Override
	public Event previous() {
		Event ev = events.get(index-1);
		index--;
		return ev;
	}

	@Override
	public boolean hasPrevious() {
		return index > 0;

	}

	@Override
	public void goFirst() {
		index = 0;

	}

	@Override
	public void goLast() {
		index = events.size() - 1;

	}

}