package indi.lyn.codedocumentconverter.core.doc;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

@Service
public class ApiDoc {
    @Autowired
    private WebApplicationContext applicationContext;

    @GetMapping("/getParam")
    public void getParam(){
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);

        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object value = entry.getValue();
            System.out.println("拿到controller："+entry.getKey()+"，拿到value："+value);
            Class<?> aClass = AopUtils.getTargetClass(value);
            System.out.println("拿到Class:"+aClass);
            RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
            RequestMapping declaredAnnotation = aClass.getDeclaredAnnotation(RequestMapping.class);

            List<Method> methods = Arrays.asList(aClass.getMethods());
            System.out.println("Public Methods:" + methods);
            List<Method> declaredMethods = Arrays.asList(aClass.getDeclaredMethods());
            for (int i = 0; i < declaredMethods.size() ; i++) {
                GetMapping getMapping = declaredMethods.get(i).getAnnotation(GetMapping.class);
                PostMapping postMapping = declaredMethods.get(i).getDeclaredAnnotation(PostMapping.class);
                System.out.println("Get相关的："+(getMapping));
                System.out.println("Post相关的："+(postMapping));
            }
        }
    }
}
