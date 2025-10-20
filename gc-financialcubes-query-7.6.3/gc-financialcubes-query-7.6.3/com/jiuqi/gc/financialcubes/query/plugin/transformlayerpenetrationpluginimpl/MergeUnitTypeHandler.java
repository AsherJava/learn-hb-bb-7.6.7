/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.impl.SubjectCacheProvider
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gc.financialcubes.query.plugin.transformlayerpenetrationpluginimpl;

import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.impl.SubjectCacheProvider;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;
import com.jiuqi.gc.financialcubes.query.extend.FinancialCubesPenetrateCacheManage;
import com.jiuqi.gc.financialcubes.query.plugin.TransformLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationTaskUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MergeUnitTypeHandler
implements TransformLayerPenetrationPlugin {
    @Autowired
    private FinancialCubesPenetrateCacheManage financialCubesPenetrateCacheManage;
    @Autowired
    private PenetrationTaskUtils penetrationTaskUtils;
    @Autowired
    private SubjectCacheProvider cacheProvider;

    @Override
    public UnitType getType() {
        return UnitType.MERGE;
    }

    @Override
    public String convert(PenetrationParamDTO dto, PenetrationContextInfo context) {
        return this.financialCubesPenetrateCacheManage.savePenetrateContext(this.convertMergeTable(dto, context));
    }

    private String convertMergeTable(PenetrationParamDTO penetrationParamDTO, PenetrationContextInfo context) {
        if (!MapUtils.isEmpty(penetrationParamDTO.getData()) || penetrationParamDTO.getUnitCode() != null) {
            throw new BusinessRuntimeException("\u76ee\u524d\u5408\u5e76\u5355\u4f4d\u7a7f\u900f\u4e0d\u652f\u6301\u79d1\u76ee\u4ee5\u5916\u7684\u7ef4\u5ea6");
        }
        try {
            HashMap<String, Object> targetFormat = new HashMap<String, Object>();
            PeriodWrapper periodWrapper = new PeriodWrapper(penetrationParamDTO.getDataTime());
            Pair<String, String> taskInfo = this.penetrationTaskUtils.getTaskIdByPeriodAndOrgType(context, penetrationParamDTO);
            String taskId = (String)taskInfo.getFirst();
            String schemeId = this.penetrationTaskUtils.getSchemeIdByPeriodAndTaskId(penetrationParamDTO.getDataTime(), taskId);
            HashMap<String, Object> queryDataCondtion = new HashMap<String, Object>();
            queryDataCondtion.put("taskID", taskId);
            queryDataCondtion.put("schemeID", schemeId);
            queryDataCondtion.put("org_type", penetrationParamDTO.getMdGcOrgType());
            queryDataCondtion.put("orgid", penetrationParamDTO.getMdCode());
            queryDataCondtion.put("adjustment", "00000000-0000-0000-0000-000000000001");
            queryDataCondtion.put("isAll", false);
            queryDataCondtion.put("pageNum", 1);
            queryDataCondtion.put("pageSize", 50);
            queryDataCondtion.put("isFilterZero", false);
            List<String> subjectCodes = Arrays.asList(penetrationParamDTO.getSubjectCode().split(","));
            ArrayList baseSubjectProp = new ArrayList();
            for (String code : subjectCodes) {
                HashMap<String, String> subjectVo = new HashMap<String, String>();
                subjectVo.put("code", code.trim());
                subjectVo.put("key", code.trim());
                List filteredList = this.cacheProvider.list().stream().filter(subject -> subject.getCode().equals(code.trim())).collect(Collectors.toList());
                if (!filteredList.isEmpty()) {
                    subjectVo.put("title", ((SubjectDTO)filteredList.get(0)).getShowTitle());
                }
                baseSubjectProp.add(subjectVo);
            }
            ArrayList<String> baseSubjectCodes = new ArrayList<String>(subjectCodes);
            queryDataCondtion.put("baseSubjectProp", baseSubjectProp);
            queryDataCondtion.put("baseSubjectCodes", baseSubjectCodes);
            queryDataCondtion.put("isFilterSubject", true);
            queryDataCondtion.put("periodStr", penetrationParamDTO.getDataTime());
            queryDataCondtion.put("acctYear", periodWrapper.getYear());
            queryDataCondtion.put("acctPeriod", periodWrapper.getPeriod());
            queryDataCondtion.put("periodTypeChar", PeriodUtil.convertType2Str((int)periodWrapper.getType()));
            queryDataCondtion.put("periodType", periodWrapper.getType());
            queryDataCondtion.put("selectAdjustCode", "0");
            queryDataCondtion.put("showTitle", periodWrapper.getYear() + "\u5e74" + periodWrapper.getPeriod() + "\u6708");
            HashMap<String, Object> condition = new HashMap<String, Object>();
            condition.put("taskId", taskId);
            condition.put("schemeId", schemeId);
            condition.put("acctYear", periodWrapper.getYear());
            condition.put("acctPeriod", periodWrapper.getPeriod());
            condition.put("date", penetrationParamDTO.getDataTime());
            condition.put("selectAdjustCode", "0");
            condition.put("showTitle", periodWrapper.getYear() + "\u5e74" + periodWrapper.getPeriod() + "\u6708");
            condition.put("periodType", periodWrapper.getType());
            condition.put("unitDefine", penetrationParamDTO.getMdGcOrgType());
            condition.put("periodTypeChar", PeriodUtil.convertType2Str((int)periodWrapper.getType()));
            condition.put("periodStr", penetrationParamDTO.getDataTime());
            HashMap<String, String> singleOrgParam = new HashMap<String, String>();
            singleOrgParam.put("orgType", penetrationParamDTO.getMdGcOrgType());
            singleOrgParam.put("orgVerCode", penetrationParamDTO.getDataTime());
            singleOrgParam.put("orgCode", penetrationParamDTO.getMdCode());
            singleOrgParam.put("authType", "READ");
            targetFormat.put("queryDataCondtion", queryDataCondtion);
            targetFormat.put("condition", condition);
            targetFormat.put("unitType", penetrationParamDTO.getUnitType());
            targetFormat.put("singleOrgParam", singleOrgParam);
            return JSONUtil.toJSONString(targetFormat);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u53c2\u6570\u8f6c\u6362\u9519\u8bef", (Throwable)e);
        }
    }
}

