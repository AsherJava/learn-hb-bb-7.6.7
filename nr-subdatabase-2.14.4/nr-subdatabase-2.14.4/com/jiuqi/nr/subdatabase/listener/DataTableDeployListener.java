/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeploySource
 */
package com.jiuqi.nr.subdatabase.listener;

import com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent;
import com.jiuqi.nr.datascheme.api.event.DataTableDeploySource;
import com.jiuqi.nr.subdatabase.facade.DataTableDDLInfo;
import com.jiuqi.nr.subdatabase.facade.impl.DataTableDDLInfoImpl;
import com.jiuqi.nr.subdatabase.service.DataTableDDLInfoService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name={"jiuqi.nr.subdatabase.listen"}, havingValue="true")
public class DataTableDeployListener
implements ApplicationListener<DataTableDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DataTableDeployListener.class);
    @Autowired
    DataTableDDLInfoService dataTableDDLInfoService;

    @Override
    public void onApplicationEvent(DataTableDeployEvent event) {
        DataTableDeploySource source = event.getSource();
        boolean executedDDL = source.isExecutedDDL();
        if (executedDDL) {
            try {
                DataTableDDLInfo dataTableDDLInfo = this.dataTableDDLInfoService.queryInfoBySKAndTMK(source.getDataSchemeKey(), source.getTableModelKey());
                if (dataTableDDLInfo != null) {
                    dataTableDDLInfo.setSyncOrder(dataTableDDLInfo.getSyncOrder() + 1);
                    dataTableDDLInfo.setDDLTime(new Date());
                    this.dataTableDDLInfoService.update(dataTableDDLInfo);
                } else {
                    DataTableDDLInfoImpl info = new DataTableDDLInfoImpl();
                    info.setDataSchemeKey(source.getDataSchemeKey());
                    info.setTableModelKey(source.getTableModelKey());
                    info.setDDLTime(new Date());
                    info.setSyncOrder(0);
                    this.dataTableDDLInfoService.insert(info);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

