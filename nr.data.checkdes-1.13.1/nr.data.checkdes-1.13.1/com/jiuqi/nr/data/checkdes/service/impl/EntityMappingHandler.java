/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.MapHandlePar;
import com.jiuqi.nr.data.checkdes.service.internal.IDataMappingHandler;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Order(value=1)
public class EntityMappingHandler
implements IDataMappingHandler {
    @Override
    public CKDTransObj handle(MapHandlePar par, CKDTransObj ckdTransObj) {
        Map<String, String> dims;
        ParamsMapping paramsMapping = par.getParamsMapping();
        ArrayList<String> o = new ArrayList<String>();
        String mainDimName = par.getMainDimName();
        String srcMainDimCode = ckdTransObj.getDimMap().get(mainDimName);
        o.add(srcMainDimCode);
        Map originOrgCode = paramsMapping.getOriginOrgCode(o);
        if (!CollectionUtils.isEmpty(originOrgCode) && originOrgCode.containsKey(srcMainDimCode)) {
            String tarMainDimCode = (String)originOrgCode.get(srcMainDimCode);
            ckdTransObj.getDimMap().put(mainDimName, tarMainDimCode);
        }
        if (!CollectionUtils.isEmpty(dims = par.getDims())) {
            for (Map.Entry<String, String> entry : dims.entrySet()) {
                String dimName = entry.getKey();
                String dimId = entry.getValue();
                ArrayList<String> l = new ArrayList<String>();
                String srcDimValue = ckdTransObj.getDimMap().get(dimName);
                l.add(srcDimValue);
                Map originBaseData = paramsMapping.getOriginBaseData(dimId, l);
                if (CollectionUtils.isEmpty(originBaseData) || !originBaseData.containsKey(srcDimValue)) continue;
                String tarDimValue = (String)originBaseData.get(srcDimValue);
                ckdTransObj.getDimMap().put(dimName, tarDimValue);
            }
        }
        return ckdTransObj;
    }
}

