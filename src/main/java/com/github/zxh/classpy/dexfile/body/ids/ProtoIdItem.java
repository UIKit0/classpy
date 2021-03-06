package com.github.zxh.classpy.dexfile.body.ids;

import com.github.zxh.classpy.dexfile.DexComponent;
import com.github.zxh.classpy.dexfile.DexFile;
import com.github.zxh.classpy.dexfile.DexReader;
import com.github.zxh.classpy.dexfile.datatype.UIntHex;
import com.github.zxh.classpy.dexfile.datatype.UIntStringIdIndex;
import com.github.zxh.classpy.dexfile.datatype.UIntTypeIdIndex;

/**
 *
 * @author zxh
 */
public class ProtoIdItem extends DexComponent {

    private UIntStringIdIndex shortyIdx;
    private UIntTypeIdIndex returnTypeIdx;
    private UIntHex parametersOff; // todo type_list

    public UIntStringIdIndex getShortyIdx() {return shortyIdx;}
    public UIntHex getParametersOff() {return parametersOff;}

    @Override
    protected void readContent(DexReader reader) {
        shortyIdx = reader.readUIntStringIdIndex();
        returnTypeIdx = reader.readUIntTypeIdIndex();
        parametersOff = reader.readUIntHex();
    }

    @Override
    protected void postRead(DexFile dexFile) {
        super.postRead(dexFile);
        setDesc(dexFile.getString(shortyIdx));
    }
    
}
