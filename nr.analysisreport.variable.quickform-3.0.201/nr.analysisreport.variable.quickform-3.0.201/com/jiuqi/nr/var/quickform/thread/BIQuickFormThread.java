/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.helper.ReportVarHelper
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.bi.integration.IBIIntegrationServices
 *  com.jiuqi.nr.var.common.uitl.BIDimUtil
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.jsoup.nodes.Attributes
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.quickform.thread;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.ReportVarHelper;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.bi.integration.IBIIntegrationServices;
import com.jiuqi.nr.var.common.uitl.BIDimUtil;
import com.jiuqi.nr.var.quickform.helper.QuickReportGenerator;
import com.jiuqi.nr.var.quickform.util.QuickFormUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BIQuickFormThread
implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(BIQuickFormThread.class);
    public Element varElement = null;
    private IBIIntegrationServices biIntegrateServices;
    private QuickReportGenerator quickReportGenerator;
    private ReportVariableParseVO variableParseVO;

    public BIQuickFormThread(Element varElement, ReportVariableParseVO variableParseVO) {
        this.varElement = varElement;
        this.variableParseVO = variableParseVO;
        this.quickReportGenerator = (QuickReportGenerator)SpringBeanUtils.getBean(QuickReportGenerator.class);
        this.biIntegrateServices = (IBIIntegrationServices)SpringBeanUtils.getBean(IBIIntegrationServices.class);
    }

    public Object call() {
        long beginTime = System.currentTimeMillis();
        String varKey = this.varElement.attr("var-expr");
        try {
            Map masterValues = BIDimUtil.generateMasterValues((ReportVariableParseVO)this.variableParseVO);
            byte[] response = this.biIntegrateServices.ExportBIReport(varKey, masterValues, "\u5206\u6790\u62a5\u544a");
            AnalysisReportLogHelper.log((String)"\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (String)("com.jiuqi.nr.analysisreport.utils.WordUtil.formulaVarToMap Response BIREPORT " + varKey + ": " + (System.currentTimeMillis() - beginTime) / 1000L + "s"), (int)AnalysisReportLogHelper.LOGLEVEL_INFO);
            logger.info("\u5206\u6790\u62a5\u544a\u8c03\u7528BI\u56fe\u8868\u6d88\u8017\u65f6\u95f4\uff1a " + (System.currentTimeMillis() - beginTime));
            this.replaceEle(response);
            AnalysisReportLogHelper.log((String)"\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (String)("com.jiuqi.nr.analysisreport.utils.WordUtil.formulaVarToMap Finish BIREPORT " + varKey + ": " + (System.currentTimeMillis() - beginTime) / 1000L + "s"), (int)AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log((String)"\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (String)("com.jiuqi.nr.analysisreport.utils.WordUtil.formulaVarToMap Response ERROR " + varKey + ": " + (System.currentTimeMillis() - beginTime) / 1000L + "s"), (int)AnalysisReportLogHelper.LOGLEVEL_ERROR);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void replaceEle(byte[] response) {
        ReentrantLock reentrantLock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
        try {
            String base64Str = "";
            Grid2Data grid2Data = new Grid2Data();
            GridData gridData = GridData.bytesToGrid((byte[])response);
            if (gridData == null) {
                if (response != null && response.length > 0) {
                    Base64.Encoder encoder = Base64.getEncoder();
                    base64Str = encoder.encodeToString(response);
                    QuickFormUtil.fillImg(base64Str, this.varElement, reentrantLock);
                }
            } else {
                String table = this.quickReportGenerator.geneartorTable(gridData, null, this.variableParseVO.getReportBaseVO().getKey(), "quickFormVar", null);
                table = ReportVarHelper.copyAttributes((String)table, (Attributes)this.varElement.attributes(), (String)"table");
                reentrantLock.lock();
                this.varElement.before(table);
                this.varElement.remove();
            }
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }
}

