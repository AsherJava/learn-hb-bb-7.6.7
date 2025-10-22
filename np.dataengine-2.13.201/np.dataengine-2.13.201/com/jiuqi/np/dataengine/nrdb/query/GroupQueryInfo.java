/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.np.definition.common.FieldGatherType
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.np.definition.common.FieldGatherType;
import java.util.HashMap;
import java.util.List;

public class GroupQueryInfo {
    public HashMap<String, FieldGatherType> uidGatherTypes;
    public List<Integer> groupColumns;
    public HashMap<Integer, ASTNode> grpByColIndex2Node;
}

