/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  net.coobird.thumbnailator.Thumbnails
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.tz.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.common.DataIOTempTableDefine;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MzOrgCodeRepairService;
import com.jiuqi.nr.io.service.impl.ParameterService;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.JioTempTableInfo;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import com.jiuqi.nr.io.tz.service.BatchImportMonitor;
import com.jiuqi.nr.io.tz.service.BatchImportService;
import com.jiuqi.nr.io.tz.service.impl.DefaultBatchImport;
import com.jiuqi.nr.io.tz.service.impl.JioTempTableDao;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class TzRegionDataSet
extends AbstractRegionDataSet
implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(TzRegionDataSet.class);
    private RegionData regionData;
    private TableContext tableContext;
    private boolean importData;
    private ExecutorContext executorContext;
    private int dwNameIndex;
    private String floatLastDw;
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IDataQuery dataQuery;
    private IDataAccessProvider dataAccessProvider;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private List<IEntityTable> entityTable;
    private IEntityQuery entityQuery;
    private FileInfoService fileInfoService;
    private FileService fileService;
    private FileAreaService fileAreaService;
    private IoEntityService ioEntityService;
    private IEntityMetaService iEntityMetaService;
    private DataModelService dataModelService;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private NrIoProperties nrIoProperties;
    private int rowSize;
    private Map<String, String> enumDatakeys;
    private Map<String, IEntityTable> enumDataValues;
    private IDataAssist dataAssist;
    private List<FieldDefine> listFieldDefine;
    private List<FieldDefine> bizKeyFieldDef;
    private List<FieldDefine> bizKeyFieldDefCp;
    private List<FieldDefine> floatOrders;
    private int floatIncrease;
    private boolean hasBizKey;
    private boolean hasDimField;
    private FieldDefine bizKeyOrder;
    private List<DataField> floatKeys;
    private List<String> orderedFieldDef;
    private List<Integer> bizindex;
    private DimensionValueSet dimensionValueSet;
    private String fieldBizKeys;
    private final List<List<Object>> pendingRows;
    private final List<DimensionValueSet> pendingRowsDvs;
    private Set<List<String>> pendingDVS;
    private Set<List<String>> processedDVS;
    private DimensionValueSet impDimensionValueSet;
    private Map<String, String> dataTableMap;
    private JdbcTemplate jdbcTemplate;
    private DataSchemeTmpTable tmpTable;
    private ITempTable jioTempTable;
    private Set<DimensionValueSet> access;
    private Set<DimensionValueSet> noAccess;
    private JioTempTableDao jioTempTableDao;
    private ITempTableManager tempTableManager;
    private List<String> sqlField;
    private Map<String, FieldDefine> bizKeyDimNameMap;
    private Map<String, String> bizKeyFieldMap;
    private boolean addSqlField;
    private AtomicInteger id;
    private List<Object[]> batchValues;
    private BatchImportService batchImportOptions;
    private String destFormKey;
    private String batchSql;
    private Map<Integer, String> rowDimensionName;
    private MzOrgCodeRepairService mzOrgCodeRepairService;
    private int dwIndex;
    private int datatimeIndex;
    private FormSchemeDefine formSchemeDefineImport;
    private Map<String, String> mzOrgCodeRepairCache;

    public TzRegionDataSet(TableContext context, RegionData regionData, List<ExportFieldDefine> importDefine) {
        String tableKey;
        block31: {
            this.regionData = null;
            this.importData = false;
            this.dwNameIndex = 0;
            this.floatLastDw = null;
            this.entityTable = new ArrayList<IEntityTable>();
            this.rowSize = 5000;
            this.enumDatakeys = new HashMap<String, String>();
            this.enumDataValues = new HashMap<String, IEntityTable>();
            this.listFieldDefine = null;
            this.bizKeyFieldDef = new ArrayList<FieldDefine>();
            this.bizKeyFieldDefCp = new ArrayList<FieldDefine>();
            this.floatOrders = new ArrayList<FieldDefine>();
            this.floatIncrease = 1;
            this.hasBizKey = false;
            this.hasDimField = false;
            this.bizKeyOrder = null;
            this.floatKeys = null;
            this.orderedFieldDef = null;
            this.bizindex = new ArrayList<Integer>();
            this.dimensionValueSet = null;
            this.fieldBizKeys = "";
            this.pendingRows = new ArrayList<List<Object>>();
            this.pendingRowsDvs = new ArrayList<DimensionValueSet>();
            this.pendingDVS = new LinkedHashSet<List<String>>();
            this.processedDVS = new LinkedHashSet<List<String>>();
            this.impDimensionValueSet = null;
            this.dataTableMap = new HashMap<String, String>();
            this.access = new HashSet<DimensionValueSet>();
            this.noAccess = new HashSet<DimensionValueSet>();
            this.bizKeyDimNameMap = new HashMap<String, FieldDefine>();
            this.bizKeyFieldMap = new HashMap<String, String>();
            this.addSqlField = true;
            this.id = new AtomicInteger(0);
            this.batchValues = new ArrayList<Object[]>();
            this.dwIndex = -1;
            this.datatimeIndex = -1;
            this.importData = true;
            this.tableContext = context;
            this.regionData = regionData;
            this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
            this.fileInfoService = (FileInfoService)BeanUtil.getBean(FileInfoService.class);
            this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(context));
            this.fileService = (FileService)BeanUtil.getBean(FileService.class);
            this.fileAreaService = this.fileService.area(context.getAttachmentArea());
            this.ioEntityService = (IoEntityService)BeanUtil.getBean(IoEntityService.class);
            this.iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
            this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
            this.nrIoProperties = (NrIoProperties)BeanUtil.getBean(NrIoProperties.class);
            this.jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
            this.jioTempTableDao = (JioTempTableDao)BeanUtil.getBean(JioTempTableDao.class);
            this.tempTableManager = (ITempTableManager)BeanUtil.getBean(ITempTableManager.class);
            this.batchImportOptions = (BatchImportService)BeanUtil.getBean(DefaultBatchImport.class);
            ParameterService parameterService = ParamUtil.getParameterService();
            this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
            if (this.nrIoProperties.getRowsize() != 0) {
                this.rowSize = this.nrIoProperties.getRowsize();
            }
            this.listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(regionData.getKey()));
            this.initLinkViewMap(context, regionData);
            this.dataQuery = this.dataAccessProvider.newDataQuery();
            if (null != context.getDimension() && null != context.getTempTable() && regionData.getType() == 0) {
                this.dataQuery.setTempAssistantTable(context.getDimension(), context.getTempTable());
            }
            tableKey = null;
            if (null != this.listFieldDefine && this.listFieldDefine.size() > 0) {
                this.destFormKey = tableKey = this.listFieldDefine.get(0).getOwnerTableKey();
            }
            try {
                List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
                if (deployInfoByDataTableKey != null && !deployInfoByDataTableKey.isEmpty()) {
                    this.dataTableMap.put(tableKey, ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName());
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
            this.getBizKeysDef(tableKey, queryFieldDefine, true);
            ArrayList<FieldDefine> del = new ArrayList<FieldDefine>();
            for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
                boolean exist = false;
                for (ExportFieldDefine j : importDefine) {
                    FieldDefine code = null;
                    try {
                        code = this.dataAssist.getDimensionField(this.dataTableMap.get(this.bizKeyFieldDef.get(0).getOwnerTableKey()), j.getCode().contains(".") ? j.getCode().split("\\.")[1] : j.getCode());
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (null != code) {
                        j.setCode(code.getCode());
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
            block14: for (FieldDefine i : this.bizKeyFieldDef) {
                for (int j = 0; j < importDefine.size(); ++j) {
                    if (importDefine.get(j).getCode().contains(".")) {
                        if (!importDefine.get(j).getCode().split("\\.")[1].equals(i.getCode())) continue;
                        this.bizindex.add(j);
                        if ("MDCODE".equals(i.getCode())) {
                            this.dwIndex = j;
                        }
                        if (!"DATATIME".equals(i.getCode())) continue block14;
                        this.datatimeIndex = j;
                        continue block14;
                    }
                    if (!importDefine.get(j).getCode().equals(i.getCode())) continue;
                    this.bizindex.add(j);
                    if ("MDCODE".equals(i.getCode())) {
                        this.dwIndex = j;
                    }
                    if (!"DATATIME".equals(i.getCode())) continue block14;
                    this.datatimeIndex = j;
                    continue block14;
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
                if (fieldDefine == null) break block31;
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
        if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            Object var9_21 = null;
            try {
                TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
                this.floatKeys = this.runtimeDataSchemeService.getDataFieldByTableCodeAndKind(tableDefine.getCode(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
                if (this.floatKeys != null && !this.floatKeys.isEmpty()) {
                    this.hasDimField = true;
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        this.createMirrorTable(tableKey);
    }

    private void createMirrorTable(String tableKey) {
        this.tmpTable = this.jioTempTableDao.dataFieldInit(tableKey);
        DataIOTempTableDefine jioTempTableDefine = new DataIOTempTableDefine(new JioTempTableInfo(this.tmpTable));
        try {
            this.jioTempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)jioTempTableDefine);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
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
            fdMapDot.put(fieldDefine.getCode() + "." + fieldDefine.getCode(), fieldDefine);
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
                    io1 = orderedFieldDef.indexOf(o1.getCode() + "." + o1.getCode());
                    io2 = orderedFieldDef.indexOf(o2.getCode() + "." + o2.getCode());
                } else {
                    io1 = orderedFieldDef.indexOf(o1.getCode());
                    io2 = orderedFieldDef.indexOf(o2.getCode());
                }
                for (int i = 0; i < orderedFieldDef.size(); ++i) {
                    if (this.containtTable) {
                        if (!((String)orderedFieldDef.get(i)).equals(o2.getCode() + "." + o2.getCode())) continue;
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
                String dimensionName = this.dataAssist.getDimensionName(biz);
                this.bizKeyDimNameMap.put(dimensionName, biz);
                this.bizKeyFieldMap.put(biz.getKey(), dimensionName);
            }
        }
        catch (Exception e) {
            log.debug("\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u4e3b\u952e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private void initEntityQuery(TableContext context) {
        try {
            String masterKeys = this.runTimeViewController.getFormSchemeEntity(context.getFormSchemeKey());
            if (masterKeys != null) {
                String[] keys;
                for (String key : keys = masterKeys.split(";")) {
                    ExecutorContext executorContext;
                    IEntityTable entityTables;
                    EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
                    if (this.dataAssist.getDimensionName(entityView).equals("DATATIME") || null == (entityTables = this.ioEntityService.getIEntityTable(entityView, context, executorContext = this.getExecutorContext(context)))) continue;
                    this.entityTable.add(entityTables);
                }
            }
        }
        catch (Exception e) {
            log.debug("\u67e5\u8be2\u4e3b\u4f53\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private ExecutorContext getExecutorContext(TableContext context) {
        if (this.executorContext == null) {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setJQReportModel(true);
            this.executorContext = executorContext;
        }
        return this.executorContext;
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
                    selectViewKey = fieldDefine.getEntityKey();
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (null == (queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey))) continue;
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

    @Override
    public RegionData getRegionData() {
        return this.regionData;
    }

    @Override
    public boolean isFloatRegion() {
        return this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue();
    }

    @Override
    public List<ExportFieldDefine> getFieldDataList() {
        TableDefine td = null;
        ArrayList<ExportFieldDefine> fieldDefines = new ArrayList<ExportFieldDefine>();
        if (null != this.listFieldDefine && !this.listFieldDefine.isEmpty()) {
            for (FieldDefine item : this.listFieldDefine) {
                ExportFieldDefine fd = new ExportFieldDefine(item.getTitle(), this.dataTableMap.get(item.getOwnerTableKey()) + "." + item.getCode(), item.getSize(), item.getType().getValue());
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
    public DimensionValueSet importDatas(List<Object> row) throws Exception {
        this.repairCode(row);
        ArrayList<String> rowDim = new ArrayList<String>();
        if (this.hasBizKey) {
            for (int i = 0; i < this.bizKeyFieldDef.size(); ++i) {
                Object o = this.dataTransferOri(this.bizKeyFieldDef.get(i), row.get(this.bizindex.get(i)).toString());
                if (o == null) continue;
                rowDim.add(o.toString());
            }
        }
        if (this.pendingRows.size() >= this.rowSize) {
            this.commitTmpTable();
            this.pendingRows.add(row);
            this.pendingDVS.add(rowDim);
            return this.pendingRowsDvs(row);
        }
        this.pendingRows.add(row);
        this.pendingDVS.add(rowDim);
        return this.pendingRowsDvs(row);
    }

    private DimensionValueSet pendingRowsDvs(List<Object> row) {
        IoQualifier qualifier;
        Object object;
        int j;
        DimensionValueSet rowDimValSet = new DimensionValueSet();
        DimensionValueSet checkedRowDimValSet = new DimensionValueSet();
        boolean hasBizOrder = false;
        if (this.hasBizKey && this.rowDimensionName != null) {
            for (j = 0; j < this.bizKeyFieldDef.size(); ++j) {
                if (this.bizKeyFieldDef.get(j).getCode().equals("BIZKEYORDER")) {
                    rowDimValSet.setValue("RECORDKEY", row.get(this.bizindex.get(j)));
                    continue;
                }
                object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
                rowDimValSet.setValue(this.rowDimensionName.get(j), object);
                if (!(this.bizKeyFieldDef.get(j) instanceof DataFieldDTO) || !((DataFieldDTO)this.bizKeyFieldDef.get(j)).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) continue;
                checkedRowDimValSet.setValue(this.rowDimensionName.get(j), object);
            }
        } else if (this.hasBizKey) {
            this.rowDimensionName = new HashMap<Integer, String>();
            for (j = 0; j < this.bizKeyFieldDef.size(); ++j) {
                if (this.bizKeyFieldDef.get(j).getCode().equals("BIZKEYORDER")) {
                    rowDimValSet.setValue("RECORDKEY", row.get(this.bizindex.get(j)));
                    continue;
                }
                object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
                String dimensionName = this.dataAssist.getDimensionName(this.bizKeyFieldDef.get(j));
                rowDimValSet.setValue(dimensionName, object);
                if (this.bizKeyFieldDef.get(j) instanceof DataFieldDTO && ((DataFieldDTO)this.bizKeyFieldDef.get(j)).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) {
                    checkedRowDimValSet.setValue(dimensionName, object);
                }
                this.rowDimensionName.put(j, dimensionName);
            }
        }
        if ((qualifier = this.tableContext.getIoQualifier()) != null) {
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
                if (!isUnChecked && !isAccess && this.pendingRows.size() >= 1) {
                    this.pendingRows.remove(this.pendingRows.size() - 1);
                    this.pendingDVS.remove(rowDimValSet);
                    return null;
                }
                if (isUnChecked) {
                    if (checkedRowDimValSet.isAllNull()) {
                        this.checkQualifier(this.tableContext.getDimensionSet());
                        this.access.add(this.tableContext.getDimensionSet());
                    } else {
                        this.checkQualifier(checkedRowDimValSet);
                        this.access.add(checkedRowDimValSet);
                    }
                }
            }
            catch (Exception e) {
                if (this.pendingRows.size() >= 1) {
                    this.pendingRows.remove(this.pendingRows.size() - 1);
                    this.pendingDVS.remove(rowDimValSet);
                }
                if (checkedRowDimValSet.isAllNull()) {
                    this.noAccess.add(this.tableContext.getDimensionSet());
                } else {
                    this.noAccess.add(checkedRowDimValSet);
                }
                log.debug("\u6ca1\u6709\u6743\u9650\uff0c\u6570\u636e\u4e0d\u8fdb\u884c\u5bfc\u5165:{}", (Object)e.getMessage());
                return null;
            }
        }
        DimensionValueSet rowDimValSet1 = new DimensionValueSet(rowDimValSet);
        if (this.tableContext.getFloatImpOpt() == 2 || this.tableContext.getFloatImpOpt() == 0) {
            UUID uuid = UUID.randomUUID();
            for (FieldDefine item : this.bizKeyFieldDefCp) {
                if (!item.getCode().equals("BIZKEYORDER")) continue;
                hasBizOrder = true;
            }
            if (hasBizOrder) {
                rowDimValSet1.setValue("RECORDKEY", (Object)uuid.toString());
            }
        }
        this.pendingRowsDvs.add(rowDimValSet1);
        DimensionValueSet rowDimValSets = new DimensionValueSet(rowDimValSet1);
        for (FieldDefine def : this.bizKeyFieldDefCp) {
            if (null != rowDimValSets.getValue(this.dataAssist.getDimensionName(def)) || null == def.getDefaultValue()) continue;
            rowDimValSets.setValue(this.dataAssist.getDimensionName(def), (Object)def.getDefaultValue().replace("\"", ""));
        }
        return rowDimValSets;
    }

    private void checkQualifier(DimensionValueSet dimensionValueSet) throws Exception {
        this.tableContext.setFormKey(this.regionData.getFormKey());
        IoQualifier qualifier = this.tableContext.getIoQualifier();
        if (null == qualifier) {
            return;
        }
        if (null != dimensionValueSet && !dimensionValueSet.isAllNull()) {
            Map<String, List<String>> initQualifier = qualifier.initQualifier(this.tableContext, dimensionValueSet);
            List<String> formKey = initQualifier.get("formKeys");
            List<String> noAccessformKey = initQualifier.get("noAccessnFormKeys");
            if (null != formKey && !formKey.isEmpty() && !formKey.contains(this.regionData.getFormKey())) {
                throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\uff1a%s \u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", dimensionValueSet.toString()));
            }
            if (null != noAccessformKey && !noAccessformKey.isEmpty() && noAccessformKey.contains(this.regionData.getFormKey())) {
                throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\uff1a%s \u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e", dimensionValueSet.toString()));
            }
        } else {
            Map<String, List<String>> initQualifier = qualifier.initQualifier(this.tableContext, this.tableContext.getDimensionSet());
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

    private Object dataTransferOri(FieldDefine def, Object data) {
        if (null == data) {
            return null;
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
            return DateUtil.getOriDatetime(data.toString(), "yyyy-MM-dd");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_TIME)) {
            return DateUtil.getOriDatetime(data.toString(), "HH:mm:ss");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
            return DateUtil.getOriDatetime(data.toString(), "yyyy-MM-dd HH:mm:ss");
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
                    if (string.contains("|")) {
                        return string.split("\\|")[0];
                    }
                    List allRows = iEntityTable.getAllRows();
                    String multiTitle = "";
                    for (IEntityRow row : allRows) {
                        if (row.getTitle().equals(string)) {
                            if (Consts.EntityField.ENTITY_FIELD_KEY.equals((Object)referCode)) {
                                return null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                            }
                            return row.getCode();
                        }
                        if (!string.contains(";") || !string.contains(row.getTitle())) continue;
                        if (multiTitle.length() > 0) {
                            multiTitle = multiTitle + ";" + row.getEntityKeyData();
                            continue;
                        }
                        multiTitle = multiTitle + row.getEntityKeyData();
                    }
                    if (!multiTitle.equals("")) {
                        return multiTitle;
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

    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file);){
            int n;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            bos.close();
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

    private void dataTransferOri(FieldDefine define, Object data, Boolean[] status, int index) {
        Object transValue;
        status[index] = define.getType().equals((Object)FieldType.FIELD_TYPE_DATE) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_TIME) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_FILE) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) ? Boolean.valueOf(true) : (define.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC) ? Boolean.valueOf(true) : (data == null || data.equals("") ? Boolean.valueOf(true) : ((transValue = this.dataTransferOri(define, data)) != null && data != null && transValue.equals(data) ? Boolean.valueOf(false) : Boolean.valueOf(true))))))));
    }

    private void commitTmpTable() throws Exception {
        if (this.pendingRows.size() == 0) {
            this.pendingRows.clear();
            this.pendingDVS.clear();
            this.pendingRowsDvs.clear();
            log.warn("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u63d0\u4ea4\u4e8b\u52a1\u51fa\u9519:\u63d0\u4ea4\u7684\u6570\u636e\u4e3a\u7a7a");
            return;
        }
        if (this.pendingRows.isEmpty() && this.pendingDVS.isEmpty()) {
            return;
        }
        this.organizedDatas();
        this.pendingRows.clear();
        this.pendingDVS.clear();
        this.pendingRowsDvs.clear();
    }

    @Override
    public ImportInfo commit() throws Exception {
        this.commitTmpTable();
        int floatImpOpt = this.tableContext.getFloatImpOpt();
        String flag = "F";
        if (floatImpOpt == 0) {
            flag = "A";
        }
        TzParams param = new TzParams("JIO", this.tableContext.getDimensionSet().getValue("DATATIME").toString(), "1", this.jioTempTable.getTableName(), this.destFormKey, flag);
        this.batchImportOptions.batchImport(param, BatchImportMonitor.loggerMonitor);
        return null;
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
                if (this.hasDimField && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
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

    private void organizedDatas() throws Exception {
        this.buildDimValSets();
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
        if (this.sqlField == null) {
            this.sqlField = new ArrayList<String>();
        } else {
            this.addSqlField = false;
        }
        for (int i = 0; i < this.pendingRows.size(); ++i) {
            List<Object> row = this.pendingRows.get(i);
            DimensionValueSet rowDimValSet = this.pendingRowsDvs.get(i);
            if (null != this.impDimensionValueSet && !this.impDimensionValueSet.isAllNull() && null != this.impDimensionValueSet.getValue("DATATIME") && null != rowDimValSet && null == rowDimValSet.getValue("DATATIME") && !this.dataTableMap.get(this.listFieldDefine.get(0).getOwnerTableKey()).contains("FMDM")) {
                rowDimValSet.setValue("DATATIME", this.impDimensionValueSet.getValue("DATATIME"));
            } else if (rowDimValSet != null && (rowDimValSet.size() == 0 || rowDimValSet.size() == 1 && rowDimValSet.getValue("RECORDKEY") != null)) {
                rowDimValSet.combine(this.tableContext.getDimensionSet());
            }
            ArrayList<Object> dataRow = new ArrayList<Object>();
            int k = 0;
            for (int f = 0; k < row.size() && f < this.listFieldDefine.size(); ++k, ++f) {
                String fileCodeCopy;
                boolean ct = false;
                for (int m = 0; m < this.bizindex.size(); ++m) {
                    if (k != this.bizindex.get(m)) continue;
                    ct = true;
                    break;
                }
                if (ct) continue;
                FieldDefine field = this.listFieldDefine.get(f);
                String fieldCode = this.orderedFieldDef.get(k);
                String string = fileCodeCopy = fieldCode.contains(".") ? fieldCode.split("\\.")[1] : fieldCode;
                if (!fieldCode.equals(field.getCode()) && !fileCodeCopy.equals(field.getCode()) && f >= this.bizindex.size()) {
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
                if (this.addSqlField) {
                    this.sqlField.add(this.getFieldName(field));
                }
                dataRow.add(data);
            }
            Object value = rowDimValSet.getValue(this.dataAssist.getDimensionName(this.getUnitFieldDefine()));
            if (rowDimValSet != null && !rowDimValSet.isAllNull() && value == null) {
                rowDimValSet.setValue(this.dataAssist.getDimensionName(this.getUnitFieldDefine()), this.tableContext.getDimensionSet().getValue(this.dataAssist.getDimensionName(this.getUnitFieldDefine())));
            }
            if (rowDimValSet != null && rowDimValSet.isAllNull()) {
                rowDimValSet = this.dimensionValueSet;
            }
            List<DataFieldDeployInfo> dimDeploys = this.tmpTable.getDimDeploys();
            for (DataFieldDeployInfo dimDeploy : dimDeploys) {
                String dimName = this.bizKeyFieldMap.get(dimDeploy.getDataFieldKey());
                Object dimValue = rowDimValSet.getValue(dimName);
                if (dimValue != null) continue;
                rowDimValSet.setValue(dimName, this.tableContext.getDimensionSet().getValue(dimName));
            }
            for (int j = 0; j < rowDimValSet.size(); ++j) {
                String name = rowDimValSet.getName(j);
                FieldDefine fieldDefine = this.bizKeyDimNameMap.get(name);
                if (this.addSqlField && !fieldDefine.getCode().equals("DATATIME") && !fieldDefine.getCode().equals("BIZKEYORDER")) {
                    this.sqlField.add(this.getFieldName(fieldDefine));
                }
                if (fieldDefine.getCode().equals("DATATIME") || fieldDefine.getCode().equals("BIZKEYORDER")) continue;
                if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_TIME)) {
                    Object dataDim = rowDimValSet.getValue(j);
                    if (dataDim == null || "".equals(dataDim)) {
                        dataDim = "9999R0001";
                    }
                    Date date = DataTypesConvert.periodToDate((PeriodWrapper)new PeriodWrapper((String)dataDim));
                    dataRow.add(date);
                    continue;
                }
                dataRow.add(rowDimValSet.getValue(j));
            }
            dataRow.add(this.id.incrementAndGet());
            this.batchValues.add(dataRow.toArray(new Object[dataRow.size()]));
            this.addSqlField = false;
        }
        if (this.batchSql == null) {
            StringBuilder sql = new StringBuilder("insert into " + this.jioTempTable.getTableName());
            StringBuilder valueSql = new StringBuilder();
            sql.append("( ").append(this.sqlField.get(0));
            for (int i = 1; i < this.sqlField.size(); ++i) {
                sql.append(",").append(this.sqlField.get(i));
                valueSql.append(" , ? ");
            }
            sql.append(",").append("ID");
            sql.append(") values (?,? ").append((CharSequence)valueSql).append(")");
            this.batchSql = sql.toString();
        }
        Connection connection = null;
        try {
            connection = this.jdbcTemplate.getDataSource().getConnection();
            DataEngineUtil.batchUpdate((Connection)connection, (String)this.batchSql, this.batchValues);
        }
        catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw e;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
        this.batchValues.clear();
    }

    private String getFieldName(FieldDefine field) {
        DataFieldDeployInfo dataFieldDeployInfo = this.tmpTable.getDeployInfoMap().get(field.getKey());
        if (dataFieldDeployInfo == null) {
            return field.getCode();
        }
        return dataFieldDeployInfo.getFieldName();
    }

    @Override
    public List<FieldDefine> getBizFieldDefList() {
        return this.bizKeyFieldDef;
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
    public void close() {
        try {
            this.jioTempTable.close();
        }
        catch (Exception e) {
            log.error("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
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
}

