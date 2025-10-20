/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.intermediatelibrary.dao;

import com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.FieldDataRegionEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILOrgEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSetupCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSyncCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.MdZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.MetaDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediateLibraryDao {
    public List<ILEntity> getAllProgramme();

    public ILEntity getProgrammeForId(String var1);

    public ILEntity getProgrammeForName(String var1);

    public List<ILOrgEntity> getAllOrgIdForProgrammeId(String var1);

    public String addProgramme(ILCondition var1);

    public void addProgrammeOfOrgId(ILCondition var1);

    public void deleteProgrammeOfOrgId(ILCondition var1);

    public void addProgrammeOfField(ILCondition var1);

    public void deleteProgrammeOfField(ILCondition var1);

    public void deleteFieldOfProgrammeId(ILCondition var1);

    public void deleteAllProgrammeOfField(ILCondition var1);

    public void updateProgramme(ILCondition var1);

    public void deleteProgramme(ILCondition var1);

    public void synchroProgramme(ILSetupCondition var1);

    public void clearProgramme(ILSetupCondition var1);

    public void clearProgrammeForYear(ILSetupCondition var1, ILClearCondition var2, String var3);

    public List<ILFieldVO> getFieldOfProgrammeIdPage(ILFieldCondition var1, int var2);

    public List<ILFieldVO> getFieldOfProgrammeId(String var1);

    public List<String> getFieldIdOfProgrammeId(String var1);

    public int getFieldCouontOfProgrammeId(ILFieldCondition var1);

    public List<String> getDataRegionKey(String var1);

    public List<FieldDataRegionEntity> getDataRegionKeyForId(String var1);

    public MetaDataEntity extractMetaData(ILSyncCondition var1, String var2, String var3);

    public List<ZbDataEntity> extractZbData(ZbDataEntity var1, ILEntity var2, JdbcTemplate var3);

    public void deleteZbDataByWhere(List<ZbDataEntity> var1, ILEntity var2) throws SQLException;

    public void pushZbData(List<ZbDataEntity> var1, ILEntity var2);

    public void deleteMdZbDataByWhere(List<MdZbDataEntity> var1, ILEntity var2) throws SQLException;

    public void pushMdZbData(List<MdZbDataEntity> var1, ILEntity var2);

    public List<MdZbDataEntity> extractMdZbData(MdZbDataEntity var1, ILEntity var2, JdbcTemplate var3);

    public List<MdZbDataEntity> extractAllMdZbData(MdZbDataEntity var1, ILEntity var2, JdbcTemplate var3);

    public JdbcTemplate getJdbcTemplate(String var1);

    public void addOrgInfo(List<GcOrgCacheVO> var1, ILCondition var2);

    public void createOrgInfo(ILCondition var1);

    public Map<String, List<String>> getDataRegionKeyMap();
}

