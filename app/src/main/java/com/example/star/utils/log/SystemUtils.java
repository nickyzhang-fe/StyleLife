package com.example.star.utils.log;
import java.lang.reflect.Field;

/**
 * Created by：Cral-Gates on 16/4/12 19:40
 * EMail：zhangxia2013105@gmail.com
 * Date: 16/4/12
 * Description:
 */
public class SystemUtils {

    /**
     * 获取StackTraceElement对象
     *
     * @return
     */
    public static StackTraceElement getStackTrace() {
        return Thread.currentThread().getStackTrace()[4];
    }


    // 基本数据类型
    private final static String[] types = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static <T> String objectToString(T object) {
        if (object == null) {
            return "Object{object is null}";
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean flag = false;
                for (String type : types) {
                    if (field.getType().getName().equalsIgnoreCase(type)) {
                        flag = true;
                        Object value = null;
                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException e) {
                            value = e;
                        } finally {
                            builder.append(String.format("%s=%s, ", field.getName(),
                                    value == null ? "null" : value.toString()));
                            break;
                        }
                    }
                }
                if (!flag) {
                    builder.append(String.format("%s=%s, ", field.getName(), "Object"));
                }
            }
            return builder.replace(builder.length() - 2, builder.length() - 1, "}").toString();
        } else {
            return object.toString();
        }
    }


}
