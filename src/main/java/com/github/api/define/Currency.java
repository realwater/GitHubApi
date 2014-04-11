package com.github.api.define;

public enum Currency {
    PENNY(1), NICKLE(5), DIME(10), QUARTER(25);
    private int value;

    private Currency(int value) {
        this.value = value;
    }

    public int getValue() {
		return value;
	}

    @Override
    public String toString() {
         switch (this) {
           case PENNY:
                System.out.println(value);
                break;
           case NICKLE:
                System.out.println( value);
                break;
           case DIME:
                System.out.println(value);
                break;
           case QUARTER:
                System.out.println(value);
          }
         return super.toString();
   }
};