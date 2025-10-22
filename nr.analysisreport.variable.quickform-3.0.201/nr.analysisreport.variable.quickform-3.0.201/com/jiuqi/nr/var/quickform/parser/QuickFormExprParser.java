/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource
 *  com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor
 *  com.jiuqi.nr.analysisreport.support.AbstractExprParser
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.var.quickform.parser;

import com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource;
import com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor;
import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.var.quickform.thread.BIQuickFormThread;
import com.jiuqi.nr.var.quickform.thread.QuickFormLocalThread;
import com.jiuqi.nr.var.quickform.util.QuickFormUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@AnaReportInsertVariableResource(name="\u5feb\u901f\u5206\u6790\u8868", pluginName="quickForm-choose", pluginType="insert-variable-plugin", order=3)
public class QuickFormExprParser
extends AbstractExprParser {
    private static Logger logger = LoggerFactory.getLogger(QuickFormExprParser.class);

    public String getName() {
        return "quickFormVar";
    }

    public void parse(Element es, Object ext) {
    }

    public void parse(Elements elements, ReportVariableParseVO reportVariableParseVO) {
        if (elements.size() == 1) {
            try {
                this.buildCallable((Element)elements.get(0), reportVariableParseVO).call();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            ArrayList futures = new ArrayList();
            int corePoolSize = this.getThreadPoolTaskExecutor().getCorePoolSize();
            List lists = QuickFormUtil.splitList(elements, corePoolSize);
            for (List list : lists) {
                futures.add(this.getThreadPoolTaskExecutor().submit(() -> {
                    for (Element element : list) {
                        try {
                            this.buildCallable(element, reportVariableParseVO).call();
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }));
            }
            AnalysisReportThreadExuecutor.waitAllFinish(futures);
        }
    }

    public Callable buildCallable(Element es, ReportVariableParseVO reportVariableParseVO) {
        if (es.hasClass("quickFormVar")) {
            return new BIQuickFormThread(es, reportVariableParseVO);
        }
        if (es.hasClass("quickFormVar_Local")) {
            return new QuickFormLocalThread(es, reportVariableParseVO);
        }
        return null;
    }

    public List<String> getResourceVarNames() {
        ArrayList<String> resouceNames = new ArrayList<String>();
        resouceNames.add("quickFormVar_Local");
        return resouceNames;
    }
}

