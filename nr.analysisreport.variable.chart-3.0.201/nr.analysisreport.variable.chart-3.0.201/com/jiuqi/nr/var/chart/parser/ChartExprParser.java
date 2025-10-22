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
package com.jiuqi.nr.var.chart.parser;

import com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource;
import com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor;
import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.var.chart.thread.BIChartThread;
import com.jiuqi.nr.var.chart.thread.ChartImageThread;
import com.jiuqi.nr.var.chart.util.ChartUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@AnaReportInsertVariableResource(name="\u56fe\u8868", pluginName="chart-choose", pluginType="insert-variable-plugin", order=2)
public class ChartExprParser
extends AbstractExprParser {
    private static Logger logger = LoggerFactory.getLogger(ChartExprParser.class);

    public String getName() {
        return "biChartVar";
    }

    public void parse(Element es, Object ext) {
    }

    public void parse(Elements elements, ReportVariableParseVO variableParseVO) {
        if (elements.size() == 1) {
            for (Element element : elements) {
                try {
                    this.buildCallable(element, variableParseVO).call();
                }
                catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        } else {
            ArrayList futures = new ArrayList();
            int corePoolSize = this.getThreadPoolTaskExecutor().getCorePoolSize();
            List lists = ChartUtil.splitList(elements, corePoolSize);
            for (List list : lists) {
                futures.add(this.getThreadPoolTaskExecutor().submit(() -> {
                    for (Element element : list) {
                        try {
                            this.buildCallable(element, variableParseVO).call();
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

    public Callable buildCallable(Element es, ReportVariableParseVO variableParseVO) {
        if (es.hasClass("biChartVar")) {
            return new BIChartThread(es, variableParseVO);
        }
        if (es.hasClass("biChartVar_Local")) {
            return new ChartImageThread(es, variableParseVO);
        }
        return null;
    }

    public List<String> getResourceVarNames() {
        ArrayList<String> resouceNames = new ArrayList<String>();
        resouceNames.add("biChartVar_Local");
        return resouceNames;
    }
}

