/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.util.StringUtils;
import java.util.Date;

public class I18nTaskOrgLinkDefine
implements TaskOrgLinkDefine {
    private final TaskOrgLinkDefine taskOrgLinkDefine;
    private String entityAlias;

    public void setI18nEntityAlias(String i18nEntityAlias) {
        this.entityAlias = i18nEntityAlias;
    }

    public I18nTaskOrgLinkDefine(TaskOrgLinkDefine taskOrgLinkDefine) {
        this.taskOrgLinkDefine = taskOrgLinkDefine;
    }

    public String getKey() {
        return this.taskOrgLinkDefine.getKey();
    }

    public String getTitle() {
        return null;
    }

    @Override
    public String getTask() {
        return this.taskOrgLinkDefine.getTask();
    }

    @Override
    public String getEntity() {
        return this.taskOrgLinkDefine.getEntity();
    }

    @Override
    public String getEntityAlias() {
        return StringUtils.isEmpty((String)this.entityAlias) ? this.taskOrgLinkDefine.getEntityAlias() : this.entityAlias;
    }

    @Override
    public String getOrder() {
        return this.taskOrgLinkDefine.getOrder();
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return null;
    }
}

