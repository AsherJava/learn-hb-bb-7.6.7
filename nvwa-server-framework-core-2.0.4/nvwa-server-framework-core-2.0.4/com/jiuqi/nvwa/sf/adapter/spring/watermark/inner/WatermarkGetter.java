/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark.inner;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkContext;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkException;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.inner.TextWatermark;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.inner.WatermarkEnum;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.inner.WatermarkManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WatermarkGetter {
    private final List<String> defaultTags = new ArrayList<String>(Arrays.asList("user", "userTitle", "org", "telephoneNumber", "phoneNumLast4", "mailAddress", "date"));

    public Watermark get() throws WatermarkException {
        return this.get(new WatermarkContext());
    }

    public Watermark get(WatermarkContext watermarkContext) throws WatermarkException {
        Watermark watermark = WatermarkManager.getInstance().getWatermarkInfo();
        if (watermark.getMode() == WatermarkEnum.TXT.mode()) {
            TextWatermark textWatermark = (TextWatermark)watermark;
            ArrayList<String> defTags = new ArrayList<String>();
            ArrayList<String> extResTags = new ArrayList<String>();
            if (StringUtils.isNotEmpty((String)textWatermark.getContent())) {
                this.parseDefaultTags(defTags, extResTags, textWatermark.getContent());
            }
            Map<String, String> mapTags = WatermarkManager.getInstance().getValue(watermarkContext, defTags, extResTags);
            String contentStr = textWatermark.getContent();
            for (String tag : defTags) {
                contentStr = contentStr.replace("${" + tag + "}", mapTags.get(tag) == null ? "" : (CharSequence)mapTags.get(tag));
            }
            for (String tag : extResTags) {
                contentStr = contentStr.replace("${" + tag + "}", mapTags.get(tag) == null ? "" : (CharSequence)mapTags.get(tag));
            }
            while (contentStr.contains("\n\n")) {
                contentStr = contentStr.replace("\n\n", "\n");
            }
            if ((contentStr = contentStr.replace("\n", "<br>")).length() >= 4 && contentStr.substring(contentStr.length() - 4).equals("<br>")) {
                contentStr = contentStr.substring(0, contentStr.length() - 4);
            }
            textWatermark.setContent(contentStr);
            return textWatermark;
        }
        return watermark;
    }

    private void parseDefaultTags(List<String> defTags, List<String> extResTags, String content) {
        content = content.trim();
        int startIdx = content.indexOf("${");
        int endIdx = content.indexOf("}");
        while (startIdx >= 0 && endIdx >= 0) {
            String str1 = content.substring(0, startIdx);
            String tag = content.substring(startIdx + 2, endIdx);
            String str2 = content.substring(endIdx + 1);
            content = str1 + str2;
            startIdx = content.indexOf("${");
            endIdx = content.indexOf("}");
            if (this.defaultTags.contains(tag)) {
                defTags.add(tag);
                continue;
            }
            extResTags.add(tag);
        }
    }
}

