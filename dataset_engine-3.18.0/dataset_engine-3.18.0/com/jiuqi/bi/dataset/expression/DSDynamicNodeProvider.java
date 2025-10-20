/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSRestrictNode;
import com.jiuqi.bi.dataset.expression.RestrictTagNode;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSDynamicNodeProvider
implements IDynamicNodeProvider {
    private Map<String, BIDataSetFieldInfo> finder;
    private DSModel dsModel;

    public DSDynamicNodeProvider(Map<String, BIDataSetFieldInfo> fieldMap) {
        this.finder = fieldMap;
    }

    public DSDynamicNodeProvider(DSModel dsModel, BIDataSet dataset) {
        this.dsModel = dsModel;
        List columns = dataset.getMetadata().getColumns();
        this.finder = new HashMap<String, BIDataSetFieldInfo>();
        for (Column column : columns) {
            this.finder.put(column.getName().toUpperCase(), (BIDataSetFieldInfo)column.getInfo());
        }
    }

    public void setDSModel(DSModel dsModel) {
        this.dsModel = dsModel;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        if (objPath.size() == 1) {
            String fdName = objPath.get(0);
            DSRestrictNode restrictNode = new DSRestrictNode(token, null, fdName, restrictItems);
            restrictNode.setInfo(this.finder.get(fdName));
            return restrictNode;
        }
        if (objPath.size() == 2) {
            String dsName = objPath.get(0);
            String fdName = objPath.get(1);
            DSRestrictNode restrictNode = new DSRestrictNode(token, dsName, fdName, restrictItems);
            restrictNode.setDsModel(this.dsModel);
            restrictNode.setInfo(this.finder.get(fdName));
            return restrictNode;
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        String name;
        if (objPath.size() != 2) {
            return null;
        }
        String f1 = objPath.get(0);
        String f2 = objPath.get(1);
        if (this.dsModel != null && !(name = this.dsModel.getName()).equalsIgnoreCase(f1)) {
            return null;
        }
        BIDataSetFieldInfo fieldInfo = this.finder.get(f2.toUpperCase());
        if (fieldInfo != null) {
            return new DSFieldNode(token, fieldInfo);
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        if (RestrictionTag.isMB(refName) || RestrictionTag.isALL(refName) || RestrictionTag.isPREV(refName) || RestrictionTag.isNEXT(refName) || RestrictionTag.isCURRENT(refName)) {
            return new RestrictTagNode(token, refName);
        }
        BIDataSetFieldInfo fieldInfo = this.finder.get(refName.toUpperCase());
        if (fieldInfo != null) {
            return new DSFieldNode(token, fieldInfo);
        }
        return null;
    }
}

