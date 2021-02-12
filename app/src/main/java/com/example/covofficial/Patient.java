package com.example.covofficial;

public class Patient {

 String name,
         phone,
         kind,
         governate,
         address;

 public Patient() {
 }

 public Patient(String name, String phone, String kind, String governate, String address) {
  this.name = name;
  this.phone = phone;
  this.kind = kind;
  this.governate = governate;
  this.address = address;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getPhone() {
  return phone;
 }

 public void setPhone(String phone) {
  this.phone = phone;
 }

 public String getKind() {
  return kind;
 }

 public void setKind(String kind) {
  this.kind = kind;
 }

 public String getGovernate() {
  return governate;
 }

 public void setGovernate(String governate) {
  this.governate = governate;
 }

 public String getAddress() {
  return address;
 }

 public void setAddress(String address) {
  this.address = address;
 }
}
