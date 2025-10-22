/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition
 *  com.jiuiqi.nr.unit.treebase.entity.filter.BBLXEntityRowFilter
 *  com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow
 *  com.jiuiqi.nr.unit.treebase.entity.filter.TagEntityRowFilter
 *  com.jiuiqi.nr.unit.treebase.entity.filter.WorkFlowEntityRowFilter
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.treeimpl.filter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition;
import com.jiuiqi.nr.unit.treebase.entity.filter.BBLXEntityRowFilter;
import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.filter.TagEntityRowFilter;
import com.jiuiqi.nr.unit.treebase.entity.filter.WorkFlowEntityRowFilter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.treeimpl.filter.USelectorEntityRowFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UnitTreeNodeFilter
implements IFilterEntityRow {
    private List<String> filterKeys;
    private IUnitTreeContext context;
    private UnitTreeFilterCondition filterCondition;
    private Map<String, IFilterEntityRow> checkerMap = new HashMap<String, IFilterEntityRow>();

    public UnitTreeNodeFilter(IUnitTreeContext context, UnitTreeFilterCondition filterCondition) {
        this.context = context;
        this.filterCondition = filterCondition;
        this.filterKeys = this.initCheckPloy(filterCondition);
    }

    public boolean matchRow(IEntityRow row) {
        String poly;
        IFilterEntityRow checker;
        boolean checked = false;
        Iterator<String> iterator = this.filterKeys.iterator();
        while (iterator.hasNext() && (checked = (checker = this.getChecker(poly = iterator.next())).matchRow(row))) {
        }
        return checked;
    }

    public void setMatchRangeRows(List<IEntityRow> rangeRows) {
        for (String poly : this.filterKeys) {
            this.getChecker(poly).setMatchRangeRows(rangeRows);
        }
    }

    public List<IEntityRow> getMatchResultSet(List<String> filter) {
        return null;
    }

    private List<String> initCheckPloy(UnitTreeFilterCondition filterCondition) {
        String uselector;
        List tagKeys;
        List uploadCodes;
        ArrayList<String> filterKeys = new ArrayList<String>();
        List bblxCodes = filterCondition.getBblx();
        if (null != bblxCodes && !bblxCodes.isEmpty()) {
            filterKeys.add("checkWithBBLX");
        }
        if (null != (uploadCodes = filterCondition.getUpload()) && !uploadCodes.isEmpty()) {
            filterKeys.add("checkWithFlow");
        }
        if (null != (tagKeys = filterCondition.getTags()) && !tagKeys.isEmpty()) {
            filterKeys.add("checkWithTag");
        }
        if (StringUtils.isNotEmpty((String)(uselector = filterCondition.getUselector()))) {
            filterKeys.add("checkWithUSelector");
        }
        return filterKeys;
    }

    private IFilterEntityRow getChecker(String poly) {
        IFilterEntityRow checker = this.checkerMap.get(poly);
        if (null == checker) {
            IUnitTreeEntityDataQuery entityDataQuery = (IUnitTreeEntityDataQuery)SpringBeanUtils.getBean(UnitTreeEntityDataQuery.class);
            switch (poly) {
                case "checkWithBBLX": {
                    checker = new BBLXEntityRowFilter(this.context, entityDataQuery, this.filterCondition.getBblx());
                    break;
                }
                case "checkWithFlow": {
                    checker = new WorkFlowEntityRowFilter(this.context, entityDataQuery, this.filterCondition.getUpload());
                    break;
                }
                case "checkWithTag": {
                    checker = new TagEntityRowFilter(this.context, entityDataQuery, this.filterCondition.getTags());
                    break;
                }
                case "checkWithUSelector": {
                    checker = new USelectorEntityRowFilter(this.filterCondition.getUselector());
                }
            }
            if (null != checker) {
                this.checkerMap.put(poly, checker);
            }
        }
        return checker;
    }
}

