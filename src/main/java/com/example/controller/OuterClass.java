package com.example.controller;

class Outerclass {
    private String name;
    public void Stingto(String ident) {
        System.out.println(ident);
    }
    public  class innerclass{
        public void  to() {
            System.out.println(name);
        }
    }
    public innerclass innerclass() {
        return new innerclass();
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String identname = "tuo";
        Outerclass q = new Outerclass();
        Outerclass.innerclass c = q.innerclass();
        c.to();
    }

}
