package com.itheima.atmsystem;

import com.itheima.domain.Account;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;




public class ATMIndex {

    public void indexPage(){

        //定义一个集合，用于存放用户对象
        ArrayList<Account> list=new ArrayList<>();

        Scanner sc=new Scanner(System.in);



        while (true) {
            System.out.println("============欢迎您进入到邓丹的ATM系统============");
            System.out.println("1. 登录账户");
            System.out.println("2. 注册账户");
            System.out.println("请您选择操作：");
            String choose=sc.next();

            switch(choose){
                case "1":
                    login(list);
                    break;
                case "2":
                    register(list);
                    break;
                default:
                    System.out.println("没有这个选项");
                    break;
            }
        }
    }

    private void register(ArrayList list) {
        Scanner sc=new Scanner(System.in);
        System.out.println("========欢迎您进入到开户页面========");
        System.out.println("请您输入账户名称：");
        String username=sc.next();

        String password;
        String rePassword;

        while (true) {
            System.out.println("请您输入账户密码：");
            password=sc.next();
            System.out.println("请您确认输入账户密码：");
            rePassword=sc.next();
            if(password.equals(rePassword)){
                System.out.println("两次密码一致，可以使用");
                break;
            }else{
                System.out.println("两次密码不一致，请重新输入");
                continue;
            }
        }
        //输入取现额度
        double limitMoney;
        while (true) {
            System.out.println("请设置您的取现额度：");
            limitMoney = sc.nextDouble();
            if(limitMoney>=100){
                System.out.println("当前取现额度合理");
                break;
            }else{
                System.out.println("当前取现额度设置不合理，需要大于等于100");
                continue;
            }
        }

        //自动生成一个随机而且唯一的卡号
        String cardID;
        while (true) {
            cardID=getCardID();
            //判断卡号的唯一性
            System.out.println("卡号已经取到，卡号是:"+cardID);
            boolean flag=contains(list,cardID);
            if(flag){
                //
                System.out.println("当前自动生成的卡号已经存在，正在重新生成~");
                continue;
            }else{
                System.out.println("当前自动生成的卡号是唯一的，可以使用");
                break;
            }
        }

        //创建对象并添加到集合中
        double money=0.0;
        Account account=new Account(cardID,username,password,0,limitMoney);
        list.add(account);
        System.out.println("恭喜您，"+username+"先生/女士，您开户完成，您的卡号是："+cardID);
        System.out.println("当前集合中有："+list.size()+"个用户");

    }

    //定义一个方法，用于判断卡号是否已经存在
    private boolean contains(ArrayList<Account> list, String cardID) {
        for(int i=0;i<list.size();i++){
            Account account=list.get(i);
            System.out.println(account);
            if(account.getCardID().equals(cardID)){
                //找到了，返回True,表示卡号存在
                return true;
            }
        }
        //如果循环结束之后还没有找到，表示卡号不存在，返回false
        return false;
    }

    //生成一个随机的卡号
    //保证方法功能的单一性
    private String getCardID() {
        String cardID="";
        Random r=new Random();
        for(int i=0;i<8;i++){
            int num=r.nextInt(10);
            cardID=cardID+num;
        }
        System.out.println("卡号为:"+cardID);
        return cardID;
    }

    //登录
    private void login(ArrayList<Account> list){
        Scanner sc=new Scanner(System.in);
        System.out.println("=============欢迎您进入倒登录操作============");
        if(list.size()==0){
            System.out.println("当前系统无任何账户，请先注册再登录；");
            return;
        }
        //代码执行到这里，表示集合中有用户对象
        String cardID;
        while(true){
            System.out.println("请您输入登录的卡号：");
            cardID=sc.next();
            boolean flag=contains(list,cardID);
            if(flag){
                System.out.println("当前卡号是存在的，可以继续输入密码~");
                break;
            }else{
                System.out.println("卡号不存在，请确认！");
                continue;
            }
        }
        while(true) {
            //输入登录的密码
            System.out.println("请您输入登录的密码");
            String password = sc.next();
            //根据卡号，找到对应的用户对象，再从用户对象中获取密码
            Account account = findAccountByID(list, cardID);
            //通过的对象，获取正确的密码
            String rightPassword = account.getPassword();
            if (rightPassword.equals(password)) {
                System.out.println("登录成功");
                //跳转到服务的页面
                ATMSystem system=new ATMSystem();
                system.atmSystemService(account);
                //服务结束之后，就结束方法
                return;
            } else {
                System.out.println("密码输入错误，请重新输入");
                continue;
            }
        }
    }

    //通过卡号找对应的用户
    private Account findAccountByID(ArrayList<Account> list,String cardID){
        for (int i = 0; i < list.size(); i++) {
            Account account=list.get(i);
            if(account.getCardID().equals(cardID)){
                return account;
            }
        }
        return null;
    }


}
