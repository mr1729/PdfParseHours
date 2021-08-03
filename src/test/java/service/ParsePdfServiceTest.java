package service;

import model.InvoiceDetails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParsePdfServiceTest {

    @Mock
    PDDocument pdDocument;

    ParsePdfService parsePdfService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        parsePdfService = new ParsePdfService();
    }

    @Test
    void getInvoiceDataTest() {

        InvoiceDetails invoiceDetails;

        Map<String, Integer> map = new HashMap<>();
        System.err.println(map.getOrDefault("hello",999));
    }
}