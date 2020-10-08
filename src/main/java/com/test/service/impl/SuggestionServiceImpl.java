package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.ISuggestionDao;
import com.test.model.suggestion.Suggestion;
import com.test.model.user.User;
import com.test.service.ISuggestionService;
import com.test.service.IUserService;
import com.test.utils.CheckTool;

@Service("suggestionService")
public class SuggestionServiceImpl implements ISuggestionService {

	@Autowired
	private IUserService userService;
	@Autowired
	private ISuggestionDao suggestionDao;

	@Override
	public boolean add(String token, Suggestion suggestion) {

		User userMongo = userService.getUserByToken(token);
		if (CheckTool.isNullOrEmpty(userMongo)) {
			//suggestion.setName("匿名的建议");

		} else {

			suggestion.setUserId(userMongo.getId());
			//suggestion.setName(userMongo.getName() + " 的建议");

		}

		suggestion.setState(true);
		suggestionDao.save(suggestion);

		return true;
	}
}
