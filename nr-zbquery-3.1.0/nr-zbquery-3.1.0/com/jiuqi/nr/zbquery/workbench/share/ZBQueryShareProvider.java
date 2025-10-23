/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.INormalAction
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.workbench.myanalysis.bean.dto.MyAnalysisDataDTO
 *  com.jiuqi.nvwa.workbench.myanalysis.service.IMyAnalysisDataService
 *  com.jiuqi.nvwa.workbench.share.bean.ShareResourceInfo
 *  com.jiuqi.nvwa.workbench.share.view.extend.INvwaShareExtendProvider
 */
package com.jiuqi.nr.zbquery.workbench.share;

import com.jiuqi.nr.zbquery.workbench.share.action.ZBQueryShowTableAction;
import com.jiuqi.nvwa.resourceview.action.INormalAction;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.workbench.myanalysis.bean.dto.MyAnalysisDataDTO;
import com.jiuqi.nvwa.workbench.myanalysis.service.IMyAnalysisDataService;
import com.jiuqi.nvwa.workbench.share.bean.ShareResourceInfo;
import com.jiuqi.nvwa.workbench.share.view.extend.INvwaShareExtendProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZBQueryShareProvider
implements INvwaShareExtendProvider {
    @Autowired
    private IMyAnalysisDataService myAnalysisDataService;

    public ResourceType getResourceType() {
        return new ResourceType("com.jiuqi.nvwa.myanalysis.zbquery", "\u67e5\u8be2");
    }

    public String getIcon() {
        return "#icon-16_DH_A_NR_guoluchaxun";
    }

    public INormalAction getShareResourceShowAction() {
        return new ZBQueryShowTableAction();
    }

    public ShareResourceInfo get(String dataId) {
        MyAnalysisDataDTO myAnalysisDataDTO = this.myAnalysisDataService.getById(dataId);
        if (myAnalysisDataDTO != null) {
            ShareResourceInfo shareResourceInfo = new ShareResourceInfo();
            shareResourceInfo.setDataId(dataId);
            shareResourceInfo.setTitle(myAnalysisDataDTO.getTitle());
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
        ArrayList<ShareResourceInfo> shareResourceInfos = new ArrayList<ShareResourceInfo>();
        this.myAnalysisDataService.search(keyword, dataIds).forEach(myAnalysisDataDTO -> {
            ShareResourceInfo shareResourceInfo = new ShareResourceInfo();
            shareResourceInfo.setDataId(myAnalysisDataDTO.getId());
            shareResourceInfo.setTitle(myAnalysisDataDTO.getTitle());
            shareResourceInfos.add(shareResourceInfo);
        });
        return shareResourceInfos;
    }
}

