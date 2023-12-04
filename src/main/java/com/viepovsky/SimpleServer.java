package com.viepovsky;

import com.viepovsky.dto.Insurance;
import com.viepovsky.dto.Vehicle;
import com.viepovsky.dto.VehicleInsuranceResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.viepovsky.Port.PORT8090;

class SimpleServer {
    private static final DbManagerPostgres DB_MANAGER = DbManagerPostgres.getInstance();
    private static ServerSocket serverSocket;
    private static Socket socket;

    public static void main(String[] args) {

        try {
            createSocketsAndWaitForConnection();
            long userId = getUserIdFromRequest();
            PreparedStatement statement = prepareQuery(userId);
            ResultSet resultSet = statement.executeQuery();
            VehicleInsuranceResponse response = retrieveResponse(resultSet, statement);
            sendResponse(response);
            serverSocket.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createSocketsAndWaitForConnection() throws IOException {
        serverSocket = new ServerSocket(PORT8090.getPort());
        System.out.println("Waiting for client to establish connection.");
        socket = serverSocket.accept();
        System.out.println("Connection with client established.");
    }

    private static long getUserIdFromRequest() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        long userId = in.readLong();
        System.out.println("Retrieved request for user id = " + userId);
        return userId;
    }

    private static PreparedStatement prepareQuery(long userId) throws SQLException {
        String query = """
                    SELECT v.brand, v.model, i.insurer, i.price
                    FROM vehicles v
                    JOIN users u on (u.login = v.login)
                    JOIN insurance_offers i on (i.vehicle_id = v.id)
                    WHERE u.id = ?;
                    """;
        PreparedStatement statement = DB_MANAGER.getConnection().prepareStatement(query);
        statement.setLong(1, userId);
        return statement;
    }

    private static VehicleInsuranceResponse retrieveResponse(ResultSet rs, PreparedStatement statement) throws SQLException {
        HashMap<Vehicle, List<Insurance>> insurances = new HashMap<>();
        while (rs.next()) {
            Vehicle vehicle = new Vehicle(rs.getString("BRAND"), rs.getString("MODEL"));
            Insurance insurance = new Insurance(rs.getString("INSURER"), rs.getFloat("PRICE"));
            insurances.computeIfAbsent(vehicle, k -> new ArrayList<>()).add(insurance);
        }
        rs.close();
        statement.close();
        return new VehicleInsuranceResponse(insurances);
    }

    private static void sendResponse(VehicleInsuranceResponse response) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(response);
        System.out.println("Response sent.");
        socket.close();
    }
}
