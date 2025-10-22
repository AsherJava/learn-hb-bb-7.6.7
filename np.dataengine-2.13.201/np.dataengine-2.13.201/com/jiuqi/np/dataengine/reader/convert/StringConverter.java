/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 */
package com.jiuqi.np.dataengine.reader.convert;

import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.encryption.common.EncryptionException;

public class StringConverter
implements IFieldDataConverter {
    private int size;
    private ColumnModelDefine field;

    public StringConverter(ColumnModelDefine field) {
        this.size = field.getPrecision();
        this.field = field;
    }

    @Override
    public Object convert(QueryContext qContext, Object value) {
        IEncryptDecryptProcesser encryptDecryptProcesser;
        if (value == null) {
            return value;
        }
        if (qContext != null && (encryptDecryptProcesser = qContext.getQueryParam().getEncryptDecryptProcesser()) != null) {
            try {
                DataModelDefinitionsCache cacheModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
                FieldDefine fieldDefine = cacheModel.getFieldDefine(this.field);
                value = encryptDecryptProcesser.decrypt(qContext, fieldDefine, value.toString());
            }
            catch (ParseException e) {
                qContext.getMonitor().exception((Exception)((Object)e));
            }
        }
        if (this.field.isMultival() && value instanceof ArrayData) {
            ArrayData array = (ArrayData)value;
            StringBuilder buff = new StringBuilder();
            for (Object o : array) {
                buff.append(o.toString()).append(";");
            }
            if (array.xSize() > 0) {
                buff.setLength(buff.length() - 1);
            }
            return buff.toString();
        }
        String formatValue = value.toString();
        if (qContext != null) {
            if (StringUtils.isNotEmpty((String)this.field.getSceneId())) {
                try {
                    formatValue = qContext.decrypt(formatValue);
                }
                catch (EncryptionException e) {
                    qContext.getMonitor().exception((Exception)((Object)e));
                }
            }
            formatValue = qContext.getMaskingData(this.field, formatValue.toString());
        }
        if (formatValue.length() > this.size) {
            formatValue = formatValue.substring(0, this.size);
        }
        return formatValue;
    }
}

