package ru.practicum.ewm.stats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hit {
    @Id
    private String id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
