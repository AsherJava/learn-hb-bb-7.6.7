/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.bi.dataset.storage.DataSetStorageManager
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financialcubes.query.redisservice;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.DataSetStorageManager;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gc.financialcubes.query.dto.AnalysePenetrationContextInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AnalyseTablePenetrationMap {
    public Map<String, String> getAnalyseMap(AnalysePenetrationContextInfo analyseContext) {
        if (analyseContext == null || analyseContext.getAnalyseTableId() == null || analyseContext.getAnalyseTableType() == null) {
            throw new BusinessRuntimeException("\u5206\u6790\u8868\u6570\u636e\u96c6\u7f16\u53f7\u6216\u8005\u7c7b\u578b\u4e3a\u7a7a");
        }
        return this.buildDataMap(analyseContext);
    }

    private Map<String, String> buildDataMap(AnalysePenetrationContextInfo analyseContext) {
        List fields;
        DataSetStorageManager dsMgr = DataSetStorageManager.getInstance();
        try {
            DSModel dsModel = dsMgr.findModel(analyseContext.getAnalyseTableId(), analyseContext.getAnalyseTableType());
            fields = dsModel.getFields();
        }
        catch (DataSetStorageException e) {
            throw new BusinessRuntimeException("\u6536\u96c6\u5206\u6790\u8868\u5b57\u6bb5\u6620\u5c04\u51fa\u9519");
        }
        if (fields != null) {
            HashMap<String, String> fieldMap = new HashMap<String, String>();
            for (DSField field : fields) {
                String key = field.getName();
                String value = field.getMessageAlias();
                fieldMap.put(key, value);
            }
            return fieldMap;
        }
        return Collections.emptyMap();
    }
}

