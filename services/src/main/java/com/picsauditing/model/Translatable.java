package com.picsauditing.model;

public interface Translatable {
	String getI18nKey();

	String getI18nKey(String property);
}