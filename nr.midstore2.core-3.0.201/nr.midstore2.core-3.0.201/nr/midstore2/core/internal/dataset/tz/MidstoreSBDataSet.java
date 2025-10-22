/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fielddatacrud.ActuatorConfig
 *  com.jiuqi.nr.fielddatacrud.ISBActuator
 *  com.jiuqi.nr.fielddatacrud.ImportInfo
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.io.config.NrIoProperties
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.ImportMode
 *  com.jiuqi.nr.io.sb.JIOSBImportActuatorConfig
 *  com.jiuqi.nr.io.sb.SBImportActuatorConfig
 *  com.jiuqi.nr.io.sb.SBImportActuatorFactory
 *  com.jiuqi.nr.io.sb.SBImportActuatorType
 *  com.jiuqi.nr.io.sb.bean.ImportInfo
 *  com.jiuqi.nr.io.service.IDataTransfer
 *  com.jiuqi.nr.io.service.IDataTransferProvider
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.io.service.MzOrgCodeRepairService
 *  com.jiuqi.nr.io.service.impl.DataAuthReadable
 *  com.jiuqi.nr.io.service.impl.ParameterService
 *  com.jiuqi.nr.io.tz.exception.TzCopyDataException
 *  com.jiuqi.nr.io.util.DateUtil
 *  com.jiuqi.nr.io.util.ParamUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  net.coobird.thumbnailator.Thumbnails
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package nr.midstore2.core.internal.dataset.tz;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.ImportMode;
import com.jiuqi.nr.io.sb.JIOSBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorFactory;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.service.IDataTransfer;
import com.jiuqi.nr.io.service.IDataTransferProvider;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.service.MzOrgCodeRepairService;
import com.jiuqi.nr.io.service.impl.DataAuthReadable;
import com.jiuqi.nr.io.service.impl.ParameterService;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.util.DateUtil;
import com.jiuqi.nr.io.util.ParamUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import nr.midstore2.core.common.MidstoreDataRowReaderImpl;
import nr.midstore2.core.dataset.IMidstoreEntityService;
import nr.midstore2.core.dataset.IMidstoreRegionDataSetReader;
import nr.midstore2.core.dataset.MidsotreTableContext;
import nr.midstore2.core.dataset.MidstoreTableData;
import nr.midstore2.core.internal.dataset.AbstractMidstoreDataSet;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

public class MidstoreSBDataSet
extends AbstractMidstoreDataSet {
    private static final Logger log = LoggerFactory.getLogger(MidstoreSBDataSet.class);
    private MidstoreTableData regionData = null;
    private MidsotreTableContext tableContext;
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
    private IMidstoreEntityService ioEntityService;
    private FileAreaService fileAreaService;
    private FileService fileService;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private Map<String, String> zbDimMap = new HashMap<String, String>();
    private DataModelService dataModelService;
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
    private ExecutorContext tbcontext1 = null;
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
    private List<IEntityTable> entityTable = new ArrayList<IEntityTable>();
    private DimensionValueSet currOrgsAccessDim = null;

    public MidstoreSBDataSet(MidsotreTableContext context, MidstoreTableData regionData) {
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
        this.fileAreaService = this.fileService.area(this.tableContext.getAttachmentArea());
        this.jdbcTemplate = parameterService.getJdbcTemplate();
        this.ioEntityService = (IMidstoreEntityService)BeanUtil.getBean(IMidstoreEntityService.class);
        this.iEntityMetaService = parameterService.getEntityMetaService();
        this.dataModelService = parameterService.getDataModelService();
        this.runtimeDataSchemeService = parameterService.getRuntimeDataSchemeService();
        this.multistageUnitReplace = this.tableContext.getMultistageUnitReplace();
        this.dimensionProviderFactory = (DimensionProviderFactory)BeanUtil.getBean(DimensionProviderFactory.class);
        this.dataAccesslUtil = (DataAccesslUtil)BeanUtil.getBean(DataAccesslUtil.class);
        this.attachmentIOService = parameterService.getAttachmentIOService();
        this.dataAuthReadable = parameterService.getDataAuthReadable();
        this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
        this.resetDimensionValueSet();
        this.listFieldDefine = new ArrayList<FieldDefine>();
        try {
            List listFieldDefine2 = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
            for (Object field : listFieldDefine2) {
                DataField dataField = this.runtimeDataSchemeService.getDataField(field.getKey());
                if (dataField != null && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                this.listFieldDefine.add((FieldDefine)field);
            }
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
        this.initLinkViewMap(this.tableContext, regionData);
        QueryEnvironment environment = new QueryEnvironment();
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
        block7: for (FieldDefine item : queryFieldDefine) {
            for (FieldDefine it : this.listFieldDefine) {
                if (!it.getKey().equals(item.getKey())) continue;
                this.listFieldDefine.remove(it);
                continue block7;
            }
        }
        this.initDataQuerys(regionData, queryFieldDefine);
        this.listFieldDefine = queryFieldDefine;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(this.tableContext.getTaskKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(taskDefine.getDw());
        Object mdOrg = this.tableContext.getDimensionSet().getValue(queryEntity.getDimensionName());
        if (mdOrg instanceof List) {
            this.orgList.addAll((List)mdOrg);
        } else {
            this.orgList.add((String)mdOrg);
        }
    }

    public MidstoreSBDataSet(MidsotreTableContext context, MidstoreTableData regionData, List<ExportFieldDefine> importDefine) {
        String code;
        List deployInfoByDataTableKey;
        this.tableContext = context;
        this.regionData = regionData;
        ParameterService parameterService = ParamUtil.getParameterService();
        this.runTimeViewController = parameterService.getRunTimeViewController();
        this.dataDefinitionRuntimeController = parameterService.getDataDefinitionRuntimeController();
        this.entityViewRunTimeController = parameterService.getEntityViewRunTimeController();
        this.dataAccessProvider = parameterService.getDataAccessProvider();
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(context));
        this.sbImportActuatorFactory = (SBImportActuatorFactory)BeanUtil.getBean(SBImportActuatorFactory.class);
        this.ioEntityService = (IMidstoreEntityService)BeanUtil.getBean(IMidstoreEntityService.class);
        this.fileService = parameterService.getFileService();
        this.fileAreaService = this.fileService.area(context.getAttachmentArea());
        this.runtimeDataSchemeService = parameterService.getRuntimeDataSchemeService();
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
        this.nrIoProperties = parameterService.getNrIoProperties();
        this.ioQualifier = context.getIoQualifier();
        this.dataEngineService = (DataEngineService)BeanUtil.getBean(DataEngineService.class);
        this.executorContextFactory = (IExecutorContextFactory)BeanUtil.getBean(IExecutorContextFactory.class);
        this.regionRelationFactory = (RegionRelationFactory)BeanUtil.getBean(RegionRelationFactory.class);
        JIOSBImportActuatorConfig cfg = new JIOSBImportActuatorConfig(true);
        this.listFieldDefine = new ArrayList<FieldDefine>();
        try {
            List listFieldDefine2 = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
            for (FieldDefine field : listFieldDefine2) {
                DataField dataField = this.runtimeDataSchemeService.getDataField(field.getKey());
                if (dataField != null && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                this.listFieldDefine.add(field);
            }
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
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
        block5: for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            for (int j = 0; j < importDefine.size(); ++j) {
                code = importDefine.get(j).getCode();
                String string = code = code.contains(".") ? code.split("\\.")[1] : code;
                if (!code.equals(fieldDefine.getCode())) continue;
                this.bizindex.add(j);
                continue block5;
            }
        }
        int j = 0;
        for (ExportFieldDefine item : importDefine) {
            DataField dataField;
            code = item.getCode();
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
                this.datatimeIndex = j;
            }
            listField.add(this.dataFields.get(code));
            this.fieldRowList.add(this.fielddefineMap.get(code));
            ++j;
        }
        cfg.configItems().put("TYPE", SBImportActuatorType.BUF_DB);
        cfg.configItems().put("DEST_TABLE", tableKey);
        cfg.configItems().put("DEST_PERIOD", context.getDimensionSet().getValue("DATATIME"));
        if (this.tableContext.getFloatImpOpt() == 0) {
            cfg.configItems().put("IMPORT_MODE", ImportMode.INCREMENT);
        } else {
            cfg.configItems().put("IMPORT_MODE", ImportMode.ALL);
        }
        for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            String dimensionName = this.dataAssist.getDimensionName(fieldDefine);
            this.zbDimMap.put(fieldDefine.getCode(), dimensionName);
        }
        cfg.configItems().put("ZB_DIM_MAPPING", this.zbDimMap);
        cfg.configItems().put("SBID_SPECIFY", context.isImportBizkeyorder());
        this.sbImportActuator = this.sbImportActuatorFactory.getActuator((ActuatorConfig)cfg);
        this.sbImportActuator.setDataFields(listField);
        this.sbImportActuator.prepare();
        if (this.tableContext.isMultistageEliminateBizKey()) {
            // empty if block
        }
        ArrayList<FieldDefine> arrayList = new ArrayList<FieldDefine>();
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
            arrayList.add(i);
        }
        this.bizKeyFieldDef.removeAll(arrayList);
    }

    public MidstoreSBDataSet(MidsotreTableContext context, MidstoreTableData regionData, List<ExportFieldDefine> importDefine, boolean old) {
        this.tableContext = context;
        this.regionData = regionData;
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(context));
        this.sbImportActuatorFactory = (SBImportActuatorFactory)BeanUtil.getBean(SBImportActuatorFactory.class);
        this.ioEntityService = (IMidstoreEntityService)BeanUtil.getBean(IMidstoreEntityService.class);
        this.fileService = (FileService)BeanUtil.getBean(FileService.class);
        this.fileAreaService = this.fileService.area(context.getAttachmentArea());
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        JIOSBImportActuatorConfig cfg = new JIOSBImportActuatorConfig(true);
        this.listFieldDefine = new ArrayList<FieldDefine>();
        try {
            List listFieldDefine2 = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
            for (FieldDefine field : listFieldDefine2) {
                DataField dataField = this.runtimeDataSchemeService.getDataField(field.getKey());
                if (dataField != null && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                this.listFieldDefine.add(field);
            }
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
        String tableKey = null;
        if (null != this.listFieldDefine && this.listFieldDefine.size() > 0) {
            tableKey = this.listFieldDefine.get(0).getOwnerTableKey();
        }
        ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
        this.getBizKeysDef(tableKey, queryFieldDefine, true);
        ArrayList<DataField> listField = new ArrayList<DataField>();
        String tableName = null;
        try {
            List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
            if (deployInfoByDataTableKey != null && !deployInfoByDataTableKey.isEmpty()) {
                tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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
            listField.add(this.dataFields.get(code));
            this.fieldRowList.add(this.fielddefineMap.get(code));
        }
        cfg.configItems().put("TYPE", SBImportActuatorType.BUF_DB);
        cfg.configItems().put("DEST_TABLE", tableKey);
        cfg.configItems().put("DEST_PERIOD", context.getDimensionSet().getValue("DATATIME"));
        if (this.tableContext.getFloatImpOpt() == 0) {
            cfg.configItems().put("IMPORT_MODE", ImportMode.INCREMENT);
        } else {
            cfg.configItems().put("IMPORT_MODE", ImportMode.ALL);
        }
        for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            String dimensionName = this.dataAssist.getDimensionName(fieldDefine);
            this.zbDimMap.put(fieldDefine.getCode(), dimensionName);
        }
        cfg.configItems().put("ZB_DIM_MAPPING", this.zbDimMap);
        cfg.configItems().put("SBID_SPECIFY", context.isImportBizkeyorder());
        this.sbImportActuator = this.sbImportActuatorFactory.getImportActuator((SBImportActuatorConfig)cfg);
        this.sbImportActuator.setDataFields(listField);
        this.sbImportActuator.prepare();
        this.initLinkViewMap(context, regionData);
    }

    public void setMdCodeScop(Collection<String> mdCodeScop) {
        if (this.tableContext.isImportBizkeyorder()) {
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
    public MidstoreTableData getRegionData() {
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
                ExportFieldDefine fd = new ExportFieldDefine(item.getTitle(), item.getCode(), item.getSize().intValue(), item.getType().getValue());
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
    public DimensionValueSet importDatas(List<Object> row) throws Exception {
        for (int i = 0; i < row.size(); ++i) {
            Object data = row.get(i);
            data = this.dataTransferOri(this.fieldRowList.get(i), data);
            row.set(i, data);
        }
        try {
            this.sbImportActuator.put(row);
        }
        catch (TzCopyDataException e) {
            return DimensionValueSet.EMPTY;
        }
        DimensionValueSet valueSet = new DimensionValueSet();
        for (String value : this.zbDimMap.values()) {
            valueSet.setValue(value, null);
        }
        return valueSet;
    }

    @Override
    public ImportInfo commit() throws Exception {
        try {
            com.jiuqi.nr.fielddatacrud.ImportInfo infos = this.sbImportActuator.commitData();
            ImportInfo resultInfo = new ImportInfo();
            List dimValues = infos.getDimValues();
            if (dimValues != null) {
                dimValues.forEach(arg_0 -> ((ImportInfo)resultInfo).addDimValues(arg_0));
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
            TableDefine tableDef = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
            if (null == tableDef) {
                return;
            }
            String bizKeys = Arrays.toString(tableDef.getBizKeyFieldsID());
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

    private ExecutorContext getExecutorContext(MidsotreTableContext context) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private Object dataTransferOri(FieldDefine def, Object data) {
        if (null == data) {
            return null;
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
            return DateUtil.getOriDatetime((String)data.toString(), (String)"yyyy-MM-dd");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_TIME)) {
            return DateUtil.getOriDatetime((String)data.toString(), (String)"HH:mm:ss");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
            return DateUtil.getOriDatetime((String)data.toString(), (String)"yyyy-MM-dd HH:mm:ss");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
            if (!this.tableContext.isAttachment()) {
                return data;
            }
            String groupKey = UUID.randomUUID().toString();
            String[] fileNames = data.toString().split(";");
            if (fileNames.length == 1 && fileNames[0].length() == 0) {
                return null;
            }
            for (int i = 0; i < fileNames.length; ++i) {
                File file = this.getFile(this.tableContext.getPwd() + fileNames[i]);
                byte[] buffer = this.getBytes(file);
                if (null == buffer) continue;
                this.fileAreaService.uploadByGroup(file.getName(), groupKey, buffer);
            }
            return groupKey;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
            if (!this.tableContext.isAttachment()) {
                return null;
            }
            String groupKey = UUID.randomUUID().toString();
            String[] fileNames = data.toString().split(";");
            if (fileNames.length == 1 && fileNames[0].length() == 0) {
                return null;
            }
            for (int i = 0; i < fileNames.length; ++i) {
                File file = this.getFile(this.tableContext.getPwd() + fileNames[i]);
                byte[] buffer = this.getBytes(file);
                if (null == buffer) continue;
                this.fileAreaService.uploadByGroup(file.getName(), groupKey, buffer);
                this.suoluotu(buffer, file.getName(), groupKey);
            }
            return groupKey;
        }
        if (null != enumKey) {
            IEntityTable iEntityTable = this.enumDataValues.get(enumKey);
            if (null != iEntityTable) {
                IEntityRow byEntityKey;
                FieldDefine refer = null;
                String referCode = "";
                DataField df = (DataField)def;
                if (null != df.getRefDataEntityKey()) {
                    try {
                        refer = this.dataDefinitionRuntimeController.queryFieldDefine(df.getRefDataEntityKey());
                        if (refer == null) {
                            ColumnModelDefine colum = this.dataModelService.getColumnModelDefineByID(df.getRefDataFieldKey());
                            referCode = colum.getCode();
                        } else {
                            referCode = refer.getCode();
                        }
                    }
                    catch (Exception e) {
                        log.debug("\u6ca1\u6709\u627e\u5173\u8054\u7684\u6307\u6807{}", (Object)e.getMessage());
                    }
                }
                if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.TITLE)) {
                    String string = data.toString();
                    if (string != null && string.contains("|")) {
                        return string.split("\\|")[0];
                    }
                    List allRows = iEntityTable.getAllRows();
                    for (IEntityRow row : allRows) {
                        if (!row.getTitle().equals(data.toString())) continue;
                        if (Consts.EntityField.ENTITY_FIELD_KEY.equals((Object)referCode)) {
                            return null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                        }
                        return row.getCode();
                    }
                } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
                    IEntityRow findByCode = iEntityTable.findByCode(data.toString());
                    if (findByCode != null) {
                        return findByCode.getCode() != null ? findByCode.getCode() : data.toString();
                    }
                } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.KEY) && null != (byEntityKey = iEntityTable.findByEntityKey(data.toString()))) {
                    return byEntityKey.getEntityKeyData() != null ? byEntityKey.getEntityKeyData() : data.toString();
                }
            }
            return data;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC)) {
            data = data.equals("\u662f");
        } else if (1 == def.getType().getValue() || 3 == def.getType().getValue() || 8 == def.getType().getValue()) {
            BigDecimal initialBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)data.toString())) {
                    if (data.toString().contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(data.toString());
                        initialBigDecimal = new BigDecimal(number.doubleValue());
                    } else {
                        initialBigDecimal = new BigDecimal(data.toString());
                    }
                }
                return initialBigDecimal;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return data;
    }

    private void initLinkViewMap(MidsotreTableContext context, MidstoreTableData regionData) {
        boolean isTable = true;
        List allFieldsInTable = null;
        try {
            allFieldsInTable = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getTablekey());
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
        if (allFieldsInTable == null) {
            return;
        }
        for (FieldDefine fieldDefine : allFieldsInTable) {
            String selectViewKey = null;
            if (!StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
            this.enumDatakeys.put(fieldDefine.getKey(), fieldDefine.getEntityKey());
            selectViewKey = fieldDefine.getEntityKey();
            EntityViewDefine queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey);
            if (null == queryEntityView || queryEntityView.getEntityId() == null) continue;
            try {
                ExecutorContext executorContext = this.getExecutorContext(context);
                IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, context, executorContext);
                this.enumDataValues.put(selectViewKey, entityTables);
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
        File file = new File(filePath);
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

    private void setOrders() {
        for (FieldDefine it : this.bizKeyFieldDef) {
            if (it.getCode().equals("BIZKEYORDER")) continue;
            this.dataQuery.addOrderByItem(it, false);
        }
        this.dataQuery.addOrderByItem(this.floatOrder, false);
    }

    private void initDataQuerys(MidstoreTableData regionData, List<FieldDefine> queryFieldDefine) {
        this.setOrders();
        for (FieldDefine it : queryFieldDefine) {
            this.dataQuery.addColumn(it);
        }
        for (FieldDefine fieldDefine : this.listFieldDefine) {
            this.dataQuery.addColumn(fieldDefine);
            queryFieldDefine.add(fieldDefine);
        }
        this.dataQuery.setMasterKeys(this.tableContext.getDimensionSet());
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
        if (ifSplitOrg) {
            this.currOrgsAccessDim = varDim;
        } else {
            this.accessDim = varDim;
        }
        return varDim;
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityDimData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getEntityId(), dimensionProvider);
    }

    private String getRowDimension(FieldDefine def) {
        String dimension = null;
        if (!this.rowDimension.containsKey(def.getKey())) {
            ColumnModelDefine keyColumn;
            DataModelDefinitionsCache dataModelDefinitionsCache = null;
            try {
                dataModelDefinitionsCache = this.tbcontext1.getCache().getDataModelDefinitionsCache();
            }
            catch (ParseException e1) {
                e1.printStackTrace();
            }
            if (dataModelDefinitionsCache != null && (keyColumn = dataModelDefinitionsCache.getColumnModel(def)) != null) {
                dimension = dataModelDefinitionsCache.getDimensionName(keyColumn);
                this.rowDimension.put(def.getKey(), dimension);
            }
        } else {
            dimension = this.rowDimension.get(def.getKey());
        }
        return dimension;
    }

    @Override
    public void queryToDataRowReader(IMidstoreRegionDataSetReader regionDataSetReader) {
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
                this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
                this.setOrders();
                org = this.listFieldDefine.iterator();
                while (org.hasNext()) {
                    FieldDefine it = (FieldDefine)org.next();
                    this.dataQuery.addColumn(it);
                }
                if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable()) {
                    this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
                }
            }
            DimensionValueSet varDim = this.getCollectionDim(this.tableContext.getDimensionSet(), true);
            this.dataQuery.setMasterKeys(varDim);
            try {
                MidstoreDataRowReaderImpl dataRowReader = new MidstoreDataRowReaderImpl(this.listFieldDefine, regionDataSetReader, this);
                ExecutorContext executeContext = this.getExecutorContext(this.tableContext);
                this.dataQuery.queryToDataRowReader(executeContext, (IDataRowReader)dataRowReader);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
            }
        }
    }

    @Override
    public String dataTransfer(FieldDefine def, String data) {
        IEntityTable iEntityTable;
        if (null == data || data.equals("")) {
            Object dataTimeObj;
            if ("DATATIME".equalsIgnoreCase(def.getCode()) && this.tableContext.getDimensionSet() != null && (dataTimeObj = this.tableContext.getDimensionSet().getValue("DATATIME")) != null) {
                return dataTimeObj.toString();
            }
            return "";
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_UUID) || def.getType().equals((Object)FieldType.FIELD_TYPE_STRING)) {
            for (FieldDefine bizKey : this.bizKeyFieldDef) {
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
                DataField df = (DataField)def;
                if (!def.getKey().equals(bizKey.getKey()) && (null == df.getRefDataEntityKey() || !df.getRefDataEntityKey().equals(bizKey.getKey()))) continue;
                for (IEntityTable entity : this.entityTable) {
                    IEntityRow entityKey = entity.findByEntityKey(data);
                    if (null == entityKey) continue;
                    if (this.tableContext.getExpEntryFields().equals((Object)ExpViewFields.CODE)) {
                        return entityKey.getCode();
                    }
                    if (this.tableContext.getExpEntryFields().equals((Object)ExpViewFields.TITLE)) {
                        return entityKey.getTitle();
                    }
                    if (!this.tableContext.getExpEntryFields().equals((Object)ExpViewFields.CODETITLE)) continue;
                    return entityKey.getCode() + "|" + entityKey.getTitle();
                }
            }
        } else if (!(def.getType().equals((Object)FieldType.FIELD_TYPE_FILE) && this.tableContext.isAttachment() || def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) && this.tableContext.isAttachment() || null == enumKey || null == (iEntityTable = this.enumDataValues.get(enumKey + def.getKey())))) {
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
}

