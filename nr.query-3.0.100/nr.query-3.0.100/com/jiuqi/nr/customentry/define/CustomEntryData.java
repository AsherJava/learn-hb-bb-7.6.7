/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.customentry.define;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.customentry.define.CustomEntryCellData;
import com.jiuqi.nr.customentry.define.CustomEntryDataDeserializer;
import com.jiuqi.nr.customentry.define.CustomEntryDataSerializer;
import com.jiuqi.nr.customentry.define.CustomEntryType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@JsonSerialize(using=CustomEntryDataSerializer.class)
@JsonDeserialize(using=CustomEntryDataDeserializer.class)
public class CustomEntryData {
    private static final Logger log = LoggerFactory.getLogger(CustomEntryData.class);
    public static final String CUSTOMENTRYDATA_TYPE = "type";
    public static final String CUSTOMENTRYDATA_CELLS = "cells";
    private CustomEntryType type;
    private List<CustomEntryCellData> cells;

    public void setType(CustomEntryType type) {
        this.type = type;
    }

    public CustomEntryType getType() {
        return this.type;
    }

    public void setCells(List<CustomEntryCellData> cells) {
        this.cells = cells;
    }

    public List<CustomEntryCellData> getCells() {
        return this.cells;
    }

    public void parseCellsStr(String cellInfo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{CustomEntryCellData.class});
            this.setCells((List)mapper.readValue(cellInfo, javaType));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

