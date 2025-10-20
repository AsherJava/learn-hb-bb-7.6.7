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
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.va.filter.bill.filter;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.ruler.impl.FunRefDataDefineImpl;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataFilter;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.filter.bill.filter.BaseDataEncryptDES;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.filter.bill.formula.OrgFormulaExecute;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgFormulaFilter
implements OrgDataFilter {
    @Autowired
    private DataModelClient dataModelClient;

    public Boolean checkData(OrgDTO param, OrgDO data) {
        IExpression expressionObj;
        OrgFormulaContext context = this.getContext(param);
        if (param.containsKey((Object)"decrypted")) {
            param.setExpression(BaseDataEncryptDES.decrypt(param.getExpression()));
            param.remove((Object)"decrypted");
        }
        if ((expressionObj = (IExpression)context.get("transforFormula")) == null) {
            expressionObj = OrgFormulaExecute.getInstance().transforFormula(param.getExpression(), context);
            context.put("transforFormula", expressionObj);
        }
        context.setOrg(data);
        return OrgFormulaExecute.getInstance().judge(context, expressionObj);
    }

    public String getSqlCondition(OrgDTO param) {
        OrgFormulaContext context = this.getContext(param);
        return OrgFormulaExecute.getInstance().toSQL(param.getExpression(), context);
    }

    public OrgDataOption.FilterType getFilterType() {
        return OrgDataOption.FilterType.FORMULA;
    }

    public boolean isEnable(OrgDTO param) {
        return param.containsKey((Object)"vaBizFormula");
    }

    private OrgFormulaContext getContext(OrgDTO param) {
        if (param.get((Object)"OrgFormulaContext") != null) {
            return (OrgFormulaContext)((Object)param.get((Object)"OrgFormulaContext"));
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
        OrgFormulaContext context = new OrgFormulaContext(null, dataDefines);
        param.put("OrgFormulaContext", (Object)context);
        return context;
    }
}

