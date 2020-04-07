package com.jakewharton.nopen.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @Noverride might be mistaken with the original @Override, but for now this is only a POC
 */
@Target(METHOD)
@Retention(SOURCE)
public @interface Noverride {
}
