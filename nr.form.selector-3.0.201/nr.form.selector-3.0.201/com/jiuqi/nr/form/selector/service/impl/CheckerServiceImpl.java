/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.form.selector.service.impl;

import com.jiuqi.nr.form.selector.service.CheckerService;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormCheckerFactory;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckerServiceImpl
implements CheckerService {
    @Resource
    private IReportFormCheckerFactory checkerFactory;

    @Override
    public List<IReportFormChecker> getAllFormCheckerList() {
        List<IReportFormChecker> formCheckerList = this.checkerFactory.getFormCheckerList();
        if (formCheckerList != null) {
            return formCheckerList.stream().filter(e -> e.isDisplay()).sorted(Comparator.comparing(IReportFormChecker::getOrdinary)).collect(Collectors.toList());
        }
        return null;
    }
}

