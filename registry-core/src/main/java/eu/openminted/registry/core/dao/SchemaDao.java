package eu.openminted.registry.core.dao;

import eu.openminted.registry.core.domain.ResourceType;
import eu.openminted.registry.core.domain.Schema;
import org.everit.json.schema.loader.SchemaClient;
import org.w3c.dom.ls.LSResourceResolver;

public interface SchemaDao extends LSResourceResolver, SchemaClient {

    Schema getSchema(String id);

    Schema getSchemaByUrl(String originalURL);

    void addSchema(Schema schema);

    void deleteSchema(Schema schema);

    javax.xml.validation.Schema loadXMLSchema(ResourceType url);

    org.everit.json.schema.Schema loadJSONSchema(ResourceType url);

}
