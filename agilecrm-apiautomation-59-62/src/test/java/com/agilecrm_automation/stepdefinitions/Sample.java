package com.agilecrm_automation.stepdefinitions;

public class Sample {

    public static void m1(){
        System.out.println("m1");
    }

    public void m2(){
        m1();
    }
    public static void main(String [] arg){
        m1();

        int a= 10;

        long l= (long)a;
    }
}
