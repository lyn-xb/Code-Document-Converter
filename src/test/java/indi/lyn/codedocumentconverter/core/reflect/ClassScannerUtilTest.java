package indi.lyn.codedocumentconverter.core.reflect;

import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassScannerUtilTest {

    @Test
    void getClasses() {
        String packageName = "indi.lyn"; // 指定你的包名

        List<Class<?>> classes = ClassScannerUtil.getClasses(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Entity.class)) {
                System.out.println(clazz.getName());
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if(field.isAnnotationPresent(Column.class)){
                        Class<?> type = field.getType();
                        System.out.println(type.getName());
                        Column column = field.getAnnotation(Column.class);
                        System.out.println(column.name());
                    }
                }
            }
        }

    }
}