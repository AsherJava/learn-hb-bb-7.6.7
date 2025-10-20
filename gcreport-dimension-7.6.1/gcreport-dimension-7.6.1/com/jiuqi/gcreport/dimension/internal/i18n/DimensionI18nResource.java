/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.gcreport.dimension.internal.i18n;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DimensionI18nResource
implements I18NResource {
    private static final long serialVersionUID = 1L;

    public String name() {
        return "\u5408\u5e76\u62a5\u8868/\u7ef4\u5ea6\u7ba1\u7406";
    }

    public String getNameSpace() {
        return "gc";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        List<DimensionVO> dimensionVOList = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).loadAllDimensions();
        if (!CollectionUtils.isEmpty(dimensionVOList)) {
            for (DimensionVO dimension : dimensionVOList) {
                resourceObjects.add(new I18NResourceItem(dimension.getId(), dimension.getTitle()));
            }
        }
        return resourceObjects;
    }
}

