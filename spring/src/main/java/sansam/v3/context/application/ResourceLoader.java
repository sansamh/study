package sansam.v3.context.application;

import sansam.v3.context.resource.Resource;

public interface ResourceLoader {

    Resource getResource(String location) throws Exception;
}
