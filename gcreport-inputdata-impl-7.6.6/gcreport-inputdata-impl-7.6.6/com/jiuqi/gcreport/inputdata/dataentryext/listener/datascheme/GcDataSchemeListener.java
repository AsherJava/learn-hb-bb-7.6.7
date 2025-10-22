/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeListener
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 */
package com.jiuqi.gcreport.inputdata.dataentryext.listener.datascheme;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.inputdata.dataentryext.listener.service.GcUpdateDataSchemeService;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.event.DataSchemeListener;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class GcDataSchemeListener
implements DataSchemeListener {
    @Lazy
    @Autowired
    private InputDataSchemeService inputDataSchemeService;
    @Lazy
    @Autowired
    private GcUpdateDataSchemeService gcUpdateDataSchemeService;
    @Lazy
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;

    public void savePostProcess(DesignDataScheme dataScheme, List<DesignDataDimension> add) {
    }

    public void updatePostProcess(DesignDataScheme srcDataScheme, DesignDataScheme dataScheme, List<DesignDataDimension> add, List<DesignDataDimension> delete) {
        List dimensions = this.iDesignDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        this.gcUpdateDataSchemeService.addDataSchemeAdjustDimExecute(dataScheme, dimensions);
    }

    public void deletePostProcess(DesignDataScheme dataScheme) {
        if (dataScheme != null && !StringUtils.isEmpty((String)dataScheme.getKey())) {
            this.inputDataSchemeService.deleteInputDataSchemeByDataSchemeKey(dataScheme.getKey());
        }
    }
}

