/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataInputTypeEnum
 *  com.jiuqi.gcreport.intermediatelibrary.vo.GcOppUnitFieldVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.grid2.Grid2Data
 *  com.jiuqi.np.grid2.GridCellData
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult
 *  com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.dataentry.service.IFormLockService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.option.TaskOptionService
 *  com.jiuqi.nr.definition.option.core.TaskOptionDefineDTO
 *  com.jiuqi.nr.definition.option.core.TaskOptionVO
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn
 *  com.jiuqi.nr.jtable.exception.FieldDataErrorException
 *  com.jiuqi.nr.jtable.exception.SaveDataException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.input.RegionDataCommitSet
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.input.ReportDataCommitSet
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.util.DataLinkStyleUtil
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.LinkDataFactory
 *  com.jiuqi.nr.jtable.util.RegionDataFactory
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.organization.service.OrgDataService
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.intermediatelibrary.dao.IntermediateLibraryDao;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILHandleZbEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILOrgEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSyncCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.MdZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.enums.IntermediateLibraryEnums;
import com.jiuqi.gcreport.intermediatelibrary.job.IntermediateLibraryFactory;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryService;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataInputTypeEnum;
import com.jiuqi.gcreport.intermediatelibrary.vo.GcOppUnitFieldVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.option.TaskOptionService;
import com.jiuqi.nr.definition.option.core.TaskOptionDefineDTO;
import com.jiuqi.nr.definition.option.core.TaskOptionVO;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.FieldDataErrorException;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.LinkDataFactory;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.organization.service.OrgDataService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class IntermediateLibraryServiceImpl
implements IntermediateLibraryService {
    @Resource
    private IntermediateLibraryDao intermediateLibraryDao;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Resource
    private IJtableResourceService iJtableResourceService;
    @Resource
    private IJtableParamService jtableParamService;
    @Resource
    private IJtableEntityService jtableEntityService;
    @Resource
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Resource
    private BaseDataClient baseDataClient;
    @Resource
    private TaskOptionService taskOptionService;
    private static final Logger logger = LoggerFactory.getLogger(IntermediateLibraryServiceImpl.class);
    private final Map<String, JdbcTemplate> sourceCode2Jdbc = new ConcurrentHashMap<String, JdbcTemplate>();
    private static final String PUSH_ZB_DATA_LIST = "pushZbDataList";
    private static final String PUSH_ZB_DATA_DELETE_LIST = "pushZbDataDeleteList";
    private static final String PUSH_MD_ZB_DATA_LIST = "pushMdZbDataList";
    private static final String PUSH_MD_ZB_DATE_DELETE_LIST = "pushMdZbDataDeleteList";
    private static final String MD_GCADJTYPE = "MD_GCADJTYPE";
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private OrgDataService vaOrgDataService;
    @Resource
    private DefinitionAuthorityProvider authoritryProvider;

    @Override
    public void createAsyncTask(AsyncTaskMonitor asyncTaskMonitor, String title) {
        if (asyncTaskMonitor == null) {
            return;
        }
        asyncTaskMonitor.progressAndMessage(0.0, title);
    }

    @Override
    public void modifyAsyncTaskState(AsyncTaskMonitor asyncTaskMonitor, double progress, String result) {
        if (asyncTaskMonitor == null) {
            return;
        }
        asyncTaskMonitor.progressAndMessage(progress, result);
    }

    private double getStepProgress(Set<String> fileIdList) {
        double stepProgress = fileIdList != null && fileIdList.size() > 0 ? BigDecimal.valueOf(0.9f / (float)fileIdList.size()).setScale(5, 1).doubleValue() : 0.9;
        return stepProgress;
    }

    @Override
    public ILExtractCondition getAsyncData(Object args, AsyncTaskMonitor asyncTaskMonitor) {
        ILExtractCondition iLExtractCondition = (ILExtractCondition)JsonUtils.readValue((String)((String)args), ILExtractCondition.class);
        iLExtractCondition.setAsyncTaskMonitor(asyncTaskMonitor);
        return iLExtractCondition;
    }

    private Map<String, Integer> initLoggerMap() {
        HashMap<String, Integer> logger2ValueMap = new HashMap<String, Integer>();
        logger2ValueMap.put("simpleTableCount", 0);
        logger2ValueMap.put("floatTableCount", 0);
        logger2ValueMap.put("simpleZbCount", 0);
        logger2ValueMap.put("floatZbCount", 0);
        logger2ValueMap.put("floatZbRow", 0);
        logger2ValueMap.put("simpleZbSuccessCount", 0);
        logger2ValueMap.put("floatZbSuccessRow", 0);
        return logger2ValueMap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void handleConnIsSuccess(String dataSource) throws Exception {
        Connection connection = null;
        JdbcTemplate jdbcTemplate = null;
        try {
            jdbcTemplate = ((IntermediateLibraryDao)SpringContextUtils.getBean(IntermediateLibraryDao.class)).getJdbcTemplate(dataSource);
            this.sourceCode2Jdbc.put(dataSource, jdbcTemplate);
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(jdbcTemplate.getDataSource()));
            if (jdbcTemplate == null) return;
        }
        catch (Exception e) {
            try {
                logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u8fde\u63a5\u5931\u8d25\u3002", e);
                throw new Exception(GcI18nUtil.getMessage((String)"gc.intermediate.jdbc.error"));
            }
            catch (Throwable throwable) {
                if (jdbcTemplate == null) throw throwable;
                DataSourceUtils.releaseConnection(connection, (DataSource)jdbcTemplate.getDataSource());
                throw throwable;
            }
        }
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)jdbcTemplate.getDataSource());
        return;
    }

    @Override
    public void programmeHandle(ILExtractCondition iLExtractCondition) throws Exception {
        ILEntity iLEntity = this.intermediateLibraryDao.getProgrammeForId(iLExtractCondition.getProgrammeId());
        if (StringUtils.isEmpty((String)iLEntity.getSourceType())) {
            logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u7684\u6570\u636e\u6e90\u7c7b\u578b\u3002");
            iLExtractCondition.getLibraryMessages().add("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u7684\u6570\u636e\u6e90\u7c7b\u578b\u3002");
            return;
        }
        if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(iLEntity.getSourceType())) {
            this.handleConnIsSuccess(iLEntity.getLibraryDataSource());
        }
        try {
            this.programmeExtractAndPush(iLExtractCondition, iLEntity);
        }
        catch (Exception e) {
            iLExtractCondition.getLibraryMessages().add("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(iLExtractCondition.getEnvContext().getTaskKey());
            if (iLExtractCondition.getPushType()) {
                logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002", e);
                LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001", (String)("\u63a8\u9001-\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011-\u65f6\u671f\u3010" + iLExtractCondition.getEnvContext().getPeriod() + "\u3011-\u65b9\u6848\u3010" + iLEntity.getProgrammeName() + "\u3011"), (String)"\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            }
            logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63d0\u53d6\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002", e);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001", (String)("\u63a8\u9001-\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011-\u65f6\u671f\u3010" + iLExtractCondition.getEnvContext().getPeriod() + "\u3011-\u65b9\u6848\u3010" + iLEntity.getProgrammeName() + "\u3011"), (String)"\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
    }

    private Map<String, List<ZbDataEntity>> zbDataEntityMapInit() {
        HashMap<String, List<ZbDataEntity>> zbType2ZbList = new HashMap<String, List<ZbDataEntity>>();
        zbType2ZbList.put(PUSH_ZB_DATA_LIST, new ArrayList());
        zbType2ZbList.put(PUSH_ZB_DATA_DELETE_LIST, new ArrayList());
        return zbType2ZbList;
    }

    private Map<String, List<MdZbDataEntity>> mdZbDataEntityMapInit() {
        HashMap<String, List<MdZbDataEntity>> mdZbType2ZbList = new HashMap<String, List<MdZbDataEntity>>();
        mdZbType2ZbList.put(PUSH_MD_ZB_DATA_LIST, new ArrayList());
        mdZbType2ZbList.put(PUSH_MD_ZB_DATE_DELETE_LIST, new ArrayList());
        return mdZbType2ZbList;
    }

    private void programmeExtractAndPush(ILExtractCondition iCondition, ILEntity iLEntity) throws Exception {
        logger.info("\u5f00\u59cb\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4efb\u52a1" + iCondition.getPushType() + "\u64cd\u4f5c\u3002");
        Map<String, List<ZbDataEntity>> zbType2ZbList = this.zbDataEntityMapInit();
        Map<String, List<MdZbDataEntity>> mdZbType2ZbList = this.mdZbDataEntityMapInit();
        Map<String, Integer> logger2ValueMap = this.initLoggerMap();
        this.createAsyncTask(iCondition.getAsyncTaskMonitor(), "\u5f00\u59cb\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u4efb\u52a1");
        this.modifyAsyncTaskState(iCondition.getAsyncTaskMonitor(), 0.1, "\u5f00\u59cb\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u9a8c\u8bc1");
        if (Boolean.parseBoolean(iCondition.getIsAllFormChoose())) {
            List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(iCondition.getEnvContext().getFormSchemeKey());
            iCondition.setFormIdList(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        }
        Map<String, Map<String, List<ILSyncCondition>>> regionTypeToFileIdMap = this.filterZbData(iCondition, logger2ValueMap);
        Set<String> orgIdList = this.filterOrgId(iCondition, iLEntity);
        double stepProgress = this.getStepProgress(orgIdList);
        iCondition.setStepProgress(stepProgress);
        ILHandleZbEntity ilHandleZbEntity = this.initILHandleZbEntity(iCondition.getPushType(), iCondition, iLEntity, logger2ValueMap);
        String orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)ilHandleZbEntity.getiCondition().getEnvContext().getTaskKey());
        boolean formLock = this.getFormLock(iCondition.getEnvContext().getTaskKey());
        orgIdList.forEach(orgId -> {
            GcOrgCacheVO org = this.getGcOrgCacheVOById(iCondition, ilHandleZbEntity, orgCategory, (String)orgId);
            if (org == null) {
                iCondition.getLibraryMessages().add("\u5355\u4f4d\u3010" + orgId + "\u3011\u6ca1\u6709\u6743\u9650");
                return;
            }
            ilHandleZbEntity.setGcOrgCacheVO(org);
            ilHandleZbEntity.setOrgId((String)orgId);
            if (iCondition.getAsyncTaskMonitor() != null && iCondition.getAsyncTaskMonitor().isCancel()) {
                return;
            }
            double currentProgress = iCondition.getCurrentProgress() + iCondition.getStepProgress();
            iCondition.setCurrentProgress(currentProgress);
            this.modifyAsyncTaskState(iCondition.getAsyncTaskMonitor(), currentProgress, "\u6b63\u5728\u6267\u884c[" + orgId + "]\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63d0\u53d6\u9a8c\u8bc1");
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(iCondition.getEnvContext().getFormSchemeKey());
            DimensionValueSet dimensionValueSet = IntermediateLibraryServiceImpl.getDimensionValueSet(dwEntity.getDimensionName(), iCondition.getEnvContext().getDimensionSet(), orgId);
            ilHandleZbEntity.setDimensionValueSet(dimensionValueSet);
            this.setDimensionSet(org, iCondition, dwEntity);
            if (!this.isReadWriteAccess(ilHandleZbEntity, iCondition)) {
                return;
            }
            Map<String, String> formId2Desc = new HashMap<String, String>();
            if (!ilHandleZbEntity.getPushType().booleanValue() && formLock) {
                formId2Desc = this.getFormState(iCondition, dimensionValueSet);
            }
            this.initZbData(ilHandleZbEntity, regionTypeToFileIdMap, zbType2ZbList, formId2Desc, mdZbType2ZbList);
            try {
                this.pushZbData(iLEntity, mdZbType2ZbList, zbType2ZbList, ilHandleZbEntity);
            }
            catch (SQLException e) {
                logger.error("\u5355\u4f4d\u3010" + orgId + "\u3011\u6307\u6807\u64cd\u4f5c\u5f02\u5e38\u3002", e);
                ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u5355\u4f4d\u3010" + orgId + "\u3011\u6307\u6807\u64cd\u4f5c\u5f02\u5e38\u3002");
            }
        });
        String loggerStr = this.setLogs(iCondition, orgIdList, logger2ValueMap, iLEntity);
        if (!iCondition.getLibraryMessages().isEmpty()) {
            loggerStr = loggerStr + "--\u8be6\u7ec6\u4fe1\u606f\uff1a";
        }
        iCondition.getLibraryMessages().add(loggerStr);
    }

    private String setLogs(ILExtractCondition iCondition, Set<String> orgIdList, Map<String, Integer> logger2ValueMap, ILEntity iLEntity) {
        String loggerStr = "";
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(iCondition.getEnvContext().getTaskKey());
        if (iCondition.getPushType()) {
            loggerStr = loggerStr + "\u672c\u6b21\u5171\u63a8\u9001\u5355\u4f4d" + orgIdList.size() + "\u5bb6\uff0c\u56fa\u5b9a\u8868" + logger2ValueMap.get("simpleTableCount") + "\u5f20\uff0c\u6d6e\u52a8\u8868" + logger2ValueMap.get("floatTableCount") + "\u5f20\u3002--\u63a8\u9001\u6570\u91cf\uff1a\u56fa\u5b9a\u6307\u6807\u6570\u91cf" + logger2ValueMap.get("simpleZbCount") * orgIdList.size() + "\u4e2a\uff0c\u6d6e\u52a8\u6307\u6807\u884c\u6570" + logger2ValueMap.get("floatZbRow") + "\u884c\u3002--\u63a8\u9001\u6210\u529f\uff1a\u56fa\u5b9a\u6307\u6807\u6570\u91cf" + logger2ValueMap.get("simpleZbSuccessCount") + "\u4e2a\uff0c\u6d6e\u52a8\u6307\u6807\u884c\u6570" + logger2ValueMap.get("floatZbSuccessRow") + "\u884c\u3002--\u63a8\u9001\u5931\u8d25\uff1a\u56fa\u5b9a\u6307\u6807\u6570\u91cf" + (logger2ValueMap.get("simpleZbCount") * orgIdList.size() - logger2ValueMap.get("simpleZbSuccessCount")) + "\u4e2a\uff0c\u6d6e\u52a8\u6307\u6807\u884c\u6570" + (logger2ValueMap.get("floatZbRow") - logger2ValueMap.get("floatZbSuccessRow")) + "\u884c\u3002";
            logger.info(loggerStr);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001", (String)("\u63a8\u9001-\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011-\u65f6\u671f\u3010" + ((DimensionValue)iCondition.getEnvContext().getDimensionSet().get("DATATIME")).getValue() + "\u3011-\u65b9\u6848\u3010" + iLEntity.getProgrammeName() + "\u3011"), (String)this.addLibraryMessage(loggerStr, iCondition));
        } else {
            loggerStr = loggerStr + "\u672c\u6b21\u5171\u63d0\u53d6\u5355\u4f4d" + orgIdList.size() + "\u5bb6\uff0c\u56fa\u5b9a\u8868" + logger2ValueMap.get("simpleTableCount") + "\u5f20\uff0c\u6d6e\u52a8\u8868" + logger2ValueMap.get("floatTableCount") + "\u5f20\u3002--\u63d0\u53d6\u6570\u91cf\uff1a\u56fa\u5b9a\u6307\u6807\u6570\u91cf" + logger2ValueMap.get("simpleZbCount") * orgIdList.size() + "\u4e2a\uff0c\u6d6e\u52a8\u6307\u6807\u884c\u6570" + logger2ValueMap.get("floatZbRow") + "\u884c\u3002--\u63d0\u53d6\u6210\u529f\uff1a\u56fa\u5b9a\u6307\u6807\u6570\u91cf" + logger2ValueMap.get("simpleZbSuccessCount") + "\u4e2a\uff0c\u6d6e\u52a8\u6307\u6807\u884c\u6570" + logger2ValueMap.get("floatZbSuccessRow") + "\u884c\u3002--\u63d0\u53d6\u5931\u8d25\uff1a\u56fa\u5b9a\u6307\u6807\u6570\u91cf" + (logger2ValueMap.get("simpleZbCount") * orgIdList.size() - logger2ValueMap.get("simpleZbSuccessCount")) + "\u4e2a\uff0c\u6d6e\u52a8\u6307\u6807\u884c\u6570" + (logger2ValueMap.get("floatZbRow") - logger2ValueMap.get("floatZbSuccessRow")) + "\u884c\u3002";
            logger.info(loggerStr);
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165-\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63d0\u53d6", (String)("\u63d0\u53d6-\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011-\u65f6\u671f\u3010" + ((DimensionValue)iCondition.getEnvContext().getDimensionSet().get("DATATIME")).getValue() + "\u3011-\u65b9\u6848\u3010" + iLEntity.getProgrammeName() + "\u3011"), (String)this.addLibraryMessage(loggerStr, iCondition));
        }
        return loggerStr;
    }

    private void pushZbData(ILEntity iLEntity, Map<String, List<MdZbDataEntity>> mdZbType2ZbList, Map<String, List<ZbDataEntity>> zbType2ZbList, ILHandleZbEntity ilHandleZbEntity) throws SQLException {
        if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(iLEntity.getSourceType())) {
            this.handleZbData(zbType2ZbList, mdZbType2ZbList, iLEntity);
        } else {
            IntermediateLibraryFactory.getInstance().getIntermediateLibraryBulkService().programmePush(ilHandleZbEntity, zbType2ZbList, mdZbType2ZbList);
        }
        this.zbDataEntityMapBlank(zbType2ZbList);
        this.mdZbDataEntityMapBlank(mdZbType2ZbList);
    }

    private void initZbData(ILHandleZbEntity ilHandleZbEntity, Map<String, Map<String, List<ILSyncCondition>>> regionTypeToFileIdMap, Map<String, List<ZbDataEntity>> zbType2ZbList, Map<String, String> formId2Desc, Map<String, List<MdZbDataEntity>> mdZbType2ZbList) {
        ilHandleZbEntity.setRegionSimpleToFieldsMap(regionTypeToFileIdMap.get("REGION_SIMPLE"));
        this.handleSimpleZb(ilHandleZbEntity, zbType2ZbList, formId2Desc);
        ilHandleZbEntity.setRegionSimpleToFieldsMap(regionTypeToFileIdMap.get("REGION_NO_SIMPLE"));
        this.handleNoSimpleZb(ilHandleZbEntity, mdZbType2ZbList, formId2Desc);
    }

    private boolean isReadWriteAccess(ILHandleZbEntity ilHandleZbEntity, ILExtractCondition iCondition) {
        ReadWriteAccessDesc unitWriteable;
        if (!ilHandleZbEntity.getPushType().booleanValue() && !(unitWriteable = this.unitWriteable(iCondition)).getAble().booleanValue()) {
            iCondition.getLibraryMessages().add(unitWriteable.getDesc());
            return false;
        }
        return true;
    }

    private void setDimensionSet(GcOrgCacheVO org, ILExtractCondition iCondition, EntityViewData dwEntity) {
        DimensionValue orgTypeDimen;
        if (org.getOrgTypeId() != null) {
            orgTypeDimen = new DimensionValue();
            orgTypeDimen.setName("MD_GCORGTYPE");
            orgTypeDimen.setValue(org.getOrgTypeId());
            iCondition.getEnvContext().getDimensionSet().put("MD_GCORGTYPE", orgTypeDimen);
            iCondition.setDimensionSet(iCondition.getEnvContext().getDimensionSet());
        }
        if (DimensionUtils.isExisAdjType((String)iCondition.getEnvContext().getTaskKey())) {
            orgTypeDimen = new DimensionValue();
            orgTypeDimen.setName(MD_GCADJTYPE);
            orgTypeDimen.setValue(GCAdjTypeEnum.BEFOREADJ.getCode());
            iCondition.getEnvContext().getDimensionSet().put(MD_GCADJTYPE, orgTypeDimen);
            iCondition.setDimensionSet(iCondition.getEnvContext().getDimensionSet());
        }
        ((DimensionValue)iCondition.getEnvContext().getDimensionSet().get(dwEntity.getDimensionName())).setValue(org.getCode());
        iCondition.getDimensionSet().get(dwEntity.getDimensionName()).setValue(org.getCode());
    }

    private GcOrgCacheVO getGcOrgCacheVOById(ILExtractCondition iCondition, ILHandleZbEntity ilHandleZbEntity, String orgCategory, String orgId) {
        YearPeriodObject yp = new YearPeriodObject(iCondition.getEnvContext().getFormSchemeKey(), ((DimensionValue)iCondition.getEnvContext().getDimensionSet().get("DATATIME")).getValue());
        GcOrgCenterService tool = ilHandleZbEntity.getPushType() != false ? GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp) : GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.WRITE, (YearPeriodObject)yp);
        return tool.getOrgByCode(orgId);
    }

    private void zbDataEntityMapBlank(Map<String, List<ZbDataEntity>> zbType2ZbList) {
        zbType2ZbList.put(PUSH_ZB_DATA_LIST, new ArrayList());
        zbType2ZbList.put(PUSH_ZB_DATA_DELETE_LIST, new ArrayList());
    }

    private void mdZbDataEntityMapBlank(Map<String, List<MdZbDataEntity>> mdZbType2ZbList) {
        mdZbType2ZbList.put(PUSH_MD_ZB_DATA_LIST, new ArrayList());
        mdZbType2ZbList.put(PUSH_MD_ZB_DATE_DELETE_LIST, new ArrayList());
    }

    private String addLibraryMessage(String loggerStr, ILExtractCondition iCondition) {
        if (!iCondition.getLibraryMessages().isEmpty()) {
            loggerStr = loggerStr + "--\u8be6\u7ec6\u4fe1\u606f\uff1a";
            loggerStr = loggerStr + iCondition.getLibraryMessages();
        }
        return loggerStr;
    }

    private boolean getFormLock(String taskKey) {
        TaskOptionVO taskOptionDefineDTOList = this.taskOptionService.getOptionDefines(taskKey);
        for (TaskOptionDefineDTO taskOptionDefineDTO : taskOptionDefineDTOList.getKeyMap().values()) {
            if (!"FORM_LOCK".equals(taskOptionDefineDTO.getKey()) || !"0".equals(taskOptionDefineDTO.getValue())) continue;
            return false;
        }
        return true;
    }

    private ILHandleZbEntity initILHandleZbEntity(Boolean pushType, ILExtractCondition iCondition, ILEntity iLEntity, Map<String, Integer> logger2ValueMap) {
        ILHandleZbEntity ilHandleZbEntity = new ILHandleZbEntity();
        ilHandleZbEntity.setPushType(pushType);
        ilHandleZbEntity.setiLEntity(iLEntity);
        ilHandleZbEntity.setiCondition(iCondition);
        ilHandleZbEntity.setLogger2ValueMap(logger2ValueMap);
        return ilHandleZbEntity;
    }

    private void handleZbData(Map<String, List<ZbDataEntity>> zbType2ZbList, Map<String, List<MdZbDataEntity>> mdZbType2ZbList, ILEntity iLEntity) throws SQLException {
        if (!zbType2ZbList.get(PUSH_ZB_DATA_DELETE_LIST).isEmpty()) {
            this.intermediateLibraryDao.deleteZbDataByWhere(zbType2ZbList.get(PUSH_ZB_DATA_DELETE_LIST), iLEntity);
        }
        if (!zbType2ZbList.get(PUSH_ZB_DATA_LIST).isEmpty()) {
            this.intermediateLibraryDao.pushZbData(zbType2ZbList.get(PUSH_ZB_DATA_LIST), iLEntity);
        }
        if (!mdZbType2ZbList.get(PUSH_MD_ZB_DATE_DELETE_LIST).isEmpty()) {
            this.intermediateLibraryDao.deleteMdZbDataByWhere(mdZbType2ZbList.get(PUSH_MD_ZB_DATE_DELETE_LIST), iLEntity);
        }
        if (!mdZbType2ZbList.get(PUSH_MD_ZB_DATA_LIST).isEmpty()) {
            this.intermediateLibraryDao.pushMdZbData(mdZbType2ZbList.get(PUSH_MD_ZB_DATA_LIST), iLEntity);
        }
    }

    private void handleSimpleZb(ILHandleZbEntity ilHandleZbEntity, Map<String, List<ZbDataEntity>> zbType2ZbList, Map<String, String> formId2Desc) {
        try {
            ZbDataEntity zbDataEntityAllZbCode = this.handleZbDataEntity(ilHandleZbEntity, null);
            ArrayList conditionList = new ArrayList();
            ilHandleZbEntity.getRegionSimpleToFieldsMap().forEach((regionKey, iLSyncConditionList) -> conditionList.addAll(iLSyncConditionList));
            if (CollectionUtils.isEmpty(conditionList)) {
                return;
            }
            List<Object> zbDataList = new ArrayList();
            Set<Object> zbCodeDataList = new HashSet();
            if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(ilHandleZbEntity.getiLEntity().getSourceType())) {
                List<String> zbCodeList = conditionList.stream().map(iLSyncCondition -> iLSyncCondition.getTableDefine().getCode() + "[" + iLSyncCondition.getFieldDefine().getCode() + "]").collect(Collectors.toList());
                zbDataEntityAllZbCode.setZbCodeList(zbCodeList);
                zbDataList = this.intermediateLibraryDao.extractZbData(zbDataEntityAllZbCode, ilHandleZbEntity.getiLEntity(), this.sourceCode2Jdbc.get(ilHandleZbEntity.getiLEntity().getLibraryDataSource()));
                zbCodeDataList = zbDataList.stream().map(ZbDataEntity::getZbCode).collect(Collectors.toSet());
            }
            this.zbNoPower(ilHandleZbEntity, formId2Desc);
            HashSet finalZbCodeDataList = zbCodeDataList;
            ArrayList finalZbDataList = zbDataList;
            ilHandleZbEntity.getRegionSimpleToFieldsMap().forEach((regionKey, zbValueList) -> {
                try {
                    IDataTable dataTable;
                    int rowCount;
                    if (!CollectionUtils.isEmpty((Collection)zbValueList)) {
                        ilHandleZbEntity.getiCondition().getEnvContext().setFormKey(((ILSyncCondition)zbValueList.get(0)).getFormId());
                    }
                    IDataRow dataRow = (rowCount = (dataTable = this.getZbValueSimpleList(ilHandleZbEntity, zbValueList.stream().map(ILSyncCondition::getFieldDefine).collect(Collectors.toList()), (String)regionKey)).getCount()) == 0 ? dataTable.appendRow(ilHandleZbEntity.getDimensionValueSet()) : dataTable.getItem(0);
                    zbValueList.forEach(condition -> {
                        try {
                            if (formId2Desc.containsKey(condition.getFormId())) {
                                ilHandleZbEntity.getiCondition().getLibraryMessages().add((String)formId2Desc.get(condition.getFormId()));
                                return;
                            }
                            ZbDataEntity zbDataEntity = this.handleZbDataEntity(ilHandleZbEntity, (ILSyncCondition)condition);
                            if (ilHandleZbEntity.getPushType().booleanValue()) {
                                this.pushSimpleZbValue(zbDataEntity, (ILSyncCondition)condition, ilHandleZbEntity, dataRow);
                                if (finalZbCodeDataList.contains(condition.getCode())) {
                                    ((List)zbType2ZbList.get(PUSH_ZB_DATA_DELETE_LIST)).add(zbDataEntity);
                                }
                                ((List)zbType2ZbList.get(PUSH_ZB_DATA_LIST)).add(zbDataEntity);
                            } else {
                                List<ZbDataEntity> zbDataEntityList = finalZbDataList.stream().filter(zbData -> zbData.getZbCode().equals(condition.getCode())).collect(Collectors.toList());
                                this.extractSimpleZbValue(zbDataEntityList, (ILSyncCondition)condition, ilHandleZbEntity, dataRow);
                            }
                        }
                        catch (Exception e) {
                            ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u64cd\u4f5c\u5f02\u5e38\u3002");
                        }
                    });
                    if (!ilHandleZbEntity.getPushType().booleanValue()) {
                        dataTable.commitChanges(true);
                    }
                }
                catch (Exception e) {
                    this.exceptionMessage(ilHandleZbEntity, e);
                }
            });
        }
        catch (Exception e) {
            this.exceptionMessage(ilHandleZbEntity, e);
        }
    }

    private void handleNoSimpleZb(ILHandleZbEntity ilHandleZbEntity, Map<String, List<MdZbDataEntity>> mdZbType2ZbList, Map<String, String> formId2Desc) {
        MdZbDataEntity mdZbDataEntityAllZbCode = this.handleMdZbDataEntity(ilHandleZbEntity, null);
        ArrayList conditionList = new ArrayList();
        ilHandleZbEntity.getRegionSimpleToFieldsMap().forEach((regionKey, iLSyncConditionList) -> conditionList.addAll(iLSyncConditionList));
        if (CollectionUtils.isEmpty(conditionList)) {
            return;
        }
        List<Object> mdZbDataList = new ArrayList();
        if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue().equals(ilHandleZbEntity.getiLEntity().getSourceType())) {
            List<String> mdZbCodeList = conditionList.stream().map(iLSyncCondition -> iLSyncCondition.getTableDefine().getCode() + "[" + iLSyncCondition.getFieldDefine().getCode() + "]").collect(Collectors.toList());
            mdZbDataEntityAllZbCode.setZbCodeList(mdZbCodeList);
            mdZbDataList = this.intermediateLibraryDao.extractMdZbData(mdZbDataEntityAllZbCode, ilHandleZbEntity.getiLEntity(), this.sourceCode2Jdbc.get(ilHandleZbEntity.getiLEntity().getLibraryDataSource()));
        }
        this.zbNoPower(ilHandleZbEntity, formId2Desc);
        ArrayList finalMdZbDataList = mdZbDataList;
        ilHandleZbEntity.getRegionSimpleToFieldsMap().forEach((regionKey, zbValueList) -> {
            block17: {
                try {
                    if (formId2Desc.containsKey(((ILSyncCondition)zbValueList.get(0)).getFormId())) {
                        ilHandleZbEntity.getiCondition().getLibraryMessages().add((String)formId2Desc.get(((ILSyncCondition)zbValueList.get(0)).getFormId()));
                        return;
                    }
                    if (!CollectionUtils.isEmpty((Collection)zbValueList)) {
                        ilHandleZbEntity.getiCondition().getEnvContext().setFormKey(((ILSyncCondition)zbValueList.get(0)).getFormId());
                    }
                    DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
                    try {
                        boolean isInputData = !zbValueList.isEmpty() && ((ILSyncCondition)zbValueList.get(0)).getTableDefine().getCode().toUpperCase().contains("GC_INPUTDATA");
                        IDataTable dataTable = this.getZbValueList(ilHandleZbEntity, zbValueList.stream().map(ILSyncCondition::getFieldDefine).collect(Collectors.toList()), (String)regionKey, ((ILSyncCondition)zbValueList.get(0)).getFormId());
                        if (!ilHandleZbEntity.getPushType().booleanValue()) {
                            try {
                                if (isInputData) {
                                    if (ilHandleZbEntity.getGcOrgCacheVO().getOrgKind() == GcOrgKindEnum.UNIONORG) {
                                        ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u5408\u5e76\u5355\u4f4d\u3010" + ilHandleZbEntity.getOrgId() + "\u3011\u5185\u90e8\u8868\u4e0d\u5141\u8bb8\u66f4\u6539\u6570\u636e\u3002");
                                        return;
                                    }
                                    if (ilHandleZbEntity.getGcOrgCacheVO().getOrgKind() == GcOrgKindEnum.DIFFERENCE) {
                                        ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u5dee\u989d\u5355\u4f4d\u3010" + ilHandleZbEntity.getOrgId() + "\u3011\u5185\u90e8\u8868\u4e0d\u5141\u8bb8\u66f4\u6539\u6570\u636e\u3002");
                                        return;
                                    }
                                }
                                JtableContext jtableContext = this.initContext(ilHandleZbEntity.getiCondition().getEnvContext());
                                RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
                                regionQueryInfo.setContext(jtableContext);
                                RegionData region = this.jtableParamService.getRegion(regionKey);
                                RegionDataFactory factory = new RegionDataFactory();
                                factory.clearRegionDataSet(region, regionQueryInfo);
                            }
                            catch (Exception e) {
                                FormDefine formDefine = this.iRunTimeViewController.queryFormById(((ILSyncCondition)zbValueList.get(0)).getFormId());
                                logger.error("\u62a5\u8868\u3010" + formDefine.getTitle() + "\u3011\u6e05\u9664\u533a\u57df\u5f02\u5e38\u3002", e);
                                ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u62a5\u8868\u3010" + formDefine.getTitle() + "\u3011\u6e05\u9664\u533a\u57df\u5f02\u5e38\u3002");
                                return;
                            }
                        }
                        Set zbCodeSet = zbValueList.stream().map(ILSyncCondition::getCode).collect(Collectors.toSet());
                        List mdZbDataAllList = finalMdZbDataList.stream().filter(mdZbData -> zbCodeSet.contains(mdZbData.getZbCode()) && mdZbData.getFloatArea().equals(regionKey)).collect(Collectors.toList());
                        Map<String, List<MdZbDataEntity>> listMap = mdZbDataAllList.stream().collect(Collectors.groupingBy(MdZbDataEntity::getZbCode));
                        if (ilHandleZbEntity.getPushType().booleanValue()) {
                            int row = dataTable.getCount();
                            for (ILSyncCondition condition : zbValueList) {
                                MdZbDataEntity mdZbDataEntity = this.handleMdZbDataEntity(ilHandleZbEntity, condition);
                                mdZbDataEntity.setFloatArea((String)regionKey);
                                try {
                                    List<MdZbDataEntity> getMdZbDataList = listMap.get(condition.getTableDefine().getCode() + "[" + condition.getFieldDefine().getCode() + "]");
                                    if (getMdZbDataList != null && !getMdZbDataList.isEmpty()) {
                                        ((List)mdZbType2ZbList.get(PUSH_MD_ZB_DATE_DELETE_LIST)).add(mdZbDataEntity);
                                    }
                                    this.pushNotSimpleZbValue(mdZbDataEntity, condition, ilHandleZbEntity.getiCondition(), dataTable, mdZbType2ZbList, isInputData);
                                }
                                catch (Exception e) {
                                    logger.error("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u64cd\u4f5c\u5f02\u5e38\u3002");
                                    ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u64cd\u4f5c\u5f02\u5e38\u3002");
                                }
                            }
                            int floatZbRow = ilHandleZbEntity.getLogger2ValueMap().get("floatZbRow");
                            ilHandleZbEntity.getLogger2ValueMap().put("floatZbRow", floatZbRow += row);
                            int floatZbSuccessRow = ilHandleZbEntity.getLogger2ValueMap().get("floatZbSuccessRow");
                            ilHandleZbEntity.getLogger2ValueMap().put("floatZbSuccessRow", floatZbSuccessRow += row);
                            break block17;
                        }
                        this.extractNotSimpleZbValue(listMap, (List<ILSyncCondition>)zbValueList, ilHandleZbEntity, dataRegionDefine, isInputData);
                    }
                    catch (Exception e) {
                        this.exceptionMessage(ilHandleZbEntity, e);
                    }
                }
                catch (Exception e) {
                    this.exceptionMessage(ilHandleZbEntity, e);
                }
            }
        });
    }

    private void exceptionMessage(ILHandleZbEntity ilHandleZbEntity, Exception e) {
        ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        String loggerStr = "\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3%s\u64cd\u4f5c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002";
        if (ilHandleZbEntity.getPushType().booleanValue()) {
            logger.error(String.format(loggerStr, "\u63a8\u9001"), e);
            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)"\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63a8\u9001", (String)String.format(loggerStr, "\u63a8\u9001"));
        } else {
            logger.error(String.format(loggerStr, "\u63d0\u53d6"), e);
            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)"\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u63d0\u53d6", (String)String.format(loggerStr, "\u63d0\u53d6"));
        }
    }

    public PageVO<OrgDO> listAllChildrenWithSelf(String orgParentCode) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setCode(orgParentCode);
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        return this.vaOrgDataService.list(orgDTO);
    }

    private ZbDataEntity handleZbDataEntity(ILHandleZbEntity ilHandleZbEntity, ILSyncCondition condition) {
        ZbDataEntity zbDataEntity = new ZbDataEntity();
        zbDataEntity.setYear(Integer.parseInt(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("DATATIME")).getValue().substring(0, 4)));
        zbDataEntity.setPeriod(Integer.parseInt(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("DATATIME")).getValue().substring(5)));
        zbDataEntity.setUnitCode(ilHandleZbEntity.getOrgId());
        if (condition != null) {
            zbDataEntity.setZbCode(condition.getTableDefine().getCode() + "[" + condition.getFieldDefine().getCode() + "]");
        }
        zbDataEntity.setDataType(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("DATATIME")).getValue().substring(4, 5));
        if (ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("MD_CURRENCY") != null) {
            zbDataEntity.setCurrencyCode(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("MD_CURRENCY")).getValue());
        } else {
            zbDataEntity.setCurrencyCode("CNY");
        }
        return zbDataEntity;
    }

    private MdZbDataEntity handleMdZbDataEntity(ILHandleZbEntity ilHandleZbEntity, ILSyncCondition condition) {
        MdZbDataEntity mdZbDataEntity = new MdZbDataEntity();
        mdZbDataEntity.setYear(Integer.parseInt(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("DATATIME")).getValue().substring(0, 4)));
        mdZbDataEntity.setPeriod(Integer.parseInt(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("DATATIME")).getValue().substring(5)));
        mdZbDataEntity.setUnitCode(ilHandleZbEntity.getOrgId());
        if (condition != null) {
            mdZbDataEntity.setZbCode(condition.getTableDefine().getCode() + "[" + condition.getFieldDefine().getCode() + "]");
        }
        mdZbDataEntity.setDataType(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("DATATIME")).getValue().substring(4, 5));
        if (ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("MD_CURRENCY") != null) {
            mdZbDataEntity.setCurrencyCode(((DimensionValue)ilHandleZbEntity.getiCondition().getEnvContext().getDimensionSet().get("MD_CURRENCY")).getValue());
        } else {
            mdZbDataEntity.setCurrencyCode("CNY");
        }
        return mdZbDataEntity;
    }

    private void pushSimpleZbValue(ZbDataEntity zbDataEntity, ILSyncCondition condition, ILHandleZbEntity ilHandleZbEntity, IDataRow dataRow) throws Exception {
        int typeValue = condition.getFieldDefine().getType().getValue();
        if (this.isZbValueN(typeValue).booleanValue()) {
            zbDataEntity.setZbValue_N(dataRow.getValue(condition.getFieldDefine()).getAsCurrency());
        }
        if (this.isZbValueT(typeValue).booleanValue()) {
            zbDataEntity.setZbValue_T(dataRow.getValue(condition.getFieldDefine()).getAsString());
        }
        if (FieldType.FIELD_TYPE_DATE.getValue() == typeValue && !StringUtils.isNull((String)String.valueOf(dataRow.getValue(condition.getFieldDefine()).getAsString()))) {
            if (this.initDateMessagePush(String.valueOf(dataRow.getValue(condition.getFieldDefine()).getAsString())).booleanValue()) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dataRow.getValue(condition.getFieldDefine()).getAsString()));
                zbDataEntity.setZbValue_T(new SimpleDateFormat("yyyy.MM.dd").format(date));
            } else {
                ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + dataRow.getValue(condition.getFieldDefine()).getAsString() + "\u4e0d\u6b63\u786e\u3002");
                logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + dataRow.getValue(condition.getFieldDefine()).getAsString() + "\u4e0d\u6b63\u786e\u3002");
            }
        }
        int simpleZbSuccessCount = ilHandleZbEntity.getLogger2ValueMap().get("simpleZbSuccessCount");
        ilHandleZbEntity.getLogger2ValueMap().put("simpleZbSuccessCount", ++simpleZbSuccessCount);
    }

    private void extractSimpleZbValue(List<ZbDataEntity> zbDataEntityList, ILSyncCondition condition, ILHandleZbEntity ilHandleZbEntity, IDataRow dataRow) throws Exception {
        if (!CollectionUtils.isEmpty(zbDataEntityList)) {
            ZbDataEntity zbDataEntity = zbDataEntityList.get(0);
            Object value = null;
            int typeValue = condition.getFieldDefine().getType().getValue();
            if (this.isZbValueN(typeValue).booleanValue()) {
                value = zbDataEntity.getZbValue_N();
            }
            if (this.isZbValueT(typeValue).booleanValue()) {
                value = zbDataEntity.getZbValue_T();
            }
            if (FieldType.FIELD_TYPE_DATE.getValue() == typeValue && !StringUtils.isNull((String)String.valueOf(value))) {
                if (this.initDateMessage(String.valueOf(value)).booleanValue()) {
                    Date date = new SimpleDateFormat("yyyy.MM.dd").parse(String.valueOf(value));
                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + value + "\u4e0d\u6b63\u786e\u3002");
                    logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + value + "\u4e0d\u6b63\u786e\u3002");
                }
            }
            if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_FULL_COVERAGE.getValue().equals(ilHandleZbEntity.getiLEntity().getExtractSimplePloy())) {
                dataRow.setValue(condition.getFieldDefine(), value);
            } else if (value != null) {
                dataRow.setValue(condition.getFieldDefine(), value);
            }
        } else {
            if (IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_FULL_COVERAGE.getValue().equals(ilHandleZbEntity.getiLEntity().getExtractSimplePloy())) {
                dataRow.setValue(condition.getFieldDefine(), null);
            }
            ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u63d0\u53d6\u7684\u6570\u636e\u4e3a\u7a7a");
            logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u63d0\u53d6\u7684\u6570\u636e\u4e3a\u7a7a");
        }
        int simpleZbSuccessCount = ilHandleZbEntity.getLogger2ValueMap().get("simpleZbSuccessCount");
        ilHandleZbEntity.getLogger2ValueMap().put("simpleZbSuccessCount", ++simpleZbSuccessCount);
    }

    private Boolean isZbValueN(int typeValue) {
        return FieldType.FIELD_TYPE_FLOAT.getValue() == typeValue || FieldType.FIELD_TYPE_INTEGER.getValue() == typeValue || FieldType.FIELD_TYPE_DECIMAL.getValue() == typeValue;
    }

    private Boolean isZbValueT(int typeValue) {
        return this.isZbValueN(typeValue) == false && FieldType.FIELD_TYPE_FILE.getValue() != typeValue && FieldType.FIELD_TYPE_PICTURE.getValue() != typeValue && FieldType.FIELD_TYPE_BINARY.getValue() != typeValue;
    }

    private boolean isFieldTypeEnum(ILSyncCondition condition, ILExtractCondition iCondition) {
        List dataLinkDefineList = this.iRunTimeViewController.getLinksInFormByField(condition.getFormId(), condition.getFieldDefine().getKey());
        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
        entityQueryInfo.setContext(this.initContext(iCondition.getEnvContext()));
        entityQueryInfo.setEntityViewKey(condition.getFieldDefine().getEntityKey());
        if (dataLinkDefineList.size() > 0) {
            List queryDataLinkMapping = this.iRunTimeViewController.queryDataLinkMapping(iCondition.getEnvContext().getFormKey());
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(iCondition.getEnvContext().getFormKey());
            LinkData linkData = LinkDataFactory.fieldDefine((DataLinkDefine)((DataLinkDefine)dataLinkDefineList.get(0)), (FieldDefine)condition.getFieldDefine(), (List)queryDataLinkMapping, (FormDefine)formDefine);
            return ((DataLinkDefine)dataLinkDefineList.get(0)).getRegionKey().equals(condition.getDataRegionDefine().getKey()) && FieldType.FIELD_TYPE_ENUM.getValue() == linkData.getType();
        }
        return false;
    }

    private Boolean handleZbCondition(ILSyncCondition condition, Object value, ILExtractCondition iCondition) {
        try {
            List dataLinkDefineList = this.iRunTimeViewController.getLinksInFormByField(condition.getFormId(), condition.getFieldDefine().getKey());
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryInfo.setContext(this.initContext(iCondition.getEnvContext()));
            entityQueryInfo.setEntityViewKey(condition.getFieldDefine().getEntityKey());
            if (dataLinkDefineList.size() > 0 && !StringUtils.isNull((String)String.valueOf(value))) {
                List queryDataLinkMapping = this.iRunTimeViewController.queryDataLinkMapping(iCondition.getEnvContext().getFormKey());
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(iCondition.getEnvContext().getFormKey());
                LinkData linkData = LinkDataFactory.fieldDefine((DataLinkDefine)((DataLinkDefine)dataLinkDefineList.get(0)), (FieldDefine)condition.getFieldDefine(), (List)queryDataLinkMapping, (FormDefine)formDefine);
                if (((DataLinkDefine)dataLinkDefineList.get(0)).getRegionKey().equals(condition.getDataRegionDefine().getKey()) && FieldType.FIELD_TYPE_ENUM.getValue() == linkData.getType()) {
                    EntityReturnInfo returnInfo;
                    entityQueryInfo.setDataLinkKey(((DataLinkDefine)dataLinkDefineList.get(0)).getKey());
                    entityQueryInfo.setAllChildren(true);
                    if (!StringUtils.isNull((String)condition.getFieldDefine().getEntityKey()) && !StringUtils.isNull((String)entityQueryInfo.getDataLinkKey()) && this.handleZbEnum((returnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo)).getEntitys(), value).booleanValue()) {
                        iCondition.getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u5728\u679a\u4e3e\u5b57\u5178\u4e2d\u4e0d\u5b58\u5728");
                        logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u5728\u679a\u4e3e\u5b57\u5178\u4e2d\u4e0d\u5b58\u5728");
                        return false;
                    }
                }
            }
            return true;
        }
        catch (Exception e) {
            iCondition.getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u5728\u679a\u4e3e\u5b57\u5178\u4e2d\u6267\u884c\u5931\u8d25" + e.getMessage());
            logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u5728\u679a\u4e3e\u5b57\u5178\u4e2d\u6267\u884c\u5931\u8d25");
            return false;
        }
    }

    private Boolean handleZbEnum(List<EntityData> list, Object value) {
        for (EntityData data : list) {
            if (!(data.getChildren().size() > 0 ? this.handleZbEnum(data.getChildren(), value) == false : data.getCode().equals(value))) continue;
            return false;
        }
        return true;
    }

    private List<String> getFieldDataKeyList(ILExtractCondition iCondition, DataRegionDefine dataRegionDefine) {
        RegionDataFactory factory = new RegionDataFactory();
        RegionData regionData = new RegionData();
        regionData.initialize(dataRegionDefine);
        AbstractRegionRelationEvn regionRelation = factory.createRegionRelationEvn(regionData, this.initContext(iCondition.getEnvContext()));
        List fieldDataList = regionRelation.getBizKeyOrderFields();
        return fieldDataList.size() > 0 ? ((List)fieldDataList.get(0)).stream().map(FieldData::getFieldKey).collect(Collectors.toList()) : new ArrayList<String>();
    }

    private boolean handleZbNotNull(ILExtractCondition iCondition, ILSyncCondition condition, List<String> fieldDataKeyList, Object value) {
        if (value == null && fieldDataKeyList.contains(condition.getFieldDefine().getKey())) {
            iCondition.getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            return false;
        }
        return true;
    }

    private void pushNotSimpleZbValue(MdZbDataEntity mdZbDataEntity, ILSyncCondition condition, ILExtractCondition iCondition, IDataTable dataTable, Map<String, List<MdZbDataEntity>> mdZbType2ZbList, boolean isInputData) throws Exception {
        for (int count = 0; count < dataTable.getCount(); ++count) {
            MdZbDataEntity entity;
            mdZbDataEntity.setAreaRowId(BigDecimal.valueOf(count + 1));
            IDataRow dataRow = dataTable.getItem(count);
            int typeValue = condition.getFieldDefine().getType().getValue();
            if (this.isZbValueN(typeValue).booleanValue()) {
                entity = new MdZbDataEntity(mdZbDataEntity);
                entity.setZbValue_N(dataRow.getValue(condition.getFieldDefine()).getAsCurrency());
                entity.setAreaRowId(BigDecimal.valueOf(count + 1));
                mdZbType2ZbList.get(PUSH_MD_ZB_DATA_LIST).add(entity);
            }
            if (!this.isZbValueT(typeValue).booleanValue()) continue;
            entity = new MdZbDataEntity(mdZbDataEntity);
            String value = dataRow.getValue(condition.getFieldDefine()).getAsString();
            if (isInputData && this.isFieldTypeEnum(condition, iCondition) && value != null && value.indexOf("||") >= 0) {
                entity.setZbValue_T(value.substring(0, value.indexOf("||")));
            } else {
                entity.setZbValue_T(dataRow.getValue(condition.getFieldDefine()).getAsString());
                if (FieldType.FIELD_TYPE_DATE.getValue() == typeValue && !StringUtils.isNull((String)String.valueOf(value))) {
                    if (this.initDateMessagePush(String.valueOf(value)).booleanValue()) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(value));
                        entity.setZbValue_T(new SimpleDateFormat("yyyy.MM.dd").format(date));
                    } else {
                        iCondition.getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + value + "\u4e0d\u6b63\u786e\u3002");
                        logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + value + "\u4e0d\u6b63\u786e\u3002");
                        entity.setZbValue_T("");
                    }
                }
            }
            entity.setAreaRowId(BigDecimal.valueOf(count + 1));
            mdZbType2ZbList.get(PUSH_MD_ZB_DATA_LIST).add(entity);
        }
    }

    private JtableContext initContext(DataEntryContext envContext) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(envContext.getDimensionSet());
        jtableContext.setFormKey(envContext.getFormKey());
        jtableContext.setFormGroupKey(envContext.getFormGroupKey());
        jtableContext.setFormSchemeKey(envContext.getFormSchemeKey());
        jtableContext.setTaskKey(envContext.getTaskKey());
        jtableContext.setFormulaSchemeKey(envContext.getFormulaSchemeKey());
        jtableContext.setVariableMap(envContext.getVariableMap());
        return jtableContext;
    }

    private void extractNotSimpleZbValue(Map<String, List<MdZbDataEntity>> listMap, List<ILSyncCondition> zbValueList, ILHandleZbEntity ilHandleZbEntity, DataRegionDefine dataRegionDefine, boolean isInputData) {
        String fromId = zbValueList.get(0).getFormId();
        BigDecimal maxCount = new BigDecimal(0);
        HashMap<String, Object> fieldId2ValueMap = new HashMap<String, Object>();
        HashMap valueFieldIdMap = new HashMap();
        List<String> fieldKeyList = this.getFieldDataKeyList(ilHandleZbEntity.getiCondition(), dataRegionDefine);
        for (ILSyncCondition condition : zbValueList) {
            fieldId2ValueMap.put(condition.getFieldDefine().getKey(), null);
            fieldId2ValueMap.put("fieldTitle", condition.getFieldDefine().getTitle());
            List<MdZbDataEntity> mdZbDataList = listMap.get(condition.getCode());
            if (CollectionUtils.isEmpty(mdZbDataList)) continue;
            for (MdZbDataEntity mdZbData : mdZbDataList) {
                HashMap<String, Object> valueMap;
                boolean isInsert;
                BigDecimal rowId = mdZbData.getAreaRowId();
                if (rowId.compareTo(maxCount) > 0) {
                    maxCount = rowId;
                }
                int typeValue = condition.getFieldDefine().getType().getValue();
                Object value = null;
                if (this.isZbValueN(typeValue).booleanValue()) {
                    int digits = condition.getFieldDefine().getFractionDigits();
                    value = mdZbData.getZbValue_N() != null ? mdZbData.getZbValue_N().setScale(digits, RoundingMode.HALF_UP) : mdZbData.getZbValue_N();
                }
                if (this.isZbValueT(typeValue).booleanValue()) {
                    value = mdZbData.getZbValue_T();
                }
                if (!(isInsert = this.handleZbNotNull(ilHandleZbEntity.getiCondition(), condition, fieldKeyList, value))) {
                    fieldId2ValueMap.put("isNotInsert", true);
                }
                if (FieldType.FIELD_TYPE_DATE.getValue() == typeValue && !StringUtils.isNull((String)String.valueOf(value))) {
                    if (this.initDateMessage(String.valueOf(value)).booleanValue()) {
                        try {
                            Date date = new SimpleDateFormat("yyyy.MM.dd").parse(String.valueOf(value));
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        }
                        catch (Exception e) {
                            logger.error("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u65f6\u95f4\u8f6c\u6362\u5f02\u5e38\u3002", e);
                            ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u65f6\u95f4\u8f6c\u6362\u5f02\u5e38\u3002");
                        }
                    } else {
                        ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + value + "\u4e0d\u6b63\u786e\u3002(yyyy.MM.dd)");
                        logger.info("\u3010" + condition.getFieldDefine().getTitle() + "\u3011\u6307\u6807\u503c\u65f6\u95f4\u683c\u5f0f" + value + "\u4e0d\u6b63\u786e\u3002");
                        value = null;
                    }
                }
                if (!isInsert && StringUtils.isNull((String)String.valueOf(value))) {
                    if (valueFieldIdMap.get(mdZbData.getAreaRowId().intValue()) != null) {
                        ((Map)valueFieldIdMap.get(mdZbData.getAreaRowId().intValue())).put("isNotInsert", true);
                        continue;
                    }
                    valueMap = new HashMap<String, Object>();
                    valueMap.put("isNotInsert", true);
                    valueFieldIdMap.put(mdZbData.getAreaRowId().intValue(), valueMap);
                    continue;
                }
                if (!StringUtils.isNull((String)String.valueOf(value)) && isInputData && this.isFieldTypeEnum(condition, ilHandleZbEntity.getiCondition()) || this.handleZbCondition(condition, value, ilHandleZbEntity.getiCondition()).booleanValue()) {
                    if (valueFieldIdMap.get(mdZbData.getAreaRowId().intValue()) != null) {
                        ((Map)valueFieldIdMap.get(mdZbData.getAreaRowId().intValue())).put(condition.getFieldDefine().getKey(), value);
                        continue;
                    }
                    valueMap = new HashMap();
                    valueMap.put(condition.getFieldDefine().getKey(), value);
                    valueFieldIdMap.put(mdZbData.getAreaRowId().intValue(), valueMap);
                    continue;
                }
                if (valueFieldIdMap.get(mdZbData.getAreaRowId().intValue()) != null) {
                    ((Map)valueFieldIdMap.get(mdZbData.getAreaRowId().intValue())).put(condition.getFieldDefine().getKey(), null);
                    continue;
                }
                valueMap = new HashMap();
                valueMap.put(condition.getFieldDefine().getKey(), null);
                valueFieldIdMap.put(mdZbData.getAreaRowId().intValue(), valueMap);
            }
        }
        int index = maxCount.intValue();
        int floatZbRow = ilHandleZbEntity.getLogger2ValueMap().get("floatZbRow");
        ilHandleZbEntity.getLogger2ValueMap().put("floatZbRow", floatZbRow += index);
        if (listMap.isEmpty()) {
            logger.info("\u3010" + dataRegionDefine.getTitle() + "\u3011\u533a\u57df\u63d0\u53d6\u7684\u6570\u636e\u4e3a\u7a7a");
            ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u3010" + dataRegionDefine.getTitle() + "\u3011\u533a\u57df\u63d0\u53d6\u7684\u6570\u636e\u4e3a\u7a7a");
            return;
        }
        int floatZbSuccessRow = ilHandleZbEntity.getLogger2ValueMap().get("floatZbSuccessRow");
        int floatZbNewRow = 0;
        for (int count = 1; count <= index; ++count) {
            if (valueFieldIdMap.get(count) != null && ((Map)valueFieldIdMap.get(count)).get("isNotInsert") == null) {
                boolean isSuccess = this.inputNewData((Map)valueFieldIdMap.get(count), fromId, ilHandleZbEntity.getiCondition(), dataRegionDefine.getKey(), ++floatZbNewRow);
                if (!isSuccess) continue;
                ++floatZbSuccessRow;
                continue;
            }
            if (valueFieldIdMap.get(count) != null || fieldId2ValueMap.get("isNotInsert") == null) continue;
            this.inputNewData(fieldId2ValueMap, fromId, ilHandleZbEntity.getiCondition(), dataRegionDefine.getKey(), floatZbNewRow);
            logger.info("\u3010" + fieldId2ValueMap.get("fieldTitle") + "\u3011\u6307\u6807\u503c\u63d0\u53d6\u7684\u6570\u636e\u4e3a\u7a7a");
        }
        ilHandleZbEntity.getLogger2ValueMap().put("floatZbSuccessRow", floatZbSuccessRow);
    }

    private Boolean initDateMessagePush(String dateStr) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.setLenient(false);
            format.parse(dateStr);
        }
        catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    private Boolean initDateMessage(String dateStr) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            format.setLenient(false);
            format.parse(dateStr);
        }
        catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    private boolean inputNewData(Map<String, Object> valueFieldIdMap, String fromId, ILExtractCondition iCondition, String regionKey, int floatZbNewRow) {
        ReportDataCommitSet reportDataCommitSet = new ReportDataCommitSet();
        iCondition.getEnvContext().setFormKey(fromId);
        JtableContext context = this.initContext(iCondition.getEnvContext());
        reportDataCommitSet.setContext(context);
        HashMap<String, RegionDataCommitSet> commitData = new HashMap<String, RegionDataCommitSet>();
        RegionDataCommitSet set = new RegionDataCommitSet();
        JtableContext setContext = new JtableContext(context);
        set.setContext(setContext);
        ArrayList<String> title = new ArrayList<String>();
        set.getCells().put(regionKey, title);
        ArrayList<List<Object>> data = new ArrayList<List<Object>>();
        set.getNewdata().add(data);
        title.add("ID");
        String id = UUIDUtils.newUUIDStr();
        data.add(Arrays.asList(id, id));
        title.add("FLOATORDER");
        data.add(Arrays.asList(floatZbNewRow, floatZbNewRow));
        try {
            List<GcOppUnitFieldVO> voList = this.initOppUnitField(iCondition.getEnvContext(), regionKey, valueFieldIdMap, fromId, iCondition);
            if (voList == null) {
                return false;
            }
            voList.forEach(f -> {
                title.add(f.getLinkKey());
                data.add(Arrays.asList(f.getRealValue(), f.getRealValue()));
            });
            commitData.put(regionKey, set);
            reportDataCommitSet.setCommitData(commitData);
            this.iJtableResourceService.saveReportData(reportDataCommitSet);
        }
        catch (SaveDataException e) {
            List infoList = e.getSaveResult().getErrors();
            for (SaveErrorDataInfo info : infoList) {
                iCondition.getLibraryMessages().add(info.getDataError().getErrorInfo());
                logger.info(info.getDataError().getErrorInfo());
            }
            return false;
        }
        catch (FieldDataErrorException e) {
            iCondition.getLibraryMessages().add(Arrays.toString(e.getDatas()));
            logger.info(Arrays.toString(e.getDatas()));
            return false;
        }
        return true;
    }

    private List<GcOppUnitFieldVO> initOppUnitField(DataEntryContext context, String regionDefineKey, Map<String, Object> valueFieldIdMap, String fromId, ILExtractCondition iCondition) {
        Grid2Data griddata;
        ArrayList<GcOppUnitFieldVO> linkFields = new ArrayList<GcOppUnitFieldVO>();
        FormDefine formDefine = this.iRunTimeViewController.queryFormById(fromId);
        if (formDefine.getBinaryData() != null) {
            griddata = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
        } else {
            griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
        }
        List allLinkDefines = this.iRunTimeViewController.getAllLinksInRegion(regionDefineKey);
        for (DataLinkDefine dataLinkDefine : allLinkDefines) {
            if (dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0) continue;
            GcOppUnitFieldVO vo = new GcOppUnitFieldVO();
            linkFields.add(vo);
            GridCellData cell = griddata.getGridCellData(dataLinkDefine.getPosX(), dataLinkDefine.getPosY());
            if (cell != null) {
                vo.setCanEdit(cell.isEditable());
            }
            FieldDefine fieldDefine = null;
            try {
                String fieldId = dataLinkDefine.getLinkExpression();
                if (fieldId != null) {
                    fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldId);
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u6307\u6807\u5f02\u5e38", e);
            }
            if (fieldDefine == null) {
                logger.error("\u5355\u5143\u683c" + dataLinkDefine.getKey() + "\u5173\u8054\u7684\u6307\u6807\u672a\u627e\u5230" + dataLinkDefine.getLinkExpression());
                String[] i18Args = new String[]{dataLinkDefine.getKey(), dataLinkDefine.getLinkExpression()};
                iCondition.getLibraryMessages().add("\u5355\u5143\u683c" + dataLinkDefine.getKey() + "\u5173\u8054\u7684\u6307\u6807\u672a\u627e\u5230" + dataLinkDefine.getLinkExpression());
                return null;
            }
            vo.setValue(IntermediateLibraryServiceImpl.toViewString(valueFieldIdMap.get(fieldDefine.getKey())));
            vo.setFieldKey(fieldDefine.getKey());
            vo.setFieldCode(fieldDefine.getCode());
            vo.setFieldTitle(fieldDefine.getTitle());
            vo.setFieldType(DataInputTypeEnum.typeOf((FieldType)fieldDefine.getType()));
            vo.setFieldsize(fieldDefine.getSize().intValue());
            vo.setTaskKey(context.getTaskKey());
            vo.setFormKey(fromId);
            vo.setRegionKey(regionDefineKey);
            vo.setLinkKey(dataLinkDefine.getKey());
            vo.setRow(dataLinkDefine.getPosY());
            vo.setCol(dataLinkDefine.getPosX());
            vo.setTitle(dataLinkDefine.getTitle());
            vo.setFormat(DataLinkStyleUtil.getOtherLinkStyle((DataLinkDefine)dataLinkDefine, (FieldDefine)fieldDefine));
            EntityViewDefine view = this.iRunTimeViewController.getViewByLinkDefineKey(dataLinkDefine.getKey());
            TableModelDefine table = null;
            if (view == null) continue;
            try {
                table = this.metaService.getTableModel(view.getEntityId());
                vo.setEntityKey(view.getEntityId());
                vo.setFieldType(DataInputTypeEnum.ENTITY);
                vo.setEntityTitle("");
                vo.setEntityCode(table.getName());
            }
            catch (Exception exception) {}
        }
        return linkFields.stream().sorted(Comparator.comparing(GcOppUnitFieldVO::getCol)).collect(Collectors.toList());
    }

    public static String toViewString(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Double) {
            return new DecimalFormat("#0.00").format((Double)value);
        }
        return String.valueOf(value);
    }

    private void getFormPower(ILExtractCondition iCondition, Map<String, String> formId2Desc) {
        FormReadWriteAccessData formData = this.formReadWritePower(iCondition.getEnvContext(), iCondition.getEnvContext().getDimensionSet(), iCondition.getFormIdList());
        iCondition.getFormIdList().forEach(formId -> {
            String errorMessage = formData.getOneFormKeyReason(formId);
            if (!StringUtils.isEmpty((String)errorMessage) && !"\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equals(errorMessage)) {
                formId2Desc.put((String)formId, errorMessage);
            }
        });
    }

    private FormReadWriteAccessData formReadWritePower(DataEntryContext envContext, Map<String, DimensionValue> dimensionSet, List<String> formIdList) {
        ReadWriteAccessCacheManager accessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        JtableContext readWriteAccessContext = new JtableContext();
        readWriteAccessContext.setTaskKey(envContext.getTaskKey());
        readWriteAccessContext.setFormSchemeKey(envContext.getFormSchemeKey());
        readWriteAccessContext.setFormulaSchemeKey(envContext.getFormulaSchemeKey());
        readWriteAccessContext.setDimensionSet(dimensionSet);
        readWriteAccessContext.setVariableMap(envContext.getVariableMap());
        ArrayList<String> formIds = new ArrayList<String>(formIdList);
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(readWriteAccessContext, formIds, Consts.FormAccessLevel.FORM_DATA_WRITE);
        accessCacheManager.initCache(readWriteAccessCacheParams);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(envContext.getTaskKey());
        jtableContext.setFormSchemeKey(envContext.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(envContext.getFormulaSchemeKey());
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setVariableMap(envContext.getVariableMap());
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, Consts.FormAccessLevel.FORM_DATA_WRITE, formIds);
        return this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, accessCacheManager);
    }

    private ReadWriteAccessDesc unitWriteable(ILExtractCondition iCondition) {
        return this.writeableMessage(this.getUnitWriteable(iCondition.getEnvContext().getDimensionSet(), iCondition.getEnvContext()));
    }

    private boolean getFormLocked(ILHandleZbEntity ilHandleZbEntity, String formId) {
        boolean isFormLocked = this.isFormLocked(ilHandleZbEntity.getiCondition().getEnvContext(), formId);
        if (isFormLocked) {
            ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u8868\u5355\u3010" + this.iRunTimeViewController.queryFormById(formId).getTitle() + "\u3011\u5df2\u9501\u5b9a\uff0c\u4e0d\u80fd\u751f\u6210\u8bb0\u5f55\u3002");
            return false;
        }
        boolean canWriteForm = this.authoritryProvider.canWriteForm(formId);
        if (!canWriteForm) {
            ilHandleZbEntity.getiCondition().getLibraryMessages().add("\u8868\u5355\u3010" + this.iRunTimeViewController.queryFormById(formId).getTitle() + "\u3011\u5f53\u524d\u7528\u6237\u6ca1\u6709\u5199\u6743\u9650\u3002");
            return false;
        }
        return true;
    }

    private void zbNoPower(ILHandleZbEntity ilHandleZbEntity, Map<String, String> formId2Desc) {
        if (ilHandleZbEntity.getPushType().booleanValue()) {
            return;
        }
        List<FormLockBatchReadWriteResult> formLockBatchReadWriteResultList = this.allFormLockMessage(ilHandleZbEntity.getiCondition().getEnvContext());
        formLockBatchReadWriteResultList.forEach(formLockResult -> {
            if (formLockResult.isLock() && !formId2Desc.containsKey(formLockResult.getFormKey())) {
                formId2Desc.put(formLockResult.getFormKey(), "\u8868\u5355\u3010" + this.iRunTimeViewController.queryFormById(formLockResult.getFormKey()).getTitle() + "\u3011\u5df2\u9501\u5b9a\uff0c\u4e0d\u80fd\u751f\u6210\u8bb0\u5f55\u3002");
            }
        });
        ilHandleZbEntity.getiCondition().getFormIdList().forEach(formId -> {
            boolean formPower;
            if (!formId2Desc.containsKey(formId) && !(formPower = this.authoritryProvider.canWriteForm(formId))) {
                formId2Desc.put((String)formId, "\u8868\u5355\u3010" + this.iRunTimeViewController.queryFormById(formId).getTitle() + "\u3011\u5f53\u524d\u7528\u6237\u6ca1\u6709\u5199\u6743\u9650\u3002");
            }
        });
    }

    public Map<String, String> getFormState(ILExtractCondition iCondition, DimensionValueSet dimensionValueSet) {
        HashMap<String, String> formId2Desc = new HashMap<String, String>();
        ArrayList<String> formIds = new ArrayList<String>(iCondition.getFormIdList());
        List<UploadState> uploadStates = this.queryUploadState(iCondition.getEnvContext(), formIds, dimensionValueSet);
        iCondition.getFormIdList().forEach(formId -> {
            if (!this.writeableMessage((UploadState)uploadStates.get(iCondition.getFormIdList().indexOf(formId))).getAble().booleanValue()) {
                formId2Desc.put((String)formId, this.writeableMessage((UploadState)uploadStates.get(iCondition.getFormIdList().indexOf(formId))).getDesc());
            }
        });
        return formId2Desc;
    }

    private ReadWriteAccessDesc writeableMessage(UploadState status) {
        boolean writeable = true;
        String unwriteableDesc = "";
        if (status == UploadState.SUBMITED) {
            writeable = false;
            unwriteableDesc = "\u6570\u636e\u5df2\u9001\u5ba1\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c";
        }
        if (status == UploadState.UPLOADED) {
            writeable = false;
            unwriteableDesc = "\u6570\u636e\u5df2\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c";
        }
        if (status == UploadState.CONFIRMED) {
            writeable = false;
            unwriteableDesc = "\u6570\u636e\u5df2\u786e\u8ba4\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c";
        }
        return new ReadWriteAccessDesc(Boolean.valueOf(writeable), unwriteableDesc);
    }

    private List<UploadState> queryUploadState(DataEntryContext envContext, List<String> formIds, DimensionValueSet dimensionValueSet) {
        FormSchemeDefine formScheme;
        IRunTimeViewController authViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            formScheme = authViewController.getFormScheme(envContext.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25", e);
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.form.scheme.error"));
        }
        IBatchQueryUploadStateService batchQueryUploadStateService = (IBatchQueryUploadStateService)SpringContextUtils.getBean(IBatchQueryUploadStateService.class);
        List uploadStateNewList = batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, null);
        if (CollectionUtils.isEmpty((Collection)uploadStateNewList)) {
            return formIds.stream().map(item -> UploadState.ORIGINAL).collect(Collectors.toList());
        }
        Map<String, UploadState> formId2UploadStateMap = uploadStateNewList.stream().collect(Collectors.toMap(UploadStateNew::getFormId, uploadStateNew -> {
            ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
            if (null == actionStateBean) {
                return UploadState.ORIGINAL;
            }
            return UploadState.valueOf((String)actionStateBean.getCode());
        }));
        IWorkflow iWorkflow = (IWorkflow)SpringContextUtils.getBean(IWorkflow.class);
        WorkFlowType workFlowType = iWorkflow.queryStartType(envContext.getFormSchemeKey());
        if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            uploadStateNewList.stream().forEach(item -> {
                try {
                    List formDefines = iRunTimeViewController.getAllFormsInGroup(item.getFormId());
                    if (!CollectionUtils.isEmpty((Collection)formDefines)) {
                        formDefines.stream().forEach(formDefine -> formId2UploadStateMap.put(formDefine.getKey(), (UploadState)formId2UploadStateMap.get(item.getFormId())));
                    }
                }
                catch (Exception e) {
                    logger.error("\u6839\u636e\u5206\u7ec4\u83b7\u53d6\u62a5\u8868\u5f02\u5e38\u3002", e);
                    throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.intermediate.group.form"));
                }
            });
        }
        return formIds.stream().map(formId -> {
            UploadState uploadState = (UploadState)formId2UploadStateMap.get(formId);
            return null == uploadState ? UploadState.ORIGINAL : uploadState;
        }).collect(Collectors.toList());
    }

    private boolean isFormLocked(DataEntryContext envContext, String formId) {
        JtableContext jtableContext = this.convert2JtableContext(envContext);
        FormLockParam lockParam = new FormLockParam();
        lockParam.setContext(jtableContext);
        jtableContext.setFormKey(formId);
        IFormLockService formLockService = (IFormLockService)SpringContextUtils.getBean(IFormLockService.class);
        return formLockService.isFormLocked(lockParam);
    }

    private List<FormLockBatchReadWriteResult> allFormLockMessage(DataEntryContext envContext) {
        IFormLockService formLockService = (IFormLockService)SpringContextUtils.getBean(IFormLockService.class);
        return formLockService.batchDimension(this.convert2JtableContext(envContext));
    }

    private JtableContext convert2JtableContext(DataEntryContext envContext) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(envContext.getDimensionSet());
        jtableContext.setFormSchemeKey(envContext.getFormSchemeKey());
        jtableContext.setTaskKey(envContext.getTaskKey());
        return jtableContext;
    }

    public UploadState getUnitWriteable(Map<String, DimensionValue> dimensionSet, DataEntryContext envContext) {
        PageVO list;
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>(16);
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(dimensionSet.get("DATATIME").getValue());
        dimensionSetMap.put("DATATIME", dimensionValue);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(envContext.getFormSchemeKey());
        dimensionValue = new DimensionValue();
        dimensionValue.setName(dwEntity.getDimensionName());
        dimensionValue.setValue(dimensionSet.get(dwEntity.getDimensionName()).getValue());
        dimensionSetMap.put(dwEntity.getDimensionName(), dimensionValue);
        BaseDataDTO vaBaseDataDTO = new BaseDataDTO();
        vaBaseDataDTO.setTableName("MD_CURRENCY");
        vaBaseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
        if (dimensionSet.get("MD_CURRENCY") != null) {
            vaBaseDataDTO.setCode(dimensionSet.get("MD_CURRENCY").getValue());
        }
        if ((list = this.baseDataClient.list(vaBaseDataDTO)) != null && !list.getRows().isEmpty()) {
            dimensionValue = new DimensionValue();
            dimensionValue.setName("MD_CURRENCY");
            dimensionValue.setValue(((BaseDataDO)list.getRows().get(0)).getId().toString());
            dimensionSetMap.put("MD_CURRENCY", dimensionValue);
        }
        if (dimensionSet.get("MD_GCORGTYPE") != null) {
            dimensionValue = new DimensionValue();
            dimensionValue.setName("MD_GCORGTYPE");
            dimensionValue.setValue(dimensionSet.get("MD_GCORGTYPE").getValue());
            dimensionSetMap.put("MD_GCORGTYPE", dimensionValue);
        }
        return this.queryUnitUploadState(envContext.getFormSchemeKey(), DimensionValueSetUtil.getDimensionValueSet(dimensionSetMap));
    }

    private UploadState queryUnitUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setDim(dimensionValueSet);
        dataEntryParam.setFormSchemeKey(formSchemeKey);
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        IFormConditionService formConditionService = (IFormConditionService)BeanUtil.getBean(IFormConditionService.class);
        IConditionCache conditionCache = formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        dataEntryParam.setFormKeys(conditionCache.getSeeForms(dimensionValueSet));
        dataEntryParam.setGroupKeys(conditionCache.getSeeFormGroups(dimensionValueSet));
        ActionState uploadState = dataFlowService.queryState(dataEntryParam);
        if (null == uploadState || null == uploadState.getUnitState()) {
            return UploadState.ORIGINAL;
        }
        return UploadState.valueOf((String)uploadState.getUnitState().getCode());
    }

    private IDataTable getZbValueSimpleList(ILHandleZbEntity ilHandleZbEntity, List<FieldDefine> fieldDefineList, String regionKey) throws Exception {
        QueryEnvironment environment = new QueryEnvironment();
        environment.setFormSchemeKey(ilHandleZbEntity.getiCondition().getEnvContext().getFormSchemeKey());
        environment.setRegionKey(regionKey);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(environment);
        fieldDefineList.forEach(arg_0 -> ((IDataQuery)dataQuery).addColumn(arg_0));
        dataQuery.setMasterKeys(ilHandleZbEntity.getDimensionValueSet());
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        return dataQuery.executeQuery(context);
    }

    private IDataTable getZbValueList(ILHandleZbEntity ilHandleZbEntity, List<FieldDefine> fieldDefineList, String regionKey, String formId) throws Exception {
        FormDefine formDefine = this.iRunTimeViewController.queryFormById(formId);
        QueryEnvironment environment = new QueryEnvironment();
        environment.setRegionKey(regionKey);
        environment.setFormCode(formDefine.getFormCode());
        environment.setFormKey(formDefine.getKey());
        environment.setFormSchemeKey(ilHandleZbEntity.getiCondition().getEnvContext().getFormSchemeKey());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(environment);
        fieldDefineList.forEach(arg_0 -> ((IDataQuery)dataQuery).addColumn(arg_0));
        dataQuery.setMasterKeys(ilHandleZbEntity.getDimensionValueSet());
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        return dataQuery.executeQuery(context);
    }

    public static DimensionValueSet getDimensionValueSet(String dimensionName, Map<String, DimensionValue> dimensionSet, String orgId) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (dimensionName.equals(value.getName())) {
                dimensionValueSet.setValue(value.getName(), (Object)orgId);
                continue;
            }
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    private Map<String, Map<String, List<ILSyncCondition>>> filterZbData(ILExtractCondition iCondition, Map<String, Integer> logger2ValueMap) throws Exception {
        HashMap<String, Map<String, List<ILSyncCondition>>> regionTypeToFileIdMap = new HashMap<String, Map<String, List<ILSyncCondition>>>();
        HashMap regionSimpleToFileIdMap = new HashMap();
        HashMap regionNoSimpleToFileIdMap = new HashMap();
        List<ILFieldVO> fieldVoList = this.intermediateLibraryDao.getFieldOfProgrammeId(iCondition.getProgrammeId());
        List programmeFileIdList = fieldVoList.stream().map(ILFieldVO::getFieldId).collect(Collectors.toList());
        int simpleZbCount = 0;
        int floatZbCount = 0;
        HashSet<String> fromIdSet = new HashSet<String>();
        ArrayList<String> formIdList = new ArrayList<String>();
        for (String formId : iCondition.getFormIdList()) {
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(formId);
            if (formDefine == null) continue;
            formIdList.add(formId);
            List dataRegionDefines = this.iRunTimeViewController.getAllRegionsInForm(formId);
            for (DataRegionDefine define : dataRegionDefines) {
                List fieldIdList = this.iRunTimeViewController.getFieldKeysInRegion(define.getKey());
                HashSet fieldIdSet = new HashSet(fieldIdList);
                List accountIdList = programmeFileIdList.stream().filter(fieldIdSet::contains).collect(Collectors.toList());
                ArrayList<ILSyncCondition> fieldConditionList = new ArrayList<ILSyncCondition>();
                for (String fileId : accountIdList) {
                    TableDefine tableDefine;
                    FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fileId);
                    try {
                        tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                    }
                    catch (Exception e) {
                        iCondition.getLibraryMessages().add("\u83b7\u53d6\u6307\u6807\u3010" + fieldDefine.getCode() + "\u3011\u7684\u5b58\u50a8\u8868\u5931\u8d25\u3002");
                        continue;
                    }
                    ILSyncCondition condition = new ILSyncCondition();
                    condition.setCode(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]");
                    condition.setFieldDefine(fieldDefine);
                    condition.setFormId(formId);
                    condition.setDataRegionDefine(define);
                    condition.setTableDefine(tableDefine);
                    fieldConditionList.add(condition);
                }
                if (fieldConditionList.isEmpty()) continue;
                if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)define.getRegionKind())) {
                    regionSimpleToFileIdMap.put(define.getKey(), fieldConditionList);
                    simpleZbCount += fieldConditionList.size();
                    continue;
                }
                regionNoSimpleToFileIdMap.put(define.getKey(), fieldConditionList);
                floatZbCount += fieldConditionList.size();
                fromIdSet.add(formId);
            }
        }
        iCondition.setFormIdList(formIdList);
        regionTypeToFileIdMap.put("REGION_SIMPLE", regionSimpleToFileIdMap);
        regionTypeToFileIdMap.put("REGION_NO_SIMPLE", regionNoSimpleToFileIdMap);
        logger2ValueMap.put("simpleZbCount", simpleZbCount);
        logger2ValueMap.put("floatZbCount", floatZbCount);
        logger2ValueMap.put("floatTableCount", fromIdSet.size());
        if (regionSimpleToFileIdMap.isEmpty()) {
            logger2ValueMap.put("simpleTableCount", 0);
        } else {
            logger2ValueMap.put("simpleTableCount", iCondition.getFormIdList().size() - fromIdSet.size());
        }
        return regionTypeToFileIdMap;
    }

    @Override
    public Set<String> filterOrgId(ILExtractCondition iCondition, ILEntity entity) {
        Set<String> orgIdSet = new HashSet<String>();
        List<ILOrgEntity> iLOrgEntityList = this.intermediateLibraryDao.getAllOrgIdForProgrammeId(entity.getId());
        Set ilOrgIdList = iLOrgEntityList.stream().map(ILOrgEntity::getOrgId).collect(Collectors.toSet());
        String orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)iCondition.getEnvContext().getTaskKey());
        YearPeriodObject yp = new YearPeriodObject(iCondition.getEnvContext().getFormSchemeKey(), ((DimensionValue)iCondition.getEnvContext().getDimensionSet().get("DATATIME")).getValue());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        if (!iLOrgEntityList.isEmpty()) {
            if (Boolean.parseBoolean(iCondition.getIsAllOrgChoose())) {
                List gcOrgCacheVOTree = tool.getOrgTree();
                for (GcOrgCacheVO node : gcOrgCacheVOTree) {
                    List gcOrgCacheVOList = tool.listAllOrgByParentIdContainsSelf(node.getKey());
                    for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheVOList) {
                        if (!ilOrgIdList.contains(gcOrgCacheVO.getCode())) continue;
                        orgIdSet.add(gcOrgCacheVO.getCode());
                    }
                }
            } else {
                orgIdSet = iCondition.getOrgIdList().stream().filter(ilOrgIdList::contains).collect(Collectors.toSet());
            }
        } else {
            iCondition.getLibraryMessages().add("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u65b9\u6848\u672a\u9009\u62e9\u5355\u4f4d\u3002");
        }
        return orgIdSet;
    }
}

