/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.BizKeyOrder;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableDesignService;
import com.jiuqi.nr.datascheme.internal.service.impl.AbstractCopyVisitor;
import com.jiuqi.nr.datascheme.internal.service.impl.CopyAttributes;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(rollbackFor={Exception.class})
public class CopySchemeVisitor
extends AbstractCopyVisitor
implements SchemeNodeVisitor<CopyAttributes> {
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private DataTableDesignService dataTableService;

    public CopySchemeVisitor(IDesignDataSchemeService iDesignDataSchemeService) {
        super(iDesignDataSchemeService);
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, CopyAttributes> visitSchemeNode(SchemeNode<CopyAttributes> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        String tableKey = this.doCopyDims(dims, ele);
        HashMap<String, CopyAttributes> all = new HashMap<String, CopyAttributes>(16);
        this.doCopyGroups(ele, dataGroups, all);
        this.doCopyTables(ele, dataTables, tableKey, all);
        return all;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<CopyAttributes> ele, List<DA> attributes) {
    }

    private <DT extends DesignDataTable> void doCopyTables(SchemeNode<CopyAttributes> ele, List<DT> dataTables, String tableKey, Map<String, CopyAttributes> all) {
        ArrayList<String> keyTemp = new ArrayList<String>(dataTables.size());
        Iterator<DT> iterator = dataTables.iterator();
        while (iterator.hasNext()) {
            DesignDataTable table = (DesignDataTable)iterator.next();
            if (table.getDataTableType() == null) {
                iterator.remove();
                continue;
            }
            keyTemp.add(table.getKey());
        }
        if (dataTables.isEmpty()) {
            return;
        }
        List<DesignDataTableDO> tables = super.doCopyTable(dataTables, ele);
        String[] tableKeys = this.dataTableService.insertDataTables(tables);
        for (int i = 0; i < tableKeys.length; ++i) {
            CopyAttributes copyAttributes = new CopyAttributes(tableKeys[i], (CopyAttributes)ele.getOther());
            all.put((String)keyTemp.get(i), copyAttributes);
        }
    }

    private <DG extends DesignDataGroup> void doCopyGroups(SchemeNode<CopyAttributes> ele, List<DG> dataGroups, Map<String, CopyAttributes> all) {
        ArrayList<String> oldKey = new ArrayList<String>(dataGroups.size());
        for (DesignDataGroup dataGroup : dataGroups) {
            oldKey.add(dataGroup.getKey());
        }
        List<DesignDataGroupDO> list = super.doCopyGroup(dataGroups, ele);
        String[] newIds = this.dataGroupDao.batchInsert(list);
        for (int i = 0; i < newIds.length; ++i) {
            all.put((String)oldKey.get(i), new CopyAttributes(newIds[i], (CopyAttributes)ele.getOther()));
        }
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, CopyAttributes> visitGroupNode(SchemeNode<CopyAttributes> ele, List<DG> dataGroups, List<DT> dataTables) {
        HashMap<String, CopyAttributes> all = new HashMap<String, CopyAttributes>(16);
        this.doCopyGroups(ele, dataGroups, all);
        this.doCopyTables(ele, dataTables, null, all);
        return all;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<CopyAttributes> ele, List<DF> dataFields) {
        if (dataFields.isEmpty()) {
            return;
        }
        CopyAttributes other = (CopyAttributes)ele.getOther();
        if (other == null) {
            return;
        }
        String newKey = other.getNewKey();
        if (!StringUtils.hasLength(newKey)) {
            return;
        }
        this.doCopyField(ele, dataFields, other.getNewScheme().getKey(), other.getNewKey());
    }

    @Override
    protected DataScheme getSrcDataScheme(SchemeNode<CopyAttributes> ele) {
        return ((CopyAttributes)ele.getOther()).getScheme();
    }

    @Override
    protected DesignDataScheme getDataScheme(SchemeNode<CopyAttributes> ele) {
        return ((CopyAttributes)ele.getOther()).getNewScheme();
    }

    protected <DF extends DesignDataField> String[] doCopyField(SchemeNode<CopyAttributes> ele, List<DF> dataFields, String schemeKey, String tableKey) {
        if (dataFields.isEmpty()) {
            return new String[0];
        }
        ArrayList<DesignDataFieldDO> fields = new ArrayList<DesignDataFieldDO>(dataFields.size());
        int userFieldCopy = DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        boolean encrypted = StringUtils.hasText(this.getDataScheme(ele).getEncryptScene());
        ArrayList<DesignDataField> bizKeys = null;
        for (DesignDataField field : dataFields) {
            DataFieldKind dataFieldKind = field.getDataFieldKind();
            if ((userFieldCopy & dataFieldKind.getValue()) == 0) continue;
            field.setKey(UUIDUtils.getKey());
            field.setDataSchemeKey(schemeKey);
            field.setDataTableKey(tableKey);
            field.setUpdateTime(Instant.now());
            field.setEncrypted(Boolean.valueOf(field.getEncrypted() != false && encrypted));
            fields.add(Convert.iDf2Do(field));
            if (dataFieldKind.getValue() != DataFieldKind.TABLE_FIELD_DIM.getValue()) continue;
            if (bizKeys == null) {
                bizKeys = new ArrayList<DesignDataField>();
            }
            bizKeys.add(field);
        }
        if (bizKeys != null) {
            DesignDataTableDO tableDO = this.dataTableDao.get(tableKey);
            String[] oldBizKeys = tableDO.getBizKeys();
            List<DesignDataFieldDO> bizFields = this.dataFieldDao.batchGet(Arrays.asList(oldBizKeys));
            bizKeys.addAll(bizFields);
            tableDO.setBizKeys(BizKeyOrder.order(bizKeys));
            this.dataTableDao.update(tableDO);
        }
        return this.dataFieldDao.batchInsert(fields);
    }

    private <DM extends DesignDataDimension> String doCopyDims(List<DM> dims, SchemeNode<CopyAttributes> ele) {
        List<DesignDataDimension> dimensions = ((CopyAttributes)ele.getOther()).getDimensions();
        if (CollectionUtils.isEmpty(dimensions)) {
            if (dims.isEmpty()) {
                return null;
            }
            for (DesignDataDimension dim : dims) {
                dim.setUpdateTime(Instant.now());
                dim.setDataSchemeKey(((CopyAttributes)ele.getOther()).getNewScheme().getKey());
                dim.setOrder(OrderGenerator.newOrder());
                this.dataDimDao.insert(Convert.iDm2Do(dim));
            }
        } else {
            for (DesignDataDimension dim : dimensions) {
                dim.setUpdateTime(Instant.now());
                dim.setDataSchemeKey(((CopyAttributes)ele.getOther()).getNewScheme().getKey());
                dim.setOrder(OrderGenerator.newOrder());
                this.dataDimDao.insert(Convert.iDm2Do(dim));
            }
        }
        return null;
    }

    public VisitorResult preVisitNode(SchemeNode<CopyAttributes> ele) {
        return null;
    }

    public CopyAttributes visitRootIsSchemeNode(DesignDataScheme scheme) {
        return null;
    }

    public CopyAttributes visitRootIsGroupNode(DesignDataGroup group) {
        return null;
    }

    public CopyAttributes visitRootIsTableNode(DesignDataTable table) {
        return null;
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, CopyAttributes> visitSchemeGroupNode(SchemeNode<CopyAttributes> next, List<DG> groups, List<DS> schemes) {
        return null;
    }
}

