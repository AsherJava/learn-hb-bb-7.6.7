/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.common.consts.CheckState;
import com.jiuqi.va.biz.scene.intf.SceneEditProp;
import com.jiuqi.va.biz.utils.FormulaUtils;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SceneEditPropImpl
implements SceneEditProp {
    private static final Logger log = LoggerFactory.getLogger(SceneEditPropImpl.class);
    private UUID id;
    private String remark;
    private String expression;
    private IExpression compileExpression;
    private CheckState checkState;
    private String propTable;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public IExpression getCompileExpression() {
        return this.compileExpression;
    }

    public CheckState getCheckState() {
        return this.checkState;
    }

    public String computePropTable(ModelDefine modelDefine) {
        return FormulaUtils.computePropTable(modelDefine, this.getCompileExpression());
    }

    @Override
    public String getPropTable() {
        return this.propTable;
    }

    public void setPropTable(String propTable) {
        this.propTable = propTable;
    }

    public void setCompileExpression(IExpression expression) {
        this.compileExpression = expression;
    }
}

