package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Store {

    private Long id;
    private Long petId;
    private Long quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    /**
     * No args constructor for use in serialization
     *
     */
    public Store() {
    }

    /**
     *
     * @param petId
     * @param quantity
     * @param id
     * @param shipDate
     * @param complete
     * @param status
     */
    public Store(Long id, Long petId, Long quantity, String shipDate, String status, Boolean complete) {
        super();
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }
}