package com.jakewharton.nopen.checker;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.util.ASTHelpers;
import com.jakewharton.nopen.annotation.Noverride;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;
import java.util.Set;

import static com.google.errorprone.BugPattern.LinkType.CUSTOM;
import static com.google.errorprone.BugPattern.SeverityLevel.ERROR;
import static com.google.errorprone.matchers.Description.NO_MATCH;
import static com.sun.source.tree.Tree.Kind.METHOD;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;
//import static javax.lang.model.element.Modifier.NATIVE;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

@AutoService(BugChecker.class)
@BugPattern(
        name = "Noverride",
        summary = "Methods should be explicitly marked final, static, private, abstract, native or @Noverride",
        explanation = "Java creates new types as open by default which can be dangerous. "
                + "This checker ensures that the intent to leave a method overridable is explicitly declared. "
                + "For more information see Item 19 of Effective Java, Third Edition.", // is there a specific item for that or is item 19 suitable?
        linkType = CUSTOM,
        link = "https://github.com/JakeWharton/nopen#usage",
        severity = ERROR
)
public final class NoverrideChecker extends BugChecker implements BugChecker.MethodTreeMatcher {

    private static final String NOVERRIDE_FQCN = Noverride.class.getCanonicalName();
//    private static final String OVERRIDE_FQCN = Override.class.getCanonicalName();

    @Override
    public Description matchMethod(MethodTree tree, VisitorState state) {
        if (tree.getKind() != METHOD) {
            return NO_MATCH;
        }

        ModifiersTree modifiers = tree.getModifiers();
        Set<Modifier> modifierFlags = modifiers.getFlags();
        if (modifierFlags.contains(FINAL) || modifierFlags.contains(STATIC) || modifierFlags.contains(PRIVATE) ||
                modifierFlags.contains(ABSTRACT)) {// || modifierFlags.contains(NATIVE)) { // does it make sense for native methods?
            return NO_MATCH;
        }

        // TODO: find out if a method is within an Interface, so obviously it should not match.
        if (tree.getBody() == null) {
            return NO_MATCH;
        }

        for (AnnotationTree annotation : modifiers.getAnnotations()) {
            AnnotationMirror annotationMirror = ASTHelpers.getAnnotationMirror(annotation);
            if (annotationMirror.getAnnotationType().toString().equals(NOVERRIDE_FQCN)) {// ||
//                    annotationMirror.getAnnotationType().toString().equals(OVERRIDE_FQCN)) { // does it make sense if method is already overridden?
                return NO_MATCH;
            }
        }
        return describeMatch(tree);
    }
}
