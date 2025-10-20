/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.vo.print;

import org.apache.commons.lang3.StringUtils;

public class PrintStyle {
    private String paperType;
    private Integer orientation;
    private Watermark watermark;
    private String paperWidth;
    private String paperHeight;
    private String lineHeight;
    private String allowWordWrap;
    private String marginTop;
    private String marginLeft;
    private String marginBottom;

    public String getPaperType() {
        return this.paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public Integer getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public Watermark getWatermark() {
        return this.watermark;
    }

    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }

    public String getPaperWidth() {
        return this.paperWidth;
    }

    public void setPaperWidth(String paperWidth) {
        this.paperWidth = paperWidth;
    }

    public String getPaperHeight() {
        return this.paperHeight;
    }

    public void setPaperHeight(String paperHeight) {
        this.paperHeight = paperHeight;
    }

    public String getLineHeight() {
        return this.lineHeight;
    }

    public void setLineHeight(String lineHeight) {
        this.lineHeight = lineHeight;
    }

    public Boolean getAllowWordWrap() {
        if (StringUtils.isNotEmpty((CharSequence)this.allowWordWrap)) {
            return Boolean.parseBoolean(this.allowWordWrap);
        }
        return false;
    }

    public void setAllowWordWrap(String allowWordWrap) {
        this.allowWordWrap = allowWordWrap;
    }

    public String getMarginTop() {
        return this.marginTop;
    }

    public void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    public String getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public String getMarginBottom() {
        return this.marginBottom;
    }

    public void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    public static class Watermark {
        private String color;
        private String fontfamily;
        private String format;
        private String fontsize;
        private String zoom;
        private String type;
        private String title;
        private String content;
        private Boolean translucent;
        private Boolean erosion;
        private String paperWidth;

        public String getColor() {
            return this.color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getFontfamily() {
            return this.fontfamily;
        }

        public void setFontfamily(String fontfamily) {
            this.fontfamily = fontfamily;
        }

        public String getFormat() {
            return this.format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getFontsize() {
            return this.fontsize;
        }

        public void setFontsize(String fontsize) {
            this.fontsize = fontsize;
        }

        public String getZoom() {
            return this.zoom;
        }

        public void setZoom(String zoom) {
            this.zoom = zoom;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Boolean getTranslucent() {
            return this.translucent;
        }

        public void setTranslucent(Boolean translucent) {
            this.translucent = translucent;
        }

        public Boolean getErosion() {
            return this.erosion;
        }

        public void setErosion(Boolean erosion) {
            this.erosion = erosion;
        }

        public String getPaperWidth() {
            return this.paperWidth;
        }

        public void setPaperWidth(String paperWidth) {
            this.paperWidth = paperWidth;
        }
    }
}

