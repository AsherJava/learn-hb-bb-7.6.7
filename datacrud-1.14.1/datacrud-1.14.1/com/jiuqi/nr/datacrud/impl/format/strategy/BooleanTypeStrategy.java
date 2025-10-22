/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BooleanTypeStrategy
implements TypeFormatStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BooleanTypeStrategy.class);
    private static final String[] trueValues = new String[]{"true", "yes", "y", "on", "1", "\u662f", "\u221a"};
    private static final String[] falseValues = new String[]{"false", "no", "n", "off", "0", "\u5426", "\u00d7"};

    @Override
    public String format(IMetaData metaData, AbstractData abstractData) {
        IFMDMAttribute fmAttribute = metaData.getFmAttribute();
        if (fmAttribute != null) {
            return this.format(metaData.getDataLinkDefine(), fmAttribute, abstractData);
        }
        return this.format(metaData.getDataLinkDefine(), metaData.getDataField(), abstractData);
    }

    @Override
    public String format(DataLinkDefine link, DataField field, AbstractData data) {
        if (data == null || data.isNull) {
            return "";
        }
        if (data instanceof BoolData) {
            try {
                boolean asBool = data.getAsBool();
                if (asBool) {
                    return trueValues[5];
                }
                return falseValues[5];
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            if (data instanceof StringData) {
                return data.getAsString();
            }
            try {
                int intValue = data.getAsInt();
                if (intValue > 0) {
                    return trueValues[5];
                }
                return falseValues[5];
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return "";
    }

    @Override
    public String format(DataLinkDefine link, IFMDMAttribute fmAttribute, AbstractData abstractData) {
        return this.format(link, (DataField)null, abstractData);
    }
}

