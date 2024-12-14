package backend.academy;

/**
 * Represents a student with a first name and a last name.
 * This class is implemented as a Java record.
 *
 * @param name    the first name of the student.
 * @param surname the last name of the student.
 */
public record Student(String name, String surname) {
}
