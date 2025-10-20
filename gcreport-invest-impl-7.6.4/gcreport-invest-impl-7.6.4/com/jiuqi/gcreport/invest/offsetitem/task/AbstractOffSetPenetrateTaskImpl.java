/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.task.OffSetPenetrateTask
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.task.OffSetPenetrateTask;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractOffSetPenetrateTaskImpl
implements OffSetPenetrateTask {
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;

    public List<GcOffSetPenetrateVO> getPenetrateData(String mrecid, String ruleId, String orgType) {
        List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS = this.queryOffsetData(mrecid);
        HashMap<String, List<String>> debitSubject2FormulaMap = new HashMap<String, List<String>>();
        HashMap<String, List<String>> creditSubject2FormulaMap = new HashMap<String, List<String>>();
        this.initSubject2FormulaMap(debitSubject2FormulaMap, creditSubject2FormulaMap, ruleId);
        List<GcOffSetPenetrateVO> gcOffSetPenetrateVOS = offSetVchrItemAdjustEOS.stream().map(offsetEO -> {
            GcOffSetPenetrateVO offSetPenetrateVO = new GcOffSetPenetrateVO();
            BeanUtils.copyProperties(offsetEO, offSetPenetrateVO);
            String subjectCode = offSetPenetrateVO.getSubjectCode();
            ConsolidatedSubjectEO subjectEO = this.consolidatedSubjectService.getSubjectByCode(offSetPenetrateVO.getSystemId(), subjectCode);
            if (subjectEO != null) {
                offSetPenetrateVO.setSubjectName(subjectEO.getTitle());
            }
            YearPeriodObject yp = new YearPeriodObject(null, offsetEO.getDefaultPeriod());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            this.setUnitAndOppUnitName((GcOffSetVchrItemAdjustEO)offsetEO, offSetPenetrateVO, tool);
            if (offsetEO.getOrient() == OrientEnum.D.getValue()) {
                offSetPenetrateVO.setCreditStr(NumberUtils.doubleToString((Double)offsetEO.getOffSetDebit()));
                this.setPenetrateDetail((Map<String, List<String>>)debitSubject2FormulaMap, (GcOffSetVchrItemAdjustEO)offsetEO, offSetPenetrateVO, tool, orgType);
            } else if (offsetEO.getOrient() == OrientEnum.C.getValue()) {
                offSetPenetrateVO.setDebitStr(NumberUtils.doubleToString((Double)offsetEO.getOffSetCredit()));
                this.setPenetrateDetail((Map<String, List<String>>)creditSubject2FormulaMap, (GcOffSetVchrItemAdjustEO)offsetEO, offSetPenetrateVO, tool, orgType);
            }
            return offSetPenetrateVO;
        }).collect(Collectors.toList());
        return gcOffSetPenetrateVOS;
    }

    private void setUnitAndOppUnitName(GcOffSetVchrItemAdjustEO offsetEO, GcOffSetPenetrateVO offSetPenetrateVO, GcOrgCenterService tool) {
        GcOrgCacheVO unitVO = tool.getOrgByCode(offsetEO.getUnitId());
        offSetPenetrateVO.setUnitName(unitVO.getCode() + "|" + unitVO.getTitle());
        GcOrgCacheVO oppUnitVo = tool.getOrgByCode(offsetEO.getOppUnitId());
        offSetPenetrateVO.setOppUnitName(oppUnitVo.getCode() + "|" + oppUnitVo.getTitle());
    }

    private void initSubject2FormulaMap(Map<String, List<String>> debitSubject2FormulaMap, Map<String, List<String>> creditSubject2FormulaMap, String ruleId) {
        UnionRuleVO ruleVO = this.ruleService.selectUnionRuleById(ruleId);
        String jsonString = ruleVO.getJsonString();
        JSONObject jsonObject = new JSONObject(jsonString);
        debitSubject2FormulaMap.putAll(this.getSubject2FormulaMap(jsonObject.getJSONArray("debitItemList")));
        creditSubject2FormulaMap.putAll(this.getSubject2FormulaMap(jsonObject.getJSONArray("creditItemList")));
    }

    private List<GcOffSetVchrItemAdjustEO> queryOffsetData(String mrecid) {
        List offSetVchrItemAdjustEOS = this.offsetCoreService.listByWhere(new String[]{"mrecid"}, new Object[]{mrecid});
        Comparator comparator = (record1, record2) -> {
            int result = record2.getOrient() - record1.getOrient();
            if (result == 0) {
                result = record1.getSubjectCode().compareTo(record2.getSubjectCode());
            }
            if (result == 0) {
                result = record1.getId().compareTo(record2.getId());
            }
            return result;
        };
        offSetVchrItemAdjustEOS.sort(comparator);
        return offSetVchrItemAdjustEOS;
    }

    private Map<String, List<String>> getSubject2FormulaMap(JSONArray debitItemList) {
        HashMap<String, List<String>> subject2FormulaMap = new HashMap<String, List<String>>();
        for (int i = 0; i < debitItemList.length(); ++i) {
            JSONObject jsonObj = debitItemList.getJSONObject(i);
            if ("null".equals(jsonObj.get("fetchFormula").toString())) continue;
            String fetchFormula = (String)jsonObj.get("fetchFormula");
            JSONObject subjectObj = (JSONObject)jsonObj.get("subjectCode");
            String subjectCode = (String)subjectObj.get("code");
            if (subject2FormulaMap.containsKey(subjectCode)) {
                ((List)subject2FormulaMap.get(subjectCode)).add(fetchFormula);
                continue;
            }
            subject2FormulaMap.put(subjectCode, new ArrayList<String>(Arrays.asList(fetchFormula)));
        }
        return subject2FormulaMap;
    }

    protected abstract void setPenetrateDetail(Map<String, List<String>> var1, GcOffSetVchrItemAdjustEO var2, GcOffSetPenetrateVO var3, GcOrgCenterService var4, String var5);

    protected DimensionValueSet getDimensionValueSet(GcOffSetVchrItemAdjustEO offsetEO, String orgType) {
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)offsetEO.getUnitId(), (Object)offsetEO.getDefaultPeriod(), (Object)offsetEO.getOffSetCurr(), (Object)orgType, (String)offsetEO.getAdjust(), (String)offsetEO.getTaskId());
        return dset;
    }

    protected GcCalcEnvContextImpl getEnv(GcOffSetVchrItemAdjustEO offsetEO, String orgType, GcOrgCenterService tool, String schemeKey) {
        GcCalcArgmentsDTO calcArgmentsDTO = new GcCalcArgmentsDTO();
        calcArgmentsDTO.setPeriodStr(offsetEO.getDefaultPeriod());
        calcArgmentsDTO.setSchemeId(schemeKey);
        calcArgmentsDTO.setTaskId(offsetEO.getTaskId());
        calcArgmentsDTO.setCurrency(offsetEO.getOffSetCurr());
        calcArgmentsDTO.setOrgType(orgType);
        calcArgmentsDTO.setSelectAdjustCode(offsetEO.getAdjust());
        GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(offsetEO.getUnitId()), tool.getOrgByCode(offsetEO.getOppUnitId()));
        calcArgmentsDTO.setOrgId(commonUnit.getId());
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl();
        env.setCalcArgments(calcArgmentsDTO);
        return env;
    }
}

