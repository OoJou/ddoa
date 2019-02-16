package com.OoJou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Dictionary;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

	
	/**
	 * 管理页面-获取全部字典列表
	 */
	@RequestMapping(value="get_all_dictionary.do")
	public ServerResponse<Dictionary> getAllDictionary(){
		return null;
	}
	
	/**
	 * 管理页面-新增字典
	 */
	@RequestMapping(value="create_dictionary.do")
	public ServerResponse<Dictionary> createDictionary(){
		return null;
	}
	
	/**
	 * 管理页面-修改字典
	 */
	@RequestMapping(value="update_dictionary.do")
	public ServerResponse<Dictionary> updateDictionary(){
		return null;
	}
	
	/**
	 * 管理页面-删除字典
	 */
	@RequestMapping(value="delete_dictionary.do")
	public ServerResponse<String> deleteDictionary(){
		return null;
	}
	
}
