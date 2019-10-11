package com.snowman.mymall.common.jpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {

	private final EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation,
					   EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryIdxParm(String namedQuery, Map<Integer, Object> params) {
		return executeQuery(true, namedQuery, (Map<T, Object>) params, -1, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryNameParm(String namedQuery, Map<String, Object> params) {
		return executeQuery(true, namedQuery, (Map<T, Object>) params, -1, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryIdxParm(String namedQuery, Map<Integer, Object> params, int pageNum, int pageSize) {
		return executeQuery(true, namedQuery, (Map<T, Object>) params, pageNum, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryNameParm(String namedQuery, Map<String, Object> params, int pageNum, int pageSize) {
		return executeQuery(true, namedQuery, (Map<T, Object>) params, pageNum, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryCustIdxParm(String jpql, Map<Integer, Object> params) {
		return executeQuery(false, jpql, (Map<T, Object>) params, -1, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryCustNameParm(String jpql, Map<String, Object> params) {
		return executeQuery(false, jpql, (Map<T, Object>) params, -1, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryCustIdxParm(String jpql, Map<Integer, Object> params, int pageNum, int pageSize) {
		return executeQuery(false, jpql, (Map<T, Object>) params, pageNum, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> exeQueryCustNameParm(String jpql, Map<String, Object> params, int pageNum, int pageSize) {
		return executeQuery(false, jpql, (Map<T, Object>) params, pageNum, pageSize);
	}


	private List<T> executeQuery(boolean isNamedQuery, String jpQuery, Map<T, Object> params, int pageNum,
			int pageSize) {
		Query query = null;
		if (isNamedQuery) {
			query = entityManager.createNamedQuery(jpQuery);
		} else {
			query = entityManager.createQuery(jpQuery);
		}
		return executeQuery(query, (Map<T, Object>) params, pageNum, pageSize);
	}

	@SuppressWarnings("unchecked")
	private List<T> executeQuery(Query query, Map<T, Object> params, int pageNum, int pageSize) {
		if (params != null) {
			String strParam = "";
			for (Entry<T, Object> entry : params.entrySet()) {
				strParam = String.valueOf(entry.getKey());
				if (entry.getKey().getClass() == String.class) {
					query.setParameter(strParam, entry.getValue());
				} else {
					query.setParameter(Integer.valueOf(strParam), entry.getValue());
				}
			}
		}
		if (pageNum > 0) {
			query.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize);
		}

		return query.getResultList();
	}

}
