/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ReadWriteAccessCacheParams
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext jtableContext;
    private List<EntityViewData> entityList;
    private List<String> formKeys;
    private Consts.FormAccessLevel formAccessLevel;
    private Set<String> ignoreItems;

    public ReadWriteAccessCacheParams(JtableContext jtableContext, List<String> formKeys, Consts.FormAccessLevel formAccessLevel) {
        this.jtableContext = jtableContext;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.entityList = jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        this.formKeys = formKeys;
        this.formAccessLevel = formAccessLevel;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public List<EntityViewData> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityViewData> entityList) {
        this.entityList = entityList;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Consts.FormAccessLevel getFormAccessLevel() {
        return this.formAccessLevel;
    }

    public void setFormAccessLevel(Consts.FormAccessLevel formAccessLevel) {
        this.formAccessLevel = formAccessLevel;
    }

    public Set<String> getIgnoreItems() {
        return this.ignoreItems;
    }

    public void setIgnoreItems(Set<String> ignoreItems) {
        this.ignoreItems = ignoreItems;
    }
}

