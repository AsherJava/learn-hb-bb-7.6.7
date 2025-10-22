/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckObj;
import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EntityCheckResult
implements Serializable {
    private String unitCode;
    private MultCheckObj checkObj;
    private Map<Integer, Map<String, EntityCheckUpRecord>> resultMap;

    public EntityCheckResult() {
    }

    public EntityCheckResult(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public MultCheckObj getCheckObj() {
        return this.checkObj;
    }

    public void setCheckObj(MultCheckObj checkObj) {
        this.checkObj = checkObj;
    }

    public Map<Integer, Map<String, EntityCheckUpRecord>> getResultMap() {
        return this.resultMap;
    }

    public void setResultMap(Map<Integer, Map<String, EntityCheckUpRecord>> resultMap) {
        this.resultMap = resultMap;
    }
}

