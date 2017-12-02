package com.baseclasses;

import com.rocket.database.DBWorker;
import org.junit.Test;

import static org.junit.Assert.*;

public class RocketClassTest {

    @Test
    public void testDb(){
        DBWorker dbWorker = new DBWorker();

        assertTrue(true);
    }

}