/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark.inner;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.IResourceTagProvider;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.IWatermarkVariableProvider;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.NvwaWatermarkVariableDTO;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkContext;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkException;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class WatermarkManager {
    private static final WatermarkManager instance = new WatermarkManager();
    private WatermarkFactory watermarkFactory;

    public static WatermarkManager getInstance() {
        return instance;
    }

    public void register(WatermarkFactory factory) {
        if (factory != null) {
            this.watermarkFactory = factory;
        }
    }

    public void saveWatermarkInfo(Watermark watermark) throws WatermarkException {
        if (this.watermarkFactory == null) {
            throw new WatermarkException("\u672a\u6ce8\u518c\u6c34\u5370\u64cd\u4f5c\u63d0\u4f9b\u5668");
        }
        this.watermarkFactory.getStorage().saveWatermarkInfo(watermark);
    }

    public Watermark getWatermarkInfo() throws WatermarkException {
        if (this.watermarkFactory == null) {
            throw new WatermarkException("\u672a\u6ce8\u518c\u6c34\u5370\u64cd\u4f5c\u63d0\u4f9b\u5668");
        }
        return this.watermarkFactory.getStorage().getWatermarkInfo();
    }

    public Map<String, String> getValue(WatermarkContext context, List<String> tags, List<String> resTags) throws WatermarkException {
        Map extendVarMap;
        if (this.watermarkFactory == null) {
            throw new WatermarkException("\u672a\u6ce8\u518c\u6c34\u5370\u6807\u7b7e\u64cd\u4f5c\u63d0\u4f9b\u5668");
        }
        Map<String, String> defTags = this.watermarkFactory.getTags().getValue(tags);
        if (this.watermarkFactory.getResourceTags() != null) {
            for (IResourceTagProvider resourceTagProvider : this.watermarkFactory.getResourceTags().values()) {
                Map<String, String> resTagsValue = resourceTagProvider.getValue(context.getResourceGuid(), resTags);
                defTags.putAll(resTagsValue);
            }
        }
        if (!CollectionUtils.isEmpty(extendVarMap = (Map)Optional.ofNullable(SpringBeanUtils.getApplicationContext()).map(it -> it.getBeansOfType(IWatermarkVariableProvider.class)).orElse(null))) {
            ArrayList<NvwaWatermarkVariableDTO> extendVarList = new ArrayList<NvwaWatermarkVariableDTO>();
            for (IWatermarkVariableProvider provider : extendVarMap.values()) {
                if (CollectionUtils.isEmpty(provider.getExtendVariableList())) continue;
                extendVarList.addAll(provider.getExtendVariableList());
            }
            Map<String, NvwaWatermarkVariableDTO> code2VarDTOMap = extendVarList.stream().collect(Collectors.toMap(NvwaWatermarkVariableDTO::getCode, e -> e, (e1, e2) -> e1));
            for (String resTag : resTags) {
                if (!code2VarDTOMap.containsKey(resTag)) continue;
                NvwaWatermarkVariableDTO varDTO = code2VarDTOMap.get(resTag);
                defTags.put(varDTO.getCode(), varDTO.getValueSupplier().get());
            }
        }
        return defTags;
    }
}

