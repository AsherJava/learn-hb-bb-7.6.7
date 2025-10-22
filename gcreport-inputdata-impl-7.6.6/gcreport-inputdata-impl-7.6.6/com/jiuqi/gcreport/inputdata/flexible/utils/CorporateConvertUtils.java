/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.inputdata.flexible.utils;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.EnumSet;

public class CorporateConvertUtils {
    public static void convertToOffsetUnit(InputDataEO inputDataEo) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)inputDataEo.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(null, inputDataEo.getPeriod()));
        String unitId = inputDataEo.getUnitId();
        String unitCorporateOrgCode = CorporateConvertUtils.getCorporateUnitId(unitId, instance);
        String oppUnitId = inputDataEo.getOppUnitId();
        String oppCorporateOrgCode = CorporateConvertUtils.getCorporateUnitId(oppUnitId, instance);
        if (unitCorporateOrgCode != null && !unitCorporateOrgCode.equals(oppCorporateOrgCode)) {
            inputDataEo.setConvertOffsetOrgFlag(true);
            inputDataEo.setOffsetOrgCode(unitCorporateOrgCode);
            inputDataEo.setOffsetOppUnitId(oppCorporateOrgCode == null ? oppUnitId : oppCorporateOrgCode);
        } else if (oppCorporateOrgCode != null && !oppCorporateOrgCode.equals(unitCorporateOrgCode)) {
            inputDataEo.setConvertOffsetOrgFlag(true);
            inputDataEo.setOffsetOrgCode(unitId);
            inputDataEo.setOffsetOppUnitId(oppCorporateOrgCode);
        } else {
            inputDataEo.setConvertOffsetOrgFlag(false);
            inputDataEo.setOffsetOrgCode(unitId);
            inputDataEo.setOffsetOppUnitId(oppUnitId);
        }
    }

    public static String getCorporateUnitId(String unitId, GcOrgCenterService instance) {
        GcOrgCacheVO unitCacheVO = instance.getOrgByCode(unitId);
        Object unitCorporate = unitCacheVO.getTypeFieldValue("FRDWDM");
        if (unitCorporate == null) {
            return null;
        }
        if (EnumSet.of(GcOrgKindEnum.BASE, GcOrgKindEnum.SINGLE).contains(instance.getOrgByCode(String.valueOf(unitCorporate)).getOrgKind())) {
            return String.valueOf(unitCorporate);
        }
        Object unitName = unitCacheVO.getTypeFieldValue("NAME");
        LogHelper.error((String)"\u591a\u5355\u4f4d\u6cd5\u4eba\u62b5\u6d88", (String)"\u591a\u5355\u4f4d\u6cd5\u4eba\u62b5\u9500\u914d\u7f6e\u9519\u8bef", (String)("\u5355\u4f4d\uff1a" + unitName + "\uff0c\u7684\u6240\u5c5e\u6cd5\u4eba\u5fc5\u987b\u4e3a\u5355\u6237\u5355\u4f4d"));
        throw new RuntimeException("\u591a\u5355\u4f4d\u6cd5\u4eba\u62b5\u9500\u914d\u7f6e\u9519\u8bef\uff0c\u5355\u4f4d\uff1a" + unitName + "\uff0c\u6240\u5c5e\u6cd5\u4eba\u5fc5\u987b\u4e3a\u5355\u6237\u5355\u4f4d");
    }
}

