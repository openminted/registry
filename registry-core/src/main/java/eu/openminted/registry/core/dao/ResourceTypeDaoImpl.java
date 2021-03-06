package eu.openminted.registry.core.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import eu.openminted.registry.core.domain.ResourceType;
import eu.openminted.registry.core.domain.Version;
import eu.openminted.registry.core.domain.index.IndexField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

@Repository("resourceTypeDao")
public class ResourceTypeDaoImpl extends AbstractDao<ResourceType> implements ResourceTypeDao {

    public ResourceTypeDaoImpl() {
        super();
    }

	public ResourceType getResourceType(String name) {
        return getSingleResult("name",name);
	}

	public List<ResourceType> getAllResourceType() {
		return getList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ResourceType> getAllResourceType(int from, int to) {

		CriteriaQuery<ResourceType> criteriaQuery = getCriteriaQuery();
		Root<ResourceType> root = criteriaQuery.from(ResourceType.class);
		criteriaQuery.distinct(true);
		criteriaQuery.select(root);

		TypedQuery<ResourceType> typedQuery = getEntityManager().createQuery(criteriaQuery);
		if (to == 0) {
			typedQuery.setFirstResult(from);
		} else {
			typedQuery.setFirstResult(from);
			typedQuery.setMaxResults((to-from)+1);
		}
		return typedQuery.getResultList();
	}

	public void addResourceType(ResourceType resourceType) {
		persist(resourceType);
	}

	@Override
	public Set<IndexField> getResourceTypeIndexFields(String name) {
		Set<IndexField> indexFields = new HashSet<>();
		Query query = getEntityManager().createQuery("from IndexField where resourceType in " +
				"(from ResourceType where name = :name or aliasGroup = :name)");
		query.setParameter("name",name);
		indexFields.addAll(query.getResultList());
		return indexFields;
	}

	@Override
	public void deleteResourceType(String resourceType) {
		delete(getResourceType(resourceType));
	}

}
