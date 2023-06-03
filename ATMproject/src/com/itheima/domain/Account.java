package com.itheima.domain;

public class Account {
    private String cardID;
    private String username;
    private String password;
    private double money;
    private double limitMoney;

    public Account(String cardID,String username,String password,double money,double limitMoney){
        this.cardID=cardID;
        this.username=username;
        this.password=password;
        this.money=money;
        this.limitMoney=limitMoney;

    }


    public String getCardID() {
        System.out.println("cardID:"+cardID);
        return cardID;

    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public Double getMoney() {
        return this.money;
    }

    public Double getLimitMoney() {
        return this.limitMoney;
    }

    public void setMoney(double myMoney) {
        this.money=this.money+myMoney;
    }

    public void setPassword(String newPassword) {
        this.password=newPassword;
    }
}
