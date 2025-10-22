/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.helper.ReportVarHelper
 *  com.jiuqi.nr.analysisreport.utils.IntegerParser
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  io.netty.util.internal.StringUtil
 *  org.jsoup.nodes.Attributes
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.var.quickform.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.ReportVarHelper;
import com.jiuqi.nr.analysisreport.utils.IntegerParser;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import io.netty.util.internal.StringUtil;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuickFormUtil {
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
            int[] imageSize = QuickFormUtil.getImageSize(byteArray, element);
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

    public static void drawLongString(Graphics g, String text, int x, int y, int maxWidth) {
        JLabel label = new JLabel(text);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int textH = metrics.getHeight();
        int textW = metrics.stringWidth(label.getText());
        String tempText = text;
        while (textW > maxWidth) {
            int n = textW / maxWidth;
            int subPos = tempText.length() / n;
            String drawText = tempText.substring(0, subPos);
            int subTxtW = metrics.stringWidth(drawText);
            while (subTxtW > maxWidth) {
                drawText = tempText.substring(0, --subPos);
                subTxtW = metrics.stringWidth(drawText);
            }
            g.drawString(drawText, x, y);
            y += textH;
            textW -= subTxtW;
            tempText = tempText.substring(subPos);
        }
        g.drawString(tempText, x, y);
    }

    public static Map<String, Object> handleCustomStyle(Element varElement, ReportVariableParseVO reportVariableParseVO) {
        Map<String, Object> customStyle = new LinkedHashMap<String, Object>();
        String varSetting = varElement.attr("var-setings");
        if (!StringUtils.isEmpty((String)varSetting)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                customStyle = (Map)mapper.readValue(varSetting, LinkedHashMap.class);
            }
            catch (JsonProcessingException e) {
                customStyle = new LinkedHashMap();
            }
            String genDatType = (String)reportVariableParseVO.getExt().get("GEN_DATA_TYPE");
            customStyle.put("GEN_DATA_TYPE", genDatType);
        }
        return customStyle;
    }

    public static List<List<Element>> divide(Elements elements) {
        ArrayList<List<Element>> lists = new ArrayList<List<Element>>();
        if (elements.size() <= 5) {
            lists.add((List<Element>)elements);
        } else {
            for (int i = 0; i < elements.size(); i += 5) {
                int end = Math.min(i + 5, elements.size());
                lists.add(elements.subList(i, end));
            }
        }
        return lists;
    }

    public static <T> List<List<T>> splitList(ArrayList<T> list, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(list) || n == 0) {
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

