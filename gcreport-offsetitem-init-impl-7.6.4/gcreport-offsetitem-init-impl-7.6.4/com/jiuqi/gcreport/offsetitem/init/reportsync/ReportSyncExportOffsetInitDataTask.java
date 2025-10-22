/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
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
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.gcreport.offsetitem.init.reportsync;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
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
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.feign.util.FeignUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportOffsetInitDataTask
implements IReportSyncExportTask {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    private final String OFFSET_INIT_FOLDER_NAME = "GC-data-offset-init";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean match(ReportDataSyncParams dataSyncParam) {
        OffsetDataParam param = dataSyncParam.getOffsetInitData();
        return null != param && !CollectionUtils.isEmpty((Collection)param.getUnitVos());
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        ArrayList<String> msgList = new ArrayList<String>();
        ReportDataSyncServerInfoBase syncServerInfoBase = reportSyncExportTaskContext.getReportDataSyncServerInfoBase();
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"GC-data-offset-init");
        OffsetDataParam param = dataSyncParam.getOffsetInitData();
        PeriodWrapper currentPeriod = this.getPeriodWrapper(param);
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = this.getOffsetItemInitQueryParamsVO(param, currentPeriod);
        this.writeParamFile(offsetItemInitQueryParamsVO, filePath);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskId());
        String log = String.format("\u62b5\u9500\u521d\u59cb\u6570\u636e\u5bfc\u51fa\uff0c\u62a5\u8868\u4efb\u52a1:%1$s;\u65f6\u671f:%2$s;\u5355\u4f4d:%3$s\u5bb6;", taskDefine.getTitle(), CommonReportUtil.getPeriodTitle((String)param.getPeriodStr()), param.getUnitVos().size());
        msgList.add(log);
        HashMap<String, ConsolidatedSubjectVO> subjectCode2SubjectMap = new HashMap();
        List currServerSubjects = this.subjectService.listAllSubjectsBySystemId(offsetItemInitQueryParamsVO.getSystemId());
        Map<String, ConsolidatedSubjectEO> currSubjectCode2SubjectMap = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, entity -> entity, (f1, f2) -> f1));
        try {
            subjectCode2SubjectMap = ReportSyncExportOffsetInitDataTask.getSubjectCode2SubjectMap(offsetItemInitQueryParamsVO.getSystemId(), syncServerInfoBase);
        }
        catch (Exception e) {
            this.logger.error("\u4e0a\u7ea7\u67e5\u8be2\u79d1\u76ee\u4ee3\u7801\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage());
            Map<String, String> currCode2Parentid = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getParentCode, (f1, f2) -> f1));
            try {
                CommonReportUtil.writeFileJson(currCode2Parentid, (String)(filePath + "/subjectCode.txt"));
            }
            catch (Exception e2) {
                this.logger.error("\u79d1\u76ee\u5bfc\u51fa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e2.getMessage());
            }
        }
        for (GcOrgCacheVO orgCacheVO : param.getUnitVos()) {
            try {
                if (orgCacheVO.getOrgKind() != GcOrgKindEnum.UNIONORG) continue;
                offsetItemInitQueryParamsVO.setOrgId(orgCacheVO.getCode());
                HashSet<String> mrecids = new HashSet<String>();
                this.offSetVchrItemInitDao.queryMrecids(offsetItemInitQueryParamsVO, mrecids);
                List<GcOffSetVchrItemInitEO> itemInitEOS = this.offSetVchrItemInitDao.queryOffsetingEntryEO(mrecids);
                if (!MapUtils.isEmpty(subjectCode2SubjectMap)) {
                    for (GcOffSetVchrItemInitEO itemInitEO : itemInitEOS) {
                        String subjectCode = ReportSyncExportOffsetInitDataTask.getExistsSubjectCode(itemInitEO.getSubjectCode(), currSubjectCode2SubjectMap, subjectCode2SubjectMap);
                        if (subjectCode == null) continue;
                        itemInitEO.setSubjectCode(subjectCode);
                        itemInitEO.getFields().put("SUBJECTCODE", subjectCode);
                    }
                }
                CommonReportUtil.writeBase64((String)"GC_OFFSETVCHRITEM_INIT", itemInitEOS, (String)(filePath + "/" + orgCacheVO.getCode()));
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return msgList;
    }

    private static String getExistsSubjectCode(String subjectCode, Map<String, ConsolidatedSubjectEO> currSubjectCode2SubjectMap, Map<String, ConsolidatedSubjectVO> subjectCode2SubjectMap) {
        if (!subjectCode2SubjectMap.containsKey(subjectCode)) {
            if (!currSubjectCode2SubjectMap.containsKey(subjectCode)) {
                return null;
            }
            ConsolidatedSubjectEO subjectEO = currSubjectCode2SubjectMap.get(subjectCode);
            return ReportSyncExportOffsetInitDataTask.getExistsSubjectCode(subjectEO.getParentCode(), currSubjectCode2SubjectMap, subjectCode2SubjectMap);
        }
        return subjectCode;
    }

    private OffsetItemInitQueryParamsVO getOffsetItemInitQueryParamsVO(OffsetDataParam param, PeriodWrapper currentPeriod) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = new OffsetItemInitQueryParamsVO();
        offsetItemInitQueryParamsVO.setTaskId(param.getTaskId());
        offsetItemInitQueryParamsVO.setPeriodStr(param.getPeriodStr());
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(offsetItemInitQueryParamsVO.getTaskId(), offsetItemInitQueryParamsVO.getPeriodStr());
        offsetItemInitQueryParamsVO.setSystemId(systemId);
        offsetItemInitQueryParamsVO.setAcctYear(Integer.valueOf(currentPeriod.getYear()));
        offsetItemInitQueryParamsVO.setAcctPeriod(Integer.valueOf(currentPeriod.getPeriod()));
        offsetItemInitQueryParamsVO.setOrgType(param.getOrgType());
        offsetItemInitQueryParamsVO.setSelectAdjustCode(param.getAdjustCode());
        return offsetItemInitQueryParamsVO;
    }

    public static Map<String, ConsolidatedSubjectVO> getSubjectCode2SubjectMap(String systemId, ReportDataSyncServerInfoBase syncServerInfoBase) {
        CommonAuthUtils.initNvwaFeignClientTokenEnv((String)syncServerInfoBase.getTargetUrl(), (String)syncServerInfoBase.getTargetUserName(), (String)syncServerInfoBase.getTargetPwd(), (String)syncServerInfoBase.getTargetEncryptType());
        ConsolidatedSystemClient client = (ConsolidatedSystemClient)FeignUtil.getDynamicClient(ConsolidatedSystemClient.class, (String)syncServerInfoBase.getTargetUrl());
        BusinessResponseEntity subjects = client.listAllSubjectBySystemId(systemId);
        return ((List)subjects.getData()).stream().collect(Collectors.toMap(ConsolidatedSubjectVO::getCode, entity -> entity, (f1, f2) -> f1));
    }

    private PeriodWrapper getPeriodWrapper(OffsetDataParam param) {
        PeriodWrapper currentPeriod;
        if (StringUtils.isEmpty((String)param.getPeriodStr()) && null != param.getPeriodOffset()) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskId());
            currentPeriod = PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)param.getPeriodOffset());
            param.setPeriodStr(currentPeriod.toString());
        } else {
            currentPeriod = new PeriodWrapper(param.getPeriodStr());
        }
        return currentPeriod;
    }

    private void writeParamFile(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO, String filePath) {
        OffsetDataParam offsetDataParam = new OffsetDataParam();
        try {
            offsetDataParam.setTaskId(offsetItemInitQueryParamsVO.getTaskId());
            offsetDataParam.setPeriodStr(offsetItemInitQueryParamsVO.getPeriodStr());
            offsetDataParam.setSystemId(offsetItemInitQueryParamsVO.getSystemId());
            offsetDataParam.setOrgType(offsetItemInitQueryParamsVO.getOrgType());
            CommonReportUtil.writeFileJson((Object)offsetDataParam, (String)(filePath + "/param.txt"));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public String funcTitle() {
        return "\u62b5\u9500\u521d\u59cb\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

