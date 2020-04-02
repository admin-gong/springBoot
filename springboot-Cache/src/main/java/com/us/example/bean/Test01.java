package com.us.example.bean;

import java.util.*;
import java.util.stream.Collectors;

public class Test01 {
    public static void main(String[] args) {
        //对list 去重的方法
        List list = new ArrayList();
        HashSet newset  = new HashSet();
        list.add(26);
        list.add(39);
        list.add(5);
        list.add(40);
        list.add(39);
        list.add(25);
        list.add(25);
       newset.addAll(list);
//        newset.add(list);
        System.out.println(newset);
     // List newlist = (List) list.stream().distinct().collect(Collectors.toList());
      //  System.out.println(newlist);
    }
}
