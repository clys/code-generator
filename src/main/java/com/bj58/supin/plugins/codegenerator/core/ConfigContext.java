package com.bj58.supin.plugins.codegenerator.core;

import com.bj58.supin.plugins.codegenerator.core.util.PropsUtil;

import java.util.Properties;

/**
 * @author lujianing01@58.com
 * @Description:
 * @date 2015/12/18
 */
public class ConfigContext {


    public ConfigContext(String sourcePath,String outputPath){
        Properties properties = PropsUtil.loadProps(sourcePath+Constant.CONFIG_PROPS);
        setSourcePath(sourcePath);
        setOutputPath(outputPath);

        setDriver(PropsUtil.getString(properties, Constant.JDBC_DRIVER));
        setUrl(PropsUtil.getString(properties, Constant.JDBC_URL));
        setUserName(PropsUtil.getString(properties, Constant.JDBC_USERNAME));
        setPassword(PropsUtil.getString(properties, Constant.JDBC_PASSWORD));

        setTargetTable(PropsUtil.getString(properties, Constant.TARGET_TABLE));
        setTargetName(PropsUtil.getString(properties, Constant.TARGET_NAME));
        setTargetPackage(PropsUtil.getString(properties, Constant.TARGET_PACKAGE));

    }

    private String sourcePath;
    private String outputPath;

    private String driver;
    private String url;
    private String userName;
    private String password;


    private String targetTable;
    private String targetName;
    private String targetPackage;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }
}