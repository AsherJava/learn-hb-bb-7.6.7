/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFunctionNode;
import com.jiuqi.bi.dataset.expression.DSRestrictNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.idx.IdxFilterInfo;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.restrict.AggrCondition;
import com.jiuqi.bi.dataset.restrict.CondKey;
import com.jiuqi.bi.dataset.restrict.ExpressionCondition;
import com.jiuqi.bi.dataset.restrict.FilterExecutor;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.MixOffsetValueCondition;
import com.jiuqi.bi.dataset.restrict.OffsetCondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.dataset.restrict.ValueListCondition;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FilterOptimizer {
    private BIDataSetImpl dataset;
    private Map<IASTNode, CondInfo> restrictionMap = new HashMap<IASTNode, CondInfo>();
    private boolean ignoreCalcFieldDefaultRst = false;

    public void analysis(IASTNode root, DSFormulaContext context) throws BIDataSetException {
        this.analysis(root, context, false);
    }

    public void analysis(IASTNode root, DSFormulaContext context, boolean ignoreCalcFieldDefaultRst) throws BIDataSetException {
        this.restrictionMap.clear();
        this.dataset = context.getDataSet();
        this.ignoreCalcFieldDefaultRst = ignoreCalcFieldDefaultRst;
        for (IASTNode node : root) {
            this.analysisNode(node, context);
        }
    }

    public CondKey calcKey(IASTNode node, DSFormulaContext dsCxt) throws BIDataSetException {
        CondInfo condInfo = this.restrictionMap.get(node);
        if (condInfo == null) {
            throw new BIDataSetException("\u672a\u627e\u5230\u8be5\u8282\u70b9\u7684\u9650\u5b9a\u4fe1\u606f");
        }
        List<ICondition> rstConds = condInfo.conds;
        Object[] filterValue = new Object[rstConds.size()];
        for (int i = 0; i < rstConds.size(); ++i) {
            filterValue[i] = rstConds.get(i) instanceof AggrCondition ? null : rstConds.get(i).getValue(dsCxt);
        }
        return new CondKey(node, filterValue);
    }

    public List<Integer> getRestrictFields(IASTNode node) throws BIDataSetException {
        CondInfo condInfo = this.restrictionMap.get(node);
        if (condInfo == null) {
            throw new BIDataSetException("\u672a\u627e\u5230\u8be5\u8282\u70b9\u7684\u9650\u5b9a\u4fe1\u606f");
        }
        return new ArrayList<Integer>(condInfo.restricts);
    }

    public BIDataSetImpl evalFilter(IASTNode node, DSFormulaContext dsCxt) throws BIDataSetException {
        CondInfo condInfo = this.restrictionMap.get(node);
        if (condInfo == null) {
            throw new BIDataSetException("\u672a\u627e\u5230\u8be5\u8282\u70b9\u7684\u9650\u5b9a\u4fe1\u606f");
        }
        ArrayList<IdxFilterInfo> idxFilters = new ArrayList<IdxFilterInfo>();
        ArrayList<IASTNode> exprFilters = new ArrayList<IASTNode>();
        List<ICondition> rstConds = condInfo.conds;
        for (ICondition cond : rstConds) {
            Object value;
            if (cond.canUseIndex()) {
                List<Object> data;
                Object obj = cond.getValue(dsCxt);
                if (obj instanceof Object[]) {
                    data = Arrays.asList((Object[])obj);
                } else {
                    data = new ArrayList<Object>(1);
                    data.add(obj);
                }
                idxFilters.add(new IdxFilterInfo(cond.getCol(), data));
                continue;
            }
            if (cond instanceof AggrCondition || !((value = cond.getValue(dsCxt)) instanceof IExpression)) continue;
            IExpression expr = (IExpression)value;
            exprFilters.add(expr.getChild(0));
        }
        return FilterExecutor.doFilter(this.dataset, idxFilters, exprFilters, dsCxt, false);
    }

    private void analysisNode(IASTNode node, DSFormulaContext context) throws BIDataSetException {
        ArrayList<RestrictionDescriptor> restricts = null;
        if (node instanceof DSRestrictNode) {
            restricts = new ArrayList<RestrictionDescriptor>();
            DSRestrictNode rstNode = (DSRestrictNode)node;
            rstNode.setOptimizer(this);
            List<IASTNode> rstItems = rstNode.getRestrictItems();
            for (IASTNode nd : rstItems) {
                FilterExecutor.analysisNode(restricts, nd, context);
            }
        } else if (node instanceof DSFunctionNode) {
            restricts = new ArrayList();
            DSFunctionNode funcNode = (DSFunctionNode)node;
            DSFunction func = (DSFunction)funcNode.getDefine();
            funcNode.setOptimizer(this);
            List<IASTNode> params = func.getFilterItems(funcNode.getParams());
            for (IASTNode nd : params) {
                FilterExecutor.analysisNode(restricts, nd, context);
            }
        }
        if (restricts != null) {
            this.appendDefaultRestrict(restricts);
            List<ICondition> conds = this.transform(restricts);
            List columns = this.dataset.getMetadata().getColumns();
            ArrayList<Integer> restrictDimCols = new ArrayList<Integer>();
            for (ICondition cond : conds) {
                cond.validate();
                if (cond instanceof AggrCondition) continue;
                if (cond instanceof ExpressionCondition) {
                    IExpression expr = (IExpression)cond.getValue(context);
                    Set<Integer> hasRstNames = this.searchDimColFromExpression(expr);
                    restrictDimCols.addAll(hasRstNames);
                    continue;
                }
                Column column = (Column)columns.get(cond.getCol());
                FieldType fType = ((BIDataSetFieldInfo)column.getInfo()).getFieldType();
                if (fType == null || !fType.isDimField()) continue;
                restrictDimCols.add(column.getIndex());
            }
            this.restrictionMap.put(node, new CondInfo(conds, restrictDimCols));
        }
    }

    private List<ICondition> transform(List<RestrictionDescriptor> restricts) throws BIDataSetException {
        ArrayList<ICondition> conditions = new ArrayList<ICondition>();
        ArrayList<RestrictionDescriptor> timeRst = new ArrayList<RestrictionDescriptor>();
        boolean hasRstTimekey = false;
        boolean hasRstComTime = false;
        for (RestrictionDescriptor rstDesc : restricts) {
            if (rstDesc.item == null) {
                conditions.add(FilterExecutor.transform(this.dataset, rstDesc));
                continue;
            }
            if (rstDesc.item.getName().equals("SYS_TIMEKEY")) {
                hasRstTimekey = true;
                timeRst.add(rstDesc);
                continue;
            }
            BIDataSetFieldInfo info = rstDesc.item;
            FieldType fType = info.getFieldType();
            if (info.getName().equals("SYS_TIMEKEY") || fType.isTimeDimField()) {
                timeRst.add(rstDesc);
                if (rstDesc.item.isTimekey()) {
                    hasRstTimekey = true;
                    continue;
                }
                hasRstComTime = true;
                continue;
            }
            conditions.add(FilterExecutor.transform(this.dataset, rstDesc));
        }
        if (!hasRstTimekey || hasRstComTime) {
            // empty if block
        }
        List<ICondition> timeConds = this.transformTimeDimRestriction(timeRst);
        conditions.addAll(timeConds);
        return conditions;
    }

    private void appendDefaultRestrict(List<RestrictionDescriptor> curRestrict) {
        Set<String> appearNames = this.getAppearFieldNames(curRestrict);
        boolean autoFill = this.isNeedAutoFillTimeRestrict(curRestrict);
        boolean hasTimeRst = this.checkTimeDimRestrict(appearNames);
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        Column sys_timekey = metadata.find("SYS_TIMEKEY");
        Set<Integer> parCols = this.getParentColSet();
        ArrayList<RestrictionDescriptor> appendTimeRst = new ArrayList<RestrictionDescriptor>();
        List columns = metadata.getColumns();
        for (Column column : columns) {
            FieldType fType;
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            String name = info.getName().toUpperCase();
            if (appearNames.contains(name) || (fType = info.getFieldType()) == null || !fType.isDimField()) continue;
            if (fType.isTimeDimField()) {
                int days;
                if (!autoFill || !hasTimeRst) continue;
                TimeGranularity tg = info.getTimegranularity();
                if (info.isTimekey() && !this.isNeedAutoFillTimekeyRestrict(tg, curRestrict) || sys_timekey != null && (tg == TimeGranularity.QUARTER || tg == TimeGranularity.XUN || tg == TimeGranularity.WEEK) && (days = ((BIDataSetFieldInfo)sys_timekey.getInfo()).getTimegranularity().days()) < tg.days()) continue;
                RestrictionDescriptor rstDesc = this.createCURTagRst((Column<BIDataSetFieldInfo>)column);
                appendTimeRst.add(rstDesc);
                continue;
            }
            boolean isParent = parCols.contains(column.getIndex());
            if (!this.isDimNeedAppendDefaultRst(info, isParent)) continue;
            RestrictionDescriptor rstDesc = this.createCURTagRst((Column<BIDataSetFieldInfo>)column);
            curRestrict.add(rstDesc);
        }
        if (sys_timekey == null) {
            curRestrict.addAll(appendTimeRst);
        } else if (!hasTimeRst) {
            RestrictionDescriptor rstDesc = this.createCURTagRst((Column<BIDataSetFieldInfo>)sys_timekey);
            curRestrict.add(rstDesc);
        } else {
            curRestrict.addAll(appendTimeRst);
        }
    }

    private Set<Integer> getParentColSet() {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        Map props = metadata.getProperties();
        List hiers = (List)props.get("HIERARCHY");
        HashSet<Integer> parCols = new HashSet<Integer>();
        if (hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                int col = metadata.indexOf(hier.getParentFieldName());
                parCols.add(col);
            }
        }
        return parCols;
    }

    private boolean checkTimeDimRestrict(Set<String> appearNames) {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        List columns = metadata.getColumns();
        for (Column column : columns) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (!appearNames.contains(info.getName().toUpperCase()) || !info.getName().equals("SYS_TIMEKEY") && !info.getFieldType().isTimeDimField()) continue;
            return true;
        }
        return false;
    }

    private boolean isDimNeedAppendDefaultRst(BIDataSetFieldInfo info, boolean isParent) {
        if (isParent) {
            return false;
        }
        String keyField = info.getKeyField();
        if (keyField == null || !keyField.equalsIgnoreCase(info.getName())) {
            return false;
        }
        return !info.isCalcField() || !info.isDimention() || !this.ignoreCalcFieldDefaultRst;
    }

    private List<ICondition> transformTimeDimRestriction(List<RestrictionDescriptor> timeRestrict) throws BIDataSetException {
        Iterator cond;
        ArrayList<ICondition> conds = new ArrayList<ICondition>();
        if (timeRestrict.size() == 0) {
            return conds;
        }
        ArrayList<RestrictionDescriptor> commRst = new ArrayList<RestrictionDescriptor>();
        ArrayList<RestrictionDescriptor> offsRst = new ArrayList<RestrictionDescriptor>();
        ArrayList<RestrictionDescriptor> aggrRst = new ArrayList<RestrictionDescriptor>();
        for (RestrictionDescriptor rstDesc : timeRestrict) {
            if (rstDesc.mode == 2) {
                commRst.add(rstDesc);
                continue;
            }
            if (rstDesc.mode == 1) {
                offsRst.add(rstDesc);
                continue;
            }
            if (rstDesc.mode != 0) continue;
            String tag = (String)rstDesc.condition;
            if (RestrictionTag.isCURRENT(tag)) {
                commRst.add(rstDesc);
                continue;
            }
            if (RestrictionTag.isALL(tag) || RestrictionTag.isMB(tag)) {
                aggrRst.add(rstDesc);
                continue;
            }
            throw new BIDataSetException("\u672a\u652f\u6301\u7684\u9650\u5b9a\u5173\u952e\u5b57\uff1a" + tag);
        }
        if (aggrRst.size() != 0) {
            for (RestrictionDescriptor rstDesc : timeRestrict) {
                cond = FilterExecutor.transform(this.dataset, rstDesc);
                conds.add((ICondition)((Object)cond));
            }
        } else if (commRst.size() != 0 && offsRst.size() != 0) {
            if (offsRst.size() > 1) {
                throw new BIDataSetException("\u8868\u8fbe\u5f0f\u4e2d\u5b58\u5728\u591a\u4e2a\u65f6\u671f\u504f\u79fb\u3002\u5982\u679c\u5bf9\u65f6\u671f\u8fdb\u884c\u503c\u9650\u5b9a\uff0c\u5219\u53ea\u80fd\u5bf9\u5e74\u8fdb\u884c\u504f\u79fb");
            }
            ArrayList<RestrictionDescriptor> ymrRst = new ArrayList<RestrictionDescriptor>();
            boolean useTimekey = true;
            for (RestrictionDescriptor rdr : commRst) {
                TimeGranularity tg = rdr.item.getTimegranularity();
                if (this.isYearorMonthorDay(tg)) {
                    ymrRst.add(rdr);
                    continue;
                }
                useTimekey = false;
                conds.add(new ValueListCondition(this.dataset, rdr));
            }
            if (ymrRst.size() == 0) {
                if (useTimekey) {
                    conds.add(new OffsetCondition(this.dataset, offsRst, useTimekey));
                } else {
                    for (RestrictionDescriptor rd : offsRst) {
                        ArrayList<RestrictionDescriptor> v = new ArrayList<RestrictionDescriptor>(1);
                        v.add(rd);
                        conds.add(new OffsetCondition(this.dataset, v, useTimekey));
                    }
                }
            } else {
                conds.add(new MixOffsetValueCondition(this.dataset, (RestrictionDescriptor)offsRst.get(0), ymrRst));
            }
        } else if (commRst.size() == 0) {
            conds.add(new OffsetCondition(this.dataset, offsRst, true));
        } else {
            for (RestrictionDescriptor rstDesc : commRst) {
                cond = FilterExecutor.transform(this.dataset, rstDesc);
                conds.add((ICondition)((Object)cond));
            }
        }
        return conds;
    }

    private boolean isYearorMonthorDay(TimeGranularity tg) {
        return tg == TimeGranularity.YEAR || tg == TimeGranularity.MONTH || tg == TimeGranularity.DAY;
    }

    private boolean isNeedAutoFillTimeRestrict(List<RestrictionDescriptor> curRestrict) {
        boolean hasTimeRst = false;
        for (RestrictionDescriptor rstDesc : curRestrict) {
            BIDataSetFieldInfo info = rstDesc.item;
            if (info == null || info.getName().equals("SYS_TIMEKEY") || !info.getFieldType().isTimeDimField()) continue;
            hasTimeRst = true;
            if (info.isTimekey()) {
                return false;
            }
            if (rstDesc.mode != 1 || info.getTimegranularity() == TimeGranularity.YEAR) continue;
            return false;
        }
        return hasTimeRst;
    }

    private boolean isNeedAutoFillTimekeyRestrict(TimeGranularity timekeyGranularity, List<RestrictionDescriptor> curRestrict) {
        for (RestrictionDescriptor rstDesc : curRestrict) {
            if (rstDesc.mode == 3) {
                IExpression expr = (IExpression)rstDesc.condition;
                for (IASTNode node : expr) {
                    if (!(node instanceof DSFieldNode)) continue;
                    return false;
                }
                continue;
            }
            if (rstDesc.mode == 1) {
                return false;
            }
            BIDataSetFieldInfo info = rstDesc.item;
            if (info.getName().equals("SYS_TIMEKEY") || !info.getFieldType().isTimeDimField()) continue;
            if (info.isTimekey()) {
                return false;
            }
            if (info.getTimegranularity().days() > timekeyGranularity.days()) continue;
            return false;
        }
        return true;
    }

    private RestrictionDescriptor createCURTagRst(Column<BIDataSetFieldInfo> sys_timekeyCol) {
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)sys_timekeyCol.getInfo();
        int colIdx = sys_timekeyCol.getIndex();
        return new RestrictionDescriptor(0, info, colIdx, "CURRENT");
    }

    private Set<String> getAppearFieldNames(List<RestrictionDescriptor> rst) {
        HashSet<String> hasRstColNames = new HashSet<String>();
        for (RestrictionDescriptor rstDesc : rst) {
            if (rstDesc.mode == 3) {
                IExpression expr = (IExpression)rstDesc.condition;
                Set<String> hasRstNames = this.searchNameFromExpression(expr);
                hasRstColNames.addAll(hasRstNames);
                continue;
            }
            BIDataSetFieldInfo info = rstDesc.item;
            hasRstColNames.add(info.getName().toUpperCase());
            if (!StringUtils.isNotEmpty((String)info.getKeyField())) continue;
            hasRstColNames.add(info.getKeyField().toUpperCase());
        }
        return hasRstColNames;
    }

    private Set<String> searchNameFromExpression(IExpression expr) {
        HashSet<String> fieldNames = new HashSet<String>();
        for (IASTNode node : expr) {
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode field = (DSFieldNode)node;
            BIDataSetFieldInfo info = field.getFieldInfo();
            fieldNames.add(info.getName().toUpperCase());
            if (!StringUtils.isNotEmpty((String)info.getKeyField())) continue;
            fieldNames.add(info.getKeyField().toUpperCase());
        }
        return fieldNames;
    }

    private Set<Integer> searchDimColFromExpression(IExpression expr) {
        HashSet<Integer> fieldCols = new HashSet<Integer>();
        for (IASTNode node : expr) {
            DSFieldNode field;
            BIDataSetFieldInfo info;
            if (!(node instanceof DSFieldNode) || !(info = (field = (DSFieldNode)node).getFieldInfo()).getFieldType().isDimField()) continue;
            Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
            fieldCols.add(metadata.indexOf(info.getName()));
            if (!StringUtils.isNotEmpty((String)info.getKeyField())) continue;
            fieldCols.add(metadata.indexOf(info.getKeyField()));
        }
        return fieldCols;
    }

    private final class CondInfo {
        public final List<ICondition> conds;
        public final List<Integer> restricts;

        public CondInfo(List<ICondition> conds, List<Integer> restricts) {
            this.conds = conds;
            this.restricts = restricts;
        }
    }
}

