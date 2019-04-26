package com.OoJou.utils;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileTest {

	@Value("${img.location}")
	private String path;
	
	@Test
	public void  uploadTest() throws Exception {
        File f = new File("H:/ddoa/img/arr_left1.png");
        FileCopyUtils.copy(f, new File(path+"/1.jpg"));
    }
}
