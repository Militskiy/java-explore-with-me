package ru.practicum.ewm.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("hits")
public class Hit {
    @Id
    @Column("hit_id")
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @Column("created")
    private LocalDateTime timestamp = LocalDateTime.now();
}
