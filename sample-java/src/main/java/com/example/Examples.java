package com.example;

import com.jakewharton.nopen.annotation.Noverride;
import com.jakewharton.nopen.annotation.Open;

final class FinalClass {
    public final void finalTest() {

    }

    @Noverride
    public void test() {

    }

    public static void statictest() {

    }

    @SuppressWarnings("UnusedMethod")
    private void privateTest() {

    }
}

abstract class AbstractClass {

    abstract void test();
}

@Open class ExplicitlyOpenClass {

    final protected void protectedTest() {

    }

    @Noverride
    void privatePackateTest() {

    }

    @Noverride
    native void nativeTEst();
}

interface InterfaceTest {
    void overrideMethod();
}

@Open
class ChildExplicitlyOpenClass extends ExplicitlyOpenClass {
    @Override
    final void privatePackateTest() {
        super.privatePackateTest();
    }

    @Override
    final void nativeTEst() {

    }
}

// Uncomment the following to see error:
//class ImplicitlyOpenClass {}
