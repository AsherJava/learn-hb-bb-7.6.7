/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.api.IDelTableDataService;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataPublishDelTableDataService
implements IDelTableDataService {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    public boolean deleteDataByRowKey(String formSchemeKey, DimensionValueSet masterKey) {
        return false;
    }

    @Override
    public void truncateTable(String formSchemeKey) {
    }

    @Override
    public void clearTableData(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey is must not be null!");
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        String tableCode = TableConsts.getSysTableName("NR_DATAPUBLISH_%s", dataScheme.getBizCode());
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableCode);
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        DimensionValueSet masterKey = new DimensionValueSet();
        this.nvwaDataEngineQueryUtil.delete(masterKey, tableCode, fields);
    }
}

