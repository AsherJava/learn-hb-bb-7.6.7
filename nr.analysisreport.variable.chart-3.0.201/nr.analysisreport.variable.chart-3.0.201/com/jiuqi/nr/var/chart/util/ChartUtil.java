/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.helper.ReportVarHelper
 *  com.jiuqi.nr.analysisreport.utils.IntegerParser
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.var.common.uitl.BIDimUtil
 *  io.netty.util.internal.StringUtil
 *  org.jsoup.nodes.Attributes
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.chart.util;

import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.ReportVarHelper;
import com.jiuqi.nr.analysisreport.utils.IntegerParser;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.var.common.uitl.BIDimUtil;
import io.netty.util.internal.StringUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartUtil {
    private Logger logger = LoggerFactory.getLogger(ChartUtil.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int[] getImageSize(byte[] imageByte, Element imageNode) {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(imageByte);
        int width = 100;
        int height = 100;
        try {
            BufferedImage buf = ImageIO.read(byteInputStream);
            width = buf.getWidth();
            height = buf.getHeight();
            String style = imageNode.attr("style");
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
        }
        catch (Exception exec) {
            AnalysisReportLogHelper.log((String)"\u751f\u6210\u5206\u6790\u62a5\u544a", (String)("\u89e3\u6790\u56fe\u7247\u9ad8\u5ea6\u5bbd\u5ea6\u5f02\u5e38\uff1a" + imageNode.outerHtml()), (Exception)exec);
        }
        finally {
            if (byteInputStream != null) {
                try {
                    byteInputStream.close();
                }
                catch (IOException iOException) {}
            }
        }
        return new int[]{width, height};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void fillImg(String res, Element element, ReentrantLock reentrantLock) {
        try {
            if (!Thread.holdsLock(reentrantLock)) {
                reentrantLock.lock();
            }
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] byteArray = decoder.decode(res);
            int[] imageSize = ChartUtil.getImageSize(byteArray, element);
            String imgStr = "<img src=\"data:image/png;base64," + res + "\" width=\"" + imageSize[0] + "\" height=\"" + imageSize[1] + "\">";
            imgStr = ReportVarHelper.copyAttributes((String)imgStr, (Attributes)element.attributes(), (String)"img");
            element.after(imgStr);
            element.remove();
        }
        catch (Exception exception) {
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void fillError(String errorMsg, Element e, ReentrantLock reentrantLock) {
        try {
            if (!Thread.holdsLock(reentrantLock)) {
                reentrantLock.lock();
            }
            String spanStr = "<span style=\"display:block;text-align:left\">" + errorMsg + "</span>";
            spanStr = ReportVarHelper.copyAttributes((String)spanStr, (Attributes)e.attributes(), (String)"span");
            e.after("<br>");
            e.after(spanStr);
            e.remove();
        }
        catch (Exception exception) {
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public static Map<String, String[]> generateMasterValueArray(ReportVariableParseVO reportVariableParseVO) {
        Map map = BIDimUtil.generateMasterValues((ReportVariableParseVO)reportVariableParseVO);
        HashMap<String, String[]> arrayValue = new HashMap<String, String[]>();
        for (Map.Entry keyValue : map.entrySet()) {
            List value = (List)keyValue.getValue();
            arrayValue.put((String)keyValue.getKey(), ((List)keyValue.getValue()).toArray(new String[value.size()]));
        }
        return arrayValue;
    }

    public static <T> List<List<T>> splitList(ArrayList<T> list, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        int size = list.size();
        if (n > size) {
            n = size;
        }
        int quotient = size / n;
        int remainder = size % n;
        for (int i = 0; i < n; ++i) {
            int start = i * quotient + Math.min(i, remainder);
            int end = (i + 1) * quotient + Math.min(i + 1, remainder);
            result.add(list.subList(start, end));
        }
        return result;
    }
}

