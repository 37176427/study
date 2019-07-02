package com.study.provider.service;

import com.study.provider.eneity.User;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/2 15:29
 **/
public interface UserService {
    void testGet();
    User getUser();
    User getUser(String id);
    User getUser(String id,String name);

    void testPost();
    User postUser(User user);
    User postUser(String id);
}
