/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.multcheck2.listener;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.multcheck2.service.IMCTableService;
import com.jiuqi.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DeployMCTable
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DeployMCTable.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IMCTableService mcTableService;

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        String dataSchemeKey = event.getSource().getDataSchemeKey();
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return;
        }
        DesignDataScheme designScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (designScheme == null) {
            try {
                this.mcTableService.dropResultTable(event.getSource().getDataScheme());
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u6570\u636e\u65b9\u6848\u65f6\u5220\u9664\u7ed3\u679c\u8868\u5f02\u5e38\uff0c\u6570\u636e\u65b9\u6848\uff1a" + designScheme.getTitle() + "[" + designScheme.getCode() + "]", e);
            }
        } else {
            DataScheme runScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            try {
                this.mcTableService.createResultTable(runScheme);
            }
            catch (Exception e) {
                logger.error("\u53d1\u5e03\u6570\u636e\u65b9\u6848\u65f6\u521b\u5efa\u7ed3\u679c\u8868\u5f02\u5e38\uff0c\u6570\u636e\u65b9\u6848\uff1a" + runScheme.getTitle() + "[" + runScheme.getCode() + "]", e);
            }
        }
    }
}

