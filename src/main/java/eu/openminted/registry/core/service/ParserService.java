package eu.openminted.registry.core.service;

import eu.openminted.registry.core.domain.Resource;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by stefanos on 4/7/2017.
 */
public interface ParserService {

    <T> Future<T> serialize(Resource resource, Class<T> returnType);

    <T> T deserialize(String json, Class<T> returnType) throws IOException;

    Resource deserializeResource(File file, ParserServiceTypes mediaType);

    Future<String> deserialize(Object resource, ParserServiceTypes mediaType);

    enum ParserServiceTypes {
        JSON,XML
    }
}

