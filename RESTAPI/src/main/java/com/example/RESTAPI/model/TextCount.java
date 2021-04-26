package com.example.RESTAPI.model;

import java.util.LinkedHashMap;

public class TextCount {

    private LinkedHashMap<String, Integer> counts = new LinkedHashMap<>();

    public TextCount(LinkedHashMap<String, Integer> counts) {
        this.counts = counts;
    }

    public LinkedHashMap<String, Integer> getCounts() {
        return counts;
    }

    public void setCounts(LinkedHashMap<String, Integer> counts) {
        this.counts = counts;
    }
}
