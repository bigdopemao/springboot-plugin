package com.mao.logs.service;

import com.mao.logs.config.LogsProperties;

/**
 * @author bigdope
 * @create 2019-06-20
 **/
public class LogsService {

    public static String logsOpenMessage = ">>>>>>>>>> logsService配置成功:";

    private LogsProperties logsProperties;

    public LogsProperties getLogsProperties() {
        return logsProperties;
    }

    public void setLogsProperties(LogsProperties logsProperties) {
        this.logsProperties = logsProperties;
    }
}
