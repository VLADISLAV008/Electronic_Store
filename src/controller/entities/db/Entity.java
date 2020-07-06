package controller.entities.db;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 *
 * @author V.Shramenko
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -5514910940151508631L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

