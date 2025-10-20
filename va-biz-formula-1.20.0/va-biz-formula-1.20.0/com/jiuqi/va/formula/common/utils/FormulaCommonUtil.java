/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.formula.common.utils;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class FormulaCommonUtil {
    protected static final String IDENT = "    ";

    public static List<BaseDataDO> getBasedataList(String tableName) {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(tableName);
        FormulaCommonUtil.setBaseDataQueryBaseParam(param);
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        List rows = baseDataClient.list(param).getRows();
        return rows;
    }

    public static BaseDataDO getBasedata(BaseDataDTO param) {
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        List rows = baseDataClient.list(param).getRows();
        return rows != null && rows.size() > 0 ? (BaseDataDO)rows.get(0) : null;
    }

    public static BaseDataDO getBasedataByCode(String table, String code, Map<String, Object> dimValue) {
        if (ObjectUtils.isEmpty(table) || ObjectUtils.isEmpty(code)) {
            return null;
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(table);
        if (dimValue != null && !dimValue.isEmpty()) {
            param.putAll(dimValue);
        }
        param.setCode(code);
        FormulaCommonUtil.setBaseDataQueryBaseParam(param);
        return FormulaCommonUtil.getBasedata(param);
    }

    public static BaseDataDO getBasedataByObjectCode(String table, String objectcode) {
        if (ObjectUtils.isEmpty(table) || ObjectUtils.isEmpty(objectcode)) {
            return null;
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(table);
        param.setObjectcode(objectcode);
        FormulaCommonUtil.setBaseDataQueryBaseParam(param);
        return FormulaCommonUtil.getBasedata(param);
    }

    private static void setBaseDataQueryBaseParam(BaseDataDTO param) {
        param.setStopflag(Integer.valueOf(-1));
        param.setAuthType(BaseDataOption.AuthType.NONE);
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
    }

    public static StringBuilder getBaseDes(ModelFunction model) {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT);
        model.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append(model.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        return buffer;
    }

    public static StringBuilder setParamDes(ModelFunction model, StringBuilder buffer) {
        if (buffer == null) {
            buffer = new StringBuilder(64);
        }
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        for (IParameter p : model.parameters()) {
            buffer.append(IDENT).append(p.name() + "\uff1a").append(DataType.toString((int)p.dataType())).append("\uff1b ").append(FunctionUtils.LINE_SEPARATOR);
        }
        return buffer;
    }

    public static StringBuilder setReturnParamDes(ModelFunction model, StringBuilder buffer, int type) {
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append(DataType.toExpression((int)type)).append("\uff1a").append(DataType.toString((int)type)).append("\uff1b").append(FunctionUtils.LINE_SEPARATOR);
        return buffer;
    }

    public static StringBuilder setExampleDes(ModelFunction model, StringBuilder buffer, String scene, String formula, String res) {
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append(IDENT).append(scene).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append(IDENT).append(formula).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append(IDENT).append(IDENT).append(res);
        return buffer;
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal)value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String)value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger)value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number)value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }
}

