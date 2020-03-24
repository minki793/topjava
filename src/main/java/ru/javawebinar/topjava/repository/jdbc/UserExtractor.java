package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        Map<User, List<Role>> data = new HashMap<>();
        int row = 0;
        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            User user = JdbcUserRepository.ROW_MAPPER.mapRow(rs, ++row);
            String roleS = rs.getString("role");
            List<Role> roles = data.computeIfAbsent(user, k -> {userList.add(user); return new ArrayList<>();});
            if (roleS != null) {
                Role role = Role.valueOf(roleS);
                roles.add(role);
            }
        }
        for (Map.Entry<User, List<Role>> entry: data.entrySet()) {
            User user = entry.getKey();
            user.setRoles(entry.getValue());
        }
        return userList;
    }


}
