package com.study.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.study.provider.eneity.User;
import com.study.provider.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/2 15:31
 **/
@Path("/userService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})//接收的格式
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回的格式
@org.springframework.stereotype.Service("userService")
@Service(interfaceClass = com.study.provider.service.UserService.class, protocol = {"rest", "dubbo"})
public class UserServiceImpl implements UserService {

    @GET
    @Path("/testGet")
    @Override
    public void testGet() {
        System.out.println("测试 test：get");
    }

    @Override
    @GET
    @Path("/getUser")
    public User getUser() {
        User user = new User();
        user.setId("1111");
        user.setName("张三");
        return user;
    }

    @Override
    @GET
    @Path("/get/{id:\\d+}")
    public User getUser(@PathParam("id") String id) {
        System.out.println(id);
        System.out.println("test get...");
        User user = new User();
        user.setId("1002");
        user.setName("li si");
        return user;
    }

    @Override
    @GET
    @Path("/get/{id:\\d+}/{name:[a-zA_Z][0-9]}")
    public User getUser(@PathParam("id") String id, @PathParam("name") String name) {
        System.out.println("id:" + id);
        System.out.println("name:" + name);
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    @Override
    @POST
    @Path("/testPost")
    public void testPost() {
        System.out.println("test post...");
    }

    @Override
    @POST
    @Path("post/{id}")
    public User postUser(@PathParam("id") String id) {
        System.out.println(id);
        User user = new User();
        user.setName("ss");
        user.setId("111");
        return user;
    }

    @Override
    @POST
    @Path("/postUser")
    public User postUser(User user) {
        System.out.println(user.getName());
        User u = new User();
        u.setId("p1");
        u.setName("zs");
        return u;
    }


}
