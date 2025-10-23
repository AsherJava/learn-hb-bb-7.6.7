/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableMap
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.common.DataSchemeEnum
 *  com.jiuqi.nr.datascheme.internal.convert.Convert
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataGroupDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.datascheme.internal.service.impl.DesignDataSchemeServiceImpl
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.query.datascheme.service.impl;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableMap;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.impl.DesignDataSchemeServiceImpl;
import com.jiuqi.nr.query.datascheme.bean.FieldRel;
import com.jiuqi.nr.query.datascheme.bean.TableRelInfo;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager;
import com.jiuqi.nr.query.datascheme.extend.DataTableInfo;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.IQueryDataFieldValidator;
import com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service(value="QueryDataSchemeService")
@Transactional(rollbackFor={Exception.class})
public class DesignQueryDataSchemeServiceImpl
extends DesignDataSchemeServiceImpl
implements IDesignQueryDataSchemeService {
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IDataTableMapDao iDataTableMapDao;
    @Autowired
    private IDataSchemeAuthService iDataSchemeAuthService;
    @Autowired
    private IQueryDataFieldValidator fieldValidator;
    @Autowired
    private DataTableValidator dataTableValidator;

    @Override
    @Transactional(readOnly=true)
    public QueryDataTableDTO getQueryDataTable(String dataTableKey) {
        DesignDataTable dataTable = this.getDataTable(dataTableKey);
        if (null == dataTable) {
            return null;
        }
        DataTableMapDO dataTableMap = this.iDataTableMapDao.get(dataTable.getKey());
        if (null == dataTableMap) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DT_4.getMessage());
        }
        return QueryDataTableDTO.valueOf((DataTable)dataTable, (DataTableMap)dataTableMap);
    }

    @Override
    public String insertQueryDataTable(QueryDataTableDTO dataTable, Map<String, String> dimFields) {
        List<FieldRel> fieldRels;
        IDataTableFactory factory;
        if (!StringUtils.hasLength(dataTable.getKey())) {
            dataTable.setKey(UUIDUtils.getKey());
        }
        if (null == (factory = DataTableFactoryManager.getInstance().getFactory(dataTable.getTableType()))) {
            throw new SchemeDataException("\u672a\u652f\u6301\u7684\u67e5\u8be2\u6570\u636e\u8868\u7c7b\u578b\uff1a" + dataTable.getTableType());
        }
        if (dataTable.getDataTableType().equals((Object)DataTableType.SUB_TABLE) && !factory.getType().equals("nvwaDataSource")) {
            throw new SchemeDataException("\u5b50\u8868\u53ea\u652f\u6301\u5f15\u7528\u5973\u5a32\u6570\u636e\u6e90\uff0c\u6682\u65f6\u4e0d\u652f\u6301\u5176\u4ed6\u7c7b\u578b\u6570\u636e\u6e90");
        }
        this.checkDeployStatus(dataTable.getDataSchemeKey());
        this.dataTableValidator.checkTable((DesignDataTable)dataTable);
        AbstractDataTableAdapter dataTableAdapter = factory.getDataTableAdapter();
        DataTableInfo info = DesignQueryDataSchemeServiceImpl.createDataTableInfo(dataTable, dimFields);
        TableRelInfo tableRelInfo = dataTable.getTableRelInfo();
        if (tableRelInfo != null && !CollectionUtils.isEmpty(fieldRels = tableRelInfo.getFieldRels())) {
            fieldRels.forEach(rel -> info.getSrcFieldCodes().add(rel.getSrcFieldCode()));
        }
        try {
            dataTableAdapter.createDataTable(info);
        }
        catch (DataTableAdaptException e) {
            throw new SchemeDataException((Throwable)e);
        }
        return dataTable.getKey();
    }

    private static DataTableInfo createDataTableInfo(QueryDataTableDTO dataTable, Map<String, String> dimFields) {
        DataTableInfo info = new DataTableInfo();
        info.setKey(dataTable.getKey());
        info.setCode(dataTable.getCode());
        info.setTitle(dataTable.getTitle());
        info.setDataTableType(dataTable.getDataTableType());
        info.setDescription(dataTable.getDesc());
        info.setExpression(dataTable.getExpression());
        info.setGroupKey(dataTable.getDataGroupKey());
        info.setDataSchemeKey(dataTable.getDataSchemeKey());
        info.setSrcTableKey(dataTable.getTableKey());
        info.setSrcType(dataTable.getTableType());
        info.getDimFieldMap().putAll(dimFields);
        return info;
    }

    @Override
    public void deleteQueryDataTable(String dataTableKey) {
        this.iDataTableMapDao.delete(dataTableKey);
        this.deleteDataTable(dataTableKey);
    }

    @Override
    public void deleteQueryDataGroup(String dataGroupKey) {
        DesignDataGroup dataGroup = this.getDataGroup(dataGroupKey);
        if (dataGroup == null) {
            return;
        }
        DataGroupKind dataGroupKind = dataGroup.getDataGroupKind();
        if (dataGroupKind == DataGroupKind.SCHEME_GROUP || dataGroupKind == DataGroupKind.QUERY_SCHEME_GROUP) {
            String next;
            LinkedList<String> delete = new LinkedList<String>();
            Stack<String> stack = new Stack<String>();
            stack.push(dataGroupKey);
            while (!stack.isEmpty() && (next = (String)stack.pop()) != null) {
                delete.add(next);
                List scheme = this.dataSchemeDao.getByParent(next);
                if (!scheme.isEmpty()) {
                    throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DG_2_1.getMessage());
                }
                List byParent = this.dataGroupDao.getByParent(next);
                List children = byParent.stream().map(DataGroupDO::getKey).collect(Collectors.toList());
                if (!children.isEmpty()) {
                    stack.addAll(children);
                }
                if (stack.size() <= 1000) continue;
                throw new RuntimeException("\u5206\u7ec4\u7ea7\u6b21\u5b58\u5728\u73af\u5f62\u6570\u636e");
            }
            this.dataGroupDao.batchDelete(delete);
            this.iDataSchemeAuthService.revokeAllForSchemeGroup(delete);
        } else {
            String next;
            this.checkDeployStatus(dataGroup.getDataSchemeKey());
            LinkedList<String> deleteGroups = new LinkedList<String>();
            LinkedList deleteTables = new LinkedList();
            Stack<String> stack = new Stack<String>();
            stack.push(dataGroupKey);
            while (!stack.isEmpty() && (next = (String)stack.pop()) != null) {
                List byParent;
                List children;
                deleteGroups.add(next);
                List byGroup = this.dataTableDao.getByGroup(next);
                List tables = byGroup.stream().map(DataTableDO::getKey).collect(Collectors.toList());
                if (!tables.isEmpty()) {
                    deleteTables.addAll(tables);
                }
                if (!(children = (byParent = this.dataGroupDao.getByParent(next)).stream().map(DataGroupDO::getKey).collect(Collectors.toList())).isEmpty()) {
                    stack.addAll(children);
                }
                if (stack.size() <= 1000) continue;
                throw new RuntimeException("\u5206\u7ec4\u7ea7\u6b21\u5b58\u5728\u73af\u5f62\u6570\u636e");
            }
            this.dataGroupDao.batchDelete(deleteGroups);
            this.dataTableDao.batchDelete(deleteTables);
            for (String deleteTable : deleteTables) {
                this.iDataTableMapDao.delete(deleteTable);
            }
        }
    }

    @Override
    public void deleteQueryDataScheme(String dataSchemeKey) {
        this.iDataTableMapDao.deleteByScheme(dataSchemeKey);
        this.deleteDataScheme(dataSchemeKey);
    }

    @Override
    public void updateQueryDataField(DesignDataField dataField) throws SchemeDataException {
        Assert.notNull((Object)dataField, "dataField must not be null.");
        Assert.notNull((Object)dataField.getKey(), "key must not be null.");
        this.checkDeployStatus(dataField.getDataSchemeKey());
        this.fieldValidator.checkField(dataField);
        DesignDataFieldDO designDataFieldDO = Convert.iDf2Do((DesignDataField)dataField);
        this.fieldValidationRule((DesignDataField)designDataFieldDO);
        this.dataFieldDao.update((DataFieldDO)designDataFieldDO);
        String dataTableKey = dataField.getDataTableKey();
        DesignDataTableDO dataTableDO = (DesignDataTableDO)this.dataTableDao.get(dataTableKey);
        if (dataTableDO == null) {
            throw new SchemeDataException("\u8868\u5df2\u5220\u9664\uff0c" + DataSchemeEnum.DATA_SCHEME_DF_1.getMessage());
        }
        this.maintenance(dataTableDO, designDataFieldDO);
    }

    @Override
    public <E extends DesignDataField> void updateQueryDataFields(List<E> dataField) throws SchemeDataException {
        Map table2Fields = this.pretreatmentDataFields(dataField);
        if (table2Fields == null) {
            return;
        }
        for (Map.Entry table2List : table2Fields.entrySet()) {
            String dataTableKey = (String)table2List.getKey();
            List value = (List)table2List.getValue();
            DesignDataTableDO designDataTableDO = this.checkDesignDataTable(dataTableKey);
            this.fieldValidator.checkField(value);
            List list = this.maintenance(designDataTableDO, value, true);
            this.dataFieldDao.batchUpdate(list);
        }
    }
}

