/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO
 *  com.jiuqi.gcreport.org.impl.fieldManager.service.GcOrgTypeService
 *  com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService
 *  com.jiuqi.gcreport.org.impl.util.base.OrgParamParse
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.organization.service.OrgVersionService
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.reportdatasync.task.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcOrgTypeService;
import com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncImportMergeUnitDataTask
implements IReportSyncImportTask {
    private final String DATA_FOLDER_NAME = "GC-unitversion";
    @Autowired
    private GcOrgDataService gcOrgDataService;
    @Autowired
    private GcOrgTypeService orgTypeService;
    @Autowired
    private OrgVersionService versionService;

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String dataFolderPath = ReportDataSyncUtil.createNewPath(rootFolder.getPath(), "GC-unitversion");
        ArrayList<String> msgList = new ArrayList<String>();
        Map<String, OrgTypeTreeVO> orgVersionMap = this.getOrgVersion(dataFolderPath);
        File dataFolder = new File(dataFolderPath);
        for (File file : dataFolder.listFiles()) {
            if ("MD_ORG_VERSION".equals(file.getName()) || "MD_ORG_CATEGORY".equals(file.getName()) || !this.checkOrgTypeAndVersion(file.getName(), orgVersionMap, msgList).booleanValue()) continue;
            String[] arr = file.getName().split("-");
            String tableName = arr[0];
            ExportConditionVO conditionVO = new ExportConditionVO();
            conditionVO.setOrgVer(arr[1]);
            conditionVO.setOrgType(tableName);
            conditionVO.setTableName(tableName);
            conditionVO.setExecuteOnDuplicate(Boolean.valueOf(true));
            Workbook workbook = ReportDataSyncUtil.readWorkBook(file.getPath());
            if (null == workbook) {
                return null;
            }
            try {
                List exportMessageList = this.gcOrgDataService.uploadOrgData(workbook, conditionVO).stream().filter(v -> !v.getType().equals("INFO")).collect(Collectors.toList());
                OrgTypeTreeVO vo = orgVersionMap.get(file.getName());
                if (CollectionUtils.isEmpty(exportMessageList)) continue;
                List msg = exportMessageList.stream().map(ExportMessageVO::getMessage).collect(Collectors.toList());
                msgList.add(String.format("\u7c7b\u578b[%1$s]\u7248\u672c\u540d\u79f0[%2$s]\uff1a%3$s", tableName, vo.getTitle(), String.join((CharSequence)";", msg)));
            }
            catch (Exception e) {
                msgList.add(String.format("\u89e3\u6790\u6587\u4ef6%1$s\u5931\u8d25:%2$s", file.getName(), e.getMessage()));
            }
        }
        return msgList;
    }

    private Set<String> executeImportOrgTypeAndVersion(String dataFolderPath, List<String> msgList) {
        String orgCategorysJson = ReportDataSyncUtil.readJsonFile(dataFolderPath + "/MD_ORG_CATEGORY");
        if (ReportDataSyncUtil.isEmptyJson(orgCategorysJson)) {
            return null;
        }
        List orgCategorys = (List)JsonUtils.readValue((String)orgCategorysJson, (TypeReference)new TypeReference<List<OrgTypeVO>>(){});
        String orgVersionJson = ReportDataSyncUtil.readJsonFile(dataFolderPath + "/MD_ORG_VERSION");
        if (ReportDataSyncUtil.isEmptyJson(orgVersionJson)) {
            return null;
        }
        List orgVersions = (List)JsonUtils.readValue((String)orgVersionJson, (TypeReference)new TypeReference<List<OrgTypeTreeVO>>(){});
        return this.importOrgCategoryAndVersion(orgCategorys, orgVersions, msgList);
    }

    private Set<String> importOrgCategoryAndVersion(List<OrgTypeVO> orgCategorys, List<OrgTypeTreeVO> orgVersions, List<String> msgList) {
        HashMap<String, OrgTypeVO> orgTypeVOMap = new HashMap<String, OrgTypeVO>();
        for (OrgTypeVO orgCategory : orgCategorys) {
            OrgTypeVO orgTypeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgCategory.getName());
            if (orgTypeVO == null) {
                GcOrgTypeTool.getInstance().createOrgType(orgCategory);
                this.orgTypeService.addOrgTypeBaseData(orgCategory);
            } else {
                orgTypeVO = orgCategory;
            }
            orgTypeVOMap.put(orgTypeVO.getName(), orgTypeVO);
        }
        orgVersions.sort((item1, item2) -> null != item1.getValidTime() && item1.getValidTime().before(item2.getValidTime()) ? -1 : 1);
        HashSet<String> orgVersionFileNames = new HashSet<String>();
        for (OrgTypeTreeVO orgVersion : orgVersions) {
            OrgTypeVO orgTypeVO = (OrgTypeVO)orgTypeVOMap.get(orgVersion.getCategoryName());
            PageVO list = this.versionService.list(OrgParamParse.createOrgVerParam(v -> {
                v.setCategoryname(orgVersion.getCategoryName());
                v.setValidtime(orgVersion.getValidTime());
                v.setInvalidtime(orgVersion.getInvalidTime());
            }));
            if (list.getTotal() != 1 && list.getTotal() == 0) {
                msgList.add(String.format("\u6ca1\u6709\u627e\u5230\u7c7b\u578b[%1$s]\u751f\u6548\u65f6\u95f4[%2$s]\u5931\u6548\u65f6\u95f4[%3$s]\u7684\u7ec4\u7ec7\u673a\u6784\u7248\u672c", orgTypeVO.getTitle(), DateUtils.format((Date)orgVersion.getValidTime()), DateUtils.format((Date)orgVersion.getInvalidTime())));
                continue;
            }
            List vos = GcOrgVerTool.getInstance().listOrgVersion(orgVersion.getCategoryName());
            List voList = vos.stream().filter(orgVersionVO -> orgVersionVO.getName().equals(orgVersion.getTitle())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(voList)) {
                String orgVersionFileName = this.getOrgVersionFileName(orgVersion);
                orgVersionFileNames.add(orgVersionFileName);
                continue;
            }
            OrgVersionVO orgVersionVO2 = new OrgVersionVO();
            BeanUtils.copyProperties(orgVersion, orgVersionVO2);
            orgVersionVO2.setName(DateUtils.format((Date)orgVersionVO2.getValidTime(), (String)"yyyyMM"));
            orgVersionVO2.setOrgType(orgTypeVO);
            try {
                GcOrgVerTool.getInstance().createOrgVersion(orgVersionVO2);
            }
            catch (Exception e) {
                msgList.add(String.format("\u7c7b\u578b[%1$s]\u7248\u672c\u540d\u79f0[%2$s]\u5bfc\u5165\u5931\u8d25:%3$s", orgTypeVO.getTitle(), orgVersionVO2.getTitle(), e.getMessage()));
            }
        }
        return orgVersionFileNames;
    }

    private Map<String, OrgTypeTreeVO> getOrgVersion(String dataFolderPath) {
        String orgVersionJson = ReportDataSyncUtil.readJsonFile(dataFolderPath + "/MD_ORG_VERSION");
        if (ReportDataSyncUtil.isEmptyJson(orgVersionJson)) {
            return null;
        }
        List orgVersions = (List)JsonUtils.readValue((String)orgVersionJson, (TypeReference)new TypeReference<List<OrgTypeTreeVO>>(){});
        HashMap<String, OrgTypeTreeVO> orgTypeVOMap = new HashMap<String, OrgTypeTreeVO>();
        for (OrgTypeTreeVO orgVersion : orgVersions) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(orgVersion.getValidTime());
            YearPeriodDO yearPeriodDO = YearPeriodUtil.transform(null, (int)PeriodType.MONTH.type(), (Calendar)calendar);
            String key = orgVersion.getCategoryName() + "-" + yearPeriodDO.toString();
            orgTypeVOMap.put(key, orgVersion);
        }
        return orgTypeVOMap;
    }

    public Boolean checkOrgTypeAndVersion(String fileName, Map<String, OrgTypeTreeVO> orgVersionMap, List<String> msgList) {
        String[] arr = fileName.split("-");
        String tableName = arr[0];
        OrgTypeVO orgTypeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(tableName);
        if (orgTypeVO == null) {
            msgList.add("\u6ca1\u6709\u627e\u5230\u7c7b\u578b[" + tableName + "]\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u4e0b\u53d1");
            return Boolean.FALSE;
        }
        OrgTypeTreeVO orgVersion = orgVersionMap.get(fileName);
        if (orgVersion == null) {
            return Boolean.FALSE;
        }
        PageVO list = this.versionService.list(OrgParamParse.createOrgVerParam(v -> {
            v.setCategoryname(orgVersion.getCategoryName());
            v.setValidtime(orgVersion.getValidTime());
            v.setInvalidtime(orgVersion.getInvalidTime());
        }));
        if (list.getTotal() == 0) {
            msgList.add(String.format("\u6ca1\u6709\u627e\u5230\u7c7b\u578b[%1$s]\u751f\u6548\u65f6\u95f4[%2$s]\u5931\u6548\u65f6\u95f4[%3$s]\u7684\u7ec4\u7ec7\u673a\u6784\u7248\u672c", orgTypeVO.getTitle(), DateUtils.format((Date)orgVersion.getValidTime()), DateUtils.format((Date)orgVersion.getInvalidTime())));
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private String getOrgVersionFileName(OrgTypeTreeVO orgVersion) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orgVersion.getValidTime());
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transform(null, (int)PeriodType.MONTH.type(), (Calendar)calendar);
        return orgVersion.getCategoryName() + "-" + yearPeriodDO.toString();
    }

    public String funcTitle() {
        return "\u5408\u5e76\u7ec4\u7ec7";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

