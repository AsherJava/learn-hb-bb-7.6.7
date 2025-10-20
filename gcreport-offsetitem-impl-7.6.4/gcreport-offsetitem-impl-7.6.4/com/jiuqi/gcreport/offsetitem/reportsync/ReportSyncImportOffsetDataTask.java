/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
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
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  sun.misc.BASE64Decoder
 */
package com.jiuqi.gcreport.offsetitem.reportsync;

import com.jiuqi.common.base.BusinessRuntimeException;
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
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

@Component
public class ReportSyncImportOffsetDataTask
implements IReportSyncImportTask {
    private final String OFFSET_FOLDER_NAME = "GC-data-offset";
    @Autowired
    private GcOffSetAppOffsetService adjustService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"GC-data-offset");
        String json = CommonReportUtil.readJsonFile((String)(filePath + "/param.txt"));
        ArrayList<String> msgList = new ArrayList<String>();
        if (CommonReportUtil.isEmptyJson((String)json)) {
            return null;
        }
        HashMap<String, String> code2ParentidMap = new HashMap();
        String subjectCodeJson = CommonReportUtil.readJsonFile((String)(filePath + "/subjectCode.txt"));
        if (!CommonReportUtil.isEmptyJson((String)subjectCodeJson)) {
            code2ParentidMap = (Map)JsonUtils.readValue((String)subjectCodeJson, HashMap.class);
        }
        QueryParamsVO queryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)json, QueryParamsVO.class);
        PeriodWrapper periodWrapper = new PeriodWrapper(queryParamsVO.getPeriodStr());
        queryParamsVO.setAcctYear(Integer.valueOf(periodWrapper.getYear()));
        queryParamsVO.setAcctPeriod(Integer.valueOf(periodWrapper.getPeriod()));
        String systemId = queryParamsVO.getSystemId();
        if (systemId != null && !systemId.isEmpty()) {
            ConsolidatedSystemEO eo = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
            if (eo == null) {
                msgList.add("\u5408\u5e76\u4f53\u7cfb\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u4e0b\u53d1\u81f3\u4e0b\u7ea7\u66f4\u65b0");
                return msgList;
            }
        } else {
            systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        }
        List currServerSubjects = this.subjectService.listAllSubjectsBySystemId(systemId);
        Map<String, ConsolidatedSubjectEO> currSubjectCode2SubjectMap = currServerSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, entity -> entity, (f1, f2) -> f1));
        queryParamsVO.setSystemId(systemId);
        for (File file : new File(filePath).listFiles()) {
            if (file.getName().startsWith("param")) continue;
            queryParamsVO.setOrgId(file.getName());
            this.adjustService.deleteAllOffsetEntrys(queryParamsVO).size();
        }
        int totalNum = 0;
        for (File file : new File(filePath).listFiles()) {
            if (file.getName().startsWith("param")) continue;
            queryParamsVO.setOrgId(file.getName());
            int num = ReportSyncImportOffsetDataTask.readBase64Db(file.getPath(), "GC_OFFSETVCHRITEM", systemId, code2ParentidMap, currSubjectCode2SubjectMap);
            totalNum += num;
        }
        msgList.add(String.format("\u6210\u529f\u5bfc\u5165%1$d\u6761", totalNum));
        return msgList;
    }

    public static int readBase64Db(String fileName, String tableName, String systemId, Map<String, String> code2ParentidMap, Map<String, ConsolidatedSubjectEO> currSubjectCode2SubjectMap) {
        List defines = CommonReportUtil.getFieldDefines((String)tableName);
        Map<String, ColumnModelType> columnCode2FieldType = defines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getColumnType, (e1, e2) -> e1));
        File txtFile = new File(fileName);
        if (!txtFile.exists()) {
            return 0;
        }
        int rowCount = 0;
        BASE64Decoder decoder = new BASE64Decoder();
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile));){
            String oneRow = reader.readLine();
            String[] columnCodes = oneRow.split(CommonReportUtil.SPLIT_SIGN);
            while ((oneRow = reader.readLine()) != null) {
                oneRow = new String(decoder.decodeBuffer(oneRow));
                DefinitionValueV entity = new DefinitionValueV();
                String[] rowDatas = oneRow.split(CommonReportUtil.SPLIT_SIGN);
                for (int i = 0; i < columnCodes.length && i < rowDatas.length; ++i) {
                    ColumnModelType fieldType;
                    String subCode;
                    if (columnCodes[i].equals("SUBJECTCODE") && ReportSyncImportOffsetDataTask.getExistsSubjectCode(subCode = rowDatas[i], code2ParentidMap, currSubjectCode2SubjectMap) != null) {
                        rowDatas[i] = ReportSyncImportOffsetDataTask.getExistsSubjectCode(subCode, code2ParentidMap, currSubjectCode2SubjectMap);
                    }
                    if (columnCodes[i].equals("DISABLEFLAG")) {
                        if (rowDatas[i].equals("false")) {
                            rowDatas[i] = "0";
                        } else if (rowDatas[i].equals("true")) {
                            rowDatas[i] = "1";
                        }
                    }
                    if ((fieldType = columnCode2FieldType.get(columnCodes[i])) == null) continue;
                    entity.addFieldValue(columnCodes[i], CommonReportUtil.dataTransferOri((ColumnModelType)fieldType, (String)rowDatas[i]));
                }
                if (tableName.equals("GC_OFFSETVCHRITEM")) {
                    entity.getFields().put("SYSTEMID", systemId);
                }
                EntNativeSqlDefaultDao.newInstance((String)tableName, DefinitionValueV.class).add((BaseEntity)entity);
                ++rowCount;
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return rowCount;
    }

    private static String getExistsSubjectCode(String subjectCode, Map<String, String> code2Parentid, Map<String, ConsolidatedSubjectEO> currSubjectCode2SubjectMap) {
        if (!currSubjectCode2SubjectMap.containsKey(subjectCode)) {
            if (!code2Parentid.containsKey(subjectCode)) {
                return null;
            }
            if (code2Parentid.get(subjectCode).equals("-")) {
                return null;
            }
            return ReportSyncImportOffsetDataTask.getExistsSubjectCode(code2Parentid.get(subjectCode), code2Parentid, currSubjectCode2SubjectMap);
        }
        return subjectCode;
    }

    public String funcTitle() {
        return "\u62b5\u9500\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

