package ru.feryafox.ctm.services;



import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.feryafox.ctm.components.HashingService;

import ru.feryafox.ctm.projections.VisitorReportProjection;
import ru.feryafox.ctm.repositories.EmployeeRepository;
import ru.feryafox.ctm.repositories.ExhibitionRepository;
import ru.feryafox.ctm.repositories.VisitorRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ReportService {
    private static final Font font;
    private static final Font fontHeader;
    private static final Font fontBase;

    static {
        FontFactory.register("font/font.ttf", "TimesNewRoman");
        fontBase = FontFactory.getFont("TimesNewRoman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16);
        fontHeader = FontFactory.getFont("TimesNewRoman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16, Font.BOLD, BaseColor.BLACK);
        font= FontFactory.getFont("TimesNewRoman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16, Font.BOLD, BaseColor.BLACK);
    }

    private final ExhibitionRepository exhibitionRepository;
    private final MinioService minioService;
    private final HashingService hashingService;
    private final EmployeeRepository employeeRepository;
    private final VisitorRepository visitorRepository;


    public ReportService(ExhibitionRepository exhibitionRepository, MinioService minioService, HashingService hashingService, HashingService hashingService1, EmployeeRepository employeeRepository, VisitorRepository visitorRepository) {
        this.exhibitionRepository = exhibitionRepository;
        this.minioService = minioService;
        this.hashingService = hashingService1;
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
    }

    public String createReportExhibitionsCuratorAndTickets(LocalDate startDate, LocalDate endDate) throws DocumentException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        var report = exhibitionRepository.getReportExhibitionsCuratorAndTickets(startDate, endDate);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);

        document.open();

        fillReportHeader(document, "Отчет по выставкам, их кураторам и количеству проданных билетов", startDate, endDate);

        for (var i : report) {

            Paragraph header = new Paragraph(i.getName(), fontHeader);
            document.add(header);

            Paragraph exhibitionId = new Paragraph(String.format("Номер выставки: %s", i.getExhibitionId()), fontBase);
            exhibitionId.setIndentationLeft(50);
            document.add(exhibitionId);

            Paragraph theme = new Paragraph(String.format("Тема: «%s»", i.getTheme()), fontBase);
            theme.setIndentationLeft(50);
            document.add(theme);

            Paragraph ticketsCount = new Paragraph(String.format("Количество проданных билетов: %s", i.getTicketsCount()), fontBase);
            ticketsCount.setIndentationLeft(50);
            document.add(ticketsCount);

            Paragraph ticketsPrice = new Paragraph(String.format("Общая выручка: %s рублей", i.getTicketsPrice()), fontBase);
            ticketsPrice.setIndentationLeft(50);
            document.add(ticketsPrice);

            Paragraph curatorCount = new Paragraph(String.format("Количество кураторов: %s", i.getCuratorCount()), fontBase);
            curatorCount.setIndentationLeft(50);
            document.add(curatorCount);

            Paragraph exhibitsCount = new Paragraph(String.format("Количество экспонатов: %s", i.getExhibitsCount()), fontBase);
            exhibitsCount.setIndentationLeft(50);
            document.add(exhibitsCount);

            Paragraph emptyLine = new Paragraph(" ");
            emptyLine.setIndentationLeft(50);
            document.add(emptyLine);
        }


        document.close();

        return minioService.saveReport(hashingService.hashString("exhibitions_curator_tickets_report_" + LocalDateTime.now()) + ".pdf", pdfOutputStream.toByteArray());
    }

    public String createReportEmployeesExhibitions(LocalDate startDate, LocalDate endDate) throws DocumentException, NoSuchAlgorithmException, ServerException, InsufficientDataException, ErrorResponseException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        var report = employeeRepository.getReportEmployeesExhibitions(startDate, endDate);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);

        document.open();

        fillReportHeader(document, "Отчет по сотрудникам музея, их участию в выставках и курации экспонатов", startDate, endDate);




        for (var i : report) {

            Paragraph header = new Paragraph(i.getFullName(), fontHeader);
            document.add(header);

            Paragraph exhibitionId = new Paragraph(String.format("Номер сотрудника: %s", i.getEmployeeId()), fontBase);
            exhibitionId.setIndentationLeft(50);
            document.add(exhibitionId);

            Paragraph position = new Paragraph(String.format("Должность: %s", i.getPosition()), fontBase);
            position.setIndentationLeft(50);
            document.add(position);

            Paragraph curatorExhibitionCount = new Paragraph(String.format("Количество курируемых выставок: %s", i.getExhibitionCount()), fontBase);
            curatorExhibitionCount.setIndentationLeft(50);
            document.add(curatorExhibitionCount);

            Paragraph curatorExhibitsCount = new Paragraph(String.format("Количество курируемых выставок: %s", i.getExhibitCount()), fontBase);
            curatorExhibitsCount.setIndentationLeft(50);
            document.add(curatorExhibitsCount);

            Paragraph workExperience = new Paragraph(String.format("Стаж работы: %s", i.getWorkExperience()), fontBase);
            workExperience.setIndentationLeft(50);
            document.add(workExperience);

            Paragraph emptyLine = new Paragraph(" ");
            emptyLine.setIndentationLeft(50);
            document.add(emptyLine);
        }


        document.close();

        return minioService.saveReport(hashingService.hashString("employees_exhibitions_report_" + LocalDateTime.now()) + ".pdf", pdfOutputStream.toByteArray());
    }

    public String createVisitorReport(String contactPhone, LocalDate startDate, LocalDate endDate)
            throws DocumentException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            ServerException, InsufficientDataException, ErrorResponseException, InvalidResponseException,
            XmlParserException, InternalException {

        var report = visitorRepository.getVisitorReportByVisitorId(startDate, endDate, contactPhone);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);

        document.open();

        fillReportHeader(document, "Отчет по посетителю, приобретенным билетам и выставкам", startDate, endDate);

        Long lastVisitorId = null;

        if (!report.isEmpty()) {
            var visitor = report.get(0);

            Paragraph header = new Paragraph(visitor.getVisitorFullName(), fontBase);
            document.add(header);

            Paragraph visitorInfo = new Paragraph(String.format("Номер посетителя: %s", visitor.getVisitorId()), fontBase);
            visitorInfo.setIndentationLeft(50);
            document.add(visitorInfo);

            Paragraph contactPhoneP = new Paragraph(String.format("Контактный телефон: %s", visitor.getContactPhone()), fontBase);
            contactPhoneP.setIndentationLeft(50);
            document.add(contactPhoneP);

//            Paragraph totalVisits = new Paragraph(String.format("Общее количество посещений: %s", visitor.getTotalVisits()), fontBase);
//            totalVisits.setIndentationLeft(50);
//            document.add(totalVisits);


            Paragraph ticketInfo = new Paragraph("Информация о билетах:", fontHeader);
            ticketInfo.setIndentationLeft(50);
            document.add(ticketInfo);

            Paragraph emptyLine = new Paragraph(" ");
            emptyLine.setIndentationLeft(50);
            document.add(emptyLine);

            for (var ticket : report) {


                Paragraph ticketId = new Paragraph(String.format("Номер билета: %s", ticket.getTicketId()), fontBase);
                ticketId.setIndentationLeft(50);
                document.add(ticketId);

                Paragraph exhibitionInfo = new Paragraph(String.format("Выставка: %s", ticket.getExhibitionName()), fontBase);
                exhibitionInfo.setIndentationLeft(50);
                document.add(exhibitionInfo);

                Paragraph exhibitionDate = new Paragraph(String.format("Дата посещения: %s", ticket.getExhibitionVisitDate()), fontBase);
                exhibitionDate.setIndentationLeft(50);
                document.add(exhibitionDate);

                Paragraph ticketPrice = new Paragraph(String.format("Стоимость билета: %s", ticket.getTicketPrice()), fontBase);
                ticketPrice.setIndentationLeft(50);
                document.add(ticketPrice);

                Paragraph emptyLineBetweenTickets = new Paragraph(" ");
                emptyLineBetweenTickets.setIndentationLeft(50);
                document.add(emptyLineBetweenTickets);
            }
        }

        document.close();

        return minioService.saveReport(hashingService.hashString("visitor_report_" + LocalDateTime.now()) + ".pdf", pdfOutputStream.toByteArray());
    }




    private void fillReportHeader(Document document, String header, LocalDate startDate, LocalDate endDate) throws DocumentException {
        Paragraph title = new Paragraph(header, font);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        document.add(new Paragraph(" "));

        Paragraph createDate = new Paragraph(String.format("Дата составления: %s", LocalDate.now()), fontBase);

        document.add(createDate);

        Paragraph reportPeriod = new Paragraph(String.format("Период: %s - %s", startDate, endDate), fontBase);

        document.add(reportPeriod);
    }

}
