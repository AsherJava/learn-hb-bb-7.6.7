/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.customentry.define;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.customentry.define.CustomEntryContextDeserializer;
import com.jiuqi.nr.customentry.define.CustomEntryContextSerializer;
import com.jiuqi.nr.customentry.define.CustomEntryData;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@JsonSerialize(using=CustomEntryContextSerializer.class)
@JsonDeserialize(using=CustomEntryContextDeserializer.class)
public class CustomEntryContext {
    private static final Logger log = LoggerFactory.getLogger(CustomEntryContext.class);
    public static final String CUSTOMENTRYCONTEXT_BLOCK = "block";
    public static final String CUSTOMENTRYCONTEXT_DATAS = "datas";
    private QueryBlockDefine block;
    private List<CustomEntryData> datas;

    public void setBlock(QueryBlockDefine block) {
        this.block = block;
    }

    public QueryBlockDefine getBlock() {
        return this.block;
    }

    public void setDatas(List<CustomEntryData> datas) {
        this.datas = datas;
    }

    public List<CustomEntryData> getDatas() {
        return this.datas;
    }

    public String getDatasStr() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.datas);
        }
        catch (Exception ex) {
            LogHelper.error((String)"CustomEntry", (String)"\u5e8f\u5217\u5316\u5b58\u50a8\u6570\u636e\u5f02\u5e38", (String)ex.getMessage());
            return null;
        }
    }

    public void parseBlockStr(String block) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryBlockDefine.class});
            this.setBlock((QueryBlockDefine)mapper.readValue(block, QueryBlockDefine.class));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void parseDatasStr(String datas) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{CustomEntryData.class});
            this.setDatas((List)mapper.readValue(datas, javaType));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

