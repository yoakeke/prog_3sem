package ru.samarina.MySecondTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.samarina.MySecondTestAppSpringBoot.model.Request;

@Service
@Qualifier("ModifySystemNameRequestService")
public class ModifySystemNameRequestService implements ModifyRequestService {

    @Override
    public Request modify(Request request) {

        request.setSystemName("Service 1");

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange(
                "http://localhost:8083/lr4/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {});

        return request;
    }
}