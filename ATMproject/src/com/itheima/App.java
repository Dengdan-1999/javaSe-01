package com.itheima;

import com.itheima.atmsystem.ATMIndex;
import com.itheima.atmsystem.ATMSystem;
import com.itheima.domain.Account;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        //构建一些假数据
        //目的：简化测试，不用每次输入全部数据
//        ArrayList<Account> list=new ArrayList<>();
//        Account account1=new Account("11111111","张三","123",10000,10000);
//        Account account2=new Account("11111112","李四","1234",5000,10000);
//        Account account3=new Account("11111113","王五","12345",0,10000);
//        list.add(account1);
//        list.add(account2);
//        list.add(account3);
//
//        ATMSystem system=new ATMSystem();
//        system.atmSystemService(account1);


        //打开首页
        ATMIndex ai=new ATMIndex();
        ai.indexPage();

    }
    //程序的启动入口

    //打开首页
}
