package com.example.CoindeskAPIwork.entities;

import java.util.Map;

public class Coindesk {
    public Time time;
    public String disclaimer;
    public String chartName;
    public Map<String, Bpi> bpi;

    public static class Time {
        public String updated;
        public String updatedISO;
        public String updateduk;
    }

    public static class Bpi {
        public String code;
        public String symbol;
        public String rate;
        public String description;
        public Double rate_float;
    }
}
