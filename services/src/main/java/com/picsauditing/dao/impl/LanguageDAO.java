package com.picsauditing.dao.impl;

import com.picsauditing.dao.LanguageProvider;
import com.picsauditing.model.Language;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Repository
public class LanguageDAO implements LanguageProvider {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Language find(String id) {
		return em.find(Language.class, id);
	}

	@Override
	public Language find(Locale locale) {
		return em.find(Language.class, locale);
	}

	@Override
	public List<Language> findByStatuses(Set<String> statuses) {
		TypedQuery<Language> query = em.createQuery("SELECT l FROM Language l " +
				"WHERE l.status IN (:statuses)", Language.class);

		query.setParameter("statuses", statuses);

		return query.getResultList();
	}

	@Override
	public List<Language> findDialectsByLanguage(String language) {
		TypedQuery<Language> query = em.createQuery("SELECT l FROM Language l WHERE l.language = :language",
				Language.class);

		query.setParameter("language", language);

		return query.getResultList();
	}
}
