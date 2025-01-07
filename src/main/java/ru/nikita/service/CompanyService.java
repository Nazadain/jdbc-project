package ru.nikita.service;

import ru.nikita.dao.CompanyDao;
import ru.nikita.dto.CompanyDto;

import java.util.List;

public final class CompanyService {
    private static CompanyService instance;
    private CompanyDao companyDao = CompanyDao.getInstance();

    public List<CompanyDto> findAll() {
        return companyDao.findAll().stream()
                .map(company ->
                        new CompanyDto(
                                company.getId(),
                                company.getName()
                        ))
                .toList();
    }

    private CompanyService() {
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }
}
