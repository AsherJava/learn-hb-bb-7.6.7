/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import org.springframework.util.ObjectUtils;

public class FMDMAttributeDO
extends DesignColumnModelDefineImpl
implements IFMDMAttribute {
    private String formSchemeKey;
    private String entityId;
    private boolean referEntity;
    private String referEntityId;
    private String ZBKey;

    public void setCode(String code) {
        super.setCode(code);
    }

    public String getCode() {
        return super.getCode();
    }

    @Override
    public boolean isReferEntity() {
        return this.referEntity;
    }

    public void setReferEntity(boolean referEntity) {
        this.referEntity = referEntity;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getReferEntityId() {
        return this.referEntityId;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    @Override
    public String getZBKey() {
        return this.ZBKey;
    }

    public void setZBKey(String ZBKey) {
        this.ZBKey = ZBKey;
    }

    public String getLocaleTitle() {
        I18nHelper i18nHelper = (I18nHelper)SpringBeanUtils.getBean((String)"DataModelManage", I18nHelper.class);
        String localeTitle = i18nHelper.getMessage(super.getID());
        return ObjectUtils.isEmpty(localeTitle) ? super.getTitle() : localeTitle;
    }
}

