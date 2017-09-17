package com.github.frajimiba.commonstruct.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Originally based on http://www.artima.com/weblogs/viewpost.jsp?thread=208860.
 */
public final class GenericsUtil {

  /**
   * Private constructor prevents construction outside of this class.
   */
  private GenericsUtil() {

  }

  /**
   * Get the underlying class for a type, or null if the type is a variable
   * type.
   * 
   * @param type
   *          the type
   * @return the underlying class
   */
  public static Class<?> getClass(Type type) {
    Class<?> result = null;
    if (type instanceof Class<?>) {
      result = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      result = getClass(((ParameterizedType) type).getRawType());
    } else if (type instanceof GenericArrayType) {
      Type componentType = ((GenericArrayType) type).getGenericComponentType();
      Class<?> componentClass = getClass(componentType);
      if (componentClass != null) {
        result = Array.newInstance(componentClass, 0).getClass();
      }
    }

    return result;
  }

  /**
   * Get the actual type arguments a child class has used to extend a generic
   * base class.
   * 
   * @param <T>
   *          the generic type
   * @param baseClass
   *          the base class
   * @param childClass
   *          the child class
   * @return a list of the raw classes for the actual type arguments.
   */
  public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {

    Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
    Type type = childClass;

    while (!getClass(type).equals(baseClass)) {
      if (type instanceof Class<?>) {
        type = ((Class<?>) type).getGenericSuperclass();
      } else {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Class<?> rawType = (Class<?>) parameterizedType.getRawType();

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
        for (int i = 0; i < actualTypeArguments.length; i++) {
          resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
        }

        if (!rawType.equals(baseClass)) {
          type = rawType.getGenericSuperclass();
        }
      }
    }

    Type[] actualTypeArguments;
    if (type instanceof Class<?>) {
      actualTypeArguments = ((Class<?>) type).getTypeParameters();
    } else {
      actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
    }
    List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();

    for (Type baseType : actualTypeArguments) {
      while (resolvedTypes.containsKey(baseType)) {
        baseType = resolvedTypes.get(baseType);
      }
      typeArgumentsAsClasses.add(getClass(baseType));
    }

    return typeArgumentsAsClasses;
  }

}
