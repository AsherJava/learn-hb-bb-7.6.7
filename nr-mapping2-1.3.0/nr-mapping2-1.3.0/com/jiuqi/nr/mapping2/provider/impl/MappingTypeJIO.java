/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.provider.PluginInfo
 *  com.jiuqi.nvwa.mapping.transfer.TransferUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.mapping2.provider.impl;

import com.jiuqi.nr.mapping2.bean.MappingConfig;
import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingResource;
import com.jiuqi.nr.mapping2.provider.TypeOption;
import com.jiuqi.nr.mapping2.provider.impl.MappingTypeConst;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.util.OSSUtils;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.provider.PluginInfo;
import com.jiuqi.nvwa.mapping.transfer.TransferUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MappingTypeJIO
implements INrMappingType {
    protected final Logger logger = LoggerFactory.getLogger(MappingTypeJIO.class);
    public static final String TYPE_JIO = "JIO";
    @Autowired
    private JIOConfigService JIOConfigService;

    @Override
    public String getTypeCode() {
        return TYPE_JIO;
    }

    @Override
    public String getTypeTitle() {
        return "JIO\u6620\u5c04";
    }

    @Override
    public double getOrder() {
        return 10.0;
    }

    @Override
    public ArrayList<NrMappingResource> getResources(MappingScheme scheme) {
        ArrayList<NrMappingResource> res = new ArrayList<NrMappingResource>();
        if (this.JIOConfigService.isJIOSchemeWithFile(scheme).isJioFile()) {
            res.add(MappingTypeConst.JIO_CONFIG);
        }
        return res;
    }

    @Override
    public ArrayList<PluginInfo> getExtendButtons(MappingScheme scheme) {
        ArrayList<PluginInfo> res = new ArrayList<PluginInfo>();
        res.add(new PluginInfo("@nr", "nr-mapping2-jio-plugin", TYPE_JIO));
        return res;
    }

    @Override
    public String getOrgMappingTip(MappingScheme scheme) {
        return "\u9664\u65f6\u671f\u5916\u7684\u5355\u673a\u7248\u4e3b\u4ee3\u7801\u6784\u6210";
    }

    @Override
    public boolean showOrgParentMapping(MappingScheme scheme) {
        try {
            MappingConfig mappingConfig = this.JIOConfigService.queryMappingConfig(scheme.getKey());
            if (mappingConfig == null) {
                return false;
            }
            return mappingConfig.isConfigParentNode();
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public TypeOption getTypeOption(MappingScheme scheme) {
        return new TypeOption(false, false, true, false, false, true);
    }

    @Override
    public void deleteMapping(MappingScheme scheme) {
        String fileKey = this.JIOConfigService.getJIOFileByMs(scheme.getKey());
        if (StringUtils.hasText(fileKey)) {
            this.JIOConfigService.deleteByMS(scheme.getKey());
            try {
                OSSUtils.delete(fileKey);
            }
            catch (Exception e) {
                this.logger.error("\u5220\u9664\u6620\u5c04\u65b9\u6848\uff08JIO\uff09\u9644\u4ef6\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
    }

    @Override
    public void copyMapping(MappingScheme sourceScheme, MappingScheme targetScheme) {
        String sourceKey = "";
        try {
            sourceKey = sourceScheme.getKey();
            String targetKey = targetScheme.getKey();
            String file = this.JIOConfigService.getJIOFileByMs(sourceKey);
            if (!StringUtils.hasText(file)) {
                return;
            }
            InputStream stream = OSSUtils.download(file);
            String fileKey = UUID.randomUUID().toString();
            OSSUtils.upload(fileKey, stream, stream.available());
            this.JIOConfigService.saveJIO(targetKey, fileKey, this.JIOConfigService.getJIOConfigByMs(sourceKey), this.JIOConfigService.getJIOContentByMs(sourceKey));
            MappingConfig mappingConfig = this.JIOConfigService.queryMappingConfig(sourceKey);
            this.JIOConfigService.updateMappingConfig(targetKey, mappingConfig);
        }
        catch (Exception e) {
            this.logger.error(sourceKey + "\u590d\u5236\u5230\u65b0\u7684\u6620\u5c04\u65b9\u6848\uff08JIO\uff09\u9644\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void exportResource(MappingScheme scheme, JSONObject parent) {
        if (this.JIOConfigService.isJIOSchemeWithFile(scheme).isJioFile()) {
            try {
                String msKey = scheme.getKey();
                JSONObject jioJson = new JSONObject();
                parent.put("jio", (Object)jioJson);
                String fileKey = this.JIOConfigService.getJIOFileByMs(msKey);
                InputStream stream = OSSUtils.download(fileKey);
                int len = stream.available();
                byte[] fileByte = new byte[len];
                stream.read(fileByte, 0, len);
                String fileStr = Base64.getEncoder().encodeToString(fileByte);
                jioJson.put("jioFileKey", (Object)fileKey);
                jioJson.put("jioFileData", (Object)fileStr);
                jioJson.put("jioConfig", (Object)Base64.getEncoder().encodeToString(this.JIOConfigService.getJIOConfigByMs(msKey)));
                jioJson.put("jioContent", (Object)Base64.getEncoder().encodeToString(TransferUtil.jsonSerializeToByte((Object)this.JIOConfigService.getJIOContentByMs(msKey))));
                jioJson.put("jioMappingConfig", (Object)Base64.getEncoder().encodeToString(TransferUtil.jsonSerializeToByte((Object)this.JIOConfigService.queryMappingConfig(msKey))));
            }
            catch (Exception e) {
                this.logger.error("\u5973\u5a32\u6620\u5c04\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1a\u5bfc\u51fajio\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }

    @Override
    public void importResource(MappingScheme scheme, JSONObject parent) {
        if (!parent.has("jio")) {
            return;
        }
        JSONObject jioJson = parent.getJSONObject("jio");
        if (jioJson != null && !jioJson.isEmpty()) {
            String fileKey = jioJson.getString("jioFileKey");
            String fileStr = jioJson.getString("jioFileData");
            if (!StringUtils.hasText(fileStr)) {
                return;
            }
            try {
                if (!StringUtils.hasText(fileKey)) {
                    fileKey = UUID.randomUUID().toString();
                } else {
                    OSSUtils.delete(fileKey);
                }
                byte[] fileByte = Base64.getDecoder().decode(fileStr);
                OSSUtils.upload(fileKey, new ByteArrayInputStream(fileByte), fileByte.length);
                String msKey = scheme.getKey();
                String configStr = jioJson.getString("jioConfig");
                byte[] configByte = null;
                if (StringUtils.hasText(configStr)) {
                    configByte = Base64.getDecoder().decode(configStr);
                }
                String contentStr = jioJson.getString("jioContent");
                JIOContent content = null;
                if (StringUtils.hasText(contentStr)) {
                    content = (JIOContent)TransferUtil.tranJsonToObject((byte[])Base64.getDecoder().decode(contentStr), JIOContent.class);
                }
                this.JIOConfigService.saveJIO(msKey, fileKey, configByte, content);
                String mappingConfigStr = jioJson.getString("jioMappingConfig");
                if (StringUtils.hasText(mappingConfigStr)) {
                    MappingConfig mappingConfig = (MappingConfig)TransferUtil.tranJsonToObject((byte[])Base64.getDecoder().decode(mappingConfigStr), MappingConfig.class);
                    this.JIOConfigService.updateMappingConfig(msKey, mappingConfig);
                }
            }
            catch (Exception e) {
                this.logger.error("\u5973\u5a32\u6620\u5c04\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1asyncJIO\u5bfc\u5165jio\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }
}

