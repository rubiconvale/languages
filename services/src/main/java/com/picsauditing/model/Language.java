package com.picsauditing.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Locale;

@Entity
@Table(name = "app_language")
public class Language implements Comparable<Language>, Translatable {

	@Id
	private Locale locale;
	private String language;
	private String country;
	private String status;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	@Transient
	public String getI18nKey() {
		return this.getClass().getSimpleName() + "." + this.locale;
	}

	@Override
	@Transient
	public String getI18nKey(String property) {
		return getI18nKey() + "." + property;
	}

	@Override
	public int compareTo(Language otherLocale) {
		if (locale != null && otherLocale.getLocale() != null) {
			return this.locale.toString().compareTo(otherLocale.toString());
		}

		return this.language.compareTo(otherLocale.getLanguage());
	}

	@Override
	public String toString() {
		return this.locale.toString();
	}
}
