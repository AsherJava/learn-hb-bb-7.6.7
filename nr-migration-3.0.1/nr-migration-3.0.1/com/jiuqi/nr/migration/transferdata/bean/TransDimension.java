/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

public class TransDimension {
    private String name;
    private String entityId;
    private String value;
    private String title;
    private int type;
    private boolean createXM;
    private String tableName;
    private String management;
    private String parentCode;

    public static TransDimension getDWDimension(String name, String dwEntityId, String code, String title) {
        TransDimension dim = new TransDimension();
        dim.name = name;
        dim.entityId = dwEntityId;
        dim.value = code;
        dim.title = title;
        dim.type = 7;
        return dim;
    }

    public static TransDimension getDWDim(String dwEntityId, boolean createOrg, String code, String title, String tableName) {
        String dwName = "";
        if (dwEntityId.endsWith("@BASE")) {
            dwName = dwEntityId.split("@")[0].substring(3);
        } else if (dwEntityId.endsWith("@ORG")) {
            dwName = "UNITID";
        }
        return TransDimension.getDWDimension(dwName, dwEntityId, code, title).setCreateXM(createOrg).setTableName(tableName);
    }

    public static TransDimension getPeriodDimension(String name, String periodEntityId, String periodValue) {
        TransDimension dim = new TransDimension();
        dim.name = name;
        dim.entityId = periodEntityId;
        dim.value = periodValue;
        dim.type = 2;
        return dim;
    }

    public String getTableName() {
        return this.tableName;
    }

    public TransDimension setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCreateXM() {
        return this.createXM;
    }

    public TransDimension setCreateXM(boolean createXM) {
        this.createXM = createXM;
        return this;
    }

    public String getManagement() {
        return this.management;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}

