package com.mihani.rest;

import com.mihani.Exceptions.BricoleurAlreadyExistsException;
import com.mihani.Exceptions.BricoleurNotFoundException;
import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;
import com.mihani.entities.Report;
import com.mihani.dtos.ReportModel;
import com.mihani.services.BricoleurServiceImpl;
import com.mihani.services.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
