/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployListener
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource
 *  com.jiuqi.nr.subdatabase.controller.SubDataBaseController
 *  com.jiuqi.nr.subdatabase.service.SubDataBaseService
 */
package com.jiuqi.nr.batch.summary.database.listener;

import com.jiuqi.nr.batch.summary.database.service.GatherSubDataBaseService;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployListener;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseController;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeDeployListenerImpl
implements DataSchemeDeployListener {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeDeployListenerImpl.class);
    @Autowired
    private GatherSubDataBaseService gatherSubDataBaseService;
    @Autowired
    private SubDataBaseController subDataBaseController;
    @Autowired
    private SubDataBaseService subDataBaseService;

    public void onDataSchemeDeploy(DataSchemeDeploySource source) {
    }
}

