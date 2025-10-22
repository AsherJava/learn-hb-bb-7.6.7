/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.analysisreport.helper.ReportVarHelper
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.var.common.uitl.BIDimUtil
 *  com.jiuqi.nvwa.quickreport.api.query.Wrapper
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.dto.ReportData
 *  com.jiuqi.nvwa.quickreport.web.query.GridController
 *  javax.xml.bind.DatatypeConverter
 *  org.jsoup.nodes.Attributes
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.quickform.thread;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.analysisreport.helper.ReportVarHelper;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.var.common.uitl.BIDimUtil;
import com.jiuqi.nr.var.quickform.helper.QuickReportGenerator;
import com.jiuqi.nr.var.quickform.util.QuickFormUtil;
import com.jiuqi.nvwa.quickreport.api.query.Wrapper;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.dto.ReportData;
import com.jiuqi.nvwa.quickreport.web.query.GridController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickFormLocalThread
implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(QuickFormLocalThread.class);
    private Element varElement;
    private GridController gridController;
    private QuickReportGenerator quickReportGenerator;
    private final String errorMsg = "\u5feb\u901f\u5206\u6790\u8868\u89e3\u6790\u5931\u8d25\uff1a";
    private ReportVariableParseVO reportVariableParseVO;

    public QuickFormLocalThread(Element varElement, ReportVariableParseVO reportVariableParseVO) {
        this.varElement = varElement;
        this.reportVariableParseVO = reportVariableParseVO;
        this.gridController = (GridController)SpringBeanUtils.getBean(GridController.class);
        this.quickReportGenerator = (QuickReportGenerator)SpringBeanUtils.getBean(QuickReportGenerator.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object call() throws Exception {
        ReentrantLock reentrantLock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
        logger.info("\u5f00\u59cb\u89e3\u6790\u5feb\u901f\u5206\u6790\u8868");
        try {
            Map masterValues = BIDimUtil.generateMasterValues((ReportVariableParseVO)this.reportVariableParseVO);
            Option option = new Option();
            masterValues.keySet().stream().forEach(key -> option.addParamValue(key, masterValues.get(key)));
            Wrapper wrapper = new Wrapper();
            String varKey = this.varElement.attr("var-expr");
            wrapper.setReportData(new ReportData(varKey, null));
            option.setPageNum(0);
            option.setTransparent(false);
            wrapper.setOption(option);
            String table = null;
            GridData gridData = this.gridController.getPrimarySheetGridData(wrapper);
            table = this.quickReportGenerator.geneartorTable(gridData, this.varElement, this.reportVariableParseVO.getReportBaseVO().getKey(), "quickFormVar_Local", this.reportVariableParseVO);
            Element parent = this.varElement.closest("p");
            if (parent != null) {
                String styleStr = parent.attr("style");
                String genDataType = (String)this.reportVariableParseVO.getExt().get("GEN_DATA_TYPE");
                boolean isWord = "GEN_WORD_DATA".equals(genDataType);
                String attrValue = "";
                if (styleStr.contains("text-align: center;")) {
                    attrValue = isWord ? "center" : this.varElement.attr("style") + ";" + "margin:0 auto;";
                } else if (styleStr.contains("text-align: right;")) {
                    String string = attrValue = isWord ? "right" : this.varElement.attr("style") + ";" + "margin-left: auto;margin-right: 0;";
                }
                if (!StringUtils.isEmpty((String)attrValue)) {
                    this.varElement.attr(isWord ? "align" : "style", attrValue);
                }
            }
            table = ReportVarHelper.copyAttributes((String)table, (Attributes)this.varElement.attributes(), (String)"table");
            reentrantLock.lock();
            this.varElement.before(table);
            this.varElement.remove();
        }
        catch (Exception e) {
            try {
                logger.error("\u5206\u6790\u8868\u5904\u7406\u5f02\u5e38", e);
                byte[] errorMessage = QuickFormLocalThread.getErrorMessage("\u5feb\u901f\u5206\u6790\u8868\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage());
                String base64Str = DatatypeConverter.printBase64Binary((byte[])errorMessage);
                QuickFormUtil.fillImg(base64Str, this.varElement, reentrantLock);
            }
            catch (Exception e1) {
                logger.error("\u5206\u6790\u8868\u89e3\u6790\u5f02\u5e38\uff0c\u9519\u8bef\u63d0\u793a\u56fe\u7247\u751f\u6210\u5f02\u5e38", e1);
            }
        }
        finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
        return null;
    }

    public static byte[] getErrorMessage(String error) {
        BufferedImage bufferedImage = new BufferedImage(300, 300, 1);
        Graphics paint = bufferedImage.getGraphics();
        paint.setColor(Color.WHITE);
        paint.fillRect(0, 0, 300, 300);
        paint.setColor(Color.GRAY);
        paint.drawLine(1, 1, 1, 299);
        paint.drawLine(1, 299, 299, 299);
        paint.drawLine(299, 299, 299, 1);
        paint.drawLine(299, 1, 1, 1);
        paint.setColor(Color.RED);
        paint.setFont(new Font("Microsoft YaHei", 0, 12));
        QuickFormUtil.drawLongString(paint, "\u56fe\u8868\u751f\u6210\u5931\u8d25\uff1a" + error, 5, 15, 290);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)bufferedImage, "png", out);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return out.toByteArray();
    }
}

