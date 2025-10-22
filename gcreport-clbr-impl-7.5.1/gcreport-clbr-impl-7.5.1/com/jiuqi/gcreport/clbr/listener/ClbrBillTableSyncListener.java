/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableFinishedEvent
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.listener;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableFinishedEvent;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClbrBillTableSyncListener
implements ApplicationListener<DeployTableFinishedEvent> {
    private Logger logger = LoggerFactory.getLogger(ClbrBillTableSyncListener.class);
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesignDataModelService designDataModelService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void onApplicationEvent(DeployTableFinishedEvent event) {
        DesignTableModelDefine deleteBillTableDefine;
        TableModelDefine billTableDefine = null;
        try {
            List billTableDefines = this.dataModelService.getTableModelDefinesByIds((Collection)event.getTableParams().getTable().getRunTimeKeys());
            for (TableModelDefine tableModelDefine : billTableDefines) {
                if (!"GC_CLBR_BILL".equals(tableModelDefine.getCode())) continue;
                billTableDefine = tableModelDefine;
                break;
            }
            deleteBillTableDefine = this.designDataModelService.getTableModelDefineByCode("GC_CLBR_BILL_DELETE");
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38\u3002", e);
            return;
        }
        if (Objects.isNull(billTableDefine)) {
            return;
        }
        String billTableId = billTableDefine.getID();
        String deleteBillTableId = deleteBillTableDefine.getID();
        this.syncTable(billTableId, deleteBillTableId);
        try {
            this.dataModelDeployService.deployTable(deleteBillTableId);
        }
        catch (Exception e) {
            this.logger.error("\u534f\u540c\u65b0\u589e\u6269\u5c55\u5217\u53d1\u5e03\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u534f\u540c\u65b0\u589e\u6269\u5c55\u5217\u53d1\u5e03\u5931\u8d25");
        }
    }

    public void syncTable(String billTableId, String deleteBillTableId) {
        List runTimeTableFields = this.dataModelService.getColumnModelDefinesByTable(billTableId);
        List designTableFields = this.designDataModelService.getColumnModelDefinesByTable(deleteBillTableId);
        Map deleteBillTableFieldsMap = designTableFields.stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity()));
        Map billTableFieldsMap = runTimeTableFields.stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity()));
        billTableFieldsMap.forEach((billTableFieldName, billTableFieldDefine) -> {
            DesignColumnModelDefineImpl designColumnModelDefine = new DesignColumnModelDefineImpl();
            BeanUtils.copyProperties(billTableFieldDefine, designColumnModelDefine);
            designColumnModelDefine.setTableID(deleteBillTableId);
            designColumnModelDefine.setCatagory("GC_CLBR_BILL_DELETE");
            if (deleteBillTableFieldsMap.containsKey(billTableFieldName)) {
                String deleteItemId = ((DesignColumnModelDefine)deleteBillTableFieldsMap.get(billTableFieldName)).getID();
                designColumnModelDefine.setID(deleteItemId);
                designColumnModelDefine.setOrder(((DesignColumnModelDefine)deleteBillTableFieldsMap.get(billTableFieldName)).getOrder());
                this.designDataModelService.updateColumnModelDefine((DesignColumnModelDefine)designColumnModelDefine);
            } else {
                designColumnModelDefine.setID(UUIDUtils.getKey());
                designColumnModelDefine.setOrder((double)OrderGenerator.newOrderID());
                try {
                    this.designDataModelService.insertColumnModelDefine((DesignColumnModelDefine)designColumnModelDefine);
                }
                catch (ModelValidateException e) {
                    this.logger.error("\u534f\u540c\u65b0\u589e\u6269\u5c55\u5217\u5931\u8d25", e);
                    throw new BusinessRuntimeException("\u534f\u540c\u65b0\u589e\u6269\u5c55\u5217\u5931\u8d25");
                }
            }
        });
    }
}

