/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.designer.web.service.ReportTaskService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 *  javax.annotation.Resource
 */
package com.jiuqi.gc.financialcubes.query.plugin.transformlayerpenetrationpluginimpl;

import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;
import com.jiuqi.gc.financialcubes.query.extend.FinancialCubesPenetrateCacheManage;
import com.jiuqi.gc.financialcubes.query.plugin.TransformLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationTaskUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.designer.web.service.ReportTaskService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiffrenceUnitTypeHandler
implements TransformLayerPenetrationPlugin {
    @Autowired
    private FinancialCubesPenetrateCacheManage financialCubesPenetrateCacheManage;
    @Autowired
    private PenetrationTaskUtils penetrationTaskUtils;
    @Autowired
    private OrgDataService orgService;
    @Resource
    private ReportTaskService reportTaskService;

    @Override
    public UnitType getType() {
        return UnitType.DIFFERENCE;
    }

    @Override
    public String convert(PenetrationParamDTO dto, PenetrationContextInfo context) {
        return this.financialCubesPenetrateCacheManage.savePenetrateContext(this.convertOffsetTable(dto, context));
    }

    private String convertOffsetTable(PenetrationParamDTO penetrationParamDTO, PenetrationContextInfo context) {
        if (!MapUtils.isEmpty(penetrationParamDTO.getData())) {
            throw new BusinessRuntimeException("\u76ee\u524d\u5dee\u989d\u5355\u4f4d\u7a7f\u900f\u4e0d\u652f\u6301\u79d1\u76ee\u3001\u672c\u5bf9\u65b9\u5355\u4f4d\u4ee5\u5916\u7684\u7ef4\u5ea6:" + penetrationParamDTO.getData().toString());
        }
        try {
            HashMap<String, Object> targetFormat = new HashMap<String, Object>();
            PeriodWrapper periodWrapper = new PeriodWrapper(penetrationParamDTO.getDataTime());
            Pair<String, String> taskInfo = this.penetrationTaskUtils.getTaskIdByPeriodAndOrgType(context, penetrationParamDTO);
            String taskId = (String)taskInfo.getFirst();
            targetFormat.put("taskTitle", taskInfo.getSecond());
            targetFormat.put("taskId", taskId);
            targetFormat.put("schemeId", this.penetrationTaskUtils.getSchemeIdByPeriodAndTaskId(penetrationParamDTO.getDataTime(), taskId));
            targetFormat.put("unitType", penetrationParamDTO.getUnitType());
            targetFormat.put("defaultPeriod", penetrationParamDTO.getDataTime());
            targetFormat.put("periodStr", penetrationParamDTO.getDataTime());
            targetFormat.put("acctYear", periodWrapper.getYear());
            targetFormat.put("acctPeriod", periodWrapper.getPeriod());
            targetFormat.put("periodType", periodWrapper.getType());
            targetFormat.put("currency", penetrationParamDTO.getMdCurrency());
            targetFormat.put("orgType", penetrationParamDTO.getMdGcOrgType());
            targetFormat.put("orgId", penetrationParamDTO.getMdCode());
            targetFormat.put("tabSelect", "OFFSETPAGE");
            targetFormat.put("showTitle", periodWrapper.getYear() + "\u5e74" + periodWrapper.getPeriod() + "\u6708");
            targetFormat.put("selectAdjustCode", "0");
            targetFormat.put("selectAdjust", Collections.emptyMap());
            if (penetrationParamDTO.getUnitCode() != null) {
                this.addOrgNodeToTargetFormat("unitOrgNode", penetrationParamDTO.getUnitCode(), targetFormat);
            }
            if (penetrationParamDTO.getOppoUnitCode() != null) {
                this.addOrgNodeToTargetFormat("oppUnitOrgNode", penetrationParamDTO.getOppoUnitCode(), targetFormat);
            }
            HashMap filterModel = new HashMap();
            HashMap filterCondition = new HashMap();
            HashMap filterConditionDict = new HashMap();
            ArrayList subjectVoList = new ArrayList();
            List<String> subjectCodes = Arrays.asList(penetrationParamDTO.getSubjectCode().split(","));
            for (String string : subjectCodes) {
                HashMap<String, String> subjectVo = new HashMap<String, String>();
                subjectVo.put("code", string.trim());
                subjectVoList.add(subjectVo);
            }
            if (penetrationParamDTO.getData() != null) {
                for (Map.Entry entry : penetrationParamDTO.getData().entrySet()) {
                    ArrayList dictList = new ArrayList();
                    if (!(entry.getValue() instanceof String)) {
                        throw new BusinessRuntimeException("\u503c\u7c7b\u578b\u5f02\u5e38: " + entry.getValue().getClass());
                    }
                    String valueStr = (String)entry.getValue();
                    HashMap<String, String> dictEntry = new HashMap<String, String>();
                    dictEntry.put("code", valueStr);
                    dictList.add(dictEntry);
                    filterConditionDict.put(entry.getKey(), dictList);
                }
            }
            filterModel.put("filterConditionDict", filterConditionDict);
            filterCondition.put("subjectVo", subjectVoList);
            filterModel.put("filterCondition", filterCondition);
            targetFormat.put("filterModel", filterModel);
            return JSONUtil.toJSONString(targetFormat);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u53c2\u6570\u8f6c\u6362\u9519\u8bef", (Throwable)e);
        }
    }

    private void addOrgNodeToTargetFormat(String key, String code, Map<String, Object> targetFormat) {
        if (code != null) {
            HashMap<String, String> orgNode = new HashMap<String, String>();
            orgNode.put("code", code);
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode(code);
            List orgList = this.orgService.list(orgDTO).getRows();
            if (!orgList.isEmpty()) {
                String title = ((OrgDO)orgList.get(0)).getName();
                orgNode.put("title", title);
                targetFormat.put(key, orgNode);
            }
        }
    }
}

