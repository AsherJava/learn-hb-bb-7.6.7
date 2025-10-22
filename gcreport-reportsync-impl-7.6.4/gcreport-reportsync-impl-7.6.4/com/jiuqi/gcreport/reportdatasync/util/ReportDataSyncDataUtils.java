/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.base.util.ZipUtils
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService
 *  com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.np.period.PeriodLanguage
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.dataentry.bean.BatchExportData
 *  com.jiuqi.nr.dataentry.bean.BatchExportInfo
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.dataentry.bean.JIOFormImportResult
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.internal.service.CurrencyService
 *  com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter
 *  com.jiuqi.nr.dataentry.model.BatchDimensionParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.provider.DimensionValueProvider
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.dataentry.web.ActionController
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.DescriptionInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.ZipUtil
 *  com.jiuqi.nr.transmission.data.common.MultilingualLog
 *  com.jiuqi.nr.transmission.data.common.TransmissonDataType
 *  com.jiuqi.nr.transmission.data.common.Utils
 *  com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO
 *  com.jiuqi.nr.transmission.data.exception.SchemeSchemeException
 *  com.jiuqi.nr.transmission.data.internal.file.FileHandleService
 *  com.jiuqi.nr.transmission.data.intf.FormSchemeParam
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.organization.common.OrgDataCacheUtil
 *  com.jiuqi.va.organization.domain.OrgImportTemplateDO
 *  com.jiuqi.va.organization.domain.OrgImportTemplateDTO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.va.organization.service.OrgImportTemplateService
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  nr.single.client.bean.JIOImportResultObject
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.data.internal.service.SingleMappingServiceImpl
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.csvreader.CsvReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.base.util.ZipUtils;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcFieldManagerService;
import com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadDataTaskEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataSyncUploadSchemePeriodType;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.scheduler.impl.type.ReportDataSyncScheduler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncOrgFilePortUtil;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.JIOFormImportResult;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.internal.service.CurrencyService;
import com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.web.ActionController;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.TransmissonDataType;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeSchemeException;
import com.jiuqi.nr.transmission.data.internal.file.FileHandleService;
import com.jiuqi.nr.transmission.data.intf.FormSchemeParam;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.OrgDataCacheUtil;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.OrgImportTemplateService;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.internal.service.SingleMappingServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class ReportDataSyncDataUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncDataUtils.class);
    public static final String REPORT_DATA_META_FILENAME = "ReportDataMeta.JSON";
    public static final String JIO_FILE_NAME = "jioFile";
    private static String[] checkDataHeads = new String[]{"ID", "YEAR", "PERIOD", "UNITCODE", "CURRENCYCODE", "FORMCODE", "FORMULACODE", "GLOBROW", "GLOBCOL", "CKDINFO", "FLOATAREA", "AREA_ROWID", "CREATETIME", "UPTIMESTAMP"};
    private static String[] attachmentsHeads = new String[]{"ID", "ATT_NAME", "ATT_SIZE", "ATT_LOCATION"};

    public static ObjectInfo exportDataToOss(TaskDefine taskDefine, ReportDataSyncUploadSchemeEO uploadSchemeEO, ReportDataSyncUploadLogEO uploadLogEO, List<String> logs) {
        ObjectInfo objectInfo;
        if (uploadLogEO.getSyncDataAttachId() == null) {
            String syncDataAttachId = UUIDUtils.newUUIDStr();
            uploadLogEO.setSyncDataAttachId(syncDataAttachId);
        }
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
        FileHandleService fileHandleService = (FileHandleService)SpringContextUtils.getBean(FileHandleService.class);
        TransmissionMonitor monitor = new TransmissionMonitor(uploadLogEO.getId(), cacheObjectResourceRemote);
        String fileFormat = ReportDataSyncUtil.getFileFormat();
        File file = null;
        String rootPath = null;
        logs.add("\u6b63\u5728\u8fdb\u884c\u6570\u636e\u5305\u751f\u6210;");
        try {
            Serializable jioFile;
            rootPath = BatchExportConsts.EXPORTDIR + File.separator + "reportdatasync" + File.separator + "exportdata" + File.separator + LocalDate.now() + File.separator + uploadLogEO.getSyncDataAttachId() + File.separator;
            File rootFile = new File(rootPath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String taskTitle = taskDefine == null ? uploadSchemeEO.getTitle() : taskDefine.getTitle();
            String fileName = taskTitle + "(" + uploadLogEO.getPeriodStr() + ")" + DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmss") + ".zip";
            String dataZipLocation = rootPath + fileName;
            ReportDataSyncUtils.createEmptyZipFile(dataZipLocation);
            ReportDataSyncUploadSchemeVO uploadSchemeVO = null;
            boolean applicationMode = uploadSchemeEO.getApplicationMode() == null || uploadSchemeEO.getApplicationMode() == 1;
            int formKeysSize = 0;
            if (!applicationMode) {
                String schemeEOContent = uploadSchemeEO.getContent();
                uploadSchemeVO = (ReportDataSyncUploadSchemeVO)com.jiuqi.common.base.util.JsonUtils.readValue((String)schemeEOContent, ReportDataSyncUploadSchemeVO.class);
                ReportDataSyncDataUtils.checkOrgAndCurrency(uploadSchemeVO);
                ReportDataSyncParams reportDataSyncParams = new ReportDataSyncParams();
                BeanUtils.copyProperties(uploadSchemeVO, reportDataSyncParams);
                ReportDataSyncService reportDataSyncService = (ReportDataSyncService)SpringContextUtils.getBean(ReportDataSyncService.class);
                reportDataSyncParams.setLogs(logs);
                reportDataSyncParams.setSyncType(ReportDataSyncTypeEnum.DATA.getCode());
                if (uploadSchemeVO.getReportData() != null && uploadSchemeVO.getReportData().getFormKeys() != null) {
                    formKeysSize = uploadSchemeVO.getReportData().getFormKeys().size();
                }
                reportDataSyncService.export(reportDataSyncParams, uploadLogEO.getSyncDataAttachId());
                String snFolderPath = ReportDataSyncUtil.getSnFolderPath(uploadLogEO.getSyncDataAttachId());
                File mergeDataFile = new File(SinglePathUtil.getNewPath((String)snFolderPath, (String)reportDataSyncParams.getSyncType()));
                if (mergeDataFile.exists()) {
                    ZipUtils.addFile((String)dataZipLocation, (String)mergeDataFile.getCanonicalPath());
                    mergeDataFile.delete();
                }
            }
            SyncSchemeParamDTO syncSchemeParamDTO = null;
            if (taskDefine != null) {
                String reportLog = String.format("\u62a5\u8868\u4efb\u52a1\u6570\u636e\u5bfc\u51fa\uff0c\u62a5\u8868\u4efb\u52a1:%1$s;\u7248\u672c\u53f7:%2$s;\u65f6\u671f\uff1a%3$s;", taskDefine.getTitle(), uploadLogEO.getSyncVersion(), uploadLogEO.getPeriodStr());
                List<String> formKeys = new ArrayList<String>();
                if (uploadSchemeVO != null) {
                    reportLog = reportLog.concat(String.format("\u5355\u4f4d:%1$s\u5bb6;\u62a5\u8868:%2$s\u5f20;", uploadSchemeVO.getReportData().getUnitVos().size(), formKeysSize));
                    formKeys = uploadSchemeVO.getReportData().getFormKeys();
                }
                logs.add(reportLog);
                if (!CollectionUtils.isEmpty(formKeys)) {
                    if (fileFormat.equals("nrd")) {
                        syncSchemeParamDTO = ReportDataSyncDataUtils.getSyncSchemeParamDTO(taskDefine, uploadLogEO, uploadSchemeEO, formKeys, logs);
                        if (syncSchemeParamDTO.getEntityType() == 2 && !StringUtils.isEmpty((String)syncSchemeParamDTO.getEntity())) {
                            String executeExportKey = UUID.randomUUID().toString();
                            monitor.progressAndMessage(0.0, MultilingualLog.executeSyncMessage((int)1));
                            TransmissionMonitor exportMonitor = new TransmissionMonitor(executeExportKey, cacheObjectResourceRemote, (AsyncTaskMonitor)monitor, Double.valueOf(0.9));
                            syncSchemeParamDTO.setDataMessage(TransmissonDataType.CHECK_MESSAGE.getCode());
                            file = fileHandleService.fileExport((AsyncTaskMonitor)exportMonitor, syncSchemeParamDTO);
                            ZipUtils.addFile((String)dataZipLocation, (String)file.getCanonicalPath());
                            ReportDataSyncDataUtils.exportGzOrgCode(rootPath, dataZipLocation);
                        } else {
                            LOGGER.info("\u751f\u6210nrd\u6587\u4ef6\u5931\u8d25,\u6ca1\u6709\u7b26\u5408\u7684\u5355\u4f4d");
                        }
                    } else {
                        String jioFilepath = BatchExportConsts.EXPORTDIR + File.separator + JIO_FILE_NAME + File.separator;
                        ExportData exportData = ReportDataSyncDataUtils.buildJioExportData(jioFilepath, uploadSchemeVO.getReportData(), uploadLogEO.getPeriodValue(), formKeys, logs);
                        jioFile = new File(jioFilepath);
                        ZipUtils.addFile((String)dataZipLocation, (String)((File)jioFile).getCanonicalPath());
                        Utils.deleteAllFilesOfDir((File)jioFile);
                    }
                }
                uploadLogEO.setTaskId(taskDefine.getKey());
            }
            ReportDataSyncUploadDataTaskVO reportDataSyncUploadDataTaskVO = ReportDataSyncDataUtils.convertUploadLogEOToReceiveTaskVO(uploadLogEO);
            if (!applicationMode && uploadSchemeVO.getReportData() != null) {
                if (uploadSchemeVO.getReportData().getSyncUnit() != null && uploadSchemeVO.getReportData().getSyncUnit().booleanValue()) {
                    List unitCodes = uploadSchemeVO.getReportData().getUnitCodes();
                    if (!CollectionUtils.isEmpty((Collection)unitCodes)) {
                        String mergeOrgFilePath = rootPath + "/" + uploadSchemeVO.getReportData().getOrgType() + "-" + uploadLogEO.getPeriodValue();
                        ReportDataSyncDataUtils.exportMergeOrgDatas(uploadSchemeVO.getReportData().getOrgType(), uploadLogEO.getPeriodValue(), mergeOrgFilePath, null, unitCodes, null, dataZipLocation, true, logs);
                        ReportDataSyncDataUtils.exportBaseOrgDatas(rootPath, unitCodes, dataZipLocation, reportDataSyncUploadDataTaskVO);
                    }
                } else {
                    logs.add("\u4e0d\u540c\u6b65\u5355\u4f4d\u4fe1\u606f");
                }
            }
            String reportDataMetaLocation = rootPath + REPORT_DATA_META_FILENAME;
            File reportDataMetaFile = new File(reportDataMetaLocation);
            reportDataMetaFile.createNewFile();
            FileOutputStream reportDataMetaFileOutputStream = new FileOutputStream(reportDataMetaFile);
            jioFile = null;
            try {
                if (syncSchemeParamDTO != null && !applicationMode) {
                    reportDataSyncUploadDataTaskVO.setAllowForceUpload(Boolean.valueOf(syncSchemeParamDTO.getAllowForceUpload() == 1));
                    reportDataSyncUploadDataTaskVO.setExecuteUpload(Boolean.valueOf(syncSchemeParamDTO.getIsUpload() == 1));
                }
                reportDataSyncUploadDataTaskVO.setApplicationMode(Boolean.valueOf(applicationMode));
                IOUtils.write(com.jiuqi.common.base.util.JsonUtils.writeValueAsString((Object)reportDataSyncUploadDataTaskVO).getBytes(), (OutputStream)reportDataMetaFileOutputStream);
            }
            catch (Throwable throwable) {
                jioFile = throwable;
                throw throwable;
            }
            finally {
                if (reportDataMetaFileOutputStream != null) {
                    if (jioFile != null) {
                        try {
                            reportDataMetaFileOutputStream.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)jioFile).addSuppressed(throwable);
                        }
                    } else {
                        reportDataMetaFileOutputStream.close();
                    }
                }
            }
            ZipUtils.addFile((String)dataZipLocation, (String)reportDataMetaLocation);
            byte[] bytes = FileCopyUtils.copyToByteArray(new File(dataZipLocation));
            VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", bytes);
            CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
            objectInfo = commonFileService.uploadFileToOss((MultipartFile)multipartFile, uploadLogEO.getSyncDataAttachId());
            logs.add("\u5b8c\u6210\u6570\u636e\u5305\u5bfc\u51fa");
        }
        catch (Exception e) {
            try {
                LOGGER.error(e.getMessage(), e);
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
            catch (Throwable throwable) {
                if (!StringUtils.isEmpty(rootPath)) {
                    ReportDataSyncUtils.delFiles(new File(rootPath));
                }
                if (file != null && file.exists()) {
                    Utils.deleteAllFilesOfDirByPath((String)file.getParent());
                }
                throw throwable;
            }
        }
        if (!StringUtils.isEmpty((String)rootPath)) {
            ReportDataSyncUtils.delFiles(new File(rootPath));
        }
        if (file != null && file.exists()) {
            Utils.deleteAllFilesOfDirByPath((String)file.getParent());
        }
        return objectInfo;
    }

    private static ExportData buildJioExportData(String jioFilePath, ReportDataParam reportDataParam, String period, List<String> formKeys, List<String> logs) throws Exception {
        JioBatchExportExecuter exportExecuter = (JioBatchExportExecuter)SpringContextUtils.getBean(JioBatchExportExecuter.class);
        BatchExportInfo info = ReportDataSyncDataUtils.buildJioBatchInfo(reportDataParam, period);
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), cacheObjectResourceRemote);
        ArrayList<BatchDimensionParam> dimensionInfoBuild = new ArrayList<BatchDimensionParam>();
        ArrayList<Map> resultDimension = new ArrayList<Map>();
        resultDimension.add(info.getContext().getDimensionSet());
        BatchDimensionParam batchDimensionParam = ReportDataSyncDataUtils.dimensionInfoBuild(info.getContext(), (AsyncTaskMonitor)asyncTaskMonitor);
        dimensionInfoBuild.add(batchDimensionParam);
        ArrayList<String> multiplePeriodList = new ArrayList<String>();
        multiplePeriodList.add(period);
        String dateDir = ReportDataSyncDataUtils.getPeriodTitle(reportDataParam.getSchemeId(), period) + BatchExportConsts.SEPARATOR;
        ArrayList datas = new ArrayList();
        info.setLocation(jioFilePath);
        info.setZipLocation(jioFilePath);
        ExportData exportData = exportExecuter.exportOfMultiplePeriod(info, (AsyncTaskMonitor)asyncTaskMonitor, dimensionInfoBuild, multiplePeriodList, formKeys, dateDir, datas);
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName("MD_CURRENCY");
        BaseDataClient baseDataClient = (BaseDataClient)SpringUtil.getBean(BaseDataClient.class);
        Map<String, String> title2code = baseDataClient.list(baseDataDTO).getRows().stream().collect(Collectors.toMap(BaseDataDO::getName, BaseDataDO::getCode, (v1, v2) -> v1));
        HashMap<String, String> fileName2currency = new HashMap<String, String>();
        for (BatchExportData data : datas) {
            String location = data.getLocation();
            String[] parts = location.split("/");
            String[] parts2 = location.split("\\\\");
            String currencyTitle = null;
            if (parts.length > 1) {
                currencyTitle = parts[1];
            } else if (parts2.length > 1) {
                currencyTitle = parts2[1];
            }
            String currencyCode = title2code.containsKey(currencyTitle) ? title2code.get(currencyTitle) : currencyTitle;
            String fileLocation = data.getData().getFileLocation();
            String fileName = fileLocation.split("\\\\")[fileLocation.split("\\\\").length - 1];
            fileName2currency.put(fileName, currencyCode);
        }
        String jioFileMsg = jioFilePath + "jioFileMsg";
        File jioDataMsgFile = new File(jioFileMsg);
        jioDataMsgFile.createNewFile();
        FileOutputStream reportDataMetaFileOutputStream = new FileOutputStream(jioDataMsgFile);
        IOUtils.write(com.jiuqi.common.base.util.JsonUtils.writeValueAsString(fileName2currency).getBytes(), (OutputStream)reportDataMetaFileOutputStream);
        reportDataMetaFileOutputStream.close();
        return exportData;
    }

    private static BatchExportInfo buildJioBatchInfo(ReportDataParam reportData, String period) {
        BatchExportInfo info = new BatchExportInfo();
        if (StringUtils.isEmpty((String)reportData.getMappingScheme())) {
            throw new BusinessRuntimeException("\u672a\u9009\u62e9\u6620\u5c04\u65b9\u6848\uff0c\u5bfc\u51fa\u5931\u8d25");
        }
        info.setConfigKey(reportData.getMappingScheme());
        info.setFileType("EXPORT_BATCH_JIO");
        info.setCompressionType("zip");
        List<Object> currencyIds = new ArrayList();
        String currency = null;
        if (reportData.getCurrency() != null && !"all".equals(reportData.getCurrency())) {
            if ("base".equals(reportData.getCurrency())) {
                currency = "PROVIDER_BASECURRENCY";
            } else if ("supBase".equals(reportData.getCurrency())) {
                currency = "PROVIDER_PBASECURRENCY";
            } else {
                currencyIds = Arrays.asList(reportData.getCurrency().split(";"));
            }
        }
        String adjustCode = reportData.getAdjustCode() == null || reportData.getAdjustCode().isEmpty() ? "0" : reportData.getAdjustCode();
        DimensionValueSet ds = DimensionUtils.generateDimSet((Object)reportData.getUnitCodes(), (Object)period, (Object)(currency == null ? currencyIds : currency), (Object)"", (String)adjustCode, (String)reportData.getTaskId());
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)ds);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(reportData.getTaskId());
        jtableContext.setFormSchemeKey(reportData.getSchemeId());
        jtableContext.setFormKey(String.join((CharSequence)";", reportData.getFormKeys()));
        jtableContext.setDimensionSet(dimensionSet);
        info.setContext(jtableContext);
        return info;
    }

    private static String getPeriodTitle(String formSchemeKey, String period) {
        IRunTimeViewController runtimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        PeriodEngineService periodEngineService = (PeriodEngineService)SpringContextUtils.getBean(PeriodEngineService.class);
        FormSchemeDefine formScheme = runtimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (DataEntryUtil.isChinese()) {
            periodWrapper.setLanguage(PeriodLanguage.Chinese);
        } else {
            periodWrapper.setLanguage(PeriodLanguage.English);
        }
        return periodProvider.getPeriodTitle(periodWrapper);
    }

    public static BatchDimensionParam dimensionInfoBuild(JtableContext jtableContext, AsyncTaskMonitor asyncTaskMonitor) {
        String buildExportDimension = "build_export_dimension_info";
        asyncTaskMonitor.progressAndMessage(0.02, buildExportDimension);
        IJtableParamService jtableParamService = (IJtableParamService)SpringUtil.getBean(IJtableParamService.class);
        List<Map<String, DimensionValue>> resultDimension = null;
        List entityList = jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        HashMap<String, DimensionValue> orderDimension = new HashMap<String, DimensionValue>();
        for (Map.Entry entityViewData : jtableContext.getDimensionSet().entrySet()) {
            DimensionValue one = new DimensionValue();
            one.setName(((DimensionValue)entityViewData.getValue()).getName());
            one.setValue(((DimensionValue)entityViewData.getValue()).getValue());
            orderDimension.put((String)entityViewData.getKey(), one);
        }
        EntityViewData periodEntityInfo = jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String dateType = periodEntityInfo.getDimensionName();
        resultDimension = ReportDataSyncDataUtils.getResultDimension(orderDimension, jtableContext.getFormSchemeKey());
        CurrencyService currencyService = (CurrencyService)SpringUtil.getBean(CurrencyService.class);
        if (jtableContext.getDimensionSet().containsKey("MD_CURRENCY") && (((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_BASECURRENCY") || ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_PBASECURRENCY"))) {
            for (Map<String, DimensionValue> map : resultDimension) {
                JtableContext jtableContext2 = new JtableContext(jtableContext);
                jtableContext2.setDimensionSet(map);
                List currencyInfo = currencyService.getCurrencyInfo(jtableContext2, ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue());
                if (currencyInfo == null || currencyInfo.size() <= 0) continue;
                map.get("MD_CURRENCY").setValue((String)currencyInfo.get(0));
            }
        }
        if (resultDimension.size() > 0 && orderDimension.size() > resultDimension.get(0).size()) {
            Set entrySet = orderDimension.entrySet();
            for (Map.Entry entry : entrySet) {
                for (Map map : resultDimension) {
                    if (map.containsKey(entry.getKey())) continue;
                    DimensionValue value = (DimensionValue)entry.getValue();
                    DimensionValue newDimensionValue = new DimensionValue();
                    newDimensionValue.setName(value.getName());
                    newDimensionValue.setValue(value.getValue());
                    map.put(entry.getKey(), newDimensionValue);
                }
            }
        }
        Map<String, DimensionValue> sortEntity = resultDimension.get(0);
        BatchDimensionParam batchDimensionParam = new BatchDimensionParam();
        ArrayList<String> arrayList = new ArrayList<String>();
        block4: for (Map.Entry<String, DimensionValue> entry : sortEntity.entrySet()) {
            for (int i = 0; i < entityList.size(); ++i) {
                if (dateType.equals(entry.getKey())) {
                    batchDimensionParam.setDate(entry.getValue().getValue());
                }
                if (!entry.getKey().equals(((EntityViewData)entityList.get(i)).getDimensionName())) continue;
                arrayList.add(((EntityViewData)entityList.get(i)).getKey());
                continue block4;
            }
        }
        batchDimensionParam.setEntitys(arrayList);
        batchDimensionParam.setList(resultDimension);
        return batchDimensionParam;
    }

    private static List<Map<String, DimensionValue>> getResultDimension(Map<String, DimensionValue> orderDimension, String formSchemeKey) {
        ArrayList errorDimensionSet = new ArrayList();
        DimensionValueProvider dimensionValueProvider = (DimensionValueProvider)SpringUtil.getBean(DimensionValueProvider.class);
        List splitDimensionValueList = dimensionValueProvider.splitDimensionValueList(orderDimension, formSchemeKey, errorDimensionSet, false);
        return splitDimensionValueList;
    }

    public static void exportBaseOrgDatas(String rootPath, List<String> unitCodes, String dataZipLocation, ReportDataSyncUploadDataTaskVO reportDataSyncUploadDataTaskVO) {
        ArrayList<String> msgList = new ArrayList<String>();
        String orgFilePath = rootPath + "/org";
        try {
            File orgFile = new File(orgFilePath);
            orgFile.createNewFile();
            PageVO<OrgDO> baseOrgs = ReportDataSyncDataUtils.listBaseOrgs(unitCodes);
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setName("MD_ORG");
            OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
            OrgCategoryDO orgcate = orgCategoryService.get(orgCategoryDO);
            LinkedHashMap<String, String> extFieldMap = new LinkedHashMap<String, String>();
            for (ZB zb : orgcate.getZbs()) {
                extFieldMap.put(zb.getName(), zb.getTitle());
            }
            Map<String, DataModelColumn> fieldTypeMap = ReportDataSyncDataUtils.getFixedColumnMap(orgCategoryDO);
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u8868\u683c\u5bfc\u51fa", (String)"MD_ORG", (String)"", (String)("\u5bfc\u51fa\u4e86" + baseOrgs.getTotal() + "\u6761\u6570\u636e"));
            ArrayList<String> headCodes = new ArrayList<String>();
            ArrayList<String> headTitles = new ArrayList<String>();
            Workbook workbook = ReportDataSyncOrgFilePortUtil.exportExcel(orgcate.getTitle(), baseOrgs, extFieldMap, fieldTypeMap, headCodes, headTitles);
            reportDataSyncUploadDataTaskVO.setHeadCodes(headCodes);
            reportDataSyncUploadDataTaskVO.setHeadTitles(headTitles);
            workbook.write(new FileOutputStream(orgFile));
            workbook.close();
            if (dataZipLocation != null) {
                ZipUtils.addFile((String)dataZipLocation, (String)orgFile.getCanonicalPath());
            }
        }
        catch (Exception e) {
            msgList.add(e.getMessage());
        }
    }

    public static PageVO<OrgDO> listBaseOrgs(List<String> unitCodes) {
        OrgDataService orgService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setStopflag(Integer.valueOf(-1));
        if (!CollectionUtils.isEmpty(unitCodes)) {
            orgDTO.setOrgCodes(unitCodes);
        }
        PageVO dataList = orgService.list(orgDTO);
        return dataList;
    }

    public static void exportMergeOrgDatas(String orgType, String periodValue, String mergeOrgFilePath, String parentId, List<String> unitCodes, List<Map<String, Object>> dataList, String dataZipLocation, boolean containTitle, List<String> logs) {
        try {
            GcFieldManagerService fieldManagerService = (GcFieldManagerService)SpringContextUtils.getBean(GcFieldManagerService.class);
            GcFieldManagerService gcFieldManagerService = (GcFieldManagerService)SpringContextUtils.getBean(GcFieldManagerService.class);
            ExportConditionVO conditionVO = new ExportConditionVO();
            conditionVO.setTableName(orgType);
            conditionVO.setOrgType(orgType);
            conditionVO.setOrgVer(periodValue);
            if (!StringUtils.isEmpty((String)parentId)) {
                conditionVO.setParentId(parentId);
            }
            List fieldComponent = gcFieldManagerService.getFieldComponent(conditionVO.getOrgType());
            if (CollectionUtils.isEmpty(dataList)) {
                dataList = fieldManagerService.exportExcel(conditionVO);
            }
            File mergeOrgFile = new File(mergeOrgFilePath);
            mergeOrgFile.createNewFile();
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            Row head = sheet.createRow(0);
            for (int i = 0; i < fieldComponent.size(); ++i) {
                if (containTitle) {
                    head.createCell(i).setCellValue(((OrgFiledComponentVO)fieldComponent.get(i)).getLabel() + "|" + ((OrgFiledComponentVO)fieldComponent.get(i)).getCode());
                    continue;
                }
                head.createCell(i).setCellValue(((OrgFiledComponentVO)fieldComponent.get(i)).getCode());
            }
            if (!CollectionUtils.isEmpty(unitCodes)) {
                dataList = dataList.stream().filter(data -> unitCodes.contains(data.get("CODE"))).collect(Collectors.toList());
            }
            PageVO<OrgDO> baseOrgs = ReportDataSyncDataUtils.listBaseOrgs(unitCodes);
            Map<String, Object> orgCode2GzCodeMap = baseOrgs.getRows().stream().filter(orgDO -> !StringUtils.isEmpty((String)((String)orgDO.get((Object)"gzcode")))).collect(Collectors.toMap(OrgDO::getCode, orgDo -> orgDo.get((Object)"gzcode"), (o1, o2) -> o1));
            for (int row = 0; row < dataList.size(); ++row) {
                Map org = (Map)dataList.get(row);
                Row hssfRow = sheet.createRow(row + 1);
                for (int i = 0; i < fieldComponent.size(); ++i) {
                    String orgCodeAndTitle;
                    String fieldCode = ((OrgFiledComponentVO)fieldComponent.get(i)).getCode();
                    Object cellValue = org.get(fieldCode);
                    if (cellValue == null) {
                        cellValue = "";
                    }
                    if (orgCode2GzCodeMap.containsKey(org.get("CODE")) && ("CODE".equals(fieldCode) || "ORGCODE".equals(fieldCode))) {
                        cellValue = orgCode2GzCodeMap.get(org.get("CODE"));
                    }
                    if ("STOPFLAG".equalsIgnoreCase(fieldCode)) {
                        String string = cellValue = cellValue == null || "\u5426".equals(cellValue.toString()) ? "0" : "1";
                    }
                    if (("PARENTCODE".equals(fieldCode) || "BASEUNITID".equals(fieldCode) || "DIFFUNITID".equals(fieldCode)) && null != (orgCodeAndTitle = cellValue.toString()) && !orgCodeAndTitle.equals("-") && !orgCodeAndTitle.isEmpty()) {
                        String parentCode = orgCodeAndTitle.contains("|") ? orgCodeAndTitle.substring(0, orgCodeAndTitle.indexOf("|")) : orgCodeAndTitle;
                        String newParentCode = orgCode2GzCodeMap.containsKey(parentCode) ? orgCode2GzCodeMap.get(parentCode).toString() : parentCode;
                        cellValue = orgCodeAndTitle.replace(parentCode, newParentCode);
                    }
                    hssfRow.createCell(i).setCellValue(cellValue.toString());
                }
            }
            workbook.write(new FileOutputStream(mergeOrgFile));
            workbook.close();
            ZipUtils.addFile((String)dataZipLocation, (String)mergeOrgFile.getCanonicalPath());
        }
        catch (Exception e) {
            logs.add("\u3010\u5408\u5e76\u5355\u4f4d\u3011" + e.getMessage());
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u3010\u5408\u5e76\u5355\u4f4d\u3011" + e.getMessage());
        }
    }

    private static SyncSchemeParamDTO getSyncSchemeParamDTO(TaskDefine taskDefine, ReportDataSyncUploadLogEO uploadLogEO, ReportDataSyncUploadSchemeEO uploadSchemeEO, List<String> formKeys, List<String> logs) throws JQException {
        SyncSchemeParamDTO syncSchemeParamDTO = new SyncSchemeParamDTO();
        Boolean syncUnit = Boolean.FALSE;
        String executeKey = uploadLogEO.getId();
        syncSchemeParamDTO.setKey(executeKey);
        syncSchemeParamDTO.setTask(taskDefine == null ? null : taskDefine.getKey());
        syncSchemeParamDTO.setPeriodValue(uploadLogEO.getPeriodValue());
        if (DimensionUtils.isExistAdjust((String)taskDefine.getKey())) {
            syncSchemeParamDTO.setAdjustPeriod(uploadLogEO.getAdjustCode());
        }
        syncSchemeParamDTO.setPeriod(2);
        syncSchemeParamDTO.setAllowForceUpload(0);
        syncSchemeParamDTO.setIsUpload(0);
        String mergeDataParams = uploadSchemeEO.getContent();
        ReportDataSyncUploadSchemeVO uploadSchemeVO = (ReportDataSyncUploadSchemeVO)com.jiuqi.common.base.util.JsonUtils.readValue((String)mergeDataParams, ReportDataSyncUploadSchemeVO.class);
        ReportDataParam reportData = uploadSchemeVO.getReportData();
        if (!CollectionUtils.isEmpty(formKeys)) {
            syncSchemeParamDTO.setForm(String.join((CharSequence)";", formKeys));
        }
        if (!CollectionUtils.isEmpty((Collection)reportData.getUnitCodes())) {
            syncSchemeParamDTO.setEntity(String.join((CharSequence)";", reportData.getUnitCodes()));
        }
        syncSchemeParamDTO.setMappingSchemeKey(reportData.getMappingScheme());
        syncUnit = reportData.getSyncUnit();
        syncSchemeParamDTO.setEntityType(2);
        syncSchemeParamDTO.setFormType(2);
        syncSchemeParamDTO.setIsUpload(reportData.getExecuteUpload() != null && reportData.getExecuteUpload() != false ? 1 : 0);
        syncSchemeParamDTO.setAllowForceUpload(reportData.getAllowForceUpload() != null && reportData.getAllowForceUpload() != false ? 1 : 0);
        if (!(reportData.getCurrency() == null || "all".equals(reportData.getCurrency()) || "base".equals(reportData.getCurrency()) || "supBase".equals(reportData.getCurrency()))) {
            syncSchemeParamDTO.setDimKeys("MD_CURRENCY");
            syncSchemeParamDTO.setDimValues(reportData.getCurrency().replace(';', ','));
        }
        FormSchemeParam formSchemeParam = new FormSchemeParam();
        formSchemeParam.setTaskKey(taskDefine.getKey());
        formSchemeParam.setPeriodValue(uploadLogEO.getPeriodValue());
        String formSchemeKey = ReportDataSyncDataUtils.getFormSchemeKey(formSchemeParam.getTaskKey(), formSchemeParam.getPeriodValue());
        syncSchemeParamDTO.setFormSchemeKey(formSchemeKey);
        syncSchemeParamDTO.setSchemeName(taskDefine.getTitle());
        syncSchemeParamDTO.setSchemeKey(UUIDOrderUtils.newUUIDStr());
        if (!syncUnit.booleanValue()) {
            try {
                ReportDataSyncDataUtils.checkUnitCodes(reportData.getOrgType(), syncSchemeParamDTO, uploadLogEO, logs);
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return syncSchemeParamDTO;
    }

    private static void checkUnitCodes(String orgType, SyncSchemeParamDTO syncSchemeParamDTO, ReportDataSyncUploadLogEO uploadLogEO, List<String> logs) {
        Collection syncMethodSchedulerList = SpringContextUtils.getBeans(ISyncMethodScheduler.class);
        List<String> unitCodes = Arrays.asList(syncSchemeParamDTO.getEntity().split(";"));
        List orgDOS = ReportDataSyncDataUtils.listBaseOrgs(unitCodes).getRows();
        ArrayList needCheckOrgCodes = new ArrayList();
        orgDOS.stream().forEach(v -> {
            needCheckOrgCodes.add(v.getCode());
            if (v.get((Object)"gzcode") != null && !StringUtils.isEmpty((String)v.get((Object)"gzcode").toString())) {
                needCheckOrgCodes.add(v.get((Object)"gzcode").toString());
            }
        });
        ReportDataCheckParam checkParam = new ReportDataCheckParam();
        checkParam.setOrgCodes(needCheckOrgCodes);
        checkParam.setTaskId(syncSchemeParamDTO.getTask());
        checkParam.setOrgType(orgType);
        checkParam.setPeriodStr(uploadLogEO.getPeriodValue());
        ReportDataSyncServerListService serverListService = (ReportDataSyncServerListService)SpringContextUtils.getBean(ReportDataSyncServerListService.class);
        ReportDataSyncServerInfoVO serverInfoVO = serverListService.listServerInfos().stream().filter(v -> v.getSyncMethod() != null && v.getSyncType().contains(SyncTypeEnums.REPORTDATA.getCode())).findFirst().get();
        String type = serverInfoVO.getSyncMethod();
        MultilevelExtendHandler extendService = ((ISyncMethodScheduler)syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
        if (null == extendService) {
            return;
        }
        List<OrgDO> targetOrgDOS = extendService.getOrgDO(serverInfoVO, checkParam);
        if (CollectionUtils.isEmpty(targetOrgDOS)) {
            return;
        }
        List orgCodes = targetOrgDOS.stream().map(OrgDO::getCode).collect(Collectors.toList());
        ArrayList<String> needOrgCodes = new ArrayList<String>();
        for (OrgDO orgDO : orgDOS) {
            if (orgCodes.contains(orgDO.getCode())) {
                needOrgCodes.add(orgDO.getCode());
                continue;
            }
            if (orgDO.get((Object)"gzcode") != null && orgCodes.contains(orgDO.get((Object)"gzcode").toString())) {
                needOrgCodes.add(orgDO.getCode());
                continue;
            }
            logs.add(uploadLogEO.getTaskTitle() + "\u4efb\u52a1" + uploadLogEO.getPeriodStr() + orgDO.get((Object)"name") + "\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u8be5\u6761\u6570\u636e\u4e0d\u4e0a\u4f20\n");
        }
        syncSchemeParamDTO.setEntity(String.join((CharSequence)";", needOrgCodes));
    }

    private static ReportDataSyncUploadDataTaskVO convertUploadLogEOToReceiveTaskVO(ReportDataSyncUploadLogEO uploadLogEO) {
        ReportDataSyncScheduler reportDataSyncScheduler;
        List<ReportDataSyncServerInfoVO> syncServerInfoVOList;
        ReportDataSyncUploadDataTaskVO receiveTaskVO = new ReportDataSyncUploadDataTaskVO();
        receiveTaskVO.setId(UUIDUtils.newUUIDStr());
        receiveTaskVO.setSyncVersion(uploadLogEO.getSyncVersion());
        receiveTaskVO.setTaskCode(uploadLogEO.getTaskCode());
        receiveTaskVO.setTaskId(uploadLogEO.getTaskId());
        receiveTaskVO.setTaskTitle(uploadLogEO.getTaskTitle());
        receiveTaskVO.setSyncDataAttachId(uploadLogEO.getSyncDataAttachId());
        receiveTaskVO.setPeriodStr(uploadLogEO.getPeriodStr());
        receiveTaskVO.setPeriodValue(uploadLogEO.getPeriodValue());
        receiveTaskVO.setUploadTime(DateUtils.format((Date)uploadLogEO.getUploadTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        receiveTaskVO.setStatus(TaskStatusEnum.WAIT.getCode());
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user != null) {
            receiveTaskVO.setUploadUserId(user.getId());
            receiveTaskVO.setUploadUsername(user.getFullname());
        }
        if (!CollectionUtils.isEmpty(syncServerInfoVOList = (reportDataSyncScheduler = (ReportDataSyncScheduler)SpringContextUtils.getBean(ReportDataSyncScheduler.class)).getServerInfoVOList())) {
            receiveTaskVO.setOrgCode(syncServerInfoVOList.get(0).getOrgCode());
            receiveTaskVO.setOrgTitle(syncServerInfoVOList.get(0).getOrgTitle());
        }
        return receiveTaskVO;
    }

    /*
     * Exception decompiling
     */
    public static Boolean importData(String executeKey, String orgCode, String taskTitle, String syncDataAttachId, List<String> logs, ReportDataSyncServerInfoVO serverInfoVO) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static Boolean importHeterogeneousData(ReportDataSyncUploadDataTaskEO uploadDataTaskEO, String syncDataAttachId) {
        String taskId = uploadDataTaskEO.getTaskId();
        String periodValue = uploadDataTaskEO.getPeriodValue();
        String orgCode = uploadDataTaskEO.getOrgCode();
        String taskTitle = uploadDataTaskEO.getTaskTitle();
        List<String> unitCodes = Arrays.asList(uploadDataTaskEO.getUnitCodes().split(";"));
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        DimensionValueSet ds = new DimensionValueSet();
        try {
            ArrayList logs = new ArrayList();
            String schemeId = ReportDataSyncDataUtils.getFormSchemeKey(taskId, periodValue);
            ds.setValue("DATATIME", (Object)periodValue);
            ds.setValue("MD_ORG", unitCodes);
            CommonFileDTO syncDataAttachFile = commonFileService.queryOssFileByFileKey(syncDataAttachId);
            String rootPath = System.getProperty(FilenameUtils.normalize("java.io.tmpdir")) + File.separator + "heterogeneousDatasync" + File.separator + "importdata" + File.separator + LocalDate.now() + File.separator + syncDataAttachId;
            String filePath = rootPath + File.separator + taskTitle + ".zip";
            File file = new File(FilenameUtils.normalize(filePath));
            FileUtils.forceMkdirParent(file);
            FileUtils.copyInputStreamToFile(syncDataAttachFile.getInputStream(), file);
            String unZipFilePath = rootPath + File.separator + taskTitle;
            ZipUtil.unzipFile((String)unZipFilePath, (String)filePath, (String)"GBK");
            String snFolderPath = ReportDataSyncUtil.getSnFolderPath(syncDataAttachId);
            ReportDataSyncUtil.unzipFolder(filePath, ReportDataSyncTypeEnum.DATA.getCode(), snFolderPath);
            ArrayList<String> formKeys = new ArrayList<String>();
            ReportDataSyncDataUtils.importZbData(unZipFilePath, taskId, schemeId, formKeys, ds);
            ReportDataSyncDataUtils.importDataCheck(unZipFilePath, taskId, schemeId, formKeys, ds);
            ReportDataSyncDataUtils.importAttachment(unZipFilePath, taskId, schemeId);
        }
        catch (Throwable e) {
            throw new BusinessRuntimeException(e.getMessage(), e);
        }
        return true;
    }

    public static void importZbData(String unZipFilePath, String taskId, String schemeId, List<String> formKeys, DimensionValueSet ds) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            String filePath = CommonReportUtil.createNewPath((String)unZipFilePath, (String)"DATA_ZBDATA");
            File dataFile = new File(filePath);
            if (!dataFile.exists()) {
                return;
            }
            File[] files = dataFile.listFiles();
            if (files == null) {
                return;
            }
            String periodStr = ds.getValue("DATATIME").toString();
            List allFormDefine = runTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
            Map<String, File> fileMap = Arrays.stream(files).collect(Collectors.toMap(File::getName, f -> f));
            for (FormDefine formDefine : allFormDefine) {
                List regionDefineList = runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                for (DataRegionDefine regionDefine : regionDefineList) {
                    File file;
                    String fileName = formDefine.getFormCode();
                    if (!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionDefine.getRegionKind())) {
                        fileName = fileName + "_F" + regionDefine.getRegionTop();
                    }
                    if ((file = fileMap.get(fileName = fileName + ".csv")) == null) continue;
                    if (!formKeys.contains(formDefine.getKey())) {
                        formKeys.add(formDefine.getKey());
                    }
                    CsvReader reader = null;
                    reader = new CsvReader(file.getAbsolutePath());
                    ArrayList<String> heads = new ArrayList<String>();
                    if (reader.readHeaders()) {
                        String[] headers = reader.getHeaders();
                        for (int i = 0; i < headers.length; ++i) {
                            heads.add(headers[i]);
                        }
                        System.out.println();
                    }
                    ArrayList dataList = new ArrayList();
                    while (reader.readRecord()) {
                        String[] values = reader.getValues();
                        HashMap map = new HashMap();
                        for (int i = 0; i < values.length; ++i) {
                            map.put(heads.get(i), values[i]);
                        }
                        dataList.add(map);
                    }
                    RegionDataSet regionDataSet = null;
                    RegionData regionData = new RegionData();
                    regionData.initialize(regionDefine);
                    TableContext tableContext = new TableContext(taskId, schemeId, formDefine.getKey(), ds, OptTypes.FORM, ".csv");
                    tableContext.setExportBizkeyorder(true);
                    regionDataSet = new RegionDataSet(tableContext, regionData);
                    List defines = regionDataSet.getFieldDataList();
                    ArrayList<String> importHeads = new ArrayList<String>();
                    for (ExportFieldDefine exportFieldDefine : defines) {
                        int lastDotIndex = exportFieldDefine.getCode().lastIndexOf(".");
                        String fieldName = lastDotIndex != -1 ? exportFieldDefine.getCode().substring(lastDotIndex + 1) : exportFieldDefine.getCode();
                        importHeads.add(fieldName);
                    }
                    for (Map map : dataList) {
                        ArrayList<Object> importData = new ArrayList<Object>();
                        Iterator iterator = importHeads.iterator();
                        block19: while (iterator.hasNext()) {
                            String headCode;
                            switch (headCode = (String)iterator.next()) {
                                case "BIZKEYORDER": {
                                    importData.add(map.get("ID"));
                                    continue block19;
                                }
                                case "MDCODE": {
                                    importData.add(map.get("UNITCODE"));
                                    continue block19;
                                }
                                case "DATATIME": {
                                    importData.add(periodStr);
                                    continue block19;
                                }
                            }
                            importData.add(map.get(headCode));
                        }
                        regionDataSet.importDatas(importData);
                    }
                    regionDataSet.commit();
                }
            }
        }
        catch (Throwable e) {
            throw new BusinessRuntimeException(e.getMessage(), e);
        }
    }

    public static void importDataCheck(String unZipFilePath, String taskId, String schemeId, List<String> formKeys, DimensionValueSet ds) {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        ActionController actionController = (ActionController)SpringContextUtils.getBean(ActionController.class);
        try {
            String filePath = CommonReportUtil.createNewPath((String)unZipFilePath, (String)"DATA_CKDINFO");
            File file = new File(filePath + File.separator + "DATA_CKDINFO.csv");
            if (!file.exists()) {
                return;
            }
            CsvReader reader = null;
            reader = new CsvReader(file.getAbsolutePath());
            ArrayList dataList = new ArrayList();
            while (reader.readRecord()) {
                String[] values = reader.getValues();
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < values.length; ++i) {
                    map.put(checkDataHeads[i], values[i]);
                }
                dataList.add(map);
            }
            if (!dataList.isEmpty()) {
                dataList.remove(0);
            }
            List formulaSchemeDefines = formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(schemeId);
            String formulaSchemeKey = ((FormulaSchemeDefine)formulaSchemeDefines.get(0)).getKey();
            for (Map map : dataList) {
                FormulaCheckDesInfo checkDesObj = new FormulaCheckDesInfo();
                checkDesObj.setGlobCol(Integer.parseInt(map.get("GLOBCOL").toString()));
                checkDesObj.setGlobRow(Integer.parseInt(map.get("GLOBROW").toString()));
                String formCode = map.get("FORMCODE").toString().substring(0, map.get("FORMCODE").toString().indexOf("|"));
                FormDefine formDefine = runTimeViewController.queryFormByCodeInScheme(schemeId, formCode);
                checkDesObj.setFormKey(formDefine.getKey());
                checkDesObj.setFormulaCode(map.get("FORMULACODE").toString());
                checkDesObj.setFormulaSchemeKey(formulaSchemeKey);
                ds.setValue("MD_ORG", (Object)map.get("UNITCODE").toString());
                checkDesObj.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)ds));
                ds.setValue("MD_CURRENCY", (Object)map.get("CURRENCYCODE").toString());
                String floatId = map.get("FLOATAREA") == null || map.get("FLOATAREA").equals("") ? "null" : map.get("FLOATAREA").toString();
                checkDesObj.setFloatId("ID:" + floatId);
                DescriptionInfo descriptionInfo = new DescriptionInfo();
                descriptionInfo.setDescription("123");
                checkDesObj.setDescriptionInfo(descriptionInfo);
                checkDesObj.setFormSchemeKey(schemeId);
                checkDesObj.setDesKey(UUIDUtils.newUUIDStr());
                checkDesObj.setTaskKey(taskId);
                actionController.save(checkDesObj);
            }
        }
        catch (Throwable e) {
            LOGGER.error("\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u5bfc\u5165\u5931\u8d25" + e.getMessage(), e);
        }
    }

    public static void importAttachment(String unZipFilePath, String taskId, String schemeId) {
        AttachmentIOService attachmentIOService = (AttachmentIOService)SpringContextUtils.getBean(AttachmentIOService.class);
        try {
            String filePath = CommonReportUtil.createNewPath((String)unZipFilePath, (String)"DATA_ZBDATA_ATTACHMENT");
            String storePath = CommonReportUtil.createNewPath((String)filePath, (String)"attachments");
            File storeFile = new File(storePath);
            if (!storeFile.exists()) {
                return;
            }
            File[] files = storeFile.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            Map<String, File> fileMap = Arrays.stream(files).collect(Collectors.toMap(File::getName, v -> v));
            File csvFile = new File(filePath + File.separator + "DATA_CKDINFO.csv");
            if (!csvFile.exists()) {
                return;
            }
            CsvReader reader = null;
            reader = new CsvReader(csvFile.getAbsolutePath());
            ArrayList dataList = new ArrayList();
            while (reader.readRecord()) {
                String[] values = reader.getValues();
                HashMap<String, String> hashMap = new HashMap<String, String>();
                for (int i = 0; i < values.length; ++i) {
                    hashMap.put(attachmentsHeads[i], values[i]);
                }
                dataList.add(hashMap);
            }
            if (!dataList.isEmpty()) {
                dataList.remove(0);
            }
            for (Map map : dataList) {
                String id = map.get("ID").toString();
                String fileName = map.get("ATT_NAME").toString();
                String fileSize = map.get("ATT_SIZE").toString();
                ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
                FileUploadByGroupKeyContext groupKeyContext = new FileUploadByGroupKeyContext();
                groupKeyContext.setGroupKey(id);
                groupKeyContext.setFormSchemeKey(schemeId);
                groupKeyContext.setTaskKey(taskId);
                FileUploadInfo fileUploadInfo = new FileUploadInfo();
                File file = fileMap.get(fileName);
                FileInputStream in = new FileInputStream(file);
                Throwable throwable = null;
                try {
                    fileUploadInfo.setFile((InputStream)in);
                    fileUploadInfo.setName(fileName);
                    fileUploadInfo.setSize(Long.parseLong(fileSize));
                    fileUploadInfos.add(fileUploadInfo);
                    groupKeyContext.setFileUploadInfos(fileUploadInfos);
                    if (groupKeyContext.getFileUploadInfos() == null || groupKeyContext.getFileUploadInfos().isEmpty()) continue;
                    attachmentIOService.uploadByGroup(groupKeyContext);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (in == null) continue;
                    if (throwable != null) {
                        try {
                            ((InputStream)in).close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    ((InputStream)in).close();
                }
            }
        }
        catch (Throwable e) {
            LOGGER.error("\u9644\u4ef6\u5bfc\u5165\u5931\u8d25" + e.getMessage(), e);
        }
    }

    public static UploadParam getUploadParam(ReportDataSyncUploadDataTaskVO uploadDataTaskVO) {
        ReportDataSyncServerListService serverListService = (ReportDataSyncServerListService)SpringUtil.getBean(ReportDataSyncServerListService.class);
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringUtil.getBean(IDesignTimeViewController.class);
        IJtableParamService jtableParamService = (IJtableParamService)SpringUtil.getBean(IJtableParamService.class);
        IFuncExecuteService funcExecuteService = (IFuncExecuteService)SpringUtil.getBean(IFuncExecuteService.class);
        SingleMappingServiceImpl mappingConfigService = (SingleMappingServiceImpl)SpringUtil.getBean(SingleMappingServiceImpl.class);
        ReportDataSyncServerInfoVO serverInfoVO = serverListService.queryServerInfoByOrgCode(uploadDataTaskVO.getOrgCode());
        if (serverInfoVO == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + uploadDataTaskVO.getOrgCode() + "]\u672a\u5728\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u529f\u80fd\u70b9\u542f\u7528\uff0c\u4e0d\u652f\u6301\u4e0a\u4f20\u3002");
        }
        DesignTaskDefine taskDefine = designTimeViewController.queryTaskDefineByTaskTitle(uploadDataTaskVO.getTaskTitle());
        if (taskDefine == null) {
            throw new RuntimeException("\u672a\u67e5\u8be2\u5230\u4efb\u52a1:" + uploadDataTaskVO.getTaskTitle());
        }
        uploadDataTaskVO.setId(StringUtils.isEmpty((String)uploadDataTaskVO.getId()) ? UUIDOrderUtils.newUUIDStr() : uploadDataTaskVO.getId());
        uploadDataTaskVO.setTaskId(taskDefine.getKey());
        uploadDataTaskVO.setTaskCode(taskDefine.getTaskCode());
        uploadDataTaskVO.setTaskTitle(taskDefine.getTitle());
        uploadDataTaskVO.setSyncDataAttachId(StringUtils.isEmpty((String)uploadDataTaskVO.getSyncDataAttachId()) ? UUIDOrderUtils.newUUIDStr() : uploadDataTaskVO.getSyncDataAttachId());
        uploadDataTaskVO.setPeriodStr(ReportDataSyncUtils.getPeriodTitle(uploadDataTaskVO.getPeriodValue()));
        UploadParam uploadParam = new UploadParam();
        uploadParam.setTaskKey(taskDefine.getKey());
        FormSchemeDefine formSchemeDefine = funcExecuteService.queryFormScheme(taskDefine.getKey(), uploadDataTaskVO.getPeriodValue());
        if (formSchemeDefine == null) {
            throw new BusinessRuntimeException("\u4e0a\u62a5\u4efb\u52a1[" + uploadDataTaskVO.getTaskTitle() + "]-\u65f6\u671f[" + uploadDataTaskVO.getPeriodStr() + "]\u672a\u67e5\u8be2\u5230\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
        }
        uploadParam.setFormSchemeKey(formSchemeDefine.getKey());
        List schemeMapping = mappingConfigService.getAllMappingInReport(formSchemeDefine.getKey());
        if (uploadDataTaskVO.getMappingSchemeTitle() != null && uploadDataTaskVO.getMappingSchemeTitle().isEmpty()) {
            schemeMapping = schemeMapping.stream().filter(mapping -> mapping.getConfigName().equals(uploadDataTaskVO.getMappingSchemeTitle())).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty((Collection)schemeMapping)) {
            throw new RuntimeException("\u672a\u67e5\u8be2\u5230\u6620\u5c04\u65b9\u6848:");
        }
        uploadDataTaskVO.setMappingSchemeId(((SingleConfigInfo)schemeMapping.get(0)).getConfigKey());
        uploadParam.setConfigKey(((SingleConfigInfo)schemeMapping.get(0)).getConfigKey());
        EntityViewData dwEntity = jtableParamService.getDwEntity(formSchemeDefine.getKey());
        YearPeriodObject yp = new YearPeriodObject(null, uploadDataTaskVO.getPeriodValue());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)dwEntity.getTableName(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = instance.getOrgByCode(uploadDataTaskVO.getOrgCode());
        if (orgCacheVO == null) {
            throw new RuntimeException("\u672a\u67e5\u8be2\u5230\u5355\u4f4d:" + uploadDataTaskVO.getOrgCode());
        }
        String currencyCode = "CNY";
        if (orgCacheVO.getTypeFieldValue("CURRENCYIDS") != null && !"".equals(orgCacheVO.getTypeFieldValue("CURRENCYIDS"))) {
            String[] currencyCodes = ((String)orgCacheVO.getTypeFieldValue("CURRENCYIDS")).split(";");
            currencyCode = currencyCodes[0];
        }
        Map dimensionValueMap = DimensionUtils.buildDimensionMap((String)taskDefine.getKey(), (String)currencyCode, (String)uploadDataTaskVO.getPeriodValue(), (String)orgCacheVO.getOrgTypeId(), (String)orgCacheVO.getCode(), (String)"0");
        uploadParam.setDimensionSet(dimensionValueMap);
        uploadDataTaskVO.setOrgTitle(orgCacheVO.getTitle());
        uploadParam.setVariableMap(new HashMap());
        return uploadParam;
    }

    public static void importBaseOrgs(String orgCode, String snFolderPath, ReportDataSyncUploadDataTaskVO uploadDataTaskVO, List<String> logs) {
        block14: {
            File orgFile = new File(snFolderPath + "/org");
            if (!orgFile.exists()) {
                return;
            }
            OrgImportTemplateService orgImportTemplateService = (OrgImportTemplateService)SpringContextUtils.getBean(OrgImportTemplateService.class);
            DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
            try {
                byte[] bytes = FileCopyUtils.copyToByteArray(orgFile);
                VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("file", orgFile.getName(), null, bytes);
                OrgImportTemplateDTO template = new OrgImportTemplateDTO();
                template.setId(UUID.randomUUID());
                String resultKey = "importCheckResultKey" + new Date().getTime();
                template.setResultKey(resultKey);
                template.setCode("MD_ORG");
                template.setPagination(false);
                template.setLimit(0);
                template.setOffset(0);
                HashMap<String, Object> templatedata = new HashMap<String, Object>();
                templatedata.put("categoryname", "MD_ORG");
                Date date = new GregorianCalendar(1970, 1, 1, 0, 0, 0).getTime();
                OrgVersionVO orgVersion = GcOrgPublicTool.getInstance().getTypeVerInstance().getOrgVersionByType("MD_ORG", date);
                templatedata.put("title", orgVersion.getOrgType().getTitle());
                templatedata.put("firstline", 2);
                templatedata.put("dateformat", "yyyy-MM-dd");
                DataModelDTO dataModelDTO = new DataModelDTO();
                dataModelDTO.setName("MD_ORG");
                DataModelDO data = dataModelClient.get(dataModelDTO);
                List dataColumns = data.getColumns();
                ArrayList fields = new ArrayList();
                if (uploadDataTaskVO == null) {
                    return;
                }
                List headCodes = uploadDataTaskVO.getHeadCodes();
                List headTitles = uploadDataTaskVO.getHeadTitles();
                Map<String, DataModelColumn> dataModelColumnMap = dataColumns.stream().collect(Collectors.toMap(column -> column.getColumnName().toUpperCase(), entity -> entity, (f1, f2) -> f1));
                for (int i = 0; i < headCodes.size(); ++i) {
                    String headCode = (String)headCodes.get(i);
                    DataModelColumn column2 = dataModelColumnMap.get(headCode);
                    if (column2 == null) {
                        logs.add("\u57fa\u7840\u7ec4\u7ec7\u5b57\u6bb5\u3010" + headCode + "|" + (String)headTitles.get(i) + "\u3011\u4e0d\u5b58\u5728\uff0c\u8bf7\u521b\u5efa\u5b57\u6bb5\u540e\u518d\u6b21\u540c\u6b65;\n");
                        continue;
                    }
                    HashMap<String, Object> field = new HashMap<String, Object>();
                    field.put("checkval", false);
                    field.put("column", headCodes.indexOf(headCode) + 1);
                    field.put("name", column2.getColumnName());
                    field.put("type", column2.getColumnType().name());
                    field.put("title", headTitles.get(i));
                    fields.add(field);
                }
                templatedata.put("fields", fields);
                template.setTemplatedata((Object)JSONUtil.toJSONString(Collections.singletonList(templatedata)));
                template.setTempImportTemplate((OrgImportTemplateDO)template);
                orgImportTemplateService.importCheck(template, (MultipartFile)multipartFile);
                Map resultMap = null;
                boolean pd = false;
                for (int i = 15; i > 0; --i) {
                    String resString = OrgDataCacheUtil.getImportDataResult((String)resultKey);
                    if (resString != null && (resultMap = (Map)com.jiuqi.common.base.util.JsonUtils.readValue((String)resString, (TypeReference)new TypeReference<Map<String, Object>>(){})).containsKey("code")) {
                        pd = true;
                        break;
                    }
                    try {
                        Thread.sleep(2000L);
                        continue;
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!pd) break block14;
                if ((Integer)resultMap.get("code") == 0 && resultMap.containsKey("data")) {
                    String result = com.jiuqi.common.base.util.JsonUtils.writeValueAsString(resultMap.get("data"));
                    Map importDataMap = (Map)com.jiuqi.common.base.util.JsonUtils.readValue((String)result, (TypeReference)new TypeReference<Map<String, List<OrgDTO>>>(){});
                    template.setImportDataMap(importDataMap);
                    try {
                        String resString;
                        orgImportTemplateService.importSave(template);
                        for (int i = 30; !(i <= 0 || (resString = OrgDataCacheUtil.getImportDataResult((String)template.getResultKey())) != null && (resultMap = (Map)com.jiuqi.common.base.util.JsonUtils.readValue((String)resString, (TypeReference)new TypeReference<Map<String, Object>>(){})).containsKey("code")); --i) {
                            Thread.sleep(2000L);
                        }
                        break block14;
                    }
                    catch (Exception e) {
                        throw new BusinessRuntimeException((Throwable)e);
                    }
                }
                logs.add("\u5bfc\u5165\u57fa\u7840\u7ec4\u7ec7\u5931\u8d25\uff1a" + resultMap.get("msg"));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void importMergeOrgs(List<String> orgCodes, String snFolderPath, List<String> logs) {
        GcOrgDataService gcOrgDataService = (GcOrgDataService)SpringContextUtils.getBean(GcOrgDataService.class);
        File dataFolder = new File(snFolderPath);
        String orgType = null;
        for (File mergeOrgFile : dataFolder.listFiles()) {
            String fileName;
            int lastDashIndex;
            if (!mergeOrgFile.getName().startsWith("MD_ORG") || (lastDashIndex = (fileName = mergeOrgFile.getName()).lastIndexOf(45)) == -1) continue;
            orgType = fileName.substring(0, lastDashIndex);
            break;
        }
        if (orgType == null) {
            return;
        }
        Object parentId = null;
        Object parents = null;
        for (File mergeOrgFile : dataFolder.listFiles()) {
            if (!mergeOrgFile.getName().contains(orgType)) continue;
            String[] arr = mergeOrgFile.getName().split("-");
            String tableName = arr[0];
            String orgVer = arr[1];
            ExportConditionVO conditionVO = new ExportConditionVO();
            conditionVO.setOrgVer(orgVer);
            conditionVO.setOrgType(tableName);
            conditionVO.setTableName(tableName);
            conditionVO.setExecuteOnDuplicate(Boolean.valueOf(true));
            YearPeriodObject yp = new YearPeriodObject(null, orgVer);
            GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)tableName, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            Workbook workbook = ReportDataSyncUtil.readWorkBook(mergeOrgFile.getPath());
            ReportDataSyncDataUtils.modifyParentId(workbook, orgCodes, noAuthzOrgTool);
            List exportMessageList = gcOrgDataService.uploadOrgData(workbook, conditionVO, false);
            ReportDataSyncDataUtils.modifyReportType(orgType, orgVer);
            logs.addAll(exportMessageList.stream().map(ExportMessageVO::getMessage).collect(Collectors.toList()));
        }
    }

    private static void modifyReportType(String orgType, String orgVer) {
        OrgDataService orgService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(orgType);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setStopflag(Integer.valueOf(-1));
        List orgDOList = orgService.list(orgDTO).getRows();
        HashMap<String, OrgDO> singleOrg = new HashMap<String, OrgDO>();
        for (OrgDO orgDO : orgDOList) {
            if (orgDO.get((Object)"bblx") == null || StringUtils.isEmpty((String)orgDO.get((Object)"bblx").toString()) || !orgDO.get((Object)"bblx").toString().equals("9")) continue;
            singleOrg.put(orgDO.getCode(), orgDO);
        }
        for (OrgDO orgDO : orgDOList) {
            if (orgDO.get((Object)"parentcode") == null || StringUtils.isEmpty((String)orgDO.get((Object)"parentcode").toString())) continue;
            singleOrg.remove(orgDO.get((Object)"parentcode").toString());
        }
        OrgDataClient orgDataClient = (OrgDataClient)SpringContextUtils.getBean(OrgDataClient.class);
        Date date = PeriodUtil.period2Calendar((String)orgVer).getTime();
        Set keySet = singleOrg.keySet();
        for (String key : keySet) {
            OrgDO orgDO = (OrgDO)singleOrg.get(key);
            OrgDTO curOrg = new OrgDTO((Map)orgDO);
            curOrg.remove((Object)"bblx");
            curOrg.put("bblx", (Object)"0");
            curOrg.setVersionDate(date);
            String msg = orgDataClient.update(curOrg).getMsg();
            LOGGER.info(curOrg.getCode() + "\u5355\u4f4d\u4fee\u6539\u62a5\u8868\u7c7b\u578b\u7ed3\u679c\u4e3a\uff1a" + msg);
        }
    }

    private static void modifyParentId(Workbook workbook, List<String> orgCodes, GcOrgCenterService noAuthzOrgTool) {
        String cellValue;
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);
        ArrayList headers = new ArrayList();
        int codeIndex = 0;
        int parentIdIndex = 0;
        for (Cell cell : headerRow) {
            cellValue = cell.getStringCellValue().trim();
            if (cellValue.endsWith("|CODE")) {
                codeIndex = cell.getColumnIndex();
                continue;
            }
            if (!cellValue.endsWith("|PARENTCODE")) continue;
            parentIdIndex = cell.getColumnIndex();
        }
        for (int i = 1; i < sheet.getLastRowNum(); ++i) {
            GcOrgCacheVO currentUnit;
            Cell codeCell = sheet.getRow(i).getCell(codeIndex);
            cellValue = codeCell.getStringCellValue().trim();
            if (!orgCodes.contains(cellValue) || (currentUnit = noAuthzOrgTool.getOrgByCode(cellValue)) == null) continue;
            String parentId = currentUnit.getParentId();
            Cell parentCell = sheet.getRow(i).getCell(parentIdIndex);
            parentCell.setCellValue(parentId);
        }
    }

    private static ReportDataSyncUploadDataTaskVO getReportDataSyncUploadDataTaskVO(MultipartFile syncDataAttachFile) throws IOException {
        byte[] bytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)syncDataAttachFile, (String)REPORT_DATA_META_FILENAME);
        if (bytes == null) {
            throw new BusinessRuntimeException("\u6570\u636e\u5305\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u7f3a\u5c11\u6587\u4ef6\uff1aReportDataMeta.JSON");
        }
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((byte[])bytes, ReportDataSyncUploadDataTaskVO.class);
        return uploadDataTaskVO;
    }

    public static boolean dataSyncProcessON(TaskState state) {
        return state.equals((Object)TaskState.PROCESSING) || state.equals((Object)TaskState.WAITING) || state.equals((Object)TaskState.CANCELING);
    }

    public static boolean dataSyncProcessEnd(TaskState state) {
        return state.equals((Object)TaskState.CANCELED) || state.equals((Object)TaskState.FINISHED);
    }

    public static boolean dataSyncProcessError(TaskState state) {
        return state.equals((Object)TaskState.ERROR) || state.equals((Object)TaskState.OVERTIME) || state.equals((Object)TaskState.NONE);
    }

    public static String getTaskMessageFromAsyncTask(JIOImportResultObject jioUnitImportResult) {
        if (jioUnitImportResult == null) {
            return "";
        }
        StringBuilder importLog = new StringBuilder();
        List errorUnits = jioUnitImportResult.getErrorUnits();
        importLog.append("\u6570\u636e\u88c5\u5165\u5b8c\u6210\u3002\n");
        importLog.append("JIO\u6587\u4ef6\u5355\u4f4d\u603b\u6570:").append(jioUnitImportResult.getUploadUnitNum()).append(",\u5bfc\u5165\u6210\u529f").append(jioUnitImportResult.getAllSuccesssUnitNum());
        importLog.append(",\u5bfc\u5165\u5f02\u5e38").append(jioUnitImportResult.getErrorUnitNum()).append(",\u5176\u4e2d:\u5176\u4ed6\u9519\u8bef:").append(errorUnits == null ? 0 : errorUnits.size()).append("\u3002\n");
        if (CollectionUtils.isEmpty((Collection)errorUnits)) {
            return importLog.toString();
        }
        importLog.append("\n\u9519\u8bef\u4fe1\u606f:\n");
        for (JIOUnitImportResult errorUnit : errorUnits) {
            List formResults;
            importLog.append("   ").append(errorUnit.getUnitCode()).append("|").append(errorUnit.getUnitTitle()).append(":\n");
            if (!StringUtils.isEmpty((String)errorUnit.getMessage())) {
                importLog.append("   ").append(errorUnit.getMessage()).append("\n");
            }
            if (CollectionUtils.isEmpty((Collection)(formResults = errorUnit.getFormResults()))) continue;
            for (JIOFormImportResult errorForm : formResults) {
                importLog.append("      ").append(errorForm.getFormCode()).append("|").append(errorForm.getFormTitle()).append(": ").append(errorForm.getMessage()).append("\n");
            }
        }
        return importLog.toString();
    }

    public static String getFormSchemeKey(String taskKey, String periodValue) throws JQException {
        String formSchemeKey;
        try {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodValue, taskKey);
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
            if (!org.springframework.util.StringUtils.hasText(formSchemeKey)) {
                List formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskKey);
                formSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
            }
        }
        catch (Exception e) {
            LOGGER.error(String.format("\u67e5\u8be2\u4efb\u52a1:%s\u65f6\u671f:%s\u5173\u8054\u7684\u65b9\u6848\u65f6\uff0c\u53d1\u751f\u5f02\u5e38", periodValue, taskKey));
            throw new JQException((ErrorEnum)SchemeSchemeException.SEARCH_SCHEME_FORM_SCHEME_ERROR, (Throwable)e);
        }
        return formSchemeKey;
    }

    public static String getPeriodValue(String periodType, String periodStr, PeriodType taskPeriodType) {
        String periodValue;
        String schemePeriodType = periodType;
        ReportDataSyncUploadSchemePeriodType uploadSchemePeriodType = ReportDataSyncUploadSchemePeriodType.getPperiodTypeByName(periodType);
        switch (uploadSchemePeriodType) {
            case before: {
                periodValue = ReportDataSyncUtils.getBeforePriorPeriod(taskPeriodType);
                break;
            }
            case current: {
                periodValue = ReportDataSyncUtils.getCurrentPriorPeriod(taskPeriodType);
                break;
            }
            case custom: {
                periodValue = periodStr;
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u4e0a\u62a5\u65b9\u6848\u65f6\u671f\u7c7b\u578b\u3002");
            }
        }
        return periodValue;
    }

    public static Map<String, DataModelColumn> getFixedColumnMap(OrgCategoryDO orgCategoryDO) {
        HashMap<String, DataModelColumn> res = new HashMap<String, DataModelColumn>();
        DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(orgCategoryDO.getName());
        DataModelDO data = dataModelClient.get(dataModelDTO);
        if (data.getColumns() == null) {
            return res;
        }
        List list = data.getColumns();
        for (DataModelColumn column : list) {
            res.put(column.getColumnName(), column);
        }
        return res;
    }

    public static List<String> checkOrgAndCurrency(ReportDataSyncUploadSchemeVO uploadSchemeVO) {
        if (uploadSchemeVO == null || uploadSchemeVO.getReportData() == null) {
            return null;
        }
        ReportDataParam reportData = uploadSchemeVO.getReportData();
        if (reportData.getCurrency() == null) {
            return null;
        }
        if (Objects.equals(reportData.getCurrency(), "all") || Objects.equals(reportData.getCurrency(), "base") || Objects.equals(reportData.getCurrency(), "supBase")) {
            return null;
        }
        List<String> chooseCurrey = Arrays.asList(reportData.getCurrency().split(";"));
        List orgCodes = reportData.getUnitCodes();
        Boolean pd = Boolean.FALSE;
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        for (String orgCode : orgCodes) {
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(orgCode);
            List<String> orgCurrency = Arrays.asList(orgCacheVO.getFields().get("CURRENCYIDS").toString().split(";"));
            if (CollectionUtils.isEmpty(orgCurrency = orgCurrency.stream().filter(chooseCurrey::contains).collect(Collectors.toList()))) continue;
            pd = Boolean.TRUE;
            break;
        }
        if (!pd.booleanValue()) {
            throw new RuntimeException("\u6240\u9009\u5355\u4f4d\u4e0d\u6d89\u53ca\u6240\u9009\u5e01\u79cd\u6570\u636e\uff0c\u4e0a\u4f20\u5931\u8d25");
        }
        return null;
    }

    public static ReportDataSyncUploadLogEO buildUploadLogEO(ReportDataSyncUploadSchemeEO uploadSchemeEO) {
        String periodValue;
        TaskDefine taskDefine;
        String adjustCode = null;
        try {
            RunTimeTaskDefineService taskDefineService = (RunTimeTaskDefineService)SpringContextUtils.getBean(RunTimeTaskDefineService.class);
            if (uploadSchemeEO.getApplicationMode() == null || uploadSchemeEO.getApplicationMode() == 1) {
                String taskId = uploadSchemeEO.getTaskId();
                taskDefine = taskDefineService.queryTaskDefine(taskId);
                PeriodType taskPeriodType = taskDefine.getPeriodType();
                periodValue = ReportDataSyncDataUtils.getPeriodValue(uploadSchemeEO.getPeriodType(), uploadSchemeEO.getPeriodStr(), taskPeriodType);
                adjustCode = uploadSchemeEO.getAdjustCode();
            } else {
                String mergeDataParams = uploadSchemeEO.getContent();
                ReportDataSyncUploadSchemeVO uploadSchemeVO = (ReportDataSyncUploadSchemeVO)com.jiuqi.common.base.util.JsonUtils.readValue((String)mergeDataParams, ReportDataSyncUploadSchemeVO.class);
                ReportDataParam reportData = uploadSchemeVO.getReportData();
                if (reportData != null && !StringUtils.isEmpty((String)reportData.getTaskId())) {
                    String taskId = reportData.getTaskId();
                    taskDefine = taskDefineService.queryTaskDefine(taskId);
                    PeriodType taskPeriodType = taskDefine.getPeriodType();
                    adjustCode = reportData.getAdjustCode();
                    if (StringUtils.isEmpty((String)reportData.getPeriodStr()) && null != reportData.getPeriodOffset()) {
                        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskPeriodType.type(), (int)reportData.getPeriodOffset());
                        periodValue = currentPeriod.toString();
                    } else {
                        periodValue = reportData.getPeriodStr();
                    }
                } else {
                    taskDefine = null;
                    periodValue = null;
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        ReportDataSyncUploadLogEO uploadLogEO = new ReportDataSyncUploadLogEO();
        uploadLogEO.setId(UUIDUtils.newUUIDStr());
        uploadLogEO.setSyncVersion(DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmssSSS"));
        uploadLogEO.setTaskId(taskDefine == null ? null : taskDefine.getKey());
        uploadLogEO.setTaskTitle(taskDefine == null ? uploadSchemeEO.getTitle() : taskDefine.getTitle());
        uploadLogEO.setTaskCode(taskDefine == null ? null : taskDefine.getTaskCode());
        uploadLogEO.setUploadSchemeId(uploadSchemeEO.getId());
        uploadLogEO.setPeriodValue(periodValue);
        uploadLogEO.setAdjustCode(StringUtils.isEmpty((String)adjustCode) ? "0" : adjustCode);
        uploadLogEO.setPeriodStr(periodValue == null ? null : ReportDataSyncUtils.getPeriodTitle(periodValue));
        NpContext npContext = NpContextHolder.getContext();
        uploadLogEO.setUploadUsername(npContext.getUser().getFullname());
        uploadLogEO.setUploadUserId(npContext.getUserId());
        uploadLogEO.setUploadStatus(UploadStatusEnum.UPLOADING.getCode());
        uploadLogEO.setUploadTime(new Date());
        return uploadLogEO;
    }

    private static void setDsContext(MultipartFile syncDataAttachFile) {
        try {
            DsContextImpl context = (DsContextImpl)DsContextHolder.getDsContext();
            byte[] bytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)syncDataAttachFile, (String)"GzOrgCode.JSON");
            if (bytes != null) {
                String gzOrgCodejson = new String(bytes);
                context.getExtension().put("GzOrgCodeMap", (Serializable)((Object)gzOrgCodejson));
                DsContextHolder.setDsContext((DsContext)context);
            } else {
                LOGGER.info("\u672a\u627e\u5230\u56fd\u8d44\u5355\u4f4d\u6620\u5c04\u6587\u4ef6\uff1aGzOrgCode.JSON");
            }
        }
        catch (Exception e) {
            LOGGER.error("\u5355\u4f4d\u8f6c\u6362\u63a5\u53e3\u8bbe\u7f6e\u73af\u5883\u53c2\u6570\u5931\u8d25");
        }
    }

    private static void exportGzOrgCode(String rootPath, String dataZipLocation) {
        PageVO<OrgDO> baseOrgs = ReportDataSyncDataUtils.listBaseOrgs(null);
        Map<String, String> gzCode2OrgCode = baseOrgs.getRows().stream().filter(orgDO -> !StringUtils.isEmpty((String)((String)orgDO.get((Object)"gzcode")))).collect(Collectors.toMap(OrgDO::getCode, orgDo -> (String)orgDo.get((Object)"gzcode"), (o1, o2) -> o2));
        try {
            String gzOrgCodeMap = rootPath + "GzOrgCode.JSON";
            File reportDataMetaFile = new File(gzOrgCodeMap);
            reportDataMetaFile.createNewFile();
            try (FileOutputStream reportDataMetaFileOutputStream = new FileOutputStream(reportDataMetaFile);){
                IOUtils.write(com.jiuqi.common.base.util.JsonUtils.writeValueAsString(gzCode2OrgCode).getBytes(), (OutputStream)reportDataMetaFileOutputStream);
                reportDataMetaFileOutputStream.close();
                ZipUtils.addFile((String)dataZipLocation, (String)gzOrgCodeMap);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u56fd\u8d44\u5355\u4f4d\u4ee3\u7801\u5bfc\u51fa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage());
        }
    }

    public static ObjectInfo exportHeterogeneousDataToOss(TaskDefine taskDefine, ReportDataSyncUploadSchemeEO uploadSchemeEO, ReportDataSyncUploadLogEO uploadLogEO, List<String> logs) {
        if (taskDefine == null) {
            LOGGER.error("\u5f02\u6784\u7cfb\u7edf\u6570\u636e\u5305\u5bfc\u51fa\u5931\u8d25\uff1a\u4e0a\u4f20\u65b9\u6848\u4e2d\u672a\u914d\u7f6e\u62a5\u8868\u6570\u636e");
            return null;
        }
        String syncDataAttachId = uploadLogEO.getSyncDataAttachId();
        logs.add("\u6b63\u5728\u8fdb\u884c\u5f02\u6784\u6570\u636e\u5305\u751f\u6210;");
        ReportDataSyncUploadSchemeVO uploadSchemeVO = null;
        String schemeEOContent = uploadSchemeEO.getContent();
        uploadSchemeVO = (ReportDataSyncUploadSchemeVO)com.jiuqi.common.base.util.JsonUtils.readValue((String)schemeEOContent, ReportDataSyncUploadSchemeVO.class);
        ReportDataParam reportData = uploadSchemeVO.getReportData();
        List formKeys = reportData.getFormKeys();
        String orgType = reportData.getOrgType();
        String adjustCode = reportData.getAdjustCode();
        String periodValue = null;
        if (StringUtils.isEmpty((String)reportData.getPeriodStr()) && null != reportData.getPeriodOffset()) {
            PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)reportData.getPeriodOffset());
            periodValue = currentPeriod.toString();
        } else {
            periodValue = reportData.getPeriodStr();
        }
        List<Object> currency = new ArrayList();
        if (!(reportData.getCurrency() == null || "all".equals(reportData.getCurrency()) || "base".equals(reportData.getCurrency()) || "supBase".equals(reportData.getCurrency()))) {
            currency = Arrays.asList(reportData.getCurrency().split(";"));
        }
        DimensionValueSet ds = DimensionUtils.generateDimSet((Object)reportData.getUnitCodes(), (Object)periodValue, currency, (Object)"", (String)adjustCode, (String)reportData.getTaskId());
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        try {
            String rootPath = BatchExportConsts.EXPORTDIR + File.separator + "reportdatasync" + File.separator + "exportdata" + File.separator + LocalDate.now() + File.separator + syncDataAttachId + File.separator;
            File rootFile = new File(rootPath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String fileName = taskDefine.getTitle() + "\u6570\u636e\u5305.zip";
            String dataZipLocation = rootPath + fileName;
            ReportDataSyncUtils.createEmptyZipFile(dataZipLocation);
            ArrayList<FileInfo> attamentFile = new ArrayList<FileInfo>();
            ReportDataSyncDataUtils.exportDataCheck(dataZipLocation, rootPath, reportData.getSchemeId(), formKeys, ds);
            ReportDataSyncDataUtils.exportZbData(dataZipLocation, rootPath, taskDefine.getKey(), reportData.getSchemeId(), formKeys, ds, attamentFile);
            ReportDataSyncDataUtils.exportAttachment(dataZipLocation, rootPath, attamentFile);
            File srcFile = new File(dataZipLocation);
            byte[] bytes = FileCopyUtils.copyToByteArray(srcFile);
            VaParamSyncMultipartFile zipFile = new VaParamSyncMultipartFile("multipartFile", fileName, "multipart/form-data; charset=ISO-8859-1", bytes);
            commonFileService.uploadFileToOss((MultipartFile)zipFile, syncDataAttachId);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5f02\u6784\u7cfb\u7edf\u6570\u636e\u5305\u751f\u6210\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage());
        }
        return null;
    }

    private static void exportDataCheck(String dataZipLocation, String rootPath, String schemeId, List<String> formKeys, DimensionValueSet ds) throws Exception {
        ICheckErrorDescriptionService errorDescriptionService = (ICheckErrorDescriptionService)SpringContextUtils.getBean(ICheckErrorDescriptionService.class);
        DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)SpringContextUtils.getBean(DimensionCollectionUtil.class);
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
        RunTimeAuthViewController runTimeViewController = (RunTimeAuthViewController)SpringContextUtils.getBean(RunTimeAuthViewController.class);
        String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"DATA_CKDINFO");
        File file = new File(filePath + File.separator + "DATA_CKDINFO.csv");
        file.createNewFile();
        String periodStr = ds.getValue("DATATIME").toString();
        YearPeriodObject yp = new YearPeriodObject(schemeId, periodStr);
        int year = yp.getYear();
        int period = yp.getPeriod();
        CheckDesQueryParam queryParam = new CheckDesQueryParam();
        List formulaSchemeDefines = formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(schemeId);
        List formulaSchemeKeys = formulaSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        queryParam.setDimensionCollection(dimensionCollectionUtil.getDimensionCollection(ds, schemeId));
        queryParam.setFormSchemeKey(schemeId);
        queryParam.setFormulaSchemeKey(formulaSchemeKeys);
        queryParam.setFormKey(formKeys);
        queryParam.setFormulaKey(new ArrayList());
        List checkDesObjs = errorDescriptionService.queryFormulaCheckDes(queryParam);
        ArrayList checkDataList = new ArrayList();
        for (int i = 0; i < checkDesObjs.size(); ++i) {
            CheckDesObj checkDesObj = (CheckDesObj)checkDesObjs.get(i);
            String unitCode = ((DimensionValue)checkDesObj.getDimensionSet().get("MD_ORG")).getValue();
            String currency = ((DimensionValue)checkDesObj.getDimensionSet().get("MD_CURRENCY")).getValue();
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("ID", i);
            hashMap.put("YEAR", year);
            hashMap.put("PERIOD", period);
            hashMap.put("UNITCODE", unitCode);
            hashMap.put("CURRENCYCODE", currency);
            hashMap.put("GLOBROW", checkDesObj.getGlobRow());
            hashMap.put("GLOBCOL", checkDesObj.getGlobCol());
            hashMap.put("CKDINFO", checkDesObj.getCheckDescription().getDescription());
            FormDefine formDefine = runTimeViewController.queryEntityForm(checkDesObj.getFormKey());
            if (formDefine != null) {
                hashMap.put("FORMCODE", formDefine.getFormCode() + "|" + formDefine.getTitle());
            }
            hashMap.put("FORMULACODE", checkDesObj.getFormulaCode());
            hashMap.put("CREATETIME", checkDesObj.getCheckDescription().getUpdateTime());
            hashMap.put("UPTIMESTAMP", checkDesObj.getCheckDescription().getUpdateTime());
            String floatId = checkDesObj.getFloatId().substring(3, checkDesObj.getFloatId().length() - 1);
            if (!floatId.equals("null")) {
                hashMap.put("FLOATAREA", floatId);
            }
            checkDataList.add(hashMap);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));){
            String headRow = String.join((CharSequence)",", checkDataHeads);
            bw.write(headRow);
            for (Map map : checkDataList) {
                StringBuffer formatResul = new StringBuffer();
                for (String head : checkDataHeads) {
                    formatResul.append((Object)(map.get(head) == null ? "" : map.get(head))).append(",");
                }
                bw.newLine();
                String oneRowStr = formatResul.deleteCharAt(formatResul.length() - 1).toString();
                bw.write(oneRowStr);
            }
        }
        ZipUtils.addFile((String)dataZipLocation, (String)filePath);
    }

    private static void exportZbData(String dataZipLocation, String rootPath, String taskId, String schemeId, List<String> formKeys, DimensionValueSet ds, List<FileInfo> attamentFile) throws Exception {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"DATA_ZBDATA");
        String periodStr = ds.getValue("DATATIME").toString();
        YearPeriodObject yp = new YearPeriodObject(schemeId, periodStr);
        int year = yp.getYear();
        int period = yp.getPeriod();
        for (String formKey : formKeys) {
            FormDefine formDefine = runTimeViewController.queryFormById(formKey);
            List regionDefineList = runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            for (DataRegionDefine regionDefine : regionDefineList) {
                List defines;
                String fileName = filePath + formDefine.getFormCode();
                if (!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionDefine.getRegionKind())) {
                    fileName = fileName + "_F" + regionDefine.getRegionTop();
                }
                fileName = fileName + ".csv";
                RegionDataSet regionDataSet = null;
                RegionData regionData = new RegionData();
                regionData.initialize(regionDefine);
                TableContext tableContext = new TableContext(taskId, schemeId, formKey, ds, OptTypes.FORM, ".csv");
                tableContext.setExportBizkeyorder(true);
                regionDataSet = new RegionDataSet(tableContext, regionData);
                if (!CollectionUtils.isEmpty((Collection)regionDataSet.getAttamentFiles())) {
                    attamentFile.addAll(regionDataSet.getAttamentFiles());
                }
                if (CollectionUtils.isEmpty((Collection)(defines = regionDataSet.getFieldDataList())) || !regionDataSet.hasNext()) continue;
                LinkedHashSet<String> heads = new LinkedHashSet<String>();
                heads.add("YEAR");
                heads.add("PERIOD");
                heads.add("ID");
                ArrayList<String> defineHeads = new ArrayList<String>();
                block19: for (ExportFieldDefine fieldDefine : defines) {
                    int lastDotIndex = fieldDefine.getCode().lastIndexOf(".");
                    String fieldName = lastDotIndex != -1 ? fieldDefine.getCode().substring(lastDotIndex + 1) : fieldDefine.getCode();
                    switch (fieldName) {
                        case "BIZKEYORDER": {
                            defineHeads.add("ID");
                            continue block19;
                        }
                        case "MDCODE": {
                            defineHeads.add("UNITCODE");
                            continue block19;
                        }
                    }
                    defineHeads.add(fieldName);
                }
                heads.addAll(defineHeads);
                heads.remove("DATATIME");
                StringBuffer headsResul = new StringBuffer();
                heads.stream().forEach(v -> headsResul.append((String)v).append(","));
                File cellFile = new File(fileName);
                cellFile.createNewFile();
                ArrayList dataList = new ArrayList();
                do {
                    ArrayList dataRows;
                    if (CollectionUtils.isEmpty((Collection)(dataRows = (ArrayList)regionDataSet.next()))) continue;
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("YEAR", year);
                    data.put("PERIOD", period);
                    for (int i = 0; i < defineHeads.size(); ++i) {
                        data.put((String)defineHeads.get(i), dataRows.get(i));
                    }
                    if (data.get("ID") == null) {
                        data.put("ID", UUIDUtils.newUUIDStr());
                    }
                    dataList.add(data);
                } while (regionDataSet.hasNext());
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
                Object object = null;
                try {
                    bw.write(headsResul.deleteCharAt(headsResul.length() - 1).toString());
                    for (Map map : dataList) {
                        StringBuffer formatResul = new StringBuffer();
                        for (String head : heads) {
                            formatResul.append((Object)(map.get(head) == null ? "" : map.get(head))).append(",");
                        }
                        bw.newLine();
                        String oneRowStr = formatResul.deleteCharAt(formatResul.length() - 1).toString();
                        bw.write(oneRowStr);
                    }
                }
                catch (Throwable throwable) {
                    object = throwable;
                    throw throwable;
                }
                finally {
                    if (bw == null) continue;
                    if (object != null) {
                        try {
                            bw.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                        continue;
                    }
                    bw.close();
                }
            }
        }
        ZipUtils.addFile((String)dataZipLocation, (String)filePath);
    }

    private static void exportAttachment(String dataZipLocation, String rootPath, List<FileInfo> attamentFile) throws Exception {
        String filePath = CommonReportUtil.createNewPath((String)rootPath, (String)"DATA_ZBDATA_ATTACHMENT");
        String fileStorePath = CommonReportUtil.createNewPath((String)filePath, (String)"attachments");
    }
}

