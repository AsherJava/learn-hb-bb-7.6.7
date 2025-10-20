/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.utils.BaseDataUtils;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class GcInputAdjustConvertUtil {
    public static GcInputAdjustConvertUtil getInstance() {
        return new GcInputAdjustConvertUtil();
    }

    public GcInputAdjustVO convertDTOToVO(GcOffSetVchrItemDTO offSetDTO, String orgType) {
        GcOrgCacheVO oppUnitOrg;
        YearPeriodObject yp;
        GcOrgCenterService tool;
        GcOrgCacheVO unitOrg;
        String title;
        GcInputAdjustVO vo = new GcInputAdjustVO();
        BeanUtils.copyProperties(offSetDTO, vo);
        vo.setSrcID(offSetDTO.getSrcOffsetGroupId());
        vo.setUnionRuleId(offSetDTO.getRuleId());
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)offSetDTO.getRuleId());
        if (null != rule) {
            vo.setRuleTitle(rule.getLocalizedName());
            vo.setRuleParentId(rule.getParentId());
        }
        if (!StringUtils.isEmpty((String)offSetDTO.getSubjectCode())) {
            GcBaseData baseDataVO = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)offSetDTO.getSubjectCode(), (String[])new String[]{offSetDTO.getSystemId()}));
            if (baseDataVO == null) {
                throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u4ee3\u7801\u4e3a[" + offSetDTO.getSubjectCode() + "]\u7684\u5408\u5e76\u79d1\u76ee\u3002");
            }
            GcBaseDataVO subjVo = GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(baseDataVO);
            vo.setSubjectVo(subjVo);
            vo.setSubjectName(subjVo.getCode() + "-" + subjVo.getTitle());
        }
        OffSetUnSysDimensionCache.load();
        List dimensionVOs = OffSetUnSysDimensionCache.allDimValue((String)offSetDTO.getSystemId());
        if (!CollectionUtils.isEmpty((Collection)dimensionVOs)) {
            GcBaseDataCenterTool tool2 = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                String dictTableName = dimensionVO.getDictTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                Object fieldDictValue = offSetDTO.getFieldValue(fieldCode);
                if (fieldDictValue == null || StringUtils.isEmpty((String)fieldDictValue.toString())) {
                    return;
                }
                if (StringUtils.isEmpty((String)dictTableName)) {
                    vo.addUnSysFieldValue(fieldCode, fieldDictValue);
                    return;
                }
                if (dictTableName.toUpperCase().startsWith("MD_ORG")) {
                    GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)dictTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, offSetDTO.getDefaultPeriod()));
                    GcOrgCacheVO orgByCode = orgTool.getOrgByCode(ConverterUtils.getAsString((Object)fieldDictValue));
                    if (orgByCode != null) {
                        vo.addUnSysFieldValue(fieldCode, (Object)orgByCode);
                    }
                    return;
                }
                GcBaseData baseData = fieldDictValue instanceof LinkedHashMap ? tool2.queryBasedataByCode(dictTableName, String.valueOf(((LinkedHashMap)fieldDictValue).get("code"))) : tool2.queryBasedataByCode(dictTableName, String.valueOf(fieldDictValue));
                vo.addUnSysFieldValue(fieldCode, (Object)baseData);
            });
        }
        if ((title = BaseDataUtils.getDictTitle("MD_GCBUSINESSTYPE", offSetDTO.getGcBusinessTypeCode())) != null) {
            vo.setGcBusinessTypeName(title);
        }
        if (!"NONE".equals(offSetDTO.getOrgType())) {
            orgType = offSetDTO.getOrgType();
        }
        if (null != (unitOrg = (tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)(yp = new YearPeriodObject(null, vo.getDefaultPeriod())))).getOrgByCode(vo.getUnitId()))) {
            vo.setUnitVo(unitOrg);
            vo.setUnitName(unitOrg.getCode() + " " + unitOrg.getTitle());
        }
        if (null != (oppUnitOrg = tool.getOrgByCode(vo.getOppUnitId()))) {
            vo.setOppUnitVo(oppUnitOrg);
            vo.setOppUnitName(oppUnitOrg.getCode() + " " + oppUnitOrg.getTitle());
        }
        vo.setDc(null == offSetDTO.getOrient() ? 0 : offSetDTO.getOrient().getValue());
        String offSetCurr = null == offSetDTO.getOffSetCurr() ? "CNY" : offSetDTO.getOffSetCurr();
        vo.setCurrencyCode(offSetCurr);
        vo.setDebit(offSetDTO.getDebit());
        vo.setCredit(offSetDTO.getCredit());
        vo.setSrcType(OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue());
        return vo;
    }

    public GcInputAdjustVO convertEOToVO(GcOffSetVchrItemAdjustEO eo, String orgType) {
        String title;
        List dimensionVOs;
        GcInputAdjustVO vo = new GcInputAdjustVO();
        BeanUtils.copyProperties(eo, vo);
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
        if (!CollectionUtils.isEmpty((Collection)(dimensionVOs = ((DimensionService)SpringContextUtils.getBean(DimensionService.class)).findAllDimFieldsVOByTableName("GC_OFFSETVCHRITEM")))) {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                String dictTableName = dimensionVO.getDictTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                Object fieldDictValue = eo.getFieldValue(fieldCode);
                if (fieldDictValue == null || StringUtils.isEmpty((String)fieldDictValue.toString())) {
                    return;
                }
                if (StringUtils.isEmpty((String)dictTableName)) {
                    vo.addUnSysFieldValue(fieldCode, fieldDictValue);
                    return;
                }
                if (dictTableName.toUpperCase().startsWith("MD_ORG")) {
                    GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)dictTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, eo.getDefaultPeriod()));
                    GcOrgCacheVO orgByCode = orgTool.getOrgByCode(ConverterUtils.getAsString((Object)fieldDictValue));
                    if (orgByCode != null) {
                        vo.addUnSysFieldValue(fieldCode, (Object)orgByCode);
                    }
                    return;
                }
                GcBaseData baseData = tool.queryBasedataByCode(dictTableName, String.valueOf(fieldDictValue));
                if (null != baseData) {
                    vo.addUnSysFieldValue(fieldCode, (Object)baseData);
                }
            });
        }
        if ((title = BaseDataUtils.getDictTitle("MD_GCBUSINESSTYPE", eo.getGcBusinessTypeCode())) != null) {
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
        vo.setDebit(ConverterUtils.getAsDouble(eo.getFields().get("OFFSETDEBIT")));
        vo.setCredit(ConverterUtils.getAsDouble(eo.getFields().get("OFFSETCREDIT")));
        vo.setSrcType(OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue());
        return vo;
    }
}

