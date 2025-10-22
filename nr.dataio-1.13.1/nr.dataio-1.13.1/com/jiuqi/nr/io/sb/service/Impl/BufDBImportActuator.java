/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.bufdb.BufDBException
 *  com.jiuqi.bi.bufdb.config.TableConfig
 *  com.jiuqi.bi.bufdb.db.ICursor
 *  com.jiuqi.bi.bufdb.db.IFilter
 *  com.jiuqi.bi.bufdb.db.IIndexedCursor
 *  com.jiuqi.bi.bufdb.db.IRecord
 *  com.jiuqi.bi.bufdb.db.ISchema
 *  com.jiuqi.bi.bufdb.db.ITable
 *  com.jiuqi.bi.bufdb.db.filter.ValuesFilter
 *  com.jiuqi.bi.bufdb.define.FieldDefine
 *  com.jiuqi.bi.bufdb.define.IndexType
 *  com.jiuqi.bi.bufdb.define.TableDefine
 *  com.jiuqi.bi.bufdb.model.OrderedField
 *  com.jiuqi.bi.bufdb.model.RankModel
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.fielddatacrud.ActuatorConfig
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricDecryptor
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptor
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.sb.service.Impl;

import com.jiuqi.bi.bufdb.BufDBException;
import com.jiuqi.bi.bufdb.config.TableConfig;
import com.jiuqi.bi.bufdb.db.ICursor;
import com.jiuqi.bi.bufdb.db.IFilter;
import com.jiuqi.bi.bufdb.db.IIndexedCursor;
import com.jiuqi.bi.bufdb.db.IRecord;
import com.jiuqi.bi.bufdb.db.ISchema;
import com.jiuqi.bi.bufdb.db.ITable;
import com.jiuqi.bi.bufdb.db.filter.ValuesFilter;
import com.jiuqi.bi.bufdb.define.FieldDefine;
import com.jiuqi.bi.bufdb.define.IndexType;
import com.jiuqi.bi.bufdb.define.TableDefine;
import com.jiuqi.bi.bufdb.model.OrderedField;
import com.jiuqi.bi.bufdb.model.RankModel;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.io.bufdb.IIOBufDBProvider;
import com.jiuqi.nr.io.params.base.DFAndTypeInfo;
import com.jiuqi.nr.io.sb.ISBImportActuator;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.sb.bean.RowDimValue;
import com.jiuqi.nr.io.sb.service.BaseTableCreateDao;
import com.jiuqi.nr.io.sb.service.Impl.BaseActuator;
import com.jiuqi.nr.io.sb.service.Impl.SbIdTableCreateDao;
import com.jiuqi.nr.io.tz.TzConstants;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.FlagState;
import com.jiuqi.nr.io.tz.exception.ParamCheckException;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.tz.exception.TzCreateTmpTableException;
import com.jiuqi.nr.io.tz.exception.TzDataCheckException;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.listener.ChangeInfo;
import com.jiuqi.nr.io.tz.listener.ColumnData;
import com.jiuqi.nr.io.tz.listener.DataRecord;
import com.jiuqi.nr.io.tz.listener.TzDataChangeListener;
import com.jiuqi.nr.io.tz.service.impl.TzData2HisBySbIdInModel;
import com.jiuqi.nr.io.tz.service.impl.TzData2HisBySbIdModel;
import com.jiuqi.nr.io.tz.service.impl.TzData2HisModel;
import com.jiuqi.nvwa.encryption.common.EncryptionException;
import com.jiuqi.nvwa.encryption.crypto.SymmetricDecryptor;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

public class BufDBImportActuator
extends BaseActuator
implements ISBImportActuator {
    protected final Logger logger = LoggerFactory.getLogger(BufDBImportActuator.class);
    protected final byte DEFAULT = 0;
    protected final byte PARAM_INIT = 1;
    protected final byte TMP_INIT = (byte)2;
    protected final byte PUT_DATA = (byte)3;
    protected final byte COMMIT = (byte)4;
    protected final byte CLOSE = (byte)5;
    protected final byte ERROR = (byte)6;
    protected final byte FINISH = (byte)7;
    protected byte status = 0;
    protected BaseTableCreateDao mdCodeTableCreateDao;
    protected SbIdTableCreateDao sbIdTableCreateDao;
    protected IIOBufDBProvider provider;
    protected JdbcTemplate jdbcTemplate;
    protected List<TzDataChangeListener> listeners;
    protected ITempTableManager tempTableManager;
    protected ActuatorConfig config;
    protected Map<String, String> mapping;
    protected ISchema scheme;
    protected DataSchemeTmpTable tmpTable;
    protected ITable table;
    protected int currRowId;
    protected final Map<String, Integer> dwMap = new HashMap<String, Integer>();
    protected final Set<String> failDw = new HashSet<String>();
    protected final Map<RowDimValue, Integer> rowDimOrder = new LinkedHashMap<RowDimValue, Integer>();
    protected List<RowDimValue> rowDim = new ArrayList<RowDimValue>();
    protected List<RowDimValue> delRowDim = new ArrayList<RowDimValue>();
    protected final Map<String, Integer> tableIndex = new LinkedHashMap<String, Integer>();
    protected final String SRC_INDEX_SUFFIX = "_INDEX_ORDER";
    protected ITable srcTable;
    protected final Map<String, Integer> srcTableIndex = new LinkedHashMap<String, Integer>();
    protected final LinkedHashMap<Integer, DFAndTypeInfo> srcTableIndexEncrypted = new LinkedHashMap();
    protected ITable srcRptTable;
    protected final Map<String, Integer> srcRptTableIndex = new LinkedHashMap<String, Integer>();
    protected final LinkedHashMap<Integer, DFAndTypeInfo> srcRptTableIndexEncrypted = new LinkedHashMap();
    protected ITempTable mdCodeTable;
    protected String mdCodeColumnName;
    protected ITempTable sbIdTable;
    protected String sbIdColumnName;
    @Deprecated
    protected ITempTable sbIdTableTemp;
    protected int batchCount = 1000;
    protected LinkedHashMap<Integer, DFAndTypeInfo> colIndexType;
    protected LinkedHashMap<Integer, DFAndTypeInfo> rptColIndexType;
    protected SymmetricEncryptFactory sEFactory;
    protected SymmetricDecryptor decryptor;
    protected SymmetricEncryptor encryptor;
    protected final ImportInfo info = new ImportInfo();
    protected final StopWatch all = new StopWatch();
    protected StopWatch putCommit;
    protected final StopWatch watch = new StopWatch();
    protected int queryCount;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BufDBImportActuator setProvider(IIOBufDBProvider provider) {
        this.provider = provider;
        return this;
    }

    public BufDBImportActuator setSchemeService(IRuntimeDataSchemeService schemeService) {
        this.schemeService = schemeService;
        return this;
    }

    public BufDBImportActuator setSBImportActuatorConfig(ActuatorConfig config) {
        this.config = config;
        this.logger.info("\u53c2\u6570\u914d\u7f6e\u4fe1\u606f {}", (Object)this.config);
        return this;
    }

    public BufDBImportActuator setMdCodeTableCreateDao(BaseTableCreateDao mdCodeTableCreateDao) {
        this.mdCodeTableCreateDao = mdCodeTableCreateDao;
        return this;
    }

    public BufDBImportActuator setSbIdTableCreateDao(SbIdTableCreateDao sbIdTableCreateDao) {
        this.sbIdTableCreateDao = sbIdTableCreateDao;
        return this;
    }

    public BufDBImportActuator setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    public BufDBImportActuator setChangeListener(List<TzDataChangeListener> listeners) {
        this.listeners = listeners;
        return this;
    }

    public BufDBImportActuator setTempTableManager(ITempTableManager tempTableManager) {
        this.tempTableManager = tempTableManager;
        return this;
    }

    public BufDBImportActuator setSymmetricEncryptFactory(SymmetricEncryptFactory sEFactory) {
        this.sEFactory = sEFactory;
        return this;
    }

    @Override
    public SBImportActuatorType getType() {
        return SBImportActuatorType.BUF_DB;
    }

    @Override
    public void setDataFields(List<DataField> fields) {
        this.logger.info("setDataFields");
        if (this.status != 0) {
            this.logger.warn("\u5f53\u524d\u73af\u5883\u5f02\u5e38\uff0c\u65e0\u6cd5\u7ee7\u7eed\u6267\u884c,\u72b6\u6001{}", (Object)this.status);
            return;
        }
        this.all.start();
        this.status = 1;
        this.logger.debug("\u5bfc\u5165\u8bbe\u7f6e\u53c2\u6570\n {} ", (Object)fields);
        try {
            int i;
            Map mappingObj = this.config.getField2DimMap();
            if (!(mappingObj instanceof Map)) {
                throw new ParamCheckException("\u7f3a\u5c11\u6307\u6807\u7ef4\u5ea6\u6620\u5c04");
            }
            this.mapping = mappingObj;
            String tableKey = this.config.getDestTable();
            DataTable dataTable = this.schemeService.getDataTable(tableKey);
            if (dataTable == null) {
                this.close();
                this.status = (byte)6;
                throw new ParamCheckException("\u6ca1\u6709\u53c2\u6570\u8868\u5b9a\u4e49");
            }
            DataScheme dataScheme = this.schemeService.getDataScheme(dataTable.getDataSchemeKey());
            String encryptScene = dataScheme.getEncryptScene();
            if (StringUtils.hasLength(encryptScene)) {
                this.decryptor = this.sEFactory.createDecryptor();
                this.encryptor = this.sEFactory.createEncryptor(dataScheme.getEncryptScene());
            }
            this.tmpTable = this.initModel(dataTable, fields);
            this.logger.debug("\u6a21\u578b\u53c2\u6570\u6784\u5efa {} ", (Object)this.tmpTable);
            for (i = 0; i < fields.size(); ++i) {
                DataField dataField = fields.get(i);
                if (dataField == null) continue;
                this.tableIndex.put(dataField.getCode(), i);
            }
            for (DataField missingField : this.tmpTable.getMissingFields()) {
                this.tableIndex.put(missingField.getCode(), i);
                ++i;
            }
            this.logger.debug("\u6307\u6807\u8868\u793a\u5230\u7d22\u5f15\u7684\u6620\u5c04\n {} ", (Object)this.tableIndex);
        }
        catch (ParamCheckException e) {
            this.status = (byte)6;
            throw e;
        }
        catch (Exception e) {
            this.status = (byte)6;
            throw new ParamCheckException("\u6784\u5efa\u53c2\u6570\u9047\u5230\u672a\u77e5\u5f02\u5e38\uff0c\u5bfc\u5165\u5931\u8d25", e);
        }
    }

    @Override
    public void setMdCodeScope(Collection<String> codes) {
        for (String code : codes) {
            this.dwMap.put(code, null);
        }
    }

    @Override
    public void prepare() {
        this.logger.info("prepare");
        if (this.status != 1) {
            this.logger.warn("\u5f53\u524d\u73af\u5883\u5f02\u5e38\uff0c\u65e0\u6cd5\u7ee7\u7eed\u6267\u884c,\u72b6\u6001{}", (Object)this.status);
            return;
        }
        this.logger.debug("\u521d\u59cb\u5316\u73af\u5883 ");
        this.status = (byte)2;
        try {
            this.scheme = this.provider.getSchema();
            this.table = this.createTable(this.getInputTableDefine());
            this.initSrcIndexTypeMap();
        }
        catch (BufDBException e) {
            this.close();
            this.status = (byte)6;
            throw new TzCreateTmpTableException("\u521d\u59cb\u5316 bufDb \u5bfc\u5165\u73af\u5883\u5931\u8d25", e);
        }
        catch (TzCreateTmpTableException e) {
            this.close();
            this.status = (byte)6;
            throw new TzCreateTmpTableException("\u521d\u59cb\u5316\u5bfc\u5165\u73af\u5883\u5931\u8d25", e);
        }
        catch (Exception e) {
            this.close();
            this.status = (byte)6;
            throw new TzCreateTmpTableException("\u521d\u59cb\u5316\u5bfc\u5165\u73af\u5883\u9047\u5230\u672a\u77e5\u9519\u8bef", e);
        }
        if (this.putCommit == null) {
            this.putCommit = new StopWatch();
            this.putCommit.start();
        }
    }

    private TableDefine getInputTableDefine() {
        TableDefine tableDefine = new TableDefine();
        tableDefine.setName(TzConstants.createName());
        List fields = tableDefine.getFields();
        Stream.of(this.tmpTable.getAllFields(), this.tmpTable.getMissingFields()).flatMap(Collection::stream).map(this::df2fd).forEach(fields::add);
        this.tableIndex.put("_SBID", tableDefine.getFields().size());
        tableDefine.addField("_SBID", 6).setPrecision(50);
        this.tableIndex.put("_ID", fields.size());
        tableDefine.addField("_ID", 5).setPrecision(50);
        tableDefine.getKeyFields().add("_ID");
        this.tableIndex.put("_OPT", tableDefine.getFields().size());
        tableDefine.addField("_OPT", 5);
        this.tableIndex.put("_BIZKEYORDER", tableDefine.getFields().size());
        tableDefine.addField("_BIZKEYORDER", 6).setPrecision(50);
        this.tableIndex.put("_RPT_OPT", tableDefine.getFields().size());
        tableDefine.addField("_RPT_OPT", 5);
        this.tableIndex.put("_ORDINAL", tableDefine.getFields().size());
        tableDefine.addField("_ORDINAL", 5).setPrecision(10);
        this.logger.info("\u6784\u5efabufDb \u8f93\u5165\u8868 {}", (Object)tableDefine);
        return tableDefine;
    }

    private ITable createTable(TableDefine tableDefine) throws BufDBException {
        TableConfig config = new TableConfig();
        config.setReadOnly(false);
        config.setTemporary(true);
        return this.scheme.createTable(tableDefine, config);
    }

    private ITable openTable(String tableName) throws BufDBException {
        TableConfig tableConfig = new TableConfig();
        tableConfig.setReadOnly(false);
        tableConfig.setTemporary(true);
        return this.scheme.openTable(tableName, tableConfig);
    }

    private FieldDefine df2fd(DataField field) {
        DataFieldType dataFieldType = field.getDataFieldType();
        String fieldName = field.getCode();
        DataFieldDeployInfo deployInfo = this.tmpTable.getDeployInfoMap().get(field.getKey());
        if (deployInfo != null) {
            fieldName = deployInfo.getFieldName();
        }
        FieldDefine fieldDefine = new FieldDefine(fieldName, dataFieldType.getValue());
        this.parseFieldType(field, fieldDefine);
        return fieldDefine;
    }

    @Override
    public void put(List<Object> dataRow) {
        Object dwCode;
        if (this.status != 2 && this.status != 3) {
            this.logger.warn("\u5f53\u524d\u73af\u5883\u5f02\u5e38\uff0c\u65e0\u6cd5\u7ee7\u7eed\u6267\u884c,\u72b6\u6001{}", (Object)this.status);
            return;
        }
        this.status = (byte)3;
        if (this.tmpTable.getAllFields().size() != dataRow.size()) {
            int count = this.tmpTable.getAllFields().size() - dataRow.size();
            if (count > 0) {
                for (int n = 0; n < count; ++n) {
                    dataRow.add(null);
                }
            } else {
                throw new TzCopyDataException("\u6570\u636e\u5217\u4e2a\u6570\u8d85\u51fa\u6307\u6807\u6570\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
            }
        }
        if ((dwCode = dataRow.get(this.tableIndex.get("MDCODE"))) == null) {
            this.logger.error("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            throw new TzCopyDataException("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        String mdCode = dwCode.toString();
        if (this.failDw.contains(mdCode)) {
            this.addCurrRowId(null);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("\u8df3\u8fc7\u5355\u4f4d\u6570\u636e\u4e0d\u5bfc\u5165{}", (Object)mdCode);
            }
            return;
        }
        this.dwMap.put(mdCode, null);
        try {
            for (DataField ignored : this.tmpTable.getMissingFields()) {
                dataRow.add(null);
            }
            dataRow.add("NULL");
            dataRow.add(this.currRowId);
            dataRow.add((byte)0);
            dataRow.add("NULL");
            dataRow.add((byte)0);
            RowDimValue rowDimValue = new RowDimValue();
            rowDimValue.setMdCode(mdCode);
            this.tmpTable.getDimFields().forEach(r -> this.buildRowOrder(dataRow, rowDimValue, (DataField)r));
            int tableDimValuesSize = this.tmpTable.getTableDimFields().size();
            for (DataField tableDimField : this.tmpTable.getTableDimFields()) {
                if (DataFieldType.STRING != tableDimField.getDataFieldType() && DataFieldType.DATE != tableDimField.getDataFieldType() && DataFieldType.DATE_TIME != tableDimField.getDataFieldType()) {
                    throw new TzCopyDataException("\u4e0d\u652f\u6301\u8868\u5185\u7ef4\u5ea6\u4e3a\u975e\u5b57\u7b26\u6216\u65e5\u671f\u7c7b\u578b");
                }
                Integer colIndex = this.tableIndex.get(tableDimField.getCode());
                Iterator<Object> value = dataRow.get(colIndex);
                Object dimValue = value;
                if (value == null || "".equals(value)) {
                    if (DataFieldType.DATE == tableDimField.getDataFieldType() || DataFieldType.DATE_TIME == tableDimField.getDataFieldType()) {
                        dimValue = "9999R0001";
                        dimValue = DataTypesConvert.periodToDate((PeriodWrapper)new PeriodWrapper((String)dimValue));
                        dataRow.set(colIndex, dimValue);
                    } else {
                        dimValue = "-";
                        dataRow.set(colIndex, dimValue);
                    }
                    --tableDimValuesSize;
                }
                rowDimValue.getDim().add(dimValue);
            }
            if (tableDimValuesSize == 0) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("\u8868\u5185\u7ef4\u5ea6\u5168\u4e3a\u7a7a,\u8df3\u8fc7\u5355\u4f4d\u6570\u636e\u4e0d\u5bfc\u5165 {}", (Object)mdCode);
                }
                this.clearDataByMdCode(mdCode);
                this.addCurrRowId(null);
                return;
            }
            Integer order = this.rowDimOrder.getOrDefault(rowDimValue, 0);
            if (order > 0 && !this.tmpTable.getTable().isRepeatCode()) {
                List<Object> dim = rowDimValue.getDim();
                ArrayList<String> dimStr = new ArrayList<String>();
                for (Object o : dim) {
                    if (o instanceof Date) {
                        dimStr.add(this.dateFormat.format(o));
                        continue;
                    }
                    if (o != null) {
                        dimStr.add(o.toString());
                        continue;
                    }
                    dimStr.add("");
                }
                String msg = "[" + String.join((CharSequence)",", dimStr) + "]";
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("\u8868\u5185\u7ef4\u5ea6\u91cd\u590d:{}", (Object)rowDimValue);
                }
                throw new TzDataCheckException("\u8868\u5185\u7ef4\u5ea6\u91cd\u590d:" + msg);
            }
            Integer currOrder = order = Integer.valueOf(order + 1);
            this.rowDimOrder.put(rowDimValue, currOrder);
            this.addCurrRowId(rowDimValue);
            dataRow.add(currOrder);
            this.table.put(dataRow);
        }
        catch (BufDBException e) {
            this.logger.warn("\u6570\u636e\u4e0d\u6b63\u5e38\uff0c\u73af\u5883\u4e0d\u5173\u95ed\uff0c\u53ef\u7ee7\u7eed\u5bfc\u5165\u6570\u636e");
            throw new TzCopyDataException("\u6570\u636e\u5f02\u5e38", e);
        }
        catch (TzCopyDataException e) {
            this.logger.warn("\u6570\u636e\u4e0d\u6b63\u5e38\uff0c\u73af\u5883\u4e0d\u5173\u95ed\uff0c\u53ef\u7ee7\u7eed\u5bfc\u5165\u6570\u636e");
            throw e;
        }
        catch (TzDataCheckException e) {
            this.close();
            this.status = (byte)this.ERROR;
            throw e;
        }
        catch (Exception e) {
            this.close();
            this.status = (byte)this.ERROR;
            this.logger.error("\u521d\u59cb\u5316\u5bfc\u5165\u6570\u636e\u9047\u5230\u672a\u77e5\u9519\u8bef\uff0c\u73af\u5883\u5173\u95ed\uff0c\u4e0d\u53ef\u7ee7\u7eed\u5bfc\u5165\u6570\u636e", e);
            throw new TzImportException("\u521d\u59cb\u5316\u5bfc\u5165\u6570\u636e\u9047\u5230\u672a\u77e5\u9519\u8bef", e);
        }
    }

    private void addCurrRowId(RowDimValue rowDimValue) {
        this.rowDim.add(rowDimValue);
        ++this.currRowId;
    }

    private void clearSrcDataByMdCode(String ... mdCode) throws BufDBException {
        List<String> list = Arrays.asList(mdCode);
        ValuesFilter filter = new ValuesFilter();
        filter.setFieldName("MDCODE");
        filter.getValues().addAll(list);
        ICursor cursor = this.srcTable.openCursor(new IFilter[]{filter});
        while (cursor.next()) {
            cursor.delete();
        }
        cursor.close();
        if (this.logger.isDebugEnabled()) {
            this.logger.info("\u8df3\u8fc7 {} \u5355\u4f4d\u6570\u636e\u88c5\u5165", (Object)list);
        }
    }

    private void clearDataByMdCode(String ... mdCode) throws BufDBException {
        List<String> list = Arrays.asList(mdCode);
        this.failDw.addAll(list);
        for (String code : list) {
            this.dwMap.remove(code);
        }
        ValuesFilter filter = new ValuesFilter();
        filter.setFieldName("MDCODE");
        filter.getValues().addAll(list);
        ICursor cursor = this.table.openCursor(new IFilter[]{filter});
        while (cursor.next()) {
            IRecord record = cursor.getRecord();
            int id = record.getInt(this.tableIndex.get("_ID").intValue());
            this.rowDim.set(id, null);
            cursor.delete();
        }
        cursor.close();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\u8df3\u8fc7 {} \u5355\u4f4d\u6570\u636e\u88c5\u5165", (Object)list);
        }
    }

    private void buildRowOrder(List<Object> dataRow, RowDimValue rowDimValue, DataField field) {
        int colIndex = this.tableIndex.get(field.getCode());
        Object value = dataRow.get(colIndex);
        rowDimValue.getDim().add(value);
    }

    @Override
    public ImportInfo commit() {
        this.logger.info("commit,\u5f00\u59cb\u63d0\u4ea4\uff0c\u63d0\u4ea4\u6570\u636e {} \u6761", (Object)this.currRowId);
        if (this.status > 3) {
            this.logger.warn("\u5f53\u524d\u73af\u5883\u5f02\u5e38\uff0c\u65e0\u6cd5\u7ee7\u7eed\u6267\u884c,\u72b6\u6001{}", (Object)this.status);
            return this.info;
        }
        if (this.putCommit != null && this.putCommit.isRunning()) {
            this.putCommit.stop();
        }
        this.status = (byte)4;
        try {
            boolean allMode;
            this.mdCodeTable = this.tempTableManager.getOneKeyTempTable();
            this.mdCodeColumnName = ((LogicField)this.mdCodeTable.getMeta().getLogicFields().get(0)).getFieldName();
            this.sbIdTable = this.tempTableManager.getOneKeyTempTable();
            this.sbIdColumnName = ((LogicField)this.sbIdTable.getMeta().getLogicFields().get(0)).getFieldName();
            if (!this.initMdCodeTable()) {
                this.close();
                this.logger.info("\u65e0\u5355\u4f4d\u6570\u636e\u8df3\u8fc7\u5bfc\u5165");
                return null;
            }
            int size = this.failDw.size();
            this.filterDwByPeriod();
            if (this.failDw.size() > size) {
                if (this.dwMap.size() == this.failDw.size() - size) {
                    this.logger.info("\u6ca1\u6709\u6570\u636e\u5bfc\u5165\uff0c\u6570\u636e\u90fd\u6ca1\u901a\u8fc7\u68c0\u67e5");
                    this.buildInfo(new FlagState());
                    this.close();
                    return this.info;
                }
                String[] mdCodes = this.failDw.toArray(new String[0]);
                this.clearDataByMdCode(mdCodes);
            }
            this.delMdCodeTmp();
            boolean clearData = this.isClearData();
            if (!clearData) {
                this.watch.start("queryData");
                this.queryData();
                this.watch.stop();
            }
            boolean containPeriodField = !this.tmpTable.getPeriodicFields().isEmpty();
            ImpMode importMode = this.config.getMode();
            if (importMode == null) {
                importMode = ImpMode.FULL;
            }
            boolean bl = allMode = importMode != ImpMode.ADD;
            if (containPeriodField && !allMode && !clearData) {
                this.queryRptData();
            }
            if (!clearData) {
                this.watch.start("dataRank");
                this.dataRank(this.srcTable, "FLOATORDER", "SBID");
                this.watch.stop();
            }
            this.watch.start("flagData");
            FlagState flagState = this.flagData();
            this.watch.stop();
            this.logger.info("\u6807\u8bb0\u5b8c\u6210\uff1a\u53f0\u8d26\u4fe1\u606f\u8868{}\u6761\u6570\u636e\u5220\u9664\uff0c{}\u6761\u6570\u65b0\u589e\uff0c{}\u6761\u6570\u636e\u66f4\u65b0\uff0c{}\u6761\u6570\u636e\u66f4\u65b0\u4f46\u4e0d\u8bb0\u5f55\u53d8\u66f4\uff0c{}\u6761\u6570\u636e\u65e0\u53d8\u5316;\u53f0\u8d26\u62a5\u8868{}\u6761\u6570\u65b0\u589e\uff0c{}\u6761\u6570\u636e\u66f4\u65b0", flagState.getDel(), flagState.getAdd(), flagState.getRecordUpdate(), flagState.getNoRecordUpdate(), flagState.getNone(), flagState.getRptAdd(), flagState.getRptUpdate());
            Connection connection = null;
            try {
                connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
                connection.setAutoCommit(false);
                this.watch.start("processDelData");
                if (flagState.isContainsDel()) {
                    Integer del = flagState.getDel();
                    this.sbData2HisAndDelBatchMode(connection, del);
                    this.delSbIdTmp(connection);
                }
                this.watch.stop();
                this.watch.start("processSbData");
                if (flagState.isRptChange() || flagState.isChange()) {
                    this.processSbData(connection);
                }
                connection.commit();
                this.watch.stop();
            }
            catch (TzImportException | SQLException | DataAccessException e) {
                if (connection != null) {
                    connection.rollback();
                }
                throw new TzImportException("\u5f00\u542f\u4e8b\u7269\u540e\u5904\u7406\u6570\u636e\u5165\u5e93\u5931\u8d25", e);
            }
            catch (Exception e) {
                if (connection != null) {
                    connection.rollback();
                }
                throw e;
            }
            finally {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
            this.buildInfo(flagState);
        }
        catch (TzImportException e) {
            this.close();
            this.status = (byte)6;
            throw new TzImportException("\u5bfc\u5165\u6b63\u5fd9\uff0c\u5176\u4ed6\u7528\u6237\u6b63\u5728\u5bfc\u5165\u8be5\u6570\u636e\uff0c\u8bf7\u60a8\u7a0d\u540e\u91cd\u8bd5", e);
        }
        catch (BufDBException e) {
            this.close();
            this.status = (byte)6;
            throw new TzImportException("\u5bfc\u5165\u6b63\u5fd9\uff0c\u8bf7\u60a8\u7a0d\u540e\u91cd\u8bd5", e);
        }
        catch (Exception e) {
            this.close();
            this.status = (byte)6;
            throw new TzImportException("\u5bfc\u5165\u6570\u636e\u9047\u5230\u672a\u77e5\u9519\u8bef\uff0c\u8bf7\u60a8\u7a0d\u540e\u91cd\u8bd5", e);
        }
        this.close();
        this.status = (byte)7;
        this.logger.info("FINISH");
        return this.info;
    }

    private boolean isClearData() throws BufDBException {
        try {
            boolean empty = this.table.isEmpty();
            if (empty) {
                this.logger.info("\u8f93\u5165\u6570\u636e\u4e3a\u7a7a,\u6e05\u9664\u6a21\u5f0f,\u65e0\u9700\u67e5\u8be2\u6570\u636e\u5e93\u539f\u59cb\u6570\u636e");
            }
            return empty;
        }
        catch (Exception e) {
            throw new BufDBException((Throwable)e);
        }
    }

    private void delMdCodeTmp() {
        if (this.failDw.isEmpty()) {
            return;
        }
        String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} in (:code)", this.mdCodeTable.getTableName(), this.mdCodeColumnName);
        this.logger.debug(sql);
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("code", this.failDw);
        jdbc.update(sql, (SqlParameterSource)source);
    }

    private void buildInfo(FlagState state) {
        String sbIdDim = this.mapping.getOrDefault("SBID", "SBID");
        String bizKeyDim = this.mapping.getOrDefault("BIZKEYORDER", "BIZKEYORDER");
        String mdCodeDim = this.mapping.getOrDefault("MDCODE", "MDCODE");
        LinkedHashMap<String, Integer> nameMap = new LinkedHashMap<String, Integer>();
        int i = 0;
        List dims = Stream.of(this.tmpTable.getDimFields(), this.tmpTable.getTableDimFields()).flatMap(Collection::stream).collect(Collectors.toList());
        for (DataField dim : dims) {
            String dimName = this.mapping.getOrDefault(dim.getCode(), dim.getCode());
            nameMap.put(dimName, i++);
        }
        List<Map<String, String>> dimValues = this.info.getDimValues();
        if (dimValues == null) {
            dimValues = new ArrayList<Map<String, String>>();
            this.info.setDimValues(dimValues);
        }
        String period = this.config.getDestPeriod();
        boolean notice = state.isHaveData() && !CollectionUtils.isEmpty(this.listeners) && state.isChange();
        ChangeInfo changeInfo = this.initChangeInfo();
        ArrayList<DataRecord> insertRecords = new ArrayList<DataRecord>();
        changeInfo.setInsertRecords(insertRecords);
        ArrayList<DataRecord> updateRecords = new ArrayList<DataRecord>();
        changeInfo.setUpdateRecords(updateRecords);
        for (RowDimValue rowDimValue : this.rowDim) {
            if (null == rowDimValue || !state.isHaveData()) {
                dimValues.add(null);
                continue;
            }
            HashMap<String, String> map = new HashMap<String, String>();
            String mdCode = rowDimValue.getMdCode();
            map.put(mdCodeDim, mdCode);
            map.put("DATATIME", period);
            String sbId = rowDimValue.getSbId();
            map.put(sbIdDim, sbId);
            map.put(bizKeyDim, sbId);
            List<Object> dim = rowDimValue.getDim();
            nameMap.forEach((k, v) -> {
                Object value = dim.get((int)v);
                if (value instanceof Date) {
                    value = this.dateFormat.format((Date)value);
                }
                map.put((String)k, value == null ? "" : value.toString());
            });
            dimValues.add(map);
            if (!notice) continue;
            switch (rowDimValue.getOpt()) {
                case 4: {
                    DataRecord record = this.getInsertRecord(changeInfo, rowDimValue);
                    insertRecords.add(record);
                    break;
                }
                case 2: 
                case 3: {
                    DataRecord record = this.getUpdateRecord(changeInfo, rowDimValue);
                    updateRecords.add(record);
                    break;
                }
            }
        }
        this.rowDim = null;
        if (notice) {
            changeInfo.setDeleteRecords(this.buildDelRecord(changeInfo));
            this.delRowDim = null;
            for (TzDataChangeListener listener : this.listeners) {
                try {
                    listener.onDataChange(changeInfo);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private DataRecord getUpdateRecord(ChangeInfo changeInfo, RowDimValue rowDimValue) {
        Object dimValue;
        DataRecord record = new DataRecord();
        HashMap<String, ColumnData> columnData = new HashMap<String, ColumnData>();
        record.setColumnData(columnData);
        ColumnData sbIdCol = new ColumnData(rowDimValue.getSbId(), rowDimValue.getSbId(), DataFieldType.STRING.getValue());
        columnData.put("SBID", sbIdCol);
        columnData.put("BIZKEYORDER", sbIdCol);
        columnData.put("MDCODE", new ColumnData(rowDimValue.getMdCode(), rowDimValue.getMdCode(), DataFieldType.STRING.getValue()));
        List<DataField> dimFields = changeInfo.getDimFields();
        List<DataField> tableDimFields = changeInfo.getTableDimFields();
        int i = 0;
        for (DataField dimField : dimFields) {
            dimValue = rowDimValue.getDim().get(i++);
            columnData.put(dimField.getCode(), new ColumnData(dimValue, dimValue, dimField.getDataFieldType().getValue()));
        }
        for (DataField dimField : tableDimFields) {
            dimValue = rowDimValue.getDim().get(i++);
            columnData.put(dimField.getCode(), new ColumnData(dimValue, dimValue, dimField.getDataFieldType().getValue()));
        }
        i = 0;
        for (DataField field : changeInfo.getTimePointFields()) {
            Object value = rowDimValue.getValues().get(i);
            Object oldValue = rowDimValue.getOldValues().get(i++);
            columnData.put(field.getCode(), new ColumnData(value, oldValue, field.getDataFieldType().getValue()));
        }
        return record;
    }

    private DataRecord getInsertRecord(ChangeInfo changeInfo, RowDimValue rowDimValue) {
        DataRecord record = new DataRecord();
        HashMap<String, ColumnData> columnData = new HashMap<String, ColumnData>();
        record.setColumnData(columnData);
        ColumnData sbIdCol = new ColumnData(rowDimValue.getSbId(), DataFieldType.STRING.getValue());
        columnData.put("SBID", sbIdCol);
        columnData.put("BIZKEYORDER", sbIdCol);
        columnData.put("MDCODE", new ColumnData(rowDimValue.getMdCode(), DataFieldType.STRING.getValue()));
        List<DataField> dimFields = changeInfo.getDimFields();
        List<DataField> tableDimFields = changeInfo.getTableDimFields();
        int i = 0;
        for (DataField dimField : dimFields) {
            columnData.put(dimField.getCode(), new ColumnData(rowDimValue.getDim().get(i++), dimField.getDataFieldType().getValue()));
        }
        for (DataField dimField : tableDimFields) {
            columnData.put(dimField.getCode(), new ColumnData(rowDimValue.getDim().get(i++), dimField.getDataFieldType().getValue()));
        }
        i = 0;
        for (DataField field : changeInfo.getTimePointFields()) {
            columnData.put(field.getCode(), new ColumnData(rowDimValue.getValues().get(i++), field.getDataFieldType().getValue()));
        }
        return record;
    }

    private List<DataRecord> buildDelRecord(ChangeInfo changeInfo) {
        ArrayList<DataRecord> deleteRecords = new ArrayList<DataRecord>(this.delRowDim.size());
        for (RowDimValue rowDimValue : this.delRowDim) {
            DataRecord record = new DataRecord();
            HashMap<String, ColumnData> columnData = new HashMap<String, ColumnData>();
            ColumnData sbId = new ColumnData(null, rowDimValue.getSbId(), DataFieldType.STRING.getValue());
            columnData.put("SBID", sbId);
            columnData.put("BIZKEYORDER", sbId);
            columnData.put("MDCODE", new ColumnData(null, rowDimValue.getMdCode(), DataFieldType.STRING.getValue()));
            List<DataField> dimFields = changeInfo.getDimFields();
            List<DataField> tableDimFields = changeInfo.getTableDimFields();
            int i = 0;
            for (DataField dimField : dimFields) {
                columnData.put(dimField.getCode(), new ColumnData(null, rowDimValue.getDim().get(i++), dimField.getDataFieldType().getValue()));
            }
            for (DataField dimField : tableDimFields) {
                columnData.put(dimField.getCode(), new ColumnData(null, rowDimValue.getDim().get(i++), dimField.getDataFieldType().getValue()));
            }
            i = 0;
            for (DataField field : changeInfo.getTimePointFields()) {
                columnData.put(field.getCode(), new ColumnData(null, rowDimValue.getOldValues().get(i++), field.getDataFieldType().getValue()));
            }
            record.setColumnData(columnData);
            deleteRecords.add(record);
        }
        return deleteRecords;
    }

    private ChangeInfo initChangeInfo() {
        ChangeInfo info = new ChangeInfo();
        info.setTable(this.tmpTable.getTable());
        info.setMdCode(this.tmpTable.getMdCode());
        info.setPeriod(this.tmpTable.getPeriod());
        info.setDimFields(this.tmpTable.getDimFields());
        info.setTableDimFields(this.tmpTable.getTableDimFields());
        info.setTimePointFields(this.tmpTable.getTimePointFields());
        info.setPeriodicFields(this.tmpTable.getPeriodicFields());
        List<DataField> allFields = this.tmpTable.getAllFields();
        ArrayList<DataField> all = new ArrayList<DataField>(allFields);
        all.addAll(this.tmpTable.getMissingFields());
        info.setAllFields(all);
        info.setFieldMap(this.tmpTable.getFieldMap());
        info.setDatatime(this.config.getDestPeriod());
        return info;
    }

    private void processSbData(Connection connection) throws BufDBException {
        boolean notice;
        this.logger.info("processSbData");
        Integer idIndex = this.tableIndex.get("_ID");
        Integer optIndex = this.tableIndex.get("_OPT");
        Integer rptOptIndex = this.tableIndex.get("_RPT_OPT");
        Integer sbIdIndex = this.tableIndex.get("_SBID");
        Integer rptBizKeyOrderIndex = this.tableIndex.get("_BIZKEYORDER");
        String period = this.config.getDestPeriod();
        int limit = this.batchCount;
        ArrayList<Object[]> addValues = new ArrayList<Object[]>(limit);
        List<String> updateCol = this.getUpdateCol();
        ArrayList<Object[]> updateValues = new ArrayList<Object[]>(limit);
        List<String> noRecordUpdateCol = this.getNoRecordUpdateCol();
        ArrayList<Object[]> noRecUpdateValues = new ArrayList<Object[]>(limit);
        boolean containPeriodField = !this.tmpTable.getPeriodicFields().isEmpty();
        boolean allMode = this.config.getMode() != ImpMode.ADD;
        ArrayList<Object[]> addRptValues = null;
        ArrayList<Object[]> updateRptValues = null;
        List<String> rptUpdateCol = null;
        List<String> rptAddCol = null;
        if (containPeriodField) {
            addRptValues = new ArrayList<Object[]>();
            updateRptValues = new ArrayList<Object[]>();
            rptUpdateCol = this.getRptUpdateCol();
            rptAddCol = this.getRptInsertCol();
            if (allMode) {
                this.delRtpData(connection);
            }
        }
        Double begin = null;
        IIndexedCursor cursor = this.table.openCursor();
        Timestamp now = Timestamp.from(Instant.now());
        int formIndex = this.tmpTable.getDimFields().size() + this.tmpTable.getTableDimFields().size() + 1;
        int toIndex = formIndex + this.tmpTable.getTimePointFields().size();
        boolean bl = notice = !CollectionUtils.isEmpty(this.listeners);
        while (cursor.next()) {
            IRecord record = cursor.getRecord();
            int opt = record.getInt(optIndex.intValue());
            int rptOpt = record.getInt(rptOptIndex.intValue());
            if (opt == 4) {
                int id = record.getInt(idIndex.intValue());
                RowDimValue rowDimValue = this.rowDim.get(id);
                Object[] row = new Object[this.colIndexType.size() + 5];
                this.collectColData2(row, this.colIndexType, record);
                if (begin == null) {
                    String maxOrderSql = "SELECT MAX(%s) FROM %s WHERE %s IN (SELECT %s FROM %s)";
                    maxOrderSql = String.format(maxOrderSql, "FLOATORDER", this.tmpTable.getTzTableName(), "MDCODE", this.mdCodeColumnName, this.mdCodeTable.getTableName());
                    Optional<Object> max = Optional.ofNullable(this.jdbcTemplate.query(maxOrderSql, rs -> {
                        if (rs.next()) {
                            return rs.getDouble(1);
                        }
                        return 0.0;
                    }));
                    begin = (Double)max.orElse(0.0);
                }
                begin = begin + 10.0;
                if (notice) {
                    rowDimValue.setValues(new ArrayList<Object>());
                    for (int i = formIndex; i < toIndex; ++i) {
                        rowDimValue.addValue(row[i]);
                    }
                    rowDimValue.setOpt(4);
                }
                String sbId = record.getString(sbIdIndex.intValue());
                row[this.colIndexType.size()] = sbId;
                row[this.colIndexType.size() + 1] = sbId;
                row[this.colIndexType.size() + 2] = period;
                row[this.colIndexType.size() + 3] = begin;
                row[this.colIndexType.size() + 4] = now;
                addValues.add(row);
            } else if (opt == 2) {
                Object[] row = new Object[this.colIndexType.size() + 3];
                this.collectColData2(row, this.colIndexType, record);
                row[this.colIndexType.size()] = period;
                String sbId = record.getString(sbIdIndex.intValue());
                row[this.colIndexType.size() + 1] = now;
                row[this.colIndexType.size() + 2] = sbId;
                updateValues.add(row);
            } else if (opt == 3) {
                Object[] row = new Object[this.colIndexType.size() + 2];
                this.collectColData2(row, this.colIndexType, record);
                row[this.colIndexType.size()] = now;
                row[this.colIndexType.size() + 1] = record.getString(sbIdIndex.intValue());
                noRecUpdateValues.add(row);
            }
            if (addValues.size() == limit) {
                this.addSbData(addValues, connection);
                addValues.clear();
            }
            if (updateValues.size() == limit) {
                this.logger.info("\u5206\u6279\u5904\u7406\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e");
                this.sbData2HisAsIn(connection, updateValues);
                this.updateSbData(updateCol, updateValues, connection);
                updateValues.clear();
            }
            if (noRecUpdateValues.size() == limit) {
                this.logger.info("\u5206\u6279\u5904\u7406\u66f4\u65b0\u4f46\u4e0d\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e");
                this.updateSbData(noRecordUpdateCol, noRecUpdateValues, connection);
                noRecUpdateValues.clear();
            }
            if (!containPeriodField) continue;
            if (rptOpt == 4) {
                Object[] row = new Object[this.rptColIndexType.size() + 3];
                this.collectColData2(row, this.rptColIndexType, record);
                row[this.rptColIndexType.size()] = UUIDUtils.getKey();
                row[this.rptColIndexType.size() + 1] = record.getString(sbIdIndex.intValue());
                row[this.rptColIndexType.size() + 2] = this.config.getDestPeriod();
                addRptValues.add(row);
                if (addRptValues.size() != limit) continue;
                this.logger.info("\u65b0\u589e\u53f0\u8d26\u62a5\u8868\u6570\u636e");
                this.insertRtpData(rptAddCol, addRptValues, connection);
                addRptValues.clear();
                continue;
            }
            if (rptOpt != 2) continue;
            Object[] row = new Object[this.rptColIndexType.size() + 1];
            this.collectColData2(row, this.rptColIndexType, record);
            row[this.rptColIndexType.size()] = record.getString(rptBizKeyOrderIndex.intValue());
            updateRptValues.add(row);
            if (updateRptValues.size() != limit) continue;
            this.logger.info("\u66f4\u65b0\u53f0\u8d26\u62a5\u8868\u6570\u636e");
            this.updateRtpData(rptUpdateCol, updateRptValues, connection);
            updateRptValues.clear();
        }
        if (!addValues.isEmpty()) {
            this.addSbData(addValues, connection);
        }
        if (!updateValues.isEmpty()) {
            this.logger.info("\u5206\u6279\u5904\u7406\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e");
            this.sbData2HisAsIn(connection, updateValues);
            this.updateSbData(updateCol, updateValues, connection);
        }
        if (!noRecUpdateValues.isEmpty()) {
            this.logger.info("\u5206\u6279\u5904\u7406\u66f4\u65b0\u4f46\u4e0d\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e");
            this.updateSbData(noRecordUpdateCol, noRecUpdateValues, connection);
        }
        if (containPeriodField && !addRptValues.isEmpty()) {
            this.logger.info("\u65b0\u589e\u53f0\u8d26\u62a5\u8868\u6570\u636e");
            this.insertRtpData(rptAddCol, addRptValues, connection);
        }
        if (containPeriodField && !updateRptValues.isEmpty()) {
            this.logger.info("\u66f4\u65b0\u53f0\u8d26\u62a5\u8868\u6570\u636e");
            this.updateRtpData(rptUpdateCol, updateRptValues, connection);
        }
    }

    private List<String> getUpdateCol() {
        List<String> col = this.getSbDataCol();
        col.add("VALIDDATATIME");
        col.add("MODIFYTIME");
        return col;
    }

    private List<String> getNoRecordUpdateCol() {
        List<String> col = this.getSbDataCol();
        col.add("MODIFYTIME");
        return col;
    }

    private List<String> getRptUpdateCol() {
        return this.getRptDataCol();
    }

    private List<String> getRptInsertCol() {
        List<String> rptDataCol = this.getRptDataCol();
        rptDataCol.add("BIZKEYORDER");
        rptDataCol.add("SBID");
        rptDataCol.add("DATATIME");
        return rptDataCol;
    }

    private List<String> getRptDataCol() {
        ArrayList<String> col = new ArrayList<String>();
        for (Map.Entry<Integer, DFAndTypeInfo> entry : this.rptColIndexType.entrySet()) {
            Integer index = entry.getKey();
            String name = this.table.getMetadata().getName(index.intValue());
            col.add(name);
        }
        return col;
    }

    private void updateSbData(List<String> col, List<Object[]> updateValues, Connection connection) {
        this.logger.info("\u5206\u6279\u66f4\u65b0\u4e3b\u8868\u6570\u636e");
        String tzTableName = this.tmpTable.getTzTableName();
        String update = "update {0} set {1} where {2} = ?";
        String sql = MessageFormat.format(update, tzTableName, String.join((CharSequence)"=?,", col) + "=?", "SBID");
        this.logger.debug(sql);
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, updateValues);
            this.logger.info("\u5206\u6279\u66f4\u65b0\u4e3b\u8868\u6570\u636e\u5b8c\u6210");
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("sql: " + sql + "\nvalues: \n");
            for (Object[] updateValue : updateValues) {
                msg.append(Arrays.toString(updateValue));
                msg.append("\n");
            }
            msg.append("operation:").append("updateSbData").append("\n");
            msg.append(e.getMessage());
            this.logger.info(msg.toString());
            throw new TzImportException("\u66f4\u65b0\u6570\u636e\u9519\u8bef", e);
        }
    }

    private void sbId2TempTable(List<Object[]> copySbId, Connection connection) {
        this.logger.info("\u5f85\u64cd\u4f5c\u7684 SBID \u63d2\u5165\u8f85\u52a9\u8868");
        this.logger.info("sbId2TempTable");
        String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})", this.sbIdTable.getTableName(), this.sbIdColumnName, "?");
        try {
            this.logger.debug(sql);
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, copySbId);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("sql: " + sql + "\nvalues: \n");
            for (Object[] updateValue : copySbId) {
                msg.append(Arrays.toString(updateValue));
                msg.append("\n");
            }
            msg.append("operation:").append("sbId2TempTable").append("\n");
            msg.append(e.getMessage());
            this.logger.info(msg.toString());
            throw new TzImportException("\u66f4\u65b0\u6570\u636e\u9519\u8bef", e);
        }
    }

    private void addSbData(List<Object[]> addValues, Connection connection) {
        this.logger.info("\u65b0\u589e\u7684\u6570\u636e\u5206\u6279\u63d2\u5165\u5230\u53f0\u8d26\u4fe1\u606f\u8868");
        String tzTableName = this.tmpTable.getTzTableName();
        List<String> col = this.getSbDataCol();
        col.add("SBID");
        col.add("BIZKEYORDER");
        col.add("VALIDDATATIME");
        col.add("FLOATORDER");
        col.add("MODIFYTIME");
        StringBuilder value = new StringBuilder();
        for (int i = 1; i < col.size(); ++i) {
            value.append("?,");
        }
        String valueSql = value.append("?").toString();
        String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})", tzTableName, String.join((CharSequence)",", col), valueSql);
        this.logger.debug(sql);
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, addValues);
            this.logger.info("\u65b0\u589e\u7684\u6570\u636e\u5206\u6279\u63d2\u5165\u5230\u53f0\u8d26\u4fe1\u606f\u8868\u5b8c\u6210");
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("sql: " + sql + "\nvalues: \n");
            for (Object[] updateValue : addValues) {
                msg.append(Arrays.toString(updateValue));
                msg.append("\n");
            }
            msg.append("operation:").append("addSbData").append("\n");
            msg.append(e.getMessage());
            this.logger.info(msg.toString());
            throw new TzImportException("\u65b0\u589e\u6570\u636e\u9519\u8bef", e);
        }
    }

    protected FlagState flagData() throws BufDBException {
        this.logger.info("flagData");
        if (this.isClearData()) {
            return this.copyDelSbId2Tmp();
        }
        return this.flagData0();
    }

    protected FlagState copyDelSbId2Tmp() {
        this.logger.info("\u590d\u5236\u5f85\u5220\u9664\u6570\u636eSBID\u5230\u4e34\u65f6\u8868\u8fdb\u884c\u4e2d...");
        String select = MessageFormat.format("SELECT {0} FROM {1} T0 INNER JOIN {2} T1 ON T0.MDCODE = T1.{3} WHERE T0.{4} <= ?", "SBID", this.tmpTable.getTzTableName(), this.mdCodeTable.getTableName(), this.mdCodeColumnName, "VALIDDATATIME");
        String insertIntoSql = MessageFormat.format("INSERT INTO {0} {1}", this.sbIdTable.getTableName(), select);
        int update = this.jdbcTemplate.update(insertIntoSql, new Object[]{this.config.getDestPeriod()});
        this.logger.info("\u6bd4\u8f83\u6570\u636e 0 \u6761\u6570\u636e\u65b0\u589e\u6216\u53d8\u66f4\uff0c{} \u6761\u6570\u636e\u9700\u4ece\u4e3b\u8868\u5220\u9664", (Object)update);
        FlagState flagState = new FlagState();
        flagState.setDel(update);
        return flagState;
    }

    protected FlagState flagData0() throws BufDBException {
        this.logger.info("flagData");
        boolean notice = !CollectionUtils.isEmpty(this.listeners);
        boolean containPeriodField = !this.tmpTable.getPeriodicFields().isEmpty();
        boolean allMode = this.config.getMode() != ImpMode.ADD;
        FlagState flagState = new FlagState();
        List<DataField> timePointFields = this.tmpTable.getTimePointFields();
        ArrayList<Integer> noVersionIndex = new ArrayList<Integer>();
        ArrayList<Integer> srcNoVersionIndex = new ArrayList<Integer>();
        LinkedHashMap<Integer, Integer> index2Type = new LinkedHashMap<Integer, Integer>();
        LinkedHashMap<Integer, Integer> srcIndex2Type = new LinkedHashMap<Integer, Integer>();
        for (DataField timePointField : timePointFields) {
            if (!timePointField.isGenerateVersion()) {
                noVersionIndex.add(index2Type.size());
                srcNoVersionIndex.add(srcIndex2Type.size());
            }
            index2Type.put(this.tableIndex.get(timePointField.getCode()), timePointField.getDataFieldType().getValue());
            srcIndex2Type.put(this.srcTableIndex.get(timePointField.getCode()), timePointField.getDataFieldType().getValue());
        }
        LinkedHashMap<Integer, Integer> srcRptIndex2Type = null;
        IIndexedCursor srcRptCursor = null;
        Object[] srcRptRowData = null;
        Object[] rptRowData = null;
        if (containPeriodField && !allMode) {
            srcRptCursor = this.srcRptTable.openCursor(this.srcRptTable.getName() + "_INDEX_ORDER");
            srcRptIndex2Type = new LinkedHashMap<Integer, Integer>();
            srcRptIndex2Type.put(this.srcRptTableIndex.get("MDCODE"), 6);
            List<DataField> dimFields = this.tmpTable.getDimFields();
            for (DataField dataField : dimFields) {
                srcRptIndex2Type.put(this.srcRptTableIndex.get(dataField.getCode()), dataField.getDataFieldType().getValue());
            }
            List<DataField> periodicFields = this.tmpTable.getPeriodicFields();
            for (DataField periodField : periodicFields) {
                srcRptIndex2Type.put(this.srcRptTableIndex.get(periodField.getCode()), periodField.getDataFieldType().getValue());
            }
            srcRptRowData = new Object[srcRptIndex2Type.size()];
            rptRowData = new Object[this.rptColIndexType.size()];
        }
        Integer idIndex = this.tableIndex.get("_ID");
        Integer orderIndex = this.tableIndex.get("_ORDINAL");
        Integer n = this.tableIndex.get("_OPT");
        Integer rptBizKeyOrderIndex = this.tableIndex.get("_BIZKEYORDER");
        Integer rptOptIndex = this.tableIndex.get("_RPT_OPT");
        Integer sbIdIndex = this.tableIndex.get("_SBID");
        Integer srcOptIndex = this.srcTableIndex.get("_OPT");
        Integer srcSbIdIndex = this.srcTableIndex.get("SBID");
        Integer validTimeIndex = this.srcTableIndex.get("VALIDDATATIME");
        Integer srcRptBizKeyOrderIndex = this.srcRptTableIndex.get("BIZKEYORDER");
        ArrayList<Object> row = new ArrayList<Object>();
        Object[] rowData = new Object[index2Type.size()];
        Object[] srcRowData = new Object[index2Type.size()];
        int addCount = 0;
        int recordUpdateCount = 0;
        int noRecordUpdateCount = 0;
        int rptAddCount = 0;
        int tqUpdate = 0;
        int noVerUpdate = 0;
        int verUpdate = 0;
        int eqUpdate0 = 0;
        int eqUpdate = 0;
        int rptUpdate = 0;
        int noneCount = 0;
        IIndexedCursor cursor = this.table.openCursor();
        IIndexedCursor srcCursor = this.srcTable.openCursor(this.srcTable.getName() + "_INDEX_ORDER");
        while (cursor.next()) {
            String sbId;
            IRecord record = cursor.getRecord();
            row.clear();
            int id = record.getInt(idIndex.intValue());
            RowDimValue rowDimValue = this.rowDim.get(id);
            row.add(rowDimValue.getMdCode());
            row.addAll(rowDimValue.getDim());
            row.add(record.getInt(orderIndex.intValue()));
            boolean locate = srcCursor.locate(row);
            if (locate) {
                IRecord srcRecord = srcCursor.getRecord();
                sbId = srcRecord.getString(srcSbIdIndex.intValue());
                this.collectColData(srcRowData, srcIndex2Type, srcRecord);
                this.collectColData(rowData, index2Type, record);
                if (!this.compareData(srcRowData, rowData, timePointFields)) {
                    int opt;
                    if (this.config.getDestPeriod().equals(srcRecord.getString(validTimeIndex.intValue()))) {
                        opt = 3;
                        ++tqUpdate;
                    } else if (noVersionIndex.size() == index2Type.size()) {
                        opt = 3;
                        ++noVerUpdate;
                    } else if (noVersionIndex.isEmpty()) {
                        opt = 2;
                        ++verUpdate;
                    } else {
                        for (Integer index : noVersionIndex) {
                            rowData[index.intValue()] = null;
                        }
                        for (Integer versionIndex : srcNoVersionIndex) {
                            srcRowData[versionIndex.intValue()] = null;
                        }
                        if (Arrays.equals(srcRowData, rowData)) {
                            opt = 3;
                            ++eqUpdate0;
                        } else {
                            opt = 2;
                            ++eqUpdate;
                        }
                    }
                    if (opt == 2) {
                        ++recordUpdateCount;
                    } else {
                        ++noRecordUpdateCount;
                    }
                    record.setInt(n.intValue(), opt);
                    if (notice) {
                        rowDimValue.setOpt(opt);
                        rowDimValue.setValues(new ArrayList<Object>());
                        rowDimValue.setOldValues(new ArrayList<Object>());
                        rowDimValue.addOldValues(srcRowData);
                        rowDimValue.addValues(rowData);
                    }
                } else {
                    ++noneCount;
                }
                srcRecord.setInt(srcOptIndex.intValue(), 2);
                srcCursor.update();
                if (containPeriodField && !allMode && srcRptCursor != null) {
                    row.remove(row.size() - 1);
                    for (DataField ignored : this.tmpTable.getTableDimFields()) {
                        row.remove(row.size() - 1);
                    }
                    row.add(sbId);
                    boolean rptLocate = srcRptCursor.locate(row);
                    if (rptLocate) {
                        IRecord srcRptRecord = srcRptCursor.getRecord();
                        this.collectColData(srcRptRowData, srcRptIndex2Type, srcRptRecord);
                        this.collectColData2(rptRowData, this.rptColIndexType, record);
                        if (!Arrays.equals(srcRptRowData, rptRowData)) {
                            record.setInt(rptOptIndex.intValue(), 2);
                            record.setString(rptBizKeyOrderIndex.intValue(), srcRptRecord.getString(srcRptBizKeyOrderIndex.intValue()));
                            ++rptUpdate;
                        }
                    } else {
                        record.setInt(rptOptIndex.intValue(), 4);
                        ++rptAddCount;
                    }
                }
            } else {
                sbId = UUIDUtils.getKey();
                record.setInt(n.intValue(), 4);
                record.setInt(rptOptIndex.intValue(), 4);
                ++addCount;
                if (!allMode) {
                    ++rptAddCount;
                }
            }
            if (allMode) {
                ++rptAddCount;
                record.setInt(rptOptIndex.intValue(), 4);
            }
            record.setString(sbIdIndex.intValue(), sbId);
            cursor.update();
            rowDimValue.setSbId(sbId);
        }
        cursor.close();
        srcCursor.close();
        if (srcRptCursor != null) {
            srcRptCursor.close();
        }
        if (allMode) {
            flagState.setDel(this.delSbId2Tmp());
        } else {
            flagState.setDel(0);
        }
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u540c\u671f\u4fee\u6539\u4e0d\u8bb0\u5f55\u53d8\u66f4", (Object)tqUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u6ca1\u6709\u8981\u751f\u6210\u7248\u672c\u7684\u5b57\u6bb5\u4e0d\u8bb0\u5f55\u53d8\u66f4", (Object)noVerUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u5168\u662f\u8981\u751f\u6210\u7248\u672c\u7684\u8bb0\u5f55\u53d8\u66f4", (Object)verUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u91cd\u8981\u5b57\u6bb5\u6570\u636e\u53d1\u751f\u53d8\u5316\u7684\u8bb0\u5f55\u53d8\u66f4", (Object)eqUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u91cd\u8981\u5b57\u6bb5\u6570\u636e\u672a\u53d1\u751f\u53d8\u5316\u7684\u4e0d\u8bb0\u5f55\u53d8\u66f4", (Object)eqUpdate0);
        if (containPeriodField) {
            if (allMode) {
                flagState.setRptAdd(rptAddCount);
                flagState.setRptDel(-1);
                flagState.setRptNone(0);
                flagState.setRptUpdate(0);
                this.logger.info("\u65e0\u9700\u6bd4\u8f83\u53f0\u8d26\u62a5\u8868\u6570\u636e\u5168\u91cf\u5220\u9664\u518d\u65b0\u589e");
            } else {
                this.logger.info("\u6bd4\u8f83\u53f0\u8d26\u62a5\u8868\u6570\u636e {} \u6761\u6570\u636e\u9700\u8981\u65b0\u589e", (Object)rptAddCount);
                this.logger.info("\u6bd4\u8f83\u53f0\u8d26\u62a5\u8868\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316", (Object)rptUpdate);
                flagState.setRptAdd(rptAddCount);
                flagState.setRptDel(0);
                flagState.setRptNone(0);
                flagState.setRptUpdate(rptUpdate);
            }
        }
        flagState.setAdd(addCount);
        flagState.setRecordUpdate(recordUpdateCount);
        flagState.setNoRecordUpdate(noRecordUpdateCount);
        flagState.setNone(noneCount);
        return flagState;
    }

    private void delRtpData(Connection connection) {
        String sql = "DELETE FROM " + this.tmpTable.getTzTableName() + "_RPT" + " WHERE " + "MDCODE" + " IN ( SELECT " + this.mdCodeColumnName + " FROM " + this.mdCodeTable.getTableName() + ") AND " + "DATATIME" + " = ?";
        this.logger.info("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e,\u6267\u884cSQL:{}", (Object)sql);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1, this.config.getDestPeriod());
            statement.execute();
        }
        catch (SQLException e) {
            this.logger.error(sql, e);
            throw new TzImportException("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25");
        }
    }

    private void insertRtpData(List<String> col, List<Object[]> args, Connection connection) {
        this.logger.info("insertRtpData");
        String rptTable = this.tmpTable.getTzTableName() + "_RPT";
        StringBuilder valueSql = new StringBuilder();
        for (int i = 1; i < col.size(); ++i) {
            valueSql.append("?,");
        }
        String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES({2})", rptTable, String.join((CharSequence)",", col), valueSql.append("?"));
        try {
            this.logger.debug(sql);
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, args);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("sql: " + sql + "\nvalues: \n");
            for (Object[] updateValue : args) {
                msg.append(Arrays.toString(updateValue));
                msg.append("\n");
            }
            msg.append("operation:").append("insertRtpData").append("\n");
            msg.append(e.getMessage());
            this.logger.info(msg.toString());
            throw new TzImportException("\u53f0\u8d26\u62a5\u8868\u65b0\u589e\u6570\u636e\u9519\u8bef", e);
        }
    }

    private void updateRtpData(List<String> col, List<Object[]> updateValues, Connection connection) {
        this.logger.info("updateRtpData");
        String rptTable = this.tmpTable.getTzTableName() + "_RPT";
        String sql = MessageFormat.format("UPDATE {0} SET {1} =? WHERE {2} =?", rptTable, String.join((CharSequence)"=?,", col), "BIZKEYORDER");
        try {
            this.logger.debug(sql);
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, updateValues);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("sql: " + sql + "\nvalues: \n");
            for (Object[] updateValue : updateValues) {
                msg.append(Arrays.toString(updateValue));
                msg.append("\n");
            }
            msg.append("operation:").append("updateRtpData").append("\n");
            msg.append(e.getMessage());
            this.logger.info(msg.toString());
            throw new TzImportException("\u53f0\u8d26\u62a5\u8868\u66f4\u65b0\u6570\u636e\u9519\u8bef", e);
        }
    }

    protected void collectColData(Object[] row, Map<Integer, Integer> colIndexType, IRecord record) throws BufDBException {
        int index = 0;
        block7: for (Map.Entry<Integer, Integer> entry : colIndexType.entrySet()) {
            if (entry.getValue().intValue() == DataFieldType.PICTURE.getValue() || entry.getValue().intValue() == DataFieldType.FILE.getValue()) {
                row[index++] = record.getString(entry.getKey().intValue());
                continue;
            }
            switch (entry.getValue()) {
                case 3: {
                    row[index++] = record.getDouble(entry.getKey().intValue());
                    continue block7;
                }
                case 10: {
                    row[index++] = record.getBigDecimal(entry.getKey().intValue());
                    continue block7;
                }
                case 6: 
                case 12: {
                    String asString = record.getString(entry.getKey().intValue());
                    if (!StringUtils.hasLength(asString)) {
                        asString = null;
                    }
                    row[index++] = asString;
                    continue block7;
                }
                case 2: {
                    row[index++] = record.getDate(entry.getKey().intValue());
                    continue block7;
                }
                case 1: 
                case 5: {
                    row[index++] = record.getInt(entry.getKey().intValue());
                    continue block7;
                }
            }
            throw new TzImportException("\u6682\u4e0d\u652f\u6301\u8be5\u7c7b\u578b");
        }
    }

    protected void collectColData2(Object[] row, Map<Integer, DFAndTypeInfo> colIndexType, IRecord record) throws BufDBException {
        int index = 0;
        try {
            block9: for (Map.Entry<Integer, DFAndTypeInfo> entry : colIndexType.entrySet()) {
                if (entry.getValue().getDataFieldType() == DataFieldType.PICTURE.getValue() || entry.getValue().getDataFieldType() == DataFieldType.FILE.getValue()) {
                    row[index++] = record.getString(entry.getKey().intValue());
                    continue;
                }
                switch (entry.getValue().getDataFieldType()) {
                    case 3: {
                        row[index++] = record.getDouble(entry.getKey().intValue());
                        continue block9;
                    }
                    case 10: {
                        row[index++] = record.getBigDecimal(entry.getKey().intValue());
                        continue block9;
                    }
                    case 6: 
                    case 12: {
                        String asString = record.getString(entry.getKey().intValue());
                        if (!StringUtils.hasLength(asString)) {
                            asString = null;
                        } else if (entry.getValue().isEncrypted() && this.encryptor != null) {
                            asString = this.encryptor.encrypt(asString);
                        }
                        row[index++] = asString;
                        continue block9;
                    }
                    case 2: {
                        row[index++] = record.getDate(entry.getKey().intValue());
                        continue block9;
                    }
                    case 1: 
                    case 5: {
                        row[index++] = record.getInt(entry.getKey().intValue());
                        continue block9;
                    }
                }
                throw new TzImportException("\u6682\u4e0d\u652f\u6301\u8be5\u7c7b\u578b");
            }
        }
        catch (EncryptionException e) {
            this.logger.error("\u52a0\u5bc6\u6307\u6807\u6570\u636e\u5f02\u5e38", e);
            throw new TzImportException("\u52a0\u5bc6\u6307\u6807\u6570\u636e\u5f02\u5e38", e);
        }
    }

    private void delSbIdTmp(Connection connection) {
        this.delSbIdTmp(connection, this.sbIdTable.getTableName());
    }

    private void delSbIdTmp(Connection connection, String sbIdTable) {
        this.logger.info("\u6e05\u7406 sbId \u8f85\u52a9\u8868");
        this.logger.info("delSbIdTmp");
        String del = "DELETE FROM {0} ";
        String delSql = MessageFormat.format(del, sbIdTable);
        this.logger.debug(delSql);
        try (Statement statement = connection.createStatement();){
            statement.execute(delSql);
        }
        catch (SQLException e) {
            this.logger.error(delSql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u66f4\u65b0\u6570\u636e\u5931\u8d25");
        }
    }

    @Deprecated
    private void delSbData(Connection connection, String sbIdTable) {
        this.logger.info("\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u5220\u9664\u6570\u636e");
        this.logger.info("delSbData");
        String del = "DELETE FROM {0} T0 WHERE T0.{1} IN (SELECT T1.{2} FROM {3} T1)";
        String delSql = MessageFormat.format(del, this.tmpTable.getTzTableName(), this.sbIdColumnName, "SBID", sbIdTable);
        this.logger.info(delSql);
        try (PreparedStatement statement = connection.prepareStatement(delSql);){
            statement.execute();
            this.logger.info("\u5220\u9664\u5b8c\u6210");
        }
        catch (SQLException e) {
            this.logger.error(delSql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u66f4\u65b0\u6570\u636e\u5931\u8d25");
        }
    }

    private void delSbData(Connection connection, List<Object[]> batchSbIds) {
        this.logger.info("\u5c06\u9700\u8981\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4e3b\u8868\u5220\u9664");
        String del = "DELETE FROM {0} WHERE {1} = ?";
        String delSql = MessageFormat.format(del, this.tmpTable.getTzTableName(), "SBID");
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(delSql);
        }
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)delSql, batchSbIds);
            this.logger.info("\u5c06\u9700\u8981\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4e3b\u8868\u5220\u9664\u5b8c\u6210");
        }
        catch (SQLException e) {
            this.logger.error(delSql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u5220\u9664\u6570\u636e\u5931\u8d25");
        }
    }

    @Deprecated
    private void sbData2His(Connection connection) {
        this.sbData2His(connection, this.sbIdTable.getTableName());
    }

    @Deprecated
    private void sbData2His(Connection connection, String sbIdTable) {
        this.logger.info("sbData2His");
        TzParams params = new TzParams();
        params.setStateTableName(sbIdTable);
        params.setTmpTable(this.tmpTable);
        params.setDatatime(this.config.getDestPeriod());
        TzData2HisModel tzData2HisModel = new TzData2HisModel(this.jdbcTemplate, params, -1);
        tzData2HisModel.validDataTimeFilter();
        String sql = tzData2HisModel.buildSql();
        this.logger.info(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1, this.config.getDestPeriod());
            statement.execute();
            this.logger.info("\u590d\u5236\u6570\u636e\u5230\u4e34\u65f6\u8868\u5b8c\u6210");
        }
        catch (SQLException e) {
            this.logger.error(sql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u66f4\u65b0\u6570\u636e\u5931\u8d25");
        }
    }

    private void sbData2HisAsBatch(Connection connection, List<Object[]> batchSbIds) {
        this.logger.info("\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u590d\u5236\u6570\u636e\u5230\u5386\u53f2\u8868\u4e2d");
        TzParams params = new TzParams();
        params.setTmpTable(this.tmpTable);
        params.setDatatime(this.config.getDestPeriod());
        TzData2HisBySbIdModel tzData2HisModel = new TzData2HisBySbIdModel(this.jdbcTemplate, params);
        String sql = tzData2HisModel.buildSql();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(sql);
        }
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql, batchSbIds);
            this.logger.info("\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u590d\u5236\u6570\u636e\u5230\u5386\u53f2\u8868\u5b8c\u6210");
        }
        catch (SQLException e) {
            this.logger.error(sql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u66f4\u65b0\u6570\u636e\u5230\u5386\u53f2\u8868\u5931\u8d25");
        }
    }

    private void sbData2HisAsIn(Connection connection, List<Object[]> batchSbIds) {
        this.logger.info("\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u590d\u5236\u6570\u636e\u5230\u5386\u53f2\u8868\u4e2d");
        TzParams params = new TzParams();
        params.setTmpTable(this.tmpTable);
        params.setDatatime(this.config.getDestPeriod());
        TzData2HisBySbIdInModel tzData2HisModel = new TzData2HisBySbIdInModel(this.jdbcTemplate, params, batchSbIds.size());
        String sql = tzData2HisModel.buildSql();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(sql);
        }
        try {
            ArrayList<Object> sbids = new ArrayList<Object>();
            for (Object[] args : batchSbIds) {
                sbids.add(args[args.length - 1]);
            }
            DataEngineUtil.executeUpdate((Connection)connection, (String)sql, (Object[])sbids.toArray());
            this.logger.info("\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u590d\u5236\u6570\u636e\u5230\u5386\u53f2\u8868\u5b8c\u6210");
        }
        catch (SQLException e) {
            this.logger.error(sql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u66f4\u65b0\u6570\u636e\u5230\u5386\u53f2\u8868\u5931\u8d25");
        }
    }

    @Deprecated
    private void sbData2HisAndDel(Connection connection, Integer delCount) {
        this.logger.info("sbData2HisAndDel");
        int count = this.splitIntoBatches(delCount);
        IDatabase database = DatabaseInstance.getDatabase();
        for (int i = 0; i < count; ++i) {
            String copySql;
            String sql = "SELECT %s FROM %s";
            String orderSql = String.format(sql, this.sbIdColumnName, this.sbIdTable.getTableName());
            try {
                IPagingSQLBuilder pagingSQLBuilder = database.createPagingSQLBuilder();
                pagingSQLBuilder.setRawSQL(orderSql);
                OrderField orderField = new OrderField("SBID");
                pagingSQLBuilder.getOrderFields().add(orderField);
                String pageSql = pagingSQLBuilder.buildSQL(i * this.batchCount, (i + 1) * this.batchCount);
                copySql = "INSERT INTO " + this.sbIdTableTemp.getTableName() + " " + pageSql;
            }
            catch (Exception e) {
                this.logger.error("\u53f0\u8d26\u8868\u5220\u9664\u6570\u636e\u5931\u8d25", e);
                throw new TzImportException("\u53f0\u8d26\u8868\u5220\u9664\u6570\u636e\u5931\u8d25");
            }
            this.logger.info(copySql);
            try (PreparedStatement statement = connection.prepareStatement(copySql);){
                statement.execute();
            }
            catch (SQLException e) {
                this.logger.error(copySql, e);
                throw new TzImportException("\u53f0\u8d26\u8868\u5220\u9664\u6570\u636e\u5931\u8d25");
            }
            this.logger.info("\u5220\u9664\u7684\u6570\u636e\u63d2\u5165\u5230\u53f0\u8d26\u5386\u53f2\u8868");
            this.sbData2His(connection, this.sbIdTableTemp.getTableName());
            this.logger.info("\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u62a5\u8868\u5220\u9664");
            this.delSbData(connection, this.sbIdTableTemp.getTableName());
            this.delSbIdTmp(connection, this.sbIdTableTemp.getTableName());
        }
    }

    private void sbData2HisAndDelBatchMode(Connection connection, Integer delCount) {
        this.logger.info("sbData2HisAndDelBatchMode - \u6309\u6279\u6b21\u5220\u9664\u6570\u636e,\u603b\u6761\u6570: {}", (Object)delCount);
        int processCount = 0;
        String sql = String.format("SELECT %s FROM %s", this.sbIdColumnName, this.sbIdTable.getTableName());
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setFetchSize(this.batchCount);
            statement.setFetchDirection(1000);
            ResultSet sbIdRes = statement.executeQuery();
            ArrayList<Object[]> batchSbIds = new ArrayList<Object[]>();
            while (sbIdRes.next()) {
                String sbId = sbIdRes.getString(1);
                Object[] sbArr = new Object[]{sbId};
                batchSbIds.add(sbArr);
                if (batchSbIds.size() != this.batchCount) continue;
                this.logger.info("\u5206\u6279\u5904\u7406\u9700\u8981\u5220\u9664\u7684\u6570\u636e");
                this.sbData2HisAsIn(connection, batchSbIds);
                this.delSbData(connection, batchSbIds);
                this.logger.info("\u6309\u6279\u6b21\u5220\u9664\u6570\u636e\u4e2d..... {}/{} ", (Object)(processCount += this.batchCount), (Object)delCount);
                batchSbIds.clear();
            }
            if (!batchSbIds.isEmpty()) {
                this.logger.info("\u5206\u6279\u5904\u7406\u9700\u8981\u5220\u9664\u7684\u6570\u636e");
                this.sbData2HisAsIn(connection, batchSbIds);
                this.delSbData(connection, batchSbIds);
                this.logger.info("\u6309\u6279\u6b21\u5220\u9664\u6570\u636e\u4e2d..... {}/{} ", (Object)(processCount += batchSbIds.size()), (Object)delCount);
                batchSbIds.clear();
            }
        }
        catch (SQLException e) {
            this.logger.error(sql, e);
            throw new TzImportException("\u53f0\u8d26\u8868\u5220\u9664\u6570\u636e\u5931\u8d25");
        }
    }

    public int splitIntoBatches(int totalCount) {
        int batchSize;
        int count = 0;
        for (int remainingCount = totalCount; remainingCount > 0; remainingCount -= batchSize) {
            batchSize = Math.min(remainingCount, this.batchCount);
            ++count;
        }
        return count;
    }

    protected int delSbId2Tmp() throws BufDBException {
        boolean notice;
        this.logger.info("delSbId2Tmp");
        IIndexedCursor srcCursor = this.srcTable.openCursor();
        Integer optIndex = this.srcTableIndex.get("_OPT");
        Integer sbIdIndex = this.srcTableIndex.get("SBID");
        Integer mdIndex = this.srcTableIndex.get("MDCODE");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        LinkedHashMap<Integer, Integer> index2Type = null;
        LinkedHashMap<Integer, Integer> colIndexTypeMap = null;
        boolean bl = notice = !CollectionUtils.isEmpty(this.listeners);
        if (notice) {
            index2Type = new LinkedHashMap<Integer, Integer>();
            for (DataField timePointField : this.tmpTable.getTimePointFields()) {
                index2Type.put(this.srcTableIndex.get(timePointField.getCode()), timePointField.getDataFieldType().getValue());
            }
            colIndexTypeMap = new LinkedHashMap<Integer, Integer>();
            for (DataField dimField : this.tmpTable.getDimFields()) {
                colIndexTypeMap.put(this.srcTableIndex.get(dimField.getCode()), dimField.getDataFieldType().getValue());
            }
            for (DataField dimField : this.tmpTable.getTableDimFields()) {
                colIndexTypeMap.put(this.srcTableIndex.get(dimField.getCode()), dimField.getDataFieldType().getValue());
            }
        }
        int delCount = 0;
        while (srcCursor.next()) {
            IRecord record = srcCursor.getRecord();
            if (record.getInt(optIndex.intValue()) != 0) continue;
            String sbId = record.getString(sbIdIndex.intValue());
            args.add(new Object[]{sbId});
            ++delCount;
            if (args.size() == this.batchCount) {
                String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})", this.sbIdTable.getTableName(), this.sbIdColumnName, "?");
                this.logger.info("\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e SBID \u63d2\u5165\u5230\u8f85\u52a9\u5220\u9664\u8868,{} \u6761\u6570\u636e", (Object)args.size());
                this.logger.debug(sql);
                this.jdbcTemplate.batchUpdate(sql, args);
                args.clear();
            }
            if (!notice) continue;
            RowDimValue rowDimValue = new RowDimValue();
            this.delRowDim.add(rowDimValue);
            rowDimValue.setSbId(sbId);
            rowDimValue.setMdCode(record.getString(mdIndex.intValue()));
            if (!CollectionUtils.isEmpty(index2Type)) {
                Object[] values = new Object[index2Type.size()];
                this.collectColData(values, index2Type, record);
                rowDimValue.setOldValues(new ArrayList<Object>());
                rowDimValue.addOldValues(values);
            }
            if (CollectionUtils.isEmpty(colIndexTypeMap)) continue;
            Object[] dimValues = new Object[colIndexTypeMap.size()];
            this.collectColData(dimValues, colIndexTypeMap, record);
            rowDimValue.getDim().addAll(Arrays.asList(dimValues));
        }
        srcCursor.close();
        if (!args.isEmpty()) {
            String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})", this.sbIdTable.getTableName(), this.sbIdColumnName, "?");
            this.logger.debug("\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e SBID \u63d2\u5165\u5230\u8f85\u52a9\u5220\u9664\u8868,{} \u6761\u6570\u636e", (Object)args.size());
            this.logger.debug(sql);
            this.jdbcTemplate.batchUpdate(sql, args);
        }
        return delCount;
    }

    private List<String> getSbDataCol() {
        ArrayList<String> col = new ArrayList<String>();
        for (Map.Entry<Integer, DFAndTypeInfo> entry : this.colIndexType.entrySet()) {
            Integer index = entry.getKey();
            String name = this.table.getMetadata().getName(index.intValue());
            col.add(name);
        }
        return col;
    }

    private void initSrcIndexTypeMap() {
        Integer index;
        Integer dimIndex;
        if (this.colIndexType != null || this.rptColIndexType != null) {
            return;
        }
        this.colIndexType = new LinkedHashMap();
        this.rptColIndexType = new LinkedHashMap();
        Integer mdIndex = this.tableIndex.get("MDCODE");
        this.rptColIndexType.put(mdIndex, new DFAndTypeInfo(DataFieldType.STRING.getValue(), false));
        this.colIndexType.put(mdIndex, new DFAndTypeInfo(DataFieldType.STRING.getValue(), false));
        for (DataField dimField : this.tmpTable.getDimFields()) {
            dimIndex = this.tableIndex.get(dimField.getCode());
            this.rptColIndexType.put(dimIndex, new DFAndTypeInfo(dimField.getDataFieldType().getValue(), dimField.isEncrypted()));
            this.colIndexType.put(dimIndex, new DFAndTypeInfo(dimField.getDataFieldType().getValue(), dimField.isEncrypted()));
        }
        for (DataField tableDimField : this.tmpTable.getTableDimFields()) {
            dimIndex = this.tableIndex.get(tableDimField.getCode());
            this.colIndexType.put(dimIndex, new DFAndTypeInfo(tableDimField.getDataFieldType().getValue(), tableDimField.isEncrypted()));
        }
        for (DataField periodicField : this.tmpTable.getPeriodicFields()) {
            index = this.tableIndex.get(periodicField.getCode());
            this.rptColIndexType.put(index, new DFAndTypeInfo(periodicField.getDataFieldType().getValue(), periodicField.isEncrypted()));
        }
        for (DataField field : this.tmpTable.getTimePointFields()) {
            index = this.tableIndex.get(field.getCode());
            this.colIndexType.put(index, new DFAndTypeInfo(field.getDataFieldType().getValue(), field.isEncrypted()));
        }
        this.logger.debug("\u6784\u5efa\u53d6\u6570\u7d22\u5f15\u548c\u7c7b\u578b\u6620\u5c04,\u7d22\u5f15\u4f4d\u4e3a bufDb \u4e2d\u7684\u7d22\u5f15 {}", (Object)this.colIndexType);
        this.logger.debug("\u6784\u5efa\u53d6\u6570\u7d22\u5f15\u548c\u7c7b\u578b\u6620\u5c04,\u7d22\u5f15\u4f4d\u4e3a bufDb \u4e2d\u7684\u7d22\u5f15 {}", (Object)this.rptColIndexType);
    }

    private void dataRank(ITable srcTable, String ... orders) throws BufDBException {
        RankModel rankModel = new RankModel("ORDINAL");
        List partitions = rankModel.getPartitions();
        partitions.add("MDCODE");
        partitions.addAll(Stream.of(this.tmpTable.getDimDeploys(), this.tmpTable.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList()));
        for (String order : orders) {
            rankModel.getOrderBys().add(new OrderedField(order));
        }
        this.logger.info("rank");
        srcTable.rank(rankModel, "_ORDINAL");
    }

    private void filterDwByPeriod() {
        String select = MessageFormat.format("SELECT T0.{0} FROM {1} T0 INNER JOIN {2} T1 ON T0.{0} = T1.MDCODE AND T1.VALIDDATATIME > ? GROUP BY T0.{0}", this.mdCodeColumnName, this.mdCodeTable.getTableName(), this.tmpTable.getTzTableName());
        this.logger.debug(select);
        this.jdbcTemplate.query(select, rs -> {
            while (rs.next()) {
                String value = rs.getString(1);
                this.failDw.add(value);
            }
            return null;
        }, new Object[]{this.config.getDestPeriod()});
    }

    private void queryData() throws BufDBException {
        this.logger.info("queryData");
        this.srcTable = this.createTable(this.getSrcTable());
        ArrayList<String> col = new ArrayList<String>();
        for (int i = 0; i < this.srcTable.getMetadata().getCount() - 2; ++i) {
            String name = this.srcTable.getMetadata().getName(i);
            col.add("T0." + name);
        }
        String select = MessageFormat.format("SELECT {0} FROM {1} T0 INNER JOIN {2} T1 ON T0.MDCODE = T1.{3}", String.join((CharSequence)",", col), this.tmpTable.getTzTableName(), this.mdCodeTable.getTableName(), this.mdCodeColumnName);
        this.logger.debug(select);
        this.jdbcTemplate.query(select, rs -> {
            int timeIndex = this.srcTableIndex.get("VALIDDATATIME");
            int orgIndex = this.srcTableIndex.get("MDCODE");
            String period = this.config.getDestPeriod();
            try {
                int currQueryCount = 0;
                while (rs.next()) {
                    ++currQueryCount;
                    IRecord record = this.srcTable.createRecord();
                    String mdCode = null;
                    String vdt = null;
                    for (int i = 0; i < this.srcTable.getMetadata().getCount() - 2; ++i) {
                        Object val = this.readValue(i, i + 1, rs, false);
                        DFAndTypeInfo dfAndTypeInfo = this.srcTableIndexEncrypted.get(i);
                        if (null != val && null != dfAndTypeInfo && dfAndTypeInfo.getDataFieldType() == DataFieldType.STRING.getValue() && dfAndTypeInfo.isEncrypted() && this.decryptor != null) {
                            val = this.decryptor.decrypt(val.toString());
                        }
                        if (i == orgIndex && val != null) {
                            mdCode = val.toString();
                        }
                        if (i == timeIndex && val != null) {
                            vdt = val.toString();
                        }
                        record.setObject(i, val);
                    }
                    boolean contains = this.failDw.contains(mdCode);
                    if (contains) continue;
                    if (mdCode != null && vdt != null && vdt.compareTo(period) > 0) {
                        this.failDw.add(mdCode);
                        continue;
                    }
                    this.srcTable.put(record);
                }
                this.queryCount = currQueryCount;
                this.logger.info("\u67e5\u8be2\u5230\u6570\u636e\u5e93\u539f\u6570\u636e{}\u6761,", (Object)currQueryCount);
                return null;
            }
            catch (BufDBException | EncryptionException e) {
                throw new TzCreateTmpTableException("\u67e5\u8be2\u539f\u6570\u636e\u5931\u8d25", e);
            }
        });
    }

    private void queryRptData() throws BufDBException {
        this.logger.info("queryRptData");
        this.srcRptTable = this.createTable(this.getSrcRptTable());
        ArrayList<String> col = new ArrayList<String>();
        for (int i = 0; i < this.srcRptTable.getMetadata().getCount(); ++i) {
            String name = this.srcRptTable.getMetadata().getName(i);
            col.add("T0." + name);
        }
        String rptTable = this.tmpTable.getTzTableName() + "_RPT";
        String select = MessageFormat.format("SELECT {0} FROM {1} T0 INNER JOIN {2} T1 ON T0.MDCODE = T1.{3} AND T0.{4} = ?", String.join((CharSequence)",", col), rptTable, this.mdCodeTable.getTableName(), this.mdCodeColumnName, "DATATIME");
        this.logger.info(select);
        this.jdbcTemplate.query(select, rs -> {
            try {
                while (rs.next()) {
                    IRecord record = this.srcRptTable.createRecord();
                    for (int i = 0; i < this.srcRptTable.getMetadata().getCount(); ++i) {
                        Object val = this.readValue(i, i + 1, rs, true);
                        DFAndTypeInfo dfAndTypeInfo = this.srcRptTableIndexEncrypted.get(i);
                        if (null != val && null != dfAndTypeInfo && dfAndTypeInfo.getDataFieldType() == DataFieldType.STRING.getValue() && dfAndTypeInfo.isEncrypted() && this.decryptor != null) {
                            val = this.decryptor.decrypt(val.toString());
                        }
                        record.setObject(i, val);
                    }
                    this.srcRptTable.put(record);
                }
                return null;
            }
            catch (BufDBException | EncryptionException e) {
                throw new TzCreateTmpTableException("\u67e5\u8be2\u539f\u6570\u636e\u5931\u8d25", e);
            }
        }, new Object[]{this.config.getDestPeriod()});
    }

    private TableDefine getSrcTable() {
        TableDefine tableDefine = new TableDefine();
        tableDefine.setName(TzConstants.createName());
        List fields = tableDefine.getFields();
        FieldDefine mdCode = new FieldDefine("MDCODE", 6);
        mdCode.setPrecision(50);
        this.srcTableIndex.put("MDCODE", fields.size());
        this.srcTableIndexEncrypted.put(fields.size(), null);
        fields.add(mdCode);
        ArrayList<DataField> sbFields = new ArrayList<DataField>();
        sbFields.addAll(this.tmpTable.getDimFields());
        sbFields.addAll(this.tmpTable.getTableDimFields());
        sbFields.addAll(this.tmpTable.getTimePointFields());
        for (int i = 0; i < sbFields.size(); ++i) {
            this.srcTableIndex.put(((DataField)sbFields.get(i)).getCode(), i + fields.size());
            this.srcTableIndexEncrypted.put(i + fields.size(), new DFAndTypeInfo(((DataField)sbFields.get(i)).getDataFieldType().getValue(), ((DataField)sbFields.get(i)).isEncrypted()));
        }
        sbFields.stream().map(this::df2fd).forEach(fields::add);
        FieldDefine floatOrder = new FieldDefine("FLOATORDER", 3);
        floatOrder.setPrecision(20);
        floatOrder.setScale(6);
        this.srcTableIndex.put("FLOATORDER", fields.size());
        this.srcTableIndexEncrypted.put(fields.size(), null);
        fields.add(floatOrder);
        this.srcTableIndex.put("SBID", fields.size());
        this.srcTableIndexEncrypted.put(fields.size(), null);
        tableDefine.addField("SBID", 6).setPrecision(50);
        this.srcTableIndex.put("VALIDDATATIME", fields.size());
        this.srcTableIndexEncrypted.put(fields.size(), null);
        tableDefine.addField("VALIDDATATIME", 6).setPrecision(9);
        this.srcTableIndex.put("_ORDINAL", fields.size());
        this.srcTableIndexEncrypted.put(fields.size(), null);
        tableDefine.addField("_ORDINAL", 5).setPrecision(10);
        this.srcTableIndex.put("_OPT", fields.size());
        this.srcTableIndexEncrypted.put(fields.size(), null);
        tableDefine.addField("_OPT", 5).setPrecision(1);
        ArrayList<String> index = new ArrayList<String>();
        index.add("MDCODE");
        Stream.of(this.tmpTable.getDimDeploys(), this.tmpTable.getTableDimDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).distinct().forEach(index::add);
        index.add("_ORDINAL");
        tableDefine.addIndex(tableDefine.getName() + "_INDEX_ORDER", IndexType.NORMAL, index);
        return tableDefine;
    }

    private TableDefine getSrcRptTable() {
        TableDefine tableDefine = new TableDefine();
        tableDefine.setName(TzConstants.createName());
        List fields = tableDefine.getFields();
        FieldDefine mdCode = new FieldDefine("MDCODE", 6);
        this.srcRptTableIndex.put("MDCODE", fields.size());
        this.srcRptTableIndexEncrypted.put(fields.size(), null);
        mdCode.setPrecision(50);
        fields.add(mdCode);
        List<DataField> dimFields = this.tmpTable.getDimFields();
        for (DataField dimField : dimFields) {
            this.srcRptTableIndex.put(dimField.getCode(), fields.size());
            this.srcRptTableIndexEncrypted.put(fields.size(), new DFAndTypeInfo(dimField.getDataFieldType().getValue(), dimField.isEncrypted()));
            FieldDefine fieldDefine = this.df2fd(dimField);
            fields.add(fieldDefine);
        }
        FieldDefine sbId = new FieldDefine("SBID", 6);
        sbId.setPrecision(50);
        this.srcRptTableIndex.put("SBID", fields.size());
        this.srcRptTableIndexEncrypted.put(fields.size(), null);
        fields.add(sbId);
        FieldDefine id = new FieldDefine("BIZKEYORDER", 6);
        id.setPrecision(50);
        this.srcRptTableIndex.put("BIZKEYORDER", fields.size());
        this.srcRptTableIndexEncrypted.put(fields.size(), null);
        fields.add(id);
        List<DataField> sbFields = this.tmpTable.getPeriodicFields();
        for (DataField sbField : sbFields) {
            this.srcRptTableIndex.put(sbField.getCode(), fields.size());
            this.srcRptTableIndexEncrypted.put(fields.size(), new DFAndTypeInfo(sbField.getDataFieldType().getValue(), sbField.isEncrypted()));
            FieldDefine fieldDefine = this.df2fd(sbField);
            fields.add(fieldDefine);
        }
        ArrayList<String> index = new ArrayList<String>();
        index.add("MDCODE");
        Stream.of(this.tmpTable.getDimDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(index::add);
        index.add("SBID");
        tableDefine.addIndex(tableDefine.getName() + "_INDEX_ORDER", IndexType.UNIQUE, index);
        return tableDefine;
    }

    private boolean initMdCodeTable() {
        if (this.dwMap.isEmpty()) {
            this.close();
            this.status = (byte)7;
            return false;
        }
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        for (String dw : this.dwMap.keySet()) {
            list.add(new Object[]{dw});
        }
        String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES(?)", this.mdCodeTable.getTableName(), this.mdCodeColumnName);
        this.logger.debug(sql);
        try {
            this.logger.info("\u672c\u6b21\u5bfc\u5165\u6570\u636e\u8868{},\u521d\u59cb\u5316\u5355\u4f4d\u8303\u56f4\u8868 {} ", (Object)this.tmpTable.getTzTableName(), (Object)this.dwMap.keySet());
            this.jdbcTemplate.batchUpdate(sql, list);
            this.logger.info("\u672c\u6b21\u5bfc\u5165\u6570\u636e\u5355\u4f4d {} \u5bb6", (Object)list.size());
        }
        catch (DataAccessException e) {
            throw new TzCopyDataException("\u521d\u59cb\u5316\u5355\u4f4d\u8303\u56f4\u8868\u5931\u8d25", e);
        }
        return true;
    }

    @Override
    public void close() {
        if (this.status >= 5 || this.status < 2) {
            return;
        }
        this.status = (byte)5;
        if (this.table != null) {
            try {
                this.table.close();
                this.scheme.dropTable(this.table.getName());
            }
            catch (BufDBException e) {
                this.logger.info(e.getMessage(), e);
                this.status = (byte)6;
            }
        }
        if (this.srcTable != null) {
            try {
                this.srcTable.close();
                this.scheme.dropTable(this.srcTable.getName());
            }
            catch (BufDBException e) {
                this.logger.info(e.getMessage(), e);
                this.status = (byte)6;
            }
        }
        if (this.srcRptTable != null) {
            try {
                this.srcRptTable.close();
                this.scheme.dropTable(this.srcTable.getName());
            }
            catch (BufDBException e) {
                this.logger.info(e.getMessage(), e);
                this.status = (byte)6;
            }
        }
        if (this.scheme != null) {
            try {
                this.scheme.close();
            }
            catch (BufDBException e) {
                this.logger.info(e.getMessage(), e);
                this.status = (byte)6;
            }
        }
        try {
            try {
                if (this.sbIdTable != null) {
                    this.sbIdTable.close();
                }
            }
            catch (Exception e) {
                this.logger.error("\u9500\u6bc1sbIdTable\u4e34\u65f6\u8868\u51fa\u9519", e);
                this.status = (byte)6;
            }
            try {
                if (this.mdCodeTable != null) {
                    this.mdCodeTable.close();
                }
            }
            catch (Exception e) {
                this.logger.error("\u9500\u6bc1mdCodeTable\u4e34\u65f6\u8868\u51fa\u9519", e);
                this.status = (byte)6;
            }
            if (this.putCommit != null) {
                if (this.putCommit.isRunning()) {
                    this.putCommit.stop();
                }
                double totalTimeSeconds = this.putCommit.getTotalTimeSeconds();
                this.logger.debug("\u4ece\u51c6\u5907\u597d \u5230 commit {} \u79d2", (Object)totalTimeSeconds);
            }
            if (this.watch.isRunning()) {
                this.watch.stop();
            }
            for (StopWatch.TaskInfo taskInfo : this.watch.getTaskInfo()) {
                this.logger.debug("{} {} \u79d2", (Object)taskInfo.getTaskName(), (Object)String.format("%7.2f", taskInfo.getTimeSeconds()));
            }
            if (this.all.isRunning()) {
                this.all.stop();
                this.logger.info("\u63d0\u4ea4\u6570\u636e {} \u6761", (Object)this.currRowId);
                this.logger.info("\u4ece\u51c6\u5907\u5230\u7ed3\u675f {} \u79d2", (Object)String.format("%7.2f", this.all.getTotalTimeSeconds()));
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            this.status = (byte)6;
        }
    }

    public void parseFieldType(DataField field, FieldDefine define) {
        switch (field.getDataFieldType()) {
            case BIGDECIMAL: {
                if (field.getPrecision() != null) {
                    define.setPrecision(field.getPrecision().intValue());
                }
                if (field.getDecimal() == null) break;
                define.setScale(field.getDecimal().intValue());
                break;
            }
            case INTEGER: {
                if (field.getPrecision() == null) break;
                define.setPrecision(field.getPrecision().intValue());
                break;
            }
            case STRING: 
            case PICTURE: 
            case FILE: {
                define.setDataType(6);
                if (field.getPrecision() != null) {
                    define.setPrecision(field.getPrecision().intValue());
                    break;
                }
                define.setPrecision(255);
                break;
            }
            default: {
                if (field.getPrecision() != null) {
                    define.setPrecision(field.getPrecision().intValue());
                } else {
                    define.setPrecision(255);
                }
                define.setDataType(field.getDataFieldType().getValue());
            }
        }
    }

    protected void finalize() {
        try {
            this.close();
        }
        catch (Throwable e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private Object readValue(int destIndex, int srcIndex, ResultSet resultSet, boolean isRptValue) throws SQLException {
        int dataType = isRptValue ? this.srcRptTable.getMetadata().getType(destIndex) : this.srcTable.getMetadata().getType(destIndex);
        switch (dataType) {
            case 1: {
                boolean value = resultSet.getBoolean(srcIndex);
                return resultSet.wasNull() ? null : Boolean.valueOf(value);
            }
            case 2: {
                java.sql.Date value = resultSet.getDate(srcIndex);
                if (resultSet.wasNull()) {
                    return null;
                }
                Calendar date = Calendar.getInstance();
                date.setTime(value);
                return date;
            }
            case 3: {
                if (resultSet.wasNull()) {
                    return null;
                }
                return resultSet.getDouble(srcIndex);
            }
            case 5: 
            case 8: {
                long value = resultSet.getLong(srcIndex);
                return resultSet.wasNull() ? null : Long.valueOf(value);
            }
            case 6: {
                String value = resultSet.getString(srcIndex);
                return resultSet.wasNull() ? null : value;
            }
            case 9: {
                byte[] value = resultSet.getBytes(srcIndex);
                return resultSet.wasNull() ? null : value;
            }
            case 10: {
                BigDecimal value = resultSet.getBigDecimal(srcIndex);
                if (resultSet.wasNull()) {
                    return null;
                }
                int scale = isRptValue ? this.srcRptTable.getMetadata().getScale(destIndex) : this.srcTable.getMetadata().getScale(destIndex);
                value = value.setScale(scale, RoundingMode.HALF_UP);
                return value;
            }
        }
        throw new SQLException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + dataType);
    }
}

