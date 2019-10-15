package com.example.piyal.classrutine.batabase.model;

/**
 * Created by piyal on 4/10/2019.
 */
public class UpdateKeyStorer {
    private int key;
    private String updateKey;

    public UpdateKeyStorer(int key, String updateKey) {
        this.key = key;
        this.updateKey = updateKey;
    }

    public UpdateKeyStorer(String updateKey) {
        this.updateKey = updateKey;
    }

    public UpdateKeyStorer() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getUpdateKey() {
        return updateKey;
    }

    public void setUpdateKey(String updateKey) {
        this.updateKey = updateKey;
    }
}
