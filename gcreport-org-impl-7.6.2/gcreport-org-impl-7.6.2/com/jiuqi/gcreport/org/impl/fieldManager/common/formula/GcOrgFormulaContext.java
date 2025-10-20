/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.filter.bill.formula.OrgFormulaContext
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.gcreport.org.impl.fieldManager.common.formula;

import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcOrgFormulaContext
extends OrgFormulaContext {
    private static final String BASEORG = "baseorg";
    private Map<String, Object> dataMap = new HashMap<String, Object>();

    public GcOrgFormulaContext(OrgDTO orgDTO, List<FunRefDataDefine> dataDefines) {
        super(orgDTO, dataDefines);
    }

    public void setOrg(OrgDO orgDO) {
        super.setOrg(orgDO);
        for (String fieldName : orgDO.keySet()) {
            this.dataMap.put(orgDO.getCategoryname() + "_" + fieldName, orgDO.get((Object)fieldName));
        }
    }

    public void setBaseOrg(OrgDO orgDO) {
        for (String fieldName : orgDO.keySet()) {
            this.dataMap.put(orgDO.getCategoryname() + "_" + fieldName, orgDO.get((Object)fieldName));
        }
    }

    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}

