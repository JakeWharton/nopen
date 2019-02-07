Nope'n!
=======

An error-prone checker which requires that classes be `final`, `abstract`, or annotated with
`@Open`.

>  Design and document for inheritance or else prohibit it
>
> – Item 19, Effective Java, Third Edition


Usage
-----

Java creates new types as open by default which can be dangerous. This checker ensures that the
intent to leave a class open is explicitly declared.

```java
import com.jakewharton.nopen.annotation.Open;

final class Foo {}
abstract class Bar {}
@Open class Baz {}
```

Non-`final`, Non-`abstract` classes without the `@Open` annotation will be marked with an error.

```java
class Bad {}
```
```
Bad.java:1: error: [Nopen] Classes should be explicitly marked final, abstract, or @Open
class Bad {}
^
```

For more information see Item 19 of Effective Java, Third Edition.


Download
--------

Gradle, using [`net.ltgt.errorprone` plugin](https://github.com/tbroyer/gradle-errorprone-plugin):

```groovy
dependencies {
  compileOnly 'com.jakewharton.nopen:nopen-annotations:1.0.1'
  errorprone 'com.jakewharton.nopen:nopen-checker:1.0.1'
}
```

By default the check will operate everywhere that error-prone runs. You can disable it for tests,
for example, where the extra safety isn't necessary.

For Java projects:
```groovy
import net.ltgt.gradle.errorprone.CheckSeverity

tasks.named("compileTestJava").configure {
  options.errorprone.check("Nopen", CheckSeverity.OFF)
}
```

For Android projects:
```groovy
import net.ltgt.gradle.errorprone.CheckSeverity

android.testVariants.all { variant ->
  variant.javaCompileProvider.configure {
    options.errorprone.check("Nopen", CheckSeverity.OFF)
  }
}
android.unitTestVariants.all { variant ->
  variant.javaCompileProvider.configure {
    options.errorprone.check("Nopen", CheckSeverity.OFF)
  }
}
```



License
=======

    Copyright 2019 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
