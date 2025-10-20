/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.vo.print;

import com.jiuqi.nr.analysisreport.vo.print.PageHeadFoot;
import com.jiuqi.nr.analysisreport.vo.print.PageNumber;
import com.jiuqi.nr.analysisreport.vo.print.PrintStyle;
import io.netty.util.internal.StringUtil;
import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;

public class ReportPrintSettingVO {
    private PrintStyle template;
    private PrintStyle info;
    private PageNumber pageNumber;
    private PageHeadFoot pageHeader;
    private PageHeadFoot pageFooter;

    public PrintStyle getTemplate() {
        if (this.template != null) {
            return this.template;
        }
        return this.info;
    }

    public void setTemplate(PrintStyle template) {
        this.template = template;
    }

    public PageNumber getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(PageNumber pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageHeadFoot getPageHeader() {
        return this.pageHeader;
    }

    public void setPageHeader(PageHeadFoot pageHeader) {
        this.pageHeader = pageHeader;
    }

    public PageHeadFoot getPageFooter() {
        return this.pageFooter;
    }

    public void setPageFooter(PageHeadFoot pageFooter) {
        this.pageFooter = pageFooter;
    }

    public PrintStyle getInfo() {
        return this.info;
    }

    public void setInfo(PrintStyle info) {
        this.info = info;
    }

    private void fomat() {
        this.fomatPrintStyle();
        this.formatPageNumber();
        this.fomatPageHeadFoot();
    }

    private void fomatPageHeadFoot() {
    }

    private void formatPageNumber() {
    }

    public void fomatPrintStyle() {
        PrintStyle printStyle = this.getTemplate();
        if (StringUtils.isEmpty((CharSequence)printStyle.getMarginBottom())) {
            printStyle.setMarginBottom(printStyle.getMarginTop());
        }
        if (StringUtils.isEmpty((CharSequence)printStyle.getLineHeight())) {
            printStyle.setLineHeight("line-height");
        }
        if (printStyle.getWatermark() != null) {
            BigInteger paperWidth = new BigInteger(printStyle.getPaperWidth());
            String formatPaperWidth = paperWidth.subtract(new BigInteger(printStyle.getMarginLeft()).multiply(new BigInteger("2"))).toString();
            printStyle.getWatermark().setPaperWidth(formatPaperWidth);
        }
        if (StringUtils.isEmpty((CharSequence)printStyle.getMarginTop())) {
            printStyle.setMarginTop((int)Math.floor(1440.18) + "");
        } else {
            printStyle.setMarginTop((int)Math.floor(Double.valueOf(printStyle.getMarginTop()) / 100.0 * 567.0) + "");
        }
        if (StringUtils.isEmpty((CharSequence)printStyle.getMarginBottom())) {
            printStyle.setMarginBottom((int)Math.floor(1440.18) + "");
        } else {
            printStyle.setMarginBottom((int)Math.floor(Double.valueOf(printStyle.getMarginBottom()) / 100.0 * 567.0) + "");
        }
        if (StringUtil.isNullOrEmpty((String)printStyle.getMarginLeft())) {
            printStyle.setMarginLeft((int)Math.floor(1082.97) + "");
        } else {
            printStyle.setMarginLeft((int)Math.floor(Double.valueOf(printStyle.getMarginLeft()) / 100.0 * 567.0) + "");
        }
    }
}

