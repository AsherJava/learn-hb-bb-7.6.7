/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nvwa.framework.parameter.syntax;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Locale;

public class ParamNodeDataFormator
implements IDataFormator {
    private ParameterModel paramModel;
    private String language;

    public ParamNodeDataFormator(ParameterModel paramModel) {
        this.paramModel = paramModel;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Format getFormator(IContext context) throws SyntaxException {
        ParameterSelectMode mode = this.paramModel.getSelectMode();
        switch (mode) {
            case SINGLE: {
                return this.getDataShowFormat();
            }
            case MUTIPLE: 
            case RANGE: {
                return new ArrayDataFormat();
            }
        }
        return null;
    }

    private Format getDataShowFormat() {
        ParameterDataSourceManager mgr = ParameterDataSourceManager.getInstance();
        AbstractParameterDataSourceFactory factory = mgr.getFactory(this.paramModel.getDatasource().getType());
        IParameterValueFormat format = factory.createValueFormat(this.paramModel.getDatasource());
        return format.getDataShowFormat(ParamNodeDataFormator.getLocale(this.language));
    }

    private static Locale getLocale(String lang) {
        Locale locale = Locale.getDefault();
        if (StringUtils.isEmpty((String)lang)) {
            return locale;
        }
        return Locale.forLanguageTag(lang);
    }

    private class ArrayDataFormat
    extends Format {
        private static final long serialVersionUID = 1L;

        private ArrayDataFormat() {
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            throw new UnsupportedOperationException();
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            Format format = ParamNodeDataFormator.this.getDataShowFormat();
            if (obj instanceof ArrayData) {
                ArrayData d = (ArrayData)obj;
                if (!d.isEmpty()) {
                    for (Object item : d.toList()) {
                        if (item instanceof Calendar) {
                            item = ((Calendar)item).getTime();
                        }
                        if (item instanceof String) {
                            toAppendTo.append(item).append(",");
                            continue;
                        }
                        String v = format == null ? DataType.formatValue((int)0, item) : format.format(item);
                        toAppendTo.append(v).append(",");
                    }
                    toAppendTo.deleteCharAt(toAppendTo.length() - 1);
                }
            } else if (format == null) {
                toAppendTo.append(DataType.formatValue((int)0, (Object)obj));
            } else {
                if (obj instanceof Calendar) {
                    obj = ((Calendar)obj).getTime();
                }
                toAppendTo.append(format.format(obj));
            }
            return toAppendTo;
        }
    }
}

