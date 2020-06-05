package com.yzhangm.generate.config;

/**
 * @author yzhangm
 * @date 2020-06-03
 */
public class DatabaseParameter {

    private String tableName; // 数据库列名字

    private String name; // 转换后的名字

    private String capitalizedName; // 第一个字符大写名称

    private String notes;

    private String type;

    private Boolean afterLast=false;

    private Boolean primary = false;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAfterLast() {
        return afterLast;
    }

    public void setAfterLast(Boolean afterLast) {
        this.afterLast = afterLast;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getCapitalizedName() {
        return capitalizedName;
    }

    public void setCapitalizedName(String capitalizedName) {
        this.capitalizedName = capitalizedName;
    }

    @Override
    public String toString() {
        return "DatabaseParameter{" +
                "tableName='" + tableName + '\'' +
                ", name='" + name + '\'' +
                ", capitalizedName='" + capitalizedName + '\'' +
                ", notes='" + notes + '\'' +
                ", type='" + type + '\'' +
                ", afterLast=" + afterLast +
                ", primary=" + primary +
                '}';
    }
}
