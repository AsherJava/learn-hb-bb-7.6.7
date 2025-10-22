/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.map.data;

import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.data.SingleDataEnumInfo;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;

public class DataMapingCache {
    private ISingleMappingConfig mapConfig = null;
    private Map<String, Map<String, SingleFileFieldInfo>> singleFieldMap;
    private Map<String, Map<String, SingleFileFieldInfo>> netFieldMap;
    private Map<String, Map<String, List<SingleFileFieldInfo>>> singleFieldListMap;
    private Map<String, Map<String, List<SingleFileFieldInfo>>> netFieldListMap;
    private Map<String, Map<String, List<SingleFileRegionInfo>>> singleFieldRegionMap;
    private Map<String, SingleFileTableInfo> singleTableMap;
    private Map<String, UnitCustomMapping> unitMap;
    private Map<String, Map<String, String>> unitFieldMap;
    private Map<String, SingleFileTableInfo> mapTables = new HashMap<String, SingleFileTableInfo>();
    private Map<String, SingleDataEnumInfo> mapEnums;

    public Map<String, SingleFileTableInfo> getMapTables() {
        return this.mapTables;
    }

    public void setMapTables(Map<String, SingleFileTableInfo> mapTables) {
        this.mapTables = mapTables;
    }

    public Map<String, Map<String, SingleFileFieldInfo>> getSingleFieldMap() {
        if (this.singleFieldMap == null) {
            this.singleFieldMap = new HashMap<String, Map<String, SingleFileFieldInfo>>();
        }
        return this.singleFieldMap;
    }

    public void setSingleFieldMap(Map<String, Map<String, SingleFileFieldInfo>> fieldMap) {
        this.singleFieldMap = fieldMap;
    }

    public Map<String, Map<String, SingleFileFieldInfo>> getNetFieldMap() {
        if (this.netFieldMap == null) {
            this.netFieldMap = new HashMap<String, Map<String, SingleFileFieldInfo>>();
        }
        return this.netFieldMap;
    }

    public void setNetFieldMap(Map<String, Map<String, SingleFileFieldInfo>> fieldMap) {
        this.netFieldMap = fieldMap;
    }

    public ISingleMappingConfig getMapConfig() {
        return this.mapConfig;
    }

    public SingleFileTableInfo getFmdmInfo() {
        SingleFileTableInfo table = null;
        if (this.mapConfig != null && !"FMDM".equalsIgnoreCase((table = this.mapConfig.getTableInfos().get(0)).getSingleTableCode())) {
            for (SingleFileTableInfo eTable : this.mapConfig.getTableInfos()) {
                if (!"FMDM".equalsIgnoreCase(table.getSingleTableCode())) continue;
                table = eTable;
                break;
            }
        }
        return table;
    }

    public SingleFileFmdmInfo getFmdmInfo2() {
        SingleFileFmdmInfo table2 = null;
        SingleFileTableInfo table = this.getFmdmInfo();
        if (table != null) {
            table2 = (SingleFileFmdmInfo)table;
        }
        return table2;
    }

    public boolean isMapConfig() {
        return this.mapConfig != null;
    }

    public void setMapConfig(ISingleMappingConfig mapConfig) {
        this.mapConfig = mapConfig;
        this.mapTables.clear();
        if (mapConfig != null) {
            for (SingleFileTableInfo tabe : mapConfig.getTableInfos()) {
                this.mapTables.put(tabe.getSingleTableCode(), tabe);
            }
        }
    }

    public int getZdmLength() {
        int len = 10;
        if (null != this.mapConfig) {
            SingleFileFmdmInfo fmTable = (SingleFileFmdmInfo)this.mapConfig.getTableInfos().get(0);
            len = fmTable.getZdmLength();
        }
        if (len <= 0 || len > 1000) {
            len = 10;
        }
        return len;
    }

    public Map<String, SingleFileTableInfo> getSingleTableMap() {
        if (this.singleTableMap == null) {
            this.singleTableMap = new HashMap<String, SingleFileTableInfo>();
        }
        return this.singleTableMap;
    }

    public void setSingleTableMap(Map<String, SingleFileTableInfo> singleTableMap) {
        this.singleTableMap = singleTableMap;
    }

    public Map<String, UnitCustomMapping> getUnitMap() {
        return this.unitMap;
    }

    public void setUnitMap(Map<String, UnitCustomMapping> unitMap) {
        this.unitMap = unitMap;
    }

    public Map<String, Map<String, String>> getUnitFieldMap() {
        return this.unitFieldMap;
    }

    public void setUnitFieldMap(Map<String, Map<String, String>> unitFieldMap) {
        this.unitFieldMap = unitFieldMap;
    }

    public boolean getHasOrderField() {
        return this.getMapConfig() != null && this.getMapConfig().getTaskInfo() != null && StringUtils.isNotEmpty((String)this.getMapConfig().getTaskInfo().getSingleFloatOrderFiled());
    }

    public Map<String, Map<String, List<SingleFileFieldInfo>>> getSingleFieldListMap() {
        return this.singleFieldListMap;
    }

    public void setSingleFieldListMap(Map<String, Map<String, List<SingleFileFieldInfo>>> singleFieldListMap) {
        this.singleFieldListMap = singleFieldListMap;
    }

    public Map<String, Map<String, List<SingleFileFieldInfo>>> getNetFieldListMap() {
        return this.netFieldListMap;
    }

    public void setNetFieldListMap(Map<String, Map<String, List<SingleFileFieldInfo>>> netFieldListMap) {
        this.netFieldListMap = netFieldListMap;
    }

    public Map<String, Map<String, List<SingleFileRegionInfo>>> getSingleFieldRegionMap() {
        if (this.singleFieldRegionMap == null) {
            this.singleFieldRegionMap = new HashMap<String, Map<String, List<SingleFileRegionInfo>>>();
        }
        return this.singleFieldRegionMap;
    }

    public void setSingleFieldRegionMap(Map<String, Map<String, List<SingleFileRegionInfo>>> singleFieldRegionMap) {
        this.singleFieldRegionMap = singleFieldRegionMap;
    }

    public String getEnumNetItemCodeFromItem(String enumFlag, String itemCode) {
        String newFieldValue = null;
        SingleDataEnumInfo singleInfo = this.getSingleEnumInfo(enumFlag);
        if (singleInfo.getSingleMapNetItems().containsKey(itemCode)) {
            newFieldValue = singleInfo.getSingleMapNetItems().get(itemCode);
        }
        return newFieldValue;
    }

    public String getEnumItemCodeFromNetItem(String enumFlag, String netItemCode) {
        String newFieldValue = null;
        SingleDataEnumInfo singleInfo = this.getSingleEnumInfo(enumFlag);
        if (singleInfo.getNetMapSingleItems().containsKey(netItemCode)) {
            newFieldValue = singleInfo.getNetMapSingleItems().get(netItemCode);
        }
        return newFieldValue;
    }

    private SingleDataEnumInfo getSingleEnumInfo(String enumFlag) {
        SingleDataEnumInfo singleInfo = null;
        if (this.getMapEnums().containsKey(enumFlag)) {
            singleInfo = this.getMapEnums().get(enumFlag);
        } else {
            singleInfo = new SingleDataEnumInfo();
            singleInfo.setEnumCode(enumFlag);
            this.getMapEnums().put(enumFlag, singleInfo);
            SingleFileEnumInfo singleEnum = null;
            if (StringUtils.isNotEmpty((String)enumFlag) && this.getMapConfig() != null && this.getMapConfig().getEnumInfos() != null) {
                singleEnum = this.getMapConfig().getEnumInfos().get(enumFlag);
            }
            if (singleEnum != null) {
                for (SingleFileEnumItem item : singleEnum.getEnumItems()) {
                    singleInfo.getSingleMapNetItems().put(item.getItemCode(), item.getNetItemCode());
                    singleInfo.getNetMapSingleItems().put(item.getNetItemCode(), item.getItemCode());
                }
            }
        }
        return singleInfo;
    }

    public Map<String, SingleDataEnumInfo> getMapEnums() {
        if (this.mapEnums == null) {
            this.mapEnums = new HashMap<String, SingleDataEnumInfo>();
        }
        return this.mapEnums;
    }

    public void setMapEnums(Map<String, SingleDataEnumInfo> mapEnums) {
        this.mapEnums = mapEnums;
    }
}

