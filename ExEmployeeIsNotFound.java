public class ExEmployeeIsNotFound extends Exception {
    public ExEmployeeIsNotFound() {
        super("Employee is not found!");
    }

    public ExEmployeeIsNotFound(String message) {
        super(message); // Employee Samuel is not found!
    }
}
