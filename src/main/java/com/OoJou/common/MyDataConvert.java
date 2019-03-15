package com.OoJou.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class MyDataConvert implements Converter<String, Date> {
	private String pattern = "yyyy年MM月dd日  HH:mm:ss";

	@Override
	public Date convert(String s) {
		if ("".equals(s) || s == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
