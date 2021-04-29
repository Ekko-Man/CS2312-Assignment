public class ExEmployeeNameAlreadyExists extends Exception {
    public ExEmployeeNameAlreadyExists() {
        super("Employee name already exists!");
    }

    public ExEmployeeNameAlreadyExists(String message) {
        super(message);
    }
}