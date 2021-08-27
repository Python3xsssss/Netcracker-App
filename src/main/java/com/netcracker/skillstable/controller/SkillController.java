package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.service.dto.SkillService;
import com.netcracker.skillstable.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping
    @PreAuthorize("hasAuthority('skill:create')")
    public ResponseEntity<Skill> createSkill(
            @RequestBody @Valid Skill skill,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        return ResponseEntity.ok(skillService.createSkill(skill));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('skill:get')")
    public ResponseEntity<Skill> getSkill(@PathVariable(value = "id") Integer skillId) {
        return ResponseEntity.ok(skillService.getSkillById(skillId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('skill:update')")
    public ResponseEntity<Skill> updateSkill(
            @PathVariable(value = "id") Integer skillId,
            @RequestBody Skill skill,
            BindingResult bindingResult
    ) {
        if (!skillId.equals(skill.getId())) {
            throw new ResourceNotFoundException("Wrong skill id!");
        }
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        return ResponseEntity.ok(skillService.createSkill(skill));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('skill:delete')")
    public ResponseEntity<Void> deleteSkill(@PathVariable(value = "id") Integer skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.ok().build();
    }
}
