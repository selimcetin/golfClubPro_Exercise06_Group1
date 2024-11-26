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
package de.hse.golfclubmanagement.services;

import de.hse.golfclubmanagement.models.Member;
import de.hse.golfclubmanagement.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class tests the MemberService class.
 * @author Dennis Grewe
 * @since 0.1
 */
public class MemberServiceTest {

    private String testName = "testName";
    private int handicap = 2;

    @Mock
    private MemberRepository memberRepository; // Mocked repository for Member

    @InjectMocks
    private MemberService memberService; // Service instance with mocked repository injected

    /**
     * Set up the test environment before each test method.
     * Initializes mocks and prepares the service for testing.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    /**
     * Test saving a valid Member.
     */
    @Test
    public void testSaveValidMember() {
        Member member = new Member();
        member.setName(testName);
        member.setHandicap(handicap);

        // Equivalence class: valid Member
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Member savedMember = memberService.addMember(member);
        assertEquals(testName, savedMember.getName(), "The saved course name should match");
        assertEquals(handicap, savedMember.getHandicap(), "The saved course location should match");
    }

    /**
     * Test saving a null Member.
     */
    @Test
    public void testSaveNullMember() {
        // Equivalence class: null Member
        when(memberRepository.save(null)).thenThrow(new IllegalArgumentException("Member cannot be null"));
        assertThrows(IllegalArgumentException.class, () -> memberService.addMember(null), "Should throw IllegalArgumentException for null Member");
    }

    /**
     * Test retrieving all Member entities.
     */
    @Test
    public void testGetAllMembers() {
        List<Member> courses = new ArrayList<>();
        courses.add(new Member());
        courses.add(new Member());

        // Equivalence class: retrieving all courses
        when(memberRepository.findAll()).thenReturn(courses);
        List<Member> result = memberService.getAllMembers();
        assertEquals(2, result.size(), "Should return a list of 2 Members");
        verify(memberRepository, times(1)).findAll(); // Verify that findAll was called once
    }

    /**
     * Test finding a Member by a valid name.
     */
    @Test
    public void testFindByNameValid() {
        Member member = new Member();
        member.setName(testName);

        // Equivalence class: valid name
        when(memberRepository.findByName(testName)).thenReturn(member);
        Member foundCourse = memberService.findByName(testName);
        assertEquals(testName, foundCourse.getName(), "The found course name should match");
    }

    /**
     * Test finding a Member by a non-existent name.
     */
    @Test
    public void testFindByNameNotFound() {
        // Equivalence class: name not found
        when(memberRepository.findByName("Non-existent Course")).thenReturn(null);
        Member notFoundCourse = memberService.findByName("Non-existent Course");
        assertNull(notFoundCourse, "Should return null for a non-existent course");
    }
}
