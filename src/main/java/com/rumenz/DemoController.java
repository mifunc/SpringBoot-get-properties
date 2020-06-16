package com.rumenz;


import com.sun.org.apache.xpath.internal.objects.XString;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@RestController
@CrossOrigin //类上加
public class DemoController {



    @Autowired
    HttpServletRequest req;

    @Autowired
    Environment  ev;
    /**
     * 通过@Value普通字符串
     */
    @Value("${server.port}")
    private String serverPort;

    /**
     * 通过@Value注解获取数组
     */
    @Value("${rumenz.com.arr}")
    private int[] arr;

    /**
     * 通过@Value注解获取List
     */
    @Value("#{'${rumenz.com.list}'.split(',')}")
    private List<String> list;


    /**
     * ConfigurationProperties
     */
    @Component
    @PropertySource("classpath:application.properties")
    @ConfigurationProperties(prefix = "rumenz.com.conf")
    class Rumenz{
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
    @Autowired
    Rumenz rumenz;


    private void getAttr() throws IOException {
        ClassPathResource cp=new ClassPathResource("application.properties");
        Properties pt=PropertiesLoaderUtils.loadProperties(cp);
        String sp=pt.getProperty("server.port");
        System.out.println("PropertiesLoaderUtils: "+cp);

    }
    @GetMapping("/")
    public String index() {
        System.out.println("Value: "+serverPort);

        System.out.println("Value arr: "+ Arrays.asList(arr));

        System.out.println("Value list: "+ list.size());

        System.out.println("Environment: "+ev.getProperty("server.port"));



        System.out.println("ConfigurationProperties rumenz: "+rumenz.getName()+"age: "+rumenz.getAge());


        return "{\"code\":200,\"msg\":\"ok\",\"data\":[\"JSON.IM\",\"json格式化\"]}";
    }
}
