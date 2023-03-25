package com.mihani.rest;

import com.mihani.entities.Report;
import com.mihani.models.ReportModel;
import com.mihani.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/reports")
    public Report addReport(@RequestBody ReportModel model) throws Exception {
        Report report = Report.builder()
                .dateReport(model.getDateReport())
                .reason(model.getReason())
                .description(model.getDescription())
                .build();
        return reportService.addReport(model.getIdAnnouncement(), report);
    }

}
