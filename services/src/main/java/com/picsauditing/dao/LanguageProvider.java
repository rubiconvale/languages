package com.picsauditing.dao;


import com.picsauditing.model.Language;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface LanguageProvider {

	Language find(String id);

	Language find(Locale locale);

	List<Language> findByStatuses(Set<String> statuses);

	List<Language> findDialectsByLanguage(String language);
}
