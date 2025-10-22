/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.io.Serializable;

public class PeriodConditionNode
implements Comparable<PeriodConditionNode>,
Serializable {
    private static final long serialVersionUID = -4265894310676903491L;
    private String filter;
    private IASTNode filterNode;
    private String typeCode = "Y";

    public PeriodConditionNode(String filter, IASTNode filterNode) {
        this.filter = filter;
        this.filterNode = filterNode;
    }

    public String getFilter() {
        return this.filter;
    }

    public IASTNode getFilterNode() {
        return this.filterNode;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setFilterNode(IASTNode filterNode) {
        this.filterNode = filterNode;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String toString() {
        return "LJFilterNdoe [filter=" + this.filter + "]";
    }

    public void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u6708\u4efd\u7d2f\u8ba1\uff08").append(this.filterNode.interpret(context, Language.EXPLAIN, info)).append(")");
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.filter == null ? 0 : this.filter.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PeriodConditionNode other = (PeriodConditionNode)obj;
        return !(this.filter == null ? other.filter != null : !this.filter.equals(other.filter));
    }

    @Override
    public int compareTo(PeriodConditionNode o) {
        return this.filter.compareTo(o.filter);
    }
}

