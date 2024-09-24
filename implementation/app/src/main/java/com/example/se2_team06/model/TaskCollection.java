package com.example.se2_team06.model;

import com.example.se2_team06.model.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Provides class for storing Task in dedicated object.
 */

public class TaskCollection implements IContainer{
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	public Iterator<Task> getIterator() {
		return new TaskIterator();
	}

	public void addTask(Task task) {
		if (task == null) {
			System.out.println("Task is null");
		}
		this.tasks.add(task);
	}
	
	private class TaskIterator implements Iterator<Task> {
		
		private int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < tasks.size();
		}
		
		@Override
		public Task next() {
			if (this.hasNext()) {
				return tasks.get(this.index++);
			}
			return null;
		}
	}

	public int size () {
		return this.tasks.size();
	}

	public boolean equals (Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		TaskCollection tasks = (TaskCollection) o;
		if (tasks.size() != this.size()) {
			return false;
		}
		Iterator<Task> iterator1 = tasks.getIterator();
		for (Iterator<Task> iterator2 = this.getIterator(); iterator2.hasNext();) {
			Task task1 = (Task) iterator1.next();
			Task task2 = (Task) iterator2.next();
			if (!task1.equals(task2)){
				return false;
			}
		}
		return true;
	}

	public String toString () {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Tasks:\n");
		for (Iterator<Task> iterator1 = this.getIterator(); iterator1.hasNext();) {
			Task task = (Task) iterator1.next();
			buffer.append(task.addIndent(task.toString())).append("\n");
		}
		return buffer.toString();
	}

}
