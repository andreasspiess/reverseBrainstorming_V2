package com.example.reverseBrainstorming_V2.Forms;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegativForm {

    private Integer id;
    private String negativ;
    private int problem_id;



    public String getNegativ() {
        return negativ;
    }

    public void setNegativ(String negativ) {
        this.negativ = negativ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }
}
