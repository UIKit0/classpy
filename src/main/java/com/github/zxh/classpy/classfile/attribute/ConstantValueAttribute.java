package com.github.zxh.classpy.classfile.attribute;

import com.github.zxh.classpy.classfile.ClassReader;
import com.github.zxh.classpy.classfile.datatype.U2CpIndex;

/*
ConstantValue_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 constantvalue_index;
}
 */
public class ConstantValueAttribute extends AttributeInfo {

    private U2CpIndex constantValueIndex;
    
    @Override
    protected void readInfo(ClassReader reader) {
        constantValueIndex = reader.readU2CpIndex();
    }
    
}
