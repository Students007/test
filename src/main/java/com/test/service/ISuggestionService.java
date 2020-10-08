package com.test.service;

import com.test.model.suggestion.Suggestion;

public interface ISuggestionService {

	boolean add(String token, Suggestion suggestion);

}
