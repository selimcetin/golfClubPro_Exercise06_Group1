/** Copyright (c) 2024. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed for educational purposes only, but WITHOUT
 * ANY WARRANTY; See the GNU General Public License version 3 for more
 * details (a copy is included in the LICENSE file that
 * accompanied this code).
 */
package de.hse.golfclubmanagement.controllers;

import de.hse.golfclubmanagement.models.Member;
import de.hse.golfclubmanagement.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class tests the MemberController class.
 * @author Dennis Grewe
 * @since 0.1
 */
public class MemberControllerTest {
    private String testName = "testname";
    private int handicap = 2;
    @Mock
    private MemberService memberService; // Mocked service for Member

    @InjectMocks
    private MemberController memberController; // Controller instance with mocked service injected

    /**
     * Set up the test environment before each test method.
     * Initializes mocks and prepares the controller for testing.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    /**
     * Test adding a valid Member.
     */
    @Test
    public void testAddValidMember() {
        Member member = new Member();
        member.setName(testName);
        member.setHandicap(handicap);

        // Equivalence class: valid Member
        when(memberService.addMember(any(Member.class))).thenReturn(member);
        ResponseEntity<Member> response = memberController.addMember(member);

        assertEquals(200, response.getStatusCodeValue(), "Response should have status 200 OK");
        assertEquals(member, response.getBody(), "The returned Member should match the saved one");
    }

    /**
     * Test adding a null Member.
     */
    @Test
    public void testAddNullMember() {
        // Equivalence class: null Member
        assertThrows(IllegalArgumentException.class, () -> memberController.addMember(null), "Should throw IllegalArgumentException for null Member");
    }

    /**
     * Test retrieving all Member entities.
     */
    @SuppressWarnings("null")
    @Test
    public void testGetAllMembers() {
        List<Member> courses = new ArrayList<>();
        courses.add(new Member());
        courses.add(new Member());

        // Equivalence class: retrieving all courses
        when(memberService.getAllMembers()).thenReturn(courses);
        ResponseEntity<List<Member>> response = memberController.getAllMembers();

        assertEquals(200, response.getStatusCodeValue(), "Response should have status 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(2, response.getBody().size(), "Should return a list of 2 Members");
    }

    /**
     * Test finding a Member by a valid name.
     */
    @Test
    public void testFindByNameValid() {
        Member member = new Member();
        member.setName(testName);

        // Equivalence class: valid name
        when(memberService.findByName(testName)).thenReturn(member);
        ResponseEntity<Member> response = memberController.findByName(testName);

        assertEquals(200, response.getStatusCodeValue(), "Response should have status 200 OK");
        assertEquals(member, response.getBody(), "The found Member should match the requested name");
    }

    /**
     * Test finding a Member by a non-existent name.
     */
    @Test
    public void testFindByNameNotFound() {
        // Equivalence class: name not found
        when(memberService.findByName("Non-existent Course")).thenReturn(null);
        ResponseEntity<Member> response = memberController.findByName("Non-existent Course");

        assertEquals(404, response.getStatusCodeValue(), "Response should have status 404 Not Found");
        assertNull(response.getBody(), "The response body should be null for a non-existent course");
    }
}
