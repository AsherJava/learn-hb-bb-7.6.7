/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment
 *  com.jiuqi.nvwa.cellbook.constant.VerticalAlignment
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.formulaeditor.common;

import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import com.jiuqi.nr.formulaeditor.vo.FormulaVariableData;
import com.jiuqi.nr.formulaeditor.vo.FunctionData;
import com.jiuqi.nr.formulaeditor.vo.RegionData;
import com.jiuqi.nr.formulaeditor.vo.SchemeData;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.List;

public class EditorUtils {
    public static com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> createRootTaskGroupITree() {
        EditorNodeData editorNodeData = new EditorNodeData();
        editorNodeData.setKey("allTask");
        editorNodeData.setCode("allTask");
        editorNodeData.setTitle("\u5168\u90e8\u4efb\u52a1");
        editorNodeData.setType("taskGroup");
        com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(editorNodeData);
        return editorNodeDataITree;
    }

    public static com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> createTaskITree(TaskDefine taskDefine, String parent) {
        EditorNodeData editorNodeData = new EditorNodeData();
        editorNodeData.setKey(taskDefine.getKey());
        editorNodeData.setCode(taskDefine.getTaskCode());
        editorNodeData.setTitle(taskDefine.getTitle());
        editorNodeData.setType("task");
        editorNodeData.setParentKey(parent);
        com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(editorNodeData);
        editorNodeDataITree.setIsLeaf(true);
        return editorNodeDataITree;
    }

    public static com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> createTaskGroupITree(TaskGroupDefine taskGroupDefine) {
        EditorNodeData editorNodeData = new EditorNodeData();
        editorNodeData.setKey(taskGroupDefine.getKey());
        editorNodeData.setCode(taskGroupDefine.getCode());
        editorNodeData.setTitle(taskGroupDefine.getTitle());
        editorNodeData.setParentKey(taskGroupDefine.getParentKey());
        editorNodeData.setType("taskGroup");
        com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(editorNodeData);
        return editorNodeDataITree;
    }

    public static com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> createFormGroupITree(FormGroupDefine formGroupDefine) {
        EditorNodeData editorNodeData = new EditorNodeData();
        editorNodeData.setKey(formGroupDefine.getKey());
        editorNodeData.setCode(formGroupDefine.getCode());
        editorNodeData.setTitle(formGroupDefine.getTitle());
        editorNodeData.setType("formGroup");
        com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(editorNodeData);
        editorNodeDataITree.setExpand(true);
        return editorNodeDataITree;
    }

    public static com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> createFormITree(FormDefine formDefine, String parent, String icon) {
        EditorNodeData editorNodeData = new EditorNodeData();
        editorNodeData.setKey(formDefine.getKey());
        editorNodeData.setCode(formDefine.getFormCode());
        editorNodeData.setTitle(formDefine.getTitle());
        editorNodeData.setParentKey(parent);
        editorNodeData.setType("form");
        editorNodeData.setNumber(formDefine.getSerialNumber());
        com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(editorNodeData);
        editorNodeDataITree.setIcons(icon);
        editorNodeDataITree.setIsLeaf(true);
        return editorNodeDataITree;
    }

    public static SchemeData createSchemeData(FormSchemeDefine formSchemeDefine) {
        SchemeData schemeData = new SchemeData();
        schemeData.setKey(formSchemeDefine.getKey());
        schemeData.setTitle(formSchemeDefine.getTitle());
        return schemeData;
    }

    public static RegionData toRegionData(DataRegionDefine regionDefine) {
        RegionData regionData = new RegionData();
        regionData.setKey(regionDefine.getKey());
        regionData.setFormKey(regionDefine.getFormKey());
        regionData.setLeft(regionDefine.getRegionLeft());
        regionData.setRight(regionDefine.getRegionRight());
        regionData.setTop(regionDefine.getRegionTop());
        regionData.setBottom(regionDefine.getRegionBottom());
        regionData.setType(regionDefine.getRegionKind().getValue());
        return regionData;
    }

    public static void hiddenZeroRowCol(Grid2Data formStyle) {
        if (!formStyle.isRowHidden(0) || !formStyle.isColumnHidden(0)) {
            formStyle.setRowHidden(0, true);
            formStyle.setColumnHidden(0, true);
        }
    }

    public static FormulaVariableData toFormulaVariableData(FormulaVariDefine variDefine) {
        FormulaVariableData data = new FormulaVariableData();
        data.setCode(variDefine.getCode());
        data.setFormSchemeKey(variDefine.getFormSchemeKey());
        data.setInitType(variDefine.getInitType());
        data.setId(variDefine.getKey());
        data.setLength(variDefine.getLength());
        data.setTitle(variDefine.getTitle());
        data.setOrder(variDefine.getOrder());
        data.setType(variDefine.getType());
        data.setTypeTitle(EditorUtils.typeToString(variDefine.getType()));
        data.setValueType(variDefine.getValueType());
        data.setInitValue(variDefine.getInitValue());
        return data;
    }

    public static String typeToString(int type) {
        if (type == 0) {
            return "\u503c\u7c7b\u578b";
        }
        if (type == 1) {
            return "\u516c\u5f0f\u7c7b\u578b";
        }
        return "\u5217\u8868\u7c7b\u578b";
    }

    public static int allField() {
        int interestType = 0;
        for (NodeType value : NodeType.values()) {
            if (value.getValue() <= NodeType.SCHEME_GROUP.getValue() || value.getValue() == NodeType.FMDM_TABLE.getValue()) continue;
            interestType |= value.getValue();
        }
        return interestType;
    }

    public static int noDimAndField() {
        int interestType = 0;
        block3: for (NodeType value : NodeType.values()) {
            switch (value) {
                case GROUP: 
                case SCHEME: 
                case ACCOUNT_TABLE: 
                case TABLE: 
                case DETAIL_TABLE: 
                case MUL_DIM_TABLE: {
                    interestType |= value.getValue();
                    continue block3;
                }
                default: {
                    continue block3;
                }
            }
        }
        return interestType;
    }

    public static int noField() {
        int interestType = 0;
        block3: for (NodeType value : NodeType.values()) {
            switch (value) {
                case GROUP: 
                case SCHEME: 
                case ACCOUNT_TABLE: 
                case TABLE: 
                case DETAIL_TABLE: 
                case MUL_DIM_TABLE: 
                case DIM: 
                case MD_INFO: {
                    interestType |= value.getValue();
                    continue block3;
                }
                default: {
                    continue block3;
                }
            }
        }
        return interestType;
    }

    public static List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> extractedDesign(List<ITree<DataSchemeNode>> data, boolean isCurr) {
        List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> rootTree = EditorUtils.toEditorNodeTree(data);
        for (com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree : rootTree) {
            if (isCurr) {
                editorNodeDataITree.setExpand(true);
            } else {
                editorNodeDataITree.setExpand(false);
            }
            editorNodeDataITree.setSelected(false);
        }
        return rootTree;
    }

    public static List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> extractedDesignNoChildren(List<ITree<DataSchemeNode>> data, boolean isCurr) {
        List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> rootTree = EditorUtils.toEditorNodeTreeNoChildren(data);
        for (com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree : rootTree) {
            if (isCurr) {
                editorNodeDataITree.setExpand(true);
            } else {
                editorNodeDataITree.setExpand(false);
            }
            editorNodeDataITree.setSelected(false);
        }
        return rootTree;
    }

    public static List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> extractedRuntime(List<ITree<RuntimeDataSchemeNode>> data, boolean isCurr) {
        List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> rootTree = EditorUtils.toEditorNodeTreeRun(data);
        for (com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree : rootTree) {
            if (isCurr) {
                editorNodeDataITree.setExpand(true);
            } else {
                editorNodeDataITree.setExpand(false);
            }
            editorNodeDataITree.setSelected(false);
        }
        return rootTree;
    }

    public static List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> toEditorNodeTreeRun(List<ITree<RuntimeDataSchemeNode>> rootTree) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> tree = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        for (ITree<RuntimeDataSchemeNode> schemeNodeITree : rootTree) {
            com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(EditorUtils.toEditorNode((RuntimeDataSchemeNode)schemeNodeITree.getData()));
            editorNodeDataITree.setKey(schemeNodeITree.getKey());
            editorNodeDataITree.setCode(schemeNodeITree.getCode());
            editorNodeDataITree.setTitle(schemeNodeITree.getTitle());
            editorNodeDataITree.setIcons(schemeNodeITree.getIcons());
            editorNodeDataITree.setDisabled(schemeNodeITree.isDisabled());
            editorNodeDataITree.setIsLeaf(schemeNodeITree.isLeaf());
            editorNodeDataITree.setNoDrop(schemeNodeITree.isNoDrop());
            editorNodeDataITree.setNoDrag(schemeNodeITree.isNoDrag());
            editorNodeDataITree.setChecked(schemeNodeITree.isChecked());
            editorNodeDataITree.setDisabled(schemeNodeITree.isDisabled());
            editorNodeDataITree.setExpand(schemeNodeITree.isExpanded());
            editorNodeDataITree.setSelected(schemeNodeITree.isSelected());
            editorNodeDataITree.setChildCount(schemeNodeITree.getChildCount());
            editorNodeDataITree.setLevel(schemeNodeITree.getLevel());
            if (schemeNodeITree.hasChildren()) {
                editorNodeDataITree.setChildren(EditorUtils.toEditorNodeTreeRun(schemeNodeITree.getChildren()));
            }
            tree.add(editorNodeDataITree);
        }
        return tree;
    }

    public static List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> toEditorNodeTreeNoChildren(List<ITree<DataSchemeNode>> rootTree) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> tree = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        for (ITree<DataSchemeNode> schemeNodeITree : rootTree) {
            com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(EditorUtils.toEditorNode((DataSchemeNode)schemeNodeITree.getData()));
            editorNodeDataITree.setKey(schemeNodeITree.getKey());
            editorNodeDataITree.setCode(schemeNodeITree.getCode());
            editorNodeDataITree.setTitle(schemeNodeITree.getTitle());
            editorNodeDataITree.setIcons(schemeNodeITree.getIcons());
            editorNodeDataITree.setDisabled(schemeNodeITree.isDisabled());
            editorNodeDataITree.setIsLeaf(schemeNodeITree.isLeaf());
            editorNodeDataITree.setNoDrop(schemeNodeITree.isNoDrop());
            editorNodeDataITree.setNoDrag(schemeNodeITree.isNoDrag());
            editorNodeDataITree.setChecked(schemeNodeITree.isChecked());
            editorNodeDataITree.setDisabled(schemeNodeITree.isDisabled());
            editorNodeDataITree.setExpand(schemeNodeITree.isExpanded());
            editorNodeDataITree.setSelected(schemeNodeITree.isSelected());
            editorNodeDataITree.setChildCount(schemeNodeITree.getChildCount());
            editorNodeDataITree.setLevel(schemeNodeITree.getLevel());
            tree.add(editorNodeDataITree);
        }
        return tree;
    }

    public static List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> toEditorNodeTree(List<ITree<DataSchemeNode>> rootTree) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> tree = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        for (ITree<DataSchemeNode> schemeNodeITree : rootTree) {
            com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> editorNodeDataITree = new com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>(EditorUtils.toEditorNode((DataSchemeNode)schemeNodeITree.getData()));
            editorNodeDataITree.setKey(schemeNodeITree.getKey());
            editorNodeDataITree.setCode(schemeNodeITree.getCode());
            editorNodeDataITree.setTitle(schemeNodeITree.getTitle());
            editorNodeDataITree.setIcons(schemeNodeITree.getIcons());
            editorNodeDataITree.setDisabled(schemeNodeITree.isDisabled());
            editorNodeDataITree.setIsLeaf(schemeNodeITree.isLeaf());
            editorNodeDataITree.setNoDrop(schemeNodeITree.isNoDrop());
            editorNodeDataITree.setNoDrag(schemeNodeITree.isNoDrag());
            editorNodeDataITree.setChecked(schemeNodeITree.isChecked());
            editorNodeDataITree.setDisabled(schemeNodeITree.isDisabled());
            editorNodeDataITree.setExpand(schemeNodeITree.isExpanded());
            editorNodeDataITree.setSelected(schemeNodeITree.isSelected());
            editorNodeDataITree.setChildCount(schemeNodeITree.getChildCount());
            editorNodeDataITree.setLevel(schemeNodeITree.getLevel());
            if (schemeNodeITree.hasChildren()) {
                editorNodeDataITree.setChildren(EditorUtils.toEditorNodeTree(schemeNodeITree.getChildren()));
            }
            tree.add(editorNodeDataITree);
        }
        return tree;
    }

    public static EditorNodeData toEditorNode(DataSchemeNode node) {
        EditorNodeData nodeData = new EditorNodeData();
        nodeData.setKey(node.getKey());
        nodeData.setCode(node.getCode());
        nodeData.setTitle(node.getTitle());
        nodeData.setType(node.getType() + "");
        nodeData.setParentKey(node.getParentKey());
        return nodeData;
    }

    public static EditorNodeData toEditorNode(RuntimeDataSchemeNode node) {
        EditorNodeData nodeData = new EditorNodeData();
        nodeData.setKey(node.getKey());
        nodeData.setCode(node.getCode());
        nodeData.setTitle(node.getTitle());
        nodeData.setType(node.getType() + "");
        nodeData.setParentKey(node.getParentKey());
        return nodeData;
    }

    public static void setCellStyle(Cell cell) {
        cell.setValue(cell.getShowText());
        cell.getCellStyle().setFontColor("0000FF");
        cell.getCellStyle().setFontSize(14);
        cell.getCellStyle().setFontName("\u5b8b\u4f53");
        cell.getCellStyle().setBold(false);
        cell.getCellStyle().setItalic(false);
        cell.getCellStyle().setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);
        cell.getCellStyle().setFitFontSize(true);
        cell.getCellStyle().setWrapLine(false);
        cell.getCellStyle().setUnderline(false);
    }

    public static String getPosText(DataLinkDefine dataLinkDefine) {
        return "[" + dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum() + "]";
    }

    public static List<FunctionData> getOperatorFunctions() {
        FunctionData oper1 = new FunctionData("+", "\u52a0\u53f7", "\u7b26\u53f7", "\u5c06\u4e24\u4e2a\u6570\u76f8\u52a0", 0);
        FunctionData oper2 = new FunctionData("-", "\u51cf\u53f7", "\u7b26\u53f7", "\u5c06\u4e24\u4e2a\u6570\u76f8\u51cf", 0);
        FunctionData oper3 = new FunctionData("*", "\u4e58\u53f7", "\u7b26\u53f7", "\u5c06\u4e24\u4e2a\u6570\u76f8\u4e58", 0);
        FunctionData oper4 = new FunctionData("/", "\u9664\u53f7", "\u7b26\u53f7", "\u5c06\u4e24\u4e2a\u6570\u76f8\u9664", 0);
        FunctionData oper5 = new FunctionData("%", "\u53d6\u4f59\u6570", "\u7b26\u53f7", "\u5c06\u4e24\u4e2a\u6570\u53d6\u4f59\u6570", 0);
        FunctionData oper6 = new FunctionData(">", "\u5927\u4e8e", "\u7b26\u53f7", "\u67d0\u6570\u5927\u4e8e\u67d0\u6570", 0);
        FunctionData oper7 = new FunctionData(">=", "\u5927\u4e8e\u7b49\u4e8e", "\u7b26\u53f7", "\u67d0\u6570\u5927\u4e8e\u6216\u7b49\u4e8e\u67d0\u6570", 0);
        FunctionData oper8 = new FunctionData("<", "\u5c0f\u4e8e", "\u7b26\u53f7", "\u67d0\u6570\u5c0f\u4e8e\u67d0\u6570", 0);
        FunctionData oper9 = new FunctionData("<=", "\u5c0f\u4e8e\u7b49\u4e8e", "\u7b26\u53f7", "\u67d0\u6570\u5c0f\u4e8e\u7b49\u4e8e\u67d0\u6570", 0);
        ArrayList<FunctionData> functionData = new ArrayList<FunctionData>();
        functionData.add(oper1);
        functionData.add(oper2);
        functionData.add(oper3);
        functionData.add(oper4);
        functionData.add(oper5);
        functionData.add(oper6);
        functionData.add(oper7);
        functionData.add(oper8);
        functionData.add(oper9);
        return functionData;
    }
}

