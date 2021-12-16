package tung.daongoc.userrole.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {
    private String eventName;
    private String createdDate;
}
