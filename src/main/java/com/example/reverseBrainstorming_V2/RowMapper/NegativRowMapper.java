package com.example.reverseBrainstorming_V2.RowMapper;

import com.example.reverseBrainstorming_V2.Forms.NegativForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NegativRowMapper implements RowMapper <NegativForm> {

    @Override
    public NegativForm mapRow(ResultSet rs, int rowNum) throws SQLException {
        NegativForm result = new NegativForm();
        result.setId(rs.getInt("id"));
        result.setNegativ(rs.getString("negativ"));
        result.setProblem_id(rs.getInt("negativ_id"));

        return result;
    }
}
