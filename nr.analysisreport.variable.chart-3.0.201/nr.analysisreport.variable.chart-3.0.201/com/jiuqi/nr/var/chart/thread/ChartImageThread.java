/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.analysisreport.utils.IntegerParser
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nvwa.dataanalyze.screenshot.ChartImageConfig
 *  com.jiuqi.nvwa.dataanalyze.screenshot.IChartScreenShotService
 *  com.jiuqi.nvwa.datav.chart.dto.ChartRenderData
 *  com.jiuqi.nvwa.datav.chart.manager.DashboardChartManager
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.chart.thread;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.utils.IntegerParser;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.var.chart.util.ChartUtil;
import com.jiuqi.nvwa.dataanalyze.screenshot.ChartImageConfig;
import com.jiuqi.nvwa.dataanalyze.screenshot.IChartScreenShotService;
import com.jiuqi.nvwa.datav.chart.dto.ChartRenderData;
import com.jiuqi.nvwa.datav.chart.manager.DashboardChartManager;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import io.netty.util.internal.StringUtil;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartImageThread
implements Callable {
    private static Logger logger = LoggerFactory.getLogger(ChartImageThread.class);
    private Element varElement;
    private DashboardChartManager dashboardChartManager;
    private IChartScreenShotService chartShotService;
    private ReportVariableParseVO variableParseVO;

    public ChartImageThread(Element varElement, ReportVariableParseVO variableParseVO) {
        this.variableParseVO = variableParseVO;
        this.varElement = varElement;
        this.dashboardChartManager = (DashboardChartManager)SpringBeanUtils.getBean(DashboardChartManager.class);
        this.chartShotService = (IChartScreenShotService)SpringBeanUtils.getBean(IChartScreenShotService.class);
    }

    public Object call() throws Exception {
        ReentrantLock reentrantLock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
        String varKey = this.varElement.attr("var-expr");
        String[] split = varKey.split(";");
        String guid = split[split.length - 2];
        String chartId = split[split.length - 1];
        Integer width = 800;
        Integer height = 600;
        String style = this.varElement.attr("style");
        if (!StringUtil.isNullOrEmpty((String)style)) {
            for (String token : style.split(";")) {
                if (Pattern.matches("width:.*", token = token.replace(" ", ""))) {
                    width = IntegerParser.parseInt((String)token.replaceAll("width:([0-9\\.]*).*", "$1"));
                    continue;
                }
                if (!Pattern.matches("height:.*", token.replaceAll("\\s*", ""))) continue;
                height = IntegerParser.parseInt((String)token.replaceAll("height:([0-9\\.]*).*", "$1"));
            }
        }
        ChartImageConfig chartImageConfig = new ChartImageConfig(width.intValue(), height.intValue());
        LinkMsg linkMsg = new LinkMsg();
        Map<String, String[]> parmsHashMap = ChartUtil.generateMasterValueArray(this.variableParseVO);
        linkMsg.setMessages(parmsHashMap);
        try {
            ChartRenderData chartRenderData = this.dashboardChartManager.renderChart(guid, chartId, linkMsg);
            HashMap<String, String> extFiles = new HashMap<String, String>();
            if (StringUtils.isNotEmpty((CharSequence)chartRenderData.getMapData())) {
                extFiles.put("map.json", chartRenderData.getMapData());
            }
            long startTime = System.currentTimeMillis();
            logger.info("\u5f00\u59cb\u67e5\u8be2\u4eea\u8868\u76d8\u56fe\u7247,\u5f00\u59cb\u65f6\u95f4\uff1a" + startTime);
            byte[] response = this.chartShotService.getChartImage(chartImageConfig, chartRenderData.getChartData(), extFiles);
            logger.info("\u5f00\u59cb\u67e5\u4eea\u8868\u76d8\u56fe\u7247,\u7ed3\u675f\u65f6\u95f4\uff1a" + System.currentTimeMillis());
            logger.info("\u5f00\u59cb\u67e5\u8be2\u4eea\u8868\u76d8\u56fe\u7247,\u65f6\u95f4\u6d88\u8017\uff1a" + (System.currentTimeMillis() - startTime));
            Base64.Encoder encoder = Base64.getEncoder();
            String base64Str = encoder.encodeToString(response);
            ChartUtil.fillImg(base64Str, this.varElement, reentrantLock);
        }
        catch (Exception e) {
            ChartUtil.fillError(e.getMessage(), this.varElement, reentrantLock);
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}

