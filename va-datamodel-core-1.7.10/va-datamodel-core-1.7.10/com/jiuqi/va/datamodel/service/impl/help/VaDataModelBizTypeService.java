/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.DataModelBizType
 */
package com.jiuqi.va.datamodel.service.impl.help;

import com.jiuqi.va.extend.DataModelBizType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelBizTypeService {
    @Autowired(required=false)
    private List<DataModelBizType> bizTypeExtends;

    public List<DataModelBizType> listBizType() {
        if (this.bizTypeExtends != null) {
            Collections.sort(this.bizTypeExtends, (o1, o2) -> o1.getOrdinal() < o2.getOrdinal() ? -1 : 1);
        }
        return this.bizTypeExtends;
    }

    public List<Map<String, Object>> listBizTypeSimple() {
        List<DataModelBizType> list = this.listBizType();
        ArrayList<Map<String, Object>> bizTypeList = new ArrayList<Map<String, Object>>();
        for (DataModelBizType bizType : list) {
            HashMap<String, String> billTypeMap = new HashMap<String, String>(8);
            billTypeMap.put("name", bizType.getName());
            billTypeMap.put("title", bizType.getTitle());
            bizTypeList.add(billTypeMap);
        }
        return bizTypeList;
    }
}

