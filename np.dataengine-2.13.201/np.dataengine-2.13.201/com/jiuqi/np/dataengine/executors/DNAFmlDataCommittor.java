/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.sql.SQLException;

public class DNAFmlDataCommittor
extends ExecutorBase {
    private FmlDataCommittor committor;

    public DNAFmlDataCommittor(QueryContext context, FmlDataCommittor committor) {
        super(context);
        this.committor = committor;
    }

    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        this.committor.commitData(this.context);
        return true;
    }

    public static interface FmlDataCommittor {
        public void commitData(QueryContext var1) throws SQLException, ExpressionException, ParseException;
    }
}

