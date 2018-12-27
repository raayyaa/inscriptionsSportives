package inscriptions;

import static org.junit.Assert.*;

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
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveCompetition() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareTo() {
		fail("Not yet implemented");
	}

}
