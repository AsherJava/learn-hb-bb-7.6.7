/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.service;

import java.util.List;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.TaskDataContext;

public interface TaskDataService {
    public void initContext(TaskDataContext var1, String var2, String var3, String var4);

    public TaskDataContext getNewContext();

    public TaskDataContext getNewAndinitContext(String var1, String var2, String var3);

    public String getNetPeriodCode(TaskDataContext var1, String var2);

    public String getSinglePeriodCode(TaskDataContext var1, String var2, int var3);

    public String getNetCompanyKey(TaskDataContext var1, String var2);

    public List<SingleConfigInfo> queryMappingInSchemeByTask(String var1, String var2, String var3);

    public List<SingleConfigInfo> queryMappingByTask(String var1, String var2);

    public List<SingleConfigInfo> queryMappingInScheme(String var1);

    public SingleConfigInfo queryMapping(String var1);

    public void MapSingleEnityData(TaskDataContext var1) throws Exception;

    public void makeExportEnityList(TaskDataContext var1, List<String> var2);

    public List<String> queryFilterUnits(TaskDataContext var1, String var2);

    public List<String> getAuthEntityData(TaskDataContext var1, String var2);
}

