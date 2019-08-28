package com.zhoulychn.serialize;

import com.zhoulychn.serialize.serializer.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Lewis on 2018/3/26
 */
public class NettyEncoderHandler extends MessageToByteEncoder {

    //序列化类型
    private KryoSerializer serializer = new KryoSerializer();

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //将对象序列化为字节数组
        byte[] data = serializer.serialize(msg);

        //将字节数组(消息体)的长度作为消息头写入,解决半包/粘包问题

        out.writeInt(data.length);

        //写入序列化后得到的字节数组

        out.writeBytes(data);

        System.out.println("编码成功，返回结果");
        System.out.println();
    }

}
