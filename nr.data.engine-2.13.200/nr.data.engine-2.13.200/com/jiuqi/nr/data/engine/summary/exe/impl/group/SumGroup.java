/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 */
package com.jiuqi.nr.data.engine.summary.exe.impl.group;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.GroupSumCell;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SumGroup {
    private IExpression conditon;
    private int index;
    private List<GroupSumCell> groupSumCells = new ArrayList<GroupSumCell>();
    private String key;

    public SumGroup(IExpression conditon, String key) {
        this.conditon = conditon;
        this.key = key;
    }

    public void runStatistic(SumContext context, ResultSet rs) {
        try {
            if (this.conditon == null || this.conditon.judge((IContext)context)) {
                for (GroupSumCell cell : this.groupSumCells) {
                    cell.runStatistic(rs);
                }
            }
        }
        catch (Exception e) {
            context.getLogger().error(e.getMessage(), e);
        }
    }

    public void doSave(IDataRow row) throws IncorrectQueryException, SQLException {
        for (GroupSumCell cell : this.groupSumCells) {
            cell.setResultToUpdator(row);
        }
    }

    public IExpression getConditon() {
        return this.conditon;
    }

    public void setConditon(IExpression conditon) {
        this.conditon = conditon;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<GroupSumCell> getGroupSumCells() {
        return this.groupSumCells;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
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
        SumGroup other = (SumGroup)obj;
        return !(this.key == null ? other.key != null : !this.key.equals(other.key));
    }

    public String toString() {
        return "SumGroup [conditon=" + this.conditon + ", key=" + this.key + "]";
    }
}

