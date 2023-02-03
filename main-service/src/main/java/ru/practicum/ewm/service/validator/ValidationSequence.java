package ru.practicum.ewm.service.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, SecondOrder.class})
public interface ValidationSequence {
}
