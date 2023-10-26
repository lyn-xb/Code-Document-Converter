package indi.lyn.codedocumentconverter;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test1")
public class Controller {
    @PutMapping("/put1")
    public void testPut(){

    }
}
