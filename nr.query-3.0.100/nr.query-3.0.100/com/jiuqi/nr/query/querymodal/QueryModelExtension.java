/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.query.querymodal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryModelExtension {
    private static final Logger log = LoggerFactory.getLogger(QueryModelExtension.class);
    private boolean autoQuery;
    private boolean fixedSize;
    private int fixedWidth = 0;
    private int fixedHeight = 0;
    private String bgType;
    private String backColor;
    private String backgroundImage;
    private Double opacity = 1.0;
    @Autowired
    private ObjectMapper objectMapper;

    public QueryModelExtension() {
    }

    public QueryModelExtension(String queryModelExtensionStr) {
        if (StringUtils.isEmpty((String)queryModelExtensionStr)) {
            this.autoQuery = false;
            this.fixedSize = false;
            this.fixedWidth = 800;
            this.fixedHeight = 600;
            return;
        }
        try {
            QueryModelExtension item = (QueryModelExtension)this.objectMapper.readValue(queryModelExtensionStr, QueryModelExtension.class);
            this.autoQuery = item.isAutoQuery();
            this.fixedSize = item.isFixedSize();
            this.fixedWidth = item.getFixedWidth();
            this.fixedHeight = item.getFixedHeight();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            this.autoQuery = false;
            this.fixedSize = false;
            this.fixedWidth = 800;
            this.fixedHeight = 600;
        }
    }

    public boolean isAutoQuery() {
        return this.autoQuery;
    }

    public void setAutoQuery(boolean autoQuery) {
        this.autoQuery = autoQuery;
    }

    public boolean isFixedSize() {
        return this.fixedSize;
    }

    public void setFixedSize(boolean fixedSize) {
        this.fixedSize = fixedSize;
    }

    public int getFixedWidth() {
        return this.fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public int getFixedHeight() {
        return this.fixedHeight;
    }

    public void setFixedHeight(int fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public void setBackGroudType(String type) {
        this.bgType = type;
    }

    public String getBackGroudType() {
        return this.bgType;
    }

    public void setBackColor(String color) {
        this.backColor = color;
    }

    public String getBackColor() {
        return this.backColor;
    }

    public void setBackImage(String image) {
        this.backgroundImage = image;
    }

    public String getBackImage() {
        return this.backgroundImage;
    }

    public Double getOpacity() {
        return this.opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public String toString() {
        try {
            return this.objectMapper.writeValueAsString((Object)this);
        }
        catch (IOException e) {
            return null;
        }
    }
}

