package com.study.distruptor.netty;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.omg.PortableInterceptor.RequestInfo;

import java.util.HashMap;

/**
 * 描述 ：心跳检测服务端处理器
 * 作者 ：WYH
 * 时间 ：2019/1/8 17:06
 **/
public class ServeHeartBeatHandler extends ChannelHandlerAdapter {
    private static HashMap<String,String> AUTH_IP_MAP = new HashMap<>();
    private static final String SUCCESS_KEY = "auth_success_key";
    static {
        AUTH_IP_MAP.put("192.168.1.200","1234");
    }

    private boolean auth(ChannelHandlerContext ctx, Object msg){

        String[] ret = ((String)msg).split(",");
        String auth = AUTH_IP_MAP.get(ret[0]);
        if (auth!=null && auth.equals(ret[1])){
            ctx.writeAndFlush(SUCCESS_KEY);
            return true;
        }else {
            ctx.writeAndFlush("auth failure !").addListener(ChannelFutureListener.CLOSE);
            return false;
        }
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        if(msg instanceof String){
            auth(ctx,msg);
        }else if(msg instanceof RequestInfo){

        }
    }
}
