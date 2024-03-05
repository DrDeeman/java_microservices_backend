package service;

import entity.eUsers;
import entity.eProducts;
import io.r2dbc.spi.Row;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class UserMapper implements BiFunction<Row, Object, eUsers>
{

    @Override
    public eUsers apply(Row row, Object o){
           eUsers user = new eUsers(
                   row.get("user_name",String.class),
                   row.get("user_login", String.class),
                   row.get("user_password", String.class),
                   row.get("user_email", String.class)
                   );
           user.setId(row.get("user_id", Integer.class));
           eProducts product = new eProducts(
                   row.get("name", String.class),
                   row.get("price", BigDecimal.class),
                   row.get("year_issue", LocalDateTime.class),
                   row.get("raiting", Double.class),
                   row.get("page_image", String.class)
           );
           product.setId(row.get("id", Integer.class));
           user.addProduct(product);
           return user;
    }


}
