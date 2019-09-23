package io.sansam.generate;

/**
 * <p>
 * Generate
 * </p>
 *
 * @author houcb
 * @since 2019-09-04 17:39
 */
public interface Generate {

    <T> T product(Class<?> clazz) throws Exception;

}
