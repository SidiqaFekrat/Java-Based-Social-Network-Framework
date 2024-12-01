package algs34;

import java.util.*;
import stdlib.*;
import java.util.HashMap;
import java.util.HashSet;


public class MyFB {
	public static boolean DEBUG = true;
	public static Person makePerson (In in) {
		try {
			String name = in.readString ();
			int age = in.readInt ();
			return makePerson (name, age);
		} catch (java.util.InputMismatchException e) {
			StdOut.println ("Input format error");
			return null;
		}
	}
	public static Person makePerson (String name, int age) {
		return new Person (name, age);
	}
	static class Person {
		String name;
		int age;
		public Person (String name, int age) {
			this.name = name;
			this.age = age;
		}
		public String toString () {
			return name + " " + age;
		}
		@Override
		public boolean equals (Object obj) {

			if(this==obj) return true;
			if(!(obj instanceof Person)) return false;
			Person other=(Person) obj;
			return this.name.equals(other.name) &&this.age==other.age;
		}
		@Override
		public int hashCode () {
			//return 1293879128;
			int hash=1293879128;
			return hash+(name !=null ?name.hashCode(): 0)+age;

		}
		
	}
	public static void main (String[] args) {
		Trace.showBuiltInObjects (true);
		//Trace.run ();
		Person x = makePerson ("xi", 1);
		Person y = makePerson ("xi", 1);
		StdOut.println (x.hashCode());
		StdOut.println (y.hashCode());
	}
	
	HashMap<Person,HashSet<Person>> map = new HashMap<>();

	// a person "exists" after they are added using addPerson
	// addPerson does nothing if p already exists
	public void addPerson (Person p) {
		if(!map.containsKey(p)){
			map.put(p, new HashSet<>());
		}

		if (DEBUG) { StdOut.format ("P %s: %s\n", p, map); }
	}
	// addFriendship does nothing if p1 and p2 are already friends or if one does not exist
	public void addFriendship (Person p1, Person p2) {
		if(map.containsKey(p1) && map.containsKey(p2)){
			map.get(p1).add(p2);
			map.get(p2).add(p1);
		}
		if (DEBUG) { StdOut.format ("F %s %s: %s\n", p1, p2, map); }
	}
	// removeFriendship does nothing if p1 and p2 are not friends or if one does not exist
	public void removeFriendship (Person p1, Person p2) {
		if(map.containsKey(p1) && map.containsKey(p2)){
			map.get(p1).remove(p2); // Remove p2 from p1's friends
			map.get(p2).remove(p1); // Remove p1 from p2's friends
		}
		if (DEBUG) { StdOut.format ("U %s %s: %s\n", p1, p2, map); }
	}
	// queryFriendship returns false if p1 and p2 are not friends or if one does not exist
	public boolean queryFriendship (Person p1, Person p2) {
		if (DEBUG) { StdOut.format ("Q %s %s: ", p1, p2); }
		if(!map.containsKey(p1) || !map.containsKey(p2)){
			return false;
		}
		return map.get(p1).contains(p2);
	}

	// lookupFriends returns null or empty iterable if p does not exists
	public Iterable<Person> lookupFriends (Person p) {
		if (DEBUG) { StdOut.format ("L %s:", p); }
		if(!map.containsKey(p)){
			return new ArrayList<>();
		}
		return map.get(p);
	}

	public static void main2 (String[] args) {
		Trace.showBuiltInObjects (true);
		//Trace.run ();
		/*
		 * The first line below causes "in" to read from the console. You can
		 * change this to read from a file, by using something like the line
		 * below, where "input.txt" is a file you create in your eclipse
		 * workspace. The file should be in the same folder that contains "src"
		 * "bin" and "lib":
		 */

		//In[] inputFiles = new In[] { new In () }; // from console
		//In[] inputFiles = new In[] { new In ("input.txt") }; // from file
		In[] inputFiles = new In[] { new In ("input.txt"), new In () }; // from file, then console
		MyFB fb = new MyFB ();
		StdOut.println ("Enter one of the following:");
		StdOut.println ("  P name age -- add person");
		StdOut.println ("  F name1 age1 name2 age2 -- add friendship");
		StdOut.println ("  U name1 age1 name2 age2 -- remove friendship");
		StdOut.println ("  Q name1 age1 name2 age2 -- query friendship");
		StdOut.println ("  L name age -- lookup friends");
		StdOut.println ("  R -- restart");
		StdOut.println ("  X -- exit");
		for (In in : inputFiles) {
			while (in.hasNextLine ()) {
				//StdOut.println (fb. ...); // while debugging, print debugging info here!  You can print maps, sets, lists.
				String action;
				try {
					action = in.readString ();
				} catch (NoSuchElementException e) { continue; }
				switch (action) {
				case "P":
				case "p": {
					Person p = makePerson (in);
					fb.addPerson (p);
					break;
				}
				case "F":
				case "f": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					fb.addFriendship (p1, p2);
					break;
				}
				case "U":
				case "u": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					fb.removeFriendship (p1, p2);
					//Trace.draw ();
					break;
				}
				case "Q":
				case "q": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					boolean result = fb.queryFriendship (p1, p2);
					StdOut.println (result ? "Yes" : "No");
					break;
				}
				case "L":
				case "l": {
					Person p = makePerson (in);
					Iterable<Person> result = fb.lookupFriends (p);
					if (result != null) {
						for (Person friend : result) {
							StdOut.print (friend);
							StdOut.print (" ");
						}
					}
					StdOut.println ();
					break;
				}
				case "R":
				case "r":
					fb = new MyFB ();
					break;
				case "X":
				case "x":
					System.exit (0);
				}
			}
		}
	}
}
