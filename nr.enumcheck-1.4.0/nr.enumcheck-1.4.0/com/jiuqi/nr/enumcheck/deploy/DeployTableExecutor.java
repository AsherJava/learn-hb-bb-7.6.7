/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.integritycheck.deploy.IntegrityCheckObserver
 */
package com.jiuqi.nr.enumcheck.deploy;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.enumcheck.deploy.EnumCheckObserver;
import com.jiuqi.nr.integritycheck.deploy.IntegrityCheckObserver;
import java.util.List;
import javax.sql.DataSource;

public class DeployTableExecutor
implements CustomClassExecutor {
    private IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private EnumCheckObserver enumCheckObserver = (EnumCheckObserver)SpringBeanUtils.getBean(EnumCheckObserver.class);
    private IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
    private IntegrityCheckObserver integrityCheckObserver = (IntegrityCheckObserver)SpringBeanUtils.getBean(IntegrityCheckObserver.class);

    public void execute(DataSource dataSource) throws Exception {
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        for (TaskDefine allTaskDefine : allTaskDefines) {
            this.enumCheckObserver.getDeployTable(allTaskDefine.getKey());
        }
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            this.integrityCheckObserver.publishTable(dataScheme);
        }
    }
}

