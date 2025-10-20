/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.bill.executor;

import com.jiuqi.dc.bill.storage.DcAgeUnclearedBillStorage;
import com.jiuqi.dc.bill.storage.DcVoucherBillStorage;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Objects;
import javax.sql.DataSource;

public class InitPeriodChange
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        this.BillChange(client, DcVoucherBillStorage.getCreateDataMode(null), "DC_BILL_VOUCHER");
        this.BillChange(client, DcAgeUnclearedBillStorage.getCreateDataMode(null), "DC_BILL_AGEUNCLEARED");
    }

    private void BillChange(DataModelClient client, DataModelDO origalDataModel, String name) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(name);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        if (null == dataModelDO) {
            return;
        }
        DataModelColumn dataModelColumn = origalDataModel.getColumns().stream().filter(e -> "ACCTPERIOD".equalsIgnoreCase(e.getColumnName())).findFirst().orElse(null);
        if (Objects.nonNull(dataModelColumn)) {
            DataModelColumn dataModelColumn1 = dataModelDO.getColumns().stream().filter(e -> "ACCTPERIOD".equalsIgnoreCase(e.getColumnName())).findFirst().orElse(null);
            if (Objects.nonNull(dataModelColumn1)) {
                dataModelColumn1.setMapping(dataModelColumn.getMapping());
                dataModelColumn1.setMappingType(dataModelColumn.getMappingType());
            }
            client.push(dataModelDO);
        }
    }
}

