package mum.edu.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("mum.edu.serviceImpl")
@EnableAspectJAutoProxy
public class Config {
}
