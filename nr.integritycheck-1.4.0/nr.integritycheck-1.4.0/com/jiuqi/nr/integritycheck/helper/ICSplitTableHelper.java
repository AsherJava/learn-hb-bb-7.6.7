/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.integritycheck.helper;

import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ICSplitTableHelper {
    @Autowired
    private SubDatabaseTableNamesProvider subTableNamesProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public String getICRSplitTableName(DataScheme dataScheme) {
        String tableName = "NR_ICR_" + dataScheme.getBizCode();
        if (null != this.subTableNamesProvider) {
            List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
            for (TaskDefine taskDefine : allTaskDefines) {
                String libraryTableName;
                if (!taskDefine.getDataScheme().equals(dataScheme.getKey()) || (libraryTableName = this.subTableNamesProvider.getSubDatabaseTableName(taskDefine.getKey(), tableName)).isEmpty()) continue;
                return libraryTableName;
            }
        }
        return tableName;
    }

    public String getICDSplitTableName(DataScheme dataScheme) {
        String tableName = "NR_ICD_" + dataScheme.getBizCode();
        if (null != this.subTableNamesProvider) {
            List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
            for (TaskDefine taskDefine : allTaskDefines) {
                String libraryTableName;
                if (!taskDefine.getDataScheme().equals(dataScheme.getKey()) || (libraryTableName = this.subTableNamesProvider.getSubDatabaseTableName(taskDefine.getKey(), tableName)).isEmpty()) continue;
                return libraryTableName;
            }
        }
        return tableName;
    }
}

