/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.MidstoreExecutionException
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.extension.IMidstoreFieldPublishExecutor
 */
package nr.midstore2.data.extension;

import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.MidstoreExecutionException;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.extension.IMidstoreFieldPublishExecutor;
import nr.midstore2.data.publish.IReportMidstorePublishFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MidstoreReportFieldPublishExecutorImpl
implements IMidstoreFieldPublishExecutor {
    @Autowired
    private IReportMidstorePublishFieldService fieldPublishService;

    public MidstoreResultObject executePublish(MidstoreExeContext context) throws MidstoreExecutionException {
        return this.fieldPublishService.publishFields(context, context.getDataExchangeTask());
    }
}

