/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl
 *  com.jiuqi.nr.dataentry.service.ITemplateConfigService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.reportparam.init.executor;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class GcReportJshDataEntryTemplateInitExecutor
implements CustomClassExecutor {
    private static final String DATAENTRY_TEMPLATE_INNER_CODE = "JSH_template";

    public void execute(DataSource dataSource) throws Exception {
        ClassPathResource templateResource = new ClassPathResource("report-param-init/jsh_template.json");
        String defaultConfig = StreamUtils.copyToString(templateResource.getInputStream(), StandardCharsets.UTF_8);
        JSONObject defaultConfigJson = new JSONObject(defaultConfig);
        JSONObject configJson = (JSONObject)defaultConfigJson.get("config");
        String templateId = UUIDUtils.newUUIDStr();
        configJson.put("templateId", (Object)templateId);
        configJson.put("code", (Object)DATAENTRY_TEMPLATE_INNER_CODE);
        configJson.put("title", (Object)"\u51b3\u7b97\u835f\u6570\u636e\u5f55\u5165\u6a21\u7248");
        defaultConfigJson.put("config", (Object)configJson);
        TemplateConfigImpl templateConfig = new TemplateConfigImpl();
        templateConfig.setCode(DATAENTRY_TEMPLATE_INNER_CODE);
        templateConfig.setTitle("\u51b3\u7b97\u835f\u6570\u636e\u5f55\u5165\u6a21\u7248");
        templateConfig.setTemplate("standardTemplate");
        templateConfig.setTemplateId(templateId);
        templateConfig.setTemplateConfig(JSONObject.valueToString((Object)defaultConfigJson));
        ITemplateConfigService templateConfigService = (ITemplateConfigService)BeanUtil.getBean(ITemplateConfigService.class);
        templateConfigService.addTemplate(templateConfig);
    }
}

