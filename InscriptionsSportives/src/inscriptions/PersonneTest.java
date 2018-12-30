package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonneTest {
	private Personne person;
	private Inscriptions inscriptions;

	@Before
	public void setUp() throws Exception {
		inscriptions = Inscriptions.getInscriptions();
		inscriptions.reinitialiser();
		person = inscriptions.createPersonne("Raya", "Raya","ab@g.com");
		
	}

	@Test
	public void testDelete() {
		person.delete();
		assertFalse(inscriptions.getCompetitions().contains(person));
		assertFalse(person.getEquipes().contains(person));
	}


	@Test
	public void testAddCompetition() {
		Competition competition = inscriptions.createCompetition("c",LocalDate.now().plusDays(30), false);
		person.add(competition);
		assertTrue(person.getCompetitions().contains(competition));
		
	}

	

	

}
