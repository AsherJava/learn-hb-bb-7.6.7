/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.ruler.impl.FunRefDataDefineImpl
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataFilter
 *  com.jiuqi.va.domain.org.OrgDataOption$FilterType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.filter.bill.formula.OrgFormulaContext
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.gcreport.org.impl.fieldManager.common.formula;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.gcreport.org.impl.fieldManager.common.formula.GcOrgFormulaContext;
import com.jiuqi.gcreport.org.impl.fieldManager.common.formula.GcOrgFormulaExecute;
import com.jiuqi.va.biz.ruler.impl.FunRefDataDefineImpl;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataFilter;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOrgFormulaFilter
implements OrgDataFilter {
    @Autowired
    private DataModelClient dataModelClient;

    public Boolean checkData(OrgDTO param, OrgDO data) {
        OrgFormulaContext context = this.getContext(param);
        IExpression expressionObj = (IExpression)context.get("transforFormula");
        context.setOrg(data);
        if (expressionObj == null) {
            expressionObj = GcOrgFormulaExecute.getInstance().transforFormula(param.getExpression(), context);
            context.put("transforFormula", (Object)expressionObj);
        }
        return GcOrgFormulaExecute.getInstance().judge(context, expressionObj);
    }

    public String getSqlCondition(OrgDTO param) {
        OrgFormulaContext context = this.getContext(param);
        return GcOrgFormulaExecute.getInstance().toSQL(param.getExpression(), context);
    }

    public OrgDataOption.FilterType getFilterType() {
        return OrgDataOption.FilterType.FORMULA;
    }

    public boolean isEnable(OrgDTO param) {
        return param.containsKey((Object)"gcBizFormula");
    }

    private OrgFormulaContext getContext(OrgDTO param) {
        if (param.get((Object)"OrgFormulaContext") != null) {
            return (OrgFormulaContext)param.get((Object)"OrgFormulaContext");
        }
        String categoryName = param.getCategoryname();
        ArrayList<FunRefDataDefine> dataDefines = new ArrayList<FunRefDataDefine>();
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(param.getTenantName());
        dataModelDTO.setName(categoryName);
        DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
        List cols = dataModel.getColumns();
        for (DataModelColumn column : cols) {
            dataDefines.add((FunRefDataDefine)new FunRefDataDefineImpl(categoryName, column.getColumnName(), column.getColumnType(), column));
        }
        GcOrgFormulaContext context = new GcOrgFormulaContext(null, dataDefines);
        param.put("OrgFormulaContext", (Object)context);
        return context;
    }
}

