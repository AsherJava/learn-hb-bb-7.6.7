/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.budget.components.ProductNameUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.component.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.budget.component.exception.DataPeriodConvertException;
import com.jiuqi.budget.components.ProductNameUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public final class IsMdTrueCommon
extends Function {
    private static final List<IParameter> PARAMETERS = new ArrayList<IParameter>(4);

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String tableName = (String)parameters.get(0).evaluate(context);
        String wldcardType = (String)parameters.get(1).evaluate(context);
        String fieldCode = (String)parameters.get(2).evaluate(context);
        String codes = (String)parameters.get(3).evaluate(context);
        return this.getIsMdTrueResult(tableName, wldcardType, fieldCode, codes, context);
    }

    private Object getIsMdTrueResult(String tableName, String wldcardType, String fieldCode, String codes, IContext context) {
        boolean result = false;
        if (context instanceof QueryContext) {
            String data = ((QueryContext)context).getDimensionValue(tableName.toUpperCase()).toString();
            Object dataTime = ((QueryContext)context).getDimensionValue("DATATIME");
            String unitCode = ((QueryContext)context).getDimensionValue("MD_ORG").toString();
            if (unitCode == null && NpContextHolder.getContext().getOrganization() != null) {
                unitCode = NpContextHolder.getContext().getOrganization().getCode();
            }
            List<String> oldCodeList = Arrays.asList(codes.split(","));
            List<String> codeList = oldCodeList.stream().map(code -> {
                if ("true".equalsIgnoreCase((String)code)) {
                    code = "1";
                } else if ("false".equalsIgnoreCase((String)code)) {
                    code = "0";
                }
                return code;
            }).collect(Collectors.toList());
            switch (wldcardType.toUpperCase()) {
                case "IN": {
                    result = this.getResultInList(codeList, data);
                    break;
                }
                case "EXCEPT": {
                    result = !this.getResultInList(codeList, data);
                    break;
                }
                case "LEAF": {
                    result = this.getResultLeaf(tableName, data, dataTime, unitCode);
                    break;
                }
                case "NOLEAF": {
                    result = !this.getResultLeaf(tableName, data, dataTime, unitCode);
                    break;
                }
                case "ATTR": {
                    result = this.getResultAttr(tableName, data, dataTime, unitCode, codeList, fieldCode);
                    break;
                }
                default: {
                    result = false;
                }
            }
        }
        return result;
    }

    private boolean getResultAttr(String tableName, String data, Object dataTime, String unitCode, List<String> codeList, String fieldCode) {
        Object baseDataObject;
        DefaultPeriodAdapter periodAdapter;
        if (IsMdTrueCommon.isOrg(tableName)) {
            OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname(tableName);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgDTO.setCode(data);
            if (dataTime != null) {
                periodAdapter = new DefaultPeriodAdapter();
                try {
                    orgDTO.setVersionDate(periodAdapter.getPeriodDateRegion(dataTime.toString())[0]);
                }
                catch (ParseException e) {
                    throw new DataPeriodConvertException(e.getMessage());
                }
            }
            baseDataObject = orgDataClient.get(orgDTO);
        } else {
            BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
            BaseDataDTO param = new BaseDataDTO();
            param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            param.setTableName(tableName);
            param.setUnitcode(unitCode);
            param.setAuthType(BaseDataOption.AuthType.NONE);
            param.setObjectcode(data);
            if (dataTime != null) {
                periodAdapter = new DefaultPeriodAdapter();
                try {
                    param.setVersionDate(periodAdapter.getPeriodDateRegion(dataTime.toString())[0]);
                }
                catch (ParseException e) {
                    throw new DataPeriodConvertException(e.getMessage());
                }
            }
            baseDataObject = (Map)baseDataClient.list(param).getRows().get(0);
        }
        Map<String, Object> upperCaseBaseDataObject = IsMdTrueCommon.transformUpperCase((Map<String, Object>)baseDataObject);
        Object fieldVal = upperCaseBaseDataObject.get(fieldCode);
        if (fieldVal instanceof List) {
            List dataList = (List)fieldVal;
            for (String val : dataList) {
                if (!codeList.contains(val)) continue;
                return true;
            }
        }
        return codeList.contains(fieldVal = fieldVal != null ? fieldVal.toString() : "");
    }

    private boolean getResultLeaf(String tableName, String data, Object dataTime, String unitCode) {
        if (IsMdTrueCommon.isOrg(tableName)) {
            OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
            OrgDTO orgDataObjDTO = new OrgDTO();
            orgDataObjDTO.setCategoryname(tableName);
            orgDataObjDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDataObjDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
            orgDataObjDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
            orgDataObjDTO.setCode(data);
            if (dataTime != null) {
                DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
                try {
                    orgDataObjDTO.setVersionDate(periodAdapter.getPeriodDateRegion(dataTime.toString())[0]);
                }
                catch (ParseException e) {
                    throw new DataPeriodConvertException(e.getMessage());
                }
            }
            List orgDOList = orgDataClient.list(orgDataObjDTO).getRows();
            return orgDOList.isEmpty();
        }
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        BaseDataDTO param = new BaseDataDTO();
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
        param.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
        param.setTableName(tableName);
        param.setUnitcode(unitCode);
        param.setAuthType(BaseDataOption.AuthType.NONE);
        param.setObjectcode(data);
        if (dataTime != null) {
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            try {
                param.setVersionDate(periodAdapter.getPeriodDateRegion(dataTime.toString())[0]);
            }
            catch (ParseException e) {
                throw new DataPeriodConvertException(e.getMessage());
            }
        }
        List baseDataObjs = baseDataClient.list(param).getRows();
        return baseDataObjs.isEmpty();
    }

    private boolean getResultInList(List<String> codeList, String data) {
        return codeList.contains(data);
    }

    private static boolean isOrg(String str) {
        if ("MD_ORG".equals(str)) {
            return true;
        }
        return StringUtils.startsWithIgnoreCase(str, "MD_ORG_");
    }

    public String name() {
        return "IsMdTrueCommon";
    }

    public String title() {
        return "\u5224\u5b9a\u8868\u5916\u7ef4\u5ea6\u662f\u5426\u5728\u6307\u5b9a\u6761\u4ef6\u7684\u5217\u8868\u4e2d\n\u6ce8\u610f\uff1a\u516c\u5f0f\u6267\u884c\u8fc7\u7a0b\u4e2d\u9488\u5bf9\u673a\u6784\u7684\u8fc7\u6ee4\u4e0d\u4f1a\u81ea\u52a8\u5339\u914d\u4efb\u52a1\u4e0a\u7ed1\u5b9a\u7684\u673a\u6784\u7c7b\u578b\u3002" + ProductNameUtil.getProductName() + "\u9879\u76ee\u4e0d\u5efa\u8bae\u4f7f\u7528\u3002";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u901a\u914d\u76f8\u5173";
    }

    public List<IParameter> parameters() {
        return PARAMETERS;
    }

    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toUpperCase();
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }

    static {
        PARAMETERS.add((IParameter)new Parameter("\u57fa\u7840\u6570\u636e\u6807\u8bc6", 6, "\u9700\u8981\u5224\u5b9a\u7684\u8868\u5916\u7ef4\u5ea6\u6807\u8bc6", false));
        PARAMETERS.add((IParameter)new Parameter("\u8fc7\u6ee4\u6761\u4ef6", 6, "\u8fc7\u6ee4\u6761\u4ef6\u652f\u6301IN\u3001EXCEPT\u3001LEAF\u3001NOLEAF\u3001ATTR\u4e94\u79cd\u8fc7\u6ee4\u65b9\u5f0f\u3002\u5176\u4e2d\uff1a\n1\u3001IN \u5728\u8fc7\u6ee4\u503c\u8303\u56f4\u5185\n2\u3001EXCEPT \u6392\u9664\u7b49\u540c\u4e8eNOTIN \n3\u3001LEAF \u8fc7\u6ee4\u5e76\u8fd4\u56de\u6240\u6709\u53f6\u5b50\u8282\u70b9\u7684\u6570\u636e\n4\u3001NOLEAF \u8fc7\u6ee4\u5e76\u8fd4\u56de\u975e\u53f6\u5b50\u8282\u70b9\u7684\u6570\u636e \n5\u3001ATTR \u6309\u57fa\u7840\u6570\u636e\u5c5e\u6027\u8fc7\u6ee4"));
        PARAMETERS.add((IParameter)new Parameter("\u57fa\u7840\u6570\u636e\u5c5e\u6027\u5b57\u6bb5\u6807\u8bc6", 6, "\u88ab\u8fc7\u6ee4\u7684\u8868\u5916\u7ef4\u5ea6\u5c5e\u6027\u5b57\u6bb5\u6807\u8bc6\u3002\u5f53\u8fc7\u6ee4\u6761\u4ef6\u9009\u62e9ATTR\u65f6\uff0c\u6b64\u53c2\u6570\u6709\u6548\uff0c\u5426\u5219\u65e0\u6548"));
        PARAMETERS.add((IParameter)new Parameter("\u8fc7\u6ee4\u503c\u8303\u56f4", 6, "\u8fc7\u6ee4\u503c\u8303\u56f4\uff0c\u591a\u4e2a\u503c\u4e4b\u95f4\u7528\u82f1\u6587\u7684\u9017\u53f7\u5206\u9694\u3002\u598201,02"));
    }
}

