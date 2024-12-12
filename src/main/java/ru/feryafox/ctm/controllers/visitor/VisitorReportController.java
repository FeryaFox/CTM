package ru.feryafox.ctm.controllers.visitor;

import com.itextpdf.text.DocumentException;
import io.minio.errors.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.feryafox.ctm.services.ReportService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Controller
@RequestMapping("/visitor/reports")
public class VisitorReportController {
    private final ReportService reportService;

    public VisitorReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("")
    public String report(Model model) {
        return "visitors/reports";
    }

    @GetMapping("/tickets")
    @ResponseBody
    public String genEmployeesReport(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) throws ServerException, DocumentException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        return reportService.createVisitorReport(userDetails.getUsername(), startDate, endDate);
    }
}
