package com.example.RESTAPI.controller;

import com.example.RESTAPI.dao.TextDAO;
import com.example.RESTAPI.model.TextCount;
import com.example.RESTAPI.model.TextCounts;
import com.example.RESTAPI.model.TextStringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/counter-api/")
public class TextController {

    @Autowired
    private TextDAO textDAO;


    @GetMapping(path="/top/{number}", produces = "text/csv")
    public void findTopCount(@PathVariable Integer number, HttpServletResponse response) {

        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        Map<String, Integer> listText = textDAO.findTopCount(number);


        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Text", "Count"};
            String[] nameMapping = {"text", "count"};
            csvWriter.writeHeader(csvHeader);
            listText.forEach((k, v) -> {
                try {
                    csvWriter.write(new TextCounts(k, v), nameMapping);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @PostMapping(path="/search", produces = "application/json", consumes = "application/json")
    @ResponseBody
    TextCount countWords(@RequestBody TextStringList searchText) {
        return textDAO.countWords(searchText.getSearchText());
    }


}
