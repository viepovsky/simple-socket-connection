package com.viepovsky;

import com.viepovsky.dto.Insurance;
import com.viepovsky.dto.Vehicle;
import com.viepovsky.dto.VehicleInsuranceResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static com.viepovsky.Port.PORT8090;

class SimpleClient {
    private static final String SERVER_IP_ADDRESS = "127.0.0.1";
    public static Socket socket;

    public static void main(String[] args) {
        try {
            createSocket();
            sendRequest();
            VehicleInsuranceResponse response = retrieveResponse();
            displayResponse(response);
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createSocket() throws IOException {
        socket = new Socket(SERVER_IP_ADDRESS, PORT8090.getPort());
        System.out.println("Client has started.");
    }

    private static void sendRequest() throws IOException {
        long userId = 25;
        System.out.println("Preparing request for user id = " + userId);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeLong(userId);
        System.out.println("Request sent.");
    }

    private static VehicleInsuranceResponse retrieveResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        VehicleInsuranceResponse response = (VehicleInsuranceResponse) in.readObject();
        System.out.println("Response received.");
        return response;
    }

    private static void displayResponse(VehicleInsuranceResponse response) {
        for (Map.Entry<Vehicle, List<Insurance>> carInsurances : response.insurances().entrySet()) {
            Vehicle vehicle = carInsurances.getKey();
            System.out.println("Possible insurances for car: " + vehicle.brand() + " " + vehicle.make());
            for (Insurance insurance : carInsurances.getValue()) {
                System.out.println("\tInsurer: " + insurance.insurer() + ", and price: " + insurance.price());
            }
        }
    }
}
