/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.embedded;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.embedded.EmbeddedDSModel;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;

public class EmbeddedIDataSetProvider
implements IDataSetProvider {
    @Override
    public void open(MemoryDataSet<BIDataSetFieldInfo> memory, IDSContext context) throws BIDataSetException {
        DSContext dscxt = (DSContext)context;
        DSModel dsmodel = dscxt.getDsModel();
        if (dsmodel instanceof EmbeddedDSModel) {
            EmbeddedDSModel model = (EmbeddedDSModel)dsmodel;
            try {
                memory.load(model.getData());
            }
            catch (IOException e) {
                throw new BIDataSetException(e.getMessage(), e);
            }
        } else {
            throw new BIDataSetException("\u4f20\u5165\u7684\u6a21\u578b\u9519\u8bef");
        }
        Metadata metadata = memory.getMetadata();
        Map properties = metadata.getProperties();
        ArrayList<DSHierarchy> hiers = (ArrayList<DSHierarchy>)properties.get("HIERARCHY");
        String hierSerials = (String)properties.get("hierSerials");
        if (hiers == null && hierSerials != null) {
            String[] newHiers = StringUtils.split((String)hierSerials, (String)"||");
            hiers = new ArrayList<DSHierarchy>();
            for (String h : newHiers) {
                DSHierarchy hier = new DSHierarchy();
                hier.fromJson(new JSONObject(h));
                hiers.add(hier);
            }
            properties.put("HIERARCHY", hiers);
        }
    }
}

