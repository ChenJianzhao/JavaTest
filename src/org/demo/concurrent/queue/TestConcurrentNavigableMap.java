package org.demo.concurrent.queue;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class TestConcurrentNavigableMap {

    public static void main(String[] args) {

        ConcurrentNavigableMap<String,String> map = new ConcurrentSkipListMap<String,String>();

        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");

        ConcurrentNavigableMap<String,String> headMap = map.headMap("2");
        System.out.println("headMap() to key 2");
        for(String value: headMap.values())
            System.out.println(value);

        ConcurrentNavigableMap<String,String> tailMap = map.tailMap("2");
        System.out.println("tailMap() from key 2");
        for(String value: tailMap.values())
            System.out.println(value);

        ConcurrentNavigableMap<String,String> subMap = map.subMap("2","3");
        System.out.println("subMap() from key 2 to key 3");
        for(String value: subMap.values())
            System.out.println(value);
    }
}
