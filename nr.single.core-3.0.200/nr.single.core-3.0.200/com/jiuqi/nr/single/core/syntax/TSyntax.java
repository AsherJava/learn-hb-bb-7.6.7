/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.syntax;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.syntax.SyntaxProvider;
import com.jiuqi.nr.single.core.syntax.TNiporlan;
import com.jiuqi.nr.single.core.syntax.bean.BZZDataType;
import com.jiuqi.nr.single.core.syntax.bean.CodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;
import com.jiuqi.nr.single.core.syntax.bean.ExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.MonthDataType;
import com.jiuqi.nr.single.core.syntax.bean.MultiType;
import com.jiuqi.nr.single.core.syntax.bean.StringDataType;
import com.jiuqi.nr.single.core.syntax.bean.TableCellType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;
import com.jiuqi.nr.single.core.syntax.common.OpsType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import com.jiuqi.nr.single.core.syntax.common.SyntaxConsts;
import com.jiuqi.nr.single.core.syntax.common.SyntaxLib;
import com.jiuqi.nr.single.core.syntax.common.TAssignType;
import com.jiuqi.nr.single.core.syntax.common.TAttractType;
import com.jiuqi.nr.single.core.syntax.common.TCheckType;
import com.jiuqi.nr.single.core.syntax.common.TSyntaxOption;
import com.jiuqi.nr.single.core.syntax.common.TypeType;
import com.jiuqi.nr.single.core.syntax.grid.StringGrid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSyntax {
    private List<TSyntaxOption> syntaxOptions;
    private List<String> expressions = new ArrayList<String>();
    private List<String> niporlans = new ArrayList<String>();
    private StringGrid expressGrid = new StringGrid();
    private StringGrid niporlanGrid;
    private boolean nullNiporlan;
    private String defaultTabSign;
    private int errorLine;
    private TNiporlan niporlanObject;
    private String expStr;
    private int expI;
    private char ch;
    private SyntaxCode ret;
    private TCheckType checkType;
    private TAssignType assignType;
    private TAttractType attractType;
    private boolean inTableCell;
    private boolean inEffectCheck;
    private boolean makeNiporlan;
    private boolean multiSyntax;
    private String niporlan;
    private String ifexpStr;
    private String thenexpStr;
    private String elseexpStr;
    private String saveexpStr;
    private int thenPos;
    private int elsePos;
    private boolean canAssignValue;
    private boolean ifThenTrue;
    private boolean judgeresult;
    private Map<Integer, Integer> numberArray;
    private String tmpStr;
    private double tempReal;
    private int tmpInt;
    private double decPos;
    private MultiType tempVal;
    private SyntaxProvider provider;

    public TSyntax() {
        this.expressGrid.resetColRowCounts(4, 1);
        this.niporlanGrid = new StringGrid();
        this.niporlanGrid.resetColRowCounts(6, 1);
        this.niporlanObject = new TNiporlan(this);
        this.nullNiporlan = false;
        this.syntaxOptions = new ArrayList<TSyntaxOption>();
        this.syntaxOptions.add(TSyntaxOption.SO_TABLE);
        this.syntaxOptions.add(TSyntaxOption.SO_CODE);
        this.numberArray = new HashMap<Integer, Integer>();
        this.tempVal = new MultiType();
    }

    public SyntaxProvider getProvider() {
        return this.provider;
    }

    public void setProvider(SyntaxProvider provider) {
        this.provider = provider;
    }

    public String getDefaultTabSign() {
        return this.defaultTabSign;
    }

    public void setDefaultTabSign(String tabSign) {
        if (StringUtils.isNotEmpty((String)tabSign)) {
            String firstSign = (tabSign = tabSign.toUpperCase()).substring(0, 1);
            if (!SyntaxLib.checkInCharSet(firstSign, SyntaxConsts.ENGLISH_CHARSET) || !SyntaxLib.checkInCharSet(tabSign, SyntaxConsts.SIGN_CHARSET)) {
                tabSign = "";
            }
        } else {
            tabSign = "";
        }
        this.defaultTabSign = tabSign;
    }

    public String ErrorInfo() {
        return this.syntaxContent(this.ret);
    }

    public String syntaxContent(SyntaxCode code) {
        int i = 0;
        String result = "";
        if (code == SyntaxCode.SY_OK) {
            result = "\u6b63\u786e";
        } else {
            this.expStr = this.expStr + '\r' + '\n';
            for (i = 1; i <= this.expI - 1; ++i) {
                this.expStr = this.expStr + ' ';
            }
            this.expStr = this.expStr + '!';
            switch (code) {
                case SY_UNKOWN_SYNTAX: {
                    result = "\u672a\u77e5\u8bed\u6cd5";
                    break;
                }
                case SY_LACKOF_LEFT_SMALL_BRACKEK: {
                    result = "\u7f3a'('";
                    break;
                }
                case SY_LACKOF_RIGHT_SMALL_BRACKEK: {
                    result = "\u7f3a')'";
                    break;
                }
                case SY_LACKOF_LEFT_MIDDLE_BRACKEK: {
                    result = "\u7f3a'['";
                    break;
                }
                case SY_LACKOF_RIGHT_MIDDLE_BRACKEK: {
                    result = "\u7f3a']''";
                    break;
                }
                case SY_LACKOF_LEFT_BIG_BRACKEK: {
                    result = "\u7f3a'{'";
                    break;
                }
                case SY_LACKOF_RIGHT_BIG_BRACKEK: {
                    result = "\u7f3a'}''";
                    break;
                }
                case SY_LACKOF_INTEGER: {
                    result = "\u7f3a\u6574\u6570";
                    break;
                }
                case SY_LACKOF_CHAR: {
                    result = "\u7f3a\u5b57\u7b26";
                    break;
                }
                case SY_LACKOF_CHAR_WELL: {
                    result = "\u7f3a'#'";
                    break;
                }
                case SY_LACKOF_EQUAL_SIGN: {
                    result = "\u7f3a'=''";
                    break;
                }
                case SY_LACKOF_DIGIT: {
                    result = "\u7f3a\u6570\u5b57";
                    break;
                }
                case SY_LACKOF_COMMA: {
                    result = "\u7f3a','";
                    break;
                }
                case SY_LACKOF_STRING: {
                    result = "\u7f3a\u5b57\u7b26\u4e32";
                    break;
                }
                case SY_LACKOF_AND: {
                    result = "\u7f3aAND";
                    break;
                }
                case SY_LACKOF_OR: {
                    result = "\u7f3aOR";
                    break;
                }
                case SY_LACKOF_TABLE: {
                    result = "\u7f3a\u8868\u540d";
                    break;
                }
                case SY_LACKOF_THEN: {
                    result = "\u7f3aTHEN";
                    break;
                }
                case SY_NO_SUPPORT_STAR: {
                    result = "\u4e0d\u652f\u6301\u901a\u914d";
                    break;
                }
                case SY_BAD_INTERVAL_SET: {
                    result = "\u9519\u8bef\u7684\u884c\u680f\u533a\u95f4\u8bbe\u7f6e";
                    break;
                }
                case SY_INTERVAL_OUTOF_ORDER: {
                    result = "\u9519\u8bef\u7684\u884c\u680f\u8bbe\u5b9a\u6709\u8bef, \u7ec8\u70b9\u5c0f\u4e8e\u8d77\u70b9";
                    break;
                }
                case SY_INTERVAL_SET_AT_END: {
                    result = "\u884c\u680f\u533a\u95f4\u8bbe\u7f6e\u5fc5\u987b\u5728\u672b\u5c3e";
                    break;
                }
                case SY_BAD_USE_LINK_PLUS: {
                    result = "\u8fde\u52a0\u7b26'&'\u4f7f\u7528\u4e0d\u5f53";
                    break;
                }
                case SY_LINK_PLUS_OUTOF_ORDER: {
                    result = "\u8fde\u52a0\u7b26'&'\u7ec8\u70b9\u5c0f\u4e8e\u8d77\u70b9";
                    break;
                }
                case SY_BAD_USE_DOLLAR: {
                    result = "'$'\u4f7f\u7528\u4e0d\u5f53";
                    break;
                }
                case SY_BAD_USE_BIG_BRACKET: {
                    result = "'{'\u6216'}'\u4f7f\u7528\u4e0d\u5f53";
                    break;
                }
                case SY_NO_LOGIC_EXPRESS: {
                    result = "\u975e\u903b\u8f91\u578b\u8868\u8fbe\u5f0f";
                    break;
                }
                case SY_MUCH_OF_BRACKET: {
                    result = "\u62ec\u53f7\u91cd\u6570\u592a\u591a";
                    break;
                }
                case SY_BRACKET_NO_MATCH: {
                    result = "\u62ec\u53f7\u4e0d\u5339\u914d";
                    break;
                }
                case SY_INT_OVER_BORDER: {
                    result = "\u6570\u503c\u8d8a\u754c";
                    break;
                }
                case SY_INVALID_CHAR_IN_CELL: {
                    result = "\u5355\u5143\u5185\u51fa\u73b0\u975e\u6cd5\u5b57\u7b26";
                    break;
                }
                case SY_EXIST_SPARE_CHAR: {
                    result = "\u591a\u4f59\u7684\u5b57\u7b26";
                    break;
                }
                case SY_TYPE_NO_MATCH: {
                    result = "\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d";
                    break;
                }
                case SY_INVALID_CHAR: {
                    result = "\u51fa\u73b0\u975e\u6cd5\u5b57\u7b26";
                    break;
                }
                case SY_OVER_LAP_OPERATOR: {
                    result = "\u91cd\u53e0\u64cd\u4f5c\u7b26";
                    break;
                }
                case SY_BAD_USE_QUOTE_SIGN: {
                    result = "\"\u4f7f\u7528\u4e0d\u5f53";
                    break;
                }
                case SY_NEED_OF_INTERVAL_SET: {
                    result = "\u9700\u8981\u5355\u5143\u533a\u95f4\u8bbe\u7f6e";
                    break;
                }
                case SY_NEED_OF_STRING_EXP: {
                    result = "\u9700\u8981\u5b57\u7b26\u4e32\u8868\u8fbe\u5f0f";
                    break;
                }
                case SY_NEED_OF_REAL_EXP: {
                    result = "\u9700\u8981\u6570\u503c\u8868\u8fbe\u5f0f";
                    break;
                }
                case SY_NEED_OF_IF_EXP: {
                    result = "If\u540e\u9700\u8981\u8868\u8fbe\u5f0f";
                    break;
                }
                case SY_NEED_OF_THEN_EXP: {
                    result = "Then\u540e\u9700\u8981\u8868\u8fbe\u5f0f";
                    break;
                }
                case SY_NEED_OF_ELSE_EXP: {
                    result = "Else\u540e\u9700\u8981\u8868\u8fbe\u5f0f";
                    break;
                }
                case SY_IDC_NEED_EIGHT_DIGIT: {
                    result = "\u6821\u9a8c\u7801\u51fd\u6570IDC\u7684\u53c2\u6570\u662f\u516b\u4e2a\u6570\u5b57\u7ec4\u6210\u7684\u5b57\u7b26\u4e32";
                    break;
                }
                case SY_EXIST_DATA_NEED_TABSIGN: {
                    result = "\u4e0d\u6b63\u786e\u7684\u8868\u6807\u8bc6";
                    break;
                }
                case SY_LAST_SYNTAX_CODE: {
                    result = "\u8bed\u6cd5\u9519\u8bef\u7801\u5206\u754c";
                    break;
                }
                case SY_UNKOWN_SIGN: {
                    result = "\u672a\u77e5\u7684\u6807\u8bc6";
                    break;
                }
                case SY_NO_EXIST_TABLE_NAME: {
                    result = "\u4e0d\u5b58\u5728\u7684\u8868\u6807\u8bc6";
                    break;
                }
                case SY_NO_EXIST_CELL_SIGN: {
                    result = "\u4e0d\u5b58\u5728\u7684\u8868\u5355\u5143\u6807\u8bc6";
                    break;
                }
                case SY_NO_EXIST_CELL_NUMBER: {
                    result = "\u4e0d\u5b58\u5728\u7684\u8868\u5355\u5143\u7f16\u53f7";
                    break;
                }
                case SY_BAD_TABLE_CELL_PARA: {
                    result = "\u9519\u8bef\u7684\u8868\u5355\u5143\u53c2\u6570";
                    break;
                }
                case SY_NO_NUMBER_TABLE_CELL: {
                    result = "\u9700\u8981\u6570\u503c\u8868\u5355\u5143";
                    break;
                }
                case SY_CODE_NO_MEANING: {
                    result = "\u4ee3\u7801\u65e0\u542b\u4e49\u6587\u5b57";
                    break;
                }
                case SY_MONTH_OVER_BORDER: {
                    result = "\u6708\u4efd\u8d8a\u754c";
                    break;
                }
                case SY_ONLY_SET_CURRENT: {
                    result = "\u4ec5\u53ef\u4ee5\u5bf9\u5f53\u524d\u533a\u57df\u6570\u636e\u8d4b\u503c";
                    break;
                }
                case SY_CELL_READ_ONLY: {
                    result = "\u8bd5\u56fe\u5411\u53ea\u8bfb\u7684\u5355\u5143\u8d4b\u503c";
                    break;
                }
                case SY_NO_SUPPORT_MONTH: {
                    result = "\u4e0d\u652f\u6301\u8de8\u6708";
                    break;
                }
                default: {
                    result = "\u8bed\u6cd5\u9519\u8bef";
                }
            }
            result = result + '\r' + '\n' + this.expStr;
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SyntaxCode multiBuild() {
        SyntaxCode result = SyntaxCode.SY_OK;
        this.checkType = TCheckType.CHECK_JUDGE;
        this.multiSyntax = true;
        this.makeNiporlan = true;
        this.niporlan = "";
        if (this.expressGrid.getColCount() < 3) {
            this.expressGrid.setColCount(3);
        }
        this.niporlanGrid.setColCount(this.expressGrid.getColCount() + 2);
        this.niporlanGrid.setRowCount(1);
        MultiType nulVal = new MultiType();
        try {
            this.errorLine = 0;
            for (int i = 0; i <= this.expressGrid.getRowCount() - 1; ++i) {
                this.inTableCell = false;
                this.expressGrid.setRow(i);
                result = this.estimate(this.expressGrid.getCells(0, i), nulVal);
                if (result == SyntaxCode.SY_OK) continue;
                this.errorLine = i + 1;
                SyntaxCode syntaxCode = result;
                return syntaxCode;
            }
        }
        finally {
            this.multiSyntax = false;
        }
        return result;
    }

    public SyntaxCode buildNiporlan(TCheckType chkType) {
        return this.buildNiporlan(chkType, TAttractType.ATTRACT_ANY);
    }

    public SyntaxCode buildNiporlan(TCheckType chkType, TAttractType AttType) {
        SyntaxCode result = SyntaxCode.SY_OK;
        this.checkType = chkType;
        this.assignType = TAssignType.ASSIGN_TABLE;
        this.attractType = AttType;
        this.multiSyntax = false;
        this.makeNiporlan = true;
        this.niporlans.clear();
        this.niporlan = "";
        MultiType nulVal = new MultiType();
        result = SyntaxCode.SY_OK;
        this.errorLine = 0;
        for (int i = 0; i <= this.expressions.size() - 1; ++i) {
            this.inTableCell = false;
            result = this.estimate(this.expressions.get(i), nulVal);
            if (result == SyntaxCode.SY_OK && this.checkType == TCheckType.CHECK_ATTRACT) {
                switch (AttType) {
                    case ATTRACT_STRING: {
                        if (nulVal.getOpType() == TypeType.STR_TYPE) break;
                        result = SyntaxCode.SY_NEED_OF_STRING_EXP;
                        break;
                    }
                    case ATTRACT_REAL: {
                        if (nulVal.getOpType() == TypeType.INT_TYPE) {
                            nulVal.setOpType(TypeType.REAL_TYPE);
                            nulVal.setOpReal(nulVal.getOpInt());
                        }
                        if (nulVal.getOpType() == TypeType.REAL_TYPE) break;
                        this.ret = result = SyntaxCode.SY_NEED_OF_REAL_EXP;
                        break;
                    }
                }
            }
            if (result == SyntaxCode.SY_OK) continue;
            this.errorLine = i + 1;
            return result;
        }
        return result;
    }

    public SyntaxCode check(TCheckType chkType) {
        return this.check(chkType, TAttractType.ATTRACT_ANY);
    }

    public SyntaxCode check(TCheckType chkType, TAttractType AttType) {
        SyntaxCode result = SyntaxCode.SY_OK;
        this.checkType = chkType;
        this.assignType = TAssignType.ASSIGN_TABLE;
        this.attractType = AttType;
        this.multiSyntax = false;
        this.makeNiporlan = true;
        MultiType nulVal = new MultiType();
        result = SyntaxCode.SY_OK;
        this.errorLine = 0;
        for (int i = 0; i <= this.expressions.size() - 1; ++i) {
            this.inTableCell = false;
            result = this.estimate(this.expressions.get(i), nulVal);
            if (result == SyntaxCode.SY_OK && this.checkType == TCheckType.CHECK_ATTRACT) {
                switch (AttType) {
                    case ATTRACT_STRING: {
                        if (nulVal.getOpType() == TypeType.STR_TYPE) break;
                        result = SyntaxCode.SY_NEED_OF_STRING_EXP;
                        break;
                    }
                    case ATTRACT_REAL: {
                        if (nulVal.getOpType() == TypeType.INT_TYPE) {
                            nulVal.setOpType(TypeType.REAL_TYPE);
                            nulVal.setOpReal(nulVal.getOpInt());
                        }
                        if (nulVal.getOpType() == TypeType.REAL_TYPE) break;
                        this.ret = result = SyntaxCode.SY_NEED_OF_REAL_EXP;
                        break;
                    }
                }
            }
            if (result == SyntaxCode.SY_OK) continue;
            this.errorLine = i + 1;
            return result;
        }
        return result;
    }

    public SyntaxCode attractBySyntax(String syStr, CommonDataType retData) {
        SyntaxCode result = SyntaxCode.SY_OK;
        this.checkType = TCheckType.CHECK_ATTRACT;
        this.attractType = TAttractType.ATTRACT_ANY;
        this.makeNiporlan = false;
        MultiType nulVal = new MultiType();
        this.inTableCell = false;
        result = this.estimate(syStr, nulVal);
        if (result == SyntaxCode.SY_OK) {
            switch (nulVal.getOpType()) {
                case STR_TYPE: {
                    retData.setCdType(CommonDataTypeType.CD_STRING_TYPE);
                    retData.setCdString(nulVal.getOpStr());
                    break;
                }
                case REAL_TYPE: {
                    retData.setCdType(CommonDataTypeType.CD_REAL_TYPE);
                    retData.setCdReal(nulVal.getOpReal());
                    break;
                }
                case INT_TYPE: {
                    retData.setCdType(CommonDataTypeType.CD_REAL_TYPE);
                    retData.setCdReal(nulVal.getOpInt());
                    break;
                }
                default: {
                    this.ret = result = SyntaxCode.SY_UNKOWN_SYNTAX;
                }
            }
        }
        return result;
    }

    public boolean judge(String niStr) {
        String newNiStr = niStr;
        return this.niporlanObject.judge(newNiStr);
    }

    public void evaluate(String niStr) {
        this.niporlanObject.evaluate(niStr);
    }

    public void attract(String theFormula, CommonDataType retData) {
        this.niporlanObject.attract(theFormula, retData);
    }

    public void setExpressions(List<String> value) {
        this.expressions.clear();
        this.expressions.addAll(value);
    }

    public List<String> getExpressions() {
        return this.expressions;
    }

    public List<String> getNiporlans() {
        return this.niporlans;
    }

    public void setSyntaxOptions(List<TSyntaxOption> Options) {
        ArrayList<TSyntaxOption> newOptions = new ArrayList<TSyntaxOption>();
        newOptions.addAll(Options);
        if (!newOptions.contains((Object)TSyntaxOption.SO_TABLE)) {
            newOptions.remove((Object)TSyntaxOption.SO_STAR);
            newOptions.remove((Object)TSyntaxOption.SO_CHKPOS);
            this.defaultTabSign = "";
        }
        this.syntaxOptions = newOptions;
    }

    private SyntaxCode getTableCell(TableCellType tableCell) {
        if (!this.syntaxOptions.contains((Object)TSyntaxOption.SO_TABLE) || this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        if (StringUtils.isEmpty((String)tableCell.getTabSign())) {
            tableCell.setTabSign(this.defaultTabSign);
        }
        return this.provider.syntaxGetTableCell(tableCell);
    }

    private SyntaxCode setTableCell(TableCellType tableCell) {
        SyntaxCode result = null;
        if (!this.syntaxOptions.contains((Object)TSyntaxOption.SO_TABLE)) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        result = SyntaxCode.SY_OK;
        if (this.provider != null) {
            if (StringUtils.isEmpty((String)tableCell.getTabSign())) {
                tableCell.setTabSign(this.defaultTabSign);
            }
            return this.provider.syntaxSetTableCell(tableCell);
        }
        return result;
    }

    private SyntaxCode getCodeCell(CodeCellType codeCell) {
        if (!this.syntaxOptions.contains((Object)TSyntaxOption.SO_CODE) || this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        return this.provider.syntaxGetCodeCell(codeCell);
    }

    private SyntaxCode getCodeMean(CodeCellType codeCell) {
        if (!this.syntaxOptions.contains((Object)TSyntaxOption.SO_MEAN) || this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        return this.provider.syntaxGetCodeMean(codeCell);
    }

    private SyntaxCode getExistData(ExistDataType existData) {
        if (!this.syntaxOptions.contains((Object)TSyntaxOption.SO_TABLE) || this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        if (StringUtils.isEmpty((String)existData.getTabSign())) {
            existData.setTabSign(this.defaultTabSign);
        }
        return this.provider.syntaxExistData(existData);
    }

    private SyntaxCode getBzzData(BZZDataType bzzData) {
        if (this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        return this.provider.syntaxGetBZZData(bzzData);
    }

    private SyntaxCode getMonth(MonthDataType monthData) {
        if (this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        return this.provider.syntaxGetMonth(monthData);
    }

    private SyntaxCode GetBzzGM(StringDataType strData) {
        if (this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        return this.provider.syntaxGetStrData(strData);
    }

    private SyntaxCode getStringData(StringDataType stringData) {
        if (this.provider == null) {
            return SyntaxCode.SY_UNKOWN_SYNTAX;
        }
        return this.provider.syntaxGetStrData(stringData);
    }

    private void getChar() {
        if (this.expI < this.expStr.length()) {
            ++this.expI;
            this.ch = this.expStr.charAt(this.expI - 1);
        } else {
            this.ch = '\u0000';
        }
    }

    private void skipSpace() {
        while (this.ch == ' ') {
            this.getChar();
        }
    }

    private void addString(char str) {
        this.addString(String.valueOf(str));
    }

    private void addString(String str) {
        if (this.inEffectCheck && !this.inTableCell && StringUtils.isEmpty((String)this.expressGrid.getCells(1, this.expressGrid.getRow()))) {
            String aCode = this.niporlanGrid.getCells(3, this.niporlanGrid.getRowCount() - 1);
            this.niporlanGrid.setCells(3, this.niporlanGrid.getRowCount() - 1, aCode + str);
        }
    }

    private void addTableCell(TableCellType tableCell) {
        if (this.multiSyntax && this.inEffectCheck && !this.inTableCell && StringUtils.isEmpty((String)this.expressGrid.getCells(2, this.expressGrid.getRow()))) {
            this.tmpStr = tableCell.getTabSign();
            this.tmpStr = tableCell.getIsSign() ? this.tmpStr + '[' + tableCell.getSign() : this.tmpStr + '[' + String.valueOf(tableCell.getNumber());
            this.tmpStr = this.tmpStr + ']';
            String aCode = this.niporlanGrid.getCells(4, this.niporlanGrid.getRowCount() - 1);
            if (SyntaxLib.getFindPos(this.tmpStr, aCode) < 0) {
                this.niporlanGrid.setCells(4, this.niporlanGrid.getRowCount() - 1, aCode + this.tmpStr + ";");
            }
        }
    }

    private void addCodeCell(String codeStr) {
        String aCode;
        if (this.multiSyntax && this.inEffectCheck && StringUtils.isEmpty((String)this.expressGrid.getCells(2, this.expressGrid.getRow())) && StringUtils.isNotEmpty((String)codeStr) && SyntaxLib.getFindPos(codeStr, aCode = this.niporlanGrid.getCells(4, this.niporlanGrid.getRowCount() - 1)) < 0) {
            this.niporlanGrid.setCells(4, this.niporlanGrid.getRowCount() - 1, aCode + codeStr + ";");
        }
    }

    private void freshChkPos(TableCellType tableCell) {
        if (!this.multiSyntax || this.syntaxOptions.contains((Object)TSyntaxOption.SO_CHKPOS)) {
            // empty if block
        }
    }

    private void chkType(MultiType lop, MultiType rop, OpsType ops) {
        MultiType sumVal = new MultiType();
        MultiType getVal = new MultiType();
        if (ops == OpsType.SY_OR) {
            if (lop.getOpType() != TypeType.BOOL_TYPE || rop.getOpType() != TypeType.BOOL_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            lop.setOpBool(lop.isOpBool() || rop.isOpBool());
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        if (ops == OpsType.SY_AND) {
            if (lop.getOpType() != TypeType.BOOL_TYPE || rop.getOpType() != TypeType.BOOL_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            lop.setOpBool(lop.isOpBool() || rop.isOpBool());
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        if (ops == OpsType.SY_CPLUS) {
            if (lop.getOpType() != TypeType.TABLE_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() != rop.getOpType()) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() == TypeType.TABLE_TYPE) {
                if (StringUtils.isNotEmpty((String)lop.getOpTable().getTabSign()) && StringUtils.isNotEmpty((String)rop.getOpTable().getTabSign()) && !lop.getOpTable().getTabSign().equalsIgnoreCase(rop.getOpTable().getTabSign()) || lop.getOpTable().getIsSign() || rop.getOpTable().getIsSign()) {
                    this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                    return;
                }
                if (lop.getOpTable().getNumber() > rop.getOpTable().getNumber()) {
                    this.ret = SyntaxCode.SY_LINK_PLUS_OUTOF_ORDER;
                    return;
                }
                sumVal.copyFrom(lop);
                getVal.copyFrom(lop);
                sumVal.getOpTable().setValue(0.0);
                for (int n = lop.getOpTable().getNumber(); n <= rop.getOpTable().getNumber(); ++n) {
                    getVal.getOpTable().setNumber(n);
                    if (this.multiSyntax) {
                        this.addTableCell(getVal.getOpTable());
                    }
                    this.ret = this.getTableCell(getVal.getOpTable());
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    sumVal.getOpTable().setValue(sumVal.getOpTable().getValue() + getVal.getOpTable().getValue());
                }
                lop.setOpType(TypeType.REAL_TYPE);
                lop.setOpReal(sumVal.getOpTable().getValue());
                this.ret = SyntaxCode.SY_OK;
                return;
            }
        }
        getVal.copyFrom(rop);
        this.ret = rop.getOpType() == TypeType.TABLE_TYPE ? this.getTableCell(getVal.getOpTable()) : SyntaxCode.SY_OK;
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (rop.getOpType() == TypeType.TABLE_TYPE) {
            rop.setOpType(TypeType.REAL_TYPE);
            rop.setOpReal(getVal.getOpTable().getValue());
        }
        if (ops == OpsType.SY_ASSIGN_VALUE) {
            if (lop.getOpType() != TypeType.TABLE_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() == TypeType.TABLE_TYPE) {
                if (rop.getOpType() == TypeType.INT_TYPE) {
                    if (lop.getOpType() == TypeType.TABLE_TYPE) {
                        lop.getOpTable().setValue(rop.getOpInt());
                    }
                } else if (rop.getOpType() == TypeType.REAL_TYPE) {
                    if (lop.getOpType() == TypeType.TABLE_TYPE) {
                        lop.getOpTable().setValue(rop.getOpReal());
                    }
                } else {
                    this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                    return;
                }
            }
            if (this.canAssignValue && lop.getOpType() == TypeType.TABLE_TYPE) {
                this.ret = this.setTableCell(lop.getOpTable());
            }
            return;
        }
        getVal.copyFrom(lop);
        this.ret = lop.getOpType() == TypeType.TABLE_TYPE ? this.getTableCell(getVal.getOpTable()) : SyntaxCode.SY_OK;
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (lop.getOpType() == TypeType.TABLE_TYPE) {
            lop.setOpType(TypeType.REAL_TYPE);
            lop.setOpReal(getVal.getOpTable().getValue());
        }
        ArrayList<OpsType> tempTypes = new ArrayList<OpsType>();
        tempTypes.add(OpsType.SY_PLUS);
        tempTypes.add(OpsType.SY_MINUS);
        tempTypes.add(OpsType.SY_PRODUCT);
        tempTypes.add(OpsType.SY_DIV);
        if (tempTypes.contains((Object)ops)) {
            if (lop.getOpType() == TypeType.BOOL_TYPE) {
                lop.setOpType(TypeType.INT_TYPE);
                if (lop.isOpBool()) {
                    lop.setOpInt(-1);
                } else {
                    lop.setOpInt(0);
                }
            }
            if (rop.getOpType() == TypeType.BOOL_TYPE) {
                rop.setOpType(TypeType.INT_TYPE);
                if (rop.isOpBool()) {
                    rop.setOpInt(-1);
                } else {
                    rop.setOpInt(0);
                }
            }
            if (lop.getOpType() == TypeType.STR_TYPE && rop.getOpType() != TypeType.STR_TYPE || lop.getOpType() != TypeType.STR_TYPE && rop.getOpType() == TypeType.STR_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() == TypeType.STR_TYPE) {
                if (ops == OpsType.SY_PLUS) {
                    lop.setOpStr(lop.getOpStr() + rop.getOpStr());
                    this.ret = SyntaxCode.SY_OK;
                    return;
                }
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() == TypeType.INT_TYPE && rop.getOpType() != TypeType.INT_TYPE) {
                if (ops == OpsType.SY_PLUS) {
                    getVal.setOpType(TypeType.REAL_TYPE);
                    getVal.setOpReal(lop.getOpInt());
                    if (Math.abs(getVal.getOpReal() + (double)rop.getOpInt()) > 2.147483647E9) {
                        lop.setOpType(TypeType.REAL_TYPE);
                        lop.setOpReal(getVal.getOpReal() + (double)rop.getOpInt());
                    } else {
                        lop.setOpInt(lop.getOpInt() + rop.getOpInt());
                    }
                } else if (ops == OpsType.SY_MINUS) {
                    getVal.setOpType(TypeType.REAL_TYPE);
                    getVal.setOpReal(lop.getOpInt());
                    if (Math.abs(getVal.getOpReal() - (double)rop.getOpInt()) > 2.147483647E9) {
                        lop.setOpType(TypeType.REAL_TYPE);
                        lop.setOpReal(getVal.getOpReal() - (double)rop.getOpInt());
                    } else {
                        lop.setOpInt(lop.getOpInt() - rop.getOpInt());
                    }
                } else if (ops == OpsType.SY_PRODUCT) {
                    getVal.setOpType(TypeType.REAL_TYPE);
                    getVal.setOpReal(lop.getOpInt());
                    if (Math.abs(getVal.getOpReal() * (double)rop.getOpInt()) > 2.147483647E9) {
                        lop.setOpType(TypeType.REAL_TYPE);
                        lop.setOpReal(getVal.getOpReal() * (double)rop.getOpInt());
                    } else {
                        lop.setOpInt(lop.getOpInt() * rop.getOpInt());
                    }
                } else if (ops == OpsType.SY_DIV) {
                    if (rop.getOpInt() == 0) {
                        lop.setOpInt(0);
                    } else {
                        getVal.setOpType(TypeType.REAL_TYPE);
                        getVal.setOpReal(lop.getOpInt());
                        double newValue = getVal.getOpReal() / (double)rop.getOpInt();
                        if (Math.abs(SyntaxLib.getFrac(newValue)) > 1.0E-5 || Math.abs(newValue) > 2.147483647E9) {
                            lop.setOpType(TypeType.REAL_TYPE);
                            lop.setOpReal(getVal.getOpReal() / (double)rop.getOpInt());
                        } else {
                            lop.setOpInt(lop.getOpInt() / rop.getOpInt());
                        }
                    }
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            getVal.copyFrom(lop);
            if (lop.getOpType() == TypeType.INT_TYPE) {
                lop.setOpType(TypeType.REAL_TYPE);
                lop.setOpReal(getVal.getOpInt());
            }
            getVal.copyFrom(rop);
            if (rop.getOpType() == TypeType.INT_TYPE) {
                rop.setOpType(TypeType.REAL_TYPE);
                rop.setOpReal(getVal.getOpInt());
            }
            if (ops == OpsType.SY_PLUS) {
                lop.setOpReal(lop.getOpReal() + rop.getOpReal());
            } else if (ops == OpsType.SY_PLUS) {
                if (Math.abs(lop.getOpReal() - rop.getOpReal()) < 1.0E-5) {
                    lop.setOpReal(0.0);
                } else {
                    lop.setOpReal(lop.getOpReal() - rop.getOpReal());
                }
            } else if (ops == OpsType.SY_PRODUCT) {
                lop.setOpReal(lop.getOpReal() * rop.getOpReal());
            } else if (ops == OpsType.SY_DIV) {
                if (rop.getOpReal() == 0.0) {
                    lop.setOpReal(0.0);
                } else {
                    lop.setOpReal(lop.getOpReal() / rop.getOpReal());
                }
            }
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        ArrayList<OpsType> tempTypes2 = new ArrayList<OpsType>();
        tempTypes2.add(OpsType.SY_EQU);
        tempTypes2.add(OpsType.SY_NEQ);
        tempTypes2.add(OpsType.SY_GLT);
        tempTypes2.add(OpsType.SY_BLT);
        tempTypes2.add(OpsType.SY_GLE);
        tempTypes2.add(OpsType.SY_BLE);
        if (tempTypes2.contains((Object)ops)) {
            if (lop.getOpType() == TypeType.BOOL_TYPE || rop.getOpType() == TypeType.BOOL_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() == TypeType.STR_TYPE && rop.getOpType() != TypeType.STR_TYPE || lop.getOpType() != TypeType.STR_TYPE && rop.getOpType() == TypeType.STR_TYPE) {
                this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                return;
            }
            if (lop.getOpType() == TypeType.STR_TYPE) {
                sumVal.setOpType(TypeType.BOOL_TYPE);
                String leftCode = lop.getOpStr();
                String rightCode = rop.getOpStr();
                if (StringUtils.isEmpty((String)leftCode)) {
                    leftCode = "";
                }
                if (StringUtils.isEmpty((String)rightCode)) {
                    rightCode = "";
                }
                switch (ops) {
                    case SY_EQU: {
                        sumVal.setOpBool(leftCode.compareToIgnoreCase(rightCode) == 0);
                        break;
                    }
                    case SY_NEQ: {
                        sumVal.setOpBool(leftCode.compareToIgnoreCase(rightCode) != 0);
                        break;
                    }
                    case SY_GLT: {
                        sumVal.setOpBool(leftCode.compareToIgnoreCase(rightCode) > 0);
                        break;
                    }
                    case SY_BLT: {
                        sumVal.setOpBool(leftCode.compareToIgnoreCase(rightCode) < 0);
                        break;
                    }
                    case SY_GLE: {
                        sumVal.setOpBool(leftCode.compareToIgnoreCase(rightCode) >= 0);
                        break;
                    }
                    case SY_BLE: {
                        sumVal.setOpBool(leftCode.compareToIgnoreCase(rightCode) <= 0);
                        break;
                    }
                }
                lop.copyFrom(sumVal);
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (lop.getOpType() == TypeType.INT_TYPE && rop.getOpType() == TypeType.INT_TYPE) {
                sumVal.setOpType(TypeType.BOOL_TYPE);
                switch (ops) {
                    case SY_EQU: {
                        sumVal.setOpBool(lop.getOpInt() == rop.getOpInt());
                        break;
                    }
                    case SY_NEQ: {
                        sumVal.setOpBool(lop.getOpInt() != rop.getOpInt());
                        break;
                    }
                    case SY_GLT: {
                        sumVal.setOpBool(lop.getOpInt() > rop.getOpInt());
                        break;
                    }
                    case SY_BLT: {
                        sumVal.setOpBool(lop.getOpInt() < rop.getOpInt());
                        break;
                    }
                    case SY_GLE: {
                        sumVal.setOpBool(lop.getOpInt() >= rop.getOpInt());
                        break;
                    }
                    case SY_BLE: {
                        sumVal.setOpBool(lop.getOpInt() <= rop.getOpInt());
                        break;
                    }
                }
            }
            getVal.copyFrom(lop);
            if (lop.getOpType() == TypeType.INT_TYPE) {
                lop.setOpType(TypeType.REAL_TYPE);
                lop.setOpReal(getVal.getOpInt());
            }
            getVal.copyFrom(rop);
            if (rop.getOpType() == TypeType.INT_TYPE) {
                rop.setOpType(TypeType.REAL_TYPE);
                rop.setOpReal(getVal.getOpInt());
            }
            sumVal.setOpType(TypeType.BOOL_TYPE);
            try {
                switch (ops) {
                    case SY_EQU: {
                        sumVal.setOpBool((double)Math.abs(lop.getOpInt() - rop.getOpInt()) < 1.0E-5);
                        break;
                    }
                    case SY_NEQ: {
                        sumVal.setOpBool((double)Math.abs(lop.getOpInt() - rop.getOpInt()) >= 1.0E-5);
                        break;
                    }
                    case SY_GLT: {
                        sumVal.setOpBool((double)(lop.getOpInt() - rop.getOpInt()) > 1.0E-5);
                        break;
                    }
                    case SY_BLT: {
                        sumVal.setOpBool((double)(lop.getOpInt() - rop.getOpInt()) < -1.0E-5);
                        break;
                    }
                    case SY_GLE: {
                        sumVal.setOpBool((double)(lop.getOpInt() - rop.getOpInt()) > 1.0E-5 || (double)Math.abs(lop.getOpInt() - rop.getOpInt()) < 1.0E-5);
                        break;
                    }
                    case SY_BLE: {
                        sumVal.setOpBool((double)(lop.getOpInt() - rop.getOpInt()) < -1.0E-5 || (double)Math.abs(lop.getOpInt() - rop.getOpInt()) < 1.0E-5);
                        break;
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            lop.copyFrom(sumVal);
            this.ret = SyntaxCode.SY_OK;
            return;
        }
    }

    private void multor(MultiType mulPara) {
        if (this.ch == '\u0000') {
            this.ret = SyntaxCode.SY_LACKOF_CHAR;
            return;
        }
        MultiType leftOp = new MultiType();
        if (this.ch == ' ') {
            this.skipSpace();
        }
        if (this.inTableCell && (this.ch < '0' || this.ch > '9')) {
            this.ret = SyntaxCode.SY_INVALID_CHAR_IN_CELL;
            return;
        }
        if (this.ch == '\"') {
            this.tmpStr = "";
            this.getChar();
            while (this.ch != '\"' && this.ch != '\u0000') {
                this.tmpStr = this.tmpStr + this.ch;
                this.getChar();
            }
            if (this.ch == '\"') {
                this.getChar();
            }
            if (this.multiSyntax) {
                this.addString("\"" + this.tmpStr + "\"");
            }
            if (this.makeNiporlan) {
                this.writeNiporlan(false, "[\"" + this.tmpStr + "\"]");
            }
            this.ret = SyntaxCode.SY_OK;
            mulPara.setOpType(TypeType.STR_TYPE);
            mulPara.setOpStr(this.tmpStr);
            return;
        }
        if (SyntaxLib.checkInCharSet(String.valueOf(this.ch), SyntaxConsts.NUMBER_CHARSET)) {
            if (this.multiSyntax) {
                this.addString(this.ch);
            }
            this.tempReal = Integer.valueOf(String.valueOf(this.ch)).intValue();
            this.getChar();
            while (SyntaxLib.checkInCharSet(String.valueOf(this.ch), SyntaxConsts.NUMBER_CHARSET)) {
                if (this.multiSyntax) {
                    this.addString(this.ch);
                }
                this.tempReal = this.tempReal * 10.0 + (double)Integer.valueOf(String.valueOf(this.ch)).intValue();
                this.getChar();
            }
            if (this.ch == '.') {
                if (this.multiSyntax) {
                    this.addString(this.ch);
                }
                this.getChar();
                this.decPos = 0.1;
                if (SyntaxLib.checkBetween(this.ch, '0', '9')) {
                    if (this.multiSyntax) {
                        this.addString(this.ch);
                    }
                    this.tempReal += (double)Integer.valueOf(String.valueOf(this.ch)).intValue() * this.decPos;
                    this.decPos /= 10.0;
                    this.getChar();
                }
                mulPara.setOpType(TypeType.REAL_TYPE);
                mulPara.setOpReal(this.tempReal);
                return;
            }
            if (this.tempReal < 2.147483647E9) {
                mulPara.setOpType(TypeType.INT_TYPE);
                mulPara.setOpInt((int)Math.floor(this.tempReal));
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "[" + String.valueOf(this.tempReal) + "]");
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            mulPara.setOpType(TypeType.REAL_TYPE);
            mulPara.setOpReal(this.tempReal);
            if (this.makeNiporlan) {
                this.writeNiporlan(false, "[" + String.valueOf(this.tempReal) + "]");
            }
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        if (this.ch == '(') {
            if (this.multiSyntax) {
                this.addString(this.ch);
            }
            this.getChar();
            this.wholeExp(mulPara);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.multiSyntax) {
                this.addString(this.ch);
            }
            this.getChar();
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        if (this.ch == '[') {
            this.expTableCell(mulPara);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch == ' ') {
                this.skipSpace();
            }
            if (this.ch == '&') {
                this.getChar();
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                this.expTableCell(leftOp);
                if (this.ret != SyntaxCode.SY_OK) {
                    this.ret = SyntaxCode.SY_BAD_USE_LINK_PLUS;
                    return;
                }
                if (mulPara.getOpTable().getIsSign() || leftOp.getOpTable().getIsSign()) {
                    this.ret = SyntaxCode.SY_BAD_USE_LINK_PLUS;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(mulPara.getOpTable().getTabSign() + '[' + String.valueOf(mulPara.getOpTable().getNumber()) + "]&" + leftOp.getOpTable().getTabSign() + '[' + String.valueOf(leftOp.getOpTable().getNumber()) + ']');
                    this.freshChkPos(leftOp.getOpTable());
                }
                if (this.makeNiporlan) {
                    this.tempVal.copyFrom(mulPara);
                    for (int n = mulPara.getOpTable().getNumber(); n <= leftOp.getOpTable().getNumber(); ++n) {
                        this.tempVal.getOpTable().setNumber(n);
                        this.ret = this.getTableCell(this.tempVal.getOpTable());
                        if (this.ret != SyntaxCode.SY_OK) {
                            return;
                        }
                        this.tmpStr = "[#" + String.valueOf(this.tempVal.getOpTable().getTabIndex()) + "." + String.valueOf(this.tempVal.getOpTable().getIndex());
                        this.tmpStr = this.tmpStr + "]";
                        this.writeNiporlan(false, this.tmpStr);
                        if (n == mulPara.getOpTable().getNumber()) continue;
                        this.writeNiporlan(false, "+");
                    }
                }
                this.chkType(mulPara, leftOp, OpsType.SY_CPLUS);
                if (this.ret != SyntaxCode.SY_OK) {
                    this.ret = SyntaxCode.SY_BAD_USE_LINK_PLUS;
                    return;
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.multiSyntax) {
                if (mulPara.getOpTable().getIsSign()) {
                    this.addString(mulPara.getOpTable().getTabSign() + "[" + mulPara.getOpTable().getSign() + "]");
                } else {
                    this.addString(mulPara.getOpTable().getTabSign() + "[" + String.valueOf(mulPara.getOpTable().getNumber()) + "]");
                }
                this.addTableCell(mulPara.getOpTable());
                this.freshChkPos(mulPara.getOpTable());
            }
            if (this.makeNiporlan) {
                this.tmpStr = "[#" + String.valueOf(mulPara.getOpTable().getTabIndex()) + '.' + String.valueOf(mulPara.getOpTable().getIndex());
                this.tmpStr = this.tmpStr + "]";
                this.writeNiporlan(false, this.tmpStr);
            }
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        if (SyntaxLib.checkBetween(this.ch, 'A', 'Z')) {
            this.tmpStr = String.valueOf(this.ch);
            this.getChar();
            while (SyntaxLib.checkInCharSet(this.ch, SyntaxConsts.SIGN_CHARSET)) {
                this.tmpStr = this.tmpStr + String.valueOf(this.ch);
                this.getChar();
            }
            if (this.ch == '$') {
                if (this.tmpStr.equalsIgnoreCase("L")) {
                    this.getChar();
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != '(') {
                        this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                        return;
                    }
                    this.getChar();
                    if (this.multiSyntax) {
                        this.addString("L$(");
                    }
                    this.wholeExp(mulPara);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (mulPara.getOpType() != TypeType.STR_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_STRING;
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ',') {
                        this.ret = SyntaxCode.SY_LACKOF_COMMA;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(',');
                    }
                    this.getChar();
                    this.wholeExp(leftOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (leftOp.getOpType() != TypeType.INT_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_INTEGER;
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ')') {
                        this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(')');
                    }
                    if (leftOp.getOpInt() < 1) {
                        this.ret = SyntaxCode.SY_INT_OVER_BORDER;
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, "\"L$\"");
                    }
                    this.tmpStr = mulPara.getOpStr();
                    this.getChar();
                    if (StringUtils.isNotEmpty((String)this.tmpStr) && this.tmpStr.length() >= leftOp.getOpInt()) {
                        mulPara.setOpStr(this.tmpStr.substring(0, leftOp.getOpInt()));
                    }
                    this.ret = SyntaxCode.SY_OK;
                    return;
                }
                if (this.tmpStr.equalsIgnoreCase("M")) {
                    this.getChar();
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != '(') {
                        this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                        return;
                    }
                    this.getChar();
                    if (this.multiSyntax) {
                        this.addString("M$(");
                    }
                    this.wholeExp(mulPara);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (mulPara.getOpType() != TypeType.STR_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_STRING;
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ',') {
                        this.ret = SyntaxCode.SY_LACKOF_COMMA;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(',');
                    }
                    this.getChar();
                    this.wholeExp(leftOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (leftOp.getOpType() != TypeType.INT_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_INTEGER;
                        return;
                    }
                    if (leftOp.getOpInt() < 1) {
                        this.ret = SyntaxCode.SY_INT_OVER_BORDER;
                        return;
                    }
                    this.tmpInt = leftOp.getOpInt();
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ',') {
                        this.ret = SyntaxCode.SY_LACKOF_COMMA;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(',');
                    }
                    this.getChar();
                    this.multor(leftOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (leftOp.getOpType() != TypeType.INT_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_INTEGER;
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ')') {
                        this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(')');
                    }
                    if (leftOp.getOpInt() < 1) {
                        this.ret = SyntaxCode.SY_INT_OVER_BORDER;
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, "\"M$\"");
                    }
                    this.tmpStr = mulPara.getOpStr();
                    this.getChar();
                    if (StringUtils.isNotEmpty((String)this.tmpStr) && this.tmpStr.length() >= this.tmpInt + leftOp.getOpInt() - 1) {
                        mulPara.setOpStr(this.tmpStr.substring(this.tmpInt - 1, this.tmpInt + leftOp.getOpInt() - 1));
                    }
                    this.ret = SyntaxCode.SY_OK;
                    return;
                }
                if (this.tmpStr.equalsIgnoreCase("R")) {
                    this.getChar();
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != '(') {
                        this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                        return;
                    }
                    this.getChar();
                    if (this.multiSyntax) {
                        this.addString("R$(");
                    }
                    this.wholeExp(mulPara);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (mulPara.getOpType() != TypeType.STR_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_STRING;
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ',') {
                        this.ret = SyntaxCode.SY_LACKOF_COMMA;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(',');
                    }
                    this.getChar();
                    this.multor(leftOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (leftOp.getOpType() != TypeType.INT_TYPE) {
                        this.ret = SyntaxCode.SY_LACKOF_INTEGER;
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != ')') {
                        this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(')');
                    }
                    if (leftOp.getOpInt() < 1) {
                        this.ret = SyntaxCode.SY_INT_OVER_BORDER;
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, "\"R$\"");
                    }
                    this.tmpStr = mulPara.getOpStr();
                    this.getChar();
                    if (StringUtils.isNotEmpty((String)this.tmpStr) && this.tmpStr.length() >= this.tmpStr.length() - leftOp.getOpInt()) {
                        mulPara.setOpStr(this.tmpStr.substring(this.tmpStr.length() - leftOp.getOpInt(), this.tmpStr.length()));
                    }
                    this.ret = SyntaxCode.SY_OK;
                    return;
                }
                this.getChar();
                mulPara.setOpType(TypeType.CODE_TYPE);
                mulPara.getOpCode().setSign(this.tmpStr);
                this.ret = this.getCodeMean(mulPara.getOpCode());
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(mulPara.getOpCode().getSign() + "\u542b\u4e49");
                    this.addCodeCell(mulPara.getOpCode().getSign() + '$');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "[$$" + String.valueOf(mulPara.getOpCode().getIndex()) + "]");
                }
                mulPara.setOpType(TypeType.STR_TYPE);
                mulPara.setOpStr(mulPara.getOpCode().getValue());
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("NOT")) {
                if (this.multiSyntax) {
                    this.addString("\u975e ");
                }
                this.mainItem(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (mulPara.getOpType() != TypeType.BOOL_TYPE) {
                    this.ret = SyntaxCode.SY_TYPE_NO_MATCH;
                    return;
                }
                if (this.makeNiporlan) {
                    char cw = '\u0010';
                    this.writeNiporlan(false, String.valueOf(cw));
                }
                mulPara.setOpBool(!mulPara.isOpBool());
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("ABS")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString("\u7edd\u5bf9\u503c(");
                }
                this.getChar();
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString(')');
                }
                leftOp.setOpType(TypeType.INT_TYPE);
                leftOp.setOpInt(1);
                this.chkType(mulPara, leftOp, OpsType.SY_PRODUCT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u0011'));
                }
                switch (mulPara.getOpType()) {
                    case INT_TYPE: {
                        mulPara.setOpInt(Math.abs(mulPara.getOpInt()));
                        break;
                    }
                    case REAL_TYPE: {
                        mulPara.setOpReal(Math.abs(mulPara.getOpReal()));
                        break;
                    }
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("INT")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString("\u53d6\u6574(");
                }
                this.getChar();
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString(')');
                }
                leftOp.setOpType(TypeType.INT_TYPE);
                leftOp.setOpInt(1);
                this.chkType(mulPara, leftOp, OpsType.SY_PRODUCT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u0012'));
                }
                switch (mulPara.getOpType()) {
                    case INT_TYPE: {
                        mulPara.setOpInt(mulPara.getOpInt());
                        break;
                    }
                    case REAL_TYPE: {
                        mulPara.setOpReal((int)mulPara.getOpReal());
                        break;
                    }
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("ROUND")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString("\u53d6\u6574(");
                }
                this.getChar();
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString(')');
                }
                leftOp.setOpType(TypeType.INT_TYPE);
                leftOp.setOpInt(1);
                this.chkType(mulPara, leftOp, OpsType.SY_PRODUCT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u001b'));
                }
                switch (mulPara.getOpType()) {
                    case INT_TYPE: {
                        mulPara.setOpInt(mulPara.getOpInt());
                        break;
                    }
                    case REAL_TYPE: {
                        mulPara.setOpReal(Math.round(mulPara.getOpReal()));
                        break;
                    }
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("POWER")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("\u4e58\u65b9(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                this.tempVal.setOpType(TypeType.INT_TYPE);
                this.tempVal.setOpInt(1);
                this.chkType(mulPara, this.tempVal, OpsType.SY_PRODUCT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ',') {
                    this.ret = SyntaxCode.SY_LACKOF_COMMA;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(',');
                }
                this.getChar();
                this.wholeExp(leftOp);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                this.tempVal.setOpType(TypeType.INT_TYPE);
                this.tempVal.setOpInt(1);
                this.chkType(leftOp, this.tempVal, OpsType.SY_PRODUCT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u001a'));
                }
                this.getChar();
                if (mulPara.getOpType() == TypeType.INT_TYPE) {
                    mulPara.setOpType(TypeType.REAL_TYPE);
                    mulPara.setOpReal(mulPara.getOpInt());
                }
                if (leftOp.getOpType() == TypeType.INT_TYPE) {
                    leftOp.setOpType(TypeType.REAL_TYPE);
                    leftOp.setOpReal(leftOp.getOpInt());
                }
                if (mulPara.getOpReal() < 0.0 && Math.abs(SyntaxLib.getFrac(leftOp.getOpReal())) > 1.0E-5) {
                    mulPara.setOpReal(1.0);
                } else {
                    mulPara.setOpReal(Math.pow(mulPara.getOpReal(), (int)leftOp.getOpReal()));
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("VAL")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("\u6c42\u503c(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u001d'));
                }
                this.getChar();
                mulPara.setOpType(TypeType.REAL_TYPE);
                mulPara.setOpReal(SyntaxLib.myStrToFloat(mulPara.getOpStr()));
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("LEN")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("\u957f\u5ea6(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                leftOp.setOpType(TypeType.STR_TYPE);
                leftOp.setOpStr("");
                this.chkType(mulPara, leftOp, OpsType.SY_PLUS);
                if (this.ret != SyntaxCode.SY_OK) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"LEN\"");
                }
                this.tmpStr = mulPara.getOpStr();
                this.getChar();
                mulPara.setOpType(TypeType.INT_TYPE);
                mulPara.setOpInt(this.tmpStr.length());
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("IDC")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString("\u68c0\u9a8c\u4f4d(");
                }
                this.getChar();
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_IDC_NEED_EIGHT_DIGIT;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u001c'));
                }
                mulPara.setOpStr(String.valueOf(SyntaxLib.getIdc(mulPara.getOpStr())));
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("ISDIGIT")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("\u662f\u6570\u5b57(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"ISDIGIT\"");
                }
                this.tmpStr = mulPara.getOpStr();
                this.getChar();
                mulPara.setOpType(TypeType.BOOL_TYPE);
                mulPara.setOpBool(SyntaxLib.getIsDigit(this.tmpStr));
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("EXIST")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (!SyntaxLib.checkBetween(this.ch, 'A', 'Z')) {
                    this.ret = SyntaxCode.SY_EXIST_DATA_NEED_TABSIGN;
                    return;
                }
                leftOp.setOpType(TypeType.EXIST_TYPE);
                leftOp.getOpExist().setTabSign(String.valueOf(this.ch));
                leftOp.getOpExist().setTabIndex(0);
                leftOp.getOpExist().setExist(true);
                this.getChar();
                while (SyntaxLib.checkInCharSet(this.ch, SyntaxConsts.SIGN_CHARSET)) {
                    leftOp.getOpExist().setTabSign(leftOp.getOpExist().getTabSign() + this.ch);
                    this.getChar();
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                this.ret = this.getExistData(leftOp.getOpExist());
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.multiSyntax) {
                    this.addString("\u6709\u6570\u636e(" + leftOp.getOpExist().getTabSign() + ')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "[" + String.valueOf(leftOp.getOpExist().getTabIndex()) + "]");
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"EXIST\"");
                }
                mulPara.setOpType(TypeType.BOOL_TYPE);
                mulPara.setOpBool(leftOp.getOpExist().isExist());
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("ISIDCARD")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("\u662f\u8eab\u4efd\u8bc1(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                leftOp.setOpType(TypeType.STR_TYPE);
                leftOp.setOpStr("");
                this.chkType(mulPara, leftOp, OpsType.SY_PLUS);
                if (this.ret != SyntaxCode.SY_OK) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"ISIDCARD\"");
                }
                this.tmpStr = mulPara.getOpStr();
                this.getChar();
                mulPara.setOpType(TypeType.BOOL_TYPE);
                mulPara.setOpBool(SyntaxLib.getIsIDCard(this.tmpStr));
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("BZZ")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("\u6807\u51c6\u503c(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ',') {
                    this.ret = SyntaxCode.SY_LACKOF_COMMA;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(',');
                }
                this.tempVal.copyFrom(mulPara);
                this.getChar();
                this.multor(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ',') {
                    this.ret = SyntaxCode.SY_LACKOF_COMMA;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(',');
                }
                this.getChar();
                this.multor(leftOp);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (leftOp.getOpType() != TypeType.INT_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_INTEGER;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"BZZ\"");
                }
                this.tmpStr = mulPara.getOpStr();
                this.getChar();
                mulPara.setOpType(TypeType.BZZ_TYPE);
                mulPara.getOpBZZ().setSign(this.tempVal.getOpStr());
                mulPara.getOpBZZ().setZbSign(this.tmpStr);
                mulPara.getOpBZZ().setBzzType(leftOp.getOpInt());
                mulPara.getOpBZZ().setValue(0.0);
                this.ret = this.getBzzData(mulPara.getOpBZZ());
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                mulPara.setOpType(TypeType.REAL_TYPE);
                mulPara.setOpReal(mulPara.getOpBZZ().getValue());
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("MONTH")) {
                if (this.multiSyntax) {
                    this.addString("\u6708");
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"MONTH\"");
                }
                mulPara.setOpType(TypeType.INT_TYPE);
                MonthDataType monthData = new MonthDataType();
                monthData.setMonthIndex(mulPara.getOpInt());
                this.ret = this.getMonth(monthData);
                mulPara.setOpInt(monthData.getMonthIndex());
                return;
            }
            if (this.tmpStr.equalsIgnoreCase("BZZGM")) {
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != '(') {
                    this.ret = SyntaxCode.SY_LACKOF_LEFT_SMALL_BRACKEK;
                    return;
                }
                this.getChar();
                if (this.multiSyntax) {
                    this.addString("BZZGM(");
                }
                this.wholeExp(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (mulPara.getOpType() != TypeType.STR_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_STRING;
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch != ')') {
                    this.ret = SyntaxCode.SY_LACKOF_RIGHT_SMALL_BRACKEK;
                    return;
                }
                if (this.multiSyntax) {
                    this.addString(')');
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "\"BZZGM\"");
                }
                this.tmpStr = mulPara.getOpStr();
                this.getChar();
                mulPara.setOpType(TypeType.STR_TYPE);
                mulPara.setOpStr("");
                StringDataType strData = new StringDataType();
                strData.setSourceCode(this.tmpStr);
                strData.setDestCode(mulPara.getOpStr());
                this.ret = this.GetBzzGM(strData);
                mulPara.setOpStr(strData.getDestCode());
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            int saveExpi = this.expI - this.tmpStr.length();
            char saveCh = this.tmpStr.charAt(0);
            if (this.ch == ' ') {
                this.skipSpace();
            }
            if (this.ch == '[') {
                this.ch = saveCh;
                this.expI = saveExpi;
                this.expTableCell(mulPara);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.ch == ' ') {
                    this.skipSpace();
                }
                if (this.ch == '&') {
                    this.getChar();
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    this.expTableCell(leftOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        this.ret = SyntaxCode.SY_BAD_USE_LINK_PLUS;
                        return;
                    }
                    if (mulPara.getOpTable().getIsSign() || leftOp.getOpTable().getIsSign()) {
                        this.ret = SyntaxCode.SY_BAD_USE_LINK_PLUS;
                        return;
                    }
                    if (this.multiSyntax) {
                        this.addString(mulPara.getOpTable().getTabSign() + '[' + String.valueOf(mulPara.getOpTable().getNumber()) + "]&" + leftOp.getOpTable().getTabSign() + '[' + String.valueOf(leftOp.getOpTable().getNumber()) + "]");
                        this.freshChkPos(leftOp.getOpTable());
                    }
                    if (this.makeNiporlan) {
                        this.tempVal.copyFrom(mulPara);
                        for (int n = mulPara.getOpTable().getNumber(); n <= leftOp.getOpTable().getNumber(); ++n) {
                            this.tempVal.getOpTable().setNumber(n);
                            this.ret = this.getTableCell(this.tempVal.getOpTable());
                            if (this.ret != SyntaxCode.SY_OK) {
                                return;
                            }
                            this.tmpStr = "[#" + String.valueOf(this.tempVal.getOpTable().getTabIndex()) + "." + String.valueOf(this.tempVal.getOpTable().getIndex());
                            this.tmpStr = this.tmpStr + "]";
                            this.writeNiporlan(false, this.tmpStr);
                            if (n == mulPara.getOpTable().getNumber()) continue;
                            this.writeNiporlan(false, "+");
                        }
                    }
                    this.chkType(mulPara, leftOp, OpsType.SY_CPLUS);
                    if (this.ret != SyntaxCode.SY_OK) {
                        this.ret = SyntaxCode.SY_BAD_USE_LINK_PLUS;
                        return;
                    }
                    this.ret = SyntaxCode.SY_OK;
                    return;
                }
                if (this.multiSyntax) {
                    if (mulPara.getOpTable().getIsSign()) {
                        this.addString(mulPara.getOpTable().getTabSign() + "[" + mulPara.getOpTable().getSign() + "]");
                    } else {
                        this.addString(mulPara.getOpTable().getTabSign() + "[" + this.IntToStr(mulPara.getOpTable().getNumber()) + "]");
                    }
                    this.addTableCell(mulPara.getOpTable());
                    this.freshChkPos(mulPara.getOpTable());
                }
                if (this.makeNiporlan) {
                    this.tmpStr = "[#" + this.IntToStr(mulPara.getOpTable().getTabIndex()) + "." + this.IntToStr(mulPara.getOpTable().getIndex());
                    this.tmpStr = this.tmpStr + "]";
                    this.writeNiporlan(false, this.tmpStr);
                }
                this.ret = SyntaxCode.SY_OK;
                return;
            }
            mulPara.setOpType(TypeType.CODE_TYPE);
            mulPara.getOpCode().setSign(this.tmpStr);
            mulPara.getOpCode().setValue("");
            mulPara.getOpCode().setIndex(0);
            this.ret = this.getCodeCell(mulPara.getOpCode());
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.multiSyntax) {
                this.addString(mulPara.getOpCode().getSign());
                this.addCodeCell(mulPara.getOpCode().getSign());
            }
            if (this.makeNiporlan) {
                this.writeNiporlan(false, "[$" + this.IntToStr(mulPara.getOpCode().getIndex()) + "]");
            }
            mulPara.setOpType(TypeType.STR_TYPE);
            mulPara.setOpStr(mulPara.getOpCode().getValue());
            this.ret = SyntaxCode.SY_OK;
            return;
        }
        this.ret = SyntaxCode.SY_INVALID_CHAR;
    }

    private String IntToStr(int value) {
        return String.valueOf(value);
    }

    private void product(MultiType prdtPara) {
        MultiType leftOp = new MultiType();
        MultiType rightOp = new MultiType();
        char saveCh = '\u0000';
        this.multor(leftOp);
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (this.ch == ' ') {
            this.skipSpace();
        }
        while (this.ch == '*' || this.ch == '/') {
            saveCh = this.ch;
            if (this.multiSyntax) {
                this.addString(String.valueOf(saveCh));
            }
            this.getChar();
            this.multor(rightOp);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (saveCh == '*') {
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u0001'));
                }
                this.chkType(leftOp, rightOp, OpsType.SY_PRODUCT);
            } else if (saveCh == '/') {
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, String.valueOf('\u0002'));
                }
                this.chkType(leftOp, rightOp, OpsType.SY_DIV);
            }
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch != ' ') continue;
            this.skipSpace();
        }
        this.ret = SyntaxCode.SY_OK;
        prdtPara.copyFrom(leftOp);
    }

    private void subItem(MultiType siPara) {
        MultiType leftOp = new MultiType();
        MultiType rightOp = new MultiType();
        int prefixsy = 0;
        char saveCh = '\u0000';
        if (this.ch == ' ') {
            this.skipSpace();
        }
        if (this.ch == '+') {
            ++prefixsy;
            rightOp.setOpType(TypeType.INT_TYPE);
            rightOp.setOpInt(1);
            this.getChar();
            if (this.multiSyntax) {
                this.addString('+');
            }
        }
        if (this.ch == '-') {
            prefixsy += 2;
            rightOp.setOpType(TypeType.INT_TYPE);
            rightOp.setOpInt(-1);
            this.getChar();
            if (this.multiSyntax) {
                this.addString('-');
            }
        }
        if (prefixsy == 3) {
            this.ret = SyntaxCode.SY_OVER_LAP_OPERATOR;
            return;
        }
        this.product(leftOp);
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (prefixsy == 2) {
            this.chkType(leftOp, rightOp, OpsType.SY_PRODUCT);
            if (this.makeNiporlan) {
                this.writeNiporlan(false, String.valueOf('\u0003'));
            }
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
        }
        if (this.ch == ' ') {
            this.skipSpace();
        }
        while (this.ch == '+' || this.ch == '-') {
            saveCh = this.ch;
            if (this.multiSyntax) {
                this.addString(saveCh);
            }
            this.getChar();
            this.product(rightOp);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (saveCh == '+') {
                this.chkType(leftOp, rightOp, OpsType.SY_PLUS);
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "+");
                }
            } else if (saveCh == '-') {
                this.chkType(leftOp, rightOp, OpsType.SY_MINUS);
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "-");
                }
            }
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch != ' ') continue;
            this.skipSpace();
        }
        this.ret = SyntaxCode.SY_OK;
        siPara.copyFrom(leftOp);
    }

    private void mainItem(MultiType miPara) {
        char[] checkChars;
        MultiType leftOp = new MultiType();
        MultiType rightOp = new MultiType();
        this.subItem(leftOp);
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (this.ch == ' ') {
            this.skipSpace();
        }
        if (SyntaxLib.checkInCharSet(this.ch, checkChars = new char[]{'=', '>', '<'})) {
            if (this.ch == '=') {
                this.getChar();
                if (this.multiSyntax) {
                    this.addString('=');
                }
                this.subItem(rightOp);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                this.chkType(leftOp, rightOp, OpsType.SY_EQU);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "=");
                }
                this.ret = SyntaxCode.SY_OK;
                miPara.copyFrom(leftOp);
                return;
            }
            if (this.ch == '<') {
                this.getChar();
                if (this.ch == '>') {
                    this.getChar();
                    if (this.multiSyntax) {
                        this.addString("<>");
                    }
                    this.subItem(rightOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    this.chkType(leftOp, rightOp, OpsType.SY_NEQ);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, String.valueOf('\u0004'));
                    }
                    this.ret = SyntaxCode.SY_OK;
                    miPara.copyFrom(leftOp);
                    return;
                }
                if (this.ch == '=') {
                    this.getChar();
                    if (this.multiSyntax) {
                        this.addString("<=");
                    }
                    this.subItem(rightOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    this.chkType(leftOp, rightOp, OpsType.SY_BLE);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, String.valueOf('\u0006'));
                    }
                    this.ret = SyntaxCode.SY_OK;
                    miPara.copyFrom(leftOp);
                    return;
                }
                if (this.multiSyntax) {
                    this.addString('<');
                }
                this.subItem(rightOp);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, "<");
                }
                this.chkType(leftOp, rightOp, OpsType.SY_BLT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                this.ret = SyntaxCode.SY_OK;
                miPara.copyFrom(leftOp);
                return;
            }
            if (this.ch == '>') {
                this.getChar();
                if (this.ch == '=') {
                    this.getChar();
                    if (this.multiSyntax) {
                        this.addString(">=");
                    }
                    this.subItem(rightOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    this.chkType(leftOp, rightOp, OpsType.SY_GLE);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, String.valueOf('\u0005'));
                    }
                    this.ret = SyntaxCode.SY_OK;
                    miPara.copyFrom(leftOp);
                    return;
                }
                if (this.multiSyntax) {
                    this.addString('>');
                }
                this.subItem(rightOp);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                if (this.makeNiporlan) {
                    this.writeNiporlan(false, ">");
                }
                this.chkType(leftOp, rightOp, OpsType.SY_GLT);
                if (this.ret != SyntaxCode.SY_OK) {
                    return;
                }
                this.ret = SyntaxCode.SY_OK;
                miPara.copyFrom(leftOp);
                return;
            }
        }
        this.ret = SyntaxCode.SY_OK;
        miPara.copyFrom(leftOp);
    }

    private void simpleExp(MultiType SimPara) {
        MultiType leftOp = new MultiType();
        MultiType rightOp = new MultiType();
        this.mainItem(leftOp);
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (this.ch == ' ') {
            this.skipSpace();
        }
        while (this.ch == 'A') {
            this.tmpStr = String.valueOf(this.ch);
            this.getChar();
            this.tmpStr = this.tmpStr + this.ch;
            this.getChar();
            this.tmpStr = this.tmpStr + this.ch;
            if (!"AND".equalsIgnoreCase(this.tmpStr)) {
                this.ret = SyntaxCode.SY_LACKOF_AND;
                return;
            }
            if (this.multiSyntax) {
                this.addString(" \u5e76\u4e14");
            }
            this.getChar();
            this.mainItem(rightOp);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            this.chkType(leftOp, rightOp, OpsType.SY_AND);
            if (this.makeNiporlan) {
                this.writeNiporlan(false, String.valueOf('\u000e'));
            }
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch != ' ') continue;
            this.skipSpace();
        }
        this.ret = SyntaxCode.SY_OK;
        SimPara.copyFrom(leftOp);
    }

    private void wholeExp(MultiType wholePara) {
        MultiType leftOp = new MultiType();
        MultiType rightOp = new MultiType();
        this.simpleExp(leftOp);
        if (this.ret != SyntaxCode.SY_OK) {
            return;
        }
        if (this.ch == ' ') {
            this.skipSpace();
        }
        while (this.ch == 'O') {
            this.tmpStr = String.valueOf(this.ch);
            this.getChar();
            this.tmpStr = this.tmpStr + String.valueOf(this.ch);
            if (!"OR".equalsIgnoreCase(this.tmpStr)) {
                this.ret = SyntaxCode.SY_LACKOF_OR;
                return;
            }
            if (this.multiSyntax) {
                this.addString(" \u6216\u8005 ");
            }
            this.getChar();
            this.simpleExp(rightOp);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            this.chkType(leftOp, rightOp, OpsType.SY_OR);
            if (this.makeNiporlan) {
                this.writeNiporlan(false, String.valueOf('\u000f'));
            }
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch != ' ') continue;
            this.skipSpace();
        }
        this.ret = SyntaxCode.SY_OK;
        wholePara.copyFrom(leftOp);
    }

    private void expTableCell(MultiType expType) {
        MultiType lastOp = new MultiType();
        if (this.ch == ' ') {
            this.skipSpace();
        }
        this.inTableCell = true;
        try {
            lastOp.setOpType(TypeType.TABLE_TYPE);
            lastOp.getOpTable().setTabSign("");
            lastOp.getOpTable().setSign("");
            lastOp.getOpTable().setNumber(0);
            lastOp.getOpTable().setTabIndex(0);
            lastOp.getOpTable().setIndex(0);
            lastOp.getOpTable().setValue(0.0);
            if (this.ch != '[') {
                if (!SyntaxLib.checkBetween(this.ch, 'A', 'Z')) {
                    this.ret = SyntaxCode.SY_LACKOF_TABLE;
                    return;
                }
                this.tmpStr = String.valueOf(this.ch);
                this.getChar();
                while (SyntaxLib.checkInCharSet(this.ch, SyntaxConsts.SIGN_CHARSET)) {
                    this.tmpStr = this.tmpStr + String.valueOf(this.ch);
                    this.getChar();
                }
                lastOp.getOpTable().setTabSign(this.tmpStr);
                if (this.ch == ' ') {
                    this.skipSpace();
                }
            }
            if (this.ch != '[') {
                this.ret = SyntaxCode.SY_LACKOF_LEFT_MIDDLE_BRACKEK;
                return;
            }
            this.getChar();
            if (this.ch == ' ') {
                this.skipSpace();
            }
            if (SyntaxLib.checkBetween(this.ch, 'A', 'Z')) {
                this.tmpStr = String.valueOf(this.ch);
                this.getChar();
                while (SyntaxLib.checkInCharSet(this.ch, SyntaxConsts.SIGN_CHARSET)) {
                    this.tmpStr = this.tmpStr + String.valueOf(this.ch);
                    this.getChar();
                }
                lastOp.getOpTable().setIsSign(true);
                lastOp.getOpTable().setSign(this.tmpStr);
            } else {
                this.subItem(expType);
                if (this.ret != SyntaxCode.SY_OK || expType.getOpType() != TypeType.INT_TYPE) {
                    this.ret = SyntaxCode.SY_LACKOF_INTEGER;
                    return;
                }
                lastOp.getOpTable().setIsSign(false);
                lastOp.getOpTable().setNumber(expType.getOpInt());
            }
            if (this.ch == ' ') {
                this.skipSpace();
            }
            if (this.ch != ']') {
                this.ret = SyntaxCode.SY_LACKOF_RIGHT_MIDDLE_BRACKEK;
                return;
            }
            this.ret = this.getTableCell(lastOp.getOpTable());
            if (this.ret == SyntaxCode.SY_OK) {
                expType.copyFrom(lastOp);
                this.getChar();
            }
        }
        finally {
            this.inTableCell = false;
        }
    }

    private void subExpChk(MultiType lastOp, MultiType expresult) {
        this.expI = 0;
        this.getChar();
        if (this.checkType == TCheckType.CHECK_EVALUATE) {
            switch (this.assignType) {
                case ASSIGN_TABLE: {
                    this.expTableCell(expresult);
                }
            }
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch == ' ') {
                this.skipSpace();
            }
            if (this.ch != '=') {
                this.ret = SyntaxCode.SY_LACKOF_EQUAL_SIGN;
                return;
            }
            if (this.makeNiporlan) {
                switch (this.assignType) {
                    case ASSIGN_TABLE: {
                        this.tmpStr = "[#" + this.IntToStr(expresult.getOpTable().getTabIndex()) + "." + this.IntToStr(expresult.getOpTable().getIndex());
                        this.tmpStr = this.tmpStr + "]";
                        this.writeNiporlan(false, this.tmpStr);
                    }
                }
            }
            this.getChar();
            this.wholeExp(lastOp);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            this.chkType(expresult, lastOp, OpsType.SY_ASSIGN_VALUE);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.makeNiporlan) {
                this.writeNiporlan(false, ":");
            }
            if (this.ch != '\u0000') {
                this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                return;
            }
        } else {
            this.wholeExp(expresult);
            if (this.ret != SyntaxCode.SY_OK) {
                return;
            }
            if (this.ch != '\u0000') {
                this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                return;
            }
            if (this.checkType == TCheckType.CHECK_JUDGE) {
                if (expresult.getOpType() != TypeType.BOOL_TYPE) {
                    this.ret = SyntaxCode.SY_NO_LOGIC_EXPRESS;
                    return;
                }
            } else {
                switch (expresult.getOpType()) {
                    case TABLE_TYPE: {
                        lastOp.setOpType(TypeType.REAL_TYPE);
                        lastOp.setOpReal(expresult.getOpTable().getValue());
                        expresult.copyFrom(lastOp);
                        break;
                    }
                    case BOOL_TYPE: {
                        lastOp.setOpType(TypeType.INT_TYPE);
                        if (expresult.isOpBool()) {
                            lastOp.setOpInt(-1);
                        } else {
                            lastOp.setOpInt(0);
                        }
                        expresult.copyFrom(lastOp);
                        break;
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void expChk(MultiType expresult) {
        block98: {
            MultiType lastOp = new MultiType();
            try {
                this.canAssignValue = true;
                this.inEffectCheck = true;
                this.ret = SyntaxCode.SY_OK;
                if (SyntaxLib.getSignPos("IF", this.expStr) == 1) {
                    this.ifexpStr = "";
                    this.thenexpStr = "";
                    this.elseexpStr = "";
                    this.saveexpStr = this.expStr;
                    this.thenPos = SyntaxLib.getSignPos("THEN", this.expStr);
                    if (this.thenPos == 0) {
                        this.ret = SyntaxCode.SY_LACKOF_THEN;
                        return;
                    }
                    this.ifexpStr = this.expStr.substring(2, this.thenPos);
                    if (this.ifexpStr == "") {
                        this.ret = SyntaxCode.SY_NEED_OF_IF_EXP;
                        return;
                    }
                    this.elsePos = SyntaxLib.getSignPos("ELSE", this.expStr);
                    if (this.elsePos == 0) {
                        this.thenexpStr = this.expStr;
                        this.thenexpStr = this.thenexpStr.substring(this.thenPos + 4, this.thenexpStr.length());
                        if (this.thenexpStr == "") {
                            this.ret = SyntaxCode.SY_NEED_OF_THEN_EXP;
                            return;
                        }
                    } else {
                        this.thenexpStr = this.expStr.substring(this.thenPos + 4, this.elsePos);
                        this.elseexpStr = this.expStr;
                        this.elseexpStr = this.elseexpStr.substring(this.elsePos + 4, this.elseexpStr.length());
                        if (this.thenexpStr == "") {
                            this.ret = SyntaxCode.SY_NEED_OF_THEN_EXP;
                            return;
                        }
                        if (this.elseexpStr == "") {
                            this.ret = SyntaxCode.SY_NEED_OF_ELSE_EXP;
                            return;
                        }
                    }
                    this.expStr = this.ifexpStr;
                    if (this.multiSyntax) {
                        this.addString("\u5982\u679c ");
                    }
                    this.expI = 0;
                    this.getChar();
                    this.wholeExp(expresult);
                    try {
                        if (this.ret != SyntaxCode.SY_OK) {
                            return;
                        }
                        if (this.ch != '\u0000') {
                            this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                            return;
                        }
                        if (this.makeNiporlan) {
                            this.writeNiporlan(false, "|" + String.valueOf('\u0013'));
                        }
                        if (expresult.getOpType() != TypeType.BOOL_TYPE) {
                            this.ret = SyntaxCode.SY_NO_LOGIC_EXPRESS;
                            return;
                        }
                    }
                    finally {
                        if (this.ret != SyntaxCode.SY_OK) {
                            this.expStr = this.saveexpStr;
                            this.expI += 2;
                        }
                    }
                    this.ifThenTrue = expresult.isOpBool();
                    this.expStr = this.thenexpStr;
                    if (this.multiSyntax) {
                        this.addString(" \u5219 ");
                    }
                    this.expI = 0;
                    this.getChar();
                    this.canAssignValue = this.ifThenTrue;
                    this.subExpChk(lastOp, expresult);
                    try {
                        if (this.ret != SyntaxCode.SY_OK) {
                            return;
                        }
                        if (this.ch != '\u0000') {
                            this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                            return;
                        }
                    }
                    finally {
                        if (this.ret != SyntaxCode.SY_OK) {
                            this.expStr = this.saveexpStr;
                            this.expI = this.expI + this.thenPos + 3;
                        }
                    }
                    if (this.checkType == TCheckType.CHECK_JUDGE && this.ifThenTrue) {
                        this.judgeresult = expresult.isOpBool();
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(false, "|" + String.valueOf('\u0014'));
                    }
                    if (this.elsePos != 0) {
                        this.expStr = this.elseexpStr;
                        if (this.multiSyntax) {
                            this.addString(" \u5426\u5219 ");
                        }
                        this.expI = 0;
                        this.getChar();
                        this.canAssignValue = !this.ifThenTrue;
                        this.subExpChk(lastOp, expresult);
                        try {
                            if (this.ret != SyntaxCode.SY_OK) {
                                return;
                            }
                            if (this.ch != '\u0000') {
                                this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                                return;
                            }
                        }
                        finally {
                            if (this.ret != SyntaxCode.SY_OK) {
                                this.expStr = this.saveexpStr;
                                this.expI = this.expI + this.thenPos + 3;
                            }
                        }
                        if (this.checkType == TCheckType.CHECK_JUDGE && !this.ifThenTrue) {
                            this.judgeresult = expresult.isOpBool();
                        }
                        if (this.makeNiporlan) {
                            this.writeNiporlan(false, "|" + String.valueOf('\u0015'));
                        }
                    } else if (this.checkType == TCheckType.CHECK_JUDGE && !this.ifThenTrue) {
                        this.judgeresult = true;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(true, "");
                    }
                    if (this.checkType == TCheckType.CHECK_JUDGE) {
                        expresult.setOpBool(this.judgeresult);
                    }
                    break block98;
                }
                this.expI = 0;
                this.getChar();
                if (this.checkType == TCheckType.CHECK_EVALUATE) {
                    switch (this.assignType) {
                        case ASSIGN_TABLE: {
                            this.expTableCell(expresult);
                        }
                    }
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (this.ch == ' ') {
                        this.skipSpace();
                    }
                    if (this.ch != '=') {
                        this.ret = SyntaxCode.SY_LACKOF_EQUAL_SIGN;
                        return;
                    }
                    if (this.makeNiporlan) {
                        switch (this.assignType) {
                            case ASSIGN_TABLE: {
                                this.tmpStr = "[#" + this.IntToStr(expresult.getOpTable().getTabIndex()) + "." + this.IntToStr(expresult.getOpTable().getIndex());
                                this.tmpStr = this.tmpStr + "]";
                                this.writeNiporlan(false, this.tmpStr);
                            }
                        }
                    }
                    this.getChar();
                    this.wholeExp(lastOp);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    this.chkType(expresult, lastOp, OpsType.SY_ASSIGN_VALUE);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (this.ch != '\u0000') {
                        this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                        return;
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(true, ":");
                    }
                } else {
                    this.wholeExp(expresult);
                    if (this.ret != SyntaxCode.SY_OK) {
                        return;
                    }
                    if (this.ch != '\u0000') {
                        this.ret = SyntaxCode.SY_EXIST_SPARE_CHAR;
                        return;
                    }
                    if (this.checkType == TCheckType.CHECK_JUDGE) {
                        if (expresult.getOpType() != TypeType.BOOL_TYPE) {
                            this.ret = SyntaxCode.SY_NO_LOGIC_EXPRESS;
                            return;
                        }
                    } else {
                        switch (expresult.getOpType()) {
                            case TABLE_TYPE: {
                                lastOp.setOpType(TypeType.REAL_TYPE);
                                lastOp.setOpReal(expresult.getOpTable().getValue());
                                expresult.copyFrom(lastOp);
                                break;
                            }
                            case BOOL_TYPE: {
                                lastOp.setOpType(TypeType.INT_TYPE);
                                if (expresult.isOpBool()) {
                                    lastOp.setOpInt(-1);
                                } else {
                                    lastOp.setOpInt(0);
                                }
                                expresult.copyFrom(lastOp);
                                break;
                            }
                        }
                    }
                    if (this.makeNiporlan) {
                        this.writeNiporlan(true, "");
                    }
                }
            }
            finally {
                this.inEffectCheck = false;
            }
        }
    }

    private void incNiporlanGrid(String spotStr) {
        if (this.multiSyntax) {
            this.niporlanGrid.setRowCount(this.niporlanGrid.getRowCount() + 1);
            this.niporlanGrid.setCells(1, this.niporlanGrid.getRowCount() - 1, "");
            this.niporlanGrid.setCells(2, this.niporlanGrid.getRowCount() - 1, this.expStr);
            this.niporlanGrid.setCells(3, this.niporlanGrid.getRowCount() - 1, this.expressGrid.getCells(1, this.expressGrid.getRow()));
            if (StringUtils.isNotEmpty((String)this.expressGrid.getCells(2, this.expressGrid.getRow()))) {
                this.niporlanGrid.setCells(4, this.niporlanGrid.getRowCount() - 1, spotStr);
            } else {
                this.niporlanGrid.setCells(4, this.niporlanGrid.getRowCount() - 1, "");
            }
            if (this.expressGrid.getColCount() > 3) {
                this.niporlanGrid.setCells(5, this.niporlanGrid.getRowCount() - 1, this.expressGrid.getCells(3, this.expressGrid.getRow()));
            }
            if (this.expressGrid.getColCount() > 4) {
                this.niporlanGrid.setCells(6, this.niporlanGrid.getRowCount() - 1, this.expressGrid.getCells(4, this.expressGrid.getRow()));
            }
        }
    }

    private void writeNiporlan(boolean endLine, String partNiporlan) {
        if (this.makeNiporlan && this.inEffectCheck && !this.inTableCell) {
            if (StringUtils.isEmpty((String)this.niporlan)) {
                this.niporlan = partNiporlan;
            } else if (StringUtils.isNotEmpty((String)this.niporlan)) {
                this.niporlan = this.niporlan + "|" + partNiporlan;
            }
            if (endLine) {
                if (this.multiSyntax) {
                    this.niporlanGrid.setCells(0, this.niporlanGrid.getRowCount() - 1, this.niporlan);
                } else {
                    this.niporlans.add(this.niporlan);
                }
                this.niporlan = "";
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SyntaxCode estimate(String es, MultiType retVal) {
        int startI = 0;
        int endI = 0;
        String rawSpotStr = "";
        MultiType estimatePnt = null;
        SyntaxCode result = SyntaxCode.SY_OK;
        this.expI = 0;
        this.expStr = "";
        int numberNum = 0;
        try {
            int n;
            int i = SyntaxLib.getExtendPos("//", es);
            if (i >= 0) {
                es = es.substring(0, i);
            }
            boolean strStart = false;
            boolean bracketStart = false;
            int bracketNum = 0;
            es = SyntaxLib.ClearEdgeSpace(es);
            for (i = 0; i < es.length(); ++i) {
                this.ch = es.charAt(i);
                if (this.ch == '(') {
                    ++bracketNum;
                }
                if (bracketNum > 6) {
                    result = SyntaxCode.SY_MUCH_OF_BRACKET;
                    retVal.setOpType(TypeType.NIL_TYPE);
                    this.expStr = es;
                    SyntaxCode syntaxCode = result;
                    return syntaxCode;
                }
                if (this.ch == ')') {
                    --bracketNum;
                }
                if (bracketNum < 0) {
                    result = SyntaxCode.SY_BRACKET_NO_MATCH;
                    retVal.setOpType(TypeType.NIL_TYPE);
                    this.expStr = es;
                    SyntaxCode syntaxCode = result;
                    return syntaxCode;
                }
                if (this.ch == '\"') {
                    boolean bl = strStart = !strStart;
                }
                if (!strStart && this.ch == '[') {
                    bracketStart = true;
                } else if (this.ch != ' ') {
                    bracketStart = false;
                }
                if (bracketStart && this.ch == ' ') continue;
                this.expStr = !strStart ? this.expStr + SyntaxLib.UpperCase(this.ch) : this.expStr + String.valueOf(this.ch);
            }
            if (bracketNum != 0) {
                result = SyntaxCode.SY_BRACKET_NO_MATCH;
                retVal.setOpType(TypeType.NIL_TYPE);
                SyntaxCode syntaxCode = result;
                return syntaxCode;
            }
            if (strStart) {
                result = SyntaxCode.SY_BAD_USE_QUOTE_SIGN;
                retVal.setOpType(TypeType.NIL_TYPE);
                SyntaxCode syntaxCode = result;
                return syntaxCode;
            }
            if (StringUtils.isEmpty((String)this.expStr)) {
                result = SyntaxCode.SY_OK;
                if (this.checkType == TCheckType.CHECK_JUDGE) {
                    retVal.setOpType(TypeType.BOOL_TYPE);
                    retVal.setOpBool(true);
                } else {
                    retVal.setOpType(TypeType.NIL_TYPE);
                }
                if (this.makeNiporlan && this.nullNiporlan) {
                    this.writeNiporlan(true, "");
                }
                SyntaxCode syntaxCode = result;
                return syntaxCode;
            }
            if (!this.syntaxOptions.contains((Object)TSyntaxOption.SO_STAR)) {
                if (SyntaxLib.getExtendPos("[*", this.expStr) != -1) {
                    this.expI = SyntaxLib.getExtendPos("[*", this.expStr) + 1;
                    SyntaxCode syntaxCode = result = SyntaxCode.SY_NO_SUPPORT_STAR;
                    return syntaxCode;
                }
                if (SyntaxLib.getExtendPos('{', this.expStr) != -1) {
                    this.expI = SyntaxLib.getExtendPos('{', this.expStr);
                    SyntaxCode syntaxCode = result = SyntaxCode.SY_BAD_USE_BIG_BRACKET;
                    return syntaxCode;
                }
                estimatePnt = retVal;
                if (this.multiSyntax) {
                    this.tmpStr = this.expressGrid.getCells(2, this.expressGrid.getRow());
                    this.incNiporlanGrid(this.tmpStr);
                }
                this.expChk(estimatePnt);
                SyntaxCode syntaxCode = result = this.ret;
                return syntaxCode;
            }
            this.inEffectCheck = false;
            this.getChar();
            if (this.ch != '{') {
                do {
                    this.getChar();
                } while (this.ch == '{' || this.ch == '\u0000');
            }
            if (this.ch == '{') {
                this.getChar();
                do {
                    estimatePnt = retVal;
                    i = this.expI;
                    this.wholeExp(estimatePnt);
                    if (this.ret != SyntaxCode.SY_OK || retVal.getOpType() != TypeType.INT_TYPE) {
                        this.expI = i;
                        SyntaxCode syntaxCode = result = SyntaxCode.SY_LACKOF_INTEGER;
                        return syntaxCode;
                    }
                    startI = retVal.getOpInt();
                    if (this.ch == '~') {
                        estimatePnt = retVal;
                        this.getChar();
                        i = this.expI;
                        this.wholeExp(estimatePnt);
                        if (this.ret != SyntaxCode.SY_OK || retVal.getOpType() != TypeType.INT_TYPE) {
                            this.expI = i;
                            SyntaxCode syntaxCode = result = SyntaxCode.SY_LACKOF_INTEGER;
                            return syntaxCode;
                        }
                        endI = retVal.getOpInt();
                        if (endI < startI) {
                            this.expI = i;
                            SyntaxCode syntaxCode = result = SyntaxCode.SY_INTERVAL_OUTOF_ORDER;
                            return syntaxCode;
                        }
                        for (n = startI; n <= endI; ++n) {
                            this.numberArray.put(++numberNum, n);
                        }
                    } else {
                        this.numberArray.put(++numberNum, startI);
                    }
                    if (this.ch != ',' && this.ch != '\u0000' && this.ch != '}') {
                        SyntaxCode syntaxCode = result = SyntaxCode.SY_INVALID_CHAR;
                        return syntaxCode;
                    }
                    if (this.ch == '\u0000') {
                        result = SyntaxCode.SY_LACKOF_RIGHT_BIG_BRACKEK;
                        this.expI = i;
                        SyntaxCode syntaxCode = result;
                        return syntaxCode;
                    }
                    if (this.ch != ',') continue;
                    this.getChar();
                } while (this.ch == '}' || this.ch == '\u0000');
                i = this.expI;
                this.getChar();
                if (this.ch != '\u0000') {
                    this.expI = i;
                    SyntaxCode syntaxCode = result = SyntaxCode.SY_INTERVAL_SET_AT_END;
                    return syntaxCode;
                }
            }
            if ((i = SyntaxLib.getExtendPos('{', this.expStr)) != -1) {
                this.expStr = this.expStr.substring(0, i);
            }
            if (SyntaxLib.getExtendPos("[*", this.expStr) != -1) {
                if (numberNum == 0) {
                    SyntaxCode syntaxCode = result = SyntaxCode.SY_NEED_OF_INTERVAL_SET;
                    return syntaxCode;
                }
            } else {
                numberNum = 1;
            }
            String rawExpStr = this.expStr;
            if (this.multiSyntax) {
                rawSpotStr = this.expressGrid.getCells(2, this.expressGrid.getRow());
            }
            for (n = 1; n <= numberNum; ++n) {
                this.expStr = rawExpStr;
                i = SyntaxLib.getExtendPos("[*", this.expStr);
                while (i != -1) {
                    this.expStr = this.expStr.substring(0, i + 1) + this.IntToStr(this.numberArray.get(n)) + this.expStr.substring(i + 1 + 1, this.expStr.length());
                    i = SyntaxLib.getExtendPos("[*", this.expStr);
                }
                if (this.multiSyntax) {
                    this.tmpStr = rawSpotStr;
                    i = SyntaxLib.getExtendPos("[*", this.tmpStr);
                    while (i != 0) {
                        this.tmpStr = this.tmpStr.substring(0, i + 1) + this.IntToStr(this.numberArray.get(n)) + this.tmpStr.substring(i + 1 + 1, this.tmpStr.length());
                        i = SyntaxLib.getExtendPos("[*", this.tmpStr);
                    }
                    this.incNiporlanGrid(this.tmpStr);
                }
                estimatePnt = retVal;
                this.expChk(estimatePnt);
                result = this.ret;
                if (this.ret == SyntaxCode.SY_OK) continue;
                SyntaxCode syntaxCode = result;
                return syntaxCode;
            }
            result = SyntaxCode.SY_OK;
        }
        finally {
            this.ret = result;
        }
        return result;
    }
}

