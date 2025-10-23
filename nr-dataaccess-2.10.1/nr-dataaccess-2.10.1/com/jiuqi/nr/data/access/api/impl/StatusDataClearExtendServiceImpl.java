/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.nr.data.access.api.IDelTableDataService;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusDataClearExtendServiceImpl
implements IDataSchemeDataClearExtendService {
    @Autowired(required=false)
    private List<IDelTableDataService> delTableDataServices;

    public void doClear(DataClearParamObj clearParam) {
        for (IDelTableDataService delTableDataService : this.delTableDataServices) {
            delTableDataService.clearTableData(clearParam.getDataSchemeKey());
        }
    }
}

