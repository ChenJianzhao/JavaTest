package org.demo.reference;

import java.util.Map;
import java.util.WeakHashMap;

public class TestWerkHashMap {

    
    public static void main(String[] args) {
	
	Map<User, String> temp = new WeakHashMap<User, String>();
	
	User a = new User("a","123");
	User b = new User("b","test");
	
	temp.put(a, a.getName());
	temp.put(b, b.getName());
	
	a = null;
	
	System.gc();
	
	for(User user : temp.keySet())
	    System.out.println(user);
    }
    
    static class User {
	
	String name;
	String number;
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public User(String name, String number) {
	    this.name = name;
	    this.number = number;
	}
	
	@Override
	public String toString() {
	    return name + " : " + number;
	}
    }
}
