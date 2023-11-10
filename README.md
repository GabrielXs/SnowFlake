# Kotlin Snowflake Library

A Kotlin library for understanding and implementing the Snowflake algorithm, which is commonly used for generating unique identifiers.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Snowflake algorithm is a widely-used method for generating unique identifiers, particularly in distributed systems. This Kotlin library is designed to help you learn and experiment with the Snowflake algorithm.

## Features

- Implementation of the Snowflake algorithm in Kotlin.
- Ability to generate unique IDs based on timestamp, machine ID, and sequence.
- Extraction of timestamp, machine ID, and sequence from Snowflake IDs.
- Configurable structure for customizing the number of bits used for each component.

## Getting Started

These instructions will help you get started with the Kotlin Snowflake Library.

### Prerequisites

- Kotlin 1.3 or higher
- Gradle or Maven for building the project
- A basic understanding of the Snowflake algorithm

### Installation

You can add this library to your project using [JitPack](https://jitpack.io/). Add the following to your project's build.gradle file:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.GabrielXs:kotlin-snowflake-library:1.0.0'
}

```

### Usage
Check out the documentation for detailed usage instructions and examples. You can also refer to the example project for a working implementation.

```kotlin
import dev.gabriel.snowflake.SnowflakeGenerator

val id = SnowflakeGenerator.nextId(1000)
println("Generated ID: $id")
```
