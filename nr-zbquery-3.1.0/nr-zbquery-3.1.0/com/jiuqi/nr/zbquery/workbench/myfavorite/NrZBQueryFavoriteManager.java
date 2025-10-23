/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.INormalAction
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.workbench.favorites.view.extend.INvwaFavoritesExtendProvider
 */
package com.jiuqi.nr.zbquery.workbench.myfavorite;

import com.jiuqi.nr.zbquery.workbench.share.action.ZBQueryShowTableAction;
import com.jiuqi.nvwa.resourceview.action.INormalAction;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.workbench.favorites.view.extend.INvwaFavoritesExtendProvider;
import org.springframework.stereotype.Component;

@Component
public class NrZBQueryFavoriteManager
implements INvwaFavoritesExtendProvider {
    public ResourceType getResourceType() {
        return new ResourceType("com.jiuqi.nr.zbquery.manage", "\u67e5\u8be2");
    }

    public String getIcon() {
        return "#icon-16_DH_A_NR_guoluchaxun";
    }

    public INormalAction getTitleResourceShowAction() {
        return new ZBQueryShowTableAction();
    }
}

