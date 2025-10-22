/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.data.engine.summary.exe;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.common.SumEngineConsts;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.List;

public class SumCell {
    private int row;
    private int col;
    private String rowCondition;
    private String colCondition;
    private FieldDefine srcField;
    private String sumExp;
    private String customizeFormula;
    private int sumType;
    private IASTNode parsedExp;
    private FieldDefine destField;
    private Object value;
    private String alias;
    private String periodOffSet;
    private double divider = 0.0;

    public SumCell() {
    }

    public SumCell(int row, int col, String rowCondition, String colCondition, FieldDefine srcField, FieldDefine destField, int sumType) {
        this.row = row;
        this.col = col;
        this.alias = "P" + row + "_" + col;
        this.rowCondition = rowCondition;
        this.colCondition = colCondition;
        this.srcField = srcField;
        this.destField = destField;
        this.sumType = sumType;
    }

    public SumCell(int row, int col, String rowCondition, String colCondition, Object value, FieldDefine destField, int sumType) {
        this.row = row;
        this.col = col;
        this.alias = "P" + row + "_" + col;
        this.rowCondition = rowCondition;
        this.colCondition = colCondition;
        this.value = value;
        this.destField = destField;
        this.sumType = sumType;
    }

    public SumCell(int row, int col, String rowCondition, String colCondition, String sumExp, FieldDefine destField, int sumType) {
        this.row = row;
        this.col = col;
        this.alias = "P" + row + "_" + col;
        this.rowCondition = rowCondition;
        this.colCondition = colCondition;
        this.sumExp = sumExp;
        this.sumType = sumType;
        this.destField = destField;
    }

    public SumCell(int row, int col, String customizeFormula, FieldDefine destField, int sumType) {
        this.row = row;
        this.col = col;
        this.alias = "P" + row + "_" + col;
        this.sumType = sumType;
        this.customizeFormula = customizeFormula;
        this.destField = destField;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public String getRowCondition() {
        return this.rowCondition;
    }

    public String getColCondition() {
        return this.colCondition;
    }

    public String getSumExp() {
        return this.sumExp;
    }

    public int getSumType() {
        return this.sumType;
    }

    public String getCustomizeFormula() {
        return this.customizeFormula;
    }

    public void setRowCondition(String rowCondition) {
        this.rowCondition = rowCondition;
    }

    public void setColCondition(String colCondition) {
        this.colCondition = colCondition;
    }

    public String getCondition() {
        StringBuilder condition = new StringBuilder();
        if (this.rowCondition != null && this.rowCondition.length() > 0) {
            condition.append("(").append(this.rowCondition).append(")");
        }
        if (this.colCondition != null && this.colCondition.length() > 0) {
            if (condition.length() > 0) {
                condition.append(" and ");
            }
            condition.append("(").append(this.colCondition).append(")");
        }
        return condition.toString();
    }

    public IASTNode getParsedExp() {
        return this.parsedExp;
    }

    public void setParsedExp(IASTNode parsedExp) {
        this.parsedExp = parsedExp;
    }

    public FieldDefine getSrcField() {
        return this.srcField;
    }

    public FieldDefine getDestField() {
        return this.destField;
    }

    public Object getValue() {
        return this.value;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getPeriodOffSet() {
        return this.periodOffSet;
    }

    public void setPeriodOffSet(String periodOffSet) {
        this.periodOffSet = periodOffSet;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[").append(this.row).append(",").append(this.col).append("]");
        buf.append(": ");
        DataFieldDeployInfo deployInfo = this.getDeployInfo(this.destField.getKey());
        buf.append(deployInfo.getTableName()).append("[").append(this.destField.getCode()).append("]");
        buf.append(" = ");
        String condition = this.getCondition();
        if (condition.length() > 0) {
            buf.append(" IF ").append(condition).append(" THEN ");
        }
        if (this.srcField != null) {
            DataFieldDeployInfo srcDeployInfo = this.getDeployInfo(this.srcField.getKey());
            buf.append(srcDeployInfo.getTableName()).append("[").append(this.destField.getCode());
            if (this.periodOffSet != null) {
                buf.append(",").append(this.periodOffSet);
            }
            buf.append(",").append(SumEngineConsts.SumType.valueOf(this.sumType).getName());
            buf.append("]");
        } else if (this.sumExp != null) {
            buf.append(SumEngineConsts.SumType.valueOf(this.sumType).getName()).append("(").append(this.sumExp).append(")");
        } else if (this.value != null) {
            buf.append(this.value);
        }
        return buf.toString();
    }

    public void setValue(String value) {
        this.srcField = null;
        this.sumExp = null;
        this.value = value;
    }

    public double getDivider() {
        return this.divider;
    }

    public void setDivider(double divider) {
        this.divider = divider;
    }

    private DataFieldDeployInfo getDeployInfo(String dataFieldKey) {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfoByDataFieldKeys = dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFieldKey});
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        return deployInfo;
    }
}

