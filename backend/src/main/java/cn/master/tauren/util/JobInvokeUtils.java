package cn.master.tauren.util;

import cn.master.tauren.entity.QuartzJob;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
public class JobInvokeUtils {
    public static void invokeMethod(QuartzJob sysJob) throws Exception {
        String invokeTarget = sysJob.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        if (!isValidClassName(beanName)) {
            Object bean = SpringContext.getBean(beanName);
            invokeMethod(bean, methodName, methodParams);
        } else {
            Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
            invokeMethod(bean, methodName, methodParams);
        }
    }

    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (Objects.nonNull(methodParams) && !methodParams.isEmpty()) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classes = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = os[0];
            index++;
        }
        return classes;
    }

    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classes = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = (Class<?>) os[1];
            index++;
        }
        return classes;
    }

    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.contains(invokeTarget, ".");
    }

    public static String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(").trim();
        return StringUtils.substringBefore(beanName, ".");
    }

    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(").trim();
        return StringUtils.substringAfter(methodName, ".");
    }

    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isBlank(methodStr)) {
            return null;
        }
        String[] methodParams = methodStr.split(",(?=(?:[^']*\"[^']*')*[^']*$)");
        List<Object[]> classes = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StringUtils.trim(methodParam);
            // String字符串类型，包含'
            if (StringUtils.contains(str, "'")) {
                classes.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
                classes.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, "L")) {
                classes.add(new Object[]{Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class});
            }
            // double浮点类型，包含D
            else if (StringUtils.containsIgnoreCase(str, "D")) {
                classes.add(new Object[]{Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class});
            }
            // 其他类型归类为整形
            else {
                classes.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return classes;
    }
}
