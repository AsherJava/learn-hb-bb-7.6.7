/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.extend.DataModelBizType
 */
package com.jiuqi.va.datamodel.task;

import com.jiuqi.va.datamodel.service.impl.help.VaDataModelBizTypeService;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.extend.DataModelBizType;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelStorageBizTypeSyncTask
implements StorageSyncTask {
    @Autowired
    private VaDataModelBizTypeService bizTypeService;

    public void execute() {
        List<DataModelBizType> types = this.bizTypeService.listBizType();
        if (types == null) {
            return;
        }
        HashSet<String> hasType = new HashSet<String>();
        for (DataModelType.BizType bizType : DataModelType.BizType.values()) {
            hasType.add(bizType.toString());
        }
        String name = null;
        for (DataModelBizType bizType : types) {
            name = bizType.getName();
            if (hasType.contains(name)) continue;
            hasType.add(DataModelType.BizType.valueOf((String)name).toString());
        }
    }

    public int getSortNum() {
        return -2147483647;
    }

    public boolean needCompareVersion() {
        return false;
    }
}

