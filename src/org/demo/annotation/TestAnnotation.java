package org.demo.annotation;

//@MyAnnotation("value1")
@MyAnnotation(value = "value1", param1 = "param1")
public class TestAnnotation {

    public static void main(String[] args) {
	
	Class clz = TestAnnotation.class;
	MyAnnotation anno = (MyAnnotation) clz.getAnnotation(MyAnnotation.class);
	
	System.out.println(anno.value());
	
	System.out.println(anno.param1());
	
	System.out.println(anno.param2());
	
    }
}
