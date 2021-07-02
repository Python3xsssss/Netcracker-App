package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;

    private final Long userEntityId = 1L;

    public EAVObject createUser(User user) {
        EAVObject eavObj = new EAVObject(metamodelService.getEntityTypeByEntId(userEntityId), user.getUsername());
        List<Parameter> parameters = new ArrayList<>();

        parameters.add(new Parameter()); // todo: parameters addition

        return eavService.createEAVObj(eavObj);
    }

    public List<User> getAllUsers() {
        return null; // todo
    }

    public Optional<User> getUserById(Long userId) {
        return null; // todo
    }

    public void deleteUser(Long userId) {
        // todo
    }
}
