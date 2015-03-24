package com.picsauditing.model;

public enum LanguageStatus implements Translatable {

	Future, Alpha, Beta, Stable;

	@Override
	public String getI18nKey() {
		return this.getClass().getSimpleName() + "." + this.name();
	}

	@Override
	public String getI18nKey(String property) {
		return getI18nKey() + "." + property;
	}
}
