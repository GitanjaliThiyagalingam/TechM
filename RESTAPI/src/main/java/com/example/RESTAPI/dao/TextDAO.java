package com.example.RESTAPI.dao;

import com.example.RESTAPI.model.TextCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Repository
public class TextDAO {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static Map<String, Integer> textCount = new HashMap<>();

    private static LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

    public TextCount countWords(List<String> textList) {

        String result = readFile().toLowerCase();
        sortedMap = new LinkedHashMap<>();

        for(String text : textList) {
            int count = result.split(text.toLowerCase(), -1).length - 1;
            sortedMap.put(text, count);
            LOGGER.info(text + " " + String.valueOf(count));
        }

        return new TextCount(sortedMap);
    }

    public Map<String, Integer> findTopCount(Integer number) {

        String result = readFile().toLowerCase();
        sortedMap = new LinkedHashMap<>();

        String[] splittedString = result.split(" ");

        for(String text : splittedString) {
            int count = result.split(text.toLowerCase(), -1).length - 1;
            textCount.put(text, count);
            //LOGGER.info(text + " " + String.valueOf(count));
        }
        textCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(number)
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    public String readFile() {


        Resource resource = new ClassPathResource("Text.txt");
        StringBuilder resultStringBuilder = new StringBuilder();

        try {
            InputStream inputStream = resource.getInputStream();
            try (BufferedReader br
                         = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
            //LOGGER.info(resultStringBuilder.toString());
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }

        return resultStringBuilder.toString();
    }


}


