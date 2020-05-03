package com.mao.logs.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author bigdope
 * @create 2019-06-20
 **/
public class PropertiesUtils {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    public static Properties loadProperties(String fileName) {
        if (fileName == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
//            properties.load(inputStream);
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
            return properties;
        } catch (Exception e) {
            logger.info(">>>>>>>>>> loadProperties失败: " + fileName);
            return null;
        }
    }

    public static PropertiesConfiguration loadPropertiesConfiguration(String fileName) {
        if (fileName == null) {
            return null;
        }
        PropertiesConfiguration propertiesConfiguration = null;
        try {
            propertiesConfiguration = new PropertiesConfiguration(fileName);
            return propertiesConfiguration;
        } catch (Exception e) {
            logger.info(">>>>>>>>>> loadPropertiesConfiguration失败: " + fileName);
            return null;
        }
    }

}
