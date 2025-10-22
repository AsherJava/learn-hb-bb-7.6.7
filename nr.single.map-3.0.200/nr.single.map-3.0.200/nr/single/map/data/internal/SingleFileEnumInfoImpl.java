/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.internal.SingleFileEnumItemImpl;

public class SingleFileEnumInfoImpl
implements SingleFileEnumInfo {
    private static final long serialVersionUID = -5988318471092773220L;
    private String enumCode;
    private String enumTitle;
    private String netTableCode;
    private String netTableKey;
    private List<SingleFileEnumItem> enumItems = new ArrayList<SingleFileEnumItem>();

    @Override
    public String getEnumCode() {
        return this.enumCode;
    }

    @Override
    public String getEnumTitle() {
        return this.enumTitle;
    }

    @Override
    public String getNetTableCode() {
        return this.netTableCode;
    }

    @Override
    public String getNetTableKey() {
        return this.netTableKey;
    }

    @Override
    public List<SingleFileEnumItem> getEnumItems() {
        return this.enumItems;
    }

    @Override
    public void setEnumItems(List<SingleFileEnumItem> enumItems) {
        this.enumItems = enumItems;
    }

    @Override
    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    @Override
    public void setEnumTitle(String enumTitle) {
        this.enumTitle = enumTitle;
    }

    @Override
    public void setNetTableCode(String tableCode) {
        this.netTableCode = tableCode;
    }

    @Override
    public void setNetTableKey(String tableKey) {
        this.netTableKey = tableKey;
    }

    @Override
    @JsonIgnore
    public SingleFileEnumItem getNewEnumItem() {
        return new SingleFileEnumItemImpl();
    }

    @Override
    @JsonIgnore
    public String getItemCodeFromNet(String netItemCode) {
        List findList;
        String r = "";
        if (StringUtils.isNotEmpty((String)netItemCode) && !(findList = this.enumItems.stream().filter(item -> netItemCode.equalsIgnoreCase(item.getNetItemCode())).collect(Collectors.toList())).isEmpty()) {
            r = ((SingleFileEnumItem)findList.get(0)).getItemCode();
        }
        return r;
    }

    @Override
    @JsonIgnore
    public String getNetItemCodeFromItem(String itemCode) {
        List findList;
        String r = "";
        if (StringUtils.isNotEmpty((String)itemCode) && !(findList = this.enumItems.stream().filter(item -> itemCode.equalsIgnoreCase(item.getItemCode())).collect(Collectors.toList())).isEmpty()) {
            r = ((SingleFileEnumItem)findList.get(0)).getNetItemCode();
        }
        return r;
    }

    @Override
    public void copyFrom(SingleFileEnumInfo info) {
        this.enumCode = info.getEnumCode();
        this.enumTitle = info.getEnumTitle();
        this.netTableCode = info.getNetTableCode();
        this.netTableKey = info.getNetTableKey();
        this.enumItems.clear();
        for (SingleFileEnumItem item : info.getEnumItems()) {
            SingleFileEnumItemImpl itemNew = new SingleFileEnumItemImpl();
            itemNew.copyFrom(item);
            this.enumItems.add(itemNew);
        }
    }
}

