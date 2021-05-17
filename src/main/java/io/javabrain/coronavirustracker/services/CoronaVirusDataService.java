package io.javabrain.coronavirustracker.services;

import io.javabrain.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    // run once a day
    // cron " second minus hour day month year"
    @Scheduled(cron = "* * 1 * * *")

    public void fetchVirusData() throws IOException, InterruptedException {

        // data can be request by thousand of people
        // "newLocationStats" created to store latest data
        // in order to they won't have to wait for long to see at "allStats"
        List<LocationStats> newLocationStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // using apache common csv library to format like excel
        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int latestDay = Integer.parseInt(record.get(record.size() - 1));
            int prevDay = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatesTotalCases(latestDay);
            locationStats.setDiffFromPreDay(latestDay-prevDay);
            newLocationStats.add(locationStats);
        }
        this.allStats = newLocationStats;
    }
}
