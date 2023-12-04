package com.viepovsky.dto;

import java.io.Serializable;

public record Insurance(String insurer, float price) implements Serializable {
}
