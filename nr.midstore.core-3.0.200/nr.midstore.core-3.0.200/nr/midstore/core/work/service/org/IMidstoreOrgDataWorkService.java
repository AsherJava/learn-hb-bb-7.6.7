/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.midstore.core.work.service.org;

import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.List;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;

public interface IMidstoreOrgDataWorkService {
    public void saveOrgDatas(MidstoreContext var1, IDataExchangeTask var2, AsyncTaskMonitor var3) throws MidstoreException;

    public List<String> getUnitCodesByMidstoreScheme(MidstoreContext var1);

    public List<String> getUnitCodesByOrgData(MidstoreContext var1);

    public List<String> getUnitCodesFromMidstore(MidstoreContext var1);

    public List<String> getUnitCodesFromMidstore(MidstoreContext var1, IDataExchangeTask var2);

    public List<MidstoreOrgDataDTO> getOrgDataFromMidstore(String var1, AsyncTaskMonitor var2) throws MidstoreException;
}

