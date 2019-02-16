package com.OoJou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OoJou.common.ServerResponse;
import com.OoJou.dao.DictionaryMapper;
import com.OoJou.pojo.Dictionary;
import com.OoJou.service.IDictionaryServcie;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("iDictionaryService")
public class DictionaryServiceImpl implements IDictionaryServcie {

	@Autowired
	private DictionaryMapper dictionaryMapper;
	
	public ServerResponse<PageInfo> getAllDictionary(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Dictionary> dictionarieList=dictionaryMapper.selectAllDictionary();
		
		PageInfo pageResult=new PageInfo(dictionarieList);
 		return ServerResponse.createBySuccess("获取成功", pageResult);
	}

	public ServerResponse<Dictionary> createDictionary(Dictionary dictionary) {
		int resultCount=dictionaryMapper.insert(dictionary);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("创建失败");
		}
		return ServerResponse.createBySuccess("创建成功", dictionary);
	}

	public ServerResponse<Dictionary> getDictionaryDetails(int dictionaryId) {
		Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(dictionaryId);
		if (dictionary==null) {
			return ServerResponse.createByErrorMsg("字典不存在");
		}
		return ServerResponse.createBySuccess("获取字典信息成功", dictionary);
	}

	public ServerResponse<Dictionary> updateDictionary(Dictionary dictionary) {
		int resultCount=dictionaryMapper.updateByPrimaryKeySelective(dictionary);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("更新数据失败");
		}
		return ServerResponse.createBySuccess("更新数据成功", dictionary);
	}

	public ServerResponse<String> deleteDictionary(int dictionaryId) {
		int resultCount=dictionaryMapper.deleteByPrimaryKey(dictionaryId);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("删除失败");
		}
		return ServerResponse.createBySuccessMsg("删除成功");
	}

}
