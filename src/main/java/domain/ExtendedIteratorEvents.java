package domain;

import java.beans.EventSetDescriptor;
import java.util.Vector;

import gui.ExtendedIterator;

public class ExtendedIteratorEvents implements ExtendedIterator<Event> {

	Vector<Event> events;
	int index = 0;
	int size = events.size();

	public ExtendedIteratorEvents(Vector<Event> events) {
		this.events = events;
	}

	@Override
	public boolean hasNext() {
		return index < size - 1;
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
		return index != 0;

	}

	@Override
	public void goFirst() {
		index = 0;

	}

	@Override
	public void goLast() {
		index = size - 1;

	}

}