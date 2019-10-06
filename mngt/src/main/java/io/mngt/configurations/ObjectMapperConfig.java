package io.mngt.configurations;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

  @Autowired
  private ConfigurableApplicationContext context;

  @PostConstruct
  public void init(){
    BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ObjectMapper.class);
    builder.addConstructorArgValue(new JsonFactory());
    DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();
    factory.registerBeanDefinition("json", builder.getBeanDefinition());
    Map<String, ObjectMapper> beans = context.getBeansOfType(ObjectMapper.class);
    beans.entrySet().forEach(System.out::println);
  }
  
}