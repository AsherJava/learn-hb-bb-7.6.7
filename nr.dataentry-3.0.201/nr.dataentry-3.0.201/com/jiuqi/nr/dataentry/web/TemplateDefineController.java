/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.bean.DataentryDefineConfig;
import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.GatheredActions;
import com.jiuqi.nr.dataentry.bean.ResultObject;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.config.SlimButtonsConfig;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ExtendTemplateImpl;
import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import com.jiuqi.nr.dataentry.gather.TemplateItem;
import com.jiuqi.nr.dataentry.service.IPlugInService;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry/defines"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u6a21\u677f\u5b9a\u4e49\uff0c\u63d2\u4ef6\u6536\u96c6"})
public class TemplateDefineController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateDefineController.class);
    @Autowired
    private ITemplateConfigService templateConfigService;
    @Autowired
    private IPlugInService plugInService;
    @Autowired
    private SlimButtonsConfig slimButtonsConfig;

    @PostMapping(value={"config/update"})
    @ApiOperation(value="\u66f4\u65b0\u6a21\u677f\u914d\u7f6e\u4fe1\u606f")
    public ResultObject saveTemplateConfig(@RequestBody String configJson) {
        JSONObject jsonObject = new JSONObject(configJson);
        TemplateConfigImpl templateConfig = new TemplateConfigImpl();
        String title = jsonObject.getJSONObject("templateConfig").getJSONObject("config").getString("title");
        templateConfig.setTemplateId(jsonObject.getString("templateId"));
        templateConfig.setTitle(title);
        templateConfig.setTemplateConfig(jsonObject.getJSONObject("templateConfig").toString());
        boolean success = this.templateConfigService.updateTemplateConfig(templateConfig);
        ResultObject resultObject = new ResultObject(success);
        resultObject.setMessage(success ? "\u4fdd\u5b58\u6210\u529f" : "\u4fdd\u5b58\u5931\u8d25");
        return resultObject;
    }

    @PostMapping
    @RequiresPermissions(value={"nr:dataentryTemplate:manage"})
    @ApiOperation(value="\u529f\u80fd\u5b9a\u4e49\u6dfb\u52a0\u4e00\u6761\u8bb0\u5f55")
    public String addTemplate(@Valid @RequestBody TemplateConfigImpl templateConfig) {
        JSONObject result = new JSONObject();
        boolean success = false;
        if (StringUtils.isNotEmpty((String)templateConfig.getCode()) && StringUtils.isNotEmpty((String)templateConfig.getTitle())) {
            ClassPathResource templateResource = null;
            templateResource = "simpleTemplate".equals(templateConfig.getTemplate()) ? new ClassPathResource("template/templateConfig_simple.json") : new ClassPathResource("template/templateConfig_default.json");
            String defaultConfig = "";
            try (InputStream input = templateResource.getInputStream();){
                defaultConfig = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            JSONObject defaultConfigJson = new JSONObject(defaultConfig);
            JSONObject configJson = (JSONObject)defaultConfigJson.get("config");
            String templateId = UUID.randomUUID().toString();
            configJson.put("templateId", (Object)templateId);
            configJson.put("code", (Object)templateConfig.getCode());
            configJson.put("title", (Object)templateConfig.getTitle());
            defaultConfigJson.put("config", (Object)configJson);
            templateConfig.setTemplateId(templateId);
            templateConfig.setTemplateConfig(JSONObject.valueToString((Object)defaultConfigJson));
            success = this.templateConfigService.addTemplate(templateConfig);
        }
        result.put("success", success);
        result.put("message", (Object)(success ? "\u4fdd\u5b58\u6210\u529f" : "\u4fdd\u5b58\u5931\u8d25"));
        return result.toString();
    }

    @GetMapping(value={"default/{templateId}"})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6a21\u677f\u7684\u9ed8\u8ba4\u914d\u7f6e\u4fe1\u606f")
    public String getDefaultTemplateConfig(@Valid @PathVariable(value="templateId") String templateId) {
        FTemplateConfig templateConfigByCode = this.templateConfigService.getTemplateConfigById(templateId);
        ClassPathResource templateResource = null;
        templateResource = "simpleTemplate".equals(templateConfigByCode.getTemplate()) ? new ClassPathResource("template/templateConfig_simple.json") : new ClassPathResource("template/templateConfig_inner.json");
        String defaultConfig = "";
        try (InputStream input = templateResource.getInputStream();){
            defaultConfig = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return defaultConfig;
    }

    @GetMapping(value={"/{templateId}"})
    @ResponseBody
    @RequiresPermissions(value={"nr:dataentryTemplate:manage"})
    @ApiOperation(value="\u67e5\u8be2\u6a21\u677f\u7684\u914d\u7f6e\u4fe1\u606f")
    public DataentryDefineConfig getBasicTemplateConfig(@PathVariable(value="templateId") String templateId) {
        FTemplateConfig templateConfigByCode = this.templateConfigService.getTemplateConfigById(templateId);
        DataentryDefineConfig config = new DataentryDefineConfig();
        config.setTemplateConfig(templateConfigByCode.getTemplateConfig());
        TemplateItem templateItem = this.plugInService.getTemplateByCode(templateConfigByCode.getTemplate());
        JSONObject templateJson = new JSONObject(templateItem.getContent());
        config.setTemplateContent(templateJson.toString());
        return config;
    }

    @GetMapping(value={"verify/{code}"})
    @ApiOperation(value="\u9a8c\u8bc1\u529f\u80fd\u5b9a\u4e49\u4ee3\u7801\u662f\u5426\u91cd\u590d")
    public String verifyTemplateCode(@PathVariable(value="code") String code) {
        TemplateConfigImpl templateConfig = new TemplateConfigImpl();
        templateConfig.setCode(code);
        boolean noRepeat = this.templateConfigService.verifyCode(templateConfig);
        JSONObject result = new JSONObject();
        result.put("noRepeat", noRepeat);
        return result.toString();
    }

    @PostMapping(value={"/update"})
    @ApiOperation(value="\u66f4\u65b0\u529f\u80fd\u5b9a\u4e49\u7684code,title,\u914d\u7f6e\u4fe1\u606f")
    public String updateTemplateConfig(@RequestBody String configJson) {
        JSONObject jsonObject = new JSONObject(configJson);
        boolean success = false;
        JSONObject result = new JSONObject();
        if (StringUtils.isNotEmpty((String)jsonObject.getString("code")) && StringUtils.isNotEmpty((String)jsonObject.getString("title"))) {
            TemplateConfigImpl templateConfig = new TemplateConfigImpl();
            templateConfig.setTemplateId(jsonObject.getString("templateId"));
            templateConfig.setCode(jsonObject.getString("code"));
            templateConfig.setTitle(jsonObject.getString("title"));
            templateConfig.setTemplateConfig(jsonObject.getJSONObject("templateConfig").toString());
            success = this.templateConfigService.updateTemplateCode(templateConfig);
        }
        result.put("success", success);
        result.put("message", (Object)(success ? "\u4fdd\u5b58\u6210\u529f" : "\u4fdd\u5b58\u5931\u8d25"));
        return result.toString();
    }

    @GetMapping
    @RequiresPermissions(value={"nr:dataentryTemplate:manage"})
    @ApiOperation(value="\u5f97\u5230\u6240\u6709\u7684\u529f\u80fd\u5b9a\u4e49\u914d\u7f6e")
    public List<TemplateConfigImpl> getAllTemplateConfig() {
        return this.templateConfigService.getAllTemplateConfig();
    }

    @PostMapping(value={"/del/{templateId}"})
    @ApiOperation(value="\u5220\u9664\u6a21\u677f\u914d\u7f6e\u4fe1\u606f")
    public ResultObject deleteTemplateConfig(@PathVariable(value="templateId") String templateId) {
        this.templateConfigService.deleteTemplateConfig(templateId);
        return new ResultObject(true);
    }

    @GetMapping(value={"actions/toolbar"})
    @RequiresPermissions(value={"nr:dataentryTemplate:manage"})
    @ApiOperation(value="\u5de5\u5177\u6761\u83b7\u53d6\u6240\u6709\u6ce8\u518c\u7684\u6309\u94ae")
    public GatheredActions getToolbarActions() {
        Tree<ActionItem> actionsTree = this.plugInService.getActions();
        GatheredActions actions = new GatheredActions();
        if (StringUtils.isNotEmpty((String)this.slimButtonsConfig.getButtons())) {
            List<String> list = Arrays.asList(this.slimButtonsConfig.getButtons().split(","));
            List children = actionsTree.getChildren();
            for (Tree child : children) {
                List buttons = child.getChildren();
                if (buttons == null || buttons.size() <= 0) continue;
                buttons.removeIf(item -> !list.contains(((ActionItem)item.getData()).getCode()));
                if (buttons.size() <= 0) continue;
                for (Tree button : buttons) {
                    if (button.getChildren() == null || button.getChildren().size() <= 0) continue;
                    button.getChildren().removeIf(item -> !list.contains(((ActionItem)item.getData()).getCode()));
                }
            }
            children.removeIf(item -> item.getChildren().size() == 0);
        }
        actions.setNode(actionsTree);
        return actions;
    }

    @GetMapping(value={"actions/grid"})
    @RequiresPermissions(value={"nr:dataentryTemplate:manage"})
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\u83b7\u53d6\u6240\u6709\u6ce8\u518c\u7684\u53f3\u952e\u6309\u94ae")
    public GatheredActions getGridActions() {
        Tree<ActionItem> actionsTree = this.plugInService.getActions();
        GatheredActions actions = new GatheredActions();
        if (StringUtils.isNotEmpty((String)this.slimButtonsConfig.getButtons())) {
            List<String> list = Arrays.asList(this.slimButtonsConfig.getButtons().split(","));
            List children = actionsTree.getChildren();
            for (Tree child : children) {
                List buttons = child.getChildren();
                if (buttons == null || buttons.size() <= 0) continue;
                buttons.removeIf(item -> !list.contains(((ActionItem)item.getData()).getCode()));
                if (buttons.size() <= 0) continue;
                for (Tree button : buttons) {
                    if (button.getChildren() == null || button.getChildren().size() <= 0) continue;
                    button.getChildren().removeIf(item -> !list.contains(((ActionItem)item.getData()).getCode()));
                }
            }
            children.removeIf(item -> item.getChildren().size() == 0);
        }
        actions.setNode(actionsTree);
        return actions;
    }

    @GetMapping(value={"infoviews"})
    @ApiOperation(value="\u83b7\u5f97\u6240\u6709\u6ce8\u518c\u7684\u4fe1\u606f\u89c6\u56fe")
    public List<InfoViewItem> getInfoViews() {
        return this.plugInService.getInfoViews();
    }

    @GetMapping(value={"templates"})
    @RequiresPermissions(value={"nr:dataentryTemplate:manage"})
    @ApiOperation(value="\u83b7\u5f97\u6240\u6709\u6a21\u677f")
    public List<TemplateItem> getTemplates() {
        return this.plugInService.getTemplates();
    }

    @GetMapping(value={"getExtendTemplates/all"})
    @ApiOperation(value="\u83b7\u5f97\u6240\u6709\u6269\u5c55\u6a21\u677f")
    public List<ExtendTemplateImpl> getExtendTemplateImpls() {
        return this.templateConfigService.getExtendTemplateImpls();
    }

    @GetMapping(value={"getExtendTemplate/{code}"})
    @ApiOperation(value="\u83b7\u5f97\u6269\u5c55\u6a21\u677f\u901a\u8fc7code")
    public ExtendTemplateImpl getExtendTemplateImpl(@PathVariable(value="code") String code) {
        return this.templateConfigService.getExtendTemplateImpl(code);
    }

    @PostMapping(value={"updatExtendTemplate"})
    @ApiOperation(value="\u4fee\u6539\u6269\u5c55\u6a21\u677f")
    public boolean updateExtendTemplateImpl(@Valid @RequestBody ExtendTemplateImpl extendTemplate) {
        return this.templateConfigService.modifyExtendTemplate(extendTemplate);
    }

    @PostMapping(value={"uploadExtendTemplate"})
    @ApiOperation(value="\u4e0a\u4f20\u6269\u5c55\u6a21\u677f")
    public ReturnInfo uploadExtendTemplate(@Valid @RequestBody ExtendTemplateImpl extendTemplate) {
        return this.templateConfigService.uploadExtendTemplate(extendTemplate);
    }

    @PostMapping(value={"deleteExtendTemplate/{code}"})
    @ApiOperation(value="\u5220\u9664\u6269\u5c55\u6a21\u677f")
    public boolean deleteExtendTemplate(@PathVariable(value="code") String code) {
        return this.templateConfigService.deleteExtendTemplate(code);
    }
}

