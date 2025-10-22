/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExprExecContext;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.TableUpdateInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataTableWrapper {
    public ArrayList<TableUpdateInfo> tableUpdateInfos = new ArrayList();
    public int singleOrderByFieldIndex;
    public boolean keepTableAsQueryResult;
    public boolean hasKeyCalc;
    public ArrayList<Expression> calcExprs;
    public ArrayList<Expression> verifyExprs;
    public IASTNode rowFilterNode;
    public HashMap<UUID, Integer> fieldColumnIndex = new HashMap();
    public HashMap<Integer, Integer> groupByNodeIndex2SelectColumnIndex = new HashMap();
    public ArrayList<IASTNode> evalNodes = new ArrayList();
    public ExprExecContext execContext;
    public int beginDateColIndex;
    public int endDateColIndex;
    public int groupingFlagColIndex;

    public DataTableWrapper() {
    }

    public DataTableWrapper(QueryContext globalContext) {
        this();
        this.execContext = new ExprExecContext(globalContext);
        this.execContext.LocalContext = this;
    }

    public void setIntervalInfo(int startRowIndex, int endRowIndex, int ignoreFirstCubeRow) {
    }

    public void commitChanges(DimensionValueSet masterKeys, boolean deleteAllRows, List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows, Date queryVersionStartDate, Date queryVersionDate, boolean cascadeDeletion) {
    }
}

