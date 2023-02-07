package com.example.dictionaryapplication.model;

public class UserInput {

	private String sourceLang;
	private String targetLang;
	private String content;

	public UserInput(String sourceLang, String targetLang, String content) {
		this.sourceLang = sourceLang;
		this.targetLang = targetLang;
		this.content = content;
	}

	public UserInput() {
	}

	public String getSourceLang() {
		return sourceLang;
	}
	public void setSourceLang(String sourceLang) {
		this.sourceLang = sourceLang;
	}
	public String getTargetLang() {
		return targetLang;
	}
	public void setTargetLang(String targetLang) {
		this.targetLang = targetLang;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
