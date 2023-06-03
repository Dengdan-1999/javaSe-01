package com.itheima.atmsystem;

import com.itheima.domain.Account;

import java.util.ArrayList;
import java.util.Scanner;

public class ATMSystem {
    public void atmSystemService(Account account){
        Scanner sc=new Scanner(System.in);
        ArrayList<Account> list=new ArrayList<>();

        while(true){
            System.out.println("============欢迎您进入到操作界面============");
            System.out.println("1. 查询");
            System.out.println("2. 存款");
            System.out.println("3. 取款");
            System.out.println("4. 转账");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出");
            System.out.println("7. 注销账户");
            System.out.println("请您输入操作命令");
            String choose=sc.next();
            switch(choose){
                case "1":
                    System.out.println("用户选择了查询操作");
                    queryAccount(account);

                    break;
                case "2":
                    System.out.println("用户选择了存款操作");
                    depositAccount(account);
                    break;
                case "3":
                    System.out.println("用户选择了取款操作");
                    acquireAccount(account);
                    break;
                case "4":
                    transferAccount(list,account);
                    System.out.println("用户选择了转账操作");
                    break;
                case "5":
                    boolean flag=updatePassword(account);
                    if(flag){
                        System.out.println("表示密码修改成功");
                        //结束当前服务，回到登录页面
                        return;
                    }else{
                        System.out.println("密码修改失败");
                        break;
                    }
                case "6":
                    boolean flag2=deleteAccount(list,account);
                    if(flag2){
                        //表示注销成功
                        return;
                    }else{
                        //表示注销失败
                        break;
                    }
                case "7":
                    System.out.println("用户选择了注销账户操作");
                    break;
                default:
                    System.out.println("输入不合法");
                    break;

            }
        }
    }

    //注销账户的业务逻辑
    private boolean deleteAccount(ArrayList<Account> list, Account account) {
        //获取当前账户的余额
        double myMoney=account.getMoney();
        //判断当前账户余额是否为0
        if(myMoney==0){
            //为0即可注销，注销后退出，回到登录页面
            list.remove(account);
            System.out.println("");
            return true;
        }else{
            //不为0无法注销，回到继续操作账户界面
            System.out.println("当前账户还有余额，请先取款，再注销");
            return false;
        }
    }

    //修改密码的业务逻辑
    private boolean updatePassword(Account account) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请您输入当前密码认证：");
        String passwordInput = sc.next();
        //验证
        if (passwordInput.equals(account.getPassword())) {
            System.out.println("密码验证成功");
        } else {
            System.out.println("密码验证失败");
            return false;
        }
        //输入新密码
        String newPassword;
        while (true) {
            System.out.println("请您输入新密码：");
            newPassword = sc.next();
            System.out.println("请您确认新密码：");
            String reNewPassword = sc.next();
            if (newPassword.equals(reNewPassword)) {
                System.out.println("两次密码输入一致");
                break;
            } else {
                System.out.println("两次密码输入不一致");
                continue;
            }
        }
        //修改密码
        account.setPassword(newPassword);
        System.out.println("密码已经修改成功");
        return true;

    }

    //转账的业务逻辑
    //形参1：list表示存储所有用户的集合
    //形参2：当前登录的账户
    private void transferAccount(ArrayList<Account> list, Account account) {
        Scanner sc=new Scanner(System.in);
        System.out.println("============欢迎进入账户转账操作============");
        Account otherAccount;
        while(true){
            System.out.println("请输入对方卡号：");
            String otherCardID=sc.next();
            //异常校验：判断对方的账户是否存在
            otherAccount=findAccountByID(list,otherCardID);
            if(otherAccount==null){
                System.out.println("对方的账户不存在，请重新输入卡号");
                continue;
            }
            //异常校验:当前账户不能是自己
            if(otherAccount==account){
                System.out.println("转账的账户不能是自己");
                continue;
            }else{
                break;
            }
        }
        //当代码进行到此处，表示对方的账户是存在的，而且不是自己
        String otherUsername= otherAccount.getUsername();
        String otherName=otherUsername.substring(1);
        System.out.println("请您输入对方[*"+otherName+"的姓氏,并进行确认！");
        String familyNameInput=sc.next();
        //根据输入的姓氏与名字拼接
        String otherUsernameInput=familyNameInput+otherName;
        //比较该名字与账户对应的用户名是否一样
        if(otherUsernameInput.equals(otherUsername)){
            System.out.println("对方姓氏输入正确");
        }else{
            System.out.println("对方的姓氏输入错误");
            //当对方的姓氏输入错误，这是一个严重的问题，不应该重试，此时直接停止当前的转账操作
            return;
        }

        //输入转账金额
        double transferMoney;
        while(true){
            System.out.println("请您输入转账的金额(您最多可以转账："+account.getMoney()+"元):");
            transferMoney=sc.nextDouble();
            if(transferMoney<=account.getMoney()){
                System.out.println("当前余额充足");
                break;
            }else{
                System.out.println("余额不足，请减少转账的金额");
                continue;
            }
        }
        //真正的转账逻辑
        //自己的账户减去钱
        double myMoney=account.getMoney()-transferMoney;
        account.setMoney(myMoney);
        //对方的账户加上钱
        double otherMoney=otherAccount.getMoney()+transferMoney;
        otherAccount.setMoney(otherMoney);
        //提示
        System.out.println("您已经完成转账");
        //查询自己的账户
        queryAccount(account);

    }

    //查询对应卡号的账户是否存在
    private Account findAccountByID(ArrayList<Account> list,String cardID) {
        for(int i=0;i<list.size();i++){
           Account account=list.get(i);
           if(account.getCardID().equals(cardID)){
               return account;
           }
        }
        return null;
    }

    //取款的业务逻辑
    private void acquireAccount(Account account) {
        Scanner sc=new Scanner(System.in);
        System.out.println("============欢迎进入账户取款操作============");
        double acquireMoney;
        while(true){
            System.out.println("请输入取款的金额：");
            acquireMoney=sc.nextDouble();
            //异常校验：取款金额要是正数
            if(acquireMoney<0){
                System.out.println("当前取款金额是负数，请您输入一个大于0的整数：");
                continue;
            }
            //异常校验：取款金额要小于等于每次取现额度
            if(acquireMoney>account.getLimitMoney()){
                System.out.println("您输入的金额超出单次取现额度，请您核对后再次输入");
                continue;
            }
            //异常校验，得是100的整数倍
            if(acquireMoney%100!=0){
                System.out.println("您输入的金额不是100的整数倍，请核对后再次输入");
                continue;
            }
            //异常校验：取现金额不能超过账户余额
            if(acquireMoney>account.getMoney()){
                System.out.println("您输入的取现金额超过了您的账户余额，请您核对后再次输入：");
                continue;
            }else{
                System.out.println("当前的余额是足够的");
                break;
            }
        }
        //从账户余额中减去取现金额
        double money=account.getMoney()-acquireMoney;
        account.setMoney(money);
        //取款完成后，再次查看账户余额
        queryAccount(account);

    }

    private void depositAccount(Account account) {
        Scanner sc=new Scanner(System.in);
        System.out.println("============欢迎进入账户存款操作============");
        double depositMoney;
        while(true){
            System.out.println("请输入存款金额:");
            depositMoney=sc.nextDouble();
            //先验证异常数据，剩下来的就是正确的数据
            if(depositMoney<0){
                System.out.println("存款的金额要大于等于0");
                continue;
            }else{
                System.out.println("当前存款的金额是大于等于0的");
            }

            //继续校验是100的整数倍
            if(depositMoney%100==0){
                System.out.println("存款的金额是100的整数倍，可以使用");
                break;
            }else{
                System.out.println("抱歉，当前的ATM系统，只能存100的整数倍");
                continue;
            }
        }

        //当循环结束之后，表示存款的金额是可以使用的
        //把存款的金额，加到当前的账户中
        double myMoney=account.getMoney();
        myMoney+=depositMoney;
        account.setMoney(myMoney);
        //存款完毕后，再次查询一下余额
        queryAccount(account);
    }

    private void queryAccount(Account account) {
        System.out.println("============您当前账户详情信息如下============");
        System.out.println("卡号："+account.getCardID());
        System.out.println("户主："+account.getUsername());
        System.out.println("余额："+account.getMoney());
        System.out.println("单次取现额度："+account.getLimitMoney());
    }
}
