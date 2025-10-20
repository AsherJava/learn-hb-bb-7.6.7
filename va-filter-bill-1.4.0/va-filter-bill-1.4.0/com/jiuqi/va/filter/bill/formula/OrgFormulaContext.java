/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.FormulaContext
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.va.biz.impl.model.FormulaContext;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OrgFormulaContext
extends FormulaContext {
    private static final String ORG = "org";
    private static final String ORG_DEFINE = "orgDefine";
    private Map<String, Object> variables;

    public void put(String key, Object value) {
        this.variables.put(key, value);
    }

    public Object get(String key) {
        return this.variables.get(key);
    }

    public OrgFormulaContext(final OrgDTO orgDTO, final List<FunRefDataDefine> dataDefines) {
        this.variables = new HashMap<String, Object>(){
            private static final long serialVersionUID = 1L;
            {
                this.put(OrgFormulaContext.ORG, orgDTO);
                this.put(OrgFormulaContext.ORG_DEFINE, dataDefines);
            }
        };
    }

    public OrgDO getOrg() {
        return (OrgDO)Convert.cast((Object)this.variables.get(ORG), OrgDO.class);
    }

    public Stream<FunRefDataDefine> getOrgDefine() {
        return ((List)Convert.cast((Object)this.variables.get(ORG_DEFINE), List.class)).stream();
    }

    public void setOrg(OrgDO orgDO) {
        this.variables.put(ORG, orgDO);
    }
}

