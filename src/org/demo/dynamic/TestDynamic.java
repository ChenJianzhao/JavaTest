package org.demo.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDynamic {

   public static void main(String[] args) {
       
       final ISource sourc = new Source();
       
       InvocationHandler handler = new InvocationHandler() {
           
           @Override
           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               
               System.out.println("before callMethod!");
               Object result = method.invoke(sourc, args);
               System.out.println("after callMethod!");

               
               return result;
           }
       };

       ISource proxy = (ISource)Proxy.newProxyInstance(Source.class.getClassLoader(), Source.class.getInterfaces(), handler);
       
       String result = proxy.callMethod();
       
       System.out.println("result : " + result);
   }
}
