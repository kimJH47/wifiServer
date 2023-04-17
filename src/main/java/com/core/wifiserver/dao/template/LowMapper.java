package com.core.wifiserver.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LowMapper<T> {

    T mapRow(ResultSet resultSet) throws SQLException;

}
