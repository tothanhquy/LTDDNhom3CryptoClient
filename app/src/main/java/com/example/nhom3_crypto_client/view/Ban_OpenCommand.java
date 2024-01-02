package com.example.nhom3_crypto_client.view;

public class Ban_OpenCommand {
    public String CoinName=" ";
    public String vaule=" ";
    public String Leverage=" ";

    public String getCoinName() {
        return CoinName;
    }

    public Ban_OpenCommand(String coinName, String vaule, String leverage) {
        CoinName = coinName;
        this.vaule = vaule;
        Leverage = leverage;
    }

    public void setCoinName(String coinName) {
        CoinName = coinName;
    }

    public String getVaule() {
        return vaule;
    }

    public void setVaule(String vaule) {
        this.vaule = vaule;
    }

    public String getLeverage() {
        return Leverage;
    }

    public void setLeverage(String leverage) {
        Leverage = leverage;
    }
}
