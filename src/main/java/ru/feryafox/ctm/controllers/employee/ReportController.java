package ru.feryafox.ctm.controllers.employee;

import com.itextpdf.text.DocumentException;
import io.minio.errors.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.feryafox.ctm.entities.Employee;
import ru.feryafox.ctm.services.ReportService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Controller
@RequestMapping("/employee/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("")
    public String report(Model model) {
        return "employee/reports";
    }

    @GetMapping("/employees")
    @ResponseBody
    public String genEmployeesReport(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) throws ServerException, DocumentException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return reportService.createReportEmployeesExhibitions(startDate, endDate);
    }

    @GetMapping("/exhibits")
    @ResponseBody
    public String genExhibitsReport(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) throws ServerException, DocumentException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return reportService.createReportExhibitionsCuratorAndTickets(startDate, endDate);
    }
}
