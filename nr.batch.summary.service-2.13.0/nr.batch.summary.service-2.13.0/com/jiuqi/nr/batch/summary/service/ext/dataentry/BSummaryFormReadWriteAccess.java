/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.batch.summary.service.ext.dataentry;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BSummaryFormReadWriteAccess
implements IDataExtendAccessItemService {
    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        JtableContext context;
        Map variableMap;
        Object params = param.getParams();
        if (params instanceof JtableContext && (variableMap = (context = (JtableContext)params).getVariableMap()) != null && (variableMap.containsKey("batchGatherSchemeCode") || variableMap.containsKey("batchShowSchemeCodes"))) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public String name() {
        return "BATCH_SUMMARY_FORM_RWA";
    }

    public boolean isServerAccess() {
        return true;
    }

    public IAccessMessage getAccessMessage() {
        return code -> "\u6c47\u603b\u65b9\u6848\u4e2d\u7684\u6570\u636e\u4e0d\u5141\u8bb8\u7f16\u8f91\uff01";
    }

    public int getOrder() {
        return 1;
    }
}

