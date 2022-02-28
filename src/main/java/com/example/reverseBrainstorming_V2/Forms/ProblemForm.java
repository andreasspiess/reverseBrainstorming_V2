package com.example.reverseBrainstorming_V2.Forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemForm {
    private String problem;
    private long id;

    @Override
    public String toString() {
        return problem;
    }
}
