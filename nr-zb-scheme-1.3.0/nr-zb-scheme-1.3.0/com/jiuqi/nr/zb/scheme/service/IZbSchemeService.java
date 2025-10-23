/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.common.Move;
import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.PropLink;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeVersionDTO;
import com.jiuqi.nr.zb.scheme.web.vo.MoveParam;
import com.jiuqi.nr.zb.scheme.web.vo.PageVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbInfoVO;
import java.util.List;

public interface IZbSchemeService {
    public boolean enableAnalyseZb();

    public boolean isExistData(NodeType var1, String var2, String var3);

    public void insertPropInfo(PropInfo var1);

    public void insertPropInfo(List<PropInfo> var1);

    public void updatePropInfo(PropInfo var1);

    public void updatePropInfo(List<PropInfo> var1);

    public void deletePropInfo(String var1);

    public void deletePropInfo(List<String> var1);

    public PropInfo getPropInfo(String var1);

    public List<PropInfo> listAllPropInfo();

    public List<PropInfo> listPropInfoLinkedByScheme(String var1);

    public PropInfo getLinkedPropInfo(String var1);

    public void moveZbProp(Move var1, String var2);

    public List<ZbSchemeGroup> listAllZbSchemeGroup();

    public String insertZbSchemeGroup(ZbSchemeGroup var1);

    public void insertZbSchemeGroup(List<ZbSchemeGroup> var1);

    public void updateZbSchemeGroup(ZbSchemeGroup var1);

    public void updateZbSchemeGroup(List<ZbSchemeGroup> var1);

    public void deleteZbSchemeGroup(String var1);

    public ZbSchemeGroup getZbSchemeGroup(String var1);

    public List<ZbSchemeGroup> listZbSchemeGroupByParent(String var1);

    public void insertZbScheme(ZbSchemeDTO var1);

    public String insertZbSchemeAndVersion(ZbSchemeDTO var1);

    public void insertZbScheme(List<ZbSchemeDTO> var1);

    public void updateZbScheme(ZbSchemeDTO var1);

    public void deleteZbScheme(String var1);

    public ZbScheme getZbScheme(String var1);

    public List<ZbScheme> listZbSchemeByParent(String var1);

    public List<ZbScheme> listAllZbScheme();

    public void saveZbSchemeProp(String var1, List<PropLink> var2);

    public int countZbByVersion(String var1);

    public int countZbByScheme(String var1);

    public void insertZbSchemeVersion(ZbSchemeVersionDTO var1);

    public void updateZbSchemeVersion(ZbSchemeVersion var1);

    public void deleteZbSchemeVersion(String var1);

    public ZbSchemeVersion getZbSchemeVersion(String var1);

    public List<ZbSchemeVersion> listZbSchemeVersionByScheme(String var1);

    public ZbSchemeVersion getZbSchemeVersion(String var1, String var2);

    public ZbSchemeVersion getZbSchemeVersionByKey(String var1);

    public void publishVersion(String var1, String var2, VersionStatus var3);

    public int getReferVersionNum(String var1);

    default public boolean hasReferVersion(String versionKey) {
        return this.getReferVersionNum(versionKey) > 0;
    }

    public ZbGroup initZbGroup();

    public void insertZbGroup(ZbGroup var1);

    public void insertZbGroup(List<ZbGroup> var1);

    public void updateZbGroup(ZbGroup var1);

    public void updateZbGroup(List<ZbGroup> var1);

    public ZbGroup getZbGroup(String var1);

    public List<ZbGroup> listZbGroupBySchemeAndPeriod(String var1, String var2);

    public List<ZbGroup> listZbGroupByVersion(String var1);

    public List<ZbGroup> listZbGroupByParent(String var1);

    public List<ZbGroup> listZbGroupByVersionAndParent(String var1, String var2);

    public void deleteZbGroup(String var1);

    public void deleteZbGroup(List<String> var1);

    public void deleteZbGroupByVersion(String var1);

    public ZbInfo initZbInfo();

    public ZbInfo getZbInfo(String var1);

    public ZbInfo getZbInfoByVersionAndCode(String var1, String var2);

    public List<ZbInfo> listZbInfoBySchemeAndVersion(String var1, String var2);

    public List<ZbInfo> listZbInfoByParent(String var1);

    public List<ZbInfo> listZbInfoByKeys(List<String> var1);

    public List<ZbInfo> listZbInfoByVersion(String var1);

    public List<ZbInfo> listZbInfoByVersionAndCode(String var1, List<String> var2);

    public List<ZbInfo> listZbInfoBySchemeAndCode(String var1, String var2);

    public Integer insertZbInfo(ZbInfo var1);

    public void insertZbInfo(List<ZbInfo> var1);

    public String insertZbInfo(ZbInfoDTO var1, boolean var2);

    public void deleteZbInfo(String var1);

    public Integer deleteZbInfo(List<String> var1);

    public void deleteZbInfoByVersion(String var1);

    public void updateZbInfo(ZbInfo var1);

    public void updateZbInfo(List<ZbInfo> var1);

    public PageVO<ZbInfoVO> moveZbInfo(MoveParam var1);

    public Boolean isInOldVersion(String var1, String var2, String var3);

    public void moveZbScheme(String var1, Move var2);

    public void moveZbGroup(String var1, String var2, Move var3);

    public void moveZbToGroup(List<String> var1, String var2);

    public void clearEmptyGroup(String var1);
}

