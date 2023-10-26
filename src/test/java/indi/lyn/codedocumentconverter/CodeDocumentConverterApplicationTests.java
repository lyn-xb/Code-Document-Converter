package indi.lyn.codedocumentconverter;

import indi.lyn.codedocumentconverter.core.doc.ApiDoc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SpringBootTest
class CodeDocumentConverterApplicationTests {

    @Test
    void contextLoads() {
        Map<String,String> map = new HashMap<>();
        map.forEach((key,value) -> {
            System.out.println();
        });
    }

    @Autowired
    private ApiDoc apiDoc;

    @Test
    void testApiDoc(){
        apiDoc.getParam();
    }

}
