/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchArgs;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ICustomFetchResultSetFilter;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.CustomFetchFourCondiResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.CustomFetchOneCondiResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.CustomFetchSimpleResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.CustomFetchThreeCondiResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.CustomFetchTwoCondiResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.ICustomFetchResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.AbstractCustomFetchResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.CustomFetchFourCondiResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.CustomFetchOneCondiResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.CustomFetchSimpleResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.CustomFetchThreeCondiResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.CustomFetchTwoCondiResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchOneCondiResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchSimpleResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchThreeCondiResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchTwoCondiResult;
import com.jiuqi.common.base.util.CollectionUtils;

public class CustomFetchResultFactory {
    public static AbstractCustomFetchResultSetExtractor createResultSetExtractor(CustomBizModelDTO customBizModel, CustomFetchArgs fetchArgs, ICustomFetchResultSetFilter filter) {
        if (CollectionUtils.isEmpty(fetchArgs.getCondiList())) {
            return new CustomFetchSimpleResultSetExtractor(customBizModel, fetchArgs, filter);
        }
        if (fetchArgs.getCondiList().size() == 1) {
            return new CustomFetchOneCondiResultSetExtractor(customBizModel, fetchArgs, filter);
        }
        if (fetchArgs.getCondiList().size() == 2) {
            return new CustomFetchTwoCondiResultSetExtractor(customBizModel, fetchArgs, filter);
        }
        if (fetchArgs.getCondiList().size() == 3) {
            return new CustomFetchThreeCondiResultSetExtractor(customBizModel, fetchArgs, filter);
        }
        return new CustomFetchFourCondiResultSetExtractor(customBizModel, fetchArgs, filter);
    }

    public static ICustomFetchResultCalculator createCalculator(CustomBizModelDTO customBizModel, AbstractCustomFetchResult fetchResult) {
        if (fetchResult instanceof CustomFetchSimpleResult) {
            return new CustomFetchSimpleResultCalculator(customBizModel, fetchResult);
        }
        if (fetchResult instanceof CustomFetchOneCondiResult) {
            return new CustomFetchOneCondiResultCalculator(customBizModel, fetchResult);
        }
        if (fetchResult instanceof CustomFetchTwoCondiResult) {
            return new CustomFetchTwoCondiResultCalculator(customBizModel, fetchResult);
        }
        if (fetchResult instanceof CustomFetchThreeCondiResult) {
            return new CustomFetchThreeCondiResultCalculator(customBizModel, fetchResult);
        }
        return new CustomFetchFourCondiResultCalculator(customBizModel, fetchResult);
    }
}

