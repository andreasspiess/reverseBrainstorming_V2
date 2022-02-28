package com.example.reverseBrainstorming_V2.Forms;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegativForm {

    private int id;
    private String negativ;
    private int negativ_id;



    public String getNegativ() {
        return negativ;
    }

    public void setNegativ(String negativ) {
        this.negativ = negativ;
    }
}
