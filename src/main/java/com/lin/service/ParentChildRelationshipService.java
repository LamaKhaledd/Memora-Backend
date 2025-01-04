package com.lin.service;

import com.lin.entity.ParentChildRelationship;
import com.lin.entity.User;
import com.lin.repository.ParentChildRelationshipRepository;
import com.lin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParentChildRelationshipService {

    @Autowired
    private ParentChildRelationshipRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllChildrenByParentId(String parentId) {
        Optional<ParentChildRelationship> relationship = repository.findByParentId(parentId);

        if (relationship.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> childIds = relationship.get().getChildIds();

        return childIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public int getNumberOfChildrenByParentId(String parentId) {
        Optional<ParentChildRelationship> relationship = repository.findByParentId(parentId);

        return relationship.map(r -> r.getChildIds().size()).orElse(0);
    }

    public List<String> getAllParentIds() {
        return repository.findAll()
                .stream()
                .map(ParentChildRelationship::getParentId)
                .collect(Collectors.toList());
    }
}
