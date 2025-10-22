/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormCheckerFactory;
import com.jiuqi.util.StringUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportFormCheckerFactory
implements IReportFormCheckerFactory {
    private Map<String, IReportFormChecker> sourceMap;
    private List<IReportFormChecker> checkerList;

    @Autowired(required=true)
    public ReportFormCheckerFactory(List<IReportFormChecker> list) {
        this.init(list);
    }

    @Override
    public List<IReportFormChecker> getFormCheckerList() {
        return this.checkerList;
    }

    @Override
    public IReportFormChecker getReportFormChecker(String checkerId) {
        IReportFormChecker treeScheme;
        if (StringUtils.isNotEmpty((String)checkerId) && null != (treeScheme = this.sourceMap.get(checkerId))) {
            return treeScheme;
        }
        return null;
    }

    private void init(List<IReportFormChecker> list) {
        if (null != list && !list.isEmpty()) {
            this.sourceMap = new HashMap<String, IReportFormChecker>();
            this.checkerList = list.stream().sorted(Comparator.comparing(IReportFormChecker::getOrdinary).reversed()).collect(Collectors.toList());
            for (IReportFormChecker checker : this.checkerList) {
                List<IReportFormChecker> groupChecker = checker.getGroupChecker();
                if (null != groupChecker && !groupChecker.isEmpty()) {
                    groupChecker.forEach(e -> this.sourceMap.put(e.getCheckerId(), (IReportFormChecker)e));
                    continue;
                }
                this.sourceMap.put(checker.getCheckerId(), checker);
            }
        }
    }
}

