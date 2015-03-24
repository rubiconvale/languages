package models;

/**
 * Created by RNatarajan on 1/29/2015.
 */
public class LanguageModel {
    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LanguageModel(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
