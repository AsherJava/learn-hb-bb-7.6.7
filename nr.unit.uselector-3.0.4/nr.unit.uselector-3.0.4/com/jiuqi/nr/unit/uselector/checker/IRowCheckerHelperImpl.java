/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 */
package com.jiuqi.nr.unit.uselector.checker;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IRowCheckerHelperImpl
implements IRowCheckerHelper {
    private Map<String, IRowChecker> checkerMap = new HashMap<String, IRowChecker>();
    private Map<CheckerGroup, List<IRowChecker>> groupsCheckers = new EnumMap<CheckerGroup, List<IRowChecker>>(CheckerGroup.class);

    @Autowired(required=true)
    public IRowCheckerHelperImpl(List<IRowChecker> checkers) {
        this.init(checkers);
    }

    @Override
    public IRowChecker getChecker(String checkerKey) {
        return this.checkerMap.get(checkerKey);
    }

    @Override
    public List<IRowChecker> getCheckersByGroup(CheckerGroup group) {
        return this.groupsCheckers.get((Object)group);
    }

    @Override
    public List<IRowChecker> getFilterSchemeCheckers(IUnitTreeContext context) {
        ArrayList<String> checkerIds = new ArrayList<String>();
        if (context.getFormScheme() == null) {
            checkerIds.addAll(Arrays.asList("#check-with-workflow-status"));
        }
        List<IRowChecker> checkersByGroup = this.getCheckersByGroup(CheckerGroup.FILTER_SCHEME);
        return checkersByGroup.stream().filter(e -> !checkerIds.contains(e.getKeyword())).collect(Collectors.toList());
    }

    private void init(List<IRowChecker> checkers) {
        if (checkers != null) {
            checkers.sort(new CheckerComparator());
            checkers.forEach(c -> this.checkerMap.put(c.getKeyword(), (IRowChecker)c));
            for (CheckerGroup group : CheckerGroup.values()) {
                this.groupsCheckers.put(group, this.groupByCheckerGroup(group, checkers));
            }
        }
    }

    private List<IRowChecker> groupByCheckerGroup(CheckerGroup group, List<IRowChecker> checkers) {
        ArrayList<IRowChecker> groupBy = new ArrayList<IRowChecker>();
        for (IRowChecker checker : checkers) {
            if (!this.isContainsCheckerGroup(group, checker.getGroup())) continue;
            groupBy.add(checker);
        }
        return groupBy;
    }

    private boolean isContainsCheckerGroup(CheckerGroup target, CheckerGroup[] groups) {
        if (null != groups) {
            for (CheckerGroup g : groups) {
                int compareTo = target.compareTo(g);
                if (compareTo != 0) continue;
                return true;
            }
        }
        return false;
    }

    static class CheckerComparator
    implements Comparator<IRowChecker>,
    Serializable {
        private static final long serialVersionUID = 2686682046767733547L;

        CheckerComparator() {
        }

        @Override
        public int compare(IRowChecker o1, IRowChecker o2) {
            return o2.getOrder() - o1.getOrder();
        }
    }
}

