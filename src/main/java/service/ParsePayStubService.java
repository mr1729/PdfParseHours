package service;

import model.PayStubDetails;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static service.Constants.*;

public class ParsePayStubService {

    ParsePdfService parsePdfService;

    public ParsePayStubService(ParsePdfService parsePdfService) {
        this.parsePdfService = parsePdfService;
    }

    private List<String> getPdfFileLists() {
        File payStubFolder = new File(PAYSTUB_FOLDER);
        List<File> paystubFile = Arrays.asList(payStubFolder.listFiles(File::isFile));
        return paystubFile.stream().map(File::getAbsolutePath).collect(Collectors.toList());
    }

    private String parseFile(String filePath) {
        return parsePdfService.parseEncryptedPdf(filePath, ENC_PAYSTUB_PASSPHRASE);
    }

    private PayStubDetails patternCompiledData(String fileData) {

        Pattern pattern = Pattern.compile("(Salary)\\s(\\d{2}):\\d{2}\\s([\\d,\\,,\\.]{8})\\s([\\d,\\,,\\.]{8})");
        PayStubDetails payStubDetails = new PayStubDetails();
        Matcher matcher = pattern.matcher(fileData);
        if (matcher.find()) {
            payStubDetails.setHours(Integer.parseInt(matcher.group(2)));
            payStubDetails.setSalary(Double.valueOf(matcher.group(3).replaceAll(",", "")));
        }
        pattern = Pattern.compile("(Net Pay)\\s([\\d,\\,,\\.]{8})\\s.*");
        matcher = pattern.matcher(fileData);
        if (matcher.find()) {
            payStubDetails.setNetPay(Double.valueOf(matcher.group(2).replaceAll(",", "")));
        }
        pattern = Pattern.compile("(Pay Period:)\\s(\\d{2}\\/\\d{2}\\/\\d{4})\\s-\\s(\\d{2}\\/\\d{2}\\/\\d{4})\\sPay\\sDate:\\s(\\d{2}\\/\\d{2}\\/\\d{4})");
        matcher = pattern.matcher(fileData);
        if (matcher.find()) {
            payStubDetails.setFromPeriod(LocalDate.parse(matcher.group(2), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            payStubDetails.setToPeriod(LocalDate.parse(matcher.group(3), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            payStubDetails.setPayDate(LocalDate.parse(matcher.group(4), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }
        return payStubDetails;
    }

    public List<PayStubDetails> getPayStubData() {
        List<String> payStubFiles = getPdfFileLists();
        return payStubFiles.stream().map(this::parseFile).map(this::patternCompiledData).collect(Collectors.toList());
    }


    public void process() {
        getPayStubData().stream().sorted(
                Comparator.comparing(PayStubDetails::getFromPeriod)
        ).forEach(payStub -> {

            System.out.println(
                    PURPLE + payStub.getFromPeriod() + COLOR_RESET + " to " + PURPLE + payStub.getToPeriod() + COLOR_RESET +
                            " -----> " + YELLOW + payStub.getSalary() + COLOR_RESET
            );
        });
    }

}
