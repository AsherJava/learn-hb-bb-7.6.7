/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.context.ParseContext;
import com.jiuqi.bi.quickreport.engine.context.ParsingFunction;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.ReportNotFoundException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.RestrictedFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionTag;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;

public class DataSetNodeProvider
implements IDynamicNodeProvider {
    public static final DSField SYS_ROWNUM = new DSField();

    private DSFieldNode createFieldNode(IContext context, Token token, DSModel dataSet, DSField field, boolean fullName) {
        ((ParseContext)context).refField(dataSet.getName(), field.getName());
        return new DSFieldNode(token, dataSet, field, fullName);
    }

    private RestrictedFieldNode createFieldNode(IContext context, Token token, DSModel dataSet, DSField field, String tag, boolean fullName) {
        ((ParseContext)context).refField(dataSet.getName(), field.getName());
        return new RestrictedFieldNode(token, dataSet, field, tag, fullName);
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        DSField field;
        DSModel curDSModel = this.getCurrentDSModel(context);
        if (curDSModel != null && (field = this.findDSField(curDSModel, refName)) != null) {
            return this.createFieldNode(context, token, curDSModel, field, false);
        }
        DSModel dsModel = this.findDSModel(context, refName);
        if (dsModel != null) {
            return new DataSetNode(token, dsModel);
        }
        return null;
    }

    private DSModel getCurrentDSModel(IContext context) {
        ParsingFunction curFunc = ((ParseContext)context).getCurrentFunction();
        return curFunc == null ? null : curFunc.getDataSet();
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        if (objPath.size() > 3) {
            return null;
        }
        String tag = null;
        String dsName = null;
        String fieldName = null;
        if (RestrictionTag.isTag(objPath.get(objPath.size() - 1))) {
            tag = objPath.get(objPath.size() - 1);
            if (objPath.size() == 3) {
                dsName = objPath.get(0);
                fieldName = objPath.get(1);
            } else {
                fieldName = objPath.get(0);
            }
        } else if (objPath.size() == 2) {
            dsName = objPath.get(0);
            fieldName = objPath.get(1);
        } else {
            return null;
        }
        IASTNode fieldNode = this.findGlobal(context, token, dsName, fieldName, tag);
        if (fieldNode != null) {
            return fieldNode;
        }
        return this.findCurrent(context, token, dsName, fieldName, tag);
    }

    private IASTNode findCurrent(IContext context, Token token, String dsName, String fieldName, String tag) {
        DSModel curDataSet = this.getCurrentDSModel(context);
        if (curDataSet == null) {
            return null;
        }
        if (!StringUtils.isEmpty((String)dsName) && !dsName.equalsIgnoreCase(curDataSet.getName())) {
            return null;
        }
        DSField field = this.findDSField(curDataSet, fieldName);
        if (field == null) {
            return null;
        }
        if (tag == null) {
            return this.createFieldNode(context, token, curDataSet, field, !StringUtils.isEmpty((String)dsName));
        }
        return this.createFieldNode(context, token, curDataSet, field, tag, !StringUtils.isEmpty((String)dsName));
    }

    private IASTNode findGlobal(IContext context, Token token, String dsName, String fieldName, String tag) throws DynamicNodeException {
        if (dsName == null) {
            return null;
        }
        DSModel dataset = this.findDSModel(context, dsName);
        if (dataset == null) {
            return null;
        }
        DSField field = this.findDSField(dataset, fieldName);
        if (field == null) {
            return null;
        }
        if (tag == null) {
            return this.createFieldNode(context, token, dataset, field, true);
        }
        return this.createFieldNode(context, token, dataset, field, tag, true);
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        IASTNode fieldNode = objPath.size() == 1 ? this.find(context, token, objPath.get(0)) : this.find(context, token, objPath);
        if (fieldNode instanceof DSFieldNode) {
            ((DSFieldNode)fieldNode).getRestrictions().addAll(restrictItems);
        }
        return fieldNode;
    }

    private DSModel findDSModel(IContext context, String dsName) throws DynamicNodeException {
        try {
            return ((ReportContext)context).openDataSetModel(dsName);
        }
        catch (ReportNotFoundException e) {
            return null;
        }
        catch (ReportContextException e) {
            throw new DynamicNodeException((Throwable)e);
        }
    }

    private DSField findDSField(DSModel dsModel, String fieldName) {
        DSField field = dsModel.findField(fieldName);
        if (field == null) {
            if (SYS_ROWNUM.getName().equalsIgnoreCase(fieldName)) {
                return SYS_ROWNUM;
            }
            return null;
        }
        return field;
    }

    static {
        SYS_ROWNUM.setName("SYS_ROWNUM");
        SYS_ROWNUM.setTitle("\u884c\u53f7");
        SYS_ROWNUM.setValType(5);
        SYS_ROWNUM.setFieldType(FieldType.GENERAL_DIM);
        SYS_ROWNUM.setAggregation(AggregationType.MIN);
        SYS_ROWNUM.setApplyType(ApplyType.PERIOD);
        SYS_ROWNUM.setKeyField("SYS_ROWNUM");
        SYS_ROWNUM.setNameField("SYS_ROWNUM");
        SYS_ROWNUM.setShowPattern("#,##0");
    }
}

