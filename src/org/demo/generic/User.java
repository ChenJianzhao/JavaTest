package org.demo.generic;

public class User<T> {

    T userObject;
    
    public T getUserObject() {
        return userObject;
    }

    public void setUserObject(T userObject) {
        this.userObject = userObject;
    }

    public User(T userObject) {
	this.userObject = userObject;
    }
}
