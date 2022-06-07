package com.guozhi.easyscaffold.utils

import java.lang.reflect.Method

/**
 * 注解工具类
 */
object AnnotationUtils {

    /**
     * 读取一个类的注解
     * @param targetClass 目标类
     * @param targetAnnotation 目标注解
     */
    fun <T, R : Annotation> getClassAnnotation(
        targetClass: Class<T>, targetAnnotation: Class<out R>
    ): R? {
        return targetClass.getAnnotation(targetAnnotation)
    }

    /**
     * 读取一个方法的注解
     * @param targetMethod 目标类
     * @param targetAnnotation 目标注解
     */
    fun <R : Annotation> getMethodAnnotation(
        targetMethod: Method,
        targetAnnotation: Class<out R>
    ): R? {
        return targetMethod.getAnnotation(targetAnnotation)
    }
}