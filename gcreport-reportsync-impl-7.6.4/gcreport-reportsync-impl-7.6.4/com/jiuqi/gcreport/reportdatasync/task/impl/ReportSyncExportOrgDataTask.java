/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.gcreport.reportdatasync.task.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportOrgDataTask
implements IReportSyncExportTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GcFieldManagerService gcFieldManagerService;

    public boolean match(ReportDataSyncParams dataSyncParam) {
        Boolean exportOrg = dataSyncParam.getExportOrg();
        return Boolean.TRUE.equals(exportOrg);
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        ReportDataSyncUploadDataTaskVO reportDataSyncUploadDataTaskVO = new ReportDataSyncUploadDataTaskVO();
        OrgDataService orgService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setStopflag(Integer.valueOf(-1));
        List orgDOList = orgService.list(orgDTO).getRows();
        List<String> unitCodes = orgDOList.stream().map(OrgDO::getCode).collect(Collectors.toList());
        if (unitCodes.size() > 10000) {
            unitCodes = unitCodes.subList(0, 10000);
        }
        ArrayList<String> msgList = new ArrayList<String>();
        String filePath = rootFolder.getPath();
        ReportDataSyncDataUtils.exportBaseOrgDatas(filePath, unitCodes, null, reportDataSyncUploadDataTaskVO);
        try {
            String orgMsgFilePath = filePath + "/orgMsg";
            File orgMsgFile = new File(orgMsgFilePath);
            orgMsgFile.createNewFile();
            try (FileOutputStream reportDataMetaFileOutputStream = new FileOutputStream(orgMsgFile);){
                IOUtils.write((byte[])JsonUtils.writeValueAsString((Object)reportDataSyncUploadDataTaskVO).getBytes(), (OutputStream)reportDataMetaFileOutputStream);
            }
        }
        catch (Exception e) {
            msgList.add(e.getMessage());
        }
        return msgList;
    }

    public String funcTitle() {
        return "\u57fa\u7840\u7ec4\u7ec7";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

