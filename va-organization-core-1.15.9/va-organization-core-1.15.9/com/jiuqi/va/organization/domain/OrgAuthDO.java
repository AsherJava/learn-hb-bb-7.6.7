/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.mapper.domain.TenantDTO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.mapper.domain.TenantDTO;
import java.util.HashMap;
import java.util.UUID;

public class OrgAuthDO
extends HashMap<String, Object>
implements TenantDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public Object put(String key, Object value) {
        return super.put(this.convertKey(key), value);
    }

    public String convertKey(String key) {
        if ("tenantName".equalsIgnoreCase(key)) {
            return "tenantName";
        }
        return key.toLowerCase();
    }

    public void setAuthValue(OrgDataOption.AuthType authType, Integer value) {
        this.setAuthValue(("AT" + authType.toString()).toLowerCase(), value);
    }

    public void setAuthValue(String authItem, Integer value) {
        this.put(authItem, (Object)value);
    }

    public Integer getAuthValue(OrgDataOption.AuthType authType) {
        return this.getAuthValue(("AT" + authType.toString()).toLowerCase());
    }

    public Integer getAuthValue(String authItem) {
        Object value = this.get(authItem);
        if (value != null) {
            if (value instanceof Number) {
                return ((Number)value).intValue();
            }
            return Integer.parseInt(value.toString());
        }
        return 0;
    }

    public String getTenantName() {
        if (this.get("tenantName") == null) {
            this.setTenantName(ShiroUtil.getTenantName());
        }
        return (String)this.get("tenantName");
    }

    public void setTenantName(String tenantName) {
        this.put("tenantName", (Object)tenantName);
    }

    public UUID getId() {
        Object id = this.get("id");
        if (id != null) {
            if (id instanceof UUID) {
                return (UUID)id;
            }
            return UUID.fromString(id.toString());
        }
        return null;
    }

    public void setId(UUID id) {
        this.put("id", (Object)id);
    }

    public Integer getBiztype() {
        Object biztype = this.get("biztype");
        if (biztype != null) {
            if (biztype instanceof Number) {
                return ((Number)biztype).intValue();
            }
            return Integer.parseInt(biztype.toString());
        }
        return null;
    }

    public void setBiztype(Integer biztype) {
        this.put("biztype", (Object)biztype);
    }

    public String getBizname() {
        Object bizname = this.get("bizname");
        if (bizname != null) {
            return bizname.toString();
        }
        return null;
    }

    public void setBizname(String bizname) {
        this.put("bizname", (Object)bizname);
    }

    public Integer getAuthtype() {
        Object authtype = this.get("authtype");
        if (authtype != null) {
            if (authtype instanceof Number) {
                return ((Number)authtype).intValue();
            }
            return Integer.parseInt(authtype.toString());
        }
        return null;
    }

    public void setAuthtype(Integer authtype) {
        this.put("authtype", (Object)authtype);
    }

    public String getOrgcategory() {
        Object orgcategory = this.get("orgcategory");
        if (orgcategory != null) {
            return orgcategory.toString();
        }
        return null;
    }

    public void setOrgcategory(String orgcategory) {
        this.put("orgcategory", (Object)orgcategory);
    }

    public String getOrgname() {
        Object orgname = this.get("orgname");
        if (orgname != null) {
            return orgname.toString();
        }
        return null;
    }

    public void setOrgname(String orgname) {
        this.put("orgname", (Object)orgname);
    }

    public Integer getAtmanage() {
        return this.getAuthValue("atmanage");
    }

    public void setAtmanage(Integer atmanage) {
        this.setAuthValue("atmanage", atmanage);
    }

    public Integer getAtaccess() {
        return this.getAuthValue("ataccess");
    }

    public void setAtaccess(Integer ataccess) {
        this.setAuthValue("ataccess", ataccess);
    }

    public Integer getAtwrite() {
        return this.getAuthValue("atwrite");
    }

    public void setAtwrite(Integer atwrite) {
        this.setAuthValue("atwrite", atwrite);
    }

    public Integer getAtedit() {
        return this.getAuthValue("atedit");
    }

    public void setAtedit(Integer atedit) {
        this.setAuthValue("atedit", atedit);
    }

    public Integer getAtreport() {
        return this.getAuthValue("atreport");
    }

    public void setAtreport(Integer atreport) {
        this.setAuthValue("atreport", atreport);
    }

    public Integer getAtsubmit() {
        return this.getAuthValue("atsubmit");
    }

    public void setAtsubmit(Integer atsubmit) {
        this.setAuthValue("atsubmit", atsubmit);
    }

    public Integer getAtapproval() {
        return this.getAuthValue("atapproval");
    }

    public void setAtapproval(Integer atapproval) {
        this.setAuthValue("atapproval", atapproval);
    }

    @Deprecated
    public Integer getAtdelegate() {
        return 0;
    }

    @Deprecated
    public void setAtdelegate(Integer atdelegate) {
    }

    public boolean isEmptyAuth() {
        return this.getAtmanage() == 0 && this.getAtaccess() == 0 && this.getAtwrite() == 0 && this.getAtedit() == 0 && this.getAtreport() == 0 && this.getAtsubmit() == 0 && this.getAtapproval() == 0;
    }
}

