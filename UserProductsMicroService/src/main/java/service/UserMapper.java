package service;

import entity.eUsers;
import entity.eProducts;
import io.r2dbc.spi.Row;

import java.util.function.BiFunction;

public class UserMapper implements BiFunction<Row, Object, eUsers> {

    @Override
    public eUsers apply(Row row, Object o){

    }
}
