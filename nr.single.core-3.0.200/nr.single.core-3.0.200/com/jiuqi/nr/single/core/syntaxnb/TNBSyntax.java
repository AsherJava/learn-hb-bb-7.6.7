/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb;

import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import com.jiuqi.nr.single.core.syntax.common.TAssignType;
import com.jiuqi.nr.single.core.syntax.common.TAttractType;
import com.jiuqi.nr.single.core.syntax.common.TCheckType;
import com.jiuqi.nr.single.core.syntax.common.TSyntaxOption;
import com.jiuqi.nr.single.core.syntax.grid.StringGrid;
import com.jiuqi.nr.single.core.syntaxnb.NBSyntaxProvider;
import com.jiuqi.nr.single.core.syntaxnb.TNBNiporlan;
import com.jiuqi.nr.single.core.syntaxnb.bean.EnumDictValueType;
import com.jiuqi.nr.single.core.syntaxnb.bean.FVParams;
import com.jiuqi.nr.single.core.syntaxnb.bean.GetEnumMeaningType;
import com.jiuqi.nr.single.core.syntaxnb.bean.HTDataType;
import com.jiuqi.nr.single.core.syntaxnb.bean.HTSDataType;
import com.jiuqi.nr.single.core.syntaxnb.bean.NBMultiType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TNBSyntax {
    private Set<TSyntaxOption> syntaxOptions;
    private List<String> expressions;
    private List<String> niporlans;
    private StringGrid expressGrid;
    private StringGrid niporlanGrid;
    private boolean nullNiporlan;
    private String defaultTabSign;
    private int errorLine;
    private TNBNiporlan niporlanObject;
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
    private String ifExpStr;
    private String thenExpStr;
    private String elseExpStr;
    private String saveExpStr;
    private int thenPos;
    private int elsePos;
    private boolean canAssignValue;
    private boolean ifThenTrue;
    private boolean judgeResult;
    private int[] hNumArray = new int[1000];
    private int[] lNumArray = new int[1000];
    private String tmpStr;
    private String tmpStr2;
    private String tmpStr3;
    private String tmpStr4;
    private String tmpStr5;
    private String tmpstr6;
    private double tempReal;
    private int tmpInt;
    private int tmpInt2;
    private int tmpInt3;
    private double DecPos;
    private NBMultiType tempVal;
    private CommonDataType tempData;
    private EnumDictValueType enumDictValue;
    private GetEnumMeaningType getMeaninRec;
    private HTDataType hTData;
    private HTSDataType hTSData;
    private FVParams fVParams;
    private String ifNiporlan;
    private String lastError;
    private NBSyntaxProvider provider;

    public List<String> getNiporlans() {
        return this.niporlans;
    }

    public StringGrid getExpressGrid() {
        return this.expressGrid;
    }

    public StringGrid getNiporlanGrid() {
        return this.niporlanGrid;
    }

    public int getErrorLine() {
        return this.errorLine;
    }

    public void setNiporlans(List<String> niporlans) {
        this.niporlans = niporlans;
    }

    public void setExpressGrid(StringGrid expressGrid) {
        this.expressGrid = expressGrid;
    }

    public void setNiporlanGrid(StringGrid niporlanGrid) {
        this.niporlanGrid = niporlanGrid;
    }

    public void setErrorLine(int errorLine) {
        this.errorLine = errorLine;
    }

    public Set<TSyntaxOption> getSyntaxOptions() {
        return this.syntaxOptions;
    }

    public List<String> getExpressions() {
        if (this.expressions == null) {
            this.expressions = new ArrayList<String>();
        }
        return this.expressions;
    }

    public boolean isNullNiporlan() {
        return this.nullNiporlan;
    }

    public String getDefaultTabSign() {
        return this.defaultTabSign;
    }

    public String getIfNiporlan() {
        return this.ifNiporlan;
    }

    public void setSyntaxOptions(Set<TSyntaxOption> syntaxOptions) {
        if (!syntaxOptions.contains((Object)TSyntaxOption.SO_TABLE)) {
            syntaxOptions.remove((Object)TSyntaxOption.SO_STAR);
            syntaxOptions.remove((Object)TSyntaxOption.SO_CHKPOS);
        }
        this.syntaxOptions = syntaxOptions;
    }

    public void setExpressions(List<String> expressions) {
        if (expressions != null) {
            this.expressions.clear();
            this.expressions.addAll(expressions);
        }
    }

    public void setNullNiporlan(boolean nullNiporlan) {
        this.nullNiporlan = nullNiporlan;
    }

    public void setDefaultTabSign(String defaultTabSign) {
        this.defaultTabSign = defaultTabSign = defaultTabSign.toUpperCase();
    }

    public void setIfNiporlan(String ifNiporlan) {
        this.ifNiporlan = ifNiporlan;
    }

    public String getLastError() {
        return this.lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public NBSyntaxProvider getProvider() {
        return this.provider;
    }

    public void setProvider(NBSyntaxProvider provider) {
        this.provider = provider;
    }

    public TNBSyntax() {
        this.expressions = new ArrayList<String>();
        this.niporlans = new ArrayList<String>();
        this.expressGrid = new StringGrid();
        this.expressGrid.setCol(4);
        this.expressGrid.setRow(1);
        this.niporlanGrid = new StringGrid();
        this.niporlanGrid.setCol(10);
        this.niporlanGrid.setRow(1);
        this.niporlanObject = new TNBNiporlan(this);
        this.nullNiporlan = false;
        this.syntaxOptions = new HashSet<TSyntaxOption>();
        this.syntaxOptions.add(TSyntaxOption.SO_TABLE);
    }
}

