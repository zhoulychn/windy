package com.zhoulychn.impl;

import com.zhoulychn.BookService;
import org.springframework.stereotype.Component;

/**
 * Created by Lewis on 2018/3/25
 */

@Component
public class BookServiceImpl implements BookService {

    @Override
    public String getName(String name) {
        return name;
    }

}
