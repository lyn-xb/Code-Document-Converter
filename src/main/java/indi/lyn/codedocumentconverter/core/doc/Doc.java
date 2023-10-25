package indi.lyn.codedocumentconverter.core.doc;

import indi.lyn.codedocumentconverter.core.reflect.ClassScannerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Slf4j
public class Doc {

    public void handleSimpleDoc(String packageName) throws Exception {
        XWPFDocument document = new XWPFDocument();

        // 1. 数据库设计
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("1. 数据库设计");
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontFamily("宋体");
        titleRun.setFontSize(14);


        List<Class<?>> classes = ClassScannerUtil.getClasses(packageName);

        int index = 1;
        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(Entity.class) || !clazz.isAnnotationPresent(Table.class)) {
                continue;
            }
            Entity entity = clazz.getAnnotation(Entity.class);
            Table table = clazz.getAnnotation(Table.class);

            // 1.1 表名
            XWPFParagraph tableTitle = document.createParagraph();
            tableTitle.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun tableTitleRun = tableTitle.createRun();
            tableTitleRun.setText("1." + index + " " + table.name() + "(" + entity.name() + ")");
            tableTitleRun.setColor("000000");
            tableTitleRun.setBold(true);
            tableTitleRun.setFontFamily("宋体");
            tableTitleRun.setFontSize(10);
            index++;

            createTable(document, clazz);
        }


        String output = "C:\\Users\\49980\\Desktop\\rest-with-spring.docx";

        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
    }

    private static void createTable(XWPFDocument document, Class<?> clazz) {
        //create table
        XWPFTable tableContext = document.createTable();
        tableContext.setWidth("100%");
        //create first row
        XWPFTableRow tableRowOne = tableContext.getRow(0);
        //tableRowOne.getCell(0).setText("属性");
        fillTableCell(tableRowOne.getCell(0),"宋体", 10,
                "000000", "属性", false);
        //tableRowOne.addNewTableCell().setText("类型");
        fillTableCell(tableRowOne.addNewTableCell(),"宋体", 10,
                "000000", "类型", false);
        //tableRowOne.addNewTableCell().setText("是否必填");
        fillTableCell(tableRowOne.addNewTableCell(),"宋体", 10,
                "000000", "是否必填", false);
        //tableRowOne.addNewTableCell().setText("主键");
        fillTableCell(tableRowOne.addNewTableCell(),"宋体", 10,
                "000000", "主键", false);
        //tableRowOne.addNewTableCell().setText("描述");
        fillTableCell(tableRowOne.addNewTableCell(),"宋体", 10,
                "000000", "描述", false);


        for (XWPFTableCell tableCell : tableRowOne.getTableCells()) {
            // 获取文本样式属性对象
            CTTcPr tcPr = tableCell.getCTTc().addNewTcPr();
            CTShd shd = tcPr.addNewShd();
            shd.setFill("8eaadb");
        }

        //create field
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Class<?> type = field.getType();
                Column column = field.getAnnotation(Column.class);
                XWPFTableRow tableRow = tableContext.createRow();
                // 1、属性
                //tableRow.getCell(0).setText(column.name());
                fillTableCell(tableRow.getCell(0),"宋体", 10,
                        "000000", column.name(), false);
                // 2、类型
                //tableRow.getCell(1).setText(javaToSqlField(type));
                fillTableCell(tableRow.getCell(1),"宋体", 10,
                        "000000", javaToSqlField(type), false);
                // 是否是主键
                if (field.isAnnotationPresent(Id.class)) {
                    // 3、是否必填
                    //tableRow.getCell(2).setText("Y");
                    fillTableCell(tableRow.getCell(2),"宋体", 10,
                            "000000", "Y", false);
                    // 4、主键
                    //tableRow.getCell(3).setText("Y");
                    fillTableCell(tableRow.getCell(3),"宋体", 10,
                            "000000", "Y", false);
                } else {
                    // 3、是否必填
                   // tableRow.getCell(2).setText(column.nullable() ? "N" : "Y");
                    fillTableCell(tableRow.getCell(2),"宋体", 10,
                            "000000", column.nullable() ? "N" : "Y", false);
                    // 4、主键
                    //tableRow.getCell(3).setText("N");
                    fillTableCell(tableRow.getCell(3),"宋体", 10,
                            "000000", "N", false);
                }
                // 5、描述
                // tableRow.getCell(4).setText("");
            }
        }
    }


    // 插入para
    private static void fillTableCell(XWPFTableCell cell, String fontFamily, int fontSize,
                                      String colorRGB, String text, boolean bold) {
        XWPFParagraph paragraph = cell.addParagraph();
        setRun(paragraph.createRun(), fontFamily, fontSize, colorRGB, text,bold);
        // 删除多余的一行
        cell.removeParagraph(0);
    }


    // 插入para
    private static void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB,
                               String text, boolean bold) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
    }


    /**
     * Java数据类型转换为MySQL数据类型
     *
     * @param javaType：Java数据类型
     * @return 对应的MySQL数据类型
     */
    // todo 对应关系需要更改
    public static String javaToSqlField(Class<?> javaType) {
        if (javaType == null) {
            return "";
        }
        if (javaType.equals(String.class)) {
            return "varchar";
        } else if (javaType.equals(byte[].class)) {
            return "blob";
        } else if (javaType.equals(Long.class) || javaType.equals(long.class) || javaType.equals(int.class) || javaType.equals(Integer.class)) {
            return "integer";
        } else if (javaType.equals(Boolean.class) || javaType.equals(boolean.class)) {
            return "bit";
        } else if (javaType.equals(float.class) || javaType.equals(Float.class)) {
            return "float";
        } else if (javaType.equals(double.class) || javaType.equals(Double.class)) {
            return "double";
        } else if (javaType.equals(BigInteger.class)) {
            return "bigint";
        } else if (javaType.equals(BigDecimal.class)) {
            return "decimal";
        } else if (javaType.equals(Date.class) || javaType.equals(LocalDate.class)) {
            return "date";
        } else if (javaType.equals(LocalDateTime.class)) {
            return "datetime";
        } else if (javaType.equals(Timestamp.class)) {
            return "timestamp";
        } else {
            System.out.println("-----------------》转化失败：未发现的类型 " + javaType);
        }
        return "";
    }


    public static void main(String[] args) throws Exception {
        new Doc().handleSimpleDoc("indi.lyn");
    }
}
