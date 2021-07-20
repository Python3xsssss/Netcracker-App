package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.service.dto.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<Skill> saveSkill(@RequestBody Skill skill) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill saved successfully.",
                skillService.createSkill(skill)
        );
    }

    @GetMapping
    public ApiResponse<List<Skill>> getAllSkills() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill list fetched successfully.",
                skillService.getAllSkills()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Optional<Skill>> getSkill(@PathVariable(value = "id") Integer skillId) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill fetched successfully.",
                skillService.getSkillById(skillId)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Skill> updateSkill(@RequestBody Skill skill) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill updated successfully.",
                skillService.createSkill(skill)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSkill(@PathVariable(value = "id") Integer skillId) {
        skillService.deleteSkill(skillId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill deleted successfully.",
                null
        );
    }
}
