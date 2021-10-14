package tung.daongoc.employeemanagement.service;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Service
public class SearchRequest {
    private String field;
    private String keyword;
    private String order;
}
