/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.Config
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 */
package nr.midstore.core.param.service;

import com.jiuqi.bi.core.midstore.dataexchange.Config;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;

public interface IMistoreExchangeTaskService {
    public IDataExchangeTask getExchangeTask(MidstoreContext var1) throws MidstoreException;

    public MidstoreResultObject deleteExchangeTask(MidstoreSchemeDTO var1) throws MidstoreException;

    public IDataExchangeProvider getExchangeProvider(MidstoreSchemeDTO var1) throws MidstoreException;

    public Config getExchangeConfig(MidstoreSchemeDTO var1);

    public Config getMidstoreConfig();
}

