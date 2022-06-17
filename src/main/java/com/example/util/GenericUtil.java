package com.example.util;

import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

public class GenericUtil<E> {


    public GenericUtil() {
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
     * GenricManager<Book>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static void main(String[] args) {

//        ArrayList<String> strings = new ArrayList<String>(){};
//        Type genericSuperclass = strings.getClass().getGenericSuperclass();
//        ParameterizedTypeReference parameterizedType = (ParameterizedTypeReference)genericSuperclass;
//        System.out.println("genericSuperclass = " + parameterizedType.getType());

        ParameterizedTypeReference parameterizedTypeReference = new ParameterizedTypeReference<String>(){};
        Type type = parameterizedTypeReference.getType();
        System.out.println(type);

    }

    @Test
    public void testFanxing() {
        GenericUtil<String> stringGenericUtil = new GenericUtil();
        Type classFan = stringGenericUtil.getClassFan();
        System.out.println(classFan);

        ArrayList<String> strings = new ArrayList<>();
    }

    public Type getClassFan() {
        ParameterizedTypeReference parameterizedTypeReference = new ParameterizedTypeReference<E>(){};
        Type type = parameterizedTypeReference.getType();
        return type;
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
