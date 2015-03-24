package services;

import com.picsauditing.dao.impl.LanguageDAO;
import com.picsauditing.model.KeyValue;
import com.picsauditing.model.Language;
import com.picsauditing.model.LanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by RNatarajan on 1/7/2015.
 */
@Service
public class LanguageService {

	@Autowired
	private LanguageModel languageModel;

	@Autowired
	private LanguageDAO languageDAO;

	public Map<String, String> findAllLanguages(String env) {
		List<KeyValue<String, String>> visibleLanguagesWithoutDialect = languageModel.getVisibleLanguagesWithoutDialect(env);
		Map<String, String> idNameModels = new HashMap<>();
		for (KeyValue<String, String> keyValue : visibleLanguagesWithoutDialect) {
			idNameModels.put(keyValue.getKey(), keyValue.getValue());
		}

		return idNameModels;
	}

	public Language findLanguage(Locale locale) {
		return languageDAO.find(locale);
	}

	public List<Language> findLanguageDialects(String language, String env) {
		return languageDAO.findDialectsByLanguage(language);
	}
}
