/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.common.consts;

import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaDataConst {
    public static final String REG_ONE = "&";
    public static final String REG_TWO = "&define";
    public static final String DEPLOY_GROUP = "group";
    public static final String DEPLOY_METADATA = "metaData";
    public static final String KEY_METATYPE = "metaType";
    public static final String KEY_METADATAUNIQUECODE = "metaDataUniqueCode";
    public static final String KEY_MODULENAME = "moduleName";
    public static final String KEY_METAGROUPUNIQUECODE = "metaGroupUniqueCode";
    public static final String L_BILL = "bill";
    public static final String BILLLIST_CODE = "_L_";
    public static final String BILL_CODE = "_B_";
    public static final String WORKFLOW_CODE = "_W_";

    public static Map<String, String> getModuleMap() {
        List<ModuleServer> moduleServer = Modules.getModules();
        Map<String, String> multiModuleMap = moduleServer.stream().collect(Collectors.toMap(ModuleServer::getName, ModuleServer::getTitle));
        return multiModuleMap;
    }
}

