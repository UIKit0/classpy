package com.github.zxh.classpy.dexfile.datatype;

import com.github.zxh.classpy.dexfile.DexComponent;
import com.github.zxh.classpy.dexfile.DexReader;

/**
 *
 * @author zxh
 */
public class Hex extends DexComponent {

    private final int n; // number of bytes

    public Hex(int n) {
        this.n = n;
    }
    
    @Override
    protected void readContent(DexReader reader) {
        StringBuilder hex = new StringBuilder("0x");
        for (int i = 0; i < n; i++) {
            hex.append(Integer.toHexString(reader.readUnsignedByte()));
        }
        
        setDesc(hex.toString());
    }
    
}
