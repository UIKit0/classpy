package com.github.zxh.classpy.classfile.attribute;

import com.github.zxh.classpy.classfile.ClassComponent;
import com.github.zxh.classpy.common.FileParseException;
import com.github.zxh.classpy.classfile.ClassReader;
import com.github.zxh.classpy.classfile.Table;
import com.github.zxh.classpy.classfile.datatype.U1;
import com.github.zxh.classpy.classfile.datatype.U2;
import com.github.zxh.classpy.classfile.datatype.U2CpIndex;

/*
RuntimeVisibleAnnotations_attribute {
    u2         attribute_name_index;
    u4         attribute_length;
    u2         num_annotations;
    annotation annotations[num_annotations];
}
 */
public class RuntimeVisibleAnnotationsAttribute extends AttributeInfo {

    private U2 numAnnotations;
    private Table<AnnotationInfo> annotations;
    
    @Override
    protected void readInfo(ClassReader reader) {
        numAnnotations = reader.readU2();
        annotations = reader.readTable(AnnotationInfo.class, numAnnotations);
    }
    
    /*
    annotation {
        u2 type_index;
        u2 num_element_value_pairs;
        {   u2            element_name_index;
            element_value value;
        } element_value_pairs[num_element_value_pairs];
    }
    */
    public static class AnnotationInfo extends ClassComponent {
        
        private U2CpIndex typeIndex;
        private U2 numElementValuePairs;
        private Table<ElementValuePair> elementValuePairs;
        
        @Override
        protected void readContent(ClassReader reader) {
            typeIndex = reader.readU2CpIndex();
            numElementValuePairs = reader.readU2();
            elementValuePairs = reader.readTable(ElementValuePair.class,
                    numElementValuePairs);
            setDesc(reader.getConstantPool().getUtf8String(typeIndex));
        }
        
    }
    
    public static class ElementValuePair extends ClassComponent {
        
        private U2CpIndex elementNameIndex;
        private ElementValue value;
        
        @Override
        protected void readContent(ClassReader reader) {
            elementNameIndex = reader.readU2CpIndex();
            value = new ElementValue();
            value.read(reader);
            setDesc(reader.getConstantPool().getUtf8String(elementNameIndex));
        }
        
    }
    
    /*
    element_value {
        u1 tag;
        union {
            u2 const_value_index;

            {   u2 type_name_index;
                u2 const_name_index;
            } enum_const_value;

            u2 class_info_index;

            annotation annotation_value;

            {   u2            num_values;
                element_value values[num_values];
            } array_value;
        } value;
    }
    */
    public static class ElementValue extends ClassComponent {

        private U1 tag;
        
        // tag=B,C,D,F,I,J,S,Z,s
        private U2CpIndex constValueIndex;

        // tag=e
        private EnumConstValue enumConstValue;

        // tag=c
        private U2CpIndex classInfoIndex;

        // tag=@
        private AnnotationInfo annotationValue;

        // tag=[
        private ArrayValue arrayValue;
        
        @Override
        protected void readContent(ClassReader reader) {
            tag = reader.readU1();
            tag.setDesc((char) tag.getValue());
            switch (tag.getValue()) {
                case 'B':
                case 'C':
                case 'D':
                case 'F':
                case 'I':
                case 'J':
                case 'S':
                case 'Z':
                case 's': 
                    constValueIndex = reader.readU2CpIndex();
                    break;
                case 'e': 
                    enumConstValue = new EnumConstValue();
                    enumConstValue.read(reader);
                    break;
                case 'c':
                    classInfoIndex = reader.readU2CpIndex();
                    break;
                case '@':
                    annotationValue = new AnnotationInfo();
                    annotationValue.read(reader);
                    break;
                case '[':
                    arrayValue = new ArrayValue();
                    arrayValue.read(reader);
                    break;
                default: throw new FileParseException("Invalid element_value tag: " + tag.getDesc());
            }
        }
        
    }
    
    public static class EnumConstValue extends ClassComponent {

        private U2CpIndex typeNameIndex;
        private U2CpIndex constNameIndex;
        
        @Override
        protected void readContent(ClassReader reader) {
            typeNameIndex = reader.readU2CpIndex();
            constNameIndex = reader.readU2CpIndex();
        }
        
    }
    
    public static class ArrayValue extends  ClassComponent {
        
        private U2 numValues;
        private Table<ElementValue> values;
        
        @Override
        protected void readContent(ClassReader reader) {
            numValues = reader.readU2();
            values = reader.readTable(ElementValue.class, numValues);
        }
        
    }
    
}
