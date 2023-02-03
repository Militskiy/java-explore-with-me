package ru.practicum.ewm.service.model.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.service.model.category.Category;
import ru.practicum.ewm.service.model.user.User;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@NamedEntityGraph(
        name = "event-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("initiator"),
                @NamedAttributeNode("category")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000, nullable = false)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private int confirmedRequests;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(length = 7000, nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @Embedded
    private Location location;
    @Column(nullable = false)
    private Boolean paid = Boolean.FALSE;
    @Column(nullable = false)
    private Integer participantLimit = 0;
    private LocalDateTime publishedOn;
    @Column(nullable = false)
    private Boolean requestModeration = Boolean.TRUE;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EventState state = EventState.PENDING;
    @Column(length = 120, nullable = false)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
