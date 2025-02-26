package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ServiceAnnotationHandler {
    public static Map<Class<?>, Object> load(String packageName) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> classesWithAnnotation = AccessingAllClassesInPackage
                .findAllClassesInPackageWithServiceAnnotationUsingClassLoader(packageName);

        Map<Class<?>, Object> classToObject = new HashMap<>();
        for (Class<?> c : classesWithAnnotation) {
            instantiateServiceObjectAndAddToMap(c, classToObject);
        }
        return classToObject;
    }

    /**
     * assumes that there is always only 1 constructor
     * all fields within the class with @Service must also have @Service otherwise exception will be thrown
     *
     * @param clazz the class to load
     * @param map the map of Class<?> to Object that will hold all objects with Service annotation
     */
    static void instantiateServiceObjectAndAddToMap(Class<?> clazz, Map<Class<?>, Object> map) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> argumentsList = new ArrayList<>(constructor.getParameterCount());

        for (Class<?> parameterType : parameterTypes) {

            if (parameterType.isAnnotationPresent(Service.class)) {
                System.out.println("inspecting " + parameterType.getName());

                if (map.get(parameterType) == null) {
                    System.out.println("not existing in map.. adding");
                    instantiateServiceObjectAndAddToMap(parameterType, map);
                }
                argumentsList.add(map.get(parameterType));

            } else {
                throw new RuntimeException("parameter %s is not annotated with @Service and cannot instantiate it".formatted(parameterType.getName()));
            }
        }
        Object o = constructor.newInstance(argumentsList.toArray());
        map.put(clazz, o);
    }
}
