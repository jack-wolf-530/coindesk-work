package com.example.CoindeskAPIwork.service;

import com.example.CoindeskAPIwork.entities.Coin;
import com.example.CoindeskAPIwork.entities.Coindesk;
import com.example.CoindeskAPIwork.entities.RespCoindesk;
import com.example.CoindeskAPIwork.exception.DuplicateException;
import com.example.CoindeskAPIwork.exception.NotExistException;
import com.example.CoindeskAPIwork.repository.CoinRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoindeskService {
    public static Map<String, String> currencyMap = new HashMap<>();

    @Value("${coindesk.url}")
    private String coindeskUrl;

    @Autowired
    private CoinRepository coinRepository;

    public Map<String, String> getCurrencyMap() {
        currencyMap = coinRepository.findAll()
                .stream()
                .collect( Collectors.toMap(Coin::getEnName, Coin::getChName));
        return currencyMap;
    }

    public String getOrgData() {
        String json = null;
        try {
            URL url = new URL(coindeskUrl);
            InputStreamReader isr = new InputStreamReader(url.openStream(), "utf-8");
            Scanner sc = new Scanner(isr);
            json = sc.useDelimiter("\\A").next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getNewData() {
        Coindesk coindesk = new Gson().fromJson(getOrgData(), Coindesk.class);
        RespCoindesk respCoindesk = new RespCoindesk();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy/MM/dd HH:mm:ss" );
        ZonedDateTime updateTime = OffsetDateTime.parse(coindesk.time.updatedISO)
                .atZoneSameInstant(ZoneId.systemDefault());
        respCoindesk.update =(formatter.format(updateTime));

        coindesk.bpi.forEach((k, v) -> {
            RespCoindesk.Detail detail = respCoindesk.bpi.computeIfAbsent(k, f -> new RespCoindesk.Detail());
            detail.en = v.code;
            detail.zh = getCurrencyMap().getOrDefault(v.code, v.code);
            detail.rate = v.rate;
        });
        return new Gson().toJson(respCoindesk);
    }

    public List<Coin> findAll() {
        return coinRepository.findAll();
    }

    public Coin findSingle(String enName) {
        Optional findtarg = findAll().stream()
                .filter(c -> enName.equals(c.getEnName()))
                .findFirst();
        if (findtarg.isPresent())
            return (Coin) findtarg.get();
        else
            throw new NotExistException(enName);

    }

    public Coin insert(Coin coin) {
        if (findAll().stream()
                .filter(c -> coin.getEnName().equals(c.getEnName()))
                .findFirst().isPresent())
            throw new DuplicateException(coin.getEnName());
        Coin result = coinRepository.save(coin);
        return result;
    }

    public Coin update(Coin coin) {
        List<Coin> list = findAll().stream()
                .filter(c -> c.getEnName().equals(coin.getEnName()))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            Coin target = list.get(0);
            target.setChName(coin.getChName());
            Coin result = coinRepository.save(target);
            return result;
        }
        throw new NotExistException(coin.getEnName());
    }

    public void delete(String enName) {
        Optional findtarg = findAll().stream()
                .filter(c -> enName.equals(c.getEnName()))
                .findFirst();
        coinRepository.delete((Coin) findtarg.get());
    }
}
