package com.example.reverseBrainstorming_V2.Forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositivForm {

    private int id;
    private String positiv;
    private int positiv_id;

    public String getPositiv() {
        return positiv;
    }

    public void setPositiv(String positiv) {
        this.positiv = positiv;
    }
}
