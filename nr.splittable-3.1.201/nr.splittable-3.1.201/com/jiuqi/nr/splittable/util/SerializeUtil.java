/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.splittable.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nr.splittable.bean.BaseBook;
import com.jiuqi.nr.splittable.bean.CellObj;
import com.jiuqi.nr.splittable.util.SplitCellBookSerialize;
import com.jiuqi.nr.splittable.util.SplitGrid2DataSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil {
    private static final Logger log = LoggerFactory.getLogger(SerializeUtil.class);

    public static String serializeGrid2Data(Grid2Data grid2Data, List<CellObj> realXYList) {
        SimpleModule module = new SimpleModule();
        ObjectMapper mapper = new ObjectMapper();
        if (realXYList != null) {
            module.addSerializer(Grid2Data.class, (JsonSerializer)new SplitGrid2DataSerialize(realXYList));
        } else {
            module.addSerializer(Grid2Data.class, (JsonSerializer)new SplitGrid2DataSerialize(new ArrayList<CellObj>()));
        }
        mapper.registerModule((Module)module);
        String returnStr = "{}";
        try {
            returnStr = mapper.writeValueAsString((Object)grid2Data);
        }
        catch (JsonProcessingException e) {
            log.error("\u5e8f\u5217\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return returnStr;
    }

    public static String serializeCellBook(CellBook cellBook, List<List<CellObj>> cellLists) {
        SimpleModule module = new SimpleModule();
        ObjectMapper mapper = new ObjectMapper();
        module.addSerializer((JsonSerializer)new SplitCellBookSerialize(cellLists));
        mapper.registerModule((Module)module);
        String returnStr = "{}";
        try {
            returnStr = mapper.writeValueAsString((Object)cellBook);
        }
        catch (JsonProcessingException e) {
            log.error("\u5e8f\u5217\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return returnStr;
    }

    public static String serializeBaseBook(BaseBook baseBook) {
        ObjectMapper mapper = new ObjectMapper();
        String returnStr = null;
        try {
            returnStr = mapper.writeValueAsString((Object)baseBook);
        }
        catch (JsonProcessingException e) {
            log.error("\u5e8f\u5217\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return returnStr;
    }

    public static BaseBook deserializeBaseBook(String string) {
        BaseBook baseBook = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            baseBook = (BaseBook)mapper.readValue(string, BaseBook.class);
        }
        catch (JsonProcessingException e) {
            log.error("\u53cd\u5e8f\u5217\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return baseBook;
    }
}

