package com.savorgames;
//
//import org.junit.Test;
//
//public class TestOnw {
//  
//  @Test
//  public void asldk(){
////    Integer c=2; 
////    Integer d=2; 
////    Integer u=1000; 
////    Integer i=1000; 
////    int o=1000; 
////    int p=1000; 
////    System.out.println(c==d); 
////    System.out.println(u==i); 
////    System.out.println(o==p);
////    
////    Integer a=127; 
////    Integer b=127; 
////    System.out.println(a==b); 
////
////    Integer c1 = 128; 
////    Integer d1 = 128; 
////    System.out.println(c1==d1); 
//  }
//  
//}
public class TestOnw {
  String str = new String("good"); 
  char[] ch = {'a','b','c'};
public static void main(String[] args) {
    StringBuffer a = new StringBuffer("A"); 
    StringBuffer b = new StringBuffer("B"); 
    operator(a, b); 
    System.out.println(100/0);
    System.out.println(a + "," + b); 
} 
public static void operator(StringBuffer x, StringBuffer y) { 
    x.append(y); // zhuyi zhege s
    System.out.println(x);
    y = x;
}
}
