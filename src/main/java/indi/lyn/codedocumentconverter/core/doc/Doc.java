package indi.lyn.codedocumentconverter.core.doc;

import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;

public class Doc {

    public void handleSimpleDoc() throws Exception {
        XWPFDocument document = new XWPFDocument();

        // 1. 数据库设计
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("1. 数据库设计");
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontFamily("宋体");
        titleRun.setFontSize(18);


        //

//        XWPFParagraph subTitle = document.createParagraph();
//        subTitle.setAlignment(ParagraphAlignment.CENTER);
//        XWPFRun subTitleRun = subTitle.createRun();
//        subTitleRun.setText("from HTTP fundamentals to API Mastery");
//        subTitleRun.setColor("00CC44");
//        subTitleRun.setFontFamily("Courier");
//        subTitleRun.setFontSize(16);
//        subTitleRun.setTextPosition(20);
//        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
//
//        XWPFParagraph sectionTitle = document.createParagraph();
//        XWPFRun sectionTRun = sectionTitle.createRun();
//        sectionTRun.setText("What makes a good API?");
//        sectionTRun.setColor("00CC44");
//        sectionTRun.setBold(true);
//        sectionTRun.setFontFamily("Courier");



        String output = "C:\\Users\\49980\\Desktop\\rest-with-spring.docx";

        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
    }

    public static void main(String[] args) throws Exception {
        new Doc().handleSimpleDoc();
    }
}
