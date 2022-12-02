package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import controller.MainController;
import controller.PaneNavigator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Class;
import model.Course;
import model.Instructor;
import model.Room;
import model.StudentsGroup;

public class App extends Application {

    private Stage primaryStage;
    private ObservableList<Room> RoomData = FXCollections.observableArrayList();
    private ObservableList<StudentsGroup> GroupData = FXCollections.observableArrayList();
    private ObservableList<Course> CourseData = FXCollections.observableArrayList();
    private ObservableList<Instructor> InstructorData = FXCollections.observableArrayList();
    private ObservableList<Class> ClassData = FXCollections.observableArrayList();
    private HashMap<String,Boolean> workingDays = new HashMap<>();
    private ObservableList<Class> generatedTableData = FXCollections.observableArrayList();
    private int periodsCount = 5;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        initialize();
        primaryStage.setTitle("Time Table Generator");
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image("file:resources/images/logo.png"));
        primaryStage.setScene(createScene(loadMainPane()));
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void initialize(){
        System.out.println("init");
        Room room104 = new Room("B104");
        Room room201 = new Room("A201");
        Room room202 = new Room("C202");
        Room room203 = new Room("B203");
        RoomData.addAll(room104,room201,room202,room203);

        StudentsGroup firstYear = new StudentsGroup("INE 1");
        StudentsGroup secondYear = new StudentsGroup("INE 2");
        StudentsGroup thirdYear = new StudentsGroup("INE 3");
        StudentsGroup fourthYear = new StudentsGroup("INE 4");
        GroupData.addAll(firstYear,secondYear,thirdYear,fourthYear);

        Course firstBDR = new Course("Base de données relationnelles","BDR");
        Course firstMF = new Course("Marketing Fondamental","MF");
        Course firstMP = new Course("Management de projet","MP");
        Course firstDevWeb = new Course("Développement Web","DevWeb");
        Course firstTNS = new Course("Traitement numérique du signal","TNS");
        Course firstSoC = new Course("System on Chip","SoC");
        CourseData.addAll(firstBDR,firstMF,firstMP,firstDevWeb,firstTNS,firstSoC);

        Instructor Externe = new Instructor("Externe");
        Instructor Barka = new Instructor("Dr BARKA");
        Instructor Laghrissi = new Instructor("Dr LAGHRISSI");
        Instructor Khallaayoune = new Instructor("Dr KHALLAAYOUN");
        Instructor Chami = new Instructor("Dr CHAMI");

        InstructorData.addAll(Externe,Barka,Laghrissi,Khallaayoune,Chami,Externe,Externe);

        Class devWebLec = new Class(firstDevWeb,"Lec",Externe,firstYear,room104);
        Class socLec = new Class(firstSoC,"Lec",Externe,firstYear,room104);
        Class mpLec = new Class(firstMP,"Lec",Khallaayoune,firstYear,room104);
        Class mfLec = new Class(firstMF,"Lec",Laghrissi,firstYear,room104);
        Class dbrLec = new Class(firstBDR,"Lec",Chami,firstYear,room104);
        Class tnsLec = new Class(firstTNS,"Lec",Externe,firstYear,room104);
        Class mfLec2 = new Class(firstMF,"Lec",Laghrissi,firstYear,room104);
        Class dbrLec2 = new Class(firstBDR,"Lec",Barka,firstYear,room104);
        Class mpLec2 = new Class(firstMP,"Lec",Khallaayoune,firstYear,room104);
        Class tnsLec2 = new Class(firstTNS,"Lec",Externe,firstYear,room104);

        Class devWebLec21 = new Class(firstDevWeb,"Lec",Externe,secondYear,room201);
        Class mpLec21 = new Class(firstMP,"Lec",Khallaayoune,secondYear,room201);
        Class mfLec21 = new Class(firstMF,"Lec",Laghrissi,secondYear,room201);
        Class dbrLec21 = new Class(firstBDR,"Lec",Chami,secondYear,room201);
        Class tns21 = new Class(firstTNS,"Lec",Externe,secondYear,room201);
        Class devWebLec22 = new Class(firstDevWeb,"Lec",Externe,secondYear,room201);
        Class mfLec22 = new Class(firstMF,"Lec",Laghrissi,secondYear,room201);
        Class dbrLec22 = new Class(firstBDR,"Lec",Barka,secondYear,room201);
        Class mpLec22 = new Class(firstMP,"Lec",Khallaayoune,secondYear,room201);
        Class tnsLec22 = new Class(firstTNS,"Lec",Externe,secondYear,room201);

        ClassData.addAll(devWebLec,socLec,mpLec,mfLec,dbrLec,tnsLec,devWebLec,mfLec2,
                dbrLec2,mpLec2,tnsLec2,devWebLec21,mpLec21,mfLec21,dbrLec21,tns21,
                devWebLec22,mfLec22,dbrLec22,mpLec22,tnsLec22);

        workingDays.put("saturday",false);
        workingDays.put("sunday",false);
        workingDays.put("monday",true);
        workingDays.put("tuesday",true);
        workingDays.put("wednesday",true);
        workingDays.put("thursday",true);
        workingDays.put("friday",true);
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(new URL("file:resources/fxml/" + PaneNavigator.MAIN_PANE));
        Pane mainPane = loader.load();
        MainController mainController = loader.getController();
        PaneNavigator.setMainApp(this);
        PaneNavigator.setMainController(mainController);
        PaneNavigator.loadPane(PaneNavigator.START_PANE);

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        //new URL("file:resources/style/tab.css)
        File f = new File("resources/style/tab.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        //scene.getStylesheets().add(this.getClass().getResource("/resources/style/tab.css").toExternalForm());
        f = new File("resources/style/style.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        return scene;
    }


    public static void main(String[] args) {
        launch();
    }

    public ObservableList<Room> getRoomData() {
        return RoomData;
    }

    public ObservableList<StudentsGroup> getGroupData() {
        return GroupData;
    }

    public ObservableList<Course> getCourseData() {
        return CourseData;
    }

    public ObservableList<Instructor> getInstrutorData() {
        return InstructorData;
    }

    public ObservableList<Class> getClassData() {
        return ClassData;
    }

    public HashMap<String,Boolean> getWorkingDays() {
        return workingDays;
    }

    public int getPeriodsCount() {
        return periodsCount;
    }

    public void setPeriodsCount(int periodsCount) {
        this.periodsCount = periodsCount;
    }

    public ObservableList<Class> getGeneratedTableData() {
        return generatedTableData;
    }

    public void setGeneratedTableData(ArrayList<Class> generatedtable){
        generatedTableData.clear();
        generatedTableData.addAll(generatedtable);
    }
}
