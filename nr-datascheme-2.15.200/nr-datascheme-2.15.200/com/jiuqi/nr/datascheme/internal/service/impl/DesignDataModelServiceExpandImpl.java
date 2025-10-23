/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.INrDesignDataModelService
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.common.exception.DataModelException
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.service.INrDesignDataModelService;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.common.exception.DataModelException;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesignDataModelServiceExpandImpl
implements INrDesignDataModelService {
    public static final String CODE_PREFIX = "NR_";
    private DesignDataModelService createTableModelDefine;

    @Autowired
    public void setCreateTableModelDefine(DesignDataModelService createTableModelDefine) {
        this.createTableModelDefine = createTableModelDefine;
    }

    public DesignTableModelDefine createTableModelDefine() {
        return this.createTableModelDefine.createTableModelDefine();
    }

    @Transactional(rollbackFor={Exception.class})
    public int insertTableModelDefine(DesignTableModelDefine tableModelDefine) throws ModelValidateException, DataModelException {
        if (tableModelDefine == null) {
            throw new IllegalArgumentException("tableModelDefine must not be null");
        }
        String code = tableModelDefine.getCode();
        if (code != null) {
            tableModelDefine.setCode(CODE_PREFIX + code);
        }
        return this.createTableModelDefine.insertTableModelDefine(tableModelDefine);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] insertTableModelDefines(DesignTableModelDefine[] tableModelDefines) throws ModelValidateException {
        if (tableModelDefines == null) {
            throw new IllegalArgumentException("tableModelDefines must not be null");
        }
        for (DesignTableModelDefine tableModelDefine : tableModelDefines) {
            String code = tableModelDefine.getCode();
            if (code == null) continue;
            tableModelDefine.setCode(CODE_PREFIX + code);
        }
        return this.createTableModelDefine.insertTableModelDefines(tableModelDefines);
    }

    @Transactional(rollbackFor={Exception.class})
    public int deleteTableModelDefine(String id) {
        return this.createTableModelDefine.deleteTableModelDefine(id);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] deleteTableModelDefines(String[] ids) {
        return this.createTableModelDefine.deleteTableModelDefines(ids);
    }

    @Transactional(rollbackFor={Exception.class})
    public int updateTableModelDefine(DesignTableModelDefine tableModelDefine) throws ModelValidateException {
        return this.createTableModelDefine.updateTableModelDefine(tableModelDefine);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] updateTableModelDefines(DesignTableModelDefine[] tableModelDefines) {
        return this.createTableModelDefine.updateTableModelDefines(tableModelDefines);
    }

    public DesignTableModelDefine getTableModelDefine(String id) {
        return this.createTableModelDefine.getTableModelDefine(id);
    }

    public List<DesignTableModelDefine> getTableModelDefines(String[] ids) {
        return this.createTableModelDefine.getTableModelDefines(ids);
    }

    public DesignTableModelDefine getTableModelDefineByCode(String code) {
        return this.createTableModelDefine.getTableModelDefineByCode(code);
    }

    public List<DesignTableModelDefine> getTableModelDefines() {
        return this.createTableModelDefine.getTableModelDefines();
    }

    public List<DesignTableModelDefine> getTableModelDefinesByCatalogId(String catalogId) {
        return this.createTableModelDefine.getTableModelDefinesByCatalogID(catalogId);
    }

    public List<DesignTableModelDefine> getTableModelDefinesByType(TableModelType type) {
        return this.createTableModelDefine.getTableModelDefinesByType(type);
    }

    public DesignColumnModelDefine createColumnModelDefine() {
        return this.createTableModelDefine.createColumnModelDefine();
    }

    @Transactional(rollbackFor={Exception.class})
    public int insertColumnModelDefine(DesignColumnModelDefine columnModelDefine) throws ModelValidateException {
        return this.createTableModelDefine.insertColumnModelDefine(columnModelDefine);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] insertColumnModelDefines(DesignColumnModelDefine[] columnDefines) throws ModelValidateException {
        return this.createTableModelDefine.insertColumnModelDefines(columnDefines);
    }

    @Transactional(rollbackFor={Exception.class})
    public int deleteColumnModelDefine(String id) {
        return this.createTableModelDefine.deleteColumnModelDefine(id);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] deleteColumnModelDefines(String[] ids) {
        return this.createTableModelDefine.deleteColumnModelDefines(ids);
    }

    @Transactional(rollbackFor={Exception.class})
    public int deleteColumnModelDefineByTable(String tableId) {
        return this.createTableModelDefine.deleteColumnModelDefineByTable(tableId);
    }

    @Transactional(rollbackFor={Exception.class})
    public int updateColumnModelDefine(DesignColumnModelDefine columnModelDefine) {
        return this.createTableModelDefine.updateColumnModelDefine(columnModelDefine);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] updateColumnModelDefines(DesignColumnModelDefine[] columnModelDefines) {
        return this.createTableModelDefine.updateColumnModelDefines(columnModelDefines);
    }

    public DesignColumnModelDefine getColumnModelDefine(String id) {
        return this.createTableModelDefine.getColumnModelDefine(id);
    }

    public List<DesignColumnModelDefine> getColumnModelDefines(String[] ids) {
        return this.createTableModelDefine.getColumnModelDefines(ids);
    }

    public DesignColumnModelDefine getColumnModelDefineByCode(String tableId, String columnCode) {
        return this.createTableModelDefine.getColumnModelDefineByCode(tableId, columnCode);
    }

    public List<DesignColumnModelDefine> getColumnModelDefinesByTable(String tableId) {
        return this.createTableModelDefine.getColumnModelDefinesByTable(tableId);
    }

    public List<DesignIndexModelDefine> getIndexsByTable(String tableId) {
        return this.createTableModelDefine.getIndexsByTable(tableId);
    }

    @Transactional(rollbackFor={Exception.class})
    public String addIndexToTable(String tableKey, String[] fields, String indexName, IndexModelType type) {
        return this.createTableModelDefine.addIndexToTable(tableKey, fields, indexName, type);
    }

    @Transactional(rollbackFor={Exception.class})
    public int deleteIndexModelDefine(String id) {
        return this.createTableModelDefine.deleteIndexModelDefine(id);
    }

    @Transactional(rollbackFor={Exception.class})
    public int deleteIndexsByTable(String tableId) {
        return this.createTableModelDefine.deleteIndexsByTable(tableId);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] deleteIndexModelDefines(String[] ids) {
        return this.createTableModelDefine.deleteIndexModelDefines(ids);
    }

    @Transactional(rollbackFor={Exception.class})
    public int updateIndexModelDefine(DesignIndexModelDefine indexModelDefine) {
        return this.createTableModelDefine.updateIndexModelDefine(indexModelDefine);
    }

    @Transactional(rollbackFor={Exception.class})
    public int[] updateIndexModelDefines(DesignIndexModelDefine[] indexModelDefines) {
        return this.createTableModelDefine.updateIndexModelDefines(indexModelDefines);
    }

    public DesignTableModel createTableModel() {
        return this.createTableModelDefine.createTableModel();
    }

    @Transactional(rollbackFor={Exception.class})
    public int deleteTableModel(String id) {
        return this.createTableModelDefine.delteTableModel(id);
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveTableModel(DesignTableModel tableModel) throws ModelValidateException, DataModelException {
        this.createTableModelDefine.saveTableModel(tableModel);
    }

    public DesignTableModel getTableModel(String id) {
        return this.createTableModelDefine.getTableModel(id);
    }
}

