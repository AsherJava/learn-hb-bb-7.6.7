/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.bi.integration.IBIIntegrationServices
 *  com.jiuqi.nr.var.common.uitl.BIDimUtil
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.chart.thread;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.bi.integration.IBIIntegrationServices;
import com.jiuqi.nr.var.chart.util.ChartUtil;
import com.jiuqi.nr.var.common.uitl.BIDimUtil;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BIChartThread
implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(BIChartThread.class);
    public Element varElement = null;
    private IBIIntegrationServices biIntegrateServices;
    private ReportVariableParseVO variableParseVO;

    public BIChartThread(Element varElement, ReportVariableParseVO variableParseVO) {
        this.varElement = varElement;
        this.variableParseVO = variableParseVO;
        this.biIntegrateServices = (IBIIntegrationServices)SpringBeanUtils.getBean(IBIIntegrationServices.class);
    }

    public Object call() {
        long beginTime = System.currentTimeMillis();
        String varKey = null;
        try {
            varKey = this.varElement.attr("var-expr");
            Map masterValues = BIDimUtil.generateMasterValues((ReportVariableParseVO)this.variableParseVO);
            byte[] response = this.biIntegrateServices.ExportChart(varKey, masterValues, "\u5206\u6790\u62a5\u544a");
            AnalysisReportLogHelper.log((String)"\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (String)("com.jiuqi.nr.analysisreport.utils.WordUtil.formulaVarToMap Response BICHART " + varKey + ": " + (System.currentTimeMillis() - beginTime) / 1000L + "s"), (int)AnalysisReportLogHelper.LOGLEVEL_INFO);
            logger.info("\u5206\u6790\u62a5\u544a\u8c03\u7528BI\u56fe\u8868\u6d88\u8017\u65f6\u95f4\uff1a " + (System.currentTimeMillis() - beginTime));
            this.replaceEle(response);
            AnalysisReportLogHelper.log((String)"\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (String)("com.jiuqi.nr.analysisreport.utils.WordUtil.formulaVarToMap Finish BICHART " + varKey + ": " + (System.currentTimeMillis() - beginTime) / 1000L + "s"), (int)AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log((String)"\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (String)("com.jiuqi.nr.analysisreport.utils.WordUtil.formulaVarToMap Response ERROR " + varKey + ": " + (System.currentTimeMillis() - beginTime) / 1000L + "s"), (int)AnalysisReportLogHelper.LOGLEVEL_ERROR);
        }
        return null;
    }

    private void replaceEle(byte[] response) {
        String base64Str = "";
        if (response != null && response.length > 0) {
            Base64.Encoder encoder = Base64.getEncoder();
            base64Str = encoder.encodeToString(response);
            ReentrantLock reentrantLock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
            ChartUtil.fillImg(base64Str, this.varElement, reentrantLock);
        }
    }
}

