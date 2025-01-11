package ru.nikita;

import ru.nikita.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        try (
                Connection connection = ConnectionManager.get();
                Statement statement = connection.createStatement()
        ) {
            String sql = """
                    SELECT e.first_name || ' ' || e.last_name FIO, cmp.name Company, c.number || ' ' || t.name Number
                    FROM employee e
                    JOIN company cmp on cmp.id = e.company_id
                    JOIN employee_contacts ec on e.id = ec.employee_id
                    JOIN contacts c on c.id = ec.contacts_id
                    JOIN types t on t.id = c.type;
                    """;
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                System.out.printf("Name: %s\nCompany: %s\nNumber: %s",
                        result.getString("FIO"),
                        result.getString("Company"),
                        result.getString("Number"));
                System.out.println("\n-----------------------");
            }
        }
    }

    public static List<String> getEmployeesByCompany(Long companyId) {
        List<String> employees = new ArrayList<>();
        String query = """
                SELECT first_name || ' ' || last_name fio
                FROM employee
                WHERE company_id = ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, companyId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                employees.add(result.getString("fio"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
