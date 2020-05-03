package com.mao.logs;

import com.mao.logs.config.LogsProperties;
import com.mao.logs.service.LogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bigdope
 * @create 2019-06-20
 **/
@Configuration //开启配置
@EnableConfigurationProperties(LogsProperties.class) //开启使用映射实体对象
@ConditionalOnClass(LogsService.class) //存在LogsService时初始化该配置类
@ConditionalOnProperty( //存在对应配置信息时初始化该配置类
        prefix = "spring.logs", //存在配置前缀logs
        value = "enabled", //开启
        matchIfMissing = true //缺失检查
)
public class LogsAutoConfigure {

    private static Logger logger = LoggerFactory.getLogger(LogsAutoConfigure.class);

    @Autowired
    private LogsProperties logsProperties;

    public LogsAutoConfigure() {
    }

    @Bean
    @ConditionalOnMissingBean
    public LogsService logsService() throws Exception {
        logger.warn(">>>>>>>>>> The LogsService Not Found，Execute Create New Bean.");
        LogsService logsService = new LogsService();
        logger.info(logsService.logsOpenMessage);
        logsService.setLogsProperties(logsProperties);
        return logsService;
    }

}
