package com.jakewharton.nopen.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denote a class as explicitly open for extension through inheritance.
 * <p>
 * The only three valid states for a class declaration is:
 * <ul>
 *   <li>{@code final class Name}</li>
 *   <li>{@code abstract class Name}</li>
 *   <li>{@code @Open class Name}</li>
 * </ul>
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface Open {
}
