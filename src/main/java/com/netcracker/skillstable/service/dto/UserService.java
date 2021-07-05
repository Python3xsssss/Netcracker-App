package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;


    public Integer createUser(User user) {
       return eavService.createEAVObj(
               UserConverter.dtoToEavObj(
                       user,
                       metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
               )
       ).getId();
    }

    public List<User> getAllUsers() {
        return eavService
                .getAllByEntTypeId(User.getEntTypeId())
                .stream()
                .map(UserConverter::eavObjToDto)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(Integer userId) {

        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(userId);
        if (!optionalEavObj.isPresent() || !User.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject userEavObj = optionalEavObj.get();

        return UserConverter.eavObjToDto(userEavObj);
    }

    public void deleteUser(Integer userId) {
        // todo
    }
}
