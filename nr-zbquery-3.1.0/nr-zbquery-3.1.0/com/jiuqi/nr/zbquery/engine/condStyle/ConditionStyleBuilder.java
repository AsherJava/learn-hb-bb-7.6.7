/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.builder.define.FieldConditionStyle
 *  com.jiuqi.bi.quickreport.builder.define.FieldDefine
 */
package com.jiuqi.nr.zbquery.engine.condStyle;

import com.jiuqi.bi.quickreport.builder.define.FieldConditionStyle;
import com.jiuqi.bi.quickreport.builder.define.FieldDefine;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.model.CellConditionStyle;
import com.jiuqi.nr.zbquery.model.ConditionOperationItem;
import com.jiuqi.nr.zbquery.model.ConditionStyleField;
import com.jiuqi.nr.zbquery.model.ConditionStyleType;
import com.jiuqi.nr.zbquery.model.Logic;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.util.ConditionExpGenerator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class ConditionStyleBuilder {
    private final QueryDSModelBuilder dsModelBuilder;
    private final boolean sumNodeBold;

    public ConditionStyleBuilder(QueryDSModelBuilder dsModelBuilder) {
        this(dsModelBuilder, false);
    }

    public ConditionStyleBuilder(QueryDSModelBuilder dsModelBuilder, boolean sumNodeBold) {
        this.dsModelBuilder = dsModelBuilder;
        this.sumNodeBold = sumNodeBold;
    }

    public void buildCondStyles(FieldDefine fieldDefine, QueryField queryField) {
        List<FieldConditionStyle> condStyles = this.buildCondStylesInternal(fieldDefine, queryField);
        if (CollectionUtils.isEmpty(condStyles) && this.sumNodeBold) {
            FieldConditionStyle condStyle = new FieldConditionStyle();
            condStyle.setBold(true);
            condStyle.setCondition(String.format("NOT Q_IsLastLevel(%s)", "DS1." + fieldDefine.getName()));
            condStyles.add(condStyle);
        }
        fieldDefine.getConditionStyles().addAll(condStyles);
    }

    private List<FieldConditionStyle> buildCondStylesInternal(FieldDefine fieldDefine, QueryField queryField) {
        ArrayList<FieldConditionStyle> conditionStyles = new ArrayList<FieldConditionStyle>();
        ConditionStyleField conditionStyleField = this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getConditionStyleField(queryField.getFullName());
        if (conditionStyleField == null) {
            return conditionStyles;
        }
        if (!conditionStyleField.isEnableCellStyle()) {
            return conditionStyles;
        }
        List<CellConditionStyle> cellStyles = conditionStyleField.getCellStyles();
        if (CollectionUtils.isEmpty(cellStyles)) {
            return conditionStyles;
        }
        ArrayList<String> helpCondWhenSumNodeBold = new ArrayList<String>();
        String sumNodeBoldExp = String.format("NOT Q_IsLastLevel(%s) ", "DS1." + fieldDefine.getName());
        for (CellConditionStyle cellStyle : cellStyles) {
            FieldConditionStyle cond;
            String condExp;
            List<ConditionOperationItem> conditionItems;
            if (!cellStyle.getConditionType().equals((Object)ConditionStyleType.SIMPLE) || CollectionUtils.isEmpty(conditionItems = cellStyle.getConditions())) continue;
            ArrayList<String> conditions = new ArrayList<String>();
            for (ConditionOperationItem condItem : conditionItems) {
                String oneCondition = ConditionExpGenerator.generate(condItem.getOperation(), queryField.getDataType(), "DS1", fieldDefine.getName(), null, condItem.getValues().toArray());
                conditions.add(oneCondition);
            }
            String string = condExp = cellStyle.getLogic() != null && cellStyle.getLogic().equals((Object)Logic.AND) ? String.join((CharSequence)" AND ", conditions) : String.join((CharSequence)" OR ", conditions);
            if (this.sumNodeBold) {
                if (!CollectionUtils.isEmpty(helpCondWhenSumNodeBold)) {
                    if (cellStyle.getBold() == null) {
                        String helpCondExp = String.join((CharSequence)" OR ", helpCondWhenSumNodeBold);
                        FieldConditionStyle cond1 = new FieldConditionStyle();
                        cond1.setBold(true);
                        cond1.setForegroundColor(cellStyle.getForegroundColor());
                        cond1.setBackgroundColor(cellStyle.getBackgroundColor());
                        cond1.setCondition(String.format("NOT (%s) AND (%s) AND %s", helpCondExp, condExp, sumNodeBoldExp));
                        FieldConditionStyle cond2 = new FieldConditionStyle();
                        cond2.setBold(false);
                        cond2.setForegroundColor(cellStyle.getForegroundColor());
                        cond2.setBackgroundColor(cellStyle.getBackgroundColor());
                        cond2.setCondition(condExp);
                        conditionStyles.add(cond1);
                        conditionStyles.add(cond2);
                    } else {
                        cond = new FieldConditionStyle();
                        cond.setBold(cellStyle.getBold().booleanValue());
                        cond.setForegroundColor(cellStyle.getForegroundColor());
                        cond.setBackgroundColor(cellStyle.getBackgroundColor());
                        cond.setCondition(condExp);
                        conditionStyles.add(cond);
                    }
                } else if (cellStyle.getBold() == null) {
                    FieldConditionStyle cond1 = new FieldConditionStyle();
                    cond1.setBold(true);
                    cond1.setForegroundColor(cellStyle.getForegroundColor());
                    cond1.setBackgroundColor(cellStyle.getBackgroundColor());
                    cond1.setCondition(String.format("(%s) AND %s", condExp, sumNodeBoldExp));
                    FieldConditionStyle cond2 = new FieldConditionStyle();
                    cond2.setBold(false);
                    cond2.setForegroundColor(cellStyle.getForegroundColor());
                    cond2.setBackgroundColor(cellStyle.getBackgroundColor());
                    cond2.setCondition(condExp);
                    conditionStyles.add(cond1);
                    conditionStyles.add(cond2);
                } else {
                    cond = new FieldConditionStyle();
                    cond.setBold(cellStyle.getBold().booleanValue());
                    cond.setForegroundColor(cellStyle.getForegroundColor());
                    cond.setBackgroundColor(cellStyle.getBackgroundColor());
                    cond.setCondition(condExp);
                    conditionStyles.add(cond);
                }
                helpCondWhenSumNodeBold.add("(" + condExp + ")");
                continue;
            }
            cond = new FieldConditionStyle();
            cond.setBold(cellStyle.getBold() != null && cellStyle.getBold() != false);
            cond.setForegroundColor(cellStyle.getForegroundColor());
            cond.setBackgroundColor(cellStyle.getBackgroundColor());
            cond.setCondition(condExp);
            conditionStyles.add(cond);
        }
        if (this.sumNodeBold) {
            FieldConditionStyle cond = new FieldConditionStyle();
            cond.setBold(true);
            cond.setCondition(sumNodeBoldExp);
            conditionStyles.add(cond);
        }
        return conditionStyles;
    }
}

