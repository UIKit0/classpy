package com.github.zxh.classpy.dexfile.datatype;

import com.github.zxh.classpy.dexfile.DexFile;

/**
 *
 * @author zxh
 */
public class Uleb128StringIdIndex extends Uleb128 {

    @Override
    protected void postRead(DexFile dexFile) {
        int index = getValue();
        if (index >= 0) {
            setDesc(index + "->" + dexFile.getString(index));
        }
    }
    
}
