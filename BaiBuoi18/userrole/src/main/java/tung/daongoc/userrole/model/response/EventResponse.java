package tung.daongoc.userrole.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tung.daongoc.userrole.constant.Event;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {
    private String eventName;
    private String createdDate;
}
