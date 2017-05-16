package net.hashcoding.samplerpc.client.base;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.hashcoding.samplerpc.client.ResponseMapHelper;
import net.hashcoding.samplerpc.common.Command;
import net.hashcoding.samplerpc.common.Promise;
import net.hashcoding.samplerpc.common.Response;
import net.hashcoding.samplerpc.common.utils.LogUtils;

/**
 * Created by MaoChuan on 2017/5/13.
 */
public class DefaultClientHandler extends SimpleChannelInboundHandler<Command> {
    private static final String TAG = "DefaultClientHandler";

    protected void channelRead0(ChannelHandlerContext context, Command command) throws Exception {
        switch (command.getType()) {
            case Command.INVOKE_RESPONSE:
                long requestId = command.getRequestId();
                Promise<Response> promise =
                        ResponseMapHelper.responses.get(requestId);
                Response response = command.factoryFromBody(); //Response.factory(command.getBody());
                promise.setValue(response);
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        LogUtils.e(TAG, cause);
        cause.printStackTrace();
        ctx.close();
    }
}