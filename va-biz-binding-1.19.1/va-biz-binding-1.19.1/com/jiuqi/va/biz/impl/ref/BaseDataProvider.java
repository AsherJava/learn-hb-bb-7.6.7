/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.ref;

import com.jiuqi.va.biz.impl.ref.BaseAllDataProvider;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.ref.RefTableDataProvider;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataProvider
implements RefTableDataProvider {
    @Autowired
    private BaseAllDataProvider baseAllDataProvider;

    @Override
    public int getRefTableType() {
        return 1;
    }

    @Override
    public Map<String, Object> convertDimValues(String tableName, Map<String, Object> dimValues) {
        if (dimValues == null || !dimValues.containsKey("UNITCODE") || !dimValues.containsKey("BIZDATE")) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.basedataprovider.mustsetorganddate"));
        }
        return dimValues;
    }

    @Override
    public RefTableDataMap getRefTableDataMap(String tableName, Map<String, Object> dimValues) {
        return this.baseAllDataProvider.getRefTableDataMap(tableName, dimValues);
    }
}

