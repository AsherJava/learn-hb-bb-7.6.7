/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.ruler.intf.RulerConsts
 *  com.jiuqi.va.biz.utils.FormulaUtils
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.FormulaUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BillGetSsoParamAction
extends BillActionBase {
    private static final Logger logger = LoggerFactory.getLogger(BillGetSsoParamAction.class);

    public String getName() {
        return "bill-get-sso-param";
    }

    public String getTitle() {
        return "\u83b7\u53d6sso\u53c2\u6570";
    }

    public boolean isInner() {
        return true;
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        Map field = (Map)params.get("field");
        UUID id = (UUID)Convert.cast(field.get("id"), UUID.class);
        if (id == null) {
            return null;
        }
        RulerDefineImpl ruler = (RulerDefineImpl)((Plugin)model.getPlugins().get("ruler")).getDefine();
        for (FormulaImpl formula : ruler.getFormulaList()) {
            if (!id.equals(formula.getObjectId()) || !RulerConsts.FORMULA_OBJECT_PROP_FIELD_SSOPARAM.equals(formula.getPropertyType()) || !formula.isUsed()) continue;
            HashMap<String, DataRow> rowMap = new HashMap<String, DataRow>();
            Object tableName = params.get("tableName");
            if (tableName == null) {
                DataRow dataRow = model.getMaster();
                rowMap.put(model.getMasterTable().getName(), dataRow);
            } else {
                DataRow dataRow = model.getTable(tableName.toString()).getRowById((Object)UUID.fromString((String)params.get("rowId")));
                rowMap.put(tableName.toString(), dataRow);
                FormulaUtils.adjustFormulaRows((Data)model.getData(), rowMap);
            }
            try {
                Object evaluate = FormulaUtils.evaluate((Model)model, (IExpression)formula.getCompiledExpression(), rowMap);
                return evaluate == null ? "" : evaluate;
            }
            catch (SyntaxException e) {
                logger.error(e.getMessage(), e);
                throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.getSsoParam.error"));
            }
        }
        return "";
    }
}

