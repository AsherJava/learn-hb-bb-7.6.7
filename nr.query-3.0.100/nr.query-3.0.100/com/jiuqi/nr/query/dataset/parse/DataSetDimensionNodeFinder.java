/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNodeFinder
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.node.DynamicDataNodeFinder;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.query.dataset.parse.DataSetCodeNode;
import com.jiuqi.nr.query.dataset.parse.DataSetCommonNode;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;
import com.jiuqi.nr.query.dataset.parse.DataSetFMDMEnumCodeNode;
import com.jiuqi.nr.query.dataset.parse.DataSetFMDMEnumNode;
import com.jiuqi.nr.query.dataset.parse.DataSetFMDMEnumParentNode;
import com.jiuqi.nr.query.dataset.parse.DataSetFMDMEnumTitleNode;
import com.jiuqi.nr.query.dataset.parse.DataSetParentNode;
import com.jiuqi.nr.query.dataset.parse.DataSetTitleNode;
import java.util.List;

public class DataSetDimensionNodeFinder
extends DynamicDataNodeFinder {
    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        String itemName = null;
        String groupName = null;
        groupName = objPath.get(0);
        itemName = objPath.get(1);
        return DataSetDimensionNodeFinder.createNode(itemName, groupName);
    }

    public static DataSetDimensionNode createNode(String itemName, String groupName) {
        DataSetDimensionNode dimensionNode = null;
        if ("CODE".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetCodeNode(groupName, itemName);
        } else if ("TITLE".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetTitleNode(groupName, itemName);
        } else if ("PARENT".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetParentNode(groupName, itemName);
        }
        return dimensionNode;
    }

    public static DataSetDimensionNode createNode(String tableName, IEntityModel entityModel, String itemName, String sourceTableName, String sourceFieldName) {
        DataSetDimensionNode dimensionNode = null;
        if ("CODE".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetCodeNode(tableName, itemName);
        } else if ("TITLE".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetTitleNode(tableName, itemName);
        } else if ("PARENT".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetParentNode(tableName, itemName);
        } else {
            dimensionNode = new DataSetCommonNode(tableName, itemName);
            ((DataSetCommonNode)dimensionNode).setAttributeCode(itemName);
        }
        dimensionNode.setEntityModel(entityModel);
        dimensionNode.setSourceTableName(sourceTableName);
        dimensionNode.setSourceFieldName(sourceFieldName);
        return dimensionNode;
    }

    public static DataSetDimensionNode createEnumNode(String tableName, IEntityModel entityModel, String itemName, String sourceTableName, String sourceFieldName, DataSetDimensionNode fmdmNode) {
        DataSetFMDMEnumNode dimensionNode = null;
        if ("CODE".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetFMDMEnumCodeNode(tableName, itemName, fmdmNode);
        } else if ("TITLE".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetFMDMEnumTitleNode(tableName, itemName, fmdmNode);
        } else if ("PARENT".equalsIgnoreCase(itemName)) {
            dimensionNode = new DataSetFMDMEnumParentNode(tableName, itemName, fmdmNode);
        }
        dimensionNode.setEntityModel(entityModel);
        dimensionNode.setSourceTableName(sourceTableName);
        dimensionNode.setSourceFieldName(sourceFieldName);
        return dimensionNode;
    }
}

