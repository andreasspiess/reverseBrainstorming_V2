package com.example.reverseBrainstorming_V2.RowMapper;

import com.example.reverseBrainstorming_V2.Forms.ProblemForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemRowMapper implements RowMapper<ProblemForm> {

    @Override
    public ProblemForm mapRow(ResultSet rs, int rowNum) throws SQLException {

        ProblemForm result = new ProblemForm();
        result.setId(rs.getInt("id"));
        result.setProblem(rs.getString("problem"));

        return result;
    }
}
