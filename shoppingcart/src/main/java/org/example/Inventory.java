package org.example;

import java.util.Map;

public record Inventory(Map<String, Item> contents){}
