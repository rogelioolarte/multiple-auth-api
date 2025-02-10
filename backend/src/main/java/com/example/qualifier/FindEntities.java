package com.example.qualifier;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom qualifier annotation used to indicate that a method is responsible
 * for retrieving multiple entities from the database.
 * <p>
 * This annotation is typically used in conjunction with MapStruct to
 * differentiate mapping methods that find multiple entities.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * {@code
 * @FindEntities
 * public List<Entity> findAllByIds(List<Long> ids) {
 *     return entityRepository.findAllByIds(ids);
 * }
 * }
 * </pre>
 *
 * @see org.mapstruct.Qualifier
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface FindEntities {
}
