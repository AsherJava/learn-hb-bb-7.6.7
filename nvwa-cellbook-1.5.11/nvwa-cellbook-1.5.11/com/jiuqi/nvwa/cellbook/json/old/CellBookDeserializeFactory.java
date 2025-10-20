/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nvwa.cellbook.json.old;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.json.old.CellBookDeserializeV0;
import com.jiuqi.nvwa.cellbook.json.old.OldCellBookDeserialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CellBookDeserializeFactory {
    private static final List<OldCellBookDeserialize> deserializes = new ArrayList<OldCellBookDeserialize>();

    public static void register(OldCellBookDeserialize cellBookDeserialize) {
        deserializes.add(cellBookDeserialize);
    }

    public static boolean isOldVersion(JsonNode node) {
        String version = CellBookDeserializeFactory.getVersion(node);
        for (OldCellBookDeserialize cellBookDeserialize : deserializes) {
            String minVersion = cellBookDeserialize.getMinVersion();
            String maxVersion = cellBookDeserialize.getMaxVersion();
            if (StringUtils.isEmpty(version) && StringUtils.isEmpty(maxVersion) && StringUtils.isEmpty(minVersion)) {
                return true;
            }
            if (CellBookDeserializeFactory.compareVersion(maxVersion, version) < 0 || CellBookDeserializeFactory.compareVersion(version, minVersion) < 0) continue;
            return true;
        }
        return false;
    }

    public static void deserialize(CellBook cellBook, JsonNode node) throws JsonProcessingException, IOException {
        String version = CellBookDeserializeFactory.getVersion(node);
        for (OldCellBookDeserialize cellBookDeserialize : deserializes) {
            String minVersion = cellBookDeserialize.getMinVersion();
            String maxVersion = cellBookDeserialize.getMaxVersion();
            if (StringUtils.isEmpty(version) && StringUtils.isEmpty(maxVersion) && StringUtils.isEmpty(minVersion)) {
                cellBookDeserialize.deserialize(cellBook, node);
                break;
            }
            if (CellBookDeserializeFactory.compareVersion(maxVersion, version) < 0 || CellBookDeserializeFactory.compareVersion(version, minVersion) < 0) continue;
            cellBookDeserialize.deserialize(cellBook, node);
            break;
        }
    }

    private static String getVersion(JsonNode node) {
        JsonNode docProps;
        String version = null;
        if (node.has("docProps") && null != (docProps = node.get("docProps")) && docProps.has("version")) {
            version = docProps.get("version").textValue();
        }
        return version;
    }

    private static int compareVersion(String version1, String version2) {
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        for (int idx = 0; idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0 && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0; ++idx) {
        }
        diff = diff != 0 ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    static {
        CellBookDeserializeFactory.register(new CellBookDeserializeV0());
    }
}

