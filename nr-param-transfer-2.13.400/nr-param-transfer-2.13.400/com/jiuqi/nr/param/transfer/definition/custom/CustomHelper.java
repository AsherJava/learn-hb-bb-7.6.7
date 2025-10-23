/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.param.transfer.definition.custom;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;

public class CustomHelper {
    public static final String SPLIT = "!";

    public static BusinessNode createBusiness(IMetaItem parentData, IMetaItem data, TransferNodeType type, String parent) {
        BusinessNode customBusiness = new BusinessNode();
        String jointKey = parentData.getKey().concat(SPLIT).concat(data.getKey());
        customBusiness.setGuid(TransferGuidParse.toBusinessId(type, jointKey));
        customBusiness.setName(data.getTitle());
        customBusiness.setTitle(data.getTitle());
        customBusiness.setType(type.name());
        customBusiness.setTypeTitle(type.getTitle());
        customBusiness.setFolderGuid(parent);
        return customBusiness;
    }

    public static String[] splitKey(String key) {
        if (null == key) {
            return null;
        }
        return key.split(SPLIT);
    }
}

