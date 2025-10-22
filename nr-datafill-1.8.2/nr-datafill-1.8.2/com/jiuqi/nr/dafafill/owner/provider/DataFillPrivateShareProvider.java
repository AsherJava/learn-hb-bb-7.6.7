/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.INormalAction
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.workbench.share.bean.ShareResourceInfo
 *  com.jiuqi.nvwa.workbench.share.view.extend.INvwaShareExtendProvider
 */
package com.jiuqi.nr.dafafill.owner.provider;

import com.jiuqi.nr.dafafill.owner.entity.DataFillDefinitionPrivate;
import com.jiuqi.nr.dafafill.owner.provider.DataFillPrivateShareAction;
import com.jiuqi.nr.dafafill.owner.service.IDataFillPrivateService;
import com.jiuqi.nvwa.resourceview.action.INormalAction;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.workbench.share.bean.ShareResourceInfo;
import com.jiuqi.nvwa.workbench.share.view.extend.INvwaShareExtendProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataFillPrivateShareProvider
implements INvwaShareExtendProvider {
    public static final String RESOURCE_TYPE_ID = "com.jiuqi.nr.dafafill.owner";
    private static final String RESOURCE_TYPE_TITLE = "\u81ea\u5b9a\u4e49\u5f55\u5165";
    private static final String ICON = "#icon-16_SHU_A_NR_fudongbiao1";
    @Autowired
    private IDataFillPrivateService dataFillPrivateService;

    public ResourceType getResourceType() {
        return new ResourceType(RESOURCE_TYPE_ID, RESOURCE_TYPE_TITLE);
    }

    public String getIcon() {
        return ICON;
    }

    public INormalAction getShareResourceShowAction() {
        return new DataFillPrivateShareAction();
    }

    public ShareResourceInfo get(String dataId) {
        DataFillDefinitionPrivate definition = this.dataFillPrivateService.getDefinitionByKey(dataId);
        if (definition != null) {
            ShareResourceInfo shareResourceInfo = new ShareResourceInfo();
            shareResourceInfo.setDataId(dataId);
            shareResourceInfo.setTitle(definition.getTitle());
            return shareResourceInfo;
        }
        return null;
    }

    public List<ShareResourceInfo> getAll(List<String> dataIds) {
        ArrayList<ShareResourceInfo> shareResourceInfos = new ArrayList<ShareResourceInfo>();
        dataIds.forEach(dataId -> {
            ShareResourceInfo shareResourceInfo = this.get((String)dataId);
            if (shareResourceInfo != null) {
                shareResourceInfos.add(shareResourceInfo);
            }
        });
        return shareResourceInfos;
    }

    public List<ShareResourceInfo> search(String keyword, List<String> dataIds) {
        return null;
    }
}

