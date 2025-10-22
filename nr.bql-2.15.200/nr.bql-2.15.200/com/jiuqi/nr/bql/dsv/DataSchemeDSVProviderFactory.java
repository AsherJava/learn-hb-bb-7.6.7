/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.intf.IDSVProvider
 *  com.jiuqi.bi.adhoc.ext.intf.INodeDataProvider
 *  com.jiuqi.bi.adhoc.ext.intf.IProviderFactory
 */
package com.jiuqi.nr.bql.dsv;

import com.jiuqi.bi.adhoc.ext.intf.IDSVProvider;
import com.jiuqi.bi.adhoc.ext.intf.INodeDataProvider;
import com.jiuqi.bi.adhoc.ext.intf.IProviderFactory;
import com.jiuqi.nr.bql.dsv.DataSchemeDSVProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeDSVProviderFactory
implements IProviderFactory {
    @Autowired
    private DataSchemeDSVProvider dataSchemeDSVProvider;

    public IDSVProvider createDSVProvider() {
        return this.dataSchemeDSVProvider;
    }

    public INodeDataProvider createNodeDataProvider() {
        return null;
    }

    public String getDSVType() {
        return this.dataSchemeDSVProvider.getDSVType();
    }

    public String getDSVTypeName() {
        return "\u62a5\u8868\u6570\u636e\u65b9\u6848";
    }
}

