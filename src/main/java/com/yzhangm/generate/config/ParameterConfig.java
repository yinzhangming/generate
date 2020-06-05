package com.yzhangm.generate.config;

import com.yzhangm.generate.utils.ToolUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yzhangm
 * @date 2020-06-03
 */
public class ParameterConfig {

    private String tableName;

    private String capitalizedName;

    private String name;

    private String controllerPackage;

    private String servicePackage;

    private String serviceImpPackage;

    private String mapperPackage;

    private String modelPackage;

    private String author;

    private String date;

    private String comment;

    private String projectPath;

    private String jdbcUrl;

    private String userName;

    private String password;

    private String fieldNames;

    public List<DatabaseParameter> databaseParameterList;

    public ParameterConfig() {
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis());
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.name = ToolUtils.underlineFirstCapitalized(tableName);
        this.capitalizedName = ToolUtils.firstCapitalized(tableName);
        this.tableName = tableName;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalizedName() {
        return this.capitalizedName;
    }

    public void setCapitalizedName(String capitalizedName) {
        this.capitalizedName = capitalizedName;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceImpPackage() {
        return serviceImpPackage;
    }

    public void setServiceImpPackage(String serviceImpPackage) {
        this.serviceImpPackage = serviceImpPackage;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    public String getModelPackage() {
        return this.modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
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

    public List<DatabaseParameter> getDatabaseParameterList() {
        return databaseParameterList;
    }

    public void setDatabaseParameterList(List<DatabaseParameter> databaseParameterList) {
        this.databaseParameterList = databaseParameterList;
    }

    public String getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }
}
