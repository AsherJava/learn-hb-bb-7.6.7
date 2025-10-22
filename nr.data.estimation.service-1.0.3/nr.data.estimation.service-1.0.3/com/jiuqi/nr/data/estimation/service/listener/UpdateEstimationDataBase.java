/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.listener;

import com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper;
import com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent;
import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UpdateEstimationDataBase
implements ApplicationListener<DataTableDeployEvent> {
    @Resource
    private IDataSchemeSubDatabaseHelper schemeSubDatabaseHelper;

    @Override
    public void onApplicationEvent(DataTableDeployEvent event) {
        String dataSchemeKey = event.getSource().getDataSchemeKey();
        if (this.schemeSubDatabaseHelper.existSubDatabase(dataSchemeKey, "_DE_")) {
            this.schemeSubDatabaseHelper.deleteSubDatabase(dataSchemeKey, "_DE_", false);
            this.schemeSubDatabaseHelper.createSubDatabase(dataSchemeKey, "_DE_");
        }
    }
}

