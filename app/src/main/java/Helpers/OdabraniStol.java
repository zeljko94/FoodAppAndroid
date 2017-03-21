package Helpers;

import models.Stol;

/**
 * Created by zeljko94 on 5.2.2017..
 */
public class OdabraniStol {
    public static Stol stol;

    public static Stol getStol() {
        return stol;
    }

    public static void setStol(Stol stol) {
        OdabraniStol.stol = stol;
    }
}
