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
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateTimeTypeStrategy
implements TypeFormatStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeTypeStrategy.class);

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
            long dateTime = abstractData.getAsDateTime();
            FormatProperties formatProperties = link.getFormatProperties();
            if (formatProperties == null && field != null) {
                formatProperties = field.getFormatProperties();
            }
            if (formatProperties != null && StringUtils.hasLength(pattern = formatProperties.getPattern())) {
                DateTimeFormatter formatter = DateUtils.getFormatter(pattern);
                return formatter.format(Instant.ofEpochMilli(dateTime));
            }
            return DateUtils.dateToStringTime(new Date(dateTime));
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return abstractData.getAsString();
        }
    }

    @Override
    public String format(DataLinkDefine link, IFMDMAttribute fmAttribute, AbstractData abstractData) {
        return this.format(link, (DataField)null, abstractData);
    }
}

