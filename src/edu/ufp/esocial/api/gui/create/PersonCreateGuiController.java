package edu.ufp.esocial.api.gui.create;

import edu.ufp.esocial.api.Test;
import edu.ufp.esocial.api.gui.user.GuiController;
import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.model.Job;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.*;
import edu.ufp.esocial.api.service.impl.CompanyServiceImpl;
import edu.ufp.esocial.api.service.impl.CompetenceServiceImpl;
import edu.ufp.esocial.api.service.impl.MeetingServiceImpl;
import edu.ufp.esocial.api.service.impl.PersonServiceImpl;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.FileUtil;
import edu.ufp.esocial.api.util.Point;
import edu.ufp.esocial.api.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class PersonCreateGuiController {

    public TextField nomeField;
    public ComboBox empresaAtualComboBox;
    public TextField nomeCompField;
    public TextField areaCompField;
    public TextField descricaoCompField;
    public ComboBox levelCompComboBox;
    public TextArea compTextArea;
    public ComboBox empresasAnterioresComboBox;
    public TextArea empresasAnterioresTextArea;
    public TextField areaInteresseField;
    public TextArea areasInteresseTextArea;
    public DatePicker birthdayDatePicker;
    public Button adicionarButton;
    private Database database;
    private PersonService personService;
    private CompetenceService competenceService;
    private CompanyService companyService;
    private MeetingService meetingService;
    private Test test;

    public void setDatabase(Database db) {
        this.database = db;
        this.personService = new PersonServiceImpl(database);
        this.competenceService = new CompetenceServiceImpl(database);
        this.companyService = new CompanyServiceImpl(database);
        this.meetingService = new MeetingServiceImpl(database);
        test = new Test(database, personService, competenceService, companyService, meetingService);

        iniciarCampos();
    }

    /*------------- DONE ----------------*/
    public void iniciarCampos() {
        this.empresaAtualComboBox.getItems().clear();
        this.empresasAnterioresComboBox.getItems().clear();
        this.empresasAnterioresTextArea.clear();
        this.levelCompComboBox.getItems().clear();

        for (Integer i : database.getCompanyST().keys()) {
            empresaAtualComboBox.getItems().add(database.getCompanyST().get(i).getName());
            empresasAnterioresComboBox.getItems().add(database.getCompanyST().get(i).getName());
        }

        this.levelCompComboBox.getItems().add("A");
        this.levelCompComboBox.getItems().add("B");
        this.levelCompComboBox.getItems().add("C");
        this.levelCompComboBox.getItems().add("D");
        this.levelCompComboBox.getItems().add("E");
        this.levelCompComboBox.getItems().add("F");

    }

    /*------------- DONE ----------------*/
    public void addCompetenciaEvent(ActionEvent actionEvent) {
        String name = this.nomeCompField.getText();
        String descricao = this.descricaoCompField.getText();
        String area = this.areaCompField.getText();
        String level = (String) this.levelCompComboBox.getValue();
        if (level == null || name.isEmpty() || descricao.isEmpty() || area.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRO");
            alert.setHeaderText(null);
            alert.setContentText("Verifique os campos da competência");
            alert.showAndWait();
            return;
        }
        Competence competence = new Competence(name, area, descricao, new Date(), Util.CompetenceLevel.valueOf(level));
        competenceService.create(competence);
        this.compTextArea.appendText(competence.getTitle() + "\n");
        this.nomeCompField.clear();
        this.areaCompField.clear();
        this.descricaoCompField.clear();
        this.levelCompComboBox.setValue(null);
    }

    /*------------- DONE ----------------*/
    public void savePessoaEvent(ActionEvent actionEvent) throws IOException {
        String name = this.nomeField.getText();
        LocalDate localDate = this.birthdayDatePicker.getValue();
        if (this.nomeField.getText().isEmpty() || this.birthdayDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRO!");
            alert.setHeaderText(null);
            alert.setContentText("Verifique todos os campos");
            alert.showAndWait();
            return;
        }
        Date d = new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        String actualCompany = (String) this.empresaAtualComboBox.getValue();
        Company c = null;
        if (this.empresaAtualComboBox.getValue() != null) {
            for (Integer i : this.database.getCompanyST().keys()) {
                if (this.database.getCompanyST().get(i).getName().compareTo(actualCompany) == 0) {
                    c = this.database.getCompanyST().get(i);
                }
            }
        }

        Person p = new Person(name, Point.randomize(1000, 1000), d);
        if (c != null) {
            test.addJobToPerson(c.getName(), p.getId());
        } else {
            Job j = new Job(p, null);
            p.setCurrentJob(j);
        }

        String[] splitter = this.areasInteresseTextArea.getText().split("\n");
        for (String interest : splitter) {
            p.getInterestAreas().add(interest);
        }

        splitter = this.empresasAnterioresTextArea.getText().split("\n");
        Company comp;
        for (String companyName : splitter) {
            for (Integer j : this.database.getCompanyST().keys()) {
                if (this.database.getCompanyST().get(j).getName().compareTo(companyName) == 0) {
                    comp = this.database.getCompanyST().get(j);
                    Job job = new Job(p, comp);
                    p.getPreviousJobs().put(new Date(), job);
                }
            }
        }

        splitter = this.compTextArea.getText().split("\n");
        Competence competence;
        for (String competenceName : splitter) {
            for (String s : this.database.getCompetenceST().keys()) {
                if (s.compareTo(competenceName) == 0) {
                    competence = this.database.getCompetenceST().get(s);
                    test.addCompetenceToPerson(competence.getTitle(), p.getId());
                }
            }
        }

        test.addPerson(p.getName(), p.getLocation(), p.getBirth());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/usergui.fxml"));
        Parent root = loader.load();

        GuiController guiController = loader.getController();
        guiController.setDatabase(database);
        Scene scene = new Scene(root);

        Stage primaryStage = (Stage) adicionarButton.getScene().getWindow();
        primaryStage.setTitle("eSocial");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /*------------- DONE ----------------*/
    public void addEmpresaAnteriorEvent(ActionEvent actionEvent) {
        String s = (String) this.empresasAnterioresComboBox.getValue();
        s = s.concat("\n");
        this.empresasAnterioresTextArea.appendText(s);
    }

    /*------------- DONE ----------------*/
    public void addAreaInteresseEvent(ActionEvent actionEvent) {
        String s = this.areaInteresseField.getText();
        s = s.concat("\n");
        this.areasInteresseTextArea.appendText(s);
        this.areaInteresseField.clear();
    }

    /*------------- DONE ----------------*/
    public void handleLoadPersonFileEvent(ActionEvent actionEvent) {
        this.personService.deleteAll();
        this.competenceService.deleteAll();
        FileUtil.populateCompetenceST(this.competenceService);
        FileUtil.populatePersonST(this.personService);

        iniciarCampos();
    }

    /*------------- DONE ----------------*/
    public void handleLoadMeetingFileEvent(ActionEvent actionEvent) {
        this.meetingService.deleteAll();
        this.companyService.deleteAll();
        this.competenceService.deleteAll();
        this.personService.deleteAll();
        FileUtil.populateCompetenceST(this.competenceService);
        FileUtil.populatePersonST(this.personService);
        FileUtil.populateCompanyST(this.companyService);
        FileUtil.populateMeetingST(this.meetingService, this.companyService);
    }

    /*------------- DONE ----------------*/
    public void handleLoadCompanyFileEvent(ActionEvent actionEvent) {
        this.companyService.deleteAll();
        FileUtil.populateCompanyST(this.companyService);

        iniciarCampos();
    }

    /*------------- DONE ----------------*/
    public void handleLoadCompetenceFileEvent(ActionEvent actionEvent) {
        this.competenceService.deleteAll();
        FileUtil.populateCompetenceST(this.competenceService);
        iniciarCampos();
    }


    /**
     * TO-DO
     */
    public void handleSavePersonFileEvent(ActionEvent actionEvent) throws IOException {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);
        al.setTitle("Exportar pessoas!");
        ButtonType txt = new ButtonType("TXT", ButtonBar.ButtonData.OK_DONE);
        ButtonType bin = new ButtonType("BIN", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Em que formato deseja guardar?" + ".",
                        txt,
                        bin);
        alert.setTitle("Exportar pessoas!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == txt) {
            FileUtil.writePersonsFile(this.personService);
            al.setContentText("Guardado para TXT");
            al.showAndWait();
        }
        else if(result.get() == bin) {
            FileUtil.writePersonBinFile(this.personService);
            al.setContentText("Guardado para BIN");
            al.showAndWait();
        }
    }


    /**
     * TO-DO
     */
    public void handleSaveMeetingFileEvent(ActionEvent actionEvent) {
    }


    /**
     * TO-DO
     */
    public void handleSaveCompanyFileEvent(ActionEvent actionEvent) {
    }


    /**
     * TO-DO
     */
    public void handleSaveCompetenceFileEvent(ActionEvent actionEvent) {
    }


    /**
     * TO-DO
     */
    public void handleSaveAllFileEvent(ActionEvent actionEvent) {
    }

    /*------------- DONE ----------------*/
    public void handleAcercaEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca!");
        alert.setHeaderText(null);
        alert.setContentText("Programa realizado por:\n\n\t- João Vicente\n\t- Diogo Pinho\n\nUniversidade Fernando Pessoa\nEngenharia Informática\nLP2 & AED2");
        alert.showAndWait();
    }
}
