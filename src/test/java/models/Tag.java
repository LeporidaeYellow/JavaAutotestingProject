package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Tag {
    private Long id;
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Tag() {
    }

    /**
     *
     * @param name
     * @param id
     */
    public Tag(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}