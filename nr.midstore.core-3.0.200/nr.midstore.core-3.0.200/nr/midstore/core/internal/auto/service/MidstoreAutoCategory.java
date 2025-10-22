/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationCategory
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo
 *  com.jiuqi.nvwa.framework.automation.api.IAutomationCategory
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore.core.internal.auto.service;

import com.jiuqi.nvwa.core.automation.annotation.AutomationCategory;
import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo;
import com.jiuqi.nvwa.framework.automation.api.IAutomationCategory;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeGroupDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeGroupService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationCategory(name="nr-midstore", path="/NR\u62a5\u8868\u4e2d\u95f4\u5e93", title="NR\u62a5\u8868\u4e2d\u95f4\u5e93")
public class MidstoreAutoCategory
implements IAutomationCategory {
    private static final String MIDSTORE_GETATA_PREFIX_NR = "nr-midstore-getdata";
    private static final String MIDSTORE_POSTDATA_PREFIX_NR = "nr-midstore-postdata";
    private static final AutomationInstance INSTANCE = new AutomationInstance();
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreSchemeGroupService schemeGroupSevice;

    public List<AutomationInstance> getChildInstances(String pid) throws AutomationException {
        if (StringUtils.isNotEmpty((CharSequence)pid)) {
            MidstoreSchemeDTO param = new MidstoreSchemeDTO();
            param.setGroupKey(pid);
            List<MidstoreSchemeDTO> definitions = this.midstoreSchemeSevice.list(param);
            return definitions.stream().map(MidstoreAutoCategory::definition2AutomationInfo).collect(Collectors.toList());
        }
        return new ArrayList<AutomationInstance>();
    }

    public List<AutomationFolder> getChildFolders(String pid) throws AutomationException {
        ArrayList<AutomationFolder> list;
        block9: {
            MidstoreSchemeGroupDTO param;
            block8: {
                list = new ArrayList<AutomationFolder>();
                param = new MidstoreSchemeGroupDTO();
                if (!StringUtils.isEmpty((CharSequence)pid)) break block8;
                List<MidstoreSchemeGroupDTO> groups = this.schemeGroupSevice.list(param);
                if (groups == null || groups.size() <= 0) break block9;
                ArrayList<MidstoreSchemeGroupDTO> rootGroups = new ArrayList<MidstoreSchemeGroupDTO>();
                HashMap childGroupMap = new HashMap();
                for (MidstoreSchemeGroupDTO group : groups) {
                    if (StringUtils.isEmpty((CharSequence)group.getParent())) {
                        rootGroups.add(group);
                        continue;
                    }
                    List<MidstoreSchemeGroupDTO> childList = null;
                    if (childGroupMap.containsKey(group.getParent())) {
                        childList = (List)childGroupMap.get(group.getParent());
                    } else {
                        childList = new ArrayList();
                        childGroupMap.put(group.getParent(), childList);
                    }
                    childList.add(group);
                }
                for (MidstoreSchemeGroupDTO group : rootGroups) {
                    if (!childGroupMap.containsKey(group.getKey())) continue;
                    List childGroups = (List)childGroupMap.get(group.getKey());
                    for (MidstoreSchemeGroupDTO childGroup : childGroups) {
                        list.add(MidstoreAutoCategory.group2AutomationFolder(childGroup));
                    }
                }
                break block9;
            }
            param.setParent(pid);
            List<MidstoreSchemeGroupDTO> groups = this.schemeGroupSevice.list(param);
            if (groups != null && groups.size() > 0) {
                for (MidstoreSchemeGroupDTO group : groups) {
                    list.add(MidstoreAutoCategory.group2AutomationFolder(group));
                }
            }
        }
        return list;
    }

    public AutomationInstance getInstance(String guid) throws AutomationException {
        return MidstoreAutoCategory.definition2AutomationInfo(this.midstoreSchemeSevice.getByKey(guid));
    }

    public AutomationFolder getFolder(String guid) throws AutomationException {
        MidstoreSchemeGroupDTO param = new MidstoreSchemeGroupDTO();
        param.setKey(guid);
        List<MidstoreSchemeGroupDTO> list = this.schemeGroupSevice.list(param);
        return MidstoreAutoCategory.group2AutomationFolder(list.get(0));
    }

    private static AutomationInstance definition2AutomationInfo(MidstoreSchemeDTO midstoreScheme) {
        AutomationInstance info = new AutomationInstance();
        info.setGuid(midstoreScheme.getKey());
        info.setName(midstoreScheme.getCode());
        info.setTitle(midstoreScheme.getTitle());
        info.setPid(midstoreScheme.getGroupKey());
        if (midstoreScheme.getExchangeMode() == ExchangeModeType.EXCHANGE_GET) {
            info.setType(MIDSTORE_GETATA_PREFIX_NR);
        } else {
            info.setType(MIDSTORE_POSTDATA_PREFIX_NR);
        }
        return info;
    }

    private static AutomationFolder group2AutomationFolder(MidstoreSchemeGroupDTO node) {
        AutomationFolder info = new AutomationFolder();
        info.setGuid(node.getKey());
        info.setPid(node.getParent());
        info.setTitle(node.getTitle());
        return info;
    }

    public List<AutomationInstance> search(String keyword) throws AutomationException {
        ArrayList<AutomationInstance> list = new ArrayList<AutomationInstance>();
        MidstoreSchemeDTO param = new MidstoreSchemeDTO();
        List<MidstoreSchemeDTO> allList = this.midstoreSchemeSevice.list(param);
        for (MidstoreSchemeDTO scheme : allList) {
            if (scheme.getCode().contains(keyword)) {
                list.add(MidstoreAutoCategory.definition2AutomationInfo(scheme));
                continue;
            }
            if (!scheme.getTitle().contains(keyword)) continue;
            list.add(MidstoreAutoCategory.definition2AutomationInfo(scheme));
        }
        return list;
    }

    public List<String> getInstanceGuidPathList(String guid) throws AutomationException {
        ArrayList<String> pathList = new ArrayList<String>();
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByKey(guid);
        pathList.add(scheme.getKey());
        String parent = scheme.getGroupKey();
        while (StringUtils.isNotEmpty((CharSequence)parent)) {
            MidstoreSchemeGroupDTO param = new MidstoreSchemeGroupDTO();
            param.setKey(parent);
            List<MidstoreSchemeGroupDTO> list = this.schemeGroupSevice.list(param);
            if (list == null || list.size() <= 0) break;
            MidstoreSchemeGroupDTO schemeGroup = list.get(0);
            pathList.add(schemeGroup.getKey());
            parent = schemeGroup.getParent();
        }
        Collections.reverse(pathList);
        return pathList;
    }

    public List<String> getInstancePathList(String guid) throws AutomationException {
        ArrayList<String> pathList = new ArrayList<String>();
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByKey(guid);
        pathList.add(scheme.getTitle());
        String parent = scheme.getGroupKey();
        while (StringUtils.isNotEmpty((CharSequence)parent)) {
            MidstoreSchemeGroupDTO param = new MidstoreSchemeGroupDTO();
            param.setKey(parent);
            List<MidstoreSchemeGroupDTO> list = this.schemeGroupSevice.list(param);
            if (list == null || list.size() <= 0) break;
            MidstoreSchemeGroupDTO schemeGroup = list.get(0);
            pathList.add(schemeGroup.getTitle());
            parent = schemeGroup.getParent();
        }
        Collections.reverse(pathList);
        return pathList;
    }

    public AutomationInstanceAppInfo getInstanceAppInfo(String guid) throws AutomationException {
        return null;
    }

    public AutomationTransferResourceInfo getInstanceTransferResourceInfo(String guid) throws AutomationException {
        return null;
    }

    static {
        INSTANCE.setGuid("midstore-getdata-1");
        INSTANCE.setName("midstore-getdata-1");
        INSTANCE.setTitle("\u4e2d\u95f4\u5e93\u6570\u636e\u63a5\u6536\u81ea\u52a8\u5316\u5bf9\u8c61");
        INSTANCE.setType(MIDSTORE_GETATA_PREFIX_NR);
        INSTANCE.setPid(null);
    }
}

