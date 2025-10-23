/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.subdatabase.facade;

import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import java.io.Serializable;
import java.util.Set;
import org.apache.shiro.util.Assert;

public class SubDataBaseTaskParam
implements Serializable {
    private int threadPoolSize;
    private Set<String> tableModelKey;
    private SubDataBase subDataBase;
    private Boolean dataScheme;
    private String taskKey;
    private boolean createOrgCateGory;
    private String orgCateGoryName;
    private OrgCategoryDO orgCategoryDO;

    public SubDataBaseTaskParam(boolean createOrgCateGory, String orgCateGoryName, OrgCategoryDO orgCategoryDO, SubDataBase subDataBase, String taskKey) {
        Assert.notNull((Object)subDataBase, (String)"SubDataBase must not be null");
        this.createOrgCateGory = createOrgCateGory;
        this.orgCateGoryName = orgCateGoryName;
        this.orgCategoryDO = orgCategoryDO;
        this.subDataBase = subDataBase;
        this.taskKey = taskKey;
    }

    public SubDataBaseTaskParam(int threadSize, Boolean dataScheme, Set<String> tableModelKey, SubDataBase subDataBase, String taskKey) {
        Assert.notNull((Object)subDataBase, (String)"SubDataBase must not be null");
        this.threadPoolSize = threadSize;
        this.dataScheme = dataScheme;
        this.tableModelKey = tableModelKey;
        this.subDataBase = subDataBase;
        this.taskKey = taskKey;
    }

    public int getThreadPoolSize() {
        return this.threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public SubDataBase getSubDataBase() {
        return this.subDataBase;
    }

    public void setSubDataBase(SubDataBase subDataBase) {
        this.subDataBase = subDataBase;
    }

    public Boolean getDataScheme() {
        return this.dataScheme;
    }

    public void setTableModelKey(Set<String> tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    public Set<String> getTableModelKey() {
        return this.tableModelKey;
    }

    public void setDataScheme(Boolean dataScheme) {
        this.dataScheme = dataScheme;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public boolean isCreateOrgCateGory() {
        return this.createOrgCateGory;
    }

    public void setCreateOrgCateGory(boolean createOrgCateGory) {
        this.createOrgCateGory = createOrgCateGory;
    }

    public String getOrgCateGoryName() {
        return this.orgCateGoryName;
    }

    public void setOrgCateGoryName(String orgCateGoryName) {
        this.orgCateGoryName = orgCateGoryName;
    }

    public OrgCategoryDO getOrgCategoryDO() {
        return this.orgCategoryDO;
    }

    public void setOrgCategoryDO(OrgCategoryDO orgCategoryDO) {
        this.orgCategoryDO = orgCategoryDO;
    }
}

