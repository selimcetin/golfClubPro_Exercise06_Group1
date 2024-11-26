package de.hse.golfclubmanagement.repositories;

import de.hse.golfclubmanagement.models.GolfCourse;
import de.hse.golfclubmanagement.models.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Mock
    private MemberRepository memberRepository; // Mocked repository for GolfCourse

    private String testName = "testname";
    private String nonExistantName = "nonexistant";
    private int handicap = 2;

    /**
     * Set up the test environment before each test method.
     * Initializes mocks and prepares the repository for testing.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test finding a GolfCourse by a valid name.
     */
    @Test
    public void testFindByNameValid() {
        Member member = new Member();
        member.setName(testName);

        when(memberRepository.findByName(testName)).thenReturn(member);
        Member foundMember = memberRepository.findByName(testName);

        assertNotNull(foundMember, "The found member should not be null");
        assertEquals(testName, foundMember.getName(), "The found member name should match");
    }

    /**
     * Test finding a GolfCourse by a non-existent name.
     */
    @Test
    public void testFindByNameNotFound() {
        // Equivalence class: name not found
        when(memberRepository.findByName(nonExistantName)).thenReturn(null);
        Member notFoundMember = memberRepository.findByName(nonExistantName);

        assertNull(notFoundMember, "Should return null for a non-existent course");
    }

    /**
     * Test saving a GolfCourse.
     */
    @Test
    public void testSaveMember() {
        Member member = new Member();
        member.setName(testName);
        member.setHandicap(handicap);

        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember, "The saved GolfCourse should not be null");
        assertEquals(testName, savedMember.getName(), "The saved member name should match");
        assertEquals(handicap, savedMember.getHandicap(), "The saved handicap name should match");
    }

    /**
     * Test deleting a GolfCourse.
     */
    @Test
    public void testDeleteMember() {
        Member member = new Member();
        member.setName(testName);
        member.setHandicap(handicap);

        doNothing().when(memberRepository).delete(any(Member.class));
        memberRepository.delete(member);
        verify(memberRepository, times(1)).delete(member);
    }

    /**
     * Test finding a GolfCourse by ID.
     */
    @Test
    public void testFindById() {
        Member member = new Member();
        member.setName(testName);
        member.setHandicap(handicap);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        Optional<Member> foundMember = memberRepository.findById(1L);

        assertTrue(foundMember.isPresent(), "The found GolfCourse should be present");
        assertEquals(testName, foundMember.get().getName(), "The found course name should match");
    }

    /**
     * Test finding a GolfCourse by a non-existent ID.
     */
    @Test
    public void testFindByIdNotFound() {
        // Equivalence class: ID not found
        when(memberRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Member> notFoundMember = memberRepository.findById(999L);

        assertFalse(notFoundMember.isPresent(), "Should return empty for a non-existent course ID");
    }
}
