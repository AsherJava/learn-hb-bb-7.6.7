/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.designer.amount.init;

import com.jiuqi.nr.designer.web.treebean.AmountObject;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmountCreateProcessor {
    private static final Logger log = LoggerFactory.getLogger(AmountCreateProcessor.class);
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;

    public void createData() {
        try {
            this.createAndDeployTable();
            List<AmountObject> amountData = this.initAmountData();
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            TableModelDefine table = this.dataModelService.getTableModelDefineByCode("NR_MEASURE_UNIT");
            List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
            for (ColumnModelDefine column : columns) {
                queryModel.getColumns().add(new NvwaQueryColumn(column));
            }
            INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator updaor = dataAccess.openForUpdate(context);
            for (AmountObject amount : amountData) {
                INvwaDataRow newRow = updaor.addInsertRow();
                newRow.setValue(0, (Object)amount.getId());
                newRow.setValue(1, (Object)amount.getCode());
                newRow.setValue(2, (Object)amount.getTitle());
                newRow.setValue(3, (Object)amount.getParent());
                newRow.setValue(4, (Object)amount.getRatio());
                newRow.setValue(5, (Object)amount.getBaseunit());
                newRow.setValue(6, (Object)amount.getOrderl());
            }
            updaor.commitChanges(context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createAndDeployTable() throws Exception {
        DesignTableModelDefine tableModelDefine = this.designDataModelService.createTableModelDefine();
        tableModelDefine.setID("9493b4eb-6516-48a8-a878-25a63a23e63a");
        DesignColumnModelDefine[] columnModelDefineArray = this.createColumnModelDefineArray(tableModelDefine.getID());
        this.designDataModelService.insertColumnModelDefines(columnModelDefineArray);
        tableModelDefine.setCode("NR_MEASURE_UNIT");
        tableModelDefine.setTitle("\u91d1\u989d");
        tableModelDefine.setName("NR_MEASURE_UNIT");
        tableModelDefine.setKeys(columnModelDefineArray[1].getID());
        tableModelDefine.setBizKeys(columnModelDefineArray[1].getID());
        tableModelDefine.setDesc("\u91d1\u989d");
        tableModelDefine.setType(TableModelType.ENUM);
        this.designDataModelService.insertTableModelDefine(tableModelDefine);
        this.designDataModelService.addIndexToTable(tableModelDefine.getID(), new String[]{columnModelDefineArray[1].getID()}, "UNIQ_NR_MEASURE_UNIT", IndexModelType.UNIQUE);
        this.dataModelDeployService.deployTable(tableModelDefine.getID());
    }

    private DesignColumnModelDefine[] createColumnModelDefineArray(String tableID) {
        DesignColumnModelDefine columnID = this.designDataModelService.createColumnModelDefine();
        columnID.setID("7e6d0607-78f5-432a-90c8-639e76b50f51");
        columnID.setCode("MN_ID");
        columnID.setColumnType(ColumnModelType.STRING);
        columnID.setName("MN_ID");
        columnID.setTitle("\u91cf\u7eb2\u4e3b\u952e");
        columnID.setTableID(tableID);
        columnID.setPrecision(40);
        columnID.setOrder(1.0);
        DesignColumnModelDefine columnCode = this.designDataModelService.createColumnModelDefine();
        columnCode.setID("81a072d4-2a0e-4651-bd9e-4b50d20977fe");
        columnCode.setCode("MN_CODE");
        columnCode.setColumnType(ColumnModelType.STRING);
        columnCode.setName("MN_CODE");
        columnCode.setTitle("\u91cf\u7eb2\u7f16\u7801");
        columnCode.setTableID(tableID);
        columnCode.setPrecision(10);
        columnCode.setOrder(2.0);
        DesignColumnModelDefine columnTitle = this.designDataModelService.createColumnModelDefine();
        columnTitle.setID("044501a5-cded-470d-8bb4-5e00da7a13ae");
        columnTitle.setCode("MN_TITLE");
        columnTitle.setColumnType(ColumnModelType.STRING);
        columnTitle.setName("MN_TITLE");
        columnTitle.setTitle("\u91cf\u7eb2\u540d\u79f0");
        columnTitle.setTableID(tableID);
        columnTitle.setPrecision(20);
        columnTitle.setNullAble(true);
        columnTitle.setOrder(3.0);
        DesignColumnModelDefine columnParent = this.designDataModelService.createColumnModelDefine();
        columnParent.setID("4a24a753-cd43-4e47-b736-48e627079527");
        columnParent.setCode("MN_PARENT");
        columnParent.setColumnType(ColumnModelType.STRING);
        columnParent.setName("MN_PARENT");
        columnParent.setTitle("\u91cf\u7eb2\u7236\u8282\u70b9");
        columnParent.setTableID(tableID);
        columnParent.setPrecision(40);
        columnParent.setNullAble(true);
        columnParent.setOrder(4.0);
        DesignColumnModelDefine columnRatio = this.designDataModelService.createColumnModelDefine();
        columnRatio.setID("07ebcd75-cbd4-4ce7-88be-4fe2aff278c5");
        columnRatio.setCode("MN_RATIO");
        columnRatio.setColumnType(ColumnModelType.INTEGER);
        columnRatio.setName("MN_RATIO");
        columnRatio.setTitle("\u91cf\u7eb2\u5355\u4f4d\u6bd4\u7387");
        columnRatio.setTableID(tableID);
        columnRatio.setPrecision(28);
        columnRatio.setNullAble(true);
        columnRatio.setOrder(5.0);
        DesignColumnModelDefine columnBaseunit = this.designDataModelService.createColumnModelDefine();
        columnBaseunit.setID("b4ab211a-e60a-4938-a6f6-c85ad01afe57");
        columnBaseunit.setCode("MN_BASEUNIT");
        columnBaseunit.setColumnType(ColumnModelType.INTEGER);
        columnBaseunit.setName("MN_BASEUNIT");
        columnBaseunit.setTitle("\u91cf\u7eb2\u57fa\u51c6\u5355\u4f4d");
        columnBaseunit.setTableID(tableID);
        columnBaseunit.setPrecision(1);
        columnBaseunit.setNullAble(true);
        columnBaseunit.setOrder(6.0);
        DesignColumnModelDefine columnOrder = this.designDataModelService.createColumnModelDefine();
        columnOrder.setID("02bfa5c5-5a50-47a1-93f5-3672c6f51f8c");
        columnOrder.setCode("MN_ORDERL");
        columnOrder.setColumnType(ColumnModelType.STRING);
        columnOrder.setName("MN_ORDERL");
        columnOrder.setTitle("\u91cf\u7eb2\u6392\u5e8f");
        columnOrder.setTableID(tableID);
        columnOrder.setPrecision(40);
        columnOrder.setNullAble(true);
        columnOrder.setOrder(7.0);
        return new DesignColumnModelDefine[]{columnID, columnCode, columnTitle, columnParent, columnRatio, columnBaseunit, columnOrder};
    }

    private List<AmountObject> initAmountData() {
        ArrayList<AmountObject> amounts = new ArrayList<AmountObject>();
        amounts.add(new AmountObject("5ed62ea4-e418-4593-8fe2-77d26a8feefa", "RENMINBI", "\u4eba\u6c11\u5e01", "0", null, 0, "K4T71T7V"));
        amounts.add(new AmountObject("083d4898-7621-40ab-9211-9f20b5b4d7f2", "YUAN", "\u5143", "RENMINBI", new BigDecimal("1"), 1, "K4T71T7O"));
        amounts.add(new AmountObject("0c0ba239-e423-47ce-b484-bec9c17f8b8e", "BAIYUAN", "\u767e\u5143", "RENMINBI", new BigDecimal("100"), 0, "K4T71T7P"));
        amounts.add(new AmountObject("0c0ba239-e423-47ce-b484-bec9c17f8b9f", "QIANYUAN", "\u5343\u5143", "RENMINBI", new BigDecimal("1000"), 0, "K4T71T7Q"));
        amounts.add(new AmountObject("73379f90-f5e8-4872-9929-b36170c61467", "WANYUAN", "\u4e07\u5143", "RENMINBI", new BigDecimal("10000"), 0, "K4T71T7R"));
        amounts.add(new AmountObject("080d545c-dfc4-41e2-b436-f2b2b165cabd", "BAIWAN", "\u767e\u4e07", "RENMINBI", new BigDecimal("1000000"), 0, "K4T71T7S"));
        amounts.add(new AmountObject("38e0e7de-21de-47c9-8fb3-5940fe7e9dd8", "QIANWAN", "\u5343\u4e07", "RENMINBI", new BigDecimal("10000000"), 0, "K4T71T7T"));
        amounts.add(new AmountObject("5a7fc581-94fa-4dda-8a48-2beb25dc50e7", "YIYUAN", "\u4ebf\u5143", "RENMINBI", new BigDecimal("100000000"), 0, "K4T71T7U"));
        return amounts;
    }
}

