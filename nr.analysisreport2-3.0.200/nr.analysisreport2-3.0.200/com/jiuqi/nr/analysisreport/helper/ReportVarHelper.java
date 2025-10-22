/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.nodes.Attributes
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.helper;

import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReportVarHelper {
    public static String copyAttributes(String varHtml, Attributes attributes, String tag) {
        if (StringUtils.isEmpty((CharSequence)varHtml) || attributes == null || StringUtils.isEmpty((CharSequence)tag)) {
            return varHtml;
        }
        Document doc = AnaUtils.parseBodyFragment(varHtml);
        Elements elements = doc.getElementsByTag(tag);
        if (elements == null || elements.size() == 0) {
            return varHtml;
        }
        Element element = (Element)elements.get(0);
        if (attributes.hasKey("src")) {
            attributes.remove("src");
        }
        if (element.tagName().equals("table")) {
            String regex;
            if (element.attributes().hasKey("width")) {
                regex = "width\\s*:\\s*[^;]+;";
                ReportVarHelper.replaceValue(attributes, regex);
            }
            regex = "height\\s*:\\s*[^;]+;";
            ReportVarHelper.replaceValue(attributes, regex);
        }
        element.attributes().addAll(attributes);
        return element.outerHtml();
    }

    public static void replaceValue(Attributes attributes, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(attributes.get("style"));
        attributes.remove("style");
        String result = matcher.replaceAll("");
        attributes.add("style", result);
    }
}

