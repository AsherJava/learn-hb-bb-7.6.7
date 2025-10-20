/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.FormulaContext
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.va.biz.impl.model.FormulaContext;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BaseDataFormulaContext
extends FormulaContext {
    private static final String BASEDATA = "baseData";
    private static final String BAEDATA_DEFINE = "baseDataDefine";
    private Map<String, Object> variables;

    public void put(String key, Object value) {
        this.variables.put(key, value);
    }

    public Object get(String key) {
        return this.variables.get(key);
    }

    public BaseDataFormulaContext(final BaseDataDO baseDataDO, final List<FunRefDataDefine> dataDefines) {
        this.variables = new HashMap<String, Object>(){
            private static final long serialVersionUID = 1L;
            {
                this.put(BaseDataFormulaContext.BASEDATA, baseDataDO);
                this.put(BaseDataFormulaContext.BAEDATA_DEFINE, dataDefines);
            }
        };
    }

    public BaseDataDO getBaseData() {
        return (BaseDataDO)Convert.cast((Object)this.variables.get(BASEDATA), BaseDataDO.class);
    }

    public Stream<FunRefDataDefine> getBaseDataDefine() {
        return ((List)Convert.cast((Object)this.variables.get(BAEDATA_DEFINE), List.class)).stream();
    }

    public void setBaseData(BaseDataDO baseDataDO) {
        this.variables.put(BASEDATA, baseDataDO);
    }
}

