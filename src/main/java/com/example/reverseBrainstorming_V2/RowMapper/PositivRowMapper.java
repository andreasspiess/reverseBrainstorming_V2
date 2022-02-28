package com.example.reverseBrainstorming_V2.RowMapper;

import com.example.reverseBrainstorming_V2.Forms.PositivForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PositivRowMapper implements RowMapper <PositivForm> {

    @Override
    public PositivForm mapRow(ResultSet rs, int rowNum) throws SQLException {
        PositivForm result = new PositivForm();
        result.setId(rs.getInt("id"));
        result.setPositiv(rs.getString("description"));
        result.setPositiv_id(rs.getInt("problem_id"));

        return result;
    }
}
