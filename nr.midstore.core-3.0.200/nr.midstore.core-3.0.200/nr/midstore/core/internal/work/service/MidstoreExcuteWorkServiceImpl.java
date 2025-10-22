/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package nr.midstore.core.internal.work.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.work.service.MidstoreExcutePostServiceImpl;
import nr.midstore.core.work.service.IMidstoreExcuteGetService;
import nr.midstore.core.work.service.IMidstoreExcutePostService;
import nr.midstore.core.work.service.IMidstoreExcuteWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreExcuteWorkServiceImpl
implements IMidstoreExcuteWorkService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreExcutePostServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreExcutePostService postService;
    @Autowired
    private IMidstoreExcuteGetService getService;

    @Override
    public MidstoreResultObject excuteDataByCode(String midstoreSchemeCode, Map<DimensionValueSet, List<String>> unitFormKeys, AsyncTaskMonitor monitor) throws MidstoreException {
        if (StringUtils.isEmpty((String)midstoreSchemeCode)) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a");
        }
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByCode(midstoreSchemeCode);
        if (scheme == null) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + midstoreSchemeCode);
        }
        if (null == scheme.getExchangeMode()) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5b9a\u4e49\u4ea4\u6362\u6a21\u5f0f\uff0c" + midstoreSchemeCode);
        }
        if (ExchangeModeType.EXCHANGE_GET == scheme.getExchangeMode()) {
            return this.getService.excuteGetDataByCode(midstoreSchemeCode, unitFormKeys, monitor);
        }
        if (ExchangeModeType.EXCHANGE_POST == scheme.getExchangeMode()) {
            return this.postService.excutePostDataByCode(midstoreSchemeCode, unitFormKeys, monitor);
        }
        return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u7684\u6a21\u5f0f\u672a\u77e5\uff0c" + midstoreSchemeCode);
    }
}

