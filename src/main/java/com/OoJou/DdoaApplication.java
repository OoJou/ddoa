package com.OoJou;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//扫描mapper，1、在此添加注释2、application.properties添加
@MapperScan(basePackages="com.OoJou.dao")
//扫描各种需要注入的组件
@ComponentScan(basePackages="com.OoJou")
public class DdoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdoaApplication.class, args);
	}

}
