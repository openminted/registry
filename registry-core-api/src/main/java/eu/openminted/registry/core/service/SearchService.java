package eu.openminted.registry.core.service;

import eu.openminted.registry.core.domain.FacetFilter;
import eu.openminted.registry.core.domain.Paging;
import eu.openminted.registry.core.domain.Resource;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


public interface SearchService {

	QueryBuilder createQueryBuilder(FacetFilter facetFilter);

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
	Paging<Resource> cqlQuery(String query, String resourceType, int quantity,int from, String sortByField, String sortOrder);

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
	Paging<Resource> cqlQuery(String query, String resourceType);

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
    Paging<Resource> cqlQuery(FacetFilter filter);

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
	Paging<Resource> search(FacetFilter filter) throws ServiceException, UnknownHostException;

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
	Paging<Resource> searchKeyword(String resourceType, String keyword) throws ServiceException, UnknownHostException;

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
	Resource searchId(String resourceType, KeyValue... ids) throws ServiceException, UnknownHostException;

	@Retryable(value = ServiceException.class, maxAttempts = 2, backoff = @Backoff(value = 200))
	Map<String,List<Resource>> searchByCategory(FacetFilter filter, String category);

	class KeyValue {

		public String field;

		public String value;

		public KeyValue(String field, String value) {
			this.field = field;
			this.value = value;
		}

		public KeyValue() {
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}