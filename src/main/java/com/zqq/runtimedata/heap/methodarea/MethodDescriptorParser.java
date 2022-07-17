package com.zqq.runtimedata.heap.methodarea;

/**
 * 方法描述符解析
 */
public class MethodDescriptorParser {
    //在class文件中原始的描述符
    private String raw;
    //解析raw的当前索引
    private int offset;
    //解析成jvm中的MethodDescriptor
    private MethodDescriptor parsed;

    public static MethodDescriptor parseMethodDescriptorParser(String descriptor) {
        MethodDescriptorParser parser = new MethodDescriptorParser();
        return parser.parse(descriptor);
    }

    public MethodDescriptor parse(String descriptor) {
        this.raw = descriptor;
        this.parsed = new MethodDescriptor();
        //起始标记
        this.startParams();
        //解析参数
        this.parseParamTypes();
        //结束标记
        this.endParams();
        //解析返回值
        this.parseReturnType();
        //判断是否解析成功
        this.finish();
        return this.parsed;
    }

    private void startParams() {
        if (this.readUint8() != '(') {
            causePanic();
        }
    }

    private void endParams() {
        if (this.readUint8() != ')') {
            causePanic();
        }
    }

    public void finish(){
        if (this.offset != this.raw.length()){
            this.causePanic();
        }
    }

    public void causePanic() {
        throw new RuntimeException("BAD descriptor：" + this.raw);
    }

    public byte readUint8() {
        byte[] bytes = this.raw.getBytes();
        byte b = bytes[this.offset];
        this.offset++;
        return b;
    }

    public void unreadUint8() {
        this.offset--;
    }
    //解析参数
    public void parseParamTypes() {
        while (true) {
            String type = this.parseFieldType();
            if ("".equals(type)) break;
            this.parsed.addParameterType(type);
        }
    }
    //解析返回值
    public void parseReturnType() {
        if (this.readUint8() == 'V'){
            this.parsed.returnType = "V";
            return;
        }

        this.unreadUint8();
        String type = this.parseFieldType();
        if (!"".equals(type)){
            this.parsed.returnType = type;
            return;
        }

        this.causePanic();
    }

    public String parseFieldType() {
        switch (this.readUint8()) {
            case 'B'://byte
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'F':
                return "F";
            case 'I':
                return "I";
            case 'J'://long
                return "J";
            case 'S':
                return "S";
            case 'Z'://boolean
                return "Z";
            case 'L'://object
                return this.parseObjectType();
            case '[':
                return this.parseArrayType();
            default://如果产生这种情况,说明参数已经解析完毕了,目前的符号必定是),回退1,调用解析参数结束
                this.unreadUint8();
                return "";
        }
    }
    //最终的返回值是形如:Ljava/lang/String;
    private String parseObjectType() {
        String unread = this.raw.substring(this.offset);
        int semicolonIndx = unread.indexOf(";");
        if (semicolonIndx == -1) {
            this.causePanic();
            return "";
        }
        int objStart = this.offset - 1;
        int ojbEnd = this.offset + semicolonIndx + 1;
        this.offset = ojbEnd;
        //descriptor
        return this.raw.substring(objStart, ojbEnd);
    }
    //解析数组类型
    private String parseArrayType() {
        int arrStart = this.offset - 1;
        this.parseFieldType();
        int arrEnd = this.offset;
        //descriptor
        return this.raw.substring(arrStart, arrEnd);
    }


}
