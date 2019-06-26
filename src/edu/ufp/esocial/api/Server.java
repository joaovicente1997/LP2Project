package edu.ufp.esocial.api;

import edu.ufp.esocial.api.gui.GuiStart;
import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.service.CompanyService;
import edu.ufp.esocial.api.service.CompetenceService;
import edu.ufp.esocial.api.service.Database;
import edu.ufp.esocial.api.service.MeetingService;
import edu.ufp.esocial.api.service.PersonService;
import edu.ufp.esocial.api.service.impl.CompanyServiceImpl;
import edu.ufp.esocial.api.service.impl.CompetenceServiceImpl;
import edu.ufp.esocial.api.service.impl.MeetingServiceImpl;
import edu.ufp.esocial.api.service.impl.PersonServiceImpl;
import edu.ufp.esocial.api.util.Date;

public class Server {

	private Server(String[] args) {

		Database database = new Database();
		//PersonService personService = new PersonServiceImpl(database);
		//CompetenceService competenceService = new CompetenceServiceImpl(database);
		//CompanyService companyService = new CompanyServiceImpl(database);
		//MeetingService meetingService = new MeetingServiceImpl(database);
		//Test test = new Test(database, personService, competenceService, companyService, meetingService);
		GuiStart.start_gui(args, database);

		/** ------------------ PRINT ALL ------------------  ##DONE## **/
		//test.printAllPersons();
		//test.printAllCompanies();
		//test.printAllMeetings();
		/** ------------------ GET COMPANY BY ID ------------------  ##DONE## **/
		//Company c = companyService.findByKey(1);
		/** ------------------ PRINT FILTERED ------------------  ##DONE## **/
		//test.printFilteredPersons("at",null, null, null, null);
		//test.printFilteredMeetings("a", null, null, null, 0.0f, 0.0f, null, null);
		//test.printFilteredCompany("Saw", null);
		/** ------------------ ADD ------------------  ##DONE## **/
		/* --------------------- COMPETENCES ------------------ */

		//test.addCompetence("Programacao", "Inf", "programacao", new Date(), Util.CompetenceLevel.A);
		//test.addCompetence("Conducao", "Automovel", "categoria B", new Date(), Util.CompetenceLevel.A);

		/* --------------------- PERSONS ------------------ */

		//test.addPerson("Joao", null, new Date());
		//test.addPerson("Diogo", null, new Date());

		/* --------------------- COMPANIES ------------------ */

		//test.addCompany("Universidade Fernando Pessoa", null);
		//test.addCompany("Google", null);
		//test.addCompany("Apple", null);

		/* --------------------- MEETINGS ------------------ */

		//test.addMeeting("Hello World", null, new Date(), new Date(), 0.0f, null);

		/* --------------------- ADD AREA TO MEETING ------------------ */

		//test.addAreaAbordedToMeeting(0, "C");
		//test.addAreaAbordedToMeeting(0, "Java");

		/* --------------------- COMPETENCES TO PERSONS ------------------ */

		//test.addCompetenceToPerson("Programacao", 150);
		//test.addCompetenceToPerson("Conducao", 150);
		//test.addCompetenceToPerson("Programacao", 151);

		/* --------------------- MEETINGS TO PERSONS & PERSONS TO MEETINGS ------------------ */

		//test.addMeetingToPerson(0, 150);

		/* --------------------- MEETINGS TO COMPANIES & COMPANIES TO MEETINGS ------------------ */

		//test.addMeetingToCreatorCompany(0, "Universidade Fernando Pessoa");
		//test.addMeetingToCompany(0, "Apple");
		//test.addMeetingToCompany(0, "Google");

		/* --------------------- COMPANIES TO PERSONS & PERSONS TO COMPANIES ------------------ */

		//test.addJobToPerson("Universidade Fernando Pessoa", 150);

		/** ------------------ REMOVE ------------------ ##TO-DO## **/
		/* --------------------- COMPETENCES ------------------ */

		//test.removeCompetence("Programacao");

		/* --------------------- PERSONS ------------------ */

		//test.removePerson("Joao");

		/* --------------------- COMPANIES ------------------ */

		//test.removeCompany("Universidade Fernando Pessoa");

		/* --------------------- MEETINGS ------------------ */

		//test.removeMeeting(0);

		/* --------------------- COMPETENCES TO PERSONS ------------------ */

		//test.removeCompetenceFromPerson("Programacao", 150);

		/* --------------------- JOB FROM PERSON --------------------- */
		//test.removeCurrentJobFromPerson(150);

		/** ------------------ EDIT ------------------ ##TO-DO## **/


		/** ------------------ PRINT AUX ------------------ ##DONE## **/
		//test.printPerson(150);
		//test.printPerson(151);
		//test.printMeeting(0);
		//test.printCompany(51);
		//test.printCompany(52);
	}

	public static void main(String[] args) {
		new Server(args);
	}

}
