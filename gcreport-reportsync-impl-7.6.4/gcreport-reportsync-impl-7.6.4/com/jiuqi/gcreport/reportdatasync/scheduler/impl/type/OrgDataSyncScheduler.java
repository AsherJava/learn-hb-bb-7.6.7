/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.ZipUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.scheduler.impl.type;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.ZipUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.contextImpl.OrgDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.MultilevelOrgDataLogDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.service.GcOrgDataSyncService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OrgDataSyncScheduler
implements ISyncTypeScheduler {
    @Autowired
    private ReportDataSyncServerListService serverListService;
    @Autowired
    private MultilevelOrgDataLogDao orgDataLogDao;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private GcOrgDataSyncService gcOrgDataSyncService;

    @Override
    public String code() {
        return SyncTypeEnums.ORGDATA.getCode();
    }

    @Override
    public String name() {
        return SyncTypeEnums.ORGDATA.getName();
    }

    @Override
    public List<ReportDataSyncServerInfoVO> getServerInfoVOList() {
        ReportDataSyncServerInfoVO serverInfoVOS = this.serverListService.listServerInfos().stream().filter(v -> v.getSyncMethod() != null && v.getSyncType().contains(this.code())).findFirst().get();
        return new ArrayList<ReportDataSyncServerInfoVO>(Arrays.asList(serverInfoVOS));
    }

    @Override
    public MultilevelSyncContext buildContext(String info) {
        return (MultilevelSyncContext)JsonUtils.readValue((String)info, OrgDataContext.class);
    }

    @Override
    public ReportSyncFileDTO exportFileToOss(MultilevelSyncContext syncContext) {
        ReportSyncFileDTO fileDTO = new ReportSyncFileDTO();
        OrgDataContext context = (OrgDataContext)syncContext;
        String syncDataAttachId = StringUtils.isEmpty((String)context.getSn()) ? UUID.randomUUID().toString() : context.getSn();
        context.setSn(syncDataAttachId);
        Object file = null;
        String rootPath = null;
        try {
            rootPath = BatchExportConsts.EXPORTDIR + File.separator + "orgdatasync" + File.separator + "exportdata" + File.separator + LocalDate.now() + File.separator + syncDataAttachId;
            File rootFile = new File(rootPath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String fileName = context.getOrgType() + "_" + DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmss") + ".zip";
            String dataZipLocation = rootPath + File.separator + fileName;
            ReportDataSyncUtils.createEmptyZipFile(dataZipLocation);
            String reportDataMetaLocation = rootPath + File.separator + "OrgDataMeta.JSON";
            List<ReportDataSyncServerInfoVO> serverInfoVO = this.getServerInfoVOList();
            if (CollectionUtils.isEmpty(serverInfoVO)) {
                throw new BusinessRuntimeException("\u672a\u627e\u5230\u540c\u6b65\u7c7b\u578b\u4e3a" + this.name() + "\u7684\u76ee\u6807\u670d\u52a1\u5668");
            }
            String orgCode = serverInfoVO.get(0).getOrgCode();
            context.setOrgCode(orgCode);
            File reportDataMetaFile = new File(reportDataMetaLocation);
            reportDataMetaFile.createNewFile();
            try (FileOutputStream orgDataMetaFileOutputStream = new FileOutputStream(reportDataMetaFile);){
                IOUtils.write(JsonUtils.writeValueAsString((Object)((Object)context)).getBytes(), (OutputStream)orgDataMetaFileOutputStream);
            }
            ZipUtils.addFile((String)dataZipLocation, (String)reportDataMetaLocation);
            OrgDataService orgService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname(context.getOrgType());
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
            orgDTO.setStopflag(Integer.valueOf(-1));
            YearPeriodDO period = YearPeriodUtil.transform(null, (String)context.getPeriodValue());
            orgDTO.setVersionDate(period.getEndDate());
            List<OrgDO> orgList = this.getAllChildrenOrg(orgService, orgDTO, orgCode);
            ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            for (OrgDO orgDO : orgList) {
                String json = JsonUtils.writeValueAsString((Object)orgDO);
                Map dataMap = (Map)JsonUtils.readValue((String)json, Map.class);
                HashMap upperCaseMap = new HashMap();
                for (Map.Entry entry : dataMap.entrySet()) {
                    upperCaseMap.put(((String)entry.getKey()).toUpperCase(), entry.getValue());
                }
                dataList.add(upperCaseMap);
            }
            String orgDataLocation = rootPath + File.separator + "ORG_DATA.csv";
            ArrayList<String> logs = new ArrayList<String>();
            ReportDataSyncDataUtils.exportMergeOrgDatas(context.getOrgType(), context.getPeriodValue(), orgDataLocation, orgCode, null, dataList, dataZipLocation, false, logs);
            byte[] bytes = FileCopyUtils.copyToByteArray(new File(dataZipLocation));
            VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", bytes);
            this.commonFileService.uploadFileToOss((MultipartFile)multipartFile, syncDataAttachId);
            fileDTO.setMainFileId(syncDataAttachId);
            fileDTO.setMainFile((MultipartFile)multipartFile);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return fileDTO;
    }

    private List<OrgDO> getAllChildrenOrg(OrgDataService orgService, OrgDTO orgDTO, String parentCode) {
        ArrayList<OrgDO> orgList = new ArrayList<OrgDO>();
        orgDTO.setParentcode(parentCode);
        PageVO dataList = orgService.list(orgDTO);
        if (dataList.getTotal() > 0) {
            List childOrgList = dataList.getRows();
            orgList.addAll(childOrgList);
            for (OrgDO childOrg : childOrgList) {
                orgList.addAll(this.getAllChildrenOrg(orgService, orgDTO, childOrg.getCode()));
            }
        }
        return orgList;
    }

    @Override
    public void afterExport(MultilevelSyncContext syncContext) {
    }

    @Override
    public boolean importData(MultilevelImportContext importContext) {
        ReportSyncFileDTO fileDTO = importContext.getFileDTO();
        MultipartFile multipartFile = fileDTO.getMainFile();
        boolean result = false;
        try {
            result = this.gcOrgDataSyncService.importOrgDataFile(multipartFile);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    @Override
    public boolean isSync() {
        return true;
    }

    @Override
    public boolean multilevelReturn(MultilevelReturnContext context) {
        MultilevelOrgDataSyncLogVO vo = (MultilevelOrgDataSyncLogVO)JsonUtils.readValue((String)context.getInfoJson(), MultilevelOrgDataSyncLogVO.class);
        this.gcOrgDataSyncService.rejectOrgData(vo);
        return true;
    }
}

