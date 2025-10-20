/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QParserHelper {
    private QParserHelper() {
    }

    public static void evalInterpret(IContext context, IASTNode node, StringBuilder buffer) throws InterpretException {
        int dataType;
        Object value;
        try {
            value = node.evaluate(context);
            dataType = node.getType(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        if (dataType == 0) {
            dataType = DataType.typeOf((Object)value);
        }
        DataNode dataNode = new DataNode(null, dataType, value);
        dataNode.interpret(context, buffer, Language.FORMULA, null);
    }

    public static BIDataSet removeParentFields(BIDataSet dataSet, DSModel model) throws BIDataSetException {
        Set<String> parentFields = QParserHelper.getParentFields(model);
        if (parentFields.isEmpty()) {
            return dataSet;
        }
        List<String> fields = QParserHelper.removeParentFields(dataSet, parentFields);
        return dataSet.selectFields(fields);
    }

    private static Set<String> getParentFields(DSModel model) {
        HashSet<String> parents = new HashSet<String>();
        for (DSHierarchy hier : model.getHiers()) {
            if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
            parents.add(hier.getParentFieldName());
        }
        return parents;
    }

    private static List<String> removeParentFields(BIDataSet dataSet, Set<String> parentFields) {
        ArrayList<String> fields = new ArrayList<String>();
        for (Column col : dataSet.getMetadata()) {
            if (parentFields.contains(col.getName())) continue;
            fields.add(col.getName());
        }
        return fields;
    }
}

