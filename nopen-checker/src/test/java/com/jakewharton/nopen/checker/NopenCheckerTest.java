package com.jakewharton.nopen.checker;

import com.google.errorprone.CompilationTestHelper;
import org.junit.Test;

public final class NopenCheckerTest {
  private final CompilationTestHelper compiler =
      CompilationTestHelper.newInstance(NopenChecker.class, getClass());

  @Test public void tests() {
    compiler.addSourceFile("NopenTests.java").doTest();
  }
}
