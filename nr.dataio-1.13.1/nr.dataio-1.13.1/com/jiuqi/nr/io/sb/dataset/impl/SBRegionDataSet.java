/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fielddatacrud.ISBActuator
 *  com.jiuqi.nr.fielddatacrud.ImportInfo
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  net.coobird.thumbnailator.Thumbnails
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.sb.dataset.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.io.common.DataRowReaderImpl;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.datacheck.TransferData;
import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.datacheck.param.TransferSource;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.ImportResult;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.ImportMode;
import com.jiuqi.nr.io.sb.JIOSBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorFactory;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.service.IDataTransfer;
import com.jiuqi.nr.io.service.IDataTransferProvider;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.service.MzOrgCodeRepairService;
import com.jiuqi.nr.io.service.impl.DataAuthReadable;
import com.jiuqi.nr.io.service.impl.ParameterService;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.util.ParamUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public class SBRegionDataSet
extends AbstractRegionDataSet {
    private static final Logger log = LoggerFactory.getLogger(SBRegionDataSet.class);
    private RegionData regionData = null;
    private TableContext tableContext;
    private List<FieldDefine> listFieldDefine = null;
    private List<FieldDefine> bizKeyFieldDef = new ArrayList<FieldDefine>();
    private List<Integer> bizindex = new ArrayList<Integer>();
    private Map<String, FieldDefine> bizKeyDimNameMap = new HashMap<String, FieldDefine>();
    private Map<String, String> enumDatakeys = new HashMap<String, String>();
    private Map<String, IEntityTable> enumDataValues = new HashMap<String, IEntityTable>();
    private Map<String, FieldDefine> fielddefineMap = new HashMap<String, FieldDefine>();
    private Map<String, DataField> dataFields = new HashMap<String, DataField>();
    private List<FieldDefine> fieldRowList = new ArrayList<FieldDefine>();
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IDataAssist dataAssist;
    private IDataAccessProvider dataAccessProvider;
    private ISBActuator sbImportActuator;
    private SBImportActuatorFactory sbImportActuatorFactory;
    private IoEntityService ioEntityService;
    private FileService fileService;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private Map<String, String> zbDimMap = new HashMap<String, String>();
    private MzOrgCodeRepairService mzOrgCodeRepairService;
    private int dwIndex = -1;
    private int datatimeIndex = -1;
    private FormSchemeDefine formSchemeDefineImport;
    private NrIoProperties nrIoProperties;
    private Map<String, DataLinkDefine> enumDatakeyLink = new HashMap<String, DataLinkDefine>();
    private IDataTransferProvider dataTransferProvider;
    private IDataTransfer dataTransfer;
    private String dataSchemeKey;
    private Map<String, String> mzOrgCodeRepairCache;
    private IoQualifier ioQualifier;
    private DataEngineService dataEngineService;
    private IExecutorContextFactory executorContextFactory;
    private RegionRelationFactory regionRelationFactory;
    private Set<DimensionValueSet> access = new HashSet<DimensionValueSet>();
    private Set<DimensionValueSet> noAccess = new HashSet<DimensionValueSet>();
    private IReadonlyTable dataTable;
    private IDataQuery dataQuery;
    private JdbcTemplate jdbcTemplate;
    private IEntityMetaService entityMetaService;
    private DataAccesslUtil dataAccesslUtil;
    private IEntityMetaService iEntityMetaService;
    private DimensionProviderFactory dimensionProviderFactory;
    private DataAuthReadable dataAuthReadable;
    private DataRegionDefine dataRegionDefine;
    private AttachmentIOService attachmentIOService;
    private MultistageUnitReplace multistageUnitReplace;
    private boolean executed = false;
    private final Set<String> tableKeys = new HashSet<String>();
    private final List<FileInfo> attamentFiles = new ArrayList<FileInfo>();
    private final Map<String, String> dataTableMap = new HashMap<String, String>();
    private final Map<String, String> dwCurrencyRel = new HashMap<String, String>();
    private final Map<String, String> rowDimension = new HashMap<String, String>();
    private Map<String, Set<String>> unImport = new HashMap<String, Set<String>>();
    private FieldDefine floatOrder = null;
    private com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext1 = null;
    private String dwDimensionName = "";
    private String fieldBizKeys = "";
    private int totalCount = -1;
    private int pageSize = 20000;
    private int pageIndex = 0;
    private int nextCount = -1;
    private int orgSize = 100;
    private int orgIndex = 1;
    private Queue<String> orgList = new LinkedList<String>();
    private List<String> currOrgList = new ArrayList<String>();
    private DimensionValueSet accessDim = null;
    private DimensionValueSet currOrgsAccessDim = null;
    private boolean includeDataTime = false;
    private int importRowSize = 0;
    private int unImportRowSize = 0;
    Map<String, Set<Object>> amendFieldValues = new HashMap<String, Set<Object>>();

    public SBRegionDataSet(TableContext context, RegionData regionData) {
        this.tableContext = context.clone();
        this.regionData = regionData;
        ParameterService parameterService = ParamUtil.getParameterService();
        this.nrIoProperties = parameterService.getNrIoProperties();
        this.pageSize = this.nrIoProperties.getPageSize();
        this.orgSize = this.nrIoProperties.getOrgSize();
        this.nextCount = -1;
        this.entityMetaService = parameterService.getEntityMetaService();
        this.runTimeViewController = parameterService.getRunTimeViewController();
        this.dataDefinitionRuntimeController = parameterService.getDataDefinitionRuntimeController();
        this.dataAccessProvider = parameterService.getDataAccessProvider();
        this.entityViewRunTimeController = parameterService.getEntityViewRunTimeController();
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(this.tableContext));
        this.fileService = parameterService.getFileService();
        this.jdbcTemplate = parameterService.getJdbcTemplate();
        this.ioEntityService = parameterService.getIoEntityService();
        this.iEntityMetaService = parameterService.getEntityMetaService();
        this.runtimeDataSchemeService = parameterService.getRuntimeDataSchemeService();
        this.multistageUnitReplace = this.tableContext.getMultistageUnitReplace();
        this.dimensionProviderFactory = (DimensionProviderFactory)BeanUtil.getBean(DimensionProviderFactory.class);
        this.dataAccesslUtil = (DataAccesslUtil)BeanUtil.getBean(DataAccesslUtil.class);
        this.attachmentIOService = parameterService.getAttachmentIOService();
        this.dataAuthReadable = parameterService.getDataAuthReadable();
        this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
        this.resetDimensionValueSet();
        this.listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(regionData.getKey()));
        this.initLinkViewMap(this.tableContext, regionData);
        QueryEnvironment environment = new QueryEnvironment();
        environment.setRegionKey(regionData.getKey());
        environment.setFormCode(regionData.getFormCode());
        environment.setFormKey(regionData.getFormKey());
        environment.setFormSchemeKey(this.tableContext.getFormSchemeKey());
        this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
        if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable()) {
            this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
        }
        String tableKey = null;
        if (null != this.listFieldDefine && !this.listFieldDefine.isEmpty()) {
            tableKey = this.listFieldDefine.get(0).getOwnerTableKey();
            for (FieldDefine fieldDefine : this.listFieldDefine) {
                this.tableKeys.add(fieldDefine.getOwnerTableKey());
            }
        }
        for (String string : this.tableKeys) {
            try {
                List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(string);
                if (deployInfoByDataTableKey == null || deployInfoByDataTableKey.isEmpty()) continue;
                this.dataTableMap.put(string, ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName());
                if (this.dataSchemeKey != null) continue;
                this.dataSchemeKey = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getDataSchemeKey();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        this.listFieldDefine.sort((o1, o2) -> {
            int i = o1.getCode().compareTo(o2.getCode());
            if (i == 0) {
                return o1.getCode().compareTo(o2.getCode());
            }
            return i;
        });
        ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
        this.getBizKeysDef(tableKey, queryFieldDefine, false);
        block4: for (FieldDefine item : queryFieldDefine) {
            for (FieldDefine it : this.listFieldDefine) {
                if (!it.getKey().equals(item.getKey())) continue;
                this.listFieldDefine.remove(it);
                continue block4;
            }
        }
        this.initDataQuerys(regionData, queryFieldDefine);
        this.listFieldDefine = queryFieldDefine;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(this.tableContext.getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formSchemeDefine.getDw());
        Object mdOrg = this.tableContext.getDimensionSet().getValue(queryEntity.getDimensionName());
        if (mdOrg instanceof List) {
            this.orgList.addAll((List)mdOrg);
        } else {
            this.orgList.add((String)mdOrg);
        }
    }

    public SBRegionDataSet(TableContext context, RegionData regionData, List<ExportFieldDefine> originalImportDefine) {
        List deployInfoByDataTableKey;
        this.tableContext = context;
        this.regionData = regionData;
        ArrayList<ExportFieldDefine> importDefine = new ArrayList<ExportFieldDefine>(originalImportDefine);
        ParameterService parameterService = ParamUtil.getParameterService();
        this.runTimeViewController = parameterService.getRunTimeViewController();
        this.dataDefinitionRuntimeController = parameterService.getDataDefinitionRuntimeController();
        this.entityViewRunTimeController = parameterService.getEntityViewRunTimeController();
        this.dataAccessProvider = parameterService.getDataAccessProvider();
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(context));
        this.sbImportActuatorFactory = (SBImportActuatorFactory)BeanUtil.getBean(SBImportActuatorFactory.class);
        this.ioEntityService = parameterService.getIoEntityService();
        this.fileService = parameterService.getFileService();
        this.runtimeDataSchemeService = parameterService.getRuntimeDataSchemeService();
        this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
        this.nrIoProperties = parameterService.getNrIoProperties();
        this.ioQualifier = context.getIoQualifier();
        this.dataEngineService = (DataEngineService)BeanUtil.getBean(DataEngineService.class);
        this.executorContextFactory = (IExecutorContextFactory)BeanUtil.getBean(IExecutorContextFactory.class);
        this.regionRelationFactory = (RegionRelationFactory)BeanUtil.getBean(RegionRelationFactory.class);
        this.dataTransferProvider = (IDataTransferProvider)BeanUtil.getBean(IDataTransferProvider.class);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(this.tableContext.getFormSchemeKey());
        TransferParam param = new TransferParam();
        param.setRegionKey(regionData.getKey());
        param.setTaskKey(formScheme.getTaskKey());
        param.setTableContext(this.tableContext);
        param.setExecutorContext((com.jiuqi.np.dataengine.executors.ExecutorContext)this.getNrExecutorContext(context));
        this.dataTransfer = this.dataTransferProvider.getDataTransfer(param);
        JIOSBImportActuatorConfig cfg = new JIOSBImportActuatorConfig(true);
        this.listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(regionData.getKey()));
        String tableKey = null;
        if (null != this.listFieldDefine && this.listFieldDefine.size() > 0 && (deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey = this.listFieldDefine.get(0).getOwnerTableKey())) != null && !deployInfoByDataTableKey.isEmpty()) {
            this.dataSchemeKey = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getDataSchemeKey();
        }
        ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
        this.getBizKeysDef(tableKey, queryFieldDefine, true);
        ArrayList<DataField> listField = new ArrayList<DataField>();
        String tableName = null;
        try {
            List deployInfoByDataTableKey2 = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
            if (deployInfoByDataTableKey2 != null && !deployInfoByDataTableKey2.isEmpty()) {
                tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey2.get(0)).getTableName();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        int j = 0;
        for (ExportFieldDefine item : importDefine) {
            DataField dataField;
            String code = item.getCode();
            if (code.contains(".")) {
                code = code.split("\\.")[1];
            }
            if ((dataField = this.dataFields.get(code)) == null) {
                FieldDefine dimensionField = this.dataAssist.getDimensionField(tableName, code);
                code = dimensionField.getCode();
            }
            if (code.equals("MDCODE")) {
                this.dwIndex = j;
            }
            if (code.equals("DATATIME")) {
                this.includeDataTime = true;
                this.datatimeIndex = j;
            }
            listField.add(this.dataFields.get(code));
            this.fieldRowList.add(this.fielddefineMap.get(code));
            ++j;
        }
        if (!this.includeDataTime) {
            Iterator<FieldDefine> exportFieldDefine = new ExportFieldDefine();
            ((ExportFieldDefine)((Object)exportFieldDefine)).setCode("DATATIME");
            importDefine.add(0, (ExportFieldDefine)((Object)exportFieldDefine));
            listField.add(0, this.dataFields.get("DATATIME"));
            this.fieldRowList.add(0, this.fielddefineMap.get("DATATIME"));
            this.datatimeIndex = 0;
            ++this.dwIndex;
        }
        block5: for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            for (int k = 0; k < importDefine.size(); ++k) {
                String code = ((ExportFieldDefine)importDefine.get(k)).getCode();
                String string = code = code.contains(".") ? code.split("\\.")[1] : code;
                if (!code.equals(fieldDefine.getCode())) continue;
                this.bizindex.add(k);
                continue block5;
            }
        }
        cfg.configItems().put("TYPE", (Object)SBImportActuatorType.BUF_DB);
        cfg.configItems().put("DEST_TABLE", tableKey);
        cfg.configItems().put("DEST_PERIOD", context.getDimensionSet().getValue("DATATIME"));
        if (this.tableContext.getFloatImpOpt() == 0) {
            cfg.configItems().put("IMPORT_MODE", (Object)ImportMode.INCREMENT);
        } else {
            cfg.configItems().put("IMPORT_MODE", (Object)ImportMode.ALL);
        }
        for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            String dimensionName = this.dataAssist.getDimensionName(fieldDefine);
            this.zbDimMap.put(fieldDefine.getCode(), dimensionName);
        }
        cfg.configItems().put("ZB_DIM_MAPPING", this.zbDimMap);
        cfg.configItems().put("SBID_SPECIFY", context.isImportBizkeyorder());
        this.sbImportActuator = this.sbImportActuatorFactory.getActuator(cfg);
        this.sbImportActuator.setDataFields(listField);
        this.sbImportActuator.prepare();
        if (this.tableContext.isMultistageEliminateBizKey()) {
            DimensionValueSet regionDimRange = this.tableContext.getDimensionSetRange();
            regionDimRange = new DimensionValueSet(Objects.nonNull(regionDimRange) ? regionDimRange : this.tableContext.getDimensionSet());
            DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)regionDimRange, (String)this.tableContext.getFormSchemeKey());
            HashSet<String> ignore = new HashSet<String>();
            String dwDimName = this.zbDimMap.get("MDCODE");
            ignore.add("uploadTimeSetting");
            ignore.add("upload");
            String formKey = this.regionData.getFormKey();
            IDataAccessServiceProvider iDataAccessServiceProvider = (IDataAccessServiceProvider)BeanUtil.getBean(IDataAccessServiceProvider.class);
            IDataAccessService dataAccessService = iDataAccessServiceProvider.getDataAccessService(this.tableContext.getTaskKey(), this.tableContext.getFormSchemeKey(), ignore);
            IBatchAccessResult writeAccess = dataAccessService.getWriteAccess(dimensionCollection, Collections.singletonList(formKey));
            IBatchAccessResult visitAccess = dataAccessService.getVisitAccess(dimensionCollection, Collections.singletonList(formKey));
            for (DimensionCombination combination : dimensionCollection.getDimensionCombinations()) {
                IAccessResult accessResult = writeAccess.getAccess(combination, formKey);
                try {
                    IAccessResult visitAccessResult;
                    if (accessResult.haveAccess()) {
                        this.access.add(combination.toDimensionValueSet());
                        continue;
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("\u7ef4\u5ea6\u3010{}\u3011\u6743\u9650\u6821\u9a8c\u4e0d\u901a\u8fc7\uff0c\u539f\u56e0\u4e3a\uff1a{}", (Object)combination.toDimensionValueSet(), (Object)accessResult.getMessage());
                    }
                    if (!(visitAccessResult = visitAccess.getAccess(combination, formKey)).haveAccess()) continue;
                    this.noAccess.add(combination.toDimensionValueSet());
                }
                catch (Exception e) {
                    log.error("\u591a\u7ea7\u90e8\u7f72\uff0c\u521d\u59cb\u5316\u7ef4\u5ea6\u6743\u9650\u65f6\u53d1\u751f\u9519\u8bef\uff1a", e);
                }
            }
            if (!this.access.isEmpty()) {
                DimensionValueSet newDim = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList<DimensionValueSet>(this.access));
                if (dwDimName != null) {
                    Object value = newDim.getValue(dwDimName);
                    if (value instanceof Collection) {
                        Collection codes = (Collection)value;
                        this.sbImportActuator.setMdCodeScope(codes);
                    } else if (value instanceof String) {
                        this.sbImportActuator.setMdCodeScope(Collections.singleton((String)value));
                    }
                }
            } else {
                throw new RuntimeException("\u65e0\u5bfc\u5165\u6570\u636e\u6240\u5728\u5355\u4f4d\u7684\u6743\u9650");
            }
        }
        ArrayList<FieldDefine> del = new ArrayList<FieldDefine>();
        for (FieldDefine i : this.bizKeyFieldDef) {
            boolean exist = false;
            for (ExportFieldDefine exportFieldDefine : importDefine) {
                String jCode;
                String string = jCode = exportFieldDefine.getCode().contains(".") ? exportFieldDefine.getCode().split("\\.")[1] : exportFieldDefine.getCode();
                if (null != jCode) {
                    exportFieldDefine.setCode(jCode);
                }
                if (exportFieldDefine.getCode().contains(".")) {
                    if (!exportFieldDefine.getCode().split("\\.")[1].equals(i.getCode())) continue;
                    exist = true;
                    break;
                }
                if (!exportFieldDefine.getCode().equals(i.getCode())) continue;
                exist = true;
                break;
            }
            if (exist) continue;
            del.add(i);
        }
        this.bizKeyFieldDef.removeAll(del);
    }

    public void setMdCodeScop(Collection<String> mdCodeScop) {
        if (this.tableContext.isImportBizkeyorder() || this.tableContext.getFloatImpOpt() == 2) {
            if (mdCodeScop == null) {
                String dwDimName;
                DimensionValueSet dimensionSet = this.tableContext.getDimensionSet();
                Object value = dimensionSet.getValue(dwDimName = this.zbDimMap.get("MDCODE"));
                if (value instanceof Collection) {
                    Collection codes = (Collection)value;
                    this.sbImportActuator.setMdCodeScope(codes);
                }
            } else {
                this.sbImportActuator.setMdCodeScope(mdCodeScop);
            }
        }
    }

    @Override
    public RegionData getRegionData() {
        return this.regionData;
    }

    @Override
    public boolean isFloatRegion() {
        return true;
    }

    @Override
    public List<ExportFieldDefine> getFieldDataList() {
        TableDefine td = null;
        ArrayList<ExportFieldDefine> fieldDefines = new ArrayList<ExportFieldDefine>();
        if (null != this.listFieldDefine && !this.listFieldDefine.isEmpty()) {
            for (FieldDefine item : this.listFieldDefine) {
                ExportFieldDefine fd = new ExportFieldDefine(item.getTitle(), item.getCode(), item.getSize(), item.getType().getValue());
                fd.setValueType(item.getValueType().getValue());
                if (td != null) {
                    fd.setTableCode(td.getCode());
                } else {
                    try {
                        td = this.dataDefinitionRuntimeController.queryTableDefine(item.getOwnerTableKey());
                        fd.setTableCode(td.getCode());
                    }
                    catch (Exception e) {
                        log.info("\u67e5\u5b58\u50a8\u8868\u51fa\u9519{}", e);
                        fd.setTableCode(item.getCode().split("\\.")[0]);
                    }
                }
                fieldDefines.add(fd);
            }
        }
        return fieldDefines;
    }

    @Override
    public DimensionValueSet importDatas(List<Object> originalRow) throws Exception {
        Object data;
        int i;
        ++this.importRowSize;
        ArrayList<Object> row = new ArrayList<Object>(originalRow);
        if (!this.includeDataTime) {
            row.add(0, this.tableContext.getDimensionSet().getValue("DATATIME"));
        }
        this.repairCode(row);
        boolean allEmpty = true;
        for (i = 0; i < this.bizKeyFieldDef.size(); ++i) {
            if (!(this.bizKeyFieldDef.get(i) instanceof DataFieldDTO) || !((DataFieldDTO)this.bizKeyFieldDef.get(i)).getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM) || null == (data = row.get(this.bizindex.get(i))) || data.equals("")) continue;
            allEmpty = false;
            break;
        }
        if (allEmpty) {
            throw new Exception("\u53f0\u8d26\u8868\u884c\u7ef4\u5ea6\u4e0d\u80fd\u5168\u4e3a\u7a7a\uff01");
        }
        for (i = 0; i < row.size(); ++i) {
            data = row.get(i);
            TransferData transferData = this.dataTransferOri(this.fieldRowList.get(i), data);
            if (transferData.getTransferType() == 1 && transferData.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c")) {
                log.info(transferData.getMsg() + "-->" + row);
                return DimensionValueSet.EMPTY;
            }
            data = transferData.getValue();
            row.set(i, !ObjectUtils.isEmpty(data) ? data : null);
        }
        if (this.checkAccess(row)) {
            try {
                this.sbImportActuator.put(row);
            }
            catch (TzCopyDataException e) {
                ++this.unImportRowSize;
                return DimensionValueSet.EMPTY;
            }
        } else {
            ++this.unImportRowSize;
        }
        DimensionValueSet valueSet = new DimensionValueSet();
        for (String value : this.zbDimMap.values()) {
            valueSet.setValue(value, null);
        }
        return valueSet;
    }

    private boolean checkAccess(List<Object> row) throws Exception {
        DimensionValueSet checkedRowDimValSet = new DimensionValueSet();
        for (int j = 0; j < this.bizKeyFieldDef.size(); ++j) {
            TransferData object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
            if (!(this.bizKeyFieldDef.get(j) instanceof DataFieldDTO) || !((DataFieldDTO)this.bizKeyFieldDef.get(j)).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) continue;
            String dimensionName = this.dataAssist.getDimensionName(this.bizKeyFieldDef.get(j));
            if (dimensionName.equals("DATATIME")) {
                checkedRowDimValSet.setValue(dimensionName, this.tableContext.getDimensionSet().getValue(dimensionName));
                continue;
            }
            checkedRowDimValSet.setValue(dimensionName, object.getValue());
        }
        try {
            boolean isUnChecked = true;
            boolean isAccess = false;
            if (!checkedRowDimValSet.isAllNull()) {
                if (this.access.contains(checkedRowDimValSet)) {
                    isUnChecked = false;
                    isAccess = true;
                }
            } else if (this.access.contains(this.tableContext.getDimensionSet())) {
                isUnChecked = false;
                isAccess = true;
            }
            if (!checkedRowDimValSet.isAllNull()) {
                if (this.noAccess.contains(checkedRowDimValSet)) {
                    isUnChecked = false;
                }
            } else if (this.noAccess.contains(this.tableContext.getDimensionSet())) {
                isUnChecked = false;
                isAccess = false;
            }
            if (isUnChecked) {
                if (checkedRowDimValSet.isAllNull()) {
                    this.checkQualifier(this.tableContext.getDimensionSet());
                    this.isReadRegionOnly(this.regionData, this.tableContext.getDimensionSet());
                    this.access.add(this.tableContext.getDimensionSet());
                } else {
                    this.checkQualifier(checkedRowDimValSet);
                    this.isReadRegionOnly(this.regionData, checkedRowDimValSet);
                    this.access.add(checkedRowDimValSet);
                }
                isAccess = true;
            }
            return isAccess;
        }
        catch (Exception e) {
            if (checkedRowDimValSet.isAllNull()) {
                this.noAccess.add(this.tableContext.getDimensionSet());
            } else {
                this.noAccess.add(checkedRowDimValSet);
            }
            log.debug("\u6ca1\u6709\u6743\u9650\uff0c\u6570\u636e\u4e0d\u8fdb\u884c\u5bfc\u5165:{}", (Object)e.getMessage());
            return false;
        }
    }

    private void checkQualifier(DimensionValueSet dimensionValueSet) throws Exception {
        this.tableContext.setFormKey(this.regionData.getFormKey());
        if (null == this.ioQualifier) {
            return;
        }
        if (null != dimensionValueSet && !dimensionValueSet.isAllNull()) {
            Map<String, List<String>> initQualifier = this.ioQualifier.initQualifier(this.tableContext, dimensionValueSet);
            List<String> formKey = initQualifier.get("formKeys");
            List<String> noAccessformKey = initQualifier.get("noAccessnFormKeys");
            if (null != formKey && !formKey.isEmpty() && !formKey.contains(this.regionData.getFormKey())) {
                throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\uff1a%s \u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", dimensionValueSet.toString()));
            }
            if (null != noAccessformKey && !noAccessformKey.isEmpty() && noAccessformKey.contains(this.regionData.getFormKey())) {
                throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\uff1a%s \u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", dimensionValueSet.toString()));
            }
        } else {
            Map<String, List<String>> initQualifier = this.ioQualifier.initQualifier(this.tableContext, this.tableContext.getDimensionSet());
            List<String> formKey = initQualifier.get("formKeys");
            List<String> noAccessformKey = initQualifier.get("noAccessnFormKeys");
            if (null != formKey && !formKey.isEmpty() && !formKey.contains(this.regionData.getFormKey())) {
                throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\uff1a%s \u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", this.tableContext.getDimensionSet().toString()));
            }
            if (null != noAccessformKey && !noAccessformKey.isEmpty() && noAccessformKey.contains(this.regionData.getFormKey())) {
                throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\uff1a%s \u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", this.tableContext.getDimensionSet().toString()));
            }
        }
    }

    private void isReadRegionOnly(RegionData regionData, DimensionValueSet masterKeys) throws Exception {
        AbstractData abstractData;
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionData.getKey());
        String readOnlyCondition = regionRelation.getRegionDefine().getReadOnlyCondition();
        com.jiuqi.np.dataengine.executors.ExecutorContext context = this.executorContextFactory.getExecutorContext((ParamRelation)regionRelation, masterKeys);
        if (org.springframework.util.StringUtils.hasLength(readOnlyCondition) && (abstractData = this.dataEngineService.expressionEvaluate(readOnlyCondition, context, masterKeys, regionRelation)) instanceof BoolData && abstractData.getAsBool()) {
            throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\u201c%s\u201d\u4e0b\u533a\u57df\u201c" + regionData.getTitle() + "\u201d\u53ea\u8bfb\uff0c\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", this.tableContext.getDimensionSet().toString()));
        }
    }

    @Override
    public ImportInfo commit() throws Exception {
        try {
            com.jiuqi.nr.fielddatacrud.ImportInfo infos = this.sbImportActuator.commitData();
            ImportInfo resultInfo = new ImportInfo();
            List dimValues = infos.getDimValues();
            if (dimValues != null) {
                dimValues.forEach(resultInfo::addDimValues);
            }
            try {
                if (!this.amendFieldValues.isEmpty()) {
                    StringBuilder message = new StringBuilder();
                    message.append("\u6570\u636e\u63d0\u4ea4\u5b8c\u6210\uff0c\u6b64\u6b21\u63d0\u4ea4\u8fc7\u7a0b\u4e2d\uff0c\u4ee5\u4e0b\u6307\u6807\u53ca\u5176\u5bf9\u5e94\u6570\u636e\u5b58\u5728\u5f02\u5e38\uff0c\u4fee\u6b63\u540e\u5bfc\u5165\u5165\u5e93\uff1a\n");
                    for (Map.Entry<String, Set<Object>> entry : this.amendFieldValues.entrySet()) {
                        Set<Object> values = entry.getValue();
                        if (values.isEmpty()) continue;
                        message.append("\u6307\u6807\u3010").append(entry.getKey()).append("\u3011\uff1a[");
                        values.forEach(value -> message.append(value).append(","));
                        message.deleteCharAt(message.length() - 1).append("]\n");
                    }
                    log.info(message.toString());
                    this.amendFieldValues.clear();
                }
            }
            catch (Exception e) {
                log.error("\u7ec4\u88c5\u4fee\u6b63\u5bfc\u5165\u6570\u636e\u65e5\u5fd7\u5185\u5bb9\u65f6\u53d1\u751f\u9519\u8bef:{}", (Object)e.getMessage(), (Object)e);
            }
            ImportInfo importInfo = resultInfo;
            return importInfo;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("\u63d0\u4ea4\u53f0\u8d26\u6570\u636e\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        finally {
            this.sbImportActuator.close();
        }
    }

    @Override
    public Map<String, Set<String>> getUnImport() throws Exception {
        Set<String> unitCodes;
        Object value;
        FieldDefine unitFieldDefine = this.getUnitFieldDefine();
        for (DimensionValueSet item : this.noAccess) {
            value = item.getValue(this.dataAssist.getDimensionName(unitFieldDefine));
            if (this.unImport.get("unit_noaccess") == null) {
                HashSet unitNoAccess = new HashSet();
                this.unImport.put("unit_noaccess", unitNoAccess);
            }
            if ((unitCodes = this.unImport.get("unit_noaccess")).contains(value)) {
                unitCodes.remove(value);
            }
            unitCodes.add(value.toString());
        }
        for (DimensionValueSet item : this.access) {
            value = item.getValue(this.dataAssist.getDimensionName(unitFieldDefine));
            if (this.unImport.get("unit_success") == null) {
                HashSet unitAccess = new HashSet();
                this.unImport.put("unit_success", unitAccess);
            }
            if ((unitCodes = this.unImport.get("unit_success")).contains(value)) {
                unitCodes.remove(value);
            }
            unitCodes.add(value.toString());
        }
        return this.unImport;
    }

    @Override
    public ImportResult getImportResult() {
        ImportResult importResult = new ImportResult();
        importResult.setRegionTitle(this.regionData.getTitle());
        importResult.setSuccessOrgs(this.access.stream().map(e -> e.getValue(this.dwDimensionName).toString()).collect(Collectors.toSet()));
        importResult.setSuccessOrgNum(this.access.size());
        importResult.setSuccessDataNum(this.importRowSize - this.unImportRowSize);
        importResult.setFailureOrgs(this.noAccess.stream().map(e -> e.getValue(this.dwDimensionName).toString()).collect(Collectors.toSet()));
        importResult.setFailureOrgNum(this.noAccess.size());
        importResult.setFailureDataNum(this.unImportRowSize);
        return importResult;
    }

    @Override
    public void close() {
        if (this.sbImportActuator != null) {
            this.sbImportActuator.close();
        }
    }

    @Override
    public List<FieldDefine> getBizFieldDefList() {
        return this.bizKeyFieldDef;
    }

    @Override
    public FieldDefine getUnitFieldDefine() throws Exception {
        FieldDefine unitFieldDefine = null;
        for (int i = 0; i < this.bizKeyFieldDef.size(); ++i) {
            if (!this.bizKeyFieldDef.get(i).getCode().equals("MDCODE")) continue;
            unitFieldDefine = this.bizKeyFieldDef.get(i);
        }
        if (unitFieldDefine != null) {
            return unitFieldDefine;
        }
        return null;
    }

    private void getBizKeysDef(String tableKey, List<FieldDefine> queryFieldDefine, boolean isImp) {
        try {
            String bizKeys;
            TableDefine tableDef = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
            if (null == tableDef) {
                return;
            }
            this.fieldBizKeys = bizKeys = Arrays.toString(tableDef.getBizKeyFieldsID());
            List allFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(tableKey);
            Collections.sort(allFields, new Comparator<FieldDefine>(){

                @Override
                public int compare(FieldDefine o1, FieldDefine o2) {
                    int i = o1.getCode().compareTo(o2.getCode());
                    if (i == 0) {
                        return o1.getCode().compareTo(o2.getCode());
                    }
                    return i;
                }
            });
            for (FieldDefine it : allFields) {
                this.dataFields.put(it.getCode(), (DataField)it);
                this.fielddefineMap.put(it.getCode(), it);
                if (bizKeys.contains(it.getKey()) && (!it.getCode().equals("BIZKEYORDER") || this.tableContext.isExportBizkeyorder())) {
                    queryFieldDefine.add(it);
                    this.bizKeyFieldDef.add(it);
                } else if (it.getCode().equals("BIZKEYORDER") && this.tableContext.isExportBizkeyorder()) {
                    queryFieldDefine.add(it);
                }
                if (isImp && (it.getCode().equals("BIZKEYORDER") || it.getCode().equals("FLOATORDER"))) {
                    if (bizKeys.contains(it.getKey()) && it.getCode().equals("BIZKEYORDER")) {
                        this.bizKeyFieldDef.add(it);
                    }
                    queryFieldDefine.add(it);
                }
                if (!it.getCode().equals("FLOATORDER")) continue;
                this.floatOrder = it;
            }
            ArrayList<FieldDefine> bizKeyFieldDef1 = new ArrayList<FieldDefine>();
            block3: for (int i = 0; i < tableDef.getBizKeyFieldsID().length; ++i) {
                for (FieldDefine biz : this.bizKeyFieldDef) {
                    if (!tableDef.getBizKeyFieldsID()[i].equals(biz.getKey())) continue;
                    bizKeyFieldDef1.add(biz);
                    continue block3;
                }
            }
            this.bizKeyFieldDef = bizKeyFieldDef1;
            int index = 0;
            for (FieldDefine biz : this.bizKeyFieldDef) {
                queryFieldDefine.remove(biz);
                queryFieldDefine.add(index, biz);
                ++index;
                String dimensionName = this.dataAssist.getDimensionName(biz);
                this.bizKeyDimNameMap.put(dimensionName, biz);
            }
        }
        catch (Exception e) {
            log.debug("\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u4e3b\u952e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private com.jiuqi.np.dataengine.executors.ExecutorContext getExecutorContext(TableContext context) {
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private ExecutorContext getNrExecutorContext(TableContext context) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
        List<Variable> variables = context.getVariables();
        if (variables != null) {
            for (Variable variable : variables) {
                environment.getVariableManager().add(variable);
            }
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        executorContext.setPeriodView(formScheme.getDateTime());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private TransferData dataTransferOri(FieldDefine def, Object data) throws Exception {
        String fieldCode;
        TransferSource transferSource = new TransferSource();
        transferSource.setDataField(def);
        transferSource.setDataSchemeKey(this.dataSchemeKey);
        transferSource.setRegionData(this.regionData);
        transferSource.setTableContext(this.tableContext);
        transferSource.setValue(data);
        TransferData transfer = this.dataTransfer.transfer(transferSource);
        if (transfer.getTransferType() == 2) {
            throw new Exception(transfer.getMsg());
        }
        if (transfer.getTransferType() == 1 && !transfer.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c") && (fieldCode = def.getCode()) != null) {
            Set values = this.amendFieldValues.computeIfAbsent(fieldCode, k -> new HashSet());
            values.add(data);
        }
        if (null == (data = transfer.getValue()) || data.equals("") || transfer.getValue().equals("NULL")) {
            transfer.setValue(null);
            return transfer;
        }
        return transfer;
    }

    private void initLinkViewMap(TableContext context, RegionData regionData) {
        List allLinksInRegion = this.runTimeViewController.getAllLinksInRegion(regionData.getKey());
        for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
            EntityViewDefine queryEntityView;
            String linkExpression = dataLinkDefine.getLinkExpression();
            String selectViewKey = null;
            if (null != linkExpression) {
                if (dataLinkDefine.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FORMULA)) continue;
                try {
                    FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(linkExpression);
                    this.enumDatakeys.put(linkExpression, fieldDefine.getEntityKey());
                    this.enumDatakeyLink.put(linkExpression, dataLinkDefine);
                    selectViewKey = fieldDefine.getEntityKey();
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (null == (queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey))) continue;
            try {
                ExecutorContext executorContext = this.getNrExecutorContext(context);
                IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, context, (com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
                this.enumDataValues.put(selectViewKey + dataLinkDefine.getLinkExpression(), entityTables);
            }
            catch (Exception e) {
                log.debug("\u67e5\u8be2\u6307\u6807\u6570\u636e\u8fde\u63a5\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
            }
        }
    }

    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);){
            int n;
            byte[] b = new byte[1000];
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e) {
            log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
        }
        catch (IOException e) {
            log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
        }
        return buffer;
    }

    private File getFile(String filePath) {
        String[] files;
        File file = new File(FilenameUtils.normalize(filePath));
        if (file.isDirectory() && (files = file.list()).length > 0) {
            file = new File(FilenameUtils.normalize(filePath + "/" + files[0]));
        }
        return file;
    }

    private void suoluotu(byte[] buffer, String name, String groupKey) {
        try {
            String fileName = name;
            long size = buffer.length;
            byte[] smallBytes = null;
            if (size > 20480L) {
                byte[] bytes = buffer;
                float proportion = 0.1f;
                if (size < 512000L && (proportion = (float)(1.0 - (double)(((float)size - 20480.0f) / 50000.0f) * 0.1)) < 0.1f) {
                    proportion = 0.1f;
                }
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
                Thumbnails.of((InputStream[])new InputStream[]{inputStream}).scale((double)proportion).toOutputStream((OutputStream)outPutStream);
                smallBytes = outPutStream.toByteArray();
            } else {
                smallBytes = buffer;
            }
            FileInfo smallFile = this.fileService.area("JTABLEAREA").uploadByGroup(fileName, groupKey, smallBytes);
            String path = this.fileService.area("JTABLEAREA").getPath(smallFile.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            smallFile = FileInfoBuilder.newFileInfo((FileInfo)smallFile, (String)smallFile.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
        }
        catch (IOException e) {
            log.warn("\u5bfc\u5165\u56fe\u7247\u7f29\u7565\u56fe\u5931\u8d25:{}", (Object)e.getMessage());
        }
    }

    @Override
    public int getTotalCount() {
        if (this.totalCount == -1) {
            com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext = this.getExecutorContext(this.tableContext);
            QueryEnvironment environment = new QueryEnvironment();
            environment.setRegionKey(this.regionData.getKey());
            environment.setFormCode(this.regionData.getFormCode());
            environment.setFormKey(this.regionData.getFormKey());
            environment.setFormSchemeKey(this.tableContext.getFormSchemeKey());
            this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
            for (FieldDefine it : this.listFieldDefine) {
                this.dataQuery.addColumn(it);
            }
            this.dataQuery.setDefaultGroupName(this.regionData.getFormCode());
            DimensionValueSet varDim = this.getCollectionDim(this.tableContext.getDimensionSet(), false);
            this.dataQuery.setMasterKeys(varDim);
            try {
                String filterCondition;
                if (this.dataRegionDefine == null) {
                    this.dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(this.regionData.getKey());
                }
                if ((filterCondition = this.dataRegionDefine.getFilterCondition()) != null && !filterCondition.isEmpty()) {
                    this.dataQuery.setRowFilter(filterCondition);
                }
                this.dataTable = this.dataQuery.executeReader(tbcontext);
                this.totalCount = this.dataTable.getTotalCount();
            }
            catch (Exception e) {
                this.executed = true;
                log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return this.totalCount;
    }

    private int getCurrDwTotalCount() {
        if (this.dataTable == null) {
            return 0;
        }
        return this.dataTable.getTotalCount();
    }

    private int getCurrQueryCount() {
        if (this.dataTable == null) {
            return 0;
        }
        return this.dataTable.getCount();
    }

    @Override
    public boolean hasNext() {
        boolean hashNext;
        if (null == this.listFieldDefine || this.listFieldDefine.isEmpty()) {
            return false;
        }
        if (this.executed) {
            return false;
        }
        if (this.nextCount == -1) {
            String org;
            this.nextCount = 0;
            for (int i = 0; i < this.orgSize && Objects.nonNull(org = this.orgList.poll()); ++i) {
                this.currOrgList.add(org);
            }
            if (this.currOrgList.isEmpty()) {
                return false;
            }
            this.executeQuery();
            hashNext = this.getCurrQueryCount() > 0;
        } else if (this.getCurrQueryCount() > this.nextCount) {
            hashNext = true;
        } else {
            this.doNextQuery();
            hashNext = this.getCurrQueryCount() > 0;
        }
        return hashNext;
    }

    @Override
    public ArrayList<Object> next() {
        ++this.nextCount;
        return this.getCurrPageDataRowSet();
    }

    public ArrayList<Object> getCurrPageDataRowSet() throws DataTypeException {
        ArrayList<Object> row = new ArrayList<Object>();
        if (this.nextCount <= this.dataTable.getCount()) {
            IDataRow dataRow = this.dataTable.getItem(this.nextCount - 1);
            if (!this.dwCurrencyRel.isEmpty()) {
                DimensionValueSet rowKeys = dataRow.getRowKeys();
                while (this.nextCount < this.dataTable.getCount() && !this.dwCurrencyRel.get(rowKeys.getValue(this.dwDimensionName).toString()).equals(rowKeys.getValue("MD_CURRENCY").toString())) {
                    ++this.nextCount;
                    dataRow = this.dataTable.getItem(this.nextCount - 1);
                    rowKeys = dataRow.getRowKeys();
                }
            }
            DataRowImpl datarow = (DataRowImpl)dataRow;
            for (FieldDefine def : this.listFieldDefine) {
                String dimension = this.getRowDimension(def);
                try {
                    int indexOf;
                    Object object;
                    String data = dataRow.getAsString(def);
                    if (data == null && (object = datarow.getKeyValue(Integer.valueOf(indexOf = datarow.getTableImpl().getFieldsInfo().indexOf(def)), dimension)) != null) {
                        data = object.toString();
                    }
                    data = this.dataTransfer(def, data);
                    if (this.multistageUnitReplace != null && def.getCode().equals("MDCODE")) {
                        data = this.multistageUnitReplace.getSuperiorCode(data);
                    }
                    if (this.mzOrgCodeRepairService != null && "MDCODE".equals(def.getCode())) {
                        data = this.dwValueRepair(dataRow, data);
                    }
                    row.add(data);
                }
                catch (DataTypeException e) {
                    log.info("\u6570\u636e\u7c7b\u578b\u8f6c\u6362\u5f02\u5e38{}", e);
                }
            }
        }
        return row;
    }

    private void executeQuery() {
        DimensionValueSet varDim;
        com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext = this.getExecutorContext(this.tableContext);
        tbcontext.setAutoDataMasking(this.tableContext.isDataMasking());
        if (this.pageIndex > 0 || this.orgIndex > 1) {
            QueryEnvironment environment = new QueryEnvironment();
            environment.setRegionKey(this.regionData.getKey());
            environment.setFormCode(this.regionData.getFormCode());
            environment.setFormKey(this.regionData.getFormKey());
            environment.setFormSchemeKey(this.tableContext.getFormSchemeKey());
            this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
            this.setOrders();
            for (FieldDefine it : this.listFieldDefine) {
                this.dataQuery.addColumn(it);
            }
            this.dataQuery.setDefaultGroupName(this.regionData.getFormCode());
            varDim = this.getCollectionDim(this.tableContext.getDimensionSet(), true);
            this.dataQuery.setMasterKeys(varDim);
            if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable()) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
        }
        try {
            String filterCondition;
            this.setOrders();
            this.setPage(this.dataQuery, this.pageSize, this.pageIndex);
            if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable()) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
            if (!this.tableContext.isOrdered()) {
                this.dataQuery.clearOrderByItems();
                this.setPage(this.dataQuery, -1, -1);
            }
            if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() && (this.tableContext.getSortFields() == null || this.tableContext.getSortFields().isEmpty() || this.tableKeys.size() > 1)) {
                this.dataQuery.clearOrderByItems();
                this.pageSize = Integer.MAX_VALUE;
            }
            if (this.dataRegionDefine == null) {
                this.dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(this.regionData.getKey());
            }
            if ((filterCondition = this.dataRegionDefine.getFilterCondition()) != null && !filterCondition.isEmpty()) {
                this.dataQuery.setRowFilter(filterCondition);
            }
            varDim = this.getCollectionDim(this.dataQuery.getMasterKeys(), true);
            if (this.tableContext.getDimensionSet() != null && (varDim == null || varDim.isAllNull())) {
                throw new Exception("\u6ca1\u6709\u6709\u6548\u7684\u67e5\u8be2\u7ef4\u5ea6\u96c6\u5408\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u67e5\u8be2");
            }
            this.dataQuery.setMasterKeys(varDim);
            long l = System.currentTimeMillis();
            LoggerFactory.getLogger(DataEngineConsts.class).info("\u6570\u636e\u67e5\u8be2\u5f00\u59cb");
            this.dataTable = this.dataQuery.executeReader(tbcontext);
            long l1 = System.currentTimeMillis() - l;
            LoggerFactory.getLogger(DataEngineConsts.class).info("\u6570\u636e\u67e5\u8be2\u5b8c\u6210\u8017\u65f6\uff1a" + l1 + " ms");
            this.tbcontext1 = tbcontext;
            if (this.getCurrQueryCount() == 0) {
                this.doNextQuery();
            }
        }
        catch (Exception e) {
            this.executed = true;
            log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private void doNextQuery() {
        this.nextCount = 0;
        if (this.getCurrQueryCount() == this.pageSize) {
            ++this.pageIndex;
        } else if (!this.orgList.isEmpty()) {
            String org;
            this.currOrgList = new ArrayList<String>();
            this.currOrgsAccessDim = null;
            for (int i = 0; i < this.orgSize && Objects.nonNull(org = this.orgList.poll()); ++i) {
                this.currOrgList.add(org);
            }
            ++this.orgIndex;
            this.pageIndex = 0;
        } else {
            this.dataTable = null;
            return;
        }
        this.executeQuery();
    }

    private void setPage(IDataQuery item, int pageSize, int pageIndex) {
        if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            item.setPagingInfo(pageSize, pageIndex);
        }
    }

    private void setOrders() {
        for (FieldDefine it : this.bizKeyFieldDef) {
            if (it.getCode().equals("BIZKEYORDER")) continue;
            this.dataQuery.addOrderByItem(it, false);
        }
        this.dataQuery.addOrderByItem(this.floatOrder, false);
    }

    private void initDataQuerys(RegionData regionData, List<FieldDefine> queryFieldDefine) {
        this.setOrders();
        for (FieldDefine it : queryFieldDefine) {
            this.dataQuery.addColumn(it);
        }
        for (FieldDefine fieldDefine : this.listFieldDefine) {
            this.dataQuery.addColumn(fieldDefine);
            queryFieldDefine.add(fieldDefine);
        }
        this.dataQuery.setDefaultGroupName(regionData.getFormCode());
        this.dataQuery.setMasterKeys(this.tableContext.getDimensionSet());
    }

    private void resetDimensionValueSet() {
        DimensionValueSet dimensionKey;
        List<EntityDefaultValue> regionSettingDefaultValue = this.regionData.getRegionEntityDefaultValue();
        if (!CollectionUtils.isEmpty(regionSettingDefaultValue) && (dimensionKey = this.tableContext.getDimensionSet()) != null) {
            try {
                for (EntityDefaultValue entityDefaultValue : regionSettingDefaultValue) {
                    EntityValueType entityType = entityDefaultValue.getEntityValueType();
                    String defalutVal = entityDefaultValue.getItemValue();
                    if (EntityValueType.DATA_ITEM == entityType) {
                        dimensionKey.setValue(this.entityMetaService.getDimensionName(entityDefaultValue.getEntityId()), (Object)defalutVal);
                        continue;
                    }
                    FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(entityDefaultValue.getFieldKey());
                    dimensionKey.setValue(this.dataAssist.getDimensionName(field), (Object)defalutVal);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    private DimensionValueSet getCollectionDim(DimensionValueSet dimensionSet, boolean ifSplitOrg) {
        if (!ifSplitOrg && this.accessDim != null) {
            return this.accessDim;
        }
        if (ifSplitOrg && this.currOrgsAccessDim != null) {
            return this.currOrgsAccessDim;
        }
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        List dimEntities = this.dataAccesslUtil.getDimEntityDimData(this.tableContext.getFormSchemeKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(this.tableContext.getFormSchemeKey());
        boolean isCurrency = false;
        DsContext dsContext = DsContextHolder.getDsContext();
        String contextId = dsContext.getContextEntityId();
        IEntityDefine dwEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        if (StringUtils.isNotEmpty((String)contextId)) {
            dwEntity = this.iEntityMetaService.queryEntity(contextId);
        }
        builder.setDWValue(dwEntity.getDimensionName(), formScheme.getDw(), new Object[]{ifSplitOrg ? this.currOrgList : dimensionSet.getValue(dwEntity.getDimensionName())});
        if (this.dwDimensionName.equals("")) {
            this.dwDimensionName = dwEntity.getDimensionName();
        }
        builder.setEntityValue("DATATIME", formScheme.getDateTime(), new Object[]{dimensionSet.getValue("DATATIME")});
        for (EntityDimData dimEntity : dimEntities) {
            Object currenCy = dimensionSet.getValue("MD_CURRENCY");
            if ("MD_CURRENCY".equals(dimEntity.getDimensionName()) && currenCy != null && ("PROVIDER_BASECURRENCY".equals(currenCy.toString()) || "PROVIDER_PBASECURRENCY".equals(currenCy.toString()))) {
                this.buildCurrency(this.dataSchemeKey, dimensionSet.getValue("MD_CURRENCY").toString(), builder, dimEntity);
                isCurrency = true;
                continue;
            }
            if (dimEntity.getEntityId().equals(formScheme.getDw())) {
                if (this.dwDimensionName.equals("")) {
                    this.dwDimensionName = dimEntity.getDimensionName();
                }
                builder.setDWValue(dimEntity.getDimensionName(), dimEntity.getEntityId(), new Object[]{dimensionSet.getValue(dimEntity.getDimensionName())});
                continue;
            }
            if (dimensionSet.getValue(dimEntity.getDimensionName()) == null) continue;
            builder.setEntityValue(dimEntity.getDimensionName(), dimEntity.getEntityId(), new Object[]{dimensionSet.getValue(dimEntity.getDimensionName())});
        }
        DimensionValueSet varDim = builder.getCollection().combineWithoutVarDim();
        if (isCurrency) {
            HashSet<Object> currency = new HashSet<Object>();
            for (DimensionValueSet dimensionValueSet : builder.getCollection()) {
                this.dwCurrencyRel.put(dimensionValueSet.getValue(this.dwDimensionName).toString(), dimensionValueSet.getValue("MD_CURRENCY").toString());
                currency.add(dimensionValueSet.getValue("MD_CURRENCY"));
            }
            ArrayList list = new ArrayList(currency);
            varDim.setValue("MD_CURRENCY", list);
        }
        for (int i = 0; i < varDim.size(); ++i) {
            if (varDim.getValue(i) != null && !varDim.getValue(i).equals("")) continue;
            varDim.clearValue(varDim.getName(i));
        }
        IDataAccessService dataAccessService = this.dataAuthReadable.readable(this.tableContext.getTaskKey(), this.tableContext.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.dataAuthReadable.hasAuthFormUnits(dataAccessService, varDim, this.regionData.getFormKey(), this.tableContext.getFormSchemeKey());
        if (ifSplitOrg) {
            this.currOrgsAccessDim = dimensionValueSet;
        } else {
            this.accessDim = dimensionValueSet;
        }
        return dimensionValueSet;
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityDimData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getEntityId(), dimensionProvider);
    }

    private String getRowDimension(FieldDefine def) {
        String dimension = null;
        if (!this.rowDimension.containsKey(def.getKey())) {
            try {
                DataModelDefinitionsCache dataModelDefinitionsCache = this.tbcontext1.getCache().getDataModelDefinitionsCache();
                ColumnModelDefine keyColumn = dataModelDefinitionsCache.getColumnModel(def);
                dimension = dataModelDefinitionsCache.getDimensionName(keyColumn);
                this.rowDimension.put(def.getKey(), dimension);
            }
            catch (ParseException e1) {
                e1.printStackTrace();
            }
        } else {
            dimension = this.rowDimension.get(def.getKey());
        }
        return dimension;
    }

    @Override
    public String dataTransfer(FieldDefine def, String data) {
        IEntityTable iEntityTable;
        if (null == data) {
            return "";
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_UUID) || def.getType().equals((Object)FieldType.FIELD_TYPE_STRING)) {
            if (this.tableContext.getExpEntryFields().equals((Object)ExpViewFields.KEY)) {
                IEntityTable iEntityTable2;
                enumKey = this.enumDatakeys.get(def.getKey());
                if (null != enumKey && null != (iEntityTable2 = this.enumDataValues.get(enumKey + def.getKey()))) {
                    IEntityRow findByEntityKey = iEntityTable2.findByEntityKey(data);
                    if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODETITLE)) {
                        if (null != findByEntityKey) {
                            data = findByEntityKey.getCode() + "|" + findByEntityKey.getTitle();
                        }
                    } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.TITLE)) {
                        if (findByEntityKey != null) {
                            data = findByEntityKey.getTitle();
                        }
                    } else if (!this.fieldBizKeys.contains(def.getKey()) && findByEntityKey != null) {
                        data = findByEntityKey.getCode();
                    }
                }
                return data;
            }
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_FILE) && this.tableContext.isAttachment()) {
            data = this.attachmentTransfer(data);
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) && this.tableContext.isAttachment()) {
            data = this.attachmentTransfer(data);
        } else if (null != enumKey && null != (iEntityTable = this.enumDataValues.get(enumKey + def.getKey()))) {
            if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODETITLE)) {
                if (null != iEntityTable.findByEntityKey(data)) {
                    data = iEntityTable.findByEntityKey(data).getCode() + "|" + iEntityTable.findByEntityKey(data).getTitle();
                }
            } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.TITLE)) {
                data = iEntityTable.findByEntityKey(data).getTitle();
            } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
                data = iEntityTable.findByEntityKey(data).getCode();
            }
        }
        return data;
    }

    private String attachmentTransfer(String data) {
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(this.dataSchemeKey);
        commonParamsDTO.setTaskKey(this.tableContext.getTaskKey());
        List fileInfoByGroup = this.attachmentIOService.getFileByGroup(data, commonParamsDTO);
        if (null == fileInfoByGroup || fileInfoByGroup.size() == 0) {
            return data;
        }
        StringBuffer buffer = new StringBuffer();
        for (com.jiuqi.nr.attachment.message.FileInfo fileInfo : fileInfoByGroup) {
            new FileInfoBuilder();
            FileInfo fileInfoOri = FileInfoBuilder.newFileInfo((String)fileInfo.getKey(), (String)fileInfo.getArea(), (String)fileInfo.getName(), (String)fileInfo.getExtension(), (long)fileInfo.getSize());
            this.attamentFiles.add(fileInfoOri);
            buffer.append("Attament/").append(fileInfo.getKey()).append(";");
        }
        if (buffer.length() > 0) {
            data = buffer.deleteCharAt(buffer.length() - 1).toString();
        }
        return data;
    }

    @Override
    public List<FileInfo> getAttamentFiles() {
        return this.attamentFiles;
    }

    @Override
    public void queryToDataRowReader(IRegionDataSetReader regionDataSetReader) {
        boolean isFirstQuery = true;
        while (!this.orgList.isEmpty()) {
            Object org;
            this.currOrgList = new ArrayList<String>();
            this.currOrgsAccessDim = null;
            for (int i = 0; i < this.orgSize && Objects.nonNull(org = this.orgList.poll()); ++i) {
                this.currOrgList.add((String)org);
            }
            if (!isFirstQuery) {
                QueryEnvironment environment = new QueryEnvironment();
                environment.setRegionKey(this.regionData.getKey());
                environment.setFormCode(this.regionData.getFormCode());
                environment.setFormKey(this.regionData.getFormKey());
                environment.setFormSchemeKey(this.tableContext.getFormSchemeKey());
                this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
                this.setOrders();
                org = this.listFieldDefine.iterator();
                while (org.hasNext()) {
                    FieldDefine it = (FieldDefine)org.next();
                    this.dataQuery.addColumn(it);
                }
                this.dataQuery.setDefaultGroupName(this.regionData.getFormCode());
                if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable()) {
                    this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
                }
            }
            try {
                DimensionValueSet varDim = this.getCollectionDim(this.tableContext.getDimensionSet(), true);
                if (this.tableContext.getDimensionSet() != null && (varDim == null || varDim.isAllNull())) {
                    throw new Exception("\u6ca1\u6709\u6709\u6548\u7684\u67e5\u8be2\u7ef4\u5ea6\u96c6\u5408\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u67e5\u8be2");
                }
                this.dataQuery.setMasterKeys(varDim);
                DataRowReaderImpl dataRowReader = new DataRowReaderImpl(this.listFieldDefine, regionDataSetReader, this);
                com.jiuqi.np.dataengine.executors.ExecutorContext executeContext = this.getExecutorContext(this.tableContext);
                executeContext.setAutoDataMasking(this.tableContext.isDataMasking());
                this.dataQuery.queryToDataRowReader(executeContext, (IDataRowReader)dataRowReader);
            }
            catch (Exception e) {
                log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
            }
        }
    }

    @Override
    public MultistageUnitReplace getMultistageUnitReplace() {
        return this.multistageUnitReplace;
    }

    @Override
    public MzOrgCodeRepairService getMzOrgCodeRepairService() {
        return this.mzOrgCodeRepairService;
    }

    private void repairCode(List<Object> row) {
        if (this.mzOrgCodeRepairService != null && this.dwIndex != -1) {
            if (this.formSchemeDefineImport == null) {
                this.formSchemeDefineImport = this.runTimeViewController.getFormScheme(this.tableContext.getFormSchemeKey());
            }
            StringBuilder cacheKey = new StringBuilder();
            DsContext dsContext = DsContextHolder.getDsContext();
            String contextId = dsContext.getContextEntityId();
            String entityId = this.formSchemeDefineImport.getDw();
            if (StringUtils.isNotEmpty((String)contextId)) {
                entityId = contextId;
            }
            String datatime = null;
            Object dtObj = null;
            if (this.datatimeIndex != -1) {
                dtObj = row.get(this.datatimeIndex);
            } else {
                Object value = this.tableContext.getDimensionSet().getValue("DATATIME");
                if (value instanceof String) {
                    dtObj = value;
                }
            }
            if (dtObj != null) {
                datatime = dtObj.toString();
                cacheKey.append(datatime).append("-");
            }
            String dwValue = null;
            Object dwObj = row.get(this.dwIndex);
            if (dwObj != null) {
                dwValue = dwObj.toString();
                cacheKey.append(dwValue).append("-");
            }
            cacheKey.append(this.formSchemeDefineImport.getKey()).append("-").append(entityId);
            String finalEntityId = entityId;
            String finalDwValue = dwValue;
            String finalDatatime = datatime;
            if (this.mzOrgCodeRepairCache == null) {
                this.mzOrgCodeRepairCache = new HashMap<String, String>();
            }
            if (datatime != null && dwValue != null) {
                dwValue = this.mzOrgCodeRepairCache.computeIfAbsent(cacheKey.toString(), key -> this.mzOrgCodeRepairService.repairOrgCode(this.formSchemeDefineImport.getKey(), finalEntityId, finalDwValue, finalDatatime));
                row.set(this.dwIndex, dwValue);
            }
        }
    }

    @Override
    public String dwValueRepair(IDataRow dataRow, String dwValue) {
        Object value;
        if (this.formSchemeDefineImport == null) {
            this.formSchemeDefineImport = this.runTimeViewController.getFormScheme(this.tableContext.getFormSchemeKey());
        }
        DsContext dsContext = DsContextHolder.getDsContext();
        String contextId = dsContext.getContextEntityId();
        String entityId = this.formSchemeDefineImport.getDw();
        if (StringUtils.isNotEmpty((String)contextId)) {
            entityId = contextId;
        }
        Object datatimeObj = null;
        if (dataRow.getRowKeys() != null) {
            datatimeObj = dataRow.getRowKeys().getValue("DATATIME");
        }
        if (datatimeObj == null && (value = this.tableContext.getDimensionSet().getValue("DATATIME")) instanceof String) {
            datatimeObj = value;
        }
        if (datatimeObj == null) {
            return dwValue;
        }
        String datatime = datatimeObj.toString();
        String finalEntityId = entityId;
        String cacheKey = datatime + "-" + dwValue + "-" + this.formSchemeDefineImport.getKey() + "-" + entityId;
        if (this.mzOrgCodeRepairCache == null) {
            this.mzOrgCodeRepairCache = new HashMap<String, String>();
        }
        return this.mzOrgCodeRepairCache.computeIfAbsent(cacheKey, key -> this.mzOrgCodeRepairService.repairCode(this.formSchemeDefineImport.getKey(), finalEntityId, dwValue, datatime));
    }
}

