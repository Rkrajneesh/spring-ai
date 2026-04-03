package com.org.ai.tool;


import com.org.ai.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ContactsTool {
    private final JdbcTemplate jdbcTemplate;

    public ContactsTool(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Tool(description = "Find contacts in a given city")
    public List<Contact> findContactsByCity(String city) {
        log.info("Finding contacts in city: " + city);
        String sql = "SELECT name, email, city FROM contacts WHERE city = ?";

        RowMapper<Contact> rowMapper = (rs, rowNum) ->
                new Contact(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("city")
                );

        return jdbcTemplate.query(sql, rowMapper, city);
    }
}