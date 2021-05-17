package io.javabrain.coronavirustracker.Controllers;

import io.javabrain.coronavirustracker.models.LocationStats;
import io.javabrain.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String Home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatesTotalCases()).sum();
        int totalReportPreCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreDay()).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalReportPreCases", totalReportPreCases);
        return "home";
    }
}
