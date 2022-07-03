package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private volatile RpcfxResponse rpcfxResponse;

    public ChannelPromise flushMessage(FullHttpRequest request) {
        if (ctx == null)
            throw new IllegalStateException();

        System.out.println("flush flushMessage");
        promise = ctx.writeAndFlush(request).channel().newPromise();
        return promise;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
        System.out.println("已连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse)msg;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            this.rpcfxResponse = JSON.parseObject(result, RpcfxResponse.class);
            this.promise.setSuccess(); //任务完成
        }
    }

    public RpcfxResponse getRpcfxResponse() throws InterruptedException {
        return this.rpcfxResponse;
    }

}
