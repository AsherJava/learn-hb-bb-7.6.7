/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.bean.JioImportFileNode;

public class JioImportParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<JioImportFileNode> fileNodes;

    public List<JioImportFileNode> getFileNodes() {
        if (this.fileNodes == null) {
            this.fileNodes = new ArrayList<JioImportFileNode>();
        }
        return this.fileNodes;
    }

    public void setFileNodes(List<JioImportFileNode> fileNodes) {
        this.fileNodes = fileNodes;
    }
}

