package com.yzhangm.generate.utils;

import com.yzhangm.generate.config.DatabaseParameter;
import com.yzhangm.generate.config.ParameterConfig;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yzhangm
 * @date 2020-06-03
 */
public final class ToolUtils {

    /**
     * 首字符大写,下划线的首字母也大写
     *
     * @return
     */
    public static String firstCapitalized(String str) {
        str = str.substring(0, 1).toUpperCase().concat(str.substring(1).toLowerCase());
        return underlineFirstCapitalized(str);
    }

    /**
     * 下滑线的首字符大写
     *
     * @return
     */
    public static String underlineFirstCapitalized(String str) {
        String[] strs = str.split("_");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            if (i == 0) {
                stringBuffer.append(strs[i]);
                continue;
            }
            stringBuffer.append(firstCapitalized(strs[i]));
        }
        return stringBuffer.toString();
    }

    /**
     * 获取文件路径,如果文件夹不存在就生成
     *
     * @return
     */
    public static String getCodePath(String path, String currentPackage, String fileName) {
        if (currentPackage == null) {
            path = path + "src\\main\\resources\\mapper";
        } else {
            path = path + "src\\main\\java\\" + currentPackage.replace(".", "\\");
        }
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        return path + "\\" + fileName;
    }

    /**
     * 生成java代码
     *
     * @return
     */
    public static void generateJavaCode(ParameterConfig parameterConfig) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VelocityContext ctx = new VelocityContext();
        ctx.put("parameter", parameterConfig);
        StringWriter sw = new StringWriter();
        ve.getTemplate("controller.java.vm").merge(ctx, sw);
        outJavaCode(sw.toString(), getCodePath(parameterConfig.getProjectPath(), parameterConfig.getControllerPackage(), parameterConfig.getCapitalizedName() + "Controller.java"));
        sw = new StringWriter();
        ve.getTemplate("service.java.vm").merge(ctx, sw);
        outJavaCode(sw.toString(), getCodePath(parameterConfig.getProjectPath(), parameterConfig.getServicePackage(), "I" + parameterConfig.getCapitalizedName() + "Service.java"));
        sw = new StringWriter();
        ve.getTemplate("serviceImpl.java.vm").merge(ctx, sw);
        outJavaCode(sw.toString(), getCodePath(parameterConfig.getProjectPath(), parameterConfig.getServiceImpPackage(), parameterConfig.getCapitalizedName() + "ServiceImpl.java"));
        sw = new StringWriter();
        ve.getTemplate("mapper.java.vm").merge(ctx, sw);
        outJavaCode(sw.toString(), getCodePath(parameterConfig.getProjectPath(), parameterConfig.getMapperPackage(), parameterConfig.getCapitalizedName() + "Mapper.java"));
        sw = new StringWriter();
        ve.getTemplate("model.java.vm").merge(ctx, sw);
        outJavaCode(sw.toString(), getCodePath(parameterConfig.getProjectPath(), parameterConfig.getModelPackage(), parameterConfig.getCapitalizedName() + ".java"));
        sw = new StringWriter();
        ve.getTemplate("mapper.xml.vm").merge(ctx, sw);
        outJavaCode(sw.toString(), getCodePath(parameterConfig.getProjectPath(), null, parameterConfig.getCapitalizedName() + "Mapper.xml"));
    }

    /**
     * 把java代码以文件形式输出出去
     *
     * @param code     代码内容
     * @param fileName 文件名称
     */
    public static void outJavaCode(String code, String fileName) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(code);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成数据库的列
     *
     * @param parameterConfig
     * @return
     */
    public static List<DatabaseParameter> generateDatabaseParmeter(ParameterConfig parameterConfig) {
        Connection connection = null;
        List<DatabaseParameter> databaseParameterList = new ArrayList<DatabaseParameter>();
        try {
            connection = DriverManager.getConnection(parameterConfig.getJdbcUrl(), parameterConfig.getUserName(), parameterConfig.getPassword());
            DatabaseMetaData metaData = connection.getMetaData();
            String primary = "";
            ResultSet pkInfo = metaData.getPrimaryKeys(null, "%", parameterConfig.getTableName());
            while (pkInfo.next()) {
                if (pkInfo.getString("TABLE_CAT").equals("mysql") || parameterConfig.getJdbcUrl().indexOf(pkInfo.getString("TABLE_CAT")) == -1)
                    continue;
                primary = pkInfo.getString("COLUMN_NAME");
            }

            ResultSet rs = metaData.getColumns(null, null, parameterConfig.getTableName(), "%");
            while (rs.next()) {
                if (rs.getString("TABLE_CAT").equals("mysql") || parameterConfig.getJdbcUrl().indexOf(rs.getString("TABLE_CAT")) == -1)
                    continue;
                DatabaseParameter databaseParameter = new DatabaseParameter();
                if (primary.equals(rs.getString("COLUMN_NAME"))) databaseParameter.setPrimary(true);
                databaseParameter.setTableName(rs.getString("COLUMN_NAME"));
                databaseParameter.setCapitalizedName(firstCapitalized(rs.getString("COLUMN_NAME")));
                databaseParameter.setName(underlineFirstCapitalized(rs.getString("COLUMN_NAME")));
                databaseParameter.setNotes(rs.getString("REMARKS"));
                databaseParameter.setType(toSqlToJava(rs.getString("TYPE_NAME")));
                databaseParameter.setAfterLast(!rs.next());
                rs.previous();
                databaseParameterList.add(databaseParameter);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (DatabaseParameter databaseParameter : databaseParameterList) {
            stringBuffer.append(databaseParameter.getTableName().equals(databaseParameter.getName()) ? "`" + databaseParameter.getTableName() + "`" : "`" + databaseParameter.getTableName() + "` AS `" + databaseParameter.getName() + "`");
            stringBuffer.append(!databaseParameter.getAfterLast() ? "," : "");
        }
        parameterConfig.setFieldNames(stringBuffer.toString());
        return databaseParameterList;
    }


    /**
     * 数据类型转化JAVA
     *
     * @param sqlType：类型名称
     * @return
     */
    public static String toSqlToJava(String sqlType) {
        if (sqlType == null || sqlType.trim().length() == 0) return sqlType;
        sqlType = sqlType.toLowerCase();
        if ("enum".equals(sqlType)) {
            return "String";
        } else if ("int".equals(sqlType)) {
            return "Integer";
        } else if ("char".equals(sqlType)) {
            return "String";
        } else if ("varchar".equals(sqlType)) {
            return "String";
        } else if ("text".equals(sqlType)) {
            return "String";
        } else if ("integer".equals(sqlType)) {
            return "Long";
        } else if ("bigint".equals(sqlType)) {
            return "Long";
        } else if ("float".equals(sqlType)) {
            return "Fload";
        } else if ("double".equals(sqlType)) {
            return "Double";
        } else if ("boolean".equals(sqlType)) {
            return "Boolean";
        } else {
            System.out.println("-----------------》转化失败：未发现的类型" + sqlType);
        }
        return "String";
    }
}
