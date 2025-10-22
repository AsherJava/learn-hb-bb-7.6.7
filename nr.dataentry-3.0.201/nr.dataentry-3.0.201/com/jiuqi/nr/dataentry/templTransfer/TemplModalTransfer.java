/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.templTransfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class TemplModalTransfer
implements IModelTransfer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplModalTransfer.class);
    @Autowired
    private ITemplateConfigService templateConfigService;

    public void importModel(IImportContext iImportContext, byte[] bytes) throws TransferException {
        String modelData = new String(bytes, StandardCharsets.UTF_8);
        TemplateConfigImpl templateConfig = null;
        if (!ObjectUtils.isEmpty(modelData)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                templateConfig = (TemplateConfigImpl)mapper.readValue(modelData, (TypeReference)new TypeReference<TemplateConfigImpl>(){});
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if (templateConfig != null) {
            List<TemplateConfigImpl> allTemplateConfig = this.templateConfigService.getAllTemplateConfig();
            TemplateConfigImpl finalTemplateConfig = templateConfig;
            List filterCollectors = allTemplateConfig.stream().filter(item -> item.getCode().equals(finalTemplateConfig.getCode())).collect(Collectors.toList());
            if (filterCollectors.size() > 0) {
                if (StringUtils.isNotEmpty((String)templateConfig.getCode()) && StringUtils.isNotEmpty((String)templateConfig.getTitle())) {
                    this.templateConfigService.updateTemplateConfigByCode(templateConfig);
                }
            } else if (StringUtils.isNotEmpty((String)templateConfig.getCode()) && StringUtils.isNotEmpty((String)templateConfig.getTitle())) {
                this.templateConfigService.addTemplate(templateConfig);
            }
        }
    }

    private String objectToString(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String s) throws TransferException {
        FTemplateConfig templateConfigByCode = this.templateConfigService.getTemplateConfigByCode(s.substring(6));
        MetaExportModel metaExportModel = new MetaExportModel();
        metaExportModel.setData(this.objectToString(templateConfigByCode).getBytes(StandardCharsets.UTF_8));
        return metaExportModel;
    }
}

