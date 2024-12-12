package cn.master.tauren.constants;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
public class Constants {
    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi://";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap://";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml", "org.springframework.jndi"};

    /**
     * 降水量 precipitation
     */
    public static final String SENSOR_TYPE_JSL = "0501";
    /**
     * 水位
     */
    public static final String SENSOR_TYPE_WATER_LEVEL = "0502";
    /**
     * 水温
     */
    public static final String SENSOR_TYPE_WATER_TEMPERATURE = "0507";
    /**
     * 涌水量
     */
    public static final String SENSOR_TYPE_YSL = "0503";
    /**
     * 排水量
     */
    public static final String SENSOR_TYPE_PSL = "0504";
    /**
     * 地表沉陷位移
     */
    public static final String SENSOR_TYPE_DBCX = "0505";
    /**
     * 疏（放）水量
     */
    public static final String SENSOR_TYPE_FSL = "0506";
    /**
     * 微震（水文）
     */
    public static final String SENSOR_TYPE_WZ = "0607";

    public final static String END_FLAG = "||";
}
