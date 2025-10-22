/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.MidstoreExecutionException
 *  com.jiuqi.nvwa.midstore.MidstoreExecutor
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 */
package nr.midstore2.data.extension;

import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.MidstoreExecutionException;
import com.jiuqi.nvwa.midstore.MidstoreExecutor;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore2.data.work.IReportMidstoreExcuteGetService;
import nr.midstore2.data.work.IReportMidstoreExcutePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MidstoreReportExecutorImpl
extends MidstoreExecutor {
    @Autowired
    private IReportMidstoreExcuteGetService fieldGetService;
    @Autowired
    private IReportMidstoreExcutePostService fieldPostService;

    public MidstoreResultObject executeGet(MidstoreExeContext context) throws MidstoreExecutionException {
        return this.fieldGetService.readFieldDataFromMidstore(context, context.getDataExchangeTask());
    }

    public MidstoreResultObject executePost(MidstoreExeContext context) throws MidstoreExecutionException {
        return this.fieldPostService.writeFieldDataFromMidstore(context, context.getDataExchangeTask());
    }

    public MidstoreResultObject executeCheck(MidstoreExeContext context) throws MidstoreExecutionException {
        return new MidstoreResultObject(true, "");
    }
}

