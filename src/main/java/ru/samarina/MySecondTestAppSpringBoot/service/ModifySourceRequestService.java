package ru.samarina.MySecondTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.samarina.MySecondTestAppSpringBoot.model.Request;

@Service
@Qualifier("ModifySourceRequestService")
public class ModifySourceRequestService implements ModifyRequestService {

    @Override
    public Request modify(Request request) {

        request.setSource("Service 1");

        return request;
    }
}