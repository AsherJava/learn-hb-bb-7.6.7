/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.intf.IDSVProvider
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.ext.model.UpdateInfo
 */
package com.jiuqi.nr.bql.dsv;

import com.jiuqi.bi.adhoc.ext.intf.IDSVProvider;
import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.ext.model.UpdateInfo;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeDSVProvider
implements IDSVProvider {
    @Autowired
    private DSVAdapter dsvAdapter;

    public DSV getDSV(String dsvName) throws Exception {
        DSV dsv = this.dsvAdapter.getDSV(dsvName);
        return dsv;
    }

    public String getDSVType() {
        return "nr.datascheme";
    }

    public List<UpdateInfo> getUpdateInfoList() throws Exception {
        return this.dsvAdapter.queryAllUpdateInfos();
    }

    public List<UpdateInfo> getUpdateInfoList(String[] dsvNames) throws Exception {
        return this.dsvAdapter.queryUpdateInfos(dsvNames);
    }
}

