/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package nr.midstore.design.service;

import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.vo.MidstoreFormTreeVO;
import nr.midstore.design.vo.MistoreDataLinkVO;
import nr.midstore.design.vo.MistoreFieldVO;
import nr.midstore.design.vo.MistoreFormVO;
import nr.midstore.design.vo.MistoreGroupVO;
import nr.midstore.design.vo.MistoreTableVO;
import nr.midstore.design.vo.MistoreTaskVO;

public interface IParamQueryService {
    public List<MistoreTaskVO> getAllTask();

    public List<MistoreTaskVO> getTaskList(List<String> var1);

    public MistoreTaskVO getTaskDefine(String var1);

    public List<CommonParamDTO> listFormScheme(String var1);

    public String getEntityIdByTask(String var1);

    public List<CommonParamDTO> listDataTable(String var1, Boolean var2);

    public List<CommonParamDTO> listZBByTableCode(String var1);

    public List<CommonParamDTO> getEnumList(String var1);

    public List<CommonParamDTO> listPeriod();

    public List<CommonParamDTO> listEntity(List<String> var1);

    public List<MistoreFieldVO> getFieldByBaseData(String var1);

    public List<MistoreFieldVO> getFieldByEntityID(String var1);

    public List<MistoreFieldVO> getFieldByOrg(String var1);

    public List<MistoreFieldVO> getFieldByTable(String var1);

    public List<MistoreGroupVO> getAllBaseDataGroups();

    public List<MistoreGroupVO> getRootBaseDataGroups();

    public List<MistoreGroupVO> getBaseDataGroupsByParent(String var1);

    public List<CommonParamDTO> getBaseDatasByGroup(String var1);

    public List<MistoreGroupVO> getAllDataGroupsByScheme(String var1);

    public List<MistoreGroupVO> getRootDataGroupsByScheme(String var1);

    public List<MistoreGroupVO> getDataGroupsByParent(String var1);

    public List<MistoreTableVO> getRootDataTablesByScheme(String var1);

    public List<MistoreTableVO> getDataTablesByGroup(String var1);

    public List<MistoreTableVO> getAllDataTablesByScheme(String var1);

    public List<MistoreGroupVO> getAllFormGroupsByScheme(String var1);

    public List<MistoreGroupVO> getRootFormGroupsByScheme(String var1);

    public List<MistoreGroupVO> getFormGroupsByParent(String var1);

    public List<MistoreFormVO> getFormsByGroup(String var1, boolean var2) throws Exception;

    public List<MistoreFormVO> getAllFormsByScheme(String var1);

    public List<MidstoreFormTreeVO> getReportTree(String var1);

    public List<MistoreDataLinkVO> getDataLinkByForm(String var1);

    public Grid2Data getGridDataByForm(String var1);
}

