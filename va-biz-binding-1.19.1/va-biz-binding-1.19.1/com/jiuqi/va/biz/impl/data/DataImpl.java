/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.common.exception.ToSqlException
 *  com.jiuqi.va.formula.tosql.ToSqlHandle
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataPluginType;
import com.jiuqi.va.biz.impl.data.DataPostEventProcessor;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.data.DataUpdateImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.model.PluginImpl;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.inc.impl.DataRecordImpl;
import com.jiuqi.va.biz.inc.intf.DataRecord;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataListener;
import com.jiuqi.va.biz.intf.data.DataPositionEvent;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.data.DataTransEvent;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.data.GlobalDataTransEvent;
import com.jiuqi.va.biz.intf.data.LazyLoadState;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.impl.ComputedPropImpl;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.ListUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.formula.common.exception.ToSqlException;
import com.jiuqi.va.formula.tosql.ToSqlHandle;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DataImpl
extends PluginImpl
implements Data {
    private static final Logger logger = LoggerFactory.getLogger(DataImpl.class);
    private DataState state = DataState.NONE;
    private DataTableNodeContainerImpl<DataTableImpl> tables;
    private Map<String, Integer> cursor = new HashMap<String, Integer>();
    private List<DataListener> listeners = new ArrayList<DataListener>();
    private ThreadLocal<Map<String, DataUpdate>> localData = new ThreadLocal();
    private DataRecord dataRecord;
    boolean listening = true;
    boolean canload = true;
    boolean canRecord = false;
    boolean recorded = false;
    private DataPluginType dataPluginType;
    boolean enableRule;
    boolean securityEnhanced;
    protected final List<DataPostEvent> dataPostEvents = new ArrayList<DataPostEvent>();
    private List<DataTransEvent> dataTransEvents = new ArrayList<DataTransEvent>();
    private List<GlobalDataTransEvent> globalDataTransEvents;
    private DataPostEventProcessor dataPostEventProcessor = new DataPostEventProcessor(this);
    private Map<UUID, Formula> conditionMap;
    private Map<String, Set<DataTableImpl>> referTriggerMap;

    public ThreadLocal<Map<String, DataUpdate>> localData() {
        return this.localData;
    }

    Map<UUID, Formula> getConditionMap() {
        if (this.conditionMap == null) {
            this.conditionMap = this.buildConditionMap();
        }
        return this.conditionMap;
    }

    public boolean hasRecorded() {
        return this.recorded;
    }

    private Map<UUID, Formula> buildConditionMap() {
        RulerImpl rulerImpl = this.getModel().getPlugins().get(RulerImpl.class);
        Map<UUID, Map<String, List<Formula>>> map = rulerImpl.getDefine().getObjectFormulaMap().get("table");
        HashMap<UUID, Formula> conditionMap = new HashMap<UUID, Formula>();
        if (map != null) {
            ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
                Map map2 = (Map)map.get(o.getId());
                if (map2 == null) {
                    return;
                }
                List list = (List)map2.get("condition");
                if (list == null || list.size() == 0) {
                    return;
                }
                conditionMap.put(o.getId(), (Formula)list.get(0));
            });
        }
        return conditionMap;
    }

    void setDataPluginType(DataPluginType dataPluginType) {
        this.dataPluginType = dataPluginType;
    }

    @Override
    public DataDefineImpl getDefine() {
        return (DataDefineImpl)super.getDefine();
    }

    @Override
    public DataState getState() {
        return this.state;
    }

    void setListening(boolean listening) {
        this.listening = listening;
    }

    @Override
    public boolean isEnableRule() {
        return this.enableRule;
    }

    @Override
    public void setEnableRule(boolean enableRule) {
        this.enableRule = enableRule;
    }

    @Override
    public DataTableImpl getMasterTable() {
        return (DataTableImpl)((DataTableNodeContainerImpl)this.getTables()).getMasterTable();
    }

    public DataTableNodeContainerImpl<DataTableImpl> getTables() {
        return this.tables;
    }

    void setTables(DataTableNodeContainerImpl<DataTableImpl> tables) {
        this.tables = tables;
    }

    private void doReset(DataState state) {
        this.tables.forEach((n, o) -> {
            o.reset();
            o.setStatus(LazyLoadState.UNLOAD);
            this.cursor.put(o.getName(), 0);
        });
        this.state = state;
    }

    @Override
    public void reset() {
        this.setListening(false);
        try {
            this.doReset(DataState.NONE);
        }
        finally {
            this.setListening(true);
        }
    }

    @Override
    public void create() {
        this.setListening(false);
        try {
            this.doReset(DataState.NEW);
        }
        finally {
            this.setListening(true);
        }
    }

    @Override
    public void delete() {
        if (this.state != DataState.BROWSE) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.currstatusunabledelete"));
        }
        this.dataPluginType.delete(this, Arrays.asList("ID"), this.dataPostEventProcessor);
        this.setListening(false);
        try {
            this.doReset(DataState.NONE);
        }
        finally {
            this.setListening(true);
        }
    }

    @Override
    public void deleteWithLock(Set<String> fieldNames) {
        if (this.state != DataState.BROWSE) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.currstatusunabledelete"));
        }
        this.dataPluginType.delete(this, fieldNames, this.dataPostEventProcessor);
        this.setListening(false);
        try {
            this.doReset(DataState.NONE);
        }
        finally {
            this.setListening(true);
        }
    }

    public List<Map<String, Object>> lazyLoadData(DataTableDefineImpl dataTableDefine, Map<String, Object> valueMap) {
        return this.dataPluginType.load(this, dataTableDefine, valueMap);
    }

    public List<Map<String, Object>> lazyLoadReferData(DataTableDefineImpl dataTableDefine, String condition) {
        return this.dataPluginType.load(dataTableDefine, condition);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void load(Map<String, Object> valueMap) {
        if (!this.canload) {
            throw new DataException("\u5220\u9664\u524d\u7981\u6b62\u52a0\u8f7d\u5355\u636e\u6570\u636e");
        }
        Map<String, List<Map<String, Object>>> data = this.dataPluginType.load(this, valueMap);
        this.getDefine().getTableList().forEach(o -> {
            List<Map<String, String>> sortFields = o.getSortFields();
            if (sortFields == null || sortFields.size() == 0) {
                return;
            }
            List rows = (List)data.get(o.getName());
            if (rows == null || rows.size() == 0) {
                return;
            }
            String[] sortnameArr = new String[sortFields.size()];
            Boolean[] typeArr = new Boolean[sortFields.size()];
            Boolean[] nullMaxArr = new Boolean[sortFields.size()];
            for (int i = 0; i < sortFields.size(); ++i) {
                sortnameArr[i] = sortFields.get(i).get("name");
                typeArr[i] = "ASC".equals(sortFields.get(i).get("sort"));
                nullMaxArr[i] = "true".equals(sortFields.get(i).get("nullMax"));
            }
            ListUtils.sort(rows, sortnameArr, typeArr, nullMaxArr);
        });
        this.setListening(false);
        try {
            this.doReset(DataState.BROWSE);
            this.setLazyTablesData(data);
            Model model = this.getModel();
            String triggerOrigin = model.getContext().getTriggerOrigin();
            if (StringUtils.hasText(triggerOrigin)) {
                this.initCalcFieldValue();
            }
        }
        finally {
            this.setListening(true);
        }
    }

    private void initCalcFieldValue() {
        Model model = this.getModel();
        RulerImpl rulerImpl = model.getPlugins().get(RulerImpl.class);
        Map<UUID, IExpression> map = rulerImpl.getDefine().getCalcFieldExpressionMap();
        ComputedPropImpl computedPropImpl = model.getPlugins().get(ComputedPropImpl.class);
        Map<UUID, IExpression> calcFieldExpressionMap = computedPropImpl.getDefine().getCalcFieldExpressionMap();
        boolean debugEnabled = logger.isDebugEnabled();
        this.tables.stream().forEach(table -> {
            if (table.getFields().stream().filter(field -> field.getDefine().getFieldType() == DataFieldType.CALC).count() == 0L) {
                return;
            }
            table.getRows().stream().forEach(row -> {
                Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(o -> table.getDefine().getName(), o -> o));
                FormulaUtils.adjustFormulaRows(this, rowMap);
                table.getFields().stream().filter(o -> o.getDefine().getFieldType() == DataFieldType.CALC).forEach(field -> {
                    IExpression expression = field.getName().startsWith("CALC_$CMP_") ? (IExpression)calcFieldExpressionMap.get(field.getDefine().getId()) : (IExpression)map.get(field.getDefine().getId());
                    try {
                        long t = System.currentTimeMillis();
                        FormulaUtils.execute(model, expression, rowMap);
                        if (debugEnabled) {
                            logger.debug("\u6267\u884c\u89c4\u5219={}ms:{}:{}", System.currentTimeMillis() - t, field.getName(), expression);
                        }
                    }
                    catch (SyntaxException e) {
                        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.executeformulaerror", new Object[]{expression.getToken().text()}) + e.getMessage(), e);
                    }
                });
            });
        });
    }

    @Override
    public void edit() {
        if (this.state != DataState.BROWSE) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.currstatusunableedit"));
        }
        this.state = DataState.EDIT;
    }

    public void resetOrdinal() {
        ModelContextImpl context = (ModelContextImpl)this.getModel().getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter == null) {
            this.tables.forEach((n, t) -> {
                if (LazyLoadState.UNLOAD.equals((Object)t.getStatus())) {
                    return;
                }
                if (t.getTableType() != DataTableType.DATA) {
                    return;
                }
                DataFieldImpl field = t.F_ORDINAL;
                if (field == null) {
                    return;
                }
                this.setRowOrdinal(field, (DataTableImpl)t);
            });
        } else {
            this.tables.forEach((n, t) -> {
                if (LazyLoadState.UNLOAD.equals((Object)t.getStatus())) {
                    return;
                }
                if (t.getTableType() != DataTableType.DATA) {
                    return;
                }
                DataFieldImpl field = t.F_ORDINAL;
                if (field == null) {
                    return;
                }
                if (((Map)detailEnableFilter).containsKey(t.getName())) {
                    this.setFilterRowOrdinal(field, (DataTableImpl)t, context);
                } else {
                    this.setRowOrdinal(field, (DataTableImpl)t);
                }
            });
        }
    }

    private void setFilterRowOrdinal(DataFieldImpl field, DataTableImpl t, ModelContextImpl context) {
        DataRowImpl dataRow;
        int i;
        ListContainer<DataRowImpl> rows = t.getRows();
        int ordinalIndex = field.getIndex();
        Map detailFilterDataId = (Map)context.getContextValue("X--detailFilterDataId");
        Map allIdList = (Map)detailFilterDataId.get(t.getName());
        if (CollectionUtils.isEmpty(allIdList)) {
            return;
        }
        int addSize = 0;
        for (String s : allIdList.keySet()) {
            if ((Integer)allIdList.get(s) == 2) continue;
            ++addSize;
        }
        List oldRows = rows.stream().filter(row -> row.getState() != DataRowState.APPENDED).sorted(Comparator.comparingDouble(row -> row.getDouble(ordinalIndex))).collect(Collectors.toList());
        ArrayList<DataRowImpl> newAppendRows = new ArrayList<DataRowImpl>();
        for (i = 0; i < addSize; ++i) {
            dataRow = rows.get(i);
            if (dataRow.getState() == DataRowState.APPENDED) {
                newAppendRows.add(dataRow);
                continue;
            }
            if (CollectionUtils.isEmpty(newAppendRows)) continue;
            for (int i1 = 0; i1 < oldRows.size(); ++i1) {
                if (!((DataRowImpl)oldRows.get(i1)).getId().equals(dataRow.getId())) continue;
                oldRows.addAll(i1, newAppendRows);
                break;
            }
            newAppendRows.clear();
        }
        if (!CollectionUtils.isEmpty(newAppendRows)) {
            oldRows.addAll(newAppendRows);
        }
        for (i = 0; i < oldRows.size(); ++i) {
            dataRow = (DataRowImpl)oldRows.get(i);
            dataRow.setValue(ordinalIndex, (Object)(i + 1));
        }
    }

    private void setRowOrdinal(DataFieldImpl field, DataTableImpl t) {
        int i2;
        int i3;
        int ordinalIndex = field.getIndex();
        ListContainer<DataRowImpl> rows = t.getRows();
        if (rows == null || rows.size() == 0) {
            return;
        }
        int rowsSize = rows.size();
        int beforeNewRowsIndex = 0;
        if (rows.get(0).getRawValue(ordinalIndex) == null) {
            BigDecimal decimal = null;
            i3 = 0;
            int j = rowsSize;
            while (i3 < j) {
                DataRowImpl dataRow = rows.get(i3);
                if (dataRow.getRawValue(ordinalIndex) != null) {
                    decimal = dataRow.getBigDecimal(ordinalIndex);
                    break;
                }
                beforeNewRowsIndex = i3++;
            }
            if (decimal == null) {
                rows.forEach((i, r) -> r.setValue(ordinalIndex, i));
                return;
            }
            int intValue = decimal.intValue();
            int j2 = 1;
            for (i2 = beforeNewRowsIndex; i2 >= 0; --i2) {
                rows.get(i2).setValue(ordinalIndex, (Object)(intValue - j2));
                ++j2;
            }
        }
        int newBeforeIndex = -1;
        for (i3 = beforeNewRowsIndex; i3 < rowsSize; ++i3) {
            DataRowImpl dataRow = rows.get(i3);
            if (dataRow.getRawValue(ordinalIndex) == null) {
                if (newBeforeIndex != -1) continue;
                newBeforeIndex = i3;
                continue;
            }
            if (newBeforeIndex == -1) continue;
            i3 = this.setMidRowOrdinal(rows, ordinalIndex, newBeforeIndex, i3);
            newBeforeIndex = -1;
            if (i3 != 0) continue;
            return;
        }
        if (newBeforeIndex != -1) {
            int beforeOrdinal = rows.get(newBeforeIndex - 1).getBigDecimal(ordinalIndex).intValue();
            for (i2 = newBeforeIndex; i2 < rowsSize; ++i2) {
                rows.get(i2).setValue(ordinalIndex, (Object)(++beforeOrdinal));
            }
        }
    }

    private int setMidRowOrdinal(ListContainer<DataRowImpl> rows, int ordinalIndex, Integer newBefore, int curExecuteIndex) {
        int newRowsLength = curExecuteIndex - newBefore;
        BigDecimal beforeOrdinal = rows.get(newBefore - 1).getBigDecimal(ordinalIndex);
        BigDecimal afterOrdinal = rows.get(curExecuteIndex).getBigDecimal(ordinalIndex);
        BigDecimal subtract = afterOrdinal.subtract(beforeOrdinal);
        BigDecimal average = subtract.divide(new BigDecimal(newRowsLength + 1), 6, 1);
        if (average.compareTo(BigDecimal.ZERO) == 0) {
            for (int i = 1; i < rows.size(); ++i) {
                BigDecimal newAfterOrdinal;
                if (curExecuteIndex + i < rows.size()) {
                    newAfterOrdinal = rows.get(curExecuteIndex + i).getBigDecimal(ordinalIndex);
                    if (newAfterOrdinal == null) {
                        continue;
                    }
                } else {
                    int beforeInt = beforeOrdinal.intValue();
                    for (int j = newBefore.intValue(); j < rows.size(); ++j) {
                        rows.get(j).setValue(ordinalIndex, (Object)(++beforeInt));
                    }
                    return 0;
                }
                BigDecimal divideAfter = newAfterOrdinal.subtract(beforeOrdinal).divide(new BigDecimal(newRowsLength + i + 1), 6, 1);
                if (divideAfter.compareTo(BigDecimal.ZERO) == 0) {
                    if (newBefore - 1 - i < 0) {
                        int afterInt = afterOrdinal.intValue();
                        for (int j = curExecuteIndex - 1; j > -1; --j) {
                            rows.get(j).setValue(ordinalIndex, (Object)(--afterInt));
                        }
                        return curExecuteIndex;
                    }
                    BigDecimal newBeforeOrdinal = rows.get(newBefore - 1 - i).getBigDecimal(ordinalIndex);
                    BigDecimal divideBefore = afterOrdinal.subtract(newBeforeOrdinal).divide(new BigDecimal(newRowsLength + i + 1), 6, 1);
                    if (divideBefore.compareTo(BigDecimal.ZERO) == 0) continue;
                    BigDecimal cre = rows.get(newBefore - 1 - i).getBigDecimal(ordinalIndex).add(divideBefore);
                    for (int i1 = newBefore - i; i1 < curExecuteIndex; ++i1) {
                        rows.get(i1).setValue(ordinalIndex, (Object)cre);
                        cre = cre.add(divideBefore);
                    }
                    return curExecuteIndex;
                }
                BigDecimal cre = beforeOrdinal.add(divideAfter);
                for (int i1 = newBefore.intValue(); i1 < curExecuteIndex + i; ++i1) {
                    rows.get(i1).setValue(ordinalIndex, (Object)cre);
                    cre = cre.add(divideAfter);
                }
                return curExecuteIndex + i;
            }
        } else {
            BigDecimal cre = beforeOrdinal.add(average);
            for (int i = newBefore.intValue(); i < curExecuteIndex; ++i) {
                rows.get(i).setValue(ordinalIndex, (Object)cre);
                cre = cre.add(average);
            }
            return curExecuteIndex;
        }
        return 0;
    }

    public void resetIncOrdinal() {
        this.tables.forEach((n, t) -> {
            DataFieldImpl field = t.F_ORDINAL;
            if (field == null) {
                return;
            }
            this.setRowOrdinal(field, (DataTableImpl)t);
        });
    }

    @Override
    public void save() {
        if (this.state != DataState.NEW && this.state != DataState.EDIT) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.currstatusunablesave"));
        }
        this.tables.stream().forEach(o -> o.deleteUnsetRows());
        if (this.getState() == DataState.NEW) {
            ((ListContainerImpl)((Object)this.getTables())).forEach((k, v) -> v.setStatus(LazyLoadState.LOADED));
        }
        this.resetOrdinal();
        HashMap<String, DataUpdate> update = new HashMap<String, DataUpdate>();
        this.dataPluginType.save(this, Arrays.asList("ID"), this.dataPostEventProcessor, update);
        this.handleAfterCommit(update);
        this.applyChange();
    }

    private void handleAfterCommit(final Map<String, DataUpdate> update) {
        final DataImpl dataImpl = this;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCommit() {
                    for (Object dataTransEvent : DataImpl.this.dataTransEvents) {
                        dataTransEvent.onSaveAfterCommit(dataImpl, update);
                    }
                    if (DataImpl.this.globalDataTransEvents != null) {
                        for (Object dataTransEvent : DataImpl.this.globalDataTransEvents) {
                            dataTransEvent.onSaveAfterCommit(dataImpl.getModel(), dataImpl, update);
                        }
                    }
                }
            });
        } else {
            for (DataTransEvent dataTransEvent : this.dataTransEvents) {
                dataTransEvent.onSaveAfterCommit(dataImpl, update);
            }
            if (this.globalDataTransEvents != null) {
                for (GlobalDataTransEvent globalDataTransEvent : this.globalDataTransEvents) {
                    globalDataTransEvent.onSaveAfterCommit(dataImpl.getModel(), dataImpl, update);
                }
            }
        }
    }

    @Override
    public void saveWithLock(Set<String> fieldNames) {
        if (this.state != DataState.NEW && this.state != DataState.EDIT) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.currstatusunablesave"));
        }
        this.tables.stream().forEach(o -> o.deleteUnsetRows());
        if (this.getState() == DataState.NEW) {
            ((ListContainerImpl)((Object)this.getTables())).forEach((k, v) -> v.setStatus(LazyLoadState.LOADED));
        }
        this.resetOrdinal();
        HashMap<String, DataUpdate> update = new HashMap<String, DataUpdate>();
        this.dataPluginType.save(this, fieldNames, this.dataPostEventProcessor, update);
        this.handleAfterCommit(update);
        this.applyChange();
    }

    public Map<String, DataUpdate> getChangeData() {
        return this.localData.get();
    }

    @Override
    public Map<String, DataUpdate> getUpdate() {
        return this.tables.stream().collect(Collectors.toMap(o -> o.getDefine().getName(), o -> {
            DataUpdateImpl dataUpdate = new DataUpdateImpl();
            dataUpdate.setInsert(o.getInsertData());
            dataUpdate.setDelete(o.getDeleteData());
            dataUpdate.setUpdate(o.getUpdateData());
            return dataUpdate;
        }, (u, v) -> u, TreeMap::new));
    }

    public List<Map<String, Object>> getKeys(Collection<String> keyFields) {
        return this.getMasterTable().getRows().stream().map(o -> keyFields.stream().collect(Collectors.toMap(n -> n, n -> o.getOriginRow().getValue((String)n)))).collect(Collectors.toList());
    }

    @Override
    public void applyChange() {
        if (this.state == DataState.NEW || this.state == DataState.EDIT) {
            this.tables.stream().forEach(o -> o.applyChange());
            this.state = DataState.BROWSE;
        }
    }

    @Override
    public void cancelChange() {
        if (this.state == DataState.NEW || this.state == DataState.EDIT) {
            this.tables.stream().forEach(o -> o.cancelChange());
            this.state = DataState.BROWSE;
        }
    }

    @Override
    public void beginUpdate() {
    }

    @Override
    public void endUpdate() {
    }

    @Override
    public void startRecord() {
        this.canRecord = true;
        this.recorded = true;
        this.dataRecord = new DataRecordImpl();
    }

    @Override
    public DataRecord getRecordUpdate() {
        return this.dataRecord;
    }

    @Override
    public void stopRecord() {
        this.canRecord = false;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getTablesData() {
        return this.tables.stream().collect(Collectors.toMap(DataTableImpl::getName, DataTableImpl::getRowsData, (u, v) -> u, LinkedHashMap::new));
    }

    @Override
    public Map<String, List<Map<String, Object>>> getFilterTablesData() {
        return this.tables.stream().collect(Collectors.toMap(DataTableImpl::getName, DataTableImpl::getFilterRowsData, (u, v) -> u, LinkedHashMap::new));
    }

    @Override
    public Map<String, List<Map<String, Object>>> getTablesData(boolean viewData) {
        return this.tables.stream().collect(Collectors.toMap(DataTableImpl::getName, o -> o.getRowsData(viewData), (u, v) -> u, LinkedHashMap::new));
    }

    @Override
    public Map<String, List<List<Object>>> getFrontTablesData() {
        return this.tables.stream().collect(Collectors.toMap(DataTableImpl::getName, value -> this.buildFrontRowsData((DataTableImpl)value), (u, v) -> u, LinkedHashMap::new));
    }

    private List<List<Object>> buildFrontRowsData(DataTableImpl dataTable) {
        List fieldNames = dataTable.getFields().stream().map(DataFieldImpl::getName).collect(Collectors.toList());
        fieldNames.add("$UNSET");
        fieldNames.add("$STATE");
        List<List<Object>> fieldValues = dataTable.getRows().stream().map(o -> o.getFrontData()).collect(Collectors.toList());
        if (dataTable.getTableType() == DataTableType.REFER) {
            dataTable.getDeletedRows().forEach(o -> {
                ArrayList<Object> values = new ArrayList<Object>();
                for (int i = 0; i < o.values.length; ++i) {
                    values.add(o.getString(i));
                }
                values.add(o.unset);
                values.add(DataRowState.DELETED.name());
                fieldValues.add(values);
            });
        }
        fieldValues.add(0, fieldNames);
        return fieldValues;
    }

    @Override
    public void setTablesData(Map<String, List<Map<String, Object>>> tablesData) {
        ModelContextImpl context = (ModelContextImpl)this.getModel().getContext();
        Object contextValue = context.getContextValue("X--detailFilterDataId");
        LinkedList empty = new LinkedList();
        if (contextValue != null) {
            Map tableList = (Map)contextValue;
            ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
                if (o.getName().endsWith("_M")) {
                    return;
                }
                List list = tablesData.getOrDefault(o.getName(), empty);
                if (tableList.containsKey(o.getName())) {
                    o.mergeRowsData(list);
                    return;
                }
                if (list != empty) {
                    o.setRowsData(list);
                }
            });
            return;
        }
        ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
            if (o.getData().getState().equals(DataState.NEW) && o.getTableType() == DataTableType.DATA) {
                o.setStatus(LazyLoadState.LOADED);
            }
            if (!o.getName().endsWith("_M")) {
                return;
            }
            List list = tablesData.getOrDefault(o.getName(), empty);
            if (list != empty) {
                o.setRowsData(list);
            }
        });
        ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
            if (o.getName().endsWith("_M")) {
                return;
            }
            List list = tablesData.getOrDefault(o.getName(), empty);
            if (list != empty) {
                o.setRowsData(list);
            }
        });
    }

    public void handleInserData(Map<String, List<Map<Integer, Object>>> insertData) {
        for (Map.Entry<String, List<Map<Integer, Object>>> entry : insertData.entrySet()) {
            DataTableImpl dataTable = (DataTableImpl)((DataTableNodeContainerImpl)this.getTables()).find(entry.getKey());
            for (Map<Integer, Object> rowdata : entry.getValue()) {
                dataTable.appendIncRow(rowdata);
            }
        }
    }

    public void handleDeleteData(Map<String, List<Object>> deleteData) {
        for (Map.Entry<String, List<Object>> entry : deleteData.entrySet()) {
            DataTableImpl dataTable = (DataTableImpl)((DataTableNodeContainerImpl)this.getTables()).find(entry.getKey());
            for (Object id : entry.getValue()) {
                dataTable.deleteRowById(Convert.cast(id, UUID.class));
            }
        }
    }

    public void handleUpdateData(Map<String, Map<Integer, Map<Integer, Object>>> incData) {
        ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
            String name = o.getName();
            Map objects = (Map)incData.get(name);
            if (objects == null || objects.isEmpty()) {
                return;
            }
            o.getRows().forEach((i, row) -> {
                Map fieldValeus = (Map)objects.get(i.toString());
                if (fieldValeus == null || fieldValeus.isEmpty()) {
                    return;
                }
                for (Map.Entry entry : fieldValeus.entrySet()) {
                    row.setValue(Convert.cast(entry.getKey(), Integer.class), entry.getValue());
                }
                row.unset = false;
            });
        });
    }

    private void setLazyTablesData(Map<String, List<Map<String, Object>>> tablesData) {
        List listM;
        List list;
        LinkedList empty = new LinkedList();
        DataTableImpl masterTable = (DataTableImpl)((DataTableNodeContainerImpl)this.getTables()).getMasterTable();
        DataTableImpl masterTableM = (DataTableImpl)((DataTableNodeContainerImpl)this.getTables()).find(masterTable.getName() + "_M");
        masterTable.setStatus(LazyLoadState.LOADED);
        if (masterTableM != null) {
            masterTableM.setStatus(LazyLoadState.LOADED);
        }
        if ((list = (List)tablesData.getOrDefault(masterTable.getName(), empty)) != empty) {
            masterTable.setMasterRowsData(list);
        }
        if (masterTableM != null && (listM = (List)tablesData.getOrDefault(masterTable.getName() + "_M", empty)) != empty) {
            masterTableM.setMasterRowsData(listM);
        }
    }

    @Override
    public void setIncTablesData(Map<String, List<List<Object>>> tablesData) {
        LinkedList empty = new LinkedList();
        ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
            String tableName = o.getName();
            if (!tableName.endsWith("_M")) {
                return;
            }
            this.setIncTableData(tablesData, empty, (DataTableImpl)o, tableName);
        });
        ((ListContainerImpl)((Object)this.getTables())).stream().forEach(o -> {
            String tableName = o.getName();
            if (tableName.endsWith("_M")) {
                return;
            }
            this.setIncTableData(tablesData, empty, (DataTableImpl)o, tableName);
        });
    }

    private void setIncTableData(Map<String, List<List<Object>>> tablesData, List<List<Object>> empty, DataTableImpl o, String tableName) {
        List<List<Object>> list = tablesData.getOrDefault(tableName, empty);
        if (list != empty) {
            if (list.size() == 1) {
                o.setRowsData(list.get(0), new ArrayList<List<Object>>());
            } else {
                o.setRowsData(list.get(0), list.subList(1, list.size()));
            }
        }
    }

    @Override
    public void setUpdate(Map<String, ? extends DataUpdate> update) {
        update.forEach((n, v) -> {
            DataTableImpl table = this.tables.get((String)n);
            v.getDelete().forEach(o -> table.deleteRowById(o.get("ID")));
            table.insertRows(v.getInsert());
            table.updateRows(v.getUpdate());
        });
    }

    @Override
    public void addListener(DataListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(DataListener listener) {
        this.listeners.remove(listener);
    }

    public void setSetupReferTrigger() {
        if (this.referTriggerMap != null) {
            return;
        }
        this.referTriggerMap = new HashMap<String, Set<DataTableImpl>>();
        Map<UUID, Formula> conditionMap = this.getConditionMap();
        for (Map.Entry<UUID, Formula> entry : conditionMap.entrySet()) {
            IExpression expression = (IExpression)entry.getValue().getCompiledExpression();
            expression.forEach(node -> {
                ModelNode modelNode;
                if (node instanceof ModelNode && Objects.equals((modelNode = (ModelNode)((Object)node)).getTableName(), this.getMasterTable().getDefine().getName())) {
                    Set<DataTableImpl> set = this.referTriggerMap.get(modelNode.getFieldName());
                    if (set == null) {
                        set = new HashSet<DataTableImpl>();
                        this.referTriggerMap.put(modelNode.getFieldName(), set);
                    }
                    set.add((DataTableImpl)((DataTableNodeContainerImpl)this.getTables()).get((UUID)entry.getKey()));
                }
            });
        }
    }

    void reload(DataTableImpl detailTable) {
        ModelDataContext context = new ModelDataContext(this.getModel().getDefine());
        context.put(this.getMasterTable().getName(), this.getMasterTable().getRows().get(0));
        Formula formula = this.getConditionMap().get(detailTable.getId());
        if (formula != null) {
            String condition;
            IDatabase dataBase = DatabaseManager.getInstance().findDatabaseByName(Utils.convertBiDbType(VaMapperConfig.getDbType()));
            SQLInfoDescr info = new SQLInfoDescr(dataBase, true);
            try {
                condition = ToSqlHandle.toSQL((IContext)context, (IASTNode)((IASTNode)formula.getCompiledExpression()), (Object)info);
            }
            catch (ToSqlException e) {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.loadsubtabledatafailed"), e);
            }
            detailTable.setRowsData(this.dataPluginType.load(detailTable.getDefine(), condition));
            detailTable.setStatus(LazyLoadState.LOADED);
            this.afterReload(detailTable);
        }
    }

    protected void afterSetValue(DataTable table, DataRow row, DataField field, Object value, Object oldValue) {
        Set<DataTableImpl> set;
        RulerImpl rulerImpl;
        if (!this.listening) {
            return;
        }
        if (this.canRecord) {
            if (((DataRowImpl)row).isCacheInit) {
                String name;
                Map<String, Map<UUID, Map<String, Object>>> update = this.dataRecord.getUpdate();
                Map uuidMapMap = update.computeIfAbsent(table.getName(), k -> new ConcurrentHashMap());
                Map integerObjectMap = uuidMapMap.computeIfAbsent((UUID)row.getId(), k -> new HashMap());
                if (integerObjectMap.containsKey(name = field.getName())) {
                    Object o = integerObjectMap.get(name);
                    if (o == null && value == null) {
                        integerObjectMap.remove(name);
                    } else if (Objects.equals(o, value)) {
                        integerObjectMap.remove(name);
                    }
                } else {
                    integerObjectMap.put(field.getName(), oldValue);
                }
            } else {
                Map<String, Set<UUID>> insert = this.dataRecord.getInsert();
                Set rowIds = insert.computeIfAbsent(table.getName(), k -> new HashSet());
                rowIds.add((UUID)row.getId());
            }
        }
        boolean debugEnabled = logger.isDebugEnabled();
        if (!DataFieldType.CALC.equals((Object)field.getDefine().getFieldType()) || field.getName().startsWith("CALC_$CMP")) {
            for (DataListener listener : this.listeners) {
                long t = System.currentTimeMillis();
                listener.afterSetValue(table, row, field);
                long diff = System.currentTimeMillis() - t;
                if (diff <= 5L || !debugEnabled) continue;
                logger.debug("afterSetValue={}ms:{}", (Object)diff, (Object)listener.getClass().getName());
            }
        }
        if (!(rulerImpl = this.getModel().getPlugins().get(RulerImpl.class)).getRulerExecutor().isEnable()) {
            return;
        }
        this.setSetupReferTrigger();
        if (this.referTriggerMap.size() > 0 && table == this.getMasterTable() && field != null && (set = this.referTriggerMap.get(field.getName())) != null) {
            for (DataTableImpl detailTable : set) {
                this.reload(detailTable);
            }
        }
    }

    protected void afterAddRow(DataTable table, DataRow row) {
        if (this.canRecord) {
            Map<String, Set<UUID>> insert = this.dataRecord.getInsert();
            Set uuids = insert.computeIfAbsent(table.getName(), k -> new HashSet());
            uuids.add((UUID)row.getId());
        }
        if (!this.listening) {
            return;
        }
        boolean debugEnabled = logger.isDebugEnabled();
        for (DataListener listener : this.listeners) {
            long t = System.currentTimeMillis();
            listener.afterAddRow(table, row);
            if (!debugEnabled) continue;
            logger.debug("afterAddRow={}ms:{}", (Object)(System.currentTimeMillis() - t), (Object)listener.getClass().getName());
        }
    }

    protected void afterDelRow(DataTable table, DataRow row) {
        if (!this.listening) {
            return;
        }
        if (this.canRecord) {
            Map<String, List<UUID>> delete = this.dataRecord.getDelete();
            List uuids = delete.computeIfAbsent(table.getName(), k -> new ArrayList());
            UUID id = (UUID)row.getId();
            uuids.add(id);
            if (((DataRowImpl)row).isCacheInit) {
                this.dataRecord.getUpdate().remove(id);
            } else {
                this.dataRecord.getInsert().remove(id);
            }
        }
        boolean debugEnabled = logger.isDebugEnabled();
        for (DataListener listener : this.listeners) {
            long t = System.currentTimeMillis();
            listener.afterDelRow(table, row);
            if (!debugEnabled) continue;
            logger.debug("afterDelRow={}ms:{}", (Object)(System.currentTimeMillis() - t), (Object)listener.getClass().getName());
        }
    }

    protected void afterReload(DataTable table) {
        if (!this.listening) {
            return;
        }
        boolean debugEnabled = logger.isDebugEnabled();
        for (DataListener listener : this.listeners) {
            long t = System.currentTimeMillis();
            listener.afterReload(table);
            if (!debugEnabled) continue;
            logger.debug("afterReload={}ms:{}", (Object)(System.currentTimeMillis() - t), (Object)listener.getClass().getName());
        }
    }

    @Deprecated
    public void registerDataPositionEvent(DataPositionEvent event) {
        this.dataPostEvents.add(event);
    }

    public void registerDataPostEvent(DataPostEvent event) {
        this.dataPostEvents.add(event);
        this.dataPostEvents.sort(Comparator.comparing(DataPostEvent::getOrder));
    }

    public void registerDataTransEvent(DataTransEvent event) {
        this.dataTransEvents.add(event);
    }

    public Stream<DataPostEvent> getDataPostEvents() {
        return this.dataPostEvents.stream();
    }

    public List<DataTransEvent> getDataTransEvents() {
        return this.dataTransEvents;
    }

    public void removeDataPostEvent(DataPostEvent event) {
        this.dataPostEvents.remove(event);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initCacheData(Map<String, Object> tableState, Map<String, Object> tableFields, Map<String, Object> tablesData, DataState state) {
        this.setListening(false);
        try {
            this.tables.stream().forEach(o -> {
                String tableName = o.getName();
                Object[] rows = (Object[])tablesData.get(tableName);
                o.setStatus((LazyLoadState)((Object)((Object)tableState.get(tableName))));
                if (rows.length == 0) {
                    return;
                }
                for (int i = 0; i < rows.length; ++i) {
                    o.insertRow(i, 1, (Object[])rows[i]);
                }
            });
        }
        finally {
            this.setListening(true);
        }
        this.state = state;
    }

    public void initGlobalDataTransEvents(List<GlobalDataTransEvent> globalDataTransEvents) {
        this.globalDataTransEvents = globalDataTransEvents;
    }
}

