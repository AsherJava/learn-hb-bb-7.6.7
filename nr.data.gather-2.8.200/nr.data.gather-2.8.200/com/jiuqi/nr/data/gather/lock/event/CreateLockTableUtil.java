/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.model.ColumnInfo
 *  com.jiuqi.nr.data.access.model.LogicTableInfo
 *  com.jiuqi.nr.data.access.util.LogicTableUpdateUtil
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.gather.lock.event;

import com.jiuqi.nr.data.access.model.ColumnInfo;
import com.jiuqi.nr.data.access.model.LogicTableInfo;
import com.jiuqi.nr.data.access.util.LogicTableUpdateUtil;
import com.jiuqi.nr.data.gather.common.DataGatherConsts;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateLockTableUtil {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private LogicTableUpdateUtil logicTableUpdateUtil;
    @Autowired
    private DesignDataModelService designDataModelService;

    public List<String> initLockTableDeploy(String dataSchemeKey, DataScheme dataScheme, boolean noDDL) throws Exception {
        DesignDataScheme queryDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemeCode = queryDataScheme == null ? dataScheme.getBizCode() : queryDataScheme.getBizCode();
        String tableName = DataGatherConsts.getLockTableName(schemeCode);
        if (queryDataScheme == null) {
            this.logicTableUpdateUtil.deleteAndDeploy(tableName);
            return new ArrayList<String>();
        }
        String title = DataGatherConsts.getLockTableTitle(schemeCode);
        LogicTableInfo logicTableInfo = new LogicTableInfo(tableName);
        logicTableInfo.setDescription(title);
        logicTableInfo.setTitle(title);
        logicTableInfo.getPrimaryColumns().addAll(this.logicTableUpdateUtil.initPrimaryColumns(dataSchemeKey, false));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("DATA_TABLE_KEY", "DATA_TABLE_KEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("SERVER_NAME", "SERVER_NAME", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("CREATE_TIME", "CREATE_TIME", ColumnModelType.DATETIME, 0, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("HEART_TIME", "HEART_TIME", ColumnModelType.DATETIME, 0, "", false));
        return this.logicTableUpdateUtil.createAndDeployTable(logicTableInfo, noDDL);
    }

    public List<String> getTableIds(String dataSchemeKey) {
        ArrayList<String> list = new ArrayList<String>();
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemeCode = dataScheme.getBizCode();
        ArrayList<String> tableNames = new ArrayList<String>();
        tableNames.add(DataGatherConsts.getLockTableName(schemeCode));
        for (String tableName : tableNames) {
            DesignTableModelDefine designTableDefine = this.designDataModelService.getTableModelDefineByCode(tableName);
            if (designTableDefine == null) continue;
            String id = designTableDefine.getID();
            list.add(id);
        }
        return list;
    }
}

