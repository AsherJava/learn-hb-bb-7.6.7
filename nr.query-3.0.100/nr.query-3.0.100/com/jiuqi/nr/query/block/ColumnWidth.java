/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.deserializer.ColumnWidthDeserializer;
import com.jiuqi.nr.query.serializer.ColumnWidthSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=ColumnWidthSerializer.class)
@JsonDeserialize(using=ColumnWidthDeserializer.class)
public class ColumnWidth
implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(ColumnWidth.class);
    public static final String COLUMNWIDTH_WIDTH = "width";
    public static final String COLUMNWIDTH_COLUMN = "column";
    public static final String COLUMNWIDTH_ISHEADER = "isheader";
    public static final String COLUMNWIDTH_COLTAG = "colTag";
    Integer width;
    Integer column;
    Boolean isheader;
    String colTag;

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getColumn() {
        return this.column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Boolean getIsheader() {
        return this.isheader;
    }

    public void setIsheader(Boolean isheader) {
        this.isheader = isheader;
    }

    public String getColTag() {
        return this.colTag;
    }

    public void setColTag(String colTag) {
        this.colTag = colTag;
    }

    public static List<ColumnWidth> getColumnWidths(String str) {
        List columnWidths;
        if (StringUtil.isNullOrEmpty((String)str)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{ColumnWidth.class});
        try {
            columnWidths = (List)objectMapper.readValue(str, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return columnWidths;
    }

    public String toString() {
        return "ColumnWidth{width=" + this.width + ", column=" + this.column + ", isheader=" + this.isheader + ", colTag='" + this.colTag + '\'' + '}';
    }
}

