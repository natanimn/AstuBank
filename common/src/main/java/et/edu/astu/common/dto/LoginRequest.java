package et.edu.astu.common.dto;

/**
 * Represents data for login
 * @param username username
 * @param password password
 *
 * @author Natanim
 */
public record LoginRequest(String username, String password) { }
