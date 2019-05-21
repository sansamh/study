package io.sansam.spi.impl;

import io.sansam.spi.DemoService;

/**
 * <p>
 * ChineseDemoService
 * </p>
 *
 * @author houcb
 * @since 2019-05-21 17:40
 */
public class ChineseDemoService implements DemoService {
    @Override
    public String sayHi(String name) {
        return "你好 " + name;
    }
}
