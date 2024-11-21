package com.example.progetto_ingegneria_software.ui.map;

public class Item {
    private String label;
    private boolean isChecked;

    public Item(String label, boolean isChecked) {
        this.label = label;
        this.isChecked = isChecked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
