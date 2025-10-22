/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider
 */
package com.jiuqi.nr.snapshot.deploy;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.snapshot.deploy.SnapshotTableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SnapshotObserver
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotObserver.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataBaseLimitModeProvider dataBaseLimitModeProvider;

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        if (this.dataBaseLimitModeProvider.databaseLimitMode()) {
            return;
        }
        SnapshotTableUtils snapshotTableUtils = new SnapshotTableUtils();
        try {
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(event.getSource().getDataSchemeKey());
            DataScheme runTimeDataScheme = event.getSource().getDataScheme();
            if (null == dataScheme && null == runTimeDataScheme) {
                return;
            }
            if (null == dataScheme && null != runTimeDataScheme) {
                snapshotTableUtils.deleteTable(runTimeDataScheme);
                return;
            }
            snapshotTableUtils.doObserver((DataScheme)dataScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

