package com.viepovsky.dto;

import java.io.Serializable;

public record Vehicle(String brand, String make) implements Serializable {
}
