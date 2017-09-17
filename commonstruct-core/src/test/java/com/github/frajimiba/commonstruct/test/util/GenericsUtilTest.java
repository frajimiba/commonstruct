package com.github.frajimiba.commonstruct.test.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.frajimiba.commonstruct.util.GenericsUtil;

public class GenericsUtilTest {

  abstract class TypeAwareArrayList<T> extends ArrayList<T> {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public Class<? extends T> getType() {
      List<Class<?>> typeArguments = GenericsUtil.getTypeArguments(TypeAwareArrayList.class, getClass());
      return (Class<? extends T>) typeArguments.get(0);
    }
  }

  @Test
  public void reflectionArrayTest() throws Exception {
    TypeAwareArrayList<String[]> typeAware = new TypeAwareArrayList<String[]>() {
      private static final long serialVersionUID = 1L;
    };
    assertEquals("class [Ljava.lang.String", typeAware.getType(), String[].class);

  }

  @Test
  public void reflectionObjectTest() throws Exception {
    TypeAwareArrayList<String> typeAware = new TypeAwareArrayList<String>() {
      private static final long serialVersionUID = 1L;
    };
    assertEquals("class java.lang.String", typeAware.getType(), String.class);

  }

  @Test
  public void reflectionInterfaceTest() throws Exception {
    TypeAwareArrayList<List<String>> typeAware = new TypeAwareArrayList<List<String>>() {
      private static final long serialVersionUID = 1L;
    };
    assertEquals("interface java.util.List", typeAware.getType(), List.class);

  }

}