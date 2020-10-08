package com.test.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.constant.RestFulCode;
import com.test.model.suggestion.Suggestion;
import com.test.model.vo.RestFul;
import com.test.service.ISuggestionService;
import com.test.utils.CheckTool;
import com.test.utils.HttpTool;
import com.test.utils.RestFulTool;

@Controller
@RequestMapping("/suggest")
public class SuggestController {

	@Autowired
	private ISuggestionService suggestionService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestFul<String> add(HttpServletRequest request, Suggestion suggestion) {

		String token = HttpTool.getToken(request);

		//意见建议解码
		if (CheckTool.isNotNullOrEmpty(suggestion.getSuggestion())) {
			try {
				suggestion.setSuggestion(URLDecoder.decode(suggestion.getSuggestion(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		boolean tag = suggestionService.add(token, suggestion);
		return RestFulTool.getInstance().getRestFul(tag, RestFulCode.SUCCESS, RestFulCode.SUCCESS_STR);

	}
}
