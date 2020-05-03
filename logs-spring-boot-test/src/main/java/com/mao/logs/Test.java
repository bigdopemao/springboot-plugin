package com.mao.logs;

import java.util.*;

/**
 * @author bigdope
 * @create 2019-06-27
 **/
public class Test {

    public static void main(String[] args) {
        testDelList();

        testDelMap();

       getShortName("abc.def.gh");

    }

    private static void testDelMap() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        map.put("6", "6");
        Iterator<String> iteratorMap = map.keySet().iterator();
        while (iteratorMap.hasNext()) {
            String str = iteratorMap.next();
            if (str.equals("2")) {
                iteratorMap.remove();
            }
        }
        System.out.println(map.toString());
    }

    private static void testDelList() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");

        List<String> del = new ArrayList<String>();
        del.add("5");
        del.add("6");
        del.add("7");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (del.contains(str)) {
                iterator.remove();
            }
        }
        System.out.println(list.toString());
    }

    private static String getShortName(String key) {
        final String[] keyparts = key.split("\\.");
        String keypart = keyparts[keyparts.length - 1];
        System.out.println(keypart);
        return keyparts[keyparts.length - 1];
    }

}
