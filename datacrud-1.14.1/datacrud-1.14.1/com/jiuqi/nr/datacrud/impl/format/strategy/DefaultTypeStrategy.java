/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;

public class DefaultTypeStrategy
implements TypeFormatStrategy {
    @Override
    public String format(DataLinkDefine link, DataField field, AbstractData abstractData) {
        if (abstractData == null || abstractData.isNull) {
            return "";
        }
        return abstractData.getAsString();
    }

    @Override
    public String format(DataLinkDefine link, IFMDMAttribute fmAttribute, AbstractData abstractData) {
        return this.format(link, (DataField)null, abstractData);
    }

    @Override
    public String format(IMetaData metaData, AbstractData abstractData) {
        IFMDMAttribute fmAttribute = metaData.getFmAttribute();
        if (fmAttribute != null) {
            return this.format(metaData.getDataLinkDefine(), fmAttribute, abstractData);
        }
        return this.format(metaData.getDataLinkDefine(), metaData.getDataField(), abstractData);
    }
}

