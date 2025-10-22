/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 *  com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.gcreport.reportdatasync.task.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.np.period.PeriodType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportMergeUnitDataTask
implements IReportSyncExportTask {
    private final String DATA_FOLDER_NAME = "GC-unitversion";
    @Autowired
    private GcFieldManagerService fieldManagerService;
    @Autowired
    GcFieldManagerService gcFieldManagerService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean match(ReportDataSyncParams dataSyncParam) {
        List unitVersionIdList = dataSyncParam.getUnitVersionIds();
        return !CollectionUtils.isEmpty((Collection)unitVersionIdList);
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        String dataFolderPath = ReportDataSyncUtil.createNewPath(rootFolder.getPath(), "GC-unitversion");
        ArrayList<String> msgList = new ArrayList<String>();
        Map<String, OrgTypeTreeVO> versionId2TitleMap = ReportDataSyncUtil.unitVersionId2TitleMap();
        this.executeExportOrgTypeAndVersion(dataSyncParam, dataFolderPath, versionId2TitleMap);
        for (String unitVersionId : dataSyncParam.getUnitVersionIds()) {
            OrgTypeTreeVO orgType = versionId2TitleMap.get(unitVersionId);
            if (null == orgType) continue;
            ExportConditionVO conditionVO = this.getCondition(orgType);
            String filePath = dataFolderPath + "/" + conditionVO.getTableName() + "-" + conditionVO.getOrgVer();
            try {
                List fieldComponent = this.gcFieldManagerService.getFieldComponent(conditionVO.getOrgType());
                List dataList = this.fieldManagerService.exportExcel(conditionVO);
                File file = new File(filePath);
                file.createNewFile();
                XSSFWorkbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet();
                Row head = sheet.createRow(0);
                for (int i = 0; i < fieldComponent.size(); ++i) {
                    head.createCell(i).setCellValue(((OrgFiledComponentVO)fieldComponent.get(i)).getLabel() + "|" + ((OrgFiledComponentVO)fieldComponent.get(i)).getCode());
                }
                for (int row = 0; row < dataList.size(); ++row) {
                    Map map = (Map)dataList.get(row);
                    Row hssfRow = sheet.createRow(row + 1);
                    for (int i = 0; i < fieldComponent.size(); ++i) {
                        Object cellValue = map.get(((OrgFiledComponentVO)fieldComponent.get(i)).getCode());
                        if ("STOPFLAG".equalsIgnoreCase(((OrgFiledComponentVO)fieldComponent.get(i)).getCode())) {
                            cellValue = cellValue == null || "\u5426".equals(cellValue.toString()) ? "0" : "1";
                        }
                        hssfRow.createCell(i).setCellValue(cellValue.toString());
                    }
                }
                workbook.write(new FileOutputStream(file));
                workbook.close();
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return msgList;
    }

    private void executeExportOrgTypeAndVersion(ReportDataSyncParams dataSyncParam, String dataFolderPath, Map<String, OrgTypeTreeVO> versionId2TitleMap) {
        ArrayList<OrgTypeTreeVO> orgVersions = new ArrayList<OrgTypeTreeVO>();
        ArrayList<OrgTypeVO> orgTypes = new ArrayList<OrgTypeVO>();
        HashSet<String> orgTypeCodes = new HashSet<String>();
        for (String unitVersionId : dataSyncParam.getUnitVersionIds()) {
            OrgTypeVO orgCategory;
            OrgTypeTreeVO orgType = versionId2TitleMap.get(unitVersionId);
            if (null == orgType) continue;
            orgVersions.add(orgType);
            if (orgTypeCodes.contains(orgType.getCategoryName()) || (orgCategory = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType.getCategoryName())) == null) continue;
            orgTypes.add(orgCategory);
            orgTypeCodes.add(orgType.getCategoryName());
        }
        this.writeOrgTypeAndVersions(dataFolderPath, orgVersions, orgTypes);
    }

    private void writeOrgTypeAndVersions(String dataFolderPath, List<OrgTypeTreeVO> orgVersions, List<OrgTypeVO> orgCategorys) {
        try {
            String orgTypeFilePath = dataFolderPath + "/MD_ORG_CATEGORY";
            ReportDataSyncUtil.writeFileJson(orgCategorys, orgTypeFilePath);
            String orgVersionFilePath = dataFolderPath + "/MD_ORG_VERSION";
            ReportDataSyncUtil.writeFileJson(orgVersions, orgVersionFilePath);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private ExportConditionVO getCondition(OrgTypeTreeVO orgType) {
        Calendar calendar = Calendar.getInstance();
        ExportConditionVO conditionVO = new ExportConditionVO();
        conditionVO.setTableName(orgType.getCategoryName());
        conditionVO.setOrgType(orgType.getCategoryName());
        calendar.setTime(orgType.getValidTime());
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transform(null, (int)PeriodType.MONTH.type(), (Calendar)calendar);
        conditionVO.setOrgVer(yearPeriodDO.toString());
        return conditionVO;
    }

    public String funcTitle() {
        return "\u5408\u5e76\u7ec4\u7ec7";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

