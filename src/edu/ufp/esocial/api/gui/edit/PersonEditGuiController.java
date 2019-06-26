package edu.ufp.esocial.api.gui.edit;

import edu.ufp.esocial.api.Test;
import edu.ufp.esocial.api.gui.user.GuiController;
import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.model.Job;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.*;
import edu.ufp.esocial.api.service.impl.CompanyServiceImpl;
import edu.ufp.esocial.api.service.impl.CompetenceServiceImpl;
import edu.ufp.esocial.api.service.impl.MeetingServiceImpl;
import edu.ufp.esocial.api.service.impl.PersonServiceImpl;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PersonEditGuiController {

    public TextField nomeField;
    public ComboBox empresaAtualComboBox;
    public Button adicionarButton;
    public TextField nomeCompField;
    public TextField areaCompField;
    public TextField descricaoCompField;
    public ComboBox levelCompComboBox;
    public DatePicker birthdayDatePicker;
    public ComboBox empresasAnterioresComboBox;
    public TextField areaInteresseField;
    public ComboBox empresasAnterioresGuardadasComboBox;
    public ComboBox interessesComboBox;
    public ComboBox competenciasAtuaisComboBox;
    private Database database;
    private PersonService personService;
    private CompetenceService competenceService;
    private CompanyService companyService;
    private MeetingService meetingService;
    private Test test;
    private Person p;

    public void setDatabase(Database db, Person p) {
        this.database = db;
        this.p = p;
        this.personService = new PersonServiceImpl(database);
        this.competenceService = new CompetenceServiceImpl(database);
        this.companyService = new CompanyServiceImpl(database);
        this.meetingService = new MeetingServiceImpl(database);
        test = new Test(database, personService, competenceService, companyService, meetingService);

        iniciarCampos();
    }

    public void iniciarCampos() {
        this.competenciasAtuaisComboBox.getItems().clear();
        this.interessesComboBox.getItems().clear();
        this.empresasAnterioresGuardadasComboBox.getItems().clear();
        this.empresasAnterioresComboBox.getItems().clear();
        this.empresaAtualComboBox.getItems().clear();
        this.levelCompComboBox.getItems().clear();
        this.descricaoCompField.clear();
        this.areaInteresseField.clear();
        this.nomeCompField.clear();
        this.nomeField.clear();
        this.areaCompField.clear();
        Person p = this.p;

        this.nomeField.setText(p.getName());
        if (p.getCurrentJob() != null) {
            this.empresaAtualComboBox.setValue(p.getCurrentJob().getCompany().getName());
        }
        for (Integer i : this.database.getCompanyST().keys()) {
            this.empresaAtualComboBox.getItems().add(this.database.getCompanyST().get(i).getName());
            this.empresasAnterioresComboBox.getItems().add(this.database.getCompanyST().get(i).getName());
        }
        if (p.getPreviousJobs().size() > 0) {
            for (Date d : p.getPreviousJobs().keys()) {
                this.empresasAnterioresGuardadasComboBox.getItems().add(p.getPreviousJobs().get(d).getCompany().getName());
            }
        }
        birthdayDatePicker.setValue(LocalDate.of(p.getBirth().getYear(), p.getBirth().getMonth(), p.getBirth().getDay()));

        if (p.getCompetences().size() > 0) {
            for (String s : p.getCompetences().keys()) {
                this.competenciasAtuaisComboBox.getItems().add(p.getCompetences().get(s));
            }
        }

        if (p.getInterestAreas().size() > 0) {
            for (String s : p.getInterestAreas()) {
                this.interessesComboBox.getItems().add(s);
            }
        }

        this.levelCompComboBox.getItems().add("A");
        this.levelCompComboBox.getItems().add("B");
        this.levelCompComboBox.getItems().add("C");
        this.levelCompComboBox.getItems().add("D");
        this.levelCompComboBox.getItems().add("E");
        this.levelCompComboBox.getItems().add("F");
    }

    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../user/usergui.fxml"));
        Parent root = loader.load();

        GuiController guiController = loader.getController();
        guiController.setDatabase(this.database);
        Scene scene = new Scene(root);

        Stage primaryStage = (Stage) adicionarButton.getScene().getWindow();
        primaryStage.setTitle("eSocial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void addEmpresaAnteriorEvent(ActionEvent actionEvent) {
        this.empresasAnterioresComboBox.getValue();
        if (this.empresasAnterioresComboBox.getValue() != null) {
            for (Integer i : this.database.getCompanyST().keys()) {
                if (this.empresasAnterioresComboBox.getValue().equals(this.database.getCompanyST().get(i).getName())) {
                    Job job = new Job(p, this.database.getCompanyST().get(i), Date.randomizePersonBirth(), Date.randomizePersonBirth());
                    p.getPreviousJobs().put(job.getEndDate(), job);
                    empresasAnterioresGuardadasComboBox.getItems().add(job.getCompany().getName());
                    this.empresasAnterioresComboBox.setValue(null);
                    iniciarCampos();
                    return;
                }
            }
        }
    }

    public void addAreaInteresseEvent(ActionEvent actionEvent) {
        String area = this.areaInteresseField.getText();
        for (String a : p.getInterestAreas()) {
            if (a.equals(area)) {
                return;
            }
        }
        p.getInterestAreas().add(area);
        this.areaInteresseField.clear();
        iniciarCampos();
    }

    public void addCompetenciaEvent(ActionEvent actionEvent) {
        Competence c = new Competence(this.nomeCompField.getText(), this.areaCompField.getText(), descricaoCompField.getText(), Date.randomizePersonBirth(), Util.CompetenceLevel.valueOf((String) this.levelCompComboBox.getValue()));
        competenceService.create(c);
        this.competenciasAtuaisComboBox.getItems().add(c);
        this.nomeCompField.clear();
        this.areaInteresseField.clear();
        this.descricaoCompField.clear();
        this.levelCompComboBox.setValue(null);
        iniciarCampos();
    }

    public void savePessoaEvent(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja guardar as alterações?");
        alert.setTitle("Guardar");
        alert.setHeaderText(null);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            goBack();
        } else {
            return;
        }
    }

    public void handleDeleteCompanyEvent(ActionEvent actionEvent) {
        String nome = (String) this.empresasAnterioresGuardadasComboBox.getValue();
        for (Integer i : this.database.getCompanyST().keys()) {
            if (this.database.getCompanyST().get(i).getName().equals(nome)) {
                for (Date d : p.getPreviousJobs().keys()) {
                    if (p.getPreviousJobs().get(d).getCompany().equals(this.database.getCompanyST().get(i))) {
                        p.getPreviousJobs().delete(d);
                        this.empresasAnterioresGuardadasComboBox.getItems().remove(p.getPreviousJobs().get(d).getCompany().getName());
                    }
                }
            }
        }
    }

    public void handleDeleteCompEvent(ActionEvent actionEvent) {
        String nome = (String) this.competenciasAtuaisComboBox.getValue();
        p.getCompetences().delete(nome);
        this.competenciasAtuaisComboBox.getItems().remove(nome);
        this.competenciasAtuaisComboBox.setValue(null);
    }

    public void handleCancelEvent(ActionEvent actionEvent) throws IOException {
        goBack();
    }

    public void handleDeleteInteresseEvent(ActionEvent actionEvent) {
        String interesse = (String) this.interessesComboBox.getValue();
        this.interessesComboBox.getItems().remove(interesse);
        this.interessesComboBox.setValue(null);
        p.getInterestAreas().remove(interesse);
    }
}
