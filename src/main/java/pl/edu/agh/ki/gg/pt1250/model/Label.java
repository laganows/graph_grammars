package pl.edu.agh.ki.gg.pt1250.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Label {
    S("S"), I("I"), B("B"), F1("F1"), NONE("");

    @Getter
    private String textLabel;
}
