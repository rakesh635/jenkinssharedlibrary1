package com.org.log

import com.cloudbees.groovy.cps.NonCPS

/*
* This is a file contianing the definite list of Log Level
* Application User can decide which Log Level to use. 
*/
enum LogLevel implements Serializable {

  INFO(1, 34),
  WARN(2, 35),
  DEBUG(3, 32),
  ERROR(4, 31),
  FATAL(5, 91),
  TRACE(6, 96),
  DEPRECATED(7, 93),
  ALL(8, 0),
  SYSTEM(9, 0)  

  Integer level

  Integer color

  private static final long serialVersionUID = 1L

  LogLevel(Integer level, Integer color) {
    this.level = level
    this.color = color
  }

  @NonCPS
  static LogLevel fromString(String value) {
    for (level in values()) {
      if (level.toString().equalsIgnoreCase(value)) return level
    }
    return null
  }

 @NonCPS
  public String getColorCode() {
    return color.toString()
  }
}
