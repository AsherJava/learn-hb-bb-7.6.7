/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SQLParamQueryListener
implements ISQLQueryListener {
    private IDSContext context;

    public SQLParamQueryListener(IDSContext context) {
        this.context = context;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ISQLQueryListener.ParamInfo findParam(String name) throws SyntaxException {
        IParameterEnv parameterEnv;
        String range;
        String paramName;
        int index = name.indexOf(46);
        if (index != -1) {
            paramName = name.substring(0, index);
            range = name.substring(index + 1, name.length());
        } else {
            paramName = name;
            range = null;
        }
        try {
            parameterEnv = this.context.getEnhancedParameterEnv();
        }
        catch (ParameterException e1) {
            throw new SyntaxException(e1.getMessage(), (Throwable)e1);
        }
        ParameterModel model = parameterEnv.getParameterModelByName(paramName);
        if (model == null) {
            return null;
        }
        int dataType = DataType.INTEGER.value() == model.getDataType() ? DataType.DOUBLE.value() : model.getDataType();
        try {
            void var10_19;
            ParameterResultset resultVal = parameterEnv.getValue(name);
            List list = resultVal.getValueAsList();
            if (model.isRangeParameter()) {
                if (list.isEmpty()) {
                    Object var10_12 = null;
                    return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
                } else if (range == null) {
                    List list2 = list;
                    return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
                } else if ("min".equalsIgnoreCase(range)) {
                    Object e = list.get(0);
                    return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
                } else {
                    if (!"max".equalsIgnoreCase(range)) throw new SyntaxException("\u4e0d\u652f\u6301\u7684\u8bed\u6cd5:" + name);
                    Object e = list.get(0);
                }
                return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
            } else if (model.getSelectMode() == ParameterSelectMode.MUTIPLE) {
                ArrayData array = new ArrayData(dataType, list.size());
                for (int i = 0; i < list.size(); ++i) {
                    array.set(i, list.get(i));
                }
                ArrayData arrayData = array;
                dataType = 11;
                return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
            } else if (list == null || list.size() == 0) {
                Object var10_17 = null;
                return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
            } else {
                Object e = list.get(0);
            }
            return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_19);
        }
        catch (ParameterException e) {
            throw new SyntaxException("\u83b7\u53d6\u53c2\u6570\u4fe1\u606f\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    private Object formatValue(String paramName, DataType dataType, Object paramValue) {
        if (paramValue instanceof MemoryDataSet) {
            MemoryDataSet dataset = (MemoryDataSet)paramValue;
            if (dataset.size() == 0) {
                throw new IllegalArgumentException("\u53c2\u6570\u53d6\u503c\u4e3a\u7a7a\uff01\u53c2\u6570\u6807\u8bc6\uff1a" + paramName);
            }
            if (dataset.size() > 1) {
                throw new IllegalArgumentException("SQL\u8bed\u53e5\u4e2d\u53ea\u652f\u6301\u5355\u503c\u53c2\u6570\uff01\u53c2\u6570\u6807\u8bc6\uff1a" + paramName);
            }
            DataRow row = dataset.get(0);
            return row.getValue(0);
        }
        switch (dataType) {
            case INTEGER: 
            case BOOLEAN: 
            case STRING: 
            case DOUBLE: {
                return paramValue;
            }
        }
        if (DataType.DATETIME.equals((Object)dataType)) {
            Date date;
            if (paramValue instanceof Calendar) {
                return paramValue;
            }
            if (paramValue == null) {
                throw new IllegalArgumentException("\u53c2\u6570\u53d6\u503c\u4e3a\u7a7a\uff01\u53c2\u6570\u6807\u8bc6\uff1a" + paramName);
            }
            String[] datetime = ((String)paramValue).split(";");
            if (datetime.length != 3) {
                throw new IllegalArgumentException("\u53c2\u6570\u53d6\u503c\u683c\u5f0f\u4e0d\u5408\u6cd5\uff01\u53c2\u6570\u6807\u8bc6\uff1a" + paramName);
            }
            String dateStr = datetime[0] + (datetime[1].length() == 1 ? "0" + datetime[1] : datetime[1]) + (datetime[2].length() == 1 ? "0" + datetime[2] : datetime[2]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                date = dateFormat.parse(dateStr);
            }
            catch (ParseException e) {
                throw new IllegalArgumentException("\u683c\u5f0f\u5316\u65e5\u671f\u51fa\u9519\uff1a" + dateStr);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
        throw new IllegalArgumentException("\u53c2\u6570\u53d6\u503c\u4e3a\u7a7a\uff01\u53c2\u6570\u6807\u8bc6\uff1a" + paramName);
    }
}

