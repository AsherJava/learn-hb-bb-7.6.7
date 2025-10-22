/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 */
package com.jiuqi.gcreport.clbr.util;

import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClbrTypePenetrationControlUtils {
    public static void validPenetrationControl(List<ClbrBillEO> clbrBillEOS) {
        List relations = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_RELATION", AuthType.ACCESS);
        List clbrTypes = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Map clbrCode2Self = clbrTypes.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, Function.identity()));
        Set thisRelationCodes = relations.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet());
        for (ClbrBillEO clbrBillEO : clbrBillEOS) {
            if (!thisRelationCodes.contains(clbrBillEO.getThisRelation()) && ((GcBaseData)clbrCode2Self.get(clbrBillEO.getClbrType())).getFieldVal("PENETRATIONCONTROL").toString().equals("0")) {
                clbrBillEO.getFields().put("PENETRATIONCONTROL", false);
                continue;
            }
            clbrBillEO.getFields().put("PENETRATIONCONTROL", true);
        }
    }
}

