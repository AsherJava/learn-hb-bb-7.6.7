/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.bde.bizmodel.impl.dimension.i18n;

import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BdeDimensionI18nResource
implements I18NResource {
    private static final long serialVersionUID = 64125670813041L;

    public String name() {
        return "BDE/\u7ef4\u5ea6\u7ba1\u7406";
    }

    public String getNameSpace() {
        return "BDE";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        List<DimensionVO> assistDimDTOS = BdeAssistDimUtils.listAssistDim();
        if (!CollectionUtils.isEmpty(assistDimDTOS)) {
            for (DimensionVO assistDimDTO : assistDimDTOS) {
                resourceObjects.add(new I18NResourceItem(assistDimDTO.getCode(), assistDimDTO.getTitle()));
                resourceObjects.add(new I18NResourceItem(assistDimDTO.getCode() + "_CODE", assistDimDTO.getTitle() + "\u4ee3\u7801"));
                resourceObjects.add(new I18NResourceItem(assistDimDTO.getCode() + "_NAME", assistDimDTO.getTitle() + "\u540d\u79f0"));
            }
        }
        return resourceObjects;
    }
}

