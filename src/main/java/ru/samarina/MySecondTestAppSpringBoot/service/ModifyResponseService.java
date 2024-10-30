package ru.samarina.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.samarina.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);

}