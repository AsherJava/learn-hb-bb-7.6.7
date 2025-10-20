/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.nr.definition.controller.IDesignParaCheckController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignParaCheckController
implements IDesignParaCheckController {
    @Autowired
    private DesignDataLinkDefineService dataLinkService;

    @Override
    public List<DesignDataLinkDefine> listLinkNotInRegion() {
        return this.dataLinkService.listGhostLink();
    }

    @Override
    public List<String> listLinkKeyPhysicalCoordinatesDuplicate(String regionKey) {
        return this.dataLinkService.listLinkKeyPhysicalCoordinatesDuplicate(regionKey);
    }

    @Override
    public List<String> listLinkKeyDataCoordinatesDuplicate(String regionKey) {
        return this.dataLinkService.listLinkKeyDataCoordinatesDuplicate(regionKey);
    }

    @Override
    public List<String> listLinkKeyRefuseView(String regionKey) {
        return this.dataLinkService.listLinkKeyRefuseView(regionKey);
    }

    @Override
    public List<String> listLinkKeyViewQuoteError(String regionKey) {
        return this.dataLinkService.listLinkKeyViewQuoteError(regionKey);
    }
}

