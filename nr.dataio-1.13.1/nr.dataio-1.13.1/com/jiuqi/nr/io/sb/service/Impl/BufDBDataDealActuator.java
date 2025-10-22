/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.bufdb.BufDBException
 *  com.jiuqi.bi.bufdb.config.TableConfig
 *  com.jiuqi.bi.bufdb.db.IIndexedCursor
 *  com.jiuqi.bi.bufdb.db.IRecord
 *  com.jiuqi.bi.bufdb.db.ISchema
 *  com.jiuqi.bi.bufdb.db.ITable
 *  com.jiuqi.bi.bufdb.define.FieldDefine
 *  com.jiuqi.bi.bufdb.define.IndexDefine
 *  com.jiuqi.bi.bufdb.define.TableDefine
 *  com.jiuqi.bi.bufdb.model.OrderedField
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.fielddatacrud.ActuatorConfig
 *  com.jiuqi.nr.fielddatacrud.ISBActuator
 *  com.jiuqi.nr.fielddatacrud.ImportInfo
 *  com.jiuqi.nr.fielddatacrud.config.FieldDataProperties
 *  com.jiuqi.nr.fielddatacrud.impl.dto.ActuatorConfigDTO
 *  com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory
 */
package com.jiuqi.nr.io.sb.service.Impl;

import com.jiuqi.bi.bufdb.BufDBException;
import com.jiuqi.bi.bufdb.config.TableConfig;
import com.jiuqi.bi.bufdb.db.IIndexedCursor;
import com.jiuqi.bi.bufdb.db.IRecord;
import com.jiuqi.bi.bufdb.db.ISchema;
import com.jiuqi.bi.bufdb.db.ITable;
import com.jiuqi.bi.bufdb.define.FieldDefine;
import com.jiuqi.bi.bufdb.define.IndexDefine;
import com.jiuqi.bi.bufdb.define.TableDefine;
import com.jiuqi.bi.bufdb.model.OrderedField;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.fielddatacrud.ImportInfo;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.dto.ActuatorConfigDTO;
import com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory;
import com.jiuqi.nr.io.bufdb.IIOBufDBProvider;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.tz.TzConstants;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.tz.exception.TzCreateTmpTableException;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class BufDBDataDealActuator
implements ISBActuator {
    private final Logger logger = LoggerFactory.getLogger(BufDBDataDealActuator.class);
    private final ISBImportActuatorFactory factory;
    private final ActuatorConfig cfg;
    private final FieldDataProperties fieldDataProperties;
    private IIOBufDBProvider provider;
    private ISchema scheme;
    private ITable table;
    private List<DataField> fields;
    private final Map<String, Integer> tableIndex = new LinkedHashMap<String, Integer>();
    protected LinkedHashMap<Integer, Integer> colIndexType;
    private int mdCodeIndex = -1;
    private final Collection<String> mdScope = new TreeSet<String>();
    private final Set<String> currDws = new TreeSet<String>();
    private int orderIndex = 0;
    private int currentBatchSize;
    private int allBatchSize;
    private ISBActuator currActuator;
    private String lastDw;

    public BufDBDataDealActuator(FieldDataProperties fieldDataProperties, ISBImportActuatorFactory factory, ActuatorConfig cfg) {
        this.fieldDataProperties = fieldDataProperties;
        this.factory = factory;
        this.cfg = cfg;
    }

    public BufDBDataDealActuator setProvider(IIOBufDBProvider provider) {
        this.provider = provider;
        return this;
    }

    public int getActuatorType() {
        return SBImportActuatorType.BUF_DB.getValue();
    }

    public void setDataFields(List<DataField> fields) {
        this.logger.info("BufDBDataDealActuator SetDataFields");
        this.fields = fields;
        for (int i = 0; i < this.fields.size(); ++i) {
            DataField dataField = fields.get(i);
            if (dataField == null) continue;
            this.tableIndex.put(dataField.getCode(), i);
            if (!"MDCODE".equals(dataField.getCode())) continue;
            this.mdCodeIndex = i;
        }
    }

    public void setMdCodeScope(Collection<String> codes) {
        this.logger.info("BufDBDataDealActuator setMdCodeScope");
        this.mdScope.addAll(codes);
    }

    public void prepare() {
        this.logger.info("BufDBDataDealActuator Prepare");
        try {
            this.scheme = this.provider.getSchema();
            this.table = this.createTable(this.getInputTableDefine());
            this.initSrcIndexTypeMap();
        }
        catch (BufDBException e) {
            this.close();
            throw new TzCreateTmpTableException("\u521d\u59cb\u5316 bufDb \u5bfc\u5165\u73af\u5883\u5931\u8d25", e);
        }
        catch (TzCreateTmpTableException e) {
            this.close();
            throw new TzCreateTmpTableException("\u521d\u59cb\u5316\u5bfc\u5165\u73af\u5883\u5931\u8d25", e);
        }
        catch (Exception e) {
            this.close();
            throw new TzCreateTmpTableException("\u521d\u59cb\u5316\u5bfc\u5165\u73af\u5883\u9047\u5230\u672a\u77e5\u9519\u8bef", e);
        }
    }

    public void put(List<Object> dataRow) {
        if (this.fields.size() != dataRow.size()) {
            int count = this.fields.size() - dataRow.size();
            if (count > 0) {
                for (int n = 0; n < count; ++n) {
                    dataRow.add(null);
                }
            } else {
                throw new TzCopyDataException("\u6570\u636e\u5217\u4e2a\u6570\u8d85\u51fa\u6307\u6807\u6570\uff0c\u8bf7\u68c0\u67e5\u6570\u636e");
            }
        }
        if (this.mdCodeIndex < 0 || this.mdCodeIndex > dataRow.size() - 1) {
            throw new TzCopyDataException("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        Object dwCode = dataRow.get(this.mdCodeIndex);
        if (dwCode == null) {
            this.logger.error("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            throw new TzCopyDataException("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        Integer currOrder = ++this.orderIndex;
        dataRow.add(currOrder);
        try {
            this.table.put(dataRow);
        }
        catch (Exception e) {
            this.logger.error("\u7f13\u5b58\u5bfc\u5165\u6570\u636e\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef\uff01", e);
            throw new TzImportException("\u7f13\u5b58\u5bfc\u5165\u6570\u636e\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef!", e);
        }
    }

    public ImportInfo commitData() {
        this.logger.info("BufDBDataDealActuator commitData \u5171\u6536\u96c6\u5230\u6570\u636e{}\u6761", (Object)this.orderIndex);
        ArrayList<OrderedField> orderFields = new ArrayList<OrderedField>();
        orderFields.add(new OrderedField("MDCODE"));
        orderFields.add(new OrderedField("_ID"));
        ImportInfo infos = new ImportInfo();
        try {
            IIndexedCursor cursor = this.table.orderBy(orderFields);
            while (cursor.next()) {
                IRecord record = cursor.getRecord();
                String currDw = record.getString(this.mdCodeIndex);
                if (this.currActuator == null) {
                    this.currActuator = this.initCurrActuator();
                }
                if (this.currentBatchSize > this.fieldDataProperties.getRowMaxSize() && !currDw.equals(this.lastDw)) {
                    this.doCommit(infos);
                    this.currActuator = this.initCurrActuator();
                }
                Object[] dataRow = new Object[this.fields.size()];
                this.collectColData(dataRow, this.colIndexType, record);
                this.currActuator.put(new ArrayList<Object>(Arrays.asList(dataRow)));
                ++this.currentBatchSize;
                ++this.allBatchSize;
                this.lastDw = currDw;
                if (this.currDws.contains(currDw)) continue;
                this.currDws.add(currDw);
                if (this.mdScope.isEmpty()) continue;
                this.mdScope.remove(currDw);
            }
            if (this.currActuator != null) {
                this.doCommit(infos);
            }
            if (!this.mdScope.isEmpty()) {
                this.currActuator = this.initCurrActuator();
                this.currActuator.setMdCodeScope(this.mdScope);
                this.doCommit(infos);
            }
        }
        catch (BufDBException e) {
            this.close();
            this.logger.error("\u53f0\u8d26\u5206\u6279\u63d0\u4ea4\u6570\u636e\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef\uff01", e);
            throw new TzImportException("\u53f0\u8d26\u5206\u6279\u63d0\u4ea4\u6570\u636e\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef\uff01", e);
        }
        return infos;
    }

    public void close() {
        this.logger.info("BufDBDataDealActuator close");
        if (this.currActuator != null) {
            try {
                this.currActuator.close();
                this.currActuator = null;
            }
            catch (Exception e) {
                this.logger.error("\u5173\u95ed\u5206\u5355\u4f4d\u6392\u5e8fActuator\u5bf9\u8c61\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef\uff01", e);
            }
        }
        if (this.table != null) {
            try {
                this.table.close();
                this.scheme.dropTable(this.table.getName());
                this.table = null;
            }
            catch (BufDBException e) {
                this.logger.info("\u5173\u95ed\u5b58\u653e\u6240\u6709\u6570\u636etable\u5bf9\u8c61\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef", e);
            }
        }
        if (this.scheme != null) {
            try {
                this.scheme.close();
                this.scheme = null;
            }
            catch (BufDBException e) {
                this.logger.info("\u5173\u95ed\u5b58\u653e\u6240\u6709\u6570\u636escheme\u5bf9\u8c61\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef", e);
            }
        }
    }

    private TableDefine getInputTableDefine() {
        TableDefine tableDefine = new TableDefine();
        tableDefine.setName(TzConstants.createName());
        List tableDefineFields = tableDefine.getFields();
        this.fields.forEach(field -> tableDefineFields.add(this.df2fd((DataField)field)));
        this.tableIndex.put("_ID", tableDefineFields.size());
        tableDefine.addField("_ID", 5).setPrecision(50);
        tableDefine.getKeyFields().add("_ID");
        tableDefine.getIndexes().add(new IndexDefine("INDEX_MDCODE", new String[]{"MDCODE"}));
        return tableDefine;
    }

    private void initSrcIndexTypeMap() {
        if (this.colIndexType != null) {
            return;
        }
        this.colIndexType = new LinkedHashMap();
        for (DataField field : this.fields) {
            Integer dimIndex = this.tableIndex.get(field.getCode());
            this.colIndexType.put(dimIndex, field.getDataFieldType().getValue());
        }
    }

    private ITable createTable(TableDefine tableDefine) throws BufDBException {
        TableConfig config = new TableConfig();
        config.setReadOnly(false);
        config.setTemporary(true);
        return this.scheme.createTable(tableDefine, config);
    }

    private FieldDefine df2fd(DataField field) {
        DataFieldType dataFieldType = field.getDataFieldType();
        String fieldName = field.getCode();
        FieldDefine fieldDefine = new FieldDefine(fieldName, dataFieldType.getValue());
        this.parseFieldType(field, fieldDefine);
        return fieldDefine;
    }

    private void parseFieldType(DataField field, FieldDefine define) {
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

    private ISBActuator initCurrActuator() {
        ActuatorConfigDTO copy = ActuatorConfigDTO.copy((ActuatorConfig)this.cfg);
        copy.setBatchByUnit(true);
        copy.setRowByDw(true);
        ISBActuator actuator = this.factory.getActuator((ActuatorConfig)copy);
        actuator.setDataFields(this.fields);
        actuator.prepare();
        this.currentBatchSize = 0;
        return actuator;
    }

    private void doCommit(ImportInfo infos) {
        ImportInfo info;
        this.logger.info("\u53f0\u8d26\u6570\u636e\u5206\u6279\u5bfc\u5165\u5f00\u59cb\u63d0\u4ea4{} \u5bb6\u5355\u4f4d {}\u6761\u8bb0\u5f55 \u5df2\u7ecf\u7d2f\u8ba1\u63d0\u4ea4 {} \u6761\u8bb0\u5f55", this.currDws.size(), this.currentBatchSize, this.allBatchSize - this.currentBatchSize);
        try {
            info = this.currActuator.commitData();
        }
        finally {
            this.currActuator.close();
        }
        infos.addDimValues(info.getDimValues());
        this.logger.info("\u53f0\u8d26\u6570\u636e\u5206\u6279\u5bfc\u5165\u5b8c\u6210 \u672c\u6b21\u63d0\u4ea4{} \u5bb6\u5355\u4f4d {}\u6761\u8bb0\u5f55 \u5df2\u7ecf\u7d2f\u8ba1\u63d0\u4ea4 {} \u6761\u8bb0\u5f55", this.currDws.size(), this.currentBatchSize, this.allBatchSize);
    }

    private void collectColData(Object[] row, Map<Integer, Integer> colIndexType, IRecord record) throws BufDBException {
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
}

