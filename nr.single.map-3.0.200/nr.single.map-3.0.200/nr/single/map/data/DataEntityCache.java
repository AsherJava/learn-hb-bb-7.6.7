/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.map.data;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.facade.SingleFileFieldInfo;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEntityCache {
    private static final Logger logger = LoggerFactory.getLogger(DataEntityCache.class);
    private int singleQYDMIndex = -1;
    private int singleQYDMLen = -1;
    private int singlePeriodIndex = -1;
    private int singlePeriodLen = -1;
    private int singleBBLXIndex;
    private int singleBBLXLen;
    private int singleZdmLen = -1;
    private int singleZdmOutPeriodLen = -1;
    private List<SingleFileFieldInfo> singleZdmFieldList = null;
    private List<SingleFileFieldInfo> singleZdmOtherFieldList = null;
    private List<DataEntityInfo> entityList;
    private Map<String, DataEntityInfo> entityKeyFinder;
    private Map<String, DataEntityInfo> entityZdmFinder;
    private Map<String, DataEntityInfo> entityCodeFinder;
    private Map<String, DataEntityInfo> entityExCodeFinder;
    private Map<String, DataEntityInfo> entityExpCodeFinder;
    private Map<String, DataEntityInfo> entityRuleCodeFinder;
    private Map<String, DataEntityInfo> entityAutoExCodeFinder;
    private Map<String, DataEntityInfo> entityOrgCodeFinder;
    private Map<String, DataEntityInfo> entityZdmAuthFinder;
    private Map<String, DataEntityInfo> insertEntityZdmFinder;
    private Map<String, DataEntityInfo> uploadEntityZdmFinder;
    private Map<String, DataEntityInfo> downloadEntityKeyFinder;
    private List<DataEntityInfo> selectEntityList;
    private List<DataEntityInfo> rootEntitys;
    private Map<String, List<DataEntityInfo>> childrenEntitys;
    private Map<String, List<DataEntityInfo>> notExsitEntitys;

    public void addEntity(DataEntityInfo entityInfo) {
        this.getEntityList().add(entityInfo);
        this.getEntityKeyFinder().put(entityInfo.getEntityKey(), entityInfo);
        this.getEntityZdmFinder().put(entityInfo.getSingleZdm(), entityInfo);
        this.getEntityCodeFinder().put(entityInfo.getEntityCode(), entityInfo);
        this.getEntityRuleCodeFinder().put(entityInfo.getEntityRuleCode(), entityInfo);
        this.getEntityExCodeFinder().put(entityInfo.getEntityExCode(), entityInfo);
        this.getEntityExpCodeFinder().put(entityInfo.getEntityExpCode(), entityInfo);
        this.getEntityAutoExCodeFinder().put(entityInfo.getEntityAutoExCode(), entityInfo);
        if (StringUtils.isNotEmpty((String)entityInfo.getEntityOrgCode())) {
            this.getEntityOrgCodeFinder().put(entityInfo.getEntityOrgCode(), entityInfo);
        }
    }

    public void ClearEntitys() {
        this.getEntityList().clear();
        this.getEntityKeyFinder().clear();
        this.getEntityZdmFinder().clear();
        this.getEntityCodeFinder().clear();
        this.getEntityRuleCodeFinder().clear();
        this.getEntityExCodeFinder().clear();
        this.getEntityAutoExCodeFinder().clear();
    }

    public void clear() {
        this.ClearEntitys();
        this.getRootEntitys().clear();
        this.getChildrenEntitys().clear();
        this.getNotExsitEntitys().clear();
        this.getSelectEntityList().clear();
        this.getInsertEntityZdmFinder().clear();
        this.getDownloadEntityKeyFinder().clear();
        this.getUploadEntityZdmFinder().clear();
        this.getEntityZdmAuthFinder().clear();
    }

    public String getNewZdmByZdm(String oldZdm, String newPriodCode) {
        String zdm = oldZdm;
        if (StringUtils.isNotEmpty((String)oldZdm) && this.singlePeriodIndex >= 0 && this.singlePeriodLen > 0) {
            zdm = oldZdm.substring(0, this.singlePeriodIndex);
            zdm = zdm + newPriodCode;
            zdm = zdm + oldZdm.substring(this.singlePeriodIndex + this.singlePeriodLen, oldZdm.length());
        }
        return zdm;
    }

    public String getNewZdmFromOutPeriod(String oldOutPeriodZdm, String newPriodCode) {
        String zdm = oldOutPeriodZdm;
        if (StringUtils.isNotEmpty((String)oldOutPeriodZdm) && this.singlePeriodIndex >= 0 && this.singlePeriodLen > 0) {
            if (oldOutPeriodZdm.length() >= this.singlePeriodIndex) {
                zdm = oldOutPeriodZdm.substring(0, this.singlePeriodIndex);
                zdm = zdm + newPriodCode;
                zdm = zdm + oldOutPeriodZdm.substring(this.singlePeriodIndex, oldOutPeriodZdm.length());
            } else {
                zdm = oldOutPeriodZdm + newPriodCode;
            }
        }
        return zdm;
    }

    public String getSinglePeriodByZdm(String zdm) {
        String periodCode = "";
        if (StringUtils.isNotEmpty((String)zdm) && this.singlePeriodIndex >= 0 && this.singlePeriodLen > 0 && zdm.length() >= this.singlePeriodIndex + this.singlePeriodLen) {
            periodCode = zdm.substring(this.singlePeriodIndex, this.singlePeriodIndex + this.singlePeriodLen);
        }
        return periodCode;
    }

    public String getSingleZdmOutPeriodByZdm(String zdm) {
        String periodOutCode = zdm;
        if (StringUtils.isNotEmpty((String)zdm) && this.singlePeriodIndex >= 0 && this.singlePeriodLen > 0 && zdm.length() >= this.singlePeriodIndex + this.singlePeriodLen) {
            periodOutCode = zdm.substring(0, this.singlePeriodIndex) + zdm.substring(this.singlePeriodIndex + this.singlePeriodLen, zdm.length());
        }
        return periodOutCode;
    }

    public Map<String, String> getFieldValueByZdmOutPeriod(String oldOutPeriodZdm) {
        LinkedHashMap<String, String> list = new LinkedHashMap<String, String>();
        int pos = 0;
        int len = 0;
        for (SingleFileFieldInfo field : this.getSingleOtherZdmFieldList()) {
            len = field.getFieldSize();
            String fieldValue = "";
            if (oldOutPeriodZdm.length() >= pos + len) {
                fieldValue = oldOutPeriodZdm.substring(pos, pos + len);
            }
            list.put(field.getFieldCode(), fieldValue);
            pos += len;
        }
        return list;
    }

    public Map<String, String> getFieldValueByZdm(String oldZdm) {
        LinkedHashMap<String, String> list = new LinkedHashMap<String, String>();
        int pos = 0;
        int len = 0;
        if (StringUtils.isNotEmpty((String)oldZdm)) {
            for (SingleFileFieldInfo field : this.getSingleZdmFieldList()) {
                len = field.getFieldSize();
                String fieldValue = "";
                if (oldZdm.length() >= pos + len) {
                    fieldValue = oldZdm.substring(pos, pos + len);
                }
                list.put(field.getFieldCode(), fieldValue);
                pos += len;
            }
        }
        return list;
    }

    public DataEntityInfo findEntity(String entityKey) {
        DataEntityInfo entityInfo = this.findEntityByKey(entityKey);
        if (entityInfo == null) {
            entityInfo = this.findEntityByCode(entityKey);
        }
        return entityInfo;
    }

    public DataEntityInfo findEntityByKey(String entityKey) {
        return this.getEntityKeyFinder().get(entityKey);
    }

    public DataEntityInfo findEntityByZdm(String zdm) {
        return this.getEntityZdmFinder().get(zdm);
    }

    public DataEntityInfo findEntityByCode(String entityCode) {
        if (this.getEntityCodeFinder().containsKey(entityCode)) {
            return this.getEntityCodeFinder().get(entityCode);
        }
        return null;
    }

    public DataEntityInfo findEntityByOrgCode(String entityOrgCode) {
        if (this.getEntityOrgCodeFinder().containsKey(entityOrgCode)) {
            return this.getEntityOrgCodeFinder().get(entityOrgCode);
        }
        return null;
    }

    public DataEntityInfo findEntityByExCode(String entityExCode) {
        return this.getEntityExCodeFinder().get(entityExCode);
    }

    public List<DataEntityInfo> getEntityList() {
        if (null == this.entityList) {
            this.entityList = new ArrayList<DataEntityInfo>();
        }
        return this.entityList;
    }

    public void setEntityList(List<DataEntityInfo> entityList) {
        this.entityList = entityList;
    }

    public Map<String, DataEntityInfo> getEntityKeyFinder() {
        if (null == this.entityKeyFinder) {
            this.entityKeyFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityKeyFinder;
    }

    public void setEntityKeyFinder(Map<String, DataEntityInfo> entityKeyFinder) {
        this.entityKeyFinder = entityKeyFinder;
    }

    public Map<String, DataEntityInfo> getEntityZdmFinder() {
        if (null == this.entityZdmFinder) {
            this.entityZdmFinder = new CaseInsensitiveMap<String, DataEntityInfo>();
        }
        return this.entityZdmFinder;
    }

    public void setEntityZdmFinder(Map<String, DataEntityInfo> entityZdmFinder) {
        this.entityZdmFinder = entityZdmFinder;
    }

    public Map<String, DataEntityInfo> getEntityZdmAuthFinder() {
        if (null == this.entityZdmAuthFinder) {
            this.entityZdmAuthFinder = new CaseInsensitiveMap<String, DataEntityInfo>();
        }
        return this.entityZdmAuthFinder;
    }

    public void setEntityZdmAuthFinder(Map<String, DataEntityInfo> entityZdmAuthFinder) {
        this.entityZdmAuthFinder = entityZdmAuthFinder;
    }

    public Map<String, DataEntityInfo> getInsertEntityZdmFinder() {
        if (null == this.insertEntityZdmFinder) {
            this.insertEntityZdmFinder = new CaseInsensitiveMap<String, DataEntityInfo>();
        }
        return this.insertEntityZdmFinder;
    }

    public void setInsertEntityZdmFinder(Map<String, DataEntityInfo> insertEntityZdmFinder) {
        this.insertEntityZdmFinder = insertEntityZdmFinder;
    }

    public Map<String, DataEntityInfo> getUploadEntityZdmFinder() {
        if (null == this.uploadEntityZdmFinder) {
            this.uploadEntityZdmFinder = new CaseInsensitiveMap<String, DataEntityInfo>();
        }
        return this.uploadEntityZdmFinder;
    }

    public void setUploadEntityZdmFinder(Map<String, DataEntityInfo> uploadEntityZdmFinder) {
        this.uploadEntityZdmFinder = uploadEntityZdmFinder;
    }

    public Map<String, DataEntityInfo> getDownloadEntityKeyFinder() {
        if (null == this.downloadEntityKeyFinder) {
            this.downloadEntityKeyFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.downloadEntityKeyFinder;
    }

    public void setDownloadEntityKeyFinder(Map<String, DataEntityInfo> downloadEntityKeyFinder) {
        this.downloadEntityKeyFinder = downloadEntityKeyFinder;
    }

    public Map<String, DataEntityInfo> getEntityCodeFinder() {
        if (null == this.entityCodeFinder) {
            this.entityCodeFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityCodeFinder;
    }

    public void setEntityCodeFinder(Map<String, DataEntityInfo> entityCodeFinder) {
        this.entityCodeFinder = entityCodeFinder;
    }

    public Map<String, DataEntityInfo> getEntityExCodeFinder() {
        if (null == this.entityExCodeFinder) {
            this.entityExCodeFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityExCodeFinder;
    }

    public void setEntityExCodeFinder(Map<String, DataEntityInfo> entityExCodeFinder) {
        this.entityExCodeFinder = entityExCodeFinder;
    }

    public Map<String, DataEntityInfo> getEntityExpCodeFinder() {
        if (null == this.entityExpCodeFinder) {
            this.entityExpCodeFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityExpCodeFinder;
    }

    public void setEntityExpCodeFinder(Map<String, DataEntityInfo> entityExpCodeFinder) {
        this.entityExpCodeFinder = entityExpCodeFinder;
    }

    public int getSinglePeriodIndex() {
        return this.singlePeriodIndex;
    }

    public void setSinglePeriodIndex(int singlePeriodIndex) {
        this.singlePeriodIndex = singlePeriodIndex;
    }

    public int getSinglePeriodLen() {
        return this.singlePeriodLen;
    }

    public void setSinglePeriodLen(int singlePeriodLen) {
        this.singlePeriodLen = singlePeriodLen;
    }

    public int getSingleZdmLen() {
        return this.singleZdmLen;
    }

    public void setSingleZdmLen(int singleZdmLen) {
        this.singleZdmLen = singleZdmLen;
    }

    public int getSingleZdmOutPeriodLen() {
        return this.singleZdmOutPeriodLen;
    }

    public void setSingleZdmOutPeriodLen(int singleZdmOutPeriodLen) {
        this.singleZdmOutPeriodLen = singleZdmOutPeriodLen;
    }

    public List<SingleFileFieldInfo> getSingleZdmFieldList() {
        if (this.singleZdmFieldList == null) {
            this.singleZdmFieldList = new ArrayList<SingleFileFieldInfo>();
        }
        return this.singleZdmFieldList;
    }

    public void setSingleZdmFieldList(List<SingleFileFieldInfo> singleZdmFieldList) {
        this.singleZdmFieldList = singleZdmFieldList;
    }

    public List<SingleFileFieldInfo> getSingleOtherZdmFieldList() {
        if (this.singleZdmOtherFieldList == null) {
            this.singleZdmOtherFieldList = new ArrayList<SingleFileFieldInfo>();
        }
        return this.singleZdmOtherFieldList;
    }

    public void setSingleOtherZdmFieldList(List<SingleFileFieldInfo> singeOtherFieldList) {
        this.singleZdmOtherFieldList = singeOtherFieldList;
    }

    public Map<String, DataEntityInfo> getEntityAutoExCodeFinder() {
        if (this.entityAutoExCodeFinder == null) {
            this.entityAutoExCodeFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityAutoExCodeFinder;
    }

    public void setEntityAutoExCodeFinder(Map<String, DataEntityInfo> entityAutoExCodeFinder) {
        this.entityAutoExCodeFinder = entityAutoExCodeFinder;
    }

    public List<DataEntityInfo> getRootEntitys() {
        if (this.rootEntitys == null) {
            this.rootEntitys = new ArrayList<DataEntityInfo>();
        }
        return this.rootEntitys;
    }

    public void setRootEntitys(List<DataEntityInfo> rootEntitys) {
        this.rootEntitys = rootEntitys;
    }

    public Map<String, List<DataEntityInfo>> getChildrenEntitys() {
        if (this.childrenEntitys == null) {
            this.childrenEntitys = new HashMap<String, List<DataEntityInfo>>();
        }
        return this.childrenEntitys;
    }

    public void setChildrenEntitys(Map<String, List<DataEntityInfo>> childrenEntitys) {
        this.childrenEntitys = childrenEntitys;
    }

    public Map<String, List<DataEntityInfo>> getNotExsitEntitys() {
        if (this.notExsitEntitys == null) {
            this.notExsitEntitys = new HashMap<String, List<DataEntityInfo>>();
        }
        return this.notExsitEntitys;
    }

    public void setNotExsitEntitys(Map<String, List<DataEntityInfo>> notExsitEntitys) {
        this.notExsitEntitys = notExsitEntitys;
    }

    public List<DataEntityInfo> getSelectEntityList() {
        if (this.selectEntityList == null) {
            this.selectEntityList = new ArrayList<DataEntityInfo>();
        }
        return this.selectEntityList;
    }

    public void setSelectEntityList(List<DataEntityInfo> selectEntityList) {
        this.selectEntityList = selectEntityList;
    }

    public void buildNormalTree() {
        this.rootEntitys = new ArrayList<DataEntityInfo>();
        this.childrenEntitys = new HashMap<String, List<DataEntityInfo>>();
        this.notExsitEntitys = new HashMap<String, List<DataEntityInfo>>();
        for (DataEntityInfo entity : this.getEntityList()) {
            String parentKey = entity.getEntityParentKey();
            if (parentKey == null || parentKey.length() <= 0) {
                this.rootEntitys.add(entity);
                continue;
            }
            DataEntityInfo parentEntity = this.getEntityKeyFinder().get(parentKey);
            if (parentEntity == null) {
                parentEntity = this.getEntityCodeFinder().get(parentKey);
            }
            if (parentEntity == null) {
                List<Object> children;
                this.rootEntitys.add(entity);
                if (!this.notExsitEntitys.containsKey(parentKey)) {
                    children = new ArrayList();
                    this.notExsitEntitys.put(parentKey, children);
                } else {
                    children = this.notExsitEntitys.get(parentKey);
                }
                children.add(entity);
                continue;
            }
            if (parentKey.equalsIgnoreCase(entity.getEntityKey())) {
                this.rootEntitys.add(entity);
                logger.error("\u6784\u5efa\u6811\u5f62\uff1a\u4e3b\u4f53[".concat(parentKey).concat("]\u81ea\u8eab\u5b58\u5728\u73af\u94fe\uff0c\u5efa\u8bae\u68c0\u67e5"));
                continue;
            }
            this.addToChildrenEntitys(entity, parentKey);
        }
    }

    public void buildSelectTree() {
        this.rootEntitys = new ArrayList<DataEntityInfo>();
        this.childrenEntitys = new HashMap<String, List<DataEntityInfo>>();
        this.notExsitEntitys = new HashMap<String, List<DataEntityInfo>>();
        HashMap<String, DataEntityInfo> selectEntityKeyFinder = new HashMap<String, DataEntityInfo>();
        HashMap<String, DataEntityInfo> selectEntityCodeFinder = new HashMap<String, DataEntityInfo>();
        for (DataEntityInfo entity : this.getSelectEntityList()) {
            selectEntityKeyFinder.put(entity.getEntityKey(), entity);
            selectEntityCodeFinder.put(entity.getEntityCode(), entity);
        }
        for (DataEntityInfo entity : this.getSelectEntityList()) {
            String parentKey = entity.getEntityParentKey();
            if (parentKey == null || parentKey.length() <= 0) {
                this.rootEntitys.add(entity);
                continue;
            }
            DataEntityInfo parentEntity = (DataEntityInfo)selectEntityKeyFinder.get(parentKey);
            if (parentEntity == null) {
                parentEntity = (DataEntityInfo)selectEntityCodeFinder.get(parentKey);
            }
            if (parentEntity == null) {
                List<Object> children;
                this.rootEntitys.add(entity);
                if (!this.notExsitEntitys.containsKey(parentKey)) {
                    children = new ArrayList();
                    this.notExsitEntitys.put(parentKey, children);
                } else {
                    children = this.notExsitEntitys.get(parentKey);
                }
                children.add(entity);
                continue;
            }
            if (parentKey.equals(entity.getEntityKey())) {
                this.rootEntitys.add(entity);
                logger.error("\u6784\u5efa\u6811\u5f62\uff1a\u4e3b\u4f53[".concat(parentKey).concat("]\u81ea\u8eab\u5b58\u5728\u73af\u94fe\uff0c\u5efa\u8bae\u68c0\u67e5"));
                continue;
            }
            this.addToChildrenEntitys(entity, parentKey);
        }
    }

    public List<DataEntityInfo> getNodesByTree() {
        ArrayList<DataEntityInfo> list = new ArrayList<DataEntityInfo>();
        for (DataEntityInfo entity : this.getRootEntitys()) {
            list.add(entity);
            List<DataEntityInfo> childList = this.getChildrenEntitys().get(entity.getEntityKey());
            this.loadNodesByChilds(list, childList);
        }
        return list;
    }

    private void loadNodesByChilds(List<DataEntityInfo> list, List<DataEntityInfo> childList) {
        if (childList != null && !childList.isEmpty()) {
            for (DataEntityInfo childEntity : childList) {
                list.add(childEntity);
                List<DataEntityInfo> childList2 = this.getChildrenEntitys().get(childEntity.getEntityKey());
                this.loadNodesByChilds(list, childList2);
            }
        }
    }

    private void addToChildrenEntitys(DataEntityInfo entity, String parentKey) {
        List<Object> children;
        if (!this.childrenEntitys.containsKey(parentKey)) {
            children = new ArrayList();
            this.childrenEntitys.put(parentKey, children);
        } else {
            children = this.childrenEntitys.get(parentKey);
        }
        children.add(entity);
    }

    public int getSingleQYDMLen() {
        return this.singleQYDMLen;
    }

    public void setSingleQYDMLen(int singleQYDMLen) {
        this.singleQYDMLen = singleQYDMLen;
    }

    public int getSingleQYDMIndex() {
        return this.singleQYDMIndex;
    }

    public void setSingleQYDMIndex(int singleQYDMIndex) {
        this.singleQYDMIndex = singleQYDMIndex;
    }

    public String getNewZdmByQydm(String oldZdm, String newQydm) {
        String tempNewZdm = "";
        if (this.getSingleQYDMIndex() >= 0 && StringUtils.isNotEmpty((String)oldZdm)) {
            if (this.getSingleQYDMIndex() == 0) {
                tempNewZdm = newQydm + oldZdm.substring(this.getSingleQYDMLen(), oldZdm.length());
            } else if (this.getSingleQYDMIndex() > 0) {
                tempNewZdm = oldZdm.substring(0, this.getSingleQYDMIndex()) + newQydm;
                tempNewZdm = tempNewZdm + oldZdm.substring(this.getSingleQYDMIndex() + this.getSingleQYDMLen(), oldZdm.length());
            }
        } else {
            tempNewZdm = oldZdm;
        }
        return tempNewZdm;
    }

    public Map<String, DataEntityInfo> getEntityRuleCodeFinder() {
        if (this.entityRuleCodeFinder == null) {
            this.entityRuleCodeFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityRuleCodeFinder;
    }

    public void setEntityRuleCodeFinder(Map<String, DataEntityInfo> entityRuleCodeFinder) {
        this.entityRuleCodeFinder = entityRuleCodeFinder;
    }

    public int getSingleBBLXIndex() {
        return this.singleBBLXIndex;
    }

    public void setSingleBBLXIndex(int singleBBLXIndex) {
        this.singleBBLXIndex = singleBBLXIndex;
    }

    public int getSingleBBLXLen() {
        return this.singleBBLXLen;
    }

    public void setSingleBBLXLen(int singleBBLXLen) {
        this.singleBBLXLen = singleBBLXLen;
    }

    public Map<String, DataEntityInfo> getEntityOrgCodeFinder() {
        if (this.entityOrgCodeFinder == null) {
            this.entityOrgCodeFinder = new HashMap<String, DataEntityInfo>();
        }
        return this.entityOrgCodeFinder;
    }

    public void setEntityOrgCodeFinder(Map<String, DataEntityInfo> entityOrgCodeFinder) {
        this.entityOrgCodeFinder = entityOrgCodeFinder;
    }
}

