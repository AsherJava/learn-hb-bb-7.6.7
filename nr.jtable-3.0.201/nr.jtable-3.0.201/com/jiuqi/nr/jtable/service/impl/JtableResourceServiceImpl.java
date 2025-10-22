/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.grid.GridData
 *  com.jiuqi.np.grid2.Grid2Data
 *  com.jiuqi.np.grid2.GridCellData
 *  com.jiuqi.np.grid2.GridConverter
 *  com.jiuqi.np.grid2.json.Grid2DataSerialize
 *  com.jiuqi.np.grid2.json.GridCellDataSerialize
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil$FILE_TYPE_TO_PDF
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.definition.util.TitleAndKey
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.file.web.FileType
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.IFMDMUpdateResult
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nr.fmdm.exception.FMDMUpdateException
 *  com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.jtable.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.GridConverter;
import com.jiuqi.np.grid2.json.Grid2DataSerialize;
import com.jiuqi.np.grid2.json.GridCellDataSerialize;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.convert.pdf.utils.ConvertUtil;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.TitleAndKey;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.file.web.FileType;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportImportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.ReportExportDataSetImpl;
import com.jiuqi.nr.jtable.dataset.impl.ReportImportDataSetImpl;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.ErrorLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.LinkSimpleData;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.AnalysisFormulaInfo;
import com.jiuqi.nr.jtable.params.input.CardInputInit;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.PasteFormatDataInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.output.CardInputInfo;
import com.jiuqi.nr.jtable.params.output.CardRowData;
import com.jiuqi.nr.jtable.params.output.CellDataSet;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.FMDMCheckFailNodeInfoExtend;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodDataSet;
import com.jiuqi.nr.jtable.params.output.MultiPeriodRegionDataSet;
import com.jiuqi.nr.jtable.params.output.PasteFormatReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReportDataSet;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IAfterSaveAction;
import com.jiuqi.nr.jtable.service.IFormAccessService;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.service.impl.SnapshotDataQueryServiceImpl;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.LinkDataFactory;
import com.jiuqi.nr.jtable.util.PreviewThreadCount;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import com.jiuqi.nr.jtable.util.RegionGradeDataLoader;
import com.jiuqi.nr.jtable.util.SortingMethod;
import com.jiuqi.nr.jtable.util.UUIDUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class JtableResourceServiceImpl
implements IJtableResourceService {
    private static final Logger logger = LoggerFactory.getLogger(JtableResourceServiceImpl.class);
    private static final String DEFAULT_PARENT_CODE = "-";
    private static final String PARENT_CODE_SHOW = "";
    private static final String BBLX_VALUE1 = "7";
    private static final String BBLX_VALUE2 = "H";
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableContextService jtableContextService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IFormAccessService formAccessService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private ISecretLevelService secretLevelService;
    @Resource
    private IEntityMetaService metaService;
    @Autowired(required=false)
    private IAfterSaveAction afterSaveAction;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IFMDMDataService fMDMDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private SnapshotDataQueryServiceImpl snapshotDataQueryService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private PreviewThreadCount previewTheradCount;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private FileOperationService fileOperationService;
    private static Semaphore semaphore;
    private static final Object previewLock;

    @Override
    public ReportDataSet queryReportData(ReportDataQueryInfo reportDataQueryInfo) {
        JtableContext jtableContext = reportDataQueryInfo.getContext();
        ReportDataSet reportDataSet = new ReportDataSet();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        Map<String, RegionQueryInfo> regionQueryInfoMap = reportDataQueryInfo.getRegionQueryInfo();
        for (RegionData region : regions) {
            RegionQueryInfo regionDataQueryInfo = new RegionQueryInfo();
            if (regionQueryInfoMap != null && regionQueryInfoMap.containsKey(region.getKey())) {
                regionDataQueryInfo = regionQueryInfoMap.get(region.getKey());
            }
            regionDataQueryInfo.setContext(jtableContext);
            regionDataQueryInfo.setRegionKey(region.getKey());
            RegionDataFactory factory = new RegionDataFactory();
            reportDataSet.getQueryData().put(region.getKey(), factory.getRegionDataSet(region, regionDataQueryInfo));
        }
        return reportDataSet;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public SaveResult saveReportData(ReportDataCommitSet reportDataCommitSet) {
        SaveResult result = new SaveResult();
        JtableContext jtableContext = reportDataCommitSet.getContext();
        Map<String, RegionDataCommitSet> commitData = reportDataCommitSet.getCommitData();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        for (RegionData region : regions) {
            if (!commitData.containsKey(region.getKey())) continue;
            RegionDataCommitSet regionDataCommitSet = commitData.get(region.getKey());
            regionDataCommitSet.setContext(jtableContext);
            regionDataCommitSet.setRegionKey(region.getKey());
            FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
            if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
                result = this.saveFmdmData(reportDataCommitSet, region, reportDataCommitSet.isUnitAdd());
                if (result.getfMDMCheckFailNodeInfoExtend() != null && result.getfMDMCheckFailNodeInfoExtend().size() > 0 || StringUtils.isNotEmpty((String)result.getMessage()) && result.getMessage().equals("failed")) {
                    result.setMessage("failed");
                    throw new SaveDataException(result);
                }
                result.setMessage("HAVE_DATA");
                continue;
            }
            RegionDataFactory factory = new RegionDataFactory();
            SaveResult regionResult = factory.commitRegionData(region, regionDataCommitSet);
            if (null == regionResult) continue;
            result.getErrors().addAll(regionResult.getErrors());
            result.getRowKeyMap().putAll(regionResult.getRowKeyMap());
            result.getFloatOrderMap().putAll(regionResult.getFloatOrderMap());
            if (regionResult.getMessage() == null) continue;
            if (regionResult.getMessage().equals("NO_DATA") && result.getMessage() != null && result.getMessage().equals("NO_DATA")) {
                result.setMessage(regionResult.getMessage());
                continue;
            }
            if (regionResult.getMessage().equals("HAVE_DATA") && result.getMessage() != null && !result.getMessage().equals("failed")) {
                result.setMessage(regionResult.getMessage());
                continue;
            }
            if (result.getMessage() != null) continue;
            result.setMessage(regionResult.getMessage());
        }
        if (result.getErrors().isEmpty()) {
            result.setMessage("success");
            this.log(jtableContext, commitData);
            if (this.afterSaveAction != null) {
                this.afterSaveAction.afterDeleteSurvey(jtableContext, commitData);
            }
        } else {
            result.setMessage("failed");
            throw new SaveDataException(result);
        }
        return result;
    }

    @Override
    public SaveResult saveFmdmReportData(ReportDataCommitSet reportDataCommitSet) {
        SaveResult result = new SaveResult();
        JtableContext jtableContext = reportDataCommitSet.getContext();
        Map<String, RegionDataCommitSet> commitData = reportDataCommitSet.getCommitData();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        for (RegionData region : regions) {
            if (!commitData.containsKey(region.getKey())) continue;
            RegionDataCommitSet regionDataCommitSet = commitData.get(region.getKey());
            regionDataCommitSet.setContext(jtableContext);
            regionDataCommitSet.setRegionKey(region.getKey());
            FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
            if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
                result = this.saveFmdmData(reportDataCommitSet, region, reportDataCommitSet.isUnitAdd());
                if (result.getfMDMCheckFailNodeInfoExtend() != null && result.getfMDMCheckFailNodeInfoExtend().size() > 0) {
                    result.setMessage("failed");
                    throw new SaveDataException(result);
                }
                result.setMessage("HAVE_DATA");
                continue;
            }
            RegionDataFactory factory = new RegionDataFactory();
            SaveResult regionResult = factory.commitRegionData(region, regionDataCommitSet);
            if (null == regionResult) continue;
            result.getErrors().addAll(regionResult.getErrors());
            result.getRowKeyMap().putAll(regionResult.getRowKeyMap());
            result.getFloatOrderMap().putAll(regionResult.getFloatOrderMap());
            if (regionResult.getMessage() == null) continue;
            if (regionResult.getMessage().equals("NO_DATA") && result.getMessage() != null && result.getMessage().equals("NO_DATA")) {
                result.setMessage(regionResult.getMessage());
                continue;
            }
            if (regionResult.getMessage().equals("HAVE_DATA") && result.getMessage() != null && !result.getMessage().equals("failed")) {
                result.setMessage(regionResult.getMessage());
                continue;
            }
            if (result.getMessage() != null) continue;
            result.setMessage(regionResult.getMessage());
        }
        if (result.getErrors().isEmpty()) {
            result.setMessage("success");
            this.log(jtableContext, commitData);
            if (this.afterSaveAction != null) {
                this.afterSaveAction.afterDeleteSurvey(jtableContext, commitData);
            }
        } else {
            result.setMessage("failed");
            throw new SaveDataException(result);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public ReturnInfo clearReportData(ReportDataQueryInfo reportDataQueryInfo) {
        JtableContext jtableContext = reportDataQueryInfo.getContext();
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        if (regions == null || regions.size() == 0) {
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setMessage(formData.getCode() + "\uff1a\u62a5\u8868\u6ca1\u6709\u533a\u57df");
            return returnInfo;
        }
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setMessage(formData.getCode() + "\uff1a\u5c01\u9762\u4ee3\u7801\u4e0d\u80fd\u5220\u9664");
            return returnInfo;
        }
        try {
            for (RegionData region : regions) {
                RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
                if (reportDataQueryInfo.getRegionQueryInfo().containsKey(region.getKey())) {
                    regionQueryInfo = reportDataQueryInfo.getRegionQueryInfo().get(region.getKey());
                }
                regionQueryInfo.setContext(jtableContext);
                RegionDataFactory factory = new RegionDataFactory();
                factory.clearRegionDataSet(region, regionQueryInfo);
            }
        }
        catch (Exception e) {
            JTableException jtableException;
            ReturnInfo returnInfo = new ReturnInfo();
            String message = PARENT_CODE_SHOW;
            message = e instanceof JTableException ? ((jtableException = (JTableException)((Object)e)).getDatas().length > 0 ? formData.getCode() + "\uff1a" + jtableException.getDatas()[0] : formData.getCode() + "\uff1a" + e.getMessage()) : formData.getCode() + "\uff1a" + e.getMessage();
            if (message.length() > 50) {
                message = message.substring(0, 50) + "...";
            }
            returnInfo.setMessage(message);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage() + "message", e);
            return returnInfo;
        }
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public RegionDataSet queryRegionData(RegionQueryInfo regionQueryInfo) {
        int queryPage;
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        int defaultDecimal = this.jtableParamService.getDefaultDecimal(regionQueryInfo.getContext().getTaskKey());
        if (regionQueryInfo.getContext().getMeasureMap().size() != 0 && !regionQueryInfo.getContext().getMeasureMap().containsValue("YUAN") && defaultDecimal > -1) {
            regionQueryInfo.getContext().setDecimal(PARENT_CODE_SHOW + defaultDecimal);
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            RegionDataSet fmdmData = this.getFmdmData(region, regionQueryInfo);
            return fmdmData;
        }
        RegionDataFactory factory = new RegionDataFactory();
        RegionDataSet dataSet = new RegionDataSet();
        if (regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID") != null) {
            String snapshotId = regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID").getValue();
            regionQueryInfo.getContext().getDimensionSet().remove("DATASNAPSHOTID");
            RegionDataSet snapshotDataSet = this.snapshotDataQueryService.queryRegionDatas(regionQueryInfo);
            if (snapshotDataSet != null && snapshotDataSet.getData().size() != 0) {
                dataSet = snapshotDataSet;
            } else {
                dataSet.setTotalCount(0);
            }
        } else if (regionQueryInfo.getContext().getDimensionSet().get("VERSIONID") != null && !regionQueryInfo.getContext().getDimensionSet().get("VERSIONID").getValue().equals("00000000-0000-0000-0000-000000000000")) {
            this.queryDataVersions(regionQueryInfo, dataSet, region);
        } else {
            dataSet = factory.getRegionDataSet(region, regionQueryInfo);
        }
        if (dataSet.getData().size() > 0) {
            if (regionQueryInfo.getPagerInfo() != null) {
                int currentPage = regionQueryInfo.getPagerInfo().getOffset();
                dataSet.setCurrentPage(currentPage);
            }
        } else if (regionQueryInfo.getPagerInfo() != null && (queryPage = regionQueryInfo.getPagerInfo().getOffset()) != 0) {
            PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
            pagerInfo.setOffset(--queryPage);
            dataSet = factory.getRegionDataSet(region, regionQueryInfo);
            dataSet.setCurrentPage(queryPage);
        }
        return dataSet;
    }

    @Override
    public List<String> queryRegionIds(RegionQueryInfo regionQueryInfo) {
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        ArrayList<String> idList = new ArrayList<String>();
        if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            RegionDataSet queryRegionData = this.queryRegionData(regionQueryInfo);
            List<String> cells = queryRegionData.getCells().get(regionQueryInfo.getRegionKey());
            List<List<Object>> rowdatas = queryRegionData.getData();
            int idIndex = cells.indexOf("ID");
            for (int i = 0; i < rowdatas.size(); ++i) {
                List<Object> rowData = rowdatas.get(i);
                idList.add(rowData.get(idIndex).toString());
            }
            return idList;
        }
        return idList;
    }

    private void queryDataVersions(RegionQueryInfo regionQueryInfo, RegionDataSet dataSet, RegionData region) {
        DimensionValue versionDv;
        Map<String, DimensionValue> dimensionSet = regionQueryInfo.getContext().getDimensionSet();
        if (null != dimensionSet.get("VERSIONID") && !(versionDv = dimensionSet.get("VERSIONID")).getValue().equals("00000000-0000-0000-0000-000000000000")) {
            List fileList = this.fileInfoService.getFileInfoByGroup(regionQueryInfo.getContext().getFormKey(), "DataVer", FileStatus.AVAILABLE);
            ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
            if (null == fileList) {
                return;
            }
            boolean secretLevelEnable = this.secretLevelService.secretLevelEnable(regionQueryInfo.getContext().getTaskKey());
            SecretLevelInfo secretLevel = this.secretLevelService.getSecretLevel(regionQueryInfo.getContext());
            for (FileInfo item : fileList) {
                boolean canAccess = true;
                if (StringUtils.isNotEmpty((String)item.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                    SecretLevelItem secretLevelItem = this.secretLevelService.getSecretLevelItem(item.getSecretlevel());
                    boolean bl = canAccess = this.secretLevelService.canAccess(secretLevelItem) && this.secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem);
                }
                if (!canAccess || !item.getName().equals(versionDv.getValue())) continue;
                tableFile.add(item);
            }
            List fieldKeys = this.runtimeView.getFieldKeysInForm(regionQueryInfo.getContext().getFormKey());
            HashSet fieldSet = new HashSet();
            fieldSet.addAll(fieldKeys);
            List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runtimeView.getFieldKeysInRegion(regionQueryInfo.getRegionKey()));
            List<LinkData> allLinks = this.jtableParamService.getLinks(regionQueryInfo.getRegionKey());
            HashMap entityDatas = new HashMap();
            for (LinkData link : allLinks) {
                if (!(link instanceof EnumLinkData)) continue;
                EnumLinkData enumLink = (EnumLinkData)link;
                DataFormaterCache dataFormaterCache = new DataFormaterCache(regionQueryInfo.getContext());
                EntityReturnInfo fillEntityData = enumLink.getFillEntityData(null, null, dataFormaterCache);
                List<EntityData> entitys = fillEntityData.getEntitys();
                this.addEntitys(fillEntityData, entitys);
                dataSet.getEntityDataMap().put(link.getKey(), fillEntityData);
                ArrayList<EntityData> list = new ArrayList<EntityData>();
                list.addAll(fillEntityData.getEntitys());
                fillEntityData.getEntitys().clear();
                entityDatas.put(link.getKey(), list);
            }
            for (FileInfo fileInfo : tableFile) {
                byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                String result = null;
                try {
                    out.write(bs);
                    result = new String(out.toByteArray());
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                List formList = new ArrayList();
                try {
                    formList = (List)objectMapper.readValue(result, Object.class);
                }
                catch (JsonProcessingException e2) {
                    e2.printStackTrace();
                }
                HashSet<String> tableKeys = new HashSet<String>();
                HashMap<String, FieldDefine> fieldDefineInRegion = new HashMap<String, FieldDefine>();
                for (FieldDefine fieldDefine : listFieldDefine) {
                    tableKeys.add(fieldDefine.getOwnerTableKey());
                    fieldDefineInRegion.put(fieldDefine.getKey(), fieldDefine);
                }
                HashMap rows = new HashMap();
                for (Object object : formList) {
                    Map o = (Map)object;
                    block15: for (String tableKey : tableKeys) {
                        if (null == o.get(tableKey)) continue;
                        rows.put(tableKey, o.get(tableKey));
                        List rowList = (List)o.get(tableKey);
                        dataSet.setTotalCount(rowList.size());
                        PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
                        if (null == pagerInfo) {
                            pagerInfo = new PagerInfo();
                            if (region.getPageSize() > 0) {
                                pagerInfo.setLimit(region.getPageSize());
                                pagerInfo.setOffset(0);
                            } else {
                                pagerInfo.setLimit(10);
                                pagerInfo.setOffset(0);
                            }
                        } else if (0 == pagerInfo.getLimit() && region.getPageSize() > 0) {
                            pagerInfo.setLimit(region.getPageSize());
                        } else if (0 == pagerInfo.getLimit() && region.getPageSize() <= 0) {
                            pagerInfo.setLimit(10);
                        }
                        Map<String, List<String>> cells = dataSet.getCells();
                        List<String> fieldKeylist = cells.get(regionQueryInfo.getRegionKey());
                        int startIndex = pagerInfo.getLimit() * pagerInfo.getOffset();
                        int index = pagerInfo.getLimit() * pagerInfo.getOffset();
                        int rowIndex = 0;
                        int maxRowIndex = index + pagerInfo.getLimit();
                        RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
                        if (restructureInfo.getGrade() != null && (!restructureInfo.getGrade().getGradeCells().isEmpty() || restructureInfo.getGrade().isSum())) {
                            List<List<Object>> rel = dataSet.getRel();
                            if (null == rel) {
                                rel = new ArrayList<List<Object>>();
                            }
                            rel.clear();
                            for (int i = index; i < maxRowIndex; ++i) {
                                ArrayList<Integer> element = new ArrayList<Integer>();
                                element.add(i);
                                rel.add(element);
                            }
                        }
                        List deployInfos = null;
                        try {
                            deployInfos = this.dataSchemeService.getDeployInfoByDataTableKey(tableKey);
                        }
                        catch (Exception e1) {
                            logger.info(e1.getMessage(), e1);
                        }
                        DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)deployInfos.get(0);
                        String tableName = PARENT_CODE_SHOW;
                        if (null != dataFieldDeployInfo) {
                            tableName = dataFieldDeployInfo.getTableName();
                        }
                        int floatOrder = 0;
                        for (Map rowMap : rowList) {
                            ++floatOrder;
                            Map<String, DimensionValue> dimensionSet2 = regionQueryInfo.getContext().getDimensionSet();
                            boolean isCurrentRow = true;
                            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
                            IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
                            for (Map.Entry<String, DimensionValue> entry : dimensionSet2.entrySet()) {
                                FieldDefine dimensionField = dataAssist.getDimensionField(tableName, entry.getKey());
                                if (entry.getKey().equals("VERSIONID") || entry.getValue() == null || entry.getValue().getValue() == null || dimensionField == null || rowMap.get(dimensionField.getCode()) == null || entry.getValue().getValue().equals(rowMap.get(dimensionField.getCode()))) continue;
                                isCurrentRow = false;
                            }
                            if (!isCurrentRow) {
                                dataSet.setTotalCount(dataSet.getTotalCount() - 1);
                                continue;
                            }
                            if (index > rowIndex) {
                                ++rowIndex;
                                continue;
                            }
                            if (maxRowIndex == index) continue block15;
                            ArrayList<Object> datas = new ArrayList<Object>();
                            List allLinksInRegion = this.runtimeView.getAllLinksInRegion(regionQueryInfo.getRegionKey());
                            HashMap<String, DataLinkDefine> datalinkMap = new HashMap<String, DataLinkDefine>();
                            for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
                                datalinkMap.put(dataLinkDefine.getKey(), dataLinkDefine);
                            }
                            for (String key : fieldKeylist) {
                                DataLinkDefine dataLinkDefine;
                                boolean find = false;
                                if (key.equals("ID")) {
                                    datas.add(rowMap.get("BIZKEYORDER"));
                                } else if (key.equals("FLOATORDER")) {
                                    if (null != rowMap.get(key)) {
                                        datas.add(rowMap.get(key));
                                    } else {
                                        datas.add(floatOrder);
                                    }
                                } else if (key.equals("SUM")) {
                                    datas.add(PARENT_CODE_SHOW);
                                }
                                if ((dataLinkDefine = (DataLinkDefine)datalinkMap.get(key)) == null) continue;
                                FieldDefine def = (FieldDefine)fieldDefineInRegion.get(dataLinkDefine.getLinkExpression());
                                if (def == null) {
                                    if (dataLinkDefine.getLinkExpression() == null || dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FORMULA) continue;
                                    datas.add(null);
                                    continue;
                                }
                                Object fieldValue = rowMap.get(def.getCode());
                                if (dataLinkDefine.getKey().equals(key)) {
                                    String format;
                                    SimpleDateFormat sdf;
                                    if (null == rowMap.get(def.getCode())) {
                                        datas.add(null);
                                    } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        try {
                                            format = sdf.format(new Date(Long.valueOf(String.valueOf(fieldValue))));
                                        }
                                        catch (NumberFormatException e) {
                                            format = String.valueOf(fieldValue);
                                        }
                                        datas.add(format);
                                    } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        try {
                                            format = sdf.format(new Date(Long.valueOf(String.valueOf(fieldValue))));
                                        }
                                        catch (NumberFormatException e) {
                                            format = String.valueOf(fieldValue);
                                        }
                                        datas.add(format);
                                    } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC)) {
                                        String data = String.valueOf(fieldValue);
                                        if ("\u662f".equals(data)) {
                                            datas.add(true);
                                        } else if ("\u5426".equals(data)) {
                                            datas.add(false);
                                        } else {
                                            datas.add(PARENT_CODE_SHOW);
                                        }
                                    } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_FILE) || def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
                                        if (null != fieldValue) {
                                            String groupKey = String.valueOf(fieldValue);
                                            List fileInfos = this.fileInfoService.getFileInfoByGroup(groupKey, "JTABLEAREA", FileStatus.AVAILABLE);
                                            ArrayList<FileInfoImpl> files = new ArrayList<FileInfoImpl>();
                                            for (FileInfo fileInfo2 : fileInfos) {
                                                FileInfoImpl file = (FileInfoImpl)fileInfo2;
                                                String path = this.fileService.area("JTABLEAREA").getPath(file.getKey(), NpContextHolder.getContext().getTenant());
                                                file.setPath(path);
                                                files.add(file);
                                            }
                                            dataSet.getFileDataMap().put(groupKey, files);
                                        }
                                        datas.add(fieldValue);
                                    } else if (null != def.getEntityKey()) {
                                        if (null != fieldValue && null != entityDatas.get(dataLinkDefine.getKey())) {
                                            List list;
                                            if (null != dataSet.getEntityDataMap().get(def.getEntityKey())) {
                                                list = (List)entityDatas.get(dataLinkDefine.getKey());
                                                for (EntityData entityData : list) {
                                                    if (!entityData.getCode().equals(fieldValue)) continue;
                                                    dataSet.getEntityDataMap().get(dataLinkDefine.getKey()).getEntitys().add(entityData);
                                                    break;
                                                }
                                            } else {
                                                dataSet.getEntityDataMap().put(def.getEntityKey(), dataSet.getEntityDataMap().get(dataLinkDefine.getKey()));
                                                list = (List)entityDatas.get(dataLinkDefine.getKey());
                                                for (EntityData entityData : list) {
                                                    if (!entityData.getCode().equals(fieldValue)) continue;
                                                    dataSet.getEntityDataMap().get(dataLinkDefine.getKey()).getEntitys().add(entityData);
                                                    break;
                                                }
                                            }
                                        }
                                        datas.add(fieldValue);
                                    } else {
                                        datas.add(fieldValue);
                                    }
                                    find = true;
                                }
                                if (find) continue;
                                if (key.equals("ID")) {
                                    datas.add(rowMap.get("BIZKEYORDER"));
                                    continue;
                                }
                                if (key.equals("SUM")) {
                                    datas.add(PARENT_CODE_SHOW);
                                    continue;
                                }
                                datas.add(rowMap.get(key));
                            }
                            if (index == rowIndex) {
                                if (!dataSet.getData().isEmpty() && dataSet.getData().size() > rowIndex - startIndex) {
                                    List<Object> list = dataSet.getData().get(index - startIndex);
                                    for (int i = 0; i < datas.size() && i < list.size(); ++i) {
                                        if (datas.get(i) != null) continue;
                                        datas.set(i, list.get(i));
                                    }
                                    dataSet.getData().remove(index - startIndex);
                                }
                                dataSet.getData().add(index - startIndex, datas);
                                ++index;
                            }
                            ++rowIndex;
                        }
                    }
                }
            }
        }
    }

    private void addEntitys(EntityReturnInfo fillEntityData, List<EntityData> entitys) {
        ArrayList<EntityData> entitysCp = new ArrayList<EntityData>();
        entitysCp.addAll(entitys);
        for (EntityData entityData : entitysCp) {
            fillEntityData.getEntitys().addAll(entityData.getChildren());
            if (entityData.getChildrenCount() <= 0) continue;
            this.addEntitys(fillEntityData, entityData.getChildren());
            entityData.getChildren().clear();
        }
    }

    @Override
    public RegionSingleDataSet queryRegionSingleData(RegionQueryInfo regionQueryInfo) {
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        RegionDataFactory factory = new RegionDataFactory();
        return factory.getRegionSingleDataSet(region, regionQueryInfo);
    }

    @Override
    public RegionDataCount regionDataCount(RegionQueryInfo regionQueryInfo) {
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        RegionDataFactory factory = new RegionDataFactory();
        return factory.regionDataCount(region, regionQueryInfo);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public SaveResult saveRegionData(RegionDataCommitSet regionDataCommitSet) {
        RegionDataFactory factory = new RegionDataFactory();
        RegionData region = this.jtableParamService.getRegion(regionDataCommitSet.getRegionKey());
        SaveResult result = factory.commitRegionData(region, regionDataCommitSet);
        if (result.getErrors().isEmpty()) {
            result.setMessage("success");
        } else {
            result.setMessage("failed");
        }
        return result;
    }

    @Override
    public CellDataSet getCellValues(CellValueQueryInfo cellValueQueryInfo) {
        List<CellQueryInfo> cellQueryInfos;
        JtableContext jtableContext = cellValueQueryInfo.getContext();
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
        dataFormaterCache.jsonData();
        CellDataSet cellDataSet = new CellDataSet();
        LinkData link = this.jtableParamService.getLink(cellValueQueryInfo.getCellKey());
        RegionData region = this.jtableParamService.getRegion(link.getRegionKey());
        cellDataSet.setCellKey(cellValueQueryInfo.getCellKey());
        IGroupingQuery groupingQuery = this.jtableDataEngineService.getGroupingQuery(jtableContext, link.getRegionKey());
        if (link instanceof ErrorLinkData) {
            ErrorLinkData errorLink = (ErrorLinkData)link;
            cellDataSet.setMessage(errorLink.getError());
            return cellDataSet;
        }
        if (link instanceof FormulaLinkData) {
            FormulaLinkData formulaLink = (FormulaLinkData)link;
            cellDataSet.setMessage(formulaLink.getFormula());
            return cellDataSet;
        }
        FieldData field = this.jtableParamService.getField(link.getZbid());
        int columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)groupingQuery, field.getFieldKey());
        groupingQuery.addGroupColumn(columnIndex);
        StringBuffer filterBuf = new StringBuffer();
        if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFilter())) {
            filterBuf.append(" (" + cellValueQueryInfo.getFilter() + ") ");
        }
        if (StringUtils.isNotEmpty((String)region.getFilterCondition())) {
            if (filterBuf.length() != 0) {
                filterBuf.append(" AND ");
            }
            filterBuf.append(" (" + region.getFilterCondition() + ") ");
        }
        if ((cellQueryInfos = cellValueQueryInfo.getCells()) != null && cellQueryInfos.size() > 0) {
            SortingMethod sortingMe = new SortingMethod();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                String dataLinkKey = cellQueryInfo.getCellKey();
                LinkData queryLink = this.jtableParamService.getLink(dataLinkKey);
                FieldData queryField = this.jtableParamService.getField(queryLink.getZbid());
                int queryColumnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)groupingQuery, queryField.getFieldKey());
                groupingQuery.setGatherType(queryColumnIndex, FieldGatherType.FIELD_GATHER_MIN);
                queryField.setDataLinkKey(dataLinkKey);
                StringBuffer cellFilterBuf = sortingMe.sortingMethod(cellQueryInfo, queryField, (ICommonQuery)groupingQuery, queryColumnIndex, jtableContext);
                if (filterBuf.length() == 0) {
                    filterBuf.append(cellFilterBuf);
                    continue;
                }
                if (filterBuf.length() == 0 || cellFilterBuf.length() == 0) continue;
                filterBuf.append(" AND " + cellFilterBuf);
            }
        }
        if (filterBuf.length() > 0) {
            groupingQuery.setRowFilter(filterBuf.toString());
        }
        boolean isEnumLink = link.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        groupingQuery.setMasterKeys(dimensionValueSet);
        JtableContext jContext = new JtableContext(jtableContext);
        ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(jContext);
        IGroupingTable readonlyTable = null;
        try {
            readonlyTable = groupingQuery.executeReader(executorContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u5355\u5143\u683c\u679a\u4e3e\u67e5\u8be2\u51fa\u9519"});
        }
        int maxNumber = 201;
        LinkedHashSet<Object> valueSet = new LinkedHashSet<Object>();
        for (int i = 0; i < readonlyTable.getCount() && (valueSet.size() < maxNumber || isEnumLink); ++i) {
            IDataRow dataRow = readonlyTable.getItem(i);
            AbstractData fieldValue = null;
            try {
                fieldValue = dataRow.getValue(columnIndex);
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
            }
            Object value = link.getFormatData(fieldValue, dataFormaterCache, jtableContext);
            if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFuzzyValue()) && !link.checkFuzzyValue(fieldValue, value, dataFormaterCache, cellValueQueryInfo.getFuzzyValue())) continue;
            valueSet.add(value);
        }
        cellDataSet.setMessage("success");
        cellDataSet.setCount(valueSet.size());
        ArrayList<Object> values = new ArrayList<Object>(valueSet);
        if (isEnumLink) {
            List<Object> enumValues = this.setPageEnumValue(values, cellValueQueryInfo.getPagerInfo());
            cellDataSet.setCurrentPage(cellValueQueryInfo.getPagerInfo() == null ? 0 : cellValueQueryInfo.getPagerInfo().getOffset());
            cellDataSet.setData(enumValues);
        } else {
            cellDataSet.setData(values);
        }
        return cellDataSet;
    }

    private List<Object> setPageEnumValue(List<Object> values, PagerInfo pagerInfo) {
        ArrayList<Object> valueList = new ArrayList();
        if (values == null || values.size() == 0 || pagerInfo == null) {
            return values;
        }
        int total = values.size();
        int currentPage = pagerInfo.getOffset();
        int pageSize = pagerInfo.getLimit();
        if (total < pageSize) {
            return values;
        }
        int start = currentPage * pageSize;
        int end = start + pageSize;
        if (start > total) {
            start = total;
            end = total;
        }
        if (end > total) {
            end = total;
        }
        valueList = values.subList(start, end);
        return valueList;
    }

    @Override
    public PasteFormatReturnInfo pasteFormatData(PasteFormatDataInfo pasteFormatDataInfo) {
        PasteFormatReturnInfo pasteFormatReturnInfo = new PasteFormatReturnInfo();
        DataFormaterCache dataFormaterCache = new DataFormaterCache(pasteFormatDataInfo.getContext());
        dataFormaterCache.setEntityDataMap(pasteFormatReturnInfo.getEntityDataMap());
        String entity_matchAll = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_ENTITY_MATCH_ALL");
        dataFormaterCache.setEntityMatchAll("1".equals(entity_matchAll));
        dataFormaterCache.setDesensitized(false);
        Map<String, List<String>> dataLinkMaps = pasteFormatDataInfo.getDataLinkMaps();
        for (String dataLinkStr : dataLinkMaps.keySet()) {
            LinkData link = this.jtableParamService.getLink(dataLinkStr);
            ArrayList<String> groupKeyList = new ArrayList<String>();
            if (link.getType() == FieldType.FIELD_TYPE_PICTURE.getValue() || link.getType() == FieldType.FIELD_TYPE_FILE.getValue()) {
                List<String> groupList = dataLinkMaps.get(dataLinkStr);
                for (String groupKey : groupList) {
                    groupKeyList.add(PARENT_CODE_SHOW);
                }
                pasteFormatReturnInfo.getDataLinkMaps().put(dataLinkStr, groupKeyList);
                continue;
            }
            RegionData region = this.jtableParamService.getRegion(link.getRegionKey());
            AbstractRegionRelationEvn regionRelationEvn = null;
            regionRelationEvn = region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new SimpleRegionRelationEvn(region, pasteFormatDataInfo.getContext()) : new FloatRegionRelationEvn(region, pasteFormatDataInfo.getContext());
            dataFormaterCache.setEntityCaptionFields(regionRelationEvn.getEntityCaptionFields());
            List<String> dataLinkValue = dataLinkMaps.get(dataLinkStr);
            ArrayList<String> dataLinkValueCode = new ArrayList<String>();
            for (String valueTitle : dataLinkValue) {
                Object value = link.getData(valueTitle, dataFormaterCache, null, false);
                dataLinkValueCode.add(value == null ? PARENT_CODE_SHOW : value.toString());
            }
            pasteFormatReturnInfo.getDataLinkMaps().put(dataLinkStr, dataLinkValueCode);
        }
        return pasteFormatReturnInfo;
    }

    @Override
    public PagerInfo floatDataLocate(RegionQueryInfo regionQueryInfo) {
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        RegionDataFactory factory = new RegionDataFactory();
        return factory.floatDataLocate(region, regionQueryInfo);
    }

    @Override
    public IReportExportDataSet getReportExportData(JtableContext jtableContext) {
        return new ReportExportDataSetImpl(jtableContext);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public IReportImportDataSet getReportImportData(JtableContext jtableContext) {
        if (!this.formAccessService.formAccess(jtableContext, true)) {
            return null;
        }
        return new ReportImportDataSetImpl(jtableContext);
    }

    @Override
    public ReturnInfo calculateForm(JtableContext jtableContext) {
        ReturnInfo returnInfo = new ReturnInfo();
        if (this.definitionAuthorityProvider.canReadFormulaScheme(jtableContext.getFormulaSchemeKey())) {
            ArrayList<String> formKeys = new ArrayList<String>();
            formKeys.add(jtableContext.getFormKey());
            returnInfo.setMessage(this.jtableDataEngineService.calculate(jtableContext, formKeys));
        } else {
            returnInfo.setMessage("\u6ca1\u6709\u8be5\u516c\u5f0f\u65b9\u6848\u7684\u6743\u9650");
        }
        return returnInfo;
    }

    @Override
    public ReturnInfo calculateFormBetween(JtableContext jtableContext) {
        ReturnInfo returnInfo = new ReturnInfo();
        this.jtableDataEngineService.calculateByCondition(jtableContext, jtableContext.getFormKey());
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public FormulaCheckReturnInfo checkForm(JtableContext jtableContext) {
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(jtableContext.getFormKey());
        return this.jtableDataEngineService.check(jtableContext, formKeys);
    }

    @Override
    public FormulaCheckReturnInfo checkFormBetween(JtableContext jtableContext) {
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(jtableContext.getFormKey());
        formKeys.add(UUIDUtil.emptyID.toString());
        return this.jtableDataEngineService.check(jtableContext, formKeys);
    }

    @Override
    public ReturnInfo dataSumForm(JtableContext jtableContext) {
        ReturnInfo returnInfo = new ReturnInfo();
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(jtableContext.getFormKey());
        returnInfo.setMessage(this.jtableDataEngineService.dataSum(jtableContext, formKeys));
        return returnInfo;
    }

    @Override
    public boolean isFormCondition(JtableContext jtableContext) {
        FormDefine formDefine = this.runtimeView.queryFormById(jtableContext.getFormKey());
        String condition = formDefine.getFormCondition();
        if (StringUtils.isEmpty((String)condition)) {
            return true;
        }
        boolean evaluat = true;
        AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(formDefine.getFormCondition(), jtableContext, this.jtableContextService.getDimensionValueSet(jtableContext));
        if (expressionEvaluat != null && expressionEvaluat instanceof BoolData) {
            try {
                evaluat = expressionEvaluat.getAsBool();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return evaluat;
    }

    @Override
    public Map<String, String> analysisFormula(AnalysisFormulaInfo analysisFormulaInfo) {
        JtableContext context = analysisFormulaInfo.getContext();
        List<String> formulas = analysisFormulaInfo.getFormulas();
        HashMap<String, String> resultMap = new HashMap<String, String>();
        String zoneDouble = "0.0";
        String zone = "0";
        if (null != formulas && formulas.size() > 0) {
            IDataQuery newDataQuery = this.dataAccessProvider.newDataQuery();
            for (String condition : formulas) {
                newDataQuery.addExpressionColumn(condition);
            }
            ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(context);
            try {
                newDataQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(context.getDimensionSet()));
                IDataTable executeQuery = newDataQuery.executeQuery(executorContext);
                if (executeQuery.getCount() < 1) {
                    return resultMap;
                }
                IDataRow item = executeQuery.getItem(0);
                for (int x = 0; x < formulas.size(); ++x) {
                    String res = null;
                    AbstractData expressionEvaluat = item.getValue(x);
                    boolean isNumber = false;
                    if (null != expressionEvaluat) {
                        int dataType = expressionEvaluat.dataType;
                        switch (dataType) {
                            case 1: {
                                res = expressionEvaluat.getAsBool() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 2: {
                                res = expressionEvaluat.getAsDateTime() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 3: {
                                isNumber = true;
                                res = expressionEvaluat.getAsFloat() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 4: {
                                isNumber = true;
                                res = expressionEvaluat.getAsInt() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 5: {
                                res = expressionEvaluat.getAsDate() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 6: {
                                res = expressionEvaluat.getAsString() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 8: {
                                res = expressionEvaluat.getAsDateTime() + PARENT_CODE_SHOW;
                                break;
                            }
                            case 10: {
                                isNumber = true;
                                if (expressionEvaluat.getAsCurrency() == null) {
                                    res = null;
                                    break;
                                }
                                res = expressionEvaluat.getAsCurrency().toPlainString() + PARENT_CODE_SHOW;
                                break;
                            }
                        }
                    }
                    if (analysisFormulaInfo.isZoneRetrun()) {
                        resultMap.put(formulas.get(x), res);
                        continue;
                    }
                    if (null == res || PARENT_CODE_SHOW.equals(res) || isNumber && (zoneDouble.equals(res) || zone.equals(res) || !isNumber)) continue;
                    resultMap.put(formulas.get(x), res);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return resultMap;
    }

    @Override
    public void previewFileByTaskKey(String taskKey, String fileKey, HttpServletResponse response) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        this.previewFile(taskDefine.getDataScheme(), fileKey, response);
    }

    @Override
    public void previewFileByDataSchemeCode(String dataSchemeCode, String fileKey, HttpServletResponse response) {
        DataScheme dataScheme = this.dataSchemeService.getDataSchemeByCode(dataSchemeCode);
        this.previewFile(dataScheme.getKey(), fileKey, response);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private void previewFile(String dataSchemeKey, String fileKey, HttpServletResponse response) {
        block36: {
            params = new CommonParamsDTO();
            params.setDataSchemeKey(dataSchemeKey);
            fileInfo = this.fileOperationService.getFileInfoByKey(fileKey, params);
            if (null == fileInfo) break block36;
            catcheObj = null;
            if (StringUtils.isNotEmpty((String)fileInfo.getMd5())) {
                catcheObj = this.cacheObjectResourceRemote.find((Object)fileInfo.getMd5());
            }
            info = this.fileService.tempArea().getInfo((String)catcheObj);
            if (catcheObj != null && info != null && (catcheBytes = this.fileService.tempArea().download((String)catcheObj)) != null) {
                try {
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-disposition", "attachment;filename=previewFile");
                    outputStream = response.getOutputStream();
                    outputStream.write(catcheBytes);
                    response.flushBuffer();
                    return;
                }
                catch (IOException e) {
                    JtableResourceServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            tip = this.iNvwaSystemOptionService.get("attachmentManagement", "QUEU_PREVIEW_TIP");
            waitingqueue = Integer.parseInt(this.iNvwaSystemOptionService.get("attachmentManagement", "MAX_QUEUING_PREVIEW"));
            extension = fileInfo.getExtension().toLowerCase();
            if (null == extension) break block36;
            downloadFile = this.fileOperationService.downloadFile(fileKey, params);
            inputStream = new ByteArrayInputStream(downloadFile);
            bytesPdf = null;
            previewCount = Integer.parseInt(this.iNvwaSystemOptionService.get("attachmentManagement", "MAX_PREVIEW_FILE"));
            var15_17 = JtableResourceServiceImpl.previewLock;
            synchronized (var15_17) {
                if (JtableResourceServiceImpl.semaphore == null) {
                    JtableResourceServiceImpl.logger.info("\u8bbe\u7f6e\u63a7\u5236\u9884\u89c8\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf");
                    JtableResourceServiceImpl.semaphore = new Semaphore(previewCount);
                    JtableResourceServiceImpl.logger.info("\u8bbe\u7f6e\u63a7\u5236\u5bfc\u51fa\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf\uff0c\u5355\u4e2a\u8282\u70b9\u9884\u89c8\u7684\u5e76\u53d1\u6570\u4e3a\uff1a" + previewCount);
                }
            }
            queueLength = JtableResourceServiceImpl.semaphore.getQueueLength();
            if (queueLength >= waitingqueue) {
                response.setStatus(226);
                response.setContentType("text/html;charset=UTF-8");
                dataByteArr = tip.getBytes(StandardCharsets.UTF_8);
                outputStream = null;
                try {
                    outputStream = response.getOutputStream();
                    outputStream.write(dataByteArr);
                    outputStream.flush();
                    outputStream.close();
                    response.flushBuffer();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            try {
                block44: {
                    block43: {
                        block42: {
                            block41: {
                                block40: {
                                    block39: {
                                        block38: {
                                            block37: {
                                                JtableResourceServiceImpl.semaphore.acquire();
                                                if (!extension.endsWith("ppt") && !extension.endsWith("pptx")) break block37;
                                                bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.PPTX);
                                                ** GOTO lbl-1000
                                            }
                                            if (!extension.endsWith("doc") && !extension.endsWith("wps")) break block38;
                                            bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOC);
                                            ** GOTO lbl-1000
                                        }
                                        if (!extension.endsWith("docx")) break block39;
                                        bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOCX);
                                        ** GOTO lbl-1000
                                    }
                                    if (!extension.endsWith("ofd")) break block40;
                                    bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.OFD);
                                    ** GOTO lbl-1000
                                }
                                if (!extension.endsWith("pdf")) break block41;
                                bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.PDF, (String)fileInfo.getName());
                                ** GOTO lbl-1000
                            }
                            if (!extension.endsWith("txt")) break block42;
                            bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.TXT, (String)fileInfo.getName());
                            ** GOTO lbl-1000
                        }
                        if (!extension.endsWith("xls") && !extension.endsWith("xlsx") && !extension.endsWith("et")) break block43;
                        try {
                            mapper = new ObjectMapper();
                            module = new SimpleModule();
                            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
                            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
                            mapper.registerModule((Module)module);
                            sheetNames = new ArrayList<String>();
                            gridDatas = new HashMap<String, String>();
                            workbook = WorkbookFactory.create(inputStream);
                            sheetCount = workbook.getNumberOfSheets();
                            allRowNum = 0;
                            for (i = 0; i < (sheetCount >= 10 ? 10 : sheetCount); ++i) {
                                sheet = workbook.getSheetAt(i);
                                rowNum = sheet.getLastRowNum();
                                allRowNum += rowNum;
                            }
                            for (i = 0; i < (sheetCount >= 10 ? 10 : sheetCount); ++i) {
                                sheet = workbook.getSheetAt(i);
                                rowNum = sheet.getLastRowNum();
                                tagNum = 0;
                                if (allRowNum > 10000) {
                                    tagNum = 10000 * rowNum / allRowNum;
                                }
                                if (allRowNum >= 10000 && tagNum > 0) {
                                    sheet.shiftRows(rowNum + 1, rowNum * 2 - tagNum, tagNum - rowNum);
                                }
                                if (sheet != null) {
                                    for (j = 0; j < sheet.getLastRowNum(); ++j) {
                                        row = sheet.getRow(j);
                                        if (row == null || (lastCellNum = row.getLastCellNum()) < 100) continue;
                                        row.shiftCellsLeft(lastCellNum + 1, lastCellNum * 2 - 99, lastCellNum - 99);
                                    }
                                }
                                sheetNames.add(sheet.getSheetName());
                                gridData = ConvertUtil.convertToGridData((Workbook)workbook, (Sheet)sheet);
                                grid2Data = new Grid2Data();
                                GridConverter.dataTodata2((GridData)gridData, (Grid2Data)grid2Data);
                                returnStr = mapper.writeValueAsString((Object)grid2Data);
                                gridDatas.put(sheet.getSheetName(), returnStr);
                            }
                            returnMaps = new HashMap<String, Cloneable>();
                            returnMaps.put("list", sheetNames);
                            returnMaps.put("grid2DataList", gridDatas);
                            bytesPdf = mapper.writeValueAsBytes(returnMaps);
                        }
                        catch (Exception e) {
                            JtableResourceServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        ** GOTO lbl-1000
                    }
                    if (!extension.endsWith("gif") && !extension.endsWith("jpg") && !extension.endsWith("jpeg") && !extension.endsWith("jpe") && !extension.endsWith("jif") && !extension.endsWith("pcx") && !extension.endsWith("dcx") && !extension.endsWith("pic") && !extension.endsWith("png") && !extension.endsWith("tga") && !extension.endsWith("tif") && !extension.endsWith("tiff") && !extension.endsWith("xif") && !extension.endsWith("wmf") && !extension.endsWith("jfif")) break block44;
                    try {
                        fileType = FileType.valueOfExtension((String)extension);
                        if ("".equals(fileType.getContentType())) {
                            response.setContentType("application/octet-stream");
                        } else {
                            response.setContentType(fileType.getContentType());
                        }
                        this.fileOperationService.downloadFile(fileKey, (OutputStream)response.getOutputStream(), params);
                        response.flushBuffer();
                        return;
                    }
                    catch (IOException e) {
                        JtableResourceServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        ** break block45
                    }
                }
                bytesPdf = downloadFile;
lbl-1000:
                // 10 sources

                {
                    if (bytesPdf != null && StringUtils.isNotEmpty((String)fileInfo.getMd5())) {
                        catcheKey = this.fileService.tempArea().upload(fileInfo.getName().replace(fileInfo.getExtension(), "") + ".pdf", bytesPdf).getKey();
                        this.cacheObjectResourceRemote.create((Object)fileInfo.getMd5(), (Object)catcheKey);
                    }
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Task interrupted", e);
            }
            catch (Exception e) {
                throw new RuntimeException();
            }
            finally {
                if (JtableResourceServiceImpl.semaphore != null) {
                    JtableResourceServiceImpl.semaphore.release();
                }
            }
            if (null != bytesPdf) {
                try {
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-disposition", "attachment;filename=previewFile");
                    outputStream = response.getOutputStream();
                    outputStream.write(bytesPdf);
                    response.flushBuffer();
                }
                catch (IOException e) {
                    JtableResourceServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    private static String getFilecharset(byte[] sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            ByteArrayInputStream bis = new ByteArrayInputStream(sourceFile);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            }
            if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    ++loc;
                    if (read >= 240 || 128 <= read && read <= 191) break;
                    if (192 <= read && read <= 223) {
                        read = bis.read();
                        if (128 > read || read > 191) break;
                        continue;
                    }
                    if (224 > read || read > 239) continue;
                    read = bis.read();
                    if (128 > read || read > 191 || 128 > (read = bis.read()) || read > 191) break;
                    charset = "UTF-8";
                    break;
                }
            }
            bis.close();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return charset;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public CardInputInfo cardInputInit(CardInputInit cardInputInit) {
        CardInputInfo cardInputInfo = new CardInputInfo();
        JtableContext context = cardInputInit.getContext();
        String regionKey = cardInputInit.getRegionKey();
        if (!cardInputInit.isOnlyRowData()) {
            RegionData region = this.jtableParamService.getRegion(regionKey);
            cardInputInfo.setCanDeleteRow(region.isCanDeleteRow());
            cardInputInfo.setCanInsertRow(region.isCanInsertRow());
            List<LinkData> links = this.jtableParamService.getLinks(region.getKey());
            RecordCardData recordCardData = region.getCardRecord();
            RecordCard cardRecord = recordCardData.getRecordCard();
            if (null == cardRecord) {
                links.sort((o1, o2) -> o1.getCol() - o2.getCol());
                cardRecord = new RecordCard();
                cardRecord.setColumu("1");
                ArrayList<TitleAndKey> linkTitleKey = new ArrayList<TitleAndKey>();
                for (LinkData linkData : links) {
                    TitleAndKey titleAndKey = new TitleAndKey();
                    titleAndKey.setTitle(linkData.getZbtitle());
                    titleAndKey.setId(linkData.getKey());
                    linkTitleKey.add(titleAndKey);
                }
                cardRecord.setLinkTitleKey(linkTitleKey);
            }
            cardInputInfo.setCardRecord(cardRecord);
            DimensionValueSet dimensionValueSet = this.jtableContextService.getDimensionValueSet(context);
            DataFormaterCache dataFormaterCache = new DataFormaterCache(context);
            HashSet<String> entityKeys = new HashSet<String>();
            HashSet notSuppots = new HashSet();
            for (LinkData linkData : links) {
                EnumLinkData enumLink;
                if (StringUtils.isNotEmpty((String)linkData.getZbid()) && StringUtils.isNotEmpty((String)linkData.getDefaultValue())) {
                    AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(linkData.getDefaultValue(), context, dimensionValueSet);
                    if (expressionEvaluat != null) {
                        Object fieldValue = linkData.getFormatData(expressionEvaluat, dataFormaterCache, cardInputInit.getContext());
                        linkData.setDefaultValue(fieldValue.toString());
                    } else {
                        linkData.setDefaultValue(linkData.getDefaultValue());
                    }
                }
                LinkSimpleData linkSimpleData = new LinkSimpleData(linkData);
                cardInputInfo.getLinks().add(linkSimpleData);
                if (!(linkData instanceof EnumLinkData) || (enumLink = (EnumLinkData)linkData).getDisplayMode() != EnumDisplayMode.DISPLAY_MODE_IN_CELL || entityKeys.contains(enumLink.getEntityKey())) continue;
                entityKeys.add(enumLink.getEntityKey());
            }
            List<String> calcDataLinks = this.jtableParamService.getCalcDataLinks(context);
            cardInputInfo.setCalcDataLinks(calcDataLinks);
            if (null != cardRecord) {
                ArrayList arrayList = cardRecord.getLinkTitleKey();
                Iterator iterators = arrayList.iterator();
                while (iterators.hasNext()) {
                    TitleAndKey titleAndKey = (TitleAndKey)iterators.next();
                    if (notSuppots.contains(titleAndKey.getId())) {
                        iterators.remove();
                        continue;
                    }
                    if (null == calcDataLinks || !calcDataLinks.contains(titleAndKey.getId())) continue;
                    iterators.remove();
                }
            }
            for (String entityKey : entityKeys) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(entityKey);
                entityQueryInfo.setAllChildren(false);
                entityQueryInfo.setContext(context);
                EntityReturnInfo queryEntityData = this.jtableEntityService.queryEntityData(entityQueryInfo);
                Map<String, EntityReturnInfo> entityDataMap = cardInputInfo.getEntityDataMap();
                entityDataMap.put(entityKey, queryEntityData);
            }
        }
        String rowId = cardInputInit.getRowId();
        CardRowData cardRowData = cardInputInfo.getCardRowData();
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(context);
        regionQueryInfo.setRegionKey(regionKey);
        RegionDataSet regionDataSet = this.queryRegionData(regionQueryInfo);
        cardRowData.setTotalCount(regionDataSet.getData().size());
        cardInputInfo.setFileDataMap(regionDataSet.getFileDataMap());
        cardInputInfo.setImgDataMap(regionDataSet.getImgDataMap());
        List<String> list = regionDataSet.getCells().get(regionKey);
        int idFieldIndex = list.indexOf("ID");
        cardRowData.setCells(list);
        if (idFieldIndex >= 0) {
            List<List<Object>> data = regionDataSet.getData();
            block4: for (int i = 0; i < data.size(); ++i) {
                void var14_22;
                List<Object> rowData = data.get(i);
                if (!rowId.equals(rowData.get(idFieldIndex))) continue;
                int n = i + cardInputInit.getOffset();
                if (n >= data.size() || n < 0) break;
                List<List<Object>> rel = regionDataSet.getRel();
                boolean detailRow = false;
                while (!detailRow) {
                    Object type;
                    List<Object> relType;
                    if (rel.size() > var14_22 && (relType = rel.get((int)var14_22)).size() > 0 && null != (type = relType.get(4)) && RegionGradeDataLoader.groupData == (Integer)type) {
                        if ((var14_22 += cardInputInit.getOffset()) < data.size() && var14_22 >= 0) continue;
                        break block4;
                    }
                    detailRow = true;
                }
                cardRowData.setRow((int)(var14_22 + true));
                cardRowData.setData(data.get((int)var14_22));
                cardRowData.setRowId(data.get((int)var14_22).get(idFieldIndex));
                if (var14_22 + true >= data.size()) break;
                List<Object> nextRowData = data.get((int)(var14_22 + true));
                int floatOrderFieldIndex = list.indexOf("FLOATORDER");
                if (floatOrderFieldIndex < 0) break;
                cardRowData.setOrder(nextRowData.get(floatOrderFieldIndex));
                break;
            }
        }
        if (!cardInputInit.isOnlyRowData()) {
            Map<String, EntityReturnInfo> entityMap = regionDataSet.getEntityDataMap();
            Map<String, EntityReturnInfo> entityDataMap = cardInputInfo.getEntityDataMap();
            for (Map.Entry entry : entityMap.entrySet()) {
                if (entityDataMap.containsKey(entry.getKey())) continue;
                entityDataMap.put((String)entry.getKey(), (EntityReturnInfo)entry.getValue());
            }
        }
        return cardInputInfo;
    }

    @Override
    public Map<String, String> analysisFormulaByGridScript(JtableContext jtableContext) {
        String defineEndDefPattern;
        Pattern pattern;
        Matcher matcher;
        String script;
        String reportType = this.jtableParamService.getReportType(jtableContext.getFormKey());
        if (!FormType.FORM_TYPE_SURVEY.name().equals(reportType) && null != (script = this.runtimeView.getFrontScriptFromForm(jtableContext.getFormKey())) && !PARENT_CODE_SHOW.equals(script) && (matcher = (pattern = Pattern.compile(defineEndDefPattern = "//\\s*define[\\s\\S]*//\\s*enddef")).matcher(script)).find()) {
            String defineEndDefStr = matcher.group();
            String formulaPattern = "VALUE\\(.*\\[.*\\].{0,1}\\)";
            Pattern fpattern = Pattern.compile(formulaPattern);
            Matcher fmatcher = fpattern.matcher(defineEndDefStr);
            if (fmatcher.find()) {
                AnalysisFormulaInfo analysisFormulaInfo = new AnalysisFormulaInfo();
                ArrayList<String> formulas = new ArrayList<String>();
                String formula = fmatcher.group().replace("VALUE(", PARENT_CODE_SHOW).replace(")", PARENT_CODE_SHOW).replace("\"", PARENT_CODE_SHOW).replace("'", PARENT_CODE_SHOW);
                formulas.add(formula);
                while (fmatcher.find()) {
                    formula = fmatcher.group().replace("VALUE(", PARENT_CODE_SHOW).replace(")", PARENT_CODE_SHOW).replace("\"", PARENT_CODE_SHOW).replace("'", PARENT_CODE_SHOW);
                    formulas.add(formula);
                }
                analysisFormulaInfo.setContext(jtableContext);
                analysisFormulaInfo.setFormulas(formulas);
                analysisFormulaInfo.setZoneRetrun(true);
                Map<String, String> analysisFormula = this.analysisFormula(analysisFormulaInfo);
                return analysisFormula;
            }
        }
        return null;
    }

    @Override
    public MultiPeriodDataSet queryMultiPeriodData(JtableContext jtableContext) {
        int formPeriodType;
        MultiPeriodDataSet multiPeriodDataSet = new MultiPeriodDataSet();
        Map<String, MultiPeriodRegionDataSet> resultMap = multiPeriodDataSet.getRegionMap();
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        Object periodCode = dimensionValueSet.getValue("DATATIME");
        if (periodCode == null || StringUtils.isEmpty((String)periodCode.toString())) {
            multiPeriodDataSet.setNoPrevPeriod(false);
            multiPeriodDataSet.setNoPrevYear(false);
            return multiPeriodDataSet;
        }
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode.toString());
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        int periodType = periodWrapper.getType();
        if (periodType != (formPeriodType = formSchemeDefine.getPeriodType().type())) {
            multiPeriodDataSet.setNoPrevPeriod(true);
            multiPeriodDataSet.setNoPrevYear(true);
            return multiPeriodDataSet;
        }
        String prevPeriod = this.getPrevPeriod(periodCode.toString());
        String prevYear = this.getPrevYear(periodCode.toString());
        multiPeriodDataSet.setNoPrevPeriod(StringUtils.isEmpty((String)prevPeriod));
        multiPeriodDataSet.setNoPrevPeriod(StringUtils.isEmpty((String)prevYear));
        if (StringUtils.isEmpty((String)prevPeriod) && StringUtils.isEmpty((String)prevYear)) {
            return multiPeriodDataSet;
        }
        List<RegionData> regionDatas = this.jtableParamService.getRegions(jtableContext.getFormKey());
        for (RegionData regionData : regionDatas) {
            if (regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) continue;
            List<LinkData> links = this.jtableParamService.getLinks(regionData.getKey());
            regionData.setDataLinks(links);
            MultiPeriodRegionDataSet regionDataSet = this.jtableDataEngineService.getMultiPeriodRegionDataSet(jtableContext, regionData, prevPeriod, prevYear);
            resultMap.put(regionData.getKey(), regionDataSet);
        }
        return multiPeriodDataSet;
    }

    private String getPrevYear(String periodCode) {
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode);
        boolean prevYearState = periodAdapter.priorYear(periodWrapper);
        if (!prevYearState) {
            return null;
        }
        String prevPeriod = periodWrapper.toString();
        return prevPeriod;
    }

    private String getPrevPeriod(String periodCode) {
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode);
        boolean prevState = periodAdapter.priorPeriod(periodWrapper);
        if (!prevState) {
            return null;
        }
        String prevPeriod = periodWrapper.toString();
        return prevPeriod;
    }

    @Override
    public SaveResult saveFmdmData(ReportDataCommitSet reportDataCommitSet, RegionData region, boolean unitAdd) {
        SaveResult result = new SaveResult();
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        JtableContext context = reportDataCommitSet.getContext();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
        DimensionValueSet dimension = DimensionValueSetUtil.getDimensionValueSet(context.getDimensionSet());
        fmdmDataDTO.setFormSchemeKey(context.getFormSchemeKey());
        fmdmDataDTO.setFormulaSchemeKey(context.getFormulaSchemeKey());
        boolean ignoreCheck = this.addParam(fmdmDataDTO, taskDefine, dimension, reportDataCommitSet, region, unitAdd);
        fmdmDataDTO.setIgnoreCheck(ignoreCheck);
        List<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfos = this.checkFMDMLinks(fmdmDataDTO, region, reportDataCommitSet.getContext(), unitAdd);
        if (fMDMCheckFailNodeInfos == null || fMDMCheckFailNodeInfos.size() == 0) {
            if (unitAdd) {
                DimensionValueSet addDimension = new DimensionValueSet();
                addDimension.setValue("DATATIME", dimension.getValue("DATATIME"));
                fmdmDataDTO.setDimensionValueSet(addDimension);
                result = this.add(fmdmDataDTO, region);
            } else {
                fmdmDataDTO.setDimensionValueSet(dimension);
                result = this.update(fmdmDataDTO, region, context);
            }
        } else {
            result.setfMDMCheckFailNodeInfoExtend(fMDMCheckFailNodeInfos);
            result.setMessage("failed");
        }
        return result;
    }

    private SaveResult add(FMDMDataDTO fmdmDataDTO, RegionData region) {
        SaveResult result = new SaveResult();
        ArrayList<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtends = new ArrayList<FMDMCheckFailNodeInfoExtend>();
        FMDMCheckFailNodeInfoExtend fMDMCheckFailNodeInfoExtend = null;
        try {
            IFMDMUpdateResult fMDMUpdateResult = this.fMDMDataService.add(fmdmDataDTO);
            List codeToMap = fMDMUpdateResult.getUpdateKeys();
            FMDMCheckResult fmdmCheckResult = fMDMUpdateResult.getFMDMCheckResult();
            List results = fmdmCheckResult.getResults();
            List linksInRegions = this.runtimeView.getAllLinksInRegion(region.getKey());
            HashMap<String, DataLinkDefine> linkDefineMap = new HashMap<String, DataLinkDefine>();
            for (DataLinkDefine dataLinkDefine : linksInRegions) {
                LinkData linkData = this.jtableParamService.getLink(dataLinkDefine.getKey());
                if (linkData == null || !StringUtils.isNotEmpty((String)linkData.getZbcode()) || linkData.getDataLinkType() != DataLinkType.DATA_LINK_TYPE_FMDM) continue;
                linkDefineMap.put(linkData.getZbcode(), dataLinkDefine);
            }
            for (FMDMCheckFailNodeInfo checkFailNodeInfo : results) {
                fMDMCheckFailNodeInfoExtend = new FMDMCheckFailNodeInfoExtend();
                fMDMCheckFailNodeInfoExtend.setNodes(checkFailNodeInfo.getNodes());
                fMDMCheckFailNodeInfoExtend.setRegionKey(region.getKey());
                String fieldCode = checkFailNodeInfo.getFieldCode();
                DataLinkDefine dataLinkDefine = (DataLinkDefine)linkDefineMap.get(fieldCode.toUpperCase());
                if (dataLinkDefine != null) {
                    fMDMCheckFailNodeInfoExtend.setDataLinkKey(dataLinkDefine.getKey());
                    fMDMCheckFailNodeInfoExtend.setRegionKey(dataLinkDefine.getRegionKey());
                }
                fMDMCheckFailNodeInfoExtends.add(fMDMCheckFailNodeInfoExtend);
            }
            String unitId = PARENT_CODE_SHOW;
            if (codeToMap.size() > 0) {
                unitId = (String)codeToMap.get(0);
            }
            result.setUnitCode(unitId);
            result.setfMDMCheckFailNodeInfoExtend(fMDMCheckFailNodeInfoExtends);
            if (fMDMCheckFailNodeInfoExtends.size() > 0) {
                result.setMessage("failed");
            }
        }
        catch (FMDMUpdateException e) {
            logger.error("\u65b0\u589e\u5c01\u9762\u4ee3\u7801\u62a5\u9519" + e.getMessage(), e);
            result.setMessage("failed");
            return result;
        }
        return result;
    }

    private SaveResult update(FMDMDataDTO fmdmDataDTO, RegionData region, JtableContext context) {
        SaveResult result;
        block8: {
            result = new SaveResult();
            ArrayList<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtends = new ArrayList<FMDMCheckFailNodeInfoExtend>();
            FMDMCheckFailNodeInfoExtend fMDMCheckFailNodeInfoExtend = null;
            try {
                IFMDMUpdateResult fMDMUpdateResult = this.fMDMDataService.update(fmdmDataDTO);
                List codeToMap = fMDMUpdateResult.getUpdateKeys();
                FMDMCheckResult fmdmCheckResult = fMDMUpdateResult.getFMDMCheckResult();
                List results = fmdmCheckResult.getResults();
                List linksInRegions = this.runtimeView.getAllLinksInRegion(region.getKey());
                HashMap<String, DataLinkDefine> linkDefineMap = new HashMap<String, DataLinkDefine>();
                for (DataLinkDefine dataLinkDefine : linksInRegions) {
                    LinkData linkData = this.jtableParamService.getLink(dataLinkDefine.getKey());
                    if (linkData == null || !StringUtils.isNotEmpty((String)linkData.getZbcode()) || linkData.getDataLinkType() != DataLinkType.DATA_LINK_TYPE_FMDM) continue;
                    linkDefineMap.put(linkData.getZbcode(), dataLinkDefine);
                }
                for (FMDMCheckFailNodeInfo checkFailNodeInfo : results) {
                    fMDMCheckFailNodeInfoExtend = new FMDMCheckFailNodeInfoExtend();
                    fMDMCheckFailNodeInfoExtend.setNodes(checkFailNodeInfo.getNodes());
                    fMDMCheckFailNodeInfoExtend.setRegionKey(region.getKey());
                    String fieldCode = checkFailNodeInfo.getFieldCode();
                    DataLinkDefine dataLinkDefine = (DataLinkDefine)linkDefineMap.get(fieldCode.toUpperCase());
                    if (dataLinkDefine != null) {
                        fMDMCheckFailNodeInfoExtend.setDataLinkKey(dataLinkDefine.getKey());
                        fMDMCheckFailNodeInfoExtend.setRegionKey(dataLinkDefine.getRegionKey());
                    }
                    fMDMCheckFailNodeInfoExtends.add(fMDMCheckFailNodeInfoExtend);
                }
                String unitId = PARENT_CODE_SHOW;
                if (codeToMap.size() > 0) {
                    unitId = (String)codeToMap.get(0);
                }
                result.setUnitCode(unitId);
                result.setfMDMCheckFailNodeInfoExtend(fMDMCheckFailNodeInfoExtends);
                if (fMDMCheckFailNodeInfoExtends.size() > 0) {
                    result.setMessage("failed");
                }
            }
            catch (FMDMUpdateException e) {
                logger.error("\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u62a5\u9519" + e.getMessage(), e);
                result.setMessage("failed");
                return result;
            }
            catch (StackOverflowError t) {
                TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
                FMDMAttributeDTO fmdmAttributeDTO = this.fMDMAttributeDTO(taskDefine.getDw(), context.getFormSchemeKey());
                IFMDMAttribute fmdmParentField = this.fmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
                List linksInRegions = this.runtimeView.getAllLinksInRegion(region.getKey());
                Map linkDefineMap = linksInRegions.stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, Function.identity(), (key1, key2) -> key2));
                DataLinkDefine dataLinkDefine = (DataLinkDefine)linkDefineMap.get(fmdmParentField.getCode().toUpperCase());
                CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
                checkNodeInfo.setType(-1);
                checkNodeInfo.setContent("\u6307\u6807\uff1a\u7236\u8282\u70b9\u4e0d\u5141\u8bb8\u9009\u62e9\u81ea\u5df1\u6216\u81ea\u5df1\u7684\u4e0b\u7ea7");
                ArrayList<CheckNodeInfo> checkNodeInfos = new ArrayList<CheckNodeInfo>();
                checkNodeInfos.add(checkNodeInfo);
                fMDMCheckFailNodeInfoExtend = new FMDMCheckFailNodeInfoExtend();
                fMDMCheckFailNodeInfoExtend.setNodes(checkNodeInfos);
                fMDMCheckFailNodeInfoExtend.setDataLinkKey(dataLinkDefine.getKey());
                fMDMCheckFailNodeInfoExtend.setRegionKey(dataLinkDefine.getRegionKey());
                fMDMCheckFailNodeInfoExtends.add(fMDMCheckFailNodeInfoExtend);
                result.setfMDMCheckFailNodeInfoExtend(fMDMCheckFailNodeInfoExtends);
                if (fMDMCheckFailNodeInfoExtends.size() <= 0) break block8;
                result.setMessage("failed");
            }
        }
        return result;
    }

    @Override
    public List<FMDMCheckFailNodeInfoExtend> checkFMDMLinks(FMDMDataDTO fmdmDataDTO, RegionData region, JtableContext context, boolean unitAdd) {
        ArrayList<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtends = new ArrayList<FMDMCheckFailNodeInfoExtend>();
        Map dataModify = fmdmDataDTO.getDataModify();
        Map entityModify = fmdmDataDTO.getEntityModify();
        if (!(dataModify != null && dataModify.size() != 0 || entityModify != null && entityModify.size() != 0)) {
            return fMDMCheckFailNodeInfoExtends;
        }
        String regionKey = region.getKey();
        SimpleRegionRelationEvn regionRelationEvn = new SimpleRegionRelationEvn(region, context);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
        DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
        FMDMAttributeDTO fmdmAttributeDTO = this.fMDMAttributeDTO(taskDefine.getDw(), context.getFormSchemeKey());
        IFMDMAttribute fmdmParentField = this.fmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
        Map<String, LinkData> dataLinkMap = regionRelationEvn.getDataLinkMap();
        for (String linkKey : dataLinkMap.keySet()) {
            LinkData linkData = dataLinkMap.get(linkKey);
            String zbcode = linkData.getZbcode();
            if (zbcode == null || zbcode.equals(PARENT_CODE_SHOW)) continue;
            boolean haveKey = false;
            haveKey = linkData.getDataLinkType() == DataLinkType.DATA_LINK_TYPE_FMDM ? entityModify.containsKey(zbcode) : dataModify.containsKey(zbcode);
            if (!haveKey) continue;
            int posX = linkData.getCol();
            int posY = linkData.getRow();
            if (0 == posX || 0 == posY) continue;
            Object value = null;
            value = linkData.getDataLinkType() == DataLinkType.DATA_LINK_TYPE_FMDM ? (Object)entityModify.get(zbcode) : (Object)dataModify.get(zbcode);
            String zbtitle = linkData.getZbtitle();
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            if (fmdmParentField != null && fmdmParentField.getCode().equals(zbcode) && linkData.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue() && (value.toString().equals(DEFAULT_PARENT_CODE) || value.toString().equals("null"))) continue;
            linkData.getData(value, dataFormaterCache, saveErrorDataInfo, false);
            if (null == saveErrorDataInfo.getDataError().getErrorCode()) continue;
            ResultErrorInfo dataError = saveErrorDataInfo.getDataError();
            CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
            checkNodeInfo.setType(-1);
            checkNodeInfo.setContent(dataError.getErrorInfo());
            ArrayList<CheckNodeInfo> checkNodeInfos = new ArrayList<CheckNodeInfo>();
            checkNodeInfos.add(checkNodeInfo);
            FMDMCheckFailNodeInfoExtend fMDMCheckFailNodeInfoExtend = new FMDMCheckFailNodeInfoExtend();
            fMDMCheckFailNodeInfoExtend.setFieldCode(zbcode);
            fMDMCheckFailNodeInfoExtend.setFieldTitle(zbtitle);
            fMDMCheckFailNodeInfoExtend.setNodes(checkNodeInfos);
            fMDMCheckFailNodeInfoExtend.setRegionKey(regionKey);
            if (linkData != null) {
                fMDMCheckFailNodeInfoExtend.setDataLinkKey(linkData.getKey());
                fMDMCheckFailNodeInfoExtend.setRegionKey(linkData.getRegionKey());
            }
            fMDMCheckFailNodeInfoExtends.add(fMDMCheckFailNodeInfoExtend);
        }
        return fMDMCheckFailNodeInfoExtends;
    }

    private boolean addParam(FMDMDataDTO fmdmDataDTO, TaskDefine taskDefine, DimensionValueSet dimension, ReportDataCommitSet reportDataCommitSet, RegionData region, boolean unitAdd) {
        boolean ignoreCheck = false;
        Map<String, RegionDataCommitSet> commitData = reportDataCommitSet.getCommitData();
        RegionDataCommitSet regionDataCommitSet = commitData.get(region.getKey().toString());
        List<List<List<Object>>> data = regionDataCommitSet.getData();
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> cellList = cells.get(region.getKey().toString());
        List<List<Object>> list = data.get(0);
        JtableContext context = reportDataCommitSet.getContext();
        boolean setParent = false;
        FMDMAttributeDTO fmdmAttributeDTO = this.fMDMAttributeDTO(taskDefine.getDw(), context.getFormSchemeKey());
        IFMDMAttribute fmdmParentField = null;
        try {
            fmdmParentField = this.fmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
        }
        catch (FMDMQueryException e1) {
            logger.error("\u5c01\u9762\u4ee3\u7801\u7236\u8282\u70b9\u67e5\u8be2\u62a5\u9519" + e1.getMessage(), e1);
            throw new FMDMQueryException((Throwable)e1);
        }
        IEntityAttribute bblxField = null;
        bblxField = this.metaService.getEntityModel(taskDefine.getDw()).getBblxField();
        block8: for (int i = 0; i < cellList.size(); ++i) {
            DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(cellList.get(i));
            if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) continue;
            boolean isEntityData = dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM;
            boolean isInfoData = dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_INFO;
            String linkExpression = dataLinkDefine.getLinkExpression();
            fmdmAttributeDTO.setZBKey(linkExpression);
            fmdmAttributeDTO.setAttributeCode(linkExpression);
            IFMDMAttribute fmdmAttribute = this.fmdmAttributeService.queryByZbKey(fmdmAttributeDTO);
            String zbcode = fmdmAttribute.getCode();
            List<Object> cellData = list.get(i);
            if (!(cellData instanceof List)) continue;
            List<Object> cellObject = cellData;
            Object object = cellObject.get(1);
            if (fmdmAttribute == null) continue;
            switch (fmdmAttribute.getColumnType()) {
                case BOOLEAN: {
                    if (object == null || PARENT_CODE_SHOW.equals(object)) {
                        if (isEntityData) {
                            fmdmDataDTO.setEntityValue(zbcode, (Object)false);
                            continue block8;
                        }
                        if (isInfoData) {
                            fmdmDataDTO.setInfoValue(zbcode, (Object)false);
                            continue block8;
                        }
                        fmdmDataDTO.setDataValue(zbcode, (Object)false);
                        continue block8;
                    }
                    if (isEntityData) {
                        fmdmDataDTO.setEntityValue(zbcode, object);
                        continue block8;
                    }
                    if (isInfoData) {
                        fmdmDataDTO.setInfoValue(zbcode, object);
                        continue block8;
                    }
                    fmdmDataDTO.setDataValue(zbcode, object);
                    continue block8;
                }
                case DATETIME: {
                    if (object != null && StringUtils.isNotEmpty((String)object.toString())) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = dateFormat.parse(object.toString());
                            if (isEntityData) {
                                fmdmDataDTO.setEntityValue(zbcode, (Object)date);
                                continue block8;
                            }
                            if (isInfoData) {
                                fmdmDataDTO.setInfoValue(zbcode, (Object)date);
                                continue block8;
                            }
                            fmdmDataDTO.setDataValue(zbcode, (Object)date);
                        }
                        catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        continue block8;
                    }
                    if (isEntityData) {
                        fmdmDataDTO.setEntityValue(zbcode, (Object)PARENT_CODE_SHOW);
                        continue block8;
                    }
                    if (isInfoData) {
                        fmdmDataDTO.setInfoValue(zbcode, (Object)PARENT_CODE_SHOW);
                        continue block8;
                    }
                    fmdmDataDTO.setDataValue(zbcode, (Object)PARENT_CODE_SHOW);
                    continue block8;
                }
                default: {
                    if (fmdmParentField != null && fmdmParentField.getCode().equals(zbcode)) {
                        if (object == null || StringUtils.isEmpty((String)object.toString()) || object.toString().equals("null")) {
                            object = DEFAULT_PARENT_CODE;
                        }
                        setParent = true;
                    }
                    if (isEntityData) {
                        fmdmDataDTO.setEntityValue(zbcode, object);
                    } else if (isInfoData) {
                        fmdmDataDTO.setInfoValue(zbcode, object);
                    } else {
                        fmdmDataDTO.setDataValue(zbcode, object);
                    }
                    if (bblxField == null || !bblxField.getCode().equals(zbcode) || !BBLX_VALUE1.equals(object) && !BBLX_VALUE2.equals(object)) continue block8;
                    ignoreCheck = true;
                }
            }
        }
        if (unitAdd && !setParent) {
            String dimensionName = this.jtableParamService.getEntity(taskDefine.getDw()).getDimensionName();
            Object dimensionValue = dimension.getValue(dimensionName);
            fmdmDataDTO.setEntityValue(fmdmParentField.getCode(), dimensionValue);
        }
        return ignoreCheck;
    }

    private Map<String, Object> addParam(TaskDefine taskDefine, DimensionValueSet dimension, ReportDataCommitSet reportDataCommitSet, RegionData region, boolean unitAdd) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        Map<String, RegionDataCommitSet> commitData = reportDataCommitSet.getCommitData();
        RegionDataCommitSet regionDataCommitSet = commitData.get(region.getKey().toString());
        List<List<List<Object>>> data = regionDataCommitSet.getData();
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> cellList = cells.get(region.getKey().toString());
        List<List<Object>> list = data.get(0);
        JtableContext context = reportDataCommitSet.getContext();
        for (int i = 0; i < cellList.size(); ++i) {
            DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(cellList.get(i));
            String zbcode = dataLinkDefine.getLinkExpression();
            List<Object> cellData = list.get(i);
            if (!(cellData instanceof List)) continue;
            List<Object> cellObject = cellData;
            Object object = cellObject.get(1);
            param.put(zbcode, object);
        }
        if (unitAdd) {
            IFMDMAttribute fmdmParentField = null;
            try {
                FMDMAttributeDTO fmdmAttributeDTO = this.fMDMAttributeDTO(taskDefine.getDw(), context.getFormSchemeKey());
                fmdmParentField = this.fmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
                if (fmdmParentField != null) {
                    String dimensionName = this.jtableParamService.getEntity(taskDefine.getDw()).getDimensionName();
                    Object dimensionValue = dimension.getValue(dimensionName);
                    param.put(fmdmParentField.getCode(), dimensionValue);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return param;
    }

    @Override
    public RegionDataSet getFmdmData(RegionData region, RegionQueryInfo regionQueryInfo) {
        return this.getFmdmData(region, regionQueryInfo, false);
    }

    @Override
    public RegionDataSet getFmdmData(RegionData region, RegionQueryInfo regionQueryInfo, boolean export) {
        JtableContext context = regionQueryInfo.getContext();
        RegionDataFactory factory = new RegionDataFactory();
        AbstractRegionRelationEvn regionRelation = factory.createRegionRelationEvn(region, context);
        DataFormaterCache dataFormaterCache = regionRelation.getDataFormaterCache();
        dataFormaterCache.setFileDataMap(new HashMap<String, List<FileInfo>>());
        dataFormaterCache.setImgDataMap(new HashMap<String, List<byte[]>>());
        dataFormaterCache.setJsonData(export);
        Map<String, LinkData> dataLinkMap = regionRelation.getDataLinkMap();
        Set<String> keySet = dataLinkMap.keySet();
        ArrayList<String> cells = new ArrayList<String>(keySet);
        List<Object> cellDatas = this.getCellDatas(context, cells, dataFormaterCache);
        RegionDataSet regionDataSet = new RegionDataSet();
        regionDataSet.getCells().put(region.getKey(), cells);
        regionDataSet.getData().clear();
        regionDataSet.getData().add(cellDatas);
        regionDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        regionDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionDataSet.setTotalCount(regionDataSet.getData().size());
        RegionAnnotationResult regionAnnotationResult = regionRelation.getRegionAnnotationResult(regionQueryInfo.getContext());
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        return regionDataSet;
    }

    private List<Object> getCellDatas(JtableContext context, List<String> cells, DataFormaterCache dataFormaterCache) {
        ArrayList<Object> cellData = new ArrayList<Object>();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
        LinkedHashMap<LinkData, AbstractData> dataMap = this.dataMap(context, cells);
        DimensionValueSet dimension = DimensionValueSetUtil.getDimensionValueSet(context.getDimensionSet());
        String dimensionName = this.jtableParamService.getEntity(taskDefine.getDw()).getDimensionName();
        Object dimensionValue = dimension.getValue(dimensionName);
        for (Map.Entry<LinkData, AbstractData> data : dataMap.entrySet()) {
            LinkData linkData = data.getKey();
            AbstractData value = data.getValue();
            boolean parentCode = false;
            if (linkData instanceof EnumLinkData) {
                EnumLinkData enumLinkData = (EnumLinkData)linkData;
                String entity = enumLinkData.getEntityKey();
                if (taskDefine.getDw().equals(entity)) {
                    parentCode = true;
                    StringData stringData = new StringData(dimensionValue.toString());
                    linkData.getFormatData((AbstractData)stringData, dataFormaterCache, context);
                }
            }
            Object formatData = linkData.getFormatData(value, dataFormaterCache, context);
            if (parentCode && (formatData == null || StringUtils.isEmpty((String)formatData.toString())) || formatData.toString().equals("null") || formatData.toString().equals(DEFAULT_PARENT_CODE)) {
                formatData = PARENT_CODE_SHOW;
            }
            cellData.add(formatData);
        }
        return cellData;
    }

    private boolean isParentLinkData(LinkData parentLinkData, LinkData linkData) {
        return null != parentLinkData && parentLinkData.getKey().equals(linkData.getKey());
    }

    private LinkData getMainDimParentLink(TaskDefine taskDefine, LinkedHashMap<LinkData, AbstractData> dataMap) {
        String mainDimEntityId = taskDefine.getDw();
        IEntityModel entityModel = this.getEntityModel(mainDimEntityId);
        IEntityAttribute parentField = entityModel.getParentField();
        for (Map.Entry<LinkData, AbstractData> data : dataMap.entrySet()) {
            LinkData linkData = data.getKey();
            if (!linkData.getZbcode().equalsIgnoreCase(parentField.getCode()) || linkData.getType() != LinkType.LINK_TYPE\uff3fENUM.getValue()) continue;
            return linkData;
        }
        return null;
    }

    private IEntityModel getEntityModel(String entityId) {
        IEntityModel entityModel = null;
        entityModel = this.metaService.getEntityModel(entityId);
        return entityModel;
    }

    private LinkedHashMap<LinkData, AbstractData> dataMap(JtableContext context, List<String> cells) {
        LinkedHashMap<LinkData, AbstractData> dataMap;
        block16: {
            dataMap = new LinkedHashMap<LinkData, AbstractData>();
            try {
                List queryDataLinkMapping = this.runtimeView.queryDataLinkMapping(context.getFormKey());
                FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
                TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
                FormDefine formDefine = this.runtimeView.queryFormById(context.getFormKey());
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context.getDimensionSet());
                fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
                fmdmDataDTO.setFormSchemeKey(context.getFormSchemeKey());
                List list = new ArrayList();
                try {
                    list = this.fMDMDataService.list(fmdmDataDTO);
                }
                catch (FMDMQueryException e) {
                    logger.error("\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u51fa\u9519" + e.getMessage(), e);
                }
                if (CollectionUtils.isEmpty(list)) break block16;
                IFMDMData ifmdmData = (IFMDMData)list.get(0);
                for (String cell : cells) {
                    FieldDefine fieldDefine;
                    DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(cell);
                    String linkExpression = dataLinkDefine.getLinkExpression();
                    if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
                        LinkData formulaLinkData = this.jtableParamService.getLink(dataLinkDefine.getKey());
                        AbstractData formulaData = this.jtableDataEngineService.expressionEvaluat(linkExpression, context, dimensionValueSet);
                        if (formulaData != null) {
                            AbstractData abstractData = AbstractData.valueOf((Object)formulaData.getAsObject(), (int)formulaData.dataType);
                            dataMap.put(formulaLinkData, abstractData);
                            continue;
                        }
                        dataMap.put(formulaLinkData, null);
                        continue;
                    }
                    FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                    fmdmAttributeDTO.setEntityId(taskDefine.getDw());
                    fmdmAttributeDTO.setFormSchemeKey(context.getFormSchemeKey());
                    fmdmAttributeDTO.setAttributeCode(linkExpression);
                    fmdmAttributeDTO.setZBKey(linkExpression);
                    IFMDMAttribute fmdmAttribute = this.fmdmAttributeService.queryByZbKey(fmdmAttributeDTO);
                    if (fmdmAttribute == null) continue;
                    com.jiuqi.nr.entity.engine.data.AbstractData data = null;
                    LinkData linkData = null;
                    if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                        data = ifmdmData.getDataValue(fmdmAttribute.getCode());
                        fieldDefine = null;
                        try {
                            fieldDefine = this.runtimeView.queryFieldDefine(dataLinkDefine.getLinkExpression());
                        }
                        catch (Exception e) {
                            logger.error("\u6307\u6807\u67e5\u8be2\u51fa\u9519\uff1a" + e.getMessage(), e);
                        }
                        linkData = LinkDataFactory.fieldDefine(dataLinkDefine, fieldDefine, (List<DataLinkMappingDefine>)queryDataLinkMapping, formDefine);
                    } else if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_INFO) {
                        data = ifmdmData.getInfoValue(fmdmAttribute.getCode());
                        fieldDefine = null;
                        try {
                            fieldDefine = this.runtimeView.queryFieldDefine(dataLinkDefine.getLinkExpression());
                        }
                        catch (Exception e) {
                            logger.error("\u6307\u6807\u67e5\u8be2\u51fa\u9519\uff1a" + e.getMessage(), e);
                        }
                        linkData = LinkDataFactory.fieldDefine(dataLinkDefine, fieldDefine, (List<DataLinkMappingDefine>)queryDataLinkMapping, formDefine);
                    } else {
                        data = ifmdmData.getEntityValue(fmdmAttribute.getCode());
                        linkData = LinkDataFactory.fieldDefine(dataLinkDefine, fmdmAttribute, (List<DataLinkMappingDefine>)queryDataLinkMapping, formDefine);
                    }
                    if (data.isNull && StringUtils.isNotEmpty((String)linkData.getDefaultValue())) {
                        AbstractData dev = this.jtableDataEngineService.expressionEvaluat(linkData.getDefaultValue(), context, dimensionValueSet);
                        AbstractData abstractData = AbstractData.valueOf((Object)dev.getAsObject(), (int)dev.dataType);
                        dataMap.put(linkData, abstractData);
                        continue;
                    }
                    AbstractData abstractData = AbstractData.valueOf((Object)data.getAsObject(), (int)data.dataType);
                    dataMap.put(linkData, abstractData);
                }
            }
            catch (Exception e) {
                logger.error("\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u51fa\u9519" + e.getMessage(), e);
            }
        }
        return dataMap;
    }

    private List<Object> data(JtableContext context, List<String> cells) {
        ArrayList<Object> reginData = new ArrayList<Object>();
        try {
            FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context.getDimensionSet());
            fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
            fmdmDataDTO.setFormSchemeKey(context.getFormSchemeKey());
            List list = new ArrayList();
            try {
                list = this.fMDMDataService.list(fmdmDataDTO);
            }
            catch (FMDMQueryException e) {
                logger.error("\u67e5\u8be2\u62a5\u9519" + e.getMessage(), e);
            }
            Map<String, IFMDMAttribute> fmdmMap = this.getFMDMAttribute(taskDefine.getDw(), context.getFormSchemeKey());
            if (!CollectionUtils.isEmpty(list)) {
                IFMDMData ifmdmData = (IFMDMData)list.get(0);
                block7: for (String cell : cells) {
                    DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(cell);
                    String zbcode = dataLinkDefine.getLinkExpression();
                    com.jiuqi.nr.entity.engine.data.AbstractData data = ifmdmData.getValue(zbcode);
                    IFMDMAttribute findAttr = fmdmMap.get(zbcode.toUpperCase());
                    if (findAttr == null) continue;
                    switch (findAttr.getColumnType()) {
                        case DATETIME: {
                            long asDateTime = data.getAsDateTime();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date(asDateTime);
                            reginData.add(dateFormat.format(date));
                            continue block7;
                        }
                    }
                    reginData.add(ifmdmData.getAsObject(zbcode));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return reginData;
    }

    @Override
    public SaveResult deleteFMDM(JtableContext context) {
        SaveResult result = new SaveResult();
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context.getDimensionSet());
        fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
        fmdmDataDTO.setFormSchemeKey(context.getFormSchemeKey());
        try {
            this.fMDMDataService.delete(fmdmDataDTO);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u62a5\u9519" + e.getMessage(), e);
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            saveErrorDataInfo.getDataError().setErrorInfo(e.getMessage());
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.SYSTEMERROR);
            result.getErrors().add(saveErrorDataInfo);
            result.setMessage("failed");
        }
        return result;
    }

    @Override
    public Map<String, IFMDMAttribute> getFMDMAttribute(String dw, String formschemeKey) {
        Map<String, IFMDMAttribute> fmdmMap = new HashMap<String, IFMDMAttribute>();
        try {
            FMDMAttributeDTO fmdmAttributeDTO = this.fMDMAttributeDTO(dw, formschemeKey);
            List fmdmAttributes = this.fmdmAttributeService.list(fmdmAttributeDTO);
            fmdmMap = fmdmAttributes.stream().collect(Collectors.toMap(IModelDefineItem::getCode, f -> f, (f1, f2) -> f2));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return fmdmMap;
    }

    private FMDMAttributeDTO fMDMAttributeDTO(String dw, String formschemeKey) {
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        try {
            fmdmAttributeDTO.setEntityId(dw);
            fmdmAttributeDTO.setFormSchemeKey(formschemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return fmdmAttributeDTO;
    }

    private void log(JtableContext jtableContext, Map<String, RegionDataCommitSet> commitData) {
        int num = 0;
        Collection<RegionDataCommitSet> values = commitData.values();
        for (RegionDataCommitSet regionDataCommitSet : values) {
            List<String> deletedata = regionDataCommitSet.getDeletedata();
            num += deletedata.size();
        }
        if (num > 0) {
            StringBuilder buildMessing = this.buildMessing(jtableContext, "\u6d6e\u52a8\u884c\u5220\u9664", num);
            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)"\u6d6e\u52a8\u884c\u5220\u9664", (String)buildMessing.toString());
        }
    }

    private StringBuilder buildMessing(JtableContext jtableContext, String title, int num) {
        String[] infos = title.split(";");
        title = infos[0];
        StringBuilder valueBuilder = new StringBuilder();
        if (null != jtableContext) {
            jtableContext.toString();
            String taskKey = jtableContext.getTaskKey();
            if (!StringUtils.isEmpty((String)taskKey)) {
                String[] splits = taskKey.split(";");
                String taskTitle = PARENT_CODE_SHOW;
                for (String oneTaskKey : splits) {
                    TaskDefine queryTaskDefine = null;
                    try {
                        queryTaskDefine = this.runtimeView.queryTaskDefine(oneTaskKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == queryTaskDefine) continue;
                    taskTitle = PARENT_CODE_SHOW.equals(taskTitle) ? queryTaskDefine.getTitle() : taskTitle + ";" + queryTaskDefine.getTitle();
                }
                taskKey = taskTitle;
            } else {
                taskKey = "\u6240\u6709\u4efb\u52a1";
            }
            valueBuilder.append("\u4efb\u52a1\u540d\u79f0:").append(taskKey).append(", ");
            String formSchemeKey = jtableContext.getFormSchemeKey();
            String period = PARENT_CODE_SHOW;
            if (!StringUtils.isEmpty((String)formSchemeKey)) {
                String[] splits = formSchemeKey.split(";");
                String formSchemeKeyTitle = PARENT_CODE_SHOW;
                for (String oneFormSchemeKey : splits) {
                    FormSchemeDefine formSchemeDefine = null;
                    try {
                        formSchemeDefine = this.runtimeView.getFormScheme(oneFormSchemeKey);
                        period = formSchemeDefine.getDateTime();
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == formSchemeDefine) continue;
                    formSchemeKeyTitle = PARENT_CODE_SHOW.equals(formSchemeKeyTitle) ? formSchemeDefine.getTitle() : formSchemeKeyTitle + ";" + formSchemeDefine.getTitle();
                }
                formSchemeKey = formSchemeKeyTitle;
            } else {
                formSchemeKey = "\u6240\u6709\u62a5\u8868\u65b9\u6848";
            }
            valueBuilder.append("\u62a5\u8868\u65b9\u6848\u540d\u79f0:").append(formSchemeKey).append(", ");
            String unilType = PARENT_CODE_SHOW;
            String dateType = PARENT_CODE_SHOW;
            HashMap<String, String> entityViewsMap = new HashMap<String, String>();
            if (null != jtableContext.getFormSchemeKey() && !PARENT_CODE_SHOW.equals(jtableContext.getFormSchemeKey())) {
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
                unilType = dwEntity.getDimensionName();
                EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
                dateType = dataTimeEntity.getDimensionName();
                for (EntityViewData entityViewData : this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey())) {
                    entityViewsMap.put(entityViewData.getDimensionName(), entityViewData.getKey());
                }
            }
            Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                String value;
                if (entry.getKey().equals(dateType)) {
                    TableModelDefine periodEntityTableModel = this.periodEntityAdapter.getPeriodEntityTableModel(period);
                    String periodTitle = periodEntityTableModel.getTitle();
                    valueBuilder.append("\u65f6\u671f:").append(entry.getKey()).append("|").append(periodTitle).append(", ");
                    continue;
                }
                if (entry.getKey().equals(unilType)) {
                    value = entry.getValue().getValue();
                    String unitTitle = "\u5355\u4f4d:";
                    if (StringUtils.isEmpty((String)value)) {
                        valueBuilder.append(unitTitle).append(entry.getKey()).append("|").append("\u6240\u6709\u5355\u4f4d").append(", ");
                        continue;
                    }
                    this.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, unitTitle);
                    continue;
                }
                value = entry.getValue().getValue();
                if (StringUtils.isEmpty((String)value)) {
                    valueBuilder.append("\u7ef4\u5ea6\u540d:").append(entry.getKey()).append("|").append("\u6240\u6709\u503c").append(",");
                    continue;
                }
                this.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, "\u7ef4\u5ea6");
            }
            StringBuilder rang = new StringBuilder("\u62a5\u8868:");
            valueBuilder.append((CharSequence)rang);
            String formKey = jtableContext.getFormKey();
            if (!StringUtils.isEmpty((String)formKey) && !formKey.equals("all")) {
                String[] splits = formKey.split(";");
                boolean addFlag = false;
                for (String oneFormKey : splits) {
                    FormDefine formDefine = null;
                    try {
                        formDefine = this.runtimeView.queryFormById(oneFormKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == formDefine) continue;
                    if (addFlag) {
                        valueBuilder.append(",");
                    }
                    valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                    addFlag = true;
                }
            } else {
                formKey = "\u6240\u6709\u62a5\u8868";
                valueBuilder.append(formKey);
            }
            valueBuilder.append(" \u5220\u9664\u884c\u6570:").append(num);
        } else {
            valueBuilder.append("\u672a\u8bb0\u5f55\u73af\u5883\u4fe1\u606f");
        }
        return valueBuilder;
    }

    private void appendEntity(JtableContext jtableContext, StringBuilder valueBuilder, Map<String, String> entityViewsMap, Map.Entry<String, DimensionValue> entry, String title) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(entry.getKey());
        Map<String, EntityData> entitys = null;
        if (null != entityViewKey && !PARENT_CODE_SHOW.equals(entityViewKey)) {
            entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
            String[] split = entry.getValue().getValue().split(";");
            entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
            entityQueryByKeysInfo.setContext(jtableContext);
            EntityByKeysReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
            entitys = entityByKeyReturnInfo.getEntitys();
        }
        valueBuilder.append(title);
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    valueBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    valueBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        } else {
            valueBuilder.append(title).append("\u503c:").append(entry.getValue().getValue());
        }
        valueBuilder.append(", ");
    }

    static {
        previewLock = new Object();
    }
}

