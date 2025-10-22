/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.enums.RangeType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO
 *  com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.va.basedata.filter.BiSyntaxBaseDataFilter
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.inputdata.function.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.enums.RangeType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO;
import com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.va.basedata.filter.BiSyntaxBaseDataFilter;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class GcSubjectAllocationUtils {
    public static final String HBDW = "HBDW";

    public static List<GcBaseData> listBaseDataByFilter(String tableCode, Map<String, Object> fieldValues) {
        PageVO list;
        if (StringUtils.isEmpty((String)tableCode)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6bdb\u5229\u7387\u57fa\u7840\u6570\u636e\u8868\u540d\u4e3a\u7a7a");
        }
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableCode);
        baseDataDTO.setRangeType(RangeType.ALL_CHILDREN_WITH_SELF);
        baseDataDTO.setAuthType(AuthType.NONE);
        BaseDataDTO vaBaseDataDTO = BaseDataObjConverter.convert((GcBaseDataDO)baseDataDTO);
        String filterExpress = GcSubjectAllocationUtils.getExpressionStr(fieldValues);
        if (!StringUtils.isEmpty((String)filterExpress)) {
            BiSyntaxBaseDataFilter.applyFilter((BaseDataDTO)vaBaseDataDTO, (String)filterExpress);
        }
        if ((list = ((BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class)).list(vaBaseDataDTO)) == null || list.getRows().size() < 1) {
            return Collections.emptyList();
        }
        List<GcBaseData> result = list.getRows().stream().filter(Objects::nonNull).map(vabasedataDO -> BaseDataObjConverter.convert((BaseDataDO)vabasedataDO)).collect(Collectors.toList());
        return result;
    }

    private static String getExpressionStr(Map<String, Object> fieldValues) {
        String filterExpress = "";
        if (!fieldValues.isEmpty()) {
            StringBuffer expression = new StringBuffer();
            for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
                expression.append(entry.getKey()).append("=");
                Object value = entry.getValue();
                if (value instanceof Number) {
                    expression.append(entry.getValue());
                } else {
                    expression.append("'").append(entry.getValue()).append("'");
                }
                expression.append(" and ");
            }
            String expressionStr = expression.toString();
            filterExpress = expressionStr.substring(0, expressionStr.lastIndexOf(" and "));
        }
        return filterExpress;
    }

    public static Map<String, Object> getBaseDataFieldValues(String filters, InputDataEO inputData, Map<String, String> mergeUnitCode) {
        if (StringUtils.isEmpty((String)(filters = filters.trim()))) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6bdb\u5229\u7387\u6761\u4ef6\u4e3a\u7a7a");
        }
        String[] filter = filters.split(",");
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        for (String fields : filter) {
            String[] fieldCode = fields.split("=");
            String inputDataFieldCode = fieldCode[0];
            String baseDataFieldCode = fieldCode[1];
            if (StringUtils.isEmpty((String)inputDataFieldCode) || StringUtils.isEmpty((String)baseDataFieldCode)) {
                throw new BusinessRuntimeException("\u89e3\u6790\u6bdb\u5229\u7387\u6761\u4ef6\u5f02\u5e38\uff1a" + filters);
            }
            if (HBDW.equals(inputDataFieldCode.toUpperCase())) {
                mergeUnitCode.put(baseDataFieldCode.toUpperCase(), String.valueOf(inputData.getFieldValue(HBDW)));
                continue;
            }
            fieldValues.put(baseDataFieldCode.toUpperCase(), inputData.getFieldValue(inputDataFieldCode));
        }
        return fieldValues;
    }

    public static InputDataEO getInputData(QueryContext queryContext, List<String> parents) {
        InputDataEO inputData = GcSubjectAllocationUtils.getDefaultTableEntity(queryContext);
        InputDataEO convertInputData = new InputDataEO();
        BeanUtils.copyProperties((Object)inputData, (Object)convertInputData);
        HashMap fieldValues = new HashMap();
        fieldValues.putAll(inputData.getFields());
        convertInputData.resetFields(fieldValues);
        String cateGory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)inputData.getTaskId());
        YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(inputData.getPeriod()));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(cateGory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitOrg = orgTool.getOrgByCode(inputData.getOrgCode());
        GcOrgCacheVO oppUnitOrg = orgTool.getOrgByCode(inputData.getOppUnitId());
        GcOrgCacheVO commonUnit = orgTool.getCommonUnit(unitOrg, oppUnitOrg);
        if (Objects.isNull(commonUnit)) {
            throw new BusinessRuntimeException("\u5185\u90e8\u8868\u672c\u5bf9\u65b9\u5355\u4f4d\u7684\u5171\u540c\u4e0a\u7ea7\u4e3a\u7a7a\uff0c\u672c\u65b9\u5355\u4f4d=" + unitOrg.getCode() + " \u5bf9\u65b9\u5355\u4f4d\uff1a" + oppUnitOrg.getCode());
        }
        convertInputData.addFieldValue(HBDW, commonUnit.getCode());
        parents.addAll(Arrays.stream(commonUnit.getParents()).collect(Collectors.toList()));
        if (OrientEnum.D.getValue().equals(inputData.getDc())) {
            return convertInputData;
        }
        convertInputData.setMdOrg(oppUnitOrg.getCode());
        convertInputData.setOrgCode(oppUnitOrg.getCode());
        convertInputData.setOppUnitId(unitOrg.getCode());
        return convertInputData;
    }

    private static InputDataEO getDefaultTableEntity(QueryContext queryContext) {
        GcReportSimpleExecutorContext exceutorContext;
        InputDataEO entity = null;
        if (queryContext.getExeContext() instanceof GcReportSimpleExecutorContext && (exceutorContext = (GcReportSimpleExecutorContext)queryContext.getExeContext()) instanceof GcReportExceutorContext) {
            List inputDatas = ((GcReportExceutorContext)exceutorContext).getInputDatas();
            entity = !CollectionUtils.isEmpty((Collection)inputDatas) ? GcSubjectAllocationUtils.getDefaultTableEntity(inputDatas) : exceutorContext.getData();
        }
        if (Objects.isNull(entity) || !(entity instanceof InputDataEO)) {
            throw new BusinessRuntimeException("\u975e\u5185\u90e8\u8868\u516c\u5f0f\u4e0d\u5141\u8bb8\u4f7f\u7528\u6bdb\u5229\u7387");
        }
        return entity;
    }

    private static InputDataEO getDefaultTableEntity(List<? extends AbstractFieldDynamicDeclarator> inputDatas) {
        for (AbstractFieldDynamicDeclarator abstractFieldDynamicDeclarator : inputDatas) {
            if (!(abstractFieldDynamicDeclarator instanceof InputDataEO)) continue;
            InputDataEO inputData = (InputDataEO)abstractFieldDynamicDeclarator;
            if (!OrientEnum.D.getValue().equals(inputData.getDc())) continue;
            return (InputDataEO)abstractFieldDynamicDeclarator;
        }
        return (InputDataEO)((Object)inputDatas.stream().findFirst().get());
    }
}

