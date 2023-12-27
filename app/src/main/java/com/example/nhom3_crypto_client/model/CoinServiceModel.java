package com.example.nhom3_crypto_client.model;

import java.util.ArrayList;

public class CoinServiceModel {

    public class CoinNow {
        public String id;
        public int rank;
        public String symbol;
        public String name;
        public float supply;
        public float maxSupply;
        public float marketCapUsd;
        public float volumeUsd24Hr;
        public float priceUsd;
        public float changePercent24Hr;
        public float vwap24Hr;
        public String explorer;

        public CoinNow(String id, int rank, String symbol, String name, float supply, float maxSupply, float marketCapUsd, float volumeUsd24Hr, float priceUsd, float changePercent24Hr, float vwap24Hr, String explorer) {
            this.id = id;
            this.rank = rank;
            this.symbol = symbol;
            this.name = name;
            this.supply = supply;
            this.maxSupply = maxSupply;
            this.marketCapUsd = marketCapUsd;
            this.volumeUsd24Hr = volumeUsd24Hr;
            this.priceUsd = priceUsd;
            this.changePercent24Hr = changePercent24Hr;
            this.vwap24Hr = vwap24Hr;
            this.explorer = explorer;
        }
    }
    public static class CoinsNow{
        public ArrayList<CoinNow> data;
        public Long timestamp;

        public CoinsNow(ArrayList<CoinNow> data, Long timestamp) {
            this.data = data;
            this.timestamp = timestamp;
        }
        public CoinsNow() {
            this.data = new ArrayList<>();
            this.timestamp = 0L;
        }
    }
    public static class CoinServiceListenerManager{
        private ArrayList<Listener> listeners=new ArrayList<>();

        public void addListener(ArrayList<String> coinsId, String author, EventCallbackInterface callback){
            int ind = findIndex(author);
            if(ind==-1){
                listeners.add(new Listener(coinsId,author,callback));
            }else{
                Listener temp = listeners.get(ind);
                for (int i = 0; i < coinsId.size(); i++) {
                    if(temp.coinsId.indexOf(coinsId.get(i))==-1){
                        temp.coinsId.add(coinsId.get(i));
                    }
                }
            }
        }
        public void removeListener(ArrayList<String> coinsId, String author){
            int ind = findIndex(author);
            if(ind!=-1){
                Listener temp = listeners.get(ind);
                for (int i = 0; i < coinsId.size(); i++) {
                    int coinInd = temp.coinsId.indexOf(coinsId.get(i));
                    if(coinInd!=-1){
                        temp.coinsId.remove(coinInd);
                    }
                }
                if(temp.coinsId.size()==0){
                    listeners.remove(ind);
                }
            }
        }
        public void removeListener(String author){
            int ind = findIndex(author);
            if(ind!=-1){
                listeners.remove(ind);
            }
        }

        public void handleEvent(ArrayList<CoinNow> coinNows){
            for(int i=0;i<listeners.size();i++){
                ArrayList<CoinNow> sendCoins = new ArrayList<>();
                Listener listener = listeners.get(i);
                for (int j = 0; j < listener.coinsId.size(); j++) {
                    int ind = findCoinIndex(coinNows,listener.coinsId.get(j));
                    if(ind!=-1){
                        sendCoins.add(coinNows.get(ind));
                    }
                }
                if(sendCoins.size()!=0){
                    listener.callback.handle(sendCoins);
                }
            }
        }

        private int findCoinIndex(ArrayList<CoinNow> coins, String coinId){
            for(int i=coins.size()-1;i>=0;i--){
                if(coins.get(i).id.equals(coinId)){
                    return i;
                }
            }
            return -1;
        }
        private int findIndex(String author){
            for(int i=listeners.size()-1;i>=0;i--){
                if(listeners.get(i).author.equals(author)){
                    return i;
                }
            }
            return -1;
        }
    }

    public static interface EventCallbackInterface{
        public void handle(ArrayList<CoinNow> coins);
    }

    private static class Listener{
        public ArrayList<String> coinsId;
        public String author;
        public EventCallbackInterface callback;

        public Listener(ArrayList<String> coinsId, String author, EventCallbackInterface callback) {
            this.coinsId = coinsId;
            this.author = author;
            this.callback = callback;
        }
    }

}
