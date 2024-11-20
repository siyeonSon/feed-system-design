package com.demo.feedsystemdesign.support.database;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clean() {
        List<String> tableNames = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = SCHEMA()",
                String.class
        );
        truncate(tableNames);
    }

    private void truncate(List<String> tableNames) {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
        for (String tableName : tableNames) {
            jdbcTemplate.update("TRUNCATE TABLE " + tableName);
        }
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
    }

}
