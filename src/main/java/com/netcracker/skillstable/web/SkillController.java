package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.service.dto.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping
    @PreAuthorize("hasAuthority('skill:create')")
    public ApiResponse<Skill> saveSkill(@RequestBody Skill skill) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill saved successfully.",
                skillService.createSkill(skill)
        );
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<List<Skill>> getAllSkills() {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill list fetched successfully.",
                skillService.getAllSkills()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('skill:get')")
    public ApiResponse<Skill> getSkill(@PathVariable(value = "id") Integer skillId) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill fetched successfully.",
                skillService.getSkillById(skillId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('skill:update')")
    public ApiResponse<Skill> updateSkill(@RequestBody Skill skill) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill updated successfully.",
                skillService.createSkill(skill)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('skill:delete')")
    public ApiResponse<Void> deleteSkill(@PathVariable(value = "id") Integer skillId) {
        skillService.deleteSkill(skillId);
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill deleted successfully.",
                null
        );
    }
}
