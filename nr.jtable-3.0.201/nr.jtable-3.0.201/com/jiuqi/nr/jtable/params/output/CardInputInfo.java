/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.file.FileInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.base.LinkSimpleData;
import com.jiuqi.nr.jtable.params.output.CardRowData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="CardInputInfo", description="\u5361\u7247\u5f55\u5165\u7684\u521d\u59cb\u5316\u8fd4\u56de\u4fe1\u606f")
public class CardInputInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u94fe\u63a5\u5217\u8868", name="links")
    private List<LinkSimpleData> links = new ArrayList<LinkSimpleData>();
    private RecordCard cardRecord;
    private Boolean canInsertRow;
    private Boolean canDeleteRow;
    private CardRowData cardRowData = new CardRowData();
    private Map<String, List<FileInfo>> fileDataMap = new HashMap<String, List<FileInfo>>();
    private Map<String, List<byte[]>> imgDataMap = new HashMap<String, List<byte[]>>();
    private Map<String, EntityReturnInfo> entityDataMap = new HashMap<String, EntityReturnInfo>();
    private List<String> calcDataLinks;

    public Map<String, List<FileInfo>> getFileDataMap() {
        return this.fileDataMap;
    }

    public void setFileDataMap(Map<String, List<FileInfo>> fileDataMap) {
        this.fileDataMap = fileDataMap;
    }

    public Map<String, List<byte[]>> getImgDataMap() {
        return this.imgDataMap;
    }

    public void setImgDataMap(Map<String, List<byte[]>> imgDataMap) {
        this.imgDataMap = imgDataMap;
    }

    public List<LinkSimpleData> getLinks() {
        return this.links;
    }

    public void setLinks(List<LinkSimpleData> links) {
        this.links = links;
    }

    public CardRowData getCardRowData() {
        return this.cardRowData;
    }

    public void setCardRowData(CardRowData cardRowData) {
        this.cardRowData = cardRowData;
    }

    public RecordCard getCardRecord() {
        return this.cardRecord;
    }

    public void setCardRecord(RecordCard cardRecord) {
        this.cardRecord = cardRecord;
    }

    public Map<String, EntityReturnInfo> getEntityDataMap() {
        return this.entityDataMap;
    }

    public void setEntityDataMap(Map<String, EntityReturnInfo> entityDataMap) {
        this.entityDataMap = entityDataMap;
    }

    public List<String> getCalcDataLinks() {
        return this.calcDataLinks;
    }

    public void setCalcDataLinks(List<String> calcDataLinks) {
        this.calcDataLinks = calcDataLinks;
    }

    public Boolean isCanInsertRow() {
        return this.canInsertRow;
    }

    public void setCanInsertRow(Boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    public Boolean isCanDeleteRow() {
        return this.canDeleteRow;
    }

    public void setCanDeleteRow(Boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }
}

