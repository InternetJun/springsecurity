package com.example.util;

import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ParameterizedTypeReference<T> {
    private final Type type;

    protected ParameterizedTypeReference() {
        Class<?> parameterizedTypeReferenceSubclass = findParameterizedTypeReferenceSubclass(this.getClass());
        // 获取父类的泛型类 ParameterizedTypeReference<具体类型>
        Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
        // 必须是 ParameterizedType
        Assert.isInstanceOf(ParameterizedType.class, type, "Type must be a parameterized type");
        ParameterizedType parameterizedType = (ParameterizedType)type;
        // 获取泛型的具体类型  这里是单泛型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");    // 设置结果
        this.type = actualTypeArguments[0];
    }

    // 这个方法开放出去用来调用 来获取泛型的具体Class类型
    public Type getType() {
        return this.type;
    }

    private static Class<?> findParameterizedTypeReferenceSubclass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        // 如果父类是Object 就没戏了
        if (Object.class == parent) {
            throw new IllegalStateException("Expected ParameterizedTypeReference superclass");
        } else {
            // 如果父类是工具类本身 就返回否则就递归 直到获取到ParameterizedTypeReference
            return ParameterizedTypeReference.class == parent ? child : findParameterizedTypeReferenceSubclass(parent);
        }
    }
}
