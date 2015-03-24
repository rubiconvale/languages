package com.picsauditing.loader;

import com.picsauditing.dao.LanguageProvider;
import com.picsauditing.model.Language;
import com.picsauditing.model.LanguageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AvailableLanguagesLoader {

	public static final Locale ENGLISH = Locale.US;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LanguageProvider languageProvider;

	public Map<String, Set<Language>> loadAvailableLanguages() {
		final Set<Language> betaAndStableLanguages = getVisibleLanguages(false);
		final Set<Language> alphaLanguages = getVisibleLanguages(true);

		return new HashMap<String, Set<Language>>() {{

			put("alpha", alphaLanguages);
			put("qa", betaAndStableLanguages);
			put("beta", betaAndStableLanguages);
			put("stable", betaAndStableLanguages);
			put("localhost", betaAndStableLanguages);
			put("config", betaAndStableLanguages);

		}};
	}

	private Set<Language> getVisibleLanguages(boolean includeAlphaLanguages) {
		// Always add stable and beta languages
		Set<String> statuses = new HashSet<>(Arrays.asList(LanguageStatus.Stable.name(), LanguageStatus.Beta.name()));

		if (includeAlphaLanguages) {
			statuses.add(LanguageStatus.Alpha.name());
		}

		List<Language> stableAndBetaLanguages = null;
		try {
			stableAndBetaLanguages = languageProvider.findByStatuses(statuses);

			logger.debug("Languages found by Language Provider = {}", stableAndBetaLanguages);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Set<Language> visibleLanguages = new HashSet<>();
		if (stableAndBetaLanguages != null) {
			visibleLanguages.addAll(stableAndBetaLanguages);
		}

		if (visibleLanguages.isEmpty()) {
			addEnglishAsVisibleLanguage(visibleLanguages);
		}

		return Collections.unmodifiableSet(visibleLanguages);
	}

	private void addEnglishAsVisibleLanguage(Set<Language> visibleLanguages) {
		Language english = new Language();
		english.setLocale(ENGLISH);
		english.setLanguage(ENGLISH.getLanguage());
		english.setCountry(ENGLISH.getCountry());

		visibleLanguages.add(english);
	}
}
