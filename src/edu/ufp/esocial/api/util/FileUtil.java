package edu.ufp.esocial.api.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.model.Meeting;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.CompanyService;
import edu.ufp.esocial.api.service.CompetenceService;
import edu.ufp.esocial.api.service.MeetingService;
import edu.ufp.esocial.api.service.PersonService;

public class FileUtil {

	private static final String DATA = "./data/";
	private static final String PERSON_LIST = "personList.txt";
	private static final String COMPANY_LIST = "companyList.txt";
	private static final String COMPETENCE_LIST = "competenceList.txt";
	private static final String MEETING_LIST = "meetingList.txt";
	private static final String ARCHIVE_PERSON = "archivePerson.txt";
	private static final String ARCHIVE_MEETING = "archiveMeeting.txt";
	private static final String ARCHIVE_COMPETENCE = "archiveCompetence.txt";
	private static final String ARCHIVE_COMPANY = "archiveCompany.txt";

	private FileUtil() {
	}

	public static void populatePersonST(PersonService personService) {
		File file = new File(DATA + PERSON_LIST);
		try {
			In reader = new In(file);
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] parsed = line.split(";");
				personService.create(new Person(parsed[0], Point.fromString(parsed[1]), Date.fromString(parsed[2])));
			}
			reader.close();
		} catch (IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING,
					"File " + file.getAbsolutePath() + " might not exist.", e);
		}
	}

	public static void populateCompanyST(CompanyService companyService) {
		File file = new File(DATA + COMPANY_LIST);
		try {
			In reader = new In(file);
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] parsed = line.split(";");
				Point p = new Point(Double.parseDouble(parsed[1]), Double.parseDouble(parsed[2]));
				companyService.create(new Company(parsed[0], p));
			}
			reader.close();
		} catch (IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING,
					"File " + file.getAbsolutePath() + " might not exist.");
		}
	}

	public static void populateMeetingST(MeetingService meetingService, CompanyService companyService) {
		File file = new File(DATA + MEETING_LIST);
		try {
			In reader = new In(file);
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] parsed = line.split(";");
				final String[] parsedAreas = parsed[3].split(",");
				final String[] parsedStartDate = parsed[4].split("/");
				final String[] parsedEndDate = parsed[5].split("/");
				Date sd = new Date(Integer.parseInt(parsedStartDate[0]),
						Integer.parseInt(parsedStartDate[1]),
						Integer.parseInt(parsedStartDate[2]),
						Integer.parseInt(parsedStartDate[3]),
						Integer.parseInt(parsedStartDate[4]));
				Date end = new Date(Integer.parseInt(parsedEndDate[0]),
						Integer.parseInt(parsedEndDate[1]),
						Integer.parseInt(parsedEndDate[2]),
						Integer.parseInt(parsedEndDate[3]),
						Integer.parseInt(parsedEndDate[4]));
				Meeting meeting = new Meeting(parsed[0], Point.randomize(1000, 1000), sd, end, Float.parseFloat(parsed[1]), companyService.findByKey(Integer.parseInt(parsed[2])));
				for (String area : parsedAreas) {
					meeting.getAreasAborded().add(area);
				}
				meetingService.create(meeting);
			}
			reader.close();
		} catch (IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING,
					"File " + file.getAbsolutePath() + " might not exist.");
		}
	}

	public static void populateCompetenceST(CompetenceService competenceService) {
		File file = new File(DATA + COMPETENCE_LIST);
		try {
			In reader = new In(file);
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] parsed = line.split(";");
				competenceService.create(new Competence(parsed[0], parsed[1], parsed[2], null, Util.CompetenceLevel.valueOf(parsed[3])));
			}
			reader.close();
		} catch (IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING,
					"File " + file.getAbsolutePath() + " might not exist.");
		}
	}

	public static void writePersonsFile(PersonService personService) {
		try {
			Out writer = new Out(DATA + PERSON_LIST);
			for(Person person: personService.getAll()) {
				writer.println(
						person.getName() + ";"
						+ person.getLocation().toFileString() + ";"
						+ person.getBirth().toFileString() + ";");
			}
		} catch(IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING, "Writing file Person file error");
		}
	}

	public static void writeMeetingsFile(MeetingService meetingService) {
		try {
			Out writer = new Out(DATA + MEETING_LIST);
			for (Meeting m : meetingService.getAll()) {
				StringBuilder allAreas = new StringBuilder();
				for (String area : m.getAreasAborded()) {
					allAreas.append(area).append(",");
				}
				String areas = allAreas.toString();
				areas = areas.substring(0, areas.length() - 1);
				writer.println(
						m.getName() + ";"
						+ m.getLocation() + ";"
						+ m.getStartDate() + ";"
						+ m.getEndDate() + ";"
						+ m.getPrice() + ";"
						+ areas + ";");
			}
			writer.close();
		} catch (IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING,
					"File " + DATA + MEETING_LIST + " might not exist.");
		}
	}

	public static void writeCompetencesFile(CompetenceService competenceService) {
		try {
			Out writer = new Out(DATA + COMPETENCE_LIST);
			for (Competence c : competenceService.getAll()) {
				writer.println(c.getTitle() + ";" + c.getArea() + ";" + c.getDescription() + ";" + c.getAcquiredDate() + ";" + c.getLevel().name());
			}
			writer.close();
		} catch (IllegalArgumentException e) {
			Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING,
					"File " + DATA + COMPETENCE_LIST + " might not exist.");
		}
	}

	public static void writePersonBinFile(PersonService personService) throws java.io.IOException {
		FileOutputStream fos = new FileOutputStream(DATA + "personBin.bin");
		ObjectOutputStream os = new ObjectOutputStream(fos);
		for (Person p : personService.getAll()) {
			String text = p.getName() + ";"
					+ p.getLocation().toFileString() + ";"
					+ p.getBirth().toFileString() + ";\n";
			os.writeUTF(text);
		}
		fos.close();
	}

	public static void writeCompanyBinFile(CompanyService companyService) throws java.io.IOException {
		FileOutputStream out = new FileOutputStream(DATA + "companyBin.dat");
		OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		for (Company c : companyService.getAll()) {
			bw.write(c.getName() + ";"
					+ c.getLocation().toFileString() + ";\n");
			bw.flush();
		}
		out.close();
	}

	public static void writeMeetingBinFile(MeetingService meetingService) throws java.io.IOException {
		FileOutputStream out = new FileOutputStream(DATA + "personBin.dat");
		for (Meeting m : meetingService.getAll()) {

			StringBuilder allAreas = new StringBuilder();
			for (String area : m.getAreasAborded()) {
				allAreas.append(area).append(",");
			}
			String areas = allAreas.toString();
			areas = areas.substring(0, areas.length() - 1);

			out.write(Integer.parseInt(m.getName() + ";"
					+ m.getLocation() + ";"
					+ m.getStartDate() + ";"
					+ m.getEndDate() + ";"
					+ m.getPrice() + ";"
					+ areas + ";\n"));
		}
		out.close();
	}

	public static void writeCompetenceBinFile(CompetenceService competenceService) throws java.io.IOException {
		FileOutputStream out = new FileOutputStream(DATA + "personBin.dat");
		for (Competence c : competenceService.getAll()) {
			out.write(Integer.parseInt(c.getTitle() + ";" + c.getArea() + ";" + c.getDescription() + ";" + c.getAcquiredDate() + ";" + c.getLevel().name() + ";\n"));
		}
		out.close();
	}

	public static void writePersonToArchiveFile(Person p) {
		try {
			String text = "Person { name= " + p.getName() + ", location= " + p.getLocation() + ", birth= " + p.getBirth() + " }\n";
			Files.write(Paths.get(DATA + ARCHIVE_PERSON), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeCompetenceToArchiveFile(Competence c) {
		try {
			String text = "Competence { title= " + c.getTitle() + ", area= " + c.getArea() + ", description= " + c.getDescription() + ", acquiredDate= " + c.getAcquiredDate() + ", level= " + c.getLevel() + " }\n";
			Files.write(Paths.get(DATA + ARCHIVE_COMPETENCE), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeCompanyToArchiveFile(Company c) {
		try {
			String text = "Company { name= " + c.getName() + ", location= " + c.getLocation() + " }\n";
			Files.write(Paths.get(DATA + ARCHIVE_COMPANY), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeMeetingToArchiveFile(Meeting m) {
		try {
			String text = "Meeting { name= " + m.getName() + ", location= " + m.getLocation() + ", price= " + m.getPrice() + ", startDate= " + m.getStartDate() + ", endDate= " + m.getEndDate() + ", creator= " + m.getCreator() + " }\n";
			Files.write(Paths.get(DATA + ARCHIVE_MEETING), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
