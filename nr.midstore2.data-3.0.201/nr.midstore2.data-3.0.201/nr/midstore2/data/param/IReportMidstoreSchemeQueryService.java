/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 */
package nr.midstore2.data.param;

import com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import java.util.List;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;

public interface IReportMidstoreSchemeQueryService {
    public List<MidstoreSchemeDTO> getSchemesByOrgCode(String var1, String var2);

    public MidstoreSchemeDTO getSchemeByKey(String var1);

    public List<MidstoreSchemeDTO> getSchemesBySchemeKeys(List<String> var1);

    public List<MidstoreSchemeDTO> getSchemesBySchemeKeys(List<String> var1, PublishStateType var2);

    public List<MidstoreSchemeDTO> getSchemesBySchemeCodes(List<String> var1);

    public List<MidstoreSchemeDTO> getSchemesByCodes(String var1, List<String> var2);

    public List<MidstoreSchemeDTO> getSchemesByTask(String var1);

    public List<MidstoreSchemeDTO> getSchemesByTask(String var1, PublishStateType var2);

    public List<MidstoreSchemeDTO> getSchemesByTasks(List<String> var1);

    public List<ReportDataSourceDTO> getDataSoruceBySchemeCodes(List<String> var1);

    public List<ReportDataSourceDTO> getDataSoruceBySchemeKeys(List<String> var1);
}

