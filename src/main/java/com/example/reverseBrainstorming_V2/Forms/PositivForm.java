package com.example.reverseBrainstorming_V2.Forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositivForm {

    private Integer id;
    private String positiv;
    private int negativ_id;

    public String getPositiv() {
        return positiv;
    }

    public void setPositiv(String positiv) {
        this.positiv = positiv;
    }
}
