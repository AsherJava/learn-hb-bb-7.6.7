/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.database.listener;

import com.jiuqi.nr.batch.summary.database.service.GatherDataBaseService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeDeployListener
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeDeployListener.class);
    @Autowired
    private RuntimeDataSchemeServiceImpl runtimeDataSchemeService;
    @Autowired
    private GatherDataBaseService gatherDataBaseService;

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        String dataSchemeKey = event.getSource().getDataSchemeKey();
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        try {
            if (dataScheme == null) {
                logger.info("\u6c47\u603b\u5206\u5e93\u5220\u9664\u5f00\u59cb\u3002");
                if (this.gatherDataBaseService.isExistGatherDataBase(dataSchemeKey)) {
                    this.gatherDataBaseService.delete(dataSchemeKey, true);
                }
                logger.info("\u6c47\u603b\u5206\u5e93\u5220\u9664\u7ed3\u675f\u3002");
                return;
            }
            if (!dataScheme.getGatherDB().booleanValue() && this.gatherDataBaseService.isExistGatherDataBase(dataSchemeKey)) {
                this.gatherDataBaseService.delete(dataSchemeKey, true);
            }
            if (dataScheme.getGatherDB().booleanValue() && !this.gatherDataBaseService.isExistGatherDataBase(dataSchemeKey)) {
                logger.info("\u6c47\u603b\u5206\u5e93\u521b\u5efa\u5f00\u59cb\u3002");
                this.gatherDataBaseService.creatGatherDataBase(dataSchemeKey);
                logger.info("\u6c47\u603b\u5206\u5e93\u521b\u5efa\u7ed3\u675f\u3002");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

