/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.util.BaseDataUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO
 */
package com.jiuqi.gcreport.workingpaper.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.util.BaseDataUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class ArbitrarilyMergeInputAdjustConvertUtil {
    public static ArbitrarilyMergeInputAdjustConvertUtil getInstance() {
        return new ArbitrarilyMergeInputAdjustConvertUtil();
    }

    public ArbitrarilyMergeInputAdjustVO convertRyEOToVO(ArbitrarilyMergeOffSetVchrItemAdjustEO eo, String orgType) {
        String title;
        ArbitrarilyMergeInputAdjustVO vo = new ArbitrarilyMergeInputAdjustVO();
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setSrcID(eo.getSrcOffsetGroupId());
        vo.setUnionRuleId(eo.getRuleId());
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)eo.getRuleId());
        if (null != rule) {
            vo.setRuleTitle(rule.getLocalizedName());
            vo.setRuleParentId(rule.getParentId());
        }
        if (!StringUtils.isEmpty((String)eo.getSubjectCode())) {
            GcBaseData baseDataVO = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)eo.getSubjectCode(), (String[])new String[]{eo.getSystemId()}));
            if (baseDataVO == null) {
                throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u4ee3\u7801\u4e3a[" + eo.getSubjectCode() + "]\u7684\u5408\u5e76\u79d1\u76ee\u3002");
            }
            GcBaseDataVO subjVo = GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(baseDataVO);
            vo.setSubjectVo(subjVo);
            vo.setSubjectName(subjVo.getCode() + "-" + subjVo.getTitle());
        }
        OffSetUnSysDimensionCache.load();
        List dimensionVOs = OffSetUnSysDimensionCache.allDimValue((String)eo.getSystemId());
        if (!CollectionUtils.isEmpty((Collection)dimensionVOs)) {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                String dictTableName = dimensionVO.getDictTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                Object fieldDictValue = eo.getFieldValue(fieldCode);
                if (!StringUtils.isEmpty((String)dictTableName)) {
                    GcBaseData baseData;
                    if (fieldDictValue != null && !StringUtils.isEmpty((String)fieldDictValue.toString()) && null != (baseData = tool.queryBasedataByCode(dictTableName, String.valueOf(fieldDictValue)))) {
                        vo.addUnSysFieldValue(fieldCode, (Object)baseData);
                    }
                } else {
                    vo.addUnSysFieldValue(fieldCode, fieldDictValue);
                }
            });
        }
        if ((title = BaseDataUtils.getDictTitle((String)"MD_GCBUSINESSTYPE", (String)eo.getGcBusinessTypeCode())) != null) {
            vo.setGcBusinessTypeName(title);
        }
        if (!"NONE".equals(eo.getOrgType())) {
            orgType = eo.getOrgType();
        }
        YearPeriodObject yp = new YearPeriodObject(null, vo.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO unitOrg = tool.getOrgByCode(vo.getUnitId());
        GcOrgCacheVO oppUnitOrg = tool.getOrgByCode(vo.getOppUnitId());
        vo.setUnitVo(unitOrg);
        vo.setUnitName(unitOrg.getCode() + " " + unitOrg.getTitle());
        vo.setOppUnitVo(oppUnitOrg);
        vo.setOppUnitName(oppUnitOrg.getCode() + " " + oppUnitOrg.getTitle());
        vo.setDc(eo.getOrient().intValue());
        String offSetCurr = null == eo.getOffSetCurr() ? "CNY" : eo.getOffSetCurr();
        vo.setCurrencyCode(offSetCurr);
        vo.setDebit(ConverterUtils.getAsDouble(eo.getFields().get("OFFSETDEBIT" + offSetCurr)));
        vo.setCredit(ConverterUtils.getAsDouble(eo.getFields().get("OFFSETCREDIT" + offSetCurr)));
        vo.setSrcType(OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue());
        vo.setSelectAdjustCode(eo.getAdjust());
        return vo;
    }
}

