/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.support;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource;
import com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor;
import com.jiuqi.nr.analysisreport.compatible.CompatibleParseHelper;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.support.IExprParser;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ReportExprParseSupport {
    private Logger logger = LoggerFactory.getLogger(ReportExprParseSupport.class);
    @Lazy
    @Autowired(required=false)
    private List<IExprParser> exprParsers;
    @Autowired
    private CompatibleParseHelper compatibleParseHelper;
    private List<Map<String, Object>> resources;
    private static final String EXPR_KEY = "exprKey";
    private static final String VARIABLE_NAME = "name";
    private static final String RESOURCE_ORDER = "order";
    private static final String RESOURCE_PLUGIN_NAME = "pluginName";
    private static final String RESOURCE_PLUGIN_TYPE = "pluginType";

    @Deprecated
    public void support(Element es, Object ext) {
        for (int i = 0; i < this.exprParsers.size(); ++i) {
            IExprParser parser = this.exprParsers.get(i);
            if (!es.hasClass(parser.getName()) && parser.getResourceVarNames().stream().filter(e -> es.hasClass(e)).count() <= 0L) continue;
            parser.parse(es, ext);
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void support(ReportVariableParseVO reportVariableParseVO, AnalysisReportDefine reportDefine) throws InterruptedException {
        try {
            if (CollectionUtils.isEmpty(this.exprParsers)) {
                return;
            }
            String cacheKey = LockCacheUtil.getCacheKey(NpContextHolder.getContext());
            LockCacheUtil.putCacheLock(cacheKey);
            Document doc = AnaUtils.parseBodyFragment(reportVariableParseVO.getContent());
            Element body = doc.body();
            HashMap<String, Object> extInfo = this.compatibleParseHelper.buildCommonInfo(reportVariableParseVO, reportDefine);
            reportVariableParseVO.setContent(null);
            ArrayList<Callable> extThreads = new ArrayList<Callable>();
            for (int i = 0; i < this.exprParsers.size(); ++i) {
                IExprParser exprParser = this.exprParsers.get(i);
                HashSet<String> varClassNames = new HashSet<String>();
                varClassNames.addAll(exprParser.getResourceVarNames());
                varClassNames.add(exprParser.getName());
                Iterator varClassNameIterator = varClassNames.iterator();
                Elements elements = new Elements();
                while (varClassNameIterator.hasNext()) {
                    String varClassName = (String)varClassNameIterator.next();
                    elements.addAll((Collection)body.getElementsByClass(varClassName));
                }
                if (elements.isEmpty()) continue;
                HashMap commonInfo = (HashMap)extInfo.clone();
                extThreads.add(() -> {
                    exprParser.parse(elements, reportVariableParseVO);
                    this.compatibleParseHelper.parse(elements, exprParser, commonInfo);
                    return true;
                });
            }
            AnalysisReportThreadExuecutor.run(extThreads);
            reportVariableParseVO.setContent(body.toString());
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u89e3\u6790\u53d8\u91cf", "\u53d8\u91cf\u89e3\u6790\u5f02\u5e38" + e.getMessage(), AnalysisReportLogHelper.LOGLEVEL_ERROR);
        }
        finally {
            String cacheKey = LockCacheUtil.getCacheKey(NpContextHolder.getContext());
            LockCacheUtil.removeCacheLock(cacheKey);
        }
    }

    public List<Map<String, Object>> getResources() {
        try {
            this.initResource();
        }
        catch (IllegalAccessException e) {
            this.logger.error("\u521d\u59cb\u5316\u5206\u6790\u62a5\u544a\u63d2\u5165\u53d8\u91cf\u62d3\u5c55\u63d2\u4ef6\u5931\u8d25\uff01");
            throw new RuntimeException("\u521d\u59cb\u5316\u5206\u6790\u62a5\u544a\u63d2\u5165\u53d8\u91cf\u62d3\u5c55\u63d2\u4ef6\u5931\u8d25\uff01");
        }
        return this.resources;
    }

    private void initResource() throws IllegalAccessException {
        if (this.resources == null || this.resources.size() == 0) {
            this.resources = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < this.exprParsers.size(); ++i) {
                IExprParser parser = this.exprParsers.get(i);
                Class<?> clazz = parser.getClass();
                if (!clazz.isAnnotationPresent(AnaReportInsertVariableResource.class)) continue;
                AnaReportInsertVariableResource resource = clazz.getAnnotation(AnaReportInsertVariableResource.class);
                HashMap<String, Object> resourceItem = new HashMap<String, Object>();
                resourceItem.put(EXPR_KEY, parser.getName());
                resourceItem.put(VARIABLE_NAME, resource.name());
                resourceItem.put(RESOURCE_PLUGIN_NAME, resource.pluginName());
                resourceItem.put(RESOURCE_PLUGIN_TYPE, resource.pluginType());
                resourceItem.put(RESOURCE_ORDER, resource.order());
                this.resources.add(resourceItem);
            }
            Collections.sort(this.resources, new Comparator<Map<String, Object>>(){

                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    if ((Integer)o1.get(ReportExprParseSupport.RESOURCE_ORDER) < (Integer)o2.get(ReportExprParseSupport.RESOURCE_ORDER)) {
                        return -1;
                    }
                    if ((Integer)o1.get(ReportExprParseSupport.RESOURCE_ORDER) > (Integer)o2.get(ReportExprParseSupport.RESOURCE_ORDER)) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
    }
}

