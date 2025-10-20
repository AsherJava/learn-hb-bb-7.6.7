/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;

class ValueListCondition
implements ICondition {
    private BIDataSetImpl dataset;
    private RestrictionDescriptor rstDesc;
    private int sys_timekeycol;
    private int sys_rownumcol;

    public ValueListCondition(BIDataSetImpl dataset, RestrictionDescriptor rstDesc, int sys_timekeycol, int sys_rownumcol) {
        this.dataset = dataset;
        this.rstDesc = rstDesc;
        this.sys_timekeycol = sys_timekeycol;
        this.sys_rownumcol = sys_rownumcol;
    }

    public ValueListCondition(BIDataSetImpl dataset, RestrictionDescriptor rstDesc) {
        this.dataset = dataset;
        this.rstDesc = rstDesc;
        this.sys_timekeycol = dataset.getMetadata().indexOf("SYS_TIMEKEY");
        this.sys_rownumcol = dataset.getMetadata().indexOf("SYS_ROWNUM");
    }

    @Override
    public boolean canUseIndex() {
        int col = this.getCol();
        if (col != -1) {
            if (col == this.sys_timekeycol) {
                return true;
            }
            if (col == this.sys_rownumcol) {
                return true;
            }
            Column column = this.dataset.getMetadata().getColumn(col);
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            FieldType fType = info.getFieldType();
            if (fType.isMeasureField()) {
                return false;
            }
            int valType = info.getValType();
            if (valType == DataType.INTEGER.value() || valType == DataType.STRING.value() || valType == DataType.BOOLEAN.value() || valType == DataType.DATETIME.value()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getCol() {
        if (this.rstDesc.mode == 1) {
            return this.sys_timekeycol;
        }
        return this.rstDesc.fieldIdx;
    }

    @Override
    public Object getValue(DSFormulaContext dsCxt) throws BIDataSetException {
        if (this.rstDesc.mode == 2) {
            return this.calcValue(dsCxt);
        }
        if (this.rstDesc.mode == 0) {
            if (dsCxt.getCurRow() == null) {
                throw new BIDataSetException("\u516c\u5f0f\u6267\u884c\u8fc7\u6ee4\u51fa\u9519\uff0c\u65e0\u6cd5\u6839\u636e\u4e0a\u4e0b\u6587\u4fe1\u606f\u83b7\u53d6\u5f53\u524d\u884c\u8bb0\u5f55");
            }
            return dsCxt.getCurRow().getValue(this.getCol());
        }
        throw new BIDataSetException("\u503c\u5217\u8868\u6a21\u5f0f\u7684\u8868\u8fbe\u5f0f\uff0c\u683c\u5f0f\u4e66\u5199\u4e0d\u89c4\u8303");
    }

    @Override
    public void validate() throws BIDataSetException {
        String tag;
        if (this.rstDesc.mode == 1) {
            throw new BIDataSetException("\u503c\u5217\u8868\u6a21\u5f0f\u4e2d\u51fa\u73b0\u504f\u79fb\u8282\u70b9");
        }
        if (this.rstDesc.mode == 3) {
            throw new BIDataSetException("\u503c\u5217\u8868\u6a21\u5f0f\u4e2d\u51fa\u73b0\u8868\u8fbe\u5f0f\u8282\u70b9");
        }
        if (this.rstDesc.mode == 0 && !RestrictionTag.isCURRENT(tag = (String)this.rstDesc.condition)) {
            throw new BIDataSetException("\u503c\u5217\u8868\u6a21\u5f0f\u4e2d\u53ea\u5141\u8bb8\u4f7f\u7528CUR\u9650\u5b9a\u5173\u952e\u5b57\uff1a");
        }
    }

    private Object calcValue(DSFormulaContext dsCxt) throws BIDataSetException {
        if (this.rstDesc.condition instanceof IASTNode) {
            IASTNode node = (IASTNode)this.rstDesc.condition;
            try {
                Object value = node.evaluate((IContext)dsCxt);
                if (value instanceof ArrayData) {
                    ArrayData array = (ArrayData)value;
                    int baseType = array.baseType();
                    if (baseType != 3 && baseType != 6) {
                        throw new BIDataSetException("\u6570\u7ec4\u7c7b\u578b\u8282\u70b9\u4e0d\u5408\u6cd5\uff0c\u8282\u70b9\u4e2d\u7684\u57fa\u672c\u7c7b\u578b\u53ea\u80fd\u4e3a\u6570\u503c\u6216\u5b57\u7b26\u4e32\u7c7b\u578b");
                    }
                    Iterator itor = array.iterator();
                    ArrayList list = new ArrayList();
                    while (itor.hasNext()) {
                        list.add(itor.next());
                    }
                    return list.toArray(new Object[list.size()]);
                }
                return value;
            }
            catch (SyntaxException e) {
                throw new BIDataSetException("\u8ba1\u7b97\u8282\u70b9\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
        }
        return this.rstDesc.condition;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.rstDesc.item.getName()).append(" in {");
        if (this.rstDesc.condition instanceof Object[]) {
            buf.append(StringUtils.join((Object[])((Object[])this.rstDesc.condition), (String)","));
        } else {
            buf.append(this.rstDesc.condition);
        }
        buf.append("}");
        return buf.toString();
    }
}

