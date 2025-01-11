package ru.nikita.service;

import lombok.NoArgsConstructor;
import ru.nikita.dao.CompanyDao;
import ru.nikita.dto.CompanyDto;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CompanyService {
    private static CompanyService instance;
    private final CompanyDao companyDao = CompanyDao.getInstance();

    public List<CompanyDto> findAll() {
        return companyDao.findAll().stream()
                .map(company ->
                        new CompanyDto(
                                company.getId(),
                                company.getName()
                        ))
                .toList();
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }
}
