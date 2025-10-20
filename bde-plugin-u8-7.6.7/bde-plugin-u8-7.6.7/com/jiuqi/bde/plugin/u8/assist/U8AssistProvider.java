/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.bde.plugin.u8.assist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.u8.BdeU8PluginType;
import com.jiuqi.bde.plugin.u8.assist.U8AssistPojo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class U8AssistProvider
implements IAssistProvider<U8AssistPojo> {
    @Autowired
    private BdeU8PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static String assistJson;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<U8AssistPojo> listAssist(String dataSourceCode) {
        if (assistJson == null) {
            try {
                ClassPathResource classPathResource = new ClassPathResource("template/u8AssistDefine.json");
                InputStream inputStream = classPathResource.getInputStream();
                assistJson = IOUtils.toString((InputStream)inputStream, (Charset)StandardCharsets.UTF_8);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u7528\u53cbU8\u63d2\u4ef6\u9ed8\u8ba4\u521d\u59cb\u5316\u65b9\u6848\u52a0\u8f7d\u5931\u8d25", (Throwable)e);
            }
        }
        return (List)JsonUtils.readValue((String)assistJson, (TypeReference)new TypeReference<List<U8AssistPojo>>(){});
    }
}

