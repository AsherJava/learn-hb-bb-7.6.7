/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 */
package com.jiuqi.nr.annotation.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.annotation.deploy.FormCellAnnotationObserver;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradeAnnotationTypeExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(UpgradeAnnotationTypeExecutor.class);
    private IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private FormCellAnnotationObserver formCellAnnotationObserver = (FormCellAnnotationObserver)SpringBeanUtils.getBean(FormCellAnnotationObserver.class);

    public void execute(DataSource dataSource) throws Exception {
        try {
            List taskDefines = this.runTimeViewController.listAllTask();
            this.formCellAnnotationObserver.manuaDdeployAnnotationTypeTable(taskDefines);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

