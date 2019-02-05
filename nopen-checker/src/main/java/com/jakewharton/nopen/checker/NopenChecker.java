package com.jakewharton.nopen.checker;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.ClassTreeMatcher;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.util.ASTHelpers;
import com.jakewharton.nopen.annotation.Open;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ModifiersTree;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

import static com.google.errorprone.BugPattern.LinkType.CUSTOM;
import static com.google.errorprone.BugPattern.SeverityLevel.ERROR;
import static com.google.errorprone.matchers.Description.NO_MATCH;
import static com.sun.source.tree.Tree.Kind.CLASS;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;

@AutoService(BugChecker.class)
@BugPattern(
    name = "Nopen",
    summary = "Classes should be explicitly marked final, abstract, or @Open",
    explanation = "Java creates new types as open by default which is a dangerous default. "
        + "This checker ensures that the intent to leave a class open is explicitly declared. "
        + "For more information see Item 19 of Effective Java, Third Edition.",
    linkType = CUSTOM,
    link = "https://gitub.com/JakeWharton/nopen#usage",
    severity = ERROR
)
public final class NopenChecker extends BugChecker implements ClassTreeMatcher {
  private static final String OPEN_FQCN = Open.class.getCanonicalName();

  @Override public Description matchClass(ClassTree tree, VisitorState state) {
    if (tree.getKind() != CLASS) {
      return NO_MATCH;
    }

    ModifiersTree modifiers = tree.getModifiers();
    Set<Modifier> modifierFlags = modifiers.getFlags();
    if (modifierFlags.contains(FINAL) || modifierFlags.contains(ABSTRACT)) {
      return NO_MATCH;
    }

    switch (ASTHelpers.getSymbol(tree).getNestingKind()) {
      case LOCAL:
      case ANONYMOUS:
        return NO_MATCH;

      case MEMBER:
        if (modifierFlags.contains(PRIVATE)) {
          return NO_MATCH;
        }
        break;

      case TOP_LEVEL:
        break;
    }

    for (AnnotationTree annotation : modifiers.getAnnotations()) {
      AnnotationMirror annotationMirror = ASTHelpers.getAnnotationMirror(annotation);
      if (annotationMirror.getAnnotationType().toString().equals(OPEN_FQCN)) {
        return NO_MATCH;
      }
    }
    return describeMatch(tree);
  }
}
