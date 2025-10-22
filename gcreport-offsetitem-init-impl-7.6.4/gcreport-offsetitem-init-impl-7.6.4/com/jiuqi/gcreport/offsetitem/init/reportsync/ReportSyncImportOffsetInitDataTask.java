/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.reportsync.ReportSyncImportOffsetDataTask
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.offsetitem.init.reportsync;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.reportsync.ReportSyncImportOffsetDataTask;
import com.jiuqi.np.period.PeriodWrapper;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncImportOffsetInitDataTask
implements IReportSyncImportTask {
    private final String OFFSET_FOLDER_NAME = "GC-data-offset-init";
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        ConsolidatedSystemEO eo;
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO;
        String systemId;
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"GC-data-offset-init");
        ArrayList<String> msgList = new ArrayList<String>();
        String json = CommonReportUtil.readJsonFile((String)(filePath + "/param.txt"));
        if (CommonReportUtil.isEmptyJson((String)json)) {
            return null;
        }
        Map code2ParentidMap = new HashMap();
        String subjectCodeJson = CommonReportUtil.readJsonFile((String)(filePath + "/subjectCode.txt"));
        if (!CommonReportUtil.isEmptyJson((String)subjectCodeJson)) {
            code2ParentidMap = (Map)JsonUtils.readValue((String)subjectCodeJson, HashMap.class);
        }
        if ((systemId = (offsetItemInitQueryParamsVO = this.getOffsetItemInitQueryParamsVO(json)).getSystemId()) != null && !systemId.isEmpty() && (eo = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId))) == null) {
            msgList.add("\u5408\u5e76\u4f53\u7cfb\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u4e0b\u53d1\u81f3\u4e0b\u7ea7\u66f4\u65b0");
            return msgList;
        }
        List currServerSubjects = this.subjectService.listAllSubjectsBySystemId(offsetItemInitQueryParamsVO.getSystemId());
        Map<String, ConsolidatedSubjectEO> currSubjectCode2SubjectMap = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, entity -> entity, (f1, f2) -> f1));
        for (File file : new File(filePath).listFiles()) {
            if (file.getName().startsWith("param")) continue;
            offsetItemInitQueryParamsVO.setOrgId(file.getName());
            HashSet<String> mrecids = new HashSet<String>();
            this.offSetVchrItemInitDao.queryMrecids(offsetItemInitQueryParamsVO, mrecids);
            this.offSetVchrItemInitDao.deleteByMrecid(mrecids, offsetItemInitQueryParamsVO.getAcctYear(), null);
        }
        int totalNum = 0;
        for (File file : new File(filePath).listFiles()) {
            if (file.getName().startsWith("param")) continue;
            offsetItemInitQueryParamsVO.setOrgId(file.getName());
            int num = ReportSyncImportOffsetDataTask.readBase64Db((String)file.getPath(), (String)"GC_OFFSETVCHRITEM_INIT", (String)offsetItemInitQueryParamsVO.getSystemId(), code2ParentidMap, currSubjectCode2SubjectMap);
            totalNum += num;
        }
        msgList.add(String.format("\u6210\u529f\u5bfc\u5165%1$d\u6761", totalNum));
        return msgList;
    }

    private OffsetItemInitQueryParamsVO getOffsetItemInitQueryParamsVO(String json) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = (OffsetItemInitQueryParamsVO)JsonUtils.readValue((String)json, OffsetItemInitQueryParamsVO.class);
        PeriodWrapper periodWrapper = new PeriodWrapper(offsetItemInitQueryParamsVO.getPeriodStr());
        offsetItemInitQueryParamsVO.setAcctYear(Integer.valueOf(periodWrapper.getYear()));
        offsetItemInitQueryParamsVO.setAcctPeriod(Integer.valueOf(periodWrapper.getPeriod()));
        offsetItemInitQueryParamsVO.setOrgType(offsetItemInitQueryParamsVO.getOrgType());
        offsetItemInitQueryParamsVO.setSystemId(offsetItemInitQueryParamsVO.getSystemId());
        return offsetItemInitQueryParamsVO;
    }

    public String funcTitle() {
        return "\u62b5\u9500\u521d\u59cb\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

