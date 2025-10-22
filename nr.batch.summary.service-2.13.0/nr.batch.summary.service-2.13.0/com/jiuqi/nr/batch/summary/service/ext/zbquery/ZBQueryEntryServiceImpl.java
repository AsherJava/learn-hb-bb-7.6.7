/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.zbquery.model.ZBQueryModel
 *  com.jiuqi.nr.zbquery.rest.DataEntryController
 *  com.jiuqi.nr.zbquery.rest.vo.DataEntryVO
 *  com.jiuqi.nr.zbquery.rest.vo.DimensionVO
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.zbquery;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryPara;
import com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryService;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactory;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.DataEntryController;
import com.jiuqi.nr.zbquery.rest.vo.DataEntryVO;
import com.jiuqi.nr.zbquery.rest.vo.DimensionVO;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryEntryServiceImpl
implements ZBQueryEntryService {
    @Resource
    private BSSchemeService bsSchemeService;
    @Resource
    private DataEntryController dataEntryController;
    @Resource
    private TargetDimProviderFactory targetDimFactory;
    @Resource
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public ZBQueryModel newZBQueryModel(ZBQueryEntryPara funcParam) throws Exception {
        SummaryScheme summaryScheme = this.bsSchemeService.findScheme(funcParam.getBatchGatherSchemeCode());
        TargetDimProvider targetDimProvider = this.targetDimFactory.getTargetDimProvider(summaryScheme);
        List<String> entityRowKeys = targetDimProvider.getEntityRowKeys(funcParam.getPeriod(), funcParam.getDimValue());
        if (entityRowKeys == null || entityRowKeys.isEmpty()) {
            return null;
        }
        String strUnitKeys = String.join((CharSequence)";", entityRowKeys);
        ((DimensionVO)funcParam.getDimensionVOList().get(0)).setValue(strUnitKeys);
        return this.dataEntryController.getQueryModelObject((DataEntryVO)funcParam);
    }
}

