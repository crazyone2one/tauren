package cn.master.tauren.util;

import java.util.Random;

/**
 * @author Created by 11's papa on 12/11/2024
 **/
public class StringUtils {
    public static String doubleTypeString(int min, int max) {
        return String.format("%.2f%n", min + ((max - min) * new Random().nextDouble()));
    }
}
