package com.lin.controller;

import com.lin.entity.User;
import com.lin.service.ParentChildRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class ParentChildRelationshipController {

    @Autowired
    private ParentChildRelationshipService service;

    /**
     * Endpoint to get all children as user details for a given parent ID.
     *
     * @param parentId the ID of the parent
     * @return list of user details of the children
     */
    @GetMapping("/all-children/{parentId}")
    public List<User> getAllChildrenByParentId(@PathVariable String parentId) {
        return service.getAllChildrenByParentId(parentId);
    }
/**
 * Endpoint to get the number of children for a given parent ID.
 *
 * @param parentId the ID of the parent
 * @return number of children
 */
@GetMapping("/children-count/{parentId}")
public int getNumberOfChildrenByParentId(@PathVariable String parentId) {
    return service.getNumberOfChildrenByParentId(parentId);
}

    /**
     * Endpoint to get all parent IDs.
     *
     * @return list of parent IDs
     */
    @GetMapping("/all-parents")
    public List<String> getAllParentIds() {
        return service.getAllParentIds();
    }
}
