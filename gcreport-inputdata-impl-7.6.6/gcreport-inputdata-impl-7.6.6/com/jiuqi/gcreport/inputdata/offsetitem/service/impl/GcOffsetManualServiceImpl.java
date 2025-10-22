/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetManualService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GcOffsetManualServiceImpl
implements GcOffsetManualService {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;

    @Override
    public ReadWriteAccessDesc getUnitReadWriteAccessDesc(List<Object> unitGuids, DimensionParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        if (unitGuids.size() == 2) {
            GcOrgCacheVO org2;
            GcOrgCacheVO org1 = orgTool.getOrgByCode(unitGuids.get(0).toString());
            GcOrgCacheVO commonUnit = orgTool.getCommonUnit(org1, org2 = orgTool.getOrgByCode(unitGuids.get(1).toString()));
            if (commonUnit != null) {
                queryParamsVO.setOrgId(commonUnit.getCode());
                return new UploadStateTool().writeable(queryParamsVO);
            }
            return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u624b\u52a8\u62b5\u9500\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
        }
        if (unitGuids.size() == 1) {
            GcOrgCacheVO org = orgTool.getOrgByCode(unitGuids.get(0).toString());
            if (StringUtils.isEmpty(org.getParentId())) {
                return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u624b\u52a8\u62b5\u9500\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
            }
            queryParamsVO.setOrgId(org.getParentId());
            return new UploadStateTool().writeable(queryParamsVO);
        }
        return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u624b\u52a8\u62b5\u9500\u5355\u4f4d\u6570\u636e\u5f02\u5e38\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
    }

    @Override
    public void getUnitStatus(List<Object> unitGuids, DimensionParamsVO dimensionParamsVO) {
        ReadWriteAccessDesc readWriteAccessDesc = this.getUnitReadWriteAccessDesc(unitGuids, dimensionParamsVO);
        Assert.isTrue((boolean)readWriteAccessDesc.getAble(), (String)readWriteAccessDesc.getDesc(), (Object[])new Object[0]);
    }

    @Override
    public void setTitles(List<Map<String, Object>> contents, String systemId, String orgType, String periodStr) {
        HashMap<String, String> ruleId2TitleCache = new HashMap<String, String>();
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashMap<String, String> unitCode2TitleCache = new HashMap<String, String>();
        for (Map<String, Object> record : contents) {
            String unionRuleId;
            if (StringUtils.isEmpty(record.get("UNITID"))) continue;
            record.put("UNITTITLE", this.getUnitTitle(unitCode2TitleCache, (String)record.get("UNITID"), tool));
            record.put("OPPUNITTITLE", this.getUnitTitle(unitCode2TitleCache, (String)record.get("OPPUNITID"), tool));
            String subjectCode = (String)record.get("SUBJECTCODE");
            if (null != subjectCode) {
                this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE");
            }
            if (null == (unionRuleId = (String)record.get("UNIONRULEID"))) continue;
            String title = (String)ruleId2TitleCache.get(unionRuleId);
            if (null == title) {
                AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)unionRuleId);
                title = null == rule ? "" : rule.getLocalizedName();
                ruleId2TitleCache.put(unionRuleId, title);
            }
            record.put("UNIONRULETITLE", title);
        }
    }

    private void setSubjectTitle(String systemId, Map<String, Object> record, Map<String, String> subject2TitleCache, String titleKey) {
        List allSubjectEos;
        String subjectCode = (String)record.get("SUBJECTCODE");
        if (null == subjectCode) {
            return;
        }
        if (CollectionUtils.isEmpty(subject2TitleCache) && !CollectionUtils.isEmpty(allSubjectEos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId))) {
            allSubjectEos.forEach(subjectEO -> subject2TitleCache.put(subjectEO.getCode(), subjectEO.getTitle()));
        }
        if (null == subject2TitleCache.get(subjectCode)) {
            ConsolidatedSubjectEO subject = this.consolidatedSubjectService.getSubjectByCode(systemId, subjectCode);
            String title = null == subject ? subjectCode : subject.getTitle();
            subject2TitleCache.put(subjectCode, title);
        }
        record.put(titleKey, subject2TitleCache.get(subjectCode));
    }

    private String getUnitTitle(Map<String, String> unitCode2TitleCache, String unitCode, GcOrgCenterService tool) {
        if (StringUtils.isEmpty(unitCode)) {
            return "";
        }
        String unitTitle = unitCode2TitleCache.get(unitCode);
        if (null == unitTitle) {
            GcOrgCacheVO cacheVO = tool.getOrgByCode(unitCode);
            unitTitle = cacheVO == null ? "" : cacheVO.getTitle();
            unitCode2TitleCache.put(unitCode, unitTitle);
        }
        return unitTitle;
    }

    @Override
    public void setZjTitles(List<Map<String, Object>> contents, boolean showDictCode) {
        DimensionService service = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        List dimensionVOs = service.findDimFieldsVOByTableName("GC_OFFSETVCHRITEM");
        if (null == dimensionVOs) {
            return;
        }
        for (Map<String, Object> record : contents) {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                GcBaseDataDO baseData;
                String fieldDictValue;
                String dictTableName = dimensionVO.getDictTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                if (!StringUtils.isEmpty(dictTableName) && !StringUtils.isEmpty(fieldDictValue = String.valueOf(record.get(fieldCode))) && null != (baseData = (GcBaseDataDO)tool.queryBasedataByCode(dictTableName, fieldDictValue))) {
                    String title = baseData.getTitle();
                    if (showDictCode && !StringUtils.isEmpty(title)) {
                        baseData.setTitle(fieldDictValue + "|" + title);
                    }
                    record.put(fieldCode, baseData);
                }
            });
        }
    }

    @Override
    public JSONObject calcDxje(List<Map<String, Object>> records) {
        JSONObject jsonObject = new JSONObject();
        double totalJfJyje = 0.0;
        double totalDfJyje = 0.0;
        for (Map<String, Object> record : records) {
            Integer jdfx = (Integer)record.get("DC");
            if (null == jdfx) {
                jdfx = OrientEnum.D.getValue();
                record.put("DC", jdfx);
            }
            double jyje = MapUtils.doubleValue(record, (Object)"AMMOUNT");
            if (jdfx == OrientEnum.D.getValue()) {
                totalJfJyje += jyje;
                continue;
            }
            totalDfJyje += jyje;
        }
        double dxje = totalDfJyje;
        int biggerJdfx = OrientEnum.D.getValue();
        double biggerDxje = totalJfJyje;
        if (totalJfJyje < totalDfJyje) {
            dxje = totalJfJyje;
            biggerJdfx = OrientEnum.C.getValue();
            biggerDxje = totalJfJyje;
        }
        if (biggerDxje == 0.0) {
            biggerDxje = 1.0E-6;
        }
        double remainDxje = dxje;
        Map<String, Object> lastBiggerRecord = null;
        for (Map<String, Object> record : records) {
            Integer jdfx = (Integer)record.get("DC");
            double jyje = MapUtils.doubleValue(record, (Object)"AMMOUNT");
            if (jdfx == biggerJdfx) {
                lastBiggerRecord = record;
                double tempDxje = NumberUtils.round((double)(dxje * jyje / biggerDxje));
                remainDxje -= tempDxje;
                record.put("OFFSETVALUE", NumberUtils.doubleToString((double)tempDxje));
                continue;
            }
            record.put("OFFSETVALUE", NumberUtils.doubleToString((double)jyje));
        }
        if (remainDxje != 0.0 && lastBiggerRecord != null) {
            String dxjeStr = (String)lastBiggerRecord.get("OFFSETVALUE");
            double newDxje = Double.parseDouble(dxjeStr.replace(",", "")) + remainDxje;
            lastBiggerRecord.put("OFFSETVALUE", NumberUtils.doubleToString((double)newDxje));
        }
        jsonObject.put("recordData", records);
        jsonObject.put("totalJfJyje", (Object)NumberUtils.doubleToString((double)totalJfJyje));
        jsonObject.put("totalDfJyje", (Object)NumberUtils.doubleToString((double)totalDfJyje));
        jsonObject.put("dxje", (Object)NumberUtils.doubleToString((double)dxje));
        return jsonObject;
    }

    @Override
    public void getInputUnitStatus(List<Object> unitGuids, List<InputDataEO> records, GcCalcArgmentsDTO paramsVO) {
        if (unitGuids.size() == 0 || records.size() == 0) {
            Assert.isTrue((boolean)false, (String)"\u624b\u52a8\u62b5\u9500\u6570\u636e\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        }
        DimensionParamsVO dimensionParamsVO = this.initInputDimensionParamsVO(records.get(0), paramsVO);
        this.getUnitReadWriteAccessDesc(unitGuids, dimensionParamsVO);
    }

    private DimensionParamsVO initInputDimensionParamsVO(InputDataEO record, GcCalcArgmentsDTO paramsVO) {
        DimensionParamsVO queryParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties((Object)record, queryParamsVO);
        queryParamsVO.setPeriodStr(record.getPeriod());
        queryParamsVO.setOrgTypeId(paramsVO.getOrgType());
        queryParamsVO.setOrgType(paramsVO.getOrgType());
        return queryParamsVO;
    }
}

