package mum.edu;

import mum.edu.controller.PrincipalController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
public class FinalApplication {
    public static void main(String[] args) {
        File fil=new File(PrincipalController.rootDirectory);
        if(!fil.exists())
            fil.mkdir();

        SpringApplication.run(FinalApplication.class, args);
    }

}
