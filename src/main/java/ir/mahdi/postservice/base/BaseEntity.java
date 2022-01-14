package ir.mahdi.postservice.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity<ID extends Serializable> implements Serializable {
    private static final String ID = "id";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";
    private static final String IS_DELETED = "IS_DELETED";

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = ID)
    private ID id;

    @Column(name = CREATE_DATE)
    private LocalDateTime createDate;

    @Column(name = IS_DELETED)
    private boolean isDeleted;

    public BaseEntity() {
        createDate = LocalDateTime.now();
    }
}
