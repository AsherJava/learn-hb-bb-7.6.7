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
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.io.config.NrIoProperties
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.output.EntityDatas
 *  com.jiuqi.nr.io.params.output.ExportEntity
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.bean.ImportInfo
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 *  com.jiuqi.nr.io.service.MzOrgCodeRepairService
 *  com.jiuqi.nr.io.util.DateUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  net.coobird.thumbnailator.Thumbnails
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package nr.midstore2.core.internal.dataset;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.output.EntityDatas;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.service.MzOrgCodeRepairService;
import com.jiuqi.nr.io.util.DateUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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

public class MidstoreDataSet
extends AbstractMidstoreDataSet {
    private static final Logger log = LoggerFactory.getLogger(MidstoreDataSet.class);
    private MidstoreTableData regionData;
    private MidsotreTableContext tableContext;
    private IReadonlyTable dataTable;
    private int pageSize;
    private int pageIndex;
    private int nextCount;
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IDataQuery dataQuery;
    private IDataAccessProvider dataAccessProvider;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private List<IEntityTable> entityTable;
    private IEntityQuery entityQuery;
    private IEntityMetaService entityMetaService;
    private List<IDataQuery> dataQueryRegions;
    private List<IReadonlyTable> dataTables;
    private List<FieldDefine> listFieldDefine;
    private List<List<FieldDefine>> regionFieldDefs;
    private List<FieldDefine> bizKeyFieldDef;
    private List<FieldDefine> bizKeyFieldDefCp;
    private FileInfoService fileInfoService;
    private FileService fileService;
    private FileAreaService fileAreaService;
    private List<FileInfo> attamentFiles;
    private Map<String, String> enumDatakeys;
    private Map<String, IEntityTable> enumDataValues;
    private IDataTable readerDataTable;
    private IDataUpdator openForUpdate;
    private List<FieldDefine> floatOrders;
    private int floatIncrease;
    private boolean hasBizKey;
    private DimensionValueSet dimensionValueSet;
    private IDataAssist dataAssist;
    private com.jiuqi.np.dataengine.executors.ExecutorContext executorContext;
    private final List<List<Object>> pendingRows;
    private final List<DimensionValueSet> pendingRowsDvs;
    private Set<List<String>> pendingDVS;
    private List<Integer> bizindex;
    private Set<List<String>> processedDVS;
    private DimensionValueSet impDimensionValueSet;
    private List<String> orderedFieldDef;
    private boolean importData;
    private Set<DimensionValueSet> access;
    private Set<DimensionValueSet> noAccess;
    private JdbcTemplate jdbcTemplate;
    TempAssistantTable tempTable;
    private String fieldBizKeys;
    private boolean executed;
    private List<ArrayList<Object>> dataRows;
    private Set<String> tableKeys;
    private IMidstoreEntityService ioEntityService;
    private IEntityMetaService iEntityMetaService;
    private DataModelService dataModelService;
    private Map<String, Set<String>> unImport;
    private Map<String, Integer> regionSettingValIndexMap;
    private Map<Integer, Set<String>> c;
    private Map<String, Object> exits;
    private int dwNameIndex;
    private String floatLastDw;
    private int smCount;
    private DimensionValueSet currRowDim;
    private List<IDataRow> dbRows;
    private FieldDefine bizKeyOrder;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private List<DataField> floatKeys;
    private boolean hasDimField;
    private NrIoProperties nrIoProperties;
    private int rowSize;
    private Map<String, String> dataTableMap;
    public MultistageUnitReplace multistageUnitReplace;
    private Map<Integer, String> rowDimensionName;
    private boolean isMultiRegionExistDefaultValImport;
    private MzOrgCodeRepairService mzOrgCodeRepairService;
    private com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext1;
    private Map<String, String> rowDimension;

    /*
     * WARNING - void declaration
     */
    public MidstoreDataSet(MidsotreTableContext context, MidstoreTableData regionData) {
        this.pageSize = 20000;
        this.pageIndex = -1;
        this.nextCount = this.pageSize;
        this.entityTable = new ArrayList<IEntityTable>();
        this.dataQueryRegions = new ArrayList<IDataQuery>();
        this.dataTables = new ArrayList<IReadonlyTable>();
        this.listFieldDefine = null;
        this.regionFieldDefs = new ArrayList<List<FieldDefine>>();
        this.bizKeyFieldDef = new ArrayList<FieldDefine>();
        this.bizKeyFieldDefCp = new ArrayList<FieldDefine>();
        this.attamentFiles = new ArrayList<FileInfo>();
        this.enumDatakeys = new HashMap<String, String>();
        this.enumDataValues = new HashMap<String, IEntityTable>();
        this.floatOrders = new ArrayList<FieldDefine>();
        this.floatIncrease = 1;
        this.hasBizKey = false;
        this.dimensionValueSet = null;
        this.pendingRows = new ArrayList<List<Object>>();
        this.pendingRowsDvs = new ArrayList<DimensionValueSet>();
        this.pendingDVS = new LinkedHashSet<List<String>>();
        this.bizindex = new ArrayList<Integer>();
        this.processedDVS = new LinkedHashSet<List<String>>();
        this.impDimensionValueSet = null;
        this.orderedFieldDef = null;
        this.importData = false;
        this.access = new HashSet<DimensionValueSet>();
        this.noAccess = new HashSet<DimensionValueSet>();
        this.tempTable = null;
        this.fieldBizKeys = "";
        this.executed = false;
        this.dataRows = null;
        this.tableKeys = new HashSet<String>();
        this.unImport = new HashMap<String, Set<String>>();
        this.regionSettingValIndexMap = new HashMap<String, Integer>();
        this.c = new HashMap<Integer, Set<String>>();
        this.exits = new HashMap<String, Object>();
        this.dwNameIndex = 0;
        this.floatLastDw = null;
        this.smCount = 0;
        this.currRowDim = null;
        this.dbRows = new ArrayList<IDataRow>();
        this.bizKeyOrder = null;
        this.floatKeys = null;
        this.hasDimField = false;
        this.rowSize = 5000;
        this.dataTableMap = new HashMap<String, String>();
        this.isMultiRegionExistDefaultValImport = false;
        this.tbcontext1 = null;
        this.rowDimension = new HashMap<String, String>();
        this.tableContext = context.clone();
        this.regionData = regionData;
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.fileInfoService = (FileInfoService)BeanUtil.getBean(FileInfoService.class);
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(this.tableContext));
        this.fileService = (FileService)BeanUtil.getBean(FileService.class);
        this.fileAreaService = this.fileService.area(this.tableContext.getAttachmentArea());
        this.jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
        this.ioEntityService = (IMidstoreEntityService)BeanUtil.getBean(IMidstoreEntityService.class);
        this.iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.multistageUnitReplace = this.tableContext.getMultistageUnitReplace();
        this.resetDimensionValueSet();
        try {
            this.listFieldDefine = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
        this.initLinkViewMap(this.tableContext, regionData);
        QueryEnvironment environment = new QueryEnvironment();
        this.dataQuery = this.dataAccessProvider.newDataQuery();
        if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable() && regionData.getType() == 1) {
            this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
        }
        String tableKey = null;
        if (null != this.listFieldDefine && this.listFieldDefine.size() > 0) {
            tableKey = this.listFieldDefine.get(0).getOwnerTableKey();
            for (FieldDefine fieldDefine : this.listFieldDefine) {
                this.tableKeys.add(fieldDefine.getOwnerTableKey());
            }
        }
        for (String string : this.tableKeys) {
            try {
                List list = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(string);
                if (list == null || list.isEmpty()) continue;
                this.dataTableMap.put(string, ((DataFieldDeployInfo)list.get(0)).getTableName());
            }
            catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        }
        Collections.sort(this.listFieldDefine, new Comparator<FieldDefine>(){

            @Override
            public int compare(FieldDefine o1, FieldDefine o2) {
                int i = o1.getCode().compareTo(o2.getCode());
                if (i == 0) {
                    return o1.getCode().compareTo(o2.getCode());
                }
                return i;
            }
        });
        ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
        this.getBizKeysDef(tableKey, queryFieldDefine, false);
        block10: for (FieldDefine fieldDefine : queryFieldDefine) {
            for (FieldDefine it : this.listFieldDefine) {
                if (!it.getKey().equals(fieldDefine.getKey())) continue;
                this.listFieldDefine.remove(it);
                continue block10;
            }
        }
        this.initDataQuerys(regionData, queryFieldDefine);
        this.listFieldDefine = queryFieldDefine;
        if (this.tableContext.getDimension() == null && this.tableContext.getDimensionSet() != null && !this.tableContext.getDimensionSet().isAllNull()) {
            ArrayList dims;
            void var6_13;
            Object var6_11 = null;
            for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
                DataField df = (DataField)fieldDefine;
                if (df.getRefDataEntityKey() == null) continue;
                try {
                    DataTable tableDefine = (DataTable)this.dataDefinitionRuntimeController.queryTableDefine(df.getRefDataEntityKey());
                    if (tableDefine == null || this.ioEntityService.isPeriod(tableDefine.getKey())) continue;
                    String string = this.dataAssist.getDimensionName(fieldDefine);
                    break;
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            DimensionValueSet dimensionValueSet = this.tableContext.getDimensionSet();
            if (var6_13 == null) {
                return;
            }
            Object value = dimensionValueSet.getValue((String)var6_13);
            if (value instanceof List && (dims = (ArrayList)value).size() > 499) {
                HashSet set = new HashSet(dims);
                dims = new ArrayList(set);
                this.tempTable = new TempAssistantTable(dims, 6);
                try {
                    this.tempTable.createTempTable();
                    this.tempTable.insertIntoTempTable();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (this.tempTable != null) {
                this.dataQuery.setTempAssistantTable((String)var6_13, this.tempTable);
                this.tableContext.setTempAssistantTable((String)var6_13, this.tempTable);
            }
        }
    }

    public MidstoreDataSet(MidsotreTableContext context, MidstoreTableData regionData, List<ExportFieldDefine> importDefine) {
        String tableKey;
        block33: {
            this.pageSize = 20000;
            this.pageIndex = -1;
            this.nextCount = this.pageSize;
            this.entityTable = new ArrayList<IEntityTable>();
            this.dataQueryRegions = new ArrayList<IDataQuery>();
            this.dataTables = new ArrayList<IReadonlyTable>();
            this.listFieldDefine = null;
            this.regionFieldDefs = new ArrayList<List<FieldDefine>>();
            this.bizKeyFieldDef = new ArrayList<FieldDefine>();
            this.bizKeyFieldDefCp = new ArrayList<FieldDefine>();
            this.attamentFiles = new ArrayList<FileInfo>();
            this.enumDatakeys = new HashMap<String, String>();
            this.enumDataValues = new HashMap<String, IEntityTable>();
            this.floatOrders = new ArrayList<FieldDefine>();
            this.floatIncrease = 1;
            this.hasBizKey = false;
            this.dimensionValueSet = null;
            this.pendingRows = new ArrayList<List<Object>>();
            this.pendingRowsDvs = new ArrayList<DimensionValueSet>();
            this.pendingDVS = new LinkedHashSet<List<String>>();
            this.bizindex = new ArrayList<Integer>();
            this.processedDVS = new LinkedHashSet<List<String>>();
            this.impDimensionValueSet = null;
            this.orderedFieldDef = null;
            this.importData = false;
            this.access = new HashSet<DimensionValueSet>();
            this.noAccess = new HashSet<DimensionValueSet>();
            this.tempTable = null;
            this.fieldBizKeys = "";
            this.executed = false;
            this.dataRows = null;
            this.tableKeys = new HashSet<String>();
            this.unImport = new HashMap<String, Set<String>>();
            this.regionSettingValIndexMap = new HashMap<String, Integer>();
            this.c = new HashMap<Integer, Set<String>>();
            this.exits = new HashMap<String, Object>();
            this.dwNameIndex = 0;
            this.floatLastDw = null;
            this.smCount = 0;
            this.currRowDim = null;
            this.dbRows = new ArrayList<IDataRow>();
            this.bizKeyOrder = null;
            this.floatKeys = null;
            this.hasDimField = false;
            this.rowSize = 5000;
            this.dataTableMap = new HashMap<String, String>();
            this.isMultiRegionExistDefaultValImport = false;
            this.tbcontext1 = null;
            this.rowDimension = new HashMap<String, String>();
            this.importData = true;
            this.tableContext = context.clone();
            this.regionData = regionData;
            this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
            this.fileInfoService = (FileInfoService)BeanUtil.getBean(FileInfoService.class);
            this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(this.tableContext));
            this.fileService = (FileService)BeanUtil.getBean(FileService.class);
            this.fileAreaService = this.fileService.area(this.tableContext.getAttachmentArea());
            this.ioEntityService = (IMidstoreEntityService)BeanUtil.getBean(IMidstoreEntityService.class);
            this.iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
            this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
            this.nrIoProperties = (NrIoProperties)BeanUtil.getBean(NrIoProperties.class);
            if (this.nrIoProperties.getRowsize() != 0) {
                this.rowSize = this.nrIoProperties.getRowsize();
            }
            this.resetDimensionValueSet();
            try {
                this.listFieldDefine = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
            }
            catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
            this.initLinkViewMap(this.tableContext, regionData);
            this.dataQuery = this.dataAccessProvider.newDataQuery();
            if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable() && regionData.getType() == 0) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
            tableKey = null;
            if (null != this.listFieldDefine && this.listFieldDefine.size() > 0) {
                tableKey = this.listFieldDefine.get(0).getOwnerTableKey();
                for (FieldDefine field : this.listFieldDefine) {
                    this.tableKeys.add(field.getOwnerTableKey());
                }
            }
            for (String keys : this.tableKeys) {
                try {
                    List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(keys);
                    if (deployInfoByDataTableKey == null || deployInfoByDataTableKey.isEmpty()) continue;
                    this.dataTableMap.put(keys, ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName());
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
            this.getBizKeysDef(tableKey, queryFieldDefine, true);
            ArrayList<FieldDefine> del = new ArrayList<FieldDefine>();
            for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
                boolean exist = false;
                for (ExportFieldDefine j : importDefine) {
                    Object code = null;
                    FieldDefine dimensionField = null;
                    String jCode = j.getCode().contains(".") ? j.getCode().split("\\.")[1] : j.getCode();
                    try {
                        dimensionField = this.dataAssist.getDimensionField(this.dataTableMap.get(this.bizKeyFieldDef.get(0).getOwnerTableKey()), jCode);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (dimensionField != null) {
                        jCode = dimensionField.getCode();
                    }
                    if (null != jCode) {
                        j.setCode(jCode);
                    }
                    if (j.getCode().contains(".")) {
                        if (!j.getCode().split("\\.")[1].equals(fieldDefine.getCode())) continue;
                        exist = true;
                        break;
                    }
                    if (!j.getCode().equals(fieldDefine.getCode())) continue;
                    exist = true;
                    break;
                }
                if (exist) continue;
                del.add(fieldDefine);
            }
            this.bizKeyFieldDef.removeAll(del);
            for (FieldDefine fieldDefine : this.floatOrders) {
                this.listFieldDefine.add(fieldDefine);
                if (!fieldDefine.getCode().equals("BIZKEYORDER")) continue;
                this.bizKeyOrder = fieldDefine;
            }
            for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
                boolean exit = false;
                for (FieldDefine list : this.listFieldDefine) {
                    if (!list.getCode().equals(fieldDefine.getCode())) continue;
                    exit = true;
                    break;
                }
                if (exit) continue;
                this.listFieldDefine.add(fieldDefine);
            }
            ArrayList<String> orderedFieldDef = new ArrayList<String>();
            for (ExportFieldDefine expField : importDefine) {
                orderedFieldDef.add(expField.getCode());
            }
            this.orderedFieldDef = orderedFieldDef;
            this.sortFields1(this.listFieldDefine, orderedFieldDef);
            this.sortFields(this.bizKeyFieldDef, orderedFieldDef);
            block18: for (FieldDefine i : this.bizKeyFieldDef) {
                for (int j = 0; j < importDefine.size(); ++j) {
                    if (importDefine.get(j).getCode().contains(".")) {
                        if (!importDefine.get(j).getCode().split("\\.")[1].equals(i.getCode())) continue;
                        this.bizindex.add(j);
                        continue block18;
                    }
                    if (!importDefine.get(j).getCode().equals(i.getCode())) continue;
                    this.bizindex.add(j);
                    continue block18;
                }
            }
            for (FieldDefine item : this.listFieldDefine) {
                boolean isBizKey = false;
                for (FieldDefine it : this.bizKeyFieldDef) {
                    if (!it.getCode().equals(item.getCode())) continue;
                    isBizKey = true;
                    this.hasBizKey = true;
                }
                if (isBizKey) continue;
                this.dataQuery.addColumn(item);
                if (!item.getCode().equals("FLOATORDER")) continue;
                this.dataQuery.addOrderByItem(item, false);
            }
            if (this.bizKeyOrder != null) {
                this.dataQuery.addColumn(this.bizKeyOrder);
            }
            this.dimensionValueSet = new DimensionValueSet(this.tableContext.getDimensionSet());
            try {
                FieldDefine fieldDefine = this.getUnitFieldDefine();
                if (fieldDefine == null) break block33;
                for (int i = 0; i < this.bizKeyFieldDef.size(); ++i) {
                    if (!this.bizKeyFieldDef.get(i).getCode().equals(fieldDefine.getCode())) continue;
                    this.dwNameIndex = i;
                    break;
                }
            }
            catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        }
        if (this.regionData.getType() != DataTableType.TABLE.getValue()) {
            try {
                TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
                this.floatKeys = this.runtimeDataSchemeService.getDataFieldByTableCodeAndKind(tableDefine.getCode(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
                if (this.floatKeys != null && !this.floatKeys.isEmpty()) {
                    this.hasDimField = true;
                }
            }
            catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
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
                this.isMultiRegionExistDefaultValImport = true;
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void sortFields1(List<FieldDefine> fieldDef, List<String> orderedFieldDef) {
        HashMap<String, FieldDefine> fdMapDot = new HashMap<String, FieldDefine>();
        HashMap<String, FieldDefine> fdMap = new HashMap<String, FieldDefine>();
        boolean hasFieldDefOrder = false;
        for (FieldDefine fieldDefine : fieldDef) {
            if (fieldDefine.getCode().equals("FLOATORDER")) {
                hasFieldDefOrder = true;
            }
            fdMapDot.put(this.dataTableMap.get(this.bizKeyFieldDef.get(0).getOwnerTableKey()) + "." + fieldDefine.getCode(), fieldDefine);
            fdMap.put(fieldDefine.getCode(), fieldDefine);
        }
        ArrayList<FieldDefine> newDfMap = new ArrayList<FieldDefine>();
        boolean hasOrder = false;
        for (String ori : orderedFieldDef) {
            if (null != fdMapDot.get(ori)) {
                newDfMap.add((FieldDefine)fdMapDot.get(ori));
                if (!ori.equals("FLOATORDER")) continue;
                hasOrder = true;
                continue;
            }
            if (ori.contains(".") && null != fdMap.get(ori.split("\\.")[1])) {
                newDfMap.add((FieldDefine)fdMap.get(ori.split("\\.")[1]));
                if (!ori.equals("FLOATORDER")) continue;
                hasOrder = true;
                continue;
            }
            if (null != fdMap.get(ori)) {
                newDfMap.add((FieldDefine)fdMap.get(ori));
                if (!ori.equals("FLOATORDER")) continue;
                hasOrder = true;
                continue;
            }
            if (null != fdMap.get(ori)) continue;
            for (FieldDefine fieldDefine : fieldDef) {
                if (ori.equals(fieldDefine.getCode())) {
                    newDfMap.add(fieldDefine);
                    continue;
                }
                char[] charArray = ori.toCharArray();
                char[] charS = new char[charArray.length - 1];
                for (int i = 0; i < charS.length; ++i) {
                    charS[i] = charArray[i + 1];
                }
                if (!String.valueOf(charS).equals(fieldDefine.getCode())) continue;
                newDfMap.add(fieldDefine);
            }
        }
        if (!hasOrder) {
            if (null != fdMap.get("FLOATORDER")) {
                newDfMap.add((FieldDefine)fdMap.get("FLOATORDER"));
            } else if (hasFieldDefOrder) {
                for (FieldDefine order : this.floatOrders) {
                    if (!order.getCode().equals("FLOATORDER")) continue;
                    newDfMap.add(order);
                }
            }
        }
        this.listFieldDefine = newDfMap;
    }

    private void sortFields(List<FieldDefine> fieldDef, final List<String> orderedFieldDef) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(fieldDef, new Comparator<FieldDefine>(){
            boolean containtTable;
            {
                this.containtTable = ((String)orderedFieldDef.get(0)).contains(".");
            }

            @Override
            public int compare(FieldDefine o1, FieldDefine o2) {
                int io2;
                int io1;
                if (this.containtTable) {
                    io1 = orderedFieldDef.indexOf((String)MidstoreDataSet.this.dataTableMap.get(o1.getOwnerTableKey()) + "." + o1.getCode());
                    io2 = orderedFieldDef.indexOf((String)MidstoreDataSet.this.dataTableMap.get(o2.getOwnerTableKey()) + "." + o2.getCode());
                } else {
                    io1 = orderedFieldDef.indexOf(o1.getCode());
                    io2 = orderedFieldDef.indexOf(o2.getCode());
                }
                for (int i = 0; i < orderedFieldDef.size(); ++i) {
                    if (this.containtTable) {
                        if (!((String)orderedFieldDef.get(i)).equals(MidstoreDataSet.this.dataTableMap.get(o2.getOwnerTableKey() + "." + o2.getCode()))) continue;
                        io2 = i;
                        continue;
                    }
                    if (!((String)orderedFieldDef.get(i)).equals(o2.getCode())) continue;
                    io2 = i;
                }
                if (io1 == -1) {
                    return 1;
                }
                if (io2 == -1) {
                    return -1;
                }
                return io1 - io2;
            }
        });
    }

    private void initLinkViewMap(MidsotreTableContext context, MidstoreTableData regionData) {
        boolean isTable = true;
        if (isTable) {
            List allFieldsInTable = null;
            try {
                allFieldsInTable = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
            }
            catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
            if (allFieldsInTable == null) {
                return;
            }
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(regionData.getKey());
            for (FieldDefine fieldDefine : allFieldsInTable) {
                EntityViewDefine queryEntityView;
                String selectViewKey = null;
                if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                    // empty if block
                }
                if (!StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
                this.enumDatakeys.put(fieldDefine.getKey(), fieldDefine.getEntityKey());
                selectViewKey = fieldDefine.getEntityKey();
                if (this.enumDataValues.containsKey(selectViewKey) || null == (queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey)) || queryEntityView.getEntityId() == null) continue;
                try {
                    ExecutorContext executorContext = this.getNrExecutorContext(context);
                    IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, context, (com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
                    this.enumDataValues.put(selectViewKey, entityTables);
                }
                catch (Exception e) {
                    log.debug("\u67e5\u8be2\u6307\u6807\u6570\u636e\u8fde\u63a5\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
                }
            }
        } else {
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
                        selectViewKey = fieldDefine.getEntityKey();
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
                if (null == (queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey)) || queryEntityView.getEntityId() == null) continue;
                try {
                    ExecutorContext executorContext = this.getNrExecutorContext(context);
                    IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, context, (com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
                    this.enumDataValues.put(selectViewKey, entityTables);
                }
                catch (Exception e) {
                    log.debug("\u67e5\u8be2\u6307\u6807\u6570\u636e\u8fde\u63a5\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
                }
            }
        }
    }

    private void initEntityQuery(MidsotreTableContext context) {
        try {
            String masterKeys;
            ExecutorContext executorContext;
            EntityViewDefine queryEntityView;
            IEntityTable entityTables;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(this.tableContext.getFormSchemeKey());
            DsContext dsContext = DsContextHolder.getDsContext();
            String contextId = dsContext.getContextEntityId();
            if (StringUtils.isNotEmpty((String)contextId) && null != (entityTables = this.ioEntityService.getIEntityTable(queryEntityView = this.entityViewRunTimeController.buildEntityView(contextId, dsContext.getContextFilterExpression()), context, (com.jiuqi.np.dataengine.executors.ExecutorContext)(executorContext = this.getNrExecutorContext(context))))) {
                this.entityTable.add(entityTables);
            }
            if ((masterKeys = formScheme.getMasterEntitiesKey()) != null) {
                String[] keys;
                for (String key : keys = masterKeys.split(";")) {
                    EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
                    if (this.dataAssist.getDimensionName(entityView).equals("DATATIME")) continue;
                    EntityViewDefine queryEntityView2 = null;
                    if (key.equals(formScheme.getDw())) {
                        queryEntityView2 = this.runTimeViewController.getViewByFormSchemeKey(this.tableContext.getFormSchemeKey());
                    } else {
                        IDimensionFilter dimensionFilter = this.runTimeViewController.getDimensionFilter(this.tableContext.getFormSchemeKey(), key);
                        queryEntityView2 = this.entityViewRunTimeController.buildEntityView(key, dimensionFilter.getValue());
                    }
                    ExecutorContext executorContext2 = this.getNrExecutorContext(context);
                    IEntityTable entityTables2 = this.ioEntityService.getIEntityTable(queryEntityView2, context, (com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext2);
                    if (null == entityTables2) continue;
                    this.entityTable.add(entityTables2);
                }
            }
        }
        catch (Exception e) {
            log.debug("\u67e5\u8be2\u4e3b\u4f53\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private void initDataQuerys(MidstoreTableData regionData, List<FieldDefine> queryFieldDefine) {
        List<String> sortFields = this.tableContext.getSortFields();
        if (!this.floatOrders.isEmpty()) {
            FieldDefine order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
            this.dataQuery.addOrderByItem(order, false);
        }
        for (FieldDefine it : queryFieldDefine) {
            if (null != sortFields && !sortFields.isEmpty()) {
                this.addSort(sortFields, this.dataQuery, it);
            } else {
                this.dataQuery.addOrderByItem(it, false);
            }
            this.dataQuery.addColumn(it);
        }
        for (FieldDefine fieldDefine : this.listFieldDefine) {
            DataField field = this.runtimeDataSchemeService.getDataField(fieldDefine.getKey());
            this.dataQuery.addColumn(fieldDefine);
            queryFieldDefine.add(fieldDefine);
        }
        this.dataQuery.setMasterKeys(this.tableContext.getDimensionSet());
    }

    private void addSort(List<String> sortFields, IDataQuery dataQueryRegion, FieldDefine it) {
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
                    this.floatOrders.add(it);
                    continue;
                }
                if (!it.getCode().equals("FLOATORDER")) continue;
                this.floatOrders.add(it);
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
                this.bizKeyFieldDefCp.add(biz);
            }
        }
        catch (Exception e) {
            log.debug("\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u4e3b\u952e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private ExecutorContext getNrExecutorContext(MidsotreTableContext context) {
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

    private com.jiuqi.np.dataengine.executors.ExecutorContext getExecutorContext(MidsotreTableContext context) {
        if (this.executorContext == null) {
            com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
            if (StringUtils.isNotEmpty((String)context.getFormSchemeKey())) {
                executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
                List<Variable> variables = context.getVariables();
                if (variables != null) {
                    for (Variable variable : variables) {
                        environment.getVariableManager().add(variable);
                    }
                }
                executorContext.setEnv((IFmlExecEnvironment)environment);
                executorContext.setJQReportModel(true);
            }
            this.executorContext = executorContext;
        }
        return this.executorContext;
    }

    private void executeQuery() {
        com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext = this.getExecutorContext(this.tableContext);
        List<String> sortFields = this.tableContext.getSortFields();
        if (sortFields == null || sortFields.isEmpty()) {
            this.dataQuery.clearOrderByItems();
        }
        if (this.pageIndex > 0) {
            QueryEnvironment environment = new QueryEnvironment();
            this.dataQuery = this.dataAccessProvider.newDataQuery();
            if (null != sortFields && !sortFields.isEmpty()) {
                this.addSort(sortFields, this.dataQuery, this.bizKeyFieldDef.get(0));
            }
            int i = 0;
            for (FieldDefine it : this.bizKeyFieldDef) {
                this.dataQuery.addOrderByItem(it, false);
                if ((i = this.unitDatatime(i, it)) != 2 || this.floatOrders.isEmpty()) continue;
                FieldDefine order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
                this.dataQuery.addOrderByItem(order, false);
            }
            for (FieldDefine it : this.listFieldDefine) {
                this.dataQuery.addColumn(it);
            }
            this.dataQuery.setMasterKeys(this.tableContext.getDimensionSet());
            if (null == this.tableContext.getDimension() || null == this.tableContext.getTempTable() || !this.importData || this.regionData.getType() == 0) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
        }
        try {
            if (null != sortFields && !sortFields.isEmpty() && !this.bizKeyFieldDef.isEmpty()) {
                this.addSort(sortFields, this.dataQuery, this.bizKeyFieldDef.get(0));
            }
            int i = 0;
            for (FieldDefine it : this.bizKeyFieldDef) {
                this.dataQuery.addOrderByItem(it, false);
                if ((i = this.unitDatatime(i, it)) != 2 || this.floatOrders.isEmpty()) continue;
                FieldDefine order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
                this.dataQuery.addOrderByItem(order, false);
            }
            this.setPage(this.dataQuery, this.pageSize, this.pageIndex);
            if (null == this.tableContext.getDimension() || null == this.tableContext.getTempTable() || !this.importData || this.regionData.getType() == 0) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
            if (!this.tableContext.isOrdered()) {
                this.dataQuery.clearOrderByItems();
                this.setPage(this.dataQuery, -1, -1);
            }
            if (this.regionData.getType() == DataTableType.TABLE.getValue() && (this.tableContext.getSortFields() == null || this.tableContext.getSortFields().isEmpty() || this.tableKeys.size() > 1)) {
                this.dataQuery.clearOrderByItems();
                this.pageSize = Integer.MAX_VALUE;
            }
            this.dataTable = this.dataQuery.executeReader(tbcontext);
            this.tbcontext1 = tbcontext;
        }
        catch (Exception e) {
            this.executed = true;
            log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private int unitDatatime(int i, FieldDefine it) {
        DataField df = (DataField)it;
        String referFieldKey = df.getRefDataEntityKey();
        if (referFieldKey != null) {
            FieldDefine fieldDefine = null;
            try {
                TableDefine queryTableDefine;
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(referFieldKey);
                if (fieldDefine != null && (queryTableDefine = this.dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey())) != null && !this.ioEntityService.isPeriod(queryTableDefine.getKey()) && i == 0) {
                    ++i;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.dataAssist.getDimensionName(it).equals("DATATIME")) {
            ++i;
        }
        return i;
    }

    @Override
    public boolean hasNext() {
        boolean hashNext;
        if (this.dataRows != null && this.dataRows.size() > this.nextCount) {
            return true;
        }
        if (this.executed) {
            return false;
        }
        if (this.dataTable == null && this.regionData.getType() == DataTableType.TABLE.getValue()) {
            this.nextCount = 0;
            return true;
        }
        if (this.dataTable != null && this.regionData.getType() == DataTableType.TABLE.getValue()) {
            return this.dataTable.getCount() > this.nextCount;
        }
        boolean bl = this.pageIndex < this.getTotalCount() / this.pageSize ? true : (hashNext = this.dataTable.getCount() > this.nextCount);
        if (this.pageIndex > this.getTotalCount() / this.pageSize) {
            boolean bl2 = hashNext = this.pageIndex < this.getTotalCount() / this.pageSize;
        }
        if ((this.nextCount == this.pageSize || this.pageIndex == -1) && this.pageIndex < this.getTotalCount() / this.pageSize) {
            ++this.pageIndex;
            if (this.pageIndex >= this.getTotalCount() / this.pageSize && this.pageIndex > 0 && this.getTotalCount() - (this.pageIndex == 0 ? 1 : this.pageIndex) * this.pageSize <= 0) {
                hashNext = false;
            }
        }
        if (!hashNext && this.tempTable != null) {
            try {
                this.tempTable.close();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return hashNext;
    }

    @Override
    public ArrayList<Object> next() {
        if (this.pageSize == this.nextCount && this.regionData.getType() != DataTableType.TABLE.getValue()) {
            this.nextCount = 0;
        }
        ++this.nextCount;
        return this.getCurrPageDataRowSet();
    }

    @Override
    public MidstoreTableData getRegionData() {
        return this.regionData;
    }

    @Override
    public boolean isFloatRegion() {
        return this.regionData.getType() != DataTableType.TABLE.getValue();
    }

    @Override
    public List<ExportFieldDefine> getFieldDataList() {
        TableDefine td = null;
        String tableName = "";
        ArrayList<ExportFieldDefine> fieldDefines = new ArrayList<ExportFieldDefine>();
        if (null != this.listFieldDefine && !this.listFieldDefine.isEmpty()) {
            for (FieldDefine item : this.listFieldDefine) {
                if (td != null && !td.getKey().equals(item.getOwnerTableKey())) {
                    try {
                        td = this.dataDefinitionRuntimeController.queryTableDefine(item.getOwnerTableKey());
                    }
                    catch (Exception e) {
                        log.info("\u67e5\u5b58\u50a8\u8868\u51fa\u9519{}", e);
                    }
                } else if (td == null) {
                    try {
                        td = this.dataDefinitionRuntimeController.queryTableDefine(item.getOwnerTableKey());
                    }
                    catch (Exception e) {
                        log.info("\u67e5\u5b58\u50a8\u8868\u51fa\u9519{}", e);
                    }
                }
                tableName = td != null ? td.getCode() : this.dataTableMap.get(item.getOwnerTableKey());
                ExportFieldDefine fd = new ExportFieldDefine(item.getTitle(), tableName + "." + item.getCode(), item.getSize().intValue(), item.getType().getValue());
                fd.setValueType(item.getValueType().getValue());
                if (td != null && this.dataTableMap.get(td.getKey()).equals(this.dataTableMap.get(item.getOwnerTableKey()))) {
                    fd.setTableCode(td.getCode());
                } else if (td != null && !this.dataTableMap.get(td.getKey()).equals(this.dataTableMap.get(item.getOwnerTableKey()))) {
                    try {
                        td = this.dataDefinitionRuntimeController.queryTableDefine(item.getOwnerTableKey());
                        fd.setTableCode(td.getCode());
                    }
                    catch (Exception e) {
                        log.info("\u67e5\u5b58\u50a8\u8868\u51fa\u9519{}", e);
                        fd.setTableCode(item.getCode().split("\\.")[0]);
                    }
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
    public int getTotalCount() {
        if (this.dataTable == null) {
            return 0;
        }
        return this.dataTable.getTotalCount();
    }

    public ArrayList<Object> getCurrPageDataRowSet() throws DataTypeException {
        ArrayList<Object> row = new ArrayList<Object>();
        if (this.nextCount == 1) {
            this.executeQuery();
        }
        if (null == this.listFieldDefine || this.listFieldDefine.isEmpty()) {
            return row;
        }
        if (this.dataTable == null) {
            return row;
        }
        if (this.dataRows != null) {
            return this.dataRows.get(this.nextCount - 1);
        }
        int ordinalIndex = -1;
        if (this.regionData.getType() == DataTableType.TABLE.getValue() && this.tableContext.getSortFields() != null && !this.tableContext.getSortFields().isEmpty() && this.dataRows == null) {
            this.dataRows = new ArrayList<ArrayList<Object>>();
            String ordinal = this.tableContext.getSortFields().get(0);
            for (int index = 0; index < this.dataTable.getCount(); ++index) {
                IDataRow dataRow = this.dataTable.getItem(index);
                ArrayList<String> row1 = new ArrayList<String>();
                for (FieldDefine def : this.listFieldDefine) {
                    if (ordinalIndex == -1 && def.getCode().equals(ordinal)) {
                        ordinalIndex = index;
                    }
                    try {
                        Object object;
                        String data = dataRow.getAsString(def);
                        if (data == null && (object = dataRow.getKeyValue(def)) != null) {
                            data = object.toString();
                        }
                        data = this.dataTransfer(def, data);
                        if (this.multistageUnitReplace != null && def.getCode().equals("MDCODE")) {
                            data = this.multistageUnitReplace.getSuperiorCode(data);
                        }
                        row1.add(data);
                    }
                    catch (DataTypeException e) {
                        log.info("\u6570\u636e\u7c7b\u578b\u8f6c\u6362\u5f02\u5e38{}", e);
                    }
                }
                this.dataRows.add(row1);
            }
        }
        final int sortIndex = ordinalIndex;
        if (this.dataRows != null && ordinalIndex != -1) {
            Collections.sort(this.dataRows, new Comparator<ArrayList<Object>>(){

                @Override
                public int compare(ArrayList<Object> o1, ArrayList<Object> o2) {
                    if (Double.parseDouble(o1.get(sortIndex).toString()) < Double.parseDouble(o2.get(sortIndex).toString())) {
                        return -1;
                    }
                    if (o1.get(sortIndex) == o2.get(sortIndex)) {
                        return 0;
                    }
                    return 1;
                }
            });
        }
        if (this.dataRows != null && !this.dataRows.isEmpty()) {
            return this.dataRows.get(this.nextCount - 1);
        }
        if (this.nextCount <= this.dataTable.getCount()) {
            IDataRow dataRow = this.dataTable.getItem(this.nextCount - 1);
            for (FieldDefine def : this.listFieldDefine) {
                try {
                    Object object;
                    String data = dataRow.getAsString(def);
                    if (data == null && (object = dataRow.getKeyValue(def)) != null) {
                        data = object.toString();
                    }
                    data = this.dataTransfer(def, data);
                    if (this.multistageUnitReplace != null && def.getCode().equals("MDCODE")) {
                        data = this.multistageUnitReplace.getSuperiorCode(data);
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

    public String getRowDimension(FieldDefine def) {
        String dimension = null;
        if (!this.rowDimension.containsKey(def.getKey())) {
            ColumnModelDefine keyColumn;
            DataModelDefinitionsCache dataModelDefinitionsCache = null;
            try {
                com.jiuqi.np.dataengine.executors.ExecutorContext context = Objects.nonNull(this.tbcontext1) ? this.tbcontext1 : this.getExecutorContext(this.tableContext);
                dataModelDefinitionsCache = context.getCache().getDataModelDefinitionsCache();
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
    public String dataTransfer(FieldDefine def, String data) {
        IEntityTable iEntityTable;
        if (null == data) {
            return "";
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
            DateUtil.getFormatDatetime((String)data, (String)"yyyy-MM-dd");
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_TIME)) {
            DateUtil.getFormatDatetime((String)data, (String)"HH:mm:ss");
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
            DateUtil.getFormatDatetime((String)data, (String)"yyyy-MM-dd HH:mm:ss");
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_UUID) || def.getType().equals((Object)FieldType.FIELD_TYPE_STRING)) {
            for (FieldDefine bizKey : this.bizKeyFieldDef) {
                if (this.tableContext.getExpEntryFields().equals((Object)ExpViewFields.KEY)) {
                    IEntityTable iEntityTable2;
                    enumKey = this.enumDatakeys.get(def.getKey());
                    if (null != enumKey && null != (iEntityTable2 = this.enumDataValues.get(enumKey))) {
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
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_FILE) && this.tableContext.isAttachment()) {
            data = this.attachmentTransfer(data);
        } else if (def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) && this.tableContext.isAttachment()) {
            data = this.attachmentTransfer(data);
        } else if (null != enumKey && null != (iEntityTable = this.enumDataValues.get(enumKey))) {
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
        List fileInfos = this.fileInfoService.getFileInfoByGroup(data, this.tableContext.getAttachmentArea(), FileStatus.AVAILABLE);
        if (null == fileInfos || fileInfos.size() == 0) {
            return data;
        }
        StringBuffer buffer = new StringBuffer();
        for (FileInfo fileInfo : fileInfos) {
            this.attamentFiles.add(fileInfo);
            buffer.append("Attament/").append(fileInfo.getKey() + fileInfo.getExtension()).append(";");
        }
        if (buffer.length() > 0) {
            data = buffer.deleteCharAt(buffer.length() - 1).toString();
        }
        return data;
    }

    private ArrayList<Object> getDataRowSetByPage(int pageIndex) {
        if (pageIndex > this.getTotalCount() / this.pageSize) {
            pageIndex = this.getTotalCount() / this.pageSize;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        this.pageIndex = pageIndex;
        return this.getCurrPageDataRowSet();
    }

    @Override
    public List<FileInfo> getAttamentFiles() {
        return this.attamentFiles;
    }

    @Override
    @Deprecated
    public List<ExportEntity> getEntitys() {
        ArrayList<ExportEntity> entitys = new ArrayList<ExportEntity>();
        for (IEntityTable item : this.entityTable) {
            List allRows = item.getAllRows();
            ExportEntity expEntity = new ExportEntity();
            ArrayList<EntityDatas> entity = new ArrayList<EntityDatas>();
            for (IEntityRow row : allRows) {
                EntityDatas en = new EntityDatas();
                en.setTitle(row.getTitle());
                en.setCode(row.getCode());
                String parentEntityKey = row.getParentEntityKey();
                String[] parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath();
                if (null != parentEntityKey) {
                    String parentCode = item.findByEntityKey(parentEntityKey).getCode();
                    en.setParentCode(parentCode);
                }
                if (null != parentsEntityKeyDataPath && parentsEntityKeyDataPath.length > 0) {
                    String parentPath = "";
                    for (int i = 0; i < parentsEntityKeyDataPath.length; ++i) {
                        parentPath = parentPath + "/" + item.findByEntityKey(parentsEntityKeyDataPath[i]).getCode();
                    }
                    en.setParentPath(parentPath);
                }
                entity.add(en);
            }
            expEntity.setDatas(entity);
            IFieldsInfo fieldsInfo = item.getFieldsInfo();
            int count = fieldsInfo.getFieldCount();
            for (int i = 0; i < count; ++i) {
                IEntityAttribute fieldByIndex = fieldsInfo.getFieldByIndex(i);
                if (!fieldByIndex.getCode().equals("CODE")) continue;
                expEntity.setTitle(fieldByIndex.getTitle());
                expEntity.setTableName(item.getEntityTableDefine().getName());
            }
            entitys.add(expEntity);
        }
        return entitys;
    }

    @Override
    public DimensionValueSet importDatas(List<Object> row) throws Exception {
        if (!this.checkCanUpload(row)) {
            return new DimensionValueSet();
        }
        ArrayList<String> rowDim = new ArrayList<String>();
        if (this.hasBizKey) {
            for (int i = 0; i < this.bizKeyFieldDef.size(); ++i) {
                Object o;
                IEntityRow findByCode;
                IEntityTable iEntityTable;
                TableModelDefine entityTableDefine;
                Set<Object> set = null;
                if (this.hasDimField) {
                    boolean add = true;
                    for (DataField df : this.floatKeys) {
                        if (!df.getCode().equals(this.bizKeyFieldDef.get(i).getCode())) continue;
                        add = false;
                        break;
                    }
                    if (add) {
                        if (this.c.get(i) == null) {
                            set = new HashSet();
                            this.c.put(i, set);
                        } else {
                            set = this.c.get(i);
                        }
                    }
                } else if (this.c.get(i) == null) {
                    set = new HashSet();
                    this.c.put(i, set);
                } else {
                    set = this.c.get(i);
                }
                if (this.tableContext.isValidEntityExist() && !this.bizKeyFieldDef.get(i).getCode().contains("PERIOD") && !this.entityTable.isEmpty() && ((entityTableDefine = (iEntityTable = this.entityTable.get(0)).getEntityTableDefine()).getCode().equals(this.bizKeyFieldDef.get(i).getCode()) || "MDCODE".equals(this.bizKeyFieldDef.get(i).getCode())) && null == (findByCode = iEntityTable.findByCode(row.get(this.bizindex.get(i)).toString()))) {
                    if (this.unImport.get("unit_inexistence") == null) {
                        HashSet unitcode = new HashSet();
                        this.unImport.put("unit_inexistence", unitcode);
                    }
                    Set<String> unitCodes = this.unImport.get("unit_inexistence");
                    unitCodes.add(row.get(this.bizindex.get(i)).toString());
                }
                if ((o = this.dataTransferOri(this.bizKeyFieldDef.get(i), row.get(this.bizindex.get(i)).toString())) == null) continue;
                if (set != null) {
                    rowDim.add(o.toString());
                    set.add(o.toString());
                    continue;
                }
                if (this.regionData.getType() != DataTableType.TABLE.getValue()) continue;
                rowDim.add(o.toString());
            }
        }
        int descCount = 0;
        if (this.regionData.getType() != DataTableType.TABLE.getValue()) {
            this.getDescartesCount();
        }
        if (this.pendingRows.size() >= this.rowSize || descCount >= 9999) {
            this.c.clear();
            this.commit();
            this.pendingRows.add(row);
            this.pendingDVS.add(rowDim);
            return this.pendingRowsDvs(row);
        }
        this.pendingRows.add(row);
        this.pendingDVS.add(rowDim);
        return this.pendingRowsDvs(row);
    }

    private int getDescartesCount() {
        if (this.pendingDVS.isEmpty()) {
            return 1;
        }
        int flag = 1;
        for (Integer key : this.c.keySet()) {
            flag *= this.c.get(key).size() + 1;
        }
        return flag;
    }

    private void organizedDatas() throws Exception {
        this.buildDimValSets();
        this.getReaderDataTable();
        Boolean[] status = new Boolean[this.listFieldDefine.size()];
        if (this.pendingRows.size() > 0) {
            List<Object> row = this.pendingRows.get(0);
            int k = 0;
            for (int f = 0; k < row.size() && f < this.listFieldDefine.size(); ++k, ++f) {
                boolean ct = false;
                for (int m = 0; m < this.bizindex.size(); ++m) {
                    if (k > this.bizindex.get(m)) continue;
                    ct = true;
                    break;
                }
                if (ct) continue;
                FieldDefine field = this.listFieldDefine.get(f);
                this.dataTransferOri(field, row.get(k), status, f);
            }
        }
        for (int i = 0; i < this.pendingRows.size(); ++i) {
            List<Object> row = this.pendingRows.get(i);
            DimensionValueSet rowDimValSet = this.pendingRowsDvs.get(i);
            if (null != this.impDimensionValueSet && !this.impDimensionValueSet.isAllNull() && null != this.impDimensionValueSet.getValue("DATATIME") && null != rowDimValSet && null == rowDimValSet.getValue("DATATIME") && !this.dataTableMap.get(this.listFieldDefine.get(0).getOwnerTableKey()).contains("FMDM")) {
                rowDimValSet.setValue("DATATIME", this.impDimensionValueSet.getValue("DATATIME"));
            } else if (rowDimValSet != null && (rowDimValSet.size() == 0 || rowDimValSet.size() == 1 && rowDimValSet.getValue("RECORDKEY") != null)) {
                rowDimValSet.combine(this.tableContext.getDimensionSet());
            }
            IDataRow dataRow = null;
            IDataRow dataRow1 = null;
            try {
                if (rowDimValSet != null && rowDimValSet.isAllNull()) {
                    rowDimValSet = this.dimensionValueSet;
                }
                if (this.regionData.getType() == DataTableType.TABLE.getValue()) {
                    dataRow = this.readerDataTable.findRow(rowDimValSet);
                    if (null == dataRow) {
                        dataRow = this.readerDataTable.appendRow(rowDimValSet);
                    }
                } else if (this.tableContext.getFloatImpOpt() == 2) {
                    if (this.openForUpdate != null) {
                        dataRow = this.openForUpdate.addInsertedRow(rowDimValSet);
                    } else if (this.readerDataTable != null) {
                        dataRow = this.readerDataTable.appendRow(rowDimValSet);
                    }
                } else if (this.tableContext.getFloatImpOpt() == 1 || this.tableContext.getFloatImpOpt() == 0) {
                    DimensionValueSet rowDim = new DimensionValueSet(rowDimValSet);
                    rowDim.clearValue("RECORDKEY");
                    dataRow = this.readerDataTable.findRow(rowDim);
                    if (dataRow == null) {
                        dataRow = this.readerDataTable.appendRow(rowDimValSet);
                    }
                }
            }
            catch (Exception e1) {
                log.info("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u6784\u9020\u884c\u51fa\u9519{}", (Object)e1.getMessage(), (Object)e1);
                throw e1;
            }
            if (this.readerDataTable != null && this.readerDataTable.getCount() > 0) {
                dataRow1 = this.readerDataTable.getItem(0);
            }
            int k = 0;
            for (int f = 0; k < row.size() && f < this.listFieldDefine.size(); ++k, ++f) {
                boolean ct = false;
                for (int m = 0; m < this.bizindex.size(); ++m) {
                    if (k != this.bizindex.get(m)) continue;
                    ct = true;
                    break;
                }
                if (ct) continue;
                FieldDefine field = this.listFieldDefine.get(f);
                String fieldCode = this.orderedFieldDef.get(k);
                if (!fieldCode.equals(field.getCode()) && !fieldCode.equals(this.dataTableMap.get(field.getOwnerTableKey()) + "." + field.getCode()) && f >= this.bizindex.size()) {
                    if (k == 0) {
                        char[] charArray = fieldCode.toCharArray();
                        char[] copy = new char[charArray.length - 1];
                        for (int j = 0; j < copy.length; ++j) {
                            copy[j] = charArray[j + 1];
                        }
                        String v = String.valueOf(copy);
                        if (!v.equals(field.getCode()) && !v.equals(this.dataTableMap.get(field.getOwnerTableKey()) + "." + field.getCode())) {
                            --f;
                            continue;
                        }
                    } else {
                        --f;
                        continue;
                    }
                }
                Object data = row.get(k);
                if (status[f] == null || status[f].booleanValue()) {
                    data = this.dataTransferOri(field, data);
                }
                dataRow.setValue(field, data);
            }
            if (this.regionData.getType() == DataTableType.TABLE.getValue() || this.tableContext.getFloatImpOpt() == 1) continue;
            for (FieldDefine floatOrder : this.floatOrders) {
                if (floatOrder.getCode().equals("FLOATORDER") && null != dataRow1 && null != dataRow1.getValue(floatOrder).getAsString()) {
                    dataRow.setValue(floatOrder, (Object)((double)Float.parseFloat(dataRow1.getValue(floatOrder).getAsString().replace("E", "")) + 100.0 * (double)this.floatIncrease));
                    ++this.floatIncrease;
                    continue;
                }
                if (!floatOrder.getCode().equals("FLOATORDER")) continue;
                dataRow.setValue(floatOrder, (Object)(100.0 * (double)this.floatIncrease));
                ++this.floatIncrease;
            }
        }
    }

    private void checkQualifier(DimensionValueSet dimensionValueSet) throws Exception {
        IoQualifier qualifier = this.tableContext.getIoQualifier();
        if (null == qualifier) {
            return;
        }
    }

    private DimensionValueSet pendingRowsDvs(List<Object> row) {
        IoQualifier qualifier;
        Object object;
        int j;
        DimensionValueSet rowDimValSet = new DimensionValueSet();
        String unitCode = "";
        boolean hasBizOrder = false;
        if (this.hasBizKey && this.rowDimensionName == null) {
            this.rowDimensionName = new HashMap<Integer, String>();
            for (j = 0; j < this.bizKeyFieldDef.size(); ++j) {
                if (this.bizKeyFieldDef.get(j).getCode().equals("BIZKEYORDER")) {
                    rowDimValSet.setValue("RECORDKEY", row.get(this.bizindex.get(j)));
                    continue;
                }
                object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
                String dimensionName = this.dataAssist.getDimensionName(this.bizKeyFieldDef.get(j));
                rowDimValSet.setValue(dimensionName, object);
                if (this.bizKeyFieldDef.get(j).getCode().equals("MDCODE")) {
                    unitCode = row.get(this.bizindex.get(j)).toString();
                }
                this.rowDimensionName.put(j, dimensionName);
            }
        } else if (this.hasBizKey && this.rowDimensionName != null) {
            for (j = 0; j < this.bizKeyFieldDef.size(); ++j) {
                if (this.bizKeyFieldDef.get(j).getCode().equals("BIZKEYORDER")) {
                    rowDimValSet.setValue("RECORDKEY", row.get(this.bizindex.get(j)));
                    continue;
                }
                object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
                rowDimValSet.setValue(this.rowDimensionName.get(j), object);
                if (!this.bizKeyFieldDef.get(j).getCode().equals("MDCODE")) continue;
                unitCode = row.get(this.bizindex.get(j)).toString();
            }
        }
        if ((qualifier = this.tableContext.getIoQualifier()) != null) {
            try {
                boolean isUnChecked = true;
                boolean isAccess = false;
                for (DimensionValueSet dim : this.access) {
                    if (!rowDimValSet.isAllNull()) {
                        if (rowDimValSet.compareTo(dim) != 0) break;
                        isUnChecked = false;
                        isAccess = true;
                        break;
                    }
                    if (this.tableContext.getDimensionSet().compareTo(dim) != 0) continue;
                    isUnChecked = false;
                    isAccess = true;
                    break;
                }
                for (DimensionValueSet dim : this.noAccess) {
                    if (!rowDimValSet.isAllNull()) {
                        if (rowDimValSet.compareTo(dim) != 0) break;
                        isUnChecked = false;
                        break;
                    }
                    if (this.tableContext.getDimensionSet().compareTo(dim) != 0) continue;
                    isUnChecked = false;
                    isAccess = false;
                    break;
                }
                if (!isUnChecked && !isAccess && this.pendingRows.size() >= 1) {
                    this.pendingRows.remove(this.pendingRows.size() - 1);
                    this.pendingDVS.remove(rowDimValSet);
                    return null;
                }
                if (isUnChecked) {
                    if (rowDimValSet.isAllNull()) {
                        this.checkQualifier(this.tableContext.getDimensionSet());
                        this.access.add(this.tableContext.getDimensionSet());
                    } else {
                        this.checkQualifier(rowDimValSet);
                        this.access.add(rowDimValSet);
                    }
                }
            }
            catch (Exception e) {
                if (this.pendingRows.size() >= 1) {
                    this.pendingRows.remove(this.pendingRows.size() - 1);
                    this.pendingDVS.remove(rowDimValSet);
                }
                if (rowDimValSet.isAllNull()) {
                    this.noAccess.add(this.tableContext.getDimensionSet());
                } else {
                    this.noAccess.add(rowDimValSet);
                }
                if (this.unImport.get("unit_noaccess") == null) {
                    HashSet unitNoAccess = new HashSet();
                    this.unImport.put("unit_noaccess", unitNoAccess);
                }
                Set<String> unitCodes = this.unImport.get("unit_noaccess");
                unitCodes.add(unitCode);
                log.debug("\u6ca1\u6709\u6743\u9650\uff0c\u6570\u636e\u4e0d\u8fdb\u884c\u5bfc\u5165:{}", (Object)e.getMessage());
                return null;
            }
        }
        if (this.tableContext.getFloatImpOpt() == 2 || this.tableContext.getFloatImpOpt() == 0) {
            UUID uuid = UUID.randomUUID();
            for (FieldDefine item : this.bizKeyFieldDefCp) {
                if (!item.getCode().equals("BIZKEYORDER")) continue;
                hasBizOrder = true;
            }
            if (hasBizOrder && this.bizKeyFieldDefCp.size() != this.bizKeyFieldDef.size()) {
                rowDimValSet.setValue("RECORDKEY", (Object)uuid.toString());
            }
        }
        this.pendingRowsDvs.add(rowDimValSet);
        DimensionValueSet rowDimValSets = new DimensionValueSet(rowDimValSet);
        for (FieldDefine def : this.bizKeyFieldDefCp) {
            if (null != rowDimValSets.getValue(this.dataAssist.getDimensionName(def)) || null == def.getDefaultValue()) continue;
            rowDimValSets.setValue(this.dataAssist.getDimensionName(def), (Object)def.getDefaultValue().replace("\"", ""));
        }
        return rowDimValSets;
    }

    private void buildDimValSets() {
        if (this.tableContext.getFloatImpOpt() != 1) {
            for (List<String> it : this.processedDVS) {
                if (!this.pendingDVS.contains(it)) continue;
                this.pendingDVS.remove(it);
            }
        }
        if (!this.pendingDVS.isEmpty() && !this.pendingDVS.iterator().next().isEmpty()) {
            int i;
            DimensionValueSet dim = new DimensionValueSet();
            ArrayList<Object[]> dims = new ArrayList<Object[]>();
            Object[][] revertDims = new Object[this.bizKeyFieldDef.size()][this.pendingDVS.size()];
            for (List<String> dvs : this.pendingDVS) {
                Object[] e = new Object[dvs.size()];
                for (int i2 = 0; i2 < e.length; ++i2) {
                    e[i2] = dvs.get(i2);
                }
                dims.add(e);
            }
            for (i = 0; i < dims.size() && i < revertDims[0].length; ++i) {
                for (int j = 0; j < ((Object[])dims.get(i)).length && j < revertDims.length; ++j) {
                    revertDims[j][i] = ((Object[])dims.get(i))[j];
                }
            }
            for (i = 0; i < this.bizKeyFieldDef.size(); ++i) {
                if (this.bizKeyFieldDef.get(i).getCode().equals("BIZKEYORDER")) {
                    dim.setValue("RECORDKEY", Arrays.asList(revertDims[i]));
                    continue;
                }
                HashSet<Object> diff = new HashSet<Object>();
                diff.addAll(Arrays.asList(revertDims[i]));
                String dimensionName = this.dataAssist.getDimensionName(this.bizKeyFieldDef.get(i));
                if (this.hasDimField && this.regionData.getType() != DataTableType.TABLE.getValue()) {
                    boolean add = true;
                    for (DataField df : this.floatKeys) {
                        if (!df.getCode().equals(this.bizKeyFieldDef.get(i).getCode())) continue;
                        add = false;
                        break;
                    }
                    if (!add) continue;
                    dim.setValue(dimensionName, new ArrayList(diff));
                    continue;
                }
                dim.setValue(dimensionName, new ArrayList(diff));
            }
            this.impDimensionValueSet = dim;
            this.processedDVS.addAll(this.pendingDVS);
        } else {
            this.impDimensionValueSet = null;
        }
    }

    @Override
    public ImportInfo commit() throws Exception {
        this.c.clear();
        if (this.pendingRows.size() == 0) {
            this.pendingRows.clear();
            this.pendingDVS.clear();
            this.pendingRowsDvs.clear();
            log.warn("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u63d0\u4ea4\u4e8b\u52a1\u51fa\u9519:\u63d0\u4ea4\u7684\u6570\u636e\u4e3a\u7a7a");
            return null;
        }
        if (this.pendingRows.isEmpty() && this.pendingDVS.isEmpty()) {
            return null;
        }
        this.organizedDatas();
        this.pendingRows.clear();
        this.pendingDVS.clear();
        for (DimensionValueSet dimensionValueSet : this.pendingRowsDvs) {
            try {
                FieldDefine unitFieldDefine = this.getUnitFieldDefine();
                Object value = dimensionValueSet.getValue(this.dataAssist.getDimensionName(unitFieldDefine));
                if (this.unImport.get("unit_success") == null) {
                    HashSet successUnit = new HashSet();
                    this.unImport.put("unit_success", successUnit);
                }
                Set<String> unitCodes = this.unImport.get("unit_success");
                unitCodes.add(value.toString());
            }
            catch (Exception e) {
                log.info("\u7ec4\u7ec7\u5bfc\u5165\u65e5\u5fd7\u51fa\u9519\uff1a{}" + e.getMessage());
            }
        }
        this.pendingRowsDvs.clear();
        try {
            if (!this.dbRows.isEmpty()) {
                for (IDataRow iDataRow : this.dbRows) {
                    this.readerDataTable.deleteRow(iDataRow.getRowKeys());
                }
            }
            if (this.readerDataTable != null) {
                this.readerDataTable.commitChanges(true, true);
            } else if (this.openForUpdate != null) {
                this.openForUpdate.commitChanges(true);
            }
        }
        catch (Exception e) {
            log.info("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u63d0\u4ea4\u4e8b\u52a1\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
            throw e;
        }
        return null;
    }

    private void getReaderDataTable() throws Exception {
        if (null != this.impDimensionValueSet && !this.impDimensionValueSet.isAllNull()) {
            DimensionValueSet dv;
            if (this.impDimensionValueSet.size() == 1 && null != this.impDimensionValueSet.getValue("VERSIONID")) {
                dv = new DimensionValueSet(this.tableContext.getDimensionSet());
                dv.setValue(this.impDimensionValueSet.getName(0), this.impDimensionValueSet.getValue(0));
                this.dataQuery.setMasterKeys(dv);
            } else if (this.impDimensionValueSet.size() == 0) {
                dv = new DimensionValueSet(this.tableContext.getDimensionSet());
                for (FieldDefine bizDef : this.bizKeyFieldDefCp) {
                    if (null == dv.getValue(this.dataAssist.getDimensionName(bizDef))) continue;
                    dv.setValue(this.dataAssist.getDimensionName(bizDef), this.dataTransferOri(bizDef, dv.getValue(this.dataAssist.getDimensionName(bizDef))));
                }
                this.dataQuery.setMasterKeys(dv);
            } else {
                if (null == this.impDimensionValueSet.getValue("DATATIME")) {
                    this.impDimensionValueSet.setValue("DATATIME", this.tableContext.getDimensionSet().getValue("DATATIME"));
                }
                this.dataQuery.setMasterKeys(this.impDimensionValueSet);
            }
        } else {
            this.dataQuery.setMasterKeys(this.tableContext.getDimensionSet());
        }
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.getExecutorContext(this.tableContext);
        boolean throwsExeception = false;
        if (this.regionData.getType() == DataTableType.TABLE.getValue()) {
            this.dataQuery.setIgnoreDefaultOrderBy(true);
        }
        try {
            DimensionValueSet masterKeys;
            if (this.tableContext.getFloatImpOpt() == 2 && this.regionData.getType() != DataTableType.TABLE.getValue()) {
                if (this.impDimensionValueSet != null && !this.impDimensionValueSet.isAllNull()) {
                    if (this.tableContext.isMultistageEliminateBizKey()) {
                        masterKeys = this.dataQuery.getMasterKeys();
                        DimensionValueSet masterKeysCp = new DimensionValueSet(masterKeys);
                        masterKeysCp.clearValue("RECORDKEY");
                        this.dataQuery.setMasterKeys(masterKeysCp);
                    }
                    if (this.tableContext.getDimensionSet() != null && this.isMultiRegionExistDefaultValImport) {
                        masterKeys = this.dataQuery.getMasterKeys();
                        DimensionValueSet masterKeysTmp = this.setRegionDefaultDimension(masterKeys);
                        this.dataQuery.setMasterKeys(masterKeysTmp);
                    }
                    if (!(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                        this.openForUpdate = this.dataQuery.openForUpdate(executorContext, true);
                    }
                } else if (this.openForUpdate == null && this.impDimensionValueSet == null) {
                    if (!(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                        this.openForUpdate = this.dataQuery.openForUpdate(executorContext, false);
                    }
                } else if (this.openForUpdate == null) {
                    if (!(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                        this.openForUpdate = this.dataQuery.openForUpdate(executorContext, false);
                    }
                } else if (this.openForUpdate != null && !(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                    this.openForUpdate = this.dataQuery.openForUpdate(executorContext, false);
                }
                this.readerDataTable = null;
            } else {
                masterKeys = this.dataQuery.getMasterKeys();
                throwsExeception = this.checkMainDim(throwsExeception, masterKeys);
                DimensionValueSet masterKeysTmp = this.setRegionDefaultDimension(masterKeys);
                this.dataQuery.setMasterKeys(masterKeysTmp);
                throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys());
                if (!throwsExeception) {
                    this.readerDataTable = this.dataQuery.executeQuery(executorContext);
                }
                this.openForUpdate = null;
            }
            this.floatIncrease = 1;
        }
        catch (Exception e) {
            log.info("\u63d0\u4ea4\u4e8b\u52a1\u540e\u67e5\u8be2\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
        if (throwsExeception) {
            log.error("\u5bfc\u5165\u6570\u636e\u4e3b\u7ef4\u5ea6\u3010\u5355\u4f4d\u3001\u65f6\u671f\u3011\u4e0d\u80fd\u4e3a\u7a7a  !");
            throw new Exception("\u5bfc\u5165\u6570\u636e\u4e3b\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
        }
    }

    private boolean checkMainDim(boolean throwsExeception, DimensionValueSet masterKeys) throws Exception {
        if (masterKeys.getValue(this.dataAssist.getDimensionName(this.getUnitFieldDefine())) == null || masterKeys.getValue("DATATIME") == null) {
            throwsExeception = true;
        }
        return throwsExeception;
    }

    private DimensionValueSet setRegionDefaultDimension(DimensionValueSet masterKeys) throws Exception {
        DimensionValueSet masterKeysTmp = new DimensionValueSet(masterKeys);
        List<EntityDefaultValue> regionSettingsDefault = this.regionData.getRegionEntityDefaultValue();
        if (!CollectionUtils.isEmpty(regionSettingsDefault)) {
            for (EntityDefaultValue entityDefaultValue : regionSettingsDefault) {
                EntityValueType entityType = entityDefaultValue.getEntityValueType();
                String defalutVal = entityDefaultValue.getItemValue();
                if (EntityValueType.DATA_ITEM == entityType) {
                    masterKeysTmp.setValue(this.entityMetaService.getDimensionName(entityDefaultValue.getEntityId()), (Object)defalutVal);
                    continue;
                }
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(entityDefaultValue.getFieldKey());
                masterKeysTmp.setValue(this.dataAssist.getDimensionName(field), (Object)defalutVal);
            }
        }
        return masterKeysTmp;
    }

    private void dataTransferOri(FieldDefine define, Object data, Boolean[] status, int index) {
        Object transValue;
        status[index] = define.getType().equals((Object)FieldType.FIELD_TYPE_DATE) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_TIME) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_FILE) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC) ? Boolean.valueOf(true) : ((transValue = this.dataTransferOri(define, data)) != null && data != null && transValue.equals(data) ? Boolean.valueOf(false) : Boolean.valueOf(true)))))));
    }

    public Object dataTransferOri(FieldDefine def, Object data) {
        if (null == data) {
            return null;
        }
        String enumKey = null;
        enumKey = this.enumDatakeys.get(def.getKey());
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
                FileInfo uploadByGroup = this.fileAreaService.uploadByGroup(file.getName(), groupKey, buffer);
                this.suoluotu(buffer, file.getName(), uploadByGroup.getKey());
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
                        return findByCode.getEntityKeyData() != null ? findByCode.getEntityKeyData() : (findByCode.getCode() != null ? findByCode.getCode() : data.toString());
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

    @Override
    public List<FieldDefine> getBizFieldDefList() {
        return this.bizKeyFieldDef;
    }

    private void setPage(IDataQuery item, int pageSize, int pageIndex) {
        if (this.regionData.getType() != DataTableType.TABLE.getValue()) {
            item.setPagingInfo(pageSize, pageIndex);
        }
    }

    @Override
    public FieldDefine getUnitFieldDefine() throws Exception {
        FieldDefine unitFieldDefine = null;
        for (int i = 0; i < this.bizKeyFieldDefCp.size(); ++i) {
            if (!this.bizKeyFieldDefCp.get(i).getCode().equals("MDCODE")) continue;
            unitFieldDefine = this.bizKeyFieldDefCp.get(i);
        }
        if (unitFieldDefine != null) {
            return unitFieldDefine;
        }
        return null;
    }

    @Override
    public Map<String, Set<String>> getUnImport() throws Exception {
        FieldDefine unitFieldDefine = this.getUnitFieldDefine();
        for (DimensionValueSet item : this.noAccess) {
            Object value = item.getValue(this.dataAssist.getDimensionName(unitFieldDefine));
            if (this.unImport.get("unit_noaccess") == null) {
                HashSet unitNoAccess = new HashSet();
                this.unImport.put("unit_noaccess", unitNoAccess);
            }
            Set<String> unitCodes = this.unImport.get("unit_noaccess");
            for (IEntityTable entity : this.entityTable) {
                IEntityRow entityKey = entity.findByEntityKey(value.toString());
                if (null == entityKey) continue;
                value = entityKey.getCode() + "|" + entityKey.getTitle();
            }
            unitCodes.add(value.toString());
        }
        Set<String> unitSuccess = this.unImport.get("unit_success");
        HashSet unitSuccessCp = new HashSet();
        if (unitSuccess == null) {
            unitSuccess = new HashSet<String>();
        }
        return this.unImport;
    }

    @Override
    public String getCodeTitle(String code) {
        for (IEntityTable entity : this.entityTable) {
            IEntityRow entityKey = entity.findByEntityKey(code);
            if (null != entityKey) {
                code = entityKey.getCode() + "|" + entityKey.getTitle();
                return code;
            }
            IEntityRow entityCode = entity.findByCode(code);
            if (null == entityCode) continue;
            code = entityCode.getCode() + "|" + entityCode.getTitle();
            return code;
        }
        return code;
    }

    @Override
    public int getDbDataCount() {
        FieldDefine fieldDefine = this.listFieldDefine.get(0);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        IGroupingQuery groupingQuery = this.dataAccessProvider.newGroupingQuery(queryEnvironment);
        groupingQuery.setWantDetail(false);
        groupingQuery.setSortGroupingAndDetailRows(false);
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", this.tableContext.getDimensionSet().getValue("DATATIME"));
        groupingQuery.setMasterKeys(dimensionValueSet);
        int columnIndex = groupingQuery.addColumn(fieldDefine);
        groupingQuery.setGatherType(columnIndex, FieldGatherType.FIELD_GATHER_COUNT);
        IGroupingTable executeReader = null;
        try {
            executeReader = groupingQuery.executeReader(executorContext);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (executeReader != null) {
            IDataRow item = executeReader.getItem(0);
            log.info(item.getAsString(fieldDefine));
            return Integer.parseInt(item.getAsString(fieldDefine).split("\\.")[0]);
        }
        return 0;
    }

    private boolean checkCanUpload(List<Object> row) throws Exception {
        boolean canUpload = true;
        List<EntityDefaultValue> regionSettingsDefault = this.regionData.getRegionEntityDefaultValue();
        if (!CollectionUtils.isEmpty(this.orderedFieldDef) && !CollectionUtils.isEmpty(regionSettingsDefault)) {
            for (EntityDefaultValue entityDefaultValue : regionSettingsDefault) {
                Object val;
                Optional<FieldDefine> field;
                EntityValueType entityType = entityDefaultValue.getEntityValueType();
                String defaultVal = entityDefaultValue.getItemValue();
                String fieldCode = null;
                if (EntityValueType.DATA_ITEM == entityType) {
                    field = this.listFieldDefine.stream().filter(e -> entityDefaultValue.getEntityId().equals(e.getEntityKey())).findAny();
                    if (field.isPresent()) {
                        fieldCode = field.get().getCode();
                    }
                } else {
                    field = this.listFieldDefine.stream().filter(e -> entityDefaultValue.getFieldKey().equals(e.getKey())).findAny();
                    if (field.isPresent()) {
                        fieldCode = field.get().getCode();
                    }
                }
                if (!Objects.isNull(val = this.findRowData(fieldCode, row)) && (!Objects.nonNull(val) || val.equals(defaultVal))) continue;
                FieldDefine field2 = this.getUnitFieldDefine();
                if (Objects.nonNull(field2)) {
                    Object unitVal = this.findRowData(field2.getCode(), row);
                    Set<String> noAccess = this.unImport.get("region_nosuccess");
                    if (CollectionUtils.isEmpty(noAccess)) {
                        noAccess = new HashSet<String>();
                    }
                    if (unitVal != null) {
                        noAccess.add(unitVal.toString());
                    } else {
                        noAccess.add("");
                    }
                    this.unImport.put("region_nosuccess", noAccess);
                }
                canUpload = false;
                break;
            }
        }
        return canUpload;
    }

    private Object findRowData(String code, List<Object> row) {
        String targetCode = null;
        int index = 0;
        if (this.regionSettingValIndexMap.containsKey(code)) {
            return row.get(this.orderedFieldDef.indexOf(code));
        }
        Iterator<String> iterator = this.orderedFieldDef.iterator();
        while (iterator.hasNext()) {
            String key;
            targetCode = key = iterator.next();
            if (key.contains(".")) {
                targetCode = key.split(",")[1];
            }
            if (targetCode.equals(code)) {
                this.regionSettingValIndexMap.put(code, index);
                return row.get(index);
            }
            ++index;
        }
        return null;
    }

    @Override
    public void queryToDataRowReader(IMidstoreRegionDataSetReader regionDataSetReader) {
        boolean onlyOneTable = false;
        HashSet dataTableKeys = new HashSet();
        this.listFieldDefine.forEach(e -> dataTableKeys.add(e.getOwnerTableKey()));
        if (dataTableKeys.size() == 1) {
            List dataFieldDeployInfos = this.runtimeDataSchemeService.getDeployInfoByDataTableKey((String)dataTableKeys.iterator().next());
            HashSet tableMoudleKeys = new HashSet();
            dataFieldDeployInfos.forEach(e -> tableMoudleKeys.add(e.getTableName()));
            if (tableMoudleKeys.size() == 1) {
                onlyOneTable = true;
            }
        }
        if (onlyOneTable) {
            try {
                MidstoreDataRowReaderImpl dataRowReader = new MidstoreDataRowReaderImpl(this.listFieldDefine, regionDataSetReader, this);
                com.jiuqi.np.dataengine.executors.ExecutorContext executeContext = this.getExecutorContext(this.tableContext);
                this.dataQuery.queryToDataRowReader(executeContext, (IDataRowReader)dataRowReader);
            }
            catch (Exception e2) {
                log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e2.getMessage(), (Object)e2);
            }
        } else {
            regionDataSetReader.start(this.listFieldDefine);
            while (this.hasNext()) {
                Object rowData = this.next();
                regionDataSetReader.readRowData((List<Object>)rowData);
            }
            regionDataSetReader.finish();
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
}

