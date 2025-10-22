/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.common.DateUtils;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateTypeStrategy
implements TypeFormatStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DateTypeStrategy.class);

    @Override
    public String format(IMetaData metaData, AbstractData abstractData) {
        IFMDMAttribute fmAttribute = metaData.getFmAttribute();
        if (fmAttribute != null) {
            return this.format(metaData.getDataLinkDefine(), fmAttribute, abstractData);
        }
        return this.format(metaData.getDataLinkDefine(), metaData.getDataField(), abstractData);
    }

    @Override
    public String format(DataLinkDefine link, DataField field, AbstractData abstractData) {
        if (abstractData == null || abstractData.isNull) {
            return "";
        }
        if (abstractData instanceof StringData) {
            return abstractData.getAsString();
        }
        try {
            String pattern;
            Date date = abstractData.getAsDateObj();
            if (date == null) {
                return "";
            }
            FormatProperties formatProperties = link.getFormatProperties();
            if (formatProperties == null && field != null) {
                formatProperties = field.getFormatProperties();
            }
            if (formatProperties != null && StringUtils.hasLength(pattern = formatProperties.getPattern())) {
                DateTimeFormatter formatter = DateUtils.getFormatter(pattern);
                return formatter.format(date.toInstant());
            }
            return DateUtils.dateToString(date);
        }
        catch (Exception e) {
            logger.warn("\u7c7b\u578b\u8f6c\u6362\u8df3\u8fc7\uff0c\u6309\u5b57\u7b26\u7c7b\u578b\u683c\u5f0f\u5316\u3002\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return abstractData.getAsString();
        }
    }

    @Override
    public String format(DataLinkDefine link, IFMDMAttribute fmAttribute, AbstractData abstractData) {
        return this.format(link, (DataField)null, abstractData);
    }
}

