package com.yzhangm.generate;

import com.yzhangm.generate.config.ParameterConfig;
import com.yzhangm.generate.utils.ToolUtils;

/**
 * @author yzhangm
 * @date 2020-06-03
 */
public class Generator {

    public static void main(String[] args) {
        ParameterConfig parameterConfig = new ParameterConfig();
        parameterConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&userSSL=false&serverTimezone=GMT%2B8");
        parameterConfig.setTableName("test_fuck"); // 表名称
        parameterConfig.setUserName("root"); // 数据库账号
        parameterConfig.setPassword("123456"); // 数据库密码
        parameterConfig.setAuthor("yzhangm"); // 作者
        parameterConfig.setComment("用户"); // 功能介绍
        parameterConfig.setModelPackage("com.shustudio.system.model"); // 实体类包的路径
        parameterConfig.setMapperPackage("com.shustudio.system.mapper"); // mapper包的路径
        parameterConfig.setServicePackage("com.shustudio.system.service"); // service包的路径
        parameterConfig.setControllerPackage("com.shustudio.system.controller"); // Controller包的路径
        parameterConfig.setServiceImpPackage("com.shustudio.system.service.impl"); //  serviceimple包的路径
        // 项目的绝对路径,后缀必须以 \ 结尾,中间会默认拼接 src\main\java\包路径
        parameterConfig.setProjectPath("C:\\Users\\yzhan\\Desktop\\java-uy123\\system\\");
        parameterConfig.setDatabaseParameterList(ToolUtils.generateDatabaseParmeter(parameterConfig));
        ToolUtils.generateJavaCode(parameterConfig);
    }

}
