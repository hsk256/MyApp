package com.creativeboy.myapp.view;

import com.creativeboy.myapp.model.bean.Joke;

import java.util.List;

/**
 * Created by heshaokang on 2015/9/11.
 */
public interface JokeView extends BaseView{
    void setData(List<Joke> data);
}
