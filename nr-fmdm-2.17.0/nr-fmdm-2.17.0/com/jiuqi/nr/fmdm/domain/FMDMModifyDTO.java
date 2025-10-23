/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.fmdm.common.FMDMModifyTypeEnum;
import com.jiuqi.nr.fmdm.domain.FMDMQueryDTO;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class FMDMModifyDTO
extends FMDMQueryDTO {
    private Map<String, Object> modifyValue;
    private Map<String, Object> entityModify;
    private Map<String, Object> dataModify;
    private Map<String, Object> infoModify;
    private String entityKey;
    private boolean ignoreCheck;
    private String formulaSchemeKey;
    private boolean ignoreAccess = true;
    private FMDMModifyTypeEnum modifyType = FMDMModifyTypeEnum.UNDEFINED;
    private IProviderStore providerStore;
    private final Set<String> ignorePermissions = new HashSet<String>();

    public Map<String, Object> getModifyValueMap() {
        if (this.modifyValue == null) {
            this.modifyValue = new HashMap<String, Object>(16);
        }
        return this.modifyValue;
    }

    protected Map<String, Object> getModifyValue() {
        if (this.modifyValue == null) {
            this.modifyValue = new HashMap<String, Object>(16);
        }
        return this.modifyValue;
    }

    public Map<String, Object> getEntityModify() {
        if (this.entityModify == null) {
            this.entityModify = new HashMap<String, Object>(16);
        }
        return this.entityModify;
    }

    public Map<String, Object> getDataModify() {
        if (this.dataModify == null) {
            this.dataModify = new HashMap<String, Object>(16);
        }
        return this.dataModify;
    }

    public Map<String, Object> getInfoModify() {
        if (this.infoModify == null) {
            this.infoModify = new HashMap<String, Object>(16);
        }
        return this.infoModify;
    }

    protected void putValue(String code, Object value) {
        this.getModifyValue().put(code, value);
    }

    protected void putEntityValue(String code, Object value) {
        this.getEntityModify().put(code, value);
        Object dataValue = this.getDataModify().get(code);
        if (dataValue != null) {
            return;
        }
        this.putValue(code, value);
    }

    protected void putDataValue(String code, Object value) {
        this.getDataModify().put(code, value);
        this.putValue(code, value);
    }

    protected void putInfoValue(String code, Object value) {
        this.getInfoModify().put(code, value);
        this.putValue(code, value);
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public void setIgnoreCheck(boolean ignoreCheck) {
        this.ignoreCheck = ignoreCheck;
    }

    public boolean isIgnoreCheck() {
        return this.ignoreCheck;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public boolean isCompatibility() {
        return !CollectionUtils.isEmpty(this.getModifyValueMap()) && CollectionUtils.isEmpty(this.getEntityModify()) && CollectionUtils.isEmpty(this.getDataModify()) && CollectionUtils.isEmpty(this.getInfoModify());
    }

    protected void setModifyValue(Map<String, Object> modifyValue) {
        this.modifyValue = modifyValue;
    }

    protected void setEntityModify(Map<String, Object> entityModify) {
        this.entityModify = entityModify;
    }

    protected void setDataModify(Map<String, Object> dataModify) {
        this.dataModify = dataModify;
    }

    public boolean isIgnoreAccess() {
        return this.ignoreAccess;
    }

    public void setIgnoreAccess(boolean ignoreAccess) {
        this.ignoreAccess = ignoreAccess;
    }

    public FMDMModifyTypeEnum getModifyType() {
        return this.modifyType;
    }

    public void setModifyType(FMDMModifyTypeEnum modifyType) {
        this.modifyType = modifyType;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public Set<String> getIgnorePermissions() {
        return this.ignorePermissions;
    }
}

