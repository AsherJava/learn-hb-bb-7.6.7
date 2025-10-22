/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.syntax.SyntaxProvider
 *  com.jiuqi.nr.single.core.syntax.bean.BZZDataType
 *  com.jiuqi.nr.single.core.syntax.bean.CodeCellType
 *  com.jiuqi.nr.single.core.syntax.bean.ExistDataType
 *  com.jiuqi.nr.single.core.syntax.bean.FastCodeCellType
 *  com.jiuqi.nr.single.core.syntax.bean.FastExistDataType
 *  com.jiuqi.nr.single.core.syntax.bean.FastTableCellType
 *  com.jiuqi.nr.single.core.syntax.bean.MonthDataType
 *  com.jiuqi.nr.single.core.syntax.bean.StringDataType
 *  com.jiuqi.nr.single.core.syntax.bean.TableCellType
 *  com.jiuqi.nr.single.core.syntax.common.SyntaxCode
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.map.data.internal.util;

import com.jiuqi.nr.single.core.syntax.SyntaxProvider;
import com.jiuqi.nr.single.core.syntax.bean.BZZDataType;
import com.jiuqi.nr.single.core.syntax.bean.CodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.ExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.FastCodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.FastExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.FastTableCellType;
import com.jiuqi.nr.single.core.syntax.bean.MonthDataType;
import com.jiuqi.nr.single.core.syntax.bean.StringDataType;
import com.jiuqi.nr.single.core.syntax.bean.TableCellType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class SyntaxProviderMapEntityImpl
implements SyntaxProvider {
    private Map<String, Object> variableMap;
    private Map<String, Integer> valueIdexs;
    private List<Object> valueList;

    public SyntaxCode syntaxGetTableCell(TableCellType tableCell) {
        SyntaxCode result = SyntaxCode.SY_UNKOWN_SIGN;
        tableCell.setIndex(-1);
        if (StringUtils.isEmpty((CharSequence)tableCell.getSign())) {
            return result;
        }
        if (this.valueIdexs.containsKey(tableCell.getSign().toUpperCase())) {
            int num = this.valueIdexs.get(tableCell.getSign().toUpperCase());
            tableCell.setIndex(num);
            result = SyntaxCode.SY_OK;
        }
        return result;
    }

    public SyntaxCode syntaxSetTableCell(TableCellType tableCell) {
        return null;
    }

    public SyntaxCode syntaxGetCodeCell(CodeCellType codeCell) {
        SyntaxCode result = SyntaxCode.SY_UNKOWN_SIGN;
        codeCell.setIndex(-1);
        if (StringUtils.isEmpty((CharSequence)codeCell.getSign())) {
            return result;
        }
        if (this.valueIdexs.containsKey(codeCell.getSign().toUpperCase())) {
            int num = this.valueIdexs.get(codeCell.getSign().toUpperCase());
            codeCell.setIndex(num);
            result = SyntaxCode.SY_OK;
        }
        return result;
    }

    public SyntaxCode syntaxGetCodeMean(CodeCellType codeCell) {
        return null;
    }

    public SyntaxCode syntaxExistData(ExistDataType existData) {
        return null;
    }

    public SyntaxCode syntaxGetBZZData(BZZDataType bzzData) {
        return null;
    }

    public SyntaxCode syntaxGetMonth(MonthDataType monthData) {
        return null;
    }

    public SyntaxCode syntaxGetStrData(StringDataType sourceGM) {
        return null;
    }

    public void niporlanGetTableCell(FastTableCellType tableCell) {
        if (tableCell.getIndex() < 0) {
            tableCell.setValue(0.0);
            return;
        }
        Object obj = this.valueList.get(tableCell.getIndex());
        double value = 0.0;
        try {
            value = (Double)obj;
        }
        catch (Exception e1) {
            try {
                value = ((Integer)obj).intValue();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        tableCell.setValue(value);
    }

    public void niporlanSetTableCell(FastTableCellType tableCell) {
    }

    public void niporlanGetCodeCell(FastCodeCellType codeCell) {
        if (codeCell.getIndex() < 0) {
            codeCell.setValue("");
            return;
        }
        Object obj = this.valueList.get(codeCell.getIndex());
        codeCell.setValue((String)obj);
    }

    public void niporlanExistData(FastExistDataType existData) {
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
        this.valueIdexs = new HashMap<String, Integer>();
        this.valueList = new ArrayList<Object>();
        int num = 0;
        for (String code : variableMap.keySet()) {
            this.valueIdexs.put(code.toUpperCase(), num);
            this.valueList.add(variableMap.get(code));
            ++num;
        }
    }

    public Map<String, Object> getVariableMap() {
        if (this.variableMap == null) {
            this.variableMap = new HashMap<String, Object>();
        }
        return this.variableMap;
    }
}

