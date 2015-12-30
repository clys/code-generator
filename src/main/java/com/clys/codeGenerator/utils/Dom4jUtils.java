package com.clys.codeGenerator.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: xml的工具</p>
 * <p>创建日期：15-12-28</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
public class Dom4jUtils {
    public static Map<String, Element> elementListToMap(List<Element> element, String key) {
        if (element == null) {
            return null;
        }
        Map<String, Element> elementMap = new HashMap<String, Element>();
        String beanId;
        for (Element child : element) {
            beanId = child.attributeValue(key);
            if (StringUtils.isNotBlank(beanId)) {
                elementMap.put(beanId, child);
            }
        }
        return elementMap;
    }

    public static void extractProperty(Object obj, List<Element> elements) {
        if (obj == null || elements == null) {
            return;
        }
        Map<String, Element> elementMap = Dom4jUtils.elementListToMap(elements, "name");
        Class c = obj.getClass();
        Map<String, Class> propertys = ClassUtil.getPropertys(c);
        Element element;
        Class clazz;
        for (Map.Entry<String, Class> property : propertys.entrySet()) {
            element = elementMap.get(property.getKey());
            if (element != null) {
                clazz = property.getValue();
                ClassUtil.setProperty(obj, property.getKey(), get(element, clazz), clazz);
            }
        }

    }

    public static Object get(Element element, Class c) {
        if (String.class.equals(c)) {
            return getString(element);
        } else if (boolean.class.equals(c)) {
            return getBoolean(element);
        } else if (byte.class.equals(c)) {
            return getByte(element);
        } else if (char.class.equals(c)) {
            return getChar(element);
        } else if (short.class.equals(c)) {
            return getShort(element);
        } else if (long.class.equals(c)) {
            return getLong(element);
        } else if (int.class.equals(c)) {
            return getInt(element);
        } else if (float.class.equals(c)) {
            return getFloat(element);
        } else if (double.class.equals(c)) {
            return getDouble(element);
        } else if (List.class.equals(c)) {
            return getList(element);
        } else if (Map.class.equals(c)) {
            return getMap(element);
        }
        return null;
    }

    public static String getString(Element element) {
        String text;
        text = element.attributeValue("value");
        if (StringUtils.isEmpty(text)) {
            text = element.getTextTrim();
        }
        return text;
    }

    public static int getInt(Element element) {
        return Integer.parseInt(getString(element));
    }

    public static boolean getBoolean(Element element) {
        return Boolean.parseBoolean(getString(element));
    }

    public static byte getByte(Element element) {
        return Byte.parseByte(getString(element));
    }

    public static char getChar(Element element) {
        return getString(element).charAt(0);
    }

    public static short getShort(Element element) {
        return Short.parseShort(getString(element));
    }

    public static long getLong(Element element) {
        return Long.parseLong(getString(element));
    }

    public static float getFloat(Element element) {
        return Float.parseFloat(getString(element));
    }

    public static double getDouble(Element element) {
        return Double.parseDouble(getString(element));
    }

    public static List getList(Element element) {
        List list = new ArrayList();
        Element l = element.element("list");
        if (l == null) {
            return null;
        }
        List<Element> values = l.elements("value");
        if (values == null || values.size() == 0) {
            return list;
        }
        for (Element value : values) {
            list.add(getString(value));
        }
        return list;
    }

    public static Map getMap(Element element) {
        Map<String, String> map = new HashMap<String, String>();
        Element m = element.element("map");
        if (m == null) {
            return null;
        }
        List<Element> entrys = m.elements("entry");
        if (entrys == null || entrys.size() == 0) {
            return map;
        }
        String k;
        for (Element entry : entrys) {
            k = entry.attributeValue("key");
            if(StringUtils.isNotEmpty(k)){
                map.put(k,getString(entry));
            }
        }
        return map;
    }
}
