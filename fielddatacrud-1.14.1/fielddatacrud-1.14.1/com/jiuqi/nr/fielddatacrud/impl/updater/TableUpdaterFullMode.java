/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveResItem
 *  com.jiuqi.nr.datacrud.SaveRowData
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.CrudSaveException
 *  com.jiuqi.nr.datacrud.impl.out.ReturnResInstance
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.dto.DimField;
import com.jiuqi.nr.fielddatacrud.impl.dto.SaveResDTO;
import com.jiuqi.nr.fielddatacrud.impl.updater.BaseTableUpdater;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TableUpdaterFullMode
extends BaseTableUpdater
implements TableUpdater {
    private static final Logger logger = LoggerFactory.getLogger(TableUpdaterFullMode.class);
    protected IDataQuery dataQuery;
    protected ExecutorContext context;
    protected IDataUpdator iDataUpdator;
    protected final List<SaveRowData> allRows = new ArrayList<SaveRowData>();
    protected final Set<String> failDw = new LinkedHashSet<String>();
    protected final Set<String> processDw = new LinkedHashSet<String>();
    protected final Set<String> saveDw = new LinkedHashSet<String>();
    protected final Map<String, ReturnRes> failRes = new HashMap<String, ReturnRes>();
    protected int importCount = 0;

    public TableUpdaterFullMode(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        super(saveInfo, fieldRelation);
    }

    public void initIDataUpdater(DimensionValueSet masterKeys, boolean delete) throws CrudOperateException {
        try {
            this.initializeDataQueryIfNeeded(masterKeys);
            this.initializeExecutorContextIfNeeded();
            this.initializeIDataUpdater(delete);
        }
        catch (Exception e) {
            throw new CrudOperateException(4111, "\u5220\u9664\u6570\u636e\u5931\u8d25", (Throwable)e);
        }
    }

    protected void initializeDataQueryIfNeeded(DimensionValueSet masterKeys) {
        if (this.dataQuery == null) {
            this.dataQuery = this.dataEngineService.getDataQuery();
            List<IMetaData> fields = this.saveInfo.getFields();
            for (IMetaData field : fields) {
                int index = this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)field.getDataField()));
                field.setIndex(index);
            }
        }
        this.dataQuery.setMasterKeys(masterKeys);
    }

    protected void initializeExecutorContextIfNeeded() {
        if (this.context == null) {
            Iterator<Variable> varItr = null;
            if (this.saveInfo.getVariables() != null) {
                varItr = this.saveInfo.getVariables().iterator();
            }
            this.context = this.executorContextFactory.getExecutorContext((ParamRelation)this.fieldRelation, (DimensionValueSet)null, varItr);
        }
    }

    protected void initializeIDataUpdater(boolean deleteAllExistingData) throws Exception {
        try {
            if (deleteAllExistingData) {
                this.iDataUpdator = this.dataQuery.openForUpdate(this.context, true);
                this.iDataUpdator.deleteAll();
            } else {
                this.iDataUpdator = this.dataQuery.openForUpdate(this.context, false);
            }
        }
        catch (Exception e) {
            throw new CrudOperateException(4111, "\u5220\u9664\u6570\u636e\u5931\u8d25", (Throwable)e);
        }
    }

    public ReturnRes addRow(DimensionValueSet masterKeys, DimensionValueSet rowKeys) throws CrudOperateException {
        String dw = this.getDwFromMasterKeys(masterKeys);
        if (this.failDw.contains(dw)) {
            return this.failRes.get(dw);
        }
        if (this.hasAccessPermission(masterKeys)) {
            this.processDw.add(dw);
            this.addRowToAllRows(rowKeys);
            this.checkAndPerformBatchCommit();
            this.logAddRowWithPermission(masterKeys);
            return ReturnRes.ok(null);
        }
        Set<DimensionValueSet> noAccessMasterKeys = this.accessDTO.getNoAccessMasterKeys();
        if (noAccessMasterKeys.contains(masterKeys)) {
            this.logAddRowWithoutPermission(masterKeys);
            this.noPermissionDw.add(dw);
            return ReturnRes.build((int)1101);
        }
        this.logAddRowWithOutOfRange(masterKeys);
        return ReturnRes.build((int)1103);
    }

    protected String getDwFromMasterKeys(DimensionValueSet masterKeys) {
        return masterKeys.getValue(this.tableDimSet.getDwDimName()).toString();
    }

    protected boolean hasAccessPermission(DimensionValueSet masterKeys) {
        return this.accessMasterKeys.contains(masterKeys);
    }

    protected void addRowToAllRows(DimensionValueSet rowKeys) {
        this.currRow = new SaveRowData();
        this.currRow.setRowKeys(rowKeys);
        this.currRow.setLinkValues(new AbstractData[this.saveInfo.getFields().size()]);
        this.allRows.add(this.currRow);
    }

    protected boolean checkAndPerformBatchCommit() throws CrudOperateException {
        if (this.allRows.size() > this.fieldDataProperties.getRowMaxSize()) {
            if (logger.isTraceEnabled()) {
                logger.trace("\u6570\u636e\u884c\u6570{},\u5f00\u59cb\u6279\u91cf\u63d0\u4ea4", (Object)this.allRows.size());
            }
            this.batchCommit();
            if (logger.isDebugEnabled()) {
                logger.trace("\u6279\u91cf\u63d0\u4ea4\u5b8c\u6210");
            }
            this.allRows.clear();
            this.processDw.clear();
            return true;
        }
        return false;
    }

    @Override
    public ReturnRes addRow(List<Object> values) throws CrudOperateException {
        return this.addRow(values.toArray());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ReturnRes addRow(Object[] values) throws CrudOperateException {
        if (logger.isTraceEnabled()) {
            logger.trace("\u6570\u636e\u884c\u4e2d\u6570\u636e{}", (Object)Arrays.toString(values));
        }
        DimensionValueSet rowKeys = new DimensionValueSet();
        DimensionValueSet masterKeys = new DimensionValueSet();
        for (DimField dimField : this.dimFields) {
            Object value;
            block13: {
                int index = dimField.getIndex();
                if (values.length <= index) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e{}", (Object)Arrays.toString(values));
                    }
                    throw new CrudOperateException(1202, "\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e");
                }
                if (index < 0) {
                    if ("RECORDKEY".equals(dimField.getDimName())) {
                        value = UUID.randomUUID().toString();
                        break block13;
                    } else {
                        if (logger.isTraceEnabled()) {
                            logger.trace("\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e{}", (Object)Arrays.toString(values));
                        }
                        throw new CrudOperateException(1202, "\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e");
                    }
                }
                value = values[index];
            }
            rowKeys.setValue(dimField.getDimName(), value);
            if (dimField.getType() != DimField.P_DIM) continue;
            masterKeys.setValue(dimField.getDimName(), value);
        }
        ReturnRes returnRes = this.addRow(masterKeys, rowKeys);
        if (returnRes.getCode() != 0) {
            return returnRes;
        }
        for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
            ReturnRes res = this.setData(i, values[i]);
            if (res.getCode() == 0) continue;
            String dw = masterKeys.getValue(this.tableDimSet.getDwDimName()).toString();
            this.failDw.add(dw);
            this.failRes.put(dw, res);
            if (logger.isTraceEnabled()) {
                List messages = res.getMessages();
                String message = res.getMessage();
                if (!CollectionUtils.isEmpty(messages)) {
                    message = String.join((CharSequence)",", messages);
                }
                logger.trace("\u5355\u4f4d{}\u6570\u636e\u89e3\u6790\u4e0d\u901a\u8fc7{},{},{}", dw, res.getCode(), res.getData(), message);
            }
            return res;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("\u6dfb\u52a0\u884c\u6570\u636e\u6210\u529f{}", (Object)masterKeys);
        }
        return ReturnResInstance.success();
    }

    public void batchCommit() throws CrudOperateException {
        this.initDataUpdaterForBatchCommit();
        this.logBatchCommit();
        this.importCount += this.allRows.size();
        for (SaveRowData row : this.allRows) {
            DimensionValueSet key = row.getRowKeys();
            try {
                this.insertRowData(key, row);
            }
            catch (IncorrectQueryException e) {
                this.handleIncorrectQueryException(e);
            }
            catch (Exception e) {
                this.handleGeneralException(e);
            }
        }
        this.commitChangesAndHandleException();
        this.saveDw.addAll(this.processDw);
    }

    private void logBatchCommit() {
        if (logger.isTraceEnabled()) {
            logger.trace("\u6279\u91cf\u63d0\u4ea4\u6570\u636e{}\u6761", (Object)this.allRows.size());
        }
    }

    protected void initDataUpdaterForBatchCommit() throws CrudOperateException {
        if (this.iDataUpdator == null) {
            this.fileMark(this.masterKeys);
            this.initIDataUpdater(this.masterKeys, true);
        } else {
            this.initIDataUpdater(this.masterKeys, false);
        }
    }

    private void insertRowData(DimensionValueSet key, SaveRowData row) throws IncorrectQueryException {
        IDataRow iDataRow = this.iDataUpdator.addInsertedRow(key);
        for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
            IMetaData metaData = this.saveInfo.getFields().get(i);
            AbstractData value = null;
            if (row.getLinkValues().length > i) {
                value = row.getLinkValues()[i];
            }
            iDataRow.setValue(metaData.getIndex(), (Object)value);
        }
    }

    private void handleIncorrectQueryException(IncorrectQueryException e) throws CrudSaveException {
        this.failDw.addAll(this.processDw);
        logger.error("\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        List<SaveResItem> saveResItems = this.exceptionErrors((Exception)((Object)e));
        if (!CollectionUtils.isEmpty(saveResItems)) {
            String message = saveResItems.get(0).getMessage();
            if (!StringUtils.hasLength(message)) {
                message = "\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef" + e.getMessage();
            }
            for (String pdw : this.processDw) {
                this.failRes.put(pdw, ReturnRes.build((int)1900, (String)message));
            }
        }
    }

    private void handleGeneralException(Exception e) throws CrudOperateException {
        this.failDw.addAll(this.processDw);
        for (String pdw : this.processDw) {
            this.failRes.put(pdw, ReturnRes.build((int)1900, (String)("\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage())));
        }
        logger.error("\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        throw new CrudOperateException(4122, "\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", (Throwable)e);
    }

    private void commitChangesAndHandleException() throws CrudSaveException {
        try {
            this.iDataUpdator.commitChanges();
        }
        catch (Exception e) {
            this.failDw.addAll(this.processDw);
            for (String pdw : this.processDw) {
                this.failRes.put(pdw, ReturnRes.build((int)1900, (String)"\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef"));
            }
            logger.error("\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors(e);
            throw new CrudSaveException(saveResItems);
        }
    }

    @Override
    public void commit() throws CrudOperateException {
        this.batchCommit();
    }

    @Override
    public SaveRes getSaveRes() {
        SaveResDTO saveRes = new SaveResDTO();
        saveRes.setSaveDw(this.saveDw);
        saveRes.setFailDw(this.failDw);
        saveRes.setFailMessage(this.failRes);
        saveRes.setNoPermissionDw(this.noPermissionDw);
        saveRes.setCount(this.importCount);
        return saveRes;
    }
}

