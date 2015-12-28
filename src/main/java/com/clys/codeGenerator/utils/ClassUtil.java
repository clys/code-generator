package com.clys.codeGenerator.utils;

import com.bj58.supin.plugins.codegenerator.core.util.StringUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>名称: </p>
 * <p>描述: 本类描述</p>
 * <p>内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>创建日期：15-12-28</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
public class ClassUtil {
    public static Set<String> getPropertyNames(Class c) {
        Set<String> propertyNames = new HashSet<String>();
        Method[] methods = c.getMethods();
        List<String> setMethodNames = new ArrayList<String>(), getMethodNames = new ArrayList<String>();
        String methodName;
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.matches("^set.+")) {
                setMethodNames.add(methodName.substring(3));
            } else if (methodName.matches("^get.+")) {
                getMethodNames.add(methodName.substring(3));
            }
        }
        for(String setMethodName:setMethodNames){
            if(getMethodNames.contains(setMethodName)){
                propertyNames.add(StringUtil.firstToLower(setMethodName));
            }
        }
        return propertyNames;
    }

    public static Map<String,Class> getPropertys(Class c) {
        Map<String,Class> propertys = new HashMap<String, Class>();
        Method[] methods = c.getMethods();
        List<String> getMethodNames = new ArrayList<String>();
        Map<String,Class> setMethods = new HashMap<String, Class>();
        String methodName;
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.matches("^set.+")) {
                setMethods.put(methodName.substring(3), method.getParameterTypes()[0]);
            } else if (methodName.matches("^get.+")) {
                getMethodNames.add(methodName.substring(3));
            }
        }
        for(String setMethodName:setMethods.keySet()){
            if(getMethodNames.contains(setMethodName)){
                propertys.put(StringUtil.firstToLower(setMethodName),setMethods.get(setMethodName));
            }
        }
        return propertys;
    }

    public static boolean setProperty(Object o,String k,Object v,Class<?>... parameterTypes){
        Class c = o.getClass();
        try {
            Method method = c.getMethod("set"+StringUtil.firstToUpper(k),parameterTypes);
            method.invoke(o,v);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
