/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.enumcheck.deploy;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.enumcheck.deploy.EnumCheckObserver;
import java.util.List;
import javax.sql.DataSource;

public class DeployEnumTableExecutor
implements CustomClassExecutor {
    private IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private EnumCheckObserver enumCheckObserver = (EnumCheckObserver)SpringBeanUtils.getBean(EnumCheckObserver.class);

    public void execute(DataSource dataSource) throws Exception {
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        for (TaskDefine allTaskDefine : allTaskDefines) {
            this.enumCheckObserver.getDeployTable(allTaskDefine.getKey());
        }
    }
}

