/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.RowExpressionValidResult
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.DuplicateRowKeysException
 *  com.jiuqi.np.dataengine.exception.ExpressionValidateException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.DataTableImpl
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.graphics.Point
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
 *  com.jiuqi.nr.data.common.exception.DataTransferException
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.format.strategy.MeasureNumberTypeStrategy
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureData
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureFieldValueConvert
 *  com.jiuqi.nr.datacrud.impl.measure.MeasureService
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.TypeFormatStrategy
 *  com.jiuqi.nr.datacrud.util.TypeStrategyUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
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
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
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
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  net.coobird.thumbnailator.Thumbnails
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.dataset.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.graphics.Point;
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
import com.jiuqi.nr.data.common.exception.DataTransferException;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.strategy.MeasureNumberTypeStrategy;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureFieldValueConvert;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
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
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
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
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.common.DataRowReaderImpl;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.datacheck.TransferData;
import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.datacheck.param.TransferSource;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.ImportErrorData;
import com.jiuqi.nr.io.params.input.ImportErrorTypeEnum;
import com.jiuqi.nr.io.params.input.ImportResult;
import com.jiuqi.nr.io.params.output.EntityDatas;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
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
import com.jiuqi.nr.io.util.ParamUtil;
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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public class RegionDataSet
extends AbstractRegionDataSet {
    private static final Logger log = LoggerFactory.getLogger(RegionDataSet.class);
    private RegionData regionData;
    private TableContext tableContext;
    private IReadonlyTable dataTable;
    private int pageSize;
    private int pageIndex;
    private int nextCount;
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IDataQuery dataQuery;
    private final Map<String, Integer> queryFieldsIndex;
    private IDataQuery fileDataQuery;
    private IDataAccessProvider dataAccessProvider;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private List<IEntityTable> entityTable;
    private IEntityQuery entityQuery;
    private IEntityMetaService entityMetaService;
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    private ITaskOptionController taskOptionController;
    private List<IDataQuery> dataQueryRegions;
    private List<IReadonlyTable> dataTables;
    private List<FieldDefine> listFieldDefine;
    private List<List<FieldDefine>> regionFieldDefs;
    private List<FieldDefine> bizKeyFieldDef;
    private List<FieldDefine> bizKeyFieldDefCp;
    private FileInfoService fileInfoService;
    private FileService fileService;
    private List<FileInfo> attamentFiles;
    private final Map<String, String> enumDatakeys;
    private final Map<String, DataLinkDefine> enumDatakeyLink;
    private final Map<String, IEntityTable> enumDataValues;
    private IDataTable readerDataTable;
    private IDataTable openForRead;
    private IDataUpdator openForUpdate;
    private List<FieldDefine> floatOrders;
    private int floatIncrease;
    private boolean hasBizKey;
    private DimensionValueSet dimensionValueSet;
    private IDataAssist dataAssist;
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
    private DataRegionDefine dataRegionDefine;
    private IoEntityService ioEntityService;
    private IEntityMetaService iEntityMetaService;
    private DataModelService dataModelService;
    private Map<String, Set<String>> unImport;
    private int importRowSize;
    private int unImportRowSize;
    private int historyUnImportSize;
    private final Set<String> successOrgs;
    private final Set<String> failureOrgs;
    private final List<ImportErrorData> errorDataInfos;
    private final List<ImportErrorData> amendDataInfos;
    private int amendRowSize;
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
    private Map<String, String> rowDimensionName;
    private boolean isMultiRegionExistDefaultValImport;
    private IoQualifier ioQualifier;
    private DataEngineService dataEngineService;
    private RegionRelationFactory regionRelationFactory;
    private String dataSchemeKey;
    private AttachmentIOService attachmentIOService;
    private DimensionProviderFactory dimensionProviderFactory;
    private DataAccesslUtil dataAccesslUtil;
    private String dwDimensionName;
    private Map<String, String> dwCurrencyRel;
    private DataAuthReadable dataAuthReadable;
    private MzOrgCodeRepairService mzOrgCodeRepairService;
    private Map<String, String> mzOrgCodeRepairCache;
    private int dwIndex;
    private int datatimeIndex;
    private FormSchemeDefine formSchemeDefineImport;
    private MeasureService measureService;
    private DataValueFormatterBuilderFactory formatterBuilderFactory;
    private DataValueFormatterBuilder formatterBuilder;
    private DataValueFormatter crudFormatter;
    private Map<String, String> field2links;
    private IDataTransferProvider dataTransferProvider;
    private IDataTransfer dataTransfer;
    private Map<String, IEntityDefine> rowDimEntitys;
    private ReportFmlExecEnvironment reportFmlExecEnvironment;
    private DimensionValueSet accessDim;
    Map<String, Set<Object>> amendFieldValues;
    private final List<FieldDefine> attachmentFieldKeys;
    private final Map<String, Set<String>> changedGroupKeys;
    private RegionRelation regionRelation;
    private static String illegalDataImport = "";
    private com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext1;
    private Map<String, String> rowDimension;

    public RegionDataSet(TableContext context, RegionData regionData) {
        this.pageSize = 20000;
        this.pageIndex = -1;
        this.nextCount = this.pageSize;
        this.queryFieldsIndex = new HashMap<String, Integer>();
        this.entityTable = new ArrayList<IEntityTable>();
        this.dataQueryRegions = new ArrayList<IDataQuery>();
        this.dataTables = new ArrayList<IReadonlyTable>();
        this.listFieldDefine = null;
        this.regionFieldDefs = new ArrayList<List<FieldDefine>>();
        this.bizKeyFieldDef = new ArrayList<FieldDefine>();
        this.bizKeyFieldDefCp = new ArrayList<FieldDefine>();
        this.attamentFiles = new ArrayList<FileInfo>();
        this.enumDatakeys = new HashMap<String, String>();
        this.enumDatakeyLink = new HashMap<String, DataLinkDefine>();
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
        this.dataRegionDefine = null;
        this.unImport = new HashMap<String, Set<String>>();
        this.importRowSize = 0;
        this.unImportRowSize = 0;
        this.historyUnImportSize = 0;
        this.successOrgs = new HashSet<String>();
        this.failureOrgs = new HashSet<String>();
        this.errorDataInfos = new ArrayList<ImportErrorData>();
        this.amendDataInfos = new ArrayList<ImportErrorData>();
        this.amendRowSize = 0;
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
        this.dwDimensionName = "";
        this.dwCurrencyRel = new HashMap<String, String>();
        this.dwIndex = -1;
        this.datatimeIndex = -1;
        this.field2links = new HashMap<String, String>();
        this.rowDimEntitys = new HashMap<String, IEntityDefine>();
        this.accessDim = null;
        this.amendFieldValues = new HashMap<String, Set<Object>>();
        this.attachmentFieldKeys = new ArrayList<FieldDefine>();
        this.changedGroupKeys = new HashMap<String, Set<String>>();
        this.regionRelation = null;
        this.tbcontext1 = null;
        this.rowDimension = new HashMap<String, String>();
        this.tableContext = context.clone();
        this.regionData = regionData;
        ParameterService parameterService = ParamUtil.getParameterService();
        this.nrIoProperties = parameterService.getNrIoProperties();
        this.nextCount = this.pageSize = this.nrIoProperties.getPageSize();
        this.runTimeViewController = parameterService.getRunTimeViewController();
        this.dataDefinitionRuntimeController = parameterService.getDataDefinitionRuntimeController();
        this.entityViewRunTimeController = parameterService.getEntityViewRunTimeController();
        this.initReportFmlExecEnvironment();
        this.entityMetaService = parameterService.getEntityMetaService();
        this.dataAccessProvider = parameterService.getDataAccessProvider();
        this.fileInfoService = parameterService.getFileInfoService();
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(this.tableContext));
        this.fileService = parameterService.getFileService();
        this.jdbcTemplate = parameterService.getJdbcTemplate();
        this.ioEntityService = parameterService.getIoEntityService();
        this.iEntityMetaService = parameterService.getEntityMetaService();
        this.dataModelService = parameterService.getDataModelService();
        this.runtimeDataSchemeService = parameterService.getRuntimeDataSchemeService();
        this.multistageUnitReplace = this.tableContext.getMultistageUnitReplace();
        this.dimensionProviderFactory = (DimensionProviderFactory)BeanUtil.getBean(DimensionProviderFactory.class);
        this.dataAccesslUtil = (DataAccesslUtil)BeanUtil.getBean(DataAccesslUtil.class);
        this.ioQualifier = parameterService.getIoQualifier();
        this.attachmentIOService = parameterService.getAttachmentIOService();
        this.dataAuthReadable = parameterService.getDataAuthReadable();
        this.measureService = (MeasureService)BeanUtil.getBean(MeasureService.class);
        this.formatterBuilderFactory = (DataValueFormatterBuilderFactory)BeanUtil.getBean(DataValueFormatterBuilderFactory.class);
        this.formatterBuilder = this.formatterBuilderFactory.createFormatterBuilder();
        this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
        this.regionRelationFactory = (RegionRelationFactory)BeanUtil.getBean(RegionRelationFactory.class);
        this.regionRelation = this.regionRelationFactory.getRegionRelation(regionData.getKey());
        this.resetDimensionValueSet();
        this.listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(regionData.getKey()));
        List allLinksInRegion = this.runTimeViewController.getAllLinksInRegion(regionData.getKey());
        for (DataLinkDefine item : allLinksInRegion) {
            try {
                if (item.getLinkExpression() == null) continue;
                this.field2links.put(item.getLinkExpression(), item.getKey());
            }
            catch (Exception e) {
                log.warn("\u6307\u6807key\u67e5\u94fe\u63a5\u51fa\u9519");
            }
        }
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
                if (this.dataSchemeKey != null) continue;
                this.dataSchemeKey = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getDataSchemeKey();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
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
        block7: for (FieldDefine item : queryFieldDefine) {
            for (FieldDefine it : this.listFieldDefine) {
                if (!it.getKey().equals(item.getKey())) continue;
                this.listFieldDefine.remove(it);
                continue block7;
            }
        }
        this.initDataQuerys(regionData, queryFieldDefine);
        this.listFieldDefine = queryFieldDefine;
    }

    public RegionDataSet(TableContext context, RegionData regionData, List<ExportFieldDefine> importDefine) {
        String tableKey;
        block48: {
            this.pageSize = 20000;
            this.pageIndex = -1;
            this.nextCount = this.pageSize;
            this.queryFieldsIndex = new HashMap<String, Integer>();
            this.entityTable = new ArrayList<IEntityTable>();
            this.dataQueryRegions = new ArrayList<IDataQuery>();
            this.dataTables = new ArrayList<IReadonlyTable>();
            this.listFieldDefine = null;
            this.regionFieldDefs = new ArrayList<List<FieldDefine>>();
            this.bizKeyFieldDef = new ArrayList<FieldDefine>();
            this.bizKeyFieldDefCp = new ArrayList<FieldDefine>();
            this.attamentFiles = new ArrayList<FileInfo>();
            this.enumDatakeys = new HashMap<String, String>();
            this.enumDatakeyLink = new HashMap<String, DataLinkDefine>();
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
            this.dataRegionDefine = null;
            this.unImport = new HashMap<String, Set<String>>();
            this.importRowSize = 0;
            this.unImportRowSize = 0;
            this.historyUnImportSize = 0;
            this.successOrgs = new HashSet<String>();
            this.failureOrgs = new HashSet<String>();
            this.errorDataInfos = new ArrayList<ImportErrorData>();
            this.amendDataInfos = new ArrayList<ImportErrorData>();
            this.amendRowSize = 0;
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
            this.dwDimensionName = "";
            this.dwCurrencyRel = new HashMap<String, String>();
            this.dwIndex = -1;
            this.datatimeIndex = -1;
            this.field2links = new HashMap<String, String>();
            this.rowDimEntitys = new HashMap<String, IEntityDefine>();
            this.accessDim = null;
            this.amendFieldValues = new HashMap<String, Set<Object>>();
            this.attachmentFieldKeys = new ArrayList<FieldDefine>();
            this.changedGroupKeys = new HashMap<String, Set<String>>();
            this.regionRelation = null;
            this.tbcontext1 = null;
            this.rowDimension = new HashMap<String, String>();
            this.importData = true;
            this.tableContext = context.clone();
            this.regionData = regionData;
            ParameterService parameterService = ParamUtil.getParameterService();
            this.runTimeViewController = parameterService.getRunTimeViewController();
            this.dataDefinitionRuntimeController = parameterService.getDataDefinitionRuntimeController();
            this.entityViewRunTimeController = parameterService.getEntityViewRunTimeController();
            this.initReportFmlExecEnvironment();
            this.entityMetaService = parameterService.getEntityMetaService();
            this.dataAccessProvider = parameterService.getDataAccessProvider();
            this.fileInfoService = parameterService.getFileInfoService();
            this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(this.tableContext));
            this.fileService = parameterService.getFileService();
            this.ioEntityService = parameterService.getIoEntityService();
            this.iEntityMetaService = parameterService.getEntityMetaService();
            this.dataModelService = parameterService.getDataModelService();
            this.runtimeDataSchemeService = parameterService.getRuntimeDataSchemeService();
            this.nrIoProperties = parameterService.getNrIoProperties();
            this.dimensionProviderFactory = (DimensionProviderFactory)BeanUtil.getBean(DimensionProviderFactory.class);
            this.dataAccesslUtil = (DataAccesslUtil)BeanUtil.getBean(DataAccesslUtil.class);
            this.dataTransferProvider = (IDataTransferProvider)BeanUtil.getBean(IDataTransferProvider.class);
            this.iDataAccessServiceProvider = parameterService.getDataAccessServiceProvider();
            this.mzOrgCodeRepairService = parameterService.getMzOrgCodeRepairService();
            String formSchemeKey = this.tableContext.getFormSchemeKey();
            String formKey = regionData.getFormKey();
            if (this.tableContext.isMultistageEliminateBizKey()) {
                DimensionValueSet regionDimRange = this.tableContext.getDimensionSetRange();
                regionDimRange = new DimensionValueSet(Objects.nonNull(regionDimRange) ? regionDimRange : this.tableContext.getDimensionSet());
                DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)regionDimRange, (String)formSchemeKey);
                HashSet<String> ignore = new HashSet<String>();
                ignore.add("uploadTimeSetting");
                ignore.add("upload");
                IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(this.tableContext.getTaskKey(), this.tableContext.getFormSchemeKey(), ignore);
                IBatchAccessResult writeAccess = dataAccessService.getWriteAccess(dimensionCollection, Collections.singletonList(formKey));
                IBatchAccessResult visitAccess = dataAccessService.getVisitAccess(dimensionCollection, Collections.singletonList(formKey));
                for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
                    IAccessResult iAccessResult = writeAccess.getAccess(dimensionCombination, formKey);
                    try {
                        IAccessResult iAccessResult2;
                        if (iAccessResult.haveAccess()) {
                            this.access.add(dimensionCombination.toDimensionValueSet());
                            continue;
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("\u7ef4\u5ea6\u3010{}\u3011\u6743\u9650\u6821\u9a8c\u4e0d\u901a\u8fc7\uff0c\u539f\u56e0\u4e3a\uff1a{}", (Object)dimensionCombination.toDimensionValueSet(), (Object)iAccessResult.getMessage());
                        }
                        if (!(iAccessResult2 = visitAccess.getAccess(dimensionCombination, formKey)).haveAccess()) continue;
                        this.noAccess.add(dimensionCombination.toDimensionValueSet());
                    }
                    catch (Exception exception) {
                        log.error("\u591a\u7ea7\u90e8\u7f72\uff0c\u521d\u59cb\u5316\u7ef4\u5ea6\u6743\u9650\u65f6\u53d1\u751f\u9519\u8bef\uff1a", exception);
                    }
                }
                if (!this.access.isEmpty()) {
                    DimensionValueSet newDim = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList<DimensionValueSet>(this.access));
                    this.tableContext.setDimensionSet(newDim);
                }
            }
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TransferParam param = new TransferParam();
            param.setRegionKey(regionData.getKey());
            param.setTaskKey(formScheme.getTaskKey());
            param.setTableContext(this.tableContext);
            param.setExecutorContext((com.jiuqi.np.dataengine.executors.ExecutorContext)this.getNrExecutorContext(context));
            this.dataTransfer = this.dataTransferProvider.getDataTransfer(param);
            this.taskOptionController = parameterService.getTaskOptionController();
            illegalDataImport = this.taskOptionController.getValue(formScheme.getTaskKey(), "IllegalDataImport_2132");
            IEntityDefine dwEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
            String contextId = DsContextHolder.getDsContext().getContextEntityId();
            if (StringUtils.isNotEmpty((String)contextId)) {
                dwEntity = this.iEntityMetaService.queryEntity(contextId);
            }
            if (dwEntity != null) {
                this.dwDimensionName = dwEntity.getDimensionName();
            }
            this.ioQualifier = context.getIoQualifier();
            this.dataEngineService = (DataEngineService)BeanUtil.getBean(DataEngineService.class);
            this.regionRelationFactory = (RegionRelationFactory)BeanUtil.getBean(RegionRelationFactory.class);
            this.regionRelation = this.regionRelationFactory.getRegionRelation(regionData.getKey());
            if (this.nrIoProperties.getRowsize() != 0) {
                this.rowSize = this.nrIoProperties.getRowsize();
            }
            this.attachmentIOService = parameterService.getAttachmentIOService();
            this.dataAuthReadable = parameterService.getDataAuthReadable();
            this.resetDimensionValueSet();
            this.listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(regionData.getKey()));
            QueryEnvironment environment = new QueryEnvironment();
            environment.setRegionKey(regionData.getKey());
            environment.setFormCode(regionData.getFormCode());
            environment.setFormKey(formKey);
            environment.setFormSchemeKey(formSchemeKey);
            this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
            this.fileDataQuery = this.dataAccessProvider.newDataQuery(environment);
            if (null != this.tableContext.getDimension() && null != this.tableContext.getTempTable() && regionData.getType() == 0) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
            tableKey = null;
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
                    if (this.dataSchemeKey != null) continue;
                    this.dataSchemeKey = ((DataFieldDeployInfo)list.get(0)).getDataSchemeKey();
                }
                catch (Exception exception) {
                    log.error(exception.getMessage(), exception);
                }
            }
            ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
            for (ExportFieldDefine exportFieldDefine : importDefine) {
                if (!exportFieldDefine.getCode().contains(".")) continue;
                exportFieldDefine.setCode(exportFieldDefine.getCode().split("\\.")[1]);
            }
            this.getBizKeysDef(tableKey, queryFieldDefine, true);
            ArrayList<FieldDefine> arrayList = new ArrayList<FieldDefine>();
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
                arrayList.add(fieldDefine);
            }
            this.bizKeyFieldDef.removeAll(arrayList);
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
            ArrayList<String> arrayList2 = new ArrayList<String>();
            for (ExportFieldDefine expField : importDefine) {
                arrayList2.add(expField.getCode());
            }
            this.orderedFieldDef = arrayList2;
            this.sortFields1(this.listFieldDefine, arrayList2);
            this.sortFields(this.bizKeyFieldDef, arrayList2);
            block22: for (FieldDefine i : this.bizKeyFieldDef) {
                for (int j = 0; j < importDefine.size(); ++j) {
                    if (importDefine.get(j).getCode().contains(".")) {
                        if (!importDefine.get(j).getCode().split("\\.")[1].equals(i.getCode())) continue;
                        this.bizindex.add(j);
                        if (i.getCode().equals("MDCODE")) {
                            this.dwIndex = j;
                        }
                        if (!i.getCode().equals("DATATIME")) continue block22;
                        this.datatimeIndex = j;
                        continue block22;
                    }
                    if (!importDefine.get(j).getCode().equals(i.getCode())) continue;
                    this.bizindex.add(j);
                    if (i.getCode().equals("MDCODE")) {
                        this.dwIndex = j;
                    }
                    if (!i.getCode().equals("DATATIME")) continue block22;
                    this.datatimeIndex = j;
                    continue block22;
                }
            }
            for (FieldDefine item : this.listFieldDefine) {
                if (item.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
                    this.attachmentFieldKeys.add(item);
                    this.changedGroupKeys.put(item.getKey(), new HashSet());
                }
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
            if (this.tableContext.isValidEntityExist()) {
                this.initEntityQuery(context);
            }
            try {
                FieldDefine fieldDefine = this.getUnitFieldDefine();
                if (fieldDefine == null) break block48;
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
            Object var16_47 = null;
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
        log.info("nr.io -- > \u5bfc\u5165\u7684\u6307\u6807\u4e3a\uff1a\n" + this.orderedFieldDef);
        if (this.tableContext.isMultistageEliminateBizKey() && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() && this.tableContext.getFloatImpOpt() == 2 && !this.access.isEmpty()) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList<DimensionValueSet>(this.access));
            this.dataQuery.setMasterKeys(dimensionValueSet);
            try {
                IDataUpdator deleteUpdator = this.dataQuery.openForUpdate(this.getExecutorContext(this.tableContext), true);
                deleteUpdator.deleteAll();
                deleteUpdator.commitChanges();
            }
            catch (Exception e) {
                log.error("\u6e05\u9664\u6d6e\u52a8\u533a\u57df\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
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
                    io1 = orderedFieldDef.indexOf((String)RegionDataSet.this.dataTableMap.get(o1.getOwnerTableKey()) + "." + o1.getCode());
                    io2 = orderedFieldDef.indexOf((String)RegionDataSet.this.dataTableMap.get(o2.getOwnerTableKey()) + "." + o2.getCode());
                } else {
                    io1 = orderedFieldDef.indexOf(o1.getCode());
                    io2 = orderedFieldDef.indexOf(o2.getCode());
                }
                for (int i = 0; i < orderedFieldDef.size(); ++i) {
                    if (this.containtTable) {
                        if (!((String)orderedFieldDef.get(i)).equals(RegionDataSet.this.dataTableMap.get(o2.getOwnerTableKey() + "." + o2.getCode()))) continue;
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
            if (null == (queryEntityView = this.runTimeViewController.getViewByLinkDefineKey(dataLinkDefine.getKey())) || queryEntityView.getEntityId() == null) continue;
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

    private void initEntityQuery(TableContext context) {
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

    private void initDataQuerys(RegionData regionData, List<FieldDefine> queryFieldDefine) {
        List<String> sortFields = this.tableContext.getSortFields();
        if (!this.floatOrders.isEmpty()) {
            FieldDefine order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
            this.dataQuery.addOrderByItem(order, false);
        }
        int index = 0;
        for (FieldDefine it : queryFieldDefine) {
            if (null != sortFields && !sortFields.isEmpty()) {
                this.addSort(sortFields, this.dataQuery, it);
            } else {
                this.dataQuery.addOrderByItem(it, false);
            }
            if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() && this.fieldBizKeys.contains(it.getKey())) {
                if ("DATATIME".equals(it.getCode())) {
                    this.dataQuery.addExpressionColumn("[CUR_PERIODSTR]");
                } else if ("ADJUST".equals(it.getCode())) {
                    this.dataQuery.addColumn(it);
                } else {
                    String entityKey = it.getEntityKey();
                    if (StringUtils.isNotEmpty((String)entityKey)) {
                        String entityCode = this.entityMetaService.getEntityCode(it.getEntityKey());
                        this.dataQuery.addExpressionColumn("[" + entityCode + "_CODE]");
                    } else {
                        this.dataQuery.addColumn(it);
                    }
                }
            } else {
                this.dataQuery.addColumn(it);
            }
            this.queryFieldsIndex.put(it.getKey(), index++);
        }
        for (FieldDefine fieldDefine : this.listFieldDefine) {
            this.dataQuery.addColumn(fieldDefine);
            queryFieldDefine.add(fieldDefine);
            this.queryFieldsIndex.put(fieldDefine.getKey(), index++);
        }
        this.dataQuery.setDefaultGroupName(regionData.getFormCode());
        this.dataQuery.setMasterKeys(this.tableContext.getDimensionSet());
    }

    private void addSort(List<String> sortFields, IDataQuery dataQueryRegion, FieldDefine it) {
    }

    private void getBizKeysDef(String tableKey, List<FieldDefine> queryFieldDefine, boolean isImp) {
        try {
            String bizKeys;
            if (tableKey == null) {
                return;
            }
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

    private com.jiuqi.np.dataengine.executors.ExecutorContext getExecutorContext(TableContext context) {
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setEnv((IFmlExecEnvironment)this.reportFmlExecEnvironment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private void executeQuery() {
        DimensionValueSet varDim;
        com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext = this.getExecutorContext(this.tableContext);
        tbcontext.setAutoDataMasking(this.tableContext.isDataMasking());
        List<String> sortFields = this.tableContext.getSortFields();
        if (sortFields == null || sortFields.isEmpty()) {
            this.dataQuery.clearOrderByItems();
        }
        if (this.pageIndex > 0) {
            QueryEnvironment environment = new QueryEnvironment();
            environment.setRegionKey(this.regionData.getKey());
            environment.setFormCode(this.regionData.getFormCode());
            environment.setFormKey(this.regionData.getFormKey());
            environment.setFormSchemeKey(this.tableContext.getFormSchemeKey());
            this.dataQuery = this.dataAccessProvider.newDataQuery(environment);
            if (null != sortFields && !sortFields.isEmpty()) {
                this.addSort(sortFields, this.dataQuery, this.bizKeyFieldDef.get(0));
            }
            int i = 0;
            for (FieldDefine it : this.bizKeyFieldDef) {
                FieldDefine order;
                if (it.getCode().equals("BIZKEYORDER")) {
                    order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
                    this.dataQuery.addOrderByItem(order, false);
                }
                this.dataQuery.addOrderByItem(it, false);
                if ((i = this.unitDatatime(i, it)) != 1 || this.floatOrders.isEmpty()) continue;
                order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
                this.dataQuery.addOrderByItem(order, false);
            }
            for (FieldDefine it : this.listFieldDefine) {
                this.dataQuery.addColumn(it);
            }
            this.dataQuery.setDefaultGroupName(this.regionData.getFormCode());
            varDim = this.getCollectionDim(this.tableContext.getDimensionSet());
            this.dataQuery.setMasterKeys(varDim);
            if (null == this.tableContext.getDimension() || null == this.tableContext.getTempTable() || !this.importData || this.regionData.getType() == 0) {
                this.dataQuery.setTempAssistantTable(this.tableContext.getDimension(), this.tableContext.getTempTable());
            }
        }
        try {
            String filterCondition;
            if (null != sortFields && !sortFields.isEmpty() && !this.bizKeyFieldDef.isEmpty()) {
                this.addSort(sortFields, this.dataQuery, this.bizKeyFieldDef.get(0));
            }
            int i = 0;
            for (FieldDefine it : this.bizKeyFieldDef) {
                FieldDefine order;
                if (it.getCode().equals("BIZKEYORDER")) {
                    order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
                    this.dataQuery.addOrderByItem(order, false);
                }
                this.dataQuery.addOrderByItem(it, false);
                if ((i = this.unitDatatime(i, it)) != 1 || this.floatOrders.isEmpty()) continue;
                order = this.floatOrders.get(0).getCode().equals("FLOATORDER") ? this.floatOrders.get(0) : this.floatOrders.get(1);
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
            if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() && (this.tableContext.getSortFields() == null || this.tableContext.getSortFields().isEmpty() || this.tableKeys.size() > 1)) {
                this.dataQuery.clearOrderByItems();
                this.pageSize = Integer.MAX_VALUE;
            }
            if (this.dataRegionDefine == null) {
                this.dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(this.regionData.getKey());
            }
            if ((filterCondition = this.dataRegionDefine.getFilterCondition()) != null && filterCondition.length() > 0) {
                this.dataQuery.setRowFilter(filterCondition);
            }
            varDim = this.getCollectionDim(this.dataQuery.getMasterKeys());
            if (this.tableContext.getDimensionSet() != null && (varDim == null || varDim.isAllNull())) {
                throw new Exception("\u6ca1\u6709\u6709\u6548\u7684\u67e5\u8be2\u7ef4\u5ea6\u96c6\u5408\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u67e5\u8be2");
            }
            this.dataQuery.setMasterKeys(varDim);
            this.measure(tbcontext);
            this.dataTable = this.dataQuery.executeReader(tbcontext);
            this.tbcontext1 = tbcontext;
        }
        catch (Exception e) {
            this.executed = true;
            log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private void measure(com.jiuqi.np.dataengine.executors.ExecutorContext tbcontext) {
        if (this.tableContext.getMeasure() != null) {
            Measure measure = new Measure();
            FormDefine queryFormById = this.runTimeViewController.queryFormById(this.regionData.getFormKey());
            String[] measureStrs = queryFormById.getMeasureUnit().split(";");
            if (measureStrs.length == 2) {
                TableModelDefine tableModelDefine;
                String tableKey = measureStrs[0];
                String measureValue = measureStrs[1];
                if (!measureValue.equalsIgnoreCase("NotDimession") && (tableModelDefine = this.dataModelService.getTableModelDefineById(tableKey)) != null) {
                    measure.setCode(this.tableContext.getMeasure());
                    measure.setKey(tableModelDefine.getID());
                }
            }
            MeasureData selectMeasure = this.measureService.getByMeasure(measure.getKey(), measure.getCode());
            MeasureFieldValueConvert measureFieldValueConvert = new MeasureFieldValueConvert(selectMeasure, this.regionRelation.getMeasureView(), this.regionRelation.getMeasureData());
            measureFieldValueConvert.setMeasureService(this.measureService);
            measureFieldValueConvert.setRuntimeDataSchemeService(this.regionRelation.getRuntimeDataSchemeService());
            this.reportFmlExecEnvironment.setFieldValueProcessor((IFieldValueProcessor)measureFieldValueConvert);
            tbcontext.setEnv((IFmlExecEnvironment)this.reportFmlExecEnvironment);
            String decimal = this.tableContext.getDecimal();
            TypeStrategyUtil strategyUtil = (TypeStrategyUtil)BeanUtil.getBean(TypeStrategyUtil.class);
            MeasureNumberTypeStrategy sysNumberTypeStrategy = strategyUtil.initMeasureNumberTypeStrategy();
            if (StringUtils.isNotEmpty((String)decimal)) {
                sysNumberTypeStrategy.setNumDecimalPlaces(Integer.valueOf(Integer.parseInt(decimal)));
            }
            sysNumberTypeStrategy.setSelectMeasure(measure);
            sysNumberTypeStrategy.setEnableBalanceFormula(true);
            this.formatterBuilder.registerFormatStrategy(DataFieldType.INTEGER.getValue(), (TypeFormatStrategy)sysNumberTypeStrategy);
            this.formatterBuilder.registerFormatStrategy(DataFieldType.BIGDECIMAL.getValue(), (TypeFormatStrategy)sysNumberTypeStrategy);
            this.crudFormatter = this.formatterBuilder.build();
        }
    }

    private DimensionValueSet getCollectionDim(DimensionValueSet dimensionSet) {
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
        builder.setDWValue(dwEntity.getDimensionName(), formScheme.getDw(), new Object[]{dimensionSet.getValue(dwEntity.getDimensionName())});
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
            for (DimensionValueSet currDim : builder.getCollection()) {
                this.dwCurrencyRel.put(currDim.getValue(this.dwDimensionName).toString(), currDim.getValue("MD_CURRENCY").toString());
                currency.add(currDim.getValue("MD_CURRENCY"));
            }
            ArrayList list = new ArrayList(currency);
            varDim.setValue("MD_CURRENCY", list);
        }
        for (int i = 0; i < varDim.size(); ++i) {
            if (varDim.getValue(i) != null && !varDim.getValue(i).equals("")) continue;
            varDim.clearValue(varDim.getName(i));
        }
        if (!this.importData) {
            if (this.accessDim == null) {
                IDataAccessService dataAccessService = this.dataAuthReadable.readable(this.tableContext.getTaskKey(), this.tableContext.getFormSchemeKey());
                this.accessDim = this.dataAuthReadable.hasAuthFormUnits(dataAccessService, varDim, this.regionData.getFormKey(), this.tableContext.getFormSchemeKey());
            }
            return this.accessDim;
        }
        return varDim;
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityDimData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getEntityId(), dimensionProvider);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasNext() {
        boolean hashNext;
        if (this.dataRows != null && this.dataRows.size() > this.nextCount) {
            return true;
        }
        if (this.executed) {
            return false;
        }
        if (this.dataTable == null && this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            this.nextCount = 0;
            return true;
        }
        if (null != this.dataTable && this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
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
            Connection connection = null;
            try {
                connection = this.getConnection();
                this.tempTable.dropTempTable(connection);
                connection.commit();
            }
            catch (Exception exception) {
            }
            finally {
                if (connection != null) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
        return hashNext;
    }

    @Override
    public ArrayList<Object> next() {
        if (this.pageSize == this.nextCount && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            this.nextCount = 0;
        }
        ++this.nextCount;
        return this.getCurrPageDataRowSet();
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
                ExportFieldDefine fd = new ExportFieldDefine(item.getTitle(), tableName + "." + item.getCode(), item.getSize(), item.getType().getValue());
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
        if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() && this.tableContext.getSortFields() != null && !this.tableContext.getSortFields().isEmpty() && this.dataRows == null) {
            this.dataRows = new ArrayList<ArrayList<Object>>();
            String ordinal = this.tableContext.getSortFields().get(0);
            for (int index = 0; index < this.dataTable.getCount(); ++index) {
                DimensionValueSet rowKeys;
                IDataRow dataRow = this.dataTable.getItem(index);
                DataRowImpl datarow = (DataRowImpl)dataRow;
                if (this.dwCurrencyRel.size() > 0 && !this.dwCurrencyRel.get((rowKeys = dataRow.getRowKeys()).getValue(this.dwDimensionName).toString()).equals(rowKeys.getValue("MD_CURRENCY").toString())) continue;
                ArrayList<String> row1 = new ArrayList<String>();
                for (FieldDefine def : this.listFieldDefine) {
                    if (ordinalIndex == -1 && def.getCode().equals(ordinal)) {
                        ordinalIndex = index;
                    }
                    try {
                        com.jiuqi.np.dataengine.setting.IFieldsInfo fieldsInfo = datarow.getTableImpl().getFieldsInfo();
                        int fieldIndex = this.queryFieldsIndex.get(def.getKey());
                        Object dataObj = dataRow.getValueObject(fieldIndex);
                        String data = AbstractData.valueOf((Object)dataObj, (int)DataTypesConvert.fieldTypeToDataType((FieldType)fieldsInfo.getDataType(fieldIndex))).getAsString();
                        data = this.dataTransfer(def, data);
                        if (this.multistageUnitReplace != null && def.getCode().equals("MDCODE")) {
                            data = this.multistageUnitReplace.getSuperiorCode(data);
                        }
                        if (this.mzOrgCodeRepairService != null && "MDCODE".equals(def.getCode())) {
                            data = this.dwValueRepair(dataRow, data);
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
            if (this.dwCurrencyRel.size() > 0) {
                DimensionValueSet rowKeys = dataRow.getRowKeys();
                while (this.nextCount < this.dataTable.getCount() && !this.dwCurrencyRel.get(rowKeys.getValue(this.dwDimensionName).toString()).equals(rowKeys.getValue("MD_CURRENCY").toString())) {
                    ++this.nextCount;
                    dataRow = this.dataTable.getItem(this.nextCount - 1);
                    rowKeys = dataRow.getRowKeys();
                }
            }
            DataRowImpl datarow = (DataRowImpl)dataRow;
            for (FieldDefine def : this.listFieldDefine) {
                try {
                    com.jiuqi.np.dataengine.setting.IFieldsInfo fieldsInfo = datarow.getTableImpl().getFieldsInfo();
                    int index = this.queryFieldsIndex.get(def.getKey());
                    Object dataObj = dataRow.getValueObject(index);
                    String data = AbstractData.valueOf((Object)dataObj, (int)DataTypesConvert.fieldTypeToDataType((FieldType)fieldsInfo.getDataType(index))).getAsString();
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

    public String getRowDimension(FieldDefine def) {
        String dimension = null;
        if (!this.rowDimension.containsKey(def.getKey())) {
            try {
                com.jiuqi.np.dataengine.executors.ExecutorContext context = Objects.nonNull(this.tbcontext1) ? this.tbcontext1 : this.getExecutorContext(this.tableContext);
                DataModelDefinitionsCache dataModelDefinitionsCache = context.getCache().getDataModelDefinitionsCache();
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
        if (null == data || data.equals("")) {
            return "";
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) && this.crudFormatter != null && this.field2links.containsKey(def.getKey())) {
            String linkKey = this.field2links.get(def.getKey());
            return this.crudFormatter.format(linkKey, (Object)data);
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
        ++this.importRowSize;
        if (!this.checkCanUpload(row)) {
            this.pendingRows.add(null);
            this.pendingRowsDvs.add(null);
            return new DimensionValueSet();
        }
        this.repairCode(row);
        ArrayList<String> rowDim = new ArrayList<String>();
        if (this.hasBizKey) {
            for (int i = 0; i < this.bizKeyFieldDef.size(); ++i) {
                TransferData o;
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
                if (this.tableContext.isValidEntityExist() && !this.bizKeyFieldDef.get(i).getCode().contains("PERIOD") && !this.entityTable.isEmpty() && ((entityTableDefine = (iEntityTable = this.entityTable.get(0)).getEntityTableDefine()).getCode().equals(this.bizKeyFieldDef.get(i).getCode()) || "MDCODE".equals(this.bizKeyFieldDef.get(i).getCode()))) {
                    IEntityRow findByCode;
                    IEntityRow findByEntityKey;
                    int mdCodeCheckRule = this.tableContext.getMdCodeCheckRule();
                    boolean isErrorMdCode = false;
                    if (mdCodeCheckRule == 0) {
                        findByEntityKey = iEntityTable.findByEntityKey(row.get(this.bizindex.get(i)).toString());
                        findByCode = iEntityTable.findByCode(row.get(this.bizindex.get(i)).toString());
                        isErrorMdCode = findByEntityKey == null && findByCode == null;
                    } else if (mdCodeCheckRule == 1) {
                        findByEntityKey = iEntityTable.findByEntityKey(row.get(this.bizindex.get(i)).toString());
                        isErrorMdCode = findByEntityKey == null;
                    } else if (mdCodeCheckRule == 2) {
                        findByCode = iEntityTable.findByCode(row.get(this.bizindex.get(i)).toString());
                        boolean bl = isErrorMdCode = findByCode == null;
                    }
                    if (isErrorMdCode) {
                        if (this.unImport.get("unit_inexistence") == null) {
                            HashSet unitcode = new HashSet();
                            this.unImport.put("unit_inexistence", unitcode);
                        }
                        Set<String> unitCodes = this.unImport.get("unit_inexistence");
                        unitCodes.add(row.get(this.bizindex.get(i)).toString());
                        String dwCode = row.get(this.dwIndex).toString();
                        this.pendingRows.add(null);
                        this.pendingRowsDvs.add(null);
                        ++this.unImportRowSize;
                        if (!this.failureOrgs.contains(dwCode)) {
                            this.failureOrgs.add(dwCode);
                            ImportErrorData amendDataInfo = new ImportErrorData("", dwCode, null, ImportErrorTypeEnum.NO_WRITE_ACCESS, "\u5355\u4f4d\u3010" + dwCode + "\u3011\u4e0d\u5b58\u5728\uff0c\u6216\u5bf9\u5f53\u524d\u4efb\u52a1/\u62a5\u8868\u4e0d\u53ef\u89c1\uff0c\u8df3\u8fc7\u5bfc\u5165\uff01");
                            this.amendDataInfos.add(amendDataInfo);
                        }
                        return new DimensionValueSet();
                    }
                }
                if (!"BIZKEYORDER".equals(this.bizKeyFieldDef.get(i).getCode()) && (o = this.dataTransferOri(this.bizKeyFieldDef.get(i), row.get(this.bizindex.get(i)).toString())) != null) {
                    if (o.getValue() != null && o.getTransferType() == 0) {
                        if (set != null) {
                            rowDim.add(o.getValue().toString());
                            set.add(o.getValue().toString());
                        } else if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                            rowDim.add(o.getValue().toString());
                        }
                    } else if (o.getTransferType() == 2 || o.getTransferType() == 1 && !o.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c")) {
                        ImportErrorData errorDataInfo = new ImportErrorData();
                        errorDataInfo.setFieldCode(this.bizKeyFieldDef.get(i).getCode());
                        errorDataInfo.setOrgCode(row.get(this.dwIndex).toString());
                        errorDataInfo.setPoint(new Point(this.importRowSize, i + 1));
                        errorDataInfo.setErrorType(ImportErrorTypeEnum.ERROR_DATA);
                        errorDataInfo.setErrorMessage(o.getMsg());
                        if (o.getTransferType() == 2) {
                            ++this.unImportRowSize;
                            this.failureOrgs.add(row.get(this.dwIndex).toString());
                            this.errorDataInfos.add(errorDataInfo);
                            if (this.tableContext.getCheckType() != 1) {
                                throw new DataTransferException("\u5355\u4f4dcode\u4e3a\uff1a" + row.get(this.dwIndex) + "," + o.getMsg());
                            }
                        } else {
                            ++this.amendRowSize;
                            this.amendDataInfos.add(errorDataInfo);
                        }
                    } else if (o.getTransferType() == 1 && o.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c")) {
                        this.pendingRows.add(null);
                        this.pendingRowsDvs.add(null);
                        ++this.unImportRowSize;
                        log.info("{}-->{}", (Object)o.getMsg(), (Object)row);
                        return new DimensionValueSet();
                    }
                }
                if (!"".equals(row.get(this.bizindex.get(i)))) continue;
                row.set(this.bizindex.get(i), null);
            }
        }
        int descCount = 0;
        if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            descCount = this.getDescartesCount();
        }
        if (this.pendingRows.size() - (this.unImportRowSize - this.historyUnImportSize) >= this.rowSize || descCount >= 9999) {
            this.c.clear();
            this.commit();
            this.pendingRows.add(row);
            return this.pendingRowsDvs(row, rowDim);
        }
        this.pendingRows.add(row);
        return this.pendingRowsDvs(row, rowDim);
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
        block5: for (int i = 0; i < this.pendingRows.size(); ++i) {
            List<Object> row = this.pendingRows.get(i);
            if (row == null) continue;
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
                if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
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
                StringBuilder message = new StringBuilder();
                if (e1 instanceof DuplicateRowKeysException) {
                    DuplicateRowKeysException dre = (DuplicateRowKeysException)e1;
                    DimensionValueSet duplicateKey = (DimensionValueSet)dre.getDuplicateKeys().get(0);
                    RegionRelation relation = this.regionRelationFactory.getRegionRelation(this.regionData.getKey());
                    relation.getMetaData(null);
                    List dimFields = relation.getDimFields().stream().filter(e -> e.getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM)).collect(Collectors.toList());
                    ArrayList<String> titles = new ArrayList<String>();
                    ArrayList<String> values = new ArrayList<String>();
                    for (Object dataField : dimFields) {
                        Object dimValue;
                        String code = dataField.getCode();
                        String entityKey = dataField.getRefDataEntityKey();
                        if (StringUtils.isNotEmpty((String)entityKey)) {
                            IEntityDefine entityDefine = this.rowDimEntitys.get(entityKey);
                            if (Objects.isNull(entityDefine)) {
                                entityDefine = this.iEntityMetaService.queryEntity(entityKey);
                                this.rowDimEntitys.put(entityKey, entityDefine);
                            }
                            code = entityDefine.getCode();
                        }
                        if (!Objects.nonNull(dimValue = duplicateKey.getValue(code))) continue;
                        titles.add(dataField.getTitle());
                        values.add(String.valueOf(dimValue));
                    }
                    String currBizKeyOrder = (String)duplicateKey.getValue("RECORDKEY");
                    if (currBizKeyOrder != null) {
                        titles.add("BIZKEYORDER");
                        values.add(currBizKeyOrder);
                    }
                    if (!titles.isEmpty()) {
                        Object dataField;
                        for (String title : titles) {
                            message.append("\u3010").append(title).append("\u3011");
                        }
                        if (titles.size() > 1) {
                            message.append("\u7684\u7ec4\u5408");
                        }
                        message.append("\u5b58\u5728\u91cd\u590d\u6570\u636e");
                        dataField = values.iterator();
                        while (dataField.hasNext()) {
                            String value = (String)dataField.next();
                            message.append("\u3010").append(value).append("\u3011");
                        }
                    }
                    if (message.length() > 0) {
                        message.append("\u3002");
                    }
                } else {
                    message.append(e1.getMessage());
                }
                ++this.unImportRowSize;
                this.failureOrgs.add(row.get(this.dwIndex).toString());
                ImportErrorData errorDataInfo = new ImportErrorData();
                errorDataInfo.setOrgCode(row.get(this.dwIndex).toString());
                errorDataInfo.setPoint(new Point(this.importRowSize - this.pendingRows.size() + i + 1, 0));
                errorDataInfo.setErrorType(ImportErrorTypeEnum.ERROR_DATA);
                errorDataInfo.setErrorMessage(message.toString());
                this.errorDataInfos.add(errorDataInfo);
                if (this.tableContext.getCheckType() == 1) continue;
                log.info("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u6784\u9020\u884c\u51fa\u9519{}", (Object)message, (Object)e1);
                throw new Exception(message.toString(), e1);
            }
            if (null == dataRow) {
                throw new Exception("\u4e0d\u652f\u6301\u7684\u5bfc\u5165\u65b9\u5f0f");
            }
            if (this.readerDataTable != null && this.readerDataTable.getCount() > 0) {
                dataRow1 = this.readerDataTable.getItem(0);
            }
            int k = 0;
            for (int f = 0; k < row.size() && f < this.listFieldDefine.size(); ++k, ++f) {
                ImportErrorData errorDataInfo;
                boolean ct = false;
                for (int m = 0; m < this.bizindex.size(); ++m) {
                    if (k != this.bizindex.get(m)) continue;
                    ct = true;
                    Object data = row.get(k);
                    if (!Objects.isNull(data) && !StringUtils.isEmpty((String)data.toString())) break;
                    String fieldCode = this.listFieldDefine.get(f) == null ? this.orderedFieldDef.get(k) : this.listFieldDefine.get(f).getTitle();
                    String message = "\u6307\u6807\u3010" + fieldCode + "\u3011\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01";
                    ++this.unImportRowSize;
                    this.failureOrgs.add(row.get(this.dwIndex).toString());
                    errorDataInfo = new ImportErrorData();
                    errorDataInfo.setFieldCode(this.orderedFieldDef.get(k));
                    errorDataInfo.setOrgCode(row.get(this.dwIndex).toString());
                    errorDataInfo.setPoint(new Point(this.importRowSize - this.pendingRows.size() + i + 1, k + 1));
                    errorDataInfo.setErrorType(ImportErrorTypeEnum.ERROR_DATA);
                    errorDataInfo.setErrorMessage(message);
                    this.errorDataInfos.add(errorDataInfo);
                    if (this.tableContext.getCheckType() == 1) break;
                    throw new Exception(message);
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
                            log.info("nr.io -->\u8df3\u8fc7\u5355\u4f4d%s\u5bfc\u5165\u7684\u6307\u6807\u4e3a\uff1a%s", row.get(this.dwIndex), (Object)fieldCode);
                            continue;
                        }
                    } else {
                        --f;
                        log.info("nr.io -->\u8df3\u8fc7\u5355\u4f4d%s\u5bfc\u5165\u7684\u6307\u6807\u4e3a\uff1a%s", row.get(this.dwIndex), (Object)fieldCode);
                        continue;
                    }
                }
                Object data = row.get(k);
                if (status[f] == null || status[f].booleanValue()) {
                    try {
                        TransferData transferData = this.dataTransferOri(field, data, dataRow);
                        if (transferData.getTransferType() == 2 || transferData.getTransferType() == 1 && !transferData.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c")) {
                            errorDataInfo = new ImportErrorData();
                            errorDataInfo.setFieldCode(this.orderedFieldDef.get(k));
                            errorDataInfo.setOrgCode(row.get(this.dwIndex).toString());
                            errorDataInfo.setPoint(new Point(this.importRowSize - this.pendingRows.size() + i + 1, k + 1));
                            errorDataInfo.setErrorType(ImportErrorTypeEnum.ERROR_DATA);
                            errorDataInfo.setErrorMessage(transferData.getMsg());
                            if (transferData.getTransferType() == 2) {
                                ++this.unImportRowSize;
                                this.failureOrgs.add(row.get(this.dwIndex).toString());
                                this.errorDataInfos.add(errorDataInfo);
                                if (this.tableContext.getCheckType() != 1) {
                                    throw new DataTransferException(transferData.getMsg());
                                }
                            } else {
                                ++this.amendRowSize;
                                this.amendDataInfos.add(errorDataInfo);
                            }
                        } else if (transferData.getTransferType() == 1 && transferData.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c")) {
                            List allDataRows;
                            DataTableImpl table;
                            ++this.unImportRowSize;
                            String msg = transferData.getMsg() + "-->" + row.toString();
                            log.info(msg);
                            if (this.readerDataTable != null) {
                                table = (DataTableImpl)this.readerDataTable;
                                allDataRows = table.getAllDataRows();
                                allDataRows.remove(allDataRows.size() - 1);
                                table.setAllDataRows(allDataRows);
                                continue block5;
                            }
                            if (this.openForUpdate == null) continue block5;
                            table = (DataTableImpl)this.openForUpdate;
                            allDataRows = table.getAllDataRows();
                            allDataRows.remove(allDataRows.size() - 1);
                            table.setAllDataRows(allDataRows);
                            continue block5;
                        }
                        data = transferData.getValue();
                    }
                    catch (DataTransferException e2) {
                        throw new DataTransferException("\u5355\u4f4dcode\u4e3a\uff1a" + row.get(this.dwIndex) + "," + e2.getMessage());
                    }
                    catch (Exception e3) {
                        throw new Exception("\u5355\u4f4dcode\u4e3a\uff1a" + row.get(this.dwIndex) + "," + e3.getMessage());
                    }
                }
                if (field.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
                    String oldGroupKey;
                    String string = oldGroupKey = dataRow != null ? dataRow.getAsString(field) : null;
                    if (this.tableContext.getFloatImpOpt() == 2) {
                        if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() && oldGroupKey != null) {
                            this.changedGroupKeys.get(field.getKey()).add(oldGroupKey);
                        }
                    } else if (data != null && oldGroupKey != null && !data.equals(oldGroupKey)) {
                        this.changedGroupKeys.get(field.getKey()).add(oldGroupKey);
                    }
                }
                dataRow.setValue(field, data);
            }
            this.successOrgs.add(row.get(this.dwIndex).toString());
            if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() || this.regionData.getType() == 1) continue;
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
        this.tableContext.setFormKey(this.regionData.getFormKey());
        if (null == this.ioQualifier) {
            return;
        }
        try {
            if (null != dimensionValueSet && !dimensionValueSet.isAllNull()) {
                Map<String, List<String>> initQualifier = this.ioQualifier.initQualifier(this.tableContext, dimensionValueSet);
                List<String> formKey = initQualifier.get("formKeys");
                List<String> noAccessformKey = initQualifier.get("noAccessnFormKeys");
                if (null != formKey && !formKey.isEmpty() && !formKey.contains(this.regionData.getFormKey())) {
                    throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\u3010 %s\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff01", dimensionValueSet));
                }
                if (null != noAccessformKey && !noAccessformKey.isEmpty() && noAccessformKey.contains(this.regionData.getFormKey())) {
                    throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\u3010 %s\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff01", dimensionValueSet));
                }
            } else {
                Map<String, List<String>> initQualifier = this.ioQualifier.initQualifier(this.tableContext, this.tableContext.getDimensionSet());
                List<String> formKey = initQualifier.get("formKeys");
                List<String> noAccessformKey = initQualifier.get("noAccessnFormKeys");
                if (null != formKey && !formKey.isEmpty() && !formKey.contains(this.regionData.getFormKey())) {
                    throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\u3010%s\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff01", this.tableContext.getDimensionSet().toString()));
                }
                if (null != noAccessformKey && !noAccessformKey.isEmpty() && noAccessformKey.contains(this.regionData.getFormKey())) {
                    throw new Exception(String.format("\u5f53\u524d\u7ef4\u5ea6\u3010%s\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff01", this.tableContext.getDimensionSet().toString()));
                }
            }
        }
        catch (Exception e) {
            String orgCode = (dimensionValueSet == null ? this.tableContext.getDimensionSet().getValue(this.dwDimensionName) : dimensionValueSet.getValue(this.dwDimensionName)).toString();
            ++this.unImportRowSize;
            if (!this.failureOrgs.contains(orgCode)) {
                this.failureOrgs.add(orgCode);
                ImportErrorData amendDataInfo = new ImportErrorData("", orgCode, null, ImportErrorTypeEnum.NO_WRITE_ACCESS, "\u5355\u4f4d\u3010" + orgCode + "\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff0c\u8df3\u8fc7\u5bfc\u5165\uff01");
                this.amendDataInfos.add(amendDataInfo);
            }
            throw new Exception(e.getMessage());
        }
    }

    private void isReadRegionOnly(RegionData regionData, DimensionValueSet masterKeys) throws Exception {
        AbstractData abstractData;
        String readOnlyCondition = this.regionRelation.getRegionDefine().getReadOnlyCondition();
        com.jiuqi.np.dataengine.executors.ExecutorContext context = this.getExecutorContext(this.tableContext);
        if (org.springframework.util.StringUtils.hasLength(readOnlyCondition) && (abstractData = this.dataEngineService.expressionEvaluate(readOnlyCondition, context, masterKeys, this.regionRelation)) instanceof BoolData && abstractData.getAsBool()) {
            String orgCode = masterKeys.getValue(this.dwDimensionName).toString();
            ++this.unImportRowSize;
            if (!this.failureOrgs.contains(orgCode)) {
                this.failureOrgs.add(orgCode);
                ImportErrorData amendDataInfo = new ImportErrorData("", orgCode, null, ImportErrorTypeEnum.REGION_READ_ONLY, "\u5355\u4f4d\u3010" + orgCode + "\u3011\u7b26\u5408\u533a\u57df\u53ea\u8bfb\u6761\u4ef6\uff0c\u8df3\u8fc7\u5bfc\u5165\uff01");
                this.amendDataInfos.add(amendDataInfo);
            }
            throw new Exception(String.format("\u533a\u57df\u3010" + regionData.getTitle() + "\u3011\u53ea\u8bfb\uff0c\u5f53\u524d\u7ef4\u5ea6\u3010%s\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff01", masterKeys));
        }
    }

    private DimensionValueSet pendingRowsDvs(List<Object> row, List<String> rowDim) throws Exception {
        TransferData object;
        int j;
        DimensionValueSet rowDimValSet = new DimensionValueSet();
        DimensionValueSet checkedRowDimValSet = new DimensionValueSet();
        String unitCode = "";
        boolean hasBizOrder = false;
        if (this.hasBizKey && this.rowDimensionName == null) {
            this.rowDimensionName = new HashMap<String, String>();
            for (j = 0; j < this.bizKeyFieldDef.size(); ++j) {
                if (this.bizKeyFieldDef.get(j).getCode().equals("BIZKEYORDER")) {
                    rowDimValSet.setValue("RECORDKEY", row.get(this.bizindex.get(j)));
                    continue;
                }
                object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
                String dimensionName = this.dataAssist.getDimensionName(this.bizKeyFieldDef.get(j));
                rowDimValSet.setValue(dimensionName, object.getValue());
                if (this.bizKeyFieldDef.get(j) instanceof DataFieldDTO && ((DataFieldDTO)this.bizKeyFieldDef.get(j)).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) {
                    checkedRowDimValSet.setValue(dimensionName, object.getValue());
                }
                if (this.bizKeyFieldDef.get(j).getCode().equals("MDCODE")) {
                    unitCode = row.get(this.bizindex.get(j)).toString();
                }
                this.rowDimensionName.put(this.bizKeyFieldDef.get(j).getKey(), dimensionName);
            }
        } else if (this.hasBizKey && this.rowDimensionName != null) {
            for (j = 0; j < this.bizKeyFieldDef.size(); ++j) {
                if (this.bizKeyFieldDef.get(j).getCode().equals("BIZKEYORDER")) {
                    rowDimValSet.setValue("RECORDKEY", row.get(this.bizindex.get(j)));
                    continue;
                }
                object = this.dataTransferOri(this.bizKeyFieldDef.get(j), row.get(this.bizindex.get(j)));
                rowDimValSet.setValue(this.rowDimensionName.get(this.bizKeyFieldDef.get(j).getKey()), object.getValue());
                if (this.bizKeyFieldDef.get(j) instanceof DataFieldDTO && ((DataFieldDTO)this.bizKeyFieldDef.get(j)).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) {
                    checkedRowDimValSet.setValue(this.rowDimensionName.get(this.bizKeyFieldDef.get(j).getKey()), object.getValue());
                }
                if (!this.bizKeyFieldDef.get(j).getCode().equals("MDCODE")) continue;
                unitCode = row.get(this.bizindex.get(j)).toString();
            }
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
            if (!isUnChecked && !isAccess && this.pendingRows.size() >= 1) {
                this.pendingRows.remove(this.pendingRows.size() - 1);
                this.pendingRows.add(null);
                this.pendingRowsDvs.add(null);
                ++this.unImportRowSize;
                if (!this.failureOrgs.contains(unitCode)) {
                    this.failureOrgs.add(unitCode);
                    ImportErrorData amendDataInfo = new ImportErrorData("", unitCode, null, ImportErrorTypeEnum.NO_WRITE_ACCESS, "\u5355\u4f4d\u3010" + unitCode + "\u3011\u6ca1\u6709\u6743\u9650\u5bfc\u5165\u6570\u636e\uff0c\u6216\u7b26\u5408\u533a\u57df\u53ea\u8bfb\u6761\u4ef6\uff0c\u8df3\u8fc7\u5bfc\u5165\uff01");
                    this.amendDataInfos.add(amendDataInfo);
                }
                return null;
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
            }
        }
        catch (Exception e) {
            if (this.pendingRows.size() >= 1) {
                this.pendingRows.remove(this.pendingRows.size() - 1);
                this.pendingRows.add(null);
                this.pendingRowsDvs.add(null);
            }
            if (checkedRowDimValSet.isAllNull()) {
                this.noAccess.add(this.tableContext.getDimensionSet());
            } else {
                this.noAccess.add(checkedRowDimValSet);
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
        this.pendingDVS.add(rowDim);
        DimensionValueSet rowDimValSet1 = new DimensionValueSet(rowDimValSet);
        if (this.tableContext.getFloatImpOpt() == 2 || this.tableContext.getFloatImpOpt() == 0) {
            UUID uuid = UUID.randomUUID();
            for (FieldDefine item : this.bizKeyFieldDefCp) {
                if (!item.getCode().equals("BIZKEYORDER")) continue;
                hasBizOrder = true;
            }
            if (hasBizOrder && this.bizKeyFieldDefCp.size() != this.bizKeyFieldDef.size()) {
                rowDimValSet1.setValue("RECORDKEY", (Object)uuid.toString());
            }
        }
        this.pendingRowsDvs.add(rowDimValSet1);
        DimensionValueSet rowDimValSets = new DimensionValueSet(rowDimValSet1);
        for (FieldDefine def : this.bizKeyFieldDefCp) {
            String dimensionName = "";
            if (this.rowDimensionName.containsKey(def.getKey())) {
                dimensionName = this.rowDimensionName.get(def.getKey());
            } else {
                dimensionName = this.dataAssist.getDimensionName(def);
                this.rowDimensionName.put(def.getKey(), dimensionName);
            }
            if (null != rowDimValSets.getValue(dimensionName) || null == def.getDefaultValue()) continue;
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
        if (this.tableContext.getCheckType() == 1 && !this.errorDataInfos.isEmpty()) {
            return null;
        }
        try {
            if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() && this.openForRead != null) {
                Iterator<IDataRow> allRows = ((DataTableImpl)this.openForRead).getAllDataRows();
                Iterator iterator = allRows.iterator();
                while (iterator.hasNext()) {
                    DataRowImpl allRow = (DataRowImpl)iterator.next();
                    for (FieldDefine fieldDefine : this.attachmentFieldKeys) {
                        String groupKey = allRow.getAsString(fieldDefine);
                        if (groupKey == null) continue;
                        this.changedGroupKeys.get(fieldDefine.getKey()).add(groupKey);
                    }
                }
            }
            for (FieldDefine fieldDefine : this.attachmentFieldKeys) {
                Set<String> groupKeys = this.changedGroupKeys.get(fieldDefine.getKey());
                if (groupKeys != null && !groupKeys.isEmpty()) {
                    this.attachmentIOService.batchMarkDeletion(this.dataSchemeKey, this.tableContext.getFormSchemeKey(), groupKeys);
                }
                this.changedGroupKeys.put(fieldDefine.getKey(), new HashSet());
            }
            if (!this.dbRows.isEmpty()) {
                for (IDataRow item : this.dbRows) {
                    this.readerDataTable.deleteRow(item.getRowKeys());
                }
            }
            if (this.readerDataTable != null) {
                this.readerDataTable.commitChanges(true, true);
            } else if (this.openForUpdate != null) {
                this.openForUpdate.commitChanges(true);
            }
            this.historyUnImportSize = this.unImportRowSize;
        }
        catch (ExpressionValidateException validateException) {
            log.info("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u5b58\u5728\u6307\u6807\u6821\u9a8c\u5931\u8d25\u6570\u636e\u3002\u7ec4\u88c5\u9519\u8bef\u4fe1\u606f");
            this.unImportRowSize += this.pendingRows.size();
            List errorDimList = validateException.getRowExpressionValidResults();
            for (RowExpressionValidResult validResult : errorDimList) {
                DimensionValueSet validResultDim = validResult.getRowKeys();
                String orgCode = (String)validResultDim.getValue(this.dwDimensionName);
                this.failureOrgs.add(orgCode);
                List expressions = validResult.getErrorExpressions();
                for (IParsedExpression expression : expressions) {
                    ImportErrorData errorDataInfo = RegionDataSet.dealExpressionrrorData(validateException, expression, orgCode);
                    this.errorDataInfos.add(errorDataInfo);
                }
            }
            this.historyUnImportSize = this.unImportRowSize;
            throw new DataTransferException(validateException.getMessage(), (Throwable)validateException);
        }
        catch (Exception e) {
            log.info("\u533a\u57df\u6570\u636e\u5bfc\u5165\uff0c\u63d0\u4ea4\u4e8b\u52a1\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
            this.pendingRowsDvs.clear();
            throw e;
        }
        String unitDimensionName = null;
        try {
            FieldDefine unitFieldDefine = this.getUnitFieldDefine();
            unitDimensionName = this.dataAssist.getDimensionName(unitFieldDefine);
        }
        catch (Exception e) {
            log.error("\u7ec4\u7ec7\u5bfc\u5165\u65e5\u5fd7\u8fc7\u7a0b\u4e2d\uff0c\u83b7\u53d6\u5355\u4f4d\u7ef4\u5ea6\u540d\u65f6\u51fa\u9519:{}", (Object)e.getMessage(), (Object)e);
        }
        if (unitDimensionName != null) {
            for (DimensionValueSet item : this.pendingRowsDvs) {
                if (item == null) continue;
                Object value2 = item.getValue(unitDimensionName);
                if (this.unImport.get("unit_success") == null) {
                    HashSet successUnit = new HashSet();
                    this.unImport.put("unit_success", successUnit);
                }
                Set<String> unitCodes = this.unImport.get("unit_success");
                unitCodes.add((String)value2);
            }
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
            }
        }
        catch (Exception e) {
            log.error("\u7ec4\u88c5\u4fee\u6b63\u5bfc\u5165\u6570\u636e\u65e5\u5fd7\u5185\u5bb9\u65f6\u53d1\u751f\u9519\u8bef:{}", (Object)e.getMessage(), (Object)e);
        }
        this.pendingRowsDvs.clear();
        return null;
    }

    private void getReaderDataTable() throws Exception {
        String filterCondition;
        DimensionValueSet varDim;
        this.dataQuery.setDefaultGroupName(this.regionData.getFormCode());
        if (null != this.impDimensionValueSet && !this.impDimensionValueSet.isAllNull()) {
            DimensionValueSet varDim2;
            DimensionValueSet dv;
            if (this.impDimensionValueSet.size() == 1 && null != this.impDimensionValueSet.getValue("VERSIONID")) {
                dv = new DimensionValueSet(this.tableContext.getDimensionSet());
                dv.setValue(this.impDimensionValueSet.getName(0), this.impDimensionValueSet.getValue(0));
                varDim2 = this.getCollectionDim(dv);
                this.dataQuery.setMasterKeys(varDim2);
            } else if (this.impDimensionValueSet.size() == 0) {
                dv = new DimensionValueSet(this.tableContext.getDimensionSet());
                for (FieldDefine bizDef : this.bizKeyFieldDefCp) {
                    if (null == dv.getValue(this.dataAssist.getDimensionName(bizDef))) continue;
                    TransferData dataTransferOri = this.dataTransferOri(bizDef, dv.getValue(this.dataAssist.getDimensionName(bizDef)));
                    if (dataTransferOri.getTransferType() == 2) {
                        throw new DataTransferException(dataTransferOri.getMsg());
                    }
                    dv.setValue(this.dataAssist.getDimensionName(bizDef), dataTransferOri.getValue());
                }
                varDim2 = this.getCollectionDim(dv);
                this.dataQuery.setMasterKeys(varDim2);
            } else {
                if (null == this.impDimensionValueSet.getValue("DATATIME")) {
                    this.impDimensionValueSet.setValue("DATATIME", this.tableContext.getDimensionSet().getValue("DATATIME"));
                }
                varDim = this.getCollectionDim(this.impDimensionValueSet);
                this.dataQuery.setMasterKeys(varDim);
            }
        } else {
            varDim = this.getCollectionDim(this.tableContext.getDimensionSet());
            this.dataQuery.setMasterKeys(varDim);
        }
        if (this.dataRegionDefine == null) {
            this.dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(this.regionData.getKey());
        }
        if ((filterCondition = this.dataRegionDefine.getFilterCondition()) != null && filterCondition.length() > 0) {
            this.dataQuery.setRowFilter(filterCondition);
        }
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.getExecutorContext(this.tableContext);
        executorContext.setDefaultGroupName(this.regionData.getFormCode());
        executorContext.setAutoDataMasking(false);
        boolean throwsExeception = false;
        if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            this.dataQuery.setIgnoreDefaultOrderBy(true);
        }
        try {
            DimensionValueSet masterKeys;
            if (this.tableContext.getFloatImpOpt() == 2 && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                if (this.impDimensionValueSet != null && !this.impDimensionValueSet.isAllNull()) {
                    DimensionValueSet varDim3;
                    if (this.tableContext.isMultistageEliminateBizKey()) {
                        masterKeys = this.dataQuery.getMasterKeys();
                        DimensionValueSet masterKeysCp = new DimensionValueSet(masterKeys);
                        masterKeysCp.clearValue("RECORDKEY");
                        varDim3 = this.getCollectionDim(masterKeysCp);
                        this.dataQuery.setMasterKeys(varDim3);
                    }
                    if (this.tableContext.getDimensionSet() != null && this.isMultiRegionExistDefaultValImport) {
                        masterKeys = this.dataQuery.getMasterKeys();
                        DimensionValueSet masterKeysTmp = this.setRegionDefaultDimension(masterKeys);
                        varDim3 = this.getCollectionDim(masterKeysTmp);
                        this.dataQuery.setMasterKeys(varDim3);
                    }
                    if (!(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                        this.openForUpdate = this.dataQuery.openForUpdate(executorContext, true);
                        if (!this.attachmentFieldKeys.isEmpty()) {
                            this.fileDataQuery.setMasterKeys(this.dataQuery.getMasterKeys());
                            this.attachmentFieldKeys.forEach(e -> this.fileDataQuery.addColumn(e));
                            this.openForRead = this.fileDataQuery.executeQuery(executorContext);
                        }
                    }
                } else if (this.openForUpdate == null && this.impDimensionValueSet == null) {
                    if (!(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                        this.openForUpdate = this.dataQuery.openForUpdate(executorContext, false);
                        this.openForRead = null;
                    }
                } else if (this.openForUpdate == null) {
                    if (!(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                        this.openForUpdate = this.dataQuery.openForUpdate(executorContext, false);
                        this.openForRead = null;
                    }
                } else if (this.openForUpdate != null && !(throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys()))) {
                    this.openForUpdate = this.dataQuery.openForUpdate(executorContext, false);
                    this.openForRead = null;
                }
                if ("0".equals(illegalDataImport) && this.openForUpdate != null) {
                    this.openForUpdate.setValidExpression(this.regionRelation.getExpressions());
                }
                this.readerDataTable = null;
            } else {
                masterKeys = this.dataQuery.getMasterKeys();
                DimensionValueSet masterKeysTmp = this.setRegionDefaultDimension(masterKeys);
                DimensionValueSet varDim4 = this.getCollectionDim(masterKeysTmp);
                this.dataQuery.setMasterKeys(varDim4);
                throwsExeception = this.checkMainDim(throwsExeception, this.dataQuery.getMasterKeys());
                if (!throwsExeception) {
                    this.readerDataTable = this.dataQuery.executeQuery(executorContext);
                    if ("0".equals(illegalDataImport)) {
                        this.readerDataTable.setValidExpression(this.regionRelation.getExpressions());
                    }
                }
                this.openForUpdate = null;
            }
            this.floatIncrease = 1;
            if (throwsExeception) {
                log.error("\u5bfc\u5165\u6570\u636e\u4e3b\u7ef4\u5ea6\u3010\u5355\u4f4d\u3001\u65f6\u671f\u3011\u4e0d\u80fd\u4e3a\u7a7a  !");
                throw new Exception("\u5bfc\u5165\u6570\u636e\u4e3b\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
            }
        }
        catch (Exception e2) {
            log.info("\u63d0\u4ea4\u4e8b\u52a1\u540e\u67e5\u8be2\u51fa\u9519{}", (Object)e2.getMessage(), (Object)e2);
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

    private void dataTransferOri(FieldDefine define, Object data, Boolean[] status, int index) throws Exception {
        if (define.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
            status[index] = true;
        } else if (define.getType().equals((Object)FieldType.FIELD_TYPE_TIME)) {
            status[index] = true;
        } else if (define.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
            status[index] = true;
        } else if (define.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
            status[index] = true;
        } else if (define.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
            status[index] = true;
        } else {
            TransferData transValue = this.dataTransferOri(define, data);
            status[index] = data == null || !transValue.getValue().equals(data) || define.getEntityKey() != null || transValue.getTransferType() != 0;
        }
    }

    private TransferData dataTransferOri(FieldDefine def, Object data) throws Exception {
        return this.dataTransferOri(def, data, null);
    }

    private TransferData dataTransferOri(FieldDefine def, Object data, IDataRow dataRow) throws Exception {
        String fieldCode;
        TransferSource transferSource = new TransferSource();
        transferSource.setDataField(def);
        transferSource.setDataRow(dataRow);
        transferSource.setDataSchemeKey(this.dataSchemeKey);
        transferSource.setRegionData(this.regionData);
        transferSource.setTableContext(this.tableContext);
        transferSource.setValue(data);
        TransferData transfer = this.dataTransfer.transfer(transferSource);
        if (transfer.getTransferType() == 1 && !transfer.getMsg().contains("\u8df3\u8fc7\u5f53\u524d\u884c") && (fieldCode = def.getCode()) != null) {
            Set values = this.amendFieldValues.computeIfAbsent(fieldCode, k -> new HashSet());
            values.add(data);
        }
        return transfer;
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
        File file = new File(FilenameUtils.normalize(filePath));
        if (file.isDirectory() && (files = file.list()).length > 0) {
            file = new File(FilenameUtils.normalize(filePath + "/" + files[0]));
        }
        return file;
    }

    @Override
    public List<FieldDefine> getBizFieldDefList() {
        return this.bizKeyFieldDef;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    private void setPage(IDataQuery item, int pageSize, int pageIndex) {
        if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
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
            Set<String> unitCodes;
            Object value = item.getValue(this.dataAssist.getDimensionName(unitFieldDefine));
            if (this.unImport.get("unit_noaccess") == null) {
                HashSet unitNoAccess = new HashSet();
                this.unImport.put("unit_noaccess", unitNoAccess);
            }
            if ((unitCodes = this.unImport.get("unit_noaccess")).contains(value)) {
                unitCodes.remove(value);
            }
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
    public List<ImportErrorData> getImportErrorInfos() {
        return this.errorDataInfos;
    }

    @Override
    public List<ImportErrorData> getImportAmendInfos() {
        return this.amendDataInfos;
    }

    @Override
    public ImportResult getImportResult() {
        ImportResult importResult = new ImportResult();
        importResult.setRegionTitle(this.regionData.getTitle());
        importResult.setSuccessOrgs(this.successOrgs);
        importResult.setSuccessOrgNum(this.successOrgs.size());
        importResult.setSuccessDataNum(this.importRowSize - this.unImportRowSize);
        importResult.setFailureOrgs(this.failureOrgs);
        importResult.setFailureOrgNum(this.failureOrgs.size());
        importResult.setFailureDataNum(this.unImportRowSize);
        importResult.setAmendDataNum(this.amendRowSize);
        return importResult;
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
        queryEnvironment.setFormSchemeKey(this.tableContext.getFormSchemeKey());
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
                    if (null != unitVal) {
                        noAccess.add(unitVal.toString());
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
    public void queryToDataRowReader(IRegionDataSetReader regionDataSetReader) {
        try {
            if (this.accessDim == null) {
                IDataAccessService dataAccessService = this.dataAuthReadable.readable(this.tableContext.getTaskKey(), this.tableContext.getFormSchemeKey());
                this.accessDim = this.dataAuthReadable.hasAuthFormUnits(dataAccessService, this.tableContext.getDimensionSet(), this.regionData.getFormKey(), this.tableContext.getFormSchemeKey());
                if (this.tableContext.getDimensionSet() != null && (this.accessDim == null || this.accessDim.isAllNull())) {
                    throw new Exception("\u6ca1\u6709\u6709\u6548\u7684\u67e5\u8be2\u7ef4\u5ea6\u96c6\u5408\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u67e5\u8be2");
                }
                this.dataQuery.setMasterKeys(this.accessDim);
            }
            if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                this.dataQuery.clearOrderByItems();
            }
            DataRowReaderImpl dataRowReader = new DataRowReaderImpl(this.listFieldDefine, regionDataSetReader, this, this.queryFieldsIndex);
            com.jiuqi.np.dataengine.executors.ExecutorContext executeContext = this.getExecutorContext(this.tableContext);
            executeContext.setAutoDataMasking(this.tableContext.isDataMasking());
            this.measure(executeContext);
            this.dataQuery.queryToDataRowReader(executeContext, (IDataRowReader)dataRowReader);
        }
        catch (Exception e) {
            log.info("\u67e5\u8be2\u533a\u57df\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
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

    private void initReportFmlExecEnvironment() {
        if (Objects.isNull(this.reportFmlExecEnvironment)) {
            this.reportFmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, this.tableContext.getFormSchemeKey());
            List<Variable> variables = this.tableContext.getVariables();
            if (variables != null) {
                for (Variable variable : variables) {
                    this.reportFmlExecEnvironment.getVariableManager().add(variable);
                }
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

    private static ImportErrorData dealExpressionrrorData(ExpressionValidateException validateException, IParsedExpression expression, String orgCode) {
        Formula formula = expression.getSource();
        String message = validateException.getMessage() + (StringUtils.isNotEmpty((String)formula.getMeanning()) ? formula.getMeanning() : formula.getFormula());
        if (StringUtils.isEmpty((String)message)) {
            message = "\u6570\u636e\u6821\u9a8c\u5931\u8d25";
        }
        ImportErrorData errorDataInfo = new ImportErrorData();
        errorDataInfo.setOrgCode(orgCode);
        errorDataInfo.setErrorType(ImportErrorTypeEnum.ERROR_DATA);
        errorDataInfo.setErrorMessage(message);
        return errorDataInfo;
    }
}

