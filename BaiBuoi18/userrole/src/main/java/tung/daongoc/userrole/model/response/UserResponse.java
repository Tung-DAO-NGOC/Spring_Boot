package tung.daongoc.userrole.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tung.daongoc.userrole.constant.Role;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String fullName;
    private String email;
    private List<Role> roleList;
    private List<EventResponse> eventList;
}
