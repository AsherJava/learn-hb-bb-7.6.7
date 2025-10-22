/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.support;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface IExprParser {
    public String getName();

    @Deprecated
    public void parse(Element var1, Object var2);

    default public void parse(Elements elements, ReportVariableParseVO reportVariableParseVO) {
    }

    default public Object queryEntitys(Object param) {
        return "\u53d8\u91cf\u89e3\u6790\u7c7b\u6ca1\u6709\u5b9e\u73b0\u5bf9\u5e94\u65b9\u6cd5";
    }

    default public List<String> getResourceVarNames() {
        return new ArrayList<String>();
    }

    default public ReentrantLock getReentrantLock() {
        return LockCacheUtil.getCacheLock(NpContextHolder.getContext());
    }
}

