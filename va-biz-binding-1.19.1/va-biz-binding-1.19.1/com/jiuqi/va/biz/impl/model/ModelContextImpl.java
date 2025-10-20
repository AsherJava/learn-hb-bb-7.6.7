/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModelContextImpl
implements ModelContext {
    protected final Map<String, Object> map = new HashMap<String, Object>();

    public ModelContextImpl() {
        UserLoginDTO user;
        try {
            user = ShiroUtil.getUser();
        }
        catch (Exception e) {
            user = null;
        }
        this.map.put("TenantName", Env.getTenantName());
        if (user != null) {
            this.map.put("UnitCode", user.getLoginUnit());
            this.map.put("UserCode", user.getUsername());
            this.map.put("BizDate", Utils.toDate(user.getLoginDate()));
        }
    }

    public void setContextValue(String key, Object value) {
        this.map.put(key, value);
    }

    public Object getContextValue(String key) {
        return this.map.get(key);
    }

    @Override
    public String getUnitCode() {
        return (String)this.map.get("UnitCode");
    }

    public void setUnitCode(String value) {
        this.map.put("UnitCode", value);
    }

    @Override
    public String getUserCode() {
        return (String)this.map.get("UserCode");
    }

    public void setUserCode(String value) {
        this.map.put("UserCode", value);
    }

    @Override
    public Date getBizDate() {
        return (Date)this.map.get("BizDate");
    }

    public void setBizDate(Date value) {
        this.map.put("BizDate", Utils.toDate(value));
    }

    @Override
    public String getTenantName() {
        return (String)this.map.get("TenantName");
    }

    public void setTenantName(String value) {
        this.map.put("TenantName", value);
    }

    @Override
    public String getTriggerOrigin() {
        return (String)this.map.get("TriggerOrigin");
    }

    public void setTriggerOrigin(String value) {
        this.map.put("TriggerOrigin", value);
    }

    @Override
    public boolean isPreview() {
        Object isPreview = this.map.get("Preview");
        return isPreview == null ? false : Convert.cast(isPreview, Boolean.TYPE);
    }

    public void setPreview(boolean isPreview) {
        this.map.put("Preview", isPreview);
    }

    public Map<String, Object> getMap() {
        return this.map;
    }
}

