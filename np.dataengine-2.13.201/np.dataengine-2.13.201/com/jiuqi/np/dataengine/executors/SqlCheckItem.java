/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlCheckItem {
    private int leftValueIndex = -1;
    private int rightValueIndex = -1;
    private List<Integer> keyFieldIndexes = new ArrayList<Integer>();
    private int floatOrderIndex;
    private int mRecidFieldIndex = -1;
    private FieldDefine mZB;
    private int batchKeyIndex = -1;
    private String conditionSqlPart;
    private String checkSqlPart;
    private IASTNode leftNode;
    private IASTNode rightNode;
    private String formulaMeanning;
    private Map<QueryTable, List<DynamicDataNode>> nodeInfosByTable = new HashMap<QueryTable, List<DynamicDataNode>>();
    public int nodeStartIndex = 0;
    private List<DynamicDataNode> nodeList = new ArrayList<DynamicDataNode>();
    private Map<String, Integer> nodeIndexes = new HashMap<String, Integer>();
    private Formula formula;
    private QueryTable mainTable;
    private int wildcardCol;
    private int wildcardRow;

    public int getLeftValueIndex() {
        return this.leftValueIndex;
    }

    public void setLeftValueIndex(int leftValueIndex) {
        this.leftValueIndex = leftValueIndex;
    }

    public int getRightValueIndex() {
        return this.rightValueIndex;
    }

    public void setRightValueIndex(int rightValueIndex) {
        this.rightValueIndex = rightValueIndex;
    }

    public int getFloatOrderIndex() {
        return this.floatOrderIndex;
    }

    public void setFloatOrderIndex(int floatOrderIndex) {
        this.floatOrderIndex = floatOrderIndex;
    }

    public int getmRecidFieldIndex() {
        return this.mRecidFieldIndex;
    }

    public void setmRecidFieldIndex(int mRecidFieldIndex) {
        this.mRecidFieldIndex = mRecidFieldIndex;
    }

    public int getBatchKeyIndex() {
        return this.batchKeyIndex;
    }

    public void setBatchKeyIndex(int batchKeyIndex) {
        this.batchKeyIndex = batchKeyIndex;
    }

    public List<Integer> getKeyFieldIndexes() {
        return this.keyFieldIndexes;
    }

    public void setConditionSqlPart(String conditionSqlPart) {
        this.conditionSqlPart = conditionSqlPart;
    }

    public void setCheckSqlPart(String checkSqlPart) {
        this.checkSqlPart = checkSqlPart;
    }

    public String getSqlPart() {
        if (this.conditionSqlPart != null) {
            return this.conditionSqlPart + " and " + this.checkSqlPart;
        }
        return this.checkSqlPart;
    }

    public IASTNode getLeftNode() {
        return this.leftNode;
    }

    public void setLeftNode(IASTNode leftNode) {
        this.leftNode = leftNode;
    }

    public IASTNode getRightNode() {
        return this.rightNode;
    }

    public void setRightNode(IASTNode rightNode) {
        this.rightNode = rightNode;
    }

    public String getFormulaMeanning() {
        return this.formulaMeanning;
    }

    public void setFormulaMeanning(String formulaMeanning) {
        this.formulaMeanning = formulaMeanning;
    }

    public Map<QueryTable, List<DynamicDataNode>> getNodeInfosByTable() {
        return this.nodeInfosByTable;
    }

    public Formula getFormula() {
        return this.formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public Map<String, Integer> getNodeIndexes() {
        return this.nodeIndexes;
    }

    public List<DynamicDataNode> getNodeList() {
        return this.nodeList;
    }

    public QueryTable getMainTable() {
        return this.mainTable;
    }

    public void setMainTable(QueryTable mainTable) {
        this.mainTable = mainTable;
    }

    public int getWildcardCol() {
        return this.wildcardCol;
    }

    public int getWildcardRow() {
        return this.wildcardRow;
    }

    public void setWildcardCol(int wildcardCol) {
        this.wildcardCol = wildcardCol;
    }

    public void setWildcardRow(int wildcardRow) {
        this.wildcardRow = wildcardRow;
    }
}

