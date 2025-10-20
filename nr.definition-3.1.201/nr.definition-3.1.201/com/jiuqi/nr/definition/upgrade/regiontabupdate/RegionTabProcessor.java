/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.grid2.ReadMemStream2
 *  com.jiuqi.np.grid2.Stream2
 *  com.jiuqi.np.grid2.StreamException2
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.upgrade.regiontabupdate;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class RegionTabProcessor {
    Logger logger = LoggerFactory.getLogger(RegionTabProcessor.class);
    @Autowired
    private DesignBigDataTableDao designBigDataTableDao;
    @Autowired
    private RunTimeBigDataTableDao runTimeBigDataTableDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void updateData() {
        try {
            this.updateDesignData();
            this.updateRuntimeData();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private void updateDesignData() throws Exception {
        List settingDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_REGIONSETTING_DES");
        if (null == settingDefines || settingDefines.size() == 0) {
            return;
        }
        List regionDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_DATAREGION_DES");
        HashMap<String, Map> keyMap = new HashMap<String, Map>();
        for (Map settingDefine : settingDefines) {
            keyMap.put(settingDefine.get("RS_KEY").toString(), settingDefine);
        }
        List bigDataTables = this.designBigDataTableDao.list(DesignBigDataTable.class);
        HashMap bigDataMap = new HashMap();
        for (Object bigDataTable : bigDataTables) {
            if (!"REGION_TAB".equals(((DesignBigDataTable)bigDataTable).getCode())) continue;
            if (null != bigDataMap.get(((DesignBigDataTable)bigDataTable).getKey())) {
                ((List)bigDataMap.get(((DesignBigDataTable)bigDataTable).getKey())).add(bigDataTable);
                continue;
            }
            bigDataMap.put(((DesignBigDataTable)bigDataTable).getKey(), new ArrayList());
            ((List)bigDataMap.get(((DesignBigDataTable)bigDataTable).getKey())).add(bigDataTable);
        }
        ArrayList canUpdate = new ArrayList();
        for (Object regionDefine : regionDefines) {
            Map designRegionSettingDefine;
            if (null == regionDefine.get("DR_REGION_SETTING") || !StringUtils.hasLength(regionDefine.get("DR_REGION_SETTING").toString()) || null == (designRegionSettingDefine = (Map)keyMap.get(regionDefine.get("DR_REGION_SETTING"))) || null == bigDataMap.get(designRegionSettingDefine.get("RS_KEY"))) continue;
            canUpdate.addAll((Collection)bigDataMap.get(designRegionSettingDefine.get("RS_KEY")));
        }
        boolean isUpdate = false;
        if (canUpdate.size() != 0) {
            for (DesignBigDataTable designBigDataTable : canUpdate) {
                List<DesignRegionTabSettingDefine> tabSettingDefines = RegionTabSettingData.bytesToRegionTabSettingData(designBigDataTable.getData());
                if (null == tabSettingDefines) continue;
                for (DesignRegionTabSettingDefine tabSettingDefine : tabSettingDefines) {
                    if (!UUIDUtils.isUUID((String)tabSettingDefine.getId())) continue;
                    isUpdate = true;
                }
            }
            if (isUpdate) {
                return;
            }
            HashSet<String> noRepeat = new HashSet<String>();
            for (DesignBigDataTable designBigDataTable : canUpdate) {
                String setKey = designBigDataTable.getKey().concat("_").concat(designBigDataTable.getCode()).concat("_").concat(designBigDataTable.getLang() + "");
                if (!noRepeat.add(setKey)) continue;
                List<DesignRegionTabSettingDefine> tabSettingDefines = this.bytesToRegionTabSettingData(designBigDataTable.getData());
                ArrayList<RegionTabSettingDefine> ins = new ArrayList<RegionTabSettingDefine>();
                for (DesignRegionTabSettingDefine tabSettingDefine : tabSettingDefines) {
                    if (StringUtils.hasLength(tabSettingDefine.getId())) continue;
                    tabSettingDefine.setId(UUIDUtils.getKey());
                    ins.add(tabSettingDefine);
                }
                if (ins.size() != 0) {
                    byte[] bytes = RegionTabSettingData.regionTabSettingDataToBytes(ins);
                    designBigDataTable.setData(bytes);
                }
                this.designBigDataTableDao.updateData(designBigDataTable);
            }
        }
    }

    private void updateRuntimeData() throws Exception {
        List settingDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_REGIONSETTING");
        if (null == settingDefines || settingDefines.size() == 0) {
            return;
        }
        List regionDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_DATAREGION_DES");
        HashMap<String, Map> keyMap = new HashMap<String, Map>();
        for (Map settingDefine : settingDefines) {
            keyMap.put(settingDefine.get("RS_KEY").toString(), settingDefine);
        }
        List bigDataTables = this.runTimeBigDataTableDao.list(RunTimeBigDataTable.class);
        HashMap bigDataMap = new HashMap();
        for (Object bigDataTable : bigDataTables) {
            if (!"REGION_TAB".equals(bigDataTable.getCode())) continue;
            if (null != bigDataMap.get(bigDataTable.getKey())) {
                ((List)bigDataMap.get(bigDataTable.getKey())).add(bigDataTable);
                continue;
            }
            bigDataMap.put(bigDataTable.getKey(), new ArrayList());
            ((List)bigDataMap.get(bigDataTable.getKey())).add(bigDataTable);
        }
        ArrayList canUpdate = new ArrayList();
        for (Object regionDefine : regionDefines) {
            Map designRegionSettingDefine;
            if (null == regionDefine.get("DR_REGION_SETTING") || !StringUtils.hasLength(regionDefine.get("DR_REGION_SETTING").toString()) || null == (designRegionSettingDefine = (Map)keyMap.get(regionDefine.get("DR_REGION_SETTING"))) || null == bigDataMap.get(designRegionSettingDefine.get("RS_KEY"))) continue;
            canUpdate.addAll((Collection)bigDataMap.get(designRegionSettingDefine.get("RS_KEY")));
        }
        boolean isUpdate = false;
        if (canUpdate.size() != 0) {
            for (BigDataDefine designBigDataTable : canUpdate) {
                List<DesignRegionTabSettingDefine> tabSettingDefines = RegionTabSettingData.bytesToRegionTabSettingData(designBigDataTable.getData());
                if (null == tabSettingDefines) continue;
                for (DesignRegionTabSettingDefine tabSettingDefine : tabSettingDefines) {
                    if (!UUIDUtils.isUUID((String)tabSettingDefine.getId())) continue;
                    isUpdate = true;
                }
            }
            if (isUpdate) {
                return;
            }
            HashSet<String> noRepeat = new HashSet<String>();
            for (BigDataDefine designBigDataTable : canUpdate) {
                String setKey = designBigDataTable.getKey().concat("_").concat(designBigDataTable.getCode()).concat("_").concat(designBigDataTable.getLang() + "");
                if (!noRepeat.add(setKey)) continue;
                List<DesignRegionTabSettingDefine> tabSettingDefines = this.bytesToRegionTabSettingData(designBigDataTable.getData());
                ArrayList<RegionTabSettingDefine> ins = new ArrayList<RegionTabSettingDefine>();
                for (DesignRegionTabSettingDefine tabSettingDefine : tabSettingDefines) {
                    if (StringUtils.hasLength(tabSettingDefine.getId())) continue;
                    tabSettingDefine.setId(UUIDUtils.getKey());
                    ins.add(tabSettingDefine);
                }
                if (ins.size() == 0) continue;
                byte[] bytes = RegionTabSettingData.regionTabSettingDataToBytes(ins);
                RunTimeBigDataTable runTimeBigDataTable = (RunTimeBigDataTable)designBigDataTable;
                runTimeBigDataTable.setData(bytes);
                this.runTimeBigDataTableDao.updateData(runTimeBigDataTable);
            }
        }
    }

    public List<DesignRegionTabSettingDefine> bytesToRegionTabSettingData(byte[] data) {
        if (data == null) {
            return null;
        }
        List<DesignRegionTabSettingDefine> regionTabSettingData = null;
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            regionTabSettingData = this.loadFromStream((Stream2)s);
        }
        catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            regionTabSettingData = null;
        }
        return regionTabSettingData;
    }

    public List<DesignRegionTabSettingDefine> loadFromStream(Stream2 stream) throws StreamException2 {
        ArrayList<DesignRegionTabSettingDefine> regionTabSettingDataList = new ArrayList<DesignRegionTabSettingDefine>();
        int TabSettingrowNum = stream.readInt();
        int countInDB = stream.readInt();
        for (int i = 0; i < countInDB; ++i) {
            RegionTabSettingData regionTabSettingData = new RegionTabSettingData();
            regionTabSettingData.setRowNum(TabSettingrowNum);
            int length = stream.readInt();
            regionTabSettingData.setTitle(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setDisplayCondition(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setFilterCondition(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setBindingExpression(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setOrder(stream.readString(length));
            regionTabSettingDataList.add(regionTabSettingData);
        }
        return regionTabSettingDataList;
    }
}

