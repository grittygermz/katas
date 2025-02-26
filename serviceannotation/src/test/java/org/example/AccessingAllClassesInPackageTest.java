package org.example;

import org.example.target.Annotated;
import org.example.target.AnnotatedInner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccessingAllClassesInPackageTest {

    @Test
    @DisplayName("only returns classes which have service annotation on them when provided the package name")
    void shouldGiveServiceAnnotatedClassesInPackage() {
        Set<Class<?>> classesWithServiceAnnotation = AccessingAllClassesInPackage
                .findAllClassesInPackageWithServiceAnnotationUsingClassLoader("org.example.target");

        Set<Class<?>> expected = Set.of(AnnotatedInner.class, Annotated.class);
        assertThat(classesWithServiceAnnotation).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("given package with non-annotated classes should return nothing")
    void shouldReturnEmpty() {
        Set<Class<?>> classesWithServiceAnnotation = AccessingAllClassesInPackage
                .findAllClassesInPackageWithServiceAnnotationUsingClassLoader("org.example.target1");

        assertThat(classesWithServiceAnnotation).isEmpty();
    }

    @Test
    void shouldHaveErrorWhenPackageDoesntExist() {
        assertThatThrownBy(() -> AccessingAllClassesInPackage.findAllClassesInPackageWithServiceAnnotationUsingClassLoader("org.example.target99"))
                .isInstanceOf(NullPointerException.class);
    }

}