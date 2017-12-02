package com.rocket.database;

import com.baseclasses.ResultClass;

import java.util.List;

public interface Dao {
    List<ResultClass> listAll() throws Exception;
    void createResultClass(ResultClass resultClass) throws Exception;
    void updateResultClass(ResultClass resultClass) throws Exception;
}
