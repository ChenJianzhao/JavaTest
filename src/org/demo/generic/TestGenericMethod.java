package org.demo.generic;

public class TestGenericMethod {

    public static void main(String[] args) {
	User<String> user = new User<String>("SringUserObject");
	
	System.out.println(getUserObject(user));
    }
    
    
    public static <T> T getUserObject(User<T> user) {
	return user.getUserObject();
    }
}
