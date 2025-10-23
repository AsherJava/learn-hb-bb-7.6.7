/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.common.MCColumnDefine;
import com.jiuqi.nr.multcheck2.common.MCTableConsts;
import com.jiuqi.nr.multcheck2.common.MCTableDefine;
import com.jiuqi.nr.multcheck2.common.MCTableUtil;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.IMCTableService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCTableServiceImpl
implements IMCTableService {
    private final Logger logger = LoggerFactory.getLogger(MCTableServiceImpl.class);
    @Autowired
    private IMCResultService mcResultService;
    @Autowired
    private IMCDimService mcDimService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public void createResultTable(DataScheme dataScheme) throws Exception {
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(this.getTableName("NR_MCR_ITEMORG_", dataScheme));
        List<DataDimension> dims = this.mcDimService.getDynamicDimsNoCache(dataScheme.getKey());
        if (CollectionUtils.isEmpty(dims)) {
            dims = new ArrayList<DataDimension>();
        }
        if (tableDefine != null) {
            List columns = this.designDataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            int recordColCount = 8 + dims.size();
            if (columns.size() != recordColCount) {
                this.dropResultTable(dataScheme);
            } else {
                List oldColumNames = columns.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
                List newColumNames = MCTableConsts.Org.COLS.stream().filter(e -> !e.isDimension()).map(MCColumnDefine::getName).collect(Collectors.toList());
                for (DataDimension dimension : dims) {
                    String colName;
                    String entityKey = dimension.getDimKey();
                    if ("ADJUST".equals(entityKey)) {
                        colName = "ADJUST";
                    } else {
                        IEntityDefine entity = this.entityMetaService.queryEntity(entityKey);
                        colName = entity.getDimensionName();
                    }
                    newColumNames.add(colName);
                }
                if (oldColumNames.size() == newColumNames.size() && new HashSet(oldColumNames).containsAll(newColumNames)) {
                    return;
                }
                this.dropResultTable(dataScheme);
            }
        }
        for (MCTableDefine td : MCTableConsts.TABLE_INFO) {
            tableDefine = this.designDataModelService.createTableModelDefine();
            MCTableUtil.convertDesignTableModelDefine(td, tableDefine, dataScheme, this.getTableName(td.getName(), dataScheme));
            String tableId = tableDefine.getID();
            String primaryKey = "";
            ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
            ArrayList<String> indexFieldList = new ArrayList<String>();
            double order = 100.0;
            for (int j = 0; j < td.getCols().size(); ++j) {
                MCColumnDefine cd = td.getCols().get(j);
                if (cd.isDimension()) {
                    this.buildDimCol(dims, tableId, createFieldList, order);
                    order = 200.0;
                    continue;
                }
                DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
                if (cd.isPrimary()) {
                    primaryKey = fieldDefine.getID();
                }
                if (cd.isIndex()) {
                    indexFieldList.add(fieldDefine.getID());
                }
                double d = order;
                order = d + 1.0;
                MCTableUtil.convertDesignColumnModelDefine(cd, fieldDefine, tableId, d);
                createFieldList.add(fieldDefine);
            }
            tableDefine.setKeys(primaryKey);
            tableDefine.setBizKeys(primaryKey);
            DesignCatalogModelDefine catLog = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            tableDefine.setCatalogID(catLog.getID());
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[1]));
            this.designDataModelService.insertTableModelDefine(tableDefine);
            if (!CollectionUtils.isEmpty(indexFieldList)) {
                this.designDataModelService.addIndexToTable(tableId, indexFieldList.toArray(new String[1]), "IDX_" + tableDefine.getName(), IndexModelType.NORMAL);
            }
            this.dataModelDeployService.deployTable(tableId);
        }
    }

    private String getTableName(String tablePrefix, DataScheme dataScheme) {
        return tablePrefix + dataScheme.getBizCode();
    }

    private void buildDimCol(List<DataDimension> dims, String tableId, List<DesignColumnModelDefine> createFieldList, double order) {
        if (CollectionUtils.isEmpty(dims)) {
            return;
        }
        for (DataDimension dimension : dims) {
            DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
            String entityKey = dimension.getDimKey();
            if ("ADJUST".equals(entityKey)) {
                fieldDefine.setName("ADJUST");
                fieldDefine.setCode("ADJUST");
            } else {
                IEntityDefine entity = this.entityMetaService.queryEntity(entityKey);
                IEntityModel entityModel = this.entityMetaService.getEntityModel(entity.getId());
                IEntityAttribute referFieldId = entityModel.getBizKeyField();
                if (referFieldId == null) {
                    referFieldId = entityModel.getRecordKeyField();
                }
                fieldDefine.setReferColumnID(referFieldId.getID());
                fieldDefine.setName(entity.getDimensionName());
                fieldDefine.setCode(entity.getDimensionName());
            }
            fieldDefine.setTableID(tableId);
            fieldDefine.setColumnType(ColumnModelType.STRING);
            fieldDefine.setPrecision(200);
            fieldDefine.setDefaultValue(dimension.getDefaultValue());
            double d = order;
            order = d + 1.0;
            fieldDefine.setOrder(d);
            fieldDefine.setNullAble(false);
            createFieldList.add(fieldDefine);
        }
    }

    @Override
    public void dropResultTable(DataScheme dataScheme) throws Exception {
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(this.getTableName("NR_MCR_RECORD_", dataScheme));
        if (tableDefine == null) {
            return;
        }
        this.mcResultService.cleanAllRecords(dataScheme);
        for (MCTableDefine td : MCTableConsts.TABLE_INFO) {
            String tableName = this.getTableName(td.getName(), dataScheme);
            DesignTableModelDefine table = this.designDataModelService.getTableModelDefineByCode(tableName);
            if (table == null) {
                return;
            }
            this.designDataModelService.deleteTableModelDefine(table.getID());
            this.dataModelDeployService.deployTable(table.getID());
        }
    }

    @Override
    public void dealIndex(DataScheme dataScheme) throws Exception {
        this.dealOrgIndex(dataScheme);
        this.dealErrorIndex(dataScheme);
    }

    private void dealOrgIndex(DataScheme dataScheme) throws Exception {
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(this.getTableName("NR_MCR_ITEMORG_", dataScheme));
        String tableId = tableDefine.getID();
        List indexes = this.designDataModelService.getIndexsByTable(tableId);
        if (CollectionUtils.isEmpty(indexes)) {
            return;
        }
        List columns = this.designDataModelService.getColumnModelDefinesByTable(tableId);
        Map<String, String> map = columns.stream().collect(Collectors.toMap(ColumnModelDefine::getName, IModelDefineItem::getID));
        Optional<DesignIndexModelDefine> optionalIndex = indexes.stream().filter(e -> e.getName().equals("IDX_" + tableDefine.getName())).findFirst();
        CharSequence[] fields = new String[]{map.get("MRR_KEY"), map.get("MSI_KEY"), map.get("MRIO_ORG")};
        long start = System.currentTimeMillis();
        StringBuilder msg = new StringBuilder();
        if (optionalIndex.isPresent()) {
            String[] cFields;
            DesignIndexModelDefine indexDefine = optionalIndex.get();
            String fieldIDs = indexDefine.getFieldIDs();
            String fieldsStr = String.join((CharSequence)";", fields);
            if (fieldsStr.equals(fieldIDs)) {
                return;
            }
            indexDefine.setFieldIDs(fieldsStr);
            this.designDataModelService.updateIndexModelDefine(indexDefine);
            Map<String, String> id2Name = columns.stream().collect(Collectors.toMap(IModelDefineItem::getID, ColumnModelDefine::getName));
            msg.append("\n").append("\u3010").append(tableDefine.getName()).append("\u3011\u5f00\u59cb\u4fee\u590d\uff0c\u539f\u7d22\u5f15\uff1a");
            for (String field : cFields = fieldIDs.split(";")) {
                msg.append(id2Name.get(field)).append("\u3001");
            }
            msg.deleteCharAt(msg.length() - 1);
            msg.append("\n");
            msg.append("\u3010").append(tableDefine.getName()).append("\u3011\u4fee\u590d\u5b8c\u6210\uff0c\u65b0\u7d22\u5f15\uff1a");
            msg.append("MRR_KEY").append("\u3001").append("MSI_KEY").append("\u3001").append("MRIO_ORG").append("\u3002");
        } else {
            this.designDataModelService.addIndexToTable(tableId, (String[])fields, "IDX_" + tableDefine.getName(), IndexModelType.NORMAL);
            msg.append("\u3010").append(tableDefine.getName()).append("\u3011\u521b\u5efa\u65b0\u7d22\u5f15\uff1a");
            msg.append("MRR_KEY").append("\u3001").append("MSI_KEY").append("\u3001").append("MRIO_ORG").append("\u3002");
        }
        this.dataModelDeployService.deployTable(tableId);
        msg.append("\u8017\u65f6\uff1a").append(System.currentTimeMillis() - start);
        this.logger.info(msg.toString());
    }

    private void dealErrorIndex(DataScheme dataScheme) throws Exception {
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(this.getTableName("NR_MCR_ERRORINFO_", dataScheme));
        String tableId = tableDefine.getID();
        List indexes = this.designDataModelService.getIndexsByTable(tableId);
        if (CollectionUtils.isEmpty(indexes)) {
            return;
        }
        List columns = this.designDataModelService.getColumnModelDefinesByTable(tableId);
        Map<String, String> map = columns.stream().collect(Collectors.toMap(ColumnModelDefine::getName, IModelDefineItem::getID));
        Optional<DesignIndexModelDefine> optionalIndex = indexes.stream().filter(e -> e.getName().equals("IDX_" + tableDefine.getName())).findFirst();
        CharSequence[] fields = new String[]{map.get("MEI_TASK"), map.get("MEI_PERIOD"), map.get("MSI_TYPE"), map.get("MEI_ORG"), map.get("MEI_RESOURCE")};
        long start = System.currentTimeMillis();
        StringBuilder msg = new StringBuilder();
        if (optionalIndex.isPresent()) {
            String[] cFields;
            DesignIndexModelDefine indexDefine = optionalIndex.get();
            String fieldIDs = indexDefine.getFieldIDs();
            String fieldsStr = String.join((CharSequence)";", fields);
            if (fieldsStr.equals(fieldIDs)) {
                return;
            }
            indexDefine.setFieldIDs(fieldsStr);
            this.designDataModelService.updateIndexModelDefine(indexDefine);
            Map<String, String> id2Name = columns.stream().collect(Collectors.toMap(IModelDefineItem::getID, ColumnModelDefine::getName));
            msg.append("\n").append("\u3010").append(tableDefine.getName()).append("\u3011\u5f00\u59cb\u4fee\u590d\uff0c\u539f\u7d22\u5f15\uff1a");
            for (String field : cFields = fieldIDs.split(";")) {
                msg.append(id2Name.get(field)).append("\u3001");
            }
            msg.deleteCharAt(msg.length() - 1);
            msg.append("\n");
            msg.append("\u3010").append(tableDefine.getName()).append("\u3011\u4fee\u590d\u5b8c\u6210\uff0c\u65b0\u7d22\u5f15\uff1a");
            msg.append("MEI_TASK").append("\u3001").append("MEI_PERIOD").append("\u3001").append("\u3001").append("MSI_TYPE").append("MEI_ORG").append("MEI_RESOURCE").append("\u3002");
        } else {
            this.designDataModelService.addIndexToTable(tableId, (String[])fields, "IDX_" + tableDefine.getName(), IndexModelType.NORMAL);
            msg.append("\u3010").append(tableDefine.getName()).append("\u3011\u521b\u5efa\u65b0\u7d22\u5f15\uff1a");
            msg.append("MEI_TASK").append("\u3001").append("MEI_PERIOD").append("\u3001").append("\u3001").append("MSI_TYPE").append("MEI_ORG").append("MEI_RESOURCE").append("\u3002");
        }
        this.dataModelDeployService.deployTable(tableId);
        msg.append("\u8017\u65f6\uff1a").append(System.currentTimeMillis() - start);
        this.logger.info(msg.toString());
    }
}

