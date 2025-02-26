package org.example;

import org.example.target.Annotated;
import org.example.target.AnnotatedInner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ServiceAnnotationHandlerTest {

    @Test
    @DisplayName("create service annotated object with no nesting")
    void shouldCreateServiceObjectAndAddToMap() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        HashMap<Class<?>, Object> classToObject = new HashMap<>();

        ServiceAnnotationHandler.instantiateServiceObjectAndAddToMap(AnnotatedInner.class, classToObject);

        assertThat(classToObject).containsExactlyEntriesOf(Map.of(AnnotatedInner.class, new AnnotatedInner()));
    }

    @Test
    @DisplayName("create service annotated object with nesting")
    void shouldCreateServiceNestedObjectAndAddToMap() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        HashMap<Class<?>, Object> classToObject = new HashMap<>();

        ServiceAnnotationHandler.instantiateServiceObjectAndAddToMap(Annotated.class, classToObject);

        assertThat(classToObject).containsExactlyInAnyOrderEntriesOf(
                Map.of(AnnotatedInner.class, new AnnotatedInner(),
                        Annotated.class, new Annotated(new AnnotatedInner()))
        );
    }

    @Test
    @DisplayName("create service annotated object with nesting")
    void shouldHaveExceptionWhenLoadingInvalidServiceAnnotatedClass() {
        HashMap<Class<?>, Object> classToObject = new HashMap<>();

        assertThatThrownBy(() -> ServiceAnnotationHandler.instantiateServiceObjectAndAddToMap(org.example.targeterr.Annotated.class, classToObject))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("parameter org.example.targeterr.NotAnnotated is not annotated with @Service and cannot instantiate it");
    }

    @Test
    @DisplayName("should throw exception when given package name with service annotated classes having nested classes which are NOT service annotated")
    void shouldHaveErrorWhenLoadingPackageWithC() {
        String packageName = "org.example.targeterr";

        assertThatThrownBy(() -> ServiceAnnotationHandler.load(packageName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("parameter org.example.targeterr.NotAnnotated is not annotated with @Service and cannot instantiate it");
    }

    @Test
    @DisplayName("when given package name, all service annotated classes are created")
    void shouldLoadAllClassesWithServiceAnnotationGivenPackageName() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String packageName = "org.example.target";

        Map<Class<?>, Object> classToObject = ServiceAnnotationHandler.load(packageName);

        assertThat(classToObject).containsExactlyInAnyOrderEntriesOf(
                Map.of(AnnotatedInner.class, new AnnotatedInner(),
                        Annotated.class, new Annotated(new AnnotatedInner()))
        );
    }

    @Test
    @DisplayName("when given package name without service annotated classes, map is empty")
    void shouldLoadNoClassesWithServiceAnnotationGivenPackageName() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String packageName = "org.example.target1";

        Map<Class<?>, Object> classToObject = ServiceAnnotationHandler.load(packageName);

        assertThat(classToObject).isEmpty();
    }

    @Test
    @DisplayName("should throw exception when given package name with service annotated classes having nested classes which are NOT service annotated")
    void shouldHaveErrorWhenLoadingPackageWithInvalidServiceAnnotatedClass() {
        String packageName = "org.example.targeterr";

        assertThatThrownBy(() -> ServiceAnnotationHandler.load(packageName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("parameter org.example.targeterr.NotAnnotated is not annotated with @Service and cannot instantiate it");
    }
}