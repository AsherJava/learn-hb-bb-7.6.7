/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.syntax;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.syntax.TSyntax;
import com.jiuqi.nr.single.core.syntax.bean.BZZDataType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;
import com.jiuqi.nr.single.core.syntax.bean.DataStackStruct;
import com.jiuqi.nr.single.core.syntax.bean.FastCodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.FastExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.FastTableCellType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;
import com.jiuqi.nr.single.core.syntax.common.StackDataType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxLib;
import com.jiuqi.nr.single.core.syntax.common.TNiporlanAssignType;
import java.util.ArrayList;
import java.util.List;

public class TNiporlan {
    private TSyntax syntaxObject;
    private boolean assignValue;
    private char ch;
    private String comStr;
    private int comInt;
    private FastTableCellType tableCell;
    private FastCodeCellType codeCell;
    private FastExistDataType existData;
    private BZZDataType bZZData;
    private String formula;
    private TNiporlanAssignType assignType;
    private StackDataType dataType;
    private double sData;
    private String sString;
    private StackDataType rDataType;
    private double rData;
    private String rString;
    private int stackTop;
    private List<DataStackStruct> stack = null;
    private FastTableCellType saveTableCell;
    private boolean ifThenTrue;
    private boolean saveAssignValue;

    public TNiporlan(TSyntax syntax) {
        this.syntaxObject = syntax;
        this.stackTop = 0;
        this.stack = new ArrayList<DataStackStruct>();
        for (int i = 0; i < 80; ++i) {
            this.stack.add(new DataStackStruct());
        }
        this.tableCell = new FastTableCellType();
        this.codeCell = new FastCodeCellType();
        this.existData = new FastExistDataType();
        this.bZZData = new BZZDataType();
    }

    public boolean judge(String theFormula) {
        this.formula = theFormula;
        boolean result = true;
        this.ch = (char)255;
        this.getChar();
        if (this.ch != '\u0000') {
            this.saveAssignValue = false;
            this.assignValue = false;
            this.kernel();
            result = !(Math.abs(this.sData) < 1.0E-5);
        }
        return result;
    }

    public void evaluate(String theFormula) {
        this.formula = theFormula;
        this.ch = (char)255;
        this.getChar();
        this.assignType = TNiporlanAssignType.NA_TABLE;
        if (this.ch != '\u0000') {
            this.saveAssignValue = false;
            char char19 = '\u0013';
            this.assignValue = this.formula.indexOf("|" + String.valueOf(char19)) < 0;
            this.kernel();
        }
    }

    public void attract(String theFormula, CommonDataType retData) {
        this.formula = theFormula;
        retData.setCdType(CommonDataTypeType.CD_NIL_TYPE);
        retData.setCdReal(0.0);
        this.ch = (char)255;
        this.getChar();
        if (this.ch != '\u0000') {
            this.saveAssignValue = false;
            this.assignValue = false;
            this.kernel();
            if (this.dataType == StackDataType.SD_STRING_TYPE) {
                retData.setCdType(CommonDataTypeType.CD_STRING_TYPE);
                retData.setCdString(this.sString);
            } else {
                retData.setCdType(CommonDataTypeType.CD_REAL_TYPE);
                retData.setCdReal(this.sData);
            }
        }
    }

    private void getTableCell(FastTableCellType tableCell) {
        if (this.syntaxObject.getProvider() != null) {
            this.syntaxObject.getProvider().niporlanGetTableCell(tableCell);
        }
    }

    private void setTableCell(FastTableCellType tableCell) {
        if (this.syntaxObject.getProvider() != null) {
            this.syntaxObject.getProvider().niporlanSetTableCell(tableCell);
        }
    }

    private void getCodeCell(FastCodeCellType codeCell) {
        if (this.syntaxObject.getProvider() != null) {
            this.syntaxObject.getProvider().niporlanGetCodeCell(codeCell);
        }
    }

    private void getCodeMean(FastCodeCellType MeanCell) {
        if (this.syntaxObject.getProvider() != null) {
            this.syntaxObject.getProvider().niporlanGetCodeCell(MeanCell);
        }
    }

    private void getExistData(FastExistDataType existData) {
        if (this.syntaxObject.getProvider() != null) {
            this.syntaxObject.getProvider().niporlanExistData(existData);
        }
    }

    private void getBZZData(BZZDataType bzzData) {
        if (this.syntaxObject.getProvider() != null) {
            this.syntaxObject.getProvider().syntaxGetBZZData(bzzData);
        }
    }

    private int getMonth(int month) {
        return month;
    }

    private String getBZZGM(String sourceGM) {
        return "";
    }

    private void push() {
        if (this.stackTop < this.stack.size()) {
            ++this.stackTop;
            this.stack.get(this.stackTop).setStackType(this.dataType);
            if (this.dataType == StackDataType.SD_REAL_TYPE) {
                this.stack.get(this.stackTop).setSdReal(this.sData);
            } else {
                this.stack.get(this.stackTop).setSdString(this.sString);
            }
        }
    }

    private void pop() {
        if (this.stackTop > 0) {
            this.dataType = this.stack.get(this.stackTop).getStackType();
            if (this.dataType == StackDataType.SD_REAL_TYPE) {
                this.sData = this.stack.get(this.stackTop).getSdReal();
            } else {
                this.sString = this.stack.get(this.stackTop).getSdString();
            }
            --this.stackTop;
        }
    }

    private void popr() {
        if (this.stackTop > 0) {
            this.rDataType = this.stack.get(this.stackTop).getStackType();
            if (this.rDataType == StackDataType.SD_REAL_TYPE) {
                this.rData = this.stack.get(this.stackTop).getSdReal();
            } else {
                this.rString = this.stack.get(this.stackTop).getSdString();
            }
            --this.stackTop;
        }
    }

    private void getChar() {
        if (this.ch != '\u0000') {
            this.ch = StringUtils.isEmpty((String)this.formula) ? (char)'\u0000' : this.formula.charAt(0);
            if (this.ch != '\u0000') {
                this.formula = this.formula.substring(1, this.formula.length());
            }
        }
    }

    private void skipChar() {
        char ch2;
        if (this.ch != '\u0000' && (ch2 = this.formula.charAt(0)) != '\u0000') {
            this.formula = this.formula.substring(1, this.formula.length());
        }
    }

    private void getInteger() {
        this.comInt = 0;
        do {
            this.comInt = this.comInt * 10 + this.ch - 48;
            this.getChar();
        } while (this.ch >= '0' && this.ch <= '9');
    }

    private void getString() {
        this.comStr = "";
        this.getChar();
        while (this.ch != '\"' && this.ch != '\u0000') {
            this.comStr = this.comStr + String.valueOf(this.ch);
            this.getChar();
        }
        this.getChar();
    }

    private void kernel() {
        while (this.ch != '\u0000') {
            block71: {
                if (this.ch == '[') {
                    this.getChar();
                    switch (this.ch) {
                        case '#': {
                            this.getChar();
                            this.getInteger();
                            this.tableCell.setTabIndex(this.comInt);
                            this.getChar();
                            this.getInteger();
                            this.tableCell.setIndex(this.comInt);
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            if (this.stackTop > 0 || !this.assignValue) {
                                this.getTableCell(this.tableCell);
                                this.sData = this.tableCell.getValue();
                                break;
                            }
                            this.assignType = TNiporlanAssignType.NA_TABLE;
                            this.saveTableCell = this.tableCell;
                            this.sData = this.assignType.getValue();
                            break;
                        }
                        case '0': 
                        case '1': 
                        case '2': 
                        case '3': 
                        case '4': 
                        case '5': 
                        case '6': 
                        case '7': 
                        case '8': 
                        case '9': {
                            this.comStr = "";
                            do {
                                this.comStr = this.comStr + String.valueOf(this.ch);
                                this.getChar();
                            } while (this.ch != ']');
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.sData = SyntaxLib.myStrToFloat(this.comStr);
                            break;
                        }
                        case '$': {
                            this.getChar();
                            if (this.ch == '$') {
                                this.getChar();
                                this.getInteger();
                                this.codeCell.setIndex(this.comInt);
                                this.getCodeMean(this.codeCell);
                            } else {
                                this.getInteger();
                                this.codeCell.setIndex(this.comInt);
                                this.getCodeCell(this.codeCell);
                            }
                            this.dataType = StackDataType.SD_STRING_TYPE;
                            this.sString = this.codeCell.getValue();
                            break;
                        }
                        case '\"': {
                            this.getString();
                            this.dataType = StackDataType.SD_STRING_TYPE;
                            this.sString = this.comStr;
                        }
                    }
                    this.push();
                    this.skipChar();
                    this.getChar();
                    continue;
                }
                block6 : switch (this.ch) {
                    case ':': {
                        this.popr();
                        this.pop();
                        switch (this.assignType.getValue()) {
                            case 0: {
                                this.saveTableCell.setValue(this.rData);
                                this.setTableCell(this.saveTableCell);
                            }
                        }
                        this.dataType = StackDataType.SD_REAL_TYPE;
                        this.push();
                        break;
                    }
                    case '+': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData += this.rData;
                        } else {
                            this.sString = this.sString + this.rString;
                        }
                        this.push();
                        break;
                    }
                    case '-': {
                        this.popr();
                        this.pop();
                        this.dataType = StackDataType.SD_REAL_TYPE;
                        this.sData = Math.abs(this.sData - this.rData) < 1.0E-5 ? 0.0 : (this.sData -= this.rData);
                        this.push();
                        break;
                    }
                    case '\u0001': {
                        this.popr();
                        this.pop();
                        this.sData *= this.rData;
                        this.push();
                        break;
                    }
                    case '\u0002': {
                        this.popr();
                        this.pop();
                        this.sData = Math.abs(this.rData) < 1.0E-5 ? 0.0 : (this.sData /= this.rData);
                        this.push();
                        break;
                    }
                    case '\u0003': {
                        this.pop();
                        this.sData = -this.sData;
                        this.push();
                        break;
                    }
                    case '=': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData = Math.abs(this.sData - this.rData) < 1.0E-5 ? -1.0 : 0.0;
                        } else {
                            this.sData = StringUtils.isEmpty((String)this.sString) ? (StringUtils.isEmpty((String)this.rString) ? -1.0 : 0.0) : (this.sString.equalsIgnoreCase(this.rString) ? -1.0 : 0.0);
                            this.dataType = StackDataType.SD_REAL_TYPE;
                        }
                        this.push();
                        break;
                    }
                    case '\u0004': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData = Math.abs(this.sData - this.rData) >= 1.0E-5 ? -1.0 : 0.0;
                        } else {
                            this.sData = !this.sString.equalsIgnoreCase(this.rString) ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                        }
                        this.push();
                        break;
                    }
                    case '>': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData = this.sData - this.rData > 1.0E-5 ? -1.0 : 0.0;
                        } else {
                            this.sData = this.sString.compareToIgnoreCase(this.rString) > 0 ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                        }
                        this.push();
                        break;
                    }
                    case '<': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData = this.rData - this.sData > 1.0E-5 ? -1.0 : 0.0;
                        } else {
                            this.sData = this.sString.compareToIgnoreCase(this.rString) < 0 ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                        }
                        this.push();
                        break;
                    }
                    case '\u0005': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData = this.rData - this.sData < 1.0E-5 ? -1.0 : 0.0;
                        } else {
                            this.sData = this.sString.compareToIgnoreCase(this.rString) >= 0 ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                        }
                        this.push();
                        break;
                    }
                    case '\u0006': {
                        this.popr();
                        this.pop();
                        if (this.dataType == StackDataType.SD_REAL_TYPE) {
                            this.sData = this.sData - this.rData < 1.0E-5 ? -1.0 : 0.0;
                        } else {
                            this.sData = this.sString.compareToIgnoreCase(this.rString) <= 0 ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                        }
                        this.push();
                        break;
                    }
                    case '\u000e': {
                        this.popr();
                        this.pop();
                        this.sData = Math.abs(this.sData * this.rData) < 1.0E-5 ? 0.0 : -1.0;
                        this.push();
                        break;
                    }
                    case '\u000f': {
                        this.popr();
                        this.pop();
                        this.sData = Math.abs(this.sData + this.rData) < 1.0E-5 ? 0.0 : -1.0;
                        this.push();
                        break;
                    }
                    case '\u0010': {
                        this.pop();
                        this.sData = Math.abs(this.sData) < 1.0E-5 ? -1.0 : 0.0;
                        this.push();
                        break;
                    }
                    case '\u0011': {
                        this.pop();
                        this.sData = Math.abs(this.sData);
                        this.push();
                        break;
                    }
                    case '\u0012': {
                        this.pop();
                        this.sData = Math.floor(this.sData);
                        this.push();
                        break;
                    }
                    case '\u001a': {
                        this.popr();
                        this.pop();
                        this.sData = this.rData < 0.0 && Math.abs(Math.floor(this.sData) - this.sData) > 1.0E-5 ? 1.0 : Math.pow(this.rData, this.sData);
                        this.push();
                        break;
                    }
                    case '\u001b': {
                        this.pop();
                        this.sData = Math.round(this.sData);
                        this.push();
                        break;
                    }
                    case '\u001c': {
                        this.pop();
                        this.sString = String.valueOf(SyntaxLib.getIdc(this.sString));
                        this.push();
                        break;
                    }
                    case '\u001d': {
                        this.pop();
                        this.sData = SyntaxLib.myStrToFloat(this.sString);
                        this.dataType = StackDataType.SD_REAL_TYPE;
                        this.push();
                        break;
                    }
                    case '\"': {
                        int len;
                        this.comStr = "";
                        this.getChar();
                        while (this.ch != '\"' && this.ch != '\u0000') {
                            this.comStr = this.comStr + String.valueOf(this.ch);
                            this.getChar();
                        }
                        if ("L$".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.popr();
                            this.dataType = StackDataType.SD_STRING_TYPE;
                            len = (int)Math.round(this.sData);
                            this.sString = StringUtils.isNotEmpty((String)this.rString) && this.rString.length() >= len ? this.rString.substring(0, len) : "";
                            this.push();
                            break;
                        }
                        if ("M$".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.popr();
                            this.comInt = (int)Math.round(this.rData);
                            this.popr();
                            this.dataType = StackDataType.SD_STRING_TYPE;
                            len = (int)Math.round(this.sData);
                            this.sString = StringUtils.isNotEmpty((String)this.rString) && this.rString.length() >= this.comInt - 1 + len && this.comInt > 0 ? this.rString.substring(this.comInt - 1, this.comInt - 1 + len) : "";
                            this.push();
                            break;
                        }
                        if ("R$".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.popr();
                            this.dataType = StackDataType.SD_STRING_TYPE;
                            len = (int)Math.round(this.sData);
                            this.sString = StringUtils.isNotEmpty((String)this.rString) && this.rString.length() >= len ? this.rString.substring(this.rString.length() - len, this.rString.length()) : "";
                            this.push();
                            break;
                        }
                        if ("LEN".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.sData = this.sString.length();
                            this.push();
                            break;
                        }
                        if ("ISDIGIT".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.sData = SyntaxLib.getIsDigit(this.sString) ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.push();
                            break;
                        }
                        if ("EXIST".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.existData.setTabIndex((int)Math.round(this.sData));
                            this.existData.setExist(true);
                            this.getExistData(this.existData);
                            this.sData = this.existData.isExist() ? -1.0 : 0.0;
                            this.sData = SyntaxLib.getIsDigit(this.sString) ? -1.0 : 0.0;
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.push();
                            break;
                        }
                        if ("ISIDCARD".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.sData = SyntaxLib.getIsIDCard(this.sString) ? -1.0 : 0.0;
                            this.push();
                            break;
                        }
                        if ("BZZ".equalsIgnoreCase(this.comStr)) {
                            this.pop();
                            this.bZZData.setBzzType((int)Math.round(this.sData));
                            this.pop();
                            this.bZZData.setZbSign(this.sString);
                            this.pop();
                            this.bZZData.setSign(this.sString);
                            this.getBZZData(this.bZZData);
                            this.sData = this.bZZData.getValue();
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.push();
                            break;
                        }
                        if ("MONTH".equalsIgnoreCase(this.comStr)) {
                            this.comInt = this.getMonth(this.comInt);
                            this.dataType = StackDataType.SD_REAL_TYPE;
                            this.sData = this.comInt;
                            this.push();
                            break;
                        }
                        if (!"BZZGM".equalsIgnoreCase(this.comStr)) break;
                        this.pop();
                        this.comStr = this.getBZZGM(this.sString);
                        this.dataType = StackDataType.SD_STRING_TYPE;
                        this.sString = this.comStr;
                        this.push();
                        break;
                    }
                    case '|': {
                        this.getChar();
                        switch (this.ch) {
                            case '\u0013': {
                                this.ifThenTrue = Math.abs(this.sData) < 1.0E-5;
                                this.assignValue = this.saveAssignValue;
                                if (this.assignValue) {
                                    this.sData = -1.0;
                                }
                                this.ifThenTrue = false;
                                if (false) {
                                    char ach1 = '|';
                                    char ach2 = '\u0014';
                                    String subStr = String.valueOf(ach1) + String.valueOf(ach2);
                                    int idx = this.formula.indexOf(subStr);
                                    if (idx > 0) {
                                        this.formula = this.formula.substring(idx + 2, this.formula.length());
                                        break block6;
                                    }
                                }
                                break block71;
                            }
                            case '\u0014': 
                            case '\u0015': {
                                this.pop();
                                return;
                            }
                        }
                    }
                }
            }
            this.getChar();
            if (this.ch == '\u0000') continue;
            this.getChar();
        }
        this.pop();
    }
}

