package com.nikitazlain.uir.entity.container;

import java.util.List;

/**
 * Created by nikitazlain on 23.05.17.
 */

public class AbstractContainer<T> {

    private int count;

    private List<T> container;

    public List<T> getContainer(){
        return container;
    }
}
