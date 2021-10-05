package ru.ayubdzhanov.javaquiz.controllers;

public class Bongo implements FirstOne, SecondOne{
    @Override
    public String getName() {
        return null;
    }

    public static void main(String[] args) {
        FirstOne firstOne = new Bongo();
        firstOne.getName();


        firstOne.showLala(); //test
        //another
        SecondOne secondOne = (SecondOne) firstOne;
        secondOne.getName();
    }
}
