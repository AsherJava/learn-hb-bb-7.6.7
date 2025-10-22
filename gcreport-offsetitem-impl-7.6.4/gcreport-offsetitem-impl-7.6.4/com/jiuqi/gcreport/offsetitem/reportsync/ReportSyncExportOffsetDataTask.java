/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.OffsetDataParam
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.util.CommonAuthUtils
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.gcreport.offsetitem.reportsync;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.OffsetDataParam;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.util.CommonAuthUtils;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.feign.util.FeignUtil;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportOffsetDataTask
implements IReportSyncExportTask {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedSystemClient systemClient;
    private final String OFFSET_FOLDER_NAME = "GC-data-offset";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean match(ReportDataSyncParams dataSyncParam) {
        OffsetDataParam param = dataSyncParam.getOffsetData();
        return null != param && !CollectionUtils.isEmpty((Collection)param.getUnitCodes());
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        ReportDataSyncServerInfoBase serverInfoBase = reportSyncExportTaskContext.getReportDataSyncServerInfoBase();
        OffsetDataParam param = dataSyncParam.getOffsetData();
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(param.getTaskId(), param.getPeriodStr());
        HashMap<String, ConsolidatedSubjectVO> subjectCode2SubjectMap = new HashMap();
        List currServerSubjects = (List)this.systemClient.listAllSubjectBySystemId(systemId).getData();
        Map<String, ConsolidatedSubjectVO> currSubjectCode2SubjectMap = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectVO::getCode, entity -> entity, (f1, f2) -> f1));
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"GC-data-offset");
        try {
            subjectCode2SubjectMap = ReportSyncExportOffsetDataTask.getSubjectCode2SubjectMap(systemId, serverInfoBase);
        }
        catch (Exception e) {
            this.logger.error("\u4e0a\u7ea7\u67e5\u8be2\u79d1\u76ee\u4ee3\u7801\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage());
            Map<String, String> currCode2Parentid = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectVO::getCode, ConsolidatedSubjectVO::getParentCode, (f1, f2) -> f1));
            try {
                CommonReportUtil.writeFileJson(currCode2Parentid, (String)(filePath + "/subjectCode.txt"));
            }
            catch (Exception e2) {
                this.logger.error("\u79d1\u76ee\u5bfc\u51fa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e2.getMessage());
            }
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskId());
        if (StringUtils.isEmpty((String)param.getPeriodStr()) && null != param.getPeriodOffset()) {
            PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)param.getPeriodOffset());
            param.setPeriodStr(currentPeriod.toString());
        }
        String adjustCode = param.getAdjustCode() == null || param.getAdjustCode().isEmpty() ? "0" : param.getAdjustCode();
        String log = String.format("\u62b5\u9500\u5206\u5f55\u6570\u636e\u5bfc\u51fa\uff0c\u62a5\u8868\u4efb\u52a1:%1$s;\u65f6\u671f:%2$s;\u5355\u4f4d:%3$s\u5bb6;", taskDefine.getTitle(), CommonReportUtil.getPeriodTitle((String)param.getPeriodStr()), param.getUnitVos().size());
        dataSyncParam.getLogs().add(log);
        String orgType = param.getOrgType();
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setOrgType(orgType);
        queryParamsVO.setTaskId(param.getTaskId());
        queryParamsVO.setPeriodStr(param.getPeriodStr());
        queryParamsVO.setSelectAdjustCode(adjustCode);
        queryParamsVO.setSystemId(systemId);
        this.writeParamFile(queryParamsVO, filePath);
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        for (String unitCode : param.getUnitCodes()) {
            queryParamsVO.setOrgId(unitCode);
            queryParamsDTO.setOrgId(unitCode);
            List offsetList = this.offsetCoreService.listEOWithFullGroup(queryParamsDTO).getContent();
            for (GcOffSetVchrItemAdjustEO record : offsetList) {
                String subjectCode;
                if (MapUtils.isEmpty(subjectCode2SubjectMap) || (subjectCode = ReportSyncExportOffsetDataTask.getExistsSubjectCode(record.getSubjectCode(), currSubjectCode2SubjectMap, subjectCode2SubjectMap)) == null) continue;
                record.setSubjectCode(subjectCode);
                record.getFields().put("SUBJECTCODE", subjectCode);
            }
            try {
                CommonReportUtil.writeBase64((String)"GC_OFFSETVCHRITEM", (List)offsetList, (String)(filePath + "/" + unitCode));
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private static String getExistsSubjectCode(String subjectCode, Map<String, ConsolidatedSubjectVO> currSubjectCode2SubjectMap, Map<String, ConsolidatedSubjectVO> subjectCode2SubjectMap) {
        if (!subjectCode2SubjectMap.containsKey(subjectCode)) {
            if (!currSubjectCode2SubjectMap.containsKey(subjectCode)) {
                return null;
            }
            ConsolidatedSubjectVO subjectVO = currSubjectCode2SubjectMap.get(subjectCode);
            return ReportSyncExportOffsetDataTask.getExistsSubjectCode(subjectVO.getParentCode(), currSubjectCode2SubjectMap, subjectCode2SubjectMap);
        }
        return subjectCode;
    }

    private void writeParamFile(QueryParamsVO paramsVO, String filePath) {
        OffsetDataParam offsetDataParam = new OffsetDataParam();
        try {
            offsetDataParam.setSystemId(paramsVO.getSystemId());
            offsetDataParam.setTaskId(paramsVO.getTaskId());
            offsetDataParam.setPeriodStr(paramsVO.getPeriodStr());
            offsetDataParam.setOrgType(paramsVO.getOrgType());
            CommonReportUtil.writeFileJson((Object)offsetDataParam, (String)(filePath + "/param.txt"));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public static Map<String, ConsolidatedSubjectVO> getSubjectCode2SubjectMap(String systemId, ReportDataSyncServerInfoBase serverInfoBase) {
        CommonAuthUtils.initNvwaFeignClientTokenEnv((String)serverInfoBase.getTargetUrl(), (String)serverInfoBase.getTargetUserName(), (String)serverInfoBase.getTargetPwd(), (String)serverInfoBase.getTargetEncryptType());
        ConsolidatedSystemClient client = (ConsolidatedSystemClient)FeignUtil.getDynamicClient(ConsolidatedSystemClient.class, (String)serverInfoBase.getTargetUrl());
        BusinessResponseEntity subjects = client.listAllSubjectBySystemId(systemId);
        return ((List)subjects.getData()).stream().collect(Collectors.toMap(ConsolidatedSubjectVO::getCode, entity -> entity, (f1, f2) -> f1));
    }

    public String funcTitle() {
        return "\u62b5\u9500\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

