package io.sansam.spi.impl;

import io.sansam.spi.DemoService;

/**
 * <p>
 * EnglishDemoService
 * </p>
 *
 * @author houcb
 * @since 2019-05-21 17:41
 */
public class EnglishDemoService implements DemoService {
    @Override
    public String sayHi(String name) {
        return "Hello " + name;
    }
}
