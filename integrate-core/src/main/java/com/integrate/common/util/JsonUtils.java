package com.integrate.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;

/**
 * JSON 转换相关的工具类
 */
public abstract class JsonUtils {
	
	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();

    private static Map<Class, Class> primitiveToPacked = new HashMap<>(9); //原生类型=>包装类型

    static {
        mapper.registerModule(new JsonOrgModule());
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    static {
        primitiveToPacked.put(long.class, Long.class);
        primitiveToPacked.put(int.class, Integer.class);
        primitiveToPacked.put(short.class, Short.class);
        primitiveToPacked.put(char.class, Character.class);
        primitiveToPacked.put(boolean.class, Boolean.class);
        primitiveToPacked.put(byte.class, Byte.class);
        primitiveToPacked.put(double.class, Double.class);
        primitiveToPacked.put(float.class, Float.class);
        primitiveToPacked.put(String.class, String.class); //呵呵, String在这里也看成原生类型
    }
    
    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转json字符串失败 {}", e);
            return null;
        }
    }

	public static String map2String(Map<?, ?> map) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, map);
		} catch (Exception e) {
			log.error("将 map 转换为 json 字符串时发生异常", e);
			return null;
		}
		return writer.toString();
	}
	
	public static String toJsonArray(Object...object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转json字符串失败", e);
            return null;
        }
    }
	
	public static JsonNode string2JsonNode(String string) {
        try {
            return mapper.readTree(string);
        } catch (IOException e) {
            log.error("将 json 字符串转换为 JsonNode 时发生异常  " + string, e);
            return null;
        }
    }

	public static Map<String,Object> string2Map(String string) {
		StringReader reader = new StringReader(string);
		try {
			return mapper.readValue(reader, new TypeReference<HashMap<String,Object>>() {});
		} catch (IOException e) {
			log.error("将 json 字符串转换为 HashMap 时发生异常, " + string + ", error: {}", e);
			return null;
		}
	}
	
    

    /**
     * json字符转换类型到clazz
     * @param json json字符串
     * @param clazz 类型
     * @param <T> 转换后的类型
     * @return
     */
    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("字符串转" + clazz.getSimpleName() + "失败", e);
            return null;
        }
    }
    
    
    
    



    private static Object invokeMethod(Object o, String methodName, Object... args) {
        try {
            if (o instanceof Class) {
                return MethodUtils.invokeStaticMethod((Class)o, methodName, args);
            }
            return MethodUtils.invokeMethod(o, methodName, args);
        } catch (Exception e) {
            log.warn("反射调用(一般是类型转换)出错, 一般可以忽略 detail: target:{} method:{} arg:{} argType:{}",
                    o, methodName, args[0], args[0].getClass());
            return null;
        }
    }

    private static void signPrimitive(JSONObject jsonObject,
                                      GeneratedMessage.Builder builder,
                                      String fieldName,
                                      String methodName) throws Exception {
        Class c = findFieldType(builder, methodName);
        String value = jsonObject.getString(fieldName);
        Object retValue = invokeMethod(c, "valueOf", value);
        invokeMethod(builder, methodName, retValue);
    }


    private static void signPrimitiveArray(GeneratedMessage.Builder builder,
                                           String methodName,
                                           JSONArray array, Class c) throws Exception {
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            value = invokeMethod(c, "valueOf", value);
            invokeMethod(builder, methodName, value);
        }
    }


    private static Class findFieldType(GeneratedMessage.Builder builder, String methodName) {
        Method[] methods = builder.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)
                    && method.getParameterTypes().length == 1) {
                Class c = method.getParameterTypes()[0];
                if (GeneratedMessage.class.isAssignableFrom(c)) {
                    return c;
                }
                if (primitiveToPacked.containsKey(c)) {
                    return primitiveToPacked.get(c);
                }
            }
        }
        return null;
    }

    private static Set<String> collectKey(JSONObject jsonObject) {
        Set<String> set = new HashSet<>();
        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    public static Map<String,String> stringToTreeMap(String string) {
        StringReader reader = new StringReader(string);
        try {
            return mapper.readValue(string, TreeMap.class);
        } catch (IOException e) {
            return null;
        }
    }

    
    public static <T> List<T> jsonToList(String json,Class<T> claxx ){
		try {
			List<T> list = mapper.readValue(json, new TypeReference<List<T>>() {});
			return list ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
   
}
