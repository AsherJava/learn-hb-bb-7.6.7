/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.data.datain.service;

import com.jiuqi.nr.single.core.data.bean.SingleDataFileInfo;
import com.jiuqi.nr.single.core.file.SingleFile;
import java.util.List;
import nr.single.map.data.exception.SingleDataException;

public interface ITaskFileReadDataService {
    public void readFMDMunits(String var1, String var2, List<String> var3) throws SingleDataException;

    public void readFMDMunitsByOther(String var1, String var2, List<String> var3) throws SingleDataException;

    public SingleDataFileInfo getFMDMUnits(String var1, String var2) throws SingleDataException;

    public void readFMDMUnitInfosByPath(SingleFile var1, SingleDataFileInfo var2, String var3, String var4, boolean var5) throws SingleDataException;
}

