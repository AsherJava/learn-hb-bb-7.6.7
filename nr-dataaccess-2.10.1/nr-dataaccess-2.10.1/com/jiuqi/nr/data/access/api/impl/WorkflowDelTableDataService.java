/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.api.IDelTableDataService;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Deprecated
public class WorkflowDelTableDataService
implements IDelTableDataService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    public boolean deleteDataByRowKey(String formSchemeKey, DimensionValueSet masterKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        masterKey = DimensionValueSetUtil.filterDimensionValueSet(masterKey, "PROCESSKEY");
        String tableName = this.dataAccesslUtil.getTableName(formScheme, "NR_WORKFLOW_%s");
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        int del = this.nvwaDataEngineQueryUtil.delete(masterKey, tableName, fields);
        return del != 0;
    }

    @Override
    public void truncateTable(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableName = this.dataAccesslUtil.getTableName(formScheme, "NR_WORKFLOW_%s");
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        DimensionValueSet masterKey = new DimensionValueSet();
        this.nvwaDataEngineQueryUtil.delete(masterKey, tableName, fields);
    }

    @Override
    public void clearTableData(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey is must not be null!");
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        String tableCode = TableConsts.getSysTableName("NR_WORKFLOW_%s", dataScheme.getBizCode());
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableCode);
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        DimensionValueSet masterKey = new DimensionValueSet();
        this.nvwaDataEngineQueryUtil.delete(masterKey, tableCode, fields);
    }
}

