package v3.context.application;

import v3.context.resource.Resource;

public interface ResourceLoader {

    Resource getResource(String location) throws Exception;
}
