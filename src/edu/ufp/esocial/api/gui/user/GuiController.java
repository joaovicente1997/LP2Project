package edu.ufp.esocial.api.gui.user;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.ufp.esocial.api.Test;
import edu.ufp.esocial.api.gui.create.PersonCreateGuiController;
import edu.ufp.esocial.api.gui.edit.PersonEditGuiController;
import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.model.Follower;
import edu.ufp.esocial.api.model.Meeting;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.*;
import edu.ufp.esocial.api.service.impl.CompanyServiceImpl;
import edu.ufp.esocial.api.service.impl.CompetenceServiceImpl;
import edu.ufp.esocial.api.service.impl.MeetingServiceImpl;
import edu.ufp.esocial.api.service.impl.PersonServiceImpl;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.FileUtil;
import edu.ufp.esocial.api.util.Point;
import edu.ufp.esocial.api.util.filter.PersonFilter;
import edu.ufp.esocial.api.util.graphs.FollowersUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;


public class GuiController implements Initializable {
    public ComboBox<String> mapaComboBox;
    public ComboBox<String> amizadesComboBox;
    public ComboBox<String> empresasComboBox;
    public TextArea empresasTextArea;
    public TextField nomeEmpresaField;
    public TextField yCoordField;
    public Button addEmpresaButton;
    public Button removeEmpresaButton;
    public Button editEmpresaButton;
    public ComboBox<String> pessoaEmpresaTapComboBox;
    public Button addEmpresaMapButton;
    public Button removeEmpresaMapButton;
    public TextField xCoordField;
    public ComboBox<String> criadorEncontroComboBox;
    public TextArea personTextArea1;
    public TextArea personTextArea2;
    public TableView<Meeting> encontrosTable;
    public TableColumn<Object, Object> dataInicioCol;
    public TableColumn<Object, Object> dataFimCol;
    public TableColumn<Meeting, Float> precoCol;
    public TableColumn criadorCol;
    public TableColumn<Object, Object> areasCol;
    public TableColumn<Meeting, String> nomeEncontroCol;
    public TableColumn coordXEncontroCol;
    public TableColumn coordYEncontroCol;
    public DatePicker dataInicioEncontroPicker;
    public DatePicker dataFimEncontroPicker;
    public TextField precoEncontroField;
    public TextField areaEncontroField;
    public TextField coordXEncontroField;
    public TextField coordYEncontroField;
    public TextField nomeEncontroField;
    public TextField horaInicioEncontroField;
    public TextField horaFimEncontroField;
    public ComboBox<String> personsComboBox1;
    public ComboBox<String> personsComboBox2;
    public MenuButton menuButton;
    public Group amizadeGraphGroup;
    public ComboBox followersComboBox;
    public Group followerGraphGroup;
    private Database database;
    private PersonService personService;
    private CompanyService companyService;
    private CompetenceService competenceService;
    private MeetingService meetingService;
    private Test test;
    private double radius = 50.0;
    private Follower follower;

    public void setDatabase(Database db) {
        this.database = db;
        personsComboBox1.getItems().clear();
        personsComboBox2.getItems().clear();
        this.personService = new PersonServiceImpl(database);
        this.competenceService = new CompetenceServiceImpl(database);
        this.companyService = new CompanyServiceImpl(database);
        this.meetingService = new MeetingServiceImpl(database);
        test = new Test(database, personService, competenceService, companyService, meetingService);
        this.follower = new Follower(database);

        atualizar_campos();
    }

    /*------------- DONE ----------------*/
    public void atualizar_campos() {
        if (database.getCompanyST().size() > 0 || database.getPersonST().size() > 0) {
            follower.loadFollowers(database);
            System.out.println(database.getFollowers().toString());
        }
        //follower.printFollowers(database);
        personsComboBox1.getItems().clear();
        personsComboBox2.getItems().clear();
        amizadesComboBox.getItems().clear();
        followersComboBox.getItems().clear();
        pessoaEmpresaTapComboBox.getItems().clear();
        empresasComboBox.getItems().clear();
        criadorEncontroComboBox.getItems().clear();
        encontrosTable.getItems().clear();
        personTextArea1.clear();
        personTextArea2.clear();
        empresasTextArea.clear();
        nomeEmpresaField.clear();
        xCoordField.clear();
        yCoordField.clear();
        for (Integer i : database.getPersonST().keys()) {
            personsComboBox1.getItems().add(database.getPersonST().get(i).getName());
            personsComboBox2.getItems().add(database.getPersonST().get(i).getName());
            amizadesComboBox.getItems().add(database.getPersonST().get(i).getName());
            pessoaEmpresaTapComboBox.getItems().add(database.getPersonST().get(i).getName());
        }
        for (Integer i : database.getCompanyST().keys()) {
            empresasComboBox.getItems().add(database.getCompanyST().get(i).getName());
            criadorEncontroComboBox.getItems().add(database.getCompanyST().get(i).getName());
        }
        for (Integer i : database.getMeetingST().keys()) {
            encontrosTable.getItems().add(database.getMeetingST().get(i));
        }
        for (Integer i : database.getFollowerST().keys()) {
            followersComboBox.getItems().add(database.getFollowerST().get(i).getNameOfFollower(this.database, database.getFollowerST().get(i)));
        }
    }

    /*------------- DONE ----------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        encontrosTable.setRowFactory(tv -> {
            TableRow<Meeting> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1 && event.getButton() == MouseButton.SECONDARY) {

                    Meeting clickedRow = row.getItem();
                    handleDeleteMeetingAction(clickedRow);
                }
            });
            return row;
        });


        this.nomeEncontroCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.coordXEncontroCol.setCellValueFactory(new Callback<TableColumn.
                CellDataFeatures<Meeting, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.
                                                        CellDataFeatures<Meeting, String> param) {
                if (param.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(param.getValue().getLocation().getX()));
                } else {
                    return new SimpleStringProperty("<no name>");
                }
            }
        });
        this.coordYEncontroCol.setCellValueFactory(new Callback<TableColumn.
                CellDataFeatures<Meeting, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.
                                                        CellDataFeatures<Meeting, String> param) {
                if (param.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(param.getValue().getLocation().getY()));
                } else {
                    return new SimpleStringProperty("<no name>");
                }
            }
        });
        this.dataInicioCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.dataFimCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        this.precoCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.criadorCol.setCellValueFactory(new Callback<TableColumn.
                CellDataFeatures<Meeting, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.
                                                        CellDataFeatures<Meeting, String> param) {
                if (param.getValue() != null && param.getValue().getCreator() != null) {
                    return new SimpleStringProperty(param.getValue().getCreator().getName());
                }
                return null;
            }
        });
        this.areasCol.setCellValueFactory(new PropertyValueFactory<>("areasAborded"));

        encontrosTable.setEditable(true);

        this.personsComboBox1.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.personTextArea1.clear();
            for (Integer i : this.database.getPersonST().keys()) {
                if (this.database.getPersonST().get(i).getName().equals(this.personsComboBox1.getValue())) {
                    this.personTextArea1.setText(this.database.getPersonST().get(i).toString());
                }
            }
        });

        this.personsComboBox2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.personTextArea2.clear();
            for (Integer i : this.database.getPersonST().keys()) {
                if (this.database.getPersonST().get(i).getName().equals(this.personsComboBox2.getValue())) {
                    this.personTextArea2.setText(this.database.getPersonST().get(i).toString());
                }
            }
        });

        this.nomeEncontroCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //this.dataInicioCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //this.dataFimCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.coordXEncontroCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.coordYEncontroCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.criadorCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /*------------- DONE ----------------*/
    public void handleAddMeetingAction(ActionEvent actionEvent) {
        Point p = new Point(Double.parseDouble(coordXEncontroField.getText()), Double.parseDouble(coordYEncontroField.getText()));
        LocalDate localInicio = dataInicioEncontroPicker.getValue();
        LocalDate localFim = dataFimEncontroPicker.getValue();
        String horaInicio = horaInicioEncontroField.getText();
        String horaFim = horaFimEncontroField.getText();
        String[] outputInicio = horaInicio.split(":");
        String[] outputFim = horaFim.split(":");
        Company c = null;

        Date dInicio = new Date(localInicio.getDayOfMonth(), localInicio.getMonthValue(), localInicio.getYear(), Integer.parseInt(outputInicio[0]), Integer.parseInt(outputFim[1]));
        Date dFim = new Date(localFim.getDayOfMonth(), localFim.getMonthValue(), localFim.getYear(), Integer.parseInt(outputFim[0]), Integer.parseInt(outputFim[1]));
        for (Integer i : this.database.getCompanyST().keys()) {
            if (criadorEncontroComboBox.getValue().compareTo(this.database.getCompanyST().get(i).getName()) == 0) {
                c = this.database.getCompanyST().get(i);
            }
        }
        Meeting m = new Meeting(nomeEncontroField.getText(), p, dInicio, dFim, Float.parseFloat(precoEncontroField.getText()), c);
        String[] areas = areaEncontroField.getText().split(",");
        for (String area : areas) {
            m.getAreasAborded().add(area);
        }
        this.coordXEncontroField.clear();
        this.coordYEncontroField.clear();
        this.horaInicioEncontroField.clear();
        this.horaFimEncontroField.clear();
        this.nomeEncontroField.clear();
        this.precoEncontroField.clear();
        this.criadorEncontroComboBox.setValue("Empresa Criadora");
        this.areaEncontroField.clear();
        this.dataInicioEncontroPicker.getEditor().clear();
        this.dataFimEncontroPicker.getEditor().clear();

        meetingService.create(m);
        this.encontrosTable.getItems().add(m);
    }

    /*------------- DONE ----------------*/
    public void handleEscolherEmpresaEvent(ActionEvent actionEvent) {
        if (this.empresasComboBox.getValue() != null) {
            String s = this.empresasComboBox.getValue();
            for (Integer i : this.database.getCompanyST().keys()) {
                Company c = this.database.getCompanyST().get(i);
                if (c.getName().compareTo(s) == 0) {
                    this.empresasTextArea.clear();
                    this.empresasTextArea.appendText(c.toString());
                    this.nomeEmpresaField.clear();
                    this.nomeEmpresaField.appendText(c.getName());
                    this.xCoordField.clear();
                    this.xCoordField.appendText(String.valueOf(c.getLocation().getX()));
                    this.yCoordField.clear();
                    this.yCoordField.appendText(String.valueOf(c.getLocation().getY()));
                    return;
                }
            }
        }
    }

    /*------------- DONE ----------------*/
    public void handleLoadPersonFileEvent(ActionEvent actionEvent) {
        this.personService.deleteAll();
        this.competenceService.deleteAll();
        FileUtil.populateCompetenceST(this.competenceService);
        FileUtil.populatePersonST(this.personService);
        atualizar_campos();
    }

    /*------------- DONE ----------------*/
    public void handleLoadMeetingFileEvent(ActionEvent actionEvent) {
        encontrosTable.getItems().clear();
        this.meetingService.deleteAll();
        this.companyService.deleteAll();
        this.competenceService.deleteAll();
        this.personService.deleteAll();
        FileUtil.populateCompetenceST(this.competenceService);
        FileUtil.populatePersonST(this.personService);
        FileUtil.populateCompanyST(this.companyService);
        FileUtil.populateMeetingST(this.meetingService, this.companyService);
        atualizar_campos();
        for (Integer i : this.database.getMeetingST().keys()) {
            encontrosTable.getItems().add(this.database.getMeetingST().get(i));
        }
    }

    /*------------- DONE ----------------*/
    public void handleLoadCompanyFileEvent(ActionEvent actionEvent) {
        this.companyService.deleteAll();
        FileUtil.populateCompanyST(this.companyService);
        atualizar_campos();
    }

    /*------------- DONE ----------------*/
    public void handleLoadCompetenceFileEvent(ActionEvent actionEvent) {
        this.competenceService.deleteAll();
        FileUtil.populateCompetenceST(this.competenceService);
        atualizar_campos();
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

    /** TO-DO */
    public void handleSaveMeetingFileEvent(ActionEvent actionEvent) {
    }

    /** TO-DO */
    public void handleSaveCompanyFileEvent(ActionEvent actionEvent) {
    }

    /** TO-DO */
    public void handleSaveAllFileEvent(ActionEvent actionEvent) {
    }

    /** TO-DO */
    public void handleSaveCompetenceFileEvent(ActionEvent actionEvent) {
    }

    /*------------- DONE ----------------*/
    public void handleLoadAddGuiEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader createLoader = new FXMLLoader(getClass().getResource("../create/personcreategui.fxml"));
        Parent create = createLoader.load();

        PersonCreateGuiController personCreateGuiController = createLoader.getController();
        personCreateGuiController.setDatabase(this.database);

        Scene createScene = new Scene(create);

        Stage createStage = (Stage) menuButton.getScene().getWindow();
        createStage.setScene(createScene);
        createStage.setTitle("eSocial - Adicionar Pessoa");
        createStage.show();
    }

    /*------------- DONE ----------------*/
    public void handleLoadRemovePersonEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Pessoa!");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente apagar " + this.personsComboBox1.getValue() + " da Base de Dados?\n");
        String name = this.personsComboBox1.getValue();

        Optional<ButtonType> option = alert.showAndWait();

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Delete Action");
        alert1.setHeaderText(null);

        if (option.get() == ButtonType.OK) {
            this.personsComboBox1.getItems().remove(name);
            this.personsComboBox2.getItems().remove(name);
            this.personTextArea2.clear();
            this.personTextArea1.clear();
            test.removePerson(name);
            alert1.setContentText(name + " apagado da Base de Dados");
            alert1.showAndWait();
        } else {
            alert1.setContentText("Cancelado!");
            alert1.showAndWait();
        }
    }

    /**
     * TO-DO
     */
    public void handleLoadEditGuiEvent(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Editar Pessoa!");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente editar os campos de: " + this.personsComboBox1.getValue() + "?\n");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            for (Integer i : this.database.getPersonST().keys()) {
                if (this.database.getPersonST().get(i).getName().equals(this.personsComboBox1.getValue())) {
                    Person p = this.database.getPersonST().get(i);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../edit/personeditgui.fxml"));
                    Parent edit = loader.load();
                    PersonEditGuiController guiController = loader.getController();
                    guiController.setDatabase(database, p);
                    Scene editScene = new Scene(edit);

                    Stage editStage = (Stage) menuButton.getScene().getWindow();
                    editStage.setScene(editScene);
                    editStage.setTitle("eSocial - Editar Pessoa");
                    editStage.show();
                }
            }
        } else {
            return;
        }
    }

    /*------------- DONE ----------------*/
    public void handleAcercaEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca!");
        alert.setHeaderText(null);
        alert.setContentText("Programa realizado por:\n\n\t- João Vicente\n\t- Diogo Pinho\n\nUniversidade Fernando Pessoa\nEngenharia Informática\nLP2 & AED2");
        alert.showAndWait();
    }

    /*------------- DONE ----------------*/
    public void handleAddEmpresaAction(ActionEvent actionEvent) {
        if (this.empresasComboBox.getValue() != null && this.empresasComboBox.getValue().compareTo(this.nomeEmpresaField.getText()) == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro Adicionar Empresa");
            alert.setHeaderText(null);
            alert.setContentText("A empresa " + this.empresasComboBox.getValue() + " já existe");
            alert.showAndWait();
            return;
        }
        String name = this.nomeEmpresaField.getText();
        String x = this.xCoordField.getText();
        String y = this.yCoordField.getText();
        Point point = new Point(Double.parseDouble(x), Double.parseDouble(y));
        test.addCompany(name, point);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText("Empresa " + name + " criada com sucesso!");
        alert.setHeaderText(null);
        alert.showAndWait();
        atualizar_campos();
    }

    /*------------- DONE ----------------*/
    public void handleRemoveEmpresaAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Empresa!");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente apagar " + this.empresasComboBox.getValue() + " da Base de Dados?\n");
        String name = this.empresasComboBox.getValue();

        Optional<ButtonType> option = alert.showAndWait();

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Delete Action");
        alert1.setHeaderText(null);

        if (option.get() == ButtonType.OK) {
            this.empresasComboBox.getItems().remove(name);
            this.nomeEmpresaField.clear();
            this.xCoordField.clear();
            this.yCoordField.clear();
            this.empresasTextArea.clear();
            test.removeCompany(name);
            alert1.setContentText(name + " apagado da Base de Dados");
            alert1.showAndWait();
        } else {
            alert1.setContentText("Cancelado!");
            alert1.showAndWait();
        }
    }

    /*------------- DONE ----------------*/
    public void handleEditEmpresaAction(ActionEvent actionEvent) {
        String newName = this.nomeEmpresaField.getText();
        String x = this.xCoordField.getText();
        String y = this.yCoordField.getText();
        String oldName = this.empresasComboBox.getValue();
        Company c = null;
        for (Integer i : this.database.getCompanyST().keys()) {
            if (this.database.getCompanyST().get(i).getName().compareTo(oldName) == 0) {
                c = this.database.getCompanyST().get(i);
            }
        }
        if (c != null && (!c.getName().equals(newName) || c.getLocation().getX() != Double.parseDouble(x) || c.getLocation().getY() != Double.parseDouble(y))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Empresa!");
            alert.setHeaderText(null);
            alert.setContentText("Deseja realmente atualizar os campos de " + this.empresasComboBox.getValue() + "?\n");

            Optional<ButtonType> option = alert.showAndWait();

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Update Action");
            alert1.setHeaderText(null);

            if (option.get() == ButtonType.OK) {
                c.setName(newName);
                Point newPoint = new Point(Double.parseDouble(x), Double.parseDouble(y));
                c.setLocation(newPoint);
                this.empresasTextArea.clear();
                this.nomeEmpresaField.clear();
                this.xCoordField.clear();
                this.yCoordField.clear();
                this.empresasComboBox.setValue("Selecione Empresa");
                alert1.setContentText("Informação atualizada com sucesso!!");
                alert1.showAndWait();
            } else {
                alert1.setContentText("Cancelado!");
                alert1.showAndWait();
            }
        }
    }

    /*------------- DONE ----------------*/
    public void handleFriendshipsDisplayEvent(ActionEvent actionEvent) {
        amizadeGraphGroup.getChildren().clear();
        ArrayList<Person> list = new ArrayList<>();
        if (this.amizadesComboBox.getValue() != null) {
            for (int id : this.database.getPersonST().keys()) {
                Person p1 = this.database.getPersonST().get(id);
                if (p1.getName().compareTo(this.amizadesComboBox.getValue()) == 0) {
                    list.add(p1);
                    String s = this.database.getAmizades().amizades(this.database, id);
                    String[] split = s.split("\n");
                    for (String s1 : split) {
                        for (int id1 : this.database.getPersonST().keys()) {
                            Person friend = this.database.getPersonST().get(id1);
                            if (friend.getName().compareTo(s1) == 0) {
                                list.add(friend);
                            }
                        }
                    }
                    createGraphGroup(list.size(), list);
                }
            }
        }
    }

    /*------------- DONE ----------------*/
    public void createGraphGroup(int vNumber, ArrayList<Person> list) {
        Person init = list.get(0);
        double initialPosX = 0.0;
        double initialPosY = 0.0;
        Random random = new Random();
        Circle c = null;
        for (Person p : list) {
            double posX = random.nextDouble() * 1000;
            double posY = random.nextDouble() * 300;
            if (p.equals(init)) {
                initialPosX = posX;
                initialPosY = posY;
                c = new Circle(posX, posY, radius);
                c.setFill(Color.ORANGERED);
            }
            else {
                c = new Circle(posX, posY, radius);
                c.setFill(Color.LIGHTBLUE);
            }
            c.setId("" + p.getId());
            Text text = new Text("" + p.getName());
            text.setX(posX - 50);
            text.setY(posY);
            text.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            if (!p.equals(init)) {
                Line line = new Line(initialPosX, initialPosY, posX, posY);
                line.setStroke(Color.GREY);
                amizadeGraphGroup.getChildren().add(line);
            }
            amizadeGraphGroup.getChildren().addAll(c, text);
        }
    }

    /*------------- DONE ----------------*/
    public void handleAddFriendshipEvent(ActionEvent actionEvent) {
        if (personsComboBox1.getValue() != null && personsComboBox2.getValue() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Amizade!");
            alert.setHeaderText(null);
            alert.setContentText("Estes utilizadores já são amigos.");
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Amizade!");
            alert2.setHeaderText(null);
            alert2.setContentText("Não pode criar amizade com o mesmo utilizador!\nTente novamente.");

            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Amizade!");
            alert3.setHeaderText(null);
            alert3.setContentText("Ligação criada com sucesso!");

            for (Integer id1 : this.database.getPersonST().keys()) {
                Person p1 = this.database.getPersonST().get(id1);
                for (Integer id2 : this.database.getPersonST().keys()) {
                    Person p2 = this.database.getPersonST().get(id2);
                    if (p1.getName().equals(personsComboBox1.getValue()) && p2.getName().equals(personsComboBox2.getValue())) {
                        if (existsInAmizadesGraph(p1.getId(), p2.getId())) {
                            alert.showAndWait();
                            atualizar_campos();
                            return;
                        }
                        if (p1.getId().equals(p2.getId())) {
                            alert2.showAndWait();
                            atualizar_campos();
                            return;
                        }
                        int peso = test.pesoComum(p1.getId(), p2.getId());
                        Edge e = new Edge(p1.getId(), p2.getId(), peso);
                        this.database.getAmizades().addEdge(e);
                        System.out.println(this.database.getAmizades().toString());
                        alert3.showAndWait();
                        atualizar_campos();
                    }
                }
            }
        }
    }

    /*------------- DONE ----------------*/
    public boolean existsInAmizadesGraph(int p1_id, int p2_id) {
        Iterable<Edge> list = this.database.getAmizades().edges();
        for (Edge e : list) {
            int id1 = e.getV();
            int id2 = e.getW();
            if (id1 == p1_id && id2 == p2_id) {
                return true;
            } else if (id2 == p1_id && id1 == p2_id) {
                return true;
            }
        }
        return false;
    }


    public void handleRemoveAmizadeEvent(ActionEvent actionEvent) {

    }

    /*------------- DONE ----------------*/
    public void handleDeleteMeetingAction(Meeting m) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Apagar Encontro");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente apagar o encontro \"" + m.getName() + "\" ?");

        Optional<ButtonType> option = alert.showAndWait();

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Apagar Encontro");
        alert1.setHeaderText(null);


        if (option.get() == ButtonType.OK) {
            encontrosTable.getItems().remove(m.getId());
            meetingService.delete(m.getId());
            alert1.setContentText("Encontro apagado com sucesso!");
            alert1.showAndWait();
            atualizar_campos();
            return;
        } else {
            alert1.setContentText("Cancelado!");
            alert1.showAndWait();
            return;
        }
    }


    public void handleEditMeetingAction(Meeting m) {
    }

    public void handleShowFollowerGraphEvent(ActionEvent actionEvent) {
        followerGraphGroup.getChildren().clear();
        ArrayList<Follower> list = new ArrayList<>();
        if (followersComboBox.getValue() != null) {
            for (Integer i : database.getFollowerST().keys()) {
                Follower f = database.getFollowerST().get(i);
                if (f.getNameOfFollower(database, f).compareTo(followersComboBox.getValue().toString()) == 0) {
                    list.add(f);
                    String s = database.getFollowers().WhoIFollow(this.database, f.getId());
                    String[] split = s.split("\n");
                    for (int j = 0; j < split.length; j++) {
                        for (Integer k : database.getFollowerST().keys()) {
                            Follower friend = database.getFollowerST().get(k);
                            if (friend.getNameOfFollower(this.database, friend).compareTo(split[j]) == 0) {
                                list.add(friend);
                            }
                        }
                    }
                    System.out.println(this.database.getFollowers().toString());
                    createGraphGroupFollower(list.size(), list);
                }
            }
        }
    }

    public void createGraphGroupFollower(int vNumber, ArrayList<Follower> list) {
        Follower init = list.get(0);
        double initialPosX = 1.0;
        double initialPosY = 1.0;
        Random random = new Random();
        Circle c = null;
        for (Follower p : list) {
            double posX = random.nextDouble() * 1000;
            double posY = random.nextDouble() * 300;
            if (p.equals(init)) {
                posX = random.nextDouble() * 1000;
                posY = random.nextDouble() * 300;
                initialPosX = posX;
                initialPosY = posY;
                c = new Circle(posX, posY, radius);
                c.setFill(Color.ORANGERED);
            }
            else {
                c = new Circle(posX, posY, radius);
                c.setFill(Color.LIGHTBLUE);
            }
            c.setId("" + p.getId());
            Text text = new Text("" + p.getNameOfFollower(this.database, p));
            text.setX(posX - 50);
            text.setY(posY);
            text.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            if (!p.equals(init)) {
                Line line = new Line(initialPosX, initialPosY, posX, posY);
                line.setStroke(Color.GREY);
                followerGraphGroup.getChildren().add(line);
            }
            followerGraphGroup.getChildren().addAll(c, text);
        }
    }

    public void handleAddFollowerEvent(ActionEvent actionEvent) {
        Follower f1 = null;
        Follower f2 = null;
        for (Integer key : database.getFollowerST().keys()) {
            Follower p = database.getFollowerST().get(key);
            if (pessoaEmpresaTapComboBox.getValue().compareTo(p.getNameOfFollower(this.database, p)) == 0) {
                f1 = p;
            } else if (empresasComboBox.getValue().compareTo(p.getNameOfFollower(this.database, p)) == 0) {
                f2 = p;
            }
        }

        this.database.getFollowers().addEdge(f2.getId(), f1.getId());
    }

    public void handleRemoveFollowerEvent(ActionEvent actionEvent) {
    }

    public void handleFollowPersonEvent(ActionEvent actionEvent) {
        Follower f1 = null;
        Follower f2 = null;
        for (Integer key : database.getFollowerST().keys()) {
            Follower p = database.getFollowerST().get(key);
            if (pessoaEmpresaTapComboBox.getValue().compareTo(p.getNameOfFollower(this.database, p)) == 0) {
                f1 = p;
            } else if (empresasComboBox.getValue().compareTo(p.getNameOfFollower(this.database, p)) == 0) {
                f2 = p;
            }
        }

        this.database.getFollowers().addEdge(f1.getId(), f2.getId());
    }

    public void handleDeleteFollowPersonEvent(ActionEvent actionEvent) {
    }

    public void handleOnEditNameEvent(TableColumn.CellEditEvent<Meeting, String> meetingStringCellEditEvent) {
        Meeting m = encontrosTable.getSelectionModel().getSelectedItem();
        m.setName(meetingStringCellEditEvent.getNewValue());
        atualizar_campos();
    }

    public void handleOnEditDataInicioEvent(TableColumn.CellEditEvent<Meeting, String> meetingStringCellEditEvent) {
        Meeting m = encontrosTable.getSelectionModel().getSelectedItem();

        //m.setStartDate();
    }

    public void handleOnEditDataFimEvent(TableColumn.CellEditEvent<Meeting, String> meetingStringCellEditEvent) {
    }

    public void handleOnEditXEvent(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void handleOnEditYEvent(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void handleOnEditPrecoEvent(TableColumn.CellEditEvent<Meeting, String> meetingStringCellEditEvent) {
    }

    public void handleOnEditCriadorEvent(TableColumn.CellEditEvent cellEditEvent) {
        Meeting m = encontrosTable.getSelectionModel().getSelectedItem();
        for(Integer i : this.database.getCompanyST().keys()) {
            if(this.database.getCompanyST().get(i).getName().equals(cellEditEvent.getNewValue())){
                Company c = this.database.getCompanyST().get(i);
                m.setCreator(c);
                atualizar_campos();
            }
        }
    }
}