package com.picsauditing.model;

import com.picsauditing.loader.AvailableLanguagesLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Glossary:
 * <p/>
 * "Language Code" = ISO 639-1 Code is the 2-letter language code (e.g. en =
 * English, fr = French).
 * <p/>
 * "Country Code" = ISO 3166-1 alpha-2 is the 2-letter country code (e.g. US =
 * United States, GB = Great Britain).
 * <p/>
 * "Language Tag" = Internet Engineering Task Force (IETF) language tag = An
 * IETF best practice, currently specified by RFC 5646 and RFC 4647, for
 * language tags easy to parse by computer. The tag system is extensible to
 * region, dialect, and private designations. (e.g. en_US is the US dialect of
 * English, and en_GB is the British dialect). A valid language tag can be
 * either just a 2-letter Language Code, or a 5-character LanguageCode + "_" +
 * CountryCode
 * <p/>
 * "Locale" = the Java Locale object that corresponds to a language tag
 * <p/>
 * "Languages" = is how we'll refer to a list of languages that distinguishes
 * between the various dialects. For English, that would be "en_US", "en_GB",
 * etc. ("en" alone is specifically excluded) For Finnish, it would be just
 * "fi", which has no dialects. (This is the default behavior, unless we
 * specifically use one of the terms below.)
 * <p/>
 * "Language Sans Dialect" = is how we'll refer to a language without concern
 * for any particular dialect (either because we are unconcerned about the
 * dialect differences, or because there are no dialect differences) e.g. "en"
 * to refer to all dialects of English, and "fi" for Finnish, which has no
 * dialects.
 * <p/>
 * "Unified Language List" = is how we'll refer to a merged list of the previous
 * 2.
 */
@Component
public class LanguageModel {

	@Autowired
	private AvailableLanguagesLoader availableLanguagesLoader;

	private Map<String, Set<Language>> availableLanguagesPerEnvironment;

	@PostConstruct
	public void init() {
		availableLanguagesPerEnvironment = Collections.unmodifiableMap(availableLanguagesLoader.loadAvailableLanguages());
	}

	public List<KeyValue<String, String>> getVisibleLanguagesWithoutDialect(String env) {
		List<String> supportedLanguageKeys = extractLanguageIsoCodesFrom(getVisibleLanguages(env));
		setFirstLanguageAsEnglish(supportedLanguageKeys);

		return languageAndDisplayAsKeyValues(supportedLanguageKeys);
	}

	/**
	 * A list of all languages that are stable, along with all of their variant
	 * dialects. Note: The language code alone will not appear in the list
	 * unless the language has no dialects at all. Note also that stability is a
	 * factor of the language alone. The dialects always follow the language
	 * itself af far as being stable is concerned.
	 */
	public Set<Language> getVisibleLanguages(String env) {
		if (availableLanguagesPerEnvironment.containsKey(env)) {
			return availableLanguagesPerEnvironment.get(env);
		}

		return Collections.emptySet();
	}

	private List<String> extractLanguageIsoCodesFrom(Collection<Language> visibleLanguages) {
		List<String> supportedLanguageKeys = new ArrayList<>();

		for (Language language : visibleLanguages) {
			if (!supportedLanguageKeys.contains(language.getLanguage())) {
				supportedLanguageKeys.add(language.getLanguage());
			}
		}

		return supportedLanguageKeys;
	}

	private void setFirstLanguageAsEnglish(List<String> supportedLanguageKeys) {
		// TODO: Should English should be listed first? The assumption is yes
		if (supportedLanguageKeys.contains("en") && supportedLanguageKeys.indexOf("en") > 0) {
			supportedLanguageKeys.remove("en");
			supportedLanguageKeys.add(0, "en");
		}
	}

	private List<KeyValue<String, String>> languageAndDisplayAsKeyValues(List<String> supportedLanguageKeys) {
		List<KeyValue<String, String>> supportedLanguages = new ArrayList<>();

		for (String languageKey : supportedLanguageKeys) {
			Locale locale = new Locale(languageKey);
			String displayLanguage = StringUtils.capitalize(locale.getDisplayLanguage(locale));

			supportedLanguages.add(new KeyValue<>(languageKey, displayLanguage));
		}

		return supportedLanguages;
	}
}
