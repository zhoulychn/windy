package com.zhoulychn.serialize;

import com.zhoulychn.serialize.serializer.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Lewis on 2018/3/26
 */
public class NettyDecoderHandler extends ByteToMessageDecoder {

    //序列化器
    private KryoSerializer serializer = new KryoSerializer();

    private Class<?> clazz;

    public NettyDecoderHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //获取消息头所标识的消息体字节数组长度
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        //若当前可以获取到的字节数小于实际长度,则直接返回,直到当前可以获取到的字节数等于实际长度
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        //读取完整的消息体字节数组
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        //将字节数组反序列化为java对象(SerializerEngine参考序列化与反序列化章节)

        Object result = serializer.deserialize(data,clazz);
        out.add(result);

        System.out.println("收到请求已解析：" + result);
        System.out.println();
    }
}
