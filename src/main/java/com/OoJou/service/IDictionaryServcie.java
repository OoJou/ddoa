package com.OoJou.service;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Dictionary;
import com.github.pagehelper.PageInfo;

public interface IDictionaryServcie {

	//管理页面-增删查改
	ServerResponse<PageInfo> getAllDictionary(int pageNum,int pageSize);
	ServerResponse<Dictionary> createDictionary(Dictionary dictionary);
	ServerResponse<Dictionary> getDictionaryDetails(int dictionaryId);
	ServerResponse<Dictionary> updateDictionary(Dictionary dictionary);
	ServerResponse<String> deleteDictionary(int dictionaryId);
	
}
