package com.example.CoindeskAPIwork.entities;

import java.util.HashMap;
import java.util.Map;

public class RespCoindesk {
    public String update;
    public Map<String, Detail> bpi = new HashMap<>();

    public static class Detail {
        public String en;
        public String zh;
        public String rate;
    }
}
