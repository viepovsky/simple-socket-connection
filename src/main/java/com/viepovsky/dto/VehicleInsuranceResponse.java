package com.viepovsky.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public record VehicleInsuranceResponse(
        HashMap<Vehicle, List<Insurance>> insurances) implements Serializable {
}
