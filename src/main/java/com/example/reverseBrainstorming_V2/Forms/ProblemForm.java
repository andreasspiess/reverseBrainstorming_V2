package com.example.reverseBrainstorming_V2.Forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemForm {
    private Integer id;
    private String problem;

    @Override
    public String toString() {
        return problem;
    }
}
