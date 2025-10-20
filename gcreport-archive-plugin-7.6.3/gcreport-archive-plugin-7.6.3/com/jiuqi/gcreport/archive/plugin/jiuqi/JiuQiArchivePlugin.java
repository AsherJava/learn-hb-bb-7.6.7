/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.common.ArchiveProperties
 *  com.jiuqi.gcreport.archive.common.ArchiveStatusEnum
 *  com.jiuqi.gcreport.archive.dao.ArchiveInfoDao
 *  com.jiuqi.gcreport.archive.entity.ArchiveInfoEO
 *  com.jiuqi.gcreport.archive.entity.ArchiveLogEO
 *  com.jiuqi.gcreport.archive.plugin.AbstractBaseArchivePlugin
 *  com.jiuqi.gcreport.archive.util.ArchiveCsvUtil
 *  com.jiuqi.gcreport.archive.util.ArchiveLogUtil
 *  com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.web.TaskConditionBoxController
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.common.MD5Util
 */
package com.jiuqi.gcreport.archive.plugin.jiuqi;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.plugin.AbstractBaseArchivePlugin;
import com.jiuqi.gcreport.archive.util.ArchiveCsvUtil;
import com.jiuqi.gcreport.archive.util.ArchiveLogUtil;
import com.jiuqi.gcreport.archive.util.ArchiveUploadFieldUtil;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.web.TaskConditionBoxController;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.common.MD5Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JiuQiArchivePlugin
extends AbstractBaseArchivePlugin {
    @Autowired
    private ArchiveInfoDao archiveInfoDao;
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;

    public String getPluginName() {
        return "\u4e45\u5176\u7535\u5b50\u6863\u6848\u63d2\u4ef6";
    }

    public String getPluginCode() {
        return "JiuQi";
    }

    public void afterArchiveProcessComplete(ArchiveLogEO logEO, StringBuffer logInfo) {
        List archiveInfoEOList = this.archiveInfoDao.listInfoByLogIdAndStatus(logEO.getId(), ArchiveStatusEnum.UPLOAD_SUCCESS);
        if (CollectionUtils.isEmpty((Collection)archiveInfoEOList)) {
            return;
        }
        try {
            this.uploadCsvFile(logEO, archiveInfoEOList);
        }
        catch (Exception e) {
            logInfo.append(ArchiveLogUtil.getExceptionStackStr((Throwable)e));
        }
    }

    protected void initEoFileInfo(ArchiveContext context, ArchiveLogEO logEO, String unit, String fileSuffix, ArchiveInfoEO eo, String username) {
        Map dimensionSet = context.getDimensionSet();
        String orgType = context.getOrgType();
        TableModelDefine tableModel = this.entityMetaService.getTableModel(orgType);
        String orgTypeTitle = tableModel.getTitle();
        StringBuffer filePath = new StringBuffer();
        StringBuffer fileName = new StringBuffer();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitVO = tool.getOrgByCode(unit);
        if (unitVO == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + unit + "]\u5728\u65f6\u671f[" + dataTime + "]\u4e0b\u4e0d\u5b58\u5728\uff01");
        }
        unit = this.getMappingCode(unit);
        PeriodWrapper periodWrapper = new PeriodWrapper(dataTime);
        String bblx = PeriodConsts.typeToTitle((int)periodWrapper.getType()) + "\u62a5";
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(logEO.getSchemeId());
        String taskTitle = "";
        try {
            TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(context.getTaskId());
            taskTitle = taskDefine.getTitle();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u4efb\u52a1\u5931\u8d25");
        }
        String formSchemeTitle = formScheme.getTitle();
        filePath.append(this.archiveProperties.getFtpPathPrefix()).append("/gcreport/").append(unit).append("/").append(DateUtils.format((Date)logEO.getCreateDate(), (String)"yyyy-MM-dd-HH-mm-ss")).append("/");
        fileName.append(taskTitle).append("-").append(formSchemeTitle).append("-").append(orgTypeTitle).append("-").append("CNY").append("-").append(dataTime).append("-").append(unit).append("-").append(unitVO.getTitle().replaceAll("\\p{Z}", "")).append("-").append(bblx);
        fileName.append(fileSuffix);
        filePath.append(fileName);
        eo.setFilePath(StringUtils.isEmpty((String)eo.getFilePath()) ? filePath.toString() : eo.getFilePath() + ";" + filePath.toString());
        eo.setFileName(StringUtils.isEmpty((String)eo.getFileName()) ? fileName.toString() : eo.getFileName() + ";" + fileName.toString());
    }

    private String getMappingCode(String unit) {
        OrgToJsonVO orgByCode = GcOrgBaseTool.getInstance().getOrgByCode(unit);
        if (orgByCode == null) {
            return unit;
        }
        Object qydmObj = orgByCode.getFieldValue("QYDM");
        if (qydmObj == null || StringUtils.isEmpty((String)qydmObj.toString())) {
            return unit;
        }
        return qydmObj.toString();
    }

    private void uploadCsvFile(ArchiveLogEO logEO, List<ArchiveInfoEO> archiveInfoEOList) {
        Map<String, List<ArchiveInfoEO>> unit2ArchiveInfoMap = archiveInfoEOList.stream().collect(Collectors.groupingBy(item -> this.getMappingCode(item.getUnitId())));
        String[] header = new String[]{"UUID", "PDF\u6587\u4ef6\u540d", "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", "\u7ec4\u7ec7\u673a\u6784\u540d\u79f0", "\u4f1a\u8ba1\u5e74\u6708", "\u62a5\u8868\u65b9\u6848\u540d\u79f0", "\u5e01\u79cd\u4ee3\u7801", "\u5f52\u6863\u4eba", "\u5f52\u6863\u65f6\u95f4", "\u6863\u6848\u63cf\u8ff0", "\u66f4\u65b0\u6807\u5fd7"};
        for (String mappingUnitCode : unit2ArchiveInfoMap.keySet()) {
            List<ArchiveInfoEO> unitArchiveInfoEOList = unit2ArchiveInfoMap.get(mappingUnitCode);
            ArrayList<String[]> rowDataList = new ArrayList<String[]>(unitArchiveInfoEOList.size());
            for (ArchiveInfoEO archiveInfoEO : unitArchiveInfoEOList) {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(logEO.getSchemeId());
                TaskConditionBoxController taskConditionBoxController = (TaskConditionBoxController)SpringContextUtils.getBean(TaskConditionBoxController.class);
                Scheme scheme = taskConditionBoxController.convertSchemeDefinToScheme(formScheme);
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)logEO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, archiveInfoEO.getDefaultPeriod()));
                GcOrgCacheVO unitVO = tool.getOrgByCode(archiveInfoEO.getUnitId());
                String rowId = MD5Util.encrypt((String)(archiveInfoEO.getSchemeId() + logEO.getOrgType() + archiveInfoEO.getUnitId() + "CNY" + archiveInfoEO.getDefaultPeriod()));
                String[] rowData = new String[]{rowId, archiveInfoEO.getFileName(), mappingUnitCode, unitVO.getTitle(), archiveInfoEO.getDefaultPeriod(), formScheme.getTitle(), "CNY", archiveInfoEO.getCreateUser(), DateUtils.format((Date)archiveInfoEO.getCreateDate(), (String)"yyyy-MM-dd HH:mm:ss"), "", "0"};
                rowDataList.add(rowData);
            }
            File csv = ArchiveCsvUtil.createCsv((String[])header, rowDataList);
            if (csv == null) continue;
            String timeStr = DateUtils.format((Date)logEO.getCreateDate(), (String)"yyyy-MM-dd-HH-mm-ss");
            String fileName = this.archiveProperties.getFtpPathPrefix() + "/gcreport/" + mappingUnitCode + "/" + timeStr + "/" + mappingUnitCode + "-" + timeStr + "-ArchiveInfo.csv";
            this.upload(csv, fileName, this.archiveProperties.isSFTP());
        }
    }

    private void upload(File csv, String fileName, boolean isSFTP) {
        try {
            ArchiveUploadFieldUtil.upload((InputStream)new FileInputStream(csv), (String)fileName, (int)((int)csv.length()), (boolean)isSFTP);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        ArchiveCsvUtil.deleteTempFile((File)csv);
    }
}

