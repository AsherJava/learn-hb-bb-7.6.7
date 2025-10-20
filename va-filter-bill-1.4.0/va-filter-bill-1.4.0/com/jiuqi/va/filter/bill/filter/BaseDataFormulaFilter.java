/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.ruler.impl.FunRefDataDefineImpl
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataFilter
 *  com.jiuqi.va.domain.basedata.BaseDataOption$FilterType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 *  org.apache.shiro.util.CollectionUtils
 */
package com.jiuqi.va.filter.bill.filter;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.ruler.impl.FunRefDataDefineImpl;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataFilter;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.filter.bill.filter.BaseDataEncryptDES;
import com.jiuqi.va.filter.bill.formula.BaseDataFormulaContext;
import com.jiuqi.va.filter.bill.formula.BaseDataFormulaExecute;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataFormulaFilter
implements BaseDataFilter {
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;

    public String getSqlCondition(BaseDataDTO param) {
        BaseDataFormulaContext context = this.getContext(param);
        return BaseDataFormulaExecute.getInstance().toSQL(param.getExpression(), context);
    }

    public void filterList(BaseDataDTO param, List<BaseDataDO> dataList) {
        BaseDataFormulaContext context = this.getContext(param);
        if (param.containsKey((Object)"decrypted")) {
            param.setExpression(BaseDataEncryptDES.decrypt(param.getExpression()));
            param.remove((Object)"decrypted");
        }
        BaseDataDO data = null;
        Boolean flag = null;
        Iterator<BaseDataDO> it = dataList.iterator();
        while (it.hasNext()) {
            data = it.next();
            IExpression expressionObj = (IExpression)context.get("transforFormula");
            if (expressionObj == null) {
                expressionObj = BaseDataFormulaExecute.getInstance().transforFormula(param.getExpression(), context);
                context.put("transforFormula", expressionObj);
            }
            context.setBaseData(data);
            flag = BaseDataFormulaExecute.getInstance().judge(context, expressionObj);
            if (flag.booleanValue()) continue;
            it.remove();
        }
    }

    private BaseDataFormulaContext getContext(BaseDataDTO param) {
        if (param.get((Object)"BaseDataFormulaContext") != null) {
            return (BaseDataFormulaContext)((Object)param.get((Object)"BaseDataFormulaContext"));
        }
        String tableName = param.getTableName();
        ArrayList<FunRefDataDefine> dataDefines = new ArrayList<FunRefDataDefine>();
        BaseDataFormulaContext context = new BaseDataFormulaContext(null, dataDefines);
        if (param.get((Object)"dummyflag") != null && (Integer)param.get((Object)"dummyflag") == 1) {
            ObjectNode defineJson = JSONUtil.parseObject((String)((String)param.get((Object)"defineStr")));
            ArrayNode columnsArr = defineJson.withArray("columns");
            List cols = JSONUtil.parseArray((String)columnsArr.toString(), DataModelColumn.class);
            for (DataModelColumn column : cols) {
                dataDefines.add((FunRefDataDefine)new FunRefDataDefineImpl(tableName, column.getColumnName(), column.getColumnType(), column));
            }
        } else {
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setTenantName(param.getTenantName());
            dataModelDTO.setName(tableName);
            DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
            List cols = dataModel.getColumns();
            Map<String, Map<String, Object>> fieldProp = this.getFieldPropsMap(dataModel, context);
            for (DataModelColumn column : cols) {
                dataDefines.add((FunRefDataDefine)new FunRefDataDefineImpl(tableName, column.getColumnName(), column.getColumnType(), column, fieldProp == null ? null : fieldProp.get(column.getColumnName())));
            }
        }
        param.put("BaseDataFormulaContext", (Object)context);
        return context;
    }

    private Map<String, Map<String, Object>> getFieldPropsMap(DataModelDO dataModel, BaseDataFormulaContext context) {
        String define;
        if (dataModel.getExtInfo("baseDataDefine") == null) {
            BaseDataDefineDTO dataDefineDTO = new BaseDataDefineDTO();
            dataDefineDTO.setName(dataModel.getName());
            dataDefineDTO.setTenantName(dataModel.getTenantName());
            BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(dataDefineDTO);
            define = baseDataDefineDO.getDefine();
        } else {
            Map baseDataDefine = (Map)dataModel.getExtInfo("baseDataDefine");
            define = (String)baseDataDefine.get("define");
        }
        if (define == null) {
            return null;
        }
        Map stringObjectMap = JSONUtil.parseMap((String)define);
        List fieldProps = (List)stringObjectMap.get("fieldProps");
        if (CollectionUtils.isEmpty((Collection)fieldProps)) {
            return null;
        }
        HashMap<String, Map<String, Object>> fieldProp = new HashMap<String, Map<String, Object>>();
        ArrayList<String> multiColumns = new ArrayList<String>();
        for (Map objectMap : fieldProps) {
            fieldProp.put((String)objectMap.get("columnName"), objectMap);
            if (objectMap.get("multiple") == null || !((Boolean)objectMap.get("multiple")).booleanValue()) continue;
            multiColumns.add((String)objectMap.get("columnName"));
        }
        if (!CollectionUtils.isEmpty(multiColumns)) {
            context.put("multiColumns", multiColumns);
        }
        return fieldProp;
    }

    public BaseDataOption.FilterType getFilterType() {
        return BaseDataOption.FilterType.FORMULA;
    }

    public boolean isEnable(BaseDataDTO param) {
        return param.containsKey((Object)"vaBizFormula");
    }
}

