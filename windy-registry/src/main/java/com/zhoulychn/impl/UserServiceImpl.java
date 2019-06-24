package com.zhoulychn.impl;

import com.zhoulychn.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by Lewis on 2018/3/25
 */

@Component
public class UserServiceImpl implements UserService {

    @Override
    public String getName(String name) {
        return "hello" + name;
    }

}
