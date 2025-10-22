/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.dataengine.common.QueryField
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.data.engine.analysis.define.GroupingConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedGroupingConfig
extends GroupingConfig {
    private final GroupingConfig define;
    private List<QueryField> groupingFields = new ArrayList<QueryField>();
    private List<IExpression> groupingFieldExpressions = new ArrayList<IExpression>();
    private List<Integer> grouppingFieldIndexes = new ArrayList<Integer>();
    private int[] levelLength;
    private Map<QueryField, List<IExpression>> levelExpressions = new HashMap<QueryField, List<IExpression>>();

    public ParsedGroupingConfig(GroupingConfig define) {
        this.define = define;
    }

    public List<IExpression> getGrouppingFieldExpressions() {
        return this.groupingFieldExpressions;
    }

    public List<QueryField> getGroupingQueryFields() {
        return this.groupingFields;
    }

    @Override
    public String getGroupingKey() {
        return this.define.getGroupingKey();
    }

    @Override
    public String getLevelString() {
        return this.define.getLevelString();
    }

    @Override
    public List<String> getGroupingKeyEvalExps() {
        return this.define.getGroupingKeyEvalExps();
    }

    @Override
    public boolean isDiscardDetail() {
        return this.define.isDiscardDetail();
    }

    public int[] getLevelLength() {
        return this.levelLength;
    }

    public void setLevelLength(int[] levelLength) {
        this.levelLength = levelLength;
    }

    public Map<QueryField, List<IExpression>> getLevelExpressions() {
        return this.levelExpressions;
    }

    public List<Integer> getGrouppingFieldIndexes() {
        return this.grouppingFieldIndexes;
    }
}

