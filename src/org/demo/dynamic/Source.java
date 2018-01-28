package org.demo.dynamic;

public class Source implements ISource {

    public String callMethod() {
	String result = "run callMethod()";
	System.out.println(result);
	return result;
    }
}
