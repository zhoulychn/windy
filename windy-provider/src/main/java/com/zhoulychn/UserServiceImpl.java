package com.zhoulychn;


/**
 * Created by Lewis on 2018/3/25
 */

public class UserServiceImpl implements UserService {

    @Override
    public String getName(String name) {
        return "hello" + name;
    }

}
